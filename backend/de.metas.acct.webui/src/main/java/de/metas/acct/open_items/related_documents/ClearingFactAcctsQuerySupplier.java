package de.metas.acct.open_items.related_documents;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.FactAcctId;
import de.metas.acct.api.FactAcctQuery;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.acct.open_items.FAOpenItemTrxType;
import de.metas.document.references.related_documents.RelatedDocumentsCountSupplier;
import de.metas.document.references.related_documents.RelatedDocumentsPermissions;
import de.metas.document.references.related_documents.RelatedDocumentsQuerySupplier;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.MQuery;
import org.compiere.util.DB;
import org.slf4j.Logger;

import java.util.Objects;

class ClearingFactAcctsQuerySupplier implements RelatedDocumentsQuerySupplier, RelatedDocumentsCountSupplier
{
	private static final Logger logger = LogManager.getLogger(ClearingFactAcctsQuerySupplier.class);
	private final IFactAcctDAO factAcctDAO;

	private final TableRecordReference fromRecordRef;

	private ImmutableSet<FactAcctId> _clearingFactAcctIds; // lazy

	ClearingFactAcctsQuerySupplier(
			@NonNull final IFactAcctDAO factAcctDAO,
			@NonNull final TableRecordReference fromRecordRef)
	{
		this.factAcctDAO = factAcctDAO;
		this.fromRecordRef = fromRecordRef;
	}

	@Override
	public int getRecordsCount(final RelatedDocumentsPermissions permissions)
	{
		return getClearingFactAcctIds().size();
	}

	@Override
	public MQuery getQuery()
	{
		final MQuery query = new MQuery(I_Fact_Acct.Table_Name);

		final ImmutableSet<FactAcctId> clearingFactAcctIds = getClearingFactAcctIds();
		if (!clearingFactAcctIds.isEmpty())
		{
			query.addRestriction(DB.buildSqlList(I_Fact_Acct.COLUMNNAME_Fact_Acct_ID, clearingFactAcctIds, null));
		}
		else
		{
			query.addRestriction("1=2");
		}

		return query;
	}

	private ImmutableSet<FactAcctId> getClearingFactAcctIds()
	{
		ImmutableSet<FactAcctId> clearingFactAcctIds = this._clearingFactAcctIds;
		if (clearingFactAcctIds == null)
		{
			clearingFactAcctIds = this._clearingFactAcctIds = computeClearingFactAcctIds();
		}
		return clearingFactAcctIds;
	}

	private ImmutableSet<FactAcctId> computeClearingFactAcctIds()
	{
		final ImmutableSet<FAOpenItemKey> openItemKeys = factAcctDAO.stream(FactAcctQuery.builder()
						.tableName(fromRecordRef.getTableName())
						.recordId(fromRecordRef.getRecord_ID())
						.isOpenItem(true)
						.openItemTrxType(FAOpenItemTrxType.OPEN_ITEM)
						.build())
				.map(ClearingFactAcctsQuerySupplier::extractOpenItemKeyOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		if (openItemKeys.isEmpty())
		{
			return ImmutableSet.of();
		}

		return factAcctDAO.listIds(FactAcctQuery.builder()
				.tableName(I_SAP_GLJournal.Table_Name)
				.excludeRecordRef(fromRecordRef)
				.isOpenItem(true)
				.openItemTrxType(FAOpenItemTrxType.CLEARING)
				.openItemsKeys(openItemKeys)
				.build());
	}

	private static FAOpenItemKey extractOpenItemKeyOrNull(final I_Fact_Acct record)
	{
		try
		{
			return FAOpenItemKey.parseNullable(record.getOpenItemKey()).orElse(null);
		}
		catch (Exception ex)
		{
			logger.warn("Failed extracting open item key from {}. Returning null.", record, ex);
			return null;
		}
	}
}
