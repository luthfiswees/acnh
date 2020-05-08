package cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Base64;

import env.Env;

public class Cache {
    
    // Expire in one hour
    private final int DEFAULT_EXPIRE_TIME = 3600;
    private final int DEFAULT_TIMEOUT = 20000;

    private Env env;
    private JedisPool pool;

    public Cache() {
        this.env = new Env();

        String host = this.env.get("REDIS_HOST");
        String port = this.env.get("REDIS_PORT");

        this.pool = new JedisPool(new JedisPoolConfig(), host, Integer.parseInt(port), DEFAULT_TIMEOUT);
    }

    public void setByte(String key, byte[] data) {
        Jedis jedis = this.pool.getResource();

        try {
            String imgStr = Base64.getEncoder().encodeToString(data);
            
            jedis.set(key, imgStr);
            jedis.expire(key, DEFAULT_EXPIRE_TIME);
        } catch (JedisException e) {
            if (null != jedis) {
                this.pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) this.pool.returnResource(jedis);
        }
    }

    public byte[] getByte(String key) {
        Jedis jedis = this.pool.getResource();
        byte[] data = null;

        try {
            String base64String = jedis.get(key);
            data = Base64.getDecoder().decode(base64String);
        } catch (JedisException e) {
            if (null != jedis) {
                this.pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) this.pool.returnResource(jedis);
        }

        return data;
    }

    public boolean isExist(String key) {
        byte[] data = getByte(key);

        return (data != null && data.length > 0);
    }
}