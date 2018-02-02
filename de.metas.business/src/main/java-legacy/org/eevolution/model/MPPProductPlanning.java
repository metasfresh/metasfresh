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
 *                 Teo Sarca, www.arhipac.ro                                  *
 *****************************************************************************/
package org.eevolution.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MOrgInfo;
import org.compiere.model.MResource;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.wf.MWorkflow;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Product Data Planning 
 *	
 * @author Victor Perez www.e-evolution.com     
 * @author Teo Sarca, www.arhipac.ro
 */
public class MPPProductPlanning extends X_PP_Product_Planning
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3061309620804116277L;
	
	/** Log									*/
	private static Logger log = LogManager.getLogger(MPPProductPlanning.class); 


	/**************************************************************************
	 * 	Default Constructor
	 *	@param ctx context
	 *	@param pp_product_planning_id id
	 *	@param trxName
	 *  @return MPPProductPlanning Data Product Planning 
	 */
	public MPPProductPlanning(Properties ctx, int pp_product_planning_id, String trxname)
	{
		super(ctx, pp_product_planning_id, trxname);
		if (pp_product_planning_id == 0)
		{    
		}
	}	//	MPPProductPlanning

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName Transaction Name
	 *	@return MPPProductPlanning Data Product Planning 
	 */
	public MPPProductPlanning(Properties ctx, ResultSet rs, String trxname)
	{
		super(ctx, rs,trxname);
	}

	/**
	 * Get Data Product Planning to Organization
	 * @param ctx Context
	 * @param ad_org_id Organization ID
	 * @param m_product_id Product ID
	 * @param trxName Transaction Name 
	 * @return MPPProductPlanning
	 */    
	public static MPPProductPlanning get(Properties ctx, int ad_client_id, int ad_org_id,
											int m_product_id,
											String trxname)               
	{
		int M_Warehouse_ID = MOrgInfo.get(ctx, ad_org_id).getM_Warehouse_ID();
		if(M_Warehouse_ID <= 0)
		{
			return null;
		}

		int S_Resource_ID = getPlantForWarehouse(M_Warehouse_ID); 
		if (S_Resource_ID <= 0)
			return null;

		return get(ctx, ad_client_id,ad_org_id, M_Warehouse_ID, S_Resource_ID, m_product_id, trxname);
	}

	/**
	 * Get Data Product Planning 
	 * @param ctx Context
	 * @param AD_Client_ID ID Organization
	 * @param AD_Org_ID ID Organization
	 * @param M_Warehouse_ID Warehouse
	 * @param S_Resource_ID Resource type Plant
	 * @param M_Product_ID ID Product
	 * @param trxname Trx Name
	 * @return MPPProductPlanning
	 */     
	public static MPPProductPlanning get(Properties ctx, int ad_client_id, int ad_org_id,
											int m_warehouse_id, int s_resource_id, int m_product_id,
											String trxname)
	{
		log.info("AD_Client_ID="  + ad_client_id + " AD_Org_ID=" + ad_org_id + " M_Product_ID=" + m_product_id + " M_Warehouse_ID=" + m_warehouse_id + " S_Resource_ID=" + s_resource_id );
		String  sql_warehouse = COLUMNNAME_M_Warehouse_ID+"=?";
		if(m_warehouse_id == 0)
		{
			sql_warehouse += " OR "+COLUMNNAME_M_Warehouse_ID+" IS NULL";
		}

		String whereClause =
			" AD_Client_ID=? AND AD_Org_ID=?"
			+" AND "+COLUMNNAME_M_Product_ID+"=?"
			+" AND ("+sql_warehouse+")"
			+" AND "+COLUMNNAME_S_Resource_ID+"=?";

		return new Query(ctx, MPPProductPlanning.Table_Name, whereClause, trxname)
			.setParameters(new Object[]{ad_client_id, ad_org_id, m_product_id, m_warehouse_id, s_resource_id})
			.firstOnly();
	}       


	/**
	 * Find data planning, try find the specific planning data
	 * if do not found then try find data planning general 
	 * @param ctx Context
	 * @param AD_Org_ID Organization ID
	 * @param M_Warehouse_ID Resource ID
	 * @param S_Resource_ID Resource ID
	 * @param M_Product_ID Product ID
	 * @param trxName Transaction Name
	 * @return MPPProductPlanning Planning Data
	 * 
	 * @deprecated Please use IProductPlanningDAO#find
	 */
	@Deprecated
	public static MPPProductPlanning find (Properties ctx, int AD_Org_ID,
											int M_Warehouse_ID, int S_Resource_ID, int M_Product_ID,
											String trxName)
	{
		final String whereClause = "AD_Client_ID=? AND M_Product_ID=?"
								+ " AND (AD_Org_ID IN (0,?) OR AD_Org_ID IS NULL)"
								+ " AND (M_Warehouse_ID IN (0,?) OR M_Warehouse_ID IS NULL)"
								+ " AND (S_Resource_ID IN (0,?) OR S_Resource_ID IS NULL)";
		return new Query(ctx, Table_Name, whereClause, trxName)
				.setParameters(new Object[]{Env.getAD_Client_ID(ctx), M_Product_ID, AD_Org_ID, M_Warehouse_ID, S_Resource_ID})
				.setOrderBy("COALESCE(AD_Org_ID, 0) DESC"
								+", COALESCE(M_Warehouse_ID, 0) DESC"
								+", COALESCE(S_Resource_ID, 0) DESC")
				.first();
	}

	/**
	 * Get plant resource for warehouse. If more than one resource is found, first will be used.
	 * @param M_Warehouse_ID
	 * @return Plant_ID (S_Resource_ID)
	 */
	@Deprecated
	public static int getPlantForWarehouse(int M_Warehouse_ID)
	{
		final String sql = "SELECT MIN("+MResource.COLUMNNAME_S_Resource_ID+")"
							+" FROM "+MResource.Table_Name
							+" WHERE "+MResource.COLUMNNAME_IsManufacturingResource+"=?"
							+" AND "+MResource.COLUMNNAME_ManufacturingResourceType+"=?"
							+" AND "+MResource.COLUMNNAME_M_Warehouse_ID+"=?"; 
		int plant_id = DB.getSQLValueEx(null, sql, true, MResource.MANUFACTURINGRESOURCETYPE_Plant, M_Warehouse_ID);
		return plant_id;
	}
	
	
	
	@Override
	public MPPProductBOM getPP_Product_BOM()
	{
		return MPPProductBOM.get(getCtx(), getPP_Product_BOM_ID());
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
	
	/**
	 * Set Supplier
	 * @param C_BPartner_ID
	 */
	@Override
	public void setC_BPartner_ID(int C_BPartner_ID)
	{
		this.m_C_BPartner_ID = C_BPartner_ID;
	}
	
	/**
	 * @return Supplier
	 */
	@Override
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
		log.info("         TransfertTime: " + getTransfertTime ());
		log.info("             Warehouse: " + getM_Warehouse_ID());
		log.info("               Planner: " + getPlanner_ID());
		log.info("              Supplier: " + getC_BPartner_ID());
	}
}	//	Product Data Planning
