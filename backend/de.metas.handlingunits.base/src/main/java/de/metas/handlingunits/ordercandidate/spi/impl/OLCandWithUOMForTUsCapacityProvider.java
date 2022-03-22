package de.metas.handlingunits.ordercandidate.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.isNull;

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

import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.OLCandHUPackingAware;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandWithUOMForTUsCapacityProvider;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;

@Component
public class OLCandWithUOMForTUsCapacityProvider implements IOLCandWithUOMForTUsCapacityProvider
{
	private static final AdMessageKey MSG_TU_UOM_CAPACITY_REQUIRED = AdMessageKey.of("de.metas.handlingunits.ordercandidate.UOMForTUsCapacityRequired");

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IHUPackingAwareBL huPackingAwareBL = Services.get(IHUPackingAwareBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@Override
	public boolean isProviderNeededForOLCand(@NonNull final I_C_OLCand olCand)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(olCand.getM_Product_ID());
		if (productId == null)
		{
			return false; // nothing to do
		}
		if (!isNull(olCand, I_C_OLCand.COLUMNNAME_QtyItemCapacityInternal))
		{
			return false; // already set; nothing to do
		}
		final UomId uomId = extractUomId(olCand);
		if (uomId == null)
		{
			return false; // nothing to do
		}
		if (!uomDAO.isUOMForTUs(uomId))
		{
			return false; // nothing to do
		}
		return true;
	}

	@NonNull
	@Override
	public Quantity computeQtyItemCapacity(@NonNull final I_C_OLCand olCand)
	{
		final OLCandHUPackingAware huPackingAware = new OLCandHUPackingAware(olCand);
		final Capacity capacity = huPackingAwareBL.calculateCapacity(huPackingAware);
		if (capacity == null)
		{
			final ITranslatableString msg = msgBL
					.getTranslatableMsgText(MSG_TU_UOM_CAPACITY_REQUIRED,
							uomDAO.getX12DE355ById(extractUomId(olCand)),
							olCand.getC_OLCand_ID());
			throw new AdempiereException(msg).markAsUserValidationError();
		}

		final ProductId productId = ProductId.ofRepoId(olCand.getM_Product_ID());
		// note that the product's stocking UOM is never a TU-UOM
		if (capacity.isInfiniteCapacity())
		{
			return Quantity.infinite(capacity.getC_UOM());
		}
		return uomConversionBL.convertToProductUOM(capacity.toQuantity(), productId);
	}

	private UomId extractUomId(@NonNull final I_C_OLCand olCand)
	{
		return UomId.ofRepoIdOrNull(olCand.getC_UOM_ID());
	}
}
