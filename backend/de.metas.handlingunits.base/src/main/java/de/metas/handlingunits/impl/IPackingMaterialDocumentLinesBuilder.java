package de.metas.handlingunits.impl;

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


import de.metas.handlingunits.IPackingMaterialDocumentLineSource;

/**
 * Implementations of this interface are able to produce packing material document lines.
 *
 */
public interface IPackingMaterialDocumentLinesBuilder
{

	void addSource(IPackingMaterialDocumentLineSource source);

	/**
	 * Create Packing Material Document Lines, update source lines and set the link to packing material lines.
	 */
	IPackingMaterialDocumentLinesBuilder create();

	/**
	 * Check if this builder is empty.
	 *
	 * A builder is considered empty, when there are no packing material document lines.
	 *
	 * @return true if empty.
	 */
	boolean isEmpty();

}
