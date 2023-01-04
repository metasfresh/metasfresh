/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.pricing.rules.price_list_version;

import com.google.common.collect.ImmutableList;
import de.metas.pricing.exceptions.ProductNotOnPriceListException;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.eevolution.api.BOMType;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class BOMPriceCalculator
{
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IProductBOMBL bomsBL = Services.get(IProductBOMBL.class);
	private final IProductBOMDAO bomsRepo = Services.get(IProductBOMDAO.class);
	private final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);

	private final ProductId bomProductId;
	private final IAttributeSetInstanceAware asiAware;
	private final I_M_PriceList_Version priceListVersion;

	@Builder
	private BOMPriceCalculator(
			@NonNull final ProductId bomProductId,
			@Nullable final IAttributeSetInstanceAware asiAware,
			@NonNull final I_M_PriceList_Version priceListVersion)
	{
		this.bomProductId = bomProductId;
		this.asiAware = asiAware;
		this.priceListVersion = priceListVersion;
	}

	public Optional<BOMPrices> calculate()
	{
		return getBOMLines()
				.stream()
				.map(this::calculateBOMLinePrice)
				.reduce(BOMPrices::add);
	}

	private BOMPrices calculateBOMLinePrice(final I_PP_Product_BOMLine bomLine)
	{
		final ProductId bomLineProductId = ProductId.ofRepoId(bomLine.getM_Product_ID());
		final I_M_ProductPrice productPrice = ProductPrices.retrieveMainProductPriceOrNull(priceListVersion, bomLineProductId);
		if (productPrice == null)
		{
			throw ProductNotOnPriceListException.builder()
					.productId(bomLineProductId)
					.build();
		}

		final BigDecimal qty = getBOMQty(bomLine);

		return BOMPrices.builder()
				.priceStd(productPrice.getPriceStd())
				.priceList(productPrice.getPriceList())
				.priceLimit(productPrice.getPriceLimit())
				.build()
				.multiply(qty);
	}

	private BigDecimal getBOMQty(final I_PP_Product_BOMLine bomLine)
	{
		if (bomLine.getQty_Attribute_ID() > 0)
		{
			final AttributeId attributeId = AttributeId.ofRepoId(bomLine.getQty_Attribute_ID());
			final String attributeCode = attributesRepo.getAttributeCodeById(attributeId);

			final BigDecimal qty = getAttributeValueAsBigDecimal(attributeCode, null);
			if (qty != null)
			{
				return qty;
			}
		}

		final BigDecimal qty = bomsBL.computeQtyMultiplier(bomLine, bomProductId);
		return qty;
	}

	private BigDecimal getAttributeValueAsBigDecimal(final String attributeCode, final BigDecimal defaultValue)
	{
		if (asiAware == null)
		{
			return defaultValue;
		}

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNull(asiAware.getM_AttributeSetInstance_ID());
		if (asiId == null)
		{
			return defaultValue;
		}

		final ImmutableAttributeSet asi = attributesRepo.getImmutableAttributeSetById(asiId);
		if (!asi.hasAttribute(attributeCode))
		{
			return defaultValue;
		}

		final BigDecimal value = asi.getValueAsBigDecimal(attributeCode);
		return value != null ? value : defaultValue;
	}

	private List<I_PP_Product_BOMLine> getBOMLines()
	{
		final I_PP_Product_BOM bom = getBOMIfEligible();
		if (bom == null)
		{
			return ImmutableList.of();
		}

		return bomsRepo.retrieveLines(bom);
	}

	private I_PP_Product_BOM getBOMIfEligible()
	{
		final I_M_Product bomProduct = productsRepo.getById(bomProductId);
		if (!bomProduct.isBOM())
		{
			return null;
		}

		return bomsRepo.getDefaultBOMByProductId(bomProductId)
				.filter(this::isEligible)
				.orElse(null);
	}

	private boolean isEligible(final I_PP_Product_BOM bom)
	{
		final BOMType bomType = BOMType.ofNullableCode(bom.getBOMType());
		return BOMType.MakeToOrder.equals(bomType);
	}

	//
	//
	// ---
	//
	//
	public static class BOMPriceCalculatorBuilder
	{
		public Optional<BOMPrices> calculate()
		{
			return build().calculate();
		}
	}
}
