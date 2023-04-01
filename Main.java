import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;

public class Main {
    public static void main(String[] args) {

        GameProgress game1 = new GameProgress(5, 5, 6, 33.3);
        saveGame("D://games//savegames//save1.dat", game1);
        GameProgress game2 = new GameProgress(8, 9, 15, 111.11);
        saveGame("D://games//savegames//save2.dat", game2);
        GameProgress game3 = new GameProgress(3, 2, 1, 1.1);
        saveGame("D:/games/savegames/save3.dat", game3);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("D://games//savegames//save1.dat");
        arrayList.add("D://games//savegames//save2.dat");
        arrayList.add("D://games//savegames//save3.dat");
        zipFiles("D://Games//savegames//zip.zip", arrayList);
    }

    private static void saveGame(String path, GameProgress game) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + '\n');
        }
    }

    private static void zipFiles(String path, List<String> arrayList) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String arr : arrayList) {
                try (FileInputStream fis = new FileInputStream(arr)) {
                    ZipEntry entry = new ZipEntry(arr);
                    zout.putNextEntry(entry);
                    while (fis.available() > 0) {
                        zout.write(fis.read());
                    }
                    zout.closeEntry();

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}