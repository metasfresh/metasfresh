package de.metas.ui.web.window.model;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;

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

public final class DocumentChanges
{
	private final DocumentPath documentPath;
	private final Map<String, DocumentFieldChange> fieldChangesByName = new LinkedHashMap<>();
	private DocumentValidStatus documentValidStatus = null;
	private DocumentSaveStatus documentSaveStatus = null;
	private final Set<DetailId> staleDetailIds = new HashSet<>();

	/* package */ DocumentChanges(final DocumentPath documentPath)
	{
		super();

		Preconditions.checkNotNull(documentPath, "documentPath");
		this.documentPath = documentPath;
	}

	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("documentPath", documentPath)
				.add("fields", fieldChangesByName.isEmpty() ? null : fieldChangesByName)
				.add("staleDetailIds", staleDetailIds.isEmpty() ? null : staleDetailIds)
				.toString();
	}

	public Set<String> getFieldNames()
	{
		return ImmutableSet.copyOf(fieldChangesByName.keySet());
	}

	public boolean isEmpty()
	{
		return fieldChangesByName.isEmpty()
				&& documentValidStatus == null
				&& documentSaveStatus == null
				&& staleDetailIds.isEmpty();
	}

	private DocumentFieldChange fieldChangesOf(final IDocumentFieldView documentField)
	{
		// Make sure the field is about same document path
		if (!documentPath.equals(documentField.getDocumentPath()))
		{
			throw new IllegalArgumentException("Field " + documentField + " does not have expected path: " + documentPath);
		}

		return fieldChangesByName.computeIfAbsent(documentField.getFieldName(), (fieldName) -> {
			final DocumentFieldChange event = DocumentFieldChange.of(fieldName, documentField.isKey(), documentField.isPublicField(), documentField.isAdvancedField(), documentField.getWidgetType());
			if (WindowConstants.isProtocolDebugging())
			{
				event.putDebugProperty(DocumentFieldChange.DEBUGPROPERTY_FieldInfo, documentField.toString());
			}
			return event;
		});
	}

	private DocumentFieldChange fieldChangesOf(final String fieldName, final boolean key, final boolean publicField, final boolean advancedField, final DocumentFieldWidgetType widgetType)
	{
		return fieldChangesByName.computeIfAbsent(fieldName, (newFieldName) -> DocumentFieldChange.of(newFieldName, key, publicField, advancedField, widgetType));
	}

	public List<DocumentFieldChange> getFieldChangesList()
	{
		return ImmutableList.copyOf(fieldChangesByName.values());
	}

	/* package */ void collectValueChanged(final IDocumentFieldView documentField, final ReasonSupplier reason)
	{
		fieldChangesOf(documentField).setValue(documentField.getValue(), reason);
	}

	/* package */ void collectReadonlyChanged(final IDocumentFieldView documentField, final ReasonSupplier reason)
	{
		fieldChangesOf(documentField).setReadonly(documentField.getReadonly(), reason);
	}

	/* package */ void collectMandatoryChanged(final IDocumentFieldView documentField, final ReasonSupplier reason)
	{
		fieldChangesOf(documentField).setMandatory(documentField.getMandatory(), reason);
	}

	/* package */ void collectDisplayedChanged(final IDocumentFieldView documentField, final ReasonSupplier reason)
	{
		fieldChangesOf(documentField).setDisplayed(documentField.getDisplayed(), reason);
	}

	/* package */ void collectLookupValuesStaled(final IDocumentFieldView documentField, final ReasonSupplier reason)
	{
		fieldChangesOf(documentField).setLookupValuesStale(true, reason);
	}

	/* package */void collectFrom(final DocumentChanges fromDocumentChanges)
	{
		for (final DocumentFieldChange fromFieldChange : fromDocumentChanges.getFieldChangesList())
		{
			final DocumentFieldChange toFieldChange = fieldChangesOf(fromFieldChange.getFieldName(), fromFieldChange.isKey(), fromFieldChange.isPublicField(), fromFieldChange.isAdvancedField(),
					fromFieldChange.getWidgetType());
			toFieldChange.mergeFrom(fromFieldChange);
		}

		if (fromDocumentChanges.documentValidStatus != null)
		{
			collectDocumentValidStatusChanged(fromDocumentChanges.documentValidStatus);
		}

		if (fromDocumentChanges.documentSaveStatus != null)
		{
			collectDocumentSaveStatusChanged(fromDocumentChanges.documentSaveStatus);
		}
	}

	/* package */Set<String> collectFrom(final Document document, final ReasonSupplier reason)
	{
		final Set<String> collectedFieldNames = new LinkedHashSet<>();

		for (final IDocumentFieldView documentField : document.getFieldViews())
		{
			if (collectFrom(documentField, reason))
			{
				collectedFieldNames.add(documentField.getFieldName());
			}
		}

		return collectedFieldNames;
	}

	private boolean collectFrom(final IDocumentFieldView documentField, final ReasonSupplier reason)
	{
		final DocumentFieldChange toEvent = fieldChangesOf(documentField);

		boolean collected = false;

		//
		// Value
		if (!toEvent.isValueSet())
		{
			final Object value = documentField.getValue();
			toEvent.setValue(value, reason);
			collected = true;
		}
		else
		{
			final Object value = documentField.getValue();
			final Object previousValue = toEvent.getValue();
			if (!DataTypes.equals(value, previousValue))
			{
				final ReasonSupplier reasonNew = reason.addPreviousReason(toEvent.getValueReason(), previousValue == null ? "<NULL>" : previousValue);
				toEvent.setValue(value, reasonNew);
				collected = true;
			}
		}

		//
		// Readonly
		final LogicExpressionResult readonly = documentField.getReadonly();
		if (!readonly.equals(toEvent.getReadonly()))
		{
			final ReasonSupplier reasonNew = reason.add("readonly", readonly).addPreviousReason(toEvent.getReadonlyReason());
			toEvent.setReadonly(readonly, reasonNew);
			collected = true;
		}

		//
		// Mandatory
		final LogicExpressionResult mandatory = documentField.getMandatory();
		if (!mandatory.equals(toEvent.getMandatory()))
		{
			final ReasonSupplier reasonNew = reason.add("mandatory", mandatory).addPreviousReason(toEvent.getMandatoryReason());
			toEvent.setMandatory(mandatory, reasonNew);
			collected = true;
		}

		//
		// Displayed
		final LogicExpressionResult displayed = documentField.getDisplayed();
		if (!displayed.equals(toEvent.getDisplayed()))
		{
			final ReasonSupplier reasonNew = reason.add("displayed", displayed).addPreviousReason(toEvent.getDisplayedReason());
			toEvent.setDisplayed(displayed, reasonNew);
			collected = true;
		}

		return collected;
	}

	/* package */void collectDocumentValidStatusChanged(final DocumentValidStatus documentValidStatus)
	{
		this.documentValidStatus = documentValidStatus;
	}

	public DocumentValidStatus getDocumentValidStatus()
	{
		return documentValidStatus;
	}

	/* package */ void collectValidStatusChanged(final IDocumentFieldView documentField)
	{
		fieldChangesOf(documentField).setValidStatus(documentField.getValidStatus());
	}

	/* package */void collectDocumentSaveStatusChanged(final DocumentSaveStatus documentSaveStatus)
	{
		this.documentSaveStatus = documentSaveStatus;
	}

	public DocumentSaveStatus getDocumentSaveStatus()
	{
		return documentSaveStatus;
	}

	/* package */void collectStaleDetailId(final DetailId detailId)
	{
		Check.assumeNotNull(detailId, "Parameter detailId is not null");
		staleDetailIds.add(detailId);
	}

	public Set<DetailId> getStaleDetailIds()
	{
		return ImmutableSet.copyOf(staleDetailIds);
	}

	/* package */void collectEvent(final IDocumentFieldChangedEvent event)
	{
		final boolean init_isKey = false;
		final boolean init_publicField = true;
		final boolean init_advancedField = false;
		final DocumentFieldWidgetType init_widgetType = event.getWidgetType();
		fieldChangesOf(event.getFieldName(), init_isKey, init_publicField, init_advancedField, init_widgetType)
				.mergeFrom(event);
	}

}
