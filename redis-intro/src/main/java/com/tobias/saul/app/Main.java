package com.tobias.saul.app;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class Main {
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Jedis jedis = new Jedis();
		
		// Redis Strings
		
		jedis.set("events/city/rome", "32, 15, 223, 42");
		//has an expiration
		String cachedResponse = jedis.get("events/city/rome");
		
		System.out.println(cachedResponse);
		
		// Redis Lists - list of strings
		jedis.lpush("queue#tasks", "firstTask");
		jedis.lpush("queue#tasks", "secondTask");
		
		System.out.println(jedis.rpop("queue#tasks"));
		
		// Redis Sets - unordered collection of Strings
		// useful for excluding repeated members
		jedis.sadd("nicknames", "nickname#1");
		jedis.sadd("nicknames", "nickname#2");
		jedis.sadd("nicknames", "nickname#1");
		
		Set<String> nicknames = jedis.smembers("nicknames");
		nicknames.forEach(System.out::println);
		System.out.println(jedis.sismember("nicknames", "nickname#1"));
		
		
		
		
	}

}
