package API;

public class WikiSearchDTO {
    public String action;
    public String namespace;
    public String search;
    public String limit;
    public String format;

    public WikiSearchDTO(String search) {
        this.action = "opensearch";
        this.namespace = "article";
        this.search = search;
        this.limit = "5";
        this.format = "json";
    }


}
