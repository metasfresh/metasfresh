package de.metas.ui.web.quickinput;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.DocumentCollection;

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

public final class QuickInput
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final DocumentPath rootDocumentPath;
	private final DetailId targetDetailId;
	private final QuickInputProcessorFactory quickInputProcessorFactory;

	private final Document quickInputDocument;

	private transient Document rootDocument;

	private QuickInput(final Builder builder)
	{
		super();
		rootDocumentPath = builder.getRootDocumentPath();
		targetDetailId = builder.getTargetDetailId();
		quickInputProcessorFactory = builder.getQuickInputProcessorFactory();

		quickInputDocument = builder.buildQuickInputDocument();

		rootDocument = null;
	}

	/** Copy constructor */
	private QuickInput(final QuickInput from, final CopyMode copyMode)
	{
		super();
		rootDocumentPath = from.rootDocumentPath;
		targetDetailId = from.targetDetailId;
		quickInputProcessorFactory = from.quickInputProcessorFactory;

		quickInputDocument = from.quickInputDocument.copy(copyMode);

		rootDocument = null; // we are not copying it on purpose
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("rootDocumentPath", rootDocumentPath)
				.add("targetDetailId", targetDetailId)
				.add("quickInputDocument", quickInputDocument)
				.toString();
	}

	public int getId()
	{
		return quickInputDocument.getDocumentIdAsInt();
	}

	public String getTargetTableName()
	{
		return quickInputDocument.getEntityDescriptor().getTableName();
	}

	public DetailId getDetailId()
	{
		return targetDetailId;
	}

	public Document getQuickInputDocument()
	{
		return quickInputDocument;
	}

	public <T> T getQuickInputDocumentAs(final Class<T> modelClass)
	{
		return InterfaceWrapperHelper.create(getQuickInputDocument(), modelClass);
	}

	public QuickInput copy(final CopyMode copyMode)
	{
		return new QuickInput(this, copyMode);
	}

	public QuickInput bindRootDocument(final DocumentCollection documentsCollection)
	{
		rootDocument = documentsCollection.getDocument(rootDocumentPath);
		quickInputDocument.setShadowParentDocumentEvaluatee(rootDocument.asEvaluatee());

		return this;
	}
	
	/**
	 * Asserts we are allowed to create a new included document.
	 */
	public QuickInput assertTargetWritable()
	{
		getRootDocument().assertNewDocumentAllowed(targetDetailId);
		return this;
	}

	public Document getRootDocument()
	{
		if (rootDocument == null)
		{
			throw new IllegalStateException("root document not set for " + this);
		}
		return rootDocument;
	}

	public <T> T getRootDocumentAs(final Class<T> modelClass)
	{
		return InterfaceWrapperHelper.create(getRootDocument(), modelClass);
	}

	public void processValueChanges(final List<JSONDocumentChangedEvent> events)
	{
		quickInputDocument.processValueChanges(events, () -> "direct update from rest API");
	}

	/**
	 * @return newly created document
	 */
	public Document complete()
	{
		Services.get(ITrxManager.class).assertThreadInheritedTrxExists();
		
		final IQuickInputProcessor processor = quickInputProcessorFactory.createProcessor(getTargetTableName());
		final Object orderLineObj = processor.process(this);
		final int orderLineId = InterfaceWrapperHelper.getId(orderLineObj);

		final Document orderDocument = getRootDocument();
		final Document orderLineDocument = orderDocument.getIncludedDocument(targetDetailId, DocumentId.of(orderLineId));

		return orderLineDocument;
	}

	public JSONLookupValuesList getFieldDropdownValues(final String fieldName)
	{
		return getQuickInputDocument()
				.getFieldLookupValues(fieldName)
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	public JSONLookupValuesList getFieldTypeaheadValues(final String fieldName, final String query)
	{
		return getQuickInputDocument()
				.getFieldLookupValuesForQuery(fieldName, query)
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	public static final class Builder
	{
		private static final AtomicInteger nextQuickInputDocumentId = new AtomicInteger(1);

		private DocumentPath _rootDocumentPath;
		private DocumentEntityDescriptor _quickInputDescriptor;

		private QuickInputProcessorFactory _quickInputProcessorFactory;

		private Builder()
		{
			super();
		}

		public QuickInput build()
		{
			return new QuickInput(this);
		}

		public Builder setRootDocumentPath(final int adWindowId, final String documentId)
		{
			_rootDocumentPath = DocumentPath.rootDocumentPath(DocumentType.Window, adWindowId, documentId);
			return this;
		}

		private DocumentPath getRootDocumentPath()
		{
			Check.assumeNotNull(_rootDocumentPath, "Parameter rootDocumentPath is not null");
			return _rootDocumentPath;
		}

		public Builder setQuickInputDescriptor(final DocumentEntityDescriptor quickInputDescriptor)
		{
			_quickInputDescriptor = quickInputDescriptor;
			return this;
		}

		private DocumentEntityDescriptor getQuickInputDescriptor()
		{
			Check.assumeNotNull(_quickInputDescriptor, "Parameter quickInputDescriptor is not null");
			return _quickInputDescriptor;
		}

		private DetailId getTargetDetailId()
		{
			final DetailId targetDetailId = getQuickInputDescriptor().getDetailId();
			Check.assumeNotNull(targetDetailId, "Parameter targetDetailId is not null");
			return targetDetailId;
		}

		public Builder setQuickInputProcessorFactory(final QuickInputProcessorFactory quickInputProcessorFactory)
		{
			_quickInputProcessorFactory = quickInputProcessorFactory;
			return this;
		}

		private QuickInputProcessorFactory getQuickInputProcessorFactory()
		{
			Check.assumeNotNull(_quickInputProcessorFactory, "Parameter _quickInputProcessorFactory is not null");
			return _quickInputProcessorFactory;
		}

		private Document buildQuickInputDocument()
		{
			return Document.builder()
					.setEntityDescriptor(getQuickInputDescriptor())
					.setDocumentIdSupplier(() -> nextQuickInputDocumentId.getAndIncrement())
					.initializeAsNewDocument()
					.build();

		}

	}
}