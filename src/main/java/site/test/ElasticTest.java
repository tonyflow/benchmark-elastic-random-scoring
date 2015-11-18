package site.test;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermFilterBuilder;

public class ElasticTest {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		InetSocketTransportAddress localInetSocketTransportAddress = new InetSocketTransportAddress("localhost", 9300);
		InetSocketTransportAddress remoteInetSocketTransportAddress = new InetSocketTransportAddress("lhvmsrv81.lh.upstreamsystems.com", 9300);

		TransportClient client = new TransportClient().addTransportAddresses(remoteInetSocketTransportAddress, localInetSocketTransportAddress);
		SearchResponse result = client.prepareSearch("mtel_user_idx_1_3").setTypes("user")
				.setQuery(QueryBuilders.filteredQuery(new MatchAllQueryBuilder(), new TermFilterBuilder("lala", true)))
				.execute().actionGet();

	}

}
