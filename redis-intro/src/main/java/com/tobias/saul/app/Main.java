package com.tobias.saul.app;

import redis.clients.jedis.Jedis;

public class Main {
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Jedis jedis = new Jedis();
		
		jedis.set("events/city/rome", "32, 15, 223, 42");
		String cachedResponse = jedis.get("events/city/rome");
		
		System.out.println(cachedResponse);
	}

}
