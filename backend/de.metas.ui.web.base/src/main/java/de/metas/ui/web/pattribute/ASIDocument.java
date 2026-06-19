package de.metas.ui.web.pattribute;

import de.metas.logging.LogManager;
import de.metas.ui.web.pattribute.ASIDescriptorFactory.ASIAttributeFieldBinding;
import de.metas.ui.web.pattribute.json.JSONASIDocument;
import de.metas.ui.web.pattribute.json.JSONASILayout;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions;
import de.metas.ui.web.window.datatypes.json.JSONDocumentOptions;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_M_AttributeSetInstance;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

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

@ToString(of = { "data", "completed" })
public class ASIDocument
{
	private static final Logger logger = LogManager.getLogger(ASIDocument.class);
	static final String DYNATTR_ASIDescriptor = "ASIDescriptor";
	static final String DYNATTR_ASIEventType = "ASIDescriptor.ASIEventType";

	@NonNull private final ASIDescriptor descriptor;
	@NonNull private final Document data;

	// State
	@NonNull private final ReentrantReadWriteLock lock;
	@Getter private boolean completed;

	/* package */ ASIDocument(@NonNull final ASIDescriptor descriptor, @NonNull final Document data)
	{
		this.descriptor = descriptor;
		this.data = data;
		this.lock = new ReentrantReadWriteLock();
		this.completed = false;
	}

	/**
	 * copy constructor
	 */
	private ASIDocument(final ASIDocument asiDocument, final CopyMode copyMode, final IDocumentChangesCollector changesCollector)
	{
		this.descriptor = asiDocument.descriptor;
		this.data = asiDocument.data.copy(copyMode, changesCollector);
		this.lock = asiDocument.lock; // always share
		this.completed = asiDocument.completed;
	}

	@NonNull
	public static ASIDescriptor extractASIDescriptor(final ICalloutField calloutField)
	{
		final Document document = calloutField.getModel(Document.class);
		final ASIDescriptor asiDescriptor = document.getDynAttribute(DYNATTR_ASIDescriptor);
		if (asiDescriptor == null)
		{
			throw new AdempiereException("Cannot extract " + ASIDescriptor.class + " from " + document + "/" + calloutField);
		}
		return asiDescriptor;
	}

	public static ASIEventType extractASIEventType(final ICalloutField calloutField)
	{
		final Document document = calloutField.getModel(Document.class);
		final ASIEventType eventType = document.getDynAttribute(DYNATTR_ASIEventType);
		if (eventType == null)
		{
			throw new AdempiereException("Cannot extract " + ASIEventType.class + " from " + document + "/" + calloutField);
		}
		return eventType;
	}

	private void assertNotCompleted()
	{
		if (completed)
		{
			throw new IllegalStateException("ASI document was completed");
		}
	}

	IAutoCloseable lockForReading()
	{
		// assume _lock is not null
		final ReadLock readLock = lock.readLock();
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
		final WriteLock writeLock = lock.writeLock();
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

		final Document data = this.data;
		try
		{
			data.setDynAttribute(DYNATTR_ASIDescriptor, descriptor);
			data.setDynAttribute(DYNATTR_ASIEventType, ASIEventType.VALUE_CHANGED);
			data.processValueChanges(events, reason);
		}
		finally
		{
			data.setDynAttribute(DYNATTR_ASIEventType, null);
		}
	}

	public Collection<IDocumentFieldView> getFieldViews()
	{
		return data.getFieldViews();
	}

	public JSONASIDocument toJSONASIDocument(
			final JSONDocumentOptions docOpts,
			final JSONDocumentLayoutOptions layoutOpts)
	{
		return JSONASIDocument.builder()
				.id(data.getDocumentId())
				.layout(JSONASILayout.of(getLayout(), layoutOpts))
				.fieldsByName(JSONDocument.ofDocument(data, docOpts).getFieldsByName())
				.build();
	}

	public LookupValuesPage getFieldLookupValuesForQuery(final String attributeName, final String query)
	{
		return data.getFieldLookupValuesForQuery(attributeName, query);
	}

	public LookupValuesList getFieldLookupValues(final String attributeName)
	{
		return data.getFieldLookupValues(attributeName);
	}

	IntegerLookupValue complete()
	{
		assertNotCompleted();

		final I_M_AttributeSetInstance asiRecord = createM_AttributeSetInstance(this);
		final IntegerLookupValue lookupValue = IntegerLookupValue.of(asiRecord.getM_AttributeSetInstance_ID(), asiRecord.getDescription());
		completed = true;
		return lookupValue;
	}

	private static I_M_AttributeSetInstance createM_AttributeSetInstance(final ASIDocument asiDoc)
	{
		//
		// Create M_AttributeSetInstance
		final AttributeSetId attributeSetId = asiDoc.getAttributeSetId();

		final I_M_AttributeSetInstance asiRecord = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class);
		asiRecord.setM_AttributeSet_ID(attributeSetId.getRepoId());
		InterfaceWrapperHelper.save(asiRecord);

		//
		// Create M_AttributeInstances
		asiDoc.getFieldViews()
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
