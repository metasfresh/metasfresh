package org.adempiere.document.service.impl;

/*
 * #%L
 * ADempiere ERP - Base
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


import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.document.service.DocumentSequenceInfo;
import org.adempiere.document.service.IDocumentSequenceDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Sequence;
import org.compiere.util.Env;

public class DocumentSequenceDAO implements IDocumentSequenceDAO
{
	@Override
	@Cached(cacheName = I_AD_Sequence.Table_Name + "#DocumentSequenceInfo#By#SequenceName")
	public DocumentSequenceInfo retriveDocumentSequenceInfo(final String sequenceName, final int adClientId, final int adOrgId)
	{
		final IQueryBuilder<I_AD_Sequence> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Sequence.class)
				.setContext(Env.getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Sequence.COLUMNNAME_IsTableID, false)
				.addEqualsFilter(I_AD_Sequence.COLUMNNAME_AD_Client_ID, adClientId)
				.addEqualsFilter(I_AD_Sequence.COLUMNNAME_Name, sequenceName);

		//
		// Only for given organization or organization "*" (fallback).
		queryBuilder.addInArrayFilter(I_AD_Sequence.COLUMNNAME_AD_Org_ID, adOrgId, 0);
		queryBuilder.orderBy()
				.addColumn(I_AD_Sequence.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last); // make sure we get for our particular org first

		final I_AD_Sequence adSequence = queryBuilder.create().first(I_AD_Sequence.class);
		if (adSequence == null)
		{
			// TODO: shall not happen but it's safe to create AD_Sequence
			throw new AdempiereException("@NotFound@ @AD_Sequence_ID@ (@Name@: " + sequenceName + ")");
		}

		return DocumentSequenceInfo.of(adSequence);
	}

	@Override
	@Cached(cacheName = I_AD_Sequence.Table_Name + "#DocumentSequenceInfo#By#AD_Sequence_ID")
	public DocumentSequenceInfo retriveDocumentSequenceInfo(final int adSequenceId)
	{
		if (adSequenceId <= 0)
		{
			return null;
		}

		final I_AD_Sequence adSequence = InterfaceWrapperHelper.create(Env.getCtx(), adSequenceId, I_AD_Sequence.class, ITrx.TRXNAME_None);
		Check.assumeNotNull(adSequence, "adSequence not null");

		if (!adSequence.isActive())
		{
			return null;
		}
		if (adSequence.isTableID())
		{
			return null;
		}

		return DocumentSequenceInfo.of(adSequence);
	}
}
