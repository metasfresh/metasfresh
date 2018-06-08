package org.adempiere.ad.dao;

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

@FunctionalInterface
public interface IQueryUpdater<T>
{
	boolean MODEL_UPDATED = true;
	boolean MODEL_SKIPPED = false;

	/**
	 * Update given model.
	 * 
	 * If this method return <code>false</code> (i.e. model was not updated), the model won't be saved.
	 * 
	 * @param model
	 * @return true if model was updated. Or better use {@link #MODEL_UPDATED} and {@link #MODEL_SKIPPED}
	 */
	boolean update(final T model);
}
