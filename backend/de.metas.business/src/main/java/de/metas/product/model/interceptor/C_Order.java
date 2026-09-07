package de.metas.product.model.interceptor;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

import com.google.common.collect.ImmutableSet;
import de.metas.order.IOrderDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductLifeCycleAction;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Product life-cycle status enforcement on {@code C_Order}.
 * <p>
 * Lives in this feature package on purpose: {@code de.metas.order.model.validator.C_Order} is already taken,
 * and a second class with that same fully-qualified name would shadow it on the runtime classpath (only one
 * of the two jars wins), silently unregistering that interceptor's callbacks.
 */
@Interceptor(I_C_Order.class)
@Component
public class C_Order
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	/**
	 * Blocks completing an order that carries a product whose life-cycle status forbids the order's action —
	 * {@code SELL} for a sales order, {@code PURCHASE} for a purchase order.
	 * <p>
	 * This is a <b>re-check</b>: {@code de.metas.order.model.interceptor.C_OrderLine} already enforces the
	 * same rule when a line is created or its product changed, but it cannot see a status flipped to a
	 * blocking value <i>afterwards</i>. Completion is the last moment before the order becomes binding, so it
	 * is where the current status has to hold again.
	 * <p>
	 * Self-gating: products with status {@code O}/null are a no-op (see {@link IProductBL#assertAllowed}).
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void assertProductsAllowedOnComplete(final I_C_Order order)
	{
		final ImmutableSet<ProductId> productIds = orderDAO.retrieveOrderLines(order)
				.stream()
				.map(line -> ProductId.ofRepoIdOrNull(line.getM_Product_ID()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		productBL.assertAllowed(productIds, order.isSOTrx() ? ProductLifeCycleAction.SELL : ProductLifeCycleAction.PURCHASE);
	}
}
