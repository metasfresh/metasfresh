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
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.util.Services;
import org.compiere.model.MDistributionRun;
import org.compiere.model.MDistributionRunLine;
import org.compiere.model.MDocType;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProcess;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;
import org.compiere.model.MStorage;
import org.compiere.model.MWarehouse;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.eevolution.mrp.api.IMRPDAO;

/**
 * DistributionRun Orders 
 *  @author  victor.perez@e-evolution.com
 * 			<li>FR Let use the Distribution List and Distribution Run for DO
 * 	@see 	http://sourceforge.net/tracker/index.php?func=detail&aid=2030865&group_id=176962&atid=879335
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public class DistributionRunOrders extends SvrProcess 
{
	/**	The Run to execute		*/
	
	private int 				p_M_DistributionList_ID = 0;	
	/**	Date Promised			*/
	private Timestamp			p_DatePromised = null;	
	/**	Date Promised			*/
	//private Timestamp			p_DatePromised_To = null;	
	/** Organization 			*/
	private int     p_AD_Org_ID     = 0;
	/** Is Only Test 			*/
	private String p_IsTest = "N";
	/** Warehouse 				*/
	private int 				p_M_Warehouse_ID = 0;
	/** Create Distribution Order Consolidate	*/
	private String				p_ConsolidateDocument = "N";
	/** Create Distribution Based in the DRP Demand */
	private String				p_BasedInDamnd = "N";
	
	private MDistributionRun m_run = null;
	
        
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();

			if (para[i].getParameter() == null)
				;
			else if (name.equals("AD_Org_ID"))   
				p_AD_Org_ID = ((BigDecimal)para[i].getParameter()).intValue();                     
			else if (name.equals("M_Warehouse_ID"))
				p_M_Warehouse_ID = ((BigDecimal)para[i].getParameter()).intValue();                
			else if (name.equals("M_DistributionList_ID"))
				p_M_DistributionList_ID = ((BigDecimal)para[i].getParameter()).intValue();                
			else if (name.equals("DatePromised"))
			{
				p_DatePromised = (Timestamp)para[i].getParameter();
				//p_DatePromised_To = (Timestamp)para[i].getParameter_To();
			}
			else if(name.equals("ConsolidateDocument"))
				p_ConsolidateDocument = (String)para[i].getParameter();
			else if (name.equals("IsRequiredDRP"))
				p_BasedInDamnd = (String)para[i].getParameter();
			else if (name.equals("IsTest"))
				p_IsTest = (String)para[i].getParameter();
			else
				log.log(Level.SEVERE,"prepare - Unknown Parameter: " + name);
		}            
              
	}	//	prepare


	/**
	 *  doIT - run process
	 */       
     @Override
	protected String doIt() throws Exception                
     {
    	if(p_BasedInDamnd.equals("Y"))
    	{
    		if(!generateDistributionDemand())
				throw new Exception(Msg.getMsg(getCtx(), "ProcessFailed"),CLogger.retrieveException());
    	}
    	else
    	{	
    		if(!generateDistribution())
				throw new Exception(Msg.getMsg(getCtx(), "ProcessFailed"),CLogger.retrieveException());
    	}
    	
    	 if(!executeDistribution())
				throw new Exception(Msg.getMsg(getCtx(), "ProcessFailed"),CLogger.retrieveException());

        return Msg.getMsg(getCtx(), "ProcessOK");
     } 
     
     //Create Distribution Run Line
     public boolean generateDistribution()
     {
    	m_run = new MDistributionRun(this.getCtx(), 0 , this.get_TrxName());
    	m_run.setName("Generate from DRP " + p_DatePromised);
    	//m_run.setDescription("Generate from DRP");
    	m_run.save();
    	
    	StringBuffer sql = new StringBuffer("SELECT M_Product_ID , SUM (QtyOrdered-QtyDelivered) AS TotalQty, l.M_Warehouse_ID FROM DD_OrderLine ol INNER JOIN M_Locator l ON (l.M_Locator_ID=ol.M_Locator_ID) INNER JOIN DD_Order o ON (o.DD_Order_ID=ol.DD_Order_ID) ");
    	sql.append(" WHERE o.DocStatus IN ('DR','IN') AND ol.DatePromised <= ? AND l.M_Warehouse_ID=? GROUP BY M_Product_ID");
    	
 	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
 	    try
 	    {
 	            pstmt = DB.prepareStatement (sql.toString(),get_TrxName());
 	    		pstmt.setTimestamp(1, p_DatePromised);
 	    		//pstmt.setTimestamp(2, p_DatePromised_To);
 	    		pstmt.setInt(2, p_M_Warehouse_ID);
 	    		
 	            rs = pstmt.executeQuery();
 	            int line = 10;
 	            while (rs.next())
 	            {
 	            	int M_Product_ID = rs.getInt("M_Product_ID");
 	            	BigDecimal QtyAvailable = MStorage.getQtyAvailable(p_M_Warehouse_ID,0 , M_Product_ID , 0, get_TrxName());
 	            	BigDecimal QtyOrdered = rs.getBigDecimal("TotalQty");
     	
 	            	MDistributionRunLine m_runLine = new MDistributionRunLine(getCtx(),0 ,get_TrxName());
 	            	m_runLine.setM_DistributionRun_ID(m_run.getM_DistributionRun_ID());
 	            	m_runLine.setAD_Org_ID(p_AD_Org_ID);
 	            	m_runLine.setM_DistributionList_ID(p_M_DistributionList_ID);
 	            	m_runLine.setLine(line);
 	            	m_runLine.setM_Product_ID(M_Product_ID);
 	            	m_runLine.setDescription(Msg.getMsg(getCtx(), "QtyAvailable") +" : " + QtyAvailable + " " +Msg.getMsg(getCtx(), "QtyOrdered") + " : " + QtyOrdered);
 	            	if(QtyOrdered.compareTo(QtyAvailable) > 0)
 	            	{	
 	            		QtyOrdered = QtyAvailable;
 	            	}	
 	            	m_runLine.setTotalQty(QtyOrdered);
 	            	m_runLine.save();
 	            	line += 10;
 	            }
 		}
 	    catch (Exception e)
 		{
        	log.log(Level.SEVERE,"doIt - " + sql, e);
            return false;
 		}
 		finally
 		{
 			DB.close(rs, pstmt);
 			rs = null;
 			pstmt = null;
 		}	    
    	 
    	return true; 
     }
     
     //Create Distribution Run Line
     public boolean generateDistributionDemand()
     {
    	m_run = new MDistributionRun(this.getCtx(), 0 , null);
    	m_run.setName("Generate from DRP " + p_DatePromised);
    	m_run.save();
    	
    	StringBuffer sql = new StringBuffer("SELECT M_Product_ID , SUM (TargetQty) AS MinQty, SUM (QtyOrdered-QtyDelivered) AS TotalQty FROM DD_OrderLine ol INNER JOIN M_Locator l ON (l.M_Locator_ID=ol.M_Locator_ID) INNER JOIN DD_Order o ON (o.DD_Order_ID=ol.DD_Order_ID) ");
    	sql.append(" WHERE o.DocStatus IN ('DR','IN') AND ol.DatePromised <= ? AND l.M_Warehouse_ID=? GROUP BY M_Product_ID");
 	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
 	    try
 	    {
 	            pstmt = DB.prepareStatement (sql.toString(),get_TrxName());
 	    		pstmt.setTimestamp(1, p_DatePromised);
 	    		//pstmt.setTimestamp(2, p_DatePromised_To);
 	    		pstmt.setInt(2, p_M_Warehouse_ID);
 	    		
 	            rs = pstmt.executeQuery();
 	            int line = 10;
 	            while (rs.next())
 	            {
 	            	int M_Product_ID = rs.getInt("M_Product_ID");
 	            	BigDecimal QtyAvailable = MStorage.getQtyAvailable(p_M_Warehouse_ID,0 , M_Product_ID , 0, get_TrxName());
 	            	if(QtyAvailable.signum()<= 0)
 	            		continue;
 	            	BigDecimal QtyToDistribute = rs.getBigDecimal("TotalQty");
 	            	if(QtyAvailable.compareTo(QtyToDistribute) >= 0)
 	            		QtyAvailable = QtyToDistribute;
 	            	else
 	            	{	
 	 	            	BigDecimal QtyReserved = getTargetQty(M_Product_ID);
 	            		QtyToDistribute = QtyAvailable.subtract(QtyReserved);
 	            	}	
     	
 	            	//if(QtyToDistribute.equals(Env.ZERO))
 	            	//	continue;
 	            		
 	            	MDistributionRunLine m_runLine = new MDistributionRunLine(getCtx(),0 ,get_TrxName());
 	            	m_runLine.setM_DistributionRun_ID(m_run.getM_DistributionRun_ID());
 	            	m_runLine.setAD_Org_ID(p_AD_Org_ID);
 	            	m_runLine.setM_DistributionList_ID(p_M_DistributionList_ID);
 	            	m_runLine.setLine(line);
 	            	m_runLine.setM_Product_ID(M_Product_ID);
 	            	m_runLine.setDescription(Msg.translate(getCtx(), "QtyAvailable") +" : " + QtyAvailable + " " +Msg.translate(getCtx(), "QtyOrdered") + " : " + QtyToDistribute);
 	            	//m_runLine.setMinQty(rs.getBigDecimal("MinQty"));
 	            	
 	            	
 	            	m_runLine.setTotalQty(QtyToDistribute);
 	            	m_runLine.saveEx();
 	            	line += 10;
 	            }
 		}
 	    catch (Exception e)
 		{
 	            	log.log(Level.SEVERE,"doIt - " + sql, e);
 	                return false;
 		}
 		finally
 		{
 			DB.close(rs, pstmt);
 			rs = null;
 			pstmt = null;
 		}	    
    	 
    	return true; 
     }
     /**
      * Get Qty TargetQty for a Warehouse 
      * @param M_Product_ID
      * @return
      */
     private BigDecimal getTargetQty(int M_Product_ID)
 	{
 		StringBuffer sql = new StringBuffer("SELECT SUM (TargetQty)  FROM DD_OrderLine ol INNER JOIN M_Locator l ON (l.M_Locator_ID=ol.M_Locator_ID) INNER JOIN DD_Order o ON (o.DD_Order_ID=ol.DD_Order_ID) ");
 		sql.append(" WHERE o.DocStatus IN ('DR','IN') AND ol.DatePromised <= ? AND l.M_Warehouse_ID=? AND ol.M_Product_ID=? GROUP BY M_Product_ID");
 		BigDecimal qty = DB.getSQLValueBD(get_TrxName(), sql.toString(), new Object[]{p_DatePromised, p_M_Warehouse_ID, M_Product_ID}); 		
		//	SQL may return no rows or null
		if (qty == null)
			return Env.ZERO;
		
 		return qty;
      }
     
     /**
      * Execute Distribution Run
      * @return
      * @throws Exception
      */
     public boolean executeDistribution() throws Exception
     {
    	 
    	 int	M_DocType_ID  = 0;
    	 MDocType[] doc = MDocType.getOfDocBaseType(getCtx(), MDocType.DOCBASETYPE_DistributionOrder);

 		if (doc==null || doc.length == 0) 
 		{
 			log.severe ("Not found default document type for docbasetype " + MDocType.DOCBASETYPE_DistributionOrder);
 			throw new Exception(Msg.getMsg(getCtx(), "SequenceDocNotFound"),CLogger.retrieveException());
 		}
 		else
 			M_DocType_ID  = doc[0].getC_DocType_ID();
 		
 		String trxName = Trx.createTrxName("Run Distribution to DRP");	
 		Trx trx = Trx.get(trxName, true);	//trx needs to be committed too
 		
    	//Prepare Process
		int AD_Process_ID = 271;	  
		AD_Process_ID = MProcess.getProcess_ID("M_DistributionRun Create",get_TrxName());
		
		MPInstance instance = new MPInstance(Env.getCtx(), AD_Process_ID, 0, 0);
		if (!instance.save())
		{
			throw new Exception(Msg.getMsg(getCtx(), "ProcessNoInstance"),CLogger.retrieveException());
		}
		
    	//call process
		ProcessInfo pi = new ProcessInfo ("M_DistributionRun Orders", AD_Process_ID);
		pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
		pi.setRecord_ID(m_run.getM_DistributionRun_ID());
		//	Add Parameter - Selection=Y
		MPInstancePara ip = new MPInstancePara(instance, 10);
		ip.setParameter("C_DocType_ID", M_DocType_ID );
		if (!ip.save())
		{
			String msg = "No Parameter added";  //  not translated
			throw new Exception(msg,CLogger.retrieveException()); 
		}	
		//	Add Parameter - DatePromised
		ip = new MPInstancePara(instance, 20);
		ip.setParameter("DatePromised", "");
		ip.setP_Date(p_DatePromised);
		//ip.setP_Date_To(p_DatePromised_To);
		if (!ip.save())
		{
			String msg = "No Parameter added";  //  not translated
			throw new Exception(msg,CLogger.retrieveException()); 
		}	
		//	Add Parameter - M_Warehouse_ID
		ip = new MPInstancePara(instance, 30);
		ip.setParameter("M_Warehouse_ID",p_M_Warehouse_ID);
		if (!ip.save())
		{
			String msg = "No Parameter added";  //  not translated
			throw new Exception(msg,CLogger.retrieveException()); 
		}		
		//	Add Parameter - CreateDO
		ip = new MPInstancePara(instance, 40);
		ip.setParameter("ConsolidateDocument",p_ConsolidateDocument);
		if (!ip.save())
		{
			String msg = "No Parameter added";  //  not translated
			throw new Exception(msg,CLogger.retrieveException()); 
		}		
		//	Add Parameter - IsTest=Y
		ip = new MPInstancePara(instance, 50);
		ip.setParameter("IsTest",p_IsTest);
		if (!ip.save())
		{
			String msg = "No Parameter added";  //  not translated
			throw new Exception(msg,CLogger.retrieveException()); 
		}		
		//Distribution List
		ip = new MPInstancePara(instance, 60);
		ip.setParameter("M_DistributionList_ID",p_M_DistributionList_ID);
		if (!ip.save())
		{
			String msg = "No Parameter added";  //  not translated
			throw new Exception(msg,CLogger.retrieveException()); 
		}		
		//Based in DRP Demand
		ip = new MPInstancePara(instance, 70);
		ip.setParameter("IsRequiredDRP",p_BasedInDamnd);
		if (!ip.save())
		{
			String msg = "No Parameter added";  //  not translated
			throw new Exception(msg,CLogger.retrieveException()); 
		}
		
		//	Execute Process
		MProcess worker = new MProcess(getCtx(),AD_Process_ID,get_TrxName());
		worker.processIt(pi, Trx.get(get_TrxName(), true));
		m_run.delete(true);
    	return true;
     }
     
     public String groovy(String A_TrxName, Properties A_Ctx,int P_M_Warehouse_ID, int P_M_PriceList_Version_ID, int P_M_DistributionList_ID)
     {
     
     MPriceListVersion plv = new MPriceListVersion(A_Ctx,P_M_PriceList_Version_ID,A_TrxName);	
	 MPriceList pl = new MPriceList(A_Ctx,plv.getM_PriceList_ID(),A_TrxName);
	 MWarehouse w = new MWarehouse(A_Ctx,P_M_Warehouse_ID,A_TrxName);
	 MDistributionRun dr = new MDistributionRun(A_Ctx,0,A_TrxName);
	 dr.setName(plv.getName());
	 dr.setIsActive(true);
	 dr.setAD_Org_ID(w.getAD_Org_ID());
	 dr.saveEx();
	 
	 MProductPrice[] products = plv.getProductPrice(true);
	 int seq = 10;
	 for ( MProductPrice pp : products)
	 {
		int M_Product_ID = pp.getM_Product_ID();
			BigDecimal  QtyAvailable = MStorage.getQtyAvailable(P_M_Warehouse_ID, M_Product_ID, 0 ,0, A_TrxName);
			BigDecimal  QtyOnHand = Services.get(IMRPDAO.class).getQtyOnHand(A_Ctx, P_M_Warehouse_ID, M_Product_ID, A_TrxName);
			MDistributionRunLine drl = new  MDistributionRunLine(A_Ctx,0,A_TrxName);
			drl.setM_DistributionRun_ID(dr.get_ID());
			drl.setLine(seq);
			drl.setM_Product_ID(M_Product_ID);
			drl.setM_DistributionList_ID(P_M_DistributionList_ID);
	     		drl.setDescription(Msg.translate(A_Ctx, "QtyAvailable") +" = " + QtyAvailable + " | " +Msg.translate(A_Ctx, "QtyOnHand") + " = " + QtyOnHand);
			drl.setTotalQty(QtyAvailable);
			drl.saveEx();
	 }		
	 return "";	
	
     }
 
     public String groovy1(String A_TrxName, Properties A_Ctx,int P_M_Warehouse_ID, int P_M_PriceList_Version_ID, int P_M_DistributionList_ID)
     {
 MDistributionRunLine main = new  MDistributionRunLine(A_Ctx,0,A_TrxName);
 MProduct product = MProduct.get(A_Ctx,main.getM_Product_ID());
 BigDecimal Qty= main.getTotalQty();
 int seq = main.getLine();
 int num =1;
 if(product.isBOM() && Qty.signum() > 0)
 {
	seq+= 1;
	
    MPPProductBOM bom =	MPPProductBOM.getDefault(product, A_TrxName);
    //Explotando los componentes
    for(MPPProductBOMLine line :bom.getLines())
    {
    	num +=1;
 	    int M_Product_ID = line.getM_Product_ID();
 	    BigDecimal  QtyRequiered = line.getQtyBOM().multiply(Qty);
 	    BigDecimal  QtyAvailable = MStorage.getQtyAvailable(P_M_Warehouse_ID, M_Product_ID, 0 ,0, A_TrxName);
 	    BigDecimal  QtyOnHand = Services.get(IMRPDAO.class).getQtyOnHand(A_Ctx, P_M_Warehouse_ID,M_Product_ID, A_TrxName);
 	    BigDecimal  QtyToDeliver = QtyRequiered;
 	    if(QtyRequiered.compareTo(QtyAvailable) > 0)
 	    	QtyToDeliver = QtyAvailable;
 	    MDistributionRunLine drl = new  MDistributionRunLine(A_Ctx,0,A_TrxName);
 		drl.setM_DistributionRun_ID(main.getM_DistributionRun_ID());
 		drl.setLine(seq);
 		drl.setM_Product_ID(M_Product_ID);
 		drl.setM_DistributionList_ID(main.getM_DistributionList_ID());
      		drl.setDescription(Msg.translate(A_Ctx, "QtyRequiered") +" = " + QtyRequiered.intValue()+ " | "
      						  +Msg.translate(A_Ctx, "QtyAvailable") +" = " + QtyAvailable + " | " 
      						  +Msg.translate(A_Ctx, "QtyOnHand") + " = " + QtyOnHand);
 		drl.setTotalQty(QtyToDeliver);
 		drl.saveEx();
    }
 }
 main.setIsActive(false);
return "Componentes del Juego:" + num ;
     }
}
