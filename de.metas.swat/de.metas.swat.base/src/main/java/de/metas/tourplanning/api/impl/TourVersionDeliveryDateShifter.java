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
	public LocalDate shiftForward(final LocalDate deliveryDate)
	{
		final boolean forward = true;
		return shift(deliveryDate, forward);
	}

	@Override
	public LocalDate shiftBackward(final LocalDate deliveryDate)
	{
		final boolean forward = false;
		return shift(deliveryDate, forward);
	}

	private LocalDate shift(final LocalDate deliveryDate, final boolean forward)
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
				if (forward)
				{
					return businessDayMatcher.getNextBusinessDay(deliveryDate);
				}
				else
				{
					return businessDayMatcher.getPreviousBusinessDay(deliveryDate);
				}
			}
			else
			{
				throw new AdempiereException("Unknown " + OnNonBussinessDay.class + ": " + onNonBussinessDay);
			}
		}
	}
}
