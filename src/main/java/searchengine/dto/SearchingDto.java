package searchengine.dto;

public record SearchingDto (String site, String siteName, String uri, String title, String snippet, float relevance){
}
