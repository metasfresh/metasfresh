package org.adempiere.ad.table.process;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.table.api.impl.CopyColumnsProducer;
import org.adempiere.ad.table.api.impl.CopyColumnsResult;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;

import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;

/**
 * Copy selected columns to given table.
 *
 * @author tsa
 *
 */
public class AD_Column_CopySelectedToTable extends JavaProcess
{
	private int p_AD_Table_ID = -1;
	private String p_EntityType = null;
	private boolean p_IsTest = false;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				continue;
			}
			else if (name.equals("AD_Table_ID"))
			{
				p_AD_Table_ID = para.getParameterAsInt();
			}
			else if (name.equals("EntityType"))
			{
				p_EntityType = para.getParameterAsString();
			}
			else if (name.equals("IsTest"))
			{
				p_IsTest = para.getParameterAsBoolean();
			}
		}
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final CopyColumnsResult result = CopyColumnsProducer.newInstance()
				.setLogger(this)
				.setTargetTable(getTargetTable())
				.setSourceColumns(getSourceColumns())
				.setEntityType(p_EntityType)
				.setDryRun(p_IsTest)
				.create();

		//
		return "" + result;
	}

	protected I_AD_Table getTargetTable()
	{
		if (p_AD_Table_ID <= 0)
		{
			throw new AdempiereException("@NotFound@ @AD_Table_ID@ " + p_AD_Table_ID);
		}

		final I_AD_Table targetTable = InterfaceWrapperHelper.create(getCtx(), p_AD_Table_ID, I_AD_Table.class, get_TrxName());
		return targetTable;
	}

	protected List<I_AD_Column> getSourceColumns()
	{
		final IQueryFilter<I_AD_Column> selectedColumnsFilter = getProcessInfo().getQueryFilterOrElseFalse();
		final IQueryBuilder<I_AD_Column> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_AD_Column.class, this)
				.filter(selectedColumnsFilter);

		queryBuilder.orderBy()
				.addColumn(I_AD_Column.COLUMNNAME_AD_Table_ID)
				.addColumn(I_AD_Column.COLUMNNAME_AD_Column_ID);

		return queryBuilder
				.create()
				.list(I_AD_Column.class);
	}

}
