import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.zip.*;

public class Main {

    public static final String savePath = "D:\\Games\\savegames\\";

    public static void main(String[] args) throws IOException {

        GameProgress game1 = new GameProgress(5, 5, 6, 33.3);
        saveGame(game1, "game1");
        GameProgress game2 = new GameProgress(8, 9, 15, 111.11);
        saveGame(game2, "game2");
        GameProgress game3 = new GameProgress(3, 2, 1, 1.1);
        saveGame(game3, "game3");

        ArrayList<String> arrayList = arhiv();

        if (arrayList != null) {
            if (zipFiles("zip.zip", arrayList)) {
                System.out.println("Файлы успешно добавлены в архив");
                deleteFile(arrayList);
            } else {
                System.out.println("Не удалось добавить файлы в архив");
            }
        }

    }

    public static ArrayList<String> arhiv() {
        ArrayList<String> arrayList = new ArrayList<>();
        File dir = new File(savePath);
        if (dir.isDirectory()) {
            for (File item : Objects.requireNonNull(dir.listFiles())) {
                if (item.isFile()) {
                    if (item.getName().contains(".smg")) {
                        arrayList.add(item.getName());
                    }
                }
            }
        }
        return arrayList;
    }

    private static boolean saveGame(GameProgress game, String arr) {
        try (FileOutputStream fos = new FileOutputStream(savePath + arr + ".smg");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + '\n');
        }
        return false;
    }

    private static boolean zipFiles(String path, ArrayList<String> arrayList) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(savePath + path))) {
            for (String arr : arrayList) {
                FileInputStream fis = new FileInputStream(savePath + arr);
                ZipEntry entry = new ZipEntry(arr);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean deleteFile(ArrayList<String> filesToDelete) throws IOException {
        try {
            for (String fileToDelete : filesToDelete) {
                Path del = Path.of(savePath + fileToDelete);
                Files.delete(del);
            }
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }

}
