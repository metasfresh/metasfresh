package org.adempiere.appdict.validation.model.validator;

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

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.appdict.IJavaClassBL;
import org.adempiere.appdict.IJavaClassDAO;
import org.adempiere.appdict.IJavaClassTypeBL;
import org.adempiere.appdict.model.I_AD_JavaClass;
import org.adempiere.appdict.model.I_AD_JavaClass_Type;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

@Validator(I_AD_JavaClass_Type.class)
public class AD_JavaClass_Type
{
	/**
	 * Loads the given {@code type}'s {@link I_AD_JavaClass} records and attempts to initialize them. This method only needs to be called on java class type changes (because new types don'T have
	 * classes). It needs to be called <b>after</b> the change, because the classes are loaded from DB and the in turn load the type from DB (so it's mandatory that the type has already been stored
	 * within the current transaction).
	 * 
	 * @param type
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_AD_JavaClass_Type.COLUMNNAME_Classname })
	public void onChangeClassname(final I_AD_JavaClass_Type type)
	{
		final List<I_AD_JavaClass> classes = Services.get(IJavaClassDAO.class).retrieveJavaClasses(type);

		for (I_AD_JavaClass clazz : classes)
		{
			Services.get(IJavaClassBL.class).newInstance(clazz);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW })
	public void onNewType(final I_AD_JavaClass_Type type)
	{
		Services.get(IJavaClassTypeBL.class).checkClassName(type);
	}

}
