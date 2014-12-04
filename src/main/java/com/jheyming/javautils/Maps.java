package com.jheyming.javautils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Maps {
  public static <KEY,VALUE> Map<KEY,VALUE> asMap(List<Pair<KEY,VALUE>> pairs) {
    Map<KEY,VALUE> map = new HashMap<KEY, VALUE>();
    for(Pair<KEY,VALUE> pair : pairs) {
      map.put(pair.getLeft(), pair.getRight());
    }
    return map;
  }
  @SafeVarargs
  public static <KEY,VALUE> Map<KEY,VALUE> asMap(Pair<KEY,VALUE>... pairs) {
    return asMap(Arrays.asList(pairs));
  }
  
  public static <KEY,VALUE> Map<KEY,VALUE> asMap(Object... args) {
    List<Pair<KEY,VALUE>> list;
    if (args.length == 1 && args[0] instanceof List) {
      @SuppressWarnings("unchecked") // becasue we did instanceof
      List<Object> objects = (List<Object>) args[0];
      list = Pair.pairs(objects.toArray());  
    } else {
      list = Pair.pairs(args);
    }
    return asMap(list);
  }
   
  public static <A, B> List<Pair<A,B>> flattenPairs(Map<A,B> map) {
    List<Pair<A,B>> list = new ArrayList<Pair<A,B>>();
    for(Entry<A,B> entry : map.entrySet()) {
      list.add(new Pair<>(entry.getKey(),entry.getValue()));
    }
    return list;
  }
  
  public static <A, B> List<Entry<A,B>> flattenEntries(Map<A,B> map) {
    List<Entry<A,B>> list = new ArrayList<Entry<A,B>>();
    for(Entry<A,B> entry : map.entrySet()) {
      list.add(entry);
    }
    return list;
  }
  
  public static <A, B> Object[] flatten(Map<A,B> map) {
      List<Object> list = new ArrayList<Object>(); 
      for(Entry<A,B> entry: map.entrySet()) {
        list.add(entry.getKey());
        list.add(entry.getValue());
      }
      return list.toArray();
  }
  
  public static <KEY,VALUE> Map<KEY,VALUE> merge(Object[] override, Object... map) {
    Map<KEY,VALUE> toMerge = asMap(map);
    Map<KEY,VALUE> toOverride = asMap(override);
    toMerge.putAll(toOverride);
    return toMerge;
  }
  
  public static Object[] mergeList(Object[] override, Object... map) {
    return flatten(merge(override, map));
  }
}
