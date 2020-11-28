package de.metas.ui.web.pattribute;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_M_AttributeSetInstance;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;
import de.metas.ui.web.pattribute.ASIDescriptorFactory.ASIAttributeFieldBinding;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentOptions;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

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

public class ASIDocument
{
	private static final transient Logger logger = LogManager.getLogger(ASIDocument.class);

	private final ASIDescriptor descriptor;
	private final Document data;

	// State
	private final ReentrantReadWriteLock _lock;
	private boolean completed;

	/* package */ ASIDocument(final ASIDescriptor descriptor, final Document data)
	{
		Check.assumeNotNull(descriptor, "Parameter descriptor is not null");
		Check.assumeNotNull(data, "Parameter data is not null");
		this.descriptor = descriptor;
		this.data = data;

		_lock = new ReentrantReadWriteLock();
		completed = false;
	}

	/** copy constructor */
	private ASIDocument(final ASIDocument asiDocument, final CopyMode copyMode, final IDocumentChangesCollector changesCollector)
	{
		descriptor = asiDocument.descriptor;
		data = asiDocument.data.copy(copyMode, changesCollector);

		_lock = asiDocument._lock; // always share
		completed = asiDocument.completed;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("data", data)
				.add("completed", completed)
				.toString();
	}

	private final void assertNotCompleted()
	{
		if (completed)
		{
			throw new IllegalStateException("ASI document was completed");
		}
	}

	IAutoCloseable lockForReading()
	{
		// assume _lock is not null
		final ReadLock readLock = _lock.readLock();
		logger.debug("Acquiring read lock for {}: {}", this, readLock);
		readLock.lock();
		logger.debug("Acquired read lock for {}: {}", this, readLock);

		return () -> {
			readLock.unlock();
			logger.debug("Released read lock for {}: {}", this, readLock);
		};
	}

	IAutoCloseable lockForWriting()
	{
		// assume _lock is not null
		final WriteLock writeLock = _lock.writeLock();
		logger.debug("Acquiring write lock for {}: {}", this, writeLock);
		writeLock.lock();
		logger.debug("Acquired write lock for {}: {}", this, writeLock);

		return () -> {
			writeLock.unlock();
			logger.debug("Released write lock for {}: {}", this, writeLock);
		};
	}

	public ASIDocument copy(final CopyMode copyMode, final IDocumentChangesCollector changesCollector)
	{
		return new ASIDocument(this, copyMode, changesCollector);
	}

	public ASIDocument bindContextDocumentIfPossible(@NonNull final DocumentCollection documentsCollection)
	{
		final DocumentPath contextDocumentPath = descriptor.getContextDocumentPath();
		if (contextDocumentPath == null)
		{
			return this;
		}
		if (!documentsCollection.isWindowIdSupported(contextDocumentPath.getWindowIdOrNull()))
		{
			return this;
		}

		final Document contextDocument = documentsCollection.getDocumentReadonly(contextDocumentPath);
		data.setShadowParentDocumentEvaluatee(contextDocument.asEvaluatee());

		return this;
	}

	public ASILayout getLayout()
	{
		return descriptor.getLayout();
	}

	public DocumentId getDocumentId()
	{
		return data.getDocumentId();
	}

	AttributeSetId getAttributeSetId()
	{
		return descriptor.getAttributeSetId();
	}

	void processValueChanges(final List<JSONDocumentChangedEvent> events, final ReasonSupplier reason)
	{
		assertNotCompleted();
		data.processValueChanges(events, reason);
	}

	public Collection<IDocumentFieldView> getFieldViews()
	{
		return data.getFieldViews();
	}

	public JSONDocument toJSONDocument(final JSONDocumentOptions options)
	{
		return JSONDocument.ofDocument(data, options);
	}

	public LookupValuesList getFieldLookupValuesForQuery(final String attributeName, final String query)
	{
		return data.getFieldLookupValuesForQuery(attributeName, query);
	}

	public LookupValuesList getFieldLookupValues(final String attributeName)
	{
		return data.getFieldLookupValues(attributeName);
	}

	public boolean isCompleted()
	{
		return completed;
	}

	IntegerLookupValue complete()
	{
		assertNotCompleted();

		final I_M_AttributeSetInstance asiRecord = createM_AttributeSetInstance(this);
		final IntegerLookupValue lookupValue = IntegerLookupValue.of(asiRecord.getM_AttributeSetInstance_ID(), asiRecord.getDescription());
		completed = true;
		return lookupValue;
	}

	private static final I_M_AttributeSetInstance createM_AttributeSetInstance(final ASIDocument asiDoc)
	{
		//
		// Create M_AttributeSetInstance
		final AttributeSetId attributeSetId = asiDoc.getAttributeSetId();

		final I_M_AttributeSetInstance asiRecord = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class);
		asiRecord.setM_AttributeSet_ID(attributeSetId.getRepoId());
		// TODO: set Lot, GuaranteeDate etc
		InterfaceWrapperHelper.save(asiRecord);

		//
		// Create M_AttributeInstances
		asiDoc.getFieldViews()
				.stream()
				.forEach(asiField -> asiField
						.getDescriptor()
						.getDataBindingNotNull(ASIAttributeFieldBinding.class)
						.createAndSaveM_AttributeInstance(asiRecord, asiField));

		//
		// Update the ASI description
		Services.get(IAttributeSetInstanceBL.class).setDescription(asiRecord);
		InterfaceWrapperHelper.save(asiRecord);

		return asiRecord;
	}

}
