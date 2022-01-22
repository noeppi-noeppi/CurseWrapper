package io.github.noeppi_noeppi.tools.cursewrapper.backend.data.util;

import com.google.gson.annotations.Expose;
import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseData;

public class Pagination implements CurseData {
    
    @Expose public int index;
    @Expose public int pageSize;
    @Expose public int resultCount;
    @Expose public int totalCount;

    public Pagination(int index, int pageSize, int resultCount, int totalCount) {
        this.index = index;
        this.pageSize = pageSize;
        this.resultCount = resultCount;
        this.totalCount = totalCount;
    }
}
