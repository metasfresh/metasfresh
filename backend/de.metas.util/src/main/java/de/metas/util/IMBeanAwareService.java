package de.metas.util;

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


/**
 * Implemented by services which expose an management bean. If an <code>IMBeanAwareService</code> is also a {@link ISingletonService}, then {@link Services} automatically registered the returned MBean
 * (unless that ben is <code>null</code>).
 * <p> 
 * <b>IMPORTANT: currently {@link IMultitonService} implementations can't implement this interface</b>
 * 
 * 
 * @author tsa
 * 
 */
public interface IMBeanAwareService
{
	/**
	 * Gets the Management Bean used to manage this service
	 * 
	 * @return mbean
	 */
	public Object getMBean();
}
