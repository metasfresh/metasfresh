/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
 * created by Victor Perez are Copyright (C) e-Evolution,SC. All Rights Reserved.
 * Contributor(s): Victor Perez
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
import java.util.ArrayList;
import java.util.Properties;

import org.compiere.model.MCashLine;
import org.compiere.model.MPayment;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;



/**
 *  Fix Payment Cash Line
 *  [ 1866214 ] Adempiere need can void a Cash Journal
 *  https://sourceforge.net/tracker/?func=detail&atid=879335&aid=1866214&group_id=176962
 *
 *        @author Sergio Ramazzina,Victor Perez
 *        @version $Id: FixPaymentCashLine.java,v 1.2 2005/04/19 12:54:30 srama Exp $
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public class FixPaymentCashLine extends SvrProcess {
	
	
    private static final Properties ctx = Env.getCtx();
	private static Logger		s_log = LogManager.getLogger(FixPaymentCashLine.class);
    //private static final String AD_Client_ID = ctx.getProperty("#AD_Client_ID");
    //private static final String AD_Org_ID = ctx.getProperty("#AD_Org_ID");


    /**
     *  Prepare - e.g., get Parameters.
     */
    protected void prepare() 
    {
    	ProcessInfoParameter[] para = getParametersAsArray();
    	
    } //        prepare

    /**
     *  Perform process.
     *  @return Message (clear text)
     *  @throws Exception if not successful
     */
    protected String doIt() throws Exception {

    	String sql = "SELECT cl.C_CashLine_ID, c.Name FROM C_CashLine cl INNER JOIN C_Cash c ON (c.C_Cash_ID=cl.C_Cash_ID) WHERE cl.CashType='T'";
		
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				 Trx trx = Trx.get(Trx.createTrxName(), true);
				 MCashLine cashline = new MCashLine(Env.getCtx(),rs.getInt(1),trx.getTrxName());
				 Integer c_cashline_id = cashline.getC_CashLine_ID();
				 MPayment[] payments = getOfCash(Env.getCtx(), rs.getString(2),
						cashline.getAmount(), cashline.getC_BP_BankAccount_ID(), cashline.getAD_Client_ID(), 
						trx.getTrxName());
				 for(MPayment payment : payments)
				 {
					 
					 cashline.setC_Payment_ID(payment.getC_Payment_ID());
					 
					 if(!cashline.save())
						 throw new IllegalStateException("Cannot assign payment to Cash Line");

					 break; // only the first
				 }
				 trx.commit();
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}

        return "@ProcessOK@";
    } //        doIt
    
	/**
	 * 	Get Payment of Cash
	 *	@param ctx context
	 *	@param cashName Cash Name
	 *	@return payments of cash
	 *	@param trxName transaction
	 */
	public static MPayment[] getOfCash (Properties ctx, 
			String cashName, 
			BigDecimal amt, 
			int C_BP_BankAccount_ID, 
			int AD_Client_ID, 
			String trxName)
	{
		String sql = "SELECT * FROM C_Payment p "
				+ "WHERE p.DocumentNo=? AND R_PnRef=? AND PayAmt=? AND C_BP_BankAccount_ID=? AND AD_Client_ID=? " +
				" AND TrxType='X' AND TenderType='X'";
			
		ArrayList<MPayment> list = new ArrayList<MPayment>();
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setString(1, cashName);
			pstmt.setString(2, cashName);
			pstmt.setBigDecimal(3, amt.negate());
			pstmt.setInt(4, C_BP_BankAccount_ID);
			pstmt.setInt(5, AD_Client_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add (new MPayment(ctx, rs, trxName));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		MPayment[] retValue = new MPayment[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getOfPayment
    
	/**************************************************************************
	 * 	Test
	 * 	@param args ignored
	 */
	public static void main(String[] args)
	{
		org.compiere.Adempiere.startup(true);
		Env.setContext(Env.getCtx(), "#AD_Client_ID", 11);
		FixPaymentCashLine pcf = new FixPaymentCashLine();
		try
		{
			pcf.doIt();
		}
		catch (Exception e)
		{
			System.out.println("Error" + e.getMessage());
		}
		
	}	//	main
        
}
