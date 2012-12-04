package nl.Just.BoterKaasEieren;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
public class Prefs extends PreferenceActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                addPreferencesFromResource(R.xml.settings);
         }

        public static boolean getSound(Context context) {
			return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("music", true);
		}

}
