package de.metas.ui.web.window.descriptor.factory.standard;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;

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

/*package */class SpecialDocumentFieldsCollector
{
	private static final Logger logger = LogManager.getLogger(SpecialDocumentFieldsCollector.class);

	private static final Set<String> COLUMNNAMES_DocumentNos = ImmutableSet.of(WindowConstants.FIELDNAME_DocumentNo, WindowConstants.FIELDNAME_Value, WindowConstants.FIELDNAME_Name);
	private static final Set<String> COLUMNNAMES_DocumentSummary = ImmutableSet.of(WindowConstants.FIELDNAME_DocumentSummary, WindowConstants.FIELDNAME_Name);

	private static final Set<String> COLUMNNAMES = ImmutableSet.<String> builder()
			.addAll(COLUMNNAMES_DocumentNos)
			.addAll(COLUMNNAMES_DocumentSummary)
			.add(WindowConstants.FIELDNAME_DocStatus)
			.add(WindowConstants.FIELDNAME_DocAction)
			.build();

	private final Map<String, DocumentFieldDescriptor.Builder> collectedFields = new HashMap<>();

	public void collect(final DocumentFieldDescriptor.Builder field)
	{
		final String fieldName = field.getFieldName();
		if (!COLUMNNAMES.contains(fieldName))
		{
			return;
		}

		final DocumentFieldDescriptor.Builder fieldAlreadyCollected = collectedFields.get(fieldName);
		if (fieldAlreadyCollected != null)
		{
			logger.warn("Skip collecting {} because we already collected {} for same field name", field, fieldAlreadyCollected);
			return;
		}

		collectedFields.put(fieldName, field);
	}

	public DocumentFieldDescriptor.Builder getDocumentNo()
	{
		for (final String fieldName : COLUMNNAMES_DocumentNos)
		{
			final DocumentFieldDescriptor.Builder field = collectedFields.get(fieldName);
			if (field == null)
			{
				continue;
			}

			field.addCharacteristic(Characteristic.PublicField);
			field.addCharacteristic(Characteristic.SpecialField_DocumentNo);
			return field;
		}

		return null;
	}

	public DocumentFieldDescriptor.Builder getDocumentSummary()
	{
		for (final String fieldName : COLUMNNAMES_DocumentSummary)
		{
			final DocumentFieldDescriptor.Builder field = collectedFields.get(fieldName);
			if (field == null)
			{
				continue;
			}

			field.addCharacteristic(Characteristic.PublicField);
			field.addCharacteristic(Characteristic.SpecialField_DocumentSummary);
			return field;
		}

		return null;
	}

	public Map<Characteristic, DocumentFieldDescriptor.Builder> getDocStatusAndDocAction()
	{
		final DocumentFieldDescriptor.Builder fieldDocStatus = collectedFields.get(WindowConstants.FIELDNAME_DocStatus);
		final DocumentFieldDescriptor.Builder fieldDocAction = collectedFields.get(WindowConstants.FIELDNAME_DocAction);
		if (fieldDocStatus == null || fieldDocAction == null)
		{
			return null;
		}

		fieldDocStatus.addCharacteristic(Characteristic.PublicField);
		fieldDocStatus.addCharacteristic(Characteristic.SpecialField_DocStatus);

		fieldDocAction.addCharacteristic(Characteristic.PublicField);
		fieldDocAction.addCharacteristic(Characteristic.SpecialField_DocAction);

		return ImmutableMap.<Characteristic, DocumentFieldDescriptor.Builder> builder()
				.put(Characteristic.SpecialField_DocStatus, fieldDocStatus)
				.put(Characteristic.SpecialField_DocAction, fieldDocAction)
				.build();
	}
}
