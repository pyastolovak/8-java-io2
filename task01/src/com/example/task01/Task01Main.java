package com.example.task01;

import java.io.File;
import java.io.IOException;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //здесь вы можете вручную протестировать ваше решение, вызывая реализуемый метод и смотря результат
        // например вот так:

        /*
        System.out.println(extractSoundName(new File("task01/src/main/resources/3727.mp3")));
        */
    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        Process p = new ProcessBuilder(
                "ffprobe", "-v", "error",
                "-show_entries", "format_tags=title",
                "-of", "default=noprint_wrappers=1:nokey=1",
                file.getAbsolutePath()
        ).redirectErrorStream(true).start();

        String title;
        try (java.io.InputStream in = p.getInputStream();
             java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream()) {
            byte[] buf = new byte[4096];
            int n;
            while ((n = in.read(buf)) != -1) baos.write(buf, 0, n);
            title = baos.toString(java.nio.charset.StandardCharsets.UTF_8.name()).trim();
        }
        p.waitFor();
        return title.isEmpty() ? null : title;
    }
}