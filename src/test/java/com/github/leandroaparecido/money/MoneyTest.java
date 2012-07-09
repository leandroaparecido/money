package com.github.leandroaparecido.money;

import static org.junit.Assert.*;

import java.math.*;
import java.util.*;

import org.junit.*;

public class MoneyTest {

	@Test
	public void testOf() throws Exception {
		assertEquals("1,23", Money.of(1, 23).toString());
		assertEquals("1,23", Money.of(BigDecimal.valueOf(123, 2)).toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOfSmallMinor() throws Exception {
		Money.of(0, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOfTooBigMinor() throws Exception {
		Money.of(0, 100);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOfOverScaledValue() throws Exception {
		Money.of(BigDecimal.valueOf(10.9282));
	}

	@Test
	public void testPlus() throws Exception {
		Money m1 = Money.of(1, 53);
		Money m2 = Money.of(2, 76);
		assertEquals(Money.of(4, 29), m1.plus(m2));
		assertEquals(Money.of(3, 21), m1.plus(1, 68));
	}

	@Test
	public void testMinus() throws Exception {
		Money m1 = Money.of(0, 54);
		Money m2 = Money.of(1, 89);
		assertEquals(Money.of(-1, 35), m1.minus(m2));
		assertEquals(Money.of(0, 10), m1.minus(0, 44));
	}

	@Test
	public void testMultiply() throws Exception {
		Money m1 = Money.of(1, 20);
		assertEquals(Money.of(2, 40), m1.multiply(2));
		m1 = Money.of(-1, 20);
		assertEquals(Money.of(2, 40).negate(), m1.multiply(2));
	}

	@Test
	public void testDivide() throws Exception {
		Money m1 = Money.of(1, 0);
		assertEquals(2, m1.divide(2).size());
		assertEquals(Money.of(0, 50), m1.divide(2).get(0));
	}

	@Test
	public void testSum() throws Exception {
		List<Money> monies = Arrays.asList(Money.of(1, 2), Money.of(2, 34), Money.of(0, 76));
		assertEquals(Money.of(4, 12), Money.sum(monies));
	}

	@Test
	public void testEquals() throws Exception {
		assertEquals(Money.of(2, 50), Money.of(BigDecimal.valueOf(2.5)));
		assertEquals(Money.of(2, 56), Money.of(BigDecimal.valueOf(2.56)));
	}

	@Test
	public void testParse() {
		String s = "1,47";
		assertEquals(Money.of(1, 47), Money.parse(s));

		s = "890";
		assertEquals(Money.of(890, 0), Money.parse(s));
	}
}
