package site.test;

import java.util.UUID;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilders;

public class ElasticTest {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		InetSocketTransportAddress localInetSocketTransportAddress = new InetSocketTransportAddress("localhost", 9300);

		Integer samplingSize = 30;
		TransportClient client = new TransportClient().addTransportAddresses(localInetSocketTransportAddress);
		SearchResponse result = client.prepareSearch("big_accounts").setTypes("user")
				.setQuery(QueryBuilders.rangeQuery("balance").gte(1500))
				.setSize(samplingSize)
				.addSort(SortBuilders.scriptSort("sampling", "number").lang("groovy").param("salt", UUID.randomUUID().toString().substring(0, 9)))
				.execute().actionGet();

		System.out.println("Total results fetched :: " + result.getHits().getHits().length);
		for (SearchHit h : result.getHits()) {
			System.out.println("Balance is :: " + h.getSource().get("balance"));
		}

	}

}
