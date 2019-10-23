package de.metas.cache.interceptor;

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


/**
 * Partial handler for a {@link CachedMethodDescriptor}.
 * 
 * Implementations of this interface are handling a method parameter, target object instance in some cases etc.
 * 
 * @author tsa
 *
 */
interface ICachedMethodPartDescriptor
{
	/**
	 * Extract cache keys from given target object which was invoked with given parameters.
	 * 
	 * @param keyBuilder key builder where to append the extracted keys
	 * @param targetObject target object
	 * @param params method invocation parameters
	 */
	void extractKeyParts(CacheKeyBuilder keyBuilder, Object targetObject, Object[] params);
}
