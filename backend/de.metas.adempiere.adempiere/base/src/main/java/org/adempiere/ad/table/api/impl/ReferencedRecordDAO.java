package org.adempiere.ad.table.api.impl;

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IReferencedRecordDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;

/**
 * Repository Tables: dynamic - resolved at runtime from the given {@link TableRecordReference}
 * Repository Cluster: -
 */
public class ReferencedRecordDAO implements IReferencedRecordDAO
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public boolean exists(@NonNull final TableRecordReference recordRef)
	{
		final String tableName = recordRef.getTableName();

		// NOTE: intentionally not restricted to active records - an inactive record still exists.
		return queryBL.createQueryBuilder(tableName)
				.addEqualsFilter(InterfaceWrapperHelper.getKeyColumnName(tableName), recordRef.getRecord_ID())
				.create()
				.anyMatch();
	}
}
