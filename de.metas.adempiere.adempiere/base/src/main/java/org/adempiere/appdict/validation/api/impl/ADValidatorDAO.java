package org.adempiere.appdict.validation.api.impl;

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


import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.appdict.validation.api.IADValidatorDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.Query;

public class ADValidatorDAO implements IADValidatorDAO
{
	@Override
	public <T> Iterator<T> retrieveApplicationDictionaryItems(final Properties ctx, final Class<T> appDictClass)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(appDictClass);
		final String whereClause = null;

		return new Query(ctx, tableName, whereClause, ITrx.TRXNAME_None)
				.setOnlyActiveRecords(true)
				.iterate(appDictClass, false);
	}
}
