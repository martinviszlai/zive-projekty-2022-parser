package com.infobip.ziveprojektyparser;


import com.opencsv.CSVReader;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ConversationsParser {

    public void parseConversations() {
        var lines = readFile();
        var posts = convertToPosts(lines);


        var convos = new HashMap<String, PostDto>();
        posts.forEach(post -> convos.put(post.getId(), post));
        var rootConvos = new HashMap<String, PostDto>();

        for (Map.Entry<String, PostDto> stringPostDtoEntry : convos.entrySet()) {
            var post = stringPostDtoEntry.getValue();
            var parent = convos.get(post.getParentId());
            if (parent != null) {
                post.setParent(parent);
                parent.getChildren().add(post);
            } else {
                rootConvos.put(post.getId(), post);
            }
        }


        System.out.println("done. Convo count: " + rootConvos.size());
    }

    public List<PostDto> convertToPosts(List<String[]> lines) {
        return lines.parallelStream()
                .map(line -> {
                    if (line.length != 7) {
//                        System.out.println("invalid line! line size: "+line.length);
                        return null;
                    }
//                    System.out.println("processing line");
                    return PostDto.builder()
                            .id(line[0])
                            .author(line[1])
                            .isInbound(Boolean.parseBoolean(line[2]))
                            .createdAt(line[3])
                            .message(line[4])
                            .childId(line[5])
                            .parentId(line[6])
                            .build();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<String[]> readFile() {
        try {
            Path path = Paths.get(
                    ClassLoader.getSystemResource("input.csv").toURI());
            return readLineByLine(path);
        } catch (Exception e) {
            System.out.println("Failed");
        }
        return null;
    }

    public List<String[]> readLineByLine(Path filePath) throws Exception {
        List<String[]> list = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    list.add(line);
                }
            }
        }
        return list;
    }

}
