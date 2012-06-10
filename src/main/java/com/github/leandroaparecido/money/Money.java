package com.github.leandroaparecido.money;

import java.util.*;

public class Money implements Comparable<Money> {

	private final long amount;

	private Money(long amount) {
		this.amount = amount;
	}

	public static Money of(long major, long minor) {
		if (major != 0) {
			minor = (int)Math.signum(major) * minor;
		}
		return new Money(major * 100 + minor);
	}

	public Money add(Money arg) {
		return new Money(amount + arg.amount);
	}

	public Money add(long major, long minor) {
		return new Money(amount + major * 100 + minor);
	}

	public Money subtract(Money arg) {
		return new Money(amount - arg.amount);
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

	public boolean greaterThan(Money arg) {
		return compareTo(arg) > 0;
	}

	public boolean lessThan(Money arg) {
		return compareTo(arg) < 0;
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
		return String.format("%d,%02d", amount / 100, Math.abs(amount % 100));
	}
}