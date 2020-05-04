package de.metas.handlingunits.client.terminal.editor.model;

/*
 * #%L
 * de.metas.handlingunits.client
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.Collection;
import java.util.List;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.ITerminalContextService;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.attribute.IWeightable;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKeyAttributeStorageFactory;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.document.IHUDocumentLineFinder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUStorageFactory;

/**
 * Use {@link ITerminalContext#getService(Class)} to get an instance.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IHUKeyFactory extends ITerminalContextService
{
	IHUStorageFactory getStorageFactory();

	HUKeyAttributeStorageFactory getAttributeStorageFactory();

	/**
	 * Flush existing objects to database and clears internal caches.
	 *
	 * To be used when you have changed HUs or their attributes outside of GUI.
	 */
	void clearCache();

	/**
	 * Creates the root key, which does not represent a particular HU.
	 *
	 * @return root key
	 */
	IHUKey createRootKey();

	/**
	 * Creates the root key and populates it with the HUs retrieved by given query.
	 *
	 * @param husQuery
	 * @param documentLineFinder
	 * @return root key
	 */
	IHUKey createRootKey(IHUQueryBuilder husQuery, IHUDocumentLineFinder documentLineFinder);

	/**
	 * @param hu
	 * @param documentLine please set it only on direct references; else leave it null
	 * @return created HUKey
	 */
	IHUKey createKey(I_M_HU hu, IHUDocumentLine documentLine);

	/**
	 * @param hus
	 * @param documentLine please set it only on direct references; else leave it null
	 * @return
	 */
	List<IHUKey> createKeys(Collection<I_M_HU> hus, IHUDocumentLine documentLine);

	/**
	 * @param hus
	 * @param documentLineFinder
	 * @return
	 */
	List<IHUKey> createKeys(Collection<I_M_HU> hus, IHUDocumentLineFinder documentLineFinder);

	/**
	 * Create and bind children on given key.
	 *
	 * @param key parent key; shall never be null. Using direct implementation.
	 * @return children
	 */
	List<IHUKey> createChildKeys(HUKey key);

	/**
	 * @param hu
	 * @param documentLine
	 * @return key for virtual HU with product and parent HU information (i.e display IFCO ID and Product x Qty)
	 */
	IHUKey createVirtualKeyWithParent(I_M_HU hu, IHUDocumentLine documentLine);

	/**
	 * Starting from given <code>rootKey</code>:
	 * <ul>
	 * <li>assign all
	 * <li>un-assign given original allocated HUs
	 * </ul>
	 *
	 * @param rootKey contains the HUs which are currently in {@link HUEditorModel} and needs to be allocated to documents
	 * @param originalAllocatedHUs HUs which were initially allocated
	 */
	void createHUAllocations(IHUKey rootKey, Collection<I_M_HU> originalAllocatedHUs);

	/**
	 * Gets the weight of given {@link IHUKey}.
	 *
	 * @param key
	 * @return weight or null
	 */
	IWeightable getWeightOrNull(IHUKey key);
}
