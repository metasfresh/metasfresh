package de.metas.adempiere.docline.sort.api.impl;

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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.comparator.NullComparator;
import org.compiere.model.I_C_BP_DocLine_Sort;
import org.compiere.model.I_C_DocLine_Sort;
import org.compiere.model.I_C_DocType;

import de.metas.adempiere.docline.sort.api.IDocLineSortItemFinder;
import de.metas.util.Check;
import de.metas.util.Services;

/* package */final class DocLineSortItemFinder implements IDocLineSortItemFinder
{
	//
	// Services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private Properties _ctx = null;

	private boolean _docTypeSet = false;
	private String _docBaseType = null;
	private I_C_DocType _docType = null;

	private boolean _bpartnerIdSet = false;
	private int _bpartnerId = -1;

	public DocLineSortItemFinder()
	{
		super();
	}

	@Override
	public I_C_DocLine_Sort find()
	{
		//
		// BPartner query builder
		final IQueryBuilder<I_C_BP_DocLine_Sort> bpQueryBuilder = queryBL.createQueryBuilder(I_C_BP_DocLine_Sort.class, getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter();

		bpQueryBuilder.addEqualsFilter(I_C_BP_DocLine_Sort.COLUMN_C_BPartner_ID, getC_BPartner_ID());

		//
		// Collect header
		final IQueryBuilder<I_C_DocLine_Sort> docLineQueryBuilder = bpQueryBuilder
				.andCollect(I_C_BP_DocLine_Sort.COLUMN_C_DocLine_Sort_ID, I_C_DocLine_Sort.class)
				.addOnlyActiveRecordsFilter();

		//
		// IsDefault
		docLineQueryBuilder.addEqualsFilter(I_C_DocLine_Sort.COLUMN_IsDefault, false);

		//
		// DocBaseType
		docLineQueryBuilder.addEqualsFilter(I_C_DocLine_Sort.COLUMN_DocBaseType, getDocBaseType());

		final boolean existsBPDocLineSortConfig = docLineQueryBuilder
				.create()
				.anyMatch();
		if (existsBPDocLineSortConfig)
		{
			return docLineQueryBuilder
					.create()
					.firstOnly(I_C_DocLine_Sort.class);
		}

		//
		// Retrieve default if no match was found
		final IQueryBuilder<I_C_DocLine_Sort> docLineDefaultQueryBuilder = queryBL.createQueryBuilder(I_C_DocLine_Sort.class, getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter();

		//
		// IsDefault
		docLineDefaultQueryBuilder.addEqualsFilter(I_C_DocLine_Sort.COLUMN_IsDefault, true);

		//
		// DocBaseType
		docLineDefaultQueryBuilder.addEqualsFilter(I_C_DocLine_Sort.COLUMN_DocBaseType, getDocBaseType());

		//
		// Return default (if any)
		return docLineDefaultQueryBuilder
				.create()
				.firstOnly(I_C_DocLine_Sort.class);
	}

	@Override
	public Comparator<Integer> findProductIdsComparator()
	{
		final I_C_DocLine_Sort docLineSort = find();
		if (docLineSort == null)
		{
			return NullComparator.getInstance();
		}

		return new DocLineSortProductIdsComparator(docLineSort);
	}

	@Override
	public IDocLineSortItemFinder setContext(final Properties ctx)
	{
		_ctx = ctx;
		return this;
	}

	private final Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "_ctx not null");
		return _ctx;
	}

	@Override
	public DocLineSortItemFinder setDocBaseType(final String docBaseType)
	{
		_docTypeSet = true;
		_docBaseType = docBaseType;
		return this;
	}

	@Override
	public DocLineSortItemFinder setC_DocType(final I_C_DocType docType)
	{
		_docTypeSet = true;
		_docType = docType;
		return this;
	}

	private final String getDocBaseType()
	{
		Check.assume(_docTypeSet, "DocType or DocbaseType was set");

		if (_docType != null)
		{
			return _docType.getDocBaseType();
		}
		else if (_docBaseType != null)
		{
			return _docBaseType;
		}
		else
		{
			// throw new AdempiereException("DocBaseType not found"); // can be null
			return null;
		}
	}

	@Override
	public DocLineSortItemFinder setC_BPartner_ID(final int bpartnerId)
	{
		_bpartnerIdSet = true;
		_bpartnerId = bpartnerId;
		return this;
	}

	private final int getC_BPartner_ID()
	{
		Check.assume(_bpartnerIdSet, "C_BPartner_ID was set");
		return _bpartnerId;
	}
}
