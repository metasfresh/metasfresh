package de.metas.ui.web.window_old.shared.command;

/*
 * #%L
 * metasfresh-webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

public final class LookupDataSourceQueryCommand
{
	public static final String COMMAND_Size = "size";
	public static final String COMMAND_Find = "find";
	
	public static final String PARAMETER_Filter = "filter";
	public static final String PARAMETER_FirstRow = "firstRow";
	public static final String PARAMETER_PageLength = "pageLength";
	public static final int DEFAULT_PageLength = 10;

	public static final int SIZE_InvalidFilter = -100;
}
