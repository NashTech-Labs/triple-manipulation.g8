package com.knoldus.route

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.knoldus.helper.QueryHelper
import com.knoldus.model.CassandraCluster
import com.knoldus.service.{DirectPredicateHashing, Hashing, PredicateHashing, TripleOperations}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}
import org.cassandraunit.utils.EmbeddedCassandraServerHelper
import com.knoldus.model.Triple


class TripleRouteSpec extends WordSpec with Matchers with ScalatestRouteTest with BeforeAndAfterAll {

  val queryHelper = new QueryHelper
  val cassandraCluster = new CassandraCluster(queryHelper)
  val hashing = new Hashing
  val predicateHashing = new PredicateHashing(cassandraCluster, hashing, queryHelper)
  val directPredicateHashing = new DirectPredicateHashing(cassandraCluster, queryHelper)
  val tripleOperations = new TripleOperations()(cassandraCluster, predicateHashing, directPredicateHashing)
  val tripleRoute = new TripleRoute(tripleOperations)
  val errorResponse = """{"Message":"Not Found"}"""
  val triple = Triple("Subject1", "Predicate1", "Object1")

  override def beforeAll(): Unit = {
    EmbeddedCassandraServerHelper.startEmbeddedCassandra("test-cassandra.yaml", 1000000L)
    cassandraCluster.createDatabase()
    cassandraCluster.createDPHTable()
    cassandraCluster.createPredicateSchema()
    tripleOperations.storeTriple(triple)
  }

  "Routes" should {
    "Get error message as JSON" in {
      val subject = "subTest"
      val predicate = "predTest"
      Get(s"/triples?subject=$subject&predicate=$predicate") ~>
        tripleRoute.getObjectForTriples ~> check {
        val result = responseAs[String]
        assert(result.contains(errorResponse))
      }
    }

    "Get triples as JSON" in {
      Get(s"/triples?subject=${triple.entry}&predicate=${triple.predicate}") ~>
        tripleRoute.getObjectForTriples ~> check {
        val result = responseAs[String]
        assert(!result.contains(errorResponse))
      }
    }
  }
}
