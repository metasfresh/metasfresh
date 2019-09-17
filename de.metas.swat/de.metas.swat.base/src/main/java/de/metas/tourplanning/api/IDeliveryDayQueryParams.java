package de.metas.tourplanning.api;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.tourplanning.model.I_M_DeliveryDay;

/**
 * POJO used for grouping parameters when querying for {@link I_M_DeliveryDay} using {@link IDeliveryDayDAO}.
 * 
 * @author tsa
 *
 */
public interface IDeliveryDayQueryParams
{
	ZonedDateTime getDeliveryDate();

	BPartnerLocationId getBPartnerLocationId();

	/**
	 * 
	 * @return
	 *         <ul>
	 *         <li>true if materials needs to be fetched from vendor (i.e. document has IsSOTrx=false)
	 *         <li>false if materials needs to be delivered to customer (i.e. document has IsSOTrx=true)
	 *         </ul>
	 */
	boolean isToBeFetched();

	Boolean getProcessed();

	/**
	 * The date+time when we calculate (e.g. the date+time when the order was created)
	 * 
	 * @return
	 */
	ZonedDateTime getCalculationTime();

	/**
	 * If the returned value is not <code>null</code>, then the system will retrieve the chronologically <b>first</b> delivery date that is after the returned timestamp.<br>
	 * If the value is <code>null</code>, then it will return the chronologically <b>last</b> delivery date prior to {@link #getDeliveryDate()}.
	 * Also see {@link IDeliveryDayDAO#retrieveDeliveryDay(org.adempiere.model.IContextAware, IDeliveryDayQueryParams)}.
	 * 
	 * @return
	 */
	LocalDate getPreparationDay();

}
