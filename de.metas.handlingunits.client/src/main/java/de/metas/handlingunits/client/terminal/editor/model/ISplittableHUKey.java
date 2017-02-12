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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;

/**
 * An {@link IHUKey} which can be split.
 *
 * @author tsa
 *
 */
public interface ISplittableHUKey extends IHUKey
{
	/**
	 *
	 * @return underlying HU; never return {@code null}.
	 */
	I_M_HU getM_HU();

	/**
	 *
	 * @return own documentLine or parent's document line
	 */
	IHUDocumentLine findDocumentLineOrNull();

	/**
	 *
	 * @return underlying {@link IHUProductStorage}s of this key
	 */
	List<IHUProductStorage> getProductStorages();

	/**
	 * Forcefully remove this key from it's parent
	 */
	void removeFromParent();

	/**
	 * Checks the underlying HU and destroys it if it has empty storage.
	 * 
	 * Also, in case it was destroyed, this key will be removed from it's parent.
	 */
	boolean destroyIfEmptyStorage();

	/**
	 * Create {@link IAllocationSource} of this key. To be used when you want to transfer Qty from this key.
	 *
	 * @return {@link IAllocationSource}; never return null
	 */
	IAllocationSource createAllocationSource();
}
