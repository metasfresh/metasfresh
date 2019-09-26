package org.adempiere.mm.attributes.api.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Evaluatee2;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableSet;

import de.metas.i18n.ITranslatableString;
import de.metas.uom.IUOMDAO;
import de.metas.util.NumberUtils;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class AttributeDescriptionPatternEvalCtx implements Evaluatee2
{
	private static final String VAR_Label = "Label";
	private static final String VAR_Value = "Value";
	private static final String VAR_UOM = "UOM";
	private static final ImmutableSet<String> VARS = ImmutableSet.<String> of(
			VAR_Label,
			VAR_Value,
			VAR_UOM);

	private final IAttributesBL attributesBL;
	private final IAttributeDAO attributesRepo;
	private final IUOMDAO uomsRepo;

	private final I_M_Attribute attribute;
	private final Object attributeValue;
	private final AttributeValueId attributeValueId;
	private final String adLanguage;
	private final boolean verboseDescription;

	@lombok.Builder
	private AttributeDescriptionPatternEvalCtx(
			@NonNull final IAttributeDAO attributesRepo,
			@NonNull final IAttributesBL attributesBL,
			@NonNull final IUOMDAO uomsRepo,
			//
			@NonNull final I_M_Attribute attribute,
			@Nullable final Object attributeValue,
			@Nullable final AttributeValueId attributeValueId,
			@NonNull final String adLanguage,
			final boolean verboseDescription)
	{
		this.attributesRepo = attributesRepo;
		this.attributesBL = attributesBL;
		this.uomsRepo = uomsRepo;
		//
		this.attribute = attribute;
		this.attributeValue = attributeValue;
		this.attributeValueId = attributeValueId;
		this.adLanguage = adLanguage;
		this.verboseDescription = verboseDescription;
	}

	@Override
	public String get_ValueAsString(final String variableName)
	{
		if (VAR_Label.equals(variableName))
		{
			return getAttributeLabel();
		}
		else if (VAR_Value.equals(variableName))
		{
			final ITranslatableString valueTrl = getAttributeValueAsDisplayString();
			return valueTrl != null ? valueTrl.translate(adLanguage) : null;
		}
		else if (VAR_UOM.equals(variableName))
		{
			return getAttributeUOM();
		}
		else
		{
			return null;
		}
	}

	@Override
	public boolean has_Variable(final String variableName)
	{
		return VARS.contains(variableName);
	}

	@Override
	public String get_ValueOldAsString(final String variableName)
	{
		return get_ValueAsString(variableName);
	}

	private String getAttributeLabel()
	{
		return attribute.getName();
	}

	private String getAttributeUOM()
	{
		final int uomId = attribute.getC_UOM_ID();
		if (uomId <= 0)
		{
			return null;
		}

		final I_C_UOM uom = uomsRepo.getById(uomId);
		if (uom == null)
		{
			return null;
		}

		return uom.getUOMSymbol();
	}

	private ITranslatableString getAttributeValueAsDisplayString()
	{
		final String attributeValueType = attribute.getAttributeValueType();
		if (X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40.equals(attributeValueType))
		{
			final String valueStr = attributeValue != null ? attributeValue.toString() : null;
			return ASIDescriptionBuilderCommand.formatStringValue(valueStr);
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
		{
			final BigDecimal valueBD = attributeValue != null ? NumberUtils.asBigDecimal(attributeValue, null) : null;
			if (valueBD == null && !verboseDescription)
			{
				return null;
			}
			else
			{
				final int displayType = attributesBL.getNumberDisplayType(attribute);
				return ASIDescriptionBuilderCommand.formatNumber(valueBD, displayType);
			}
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
		{
			final Date valueDate = attributeValue != null ? TimeUtil.asDate(attributeValue) : null;
			if (valueDate == null && !verboseDescription)
			{
				return null;
			}
			else
			{
				return ASIDescriptionBuilderCommand.formatDateValue(valueDate);
			}
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
		{
			final AttributeListValue attributeValue = attributeValueId != null ? attributesRepo.retrieveAttributeValueOrNull(attribute, attributeValueId) : null;
			if (attributeValue != null)
			{
				return ASIDescriptionBuilderCommand.formatStringValue(attributeValue.getName());
			}
			else
			{
				final String valueStr = attributeValue != null ? attributeValue.toString() : null;
				return ASIDescriptionBuilderCommand.formatStringValue(valueStr);
			}
		}
		else
		{
			// Unknown attributeValueType
			final String valueStr = attributeValue != null ? attributeValue.toString() : null;
			return ASIDescriptionBuilderCommand.formatStringValue(valueStr);
		}
	}
}
