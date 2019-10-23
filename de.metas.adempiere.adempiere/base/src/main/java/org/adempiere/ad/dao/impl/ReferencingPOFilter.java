package org.adempiere.ad.dao.impl;

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


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MTable;

import de.metas.adempiere.model.IPOReferenceAware;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Filter for records that reference a given PO via the {@code AD_Table_ID} and {@code Record_ID} columns.
 * 
 * @param <T> type of the result
 */
public class ReferencingPOFilter<T extends IPOReferenceAware> implements IQueryFilter<T>, ISqlQueryFilter
{
	private Object referencedModel;

	/**
	 * 
	 * @param referencedModel May not be null; this filter will only accept records that reference the given model. 
	 */
	public ReferencingPOFilter(final Object referencedModel)
	{
		super();
		
		Check.assumeNotNull(referencedModel, "Param 'referencedModel' not null");
		this.referencedModel = referencedModel;
	}

	@Override
	public boolean accept(final T model)
	{
		if(!InterfaceWrapperHelper.isInstanceOf(model, IPOReferenceAware.class))
		{
			return false;
		}
		final IPOReferenceAware referencingModel = InterfaceWrapperHelper.create(model, IPOReferenceAware.class);
		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(InterfaceWrapperHelper.getModelTableName(referencedModel));
		return 
				referencingModel.getAD_Table_ID() == tableId
				&& referencingModel.getRecord_ID() == InterfaceWrapperHelper.getId(referencedModel);
	}

	@Override
	public String getSql()
	{
		buildSql();
		return sqlWhereClause;
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx)
	{
		buildSql();
		return sqlParams;
	}

	private boolean sqlBuilt = false;
	private String sqlWhereClause = null;
	private List<Object> sqlParams = null;

	private final void buildSql()
	{
		if (sqlBuilt)
		{
			return;
		}

		sqlWhereClause = "Record_ID = ? AND AD_Table_ID = ?";
		
		sqlParams = new ArrayList<Object>();
		sqlParams.add(InterfaceWrapperHelper.getId(referencedModel));
		sqlParams.add(MTable.getTable_ID(InterfaceWrapperHelper.getModelTableName(referencedModel)));
		
		sqlBuilt = true;
	}
}
