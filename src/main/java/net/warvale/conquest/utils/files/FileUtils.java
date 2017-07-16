package net.warvale.conquest.utils.files;

import net.warvale.conquest.ConquestCore;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.*;
import java.util.logging.Level;

public class FileUtils {

    private static final int BUFFER = 2048;

    public static void loadFile(String file)
    {
        File t = new File(ConquestCore.get().getDataFolder(), file);
        ConquestCore.get().getLogger().log(Level.INFO, "Writing new file: " + t.getAbsolutePath());

        try {
            t.createNewFile();
            FileWriter out = new FileWriter(t);
            InputStream is = ConquestCore.get().getResource(file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                out.write(line + "\n");
            }
            out.flush();
            is.close();
            isr.close();
            br.close();
            out.close();

            ConquestCore.get().getLogger().log(Level.INFO, "Loaded Config: " + file + " successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean moveFile(File oldConfig) {
        ConquestCore.get().getLogger().log(Level.INFO, "Moving outdated config file: " + oldConfig.getName());
        String name = oldConfig.getName();
        File configBackup = new File(ConquestCore.get().getDataFolder(), getNextName(name, 0));
        return oldConfig.renameTo(configBackup);
    }

    private static String getNextName(String name, int n){
        File oldConfig = new File(ConquestCore.get().getDataFolder(), name+".old"+n);
        if(!oldConfig.exists()){
            return oldConfig.getName();
        }
        else{
            return getNextName(name, n+1);
        }
    }

    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            for (String child : dir.list()) {
                deleteDir(new File(dir, child));
            }
        }
        if (!dir.delete()) {
            dir.deleteOnExit();
        }
    }

    public static void copy(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            if (!dest.exists()) {
                dest.mkdir();
            }
            String[] files = src.list();
            for (String file : files) {
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);

                copy(srcFile, destFile);
            }
        } else {
            InputStream in = new FileInputStream(src);
            Object out = new FileOutputStream(dest);

            byte[] buffer = new byte['?'];
            int length;
            while ((length = in.read(buffer)) > 0) {
                ((OutputStream) out).write(buffer, 0, length);
            }
            in.close();
            ((OutputStream) out).close();
        }
    }

    public static void setPermissions(File dir, boolean writable, boolean readable, boolean runnable) {
        dir.setWritable(writable);
        dir.setReadable(readable);
        dir.setExecutable(runnable);
        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                setPermissions(f, writable, readable, runnable);
            }
        }
    }

    public static void unZip(String zipFile, String extractFolder) throws IOException {
        File file = new File(zipFile);

        ZipFile zip = new ZipFile(file);
        String newPath = extractFolder;

        new File(newPath).mkdir();
        Enumeration zipFileEntries = zip.entries();
        while (zipFileEntries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
            String currentEntry = entry.getName();

            File destFile = new File(newPath, currentEntry);

            File destinationParent = destFile.getParentFile();

            destinationParent.mkdirs();
            if (!entry.isDirectory()) {
                BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));

                byte[] data = new byte[BUFFER];

                FileOutputStream fos = new FileOutputStream(destFile);
                BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
                int currentByte;
                while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                    dest.write(data, 0, currentByte);
                }
                dest.flush();
                dest.close();
                is.close();
            }
        }
    }

}
