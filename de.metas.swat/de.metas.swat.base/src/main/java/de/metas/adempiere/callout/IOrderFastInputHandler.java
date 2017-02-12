package de.metas.adempiere.callout;

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


import org.compiere.apps.search.IGridTabRowBuilder;
import org.compiere.model.GridTab;

public interface IOrderFastInputHandler
{

	void clearFields(GridTab gridTab);

	/**
	 * 
	 * @param gridTab
	 * @return true if focus was actually requested
	 */
	boolean requestFocus(GridTab gridTab);

	IGridTabRowBuilder createLineBuilderFromHeader(Object model);
}
