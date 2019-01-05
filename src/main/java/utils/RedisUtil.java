package utils;

import org.springframework.data.redis.core.*;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class RedisUtil {
    @Resource
    private RedisTemplate redisTemplate;

    public <K> void delete(K k) {
        redisTemplate.delete(k);
    }

    public <K> Long delete(Collection<K> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     */
    public <K, V> void set(K key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获得缓存的基本对象。
     */
    public <K, V> V get(K key) {
        ValueOperations<K, V> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 获取旧值，设置新值
     */
    public <K, V> V getAndSet(K k, V v) {
        ValueOperations<K, V> operation = redisTemplate.opsForValue();
        return operation.getAndSet(k, v);
    }

    public <K> void append(K key, String appendValue) {
        redisTemplate.opsForValue().append(key, appendValue);
    }

    public <K> Boolean setBit(K k, long offset, boolean v) {
        return redisTemplate.opsForValue().setBit(k, offset, v);
    }

    public <K> Boolean getBit(K k, long offset) {
        return redisTemplate.opsForValue().getBit(k, offset);
    }

    public <K> Long size(K k) {
        return redisTemplate.opsForValue().size(k);
    }

    public <K> Long increment(K k, long v) {
        return redisTemplate.opsForValue().increment(k, v);
    }

    public <K> Double increment(K k, double v) {
        return redisTemplate.opsForValue().increment(k, v);
    }

    public <K, V> Boolean setIfAbsent(K k, V v) {
        return redisTemplate.opsForValue().setIfAbsent(k, v);
    }

    public <K, V> void setWithTimeout(K k, V v, long timeout) {
        redisTemplate.opsForValue().set(k, v, timeout, TimeUnit.SECONDS);
    }

    public <K, V> void setExpireAt(K k, V v, Date expireAt) {
        redisTemplate.opsForValue().set(k, v);
        redisTemplate.expireAt(k, expireAt);
    }

    public <K> void setExpireAt(K k, Date expireAt) {
        redisTemplate.expireAt(k, expireAt);
    }

    public <K, V> void multiSet(Map<K, V> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    public <K, V> List<V> multiGet(Collection<K> collection) {
        return redisTemplate.opsForValue().multiGet(collection);
    }

    public <K, V> Boolean multiSetIfAbsent(Map<? extends K, ? extends V> map) {
        return redisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    //将所有指定的值插入存储在键的列表的头部。如果键不存在，则在执行推送操作之前将其创建为空列表。
    public <K, V> void listLeftPush(K k, V v) {
        ListOperations listOperation = redisTemplate.opsForList();
        listOperation.leftPush(k, v);
    }

    public <K, V> void listLeftPushAll(K k, V... v) {
        ListOperations listOperation = redisTemplate.opsForList();
        listOperation.leftPushAll(k, v);
    }

    public <K, V> void listLeftPushAll(K k, Collection<V> v) {
        ListOperations listOperation = redisTemplate.opsForList();
        listOperation.leftPushAll(k, v);
    }

    public <K, V> void listRightPushAll(K k, V... v) {
        ListOperations listOperation = redisTemplate.opsForList();
        listOperation.rightPushAll(k, v);
    }

    public <K, V> void listRightPushAll(K k, Collection<V> v) {
        ListOperations listOperation = redisTemplate.opsForList();
        listOperation.rightPushAll(k, v);
    }

    public <K, V> void listRightPush(K k, V v) {
        ListOperations listOperation = redisTemplate.opsForList();
        listOperation.rightPush(k, v);
    }

    public <V> V listLeftPop(String k) {
        ListOperations listOperation = redisTemplate.opsForList();
        return (V) listOperation.leftPop(k);
    }

    public <K, V> V listRightPop(K k) {
        ListOperations listOperation = redisTemplate.opsForList();
        return (V) listOperation.rightPop(k);
    }

    public <K, V> void listSet(K k, long index, V v) {
        ListOperations listOperation = redisTemplate.opsForList();
        listOperation.set(k, index, v);
    }

    public <K, V> V listIndex(K k, long index) {
        ListOperations listOperation = redisTemplate.opsForList();
        return (V) listOperation.index(k, index);
    }

    public <K, V> List<V> listRange(K k, long start, long end) {
        ListOperations listOperation = redisTemplate.opsForList();
        return listOperation.range(k, start, end);
    }

    /**
     * 修剪现有列表，使其只包含指定的指定范围的元素，起始和停止都是基于0的索引
     **/
    public <K> void listTrim(K k, long start, long end) {
        ListOperations listOperation = redisTemplate.opsForList();
        listOperation.trim(k, start, end);
    }

    public <K> Long listSize(K k) {
        ListOperations listOperation = redisTemplate.opsForList();
        return listOperation.size(k);
    }

    /**
     * 从存储在键中的列表中删除等于值的元素的第一个计数事件。
     * 计数参数以下列方式影响操作：
     * count> 0：删除等于从头到尾移动的值的元素。
     * count <0：删除等于从尾到头移动的值的元素。
     * count = 0：删除等于value的所有元素。
     */
    public <K, V> Long remove(K k, long count, V v) {
        ListOperations listOperation = redisTemplate.opsForList();
        return listOperation.remove(k, count, v);
    }

    public <K, HK, HV> void hashPut(K k, HK hk, HV hv) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put(k, hk, hv);
    }

    public <K, HK> Long hashDelete(K k, HK... hk) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        return hashOperations.delete(k, hk);
    }

    public <K, HK> Boolean hashHasKey(K k, HK hk) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        return hashOperations.hasKey(k, hk);
    }

    public <K, HK, HV> HV hashGet(K k, HK hk) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        return (HV) hashOperations.get(k, hk);
    }

    public <K, HK, HV> List<HV> hashMultiGet(K k, Collection<HK> hk) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        return hashOperations.multiGet(k, hk);
    }

    public <K, HK> Long hashIncrement(K k, HK hk, long delta) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        return hashOperations.increment(k, hk, delta);
    }

    public <K, HK> Double hashIncrement(K k, HK hk, double delta) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        return hashOperations.increment(k, hk, delta);
    }

    public <K, HK> Set<HK> hashKeys(K k) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        return hashOperations.keys(k);
    }

    public <K, HV> List<HV> hashValues(K k) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        return hashOperations.values(k);
    }

    public <K, HK, HV> Map<HK, HV> hashEntries(K k) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(k);
    }

    public <K> Long hashSize(K k) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        return hashOperations.size(k);
    }

    public <K, HK, HV> void hashPutAll(K k, Map<HK, HV> map) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(k, map);
    }

    public <K, V> Long setAdd(K k, V... v) {
        SetOperations setOperations = redisTemplate.opsForSet();
        return setOperations.add(k, v);
    }

    public <K, V> Long setRemove(K k, V... v) {
        SetOperations setOperations = redisTemplate.opsForSet();
        return setOperations.remove(k, v);
    }

    public <K, V> V setPop(K k) {
        SetOperations setOperations = redisTemplate.opsForSet();
        return (V) setOperations.pop(k);
    }

    public <K, V> Boolean setMove(K k, V v, K destKey) {
        SetOperations setOperations = redisTemplate.opsForSet();
        return setOperations.move(k, v, destKey);
    }

    public <K, V> Set<V> setMembers(K k) {
        SetOperations setOperations = redisTemplate.opsForSet();
        return setOperations.members(k);
    }

    public <K> Long setSize(K k) {
        SetOperations setOperations = redisTemplate.opsForSet();
        return setOperations.size(k);
    }

    public <K, V> Boolean setIsMember(K k, V v) {
        SetOperations setOperations = redisTemplate.opsForSet();
        return setOperations.isMember(k, v);
    }

    public <K, V> V setIsMember(K k) {
        SetOperations setOperations = redisTemplate.opsForSet();
        return (V) setOperations.randomMember(k);
    }

    public <K, V> List<V> setIsMember(K k, long count) {
        SetOperations setOperations = redisTemplate.opsForSet();
        return setOperations.randomMembers(k, count);
    }

    public <K, V> Boolean zsetAdd(K k, V v, double score) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.add(k, v, score);
    }

    public <K, V> Long zsetAdd(K k, Set<ZSetOperations.TypedTuple<V>> typedTuples) {
//        ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<>("str",9.6);
//        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
//        tuples.add(typedTuple);
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.add(k, typedTuples);
    }

    public <K, V> Long zsetRemove(K k, V... v) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.remove(k, v);
    }

    public <K, V> Double zsetIncrementScore(K k, V v, double score) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.incrementScore(k, v, score);
    }

    //返回有序集中指定成员的排名，其中有序集成员按分数值递增(从小到大)顺序排列
    public <K, V> Long zsetRank(K k, V v) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.rank(k, v);
    }

    //通过索引区间返回有序集合成指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
    public <K, V> Long zsetReverseRank(K k, V v) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.reverseRank(k, v);
    }

    public <K, V> Set<V> zsetRange(K k, long start, long end) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.range(k, start, end);
    }

    public <K, V> Set<V> zsetRangeByScore(K k, double min, double max) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.rangeByScore(k, min, max);
    }

    public <K, V> Long zsetCount(K k, double min, double max) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.count(k, min, max);
    }

    public <K> Long zsetSize(K k) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.size(k);
    }

    public <K> Long zsetRemoveRange(K k, long start, long end) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.removeRange(k, start, end);
    }

    public <K> Long zsetRemoveRangeByScore(K k, double min, double max) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.removeRangeByScore(k, min, max);
    }
}

