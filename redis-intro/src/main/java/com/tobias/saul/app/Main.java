package com.tobias.saul.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class Main {
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Jedis jedis = new Jedis();
		
		// Redis Strings
		System.out.println("----Redis String----\n");
		jedis.set("events/city/rome", "32, 15, 223, 42");
		//has an expiration
		String cachedResponse = jedis.get("events/city/rome");
		
		System.out.println("events/city/rome: " + cachedResponse);
		System.out.println("\n--------------------\n");
		
		// Redis Lists - list of strings
		System.out.println("----Redis Lists----\n");
		jedis.lpush("queue#tasks", "firstTask");
		jedis.lpush("queue#tasks", "secondTask");
		
		System.out.println("Item popped: " + jedis.rpop("queue#tasks"));
		System.out.println("\n--------------------\n");
		
		// Redis Sets - unordered collection of Strings
		// useful for excluding repeated members
		System.out.println("----Redis Sets----\n");
		jedis.sadd("nicknames", "nickname#1");
		jedis.sadd("nicknames", "nickname#2");
		jedis.sadd("nicknames", "nickname#1");
		
		Set<String> nicknames = jedis.smembers("nicknames");
		nicknames.forEach(System.out::println);
		System.out.println("member exists: " + jedis.sismember("nicknames", "nickname#1"));
		System.out.println("\n--------------------\n");
		
		// Redis Hashes
		System.out.println("----Redis Hashes----\n");
		jedis.hset("user#1", "name", "peter");
		jedis.hset("user#1", "job", "politician");
		
		System.out.println("user#1 - name: " + jedis.hget("user#1", "name"));
		System.out.println("user#1 - job: " + jedis.hget("user#1", "job"));
		
		Map<String, String> fields = jedis.hgetAll("user#1");
		fields.forEach((key, value) -> System.out.println("key: " + key + ", value: " + value));
		
		System.out.println("\n--------------------\n");
		
		// Redis Sorted Sets
		System.out.println("----Redis Sorted Sets----\n");
		Map<String, Double> scores = new HashMap<>();
		
		scores.put("PlayerOne", 3245.00);
		scores.put("PlayerTwo", 2312.00);
		scores.put("PlayerThree", 6578.00);
		
		scores.forEach((player, score) -> System.out.println(player + " " + score));
		
		scores.entrySet().forEach(playerScore -> {
			jedis.zadd("ranking", playerScore.getValue(), playerScore.getKey());
		});
		
		// retrieve score of specified player
		System.out.println("PlayerOne score: " + jedis.zscore("ranking", "PlayerOne"));
		
		String player = jedis.zrevrange("ranking", 0, 1).iterator().next();
		System.out.println("Player with highest score: " + player);
		long rank = jedis.zrank("ranking", "PlayerOne");
		System.out.println("PlayerOne ranking: " + rank);
		
		
	}

}
