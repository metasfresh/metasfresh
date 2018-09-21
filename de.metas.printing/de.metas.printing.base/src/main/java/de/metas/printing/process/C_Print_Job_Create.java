/**
 * 
 */
package de.metas.printing.process;

/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.List;

import org.compiere.model.Query;

import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.util.Check;

/**
 * Process all {@link I_C_Printing_Queue}s from user selection and create corresponding {@link I_C_Print_Job}s
 * 
 * @author cg
 * 
 */
public class C_Print_Job_Create extends AbstractPrintJobCreate
{
	@Override
	protected void prepare()
	{
		// nothing
	}

	@Override
	protected int createSelection(final String trxName)
	{
		final StringBuilder sqlWhere = new StringBuilder();
		final List<Object> params = new ArrayList<Object>();

		final String gtWhereClause = getProcessInfo().getWhereClause();
		if (!Check.isEmpty(gtWhereClause, true))
		{
			sqlWhere.append(gtWhereClause);
		}
		else
		{
			sqlWhere.append("1=1");
		}

		sqlWhere.append(" AND ").append(I_C_Printing_Queue.COLUMNNAME_Processed).append("=?");
		params.add(false);

		final Query query = new Query(getCtx(), I_C_Printing_Queue.Table_Name, sqlWhere.toString(), trxName)
				.setParameters(params)
				.setClient_ID()
				.setOnlyActiveRecords(true);
		final int count = query.createSelection(getAD_PInstance_ID());

		log.info("Query: {}", query);
		log.info("Count: {}", count);

		return count;
	}

}
