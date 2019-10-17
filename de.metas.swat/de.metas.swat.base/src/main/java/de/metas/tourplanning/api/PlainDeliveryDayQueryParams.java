package de.metas.tourplanning.api;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.util.Check;

/**
 * Plain implementation of {@link IDeliveryDayQueryParams}
 * 
 * @author tsa
 *
 */
public final class PlainDeliveryDayQueryParams implements IDeliveryDayQueryParams
{
	private ZonedDateTime deliveryDate = null;
	private BPartnerLocationId bpartnerLocationId = null;
	private Boolean toBeFetched = null;
	private Boolean processed = null;
	/**
	 * The time when the calculation is performed. For instance, the date+time when the order was created or the system time.
	 */
	private ZonedDateTime calculationTime = null;

	/**
	 * The day when the products should be delivered
	 */
	private LocalDate preparationDay = null;

	@Override
	public String toString()
	{
		return "PlainDeliveryDayQueryParams ["
				+ "deliveryDate=" + deliveryDate
				+ ", bpartnerLocationId=" + bpartnerLocationId
				+ ", toBeFetched=" + toBeFetched
				+ ", processed=" + processed
				+ "]";
	}

	@Override
	public ZonedDateTime getDeliveryDate()
	{
		return deliveryDate;
	}

	public void setDeliveryDate(final ZonedDateTime deliveryDate)
	{
		this.deliveryDate = deliveryDate;
	}

	@Override
	public BPartnerLocationId getBPartnerLocationId()
	{
		return bpartnerLocationId;
	}

	public void setBPartnerLocationId(final BPartnerLocationId bpartnerLocationId)
	{
		this.bpartnerLocationId = bpartnerLocationId;
	}

	@Override
	public boolean isToBeFetched()
	{
		Check.assumeNotNull(toBeFetched, "toBeFetched set");
		return toBeFetched;
	}

	/**
	 * 
	 * @param toBeFetched
	 * @see #isToBeFetched()
	 */
	public void setToBeFetched(final boolean toBeFetched)
	{
		this.toBeFetched = toBeFetched;
	}

	@Override
	public Boolean getProcessed()
	{
		return processed;
	}

	public void setProcessed(final Boolean processed)
	{
		this.processed = processed;
	}

	@Override
	public ZonedDateTime getCalculationTime()
	{
		return calculationTime;
	}

	/**
	 * See {@link IDeliveryDayQueryParams#getCalculationTime()}.
	 * 
	 * @param calculationTime
	 */
	public void setCalculationTime(final ZonedDateTime calculationTime)
	{
		this.calculationTime = calculationTime;
	}

	@Override
	public LocalDate getPreparationDay()
	{
		return preparationDay;
	}

	public void setPreparationDay(LocalDate preparationDay)
	{
		this.preparationDay = preparationDay;
	}
}
