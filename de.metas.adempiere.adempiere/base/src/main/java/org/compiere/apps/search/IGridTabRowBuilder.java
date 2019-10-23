package org.compiere.apps.search;

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
 * Implementation of this interface are used for applying customized configurations to a given grid tab record.
 * 
 * Mainly used from callouts.
 * 
 * @author tsa
 * 
 */
public interface IGridTabRowBuilder
{
	/**
	 * Apply customizations for given model
	 * 
	 * @param model
	 */
	void apply(final Object model);

	/**
	 * 
	 * @return true if this builder can set all data in order to have a valid new record; false if this will customize existing created records
	 */
	boolean isCreateNewRecord();

	void setSource(Object model);

	/**
	 * 
	 * @return true if this builder is well defined and it has all informations to be able to perform on given model. i.e. you can safely call {@link #apply(Object)}.
	 */
	boolean isValid();
}
