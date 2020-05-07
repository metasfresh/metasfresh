package de.metas.printing.api;

/*
 * #%L
 * de.metas.printing.base
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

import org.adempiere.ad.dao.ISqlQueryFilter;

/**
 * Used in {@link IPrintingQueueBL#createPrintingQueueSources(java.util.Properties, IPrintingQueueQuery)} to specify which printing queue items shall be returned by the source.<br>
 * Use {@link IPrintingQueueBL#createPrintingQueueQuery()} to create an instance.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IPrintingQueueQuery
{
	IPrintingQueueQuery copy();

	Boolean getIsPrinted();

	/**
	 * Filter by if item was printed or not
	 * 
	 * @param printed
	 *            <ul>
	 *            <li>if true, only queue items which were already printed are selected
	 *            <li>if false, only queue items which were not printed are selected
	 *            <li>if null, no filtering will be applied
	 *            </ul>
	 */
	void setIsPrinted(Boolean printed);

	int getOnlyAD_PInstance_ID();

	void setOnlyAD_PInstance_ID(int onlyAD_PInstance_ID);

	int getAD_Client_ID();

	void setAD_Client_ID(int aD_Client_ID);

	int getAD_Org_ID();

	void setAD_Org_ID(int aD_Org_ID);

	int getAD_User_ID();

	void setAD_User_ID(int aD_User_ID);

	int getIgnoreC_Printing_Queue_ID();

	/**
	 * Explicitly exclude the given printing queue ID form the result.
	 * 
	 * @param ignoreC_Printing_Queue_ID
	 */
	void setIgnoreC_Printing_Queue_ID(int ignoreC_Printing_Queue_ID);

	ISqlQueryFilter getFilter();

	void setFilter(ISqlQueryFilter filter);

	void setModelToRecordId(int modelToRecordId);

	int getModelToRecordId();

	void setModelFromRecordId(int modelFromRecordId);

	int getModelFromRecordId();

	void setModelTableId(int modelTableId);

	int getModelTableId();

	ISqlQueryFilter getModelFilter();

	void setModelFilter(ISqlQueryFilter modelFilter);

	Boolean getApplyAccessFilterRW();

	void setApplyAccessFilterRW(Boolean applyAccessFilterRW);

	/**
	 * 
	 * @return the number of copies to filter by, or <code>null</code> if there is no such constraint.
	 */
	Integer getCopies();

	void setCopies(int copies);

	String getAggregationKey();

	void setAggregationKey(String aggregationKey);
}
