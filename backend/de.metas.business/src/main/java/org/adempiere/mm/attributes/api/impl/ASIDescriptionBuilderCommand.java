package org.adempiere.mm.attributes.api.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

import javax.annotation.Nullable;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.X_M_Attribute;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

final class ASIDescriptionBuilderCommand
{
	private final IAttributesBL attributesBL = Services.get(IAttributesBL.class);
	private final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

	static final String SEPARATOR = "_";

	private final I_M_AttributeSetInstance asi;
	private final String adLanguage;
	private final boolean verboseDescription;

	//
	private final AttributeSetId attributeSetId;
	private I_M_AttributeSet _attributeSet;

	public ASIDescriptionBuilderCommand(@NonNull final I_M_AttributeSetInstance asi, final boolean verboseDescription)
	{
		this.asi = asi;
		this.adLanguage = Language.getBaseAD_Language();
		this.verboseDescription = verboseDescription;

		attributeSetId = AttributeSetId.ofRepoIdOrNone(asi.getM_AttributeSet_ID());
	}

	public String buildDescription()
	{
		//
		// Guard against null or new ASI
		// In this case it makes no sense to build the Description because there are no attribute instances.
		if (asi == null || InterfaceWrapperHelper.isNew(asi))
		{
			return null;
		}

		final TranslatableStringBuilder descriptionBuilder = TranslatableStrings.builder();

		appendInstanceAttributes(descriptionBuilder);
		appendSerNo(descriptionBuilder);
		appendLot(descriptionBuilder);
		appendGuaranteeDate(descriptionBuilder);
		appendProductAttributes(descriptionBuilder);

		// NOTE: mk: if there is nothing to show then don't show ASI ID because that number will confuse the user.
		// // In case there is no other description, at least show the ID
		// if (sb.length() <= 0 && asi.getM_AttributeSetInstance_ID() > 0)
		// {
		// sb.append(asi.getM_AttributeSetInstance_ID());
		// }

		return descriptionBuilder
				.build()
				.translate(adLanguage);
	}

	private void appendInstanceAttributes(final TranslatableStringBuilder description)
	{
		for (final I_M_AttributeInstance instance : attributesRepo.retrieveAttributeInstances(asi))
		{
			appendInstanceAttribute(description, instance);
		}
	}

	private void appendInstanceAttribute(final TranslatableStringBuilder description, final I_M_AttributeInstance ai)
	{
		final ITranslatableString aiDescription = buildInstanceAttributeDescription(ai);
		if (TranslatableStrings.isBlank(aiDescription))
		{
			return;
		}

		appendSeparator(description);
		description.append(aiDescription);
	}

	private ITranslatableString buildInstanceAttributeDescription(@NonNull final I_M_AttributeInstance ai)
	{
		final AttributeId attributeId = AttributeId.ofRepoId(ai.getM_Attribute_ID());
		final I_M_Attribute attribute = attributesRepo.getAttributeById(attributeId);

		final IStringExpression descriptionPattern = extractDescriptionPattern(attribute);
		if (descriptionPattern == null)
		{
			return getInstanceAttributeValueAsString(ai);
		}
		else
		{
			final Object value = getInstanceAttributeValue(ai);
			final AttributeValueId attributeValueId = AttributeValueId.ofRepoIdOrNull(ai.getM_AttributeValue_ID());

			final AttributeDescriptionPatternEvalCtx ctx = AttributeDescriptionPatternEvalCtx.builder()
					.attributesRepo(attributesRepo)
					.attributesBL(attributesBL)
					.uomsRepo(uomsRepo)
					.attribute(attribute)
					.attributeValue(value)
					.attributeValueId(attributeValueId)
					.adLanguage(adLanguage)
					.verboseDescription(verboseDescription)
					.build();
			final String description = descriptionPattern.evaluate(ctx, OnVariableNotFound.Preserve);
			return TranslatableStrings.anyLanguage(description);
		}
	}

	private IStringExpression extractDescriptionPattern(final I_M_Attribute attribute)
	{
		final String descriptionPatternStr = attribute.getDescriptionPattern();
		if (Check.isEmpty(descriptionPatternStr, true))
		{
			return null;
		}

		return StringExpressionCompiler.instance.compileOrDefault(descriptionPatternStr, null);
	}

	private Object getInstanceAttributeValue(@NonNull final I_M_AttributeInstance ai)
	{
		final AttributeId attributeId = AttributeId.ofRepoId(ai.getM_Attribute_ID());
		final I_M_Attribute attribute = attributesRepo.getAttributeById(attributeId);

		final String attributeValueType = attribute.getAttributeValueType();
		if (X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40.equals(attributeValueType))
		{
			return ai.getValue();
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
		{
			final boolean isNull = InterfaceWrapperHelper.isNull(ai, I_M_AttributeInstance.COLUMNNAME_ValueNumber);
			return isNull ? null : ai.getValueNumber();
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
		{
			return ai.getValueDate();
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
		{
			return ai.getValue();
		}
		else
		{
			return null;
		}
	}

	private ITranslatableString getInstanceAttributeValueAsString(@NonNull final I_M_AttributeInstance ai)
	{
		final AttributeId attributeId = AttributeId.ofRepoId(ai.getM_Attribute_ID());
		final I_M_Attribute attribute = attributesRepo.getAttributeById(attributeId);

		final String attributeValueType = attribute.getAttributeValueType();
		if (X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40.equals(attributeValueType))
		{
			final String valueStr = ai.getValue();
			return formatStringValue(valueStr);
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
		{
			final boolean isNull = InterfaceWrapperHelper.isNull(ai, I_M_AttributeInstance.COLUMNNAME_ValueNumber);
			final BigDecimal valueBD = isNull ? null : ai.getValueNumber();
			if (valueBD == null && !verboseDescription)
			{
				return null;
			}
			else
			{
				final int displayType = attributesBL.getNumberDisplayType(attribute);
				return formatNumber(valueBD, displayType);
			}
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
		{
			final Date valueDate = ai.getValueDate();
			if (valueDate == null && !verboseDescription)
			{
				return null;
			}
			else
			{
				return formatDateValue(valueDate);
			}
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
		{
			final AttributeValueId attributeValueId = AttributeValueId.ofRepoIdOrNull(ai.getM_AttributeValue_ID());
			final AttributeListValue attributeValue = attributeValueId != null ? attributesRepo.retrieveAttributeValueOrNull(attribute, attributeValueId) : null;
			if (attributeValue != null)
			{
				return attributeValue.getNameTrl();
			}
			else
			{
				return formatStringValue(ai.getValue());
			}
		}
		else
		{
			// Unknown attributeValueType
			return formatStringValue(ai.getValue());
		}
	}

	static ITranslatableString formatStringValue(@Nullable final String valueStr)
	{
		if (Check.isEmpty(valueStr, true))
		{
			return TranslatableStrings.empty();
		}
		else
		{
			return TranslatableStrings.anyLanguage(valueStr.trim());
		}
	}

	static ITranslatableString formatNumber(@Nullable final BigDecimal valueBD, final int displayType)
	{
		if (valueBD == null)
		{
			return TranslatableStrings.anyLanguage("0");
		}
		else
		{
			return TranslatableStrings.number(valueBD, displayType);
		}
	}

	static ITranslatableString formatDateValue(@Nullable final java.util.Date valueDate)
	{
		return valueDate != null
				? TranslatableStrings.date(valueDate)
				: TranslatableStrings.anyLanguage("-");
	}

	static ITranslatableString formatDateValue(@Nullable final LocalDate valueDate)
	{
		return valueDate != null
				? TranslatableStrings.date(valueDate)
				: TranslatableStrings.anyLanguage("-");
	}

	private void appendSeparator(final TranslatableStringBuilder description)
	{
		if (description.isEmpty())
		{
			return;
		}

		description.append(SEPARATOR);
	}

	private void appendSerNo(final TranslatableStringBuilder description)
	{
		final I_M_AttributeSet attributeSet = getAttributeSet();
		if (!attributeSet.isSerNo())
		{
			return;
		}

		final String serNo = asi.getSerNo();
		if (serNo == null)
		{
			return;
		}

		final String prefixOverride = attributeSet.getSerNoCharSOverwrite();
		final String prefix = null == prefixOverride || prefixOverride.isEmpty() ? "#" : prefixOverride;

		final String suffixOverride = attributeSet.getSerNoCharEOverwrite();
		final String suffix = null == suffixOverride || suffixOverride.isEmpty() ? "#" : suffixOverride;

		appendSeparator(description);
		description.append(prefix).append(serNo).append(suffix);
	}

	private void appendLot(final TranslatableStringBuilder description)
	{
		final I_M_AttributeSet attributeSet = getAttributeSet();
		if (!attributeSet.isLot())
		{
			return;
		}

		final String lot = asi.getLot();
		if (lot == null)
		{
			return;
		}

		final String prefixOverride = attributeSet.getLotCharSOverwrite();
		final String prefix = null == prefixOverride || attributeSet.getSerNoCharSOverwrite().isEmpty() ? "#" : prefixOverride;

		final String suffixOverride = attributeSet.getLotCharEOverwrite();
		final String suffix = null == suffixOverride || attributeSet.getSerNoCharEOverwrite().isEmpty() ? "#" : suffixOverride;

		appendSeparator(description);
		description.append(prefix).append(lot).append(suffix);
	}

	private void appendGuaranteeDate(final TranslatableStringBuilder description)
	{
		// NOTE: we are not checking if "as.isGuaranteeDate()" because it could be that GuaranteeDate was set even though in attribute set did not mention it (task #09363).
		final Timestamp guaranteeDate = asi.getGuaranteeDate();
		if (guaranteeDate == null)
		{
			return;
		}

		appendSeparator(description);
		description.append(formatDateValue(guaranteeDate));
	}

	private void appendProductAttributes(final TranslatableStringBuilder description)
	{
		final boolean isInstanceAttribute = false;
		for (final I_M_Attribute attribute : attributesRepo.retrieveAttributes(attributeSetId, isInstanceAttribute))
		{
			appendSeparator(description);
			description.append(attribute.getName());
		}
	}

	private I_M_AttributeSet getAttributeSet()
	{
		if (_attributeSet == null)
		{
			_attributeSet = attributesRepo.getAttributeSetById(attributeSetId);
		}
		return _attributeSet;

	}
}
