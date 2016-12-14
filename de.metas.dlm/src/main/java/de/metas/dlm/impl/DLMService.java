package de.metas.dlm.impl;

import org.compiere.util.DB;

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

public class DLMService extends AbstractDLMService
{
	@Override
	void executeDBFunction_add_table_to_dlm(final String tableName, final String trxName)
	{
		DB.executeFunctionCallEx(trxName, "select dlm.add_table_to_dlm(?)", new Object[] { tableName });
	}

	@Override
	void executeDBFunction_remove_table_from_dlm(final String tableName, final String trxName)
	{
		DB.executeFunctionCallEx(trxName, "select dlm.remove_table_from_dlm(?)", new Object[] { tableName });
	}

	@Override
	void executeDBFunction_update_partition_size(final int dlm_Partition_ID, final String trxName)
	{
		DB.executeFunctionCallEx(trxName, "select dlm.update_partition_size(?)", new Object[] { dlm_Partition_ID });
	}
}
