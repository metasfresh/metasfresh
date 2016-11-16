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
package org.compiere.process;

import java.util.ArrayList;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAcctSchemaDefault;
import org.compiere.model.MAcctSchemaElement;
import org.compiere.model.MAcctSchemaGL;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.KeyNamePair;

/**
 * 	Copy Accounts from one Acct Schema to another
 *	
 *  @author Jorg Janke
 *  @version $Id: AcctSchemaCopyAcct.java,v 1.3 2006/07/30 00:51:01 jjanke Exp $
 */
public class AcctSchemaCopyAcct extends SvrProcess
{
	private int		p_SourceAcctSchema_ID = 0;
	private int		p_TargetAcctSchema_ID = 0;
	
	/**
	 * 	Prepare
	 */
	@Override
	protected void prepare ()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_AcctSchema_ID"))
				p_SourceAcctSchema_ID = para[i].getParameterAsInt();
			else
				log.error("Unknown Parameter: " + name);
		}
		p_TargetAcctSchema_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	Process
	 *	@return info
	 *	@throws Exception
	 */
	@Override
	protected String doIt () throws Exception
	{
		log.info("SourceAcctSchema_ID=" + p_SourceAcctSchema_ID 
			+ ", TargetAcctSchema_ID=" + p_TargetAcctSchema_ID);
		
		if (p_SourceAcctSchema_ID == 0 || p_TargetAcctSchema_ID == 0)
			throw new AdempiereSystemError("ID=0");
		
		if (p_SourceAcctSchema_ID == p_TargetAcctSchema_ID)
			throw new AdempiereUserError("Must be different");
		
		MAcctSchema source = new MAcctSchema(getCtx(), p_SourceAcctSchema_ID, ITrx.TRXNAME_None);
		if (source.get_ID() == 0)
			throw new AdempiereSystemError("NotFound Source C_AcctSchema_ID=" + p_SourceAcctSchema_ID);
		MAcctSchema target = new MAcctSchema (getCtx(), p_TargetAcctSchema_ID, get_TrxName());
		if (target.get_ID() == 0)
			throw new AdempiereSystemError("NotFound Target C_AcctSchema_ID=" + p_TargetAcctSchema_ID);
		
		//
		MAcctSchemaElement[] targetElements = target.getAcctSchemaElements();
		if (targetElements.length == 0)
			throw new AdempiereUserError("NotFound Target C_AcctSchema_Element");
		
		//	Accounting Element must be the same
		MAcctSchemaElement sourceAcctElement = source.getAcctSchemaElement(MAcctSchemaElement.ELEMENTTYPE_Account);
		if (sourceAcctElement == null)
			throw new AdempiereUserError("NotFound Source AC C_AcctSchema_Element");
		MAcctSchemaElement targetAcctElement = target.getAcctSchemaElement(MAcctSchemaElement.ELEMENTTYPE_Account);
		if (targetAcctElement == null)
			throw new AdempiereUserError("NotFound Target AC C_AcctSchema_Element");
		if (sourceAcctElement.getC_Element_ID() != targetAcctElement.getC_Element_ID())
			throw new AdempiereUserError("@C_Element_ID@ different");
		
		if (MAcctSchemaGL.get(getCtx(), p_TargetAcctSchema_ID) == null)
			copyGL(target);
		if (MAcctSchemaDefault.get(getCtx(), p_TargetAcctSchema_ID) == null)
			copyDefault(target);
		
		return "@OK@";
	}	//	doIt
	
	/**
	 * 	Copy GL 
	 *	@param targetAS target
	 *	@throws Exception
	 */
	private void copyGL (MAcctSchema targetAS) throws Exception
	{
		MAcctSchemaGL source = MAcctSchemaGL.get(getCtx(), p_SourceAcctSchema_ID);
		MAcctSchemaGL target = new MAcctSchemaGL(getCtx(), 0, get_TrxName());
		target.setC_AcctSchema_ID(p_TargetAcctSchema_ID);
		ArrayList<KeyNamePair> list = source.getAcctInfo();
		for (int i = 0; i < list.size(); i++)
		{
			KeyNamePair pp = list.get(i);
			int sourceC_ValidCombination_ID = pp.getKey();
			String columnName = pp.getName();
			MAccount sourceAccount = MAccount.get(getCtx(), sourceC_ValidCombination_ID);
			MAccount targetAccount = createAccount(targetAS, sourceAccount);
			target.setValue(columnName, new Integer(targetAccount.getC_ValidCombination_ID()));
		}
		if (!target.save())
			throw new AdempiereSystemError("Could not Save GL");
	}	//	copyGL
	
	/**
	 * 	Copy Default
	 *	@param targetAS target
	 *	@throws Exception
	 */
	private void copyDefault(MAcctSchema targetAS) throws Exception
	{
		MAcctSchemaDefault source = MAcctSchemaDefault.get(getCtx(), p_SourceAcctSchema_ID);
		MAcctSchemaDefault target = new MAcctSchemaDefault(getCtx(), 0, get_TrxName());
		target.setC_AcctSchema_ID(p_TargetAcctSchema_ID);
		target.setC_AcctSchema_ID(p_TargetAcctSchema_ID);
		ArrayList<KeyNamePair> list = source.getAcctInfo();
		for (int i = 0; i < list.size(); i++)
		{
			KeyNamePair pp = list.get(i);
			int sourceC_ValidCombination_ID = pp.getKey();
			String columnName = pp.getName();
			MAccount sourceAccount = MAccount.get(getCtx(), sourceC_ValidCombination_ID);
			MAccount targetAccount = createAccount(targetAS, sourceAccount);
			target.setValue(columnName, new Integer(targetAccount.getC_ValidCombination_ID()));
		}
		if (!target.save())
			throw new AdempiereSystemError("Could not Save Default");
	}	//	copyDefault
	
	/**
	 * 	Create Account
	 *	@param targetAS target AS
	 *	@param sourceAcct source account
	 *	@return target account
	 */
	private MAccount createAccount(MAcctSchema targetAS, MAccount sourceAcct)
	{
		int AD_Client_ID = targetAS.getAD_Client_ID(); 
		int C_AcctSchema_ID = targetAS.getC_AcctSchema_ID();
		//
		int AD_Org_ID = 0;
		int Account_ID = 0;
		int C_SubAcct_ID = 0;
		int M_Product_ID = 0;
		int C_BPartner_ID = 0;
		int AD_OrgTrx_ID = 0;
		int C_LocFrom_ID = 0;
		int C_LocTo_ID = 0;
		int C_SalesRegion_ID = 0; 
		int C_Project_ID = 0;
		int C_Campaign_ID = 0;
		int C_Activity_ID = 0;
		int User1_ID = 0;
		int User2_ID = 0;
		int UserElement1_ID = 0;
		int UserElement2_ID = 0;
		//
		//  Active Elements
		MAcctSchemaElement[] elements = targetAS.getAcctSchemaElements();
		for (int i = 0; i < elements.length; i++)
		{
			MAcctSchemaElement ase = elements[i];
			String elementType = ase.getElementType();
			//
			if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_Organization))
				AD_Org_ID = sourceAcct.getAD_Org_ID();
			else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_Account))
				Account_ID = sourceAcct.getAccount_ID();
			else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_SubAccount))
				C_SubAcct_ID = sourceAcct.getC_SubAcct_ID();
			else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_BPartner))
				C_BPartner_ID = sourceAcct.getC_BPartner_ID();
			else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_Product))
				M_Product_ID = sourceAcct.getM_Product_ID();
			else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_Activity))
				C_Activity_ID = sourceAcct.getC_Activity_ID();
			else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_LocationFrom))
				C_LocFrom_ID = sourceAcct.getC_LocFrom_ID();
			else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_LocationTo))
				C_LocTo_ID = sourceAcct.getC_LocTo_ID();
			else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_Campaign))
				C_Campaign_ID = sourceAcct.getC_Campaign_ID();
			else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_OrgTrx))
				AD_OrgTrx_ID = sourceAcct.getAD_OrgTrx_ID();
			else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_Project))
				C_Project_ID = sourceAcct.getC_Project_ID();
			else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_SalesRegion))
				C_SalesRegion_ID = sourceAcct.getC_SalesRegion_ID();
			else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_UserList1))
				User1_ID = sourceAcct.getUser1_ID();
			else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_UserList2))
				User2_ID = sourceAcct.getUser2_ID();
			else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_UserElement1))
				UserElement1_ID = sourceAcct.getUserElement1_ID();
			else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_UserElement2))
				UserElement2_ID = sourceAcct.getUserElement2_ID();
			//	No UserElement
		}
		//
		return MAccount.get(getCtx(), AD_Client_ID, AD_Org_ID,
			C_AcctSchema_ID, Account_ID, C_SubAcct_ID,
			M_Product_ID, C_BPartner_ID, AD_OrgTrx_ID,
			C_LocFrom_ID, C_LocTo_ID, C_SalesRegion_ID, 
			C_Project_ID, C_Campaign_ID, C_Activity_ID,
			User1_ID, User2_ID, UserElement1_ID, UserElement2_ID);
	}	//	createAccount
	
	
}	//	AcctSchemaCopyAcct

