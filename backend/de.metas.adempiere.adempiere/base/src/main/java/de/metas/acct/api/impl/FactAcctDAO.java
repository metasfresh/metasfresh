package de.metas.acct.api.impl;

<<<<<<< HEAD
import static org.adempiere.model.InterfaceWrapperHelper.load;

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

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
=======
import com.google.common.collect.ImmutableList;
import de.metas.acct.AccountConceptualName;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.FactAcctId;
import de.metas.acct.api.FactAcctQuery;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.api.IFactAcctListenersService;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.document.engine.IDocument;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.NotQueryFilter;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
<<<<<<< HEAD
import org.compiere.model.I_Fact_Acct;
import org.compiere.util.Env;

import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.api.IFactAcctListenersService;
import de.metas.document.engine.IDocument;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class FactAcctDAO implements IFactAcctDAO
{
=======
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.model.I_Fact_Acct;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.load;

public class FactAcctDAO implements IFactAcctDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	private static final String ACCOUNTCONCEPTUALNAME_NULL_MARKER = "NOTSET";

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@Override
	public I_Fact_Acct getById(final int factAcctId)
	{
		return load(factAcctId, I_Fact_Acct.class);
	}

	@Override
<<<<<<< HEAD
=======
	public void save(@NonNull final I_Fact_Acct factAcct)
	{
		InterfaceWrapperHelper.save(factAcct, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public int deleteForDocument(final IDocument document)
	{
		final int countDeleted = retrieveQueryForDocument(document)
				.create()
				.deleteDirectly();

		Services.get(IFactAcctListenersService.class).fireAfterUnpost(document);

		return countDeleted;
	}

	@Override
	public int deleteForDocumentModel(@NonNull final Object documentObj)
	{
		final int adTableId = InterfaceWrapperHelper.getModelTableId(documentObj);
		final int recordId = InterfaceWrapperHelper.getId(documentObj);
		final int countDeleted = retrieveQueryForDocument(Env.getCtx(), adTableId, recordId, ITrx.TRXNAME_ThreadInherited)
				.create()
				.deleteDirectly();

		Services.get(IFactAcctListenersService.class).fireAfterUnpost(documentObj);

		return countDeleted;
	}

	@Override
	public IQueryBuilder<I_Fact_Acct> retrieveQueryForDocument(@NonNull final IDocument document)
	{
		final Properties ctx = document.getCtx();
		final String trxName = document.get_TrxName();
		final int adTableId = document.get_Table_ID();
		final int recordId = document.get_ID();
		return retrieveQueryForDocument(ctx, adTableId, recordId, trxName);
	}

	private IQueryBuilder<I_Fact_Acct> retrieveQueryForDocument(final Properties ctx, final int adTableId, final int recordId, final String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_Fact_Acct.class, ctx, trxName)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Record_ID, recordId)
				.orderBy()
				.addColumn(I_Fact_Acct.COLUMN_Fact_Acct_ID) // make sure we have a predictable order
				.endOrderBy();
	}

	@Override
	public List<I_Fact_Acct> retrieveForDocumentLine(final String tableName, final int recordId, final Object documentLine)
	{
		Check.assumeNotNull(documentLine, "documentLine not null");
		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		final int lineId = InterfaceWrapperHelper.getId(documentLine);

		final IQueryBuilder<I_Fact_Acct> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_Fact_Acct.class, documentLine)
				.addEqualsFilter(I_Fact_Acct.COLUMN_AD_Table_ID, adTableId)
				.addEqualsFilter(I_Fact_Acct.COLUMN_Record_ID, recordId)
				.addEqualsFilter(I_Fact_Acct.COLUMN_Line_ID, lineId);

		// make sure we have a predictable order
		queryBuilder.orderBy()
				.addColumn(I_Fact_Acct.COLUMN_Fact_Acct_ID);

		return queryBuilder.create().list();
	}

	@Override
	public void updateDocStatusForDocument(final IDocument document)
	{
		final String docStatus = document.getDocStatus();
		retrieveQueryForDocument(document)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_Fact_Acct.COLUMNNAME_DocStatus, docStatus)
				.execute();
	}

	@Override
	public int updateActivityForDocumentLine(final Properties ctx, final int adTableId, final int recordId, final int lineId, final int activityId)
	{
		// Make sure we are updating the Fact_Acct records in a transaction
		Services.get(ITrxManager.class).assertThreadInheritedTrxExists();

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final int countUpdated = queryBL.createQueryBuilder(I_Fact_Acct.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_Fact_Acct.COLUMN_AD_Table_ID, adTableId)
				.addEqualsFilter(I_Fact_Acct.COLUMN_Record_ID, recordId)
				.addEqualsFilter(I_Fact_Acct.COLUMN_Line_ID, lineId)
				.addNotEqualsFilter(I_Fact_Acct.COLUMN_C_Activity_ID, activityId)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_Fact_Acct.COLUMNNAME_C_Activity_ID, activityId)
				.execute();

		return countUpdated;
	}
<<<<<<< HEAD
=======

	@Override
	public void updatePOReference(@NonNull final TableRecordReference recordRef, @Nullable final String poReference)
	{
		queryBL.createQueryBuilder(I_Fact_Acct.class)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
				.addNotEqualsFilter(I_Fact_Acct.COLUMNNAME_POReference, poReference)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_Fact_Acct.COLUMNNAME_POReference, poReference)
				.execute();
	}

	@Override
	public List<I_Fact_Acct> list(@NonNull final List<FactAcctQuery> queries)
	{
		final IQuery<I_Fact_Acct> query = queries.stream()
				.map(this::toSqlQuery)
				.reduce(IQuery.unionDistict())
				.orElse(null);
		if (query == null)
		{
			return ImmutableList.of();
		}

		return query.list();
	}

	@Override
	public List<I_Fact_Acct> list(@NonNull final FactAcctQuery query)
	{
		return toSqlQuery(query).list();
	}

	private IQuery<I_Fact_Acct> toSqlQuery(@NonNull final FactAcctQuery query)
	{
		final IQueryBuilder<I_Fact_Acct> sqlQueryBuilder = queryBL.createQueryBuilder(I_Fact_Acct.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_Fact_Acct.COLUMNNAME_Fact_Acct_ID);

		if (query.getAcctSchemaId() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_C_AcctSchema_ID, query.getAcctSchemaId());
		}
		if (query.getAccountConceptualNames() != null && !query.getAccountConceptualNames().isEmpty())
		{
			final Set<String> accountConceptualNames = query.getAccountConceptualNames()
					.stream()
					.map(AccountConceptualName::getAsString)
					.collect(Collectors.toSet());
			sqlQueryBuilder.addInArrayFilter(I_Fact_Acct.COLUMNNAME_AccountConceptualName, accountConceptualNames);
		}
		if (query.getAccountIds() != null && !query.getAccountIds().isEmpty())
		{
			sqlQueryBuilder.addInArrayFilter(I_Fact_Acct.COLUMNNAME_Account_ID, query.getAccountIds());
		}
		if (query.getPostingType() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_PostingType, query.getPostingType());
		}
		if (query.getCurrencyId() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_C_Currency_ID, query.getCurrencyId());
		}

		if (query.getDateAcct() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_DateAcct, query.getDateAcct());
		}
		if (query.getDateAcctLessOrEqualsTo() != null)
		{
			sqlQueryBuilder.addCompareFilter(I_Fact_Acct.COLUMNNAME_DateAcct, CompareQueryFilter.Operator.LESS_OR_EQUAL, query.getDateAcctLessOrEqualsTo());
		}
		if (query.getDateAcctGreaterOrEqualsTo() != null)
		{
			sqlQueryBuilder.addCompareFilter(I_Fact_Acct.COLUMNNAME_DateAcct, CompareQueryFilter.Operator.GREATER_OR_EQUAL, query.getDateAcctGreaterOrEqualsTo());
		}

		//
		// Referenced document
		if (query.getTableName() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, adTableDAO.retrieveAdTableId(query.getTableName()));
		}
		if (query.getRecordId() > 0)
		{
			sqlQueryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Record_ID, query.getRecordId());
		}
		if (query.getLineId() > 0)
		{
			sqlQueryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Line_ID, query.getLineId());
		}
		if (query.getExcludeRecordRef() != null)
		{
			sqlQueryBuilder.filter(NotQueryFilter.of(
					queryBL.createCompositeQueryFilter(I_Fact_Acct.class)
							.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, query.getExcludeRecordRef().getAdTableId())
							.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Record_ID, query.getExcludeRecordRef().getRecord_ID())
			));
		}

		//
		// Open Items Query
		if (query.getIsOpenItem() != null)
		{
			final boolean isOpenItem = query.getIsOpenItem();
			if (isOpenItem)
			{
				sqlQueryBuilder.addNotNull(I_Fact_Acct.COLUMNNAME_OpenItemKey);
			}
			else
			{
				sqlQueryBuilder.addIsNull(I_Fact_Acct.COLUMNNAME_OpenItemKey);
			}
		}
		if (query.getIsOpenItemReconciled() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_IsOpenItemsReconciled, query.getIsOpenItemReconciled());
		}
		if (query.getOpenItemsKeys() != null && !query.getOpenItemsKeys().isEmpty())
		{
			sqlQueryBuilder.addInArrayFilter(I_Fact_Acct.COLUMNNAME_OpenItemKey, query.getOpenItemsKeys()
					.stream()
					.map(openItemKey -> openItemKey != null ? openItemKey.getAsString() : null)
					.collect(Collectors.toSet()));
		}
		if (query.getOpenItemTrxType() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_OI_TrxType, query.getOpenItemTrxType().getCode());
		}

		if (query.getDocStatuses() != null && !query.getDocStatuses().isEmpty())
		{
			sqlQueryBuilder.addInArrayFilter(I_Fact_Acct.COLUMNNAME_DocStatus, query.getDocStatuses());
		}
		toSqlLikeString(query.getDocumentNoLike())
				.ifPresent(pattern -> sqlQueryBuilder.addStringLikeFilter(I_Fact_Acct.COLUMNNAME_DocumentNo, pattern, true));
		toSqlLikeString(query.getDescriptionLike())
				.ifPresent(pattern -> sqlQueryBuilder.addStringLikeFilter(I_Fact_Acct.COLUMNNAME_Description, pattern, true));
		toSqlLikeString(query.getPoReferenceLike())
				.ifPresent(pattern -> sqlQueryBuilder.addStringLikeFilter(I_Fact_Acct.COLUMNNAME_POReference, pattern, true));

		if (!query.getBpartnerIds().isAny())
		{
			sqlQueryBuilder.addInArrayFilter(I_Fact_Acct.COLUMNNAME_C_BPartner_ID, query.getBpartnerIds());
		}
		if (query.getSalesOrderId() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_Fact_Acct.COLUMNNAME_C_OrderSO_ID, query.getSalesOrderId());
		}

		toSqlLikeString(query.getUserElementString1Like())
				.ifPresent(pattern -> sqlQueryBuilder.addStringLikeFilter(I_Fact_Acct.COLUMNNAME_UserElementString1, pattern, true));
		toSqlLikeString(query.getUserElementString2Like())
				.ifPresent(pattern -> sqlQueryBuilder.addStringLikeFilter(I_Fact_Acct.COLUMNNAME_UserElementString2, pattern, true));
		toSqlLikeString(query.getUserElementString3Like())
				.ifPresent(pattern -> sqlQueryBuilder.addStringLikeFilter(I_Fact_Acct.COLUMNNAME_UserElementString3, pattern, true));
		toSqlLikeString(query.getUserElementString4Like())
				.ifPresent(pattern -> sqlQueryBuilder.addStringLikeFilter(I_Fact_Acct.COLUMNNAME_UserElementString4, pattern, true));
		toSqlLikeString(query.getUserElementString5Like())
				.ifPresent(pattern -> sqlQueryBuilder.addStringLikeFilter(I_Fact_Acct.COLUMNNAME_UserElementString5, pattern, true));
		toSqlLikeString(query.getUserElementString6Like())
				.ifPresent(pattern -> sqlQueryBuilder.addStringLikeFilter(I_Fact_Acct.COLUMNNAME_UserElementString6, pattern, true));
		toSqlLikeString(query.getUserElementString7Like())
				.ifPresent(pattern -> sqlQueryBuilder.addStringLikeFilter(I_Fact_Acct.COLUMNNAME_UserElementString7, pattern, true));

		final IQuery<I_Fact_Acct> sqlQuery = sqlQueryBuilder.create();

		final Set<FactAcctId> includeFactAcctIds = query.getIncludeFactAcctIds();
		if (includeFactAcctIds != null && !includeFactAcctIds.isEmpty())
		{
			final IQuery<I_Fact_Acct> alwaysIncludeQuery = queryBL.createQueryBuilder(I_Fact_Acct.class)
					.addInArrayFilter(I_Fact_Acct.COLUMNNAME_Fact_Acct_ID, includeFactAcctIds)
					.create();

			sqlQuery.addUnion(alwaysIncludeQuery, true);
		}

		return sqlQuery;
	}

	private static Optional<String> toSqlLikeString(@Nullable final String string)
	{
		String stringNorm = StringUtils.trimBlankToNull(string);
		if (stringNorm == null)
		{
			return Optional.empty();
		}
		else if ("%".equals(stringNorm))
		{
			return Optional.empty();
		}

		if (!stringNorm.contains("%"))
		{
			stringNorm = "%" + stringNorm + "%";
		}

		return Optional.of(stringNorm);
	}

	@Nullable
	public static AccountConceptualName extractAccountConceptualName(final I_Fact_Acct record)
	{
		final String accountConceptualName = StringUtils.trimBlankToNull(record.getAccountConceptualName());
		return accountConceptualName != null && !ACCOUNTCONCEPTUALNAME_NULL_MARKER.equals(accountConceptualName)
				? AccountConceptualName.ofString(accountConceptualName)
				: null;
	}

	public static void setAccountConceptualName(@NonNull final I_Fact_Acct record, @Nullable final AccountConceptualName accountConceptualName)
	{
		record.setAccountConceptualName(accountConceptualName != null ? accountConceptualName.getAsString() : ACCOUNTCONCEPTUALNAME_NULL_MARKER);
	}

	@Override
	public void setOpenItemTrxInfo(@NonNull final FAOpenItemTrxInfo openItemTrxInfo, @NonNull final FactAcctQuery query)
	{
		toSqlQuery(query)
				.updateDirectly()
				.addSetColumnValue(I_Fact_Acct.COLUMNNAME_OI_TrxType, openItemTrxInfo.getTrxType().getCode())
				.addSetColumnValue(I_Fact_Acct.COLUMNNAME_OpenItemKey, openItemTrxInfo.getKey().getAsString())
				.execute();
	}

	@Override
	public List<ElementValueId> retrieveAccountsForTimeFrame(@NonNull final AcctSchemaId acctSchemaId, @NonNull final Instant dateAcctFrom, @NonNull final Instant dateAcctTo)
	{
		return queryBL
				.createQueryBuilder(I_Fact_Acct.class)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_C_AcctSchema_ID, acctSchemaId)
				.addBetweenFilter(I_Fact_Acct.COLUMNNAME_DateAcct, dateAcctFrom, dateAcctTo)
				.create()
				.listDistinct(I_Fact_Acct.COLUMNNAME_Account_ID, ElementValueId.class);
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
