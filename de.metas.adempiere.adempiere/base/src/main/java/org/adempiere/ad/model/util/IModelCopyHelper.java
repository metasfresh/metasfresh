package org.adempiere.ad.model.util;

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


import org.adempiere.model.InterfaceWrapperHelper;

/**
 * Helper class which assists you to FULLY copy values from a model to another model.
 * 
 * If you want to create a new instance of this helper, use {@link InterfaceWrapperHelper#copy()}.
 * 
 * @author tsa
 *
 */
public interface IModelCopyHelper
{
	/**
	 * Execute copy.
	 * 
	 * NOTE: model will not be saved.
	 */
	void copy();

	/**
	 * Sets from which model are we copying
	 * 
	 * @param fromModel
	 */
	IModelCopyHelper setFrom(final Object fromModel);

	/**
	 * Sets to which model are we copying
	 * 
	 * @param toModel
	 */
	IModelCopyHelper setTo(final Object toModel);

	/**
	 * Sets if we shall NOT copy columns which are flagged with IsCalculated.
	 * 
	 * @param skipCalculatedColumns
	 */
	IModelCopyHelper setSkipCalculatedColumns(boolean skipCalculatedColumns);

	IModelCopyHelper addTargetColumnNameToSkip(String columnName);
}
