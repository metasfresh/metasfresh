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

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.model.I_C_OLCand;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getValueOverrideOrValue;

/**
 * Wraps an {@link I_C_OLCand} and makes it behave like an {@link IHUPackingAware}.
 *
 * @author tsa
 */
public class OLCandHUPackingAware implements IHUPackingAware
{
	private final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
	
	private final I_C_OLCand olCand;

	/**
	 * Plain delegate for fields which are not available in order line
	 */
	private final PlainHUPackingAware values = new PlainHUPackingAware();

	public OLCandHUPackingAware(@NonNull final de.metas.ordercandidate.model.I_C_OLCand olCand)
	{
		this.olCand = create(olCand, I_C_OLCand.class);
	}

	@Override
	public int getM_Product_ID()
	{
		return ProductId.toRepoId(olCandEffectiveValuesBL.getM_Product_Effective_ID(olCand));
	}

	@Override
	public void setM_Product_ID(final int productId)
	{
		olCand.setM_Product_Override_ID(productId);
		values.setProductId(ProductId.ofRepoIdOrNull(productId));
	}

	@Override
	public void setQty(final BigDecimal qty)
	{
		olCand.setQtyEntered(qty);
		values.setQty(qty);
	}

	@Override
	public BigDecimal getQty()
	{
		return olCand.getQtyEntered();
	}

	@Override
	public int getM_HU_PI_Item_Product_ID()
	{
		final Integer valueOverrideOrValue = getValueOverrideOrValue(olCand, I_C_OLCand.COLUMNNAME_M_HU_PI_Item_Product_ID);
		return valueOverrideOrValue == null ? 0 : valueOverrideOrValue;
	}

	@Override
	public void setM_HU_PI_Item_Product_ID(final int huPiItemProductId)
	{
		olCand.setM_HU_PI_Item_Product_Override_ID(huPiItemProductId);
		values.setM_HU_PI_Item_Product_ID(huPiItemProductId);
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
	public int getC_UOM_ID()
	{
		return olCandEffectiveValuesBL.getEffectiveUomId(olCand).getRepoId();
	}

	@Override
	public void setC_UOM_ID(final int uomId)
	{
		values.setUomId(UomId.ofRepoIdOrNull(uomId));

		// NOTE: uom is mandatory
		// we assume orderLine's UOM is correct
		if (uomId > 0)
		{
			olCand.setPrice_UOM_Internal_ID(uomId);
		}
	}

	@Override
	public BigDecimal getQtyTU()
	{
		return Quantitys.toBigDecimalOrNull(olCandEffectiveValuesBL.getQtyItemCapacity_Effective(olCand));
	}

	@Override
	public void setQtyTU(final BigDecimal qtyPacks)
	{
		values.setQtyTU(qtyPacks);
	}

	@Override
	public int getC_BPartner_ID()
	{
		final BPartnerId bpartnerId = olCandEffectiveValuesBL.getBPartnerEffectiveId(olCand);
		return BPartnerId.toRepoId(bpartnerId);
	}

	@Override
	public void setC_BPartner_ID(final int partnerId)
	{
		olCand.setC_BPartner_Override_ID(partnerId);
		values.setBpartnerId(BPartnerId.ofRepoIdOrNull(partnerId));
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
