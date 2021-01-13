/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
package de.metas.project.process.legacy;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_ProjectLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProductPO;
import org.compiere.model.MProject;
import org.compiere.model.MProjectLine;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.util.Services;

/**
 *  Generate Purchase Order from Project.
 *
 *	@author Jorg Janke
 *	@version $Id: ProjectGenPO.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
@Deprecated
public class ProjectGenPO extends JavaProcess
{
	/** Project Parameter			*/
	private int 		m_C_Project_ID = 0;
	/** Opt Project Line Parameter	*/
	private int 		m_C_ProjectLine_ID = 0;
	/** Consolidate Document		*/
	private boolean		m_ConsolidateDocument = true;
	/** List of POs for Consolidation	*/
	private ArrayList<MOrder>	m_pos = new ArrayList<>();

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (ProcessInfoParameter element : para)
		{
			String name = element.getParameterName();
			if (element.getParameter() == null)
			{
				
			}
			else if (name.equals("C_Project_ID"))
			{
				m_C_Project_ID = ((BigDecimal)element.getParameter()).intValue();
			}
			else if (name.equals("C_ProjectLine_ID"))
			{
				m_C_ProjectLine_ID = ((BigDecimal)element.getParameter()).intValue();
			}
			else if (name.equals("ConsolidateDocument"))
			{
				m_ConsolidateDocument = "Y".equals(element.getParameter());
			}
			else
			{
				log.error("prepare - Unknown Parameter: " + name);
			}
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message 
	 *  @throws Exception if not successful
	 */
	@Override
	protected String doIt() throws Exception
	{
		log.info("doIt - C_Project_ID=" + m_C_Project_ID + " - C_ProjectLine_ID=" + m_C_ProjectLine_ID + " - Consolidate=" + m_ConsolidateDocument);
		if (m_C_ProjectLine_ID != 0)
		{
			MProjectLine projectLine = new MProjectLine(getCtx(), m_C_ProjectLine_ID, get_TrxName());
			MProject project = new MProject (getCtx(), projectLine.getC_Project_ID(), get_TrxName());
			createPO (project, projectLine);
		}
		else
		{
			MProject project = new MProject (getCtx(), m_C_Project_ID, get_TrxName());
			for (I_C_ProjectLine line : project.getLines())
			{
				createPO (project, line);
			}
		}
		return "";
	}	//	doIt

	/**
	 * 	Create PO from Planned Amt/Qty
	 * 	@param projectLine project line
	 */
	private void createPO (MProject project, I_C_ProjectLine projectLine)
	{
		if (projectLine.getM_Product_ID() == 0)
		{
			addLog (projectLine.getLine() ,null,null, "Line has no Product");
			return;
		}
		if (projectLine.getC_OrderPO_ID() != 0)
		{
			addLog (projectLine.getLine() ,null,null, "Line was ordered previously");
			return;
		}

		//	PO Record
		MProductPO[] pos = MProductPO.getOfProduct(getCtx(), projectLine.getM_Product_ID(), get_TrxName());
		if (pos == null || pos.length == 0)
		{
			addLog (projectLine.getLine() ,null,null, "Product has no PO record");
			return;
		}

		//	Create to Order
		MOrder order = null;
		//	try to find PO to C_BPartner
		for (int i = 0; i < m_pos.size(); i++)
		{
			MOrder test = m_pos.get(i);
			if (test.getC_BPartner_ID() == pos[0].getC_BPartner_ID())
			{
				order = test;
				break;
			}
		}
		if (order == null)	//	create new Order
		{
			//	Vendor
			I_C_BPartner bp = Services.get(IBPartnerDAO.class).getById(pos[0].getC_BPartner_ID());
			
			//	New Order
			order = new MOrder (project, false, null);
			int AD_Org_ID = projectLine.getAD_Org_ID();
			if (AD_Org_ID == 0)
			{
				log.warn("createPOfromProjectLine - AD_Org_ID=0");
				AD_Org_ID = Env.getAD_Org_ID(getCtx());	
				if (AD_Org_ID != 0)
				{
					projectLine.setAD_Org_ID(AD_Org_ID);
				}
			}
			order.setClientOrg (projectLine.getAD_Client_ID (), AD_Org_ID);
			order.setBPartner (bp);
			order.save ();
			//	optionally save for consolidation
			if (m_ConsolidateDocument)
			{
				m_pos.add(order);
			}
		}

		//	Create Line
		MOrderLine orderLine = new MOrderLine (order);
		orderLine.setM_Product_ID(projectLine.getM_Product_ID(), true);
		orderLine.setQty(projectLine.getPlannedQty());
		orderLine.setDescription(projectLine.getDescription());

		//	(Vendor) PriceList Price
		orderLine.setPrice();
		if (orderLine.getPriceActual().signum() == 0)
		{
			//	Try to find purchase price
			BigDecimal poPrice = pos[0].getPricePO();
			int C_Currency_ID = pos[0].getC_Currency_ID();
			//
			if (poPrice == null || poPrice.signum() == 0)
			{
				poPrice = pos[0].getPriceLastPO();
			}
			if (poPrice == null || poPrice.signum() == 0)
			{
				poPrice = pos[0].getPriceList();
			}
			//	We have a price
			if (poPrice != null && poPrice.signum() != 0)
			{
				if (order.getC_Currency_ID() != C_Currency_ID)
				{
					poPrice = Services.get(ICurrencyBL.class).convert(
							poPrice,
							CurrencyId.ofRepoId(C_Currency_ID), 
							CurrencyId.ofRepoId(order.getC_Currency_ID()),
							TimeUtil.asLocalDate(order.getDateAcct()), 
							CurrencyConversionTypeId.ofRepoIdOrNull(order.getC_ConversionType_ID()),
							ClientId.ofRepoId(order.getAD_Client_ID()), 
							OrgId.ofRepoId(order.getAD_Org_ID()));
				}
				orderLine.setPrice(poPrice);
			}
		}
		
		orderLine.setTax();
		orderLine.save();

		//	update ProjectLine
		projectLine.setC_OrderPO_ID(order.getC_Order_ID());
		InterfaceWrapperHelper.save(projectLine);
		addLog (projectLine.getLine(), null, projectLine.getPlannedQty(), order.getDocumentNo());
	}	//	createPOfromProjectLine

}	//	ProjectGenPO
