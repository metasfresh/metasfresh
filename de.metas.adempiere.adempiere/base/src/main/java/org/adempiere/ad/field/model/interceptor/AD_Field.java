package org.adempiere.ad.field.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
@Interceptor(I_AD_Field.class)
public class AD_Field
{	
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_AD_Field.COLUMNNAME_AD_Name_ID)
	public void onNameIDChanged(final I_AD_Field field)
	{
		if(field.getAD_Name_ID() <= 0)
		{
			// the AD_Name_ID was set to null. Get back to the values from the AD_Column
			final I_AD_Column fieldColumn = field.getAD_Column();
			field.setName(fieldColumn.getName());
			field.setDescription(fieldColumn.getDescription());
			field.setHelp(fieldColumn.getHelp());
			
		}
		
		final I_AD_Element fieldElement = field.getAD_Name();
		
		field.setName(fieldElement.getName());
		field.setDescription(fieldElement.getDescription());
		field.setHelp(fieldElement.getHelp());
		
		
	}
}
