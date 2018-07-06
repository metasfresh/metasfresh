package org.adempiere.util.time.generator;

import java.time.LocalDateTime;

public interface ICalendarIncrementor
{
	LocalDateTime increment(LocalDateTime date);

	LocalDateTime decrement(LocalDateTime date);
}
