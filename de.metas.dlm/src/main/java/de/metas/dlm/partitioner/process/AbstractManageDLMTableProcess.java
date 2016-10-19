package de.metas.dlm.partitioner.process;

import org.adempiere.ad.process.ISvrProcessDefaultParametersProvider;
import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.GridField;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import de.metas.dlm.model.I_AD_Table;
import de.metas.dlm.model.I_DLM_Partition_Config_Line;
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

public abstract class AbstractManageDLMTableProcess extends SvrProcess
		implements ISvrProcessPrecondition, ISvrProcessDefaultParametersProvider
{
	@Param(mandatory = true, parameterName = I_AD_Table.COLUMNNAME_AD_Table_ID)
	protected I_AD_Table table;

	protected final Boolean isDLMTable(final PreconditionsContext context)
	{
		if (Check.equals(context.getTableName(), I_DLM_Partition_Config_Line.Table_Name))
		{
			final I_DLM_Partition_Config_Line configLine = context.getModel(I_DLM_Partition_Config_Line.class);
			final I_AD_Table table = InterfaceWrapperHelper.create(configLine.getDLM_Referencing_Table(), I_AD_Table.class);
			return table.isDLM(); // if the table is already DLMed, then return false
		}
		else if (Check.equals(context.getTableName(), org.compiere.model.I_AD_Table.Table_Name))
		{
			final I_AD_Table table = context.getModel(I_AD_Table.class);
			return table.isDLM(); // if the table is already DLMed, then return false
		}

		return null;
	}

	@Override
	public final Object getParameterDefaultValue(final GridField parameter)
	{
		final int windowNo = parameter.getWindowNo();

		final int configLineId = Env.getContextAsInt(getCtx(), windowNo, I_DLM_Partition_Config_Line.COLUMNNAME_DLM_Partition_Config_Line_ID);
		if (configLineId > 0)
		{
			final I_DLM_Partition_Config_Line configLine = InterfaceWrapperHelper.create(getCtx(), configLineId, I_DLM_Partition_Config_Line.class, ITrx.TRXNAME_None);
			return configLine.getDLM_Referencing_Table_ID();
		}

		final int tableId = Env.getContextAsInt(getCtx(), windowNo, org.compiere.model.I_AD_Table.COLUMNNAME_AD_Table_ID);
		if (tableId > 0)
		{
			return tableId;
		}

		return DEFAULT_VALUE_NOTAVAILABLE;
	}
}
