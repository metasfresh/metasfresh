package de.metas.ui.web.handlingunits;

import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.util.Env;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.impl.HUAttributesDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.ui.web.view.IDocumentViewAttributesProvider;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import lombok.Value;

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

public class HUDocumentViewAttributesProvider implements IDocumentViewAttributesProvider
{
	private final ExtendedMemorizingSupplier<IAttributeStorageFactory> _attributeStorageFactory = ExtendedMemorizingSupplier.of(() -> createAttributeStorageFactory());
	private final ConcurrentHashMap<DocumentViewAttributesKey, HUDocumentViewAttributes> documentViewAttributesByKey = new ConcurrentHashMap<>();
	
	@Value
	private static final class DocumentViewAttributesKey
	{
		private DocumentId documentId;
		private int huId;
	}

	public HUDocumentViewAttributesProvider()
	{
		super();
	}

	@Override
	public HUDocumentViewAttributes getAttributes(final DocumentId documentId, final DocumentId attributesKey)
	{
		final int huId = attributesKey.toInt();
		final DocumentViewAttributesKey key = new DocumentViewAttributesKey(documentId, huId);
		return documentViewAttributesByKey.computeIfAbsent(key, this::createDocumentViewAttributes);
	}

	private HUDocumentViewAttributes createDocumentViewAttributes(final DocumentViewAttributesKey key)
	{
		final int huId = key.getHuId();
		final I_M_HU hu = InterfaceWrapperHelper.create(Env.getCtx(), huId, I_M_HU.class, ITrx.TRXNAME_None);
		if (hu == null)
		{
			throw new IllegalArgumentException("No HU found for M_HU_ID=" + huId);
		}

		final IAttributeStorage attributesStorage = getAttributeStorageFactory().getAttributeStorage(hu);
		attributesStorage.setSaveOnChange(true);
		
		final DocumentId documentTypeId = DocumentId.of(huId);
		final DocumentId documentId = key.getDocumentId();
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(DocumentType.HUAttributes, documentTypeId, documentId);

		return new HUDocumentViewAttributes(documentPath, attributesStorage);
	}

	public IAttributeStorageFactory getAttributeStorageFactory()
	{
		return _attributeStorageFactory.get();
	}

	private final IAttributeStorageFactory createAttributeStorageFactory()
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);

		final IHUAttributesDAO huAttributesDAO = HUAttributesDAO.instance;
		final IAttributeStorageFactory huAttributeStorageFactory = attributeStorageFactoryService.createHUAttributeStorageFactory(huAttributesDAO);

		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		huAttributeStorageFactory.setHUStorageFactory(storageFactory);

		return huAttributeStorageFactory;
	}

	@Override
	public void invalidateAll()
	{
		//
		// Destroy AttributeStorageFactory
		_attributeStorageFactory.forget();

		//
		// Destroy attribute documents
		documentViewAttributesByKey.clear();
	}
}
