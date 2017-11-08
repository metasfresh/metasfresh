package de.metas.picking.legacy.form;

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


/**
 * Null implementation of {@link ITableRowSearchSelectionMatcher} which actually does nothing.
 * 
 * @author tsa
 * 
 */
public final class NullTableRowSearchSelectionMatcher extends AbstractTableRowSearchSelectionMatcher
{
	public static final NullTableRowSearchSelectionMatcher instance = new NullTableRowSearchSelectionMatcher();

	private NullTableRowSearchSelectionMatcher()
	{
		super();
		
		// initialize right away
		init();
	}

	@Override
	public boolean isAllowMultipleResults()
	{
		return false;
	}

	@Override
	public String getName()
	{
		return "";
	}

	@Override
	protected boolean load()
	{
		// return load ok => valid matcher
		return true;
	}

}
