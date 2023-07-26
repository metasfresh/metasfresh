package de.metas.ui.web.handlingunits;

import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.product.ProductId;
import de.metas.ui.web.view.IViewRowAttributesProvider;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.util.Services;
import lombok.Builder;
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

public class HUEditorRowAttributesProvider implements IViewRowAttributesProvider
{
	private final boolean readonly;

	private final ExtendedMemorizingSupplier<IAttributeStorageFactory> _attributeStorageFactory = ExtendedMemorizingSupplier.of(() -> createAttributeStorageFactory());
	private final ConcurrentHashMap<ViewRowAttributesKey, HUEditorRowAttributes> rowAttributesByKey = new ConcurrentHashMap<>();

	@Value
	private static final class ViewRowAttributesKey
	{
		private DocumentId huEditorRowId;
		private DocumentId huId;
	}

	@Builder
	private HUEditorRowAttributesProvider(final boolean readonly)
	{
		this.readonly = readonly;
	}

	private boolean isReadonly()
	{
		return readonly;
	}

	public DocumentId createAttributeKey(final HuId huId)
	{
		return DocumentId.of(huId);
	}

	@Override
	public HUEditorRowAttributes getAttributes(final DocumentId viewRowId, final DocumentId huId)
	{
		final ViewRowAttributesKey key = new ViewRowAttributesKey(viewRowId, huId);
		return rowAttributesByKey.computeIfAbsent(key, this::createRowAttributes);
	}

	private HUEditorRowAttributes createRowAttributes(final ViewRowAttributesKey key)
	{
		final I_M_HU hu = extractHU(key);
		final IAttributeStorage attributesStorage = getAttributeStorageFactory().getAttributeStorage(hu);
		attributesStorage.setSaveOnChange(true);

		final boolean rowAttributesReadonly = isReadonly() // readonly if the provider shall provide readonly attributes
				|| !X_M_HU.HUSTATUS_Planning.equals(hu.getHUStatus()); // or, readonly if not Planning, see https://github.com/metasfresh/metasfresh-webui-api/issues/314

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		final IHUStorage storage = storageFactory.getStorage(hu);

		final ImmutableSet<ProductId> productIDs = storage.getProductStorages()
				.stream()
				.map(IHUProductStorage::getProductId)
				.collect(ImmutableSet.toImmutableSet());

		final DocumentPath documentPath = createDocumentPath(key);
		return new HUEditorRowAttributes(documentPath, attributesStorage, productIDs, rowAttributesReadonly);
	}

	private I_M_HU extractHU(final ViewRowAttributesKey key)
	{
		final HuId huId = HuId.ofRepoId(key.getHuId().toInt());

		final IHandlingUnitsDAO handlingUnitsRepo = Services.get(IHandlingUnitsDAO.class);
		final I_M_HU hu = handlingUnitsRepo.getByIdOutOfTrx(huId);
		if (hu == null)
		{
			throw new IllegalArgumentException("No HU found for M_HU_ID=" + huId);
		}

		return hu;
	}

	private static DocumentPath createDocumentPath(final ViewRowAttributesKey key)
	{
		final DocumentId documentTypeId = key.getHuId();
		final DocumentId huEditorRowId = key.getHuEditorRowId();
		return DocumentPath.rootDocumentPath(DocumentType.ViewRecordAttributes, documentTypeId, huEditorRowId);
	}

	private IAttributeStorageFactory getAttributeStorageFactory()
	{
		return _attributeStorageFactory.get();
	}

	private final IAttributeStorageFactory createAttributeStorageFactory()
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);

		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		final IAttributeStorageFactory huAttributeStorageFactory = attributeStorageFactoryService
				.createHUAttributeStorageFactory(storageFactory);

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
		rowAttributesByKey.clear();
	}
}
