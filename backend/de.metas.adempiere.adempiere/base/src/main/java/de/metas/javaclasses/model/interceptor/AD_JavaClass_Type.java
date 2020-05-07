package de.metas.javaclasses.model.interceptor;

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

import java.util.List;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.javaclasses.IJavaClassBL;
import de.metas.javaclasses.IJavaClassDAO;
import de.metas.javaclasses.IJavaClassTypeBL;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;

/**
 * Note: this class is both an interceptor and a callout.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Interceptor(I_AD_JavaClass_Type.class)
@Callout(I_AD_JavaClass_Type.class)
public class AD_JavaClass_Type
{
	/**
	 * Loads the given {@code type}'s {@link I_AD_JavaClass} records and attempts to initialize them. This method only needs to be called on java class type changes (because new types don't have
	 * classes). It needs to be called <b>after</b> the change, because the classes are loaded from DB and the in turn load the type from DB (so it's mandatory that the type has already been stored
	 * within the current transaction).
	 *
	 * @param type
	 */
	@ModelChange(
			timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_AD_JavaClass_Type.COLUMNNAME_Classname })
	public void onChangeClassname(final I_AD_JavaClass_Type type)
	{
		final IJavaClassDAO javaClassDAO = Services.get(IJavaClassDAO.class);

		final List<I_AD_JavaClass> classes = javaClassDAO.retrieveAllJavaClasses(type);
		for (final I_AD_JavaClass clazz : classes)
		{
			if(!clazz.isActive())
			{
				continue;
			}
			if(clazz.isInterface())
			{
				continue;
			}
			Services.get(IJavaClassBL.class).newInstance(clazz);
		}
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW },
			ifColumnsChanged = { I_AD_JavaClass_Type.COLUMNNAME_Classname })
	public void checkClassAndUpdateInternalName(final I_AD_JavaClass_Type javaClassType)
	{
		final boolean throwEx = true; // we don't want an exception.
		checkClassAndUpdateInternalName(javaClassType, throwEx);
	}

	@CalloutMethod(columnNames = I_AD_JavaClass_Type.COLUMNNAME_Classname)
	public void checkClassAndUpdateInternalName(I_AD_JavaClass_Type javaClassType, ICalloutField field)
	{
		final boolean throwEx = false; // we don't want an exception.
		checkClassAndUpdateInternalName(javaClassType, throwEx);
	}

	private void checkClassAndUpdateInternalName(I_AD_JavaClass_Type javaClassType, final boolean throwEx)
	{
		final IJavaClassTypeBL javaClassTypeBL = Services.get(IJavaClassTypeBL.class);

		final Class<?> classNameClass = javaClassTypeBL.checkClassName(javaClassType, throwEx);
		if (classNameClass == null)
		{
			javaClassType.setInternalName(null);
			return;
		}
		javaClassType.setInternalName(classNameClass.getName());
	}
}
