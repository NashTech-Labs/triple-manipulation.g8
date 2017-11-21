A **Giter8** template for Triple Manipulation

### Step for Storing Triples to Cassandra
##### Pre-Requirements
Apache Cassandra running on default port [i.e. 9042]

*sbt "runMain com.knoldus.TripleLoader </PATH/TO/TRIPLE/FILE>"*

### Step to Start Searching Endpoint

##### Pre-Requirements
1. Apache Cassandra running on default port [i.e. 9042]
2. Triples must be loaded into Cassandra. To load triple you can visit [Wiki](https://github.com/knoldus/triple-manipulation.g8/wiki)

*sbt "runMain com.knoldus.route.TripleRoute"*

Searching Endpoint will be avaialbe on port `8082`.
###### Required Query Parameters :- 
    
    subject
    predicate
    
**Output:-** Triple value as JSON

Template license
----------------
Written in 2017 by Knoldus info@knoldus.com

To the extent possible under law, the author(s) have dedicated all copyright and related
and neighboring rights to this template to the public domain worldwide.
This template is distributed without any warranty. See <http://knoldus.com>.
