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
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.Adempiere;
import org.compiere.util.Env;

/**
 *	Test Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MTest.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 */
public class MTest extends X_Test
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5750690186875693958L;


	/**
	 * 	Constructor
	 *	@param ctx context
	 *	@param Test_ID
	 *	@param trxName transaction
	 */
	public MTest(Properties ctx, int Test_ID, String trxName)
	{
		super (ctx, Test_ID, trxName);
	}	//	MTest

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MTest(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MTest

	/**
	 * 	Test Object Constructor
	 *	@param ctx context
	 *	@param testString test string
	 *	@param testNo test no
	 */
	public MTest (Properties ctx, String testString, int testNo)
	{
		this(ctx, testString, testNo, null);
	}
	public MTest (Properties ctx, String testString, int testNo, String trxName)
	{
		super(ctx, 0, trxName);
		testString = testString + "_" + testNo;
		setName(testString);
		setDescription(testString + " " + testString + " " + testString);
		setHelp (getDescription() + " - " + getDescription());
		setT_Date(new Timestamp (System.currentTimeMillis()));
		setT_DateTime(new Timestamp (System.currentTimeMillis()));
		setT_Integer(testNo);
		setT_Amount(new BigDecimal(testNo));
		setT_Number(Env.ONE.divide(new BigDecimal(testNo), BigDecimal.ROUND_HALF_UP));
		//
		setC_Currency_ID(100);		//	USD
		setC_Location_ID(109);		//	Monroe
		setC_UOM_ID(100);			//	Each
	//	setC_BPartner_ID(C_BPartner_ID);
	//	setC_Payment_ID(C_Payment_ID);
	//	setM_Locator_ID(M_Locator_ID);
	//	setM_Product_ID(M_Product_ID);
	}	//	MTest

	
	/**
	 * 	Before Delete
	 *	@return true if it can be deleted
	 */
	protected boolean beforeDelete ()
	{
		log.info("***");
		return true;
	}
	
	/**
	 * 	After Delete
	 *	@param success
	 *	@return success
	 */
	protected boolean afterDelete (boolean success)
	{
		log.info("*** Success=" + success);
		return success;
	}
	
	/**
	 * 	Before Save
	 *	@param newRecord
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		log.info("New=" + newRecord + " ***");
		return true;
	}
	
	/**
	 * 	After Save
	 *	@param newRecord
	 *	@param success
	 *	@return success
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		log.info("New=" + newRecord + ", Seccess=" + success + " ***");
		return success;
	}	//	afterSave

	
	/*************************************************************************
	 * 	Test
	 *	@param args
	 */
	public static void main(String[] args)
	{
		Adempiere.startup(true);
		Properties ctx = Env.getCtx();
		
		/** Test CLOB	*/
		MTest t1 = new MTest (ctx, 0, null);
		t1.setName("Test1");
		System.out.println("->" + t1.getCharacterData() + "<-");
		t1.save();
		t1.setCharacterData("Long Text JJ");
		t1.save();
		int Test_ID = t1.getTest_ID();
		//
		MTest t2 = new MTest (Env.getCtx(), Test_ID, null);
		System.out.println("->" + t2.getCharacterData() + "<-");
		
		t2.delete(true);
		
		
		/**	Volume Test 
		for (int i = 1; i < 20000; i++)
		{
			new MTest (ctx, "test", i).save();
		}		
		/** */	
	}	//	main
	
}	//	MTest
