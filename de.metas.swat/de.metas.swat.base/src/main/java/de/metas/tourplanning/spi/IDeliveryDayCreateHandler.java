package de.metas.tourplanning.spi;

/*
 * #%L
 * de.metas.swat.base
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


import de.metas.tourplanning.api.IDeliveryDayAllocable;

/**
 * If an implementation of {@link IDeliveryDayHandler} is also implementing this interface then framework will register some model interceptors to model table name to automatically
 * create/update/delete delivery day allocations when that model changes.
 * 
 * @author tsa
 *
 */
public interface IDeliveryDayCreateHandler
{
	/**
	 * 
	 * @return source table name on which this handler binds
	 */
	String getModelTableName();

	/**
	 * Convert given <code>model</code> to {@link IDeliveryDayAllocable}.
	 * 
	 * Framework guarantees that the underlying tablename of given model is {@link #getModelTableName()}.
	 * 
	 * @param model
	 * @return
	 */
	IDeliveryDayAllocable asDeliveryDayAllocable(Object model);
}
