package de.metas.handlingunits.model.validator;

import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ProductPrice;
import de.metas.i18n.AdMessageKey;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionsMap;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.compiere.model.ModelValidator;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Model validator <b>with attached callout</b>. See {@link #validateUOM(I_M_ProductPrice)} for what it does.
 */
@Callout(I_M_ProductPrice.class)
@Validator(I_M_ProductPrice.class)
public class M_ProductPrice
{
	private static final AdMessageKey MSG_ERR_M_PRODUCT_PRICE_MISSING_PACKING_ITEM = AdMessageKey.of("MSG_ERR_M_PRODUCT_PRICE_MISSING_PACKING_ITEM");
	private static final AdMessageKey MSG_ERR_M_PRODUCT_PRICE_PACKING_ITEM_INFINITE_CAPACITY = AdMessageKey.of("MSG_ERR_M_PRODUCT_PRICE_PACKING_ITEM_INFINITE_CAPACITY");
	private static final AdMessageKey MSG_ERR_M_PRODUCT_PRICE_NO_UOM_CONVERSION = AdMessageKey.of("MSG_ERR_M_PRODUCT_PRICE_NO_UOM_CONVERSION");

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IHUPIItemProductBL huPIItemProductBL = Services.get(IHUPIItemProductBL.class);
	private final IHUCapacityBL huCapacityBL = Services.get(IHUCapacityBL.class);
	private final IUOMConversionDAO uomConversionDAO = Services.get(IUOMConversionDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_M_ProductPrice.COLUMNNAME_C_UOM_ID, I_M_ProductPrice.COLUMNNAME_M_Product_ID })
	@CalloutMethod(columnNames = { I_M_ProductPrice.COLUMNNAME_C_UOM_ID, I_M_ProductPrice.COLUMNNAME_M_Product_ID })
	public void validateUOM(final I_M_ProductPrice productPrice)
	{
		if (productPrice.getM_Product_ID() <= 0)
		{
			return; // nothing to do yet
		}
		final UomId uomId = UomId.ofRepoId(productPrice.getC_UOM_ID());

		if (uomDAO.isUOMForTUs(uomId))
		{
			validateTuUom(productPrice);
			productPrice.setIsHUPrice(true);
		}
		else
		{
			productPrice.setIsHUPrice(false);
			validatePriceUOM(productPrice);
		}

	}

	private void validateTuUom(@NonNull final I_M_ProductPrice productPrice)
	{
		final HUPIItemProductId packingMaterialPriceId = HUPIItemProductId.ofRepoIdOrNull(productPrice.getM_HU_PI_Item_Product_ID());

		if (packingMaterialPriceId == null)
		{
			final I_C_UOM priceUom = uomDAO.getById(productPrice.getC_UOM_ID());

			throw new AdempiereException(MSG_ERR_M_PRODUCT_PRICE_MISSING_PACKING_ITEM, priceUom.getName())
					.markAsUserValidationError();
		}

		final I_M_HU_PI_Item_Product packingMaterial = huPIItemProductBL.getRecordById(packingMaterialPriceId);

		if (huCapacityBL.isInfiniteCapacity(packingMaterial))
		{
			final Object errorParameter = CoalesceUtil.coalesceNotNull(packingMaterial.getName(), packingMaterial.getM_HU_PI_Item_Product_ID());
			throw new AdempiereException(MSG_ERR_M_PRODUCT_PRICE_PACKING_ITEM_INFINITE_CAPACITY, errorParameter)
					.markAsUserValidationError();
		}
	}

	private void validatePriceUOM(@NonNull final I_M_ProductPrice productPrice)
	{
		final UomId productPriceUOMId = UomId.ofRepoId(productPrice.getC_UOM_ID());

		final UOMConversionsMap uomConversionsMap = uomConversionDAO.getProductConversions(ProductId.ofRepoId(productPrice.getM_Product_ID()));

		final UomId productUomId = productBL.getStockUOMId(productPrice.getM_Product_ID());

		uomConversionsMap.getRateIfExists(productUomId, productPriceUOMId)
				.orElseThrow(() -> new AdempiereException(MSG_ERR_M_PRODUCT_PRICE_NO_UOM_CONVERSION)
						.markAsUserValidationError());
	}
}
