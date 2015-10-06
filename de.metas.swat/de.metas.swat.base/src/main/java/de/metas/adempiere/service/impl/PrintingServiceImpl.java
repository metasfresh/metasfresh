package de.metas.adempiere.service.impl;

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


import org.adempiere.exceptions.AdempiereException;

import de.metas.adempiere.service.IPrintingService;

public class PrintingServiceImpl implements IPrintingService
{
	private final String printerName;
	private final String printerType;
	private final boolean isDirectPrint;

	PrintingServiceImpl(String printerName, String printerType, boolean isDirectPrint)
	{
		if (printerName == null)
			throw new AdempiereException("printerName is null");
		if (printerType == null)
			throw new AdempiereException("printerType is null");
		this.printerName = printerName;
		this.printerType = printerType;
		this.isDirectPrint = isDirectPrint;
	}

	@Override
	public String getPrinterName()
	{
		return printerName;
	}

	@Override
	public String getPrinterType()
	{
		return printerType;
	}

	@Override
	public boolean isDirectPrint()
	{
		return isDirectPrint;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (isDirectPrint ? 1231 : 1237);
		result = prime * result + ((printerName == null) ? 0 : printerName.hashCode());
		result = prime * result + ((printerType == null) ? 0 : printerType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrintingServiceImpl other = (PrintingServiceImpl)obj;
		if (isDirectPrint != other.isDirectPrint)
			return false;
		if (printerName == null)
		{
			if (other.printerName != null)
				return false;
		}
		else if (!printerName.equals(other.printerName))
			return false;
		if (printerType == null)
		{
			if (other.printerType != null)
				return false;
		}
		else if (!printerType.equals(other.printerType))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "PrintingServiceImpl [printerName=" + printerName + ", printerType=" + printerType + ", isDirectPrint=" + isDirectPrint + "]";
	}

	
}
