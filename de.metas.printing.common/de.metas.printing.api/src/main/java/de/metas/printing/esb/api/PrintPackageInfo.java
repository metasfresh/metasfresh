package de.metas.printing.esb.api;

/*
 * #%L
 * de.metas.printing.esb.api
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


public class PrintPackageInfo
{
	/**
	 * The name of one of your local printers
	 */
	private String printService;

	/**
	 * Your local printer's media tray to use
	 */
	private String tray;

	/**
	 * Your local printer's media tray number
	 */
	private int trayNumber;

	/**
	 * Number of 1st page to print
	 */
	private int pageFrom;

	/**
	 * Number of last page to print
	 */
	private int pageTo;

	/**
	 * Calibration on the horizontal axis.
	 */

	private int calX;

	/**
	 * Calibration on the vertical axis.
	 */
	private int calY;
	
	public int getTrayNumber()
	{
		return trayNumber;
	}

	public void setTrayNumber(int trayNumber)
	{
		this.trayNumber = trayNumber;
	}
	
	public int getCalX()
	{
		return calX;
	}

	public void setCalX(int calX)
	{
		this.calX = calX;
	}

	public int getCalY()
	{
		return calY;
	}

	public void setCalY(int calY)
	{
		this.calY = calY;
	}

	public String getPrintService()
	{
		return printService;
	}

	/**
	 * @param printService the printService to set
	 */
	public void setPrintService(String printService)
	{
		this.printService = printService;
	}

	public String getTray()
	{
		return tray;
	}

	/**
	 * @param tray the tray to set
	 */
	public void setTray(String tray)
	{
		this.tray = tray;
	}

	public int getPageFrom()
	{
		return pageFrom;
	}

	/**
	 * @param pageFrom the pageFrom to set
	 */
	public void setPageFrom(int pageFrom)
	{
		this.pageFrom = pageFrom;
	}

	public int getPageTo()
	{
		return pageTo;
	}

	/**
	 * @param pageTo the pageTo to set
	 */
	public void setPageTo(int pageTo)
	{
		this.pageTo = pageTo;
	}


	@Override
	public String toString()
	{
		return "PrintPackageInfo [printService=" + printService + ", tray=" + tray + ", pageFrom=" + pageFrom + ", pageTo=" + pageTo + ", calX=" + calX + ", calY=" + calY + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + calX;
		result = prime * result + calY;
		result = prime * result + pageFrom;
		result = prime * result + pageTo;
		result = prime * result + ((printService == null) ? 0 : printService.hashCode());
		result = prime * result + ((tray == null) ? 0 : tray.hashCode());
		result = prime * result + trayNumber;
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
		PrintPackageInfo other = (PrintPackageInfo)obj;
		if (calX != other.calX)
			return false;
		if (calY != other.calY)
			return false;
		if (pageFrom != other.pageFrom)
			return false;
		if (pageTo != other.pageTo)
			return false;
		if (printService == null)
		{
			if (other.printService != null)
				return false;
		}
		else if (!printService.equals(other.printService))
			return false;
		if (tray == null)
		{
			if (other.tray != null)
				return false;
		}
		else if (!tray.equals(other.tray))
			return false;
		if (trayNumber != other.trayNumber)
			return false;
		return true;
	}
}
