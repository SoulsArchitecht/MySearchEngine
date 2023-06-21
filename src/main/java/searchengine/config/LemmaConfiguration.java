package searchengine.config;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

public class LemmaConfiguration {

    @Bean
    public LuceneMorphology russianLuceneMorphology() throws IOException {
        return new RussianLuceneMorphology();
    }

    @Bean
    public LuceneMorphology luceneMorphology() throws IOException {
        return new EnglishLuceneMorphology();
    }
}
