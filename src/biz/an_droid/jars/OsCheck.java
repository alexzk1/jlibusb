package biz.an_droid.jars;

/**
 * helper class to check the operating system this Java VM runs in
 *
 * please keep the notes below as a pseudo-license
 *
 * http://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java
 * compare to http://svn.terracotta.org/svn/tc/dso/tags/2.6.4/code/base/common/src/com/tc/util/runtime/Os.java
 * http://www.docjar.com/html/api/org/apache/commons/lang/SystemUtils.java.html
 */
import java.util.Locale;
public final class OsCheck
{
    /**
     * types of Operating Systems
     */
    public enum OSType {
        Windows, MacOS, Linux, Android, Other
    };

    public enum CpuBitness
    {
        b32, b64
    }
    // cached result of OS detection
    protected static OSType detectedOS;

    /**
     * detect the operating system from the os.name System property and cache
     * the result
     *
     * @returns - the operating system detected
     */
    public static OSType getOperatingSystemType() {
        if (detectedOS == null)
        {
            if (System.getProperty("java.vendor").toLowerCase().contains("android"))
                    detectedOS = OSType.Android;
            else
            {
                String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
                if ((OS.contains("mac")) || (OS.contains("darwin")))
                {
                    detectedOS = OSType.MacOS;
                } else if (OS.contains("win"))
                {
                    detectedOS = OSType.Windows;
                } else if (OS.contains("nux"))
                {
                    detectedOS = OSType.Linux;
                } else
                {
                    detectedOS = OSType.Other;
                }
            }
        }
        return detectedOS;
    }

    public static CpuBitness getBitness()
    {
        if (System.getProperty("os.arch").contains("64"))
            return CpuBitness.b64;
        return CpuBitness.b32;
    }

    public static String getJarLoadPath()
    {
        String res = "not_supported";
        if (getOperatingSystemType() == OSType.Android)
        {
            res = "/lib/armeabi-v7a";
        }
        else
        {
            if (System.getProperty("os.arch").equalsIgnoreCase("arm"))
                res = "/lib/linarm";
            else
            {
                String bitness = "32";
                if (System.getProperty("os.arch").contains("64"))
                    bitness = "64";

                switch (getOperatingSystemType())
                {

                    case Windows:
                        res = "/lib/win" + bitness;
                        break;
                    case MacOS:
                        res = "/lib/darwin" + bitness;
                        break;
                    case Linux:
                        res = "/lib/lin" + bitness;
                        break;
                    case Other:
                        break;
                }
            }
        }
        return res;
    }
}