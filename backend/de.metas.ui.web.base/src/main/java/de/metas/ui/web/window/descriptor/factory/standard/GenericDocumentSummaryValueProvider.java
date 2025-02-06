package de.metas.ui.web.window.descriptor.factory.standard;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.ad_reference.ADRefTable;
import de.metas.ad_reference.ADReferenceService;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.json.DateTimeConverters;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.IDocumentFieldValueProvider;
import de.metas.util.Services;
import lombok.Data;
import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.impl.LookupDisplayColumn;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * #%L
 * metasfresh-webui-api
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

public class GenericDocumentSummaryValueProvider implements IDocumentFieldValueProvider
{
	public static GenericDocumentSummaryValueProvider of(final DocumentEntityDescriptor.Builder entityDescriptor)
	{
		List<FieldValueExtractor> fieldValuesExtractors = extractFieldNamesFromLookup(entityDescriptor);
		if (fieldValuesExtractors != null && !fieldValuesExtractors.isEmpty())
		{
			return new GenericDocumentSummaryValueProvider(fieldValuesExtractors);
		}

		fieldValuesExtractors = extractFieldNamesFromDocumentNo(entityDescriptor);
		if (fieldValuesExtractors != null && !fieldValuesExtractors.isEmpty())
		{
			return new GenericDocumentSummaryValueProvider(fieldValuesExtractors);
		}

		fieldValuesExtractors = extractFieldNamesFromValueName(entityDescriptor);
		if (fieldValuesExtractors != null && !fieldValuesExtractors.isEmpty())
		{
			return new GenericDocumentSummaryValueProvider(fieldValuesExtractors);
		}

		return EMPTY;
	}

	private static List<FieldValueExtractor> extractFieldNamesFromLookup(final DocumentEntityDescriptor.Builder entityDescriptor)
	{
		try
		{
			final DocumentFieldDescriptor.Builder idField = entityDescriptor.getSingleIdFieldBuilderOrNull();
			if (idField == null || idField.isVirtualField())
			{
				return ImmutableList.of();
			}

			final ADReferenceService adReferenceService = ADReferenceService.get();
			final ADRefTable tableRefInfo = adReferenceService.getTableDirectRefInfo(idField.getFieldName());

			final ILookupDAO lookupDAO = Services.get(ILookupDAO.class);
			return lookupDAO.getLookupDisplayInfo(tableRefInfo)
					.getLookupDisplayColumns()
					.stream()
					.map(lookupDisplayColumn -> createFieldValueExtractorFromLookupDisplayColumn(lookupDisplayColumn, entityDescriptor))
					.filter(Objects::nonNull)
					.collect(ImmutableList.toImmutableList());
		}
		catch (final Exception ex)
		{
			logger.warn("Failed extracting summary field names for record's lookup for {}. Ignored.", entityDescriptor, ex);
			return ImmutableList.of();
		}
	}

	private static FieldValueExtractor createFieldValueExtractorFromLookupDisplayColumn(final LookupDisplayColumn lookupDisplayColumn, final DocumentEntityDescriptor.Builder entityDescriptor)
	{
		final String fieldName = lookupDisplayColumn.getColumnName();
		final DocumentFieldDescriptor.Builder field = entityDescriptor.getFieldBuilder(fieldName);
		if (field == null)
		{
			return null;
		}

		final DocumentFieldWidgetType widgetType = field.getWidgetType();
		if (widgetType.isDateOrTime())
		{
			return new DateFieldValueExtractor(fieldName, widgetType);
		}
		else if (widgetType.isNumeric())
		{
			return new NumericFieldValueExtractor(fieldName, widgetType, lookupDisplayColumn.getFormatPattern());
		}

		return new GenericFieldValueExtractor(fieldName);
	}

	private static List<FieldValueExtractor> extractFieldNamesFromDocumentNo(final DocumentEntityDescriptor.Builder entityDescriptor)
	{
		if (entityDescriptor.hasField(WindowConstants.FIELDNAME_DocumentNo))
		{
			return ImmutableList.of(new GenericFieldValueExtractor(WindowConstants.FIELDNAME_DocumentNo));
		}
		return ImmutableList.of();
	}

	private static List<FieldValueExtractor> extractFieldNamesFromValueName(final DocumentEntityDescriptor.Builder entityDescriptor)
	{
		final ImmutableList.Builder<FieldValueExtractor> fieldNames = ImmutableList.builder();
		if (entityDescriptor.hasField(WindowConstants.FIELDNAME_Value))
		{
			fieldNames.add(new GenericFieldValueExtractor(WindowConstants.FIELDNAME_Value));
		}
		if (entityDescriptor.hasField(WindowConstants.FIELDNAME_Name))
		{
			fieldNames.add(new GenericFieldValueExtractor(WindowConstants.FIELDNAME_Name));
		}

		return fieldNames.build();
	}

	private static final transient Logger logger = LogManager.getLogger(GenericDocumentSummaryValueProvider.class);
	private static final GenericDocumentSummaryValueProvider EMPTY = new GenericDocumentSummaryValueProvider(ImmutableList.of());

	private final ImmutableList<FieldValueExtractor> fieldValueExtractors;
	private final ImmutableSet<String> fieldNames;

	private GenericDocumentSummaryValueProvider(final List<FieldValueExtractor> fieldValueExtractors)
	{
		this.fieldValueExtractors = ImmutableList.copyOf(fieldValueExtractors);
		fieldNames = this.fieldValueExtractors.stream().map(FieldValueExtractor::getFieldName).collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(fieldValueExtractors)
				.toString();
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return fieldNames;
	}

	@Override
	public Object calculateValue(final Document document)
	{
		if (fieldValueExtractors.isEmpty())
		{
			return null;
		}

		final String summary = fieldValueExtractors.stream()
				.map(valueExtractor -> valueExtractor.extractFieldValueToString(document))
				.map(fieldValue -> Check.isEmpty(fieldValue, true) ? null : fieldValue.trim()) // convert empty strings to null
				.filter(fieldValue -> fieldValue != null) // skip null strings
				.collect(Collectors.joining(" ")); // join all field values

		if (Check.isEmpty(summary, true))
		{
			return "";
		}

		return summary;
		// return " / " + summary; // don't prefix with "/". That shall be done by frontend if and when needed
	}

	private interface FieldValueExtractor
	{
		String getFieldName();

		String extractFieldValueToString(final Document document);
	}

	@Data
	private static final class GenericFieldValueExtractor implements FieldValueExtractor
	{
		private final String fieldName;

		@Override
		public String extractFieldValueToString(final Document document)
		{
			final Object fieldValue = document.getFieldView(fieldName).getValue();
			if (fieldValue == null)
			{
				return null;
			}
			else if (fieldValue instanceof LookupValue)
			{
				return ((LookupValue)fieldValue).getDisplayName();
			}
			else
			{
				return fieldValue.toString();
			}
		}
	}

	@Data
	private static final class DateFieldValueExtractor implements FieldValueExtractor
	{
		private final String fieldName;
		private final DocumentFieldWidgetType widgetType;

		@Override
		public String extractFieldValueToString(final Document document)
		{
			final Object fieldValue = document.getFieldView(fieldName).getValue();
			if (fieldValue == null)
			{
				return null;
			}

			try
			{
				final java.util.Date date = TimeUtil.asDate(DateTimeConverters.fromObject(fieldValue, widgetType));
				return DisplayType.getDateFormat(widgetType.getDisplayType())
						.format(date);
			}
			catch (final Exception ex)
			{
				logger.warn("Failed formatting date field value '{}' ({}) using {}. Returning toString().", fieldValue, fieldValue.getClass(), this, ex);
				return fieldValue.toString();
			}
		}
	}

	private static final class NumericFieldValueExtractor implements FieldValueExtractor
	{
		private final String fieldName;
		private final String formatPattern;
		private int displayType;

		private NumericFieldValueExtractor(final String fieldName, final DocumentFieldWidgetType widgetType, final String formatPattern)
		{
			this.fieldName = fieldName;
			this.formatPattern = Check.isEmpty(formatPattern, true) ? null : formatPattern;

			if (widgetType == DocumentFieldWidgetType.Integer)
			{
				displayType = DisplayType.Integer;
			}
			else if (widgetType == DocumentFieldWidgetType.Number)
			{
				displayType = DisplayType.Number;
			}
			else if (widgetType == DocumentFieldWidgetType.Amount)
			{
				displayType = DisplayType.Amount;
			}
			else if (widgetType == DocumentFieldWidgetType.Quantity)
			{
				displayType = DisplayType.Quantity;
			}
			else if (widgetType == DocumentFieldWidgetType.CostPrice)
			{
				displayType = DisplayType.CostPrice;
			}
			else
			{
				// shall not happen
				displayType = DisplayType.Number;
			}
		}

		@Override
		public String getFieldName()
		{
			return fieldName;
		}

		@Override
		public String extractFieldValueToString(final Document document)
		{
			final Object fieldValue = document.getFieldView(fieldName).getValue();
			if (fieldValue == null)
			{
				return null;
			}

			try
			{
				return getDecimalFormat().format(fieldValue);
			}
			catch (final Exception ex)
			{
				logger.warn("Failed formatting date field value '{}' using {}. Returning toString().", fieldValue, this, ex);
				return fieldValue.toString();
			}
		}

		private DecimalFormat getDecimalFormat()
		{
			final Language language = Env.getLanguage(Env.getCtx());
			return DisplayType.getNumberFormat(displayType, language, formatPattern);
		}
	}

}
