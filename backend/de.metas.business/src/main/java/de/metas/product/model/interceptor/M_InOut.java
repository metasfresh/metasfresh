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

import de.metas.inout.IInOutDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductLifeCycleAction;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * Product life-cycle status enforcement on {@code M_InOut}.
 * <p>
 * Lives in this feature package on purpose: {@code de.metas.inout.model.validator.M_InOut} is already taken by
 * {@code de.metas.swat.base}, and a second class with that same fully-qualified name would shadow it on the
 * runtime classpath (only one of the two jars wins), silently unregistering that interceptor's captured-location /
 * rendered-address, BPartner-balance and void-guard callbacks.
 */
@Interceptor(I_M_InOut.class)
@Component
public class M_InOut
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	/**
	 * Blocks completing a <b>shipment</b> that contains a product whose life-cycle status forbids shipping
	 * (e.g. {@code N} / "Lieferstopp", or {@code G} / "Gesperrt"). Enforced at completion rather than at
	 * line creation so that:
	 * <ul>
	 *     <li>receipts ({@code IsSOTrx='N'}) are never affected — only sales shipments are checked;</li>
	 *     <li>reversal/void documents ({@code Reversal_ID} set) are exempt, so an already-completed shipment
	 *     can always be reversed regardless of the product's <i>current</i> status — no retroactive
	 *     invalidation of a document that was legitimately completed while the product was still shippable.</li>
	 * </ul>
	 * Self-gating: products with status {@code O}/null are a no-op (see {@link IProductBL#assertAllowed}).
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void assertProductsAllowedForShipment(final I_M_InOut inOut)
	{
		if (!inOut.isSOTrx())
		{
			return; // receipts are not shipments
		}
		if (inOut.getReversal_ID() > 0)
		{
			return; // reversal / void of an existing shipment — never retroactively blocked
		}

		for (final I_M_InOutLine line : inOutDAO.retrieveLines(inOut))
		{
			final int productRepoId = line.getM_Product_ID();
			if (productRepoId > 0)
			{
				productBL.assertAllowed(ProductId.ofRepoId(productRepoId), ProductLifeCycleAction.SHIP);
			}
		}
	}
}
