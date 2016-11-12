package de.metas.dlm.model.interceptor;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;

import de.metas.dlm.model.I_DLM_Partition_Config_Line;
import de.metas.dlm.model.I_DLM_Partition_Config_Reference;
import de.metas.dlm.model.interceptor.PartitionerInterceptor.AddToPartitionInterceptor;

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

@Interceptor(I_DLM_Partition_Config_Line.class)
public class DLM_Partition_Config_Line
{
	static final DLM_Partition_Config_Line INSTANCE = new DLM_Partition_Config_Line();

	private DLM_Partition_Config_Line()
	{
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteReferences(final I_DLM_Partition_Config_Line configLine)
	{
		Services.get(IQueryBL.class).createQueryBuilder(I_DLM_Partition_Config_Reference.class, configLine)
				.addEqualsFilter(I_DLM_Partition_Config_Reference.COLUMN_DLM_Partition_Config_Line_ID, configLine.getDLM_Partition_Config_Line_ID())
				.create()
				.delete();
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_DLM_Partition_Config_Line.COLUMNNAME_DLM_Referencing_Table_ID)
	public void updatePartitionerInterceptorOnChange(final I_DLM_Partition_Config_Line configLine)
	{
		if (configLine.getDLM_Referencing_Table_ID() <= 0)
		{
			return;
		}

		final ModelValidationEngine engine = ModelValidationEngine.get();
		final I_DLM_Partition_Config_Line oldConfigLine = InterfaceWrapperHelper.createOld(configLine, I_DLM_Partition_Config_Line.class);

		engine.removeModelChange(oldConfigLine.getDLM_Referencing_Table().getTableName(), AddToPartitionInterceptor.INSTANCE);
		engine.addModelChange(configLine.getDLM_Referencing_Table().getTableName(), AddToPartitionInterceptor.INSTANCE);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void updatePartitionerInterceptorOnNew(final I_DLM_Partition_Config_Line configLine)
	{
		if (configLine.getDLM_Referencing_Table_ID() <= 0)
		{
			return;
		}

		final ModelValidationEngine engine = ModelValidationEngine.get();
		engine.addModelChange(configLine.getDLM_Referencing_Table().getTableName(), AddToPartitionInterceptor.INSTANCE);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void updatePartitionerInterceptorOnDelete(final I_DLM_Partition_Config_Line configLine)
	{
		if (configLine.getDLM_Referencing_Table_ID() <= 0)
		{
			return;
		}

		final ModelValidationEngine engine = ModelValidationEngine.get();
		engine.removeModelChange(configLine.getDLM_Referencing_Table().getTableName(), AddToPartitionInterceptor.INSTANCE);
	}
}
