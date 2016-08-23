package com.rkt.common.tools.redis;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component("jedisClient")
public class JedisClient {

    @Resource(name = "jedisTemplate")
    private RedisTemplate<String, String> template;
    private static final String titan_Index = "titan_";
    public static final long expireTime = 3; //小时

    /**
     * Redis Key exists or not
     * @param redisKey
     * @return
     * @createTime 2014年5月29日 下午2:56:52
     * @author celine
     */
    public boolean exists(String redisKey) {
    	redisKey = titan_Index + redisKey;
        return template.hasKey(redisKey);
    }

    /**
     * 
     * 
     * @param key
     * @param hashKey
     * @return
     */
    public boolean hasKey(String key, String hashKey) {
    	key = titan_Index + key;
        HashOperations<String, String, String> hashOps = template.opsForHash();
        return hashOps.hasKey(key, hashKey);
    }

    /**
     * map put
     * 
     * @param key
     * @param hashKey
     * @param value
     */
//    public void hput(String key, String hashKey, String value) {
//        HashOperations<String, String, String> hashOps = template.opsForHash();
//        hashOps.put(key, hashKey, value);
//    }
    
    public void hput(String key, String hashKey, String value, long expireTime) {
    	key = titan_Index + key;
    	template.expire(key, expireTime, TimeUnit.HOURS);
        HashOperations<String, String, String> hashOps = template.opsForHash();
        hashOps.put(key, hashKey, value);
    }
    /**
     * map get
     * 
     * @param key the map name(new HashMap("name"))
     * @param hashKey the field key
     * @return
     */
    public String hget(String key, String hashKey) {
    	key = titan_Index + key;
        HashOperations<String, String, String> hashOps = template.opsForHash();
        return hashOps.get(key, hashKey);
    }

    /**
     * map del
     * 
     * @param key
     * @param hashKey
     */
    public void hdel(String key, String hashKey) {
    	key = titan_Index + key;
        HashOperations<String, String, String> hashOps = template.opsForHash();
        hashOps.delete(key, hashKey);
    }

    /**
     * 根据多个key查询values
     * 
     * @param key
     * @param hashKeys
     */
    public List<String> hmultiget(String key, Collection<String> hashKeys) {
    	key = titan_Index + key;
        HashOperations<String, String, String> hashOps = template.opsForHash();
        return hashOps.multiGet(key, hashKeys);
    }

    /**
     * map size
     * 
     * @param key
     * @return
     */
    public long hlen(String key) {
    	key = titan_Index + key;
        HashOperations<String, String, String> hashOps = template.opsForHash();
        return hashOps.size(key);
    }

    /**
     * all keys in the map
     * 
     * @param key
     * @return
     */
    public Set<String> hkeys(String key) {
    	key = titan_Index + key;
        HashOperations<String, String, String> hashOps = template.opsForHash();
        return hashOps.keys(key);
    }

//    /**
//     * set add
//     * 
//     * @param key
//     * @param values
//     */
//    public void sadd(String key, String... values) {
//        BoundSetOperations<String, String> setOps = template.boundSetOps(key);
//        setOps.add(values);
//    }
    public void sadd(String key,long expireTime, String... values) {
    	key = titan_Index + key;
    	template.expire(key, expireTime, TimeUnit.HOURS);
        BoundSetOperations<String, String> setOps = template.boundSetOps(key);
        setOps.add(values);
    }    

    /**
     * set all values
     * 
     * @param key
     * @return
     */
    public Set<String> smembers(String key) {
    	key = titan_Index + key;
        BoundSetOperations<String, String> setOps = template.boundSetOps(key);
        return setOps.members();
    }

    /**
     * remove object from set
     * 
     * 
     * @param key
     * @param values
     * @return
     */
    public Long sremove(String key, Object... values) {
    	key = titan_Index + key;
        BoundSetOperations<String, String> setOps = template.boundSetOps(key);
        return setOps.remove(values);
    };

    /**
     * set size
     * 
     * @param key
     * @param values
     * @return
     */
    public Long slen(String key, Object... values) {
    	key = titan_Index + key;
        BoundSetOperations<String, String> setOps = template.boundSetOps(key);
        return setOps.size();
    };

    /**
     * 删除redis中的key及所有数据（谨慎使用）
     * 
     * @param key
     */
    public void delKey(String key) {
    	key = titan_Index + key;
        template.delete(key);
    }
    
	/**
	 * list left push
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long llpush(String key, String value) {
    	key = titan_Index + key;
		BoundListOperations<String, String> listOps = template.boundListOps(key);
		return listOps.leftPush(value);
	};

	/**
	 * list left push all
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public Long llpush(String key, String... values) {
    	key = titan_Index + key;
		BoundListOperations<String, String> listOps = template.boundListOps(key);
		return listOps.leftPushAll(values);
	};
	
	public List<String> lrange(String key,long start, long end) {
    	key = titan_Index + key;
		BoundListOperations<String, String> listOps = template.boundListOps(key);
		return listOps.range(start, end);
	};
	
	/**
	 * 返回列表 key 的长度。
	 * 
	 * @param key
	 * @return
	 */
	public Long lsize(String key) {
    	key = titan_Index + key;
		BoundListOperations<String, String> listOps = template.boundListOps(key);
		return listOps.size();
	};
}
