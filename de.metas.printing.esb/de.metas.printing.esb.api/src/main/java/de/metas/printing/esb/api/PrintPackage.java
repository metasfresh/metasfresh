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


import java.util.Collections;
import java.util.List;

public class PrintPackage
{
	public static final String FORMAT_PDF = "application/pdf";

	private String transactionId;

	private String printPackageId;

	/**
	 * Number of pages that the next print package will have
	 */
	private int pageCount;

	/**
	 * Data format (e.g. PDF)
	 */
	private String format;

	private List<PrintPackageInfo> printPackageInfos = null;

	private String printJobInstructionsID;

	private int copies;

	/**
	 * @return the transactionId
	 */
	public String getTransactionId()
	{
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId)
	{
		this.transactionId = transactionId;
	}

	public String getPrintPackageId()
	{
		return printPackageId;
	}

	public void setPrintPackageId(String printPackageId)
	{
		this.printPackageId = printPackageId;
	}

	public int getPageCount()
	{
		return pageCount;
	}

	public void setPageCount(int pageCount)
	{
		this.pageCount = pageCount;
	}

	public String getFormat()
	{
		return format;
	}

	public void setFormat(String format)
	{
		this.format = format;
	}

	public List<PrintPackageInfo> getPrintPackageInfos()
	{
		if (printPackageInfos == null)
		{
			return Collections.emptyList();
		}
		return printPackageInfos;
	}

	public void setPrintPackageInfos(List<PrintPackageInfo> printPackageInfos)
	{
		this.printPackageInfos = printPackageInfos;
	}

	public String getPrintJobInstructionsID()
	{
		return printJobInstructionsID;
	}

	public void setPrintJobInstructionsID(String printJobInstructionsID)
	{
		this.printJobInstructionsID = printJobInstructionsID;
	}

	public int getCopies()
	{
		return copies;
	}

	public void setCopies(final int copies)
	{
		this.copies = copies;
	}

	@Override
	public String toString()
	{
		return String.format("PrintPackage [transactionId=%s, printPackageId=%s, pageCount=%s, copies=%s, format=%s, printPackageInfos=%s, printJobInstructionsID=%s]", transactionId, printPackageId,
				pageCount, copies, format, printPackageInfos, printJobInstructionsID);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + copies;
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + pageCount;
		result = prime * result + ((printJobInstructionsID == null) ? 0 : printJobInstructionsID.hashCode());
		result = prime * result + ((printPackageId == null) ? 0 : printPackageId.hashCode());
		result = prime * result + ((printPackageInfos == null) ? 0 : printPackageInfos.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
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
		PrintPackage other = (PrintPackage)obj;
		if (copies != other.copies)
			return false;
		if (format == null)
		{
			if (other.format != null)
				return false;
		}
		else if (!format.equals(other.format))
			return false;
		if (pageCount != other.pageCount)
			return false;
		if (printJobInstructionsID == null)
		{
			if (other.printJobInstructionsID != null)
				return false;
		}
		else if (!printJobInstructionsID.equals(other.printJobInstructionsID))
			return false;
		if (printPackageId == null)
		{
			if (other.printPackageId != null)
				return false;
		}
		else if (!printPackageId.equals(other.printPackageId))
			return false;
		if (printPackageInfos == null)
		{
			if (other.printPackageInfos != null)
				return false;
		}
		else if (!printPackageInfos.equals(other.printPackageInfos))
			return false;
		if (transactionId == null)
		{
			if (other.transactionId != null)
				return false;
		}
		else if (!transactionId.equals(other.transactionId))
			return false;
		return true;
	}
}
