package org.adempiere.ad.menu.api.impl;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.menu.api.IADMenuDAO;
import org.compiere.model.I_AD_Menu;

import de.metas.util.Services;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ADMenuDAO implements IADMenuDAO
{

	@Override
	public List<I_AD_Menu> retrieveMenusWithMissingElements()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Menu.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_AD_Menu.COLUMN_AD_Element_ID, null)
				.create()
				.list(I_AD_Menu.class);
	}

}
