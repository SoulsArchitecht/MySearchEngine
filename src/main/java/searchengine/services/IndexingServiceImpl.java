package searchengine.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.SiteConfig;
import searchengine.config.SitesListConfig;
import searchengine.dto.response.ApiResultDto;
import searchengine.model.Site;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static searchengine.model.Status.INDEXING;

@Slf4j
@Service
public class IndexingServiceImpl implements IndexingService {

    private final SiteRepository siteRepository;
    private final PageRepository pageRepository;
    private final SitesListConfig sitesList;
    private ExecutorService executorService;

    @Autowired
    public IndexingServiceImpl(SiteRepository siteRepository, PageRepository pageRepository, SitesListConfig sitesList) {
        this.siteRepository = siteRepository;
        this.pageRepository = pageRepository;
        this.sitesList = sitesList;
    }


    @Override
    public ApiResultDto startIndexing() {
        if (isIndexingActive()) {
            log.debug(Message.INDEXING_IS_ALREADY_RUNNING_LOG);
            new ApiResultDto(false, Message.INDEXING_IS_ALREADY_RUNNING_RESULT).getErrorMessage();
        } else {
            List<SiteConfig> siteList = sitesList.getSites();
            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for (SiteConfig siteConfig: siteList) {
                String url = siteConfig.getUrl();
                Site site = new Site();
                site.setName(siteConfig.getName());
                log.info(Message.INDEXING_WEB_SITE_LOG.concat(site.getName()));
                //TODO
                //executorService.submit(new SiteIndexed());
            }
            executorService.shutdown();
        }
        return new ApiResultDto(true);
    }

    @Override
    public ApiResultDto stopIndexing() {
        if (!isIndexingActive()) {
            log.info(Message.SITE_INDEXING_IS_ALREADY_RUNNING_LOG);
            return new ApiResultDto(false, Message.INDEXING_NOT_RUN_RESULT);
        } else {
            log.info(Message.INDEXING_STOPPED_LOG);
            executorService.shutdown();
            return new ApiResultDto(true);
        }
    }

    @Override
    public boolean isIndexingActive() {
        siteRepository.flush();
        Iterable<Site> siteList = siteRepository.findAll();
        for (Site site: siteList) {
            if (site.getStatus().equals(INDEXING)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean indexPage(String urlPage) {
        return false;
    }
}
