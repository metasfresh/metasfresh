package de.metas.contracts.impl;

import de.metas.contracts.FlatrateTermPricing;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.spi.FallbackFlatrateTermEventListener;
import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.ISubscriptionDAO.SubscriptionProgressQuery;
import de.metas.pricing.IPricingResult;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;

import java.util.List;

import static de.metas.contracts.model.X_C_Flatrate_Conditions.ONFLATRATETERMEXTEND_CalculatePrice;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.ONFLATRATETERMEXTEND_CopyPrice;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class SubscriptionTermEventListener extends FallbackFlatrateTermEventListener
{
	public static final String TYPE_CONDITIONS_SUBSCRIPTION = X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription;
	private static final String MSG_TERM_ERROR_DELIVERY_ALREADY_HAS_SHIPMENT_SCHED_0P = "Term_Error_Delivery_Already_Has_Shipment_Sched";

	@Override
	public void beforeFlatrateTermReactivate(@NonNull final I_C_Flatrate_Term term)
	{
		// Delete subscription progress entries
		final ISubscriptionDAO subscriptionBL = Services.get(ISubscriptionDAO.class);
		final List<I_C_SubscriptionProgress> entries = subscriptionBL.retrieveSubscriptionProgresses(SubscriptionProgressQuery.builder()
				.term(term).build());

		for (final I_C_SubscriptionProgress entry : entries)
		{
			if (entry.getM_ShipmentSchedule_ID() > 0)
			{
				throw new AdempiereException("@" + MSG_TERM_ERROR_DELIVERY_ALREADY_HAS_SHIPMENT_SCHED_0P + "@");
			}
			InterfaceWrapperHelper.delete(entry);
		}
	}

	@Override
	public void beforeSaveOfNextTermForPredecessor(
			@NonNull final I_C_Flatrate_Term next,
			@NonNull final I_C_Flatrate_Term predecessor)
	{
		final I_C_Flatrate_Conditions conditions = next.getC_Flatrate_Conditions();
		if (ONFLATRATETERMEXTEND_CalculatePrice.equals(conditions.getOnFlatrateTermExtend()))
		{
			final IPricingResult pricingInfo = FlatrateTermPricing.builder()
					.termRelatedProductId(ProductId.ofRepoIdOrNull(next.getM_Product_ID()))
					.term(next)
					.priceDate(TimeUtil.asLocalDate(next.getStartDate()))
					.qty(next.getPlannedQtyPerUnit())
					.build()
					.computeOrThrowEx();

			next.setPriceActual(pricingInfo.getPriceStd());
			next.setC_Currency_ID(pricingInfo.getCurrencyRepoId());
			next.setC_UOM_ID(UomId.toRepoId(pricingInfo.getPriceUomId()));
			next.setC_TaxCategory_ID(TaxCategoryId.toRepoId(pricingInfo.getTaxCategoryId()));
			next.setIsTaxIncluded(pricingInfo.isTaxIncluded());
		}
		else if (ONFLATRATETERMEXTEND_CopyPrice.equals(conditions.getOnFlatrateTermExtend()))
		{
			next.setPriceActual(predecessor.getPriceActual());
			next.setC_Currency_ID(predecessor.getC_Currency_ID());
			next.setC_UOM_ID(predecessor.getC_UOM_ID());
			next.setC_TaxCategory_ID(predecessor.getC_TaxCategory_ID());
			next.setIsTaxIncluded(predecessor.isTaxIncluded());
		}
		else
		{
			throw new AdempiereException("Unexpected OnFlatrateTermExtend=" + conditions.getOnFlatrateTermExtend())
					.appendParametersToMessage()
					.setParameter("conditions", conditions)
					.setParameter("predecessor", predecessor)
					.setParameter("next", next);
		}
	}
}
