package com.github.leandroaparecido.money;

import java.io.*;
import java.math.*;
import java.util.*;

import com.google.common.base.*;

public class MutableMoney implements Serializable, Comparable<MutableMoney> {

	private static final long serialVersionUID = -4601291165921669519L;

	public static final MutableMoney ZERO = new MutableMoney(0L);
	public static final MutableMoney ONE = new MutableMoney(1L);
	public static final MutableMoney TEN = new MutableMoney(10L);

	private long amount;

	private MutableMoney(long amount) {
		this.amount = amount;
	}

	public static MutableMoney of(long major, int minor) {
		Preconditions.checkArgument(minor < 100);
		Preconditions.checkArgument(minor >= 0);
		if (major != 0) {
			minor = (int)Math.signum(major) * minor;
		}
		return new MutableMoney(major * 100 + minor);
	}

	public static MutableMoney of(BigDecimal v) {
		Preconditions.checkArgument(v.scale() <= 2);
		if (v.scale() == 1) {
			return new MutableMoney(v.unscaledValue().longValue() * 10);
		}
		return new MutableMoney(v.unscaledValue().longValue());
	}

	public static MutableMoney parse(String s) {
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

	public Money immutableCopy() {
		return Money.of(amount);
	}

	static MutableMoney of(long amount) {
		return new MutableMoney(amount);
	}

	public MutableMoney plus(MutableMoney arg) {
		Preconditions.checkNotNull(arg);
		amount += arg.amount;
		return this;
	}

	public MutableMoney plus(long major, int minor) {
		amount += major * 100 + minor;
		return this;
	}

	public MutableMoney minus(MutableMoney arg) {
		Preconditions.checkNotNull(arg);
		amount -= arg.amount;
		return this;
	}

	public MutableMoney minus(long major, int minor) {
		amount -= major * 100 + minor;
		return this;
	}

	public MutableMoney negate() {
		amount = -amount;
		return this;
	}

	public MutableMoney multiply(long arg) {
		amount *= arg;
		return this;
	}

	public List<MutableMoney> divide(int denominator) {
		List<MutableMoney> result = new ArrayList<MutableMoney>(denominator);
		long simpleResult = amount / denominator;
		int remainder = (int) (amount - simpleResult * denominator);
		for (int i = 0; i < denominator; i++) {
			if (i < remainder) {
				result.add(new MutableMoney(simpleResult + 1));
			} else {
				result.add(new MutableMoney(simpleResult));
			}
		}
		return result;
	}

	public boolean isGreaterThan(MutableMoney arg) {
		Preconditions.checkNotNull(arg);
		return compareTo(arg) > 0;
	}

	public boolean isLessThan(MutableMoney arg) {
		Preconditions.checkNotNull(arg);
		return compareTo(arg) < 0;
	}

	public MutableMoney abs() {
		amount = Math.abs(amount);
		return this;
	}

	public long getAmountMajor() {
		return amount / 100;
	}

	public int getAmountMinor() {
		return (int) Math.abs(amount % 100);
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

	public double percentageOf(MutableMoney arg) {
		return amount * 100.0 / arg.amount / 100;
	}

	public MutableMoney setMajor(long major) {
		int minor = getAmountMinor();
		amount = major * 100 + minor;
		return this;
	}

	public MutableMoney setMinor(int minor) {
		Preconditions.checkArgument(minor < 100);
		Preconditions.checkArgument(minor >= 0);
		long major = getAmountMajor();
		amount = major * 100 + minor;
		return this;
	}

	@Override
	public int compareTo(MutableMoney o) {
		long result = amount - o.amount;
		if (result > 0) {
			return 1;
		} else if (result < 0) {
			return -1;
		}
		return 0;
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
		if (!(obj instanceof MutableMoney)) {
			return false;
		}
		MutableMoney other = (MutableMoney) obj;
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
