package de.metas.handlingunits.attribute.strategy;

/*
 * #%L
 * de.metas.handlingunits.base
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

import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;

/**
 * Splitting request
 *
 * @author tsa
 *
 */
public interface IAttributeSplitRequest
{
	IAttributeStorage getParentAttributeStorage();

	List<IAttributeStorage> getAttributeStorages();

	/**
	 * Gets current attribute storage
	 *
	 * @return
	 */
	IAttributeStorage getAttributeStorageCurrent();

	/**
	 * Gets index of {@link #getAttributeStorageCurrent()} in {@link #getAttributeStorages()} list
	 *
	 * @return current index
	 */
	int getAttributeStorageCurrentIndex();

	I_M_Attribute getM_Attribute();

	/**
	 *
	 * @return initial value to split
	 */
	Object getValueInitial();

	/**
	 *
	 * @return value to be split by current splitter
	 */
	Object getValueToSplit();
}
