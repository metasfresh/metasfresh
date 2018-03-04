package de.metas.ui.web.quickinput;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.IDocumentChangesCollector;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Quick input instance
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class QuickInput
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static final QuickInput getQuickInputOrNull(final ICalloutField calloutField)
	{
		final Object documentObj = calloutField.getModel(Object.class);
		final QuickInput quickInput = InterfaceWrapperHelper.getDynAttribute(documentObj, DYNATTR_QuickInput);
		return quickInput;
	}

	private static final Logger logger = LogManager.getLogger(QuickInput.class);

	private static final String VERSION_DEFAULT = "0";

	private final QuickInputDescriptor descriptor;
	private final DocumentPath rootDocumentPath;
	private final DetailId targetDetailId;

	private final Document quickInputDocument;

	private transient Document rootDocument;

	// State
	private final ReentrantReadWriteLock readwriteLock;
	private boolean completed;

	private static final String DYNATTR_QuickInput = QuickInput.class.getName();

	private QuickInput(final Builder builder)
	{
		super();
		descriptor = builder.getQuickInputDescriptor();
		rootDocumentPath = builder.getRootDocumentPath();
		targetDetailId = builder.getTargetDetailId();

		quickInputDocument = builder.buildQuickInputDocument();
		quickInputDocument.setDynAttribute(DYNATTR_QuickInput, this);

		rootDocument = null;
		
		// State
		readwriteLock = new ReentrantReadWriteLock();
		completed = false;
	}

	/** Copy constructor */
	private QuickInput(final QuickInput from, final CopyMode copyMode, final IDocumentChangesCollector changesCollector)
	{
		super();
		descriptor = from.descriptor;
		rootDocumentPath = from.rootDocumentPath;
		targetDetailId = from.targetDetailId;

		quickInputDocument = from.quickInputDocument.copy(copyMode, changesCollector);
		if (copyMode.isWritable())
		{
			quickInputDocument.setDynAttribute(DYNATTR_QuickInput, this);
		}
		else
		{
			// quickInputDocument.setDynAttribute(DYNATTR_QuickInput, null); // NOTE: cannot call it because it will throw exception (document not writable)
		}

		rootDocument = null; // we are not copying it on purpose
		
		// State
		readwriteLock = from.readwriteLock; // always shared
		completed = from.completed;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("rootDocumentPath", rootDocumentPath)
				.add("targetDetailId", targetDetailId)
				.add("completed", completed)
				.add("quickInputDocument", quickInputDocument)
				.toString();
	}
	
	public DocumentPath getDocumentPath()
	{
		return quickInputDocument.getDocumentPath();
	}
	
	public IAutoCloseable lockForReading()
	{
		final ReadLock readLock = readwriteLock.readLock();
		logger.debug("Acquiring read lock for {}: {}", this, readLock);
		readLock.lock();
		logger.debug("Acquired read lock for {}: {}", this, readLock);

		return () -> {
			readLock.unlock();
			logger.debug("Released read lock for {}: {}", this, readLock);
		};
	}

	public IAutoCloseable lockForWriting()
	{
		final WriteLock writeLock = readwriteLock.writeLock();
		logger.debug("Acquiring write lock for {}: {}", this, writeLock);
		writeLock.lock();
		logger.debug("Acquired write lock for {}: {}", this, writeLock);

		return () -> {
			writeLock.unlock();
			logger.debug("Released write lock for {}: {}", this, writeLock);
		};
	}


	public DocumentId getId()
	{
		return quickInputDocument.getDocumentId();
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

	public QuickInput copy(final CopyMode copyMode, final IDocumentChangesCollector changesCollector)
	{
		return new QuickInput(this, copyMode, changesCollector);
	}

	public QuickInput bindRootDocument(final Document rootDocument)
	{
		this.rootDocument = rootDocument;
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

		final IQuickInputProcessor processor = descriptor.createProcessor();
		final DocumentId documentLineId = processor.process(this);
		final Document rootDocument = getRootDocument();
		final Document includedDocumentJustCreated = rootDocument.getIncludedDocument(targetDetailId, documentLineId);

		this.completed = true;
		return includedDocumentJustCreated;
	}
	
	public boolean isCompleted()
	{
		return completed;
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
		private QuickInputDescriptor _quickInputDescriptor;

		private Builder()
		{
			super();
		}

		public QuickInput build()
		{
			return new QuickInput(this);
		}

		public Builder setRootDocumentPath(final DocumentPath rootDocumentPath)
		{
			_rootDocumentPath = Preconditions.checkNotNull(rootDocumentPath, "rootDocumentPath");
			return this;
		}

		private DocumentPath getRootDocumentPath()
		{
			Check.assumeNotNull(_rootDocumentPath, "Parameter rootDocumentPath is not null");
			return _rootDocumentPath;
		}

		public Builder setQuickInputDescriptor(final QuickInputDescriptor quickInputDescriptor)
		{
			_quickInputDescriptor = quickInputDescriptor;
			return this;
		}

		private QuickInputDescriptor getQuickInputDescriptor()
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

		private Document buildQuickInputDocument()
		{
			return Document.builder(getQuickInputDescriptor().getEntityDescriptor())
					.initializeAsNewDocument(nextQuickInputDocumentId::getAndIncrement, VERSION_DEFAULT)
					.build();

		}

	}
}
