package nl.Just.BoterKaasEieren;

import java.util.Random;

import android.graphics.Point;
import android.widget.Button;

public class AI
{
		public AI() {

	 	}
		
		public Point takeTurn(int[][] board) 
	 	{
	        if(board[1][1]==2 &&
	                ((board[1][2]==0 && board[1][3]==0) ||
	                  (board[2][2]==0 && board[3][3]==0) ||
	                  (board[2][1]==0 && board[3][1]==0))) 
	        {
	        	return new Point(1,1);
	        } else if (board[1][2]==2 &&
	                ((board[2][2]==0 && board[3][2]==0) ||
	                  (board[1][1]==0 && board[1][3]==0))) 
	        {
	        	return new Point(1,2);
	        } else if(board[1][3]==2 &&
	                ((board[1][1]==0 && board[1][2]==0) ||
	                  (board[3][1]==0 && board[2][2]==0) ||
	                  (board[2][3]==0 && board[3][3]==0))) 
	        {
	        	return new Point(1,3);
	        } else if(board[2][1]==2 &&
	                  ((board[2][2]==0 && board[2][3]==0) ||
	                   (board[1][1]==0 && board[3][1]==0))){
	        	return new Point(2,1);
	        } else if(board[2][2]==2 &&
	                  ((board[1][1]==0 && board[3][3]==0) ||
	                   (board[1][2]==0 && board[3][2]==0) ||
	                   (board[3][1]==0 && board[1][3]==0) ||
	                   (board[2][1]==0 && board[2][3]==0))) 
	        {
	        	return new Point(2,2);
	        } else if(board[2][3]==2 &&
	                  ((board[2][1]==0 && board[2][2]==0) ||
	                   (board[1][3]==0 && board[3][3]==0))) 
	        {
	        	return new Point(2,3);
	        } else if(board[3][1]==2 &&
	                  ((board[1][1]==0 && board[2][1]==0) ||
	                   (board[3][2]==0 && board[3][3]==0) ||
	                   (board[2][2]==0 && board[1][3]==0))){
	        	return new Point(3,1);
	        } else if(board[3][2]==2 &&
	                  ((board[1][2]==0 && board[2][2]==0) ||
	                   (board[3][1]==0 && board[3][3]==0))) 
	        {
	        	return new Point(3,2);
	        }else if( board[3][3]==2 &&
	                  ((board[1][1]==0 && board[2][2]==0) ||
	                   (board[1][3]==0 && board[2][3]==0) ||
	                   (board[3][1]==0 && board[3][2]==0))) 
	        {
	        	return new Point(3,3);
	        }
        // There is nothing to block so choose a random square
        else 
        {
              Random rand = new Random();
              int a = rand.nextInt(4);
              int b = rand.nextInt(4);
              while(a==0 || b==0 || board[a][b]!=2) 
              {
                  a = rand.nextInt(4);
                  b = rand.nextInt(4);
              }
              return new Point(a,b);
        }
    }
	   // Mark the selected square
    public void markSquare(Point pos, Button[][] buttons, int[][] board) 
    {
        buttons[pos.x][pos.y].setEnabled(false);
        buttons[pos.x][pos.y].setText("X");
        board[pos.x][pos.y] = 1;
    }
}
