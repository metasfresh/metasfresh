// Generated Model - DO NOT CHANGE
package de.metas.material.dispo.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Candidate_StockChange_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MD_Candidate_StockChange_Detail extends org.compiere.model.PO implements I_MD_Candidate_StockChange_Detail, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = -347882482L;

	/** Standard Constructor */
	public X_MD_Candidate_StockChange_Detail (final Properties ctx, final int MD_Candidate_StockChange_Detail_ID, @Nullable final String trxName)
	{
		super (ctx, MD_Candidate_StockChange_Detail_ID, trxName);
	}

	/** Load Constructor */
	public X_MD_Candidate_StockChange_Detail (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setEventDateMaterialDispo (final java.sql.Timestamp EventDateMaterialDispo)
	{
		set_Value (COLUMNNAME_EventDateMaterialDispo, EventDateMaterialDispo);
	}

	@Override
	public java.sql.Timestamp getEventDateMaterialDispo()
	{
		return get_ValueAsTimestamp(COLUMNNAME_EventDateMaterialDispo);
	}

	@Override
	public void setFresh_QtyOnHand_ID (final int Fresh_QtyOnHand_ID)
	{
		if (Fresh_QtyOnHand_ID < 1)
			set_Value (COLUMNNAME_Fresh_QtyOnHand_ID, null);
		else
			set_Value (COLUMNNAME_Fresh_QtyOnHand_ID, Fresh_QtyOnHand_ID);
	}

	@Override
	public int getFresh_QtyOnHand_ID()
	{
		return get_ValueAsInt(COLUMNNAME_Fresh_QtyOnHand_ID);
	}

	@Override
	public void setFresh_QtyOnHand_Line_ID (final int Fresh_QtyOnHand_Line_ID)
	{
		if (Fresh_QtyOnHand_Line_ID < 1)
			set_Value (COLUMNNAME_Fresh_QtyOnHand_Line_ID, null);
		else
			set_Value (COLUMNNAME_Fresh_QtyOnHand_Line_ID, Fresh_QtyOnHand_Line_ID);
	}

	@Override
	public int getFresh_QtyOnHand_Line_ID()
	{
		return get_ValueAsInt(COLUMNNAME_Fresh_QtyOnHand_Line_ID);
	}

	@Override
	public void setIsReverted (final boolean IsReverted)
	{
		set_Value (COLUMNNAME_IsReverted, IsReverted);
	}

	@Override
	public boolean isReverted()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReverted);
	}

	@Override
	public org.compiere.model.I_M_Inventory getM_Inventory()
	{
		return get_ValueAsPO(COLUMNNAME_M_Inventory_ID, org.compiere.model.I_M_Inventory.class);
	}

	@Override
	public void setM_Inventory(final org.compiere.model.I_M_Inventory M_Inventory)
	{
		set_ValueFromPO(COLUMNNAME_M_Inventory_ID, org.compiere.model.I_M_Inventory.class, M_Inventory);
	}

	@Override
	public void setM_Inventory_ID (final int M_Inventory_ID)
	{
		if (M_Inventory_ID < 1)
			set_Value (COLUMNNAME_M_Inventory_ID, null);
		else
			set_Value (COLUMNNAME_M_Inventory_ID, M_Inventory_ID);
	}

	@Override
	public int getM_Inventory_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_Inventory_ID);
	}

	@Override
	public org.compiere.model.I_M_InventoryLine getM_InventoryLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InventoryLine_ID, org.compiere.model.I_M_InventoryLine.class);
	}

	@Override
	public void setM_InventoryLine(final org.compiere.model.I_M_InventoryLine M_InventoryLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InventoryLine_ID, org.compiere.model.I_M_InventoryLine.class, M_InventoryLine);
	}

	@Override
	public void setM_InventoryLine_ID (final int M_InventoryLine_ID)
	{
		if (M_InventoryLine_ID < 1)
			set_Value (COLUMNNAME_M_InventoryLine_ID, null);
		else
			set_Value (COLUMNNAME_M_InventoryLine_ID, M_InventoryLine_ID);
	}

	@Override
	public int getM_InventoryLine_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_InventoryLine_ID);
	}

	@Override
	public de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate()
	{
		return get_ValueAsPO(COLUMNNAME_MD_Candidate_ID, de.metas.material.dispo.model.I_MD_Candidate.class);
	}

	@Override
	public void setMD_Candidate(final de.metas.material.dispo.model.I_MD_Candidate MD_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_MD_Candidate_ID, de.metas.material.dispo.model.I_MD_Candidate.class, MD_Candidate);
	}

	@Override
	public void setMD_Candidate_ID (final int MD_Candidate_ID)
	{
		if (MD_Candidate_ID < 1)
			set_Value (COLUMNNAME_MD_Candidate_ID, null);
		else
			set_Value (COLUMNNAME_MD_Candidate_ID, MD_Candidate_ID);
	}

	@Override
	public int getMD_Candidate_ID()
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_ID);
	}

	@Override
	public void setMD_Candidate_StockChange_Detail_ID (final int MD_Candidate_StockChange_Detail_ID)
	{
		if (MD_Candidate_StockChange_Detail_ID < 1)
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_StockChange_Detail_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_StockChange_Detail_ID, MD_Candidate_StockChange_Detail_ID);
	}

	@Override
	public int getMD_Candidate_StockChange_Detail_ID()
	{
		return get_ValueAsInt(COLUMNNAME_MD_Candidate_StockChange_Detail_ID);
	}
}