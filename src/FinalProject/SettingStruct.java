package FinalProject;

import java.util.prefs.Preferences;

public class SettingStruct {
    public static int numGhosts = 1;
    public static int numGhostsIncPerLevel = 2;



    public static void storePreferences(Class c){
        Preferences pref = Preferences.userNodeForPackage(c);
        pref.putInt("numGhostsPerLevel",numGhostsIncPerLevel);
        pref.putInt("numGhosts",numGhosts);

    }

    public static void readPreferences(Class c){
        Preferences pref = Preferences.userNodeForPackage(c);
        numGhostsIncPerLevel = pref.getInt("numGhostsPerLevel",numGhostsIncPerLevel);
        numGhosts = pref.getInt("numGhosts",numGhosts);
    }
}
