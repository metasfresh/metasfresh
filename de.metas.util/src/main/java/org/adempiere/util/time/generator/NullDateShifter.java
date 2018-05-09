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
	public LocalDate shiftForward(final LocalDate date)
	{
		return date;
	}

	@Override
	public LocalDate shiftBackward(final LocalDate date)
	{
		return date;
	}

}
