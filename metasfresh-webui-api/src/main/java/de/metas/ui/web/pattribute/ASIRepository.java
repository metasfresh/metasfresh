package de.metas.ui.web.pattribute;

import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.logging.LogManager;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.pattribute.ASIDescriptorFactory.ASIAttributeFieldBinding;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentsRepository;

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
public class ASIRepository implements DocumentsRepository
{
	private static final Logger logger = LogManager.getLogger(ASIRepository.class);

	@Autowired
	private ASIDescriptorFactory descriptorsFactory;

	private final CCache<Integer, Document> id2asiDoc = CCache.newLRUCache("ASIDocuments", 500, 0);

	public Document createNewFrom(final int fromASI_ID)
	{
		final I_M_AttributeSetInstance fromASI = InterfaceWrapperHelper.create(Env.getCtx(), fromASI_ID, I_M_AttributeSetInstance.class, ITrx.TRXNAME_ThreadInherited);

		final DocumentEntityDescriptor entityDescriptor = descriptorsFactory.getProductAttributesDescriptor(fromASI.getM_AttributeSet_ID())
				.getEntityDescriptor();

		final Document asiDoc = Document.builder()
				.setEntityDescriptor(entityDescriptor)
				.setDocumentIdSupplier(this::getNextId)
				.initializeAsNewDocument()
				.build();

		for (final I_M_AttributeInstance fromAI : Services.get(IAttributeDAO.class).retrieveAttributeInstances(fromASI))
		{
			loadASIDocumentField(asiDoc, fromAI);
		}

		putASIDocument(asiDoc);

		return asiDoc;
	}

	public final void putASIDocument(final Document asiDoc)
	{
		id2asiDoc.put(asiDoc.getDocumentIdAsInt(), asiDoc.copy(CopyMode.CheckInReadonly));
	}

	public final void removeASIDocument(final Document asiDoc)
	{
		id2asiDoc.remove(asiDoc.getDocumentIdAsInt());
	}

	public Document getASIDocument(final int asiId)
	{
		final Document asiDoc = id2asiDoc.get(asiId);
		if (asiDoc == null)
		{
			throw new EntityNotFoundException("No product attributes found for asiId=" + asiId);
		}
		return asiDoc;
	}

	public Document getASIDocumentForWriting(final int asiId)
	{
		return getASIDocument(asiId).copy(CopyMode.CheckOutWritable);
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

	private int getNextId()
	{
		final int adClientId = Env.getAD_Client_ID(Env.getCtx());
		final String tableName = I_M_AttributeSetInstance.Table_Name;
		final int nextId = DB.getNextID(adClientId, tableName, ITrx.TRXNAME_ThreadInherited);
		if (nextId <= 0)
		{
			throw new DBException("Cannot retrieve next ID from database for " + tableName);
		}

		logger.trace("Acquired next ID={} for {}", nextId, tableName);
		return nextId;
	}

	public LookupValue complete(final Document asiDoc)
	{
		final I_M_AttributeSetInstance asiRecord = createM_AttributeSetInstance(asiDoc);
		return IntegerLookupValue.of(asiRecord.getM_AttributeSetInstance_ID(), asiRecord.getDescription());
	}

	private final I_M_AttributeSetInstance createM_AttributeSetInstance(final Document asiDoc)
	{
		// asiDoc.saveIfValidAndHasChanges()

		final int attributeSetId = asiDoc.getEntityDescriptor().getDocumentTypeId();

		final I_M_AttributeSetInstance asiRecord = InterfaceWrapperHelper.create(Env.getCtx(), I_M_AttributeSetInstance.class, ITrx.TRXNAME_ThreadInherited);
		// TODO: set the preallocated ASI ID
		asiRecord.setM_AttributeSet_ID(attributeSetId);
		// TODO: set Lot, GuaranteeDate etc
		InterfaceWrapperHelper.save(asiRecord);

		asiDoc.getFieldViews()
				.stream()
				.forEach(asiField -> {
					final ASIAttributeFieldBinding fieldBinding = asiField.getDescriptor().getDataBindingNotNull(ASIAttributeFieldBinding.class);
					fieldBinding.createAndSaveM_AttributeInstance(asiRecord, asiField);
				});

		Services.get(IAttributeSetInstanceBL.class).setDescription(asiRecord);
		InterfaceWrapperHelper.save(asiRecord);

		return asiRecord;
	}

	@Override
	public void save(final Document asiDoc)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Document> retrieveDocuments(final DocumentQuery query)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Document retrieveDocument(final DocumentQuery query)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Document createNewDocument(final DocumentEntityDescriptor entityDescriptor, final Document parentDocument)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void refresh(final Document document)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(final Document document)
	{
		throw new UnsupportedOperationException();
	}
}
