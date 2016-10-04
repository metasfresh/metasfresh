package de.metas.ui.web.window.model;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.model.InterfaceWrapperHelper;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

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

/*package*/final class DocumentAsCalloutRecord implements ICalloutRecord
{
	private static final ReasonSupplier REASON_Value_DirectSetOnCalloutRecord = () -> "direct set on callout record";

	private final Reference<Document> _documentRef;

	/* package */ DocumentAsCalloutRecord(final Document document)
	{
		super();
		Preconditions.checkNotNull(document, "document shall not be null");
		this._documentRef = new WeakReference<>(document);
	}
	
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(_documentRef.get())
				.toString();
	}
	
	private final Document getDocument()
	{
		final Document document = _documentRef.get();
		if(document == null)
		{
			throw new IllegalStateException("Document reference already expired");
		}
		return document;
	}
	
	@Override
	public String getTableName()
	{
		final Document document = getDocument();
		return document.getEntityDescriptor().getTableName();
	}
	
	@Override
	public int getAD_Tab_ID()
	{
		final Document document = getDocument();
		return document.getEntityDescriptor().getAD_Tab_ID();
	}

	@Override
	public <T> T getModel(final Class<T> modelClass)
	{
		final Document document = getDocument();
		return DocumentInterfaceWrapper.wrap(document, modelClass);
	}
	
	@Override
	public <T> T getModelBeforeChanges(final Class<T> modelClass)
	{
		final Document document = getDocument();
		return DocumentInterfaceWrapper.wrapUsingOldValues(document, modelClass);
	}


	@Override
	public Object getValue(final String columnName)
	{
		final Document document = getDocument();
		return InterfaceWrapperHelper.getValueOrNull(document, columnName);
	}

	@Override
	public String setValue(final String columnName, final Object value)
	{
		final Document document = getDocument();
		document.setValue(columnName, value, REASON_Value_DirectSetOnCalloutRecord);
		return "";
	}

	@Override
	public void dataRefresh()
	{
		final Document document = getDocument();
		document.refreshFromRepository();
	}

	@Override
	public void dataRefreshAll()
	{
		// NOTE: there is no "All" concept here, so we are just refreshing this document
		final Document document = getDocument();
		document.refreshFromRepository();
	}

	@Override
	public void dataRefreshRecursively()
	{
		// TODO dataRefreshRecursively: refresh document and it's children
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean dataSave(final boolean manualCmd)
	{
		// TODO dataSave: save document but also update the DocumentsCollection!
		throw new UnsupportedOperationException();
	}
}