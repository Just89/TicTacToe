package nl.Just.BoterKaasEieren;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TicTacToe extends Activity implements SensorEventListener {
	  
    //this array holds the status of each button whether
	//it is an X (=1), an O(=0), or blank (=2)
    int board[][];  
    // this is the map of buttons. Think of it as buttons[ROW][COLUMN]
    // (same for board)
    Button buttons[][];   
    // variables used throughout the app (some declared here for scope,
    // others for convenience)
    int i, j, k = 0;   
    // our dialogue where we will provide status updates
    TextView textView; 
    // The AI we will play against
    AI ai;
    
    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();  
        
        mSensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }
    
    private void init() {
		ai = new AI();
	    buttons = new Button[4][4];
	    board = new int[4][4];
	    
	    // get the objects defined in main.xml
	    textView = (TextView) findViewById(R.id.dialogue);
	    
	     buttons[1][3] = (Button) findViewById(R.id.one);
	     buttons[1][2] = (Button) findViewById(R.id.two);
	     buttons[1][1] = (Button) findViewById(R.id.three);
	     buttons[2][3] = (Button) findViewById(R.id.four);
	     buttons[2][2] = (Button) findViewById(R.id.five);
	     buttons[2][1] = (Button) findViewById(R.id.six);
	     buttons[3][3] = (Button) findViewById(R.id.seven);
	     buttons[3][2] = (Button) findViewById(R.id.eight);
	     buttons[3][1] = (Button) findViewById(R.id.nine);
	    
	    // set the values of the board to 2 (empty)
	     for (i = 1; i <= 3; i++) {
	        for (j = 1; j <= 3; j++)
	            board[i][j] = 2;
	     }
	     textView.setText("Click a button to start.");
	     // add the click listeners for each button
	     for (i = 1; i <= 3; i++) {
	        for (j = 1; j <= 3; j++) {
	            buttons[i][j].setOnClickListener(new MyClickListener(i, j));
	            if(!buttons[i][j].isEnabled()) {
	                 buttons[i][j].setText(" ");
	                 buttons[i][j].setEnabled(true);
	             }
	         }
	    }
	}
    /* How the board looks like
     |1-1|1-2|1-3|
     |2-1|2-2|2-3|
     |3-1|3-2|3-3|
     */
    //check the board to see if there is a winner
	public boolean checkBoard() {
	    // is the game over?
	    boolean gameOver = false;
	    
	    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
	    boolean soundOn = pref.getBoolean("music",true);
	    
	    // first check all possible combinations to see if the user has won.
	    if ((board[1][1] == 0 && board[2][2] == 0 && board[3][3] == 0)
	          || (board[1][3] == 0 && board[2][2] == 0 && board[3][1] == 0)
	          || (board[1][2] == 0 && board[2][2] == 0 && board[3][2] == 0)
	          || (board[1][3] == 0 && board[2][3] == 0 && board[3][3] == 0)
	          || (board[1][1] == 0 && board[1][2] == 0 && board[1][3] == 0)
	          || (board[2][1] == 0 && board[2][2] == 0 && board[2][3] == 0)
	          || (board[3][1] == 0 && board[3][2] == 0 && board[3][3] == 0)
	          || (board[1][1] == 0 && board[2][1] == 0 && board[3][1] == 0)) {
	                  
	                  // user has won
	                  textView.setText("Game over. You win!");
	                  MediaPlayer mp = MediaPlayer.create(this, R.raw.cheer3);
	                  if(Prefs.getSound(this)){
	                	  mp.start();
	                  }
	                  gameOver = true;
	                  
	    } else if ((board[1][1] == 1 && board[2][2] == 1 && board[3][3] == 1)
	          || (board[1][3] == 1 && board[2][2] == 1 && board[3][1] == 1)
	          || (board[1][2] == 1 && board[2][2] == 1 && board[3][2] == 1)
	          || (board[1][3] == 1 && board[2][3] == 1 && board[3][3] == 1)
	          || (board[1][1] == 1 && board[1][2] == 1 && board[1][3] == 1)
	          || (board[2][1] == 1 && board[2][2] == 1 && board[2][3] == 1)
	          || (board[3][1] == 1 && board[3][2] == 1 && board[3][3] == 1)
	          || (board[1][1] == 1 && board[2][1] == 1 && board[3][1] == 1)) {
	                
	                // computer has won
	                textView.setText("Game over. You lost!");
	                MediaPlayer mp1 = MediaPlayer.create(this, R.raw.boo);
	                if(Prefs.getSound(this)){
	                	  mp1.start();
	                  }
	                gameOver = true;
	                
	    }
	    // Nobody has won but we still need to make sure that we have empty spaces
	    else {
	        boolean isEmpty = true;
	        for(i=1; i<=3; i++)
	        {
	            for(j=1; j<=3; j++) 
	            {
	                if(board[i][j] == 2) 
	                {
	                    isEmpty = false;
	                    break;
	                }
	            }
	        }        
	        if(isEmpty) 
	        {
	            gameOver = true;
	            textView.setText("Game over. It's a draw!");
	        }
	    }
	    
	    return gameOver;
	}

		private class MyClickListener implements View.OnClickListener {
	    // this buttons position in the arrays
	    int x;
	    int y;
	    
	    public MyClickListener(int x, int y) {
	        this.x = x;
	        this.y = y;
	    }
	    
	    // handle the click event
	    public void onClick(View view) {
	        // check to see if the button is enabled
	        if(buttons[x][y].isEnabled()) {
	            buttons[x][y].setEnabled(false);
	            // mark it as belonging to the user
	            buttons[x][y].setText("O");
	            board[x][y] = 0;
	            
	            // check to see if the user has won, if not let AI take turn
	            if(!checkBoard()) {
	                Point point = ai.takeTurn(board);
	                ai.markSquare(point, buttons, board);
	            }
	        }
	    }
	}
	
	//Making the menu
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	//Adding menu items
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    switch (item.getItemId()) 
	    {
    		case R.id.new_game:
    				init();
    				return true;
	        case R.id.settings_title:
	                startActivity(new Intent(this, Prefs.class));
	                return true;
	        case R.id.about_title:
	        		startActivity(new Intent(this, About.class));
	        		return true;
	        case R.id.exit:
        		finish();
        		return true;
	    }
	  return false;
	}
	
	//Shake feature / sensor, when u shake the phone the message
	//"Click a button to start" will change into "Don't shake me!"
    public void onSensorChanged(SensorEvent se) {
        float x = se.values[0];
        float y = se.values[1];
        float z = se.values[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta; // perform low-cut filter
        if(mAccel > 2){
        	textView.setText("Don't shake me!");
        }
      }

      public void onAccuracyChanged(Sensor sensor, int accuracy) {
      }
    

    @Override
    protected void onResume() {
      super.onResume();
      mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
      mSensorManager.unregisterListener(this);
      super.onStop();
    }
	
}



