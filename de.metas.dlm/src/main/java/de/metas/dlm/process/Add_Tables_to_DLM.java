package de.metas.dlm.process;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.compiere.model.GridField;
import org.compiere.util.Env;

import de.metas.dlm.IDLMService;
import de.metas.dlm.model.I_AD_Table;
import de.metas.dlm.model.I_DLM_Partition_Config;
import de.metas.dlm.model.I_DLM_Partition_Config_Line;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.JavaProcess;
import de.metas.process.Param;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * This process makes sure that all tables
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class Add_Tables_to_DLM
		extends JavaProcess
		implements IProcessDefaultParametersProvider
{

	@Param(mandatory = true, parameterName = I_DLM_Partition_Config.COLUMNNAME_DLM_Partition_Config_ID)
	private I_DLM_Partition_Config configDB;

	@Override
	protected String doIt() throws Exception
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IDLMService dlmService = Services.get(IDLMService.class);

		final List<I_DLM_Partition_Config_Line> configLinesDB = queryBL.createQueryBuilder(I_DLM_Partition_Config_Line.class, this)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DLM_Partition_Config_Line.COLUMN_DLM_Partition_Config_ID, configDB.getDLM_Partition_Config_ID())
				.orderBy().addColumn(I_DLM_Partition_Config_Line.COLUMNNAME_DLM_Partition_Config_ID).endOrderBy()
				.create()
				.list();
		for (I_DLM_Partition_Config_Line line : configLinesDB)
		{
			final I_AD_Table dlm_Referencing_Table = InterfaceWrapperHelper.create(line.getDLM_Referencing_Table(), I_AD_Table.class);
			if (dlm_Referencing_Table.isDLM())
			{
				Loggables.get().addLog("Table {} is already DLM'ed. Skipping", dlm_Referencing_Table.getTableName());
				continue;
			}

			dlmService.addTableToDLM(dlm_Referencing_Table);
		}

		return MSG_OK;
	}

	@Override
	public Object getParameterDefaultValue(GridField parameter)
	{
		final int windowNo = parameter.getWindowNo();

		final int configId = Env.getContextAsInt(getCtx(), windowNo, I_DLM_Partition_Config.COLUMNNAME_DLM_Partition_Config_ID);
		if (configId > 0)
		{
			return configId;
		}

		return DEFAULT_VALUE_NOTAVAILABLE;
	}

}
