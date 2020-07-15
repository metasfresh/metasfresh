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


public class PrinterHWList implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = -2897251108708482160L;

	private List<PrinterHW> hwPrinters;

	public List<PrinterHW> getHwPrinters()
	{
		return hwPrinters;
	}

	public void setHwPrinters(List<PrinterHW> hwPrinters)
	{
		this.hwPrinters = hwPrinters;
	}

	@Override
	public String toString()
	{
		return "PrinterHWList [hwPrinters=" + hwPrinters + "]";
	}
}
