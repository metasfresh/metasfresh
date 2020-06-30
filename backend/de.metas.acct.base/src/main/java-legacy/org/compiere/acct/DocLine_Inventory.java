package org.compiere.acct;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InventoryLine;

import de.metas.acct.api.AcctSchema;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostingDocumentRef;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class DocLine_Inventory extends DocLine<Doc_Inventory>
{

	private BigDecimal costPrice = BigDecimal.ZERO;

	public DocLine_Inventory(final I_M_InventoryLine inventoryLine, final Doc_Inventory doc)
	{
		super(InterfaceWrapperHelper.getPO(inventoryLine), doc);

		final BigDecimal qty;
		BigDecimal qtyInternalUse = inventoryLine.getQtyInternalUse();
		if (qtyInternalUse.signum() != 0)
		{
			qty = qtyInternalUse.negate();		// Internal Use entered positive
		}
		else
		{
			BigDecimal qtyBook = inventoryLine.getQtyBook();
			BigDecimal qtyCount = inventoryLine.getQtyCount();
			qty = qtyCount.subtract(qtyBook);
		}

		// calculate the cost price considering qty in inventory UOM
		if (qty.signum() > 0)
		{
			this.costPrice = inventoryLine.getCostPrice().multiply(qty);
		}
		
		setQty(getQuantityInStockingUOM(qty, inventoryLine.getC_UOM_ID()), false);

		setReversalLine_ID(inventoryLine.getReversalLine_ID());
	}

	public CostAmount getCreateCosts(final AcctSchema as)
	{
		if (isReversalLine())
		{
			return services.createReversalCostDetails(CostDetailReverseRequest.builder()
					.acctSchemaId(as.getId())
					.reversalDocumentRef(CostingDocumentRef.ofInventoryLineId(get_ID()))
					.initialDocumentRef(CostingDocumentRef.ofInventoryLineId(getReversalLine_ID()))
					.date(getDateAcct())
					.build())
					.getTotalAmountToPost(as);
		}
		else
		{
			return services.createCostDetail(
					CostDetailCreateRequest.builder()
							.acctSchemaId(as.getId())
							.clientId(getClientId())
							.orgId(getOrgId())
							.productId(getProductId())
							.attributeSetInstanceId(getAttributeSetInstanceId())
							.documentRef(CostingDocumentRef.ofInventoryLineId(get_ID()))
							.qty(getQty())
							.amt(CostAmount.of(this.costPrice, as.getCurrencyId()))
							.date(getDateAcct())
							.build())
					.getTotalAmountToPost(as);
		}
	}

	/**
	 * @param qty inventory quantity
	 * @param inventoryUOMId UOM used for the inventory process
	 * @return inventory quantity in the unit of measurement used for stocking.
	 */
	private Quantity getQuantityInStockingUOM(final BigDecimal qty, final int inventoryUOMId)
	{
		final Quantity quantityInStockingUOM;

		final I_C_UOM inventoryUOM = Services.get(IUOMDAO.class).getById(inventoryUOMId);

		Check.assume(inventoryUOM != null, " No Unit of measurement was found for the given Id: {}", inventoryUOMId);

		final I_C_UOM stockingUOM = getProductStockingUOM();

		final BigDecimal qtyValueInStockingUOM = Services.get(IUOMConversionBL.class)
				.convertQty(getProductId(), qty, inventoryUOM, stockingUOM);

		quantityInStockingUOM = Quantity.of(qtyValueInStockingUOM, stockingUOM);

		return quantityInStockingUOM;
	}
}
