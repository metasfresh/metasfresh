package de.metas.contracts.inoutcandidate;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import ch.qos.logback.classic.Level;
import de.metas.adempiere.model.I_C_Order;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.inoutcandidate.spi.ModelWithoutShipmentScheduleVetoer;
import de.metas.logging.LogManager;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.model.InterfaceWrapperHelper;
import org.slf4j.Logger;

import java.util.List;
import java.util.Properties;

/**
 * This implementation vetoes the creation of shipment schedule records for {@link I_C_OrderLine}s if those order lines
 * are handled by a flatrate contract.
 */
@ToString
public class ShipmentScheduleFromSubscriptionOrderLineVetoer implements ModelWithoutShipmentScheduleVetoer
{

	private static final Logger logger = LogManager.getLogger(ShipmentScheduleFromSubscriptionOrderLineVetoer.class);

	/**
	 * @param model
	 *            the object for which we want to create a shipment schedule. The method
	 *            assumes that it can obtain a {@link I_C_OrderLine} instance for model by using the
	 *            {@link InterfaceWrapperHelper}.
	 */
	@Override
	public OnMissingCandidate foundModelWithoutInOutCandidate(@NonNull final Object model)
	{
		final I_C_OrderLine ol = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);

		final boolean subscription = isSubscription(ol);
		final boolean hasAtLeastOneFlatrateContract = hasAtLeastOneFlatrateContract(ol);
		final boolean veto = subscription || hasAtLeastOneFlatrateContract;

		if (veto)
		{
			Loggables.withLogger(logger, Level.DEBUG)
					.addLog("ShipmentScheduleFromSubscriptionOrderLineVetoer - isSubscription={}; hasAtLeastOneFlatrateContract={}; return {}; orderLine={}",
							subscription, hasAtLeastOneFlatrateContract, OnMissingCandidate.I_VETO, ol);
			return OnMissingCandidate.I_VETO;
		}
		return OnMissingCandidate.I_DONT_CARE;
	}

	public boolean hasAtLeastOneFlatrateContract(@NonNull final I_C_OrderLine ol)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(ol);
		final String trxName = InterfaceWrapperHelper.getTrxName(ol);

		final I_C_Order o = InterfaceWrapperHelper.create(ol.getC_Order(), I_C_Order.class);

		final ProductId productId = ProductId.ofRepoId(ol.getM_Product_ID());
		final ProductCategoryId productCategoryId = Services.get(IProductDAO.class).retrieveProductCategoryByProductId(productId);

		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);
		final List<I_C_Flatrate_Term> termsForOl = flatrateDB.retrieveTerms(
				ctx,
				o.getBill_BPartner_ID(),
				o.getDateOrdered(),
				ProductCategoryId.toRepoId(productCategoryId),
				ProductId.toRepoId(productId),
				ol.getC_Charge_ID(),
				trxName);

		// if there are terms for 'ol', then we ask the handler not to create a shipment schedule for 'ol'
		final boolean atLeastOneFlatrateContract = !termsForOl.isEmpty();
		return atLeastOneFlatrateContract;
	}

	private boolean isSubscription(@NonNull final I_C_OrderLine ol)
	{
		return ol.getC_Flatrate_Conditions_ID() > 0;
	}
}
