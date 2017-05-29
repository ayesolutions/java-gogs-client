# Go Git Service API client for Java

[![Build Status](https://travis-ci.org/ayesolutions/java-gogs-client.svg?branch=develop)](https://travis-ci.org/ayesolutions/java-gogs-client)
[![Coverage Status](https://coveralls.io/repos/github/ayesolutions/java-gogs-client/badge.svg)](https://coveralls.io/github/ayesolutions/java-gogs-client)

This client library implements all REST methods up to Gogs version 0.9.99. 


## Get started

This package is hosted on Bintray 

 	<repositories>
 		<repository>
			<id>bintray-ayesolutions-maven</id>
 			<name>bintray</name>
 			<url>https://dl.bintray.com/ayesolutions/maven</url>
 		</repository>
 	</repositories>

    <dependencies>
        <dependency>
            <groupId>de.ayesolutions.gogs</groupId>
            <artifactId>gogs-client</artifactId>
            <version>1.0</version>
        </dependency>
    </dependencies>

## Usage

Register a Gogs client with username, password and access token: 

    GogsClient client = new GogsClient(
        "https://gogs.instance/api/v1", 
        "username",
        "password", 
        "12764abc476b4acb476b4ac76acb764");
    
    RepositoryService repositoryService = new RepositoryService(client);
    List<Repository> repositories = repositoryService.listRepoistories();
    
    UserService userService = new UserService(client);
    List<User> user = userService.search("user", 10);
