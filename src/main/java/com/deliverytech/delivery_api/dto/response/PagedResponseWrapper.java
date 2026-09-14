package com.deliverytech.delivery_api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import java.util.List;

@Schema(description = "Wrapper para respostas paginadas")
public class PagedResponseWrapper<T> {

    @Schema(description = "Lista de itens da página atual")
    private List<T> content;

    @Schema(description = "Informações de paginação")
    private PageInfo page;

    @Schema(description = "Links de navegação")
    private PageLinks links;

    public PagedResponseWrapper(Page<T> page) {
        this.content = page.getContent();
        this.page = new PageInfo(
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isLast()
        );
    }

    public List<T> getContent() { return content; }
    public void setContent(List<T> content) { this.content = content; }

    public PageInfo getPage() { return page; }
    public void setPage(PageInfo page) { this.page = page; }

    public PageLinks getLinks() { return links; }
    public void setLinks(PageLinks links) { this.links = links; }

    @Schema(description = "Informações detalhadas da página")
    public static class PageInfo {

        @Schema(description = "Número da página atual (começa em 0)", example = "0")
        private int number;

        @Schema(description = "Tamanho da página", example = "10")
        private int size;

        @Schema(description = "Total de elementos", example = "42")
        private long totalElements;

        @Schema(description = "Total de páginas", example = "5")
        private int totalPages;

        @Schema(description = "Indica se é a última página", example = "false")
        private boolean last;

        public PageInfo(int number, int size, long totalElements, int totalPages, boolean last) {
            this.number = number;
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
            this.last = last;
        }

        public int getNumber() { return number; }
        public void setNumber(int number) { this.number = number; }

        public int getSize() { return size; }
        public void setSize(int size) { this.size = size; }

        public long getTotalElements() { return totalElements; }
        public void setTotalElements(long totalElements) { this.totalElements = totalElements; }

        public int getTotalPages() { return totalPages; }
        public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

        public boolean isLast() { return last; }
        public void setLast(boolean last) { this.last = last; }
    }

    @Schema(description = "Links de navegação entre páginas")
    public static class PageLinks {

        @Schema(description = "Link para a primeira página")
        private String first;

        @Schema(description = "Link para a página anterior")
        private String prev;

        @Schema(description = "Link para a próxima página")
        private String next;

        @Schema(description = "Link para a última página")
        private String last;

        public String getFirst() { return first; }
        public void setFirst(String first) { this.first = first; }

        public String getPrev() { return prev; }
        public void setPrev(String prev) { this.prev = prev; }

        public String getNext() { return next; }
        public void setNext(String next) { this.next = next; }

        public String getLast() { return last; }
        public void setLast(String last) { this.last = last; }
    }
}