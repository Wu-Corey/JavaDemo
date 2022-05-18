package com.example.javademo.tool;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2022/5/12
 */
public class kindleNoteConverter {

    public static void main(String[] args) throws IOException {
        List<String> lines = new ArrayList<>();

        Map<String, List<String>> books = new HashMap<>();
        BufferedReader br= Files.newBufferedReader(Paths.get("C:/Users/wyc/Desktop/My Clippings.txt"));
        BufferedWriter bw = Files.newBufferedWriter(Paths.get("C:/Users/wyc/Desktop/notes.txt"));
        try{
            String line;
            while ((line=br.readLine())!=null){
                lines.add(line);
            }

            int i = 0;
            while (i < lines.size()-1){
                String bookName = "";
                String nextLine = lines.get(i + 1);
                if (!StringUtils.isEmpty(nextLine)){
                    char ch = lines.get(i + 1).charAt(0);
                    if (ch == '-'){
                        bookName = lines.get(i);

                        if (!books.containsKey(bookName)){
                            books.put(bookName,new ArrayList<>());
                        }

                        i = i+3;
                        StringBuilder sb = new StringBuilder(lines.get(i++));

                        while (lines.get(i).length() > 0  && lines.get(i).charAt(0) != '='){
                            sb.append(lines.get(i));
                            i++;
                        }
                        List<String> notes = books.get(bookName);

                        notes.add(sb.toString());

                        books.put(bookName,notes);
                    }
                }
                i++;
            }

            // 输出

            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, List<String>> entry : books.entrySet()) {
                sb.append(entry.getKey() + "\n");

                for (String s : entry.getValue()) {
                    sb.append(s + "\n\n");
                }
                sb.append("\n\n");
            }

            bw.write(sb.toString());
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            bw.close();
            br.close();
        }

    }

}
