package org.adempiere.util.proxy;

/*
 * #%L
 * de.metas.util
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


import java.lang.annotation.Annotation;

import de.metas.util.IService;

/**
 * Be sure to know about the current implementations' restrictions.
 * 
 * @author ts
 * 
 */
public interface IServiceInterceptor
{

	/**
	 * @param serviceInstanceClass the class of an {@link IService} implementation.
	 * @return intercepted class or original class if interception was not needed
	 */
	<T extends IService> Class<T> createInterceptedClass(final Class<T> serviceInstanceClass);

	Object registerInterceptor(Class<? extends Annotation> annotationClass, Object interceptorImpl);
}
