package de.metas.dlm.migrator.process;

import java.sql.ResultSet;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.lang.Mutable;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;
import org.compiere.util.TrxRunnable;

import com.google.common.base.Stopwatch;

import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Invokes the two DB functions {@code dlm.load_production_table_rows()} and {@code dlm.update_production_table()} until they are all processed.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/969
 */
public class DLM_MassMigrate extends JavaProcess
{
	@Param(mandatory = true, parameterName = "IsRun_load_production_table_rows")
	private boolean run_load_production_table_rows;

	@Param(mandatory = true, parameterName = "IsRun_update_production_table")
	private boolean run_update_production_table;

	@RunOutOfTrx
	@Override
	protected String doIt() throws Exception
	{
		final Stopwatch stopWatch = Stopwatch.createStarted();
		
		if (run_load_production_table_rows)
		{
			callDBFunctionUntilDone("select * from dlm.load_production_table_rows();");
		}
		else
		{
			Loggables.get().addLog("Skipping load_production_table_rows because of the process parameter IsRun_load_production_table_rows");
		}

		if (run_update_production_table)
		{
			callDBFunctionUntilDone("select * from dlm.update_production_table();");
		}
		else
		{
			Loggables.get().addLog("Skipping update_production_table because of the process parameter IsRun_update_production_table");
		}

		final String elapsedTime = stopWatch.stop().toString();
		Loggables.get().addLog("overall elapsed time={}", elapsedTime);

		return MSG_OK;
	}

	private void callDBFunctionUntilDone(final String sql)
	{
		final Mutable<Boolean> done = new Mutable<>(false);

		do
		{
			Services.get(ITrxManager.class).run(new TrxRunnable()
			{
				@Override
				public void run(String localTrxName) throws Exception
				{
					final CPreparedStatement stmt = DB.prepareStatement(sql, localTrxName);

					final Stopwatch stopWatch = Stopwatch.createStarted();
					final ResultSet rs = stmt.executeQuery();
					final String elapsedTime = stopWatch.stop().toString();

					if (!rs.next())
					{
						done.setValue(true);
						Loggables.get().addLog("load_production_table_rows: we are done");
						return;
					}

					final String tablename = rs.getString("tablename");
					final int updatecount = rs.getInt("updatecount");
					final int massmigrate_id = rs.getInt("massmigrate_id");

					Loggables.get().addLog("load_production_table_rows: MassMigrate_ID={}; elapsed time={}; Table={}; Updated={}", massmigrate_id, elapsedTime, tablename, updatecount);
				}
			});
		}
		while (!done.getValue());
	}

}
