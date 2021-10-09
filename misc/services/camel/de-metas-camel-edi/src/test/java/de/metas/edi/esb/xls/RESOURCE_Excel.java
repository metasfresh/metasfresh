package de.metas.edi.esb.xls;

/*
 * #%L
 * de.metas.edi.esb
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


import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * Excel resource definition used for Excel to C_OLCand import tests
 *
 * @author tsa
 *
 */
public abstract class RESOURCE_Excel
{
	// Fields to be configured by extending classes
	protected String resourceName;
	protected XLS_OLCand_Row_Expectations rowsExpectations;
	/** How many data rows our Excel file has (valid or not) */
	protected int count_DataRows;
	/** How many data rows which are also valid and can be imported to metasfresh */
	protected int count_ValidDataRows;

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "[" + resourceName + "]";
	}

	public String getResourceName()
	{
		return resourceName;
	}

	public File getFile() throws URISyntaxException
	{
		return new File(RESOURCE_Excel.class.getResource(resourceName).toURI());
	}

	public InputStream getInputStream()
	{
		return RESOURCE_Excel.class.getResourceAsStream(resourceName);
	}

	/** @return Predefined rows expectations */
	public XLS_OLCand_Row_Expectations getRowsExpectations()
	{
		return rowsExpectations;
	}

	/** @return How many data rows our Excel file has (valid or not) */
	public int getCountDataRows()
	{
		return count_DataRows;
	}

	/** @return How many data rows which are also valid and can be imported to metasfresh */
	public int getCountValidDataRows()
	{
		return count_ValidDataRows;
	}
}
