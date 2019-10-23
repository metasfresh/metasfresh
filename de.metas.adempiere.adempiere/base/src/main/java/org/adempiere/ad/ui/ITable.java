package org.adempiere.ad.ui;

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



public interface ITable
{
    Object getValueAt(int rowIndexView, int columnIndexView);
    
	/**
	 * Same as {@link #getValueAt(int, int)} but it uses model coordinates instead of view coordinates.
	 * @param rowIndexModel
	 * @param columnIndexModel
	 * @return value
	 */
    Object getModelValueAt(int rowIndexModel, int columnIndexModel);

	void setColorProvider(ITableColorProvider colorProvider);

	ITableColorProvider getColorProvider();
}
