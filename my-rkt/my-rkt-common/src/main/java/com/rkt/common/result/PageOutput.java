

package com.rkt.common.result;

public class PageOutput<T> extends BaseOutput<T> {

    private long count;
    private int pageSize;
    private int totalPage;
    private int page;

    @Override
    public T getData() {
        return super.getData();
    }

    @Override
    public void setData(T data) {
        super.setData(data);
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

}
