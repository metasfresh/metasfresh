package de.metas.acct.api.impl;

import de.metas.acct.api.IPostingDocumentDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;

public class PostingDocumentDAO implements IPostingDocumentDAO
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public boolean exists(@NonNull final TableRecordReference documentRef)
	{
		final String tableName = documentRef.getTableName();

		// NOTE: intentionally not restricted to active records - an inactive record still exists
		// and a posting error for it shall still be reported.
		return queryBL.createQueryBuilder(tableName)
				.addEqualsFilter(InterfaceWrapperHelper.getKeyColumnName(tableName), documentRef.getRecord_ID())
				.create()
				.anyMatch();
	}
}
