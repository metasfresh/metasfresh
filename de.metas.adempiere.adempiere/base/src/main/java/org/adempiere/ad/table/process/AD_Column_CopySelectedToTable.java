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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.table.api.impl.CopyColumnsProducer;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.compiere.model.MTable;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

/**
 * Copy selected columns to given table.
 * 
 * @author tsa
 *
 */
public class AD_Column_CopySelectedToTable extends SvrProcess
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
	protected String doIt() throws Exception
	{
		final CopyColumnsProducer producer = new CopyColumnsProducer(this);
		producer.setLogger(this);
		producer.setTargetTable(getTargetTable());
		producer.setSourceColumns(getSourceColumns());
		producer.setEntityType(p_EntityType);
		producer.create();

		if (p_IsTest)
		{
			throw new AdempiereException("Rollback because we are in test mode");
		}

		//
		return "@Created@ #" + producer.getCountCreated();
	}

	protected I_AD_Table getTargetTable()
	{
		if (p_AD_Table_ID <= 0)
		{
			throw new AdempiereException("@NotFound@ @AD_Table_ID@ " + p_AD_Table_ID);
		}

		final MTable targetTable = new MTable(getCtx(), p_AD_Table_ID, get_TrxName());
		return targetTable;
	}

	protected List<I_AD_Column> getSourceColumns()
	{
		final IQueryFilter<I_AD_Column> selectedColumnsFilter = getProcessInfo().getQueryFilter();
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
