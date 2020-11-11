package com.hkycu.goods.elastic.config;
        import org.apache.http.HttpHost;
        import org.apache.http.auth.AuthScope;
        import org.apache.http.auth.UsernamePasswordCredentials;
        import org.apache.http.client.CredentialsProvider;
        import org.apache.http.impl.client.BasicCredentialsProvider;
        import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
        import org.elasticsearch.client.RestClient;
        import org.elasticsearch.client.RestClientBuilder;
        import org.elasticsearch.client.RestHighLevelClient;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;

@Configuration
public class RestHighLevelClientConfig {
    private RestClientBuilder builder;

    public RestHighLevelClientConfig(RestClientBuilder builder) {
        this.builder = builder;
    }

    @Bean
    public RestHighLevelClient client() {
        // 用户认证对象
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        // 设置账户密码
        credentialsProvider.setCredentials(
                AuthScope.ANY, new UsernamePasswordCredentials("", ""));
        // 创建rest client对象
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("192.168.122.128", 9200))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder
                    customizeHttpClient(HttpAsyncClientBuilder
                                                httpAsyncClientBuilder) {
                        return  httpAsyncClientBuilder
                                .setDefaultCredentialsProvider(
                                        credentialsProvider);
                    }
                });
        RestHighLevelClient client = new RestHighLevelClient(builder);
        System.err.println("【=====================client=====================】" +
                client);
        return client;
    }
}