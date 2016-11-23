/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 * Portions created by Carlos Ruiz are Copyright (C) 2005 QSS Ltda.
 * Contributor(s): Carlos Ruiz (globalqss)
 *****************************************************************************/
package org.eevolution.process;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.TrxRunnable;
import org.compiere.util.ValueNamePair;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import de.metas.logging.MetasfreshLastError;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * Title: Check BOM Structure (free of cycles) Description: Tree cannot contain BOMs which are already referenced
 * 
 * @author Tony Snook (tspc)
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
public class PP_Product_BOM_Check extends JavaProcess
{

	/** The Record */
	private int p_Record_ID = 0;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else
				log.error("Unknown Parameter: " + name);
		}
		p_Record_ID = getRecord_ID();
	} // prepare

	/**
	 * Process
	 * 
	 * @return message
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
		final IProductBOMDAO productBOMDAO = Services.get(IProductBOMDAO.class);

		log.info("Check BOM Structure");

		// Record ID is M_Product_ID of product to be tested
		final I_M_Product xp = InterfaceWrapperHelper.create(getCtx(), p_Record_ID, I_M_Product.class, getTrxName());
		if (!xp.isBOM())
		{
			log.info("Product is not a BOM");
			// No BOM - should not happen, but no problem
			return "OK";
		}

		// Check Parent Level
		final int lowlevel = productBOMBL.calculateProductLowestLevel(xp);
		xp.setLowLevel(lowlevel);
		xp.setIsVerified(true);
		InterfaceWrapperHelper.save(xp);

		// Get Default BOM from this product
		final I_PP_Product_BOM tbom = productBOMDAO.retrieveDefaultBOM(xp);
		if (tbom == null)
		{
			raiseError("No Default BOM found: ", "Check BOM Parent search key");
		}

		// Check All BOM Lines
		if (tbom.getM_Product_ID() != 0)
		{
			for (final I_PP_Product_BOMLine tbomline : productBOMDAO.retrieveLines(tbom))
			{
				final I_M_Product bomLineProduct = tbomline.getM_Product();
				final int bomLineProductLLC = productBOMBL.calculateProductLowestLevel(bomLineProduct);
				bomLineProduct.setLowLevel(bomLineProductLLC);
				bomLineProduct.setIsVerified(true);
				InterfaceWrapperHelper.save(bomLineProduct);
			}
		}

		return "OK";
	} // doIt

	private void raiseError(String errmsg, String hint) throws Exception
	{
		//
		// Mark product as not verified
		Services.get(ITrxManager.class).run(new TrxRunnable()
		{

			@Override
			public void run(String localTrxName) throws Exception
			{
				final I_M_Product product = InterfaceWrapperHelper.create(getCtx(), p_Record_ID, I_M_Product.class, localTrxName); // parent
				product.setIsVerified(false); // set BOM not verified
				InterfaceWrapperHelper.save(product);
			}
		});

		//
		// Throw exception
		String msg = errmsg;
		ValueNamePair pp = MetasfreshLastError.retrieveError();
		if (pp != null)
			msg = pp.getName() + " - ";
		msg += hint;
		throw new AdempiereUserError(msg);
	}

} // M_Product_BOM_Check
