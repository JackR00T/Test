package org.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileCollector {
    public List<Path> collectFiles(Path startDir, String fileName) {
        List<String> targetFiles = List.of(
                "pom.xml",
                "build.gradle",
                "setting.gradle"
        );

        List<Path> filesCollected = new ArrayList<>();
        try {
            Files.walkFileTree(startDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (targetFiles.contains(file.getFileName().toString())){
                        filesCollected.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.err.println("Error walking through the directory: " + e.getMessage());
        }
        return filesCollected;
    }

    public static void zipFiles(List<Path> files, String zipFileName) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName))) {
            for (Path file : files) {
                // 假设所有文件都位于一个公共的根目录下，此处使用该目录的路径作为基准
                Path rootDir = Paths.get("/Users/jackr00t/Data/Code/Java/test/src/main/resources/code");

                // 从文件完整路径中提取出相对于根目录的路径
                Path relativePath = rootDir.relativize(file);

                // 创建ZIP条目
                zos.putNextEntry(new ZipEntry(relativePath.toString()));

                // 读取文件数据并写入到ZIP输出流中
                byte[] bytes = Files.readAllBytes(file);
                zos.write(bytes, 0, bytes.length);
                zos.closeEntry();
            }
        } catch (IOException e) {
            System.err.println("I/O Error: " + e);
        }
    }
}
