package org.adempiere.util.time.generator;

import java.time.LocalDate;

public interface ICalendarIncrementor
{
	LocalDate increment(LocalDate date);

	LocalDate decrement(LocalDate date);
}
