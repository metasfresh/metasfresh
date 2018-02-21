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
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.handlingunits.model.I_C_OLCand;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;

/**
 * Wraps an {@link I_C_OrderLine} and makes it behave like an {@link IHUPackingAware}.
 *
 * @author tsa
 *
 */
public class OLCandHUPackingAware implements IHUPackingAware
{
	private final I_C_OLCand olCand;

	/**
	 * Plain delegate for fields which are not available in order line
	 */
	private final PlainHUPackingAware values = new PlainHUPackingAware();

	public OLCandHUPackingAware(final de.metas.ordercandidate.model.I_C_OLCand olCand)
	{
		super();

		Check.assumeNotNull(olCand, "olcand not null");
		this.olCand = InterfaceWrapperHelper.create(olCand, I_C_OLCand.class);
	}

	@Override
	public int getM_Product_ID()
	{
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
		return olCandEffectiveValuesBL.getM_Product_Effective_ID(olCand);
	}

	@Override
	public I_M_Product getM_Product()
	{
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
		return olCandEffectiveValuesBL.getM_Product_Effective(olCand);
	}

	@Override
	public void setM_Product_ID(final int productId)
	{
		olCand.setM_Product_Override_ID(productId);
		values.setM_Product_ID(productId);
	}

	@Override
	public void setQty(final BigDecimal qty)
	{
		olCand.setQty(qty);
		values.setQty(qty);
	}

	@Override
	public BigDecimal getQty()
	{
		return olCand.getQty();
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);
		final String trxName = InterfaceWrapperHelper.getTrxName(olCand);

		final int m_HU_PI_Item_Product_ID = getM_HU_PI_Item_Product_ID();
		if (m_HU_PI_Item_Product_ID <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.create(
				ctx,
				m_HU_PI_Item_Product_ID,
				I_M_HU_PI_Item_Product.class,
				trxName);
	}

	@Override
	public void setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product huPiItemProduct)
	{
		olCand.setM_HU_PI_Item_Product_Override(huPiItemProduct);
		values.setM_HU_PI_Item_Product(huPiItemProduct);
	}

	@Override
	public int getM_AttributeSetInstance_ID()
	{
		return olCand.getM_AttributeSetInstance_ID();
	}

	@Override
	public void setM_AttributeSetInstance_ID(final int M_AttributeSetInstance_ID)
	{
		olCand.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);

		values.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
		return olCandEffectiveValuesBL.getC_UOM_Effective(olCand);
	}

	@Override
	public void setC_UOM(final I_C_UOM uom)
	{
		values.setC_UOM(uom);

		// NOTE: uom is mandatory
		// we assume orderLine's UOM is correct
		if (uom != null)
		{
			olCand.setPrice_UOM_Internal(uom);
		}
	}

	@Override
	public BigDecimal getQtyTU()
	{
		return olCand.getQtyItemCapacity();
	}

	@Override
	public void setQtyTU(final BigDecimal qtyPacks)
	{
		values.setQtyTU(qtyPacks);
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
		return olCandEffectiveValuesBL.getC_BPartner_Effective(olCand);
	}

	@Override
	public void setC_BPartner(final I_C_BPartner partner)
	{
		olCand.setC_BPartner_Override(partner);
		values.setC_BPartner(partner);
	}

	@Override
	public void setDateOrdered(final Timestamp dateOrdered)
	{
		olCand.setDatePromised_Override(dateOrdered);
		values.setDateOrdered(dateOrdered);
	}

	@Override
	public Timestamp getDateOrdered()
	{
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
		return olCandEffectiveValuesBL.getDatePromised_Effective(olCand);
	}

	@Override
	public int getM_HU_PI_Item_Product_ID()
	{
		final Integer valueOverrideOrValue = InterfaceWrapperHelper.getValueOverrideOrValue(olCand, I_C_OLCand.COLUMNNAME_M_HU_PI_Item_Product_ID);
		return valueOverrideOrValue == null ? 0 : valueOverrideOrValue;
	}

	@Override
	public boolean isInDispute()
	{
		// order line has no IsInDispute flag
		return values.isInDispute();
	}

	@Override
	public void setInDispute(final boolean inDispute)
	{
		values.setInDispute(inDispute);
	}
}
