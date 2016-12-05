/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_RelationType
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_RelationType extends org.compiere.model.PO implements I_AD_RelationType, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 791382458L;

    /** Standard Constructor */
    public X_AD_RelationType (Properties ctx, int AD_RelationType_ID, String trxName)
    {
      super (ctx, AD_RelationType_ID, trxName);
      /** if (AD_RelationType_ID == 0)
        {
			setAD_RelationType_ID (0);
			setEntityType (null);
// de.metas.swat
			setIsDirected (false);
// N
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_RelationType (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Reference getAD_Reference_Source() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_Source_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference_Source(org.compiere.model.I_AD_Reference AD_Reference_Source)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_Source_ID, org.compiere.model.I_AD_Reference.class, AD_Reference_Source);
	}

	/** Set Source Reference.
		@param AD_Reference_Source_ID 
		For directed relation types, the where clause of this reference's AD_Ref_Table is used to decide if a given record is a possible relation source.
	  */
	@Override
	public void setAD_Reference_Source_ID (int AD_Reference_Source_ID)
	{
		if (AD_Reference_Source_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_Source_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_Source_ID, Integer.valueOf(AD_Reference_Source_ID));
	}

	/** Get Source Reference.
		@return For directed relation types, the where clause of this reference's AD_Ref_Table is used to decide if a given record is a possible relation source.
	  */
	@Override
	public int getAD_Reference_Source_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_Source_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Reference getAD_Reference_Target() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_Target_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference_Target(org.compiere.model.I_AD_Reference AD_Reference_Target)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_Target_ID, org.compiere.model.I_AD_Reference.class, AD_Reference_Target);
	}

	/** Set Target Reference.
		@param AD_Reference_Target_ID 
		For directed relation types, the table and where clause of this reference's AD_Ref_Table is used to select the relation partners for a given source-record (e.g. the shipments for a given order).
	  */
	@Override
	public void setAD_Reference_Target_ID (int AD_Reference_Target_ID)
	{
		if (AD_Reference_Target_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_Target_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_Target_ID, Integer.valueOf(AD_Reference_Target_ID));
	}

	/** Get Target Reference.
		@return For directed relation types, the table and where clause of this reference's AD_Ref_Table is used to select the relation partners for a given source-record (e.g. the shipments for a given order).
	  */
	@Override
	public int getAD_Reference_Target_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_Target_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Relation Type.
		@param AD_RelationType_ID Relation Type	  */
	@Override
	public void setAD_RelationType_ID (int AD_RelationType_ID)
	{
		if (AD_RelationType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_RelationType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_RelationType_ID, Integer.valueOf(AD_RelationType_ID));
	}

	/** Get Relation Type.
		@return Relation Type	  */
	@Override
	public int getAD_RelationType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_RelationType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entitäts-Art.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public void setEntityType (java.lang.String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entitäts-Art.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public java.lang.String getEntityType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EntityType);
	}

	/** Set Interner Name.
		@param InternalName 
		Generally used to give records a name that can be safely referenced from code.
	  */
	@Override
	public void setInternalName (java.lang.String InternalName)
	{
		set_ValueNoCheck (COLUMNNAME_InternalName, InternalName);
	}

	/** Get Interner Name.
		@return Generally used to give records a name that can be safely referenced from code.
	  */
	@Override
	public java.lang.String getInternalName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InternalName);
	}

	/** Set Directed.
		@param IsDirected 
		Tells whether one "sees" the other end of the relation from each end or just from the source.
	  */
	@Override
	public void setIsDirected (boolean IsDirected)
	{
		set_Value (COLUMNNAME_IsDirected, Boolean.valueOf(IsDirected));
	}

	/** Get Directed.
		@return Tells whether one "sees" the other end of the relation from each end or just from the source.
	  */
	@Override
	public boolean isDirected () 
	{
		Object oo = get_Value(COLUMNNAME_IsDirected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** 
	 * Role_Source AD_Reference_ID=53331
	 * Reference name: AD_RelationType Role
	 */
	public static final int ROLE_SOURCE_AD_Reference_ID=53331;
	/** Auftrag = Order */
	public static final String ROLE_SOURCE_Auftrag = "Order";
	/** Rechnung = Invoice */
	public static final String ROLE_SOURCE_Rechnung = "Invoice";
	/** Abo = Abo */
	public static final String ROLE_SOURCE_Abo = "Abo";
	/** Lieferung = Shipment */
	public static final String ROLE_SOURCE_Lieferung = "Shipment";
	/** Gutschrift Rechnung = Credit Memo */
	public static final String ROLE_SOURCE_GutschriftRechnung = "Credit Memo";
	/** Cockpit = Cockpit */
	public static final String ROLE_SOURCE_Cockpit = "Cockpit";
	/** Zahlung = Payment */
	public static final String ROLE_SOURCE_Zahlung = "Payment";
	/** Packstück = Package */
	public static final String ROLE_SOURCE_Packstueck = "Package";
	/** Lieferdispo = ShipmentSchedule */
	public static final String ROLE_SOURCE_Lieferdispo = "ShipmentSchedule";
	/** Prov-Abrechnung = ComCalc */
	public static final String ROLE_SOURCE_Prov_Abrechnung = "ComCalc";
	/** Prov-Korrektur = ComCorr */
	public static final String ROLE_SOURCE_Prov_Korrektur = "ComCorr";
	/** Auftragskandidat = OLCand */
	public static final String ROLE_SOURCE_Auftragskandidat = "OLCand";
	/** Auftragspos. = C_OrderLine */
	public static final String ROLE_SOURCE_Auftragspos = "C_OrderLine";
	/** Bestelldispo = PurchaseSchedule */
	public static final String ROLE_SOURCE_Bestelldispo = "PurchaseSchedule";
	/** Bestellung = PurchaseOrder */
	public static final String ROLE_SOURCE_Bestellung = "PurchaseOrder";
	/** Bestellung (offen) = PurchaseOrder_Current */
	public static final String ROLE_SOURCE_BestellungOffen = "PurchaseOrder_Current";
	/** Bestellung (hist.) = PurchaseOrder_Done */
	public static final String ROLE_SOURCE_BestellungHist = "PurchaseOrder_Done";
	/** Auftrag (offen) = Order_Current */
	public static final String ROLE_SOURCE_AuftragOffen = "Order_Current";
	/** Auftrag (hist.) = Order_hist */
	public static final String ROLE_SOURCE_AuftragHist = "Order_hist";
	/** Bedarf = ReqLine */
	public static final String ROLE_SOURCE_Bedarf = "ReqLine";
	/** Beziehungen Geschäftspartner = C_BP_Relation */
	public static final String ROLE_SOURCE_BeziehungenGeschaeftspartner = "C_BP_Relation";
	/** Zuordnung = C_AllocationLine */
	public static final String ROLE_SOURCE_Zuordnung = "C_AllocationLine";
	/** Bankauszug = C_BankStatement */
	public static final String ROLE_SOURCE_Bankauszug = "C_BankStatement";
	/** Eingangsrechnung = PO_Invoice */
	public static final String ROLE_SOURCE_Eingangsrechnung = "PO_Invoice";
	/** ESR Import = ESR_Import */
	public static final String ROLE_SOURCE_ESRImport = "ESR_Import";
	/** Set Source Role.
		@param Role_Source Source Role	  */
	@Override
	public void setRole_Source (java.lang.String Role_Source)
	{

		set_Value (COLUMNNAME_Role_Source, Role_Source);
	}

	/** Get Source Role.
		@return Source Role	  */
	@Override
	public java.lang.String getRole_Source () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Role_Source);
	}

	/** 
	 * Role_Target AD_Reference_ID=53331
	 * Reference name: AD_RelationType Role
	 */
	public static final int ROLE_TARGET_AD_Reference_ID=53331;
	/** Auftrag = Order */
	public static final String ROLE_TARGET_Auftrag = "Order";
	/** Rechnung = Invoice */
	public static final String ROLE_TARGET_Rechnung = "Invoice";
	/** Abo = Abo */
	public static final String ROLE_TARGET_Abo = "Abo";
	/** Lieferung = Shipment */
	public static final String ROLE_TARGET_Lieferung = "Shipment";
	/** Gutschrift Rechnung = Credit Memo */
	public static final String ROLE_TARGET_GutschriftRechnung = "Credit Memo";
	/** Cockpit = Cockpit */
	public static final String ROLE_TARGET_Cockpit = "Cockpit";
	/** Zahlung = Payment */
	public static final String ROLE_TARGET_Zahlung = "Payment";
	/** Packstück = Package */
	public static final String ROLE_TARGET_Packstueck = "Package";
	/** Lieferdispo = ShipmentSchedule */
	public static final String ROLE_TARGET_Lieferdispo = "ShipmentSchedule";
	/** Prov-Abrechnung = ComCalc */
	public static final String ROLE_TARGET_Prov_Abrechnung = "ComCalc";
	/** Prov-Korrektur = ComCorr */
	public static final String ROLE_TARGET_Prov_Korrektur = "ComCorr";
	/** Auftragskandidat = OLCand */
	public static final String ROLE_TARGET_Auftragskandidat = "OLCand";
	/** Auftragspos. = C_OrderLine */
	public static final String ROLE_TARGET_Auftragspos = "C_OrderLine";
	/** Bestelldispo = PurchaseSchedule */
	public static final String ROLE_TARGET_Bestelldispo = "PurchaseSchedule";
	/** Bestellung = PurchaseOrder */
	public static final String ROLE_TARGET_Bestellung = "PurchaseOrder";
	/** Bestellung (offen) = PurchaseOrder_Current */
	public static final String ROLE_TARGET_BestellungOffen = "PurchaseOrder_Current";
	/** Bestellung (hist.) = PurchaseOrder_Done */
	public static final String ROLE_TARGET_BestellungHist = "PurchaseOrder_Done";
	/** Auftrag (offen) = Order_Current */
	public static final String ROLE_TARGET_AuftragOffen = "Order_Current";
	/** Auftrag (hist.) = Order_hist */
	public static final String ROLE_TARGET_AuftragHist = "Order_hist";
	/** Bedarf = ReqLine */
	public static final String ROLE_TARGET_Bedarf = "ReqLine";
	/** Beziehungen Geschäftspartner = C_BP_Relation */
	public static final String ROLE_TARGET_BeziehungenGeschaeftspartner = "C_BP_Relation";
	/** Zuordnung = C_AllocationLine */
	public static final String ROLE_TARGET_Zuordnung = "C_AllocationLine";
	/** Bankauszug = C_BankStatement */
	public static final String ROLE_TARGET_Bankauszug = "C_BankStatement";
	/** Eingangsrechnung = PO_Invoice */
	public static final String ROLE_TARGET_Eingangsrechnung = "PO_Invoice";
	/** ESR Import = ESR_Import */
	public static final String ROLE_TARGET_ESRImport = "ESR_Import";
	/** Set Target Role.
		@param Role_Target Target Role	  */
	@Override
	public void setRole_Target (java.lang.String Role_Target)
	{

		set_Value (COLUMNNAME_Role_Target, Role_Target);
	}

	/** Get Target Role.
		@return Target Role	  */
	@Override
	public java.lang.String getRole_Target () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Role_Target);
	}
}