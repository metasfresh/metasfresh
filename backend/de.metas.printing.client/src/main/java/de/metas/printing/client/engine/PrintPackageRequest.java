package de.metas.printing.client.engine;

/*
 * #%L
 * de.metas.printing.esb.client
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

import java.awt.print.Printable;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import de.metas.printing.esb.api.PrintPackage;
import de.metas.printing.esb.api.PrintPackageInfo;

public class PrintPackageRequest
{
	private final PrintPackage printPackage;
	private final PrintPackageInfo printPackageInfo;
	private final PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
	private PrintService printService;
	private String printJobName;
	private Printable printable;
	private int numPages;

	protected PrintPackageRequest(final PrintPackage printPackage, final PrintPackageInfo printPackageInfo)
	{
		if (printPackage == null)
		{
			throw new IllegalArgumentException("printPackage is null");
		}
		this.printPackage = printPackage;

		if (printPackageInfo == null)
		{
			throw new IllegalArgumentException("printPackageInfo is null");
		}
		this.printPackageInfo = printPackageInfo;
	}

	public PrintService getPrintService()
	{
		return printService;
	}

	public void setPrintService(final PrintService printService)
	{
		this.printService = printService;
	}

	public PrintPackage getPrintPackage()
	{
		return printPackage;
	}

	public PrintPackageInfo getPrintPackageInfo()
	{
		return printPackageInfo;
	}

	public PrintRequestAttributeSet getAttributes()
	{
		return attributes;
	}

	public String getPrintJobName()
	{
		return printJobName;
	}

	public void setPrintJobName(final String printJobName)
	{
		this.printJobName = printJobName;
	}

	public Printable getPrintable()
	{
		return printable;
	}

	public void setPrintable(final Printable printable)
	{
		this.printable = printable;
	}

	public int getNumPages()
	{
		return numPages;
	}

	public void setNumPages(final int numPages)
	{
		this.numPages = numPages;
	}

	@Override
	public String toString()
	{
		return "PrintPackageRequest ["
				+ "printPackage=" + printPackage
				+ ", printPackageInfo=" + printPackageInfo
				+ ", attributes=" + attributes
				+ ", printService=" + printService
				+ ", printJobName=" + printJobName
				+ ", printable=" + printable
				+ "]";
	}
}
