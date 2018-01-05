package de.metas.ui.web.handlingunits;

import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.util.Env;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.ui.web.view.IViewRowAttributesProvider;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
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

	public DocumentId createAttributeKey(final int huId)
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
		final int huId = key.getHuId().toInt();
		final I_M_HU hu = InterfaceWrapperHelper.create(Env.getCtx(), huId, I_M_HU.class, ITrx.TRXNAME_None);
		if (hu == null)
		{
			throw new IllegalArgumentException("No HU found for M_HU_ID=" + huId);
		}

		final IAttributeStorage attributesStorage = getAttributeStorageFactory().getAttributeStorage(hu);
		attributesStorage.setSaveOnChange(true);

		final DocumentId documentTypeId = DocumentId.of(huId);
		final DocumentId huEditorRowId = key.getHuEditorRowId();
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(DocumentType.ViewRecordAttributes, documentTypeId, huEditorRowId);

		final boolean rowAttributesReadonly = isReadonly() // readonly if the provider shall provide readonly attributes
				|| !X_M_HU.HUSTATUS_Planning.equals(hu.getHUStatus()); // or, readonly if not Planning, see https://github.com/metasfresh/metasfresh-webui-api/issues/314

		return new HUEditorRowAttributes(documentPath, attributesStorage, rowAttributesReadonly);
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
