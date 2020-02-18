package de.metas.adempiere.gui.search.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import java.math.BigDecimal;

import de.metas.handlingunits.model.I_C_InvoiceLine;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.product.ProductId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.NonNull;

public class InvoiceLineHUPackingAware implements IHUPackingAware
{
	private final I_C_InvoiceLine invoiceLine;
	private final PlainHUPackingAware values = new PlainHUPackingAware();

	private InvoiceLineHUPackingAware(@NonNull final I_C_InvoiceLine invoiceLine)
	{
		this.invoiceLine = invoiceLine;
	}

	public static InvoiceLineHUPackingAware of(@NonNull final org.compiere.model.I_C_InvoiceLine invoiceLine )
	{
		return new InvoiceLineHUPackingAware(InterfaceWrapperHelper.create(invoiceLine, I_C_InvoiceLine.class));
	}

	@Override
	public int getM_Product_ID()
	{
		return invoiceLine.getM_Product_ID();
	}

	@Override
	public void setM_Product_ID(final int productId)
	{
		invoiceLine.setM_Product_ID(productId);
	}

	@Override
	public int getM_AttributeSetInstance_ID()
	{
		return invoiceLine.getM_AttributeSetInstance_ID();
	}

	@Override
	public void setM_AttributeSetInstance_ID(final int M_AttributeSetInstance_ID)
	{
		invoiceLine.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
	}

	@Override
	public int getC_UOM_ID()
	{
		return invoiceLine.getC_UOM_ID();
	}

	@Override
	public void setC_UOM_ID(final int uomId)
	{
		invoiceLine.setC_UOM_ID(uomId);
	}

	@Override
	public void setQty(@NonNull final BigDecimal qtyInHUsUOM)
	{
		invoiceLine.setQtyEntered(qtyInHUsUOM);

		final ProductId productId = ProductId.ofRepoIdOrNull(getM_Product_ID());
		final I_C_UOM uom = Services.get(IUOMDAO.class).getById(getC_UOM_ID());
		final BigDecimal qtyInvoiced = Services.get(IUOMConversionBL.class).convertToProductUOM(productId, uom, qtyInHUsUOM);
		invoiceLine.setQtyInvoiced(qtyInvoiced);
	}

	@Override
	public BigDecimal getQty()
	{
		return invoiceLine.getQtyEntered();
	}

	@Override
	public int getM_HU_PI_Item_Product_ID()
	{
		//
		// Check the invoice line first
		final int invoiceLine_PIItemProductId = invoiceLine.getM_HU_PI_Item_Product_ID();
		if (invoiceLine_PIItemProductId > 0)
		{
			return invoiceLine_PIItemProductId;
		}

		//
		// Check order line
		final I_C_OrderLine orderline = InterfaceWrapperHelper.create(invoiceLine.getC_OrderLine(), I_C_OrderLine.class);
		if (orderline == null)
		{
			//
			// C_OrderLine not found (i.e Manual Invoice)
			return -1;
		}
		return orderline.getM_HU_PI_Item_Product_ID();
	}

	@Override
	public void setM_HU_PI_Item_Product_ID(final int huPiItemProductId)
	{
		invoiceLine.setM_HU_PI_Item_Product_ID(huPiItemProductId);
	}

	@Override
	public BigDecimal getQtyTU()
	{
		return invoiceLine.getQtyEnteredTU();
	}

	@Override
	public void setQtyTU(final BigDecimal qtyPacks)
	{
		invoiceLine.setQtyEnteredTU(qtyPacks);
	}

	@Override
	public void setC_BPartner_ID(final int partnerId)
	{
		values.setC_BPartner_ID(partnerId);
	}

	@Override
	public int getC_BPartner_ID()
	{
		return values.getC_BPartner_ID();
	}

	@Override
	public boolean isInDispute()
	{
		return values.isInDispute();
	}

	@Override
	public void setInDispute(final boolean inDispute)
	{
		values.setInDispute(inDispute);
	}
}
