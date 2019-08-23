package de.metas.ui.web.pattribute;

import java.util.function.Function;
import java.util.function.Supplier;

import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_M_AttributeInstance;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.pattribute.ASIDescriptorFactory.ASIAttributeFieldBinding;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;
import de.metas.util.Services;
import lombok.NonNull;

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

@Repository
public class ASIRepository
{
	// services
	private static final Logger logger = LogManager.getLogger(ASIRepository.class);
	private final ASIDescriptorFactory descriptorsFactory;

	private final Supplier<DocumentId> nextASIDocId = DocumentId.supplier("N", 1);
	private final CCache<DocumentId, ASIDocument> id2asiDoc = CCache.newLRUCache("ASIDocuments", 500, 0);

	private static final String VERSION_DEFAULT = "0";

	public ASIRepository(
			@NonNull final ASIDescriptorFactory descriptorsFactory)
	{
		this.descriptorsFactory = descriptorsFactory;
	}

	public ASIDocument createNewFrom(@NonNull final WebuiASIEditingInfo info)
	{
		//
		// Get the ASI descriptor
		final ASIDescriptor asiDescriptor = descriptorsFactory.getASIDescriptor(info);

		//
		// Create the new ASI document
		final Document asiDocData = Document.builder(asiDescriptor.getEntityDescriptor())
				.initializeAsNewDocument(nextASIDocId, VERSION_DEFAULT)
				.build();

		//
		// If we have a template ASI, populate the ASI document from it
		final AttributeSetInstanceId templateAsiId = info.getAttributeSetInstanceId();
		if (templateAsiId.isRegular())
		{
			for (final I_M_AttributeInstance fromAI : Services.get(IAttributeDAO.class).retrieveAttributeInstances(templateAsiId))
			{
				loadASIDocumentField(asiDocData, fromAI);
			}
		}

		//
		// Validate, log and add the new ASI document to our index
		asiDocData.checkAndGetValidStatus();
		logger.trace("Created from ASI={}: {}", templateAsiId, asiDocData);

		final ASIDocument asiDoc = new ASIDocument(asiDescriptor, asiDocData);
		commit(asiDoc);

		return asiDoc;
	}

	/**
	 * Retrieves {@link ASIDocument} for given ASI. The document will be readonly and not save-able.
	 *
	 * IMPORTANT: the retrieved document is not cached, so next time it will be retrieved again
	 *
	 * @param attributeSetInstanceId
	 * @return ASI document
	 */
	public ASIDocument loadReadonly(final AttributeSetInstanceId attributeSetInstanceId)
	{
		if (!AttributeSetInstanceId.isRegular(attributeSetInstanceId))
		{
			throw new EntityNotFoundException("ASI " + attributeSetInstanceId);
		}

		final WebuiASIEditingInfo info = WebuiASIEditingInfo.readonlyASI(attributeSetInstanceId);

		//
		// Get the ASI descriptor
		final ASIDescriptor asiDescriptor = descriptorsFactory.getASIDescriptor(info);

		//
		// Create the new ASI document
		final Document asiDocData = Document.builder(asiDescriptor.getEntityDescriptor())
				.initializeAsNewDocument(() -> DocumentId.of(attributeSetInstanceId), VERSION_DEFAULT)
				.build();

		//
		// If we have a template ASI, populate the ASI document from it
		final AttributeSetInstanceId templateAsiId = info.getAttributeSetInstanceId();
		for (final I_M_AttributeInstance fromAI : Services.get(IAttributeDAO.class).retrieveAttributeInstances(templateAsiId))
		{
			loadASIDocumentField(asiDocData, fromAI);
		}

		//
		// Validate, log and add the new ASI document to our index
		asiDocData.checkAndGetValidStatus();
		logger.trace("Created from ASI={}: {}", templateAsiId, asiDocData);

		final ASIDocument asiDoc = new ASIDocument(asiDescriptor, asiDocData);
		return asiDoc.copy(CopyMode.CheckInReadonly, NullDocumentChangesCollector.instance);
	}

	private ASIDocument getASIDocumentNoLock(final DocumentId asiDocId)
	{
		final ASIDocument asiDoc = id2asiDoc.get(asiDocId);
		if (asiDoc == null)
		{
			throw new EntityNotFoundException("No product attributes found for asiId=" + asiDocId);
		}
		return asiDoc;
	}

	private final void commit(final ASIDocument asiDoc)
	{
		final DocumentId asiDocId = asiDoc.getDocumentId();
		if (asiDoc.isCompleted())
		{
			final ASIDocument asiDocRemoved = id2asiDoc.remove(asiDocId);
			logger.trace("Removed from repository by ID={}: {}", asiDocId, asiDocRemoved);
		}
		else
		{
			final ASIDocument asiDocReadonly = asiDoc.copy(CopyMode.CheckInReadonly, NullDocumentChangesCollector.instance);
			id2asiDoc.put(asiDocId, asiDocReadonly);
			logger.trace("Added to repository: {}", asiDocReadonly);
		}
	}

	public <R> R forASIDocumentReadonly(
			@NonNull final DocumentId asiDocId,
			@NonNull final DocumentCollection documentsCollection,
			@NonNull final Function<ASIDocument, R> processor)
	{
		try (final IAutoCloseable readLock = getASIDocumentNoLock(asiDocId).lockForReading())
		{
			final ASIDocument asiDoc = getASIDocumentNoLock(asiDocId)
					.copy(CopyMode.CheckInReadonly, NullDocumentChangesCollector.instance)
					.bindContextDocumentIfPossible(documentsCollection);
			return processor.apply(asiDoc);
		}
	}

	public <R> R forASIDocumentWritable(
			@NonNull final DocumentId asiDocId,
			@NonNull final IDocumentChangesCollector changesCollector,
			@NonNull final DocumentCollection documentsCollection,
			@NonNull final Function<ASIDocument, R> processor)
	{
		try (final IAutoCloseable readLock = getASIDocumentNoLock(asiDocId).lockForWriting())
		{
			final ASIDocument asiDoc = getASIDocumentNoLock(asiDocId)
					.copy(CopyMode.CheckOutWritable, changesCollector)
					.bindContextDocumentIfPossible(documentsCollection);

			final R result = processor.apply(asiDoc);

			Services.get(ITrxManager.class)
					.getCurrentTrxListenerManagerOrAutoCommit()
					.newEventListener(TrxEventTiming.AFTER_COMMIT)
					.registerHandlingMethod(trx -> commit(asiDoc));

			return result;
		}
	}

	private static void loadASIDocumentField(final Document asiDoc, final I_M_AttributeInstance fromAI)
	{
		final String fieldName = fromAI.getM_Attribute().getValue();
		final IDocumentFieldView field = asiDoc.getFieldViewOrNull(fieldName);

		// Skip loading the attribute instance if it's no longer exist.
		// This can happen if we are trying to load an old ASI but in meantime the AttributeSet was changed and the attribute was removed or deactivated.
		if (field == null)
		{
			logger.warn("Attribute {} no longer exist in {}", fieldName, asiDoc.getEntityDescriptor());
			return;
		}

		final Object value = field
				.getDescriptor()
				.getDataBindingNotNull(ASIAttributeFieldBinding.class)
				.readValue(fromAI);

		asiDoc.processValueChange(fieldName, value, () -> "update from " + fromAI);
	}
}
