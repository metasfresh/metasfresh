package de.metas.ui.web.address;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Location;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.cache.CCache;
import de.metas.location.ILocationBL;
import de.metas.location.ILocationDAO;
import de.metas.logging.LogManager;
import de.metas.ui.web.address.AddressDescriptorFactory.AddressFieldBinding;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.exceptions.DocumentNotFoundException;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;
import de.metas.util.Services;

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

@Component
public class AddressRepository
{
	//
	// services
	private static final Logger logger = LogManager.getLogger(AddressRepository.class);
	@Autowired
	private AddressDescriptorFactory descriptorsFactory;

	//
	private final AtomicInteger nextAddressDocId = new AtomicInteger(1);
	private final CCache<DocumentId, Document> id2addressDoc = CCache.newLRUCache("AddressDocuments", 50, 0);

	private static final String VERSION_DEFAULT = "0";
	private static final ReasonSupplier REASON_ProcessAddressDocumentChanges = () -> "process Address document changes";

	public Document createNewFrom(final int fromC_Location_ID)
	{
		final DocumentEntityDescriptor entityDescriptor = descriptorsFactory.getAddressDescriptor()
				.getEntityDescriptor();

		final Document addressDoc = Document.builder(entityDescriptor)
				.initializeAsNewDocument(nextAddressDocId::getAndIncrement, VERSION_DEFAULT);

		final I_C_Location fromLocation = fromC_Location_ID <= 0 ? null : InterfaceWrapperHelper.create(Env.getCtx(), fromC_Location_ID, I_C_Location.class, ITrx.TRXNAME_ThreadInherited);
		if (fromLocation != null)
		{
			addressDoc.getFieldViews()
					.stream()
					.forEach(field -> {
						final Object value = field
								.getDescriptor()
								.getDataBindingNotNull(AddressFieldBinding.class)
								.readValue(fromLocation);

						addressDoc.processValueChange(
								field.getFieldName(),
								value,
								() -> "update from " + fromLocation,
								true // ignoreReadonlyFlag
						);

					});
		}

		addressDoc.checkAndGetValidStatus();

		logger.trace("Created from C_Location_ID={}: {}", fromC_Location_ID, addressDoc);

		putAddressDocument(addressDoc);

		return addressDoc;
	}

	public AddressLayout getLayout()
	{
		return descriptorsFactory.getAddressDescriptor().getLayout();
	}

	private final void putAddressDocument(final Document addressDoc)
	{
		final Document addressDocReadonly = addressDoc.copy(CopyMode.CheckInReadonly, NullDocumentChangesCollector.instance);
		id2addressDoc.put(addressDoc.getDocumentId(), addressDocReadonly);

		logger.trace("Added to repository: {}", addressDocReadonly);
	}

	private final void removeAddressDocumentById(final DocumentId addressDocId)
	{
		final Document addressDocRemoved = id2addressDoc.remove(addressDocId);

		logger.trace("Removed from repository by ID={}: {}", addressDocId, addressDocRemoved);
	}

	private Document getInnerAddressDocument(final DocumentId addressDocId)
	{
		final Document addressDoc = id2addressDoc.get(addressDocId);
		if (addressDoc == null)
		{
			throw new DocumentNotFoundException(DocumentType.Address, AddressDescriptor.DocumentTypeId, addressDocId);
		}
		return addressDoc;
	}

	public Document getAddressDocumentForReading(final int addressDocIdInt)
	{
		final DocumentId addressDocId = DocumentId.of(addressDocIdInt);
		return getInnerAddressDocument(addressDocId).copy(CopyMode.CheckInReadonly, NullDocumentChangesCollector.instance);
	}

	private Document getAddressDocumentForWriting(final DocumentId addressDocId, final IDocumentChangesCollector changesCollector)
	{
		return getInnerAddressDocument(addressDocId).copy(CopyMode.CheckOutWritable, changesCollector);
	}

	public void processAddressDocumentChanges(final int addressDocIdInt, final List<JSONDocumentChangedEvent> events, final IDocumentChangesCollector changesCollector)
	{
		final DocumentId addressDocId = DocumentId.of(addressDocIdInt);
		final Document addressDoc = getAddressDocumentForWriting(addressDocId, changesCollector);
		addressDoc.processValueChanges(events, REASON_ProcessAddressDocumentChanges);

		Services.get(ITrxManager.class)
				.getCurrentTrxListenerManagerOrAutoCommit().newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(trx -> putAddressDocument(addressDoc));

	}

	public LookupValue complete(final int addressDocIdInt)
	{
		final DocumentId addressDocId = DocumentId.of(addressDocIdInt);
		final Document addressDoc = getAddressDocumentForWriting(addressDocId, NullDocumentChangesCollector.instance);

		final I_C_Location locationRecord = createC_Location(addressDoc);

		Services.get(ITrxManager.class)
				.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(trx -> removeAddressDocumentById(addressDocId));

		final String locationStr = Services.get(ILocationBL.class).mkAddress(locationRecord);
		return IntegerLookupValue.of(locationRecord.getC_Location_ID(), locationStr);
	}

	private final I_C_Location createC_Location(final Document locationDoc)
	{
		final I_C_Location locationRecord = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Location.class, ITrx.TRXNAME_ThreadInherited);

		locationDoc.getFieldViews()
				.stream()
				.forEach(locationField -> locationField
						.getDescriptor()
						.getDataBindingNotNull(AddressFieldBinding.class)
						.writeValue(locationRecord, locationField));

		Services.get(ILocationDAO.class).save(locationRecord);

		return locationRecord;
	}
}
