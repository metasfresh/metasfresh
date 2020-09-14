/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved. *
 * Contributor(s): Victor Perez www.e-evolution.com *
 *****************************************************************************/

package org.eevolution.process;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.util.Services;

/**
 * CopyFromBOM Process
 * Copies BOM Lines from Selected BOM to the Current BOM
 * The BOM being copied to must have no pre-existing BOM Lines
 *
 * @author Tony Snook
 * @version $Id: CopyFromBOM.java,v 1.0 2008/07/04 05:24:03 tspc Exp $
 */
public class CopyFromBOM extends JavaProcess
{
	private final IProductBOMDAO productBOMsRepo = Services.get(IProductBOMDAO.class);
	private int toProductBOMId = 0;
	private int fromProductBOMId = 0;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParameters())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				;
			}
			else if (name.equals("PP_Product_BOM_ID"))
			{
				fromProductBOMId = para.getParameterAsInt();
			}
			else
			{
				log.error("prepare - Unknown Parameter: " + name);
			}
		}
		toProductBOMId = getRecord_ID();
	} // prepare

	@Override
	protected String doIt()
	{
		log.info("From PP_Product_BOM_ID=" + fromProductBOMId + " to " + toProductBOMId);
		if (toProductBOMId == 0)
		{
			throw new AdempiereException("Target PP_Product_BOM_ID == 0");
		}
		if (fromProductBOMId == 0)
		{
			throw new AdempiereException("Source PP_Product_BOM_ID == 0");
		}
		if (toProductBOMId == fromProductBOMId)
		{
			return MSG_OK;
		}

		final I_PP_Product_BOM fromBom = productBOMsRepo.getById(fromProductBOMId);
		final I_PP_Product_BOM toBOM = productBOMsRepo.getById(toProductBOMId);
		if (!productBOMsRepo.retrieveLines(toBOM).isEmpty())
		{
			throw new AdempiereException("@Error@ Existing BOM Line(s)");
		}

		for (final I_PP_Product_BOMLine fromBOMLine : productBOMsRepo.retrieveLines(fromBom))
		{
			final I_PP_Product_BOMLine toBOMLine = InterfaceWrapperHelper.copy()
					.setFrom(fromBOMLine)
					.copyToNew(I_PP_Product_BOMLine.class);
			toBOMLine.setPP_Product_BOM_ID(toBOM.getPP_Product_BOM_ID());
			InterfaceWrapperHelper.saveRecord(toBOMLine);
		}
		return MSG_OK;
	}
}
