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

import de.metas.cache.CCache;
import de.metas.i18n.AdMessageKey;
import de.metas.order.compensationGroup.calibration.CalibrationRule;
import de.metas.order.model.I_C_CompensationGroup_Schema_TemplateLine;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Keeps the Kalibrierfaktor ({@code GroupCompensationCalibrationFactor}) and {@code QtyEntered} of a calibrated order line in sync
 * when the user edits one of them in the UI:
 * <ul>
 * <li>factor changed: {@code QtyEntered = uncalibrated qty x factor %}, rounded like at line creation ({@link CalibrationRule#computeQtyCalibrated(Quantity, Percent)})</li>
 * <li>{@code QtyEntered} changed (typed, or set by the Gebindemenge/TU callout): {@code factor = QtyEntered / uncalibrated qty x 100}, rounded half-up to 2 decimals</li>
 * </ul>
 * Both act only on a tab whose factor field is editable ({@code AD_Field.IsReadOnly='N'}). In core window 143 the field is read-only, so nothing changes there;
 * a customer window can make the field editable to enable this.
 * <p>
 * Both methods are in this one callout class on purpose: with the default {@link Callout.RecursionAvoidanceLevel#CalloutClass}
 * they form one callout instance, and the callout executor does not re-enter an instance that is already running.
 * That stops the cycle factor -&gt; QtyEntered -&gt; factor in both directions.
 * Neither method skips indirect calls, so a QtyEntered set by another callout (e.g. from the Gebindemenge) still updates the factor.
 * <p>
 * The rule reference and the uncalibrated qty are never changed here.
 */
@Callout(I_C_OrderLine.class)
@Component
public class C_OrderLine
{
	private static final AdMessageKey MSG_FactorMandatory = AdMessageKey.of("C_OrderLine_CalibrationFactorMandatory");
	private static final AdMessageKey MSG_NegativeFactor = AdMessageKey.of("C_CompensationGroup_CalibrationRule_NegativeFactor");
	private static final AdMessageKey MSG_LineNotCalibrated = AdMessageKey.of("C_OrderLine_CalibrationFactorNotCalibrated");
	private static final AdMessageKey MSG_UOMChanged = AdMessageKey.of("C_OrderLine_CalibrationFactorUOMChanged");

	private static final int FACTOR_SCALE = 2;
	private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, Boolean> factorEditableByTabId = CCache.<Integer, Boolean>builder()
			.tableName(I_AD_Field.Table_Name)
			.additionalTableNameToResetFor(I_AD_Column.Table_Name)
			.build();

	@PostConstruct
	void postConstruct()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_OrderLine.COLUMNNAME_GroupCompensationCalibrationFactor, skipIfCopying = true)
	public void onCalibrationFactorChanged(@NonNull final I_C_OrderLine orderLine, @NonNull final ICalloutField field)
	{
		final BigDecimal factor = getCalibrationFactorOrNull(orderLine);
		final BigDecimal qtyUncalibrated = getQtyUncalibratedOrNull(orderLine);

		// a new line runs every field callout once; an empty factor there is not a user edit
		if (factor == null && (InterfaceWrapperHelper.isNew(orderLine) || qtyUncalibrated == null))
		{
			return;
		}
		if (!isFactorEditable(field))
		{
			return;
		}

		if (factor == null)
		{
			throw new AdempiereException(MSG_FactorMandatory).markAsUserValidationError();
		}
		if (factor.signum() < 0)
		{
			throw new AdempiereException(MSG_NegativeFactor).markAsUserValidationError();
		}
		if (orderLine.getC_CompensationGroup_CalibrationRule_ID() <= 0 || qtyUncalibrated == null)
		{
			throw new AdempiereException(MSG_LineNotCalibrated).markAsUserValidationError();
		}
		if (!isLineUomEqualToTemplateLineUom(orderLine))
		{
			throw new AdempiereException(MSG_UOMChanged).markAsUserValidationError();
		}

		final Quantity base = Quantitys.of(qtyUncalibrated, UomId.ofRepoId(orderLine.getC_UOM_ID()));
		final Quantity qtyCalibrated = CalibrationRule.computeQtyCalibrated(base, Percent.of(factor));
		orderLine.setQtyEntered(qtyCalibrated.toBigDecimal());
	}

	@CalloutMethod(columnNames = I_C_OrderLine.COLUMNNAME_QtyEntered, skipIfCopying = true)
	public void onQtyEnteredChanged(@NonNull final I_C_OrderLine orderLine, @NonNull final ICalloutField field)
	{
		final BigDecimal qtyUncalibrated = getQtyUncalibratedOrNull(orderLine);
		if (qtyUncalibrated == null || qtyUncalibrated.signum() <= 0)
		{
			return;
		}
		// raw value: the generated getter returns 0 for an emptied Qty, which would turn the factor into 0
		final BigDecimal qtyEntered = InterfaceWrapperHelper.getValueOrNull(orderLine, I_C_OrderLine.COLUMNNAME_QtyEntered);
		if (qtyEntered == null || qtyEntered.signum() < 0)
		{
			return;
		}
		if (!isLineUomEqualToTemplateLineUom(orderLine))
		{
			return;
		}
		if (!isFactorEditable(field))
		{
			return;
		}

		final BigDecimal factor = qtyEntered
				.multiply(ONE_HUNDRED)
				.divide(qtyUncalibrated, FACTOR_SCALE, RoundingMode.HALF_UP);
		orderLine.setGroupCompensationCalibrationFactor(factor);
	}

	/**
	 * The generated getter returns 0 for an empty value; an empty factor must stay distinguishable from factor 0.
	 */
	@Nullable
	private static BigDecimal getCalibrationFactorOrNull(@NonNull final I_C_OrderLine orderLine)
	{
		return InterfaceWrapperHelper.getValueOrNull(orderLine, I_C_OrderLine.COLUMNNAME_GroupCompensationCalibrationFactor);
	}

	/**
	 * The generated getter returns 0 for an empty value; an uncalibrated line has none, a calibrated one can have 0.
	 */
	@Nullable
	private static BigDecimal getQtyUncalibratedOrNull(@NonNull final I_C_OrderLine orderLine)
	{
		return InterfaceWrapperHelper.getValueOrNull(orderLine, I_C_OrderLine.COLUMNNAME_GroupCompensationQtyEnteredUncalibrated);
	}

	/**
	 * The uncalibrated qty has no UOM of its own: it is in the UOM of the template line the order line was created from.
	 */
	private static boolean isLineUomEqualToTemplateLineUom(@NonNull final I_C_OrderLine orderLine)
	{
		final int templateLineId = orderLine.getC_CompensationGroup_Schema_TemplateLine_ID();
		if (templateLineId <= 0)
		{
			return false;
		}
		final I_C_CompensationGroup_Schema_TemplateLine templateLine = InterfaceWrapperHelper.load(templateLineId, I_C_CompensationGroup_Schema_TemplateLine.class);
		return templateLine != null
				&& templateLine.getC_UOM_ID() > 0
				&& templateLine.getC_UOM_ID() == orderLine.getC_UOM_ID();
	}

	/**
	 * @return true if the tab the user works in has an editable factor field (e.g. a customer's sales order window); false in core window 143, where it is read-only, and outside the UI
	 */
	private boolean isFactorEditable(@NonNull final ICalloutField field)
	{
		final int adTabId = field.getCalloutRecord() != null ? field.getCalloutRecord().getAD_Tab_ID() : -1;
		if (adTabId <= 0)
		{
			return false;
		}
		return factorEditableByTabId.getOrLoad(adTabId, this::retrieveFactorEditable);
	}

	private boolean retrieveFactorEditable(@Nullable final Integer adTabId)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_AD_Column.class)
				.addEqualsFilter(I_AD_Column.COLUMNNAME_ColumnName, I_C_OrderLine.COLUMNNAME_GroupCompensationCalibrationFactor)
				.andCollectChildren(I_AD_Field.COLUMNNAME_AD_Column_ID, I_AD_Field.class)
				.addEqualsFilter(I_AD_Field.COLUMNNAME_AD_Tab_ID, adTabId)
				.addEqualsFilter(I_AD_Field.COLUMNNAME_IsReadOnly, false)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}
}
