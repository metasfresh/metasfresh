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
 * Teo Sarca, www.arhipac.ro *
 *****************************************************************************/
package org.eevolution.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MResource;
import org.compiere.wf.MWorkflow;
import org.eevolution.api.IProductBOMDAO;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.Services;

/**
 * Product Data Planning
 *
 * @author Victor Perez www.e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 */
@SuppressWarnings("serial")
public class MPPProductPlanning extends X_PP_Product_Planning
{
	private static Logger log = LogManager.getLogger(MPPProductPlanning.class);

	public MPPProductPlanning(Properties ctx, int pp_product_planning_id, String trxname)
	{
		super(ctx, pp_product_planning_id, trxname);
	}

	/**
	 * Load Constructor
	 * 
	 * @param ctx context
	 * @param rs result set
	 * @param trxName Transaction Name
	 * @return MPPProductPlanning Data Product Planning
	 */
	public MPPProductPlanning(Properties ctx, ResultSet rs, String trxname)
	{
		super(ctx, rs, trxname);
	}

	@Override
	public I_PP_Product_BOM getPP_Product_BOM()
	{
		return Services.get(IProductBOMDAO.class).getById(getPP_Product_BOM_ID());
	}

	@Override
	public MWorkflow getAD_Workflow()
	{
		return MWorkflow.get(getCtx(), getAD_Workflow_ID());
	}

	@Override
	public MResource getS_Resource()
	{
		return MResource.get(getCtx(), getS_Resource_ID());
	}

	private int m_C_BPartner_ID = 0;

	@Override
	@Deprecated
	public void setC_BPartner_ID(int C_BPartner_ID)
	{
		this.m_C_BPartner_ID = C_BPartner_ID;
	}

	@Override
	@Deprecated
	public int getC_BPartner_ID()
	{
		return this.m_C_BPartner_ID;
	}

	@Override
	public void dump()
	{
		if (!log.isInfoEnabled())
			return;
		log.info("------------ Planning Data --------------");
		log.info("           Create Plan: " + isCreatePlan());
		log.info("              Resource: " + getS_Resource_ID());
		log.info("          M_Product_ID: " + getM_Product_ID());
		log.info("                   BOM: " + getPP_Product_BOM_ID());
		log.info("              Workflow: " + getAD_Workflow_ID());
		log.info("  Network Distribution: " + getDD_NetworkDistribution_ID());
		log.info("Delivery Time Promised: " + getDeliveryTime_Promised());
		log.info("         TransfertTime: " + getTransfertTime());
		log.info("             Warehouse: " + getM_Warehouse_ID());
		log.info("               Planner: " + getPlanner_ID());
		log.info("              Supplier: " + getC_BPartner_ID());
	}
}
