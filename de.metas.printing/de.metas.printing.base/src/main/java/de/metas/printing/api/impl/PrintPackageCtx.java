package de.metas.printing.api.impl;

/*
 * #%L
 * de.metas.printing.base
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


import de.metas.printing.api.IPrintPackageCtx;

public class PrintPackageCtx implements IPrintPackageCtx
{
	private String hostKey = null;
	private String transactionId = null;

	PrintPackageCtx()
	{
		// nothing
	}

	@Override
	public String getHostKey()
	{
		return hostKey;
	}

	public void setHostKey(final String hostKey)
	{
		this.hostKey = hostKey;
	}

	@Override
	public String getTransactionId()
	{
		return transactionId;
	}

	@Override
	public void setTransactionId(final String transactionId)
	{
		this.transactionId = transactionId;
	}

	@Override
	public String toString()
	{
		return "PrintPackageCtx [hostKey=" + hostKey + ", transactionId=" + transactionId + "]";
	}
}
