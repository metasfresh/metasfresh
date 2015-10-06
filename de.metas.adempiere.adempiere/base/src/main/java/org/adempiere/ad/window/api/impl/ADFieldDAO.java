package org.adempiere.ad.window.api.impl;

/*
 * #%L
 * ADempiere ERP - Base
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
import org.adempiere.ad.window.api.IADFieldDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;

public class ADFieldDAO implements IADFieldDAO
{
	@Override
	public List<I_AD_Field> retrieveFields(final I_AD_Tab adTab)
	{
		Check.assumeNotNull(adTab, "adTab not null");
		
		final IQueryBuilder<I_AD_Field> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Field.class)
				.setContext(adTab)
				.addEqualsFilter(I_AD_Field.COLUMNNAME_AD_Tab_ID, adTab.getAD_Tab_ID());

		queryBuilder.orderBy()
				.addColumn(I_AD_Field.COLUMNNAME_AD_Field_ID);
		
		return queryBuilder
				.create()
				.list();
	}
}
