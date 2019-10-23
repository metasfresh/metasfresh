package de.metas.adempiere.docline.sort.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.compiere.model.I_C_DocLine_Sort;
import org.compiere.model.I_C_DocLine_Sort_Item;

import de.metas.util.ISingletonService;

/**
 * Document Line Sort Preferences DAO
 *
 * @author al
 */
public interface IDocLineSortDAO extends ISingletonService
{
	/**
	 * @param docLineSort
	 * @return document line sort items for sort header configuration
	 */
	List<I_C_DocLine_Sort_Item> retrieveItems(I_C_DocLine_Sort docLineSort);

	/**
	 * @return {@link I_C_DocLine_Sort_Item} finder
	 */
	IDocLineSortItemFinder findDocLineSort();
}
