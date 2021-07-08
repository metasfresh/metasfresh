package org.adempiere.ad.modelvalidator;

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


import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_AD_Client;

import de.metas.util.ISingletonService;

import javax.annotation.Nullable;

/**
 * Service used to register model interceptors
 * 
 * @author tsa
 * 
 */
public interface IModelInterceptorRegistry extends ISingletonService
{
	/**
	 * Register model interceptor for all {@link I_AD_Client}s.
	 * 
	 * @param interceptorObj interceptor annotated with {@link Interceptor} or which is implementing {@link IModelInterceptor}.
	 */
	void addModelInterceptor(Object interceptorObj);

	/**
	 * Register model interceptor for given {@link I_AD_Client}.
	 * 
	 * If <code>client</code> is null, it will have the same effect as calling {@link #addModelInterceptor(Object)}.
	 * 
	 * @param interceptorObj interceptor annotated with {@link Interceptor} or which is implementing {@link IModelInterceptor}.
	 * @param client tenant on which we register given interceptor
	 */
	void addModelInterceptor(Object interceptorObj, @Nullable I_AD_Client client);
}
