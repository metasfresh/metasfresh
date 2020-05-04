/** Generated Model - DO NOT CHANGE */
package de.metas.async.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Queue_Block
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Queue_Block extends org.compiere.model.PO implements I_C_Queue_Block, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 393982714L;

    /** Standard Constructor */
    public X_C_Queue_Block (Properties ctx, int C_Queue_Block_ID, String trxName)
    {
      super (ctx, C_Queue_Block_ID, trxName);
      /** if (C_Queue_Block_ID == 0)
        {
			setC_Queue_Block_ID (0);
			setC_Queue_PackageProcessor_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Queue_Block (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_PInstance getAD_PInstance_Creator()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_Creator_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance_Creator(org.compiere.model.I_AD_PInstance AD_PInstance_Creator)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_Creator_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance_Creator);
	}

	/** Set Erstellt durch Prozess-Instanz.
		@param AD_PInstance_Creator_ID Erstellt durch Prozess-Instanz	  */
	@Override
	public void setAD_PInstance_Creator_ID (int AD_PInstance_Creator_ID)
	{
		if (AD_PInstance_Creator_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_Creator_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_Creator_ID, Integer.valueOf(AD_PInstance_Creator_ID));
	}

	/** Get Erstellt durch Prozess-Instanz.
		@return Erstellt durch Prozess-Instanz	  */
	@Override
	public int getAD_PInstance_Creator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PInstance_Creator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Queue Block.
		@param C_Queue_Block_ID Queue Block	  */
	@Override
	public void setC_Queue_Block_ID (int C_Queue_Block_ID)
	{
		if (C_Queue_Block_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_Block_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_Block_ID, Integer.valueOf(C_Queue_Block_ID));
	}

	/** Get Queue Block.
		@return Queue Block	  */
	@Override
	public int getC_Queue_Block_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_Block_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.async.model.I_C_Queue_PackageProcessor getC_Queue_PackageProcessor()
	{
		return get_ValueAsPO(COLUMNNAME_C_Queue_PackageProcessor_ID, de.metas.async.model.I_C_Queue_PackageProcessor.class);
	}

	@Override
	public void setC_Queue_PackageProcessor(de.metas.async.model.I_C_Queue_PackageProcessor C_Queue_PackageProcessor)
	{
		set_ValueFromPO(COLUMNNAME_C_Queue_PackageProcessor_ID, de.metas.async.model.I_C_Queue_PackageProcessor.class, C_Queue_PackageProcessor);
	}

	/** Set WorkPackage Processor.
		@param C_Queue_PackageProcessor_ID WorkPackage Processor	  */
	@Override
	public void setC_Queue_PackageProcessor_ID (int C_Queue_PackageProcessor_ID)
	{
		if (C_Queue_PackageProcessor_ID < 1) 
			set_Value (COLUMNNAME_C_Queue_PackageProcessor_ID, null);
		else 
			set_Value (COLUMNNAME_C_Queue_PackageProcessor_ID, Integer.valueOf(C_Queue_PackageProcessor_ID));
	}

	/** Get WorkPackage Processor.
		@return WorkPackage Processor	  */
	@Override
	public int getC_Queue_PackageProcessor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_PackageProcessor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}