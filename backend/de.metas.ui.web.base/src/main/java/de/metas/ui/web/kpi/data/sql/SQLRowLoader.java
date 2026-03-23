package de.metas.ui.web.kpi.data.sql;

import de.metas.ui.web.kpi.data.KPIDataResult;
import lombok.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
interface SQLRowLoader
{
	void loadRowToResult(
			@NonNull final KPIDataResult.Builder data,
			@NonNull final ResultSet rs) throws SQLException;
}
