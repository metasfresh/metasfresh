package de.metas.material.planning.pporder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Order_BOMLine;

import de.metas.material.planning.exception.MrpException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-material-planning
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@UtilityClass
public class PPOrderUtil
{

	public BigDecimal calculateQtyRequired(final I_PP_Order_BOMLine orderBOMLine, final BigDecimal qtyFinishedGood, final I_C_UOM uomFinishedGood)
	{
		final PPOrderPojoSupplier ppOrderPojoSupplier = new PPOrderPojoSupplier();

		final PPOrderLine ppOrderLinePojo = ppOrderPojoSupplier.of(orderBOMLine);
		final PPOrder ppOrderPojo = ppOrderPojoSupplier.of(orderBOMLine.getPP_Order());

		return calculateQtyRequired(ppOrderLinePojo, ppOrderPojo, qtyFinishedGood, uomFinishedGood.getC_UOM_ID());
	}

	/**
	 * Calculates how much qty is required (standard) for given BOM Line, considering the given quantity of finished goods.
	 *
	 * @param orderBOMLine
	 * @param qtyFinishedGood
	 * @param qtyFinishedGoodUOM
	 * @return standard quantity required to be issued (standard UOM)
	 */
	public BigDecimal calculateQtyRequired(final PPOrderLine orderBOMLine, final PPOrder ppOrder, final BigDecimal qtyFinishedGood)
	{
		final Integer uomId = ppOrder.getUomId();

		return calculateQtyRequired(orderBOMLine, ppOrder, qtyFinishedGood, uomId);
	}

	private BigDecimal calculateQtyRequired(final PPOrderLine orderBOMLine, final PPOrder ppOrder, final BigDecimal qtyFinishedGood, final Integer uomId)
	{
		final BigDecimal multiplier = getQtyMultiplier(orderBOMLine, ppOrder.getProductId(), uomId);

		final BigDecimal qtyRequired;
		if (isComponentType(orderBOMLine.getComponentType(),
				X_PP_Order_BOMLine.COMPONENTTYPE_Component, X_PP_Order_BOMLine.COMPONENTTYPE_Phantom, X_PP_Order_BOMLine.COMPONENTTYPE_Packing, X_PP_Order_BOMLine.COMPONENTTYPE_By_Product, X_PP_Order_BOMLine.COMPONENTTYPE_Co_Product, X_PP_Order_BOMLine.COMPONENTTYPE_Variant))
		{
			qtyRequired = qtyFinishedGood.multiply(multiplier).setScale(8, RoundingMode.UP);
		}
		else if (isComponentType(orderBOMLine.getComponentType(), X_PP_Order_BOMLine.COMPONENTTYPE_Tools))
		{
			qtyRequired = multiplier;
		}
		else
		{
			throw new MrpException("@NotSupported@ @ComponentType@ " + orderBOMLine.getComponentType());
		}

		//
		// Adjust the qtyRequired by adding the scrap percentage to it.
		final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
		final BigDecimal qtyScrap = orderBOMLine.getScrap();
		final BigDecimal qtyRequiredPlusScrap = productBOMBL.calculateQtyWithScrap(qtyRequired, qtyScrap);
		return qtyRequiredPlusScrap;
	}

	/**
	 * Return Unified BOM Qty Multiplier.
	 *
	 * i.e. how much of this component is needed for 1 item of finished good.
	 *
	 * @param orderBOMLine
	 *
	 * @return If is percentage then QtyBatch / 100 will be returned, else QtyBOM.
	 */
	/* package */BigDecimal getQtyMultiplier(@NonNull final PPOrderLine orderBOMLine, @NonNull final PPOrder ppOrder)
	{
		final Integer endProductId = ppOrder.getProductId();
		final Integer endProductUomId = ppOrder.getUomId();

		return getQtyMultiplier(orderBOMLine, endProductId, endProductUomId);
	}

	private BigDecimal getQtyMultiplier(@NonNull final PPOrderLine orderBOMLine,
			@NonNull final Integer endProductId,
			@NonNull final Integer endProductUomId)
	{
		BigDecimal qty;
		if (orderBOMLine.getQtyPercentage())
		{
			qty = orderBOMLine.getQtyBatch().divide(Env.ONEHUNDRED, 8, RoundingMode.HALF_UP);

			//
			// We also need to multiply by BOM UOM to BOM Line UOM multiplier
			// see http://dewiki908/mediawiki/index.php/06973_Fix_percentual_BOM_line_quantities_calculation_%28108941319640%29
			final Properties ctx = Env.getCtx();
			final String trxName = ITrx.TRXNAME_None;

			final I_M_Product bomProduct = InterfaceWrapperHelper.create(ctx, endProductId, I_M_Product.class, trxName);
			final I_C_UOM bomUOM = InterfaceWrapperHelper.create(ctx, endProductUomId, I_C_UOM.class, trxName);

			final I_C_UOM bomLineUOM = InterfaceWrapperHelper.create(ctx, orderBOMLine.getUomId(), I_C_UOM.class, trxName);
			Check.assumeNotNull(bomLineUOM, "bomLineUOM not null");

			final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

			final BigDecimal bomToLineUOMMultiplier = uomConversionBL.convertQty(bomProduct, BigDecimal.ONE, bomUOM, bomLineUOM);

			qty = qty.multiply(bomToLineUOMMultiplier);
		}
		else
		{
			qty = orderBOMLine.getQtyBOM();
		}
		return qty;
	}

	/**
	 *
	 * @param orderBOMLine
	 * @param componentTypes zero or more component types
	 *
	 * @return {@code true} if the given {@code orderBOMLine}'s componentType is any of the given {@code componentTypes}.
	 */
	public boolean isComponentType(@NonNull final I_PP_Order_BOMLine orderBOMLine, @NonNull final String... componentTypes)
	{
		final String currentType = orderBOMLine.getComponentType();
		return isComponentType(currentType, componentTypes);
	}

	private boolean isComponentType(final String currentType, final String... componentTypes)
	{
		for (final String type : componentTypes)
		{
			if (currentType.equals(type))
			{
				return true;
			}
		}
		return false;
	}
}
