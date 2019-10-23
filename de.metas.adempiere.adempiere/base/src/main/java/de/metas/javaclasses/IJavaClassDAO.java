package de.metas.javaclasses;

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
import java.util.Properties;

import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;
import de.metas.util.ISingletonService;

public interface IJavaClassDAO extends ISingletonService
{
	List<I_AD_JavaClass> retrieveAllJavaClasses(I_AD_JavaClass_Type type);

	List<I_AD_JavaClass> retrieveJavaClasses(Properties ctx, String javaClassTypeInternalName);

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

	I_AD_JavaClass_Type retrieveJavaClassTypeOrNull(Properties ctx, String internalName);

}
