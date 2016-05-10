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


import java.io.Serializable;
import java.util.List;

public class PrinterHW implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private List<PrinterHWMediaSize> printerHWMediaSizes;
	private List<PrinterHWMediaTray> printerHWMediaTrays;

	public PrinterHW()
	{
		super();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<PrinterHWMediaSize> getPrinterHWMediaSizes()
	{
		return this.printerHWMediaSizes;
	}

	public void setPrinterHWMediaSizes(List<PrinterHWMediaSize> printerHWMediaSizes)
	{
		this.printerHWMediaSizes = printerHWMediaSizes;
	}

	public List<PrinterHWMediaTray> getPrinterHWMediaTrays()
	{
		return this.printerHWMediaTrays;
	}

	public void setPrinterHWMediaTrays(List<PrinterHWMediaTray> printerHWMediaTrays)
	{
		this.printerHWMediaTrays = printerHWMediaTrays;
	}

	@Override
	public String toString()
	{
		return "PrinterHW [name=" + name + ", printerHWMediaSizes=" + printerHWMediaSizes + ", printerHWMediaTrays=" + printerHWMediaTrays + "]";
	}

	/**
	 * Printer HW Media Size Object
	 *
	 * @author al
	 */
	public static class PrinterHWMediaSize implements Serializable
	{
		/**
		 *
		 */
		private static final long serialVersionUID = 5962990173434911764L;

		private String name;
		private String isDefault;

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getIsDefault()
		{
			return isDefault;
		}

		public void setIsDefault(String isDefault)
		{
			this.isDefault = isDefault;
		}

		@Override
		public String toString()
		{
			return "PrinterHWMediaSize [name=" + name + ", isDefault=" + isDefault + "]";
		}
	}

	/**
	 * Printer HW Media Tray Object
	 *
	 * @author al
	 */
	public static class PrinterHWMediaTray implements Serializable
	{
		/**
		 *
		 */
		private static final long serialVersionUID = -1833627999553124042L;

		private String name;
		private String trayNumber;
		private String isDefault;

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getTrayNumber()
		{
			return trayNumber;
		}

		public void setTrayNumber(String trayNumber)
		{
			this.trayNumber = trayNumber;
		}

		public String getIsDefault()
		{
			return isDefault;
		}

		public void setIsDefault(String isDefault)
		{
			this.isDefault = isDefault;
		}

		@Override
		public String toString()
		{
			return "PrinterHWMediaTray [name=" + name + ", trayNumber=" + trayNumber + ", isDefault=" + isDefault + "]";
		}
	}
}
