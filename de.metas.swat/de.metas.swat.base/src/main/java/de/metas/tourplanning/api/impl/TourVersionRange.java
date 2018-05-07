package de.metas.tourplanning.api.impl;

import java.util.Date;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.time.generator.DateSequenceGenerator;

import com.google.common.collect.ImmutableSet;

import de.metas.tourplanning.api.ITourVersionRange;
import de.metas.tourplanning.model.I_M_TourVersion;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
/* package */class TourVersionRange implements ITourVersionRange
{
	private I_M_TourVersion tourVersion;
	private Date validFrom;
	private Date validTo;
	private DateSequenceGenerator dateSequenceGenerator;

	@Builder
	private TourVersionRange(
			@NonNull final I_M_TourVersion tourVersion,
			@NonNull final Date validFrom,
			@NonNull final Date validTo,
			final DateSequenceGenerator dateSequenceGenerator)
	{
		Check.assume(validFrom.compareTo(validTo) <= 0, "ValidFrom({}) <= ValidTo({})", validFrom, validTo);

		this.tourVersion = tourVersion;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.dateSequenceGenerator = dateSequenceGenerator;
	}

	@Override
	public String toString()
	{
		return "TourVersionRange ["
				+ "tourVersion=" + tourVersion == null ? null
						: tourVersion.getName()
								+ ", validFrom=" + validFrom
								+ ", validTo=" + validTo
								+ "]";
	}

	@Override
	public I_M_TourVersion getM_TourVersion()
	{
		return tourVersion;
	}

	@Override
	public Date getValidFrom()
	{
		return validFrom;
	}

	@Override
	public Date getValidTo()
	{
		return validTo;
	}

	@Override
	public Set<Date> generateDeliveryDates()
	{
		if (dateSequenceGenerator == null)
		{
			return ImmutableSet.of();
		}

		return dateSequenceGenerator.generate();
	}
}
