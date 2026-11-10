package de.metas.tourplanning.model.validator;

import static org.adempiere.model.InterfaceWrapperHelper.isUIAction;
import static org.adempiere.model.InterfaceWrapperHelper.isValueChanged;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;

import de.metas.tourplanning.api.IOrderDeliveryDayBL;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.util.Services;

@Interceptor(I_C_Order.class)
public class C_Order
{
	private final IOrderDeliveryDayBL orderDeliveryDayBL = Services.get(IOrderDeliveryDayBL.class);

	@Init
	public void setupCallouts()
	{
		final IProgramaticCalloutProvider calloutProvider = Services.get(IProgramaticCalloutProvider.class);

		calloutProvider.registerAnnotatedCallout(new de.metas.tourplanning.callout.C_Order());
	}

	/**
	 * Search for matching {@link I_M_DeliveryDay} and set order's preparation date from it.
	 * <p>
	 * Before that, if the shipper lead-time forward calc applies, sets the order's DatePromised from DateOrdered;
	 * this is done first (above the "PreparationDate was set explicitly" guard) so that a save in which the UI callout
	 * also set PreparationDate still gets its DatePromised, and so the preparation date derives from the new DatePromised.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = {
			I_C_Order.COLUMNNAME_C_BPartner_Location_ID,
			I_C_Order.COLUMNNAME_DatePromised,
			I_C_Order.COLUMNNAME_PreparationDate,
			I_C_Order.COLUMNNAME_DateOrdered,
			I_C_Order.COLUMNNAME_M_Shipper_ID })
	public void setPreparationDate(final I_C_Order order, final int timing)
	{
		// Forward trigger for DatePromised = workday(DateOrdered, +leadTime).
		final boolean isForwardTrigger;
		if (timing == ModelValidator.TYPE_BEFORE_NEW)
		{
			// At creation, derive DatePromised ONLY when the creator did not supply one, so an explicit value is
			// preserved (webui callout / REST / OLCand-EDI import / copied order). "Was DatePromised supplied?" is a
			// business rule (the MOrder constructor seeds DatePromised with today, so is_ValueChanged cannot tell) and
			// lives in the BL; the interceptor stays thin glue.
			isForwardTrigger = !orderDeliveryDayBL.isDatePromisedSuppliedAtCreation(order);
		}
		else
		{
			// On change: recompute when DateOrdered or the shipper changed. A manual DatePromised edit with
			// DateOrdered and M_Shipper_ID unchanged is NOT a forward trigger: the manual value sticks and
			// PreparationDate derives from it.
			isForwardTrigger = isValueChanged(order, I_C_Order.COLUMNNAME_DateOrdered)
					|| isValueChanged(order, I_C_Order.COLUMNNAME_M_Shipper_ID);
		}
		if (isForwardTrigger)
		{
			orderDeliveryDayBL.setDatePromisedFromLeadTime(order);
		}

		//
		// If PreparationDate was explicitelly set by user, don't change it
		if (isValueChanged(order, I_C_Order.COLUMNNAME_PreparationDate)
				&& order.getPreparationDate() != null)
		{
			return;
		}

		// (Re)derive PreparationDate + tour only when something that DRIVES it changed:
		//  - a classic driving column: delivery location, DatePromised (incl. one the forward calc above just set),
		//    or an explicitly cleared PreparationDate — this is the only path for a no-lead-time order, so a bare
		//    DateOrdered / M_Shipper_ID change on such an order does NOT re-run the tour/fallback recompute;
		//  - OR a forward trigger (DateOrdered / M_Shipper_ID changed, or a new order) on a LEAD-TIME order: then the
		//    backward lead-time derivation must run even if DatePromised did not move (e.g. IsFixedDatePromised='Y',
		//    where the pinned DatePromised stays but the new route's lead time shifts PreparationDate).
		final boolean preparationDrivingColumnChanged = isValueChanged(order, I_C_Order.COLUMNNAME_C_BPartner_Location_ID)
				|| isValueChanged(order, I_C_Order.COLUMNNAME_DatePromised)
				|| isValueChanged(order, I_C_Order.COLUMNNAME_PreparationDate);
		if (!preparationDrivingColumnChanged && !(isForwardTrigger && orderDeliveryDayBL.hasLeadTime(order)))
		{
			return;
		}

		// task 08931: if the order is created by a user, that user needs to make sure a proper preparation date is in place
		// Goal: motivate/educate the users to maintain proper tour planning masterdata to reduce trouble in the further stages of operative business.
		// However, if the order is created by the system (e.g. from EDI-olCands), we allow a fallback to DatePromised
		final boolean fallbackToDatePromised = !isUIAction(order);

		orderDeliveryDayBL.setPreparationDateAndTour(order, fallbackToDatePromised);
	}

	/**
	 * Make sure the PreparationDate is set
	 */
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_PREPARE })
	public void assertValidPreparationDate(final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			return; // task 09000: nothing to do, the PreparationDate is only relevant for sales orders.
		}

		//
		// Make sure the PreparationDate is set
		final Timestamp preparationDate = order.getPreparationDate();
		if (preparationDate == null)
		{
			throw new FillMandatoryException(I_C_Order.COLUMNNAME_PreparationDate);
		}

		// task 09000: Actually, this is what we want, but commented out for now, because then the fallback won't work.
		// TODO: come up with another solution, e.g. no fallback and eval/fix this already in the OLCands
		// @formatter:off
		//
		// Make sure the PreparationDate not equals DatePromised,
		// because in that case, for sure it's an error which will strike the user later.
//		final Timestamp datePromised = order.getSupplyDate();
//		if (Check.equals(preparationDate, datePromised))
//		{
//			throw new AdempiereException("@Invalid@ @DatePromised@ = @PreparationDate@");
//		}
		// @formatter:on
	}

}
