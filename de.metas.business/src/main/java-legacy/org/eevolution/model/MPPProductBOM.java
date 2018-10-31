/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *                 Teo Sarca, http://www.arhipac.ro                           *
 *****************************************************************************/
//package org.compiere.mfg.model;
package org.eevolution.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.LegacyAdapters;
import org.eevolution.api.IProductBOMDAO;

import de.metas.util.Services;

/**
 * PP Product BOM Model.
 * 
 * @author Victor Perez www.e-evolution.com
 * @author Teo Sarca, http://www.arhipac.ro
 */
public class MPPProductBOM extends X_PP_Product_BOM
{
	private static final long serialVersionUID = -5770988975738210823L;

	@Deprecated
	public static MPPProductBOM get(Properties ctx, int PP_Product_BOM_ID)
	{
		final I_PP_Product_BOM bom = Services.get(IProductBOMDAO.class).retrieveBOMById(ctx, PP_Product_BOM_ID);
		final MPPProductBOM bomPO = LegacyAdapters.convertToPO(bom);
		return bomPO;
	}

	public MPPProductBOM(Properties ctx, int PP_Product_BOM_ID, String trxName)
	{
		super(ctx, PP_Product_BOM_ID, trxName);
	}

	public MPPProductBOM(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Deprecated
	public MPPProductBOMLine[] getLines()
	{
		final List<I_PP_Product_BOMLine> lines = Services.get(IProductBOMDAO.class).retrieveLines(this);
		final MPPProductBOMLine[] linesArr = LegacyAdapters.convertToPOArray(lines, MPPProductBOMLine.class);
		return linesArr;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MPPProductBOM[")
				.append(get_ID()).append("-").append(getDocumentNo())
				.append(", Value=").append(getValue())
				.append("]");
		return sb.toString();
	}
}	// MPPProductBOM
