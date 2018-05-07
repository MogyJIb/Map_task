package by.gstu.ip.mogyjib.map_task.models.results;

/**
 * Interface which should implements each search
 * result class, it contain one method, method return
 * next page token to load next page of data from
 * server. Result data can be paginated and you
 * should load all pages
 *
 * @author Evgeniy Shevtsov
 * @version 1.0
 */
public interface Paginated {
    /**
     * Get next page token
     * @return  string page token
     */
    String getNextPageToken();
}
