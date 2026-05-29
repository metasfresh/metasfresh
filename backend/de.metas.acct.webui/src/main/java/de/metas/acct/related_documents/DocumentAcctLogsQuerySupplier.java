package de.metas.acct.related_documents;

import de.metas.acct.model.I_Document_Acct_Log;
import de.metas.document.references.related_documents.RelatedDocumentsCountSupplier;
import de.metas.document.references.related_documents.RelatedDocumentsPermissions;
import de.metas.document.references.related_documents.RelatedDocumentsQuerySupplier;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.MQuery;

@ToString(exclude = "queryBL")
class DocumentAcctLogsQuerySupplier implements RelatedDocumentsQuerySupplier, RelatedDocumentsCountSupplier
{
	@NonNull private final IQueryBL queryBL;
	private final int adTableId;
	private final int recordId;

	@Builder
	private DocumentAcctLogsQuerySupplier(
			@NonNull final IQueryBL queryBL,
			final int adTableId,
			final int recordId)
	{
		this.queryBL = queryBL;
		this.adTableId = adTableId;
		this.recordId = recordId;
	}

	@Override
	public MQuery getQuery()
	{
		final MQuery query = new MQuery(I_Document_Acct_Log.Table_Name);
		query.addRestriction(I_Document_Acct_Log.COLUMNNAME_AD_Table_ID, MQuery.Operator.EQUAL, adTableId);
		query.addRestriction(I_Document_Acct_Log.COLUMNNAME_Record_ID, MQuery.Operator.EQUAL, recordId);
		return query;
	}

	@Override
	public int getRecordsCount(final RelatedDocumentsPermissions permissions)
	{
		return queryBL.createQueryBuilder(I_Document_Acct_Log.class)
				.addEqualsFilter(I_Document_Acct_Log.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_Document_Acct_Log.COLUMNNAME_Record_ID, recordId)
				.create()
				.count();
	}
}
