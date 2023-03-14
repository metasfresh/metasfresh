package de.metas.document.impl;

import de.metas.cache.CCache;
import de.metas.cache.annotation.CacheCtx;
import de.metas.document.DocTypeSequenceList;
import de.metas.document.DocumentSequenceInfo;
import de.metas.document.IDocumentSequenceDAO;
import de.metas.document.sequence.DocSequenceId;
import de.metas.document.sequenceno.CustomSequenceNoProvider;
import de.metas.javaclasses.IJavaClassBL;
import de.metas.javaclasses.JavaClassId;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
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
import java.util.Optional;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public class DocumentSequenceDAO implements IDocumentSequenceDAO
{
	private static final Logger logger = LogManager.getLogger(DocumentSequenceDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

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
			+ I_AD_Sequence_No.COLUMNNAME_CalendarMonth + "= '1'";

	private static final String SQL_AD_SEQUENCE_NO_BY_YEAR_MONTH = "SELECT " + I_AD_Sequence_No.COLUMNNAME_CurrentNext
			+ " FROM " + I_AD_Sequence_No.Table_Name
			+ " WHERE " + I_AD_Sequence_No.COLUMNNAME_AD_Sequence_ID + "=? AND "
			+ I_AD_Sequence_No.COLUMNNAME_CalendarYear + "=? AND "
			+ I_AD_Sequence_No.COLUMNNAME_CalendarMonth + "=?";

	private final CCache<BySequenceNameCacheKey, DocumentSequenceInfo> bySequenceNameCache = CCache.<BySequenceNameCacheKey, DocumentSequenceInfo>builder()
			.tableName(I_AD_Sequence.Table_Name)
			.build();

	@Override
	public DocumentSequenceInfo getOrCreateDocumentSequenceInfo(final String sequenceName, final int adClientId, final int adOrgId)
	{
		final BySequenceNameCacheKey key = BySequenceNameCacheKey.builder()
				.sequenceName(sequenceName)
				.adClientId(ClientId.ofRepoId(adClientId))
				.adOrgId(OrgId.ofRepoId(adOrgId))
				.build();
		return bySequenceNameCache.getOrLoad(key, this::retrieveOrCreateDocumentSequenceInfo);
	}

	private DocumentSequenceInfo retrieveOrCreateDocumentSequenceInfo(final BySequenceNameCacheKey key)
	{
		return retrieveDocumentSequenceInfo(key)
				.orElseGet(() -> createDocumentSequence(key.getAdClientId(), key.getSequenceName()));
	}

	private Optional<DocumentSequenceInfo> retrieveDocumentSequenceInfo(final BySequenceNameCacheKey key)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_AD_Sequence.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Sequence.COLUMNNAME_IsTableID, false)
				.addEqualsFilter(I_AD_Sequence.COLUMNNAME_AD_Client_ID, key.getAdClientId())
				.addEqualsFilter(I_AD_Sequence.COLUMNNAME_Name, key.getSequenceName())
				.addInArrayFilter(I_AD_Sequence.COLUMNNAME_AD_Org_ID, key.getAdOrgId(), OrgId.ANY)
				.orderBy().addColumn(I_AD_Sequence.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last).endOrderBy() // make sure we get for our particular org first
				.create()
				.firstOptional(I_AD_Sequence.class)
				.map(DocumentSequenceDAO::toDocumentSequenceInfo);
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
				.startNewYear(record.isStartNewYear())
				.startNewMonth(record.isStartNewMonth())
				.dateColumn(record.getDateColumn())
				//
				.customSequenceNoProvider(createCustomSequenceNoProviderOrNull(record))
				//
				.build();
	}

	private static IStringExpression compileStringExpressionOrUseItAsIs(final String expr)
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

		final SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
		final String calendarMonth = monthFormat.format(date);

		return DB.getSQLValueStringEx(ITrx.TRXNAME_None, SQL_AD_SEQUENCE_NO_BY_YEAR_MONTH, AD_Sequence_ID, calendarYear, calendarMonth);
	}

	@Override
	public DocTypeSequenceList retrieveDocTypeSequenceList(final I_C_DocType docType)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(docType);
		final int docTypeId = docType.getC_DocType_ID();
		return retrieveDocTypeSequenceList(ctx, docTypeId);
	}

	@Cached(cacheName = I_C_DocType_Sequence.Table_Name + "#by#" + I_C_DocType_Sequence.COLUMNNAME_C_DocType_ID)
	public DocTypeSequenceList retrieveDocTypeSequenceList(@CacheCtx final Properties ctx, final int docTypeId)
	{
		final DocTypeSequenceList.Builder docTypeSequenceListBuilder = DocTypeSequenceList.builder();

		final I_C_DocType docType = InterfaceWrapperHelper.create(ctx, docTypeId, I_C_DocType.class, ITrx.TRXNAME_None);
		final DocSequenceId docNoSequenceId = DocSequenceId.ofRepoIdOrNull(docType.getDocNoSequence_ID());
		docTypeSequenceListBuilder.defaultDocNoSequenceId(docNoSequenceId);

		final List<I_C_DocType_Sequence> docTypeSequenceDefs = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_DocType_Sequence.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_DocType_Sequence.COLUMNNAME_C_DocType_ID, docTypeId)
				.addOnlyActiveRecordsFilter()
				.orderBy().addColumn(I_C_DocType_Sequence.COLUMN_SeqNo, Direction.Ascending, Nulls.Last).endOrderBy()
				.create()
				.list(I_C_DocType_Sequence.class);

		for (final I_C_DocType_Sequence docTypeSequenceDef : docTypeSequenceDefs)
		{
			final ClientId adClientId = ClientId.ofRepoId(docTypeSequenceDef.getAD_Client_ID());
			final OrgId adOrgId = OrgId.ofRepoId(docTypeSequenceDef.getAD_Org_ID());
			final DocSequenceId docSequenceId = DocSequenceId.ofRepoId(docTypeSequenceDef.getDocNoSequence_ID());
			final CountryId countryId = CountryId.ofRepoIdOrNull(docTypeSequenceDef.getC_Country_ID());
			final SeqNo seqNo = SeqNo.ofInt(docTypeSequenceDef.getSeqNo());
			docTypeSequenceListBuilder.addDocSequenceId(adClientId, adOrgId, docSequenceId, countryId, seqNo);
		}

		return docTypeSequenceListBuilder.build();
	}

	//
	//
	//

	@Value
	@Builder
	private static class BySequenceNameCacheKey
	{
		@NonNull String sequenceName;
		@NonNull ClientId adClientId;
		@NonNull OrgId adOrgId;
	}
}
