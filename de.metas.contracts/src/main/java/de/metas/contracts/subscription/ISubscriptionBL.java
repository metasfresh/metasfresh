package de.metas.contracts.subscription;

/*
 * #%L
 * de.metas.contracts
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import org.adempiere.pricing.exceptions.ProductNotOnPriceListException;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.model.I_C_Order;
import de.metas.contracts.subscription.model.I_C_OrderLine;
import de.metas.flatrate.interfaces.I_C_OLCand;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_Matching;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_Flatrate_Transition;
import de.metas.flatrate.model.I_C_SubscriptionProgress;
import de.metas.flatrate.model.X_C_SubscriptionProgress;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

public interface ISubscriptionBL extends ISingletonService
{
	/**
	 * 
	 * Creates new {@link I_C_SubscriptionProgress} delivery records for the given <code>term</code>. The first sp has
	 * the term's <code>StartDate</code> as EventDate.
	 * 
	 * @param term
	 * @return the first <code>C_SubscriptionProgress</code> that has been created
	 */
	// this method's name used to be 'newSubscription'
	I_C_SubscriptionProgress createSubscriptionEntries(I_C_Flatrate_Term term);

	/**
	 * Iterates over all {@link I_C_SubscriptionProgress} (sp) records that represent a delivery which is due. Creates
	 * an {@link I_M_ShipmentSchedule} record for each delivery, unless:
	 * <ul>
	 * <li>there already is an older open delivery for the sp's subscription</li>
	 * <li>there already is an older delayed delivery for the sp's subscription</li>
	 * </ul>
	 * In these cases the current delivery is delayed.
	 * 
	 * @param trxName
	 */
	void evalDeliveries(Properties ctx, String trxName);

	/**
	 * Iterate the given deliveries, sum up their price (using their orderline's product and priceActual). Then compute
	 * the price that wound be payable with the given pricing system and return the different.
	 * 
	 * @param ctx
	 * @param mPricingSystemId
	 * @param deliveries
	 *            instances with {@link I_C_SubscriptionProgress#COLUMNNAME_EventType} =
	 *            {@link X_C_SubscriptionProgress#EVENTTYPE_Lieferung}.
	 * @return a positive number if the deliveries would be more expensive with the given pricing system. A negative
	 *         number if they would be less expensive.
	 * @throws IllegalArgumentException
	 *             if not all {@link I_C_SubscriptionProgress} instances if the list are deliveries or if not all
	 *             deliveries belong to the same order line or if there is no pricing system with the given id.
	 * @throws ProductNotOnPriceListException
	 *             if the product can't be delivered according to the new pricing system.
	 */
	BigDecimal computePriceDifference(Properties ctx, int mPricingSystemId, List<I_C_SubscriptionProgress> deliveries, String trxName);

	I_C_OrderLine createNewOrderAndOl(I_C_OrderLine oldOl, Timestamp dateOrdered,
			Timestamp datePromised, String trxName);

	/**
	 * Works on the given <code>MSubscriptionControl</code>'s (a.k.a sc) next {@link I_C_SubscriptionProgress} record.
	 * "Next" means the next record after the given date. If the sc doesn't have a next record and the subscription is
	 * supposed to go on, new records are created. If the next record's event date is the current date, the sc's
	 * subscriptionStatus is set to the next record's subscription status.
	 * 
	 * @param sc
	 * @param currentDate
	 */
	void evalCurrentSPs(I_C_Flatrate_Term sc, Timestamp currentDate);

	/**
	 * Uses the given <code>subscription</code> to set the given <code>ol</code>'s
	 * <ul>
	 * <li><code>QtyOrdered</code>: this will be the absolute number of all delivered goods during the subscription
	 * term.
	 * <li>price: if <code>subscription</code> has no <code>M_PricingSystem_ID</code> set, then it will use the pricing
	 * system of <code>ol</code>'s C_Order.
	 * </ul>
	 * 
	 * @param ol
	 * @param subscription
	 */
	void setSubscription(I_C_OrderLine ol, I_C_Flatrate_Conditions subscription);

	I_C_Flatrate_Matching retrieveMatching(Properties ctx, int flatrateConditionsId, I_M_Product product, String trxName);

	/**
	 * this method uses the given <code>C_Flatrate_Transition</code>'s <code>TermDurationUnit</code>,
	 * <code>TermDuration</code>, <code>FrequencyType</code> and <code>Frequency</code> values to compute the number of
	 * subscription deliveries, starting from the given date.
	 * 
	 * @param trans
	 * @param date
	 *            note that the method uses a {@link GregorianCalendar} to make the computations. Because different
	 *            months have different numbers of days, the result might be different ofr different <code>date</code>
	 *            values.
	 * 
	 * @return
	 */
	int computeNumberOfRuns(I_C_Flatrate_Transition trans, Timestamp date);

	Timestamp mkNextDate(String deliveryIntervalUnit, int deliveryInterval, Timestamp currentDate);

	/**
	 * Creates a new term for the given order line
	 * 
	 * @param ol
	 * @param completeIt
	 *            if <code>true</code>, then the new term is completed
	 * @param order
	 *            may be <code>null</code>. Can be used to provide <code>ol</code>'s order if that order has already
	 *            been loaded.
	 * @return
	 */
	I_C_Flatrate_Term createSubscriptionTerm(I_C_OrderLine ol, boolean completeIt, I_C_Order order);

	I_C_Flatrate_Term createSubscriptionTerm(I_C_OLCand olCand, boolean completeIt);

	/**
	 * 
	 * 
	 * @param ctx
	 * @param completeId
	 *            if <code>true</code>, the new terms will directly be completed.
	 * @param AD_PInstance_ID
	 *            the process instance ID of the process that called this method. Will be stored for documentation.
	 * @param trxName
	 * @return the number of <code>C_OLCand</code> records that were processed.
	 */
	int createMissingTermsForOLCands(Properties ctx, boolean completeIt, int AD_PInstance_ID, String trxName);

	I_C_Flatrate_Term createTermForOLCand(Properties ctx, I_C_OLCand olCand, int AD_PInstance_ID, boolean completeIt, String trxName);
}
