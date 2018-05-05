package by.gstu.ip.mogyjib.map_task.remote;

import java.util.Collection;
import java.util.List;

import by.gstu.ip.mogyjib.map_task.models.results.Paginated;

public interface OnDataLoadCompleteListener<T extends Paginated> {
    void onDataLoadComplete(Collection<T> result);
}
