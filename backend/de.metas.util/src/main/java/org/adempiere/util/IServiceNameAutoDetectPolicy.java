package org.adempiere.util;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * If {@link Services#isAutodetectServices()} is <code>true</code> (which is usually the case!)<br>
 * and no service implementation has been explicitly registered for a given {@link IService} class,<br>
 * then an implementor of this class constructs the class name of the service implementation class.
 * <p>
 * Note that implementation don't need to make sure that a class with the constructed name actually exists.
 *
 * @see Services#setServiceNameAutoDetectPolicy(IServiceNameAutoDetectPolicy)
 *
 */
public interface IServiceNameAutoDetectPolicy
{
	String getServiceImplementationClassName(Class<? extends IService> clazz);
}
