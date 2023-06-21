package searchengine.common;

import searchengine.config.LemmaConfiguration;
import searchengine.exception.SeRuntimeException;

import java.io.IOException;
import java.util.*;

public record LemmaEngine(LemmaConfiguration lemmaConfig) {

    private final static String RUSSIAN_HIGH_CASE_LETTER_REGEX =
            "([^а-я\\s])";

    public Map<String, Integer> getLemmaMap(String text) {
        Map<String, Integer> lemmaMap = new HashMap<>();
        String[] elements = arrayContainsWords(text).toLowerCase(Locale.ROOT)
                .split("\\s+");
        List<String> wordList;
        int count;
        for (String el : elements) {
            try {
                wordList = getLemma(el);
            } catch (Exception e) {
                throw new SeRuntimeException(e.getMessage());
            }

            for (String word : wordList) {
                count = lemmaMap.getOrDefault(word, 0);
                lemmaMap.put(word, count + 1);
            }
        }
        return lemmaMap;
    }

    private String arrayContainsWords(String text) {
        return text.toLowerCase(Locale.ROOT)
                .replaceAll(RUSSIAN_HIGH_CASE_LETTER_REGEX, " ")
                .trim();
    }

    private String detectLanguage(String word) {
        String russianAlphabet = "[a-яёА-ЯЁ]+";
        String englishAlphabet = "[a-zA-Z]+";
        if (word.matches(russianAlphabet)) {
            return "Russian";
        } else if (word.matches(englishAlphabet)) {
            return "English";
        } else {
            return "";
        }
    }

    public List<String> getLemma(String word) throws IOException {
        List<String> lemmaList = new ArrayList<>();
        if (detectLanguage(word).equals("Russian")) {
            List<String> basicRusForm = lemmaConfig
                    .russianLuceneMorphology()
                    .getNormalForms(word);
            if (!word.isEmpty() && !isCorrectWordForm(word)) {
                lemmaList.addAll(basicRusForm);
            }
        }
        return lemmaList;
    }

    private boolean isCorrectWordForm(String word) throws IOException {
        List<String> morphForm = lemmaConfig.russianLuceneMorphology()
                .getMorphInfo(word);
        for (String l : morphForm) {
            if (l.contains("ПРЕДЛ") || l.contains("ВВОДН") || l.contains("МЕЖД")
                || l.contains("ЧАСТ") || l.length() <= 3) {
                return true;
            }
        }
        return false;
    }
}
