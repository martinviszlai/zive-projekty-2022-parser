package com.infobip.ziveprojektyparser;


public class ZiveProjektyParserApplication {

    public static void main(String[] args) {

        var conversationsParser = new ConversationsParser();
        conversationsParser.parseConversations();
    }

}
