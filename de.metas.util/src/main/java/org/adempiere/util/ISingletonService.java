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
 * Subinterfaces of this interface can be registered with the {@link Services} registry.<br>
 * Note that there is only one instance for each service implementation, so each invocation of {@link Services#get(Class)} with <code>class</code> being an ISingletonService<br>
 * will basically return the same instance of the service implementation.
 * <p>
 * Also see {@link IMultitonService}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ISingletonService extends IService
{
}
