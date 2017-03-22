package de.metas.document.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Sequence;
import org.compiere.model.I_AD_Sequence_No;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_DocType_Sequence;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.adempiere.util.CacheCtx;
import de.metas.document.DocTypeSequenceMap;
import de.metas.document.DocumentSequenceInfo;
import de.metas.document.IDocumentSequenceDAO;

public class DocumentSequenceDAO implements IDocumentSequenceDAO
{
	private static final String SQL_AD_Sequence_CurrentNext = "SELECT " + I_AD_Sequence.COLUMNNAME_CurrentNext
			+ " FROM " + I_AD_Sequence.Table_Name
			+ " WHERE " + I_AD_Sequence.COLUMNNAME_AD_Sequence_ID + "=?";
	private static final String SQL_AD_Sequence_CurrentNextSys = "SELECT " + I_AD_Sequence.COLUMNNAME_CurrentNextSys
			+ " FROM " + I_AD_Sequence.Table_Name
			+ " WHERE " + I_AD_Sequence.COLUMNNAME_AD_Sequence_ID + "=?";
	private static final String SQL_AD_Sequence_No_CurrentNext = "SELECT " + I_AD_Sequence_No.COLUMNNAME_CurrentNext
			+ " FROM " + I_AD_Sequence_No.Table_Name
			+ " WHERE " + I_AD_Sequence_No.COLUMNNAME_AD_Sequence_ID + "=? AND " + I_AD_Sequence_No.COLUMNNAME_CalendarYear + "=?";

	@Override
	@Cached(cacheName = I_AD_Sequence.Table_Name + "#DocumentSequenceInfo#By#SequenceName")
	public DocumentSequenceInfo retriveDocumentSequenceInfo(final String sequenceName, final int adClientId, final int adOrgId)
	{
		final IQueryBuilder<I_AD_Sequence> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Sequence.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Sequence.COLUMNNAME_IsTableID, false)
				.addEqualsFilter(I_AD_Sequence.COLUMNNAME_AD_Client_ID, adClientId)
				.addEqualsFilter(I_AD_Sequence.COLUMNNAME_Name, sequenceName);

		//
		// Only for given organization or organization "*" (fallback).
		queryBuilder.addInArrayOrAllFilter(I_AD_Sequence.COLUMNNAME_AD_Org_ID, adOrgId, 0);
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

	@Override
	public String retrieveDocumentNo(final int AD_Sequence_ID)
	{
		return DB.getSQLValueStringEx(ITrx.TRXNAME_None, SQL_AD_Sequence_CurrentNext, AD_Sequence_ID);
	}

	@Override
	public String retrieveDocumentNoSys(final int AD_Sequence_ID)
	{
		return DB.getSQLValueStringEx(ITrx.TRXNAME_None, SQL_AD_Sequence_CurrentNextSys, AD_Sequence_ID);
	}

	@Override
	public String retrieveDocumentNoByYear(final int AD_Sequence_ID, java.util.Date date)
	{
		if (date == null)
		{
			date = new Date();
		}
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		final String calendarYear = sdf.format(date);

		return DB.getSQLValueStringEx(ITrx.TRXNAME_None, SQL_AD_Sequence_No_CurrentNext, AD_Sequence_ID, calendarYear);
	}

	@Override
	public DocTypeSequenceMap retrieveDocTypeSequenceMap(final I_C_DocType docType)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(docType);
		final int docTypeId = docType.getC_DocType_ID();
		return retrieveDocTypeSequenceMap(ctx, docTypeId);
	}

	@Cached(cacheName = I_C_DocType_Sequence.Table_Name + "#by#" + I_C_DocType_Sequence.COLUMNNAME_C_DocType_ID)
	public DocTypeSequenceMap retrieveDocTypeSequenceMap(@CacheCtx final Properties ctx, final int docTypeId)
	{
		final DocTypeSequenceMap.Builder docTypeSequenceMapBuilder = DocTypeSequenceMap.builder();

		final I_C_DocType docType = InterfaceWrapperHelper.create(ctx, docTypeId, I_C_DocType.class, ITrx.TRXNAME_None);
		docTypeSequenceMapBuilder.setDefaultDocNoSequence_ID(docType.getDocNoSequence_ID());

		final List<I_C_DocType_Sequence> docTypeSequenceDefs = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_DocType_Sequence.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_DocType_Sequence.COLUMNNAME_C_DocType_ID, docTypeId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_C_DocType_Sequence.class);

		for (final I_C_DocType_Sequence docTypeSequenceDef : docTypeSequenceDefs)
		{
			final int adClientId = docTypeSequenceDef.getAD_Client_ID();
			final int adOrgId = docTypeSequenceDef.getAD_Org_ID();
			final int docSequenceId = docTypeSequenceDef.getDocNoSequence_ID();
			docTypeSequenceMapBuilder.addDocSequenceId(adClientId, adOrgId, docSequenceId);
		}

		return docTypeSequenceMapBuilder.build();
	}
}
