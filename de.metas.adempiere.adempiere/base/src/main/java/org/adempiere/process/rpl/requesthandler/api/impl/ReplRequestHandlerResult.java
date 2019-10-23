package org.adempiere.process.rpl.requesthandler.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerResult;
import org.adempiere.server.rpl.interfaces.I_EXP_Format;
import org.compiere.model.PO;

public class ReplRequestHandlerResult implements IReplRequestHandlerResult
{
	private I_EXP_Format formatToUse;
	
	private PO poToExport;

	public I_EXP_Format getFormatToUse()
	{
		return formatToUse;
	}

	public void setFormatToUse(I_EXP_Format formatToUse)
	{
		this.formatToUse = formatToUse;
	}

	public PO getPOToExport()
	{
		return poToExport;
	}

	public void setPOToExport(PO poToExport)
	{
		this.poToExport = poToExport;
	}
}
