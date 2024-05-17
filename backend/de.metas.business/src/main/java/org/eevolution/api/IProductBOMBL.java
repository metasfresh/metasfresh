package org.eevolution.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import de.metas.material.event.commons.ProductDescriptor;
import de.metas.product.IssuingToleranceSpec;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.ISingletonService;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.eevolution.api.impl.ProductBOM;
import org.eevolution.api.impl.ProductBOMRequest;
import org.compiere.model.I_M_Product;
import org.eevolution.api.impl.ProductBOM;
import org.eevolution.api.impl.ProductBOMRequest;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IProductBOMBL extends ISingletonService
{
	boolean isValidFromTo(I_PP_Product_BOM productBOM, Date date);

	boolean isValidFromTo(I_PP_Product_BOMLine bomLine, Date date);

	void updateIsBOMFlag(ProductId productId);

	/**
	 * checking BOM cycles.
	 */
	void checkCycles(final ProductId productId);

	/**
	 * Checks if a BOMLine which is a <code>X_PP_Product_BOMLine.COMPONENTTYPE_Variant</code> has a valid VariantGroup<br>
	 *
	 * Valid variant group means that exists at least one other BOMLine which has Component Type <code>X_PP_Order_BOMLine.COMPONENTTYPE_Component</code>
	 * or <code>X_PP_Order_BOMLine.COMPONENTTYPE_Packing</code> and same VariantGroup.
	 */
	boolean isValidVariantGroup(I_PP_Product_BOMLine bomLine);

	/**
	 * @return how much of bom line's product we need to produce an amount of <code>finishedGoodQty</code>
	 */
	BigDecimal computeQtyRequired(I_PP_Product_BOMLine bomLine, ProductId finishedGoodProductId, BigDecimal finishedGoodQty);

	/**
	 * Return Unified BOM Qty Multiplier.
	 *
	 * i.e. how much of this component is needed for 1 item of finished good.
	 *
	 * @return If is percentage then QtyBatch / 100 will be returned, else QtyBOM.
	 */
	BigDecimal computeQtyMultiplier(I_PP_Product_BOMLine bomLine, ProductId finishedGoodProductId);

	@Nullable
	String getBOMDescriptionForProductId(ProductId productId);

	Quantity getQtyIncludingScrap(I_PP_Product_BOMLine bomLine);

	Quantity getQtyExcludingScrap(I_PP_Product_BOMLine bomLine);

	Percent getCoProductCostDistributionPercent(I_PP_Product_BOMLine bomLine);

	List<QtyCalculationsBOM> getQtyCalculationBOMs(
			@NonNull Set<ProductId> finishGoodIds,
			@NonNull BOMType bomType);

	QtyCalculationsBOMLine toQtyCalculationsBOMLine(
			@NonNull I_PP_Product_BOMLine productBOMLine,
			@NonNull I_PP_Product_BOM bom);

	Optional<IssuingToleranceSpec> getEffectiveIssuingToleranceSpec(@NonNull I_PP_Product_BOMLine bomLine);

	void verifyDefaultBOMProduct(@NonNull ProductId productId);

	void verifyDefaultBOMProduct(@NonNull I_M_Product product);

	Optional<ProductBOM> retrieveValidProductBOM(@NonNull ProductBOMRequest request);

	Map<ProductDescriptor, Quantity> calculateRequiredQtyInStockUOMForComponents(@NonNull Quantity qty, @NonNull ProductBOM productBOM);
}
