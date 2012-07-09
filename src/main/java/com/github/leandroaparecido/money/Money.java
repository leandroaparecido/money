package com.github.leandroaparecido.money;

import java.io.*;
import java.math.*;
import java.util.*;

import com.google.common.base.*;

public final class Money implements Serializable, Comparable<Money> {

	private static final long serialVersionUID = 3720810196241002650L;

	public static final Money ZERO = new Money(0L);
	public static final Money ONE = new Money(1L);
	public static final Money TEN = new Money(10L);

	private final long amount;

	private Money(long amount) {
		this.amount = amount;
	}

	public static Money of(long major, int minor) {
		Preconditions.checkArgument(minor < 100);
		Preconditions.checkArgument(minor >= 0);
		if (major != 0) {
			minor = (int)Math.signum(major) * minor;
		}
		return new Money(major * 100 + minor);
	}

	public static Money of(BigDecimal v) {
		Preconditions.checkArgument(v.scale() <= 2);
		if (v.scale() == 1) {
			return new Money(v.unscaledValue().longValue() * 10);
		}
		return new Money(v.unscaledValue().longValue());
	}

	public static Money parse(String s) {
		Preconditions.checkNotNull(s);
		String trimmed = s.trim();
		Preconditions.checkArgument(!trimmed.isEmpty(), "Cannot parse empty string");
		String[] parts = trimmed.split(",");
		long major = Long.parseLong(parts[0]);
		int minor = 0;
		if (parts.length > 1) {
			minor = Integer.parseInt(parts[1]);
		}
		return of(major, minor);
	}

	public static Money sum(Iterable<Money> monies) {
		Preconditions.checkNotNull(monies);
		long total = 0L;
		for (Money money : monies) {
			total += money.amount;
		}
		return new Money(total);
	}

	public Money plus(Money arg) {
		Preconditions.checkNotNull(arg);
		return new Money(amount + arg.amount);
	}

	public Money plus(long major, int minor) {
		return new Money(amount + major * 100 + minor);
	}

	public Money minus(Money arg) {
		Preconditions.checkNotNull(arg);
		return new Money(amount - arg.amount);
	}

	public Money minus(long major, int minor) {
		return new Money(amount - major * 100 - minor);
	}

	public Money negate() {
		return new Money(-amount);
	}

	public Money multiply(long arg) {
		return new Money(amount * arg);
	}

	public List<Money> divide(int denominator) {
		List<Money> result = new ArrayList<Money>(denominator);
		long simpleResult = amount / denominator;
		int remainder = (int) (amount - simpleResult * denominator);
		for (int i = 0; i < denominator; i++) {
			if (i < remainder) {
				result.add(new Money(simpleResult + 1));
			} else {
				result.add(new Money(simpleResult));
			}
		}
		return result;
	}

	public boolean isGreaterThan(Money arg) {
		Preconditions.checkNotNull(arg);
		return compareTo(arg) > 0;
	}

	public boolean isLessThan(Money arg) {
		Preconditions.checkNotNull(arg);
		return compareTo(arg) < 0;
	}

	public Money abs() {
		return isPositive() ? this : negate();
	}

	public long getAmountMajor() {
		return amount / 100;
	}

	public long getAmountMinor() {
		return Math.abs(amount % 100);
	}

	public boolean isZero() {
		return amount == 0;
	}

	public boolean isPositive() {
		return amount > 0;
	}

	public boolean isPositiveOrZero() {
		return amount >= 0;
	}

	public boolean isNegative() {
		return amount < 0;
	}

	public boolean isNegativeOrZero() {
		return amount <= 0;
	}

	@Override
	public int compareTo(Money o) {
		return (int) (amount - o.amount);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (amount ^ (amount >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Money)) {
			return false;
		}
		Money other = (Money) obj;
		if (amount != other.amount) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("%d,%02d", getAmountMajor(), getAmountMinor());
	}
}
