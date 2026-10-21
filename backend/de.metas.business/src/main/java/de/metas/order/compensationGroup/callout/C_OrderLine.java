/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.order.compensationGroup.callout;

import de.metas.i18n.AdMessageKey;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.compensationGroup.calibration.CalibrationRule;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.NoUOMConversionException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.math.BigDecimal;

/**
 * Calibrated order line, in every window: factor -&gt; {@code QtyEntered} (like {@link CalibrationRule#computeQtyCalibrated(Quantity)}),
 * {@code QtyEntered} -&gt; factor, UOM -&gt; uncalibrated qty converted to the new UOM.
 * <p>
 * All methods are in one class on purpose: with {@link Callout.RecursionAvoidanceLevel#CalloutClass} the executor does not re-enter
 * a running instance, which stops the cycle factor -&gt; QtyEntered -&gt; factor. No method skips indirect calls,
 * so a QtyEntered set by another callout (e.g. from the TU qty) still updates the factor.
 */
@Callout(I_C_OrderLine.class)
@Component
public class C_OrderLine
{
	private static final AdMessageKey MSG_FactorMandatory = AdMessageKey.of("C_OrderLine_CalibrationFactorMandatory");
	private static final AdMessageKey MSG_NegativeFactor = AdMessageKey.of("C_CompensationGroup_CalibrationRule_NegativeFactor");

	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final IProgramaticCalloutProvider calloutProvider = Services.get(IProgramaticCalloutProvider.class);

	@PostConstruct
	void postConstruct()
	{
		calloutProvider.registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_OrderLine.COLUMNNAME_GroupCompensationCalibrationFactor, skipIfCopying = true)
	public void onCalibrationFactorChanged(@NonNull final I_C_OrderLine orderLine)
	{
		final BigDecimal qtyUncalibrated = getQtyUncalibratedOrNull(orderLine);
		if (qtyUncalibrated == null)
		{
			return;
		}
		final Percent factor = OrderGroupRepository.getCalibrationFactor(orderLine);
		if (factor == null)
		{
			throw new AdempiereException(MSG_FactorMandatory);
		}
		if (factor.signum() < 0)
		{
			throw new AdempiereException(MSG_NegativeFactor);
		}
		final Quantity qtyCalibrated = Quantitys.of(qtyUncalibrated, UomId.ofRepoId(orderLine.getC_UOM_ID())).multiply(factor);
		orderLine.setQtyEntered(qtyCalibrated.toBigDecimal());
	}

	@CalloutMethod(columnNames = I_C_OrderLine.COLUMNNAME_QtyEntered, skipIfCopying = true)
	public void onQtyEnteredChanged(@NonNull final I_C_OrderLine orderLine)
	{
		final BigDecimal qtyUncalibrated = getQtyUncalibratedOrNull(orderLine);
		// qtyUncalibrated first: windows without the rule/factor fields (e.g. purchase order) have none, and isCalibrated() would read those columns (WARN on every Qty edit)
		if (qtyUncalibrated == null || qtyUncalibrated.signum() <= 0 || !isCalibrated(orderLine))
		{
			return;
		}
		final BigDecimal qtyEntered = getQtyEnteredOrNull(orderLine);
		if (qtyEntered == null || qtyEntered.signum() < 0)
		{
			return;
		}
		OrderGroupRepository.setCalibrationFactorFromQty(orderLine, qtyEntered, qtyUncalibrated);
	}

	/**
	 * Converts the uncalibrated qty as before the changes (value and UOM from the same snapshot, so two UOM edits before a save convert once).
	 * No conversion: {@link NoUOMConversionException}, like {@code CalloutOrder.qty}.
	 * Not on a product change, where {@code CalloutOrder.product} resets the UOM.
	 * Factor and Qty are not recomputed: the UOM is read-only in the order windows.
	 */
	@CalloutMethod(columnNames = I_C_OrderLine.COLUMNNAME_C_UOM_ID, skipIfCopying = true)
	public void onUomChanged(@NonNull final I_C_OrderLine orderLine, @NonNull final ICalloutField field)
	{
		final I_C_OrderLine orderLineOld = field.getModelBeforeChanges(I_C_OrderLine.class);
		final BigDecimal qtyUncalibrated = getQtyUncalibratedOrNull(orderLineOld);
		if (qtyUncalibrated == null)
		{
			return;
		}
		if (orderLineOld.getM_Product_ID() != orderLine.getM_Product_ID())
		{
			return;
		}
		final UomId uomFromId = UomId.ofRepoIdOrNull(orderLineOld.getC_UOM_ID());
		final UomId uomToId = UomId.ofRepoIdOrNull(orderLine.getC_UOM_ID());
		if (uomFromId == null || uomToId == null)
		{
			return;
		}

		orderLine.setGroupCompensationQtyEnteredUncalibrated(uomFromId.equals(uomToId)
				? qtyUncalibrated
				: uomConversionBL.convertQty(ProductId.ofRepoIdOrNull(orderLine.getM_Product_ID()), qtyUncalibrated, uomFromId, uomToId));
	}

	private static boolean isCalibrated(@NonNull final I_C_OrderLine orderLine)
	{
		return orderLine.getC_CompensationGroup_CalibrationRule_ID() > 0 || OrderGroupRepository.getCalibrationFactor(orderLine) != null;
	}

	/**
	 * The generated getter returns 0 for an emptied Qty.
	 */
	@Nullable
	private static BigDecimal getQtyEnteredOrNull(@NonNull final I_C_OrderLine orderLine)
	{
		return InterfaceWrapperHelper.getValueOrNull(orderLine, I_C_OrderLine.COLUMNNAME_QtyEntered);
	}

	/**
	 * The generated getter returns 0 for an uncalibrated line.
	 */
	@Nullable
	private static BigDecimal getQtyUncalibratedOrNull(@NonNull final I_C_OrderLine orderLine)
	{
		return InterfaceWrapperHelper.getValueOrNull(orderLine, I_C_OrderLine.COLUMNNAME_GroupCompensationQtyEnteredUncalibrated);
	}
}
