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

public class PrintJobInstructionsConfirm implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = -9040749090880603823L;

	private String printJobInstructionsID;
	private String errorMsg;
	private PrintJobInstructionsStatusEnum status;

	public String getPrintJobInstructionsID()
	{
		return printJobInstructionsID;
	}

	public void setPrintJobInstructionsID(String printJobInstructionsID)
	{
		this.printJobInstructionsID = printJobInstructionsID;
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg)
	{
		this.errorMsg = errorMsg;
	}

	public PrintJobInstructionsStatusEnum getStatus()
	{
		return status;
	}

	public void setStatus(PrintJobInstructionsStatusEnum status)
	{
		this.status = status;
	}

	@Override
	public String toString()
	{
		return "PrintJobInstructionsConfirm [printJobInstructionsID=" + printJobInstructionsID + ", errorMsg=" + errorMsg + ", status=" + status + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errorMsg == null) ? 0 : errorMsg.hashCode());
		result = prime * result + ((printJobInstructionsID == null) ? 0 : printJobInstructionsID.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		PrintJobInstructionsConfirm other = (PrintJobInstructionsConfirm)obj;
		if (errorMsg == null)
		{
			if (other.errorMsg != null)
				return false;
		}
		else if (!errorMsg.equals(other.errorMsg))
			return false;
		if (printJobInstructionsID == null)
		{
			if (other.printJobInstructionsID != null)
				return false;
		}
		else if (!printJobInstructionsID.equals(other.printJobInstructionsID))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
}
