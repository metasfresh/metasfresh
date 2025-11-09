package org.adempiere.mm.attributes.api.impl;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.Attribute;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;

import java.math.BigDecimal;
import java.time.LocalDate;

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

/**
 * @deprecated  pls replace it with org.adempiere.mm.attributes.api.impl.ASIDescriptionBuilderCommand
 */
@Deprecated
public class AttributeSetDescriptionBuilderCommand
{
	private final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

	private final ImmutableAttributeSet attributeSet;
	private final String adLanguage;

	public AttributeSetDescriptionBuilderCommand(
			@NonNull final ImmutableAttributeSet attributeSet,
			@NonNull final String adLanguage)
	{
		this.attributeSet = attributeSet;
		this.adLanguage = adLanguage;
	}

	public ITranslatableString execute()
	{
		final TranslatableStringBuilder description = TranslatableStrings.builder();

		for (final Attribute attribute : attributesRepo.getAttributesByIds(attributeSet.getAttributeIds()))
		{
			final ITranslatableString attributeDescription = buildAttributeDescription(attribute);
			if (TranslatableStrings.isBlank(attributeDescription))
			{
				continue;
			}

			if (!description.isEmpty())
			{
				description.append(ASIDescriptionBuilderCommand.SEPARATOR);
			}

			description.append(attributeDescription);
		}

		return description.build();
	}

	private ITranslatableString buildAttributeDescription(@NonNull final Attribute attribute)
	{
		final IStringExpression descriptionPattern = attribute.getDescriptionPattern();
		if (descriptionPattern == null)
		{
			return getAttributeDisplayValue(attribute);
		}
		else
		{
			final @NonNull AttributeCode attributeCode = attribute.getAttributeCode();

			final AttributeDescriptionPatternEvalCtx ctx = AttributeDescriptionPatternEvalCtx.builder()
					.attributesRepo(attributesRepo)
					.uomsRepo(uomsRepo)
					.attribute(attribute)
					.attributeValue(attributeSet.getValue(attributeCode))
					.attributeValueId(attributeSet.getAttributeValueIdOrNull(attributeCode))
					.adLanguage(adLanguage)
					.verboseDescription(true)
					.build();

			final String description = descriptionPattern.evaluate(ctx, OnVariableNotFound.Preserve);
			return TranslatableStrings.anyLanguage(description);
		}
	}

	private ITranslatableString getAttributeDisplayValue(@NonNull final Attribute attribute)
	{
		final AttributeCode attributeCode = attribute.getAttributeCode();
		final AttributeValueType attributeValueType = attribute.getValueType();
		if (AttributeValueType.STRING.equals(attributeValueType))
		{
			final String valueStr = attributeSet.getValueAsString(attributeCode);
			return ASIDescriptionBuilderCommand.formatStringValue(valueStr);
		}
		else if (AttributeValueType.NUMBER.equals(attributeValueType))
		{
			final BigDecimal valueBD = attributeSet.getValueAsBigDecimal(attributeCode);
			if (valueBD != null)
			{
				return ASIDescriptionBuilderCommand.formatNumber(valueBD, attribute.getNumberDisplayType());
			}
			else
			{
				return null;
			}
		}
		else if (AttributeValueType.DATE.equals(attributeValueType))
		{
			final LocalDate valueDate = attributeSet.getValueAsLocalDate(attributeCode);
			return valueDate != null
					? ASIDescriptionBuilderCommand.formatDateValue(valueDate)
					: null;
		}
		else if (AttributeValueType.LIST.equals(attributeValueType))
		{
			final AttributeValueId attributeValueId = attributeSet.getAttributeValueIdOrNull(attributeCode);
			final AttributeListValue attributeValue = attributeValueId != null
					? attributesRepo.retrieveAttributeValueOrNull(attribute, attributeValueId)
					: null;
			if (attributeValue != null)
			{
				return attributeValue.getNameTrl();
			}
			else
			{
				final String valueStr = attributeSet.getValueAsString(attributeCode);
				return ASIDescriptionBuilderCommand.formatStringValue(valueStr);
			}
		}
		else
		{
			// Unknown attributeValueType
			final String valueStr = attributeSet.getValueAsString(attributeCode);
			return ASIDescriptionBuilderCommand.formatStringValue(valueStr);
		}
	}
}
