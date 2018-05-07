package org.adempiere.util.time.generator;

import java.time.LocalDate;

@FunctionalInterface
public interface ICalendarIncrementor
{
	LocalDate increment(LocalDate date);
}
