/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package de.metas.letters.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_BoilerPlate_Var_Eval
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_BoilerPlate_Var_Eval extends org.compiere.model.PO implements I_AD_BoilerPlate_Var_Eval, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -92602039L;

    /** Standard Constructor */
    public X_AD_BoilerPlate_Var_Eval (Properties ctx, int AD_BoilerPlate_Var_Eval_ID, String trxName)
    {
      super (ctx, AD_BoilerPlate_Var_Eval_ID, trxName);
      /** if (AD_BoilerPlate_Var_Eval_ID == 0)
        {
			setAD_BoilerPlate_Var_ID (0);
			setC_DocType_ID (0);
			setEvalTime (null);
        } */
    }

    /** Load Constructor */
    public X_AD_BoilerPlate_Var_Eval (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_AD_BoilerPlate_Var_Eval[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public de.metas.letters.model.I_AD_BoilerPlate_Var getAD_BoilerPlate_Var() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_BoilerPlate_Var_ID, de.metas.letters.model.I_AD_BoilerPlate_Var.class);
	}

	@Override
	public void setAD_BoilerPlate_Var(de.metas.letters.model.I_AD_BoilerPlate_Var AD_BoilerPlate_Var)
	{
		set_ValueFromPO(COLUMNNAME_AD_BoilerPlate_Var_ID, de.metas.letters.model.I_AD_BoilerPlate_Var.class, AD_BoilerPlate_Var);
	}

	/** Set Boiler Plate Variable.
		@param AD_BoilerPlate_Var_ID Boiler Plate Variable	  */
	@Override
	public void setAD_BoilerPlate_Var_ID (int AD_BoilerPlate_Var_ID)
	{
		if (AD_BoilerPlate_Var_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_BoilerPlate_Var_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_BoilerPlate_Var_ID, Integer.valueOf(AD_BoilerPlate_Var_ID));
	}

	/** Get Boiler Plate Variable.
		@return Boiler Plate Variable	  */
	@Override
	public int getAD_BoilerPlate_Var_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_BoilerPlate_Var_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType)
	{
		set_ValueFromPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class, C_DocType);
	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	@Override
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * EvalTime AD_Reference_ID=53237
	 * Reference name: EventModelValidator
	 */
	public static final int EVALTIME_AD_Reference_ID=53237;
	/** Table Before New = TBN */
	public static final String EVALTIME_TableBeforeNew = "TBN";
	/** Table Before Change = TBC */
	public static final String EVALTIME_TableBeforeChange = "TBC";
	/** Table Before Delete = TBD */
	public static final String EVALTIME_TableBeforeDelete = "TBD";
	/** Table After New = TAN */
	public static final String EVALTIME_TableAfterNew = "TAN";
	/** Table After Change = TAC */
	public static final String EVALTIME_TableAfterChange = "TAC";
	/** Table After Delete = TAD */
	public static final String EVALTIME_TableAfterDelete = "TAD";
	/** Document Before Prepare = DBPR */
	public static final String EVALTIME_DocumentBeforePrepare = "DBPR";
	/** Document Before Void = DBVO */
	public static final String EVALTIME_DocumentBeforeVoid = "DBVO";
	/** Document Before Close = DBCL */
	public static final String EVALTIME_DocumentBeforeClose = "DBCL";
	/** Document Before Reactivate = DBAC */
	public static final String EVALTIME_DocumentBeforeReactivate = "DBAC";
	/** Document Before Reverse Correct = DBRC */
	public static final String EVALTIME_DocumentBeforeReverseCorrect = "DBRC";
	/** Document Before Reverse Accrual = DBRA */
	public static final String EVALTIME_DocumentBeforeReverseAccrual = "DBRA";
	/** Document Before Complete = DBCO */
	public static final String EVALTIME_DocumentBeforeComplete = "DBCO";
	/** Document Before Post = DBPO */
	public static final String EVALTIME_DocumentBeforePost = "DBPO";
	/** Document After Prepare = DAPR */
	public static final String EVALTIME_DocumentAfterPrepare = "DAPR";
	/** Document After Void = DAVO */
	public static final String EVALTIME_DocumentAfterVoid = "DAVO";
	/** Document After Close = DACL */
	public static final String EVALTIME_DocumentAfterClose = "DACL";
	/** Document After Reactivate = DAAC */
	public static final String EVALTIME_DocumentAfterReactivate = "DAAC";
	/** Document After Reverse Correct = DARC */
	public static final String EVALTIME_DocumentAfterReverseCorrect = "DARC";
	/** Document After Reverse Accrual = DARA */
	public static final String EVALTIME_DocumentAfterReverseAccrual = "DARA";
	/** Document After Complete = DACO */
	public static final String EVALTIME_DocumentAfterComplete = "DACO";
	/** Document After Post = DAPO */
	public static final String EVALTIME_DocumentAfterPost = "DAPO";
	/** Table After New Replication = TANR */
	public static final String EVALTIME_TableAfterNewReplication = "TANR";
	/** Table After Change Replication = TACR */
	public static final String EVALTIME_TableAfterChangeReplication = "TACR";
	/** Table Before Delete Replication = TBDR */
	public static final String EVALTIME_TableBeforeDeleteReplication = "TBDR";
	/** Table Subsequent Processing = TSP */
	public static final String EVALTIME_TableSubsequentProcessing = "TSP";
	/** Set Evaluation Time.
		@param EvalTime Evaluation Time	  */
	@Override
	public void setEvalTime (java.lang.String EvalTime)
	{

		set_Value (COLUMNNAME_EvalTime, EvalTime);
	}

	/** Get Evaluation Time.
		@return Evaluation Time	  */
	@Override
	public java.lang.String getEvalTime () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EvalTime);
	}
}