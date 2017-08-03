package com.luzhanov.cassandrademo.repository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class DataInitializer {

    public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS testKeySpace "
            + "WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

    @Autowired
    private CassandraClusterFactoryBean clusterFactory;

    @PostConstruct
    public void init() {
        final Cluster cluster = clusterFactory.getObject();

        final Session session = cluster.connect();
        session.execute(KEYSPACE_CREATION_QUERY);
        log.warn("Datamodel initialized");
    }

}
