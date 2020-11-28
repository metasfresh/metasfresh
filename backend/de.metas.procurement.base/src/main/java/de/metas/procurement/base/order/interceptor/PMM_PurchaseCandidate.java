package de.metas.procurement.base.order.interceptor;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;

import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.order.IPMMPurchaseCandidateBL;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.procurement.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Interceptor(I_PMM_PurchaseCandidate.class)
@Callout(I_PMM_PurchaseCandidate.class)
public class PMM_PurchaseCandidate
{
	public static final transient PMM_PurchaseCandidate instance = new PMM_PurchaseCandidate();

	private PMM_PurchaseCandidate()
	{
		super();
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void onDelete(final I_PMM_PurchaseCandidate candidate)
	{
		// NOTE: prevent deletion mainly because PMM_QtyReport_Event is fetching the QtyPromised_Old from there...
		// If we really want to allow deleting, we will have to trigger an event to update the PMM_Balance
		throw new AdempiereException("Deleting candidates is not allowed");
	}

	@CalloutMethod(columnNames = I_PMM_PurchaseCandidate.COLUMNNAME_QtyToOrder_TU)
	public void onQtyToOrderTUChanged_UI(final I_PMM_PurchaseCandidate candidate)
	{
		Services.get(IPMMPurchaseCandidateBL.class).updateQtyToOrderFromQtyToOrderTU(candidate);
	}

	@CalloutMethod(columnNames = I_PMM_PurchaseCandidate.COLUMNNAME_M_HU_PI_Item_Product_Override_ID)
	public void onM_HU_PI_Item_Product_Override_Changed(final I_PMM_PurchaseCandidate candidate)
	{
		// Task FRESH-265: Make sure the QtyToOrder also updates on M_HU_PI_Item_Product override
		Services.get(IPMMPurchaseCandidateBL.class).updateQtyToOrderFromQtyToOrderTU(candidate);
	}

}
