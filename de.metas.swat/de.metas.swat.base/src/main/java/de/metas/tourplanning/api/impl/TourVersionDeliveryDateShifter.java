package de.metas.tourplanning.api.impl;

import java.time.LocalDate;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.time.generator.IDateShifter;

import de.metas.adempiere.service.IBusinessDayMatcher;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class TourVersionDeliveryDateShifter implements IDateShifter
{
	public static enum OnNonBussinessDay
	{
		Cancel, MoveToNextBusinessDay,
	}

	private final IBusinessDayMatcher businessDayMatcher;
	private final OnNonBussinessDay onNonBussinessDay;

	@Builder
	private TourVersionDeliveryDateShifter(
			@NonNull final IBusinessDayMatcher businessDayMatcher,
			@NonNull final OnNonBussinessDay onNonBussinessDay)
	{
		this.businessDayMatcher = businessDayMatcher;
		this.onNonBussinessDay = onNonBussinessDay;
	}

	@Override
	public LocalDate shift(final LocalDate deliveryDate)
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
			if (onNonBussinessDay == OnNonBussinessDay.Cancel)
			{
				// skip this delivery date
				return null;
			}
			//
			// Case: we need to move our delivery date to next business day
			else if (onNonBussinessDay == OnNonBussinessDay.MoveToNextBusinessDay)
			{
				final LocalDate deliveryDateNextBusinessDay = businessDayMatcher.getNextBusinessDay(deliveryDate);
				return deliveryDateNextBusinessDay;
			}
			else
			{
				throw new AdempiereException("Unknown " + OnNonBussinessDay.class + ": " + onNonBussinessDay);
			}
		}
	}
}
