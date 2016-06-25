
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeProperties {
    
    public final Properties prop = new Properties();
    public final File propFile;

    public HomeProperties(Class clazz) {
        String userHome = System.getProperty("user.home");
        String propDir = userHome + "\\" + ".home\\" + clazz.getName();
        new File(propDir).mkdirs();
        propFile = new File(propDir+"\\app.store");
        //try { propFile.createNewFile(); } catch (IOException ex) {}
        try { prop.loadFromXML(new FileInputStream(propFile)); } catch (IOException ex) {}
    }
    
    public void put(String key, String value) {
        prop.setProperty(key, value);
        try {
            prop.storeToXML(new FileOutputStream(propFile), null);
        } catch (IOException ex) {
            Logger.getLogger(HomeProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String get(String key) {
        return prop.getProperty(key);
    }
    
    public void putBoolean(String key, boolean value) {
        if(value) put(key, "TRUE");
        else put(key, "FALSE");
    }
    public boolean getBoolean(String key) {
        String s = get(key);
        if(s.equalsIgnoreCase("TRUE")) return true;
        else return false;
    }

    public void putByteArray(String key, byte[] value) {
        String s = new sun.misc.BASE64Encoder().encode(value);
        put(key, s);
    }
    public byte[] getByteArray(String key) {
        String s = get(key);
        byte[] value = {};
        try {
            value = new sun.misc.BASE64Decoder().decodeBuffer(s);
        } catch (IOException ex) {
            Logger.getLogger(HomeProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }

    public void putInt(String key, int value) {
        put(key, String.valueOf(value));
    }
    public long getInt(String key) {
        String s = get(key);
        try {
            return Integer.parseInt(s);
        } catch (Exception ex) {
            return 0;
        }
    }

    public void putLong(String key, long value) {
        put(key, String.valueOf(value));
    }
    public long getLong(String key) {
        String s = get(key);
        try {
            return Long.parseLong(s);
        } catch (Exception ex) {
            return 0L;
        }
    }

    public void putFloat(String key, float value) {
        put(key, String.valueOf(value));
    }
    public float getFloat(String key) {
        String s = get(key);
        try {
            return Float.parseFloat(s);
        } catch (Exception ex) {
            return 0.0F;
        }
    }

    public void putDouble(String key, double value) {
        put(key, String.valueOf(value));
    }
    public double getDouble(String key) {
        String s = get(key);
        try {
            return Double.parseDouble(s);
        } catch (Exception ex) {
            return 0.0;
        }
    }

    public static void main(String[] args)
    {
        HomeProperties hp = new HomeProperties(java.util.List.class);
        System.out.println(hp.getBoolean("a"));
        System.out.println(hp.getBoolean("b"));
        System.out.println(hp.get("c"));
        System.out.println(hp.getLong("d"));
        byte[] bytes = hp.getByteArray("e");
        for(int i=0; i<bytes.length; i++)
        {
            System.out.println(String.format("bytes[%s]=%s", i, bytes[i]));
        }
        hp.putBoolean("a", true);
        hp.putBoolean("b", false);
        hp.put("c", "<あいうえお>");
        hp.putLong("d", 1234L);
        hp.putByteArray("e", new byte[] { 0x1, 0x2, 0x0 });
    }

}
