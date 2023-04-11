package searchengine.services;

import searchengine.dto.response.ApiResultDto;

public interface IndexingService {

    ApiResultDto startIndexing();

    ApiResultDto stopIndexing();

    boolean isIndexingActive();

    boolean indexPage(String urlPage);

}
