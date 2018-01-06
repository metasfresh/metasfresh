package org.adempiere.ad.element.model.interceptor;

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.service.IADElementDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.ModelValidator;

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
@Interceptor(I_AD_Element.class)
public class AD_Element
{

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_AD_Element.COLUMNNAME_ColumnName)
	public void onColumnNameDeleted(final I_AD_Element element)
	{
		final String columnName = element.getColumnName();
		if (!Check.isEmpty(columnName))
		{
			// nothing to do
			return;
		}

		final List<I_AD_Column> columnsUsingElement = Services.get(IADElementDAO.class).retrieveColumns(element.getAD_Element_ID());

		if (!Check.isEmpty(columnsUsingElement))
		{
			throw new AdempiereException("The ColumnName of the element "
					+ element.getName()
					+ " cannot be deleted because the element is used in the following columns: "
					+ columnsUsingElement.toString());
		}
	}
}
