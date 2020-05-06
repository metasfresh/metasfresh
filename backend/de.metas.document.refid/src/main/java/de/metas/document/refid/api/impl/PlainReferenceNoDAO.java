package de.metas.document.refid.api.impl;

/*
 * #%L
 * de.metas.document.refid
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.wrapper.IPOJOFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MTable;

import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.document.refid.model.I_C_ReferenceNo_Type_Table;

public class PlainReferenceNoDAO extends AbstractReferenceNoDAO
{

	private final POJOLookupMap lookupMap = POJOLookupMap.get();

	@Override
	public List<I_C_ReferenceNo_Type_Table> retrieveTableAssignments(I_C_ReferenceNo_Type type)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<I_C_ReferenceNo_Doc> retrieveAllDocAssignments(Properties ctx, int referenceNoTypeId, int tableId, int recordId, String trxName)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<I_C_ReferenceNo> retrieveReferenceNos(final Object model, final I_C_ReferenceNo_Type type)
	{
		// get all I_C_ReferenceNo_Docs that references 'model'
		final List<I_C_ReferenceNo_Doc> docs = lookupMap.getRecords(I_C_ReferenceNo_Doc.class, new IPOJOFilter<I_C_ReferenceNo_Doc>()
		{
			@Override
			public boolean accept(I_C_ReferenceNo_Doc pojo)
			{
				return pojo.getRecord_ID() == InterfaceWrapperHelper.getId(model)
						&& pojo.getAD_Table_ID() == MTable.getTable_ID(InterfaceWrapperHelper.getModelTableName(model));
			}
		});

		// add the C_ReferenceNos into a set to avoid duplicates
		final Set<I_C_ReferenceNo> refNos = new HashSet<>();

		for (final I_C_ReferenceNo_Doc doc : docs)
		{
			final I_C_ReferenceNo referenceNo = doc.getC_ReferenceNo();
			if (referenceNo.getC_ReferenceNo_Type_ID() == type.getC_ReferenceNo_Type_ID())
			{
				refNos.add(referenceNo);
			}
		}

		return new ArrayList<>(refNos);
	}

	@Override
	protected List<I_C_ReferenceNo_Doc> retrieveRefNoDocByRefNoAndTableName(final I_C_ReferenceNo referenceNo, final String tableName)
	{
		return lookupMap.getRecords(I_C_ReferenceNo_Doc.class, new IPOJOFilter<I_C_ReferenceNo_Doc>()
		{
			@Override
			public boolean accept(I_C_ReferenceNo_Doc pojo)
			{
				return pojo.getC_ReferenceNo_ID() == referenceNo.getC_ReferenceNo_ID()
						&& pojo.getAD_Table_ID() == MTable.getTable_ID(tableName);
			}
		});
	}

}
