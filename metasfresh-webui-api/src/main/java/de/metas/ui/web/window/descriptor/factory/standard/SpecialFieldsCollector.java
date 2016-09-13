package de.metas.ui.web.window.descriptor.factory.standard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.FieldType;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

final class SpecialFieldsCollector
{
	private static final Logger logger = LogManager.getLogger(SpecialFieldsCollector.class);

	private static final Set<String> COLUMNNAMES_DocumentNos = ImmutableSet.of(WindowConstants.FIELDNAME_DocumentNo, WindowConstants.FIELDNAME_Value, WindowConstants.FIELDNAME_Name);
	private static final Set<String> COLUMNNAMES_DocumentSummary = ImmutableSet.of(WindowConstants.FIELDNAME_Name);
	private static final Set<String> COLUMNNAMES = ImmutableSet.<String> builder()
			.addAll(COLUMNNAMES_DocumentNos)
			.addAll(COLUMNNAMES_DocumentSummary)
			.add(WindowConstants.FIELDNAME_DocStatus)
			.add(WindowConstants.FIELDNAME_DocAction)
			.build();

	private final Map<String, DocumentLayoutElementDescriptor.Builder> existingFields = new HashMap<>();

	private final Set<String> fieldNamesCollectedAndConsumed = new HashSet<>();

	public void collect(final DocumentLayoutElementDescriptor.Builder layoutElementBuilder)
	{
		for (final String fieldName : layoutElementBuilder.getFieldNames())
		{
			if (!COLUMNNAMES.contains(fieldName))
			{
				continue;
			}

			final DocumentLayoutElementDescriptor.Builder layoutElementBuilderExisting = existingFields.get(fieldName);
			if (layoutElementBuilderExisting != null)
			{
				logger.warn("Skip collecting {} because we already collected {} for same field name", layoutElementBuilder, layoutElementBuilderExisting);
				continue;
			}

			existingFields.put(fieldName, layoutElementBuilder);
		}
	}

	public DocumentLayoutElementDescriptor buildDocumentNoElementAndConsume()
	{
		for (final String fieldName : COLUMNNAMES_DocumentNos)
		{
			final DocumentLayoutElementDescriptor.Builder elementBuilder = existingFields.get(fieldName);
			if (elementBuilder == null)
			{
				continue;
			}
			if (elementBuilder.isConsumed())
			{
				logger.warn("Skip {} while building documentNo because it's already consumed", elementBuilder);
				continue;
			}

			final DocumentLayoutElementDescriptor element = elementBuilder
					.setLayoutTypeNone() // not relevant
					.build();

			fieldNamesCollectedAndConsumed.add(fieldName);

			return element;
		}

		return null;
	}

	public DocumentLayoutElementDescriptor buildDocumentSummaryElement()
	{
		// TODO: i think we shall introduce some kind of virtual fields which would be able to build the document summary

		for (final String fieldName : COLUMNNAMES_DocumentSummary)
		{
			if (fieldNamesCollectedAndConsumed.contains(fieldName))
			{
				logger.debug("Skip considering {} for DocumentSummary because it was already consumed in this collector (DocumentNo?)", fieldName);
				continue;
			}
			
			final DocumentLayoutElementDescriptor.Builder elementBuilder = existingFields.get(fieldName);
			if (elementBuilder == null)
			{
				continue;
			}

			final DocumentLayoutElementFieldDescriptor.Builder fieldBuilder = elementBuilder.getField(fieldName);
			if (fieldBuilder == null)
			{
				// shall not happen
				logger.warn("Field {} was not found in {}", fieldName, elementBuilder);
				continue;
			}

			return DocumentLayoutElementDescriptor.builder()
					.setCaption(null) // not relevant
					.setDescription(null) // not relevant
					.setLayoutTypeNone() // not relevant
					.setWidgetType(elementBuilder.getWidgetType())
					.addField(fieldBuilder.copy())
					.build();
		}

		return null;
	}

	public DocumentLayoutElementDescriptor buildDocActionElementAndConsume()
	{
		final DocumentLayoutElementFieldDescriptor.Builder docStatusFieldBuilder = getExistingField(WindowConstants.FIELDNAME_DocStatus);
		if (docStatusFieldBuilder == null)
		{
			return null;
		}

		final DocumentLayoutElementFieldDescriptor.Builder docActionFieldBuilder = getExistingField(WindowConstants.FIELDNAME_DocAction);
		if (docActionFieldBuilder == null)
		{
			return null;
		}

		final DocumentLayoutElementDescriptor layoutElement = DocumentLayoutElementDescriptor.builder()
				.setCaption(null) // not relevant
				.setDescription(null) // not relevant
				.setLayoutTypeNone() // not relevant
				.setWidgetType(DocumentFieldWidgetType.ActionButton)
				.addField(docStatusFieldBuilder.setFieldType(FieldType.ActionButtonStatus))
				.addField(docActionFieldBuilder.setFieldType(FieldType.ActionButton))
				.build();

		fieldNamesCollectedAndConsumed.add(WindowConstants.FIELDNAME_DocStatus);
		fieldNamesCollectedAndConsumed.add(WindowConstants.FIELDNAME_DocAction);

		return layoutElement;
	}

	private final DocumentLayoutElementFieldDescriptor.Builder getExistingField(final String fieldName)
	{
		final DocumentLayoutElementDescriptor.Builder elementBuilder = existingFields.get(fieldName);
		if (elementBuilder == null || elementBuilder.isConsumed())
		{
			return null;
		}

		final DocumentLayoutElementFieldDescriptor.Builder fieldBuilder = elementBuilder.getField(fieldName);
		if (fieldBuilder == null || fieldBuilder.isConsumed())
		{
			return null;
		}

		return fieldBuilder;
	}

	public boolean isDocumentNoCollectedAndConsumed(final String fieldName)
	{
		if (!COLUMNNAMES_DocumentNos.contains(fieldName))
		{
			return false;
		}
		return fieldNamesCollectedAndConsumed.contains(fieldName);
	}
}