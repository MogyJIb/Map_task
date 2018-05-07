package by.gstu.ip.mogyjib.map_task.remote;

import java.util.List;

import by.gstu.ip.mogyjib.map_task.models.results.Paginated;

/**
 * Data search complete listener, contain one method,
 * which called when search is complete and get response
 * results to process it.
 *
 * @author Evgeniy Shevtsov
 * @version 1.0
 * @see Paginated
 */
public interface OnDataSearchCompleteListener<T extends Paginated> {
    void onDataLoadComplete(List<T> result);
}
