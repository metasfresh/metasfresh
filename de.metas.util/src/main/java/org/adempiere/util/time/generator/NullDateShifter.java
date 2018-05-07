package org.adempiere.util.time.generator;

import java.time.LocalDate;

import lombok.Value;

@Value
public final class NullDateShifter implements IDateShifter
{
	public static final NullDateShifter instance = new NullDateShifter();

	private NullDateShifter()
	{
	}

	@Override
	public LocalDate shift(final LocalDate date)
	{
		return date;
	}

}
