package de.metas.ui.web.handlingunits;

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
import org.adempiere.util.lang.ExtendedMemorizingSupplier;

import java.util.concurrent.ConcurrentHashMap;

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
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsRepo = Services.get(IHandlingUnitsDAO.class);
	private final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);

	private final boolean readonly;
	private final boolean isMaterialReceipt;

	private final ExtendedMemorizingSupplier<IAttributeStorageFactory> _attributeStorageFactory = ExtendedMemorizingSupplier.of(this::createAttributeStorageFactory);
	private final ConcurrentHashMap<ViewRowAttributesKey, HUEditorRowAttributes> rowAttributesByKey = new ConcurrentHashMap<>();

	@Value
	private static class ViewRowAttributesKey
	{
		DocumentId huEditorRowId;
		DocumentId huId;
	}

	@Builder
	private HUEditorRowAttributesProvider(
			final boolean readonly,
			final boolean isMaterialReceipt)
	{
		this.readonly = readonly;
		this.isMaterialReceipt = isMaterialReceipt;
	}

	DocumentId createAttributeKey(final HuId huId)
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

		final boolean rowAttributesReadonly = readonly // readonly if the provider shall provide readonly attributes
				|| !X_M_HU.HUSTATUS_Planning.equals(hu.getHUStatus()); // or, readonly if not Planning, see https://github.com/metasfresh/metasfresh-webui-api/issues/314

		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		final IHUStorage storage = storageFactory.getStorage(hu);

		return HUEditorRowAttributes.builder()
				.documentPath(toDocumentPath(key))
				.attributesStorage(attributesStorage)
				.productIds(extractProductIds(storage))
				.hu(hu)
				.readonly(rowAttributesReadonly)
				.isMaterialReceipt(isMaterialReceipt)
				.build();
	}

	private ImmutableSet<ProductId> extractProductIds(final IHUStorage storage)
	{
		return storage.getProductStorages()
				.stream()
				.map(IHUProductStorage::getProductId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private I_M_HU extractHU(final ViewRowAttributesKey key)
	{
		final HuId huId = HuId.ofRepoId(key.getHuId().toInt());

		final I_M_HU hu = handlingUnitsRepo.getByIdOutOfTrx(huId);
		if (hu == null)
		{
			throw new IllegalArgumentException("No HU found for M_HU_ID=" + huId);
		}

		return hu;
	}

	private static DocumentPath toDocumentPath(final ViewRowAttributesKey key)
	{
		final DocumentId documentTypeId = key.getHuId();
		final DocumentId huEditorRowId = key.getHuEditorRowId();
		return DocumentPath.rootDocumentPath(DocumentType.ViewRecordAttributes, documentTypeId, huEditorRowId);
	}

	private IAttributeStorageFactory getAttributeStorageFactory()
	{
		return _attributeStorageFactory.get();
	}

	private IAttributeStorageFactory createAttributeStorageFactory()
	{
		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		return attributeStorageFactoryService.createHUAttributeStorageFactory(storageFactory);
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
