package de.metas.dlm.model.interceptor;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.dlm.model.I_DLM_Partition_Config_Line;
import de.metas.dlm.model.I_DLM_Partition_Config_Reference;

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
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteReferences(final I_DLM_Partition_Config_Line configLine)
	{
		Services.get(IQueryBL.class).createQueryBuilder(I_DLM_Partition_Config_Reference.class, configLine)
				.addEqualsFilter(I_DLM_Partition_Config_Reference.COLUMN_DLM_Partition_Config_Line_ID, configLine.getDLM_Partition_Config_Line_ID())
				.create()
				.delete();
	}

}
