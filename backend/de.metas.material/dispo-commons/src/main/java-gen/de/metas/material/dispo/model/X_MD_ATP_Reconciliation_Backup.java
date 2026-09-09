// Generated Model - DO NOT CHANGE
package de.metas.material.dispo.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for MD_ATP_Reconciliation_Backup
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MD_ATP_Reconciliation_Backup extends org.compiere.model.PO implements I_MD_ATP_Reconciliation_Backup, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -249210409L;

    /** Standard Constructor */
    public X_MD_ATP_Reconciliation_Backup (final Properties ctx, final int MD_ATP_Reconciliation_Backup_ID, @Nullable final String trxName)
    {
      super (ctx, MD_ATP_Reconciliation_Backup_ID, trxName);
    }

    /** Load Constructor */
    public X_MD_ATP_Reconciliation_Backup (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setDateProjected (final java.sql.Timestamp DateProjected)
	{
		set_ValueNoCheck (COLUMNNAME_DateProjected, DateProjected);
	}

	@Override
	public java.sql.Timestamp getDateProjected() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateProjected);
	}

	@Override
	public void setMD_ATP_Reconciliation_Backup_ID (final int MD_ATP_Reconciliation_Backup_ID)
	{
		if (MD_ATP_Reconciliation_Backup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_ATP_Reconciliation_Backup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_ATP_Reconciliation_Backup_ID, MD_ATP_Reconciliation_Backup_ID);
	}

	@Override
	public int getMD_ATP_Reconciliation_Backup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_ATP_Reconciliation_Backup_ID);
	}

	@Override
	public void setMD_Candidate_ID (final int MD_Candidate_ID)
	{
		if (MD_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_ID, MD_Candidate_ID);
	}

	@Override
	public int getMD_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setQtyAfter (final BigDecimal QtyAfter)
	{
		set_ValueNoCheck (COLUMNNAME_QtyAfter, QtyAfter);
	}

	@Override
	public BigDecimal getQtyAfter() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyAfter);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyBefore (final @Nullable BigDecimal QtyBefore)
	{
		set_ValueNoCheck (COLUMNNAME_QtyBefore, QtyBefore);
	}

	@Override
	public BigDecimal getQtyBefore() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyBefore);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setReconciliationRunUUID (final java.lang.String ReconciliationRunUUID)
	{
		set_ValueNoCheck (COLUMNNAME_ReconciliationRunUUID, ReconciliationRunUUID);
	}

	@Override
	public java.lang.String getReconciliationRunUUID() 
	{
		return get_ValueAsString(COLUMNNAME_ReconciliationRunUUID);
	}

	@Override
	public void setStorageAttributesKey (final java.lang.String StorageAttributesKey)
	{
		set_ValueNoCheck (COLUMNNAME_StorageAttributesKey, StorageAttributesKey);
	}

	@Override
	public java.lang.String getStorageAttributesKey() 
	{
		return get_ValueAsString(COLUMNNAME_StorageAttributesKey);
	}
}