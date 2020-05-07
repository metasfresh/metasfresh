package org.adempiere.ad.element.api.impl;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.element.api.IElementDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_UI_Element;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class ElementDAO implements IElementDAO
{

	@Override
	public List<I_AD_UI_Element> retrieveChildUIElements(final I_AD_Element element)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Element.class)
				.addEqualsFilter(I_AD_Element.COLUMN_AD_Element_ID, element.getAD_Element_ID())
				.andCollectChildren(I_AD_Column.COLUMN_AD_Element_ID)
				.andCollectChildren(I_AD_Field.COLUMN_AD_Column_ID)
				.andCollectChildren(I_AD_UI_Element.COLUMN_AD_Field_ID)
				.create()
				.list();

	}

}
