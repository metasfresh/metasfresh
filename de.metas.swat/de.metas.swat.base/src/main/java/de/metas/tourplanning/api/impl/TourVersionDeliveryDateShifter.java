package de.metas.tourplanning.api.impl;

import java.util.Date;

import org.adempiere.util.Check;
import org.adempiere.util.time.generator.IDateShifter;

import de.metas.adempiere.service.IBusinessDayMatcher;
import lombok.Builder;
import lombok.Value;

@Value
public class TourVersionDeliveryDateShifter implements IDateShifter
{
	private final IBusinessDayMatcher businessDayMatcher;
	private final boolean cancelIfNotBusinessDay;
	private final boolean moveToNextBusinessDay;

	@Builder
	private TourVersionDeliveryDateShifter(
			final IBusinessDayMatcher businessDayMatcher,
			final boolean cancelIfNotBusinessDay,
			final boolean moveToNextBusinessDay)
	{
		Check.assumeNotNull(businessDayMatcher, "Parameter businessDayMatcher is not null");
		this.businessDayMatcher = businessDayMatcher;
		this.cancelIfNotBusinessDay = cancelIfNotBusinessDay;
		this.moveToNextBusinessDay = moveToNextBusinessDay;
	}

	@Override
	public Date shift(final Date deliveryDate)
	{
		//
		// Case: we deal with a delivery date which is in a business day
		if (businessDayMatcher.isBusinessDay(deliveryDate))
		{
			return deliveryDate;
		}
		//
		// Case: our delivery date is not in a business day
		else
		{
			//
			// Case: we need to cancel because our delivery date it's not in a business day
			if (cancelIfNotBusinessDay)
			{
				// skip this delivery date
				return null;
			}
			//
			// Case: we need to move our delivery date to next business day
			else if (moveToNextBusinessDay)
			{
				final Date deliveryDateNextBusinessDay = businessDayMatcher.getNextBusinessDay(deliveryDate);
				return deliveryDateNextBusinessDay;
			}
			//
			// Case: no option => do nothing, same as if we were asked for canceling
			else
			{
				return null;
			}
		}
	}
}
