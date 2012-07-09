package com.github.lehphyro.money;

import static org.junit.Assert.*;

import java.math.*;

import org.junit.*;

public class MutableMoneyTest {

	@Test
	public void testOf() throws Exception {
		assertEquals("1,23", MutableMoney.of(1, 23).toString());
		assertEquals("1,23", MutableMoney.of(BigDecimal.valueOf(123, 2)).toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOfSmallMinor() throws Exception {
		MutableMoney.of(0, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOfTooBigMinor() throws Exception {
		MutableMoney.of(0, 100);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOfOverScaledValue() throws Exception {
		MutableMoney.of(BigDecimal.valueOf(10.9282));
	}

	@Test
	public void testPlus() throws Exception {
		MutableMoney m1 = MutableMoney.of(1, 53);
		MutableMoney m2 = MutableMoney.of(2, 76);
		assertEquals(MutableMoney.of(4, 29), m1.plus(m2));
		assertEquals(MutableMoney.of(5, 97), m1.plus(1, 68));
	}

	@Test
	public void testMinus() throws Exception {
		MutableMoney m1 = MutableMoney.of(0, 54);
		MutableMoney m2 = MutableMoney.of(1, 89);
		assertEquals(MutableMoney.of(-1, 35), m1.minus(m2));
		assertEquals(MutableMoney.of(-1, 79), m1.minus(0, 44));
	}

	@Test
	public void testMultiply() throws Exception {
		MutableMoney m1 = MutableMoney.of(1, 20);
		assertEquals(MutableMoney.of(2, 40), m1.multiply(2));
		m1 = MutableMoney.of(-1, 20);
		assertEquals(MutableMoney.of(2, 40).negate(), m1.multiply(2));
	}

	@Test
	public void testDivide() throws Exception {
		MutableMoney m1 = MutableMoney.of(1, 0);
		assertEquals(2, m1.divide(2).size());
		assertEquals(MutableMoney.of(0, 50), m1.divide(2).get(0));
	}

	@Test
	public void testEquals() throws Exception {
		assertEquals(MutableMoney.of(2, 50), MutableMoney.of(BigDecimal.valueOf(2.5)));
		assertEquals(MutableMoney.of(2, 56), MutableMoney.of(BigDecimal.valueOf(2.56)));
	}

	@Test
	public void testParse() {
		String s = "1,47";
		assertEquals(MutableMoney.of(1, 47), MutableMoney.parse(s));

		s = "890";
		assertEquals(MutableMoney.of(890, 0), MutableMoney.parse(s));
	}

	@Test
	public void testPercentageOf() {
		MutableMoney money = MutableMoney.ONE;
		assertEquals(0.1, money.percentageOf(MutableMoney.TEN), 0.0001);
		money = MutableMoney.of(774, 27);
		assertEquals(0.0042, money.percentageOf(MutableMoney.of(182737, 9)), 0.0001);
	}

	@Test
	public void testSetMajor() {
		MutableMoney money = MutableMoney.of(87, 30);
		assertEquals(MutableMoney.of(20, 30), money.setMajor(20));
	}

	@Test
	public void testSetMinor() {
		MutableMoney money = MutableMoney.of(87, 30);
		assertEquals(MutableMoney.of(87, 2), money.setMinor(2));
	}
}
