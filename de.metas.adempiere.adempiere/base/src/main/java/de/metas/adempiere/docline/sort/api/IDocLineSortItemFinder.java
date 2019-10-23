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


import java.util.Comparator;
import java.util.Properties;

import org.compiere.model.I_C_DocLine_Sort;
import org.compiere.model.I_C_DocType;

/**
 * Document Line Sort finder builder used in data retrieval. Just add more criteria when needed.
 *
 * @author al
 */
public interface IDocLineSortItemFinder
{
	/**
	 * @return DocLine sort header
	 */
	I_C_DocLine_Sort find();

	/**
	 * Returns the comparator suitable to order document lines by their M_Product_IDs. If no such comparator is available, then the {@link org.adempiere.util.comparator.NullComparator} is returned.
	 * 
	 * @return productId comparator of finder. Never returns <code>null</code>.
	 */
	Comparator<Integer> findProductIdsComparator();

	/**
	 * @param docBaseType
	 * @return IDocLineSortItemFinder
	 */
	IDocLineSortItemFinder setDocBaseType(String docBaseType);

	/**
	 * @param ctx
	 * @return finder
	 */
	IDocLineSortItemFinder setContext(Properties ctx);

	/**
	 * @param bpartnerId
	 * @return finder
	 */
	IDocLineSortItemFinder setC_BPartner_ID(int bpartnerId);

	/**
	 * @param docType
	 * @return finder
	 */
	IDocLineSortItemFinder setC_DocType(I_C_DocType docType);
}
