package top.bonsoirzw.pratice.page.admin;

import org.springframework.stereotype.Component;

/**
 * 分页基本信息
 */
@Component
public class Page {
    private int page = 1; //当前页码
    private int rows;
    private int offset; //偏移量

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getOffset() {
        this.offset = (page - 1) * rows;
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
