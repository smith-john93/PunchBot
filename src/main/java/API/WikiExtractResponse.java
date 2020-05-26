package API;

public class WikiExtractResponse {

    private boolean batchcomplete;
    private Query query;

    public WikiExtractResponse(boolean batchcomplete, Query query) {
        this.batchcomplete = batchcomplete;
        this.query = query;
    }

    public boolean getBatchComplete() {
        return batchcomplete;
    }

    public Query getQuery() {
        return query;
    }

    public static class Query {
        private Normalized[] normalized;
        private Pages[] pages;

        public Query(Normalized[] normalized, Pages[] pages) {
            this.normalized = normalized;
            this.pages = pages;
        }

        public Normalized[] getNormalized() {
            return normalized;
        }

        public Pages[] getPages() {
            return pages;
        }
    }

    public static class Normalized {
        private String fromencoded;
        private String from;
        private String to;

        public Normalized(String fromencoded, String from, String to) {
            this.fromencoded = fromencoded;
            this.from = from;
            this.to = to;
        }

        public String getFromencoded() {
            return fromencoded;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }
    }

    public static class Pages {
        private int pageid;
        private int ns;
        private String title;
        private String extract;

        public Pages(int pageid, int ns, String title, String extract) {
            this.pageid = pageid;
            this.ns = ns;
            this.title = title;
            this.extract = extract;
        }

        public int getPageid() {
            return pageid;
        }

        public int getNs() {
            return ns;
        }

        public String getTitle() {
            return title;
        }

        public String getExtract() {
            return extract;
        }
    }
}
