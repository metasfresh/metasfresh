package org.adempiere.ad.table.api.impl;

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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.compiere.model.I_AD_Column;
import org.compiere.util.Env;

public class PlainADTableDAO extends ADTableDAO
{
	/**
	 * Retrieves the {@link I_AD_Column} but, first it's creating it if is missing.
	 */
	@Override
	public I_AD_Column retrieveColumnOrNull(final String tableName, final String columnName)
	{
		I_AD_Column adColumn = super.retrieveColumnOrNull(tableName, columnName);

		//
		// Automatically create the AD_Column if is missing... there are a couple of tests which are relying on this feature
		if (adColumn == null)
		{
			final IContextAware context = PlainContextAware.newWithTrxName(Env.getCtx(), ITrx.TRXNAME_None);
			final int adTableId = retrieveTableId(tableName);

			adColumn = InterfaceWrapperHelper.newInstance(I_AD_Column.class, context);
			adColumn.setAD_Table_ID(adTableId);
			adColumn.setColumnName(columnName);
			InterfaceWrapperHelper.save(adColumn);
		}

		return adColumn;
	}
}
