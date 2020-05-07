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
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.handlingunits.model.I_C_InvoiceLine;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

public class InvoiceLineHUPackingAware implements IHUPackingAware
{
	private final I_C_InvoiceLine invoiceLine;
	private final PlainHUPackingAware values = new PlainHUPackingAware();

	public InvoiceLineHUPackingAware(final I_C_InvoiceLine invoiceLine)
	{
		super();

		Check.assumeNotNull(invoiceLine, "orderLine not null");
		this.invoiceLine = invoiceLine;
	}

	@Override
	public int getM_Product_ID()
	{
		return invoiceLine.getM_Product_ID();
	}

	@Override
	public I_M_Product getM_Product()
	{
		return invoiceLine.getM_Product();
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
	public I_C_UOM getC_UOM()
	{
		return invoiceLine.getC_UOM();
	}

	@Override
	public void setC_UOM(final I_C_UOM uom)
	{
		invoiceLine.setC_UOM(uom);
	}

	@Override
	public void setQty(final BigDecimal qty)
	{
		invoiceLine.setQtyEntered(qty);

		final Properties ctx = InterfaceWrapperHelper.getCtx(invoiceLine);
		final BigDecimal qtyInvoiced = Services.get(IUOMConversionBL.class).convertToProductUOM(ctx, getM_Product(), getC_UOM(), qty);
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
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		// 
		// In case the invoice line has an pi item product, just return it (task 08908)
		final I_M_HU_PI_Item_Product il_piip = invoiceLine.getM_HU_PI_Item_Product();
		if(il_piip != null)
		{
			return il_piip;
		}
		
		final I_C_OrderLine orderline = InterfaceWrapperHelper.create(invoiceLine.getC_OrderLine(), I_C_OrderLine.class);
		if (orderline == null)
		{
			//
			// C_OrderLine not found (i.e Manual Invoice)
			return null;
		}
		return orderline.getM_HU_PI_Item_Product();
	}

	@Override
	public void setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product huPiItemProduct)
	{
		invoiceLine.setM_HU_PI_Item_Product(huPiItemProduct);
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
	public void setC_BPartner(final I_C_BPartner partner)
	{
		values.setC_BPartner(partner);
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		return values.getC_BPartner();
	}

	@Override
	public void setDateOrdered(final Timestamp dateOrdered)
	{
		values.setDateOrdered(dateOrdered);
	}

	@Override
	public Timestamp getDateOrdered()
	{
		return values.getDateOrdered();
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
