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

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.ModelValidator;

import de.metas.javaclasses.IJavaClassBL;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.util.Services;

@Validator(I_AD_JavaClass.class)
public class AD_JavaClass
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW })
	public void onChangeClassname(final I_AD_JavaClass javaClassDef)
	{
		final IJavaClassBL javaClassBL = Services.get(IJavaClassBL.class);
		final Class<?> clazz = javaClassBL.verifyClassName(javaClassDef);
		javaClassDef.setIsInterface(clazz.isInterface());

		if (!clazz.isInterface())
		{
			// additionally try to instantiate the class
			javaClassBL.newInstance(javaClassDef);
		}
	}
}
