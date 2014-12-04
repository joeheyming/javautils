package com.jheyming.javautils;

import java.util.Arrays;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MapsTest extends TestCase {

	@Test
	public void test_asMap() {
		Map map = Maps.asMap();
		System.out.println(map);
		assertTrue(map.isEmpty());
		
		
		Map<String,Object> map2 = Maps.asMap("hi");
		System.out.println(map2);
		assertEquals(1,map2.size());
		assertNull(map2.get("hi"));
		
		
		Map<String,String> map3 = Maps.asMap("foo", "bar");
		System.out.println(map3);
		assertEquals(1,map3.size());
		assertEquals("bar", map3.get("foo"));
		
				
	}

	@Test
	public void testBadMap() {
		Map<String,String> map = Maps.asMap("foo", 1);
		System.out.println(map);
		assertEquals(1, map.size());
		assertEquals(1, map.get("foo"));
		
		try {
			String found = map.get("foo");
			fail();
		} catch(ClassCastException cce) {
			System.out.println("Class cast exception caught");
		}

		try {
			map = Maps.asMap(String.class, "foo", 1);
			fail();
		} catch(ClassCastException cce) {
			System.out.println("Class cast exception caught");
		}

	}

	@Test
	public void test_runtimeEnforcement() {
		Map<String,Integer> map = Maps.asMap("foo", 1, "bar", 2);
		System.out.println(map);
		
		map = Maps.asMap(String.class, Integer.class, "foo", 1, "bar", 2);
		System.out.println(map);
	}
	
	private Map<String,Object> argsOverride(Object... args) {
		return Maps.mergeDefaults(args, "something", "else");
	}
	
	@Test
	public void test_override() {
		
		Map<String,Object> map = argsOverride("foo","bar","baz", 1);
		assertEquals(3, map.size());
		assertTrue(map.containsKey("something"));
		assertEquals("else", map.get("something"));
		assertEquals("bar", map.get("foo"));
		
		map = argsOverride("something", "mine");
		assertEquals(1, map.size());
		assertTrue(map.containsKey("something"));
		assertEquals("mine", map.get("something"));
	}
	
	@Test
	public void test_flatten() {
		Map<String,Object> map = argsOverride("foo","bar","baz", "bazzy");
		Object[] flattened = Maps.flatten(map);
		
		assertEquals(6, flattened.length);
		
		Map<String,Object> map2 = Maps.asMap(flattened);
		assertEquals(map, map2);
		
		Arrays.sort(flattened);
		assertEquals(Arrays.asList("bar","baz","bazzy","else","foo","something"), Arrays.asList(flattened));
	}

}
