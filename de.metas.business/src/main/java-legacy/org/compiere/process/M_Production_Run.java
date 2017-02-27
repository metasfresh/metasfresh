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
 *****************************************************************************/

package org.compiere.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.util.Services;
import org.compiere.model.MLocator;
import org.compiere.model.MProduct;
import org.compiere.model.MStorage;
import org.compiere.model.MTransaction;
import org.compiere.model.Query;
import org.compiere.model.X_M_Production;
import org.compiere.model.X_M_ProductionLine;
import org.compiere.model.X_M_ProductionPlan;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.ValueNamePair;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;

import de.metas.logging.MetasfreshLastError;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.product.IProductBL;

/**
 * Production of BOMs
 *   1) Creating ProductionLines when IsCreated = 'N'
 *   2) Posting the Lines (optionally only when fully stocked)
 *
 * @author victor.perez@e-evolution.com
 * @contributor: Carlos Ruiz (globalqss) - review backward compatibility - implement mustBeStocked properly
 */
public class M_Production_Run extends JavaProcess {

	/** The Record */
	private int p_Record_ID = 0;

	private boolean mustBeStocked = false;

	private int m_level = 0;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("MustBeStocked"))
				mustBeStocked = ((String) para[i].getParameter()).equals("Y");
			else
				log.error("Unknown Parameter: " + name);
		}
		p_Record_ID = getRecord_ID();
	} //prepare

	/**
	 * Process
	 *
	 * @return message
	 * @throws Exception
	 */

	@Override
	protected String doIt() throws Exception
	{
		log.info("Search fields in M_Production");

		X_M_Production production = new X_M_Production(getCtx(), p_Record_ID, get_TrxName());
		/**
		 * No Action
		 */
		if (production.isProcessed())
		{
			log.info("Already Posted");
			return "@AlreadyPosted@";
		}


			String whereClause = "M_Production_ID=? ";
			List<X_M_ProductionPlan> lines = new Query(getCtx(), X_M_ProductionPlan.Table_Name , whereClause, get_TrxName())
													  .setParameters(new Object[]{p_Record_ID })
													  .setOrderBy("Line, M_Product_ID")
													  .list();
				for (X_M_ProductionPlan pp :lines)
				{

					if (!production.isCreated())
					{
						int line = 100;
						int no = DB.executeUpdateEx("DELETE FROM M_ProductionLine WHERE M_ProductionPlan_ID = ?", new Object[]{pp.getM_ProductionPlan_ID()},get_TrxName());
						if (no == -1) raiseError("ERROR", "DELETE FROM M_ProductionLine WHERE M_ProductionPlan_ID = "+ pp.getM_ProductionPlan_ID());

						MProduct product = MProduct.get(getCtx(), pp.getM_Product_ID());

						X_M_ProductionLine pl = new X_M_ProductionLine(getCtx(), 0 , get_TrxName());
						pl.setLine(line);
						pl.setDescription(pp.getDescription());
						pl.setM_Product_ID(pp.getM_Product_ID());
						pl.setM_Locator_ID(pp.getM_Locator_ID());
						pl.setM_ProductionPlan_ID(pp.getM_ProductionPlan_ID());
						pl.setMovementQty(pp.getProductionQty());
						pl.saveEx();
						if (explosion(pp, product, pp.getProductionQty() , line) == 0 )
							raiseError("No BOM Lines", "");

					}
					else
					{
						whereClause = "M_ProductionPlan_ID= ? ";
						List<X_M_ProductionLine> production_lines = new Query(getCtx(), X_M_ProductionLine.Table_Name , whereClause, get_TrxName())
																  .setParameters(new Object[]{pp.getM_ProductionPlan_ID()})
																  .setOrderBy("Line")
															  .list();

						for (X_M_ProductionLine pline : production_lines)
						{
							MLocator locator = MLocator.get(getCtx(), pline.getM_Locator_ID());
							String MovementType = MTransaction.MOVEMENTTYPE_ProductionPlus;
							BigDecimal MovementQty = pline.getMovementQty();
							if (MovementQty.signum() == 0)
								continue ;
							else if(MovementQty.signum() < 0)
							{
								BigDecimal QtyAvailable = MStorage.getQtyAvailable(
										locator.getM_Warehouse_ID(),
										locator.getM_Locator_ID(),
										pline.getM_Product_ID(),
										pline.getM_AttributeSetInstance_ID(),
										get_TrxName());

								if(mustBeStocked && QtyAvailable.add(MovementQty).signum() < 0)
								{
									raiseError("@NotEnoughStocked@: " + pline.getM_Product().getName(), "");
								}

								MovementType = MTransaction.MOVEMENTTYPE_Production_;
							}

							if (!MStorage.add(getCtx(), locator.getM_Warehouse_ID(),
								locator.getM_Locator_ID(),
								pline.getM_Product_ID(),
								pline.getM_AttributeSetInstance_ID(), 0 ,
								MovementQty,
								Env.ZERO,
								Env.ZERO,
								get_TrxName()))
							{
								raiseError("Cannot correct Inventory", "");
							}

							//Create Transaction
							MTransaction mtrx = new MTransaction (getCtx(),
									pline.getAD_Org_ID(),
								MovementType,
								locator.getM_Locator_ID(),
								pline.getM_Product_ID(),
								pline.getM_AttributeSetInstance_ID(),
								MovementQty,
								production.getMovementDate(),
								get_TrxName());
							mtrx.setM_ProductionLine_ID(pline.getM_ProductionLine_ID());
							mtrx.saveEx();

							pline.setProcessed(true);
							pline.saveEx();
						} // Production Line

				 pp.setProcessed(true);
				 pp.saveEx();
				}
		} // Production Plan

		if(!production.isCreated())
		{
			production.setIsCreated(true);
			production.saveEx();
		}
		else
		{
			 production.setProcessed(true);
			 production.saveEx();
		}

		return "@OK@";

	}


	/**
	 * Explosion the Production Plan
	 * @param pp
	 * @param product
	 * @param qty
	 * @throws Exception
	 */
	private int explosion(X_M_ProductionPlan pp , MProduct product , BigDecimal qty , int line) throws Exception
	{
		MPPProductBOM bom = MPPProductBOM.getDefault(product, get_TrxName());
		if(bom == null )
		{
			raiseError("Do not exist default BOM for this product :"
					+ product.getValue() + "-"
					+ product.getName(),"");

		}
		MPPProductBOMLine[] bom_lines = bom.getLines(new Timestamp (System.currentTimeMillis()));
		m_level += 1;
		int components = 0;
		line = line * m_level;
		for(final I_PP_Product_BOMLine bomline : bom_lines)
		{
			MProduct component = MProduct.get(getCtx(), bomline.getM_Product_ID());

			if(component.isBOM() && !Services.get(IProductBL.class).isStocked(component))
			{
				explosion(pp, component, bomline.getQtyBOM() , line);
			}
			else
			{
				line += 1;
				X_M_ProductionLine pl = new X_M_ProductionLine(getCtx(), 0 , get_TrxName());
				pl.setLine(line);
				pl.setDescription(bomline.getDescription());
				pl.setM_Product_ID(bomline.getM_Product_ID());
				pl.setM_Locator_ID(pp.getM_Locator_ID());
				pl.setM_ProductionPlan_ID(pp.getM_ProductionPlan_ID());
				pl.setMovementQty(bomline.getQtyBOM().multiply(qty).negate());
				pl.saveEx();
				components += 1;

			}

		}
		return  components;
	}

	private void raiseError(String string, String sql) throws Exception {
		String msg = string;
		ValueNamePair pp = MetasfreshLastError.retrieveError();
		if (pp != null)
			msg = pp.getName() + " - ";
		msg += sql;
		throw new AdempiereUserError (msg);
	}
} // M_Production_Run
