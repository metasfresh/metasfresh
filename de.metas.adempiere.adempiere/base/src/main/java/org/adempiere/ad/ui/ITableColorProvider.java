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


import java.awt.Color;

/**
 * {@link ITable}'s color provider.
 * 
 * NOTE to developer: instead of extending this class, please extends the {@link TableColorProviderAdapter}
 * 
 * @author tsa
 *
 */
public interface ITableColorProvider
{
	/**
	 * Value returned when provider does not want to specify a color value for given parameters
	 */
	Color COLOR_NONE = null;

	/**
	 * @return foreground color or {@link #COLOR_NONE}
	 */
	Color getForegroundColor(final ITable table, final int rowIndexModel);

	/**
	 * @return background color or {@link #COLOR_NONE}
	 */
	Color getBackgroundColor(final ITable table, final int rowIndexModel);
}
