package de.metas.document.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Sequence;
import org.compiere.model.I_AD_Sequence_No;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_DocType_Sequence;
import org.compiere.util.DB;
import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import javax.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public class DocumentSequenceDAO implements IDocumentSequenceDAO
{
	private static final Logger logger = LogManager.getLogger(DocumentSequenceDAO.class);

	private static final String SQL_AD_Sequence_CurrentNext = "SELECT " + I_AD_Sequence.COLUMNNAME_CurrentNext
			+ " FROM " + I_AD_Sequence.Table_Name
			+ " WHERE " + I_AD_Sequence.COLUMNNAME_AD_Sequence_ID + "=?";
	private static final String SQL_AD_Sequence_CurrentNextSys = "SELECT " + I_AD_Sequence.COLUMNNAME_CurrentNextSys
			+ " FROM " + I_AD_Sequence.Table_Name
			+ " WHERE " + I_AD_Sequence.COLUMNNAME_AD_Sequence_ID + "=?";
	private static final String SQL_AD_Sequence_No_CurrentNext = "SELECT " + I_AD_Sequence_No.COLUMNNAME_CurrentNext
			+ " FROM " + I_AD_Sequence_No.Table_Name
			+ " WHERE " + I_AD_Sequence_No.COLUMNNAME_AD_Sequence_ID + "=? AND "
			+ I_AD_Sequence_No.COLUMNNAME_CalendarYear + "=? AND "
			+ I_AD_Sequence_No.COLUMNNAME_CalendarMonth + "= '1' AND "
			+ I_AD_Sequence_No.COLUMNNAME_CalendarDay + "= '1'";

	private static final String SQL_AD_SEQUENCE_NO_BY_YEAR_MONTH = "SELECT " + I_AD_Sequence_No.COLUMNNAME_CurrentNext
			+ " FROM " + I_AD_Sequence_No.Table_Name
			+ " WHERE " + I_AD_Sequence_No.COLUMNNAME_AD_Sequence_ID + "=? AND "
			+ I_AD_Sequence_No.COLUMNNAME_CalendarYear + "=? AND "
			+ I_AD_Sequence_No.COLUMNNAME_CalendarMonth + "=? AND "
			+ I_AD_Sequence_No.COLUMNNAME_CalendarDay + "= '1'";

	private static final String SQL_AD_SEQUENCE_NO_BY_YEAR_MONTH_DAY = "SELECT " + I_AD_Sequence_No.COLUMNNAME_CurrentNext
			+ " FROM " + I_AD_Sequence_No.Table_Name
			+ " WHERE " + I_AD_Sequence_No.COLUMNNAME_AD_Sequence_ID + "=? AND "
			+ I_AD_Sequence_No.COLUMNNAME_CalendarYear + "=? AND "
			+ I_AD_Sequence_No.COLUMNNAME_CalendarMonth + "=? AND "
			+ I_AD_Sequence_No.COLUMNNAME_CalendarDay + "=?";

	@Override
	@Cached(cacheName = I_AD_Sequence.Table_Name + "#DocumentSequenceInfo#By#SequenceName")
	public DocumentSequenceInfo retriveDocumentSequenceInfo(@NonNull final String sequenceName, final int adClientId, final int adOrgId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_Sequence.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Sequence.COLUMNNAME_IsTableID, false)
				.addEqualsFilter(I_AD_Sequence.COLUMNNAME_AD_Client_ID, adClientId)
				.addEqualsFilter(I_AD_Sequence.COLUMNNAME_Name, sequenceName)
				.addInArrayFilter(I_AD_Sequence.COLUMNNAME_AD_Org_ID, adOrgId, 0)
				.orderBy().addColumn(I_AD_Sequence.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last).endOrderBy() // make sure we get for our particular org first
				.create()
				.firstOptional(I_AD_Sequence.class)
				.map(DocumentSequenceDAO::toDocumentSequenceInfo)
				.orElseGet(() -> createDocumentSequence(ClientId.ofRepoId(adClientId), sequenceName));
	}

	private DocumentSequenceInfo createDocumentSequence(final ClientId adClientId, final String sequenceName)
	{
		final I_AD_Sequence record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_AD_Sequence.class);
		InterfaceWrapperHelper.setValue(record, I_AD_Sequence.COLUMNNAME_AD_Client_ID, adClientId);
		record.setAD_Org_ID(OrgId.ANY.getRepoId()); // Client Ownership
		record.setName(sequenceName);
		InterfaceWrapperHelper.save(record);
		return toDocumentSequenceInfo(record);
	}    // MSequence;

	@Nullable
	@Override
	@Cached(cacheName = I_AD_Sequence.Table_Name + "#DocumentSequenceInfo#By#AD_Sequence_ID")
	public DocumentSequenceInfo retriveDocumentSequenceInfo(@NonNull final DocSequenceId sequenceId)
	{
		final I_AD_Sequence adSequence = loadOutOfTrx(sequenceId, I_AD_Sequence.class);
		Check.assumeNotNull(adSequence, "adSequence not null");

		if (!adSequence.isActive())
		{
			return null;
		}
		if (adSequence.isTableID())
		{
			return null;
		}

		return toDocumentSequenceInfo(adSequence);
	}

	private static DocumentSequenceInfo toDocumentSequenceInfo(final I_AD_Sequence record)
	{
		return DocumentSequenceInfo.builder()
				.adSequenceId(record.getAD_Sequence_ID())
				.name(record.getName())
				//
				.incrementNo(record.getIncrementNo())
				.prefix(compileStringExpressionOrUseItAsIs(record.getPrefix()))
				.suffix(compileStringExpressionOrUseItAsIs(record.getSuffix()))
				.decimalPattern(record.getDecimalPattern())
				.autoSequence(record.isAutoSequence())
				.restartFrequency(SequenceRestartFrequencyEnum.ofNullableCode(record.getRestartFrequency()))
				.dateColumn(record.getDateColumn())
				//
				.customSequenceNoProvider(createCustomSequenceNoProviderOrNull(record))
				//
				.build();
	}

	private IStringExpression compileStringExpressionOrUseItAsIs(final String expr)
	{
		try
		{
			return StringExpressionCompiler.instance.compile(expr);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed compiling '{}' string expression. Using it as is", expr, ex);
			return ConstantStringExpression.ofNullable(expr);
		}
	}

	@Nullable
	private static CustomSequenceNoProvider createCustomSequenceNoProviderOrNull(final I_AD_Sequence adSequence)
	{
		if (adSequence.getCustomSequenceNoProvider_JavaClass_ID() <= 0)
		{
			return null;
		}

		final IJavaClassBL javaClassBL = Services.get(IJavaClassBL.class);
		final JavaClassId javaClassId = JavaClassId.ofRepoId(adSequence.getCustomSequenceNoProvider_JavaClass_ID());
		return javaClassBL.newInstance(javaClassId);
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
	public String retrieveDocumentNoByYearAndMonth(final int AD_Sequence_ID, java.util.Date date)
	{
		if (date == null)
		{
			date = new Date();
		}
		final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		final String calendarYear = yearFormat.format(date);

		final SimpleDateFormat monthFormat = new SimpleDateFormat("M");
		final String calendarMonth = monthFormat.format(date);

		return DB.getSQLValueStringEx(ITrx.TRXNAME_None, SQL_AD_SEQUENCE_NO_BY_YEAR_MONTH, AD_Sequence_ID, calendarYear, calendarMonth);
	}

	@Override
	public String retrieveDocumentNoByYearMonthAndDay(final int AD_Sequence_ID, java.util.Date date)
	{
		if (date == null)
		{
			date = new Date();
		}
		final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		final String calendarYear = yearFormat.format(date);

		final SimpleDateFormat monthFormat = new SimpleDateFormat("M");
		final String calendarMonth = monthFormat.format(date);

		final SimpleDateFormat dayFormat = new SimpleDateFormat("d");
		final String calendarDay = dayFormat.format(date);

		return DB.getSQLValueStringEx(ITrx.TRXNAME_None, SQL_AD_SEQUENCE_NO_BY_YEAR_MONTH_DAY, AD_Sequence_ID, calendarYear, calendarMonth, calendarDay);
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
		final DocSequenceId docNoSequenceId = DocSequenceId.ofRepoIdOrNull(docType.getDocNoSequence_ID());
		docTypeSequenceMapBuilder.defaultDocNoSequenceId(docNoSequenceId);

		final List<I_C_DocType_Sequence> docTypeSequenceDefs = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_DocType_Sequence.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_DocType_Sequence.COLUMNNAME_C_DocType_ID, docTypeId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_C_DocType_Sequence.class);

		for (final I_C_DocType_Sequence docTypeSequenceDef : docTypeSequenceDefs)
		{
			final ClientId adClientId = ClientId.ofRepoId(docTypeSequenceDef.getAD_Client_ID());
			final OrgId adOrgId = OrgId.ofRepoId(docTypeSequenceDef.getAD_Org_ID());
			final DocSequenceId docSequenceId = DocSequenceId.ofRepoId(docTypeSequenceDef.getDocNoSequence_ID());
			docTypeSequenceMapBuilder.addDocSequenceId(adClientId, adOrgId, docSequenceId);
		}

		return docTypeSequenceMapBuilder.build();
	}
}
