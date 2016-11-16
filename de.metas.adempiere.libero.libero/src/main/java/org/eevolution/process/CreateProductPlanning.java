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

package org.eevolution.process;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.adempiere.exceptions.DBException;
import org.compiere.model.MWarehouse;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.MPPProductPlanning;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 * CreateProductPlanning
 * 
 * @author Victor Perez, e-Evolution, S.C.
 * @version $Id: CreateProductPlanning.java,v 1.1 2004/06/22 05:24:03 vpj-cd Exp $
 * 
 * @author Teo Sarca, http://www.arhipac.ro
 */
public class CreateProductPlanning extends SvrProcess
{
	/** Process Parameters */;
	private int             p_M_Product_Category_ID = 0;
	private int             p_M_Warehouse_ID = 0;
	private int             p_S_Resource_ID = 0 ;
	private int             p_Planner = 0 ; 
	private BigDecimal      p_DeliveryTime_Promised = Env.ZERO;
	private int             p_DD_NetworkDistribution_ID = 0;
	private int             p_AD_Workflow_ID = 0;
	private BigDecimal      p_TimeFence = Env.ZERO;
	private boolean         p_CreatePlan = false;
	private boolean         p_MPS = false;
	private String          p_OrderPolicy = "";
	private BigDecimal      p_OrderPeriod = Env.ZERO;
	private BigDecimal      p_TransferTime = Env.ZERO;
	private BigDecimal      p_SafetyStock = Env.ZERO;
	private BigDecimal      p_Order_Min = Env.ZERO;
	private BigDecimal      p_Order_Max = Env.ZERO;
	private BigDecimal      p_Order_Pack = Env.ZERO;
	private BigDecimal      p_Order_Qty = Env.ZERO;
	private BigDecimal      p_WorkingTime = Env.ZERO;
	private int             p_Yield = 0;
	private int 			m_AD_Org_ID = 0;	
	private int 			m_AD_Client_ID = 0;

	// Statistics 
	private int count_created = 0;
	private int count_updated = 0;
	private int count_error = 0;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals("M_Product_Category_ID"))
			{    
				p_M_Product_Category_ID = para.getParameterAsInt();
			}
			else if (name.equals(MPPProductPlanning.COLUMNNAME_M_Warehouse_ID))
			{    
				p_M_Warehouse_ID = para.getParameterAsInt();
			}
			else if (name.equals(MPPProductPlanning.COLUMNNAME_S_Resource_ID))
			{    
				p_S_Resource_ID = para.getParameterAsInt();
			}
			else if (name.equals(MPPProductPlanning.COLUMNNAME_IsCreatePlan))
			{    
				p_CreatePlan = "Y".equals((String)para.getParameter());
			}
			else if (name.equals(MPPProductPlanning.COLUMNNAME_IsMPS))
			{    
				p_MPS = "Y".equals((String)para.getParameter());
			}
			else if (name.equals(MPPProductPlanning.COLUMNNAME_DD_NetworkDistribution_ID))
			{    
				p_DD_NetworkDistribution_ID =  para.getParameterAsInt();                                
			} 		
			else if (name.equals(MPPProductPlanning.COLUMNNAME_AD_Workflow_ID))
			{    
				p_AD_Workflow_ID =  para.getParameterAsInt();
			}		
			else if (name.equals(MPPProductPlanning.COLUMNNAME_TimeFence))
			{    
				p_TimeFence =  ((BigDecimal)para.getParameter());  
			} 
			else if (name.equals(MPPProductPlanning.COLUMNNAME_TransfertTime))
			{    
				p_TransferTime =  ((BigDecimal)para.getParameter());  
			} 
			else if (name.equals(MPPProductPlanning.COLUMNNAME_SafetyStock))
			{    
				p_SafetyStock =  ((BigDecimal)para.getParameter());  
			}
			else if (name.equals(MPPProductPlanning.COLUMNNAME_Order_Min))
			{    
				p_Order_Min =  ((BigDecimal)para.getParameter());  
			}
			else if (name.equals(MPPProductPlanning.COLUMNNAME_Order_Max))
			{    
				p_Order_Max =  ((BigDecimal)para.getParameter());  
			} 
			else if (name.equals(MPPProductPlanning.COLUMNNAME_Order_Pack))
			{    
				p_Order_Pack =  ((BigDecimal)para.getParameter());  
			} 
			else if (name.equals(MPPProductPlanning.COLUMNNAME_Order_Qty))
			{    
				p_Order_Qty =  ((BigDecimal)para.getParameter());  
			} 
			else if (name.equals(MPPProductPlanning.COLUMNNAME_WorkingTime))
			{    
				p_WorkingTime =  ((BigDecimal)para.getParameter());  
			} 
			else if (name.equals(MPPProductPlanning.COLUMNNAME_Yield))
			{    
				p_Yield =  ((BigDecimal)para.getParameter()).intValue();  
			} 
			else if (name.equals(MPPProductPlanning.COLUMNNAME_DeliveryTime_Promised))
			{    
				p_DeliveryTime_Promised =  ((BigDecimal)para.getParameter());  
			}           
			else if (name.equals(MPPProductPlanning.COLUMNNAME_Order_Period))
			{    
				p_OrderPeriod =  ((BigDecimal)para.getParameter());  
			} 
			else if (name.equals(MPPProductPlanning.COLUMNNAME_Order_Policy))
			{    
				p_OrderPolicy =  ((String)para.getParameter()); 
			} 
			else if (name.equals(MPPProductPlanning.COLUMNNAME_Planner_ID))
			{    
				p_Planner =   para.getParameterAsInt(); 
			}                        
			else
			{
				log.error("prepare - Unknown Parameter: " + name);
			}
		}
		
		m_AD_Client_ID = Env.getAD_Client_ID(getCtx());
		if(p_M_Warehouse_ID > 0)
		{
			MWarehouse w = MWarehouse.get(getCtx(), p_M_Warehouse_ID);
			m_AD_Org_ID = w.getAD_Org_ID();
		}
	}	// prepare


	/***************************************************************************
	 * Create Data Planning record
	 */
	@Override
	protected String doIt() throws Exception                
	{
		ArrayList<Object> params = new ArrayList<Object>();
		String sql = "SELECT p.M_Product_ID FROM M_Product p WHERE p.AD_Client_ID=?";
		params.add(m_AD_Client_ID);
		
		if (p_M_Product_Category_ID > 0 )
		{
			sql += " AND p.M_Product_Category_ID=?";
			params.add(p_M_Product_Category_ID);
		}
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql,get_TrxName());
			DB.setParameters(pstmt, params);
			rs = pstmt.executeQuery ();
			while (rs.next())
			{                                                   
				int M_Product_ID = rs.getInt(1);
				createPlanning(M_Product_ID);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return "@Created@ #"+count_created
					+" @Updated@ #"+count_updated
					+" @Error@ #"+count_error;
	}
	
	private void createPlanning(int M_Product_ID)
	{
		MPPProductPlanning pp = MPPProductPlanning.get(getCtx(),m_AD_Client_ID , m_AD_Org_ID , p_M_Warehouse_ID, p_S_Resource_ID,M_Product_ID, get_TrxName());
		boolean isNew = pp == null;
		// Create Product Data Planning
		if (pp == null)
		{
			pp = new MPPProductPlanning(getCtx(), 0, get_TrxName());                  
			pp.setAD_Org_ID(m_AD_Org_ID);
			pp.setM_Warehouse_ID(p_M_Warehouse_ID);
			pp.setS_Resource_ID(p_S_Resource_ID);
			pp.setM_Product_ID(M_Product_ID);
		}
		pp.setDD_NetworkDistribution_ID (p_DD_NetworkDistribution_ID);		
		pp.setAD_Workflow_ID(p_AD_Workflow_ID);
		pp.setIsCreatePlan(p_CreatePlan);                                                                         
		pp.setIsMPS(p_MPS);                                    
		pp.setIsRequiredMRP(true);
		pp.setIsRequiredDRP(true);
		pp.setDeliveryTime_Promised(p_DeliveryTime_Promised);
		pp.setOrder_Period(p_OrderPeriod); 
		pp.setPlanner_ID(p_Planner);
		pp.setOrder_Policy(p_OrderPolicy);
		pp.setSafetyStock(p_SafetyStock);
		pp.setOrder_Qty(p_Order_Qty);
		pp.setOrder_Min(p_Order_Min);
		pp.setOrder_Max(p_Order_Max);
		pp.setOrder_Pack(p_Order_Pack);
		pp.setTimeFence(p_TimeFence);
		pp.setTransfertTime(p_TransferTime);
		pp.setIsPhantom(false);
		pp.setWorkingTime(p_WorkingTime);
		pp.setYield(p_Yield);                                                                                        
		//
		if (!pp.save())
			count_error++;
		if (isNew)
			count_created++;
		else
			count_updated++;
	}
}
