package de.metas.ui.web.pattribute;

import java.util.Collection;
import java.util.List;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.IDocumentFieldView;

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

/* package */class ASIDocument
{
	private final ASIDescriptor descriptor;
	private final Document data;

	/* package */ ASIDocument(final ASIDescriptor descriptor, final Document data)
	{
		Check.assumeNotNull(descriptor, "Parameter descriptor is not null");
		Check.assumeNotNull(data, "Parameter data is not null");
		this.descriptor = descriptor;
		this.data = data;
	}

	/** copy constructor */
	private ASIDocument(final ASIDocument asiDocument, final CopyMode copyMode)
	{
		descriptor = asiDocument.descriptor;
		data = asiDocument.data.copy(copyMode);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("data", data)
				.toString();
	}

	public ASIDocument copy(final CopyMode copyMode)
	{
		return new ASIDocument(this, copyMode);
	}

	public ASIDescriptor getDescriptor()
	{
		return descriptor;
	}

	public DocumentId getDocumentId()
	{
		return data.getDocumentId();
	}

	int getM_AttributeSet_ID()
	{
		return descriptor.getM_AttributeSet_ID();
	}

	public void processValueChanges(final List<JSONDocumentChangedEvent> events, final ReasonSupplier reason)
	{
		data.processValueChanges(events, reason);
	}

	public Collection<IDocumentFieldView> getFieldViews()
	{
		return data.getFieldViews();
	}

	public JSONDocument toJSONDocument(final JSONOptions jsonOpts)
	{
		return JSONDocument.ofDocument(data, jsonOpts);
	}

	public LookupValuesList getFieldLookupValuesForQuery(final String attributeName, final String query)
	{
		return data.getFieldLookupValuesForQuery(attributeName, query);
	}

	public LookupValuesList getFieldLookupValues(final String attributeName)
	{
		return data.getFieldLookupValues(attributeName);
	}
}
