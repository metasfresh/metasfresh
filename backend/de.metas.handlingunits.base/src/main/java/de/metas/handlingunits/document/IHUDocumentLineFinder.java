package de.metas.handlingunits.document;

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


import de.metas.handlingunits.model.I_M_HU;

/**
 * Implementations of this interface are responsbile for providing {@link IHUDocumentLine} for a given {@link I_M_HU}.
 *
 * @author tsa
 *
 */
public interface IHUDocumentLineFinder
{
	/**
	 * Finds corresponding {@link IHUDocumentLine} for given {@link I_M_HU}.
	 *
	 * @param hu
	 * @return corresponding HU document line or null.
	 */
	IHUDocumentLine findHUDocumentLine(final I_M_HU hu);
}
