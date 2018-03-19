package de.metas.material.planning.pporder;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.X_PP_Order_BOMLine;

import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.planning.exception.BOMExpiredException;
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

	/**
	 * Calculates how much qty is required (standard) for given BOM Line, considering the given quantity of finished goods.
	 *
	 * @param orderBOMLine
	 * @param qtyFinishedGood
	 * @return standard quantity required to be issued (standard UOM)
	 */
	public BigDecimal calculateQtyRequired(
			@NonNull final PPOrderLine ppOrderLinePojo,
			@NonNull final PPOrder ppOrderPojo,
			@NonNull final BigDecimal qtyFinishedGood)
	{
		final BigDecimal multiplier = getQtyMultiplier(ppOrderLinePojo, ppOrderPojo.getProductDescriptor().getProductId());

		final I_PP_Product_BOMLine productBomLine = getProductBomLine(ppOrderLinePojo);

		final BigDecimal qtyRequired;
		if (isComponentTypeOneOf(productBomLine.getComponentType(),
				X_PP_Order_BOMLine.COMPONENTTYPE_Component, X_PP_Order_BOMLine.COMPONENTTYPE_Phantom, X_PP_Order_BOMLine.COMPONENTTYPE_Packing, X_PP_Order_BOMLine.COMPONENTTYPE_By_Product, X_PP_Order_BOMLine.COMPONENTTYPE_Co_Product, X_PP_Order_BOMLine.COMPONENTTYPE_Variant))
		{
			qtyRequired = qtyFinishedGood.multiply(multiplier).setScale(8, RoundingMode.UP);
		}
		else if (isComponentTypeOneOf(productBomLine.getComponentType(), X_PP_Order_BOMLine.COMPONENTTYPE_Tools))
		{
			qtyRequired = multiplier;
		}
		else
		{
			throw new MrpException("@NotSupported@ @ComponentType@ " + productBomLine.getComponentType());
		}

		//
		// Adjust the qtyRequired by adding the scrap percentage to it.
		final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
		final BigDecimal qtyScrap = productBomLine.getScrap();
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
		final Integer endProductId = ppOrder.getProductDescriptor().getProductId();

		return getQtyMultiplier(orderBOMLine, endProductId);
	}

	private BigDecimal getQtyMultiplier(
			@NonNull final PPOrderLine orderBOMLine,
			@NonNull final Integer endProductId)
	{

		final I_PP_Product_BOMLine productBomLine = getProductBomLine(orderBOMLine);
		if (!productBomLine.isQtyPercentage())
		{
			return productBomLine.getQtyBOM();
		}

		// We also need to multiply by BOM UOM to BOM Line UOM multiplier
		// see http://dewiki908/mediawiki/index.php/06973_Fix_percentual_BOM_line_quantities_calculation_%28108941319640%29
		final I_M_Product endProduct = load(endProductId, I_M_Product.class);
		final I_C_UOM endUOM = endProduct.getC_UOM();

		final I_C_UOM bomLineUOM = productBomLine.getC_UOM();
		Check.assumeNotNull(bomLineUOM, "bomLineUOM not null");

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final BigDecimal bomToLineUOMMultiplier = uomConversionBL.convertQty(endProduct, BigDecimal.ONE, endUOM, bomLineUOM);

		final BigDecimal qtyBatchPercent = productBomLine.getQtyBatch();
		final BigDecimal qtyBatchAbsolute = qtyBatchPercent.divide(Env.ONEHUNDRED, 8, RoundingMode.HALF_UP);

		return qtyBatchAbsolute.multiply(bomToLineUOMMultiplier);
	}

	/**
	 *
	 * @param orderBOMLine
	 * @param componentTypes zero or more component types
	 *
	 * @return {@code true} if the given {@code orderBOMLine}'s componentType is any of the given {@code componentTypes}.
	 */
	public boolean isComponentTypeOneOf(@NonNull final I_PP_Order_BOMLine orderBOMLine, @NonNull final String... componentTypes)
	{
		final String currentType = orderBOMLine.getComponentType();
		return isComponentTypeOneOf(currentType, componentTypes);
	}

	public boolean isCoProduct(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		return isComponentTypeOneOf(bomLine, X_PP_Order_BOMLine.COMPONENTTYPE_Co_Product);
	}

	public boolean isByProduct(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		Check.assumeNotNull(bomLine, "bomLine not null");
		final String componentType = bomLine.getComponentType();
		return isByProduct(componentType);
	}

	/**
	 *
	 * @param currentType may be {@code null}. In that case, the method returns {@code false}.
	 * @param componentTypes
	 * @return
	 */
	public boolean isComponentTypeOneOf(final String currentType, @NonNull final String... componentTypes)
	{
		if (currentType == null)
		{
			return false;
		}

		for (final String type : componentTypes)
		{
			if (currentType.equals(type))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * @param componentType may be {@code null}. In that case, the method returns {@code false}.
	 * @return
	 */
	public boolean isByProduct(final String componentType)
	{
		return X_PP_Order_BOMLine.COMPONENTTYPE_By_Product.equals(componentType);
	}

	/**
	 *
	 * @param componentType may be {@code null}. In that case, the method returns {@code false}.
	 * @return true if given {@code componentType} is for receiving materials from manufacturing order (i.e. ComponentType is Co/By-Product) and not for issuing
	 */
	public boolean isReceipt(final String componentType)
	{
		return isComponentTypeOneOf(componentType, X_PP_Order_BOMLine.COMPONENTTYPE_By_Product, X_PP_Order_BOMLine.COMPONENTTYPE_Co_Product);
	}

	/**
	 *
	 * @param componentType may be {@code null}. In that case, the method returns {@code false}.
	 * @return
	 */
	public boolean isIssue(final String componentType)
	{
		return !isReceipt(componentType);
	}

	public boolean isCoOrByProduct(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		return isComponentTypeOneOf(bomLine, X_PP_Order_BOMLine.COMPONENTTYPE_By_Product, X_PP_Order_BOMLine.COMPONENTTYPE_Co_Product);
	}

	/**
	 *
	 * @param bomLine
	 * @return true if given BOM Line is a alternative/variant for a main component line
	 */
	public boolean isVariant(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		return isComponentTypeOneOf(bomLine, X_PP_Order_BOMLine.COMPONENTTYPE_Variant);
	}

	public boolean isReceipt(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		return isReceipt(bomLine.getComponentType());
	}

	/**
	 * Asserts given <code>bomLine</code> is receipt.
	 *
	 * @param bomLine
	 * @see #isReceipt(I_PP_Order_BOMLine)
	 * @throws MrpException if BOM Line is not of type receipt (see {@link #isReceipt(I_PP_Order_BOMLine)}).
	 */
	public void assertReceipt(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		Check.assume(isReceipt(bomLine), MrpException.class, "BOM Line shall be of type receipt: {}", bomLine);
	}

	public void assertIssue(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		Check.assume(!isReceipt(bomLine), MrpException.class, "BOM Line shall be of type issue: {}", bomLine);
	}

	public boolean isComponent(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		return isComponentTypeOneOf(bomLine, X_PP_Order_BOMLine.COMPONENTTYPE_Component, X_PP_Order_BOMLine.COMPONENTTYPE_Packing);
	}

	public I_PP_Product_BOMLine getProductBomLine(@NonNull final PPOrderLine ppOrderLinePojo)
	{
		return InterfaceWrapperHelper.create(Env.getCtx(), ppOrderLinePojo.getProductBomLineId(), I_PP_Product_BOMLine.class, ITrx.TRXNAME_None);
	}

	/**
	 * Verifies that the given three parameters fit together.
	 *
	 * @param ppOrderProductId the {@code M_Product_ID} of a given ppOrder
	 * @param ppOrderStartSchedule the {@code M_Product_ID} of a given ppOrder
	 * @param ppOrderProductBOM the {@code DateStartSchedule} of a given ppOrder
	 * @return
	 */
	public I_PP_Product_BOM verifyProductBOM(
			@NonNull final Integer ppOrderProductId,
			@NonNull final Date ppOrderStartSchedule,
			@NonNull final I_PP_Product_BOM ppOrderProductBOM)
	{
		// Product from Order should be same as product from BOM - teo_sarca [ 2817870 ]
		if (ppOrderProductId != ppOrderProductBOM.getM_Product_ID())
		{
			throw new MrpException("@NotMatch@ @PP_Product_BOM_ID@ , @M_Product_ID@");
		}

		// Product BOM Configuration should be verified - teo_sarca [ 2817870 ]
		final I_M_Product product = ppOrderProductBOM.getM_Product();
		if (!product.isVerified())
		{
			throw new MrpException(
					StringUtils.formatMessage(
							"Product with Value={} of Product BOM Configuration not verified; PP_Product_BOM={}; product={}",
							product.getValue(), ppOrderProductBOM, product));
		}

		//
		// Create BOM Head
		final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
		if (!productBOMBL.isValidFromTo(ppOrderProductBOM, ppOrderStartSchedule))
		{
			throw new BOMExpiredException(ppOrderProductBOM, ppOrderStartSchedule);
		}

		return ppOrderProductBOM;
	}
}
