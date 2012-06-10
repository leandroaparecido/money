package com.github.leandroaparecido.money;

import static org.junit.Assert.*;

import org.junit.*;

public class MoneyTest {

	@Test
	public void testAdd() throws Exception {
		Money m1 = Money.of(1, 53);
		Money m2 = Money.of(2, 76);
		assertEquals(Money.of(4, 29), m1.add(m2));
	}

	@Test
	public void testSubtract() throws Exception {
		Money m1 = Money.of(0, 54);
		Money m2 = Money.of(1, 89);
		assertEquals(Money.of(-1, 35), m1.subtract(m2));
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
}
