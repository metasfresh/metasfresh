package org.adempiere.appdict;

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
import java.util.Properties;

import org.adempiere.appdict.model.I_AD_JavaClass;
import org.adempiere.appdict.model.I_AD_JavaClass_Type;
import org.adempiere.util.ISingletonService;

public interface IJavaClassDAO extends ISingletonService
{
	List<I_AD_JavaClass> retrieveJavaClasses(I_AD_JavaClass_Type type);

	/**
	 * Gets {@link I_AD_JavaClass} for given ID.
	 * 
	 * If no record is found for ID, null is returned.
	 * 
	 * @param ctx
	 * @param adJavaClassId
	 * @return java class or null
	 */
	I_AD_JavaClass retriveJavaClassOrNull(final Properties ctx, final int adJavaClassId);

	/**
	 * Gets {@link I_AD_JavaClass_Type} for given ID.
	 * 
	 * @param ctx
	 * @param adJavaClassTypeId
	 * @return {@link I_AD_JavaClass_Type} or null
	 */
	I_AD_JavaClass_Type retrieveJavaClassTypeOrNull(Properties ctx, int adJavaClassTypeId);

	/**
	 * Gets {@link I_AD_JavaClass_Type} of given <code>javaClassDef</code>
	 * 
	 * @param javaClassDef
	 * @return {@link I_AD_JavaClass_Type} or null
	 */
	I_AD_JavaClass_Type retrieveJavaClassTypeOrNull(I_AD_JavaClass javaClassDef);
}
