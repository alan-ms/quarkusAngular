package br.com.alanms.dto;

public class PageDTO {

    private Integer page;

    private Integer pages;

    private String per_page;

    private Long total;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getPer_page() {
        return per_page;
    }

    public void setPer_page(String per_page) {
        this.per_page = per_page;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}

//{
//        "page": 1,
//        "pages": 6,
//        "per_page": "50",
//        "total": 299
//        }