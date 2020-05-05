package de.metas.util.time.generator;

import java.time.LocalDateTime;

import lombok.Value;

@Value
public final class SameDateShifter implements IDateShifter
{
	public static final SameDateShifter instance = new SameDateShifter();

	private SameDateShifter()
	{
	}

	@Override
	public LocalDateTime shiftForward(final LocalDateTime date)
	{
		return date;
	}

	@Override
	public LocalDateTime shiftBackward(final LocalDateTime date)
	{
		return date;
	}

}
