package edu.utils

import org.sparkproject.dmg.pmml.True
import redis.clients.jedis.JedisPoolConfig

/**
 * Redis工具类
 */
object RedisUtil {
    // 1.准备Redis配置
    val host = "localhost"
    val port = 6379
    val timeout = 30000
    val config = new JedisPoolConfig
    config.setMaxTotal(200)
    config.setMaxIdle(50)
    config.setMinIdle(8)
    config.setMaxWaitMillis(10000)
    config.setTestOnBorrow(true)
    config.setTestOnReturn(true)
    config.setTestWhileIdle(true)
    config.setTimeBetweenEvictionRunsMillis(30000)
    config.setNumTestsPerEvictionRun(10)
    config.setMinEvictableIdleTimeMillis(60000)

    // 2. 创建连接池


}
