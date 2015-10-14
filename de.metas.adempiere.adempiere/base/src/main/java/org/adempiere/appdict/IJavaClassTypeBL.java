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


import org.adempiere.appdict.model.I_AD_JavaClass_Type;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ISingletonService;


public interface IJavaClassTypeBL extends ISingletonService
{
	/**
	 * Check's the given {@code javaClassType}'s {@code Classname}. If the name is empty or can be loaded as a class by the current thread's context class loader, then the method just returns.
	 * Otherwise it throws {@link AdempiereException}
	 * 
	 * @param javaClassType
	 * @throws AdempiereException if the given {@code Classname} is set, but the class can't be loaded.
	 */
	void checkClassName(I_AD_JavaClass_Type javaClassType);

}
