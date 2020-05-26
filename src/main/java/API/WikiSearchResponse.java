package API;

public class WikiSearchResponse {

    private String batchcomplete;
    private Continue con;
    private Query query;

    public WikiSearchResponse(String searchToken, Continue resultList, Query query) {
        this.batchcomplete = searchToken;
        this.con = resultList;
        this.query = query;
    }

    public String getSearchToken() {
        return batchcomplete;
    }

    public Continue getResultList() {
        return con;
    }

    public Query getQuery() {
        return query;
    }

    public static class Continue {

        private int psoffset;
        private String con;

        public Continue(int psoffset, String con) {
            this.psoffset = psoffset;
            this.con = con;
        }

        public int getPsoffset() {
            return psoffset;
        }

        public String getCont() {
            return con;
        }
    }

    public static class Query {
        private PrefixResult[] prefixsearch;

        public Query(PrefixResult[] prefixsearch) {
            this.prefixsearch = prefixsearch;
        }
        public PrefixResult[] getPrefixSearch() {
            return prefixsearch;
        }
    }

    public static class PrefixResult {
        private int ns;
        private String title;
        private int pageid;


        public PrefixResult(int ns, String title, int pageid) {
            this.ns = ns;
            this.title = title;
            this.pageid = pageid;
        }

        public int getNs() {
            return ns;
        }

        public String getTitle() {
            return title;
        }

        public int getPageid() {
            return pageid;
        }
    }
}

