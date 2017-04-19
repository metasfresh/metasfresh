package de.metas.ui.web.view;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;

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

public class ForwardingDocumentView implements IDocumentView
{
	private final IDocumentView delegate;

	protected ForwardingDocumentView(final IDocumentView delegate)
	{
		Preconditions.checkNotNull(delegate, "delegate is null");
		this.delegate = delegate;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(delegate)
				.toString();
	}

	@OverridingMethodsMustInvokeSuper
	protected IDocumentView getDelegate()
	{
		return delegate;
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return getDelegate().getDocumentPath();
	}

	@Override
	public DocumentId getDocumentId()
	{
		return getDelegate().getDocumentId();
	}

	@Override
	public IDocumentViewType getType()
	{
		return getDelegate().getType();
	}

	@Override
	public boolean isProcessed()
	{
		return getDelegate().isProcessed();
	}

	@Override
	public Set<String> getFieldNames()
	{
		return getDelegate().getFieldNames();
	}

	@Override
	public Object getFieldValueAsJson(final String fieldName)
	{
		return getDelegate().getFieldValueAsJson(fieldName);
	}

	@Override
	public Map<String, Object> getFieldNameAndJsonValues()
	{
		return getDelegate().getFieldNameAndJsonValues();
	}

	@Override
	public boolean hasAttributes()
	{
		return getDelegate().hasAttributes();
	}

	@Override
	public IDocumentViewAttributes getAttributes() throws EntityNotFoundException
	{
		return getDelegate().getAttributes();
	}
	
	@Override
	public boolean hasIncludedView()
	{
		return getDelegate().hasIncludedView();
	}
	
	@Override
	public IDocumentViewSelection getCreateIncludedView(IDocumentViewsRepository viewsRepo)
	{
		return getDelegate().getCreateIncludedView(viewsRepo);
	}

	@Override
	public List<? extends IDocumentView> getIncludedDocuments()
	{
		return getDelegate().getIncludedDocuments();
	}

	protected final <T extends IDocumentView> List<T> getIncludedDocuments(final Class<T> type)
	{
		@SuppressWarnings("unchecked")
		final List<T> includedHUDocuments = (List<T>)(List<? extends IDocumentView>)getDelegate().getIncludedDocuments();
		return includedHUDocuments;
	}
}
