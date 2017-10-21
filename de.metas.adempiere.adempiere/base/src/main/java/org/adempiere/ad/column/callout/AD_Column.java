package org.adempiere.ad.column.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;

import de.metas.adempiere.service.IColumnBL;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * Disallow logging for Updated, UpdatedBy, Created, CreatedeBy
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Callout(I_AD_Column.class)
public class AD_Column
{

	public static final AD_Column instance = new AD_Column();

	@CalloutMethod(columnNames = { I_AD_Column.COLUMNNAME_ColumnName })
	public void onColumnName(final I_AD_Column column, final ICalloutField field)
	{
		final String columnName = column.getColumnName();
		if (Check.isEmpty(columnName, true))
		{
			return;
		}
		column.setIsAllowLogging(Services.get(IColumnBL.class).getDefaultAllowLoggingByColumnName(columnName));
	}
}
