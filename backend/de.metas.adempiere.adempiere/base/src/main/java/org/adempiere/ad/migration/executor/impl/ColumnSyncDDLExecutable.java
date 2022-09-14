package org.adempiere.ad.migration.executor.impl;

import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.migration.executor.IPostponedExecutable;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.api.MinimalColumnInfo;
import org.adempiere.ad.table.ddl.TableDDLSyncService;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

public class ColumnSyncDDLExecutable implements IPostponedExecutable
{
	private static final Logger logger = LogManager.getLogger(ColumnSyncDDLExecutable.class);

	private final AdColumnId adColumnId;
	private final boolean drop;

	public ColumnSyncDDLExecutable(@NonNull final AdColumnId adColumnId, final boolean drop)
	{
		this.adColumnId = adColumnId;
		this.drop = drop;
	}

	@Override
	public void execute()
	{
		if (drop)
		{
			// TODO unsync column?
			final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
			final MinimalColumnInfo column = adTableDAO.getMinimalColumnInfo(adColumnId);
			final String tableName = adTableDAO.retrieveTableName(column.getAdTableId());
			logger.warn("Please manualy drop column {}.{}", tableName, column.getColumnName());
		}
		else
		{
			final TableDDLSyncService syncService = SpringContextHolder.instance.getBean(TableDDLSyncService.class);
			syncService.syncToDatabase(adColumnId);
		}
	}
}
