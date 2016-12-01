package de.metas.ui.web.pattribute;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.logging.LogManager;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.pattribute.ASIDescriptorFactory.ASIAttributeFieldBinding;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;
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

@Component
public class ASIRepository
{
	// services
	private static final Logger logger = LogManager.getLogger(ASIRepository.class);
	@Autowired
	private ASIDescriptorFactory descriptorsFactory;

	private final AtomicInteger nextASIDocId = new AtomicInteger(1);
	private final CCache<DocumentId, Document> id2asiDoc = CCache.newLRUCache("ASIDocuments", 500, 0);

	private static final ReasonSupplier REASON_ProcessASIDocumentChanges = () -> "process ASI document changes";

	public Document createNewFrom(final int fromASI_ID)
	{
		final I_M_AttributeSetInstance fromASI = InterfaceWrapperHelper.create(Env.getCtx(), fromASI_ID, I_M_AttributeSetInstance.class, ITrx.TRXNAME_ThreadInherited);

		final DocumentEntityDescriptor entityDescriptor = descriptorsFactory.getProductAttributesDescriptor(fromASI.getM_AttributeSet_ID())
				.getEntityDescriptor();

		final Document asiDoc = Document.builder()
				.setEntityDescriptor(entityDescriptor)
				.setDocumentIdSupplier(nextASIDocId::getAndIncrement)
				.initializeAsNewDocument()
				.build();

		for (final I_M_AttributeInstance fromAI : Services.get(IAttributeDAO.class).retrieveAttributeInstances(fromASI))
		{
			loadASIDocumentField(asiDoc, fromAI);
		}

		logger.trace("Created from ASI={}: {}", fromASI_ID, asiDoc);

		putASIDocument(asiDoc);

		return asiDoc;
	}

	public ASILayout getLayout(final int asiDocIdInt)
	{
		final DocumentId asiDocId = DocumentId.of(asiDocIdInt);
		final int attributeSetId = getASIDocument(asiDocId)
				.getEntityDescriptor()
				.getDocumentTypeId();

		return descriptorsFactory.getProductAttributesDescriptor(attributeSetId)
				.getLayout();
	}

	private final void putASIDocument(final Document asiDoc)
	{
		final Document asiDocReadonly = asiDoc.copy(CopyMode.CheckInReadonly);
		id2asiDoc.put(asiDoc.getDocumentId(), asiDocReadonly);

		logger.trace("Added to repository: {}", asiDocReadonly);
	}

	private final void removeASIDocumentById(final DocumentId asiDocId)
	{
		final Document asiDocRemoved = id2asiDoc.remove(asiDocId);

		logger.trace("Removed from repository by ID={}: {}", asiDocId, asiDocRemoved);
	}

	public Document getASIDocument(final int asiDocIdInt)
	{
		final DocumentId asiDocId = DocumentId.of(asiDocIdInt);
		return getASIDocument(asiDocId);
	}

	public Document getASIDocument(final DocumentId asiDocId)
	{
		final Document asiDoc = id2asiDoc.get(asiDocId);
		if (asiDoc == null)
		{
			throw new EntityNotFoundException("No product attributes found for asiId=" + asiDocId);
		}
		return asiDoc;
	}

	private Document getASIDocumentForWriting(final DocumentId asiDocId)
	{
		return getASIDocument(asiDocId).copy(CopyMode.CheckOutWritable);
	}

	public void processASIDocumentChanges(final int asiDocIdInt, final List<JSONDocumentChangedEvent> events)
	{
		final DocumentId asiDocId = DocumentId.of(asiDocIdInt);
		final Document asiDoc = getASIDocumentForWriting(asiDocId);
		asiDoc.processValueChanges(events, REASON_ProcessASIDocumentChanges);

		Services.get(ITrxManager.class)
				.getCurrentTrxListenerManagerOrAutoCommit()
				.onAfterCommit(() -> putASIDocument(asiDoc));
	}

	private void loadASIDocumentField(final Document asiDoc, final I_M_AttributeInstance fromAI)
	{
		final String fieldName = fromAI.getM_Attribute().getValue();

		final Object value = asiDoc.getFieldView(fieldName)
				.getDescriptor()
				.getDataBindingNotNull(ASIAttributeFieldBinding.class)
				.readValue(fromAI);

		asiDoc.processValueChange(fieldName, value, () -> "update from " + fromAI);
	}

	public LookupValue complete(final int asiDocIdInt)
	{
		final DocumentId asiDocId = DocumentId.of(asiDocIdInt);
		final Document asiDoc = getASIDocumentForWriting(asiDocId);

		final I_M_AttributeSetInstance asiRecord = createM_AttributeSetInstance(asiDoc);

		Services.get(ITrxManager.class)
				.getCurrentTrxListenerManagerOrAutoCommit()
				.onAfterCommit(() -> removeASIDocumentById(asiDocId));

		return IntegerLookupValue.of(asiRecord.getM_AttributeSetInstance_ID(), asiRecord.getDescription());
	}

	private final I_M_AttributeSetInstance createM_AttributeSetInstance(final Document asiDoc)
	{
		//
		// Create M_AttributeSetInstance
		final int attributeSetId = asiDoc.getEntityDescriptor().getDocumentTypeId();

		final I_M_AttributeSetInstance asiRecord = InterfaceWrapperHelper.create(Env.getCtx(), I_M_AttributeSetInstance.class, ITrx.TRXNAME_ThreadInherited);
		asiRecord.setM_AttributeSet_ID(attributeSetId);
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
