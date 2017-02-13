package de.metas.ui.web.address;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.adempiere.model.I_C_Location;
import de.metas.logging.LogManager;
import de.metas.ui.web.address.AddressDescriptorFactory.AddressFieldBinding;
import de.metas.ui.web.exceptions.EntityNotFoundException;
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
public class AddressRepository
{
	//
	// services
	private static final Logger logger = LogManager.getLogger(AddressRepository.class);
	@Autowired
	private AddressDescriptorFactory descriptorsFactory;

	//
	private final AtomicInteger nextAddressDocId = new AtomicInteger(1);
	private final CCache<DocumentId, Document> id2addresssDoc = CCache.newLRUCache("AddressDocuments", 50, 0);

	private static final String VERSION_DEFAULT = "0";
	private static final ReasonSupplier REASON_ProcessAddressDocumentChanges = () -> "process Address document changes";

	public Document createNewFrom(final int fromC_Location_ID)
	{
		final DocumentEntityDescriptor entityDescriptor = descriptorsFactory.getAddressDescriptor()
				.getEntityDescriptor();

		final Document addressDoc = Document.builder(entityDescriptor)
				.initializeAsNewDocument(nextAddressDocId::getAndIncrement, VERSION_DEFAULT)
				.build();

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

						addressDoc.processValueChange(field.getFieldName(), value, () -> "update from " + fromLocation);

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
		final Document addressDocReadonly = addressDoc.copy(CopyMode.CheckInReadonly);
		id2addresssDoc.put(addressDoc.getDocumentId(), addressDocReadonly);

		logger.trace("Added to repository: {}", addressDocReadonly);
	}

	private final void removeAddressDocumentById(final DocumentId addressDocId)
	{
		final Document addressDocRemoved = id2addresssDoc.remove(addressDocId);

		logger.trace("Removed from repository by ID={}: {}", addressDocId, addressDocRemoved);
	}

	public Document getAddressDocument(final int addressDocIdInt)
	{
		final DocumentId addressDocId = DocumentId.of(addressDocIdInt);
		return getAddressDocument(addressDocId);
	}

	public Document getAddressDocument(final DocumentId addressDocId)
	{
		final Document addressDoc = id2addresssDoc.get(addressDocId);
		if (addressDoc == null)
		{
			throw new EntityNotFoundException("No address document found for ID=" + addressDocId);
		}
		return addressDoc;
	}

	private Document getAddressDocumentForWriting(final DocumentId addressDocId)
	{
		return getAddressDocument(addressDocId).copy(CopyMode.CheckOutWritable);
	}

	public void processAddressDocumentChanges(final int addressDocIdInt, final List<JSONDocumentChangedEvent> events)
	{
		final DocumentId addressDocId = DocumentId.of(addressDocIdInt);
		final Document addressDoc = getAddressDocumentForWriting(addressDocId);
		addressDoc.processValueChanges(events, REASON_ProcessAddressDocumentChanges);

		Services.get(ITrxManager.class)
				.getCurrentTrxListenerManagerOrAutoCommit()
				.onAfterCommit(() -> putAddressDocument(addressDoc));
	}

	public LookupValue complete(final int addressDocIdInt)
	{
		final DocumentId addressDocId = DocumentId.of(addressDocIdInt);
		final Document addressDoc = getAddressDocumentForWriting(addressDocId);

		final I_C_Location locationRecord = createC_Location(addressDoc);

		Services.get(ITrxManager.class)
				.getCurrentTrxListenerManagerOrAutoCommit()
				.onAfterCommit(() -> removeAddressDocumentById(addressDocId));

		final String locationStr = locationRecord.toString(); // TODO
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

		InterfaceWrapperHelper.save(locationRecord);

		return locationRecord;
	}
}
