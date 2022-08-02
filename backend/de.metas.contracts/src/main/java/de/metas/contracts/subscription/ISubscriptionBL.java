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

import de.metas.contracts.flatrate.interfaces.I_C_OLCand;
import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.order.model.I_C_Order;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.lang.SOTrx;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.exceptions.ProductNotOnPriceListException;
import de.metas.process.PInstanceId;
import de.metas.product.ProductAndCategoryId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

public interface ISubscriptionBL extends ISingletonService
{
	/**
	 * Iterates over all {@link I_C_SubscriptionProgress} (sp) records that represent a delivery which is due. Creates
	 * an {@link I_M_ShipmentSchedule} record for each delivery, unless:
	 * <ul>
	 * <li>there already is an older open delivery for the sp's subscription</li>
	 * <li>there already is an older delayed delivery for the sp's subscription</li>
	 * </ul>
	 * In these cases the current delivery is delayed.
	 */
	void evalDeliveries(Properties ctx);

	/**
	 * Iterate the given deliveries, sum up their price (using their orderline's product and priceActual). Then compute
	 * the price that would be payable with the given pricing system and return the difference.
	 *
	 * @param deliveries      instances with {@link I_C_SubscriptionProgress#COLUMNNAME_EventType} =
	 *                        {@link X_C_SubscriptionProgress#EVENTTYPE_Delivery}.
	 * @return a positive number if the deliveries would be more expensive with the given pricing system. A negative
	 * number if they would be less expensive.
	 * @throws IllegalArgumentException       if not all {@link I_C_SubscriptionProgress} instances if the list are deliveries or if not all
	 *                                        deliveries belong to the same order line or if there is no pricing system with the given id.
	 * @throws ProductNotOnPriceListException if the product can't be delivered according to the new pricing system.
	 */
	BigDecimal computePriceDifference(Properties ctx, PricingSystemId pricingSystemId, List<I_C_SubscriptionProgress> deliveries, String trxName);

	/**
	 * Works on the given {@code sc}'s next {@link I_C_SubscriptionProgress} record.
	 * "Next" means the next record after the given date. If the sc doesn't have a next record and the subscription is
	 * supposed to go on, new records are created. If the next record's event date is the current date, the sc's
	 * subscriptionStatus is set to the next record's subscription status.
	 */
	void evalCurrentSPs(I_C_Flatrate_Term sc, Timestamp currentDate);

	I_C_Flatrate_Matching retrieveMatching(
			Properties ctx,
			int flatrateConditionsId,
			ProductAndCategoryId productAndCategoryId,
			String trxName);

	/**
	 * Use the given <code>C_Flatrate_Transition</code>'s <code>TermDurationUnit</code>,
	 * <code>TermDuration</code>, <code>FrequencyType</code> and <code>Frequency</code> values to compute the number of
	 * subscription deliveries, starting from the given date.
	 *
	 * @param date  note that the method uses a {@link GregorianCalendar} to make the computations. Because different
	 *              months have different numbers of days, the result might be different for different <code>date</code>
	 *              values.
	 */
	int computeNumberOfRuns(I_C_Flatrate_Transition trans, Timestamp date);

	Timestamp mkNextDate(String deliveryIntervalUnit, int deliveryInterval, Timestamp currentDate);

	/**
	 * Creates a new term for the given order line
	 *
	 * @param completeIt if <code>true</code>, then the new term is completed
	 */
	I_C_Flatrate_Term createSubscriptionTerm(I_C_OrderLine ol, boolean completeIt);

	I_C_Flatrate_Term createSubscriptionTerm(I_C_OLCand olCand, boolean completeIt);

	/**
	 * @param completeIt      if <code>true</code>, the new terms will directly be completed.
	 * @param AD_PInstance_ID the process instance ID of the process that called this method. Will be stored for documentation.
	 * @return the number of <code>C_OLCand</code> records that were processed.
	 */
	int createMissingTermsForOLCands(Properties ctx, boolean completeIt, PInstanceId AD_PInstance_ID, String trxName);

	I_C_Flatrate_Term createTermForOLCand(Properties ctx, I_C_OLCand olCand, PInstanceId AD_PInstance_ID, boolean completeIt, String trxName);

	boolean isActiveTerm(I_C_Flatrate_Term term);

	I_C_Flatrate_Term retrieveLastFlatrateTermFromOrder(I_C_Order order);

	void updateQtysAndPrices(
			@NonNull I_C_OrderLine ol,
			@NonNull SOTrx soTrx,
			boolean updatePriceEnteredAndDiscountOnlyIfNotAlreadySet);

	/**
	 * Creates a Price change record in <code>C_SubscriptionProgress</code>
	 */
	void createPriceChange(@NonNull final I_C_Flatrate_Term term);

	/**
	 * Creates a Price change record in <code>C_SubscriptionProgress</code>
	 */
	void createQtyChange(@NonNull final I_C_Flatrate_Term term,	@Nullable final BigDecimal newQty);

	boolean isSubscription(final I_C_OrderLine ol);
}
