package com.jheyming.javautils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

public class Pair<LEFT, Right> {
	private LEFT left;
	private Right right;

	public Pair(LEFT a, Right b) {
		this.setLeft(a);
		this.setRight(b);
	}
	public String toString() {
		return "(" + left + "," + right + ")";
	}

	public LEFT getLeft() {
		return left;
	}

	public void setLeft(LEFT a) {
		this.left = a;
	}

	public Right getRight() {
		return right;
	}

	public void setRight(Right right) {
		this.right = right;
	}

	public static <A, B> Pair<A, B> pair(A a, B b) {
		return new Pair<A, B>(a, b);
	}

	public static <A, B> List<A> lefts(List<Pair<A, B>> pairs) {
		List<A> lefts = new ArrayList<>();
		for (Pair<A, ?> pair : pairs) {
			lefts.add(pair.left);
		}
		return lefts;
	}

	public static <B, A> List<B> rights(List<Pair<A, B>> pairs) {
		List<B> rights = new ArrayList<>();
		for (Pair<?, B> pair : pairs) {
			rights.add(pair.right);
		}
		return rights;
	}
	
	@SuppressWarnings("unchecked")
	public static <A, B> List<Pair<A, B>> pairs(Object... args) {
		Class<A> aKlass = null;
		Class<B> bKlass = null;
		int start = 0;
		if (args.length > 0 && args[0] instanceof Class) {
			aKlass = (Class<A>) args[0];
			start = 1;
		}
		if (aKlass != null) {
			if (args.length > 1 && args[1] instanceof Class) {
				bKlass = (Class<B>) args[1];
				start = 2;
			} else {
				bKlass = (Class<B>) aKlass;
			}
		}
		
		List<Pair<A, B>> pairs = new ArrayList<Pair<A, B>>();
		for (int i = start; i < args.length; i += 2) {
			A left = (A) args[i];
			B right = (i + 1 >= args.length) 
				? null
				: (B) args[i + 1];
			pairs.add(pair(left, right));
		}
		
		if (aKlass != null && !Iterables.all(lefts(pairs), Predicates.instanceOf(aKlass))) {
			throw new ClassCastException("All left pairs must match the same type: " + aKlass.getSimpleName());
		}
		if (aKlass != null && !Iterables.all(rights(pairs), Predicates.instanceOf(bKlass))) {
			throw new ClassCastException("All right pairs must match the same type: " + bKlass.getSimpleName());
		}
		return pairs;
	}
	
	public static <A,B> Object[] flatten(List<Pair<A,B>> pairs) {
		List<Object> list = new ArrayList<>();
		for(Pair<A,B> pair : pairs) {
			list.add(pair.left);
			list.add(pair.right);
		}
		return list.toArray();
	}
}
