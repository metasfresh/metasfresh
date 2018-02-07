/** Generated Model - DO NOT CHANGE */
package de.metas.product.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product_PlanningSchema
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Product_PlanningSchema extends org.compiere.model.PO implements I_M_Product_PlanningSchema, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 828536954L;

    /** Standard Constructor */
    public X_M_Product_PlanningSchema (Properties ctx, int M_Product_PlanningSchema_ID, String trxName)
    {
      super (ctx, M_Product_PlanningSchema_ID, trxName);
      /** if (M_Product_PlanningSchema_ID == 0)
        {
			setIsAttributeDependant (false); // N
			setIsCreatePlan (true); // Y
			setIsDocComplete (false); // N
			setIsManufactured (null); // N
			setIsMPS (false); // N
			setIsPickDirectlyIfFeasible (false); // N
			setIsPurchased (null); // N
			setIsTraded (null); // N
			setM_Product_PlanningSchema_ID (0);
			setM_ProductPlanningSchema_Selector (null);
        } */
    }

    /** Load Constructor */
    public X_M_Product_PlanningSchema (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Workflow getAD_Workflow() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Workflow_ID, org.compiere.model.I_AD_Workflow.class);
	}

	@Override
	public void setAD_Workflow(org.compiere.model.I_AD_Workflow AD_Workflow)
	{
		set_ValueFromPO(COLUMNNAME_AD_Workflow_ID, org.compiere.model.I_AD_Workflow.class, AD_Workflow);
	}

	/** Set Workflow.
		@param AD_Workflow_ID 
		Workflow oder Kombination von Aufgaben
	  */
	@Override
	public void setAD_Workflow_ID (int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1) 
			set_Value (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Workflow_ID, Integer.valueOf(AD_Workflow_ID));
	}

	/** Get Workflow.
		@return Workflow oder Kombination von Aufgaben
	  */
	@Override
	public int getAD_Workflow_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Workflow_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_DD_NetworkDistribution getDD_NetworkDistribution() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DD_NetworkDistribution_ID, org.eevolution.model.I_DD_NetworkDistribution.class);
	}

	@Override
	public void setDD_NetworkDistribution(org.eevolution.model.I_DD_NetworkDistribution DD_NetworkDistribution)
	{
		set_ValueFromPO(COLUMNNAME_DD_NetworkDistribution_ID, org.eevolution.model.I_DD_NetworkDistribution.class, DD_NetworkDistribution);
	}

	/** Set Network Distribution.
		@param DD_NetworkDistribution_ID Network Distribution	  */
	@Override
	public void setDD_NetworkDistribution_ID (int DD_NetworkDistribution_ID)
	{
		if (DD_NetworkDistribution_ID < 1) 
			set_Value (COLUMNNAME_DD_NetworkDistribution_ID, null);
		else 
			set_Value (COLUMNNAME_DD_NetworkDistribution_ID, Integer.valueOf(DD_NetworkDistribution_ID));
	}

	/** Get Network Distribution.
		@return Network Distribution	  */
	@Override
	public int getDD_NetworkDistribution_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DD_NetworkDistribution_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zugesicherte Lieferzeit.
		@param DeliveryTime_Promised 
		Zugesicherte Anzahl Tage zwischen Bestellung und Lieferung
	  */
	@Override
	public void setDeliveryTime_Promised (java.math.BigDecimal DeliveryTime_Promised)
	{
		set_Value (COLUMNNAME_DeliveryTime_Promised, DeliveryTime_Promised);
	}

	/** Get Zugesicherte Lieferzeit.
		@return Zugesicherte Anzahl Tage zwischen Bestellung und Lieferung
	  */
	@Override
	public java.math.BigDecimal getDeliveryTime_Promised () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DeliveryTime_Promised);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Attributabhängig.
		@param IsAttributeDependant Attributabhängig	  */
	@Override
	public void setIsAttributeDependant (boolean IsAttributeDependant)
	{
		set_Value (COLUMNNAME_IsAttributeDependant, Boolean.valueOf(IsAttributeDependant));
	}

	/** Get Attributabhängig.
		@return Attributabhängig	  */
	@Override
	public boolean isAttributeDependant () 
	{
		Object oo = get_Value(COLUMNNAME_IsAttributeDependant);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Beleg erstellen.
		@param IsCreatePlan 
		Legt fest, ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.
	  */
	@Override
	public void setIsCreatePlan (boolean IsCreatePlan)
	{
		set_Value (COLUMNNAME_IsCreatePlan, Boolean.valueOf(IsCreatePlan));
	}

	/** Get Beleg erstellen.
		@return Legt fest, ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.
	  */
	@Override
	public boolean isCreatePlan () 
	{
		Object oo = get_Value(COLUMNNAME_IsCreatePlan);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Beleg fertig stellen.
		@param IsDocComplete 
		Legt fest, ob ggf erstellte Belege (z.B. Produktionsaufträge) auch direkt automatisch fertig gestellt werden sollen.
	  */
	@Override
	public void setIsDocComplete (boolean IsDocComplete)
	{
		set_Value (COLUMNNAME_IsDocComplete, Boolean.valueOf(IsDocComplete));
	}

	/** Get Beleg fertig stellen.
		@return Legt fest, ob ggf erstellte Belege (z.B. Produktionsaufträge) auch direkt automatisch fertig gestellt werden sollen.
	  */
	@Override
	public boolean isDocComplete () 
	{
		Object oo = get_Value(COLUMNNAME_IsDocComplete);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** 
	 * IsManufactured AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISMANUFACTURED_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISMANUFACTURED_Yes = "Y";
	/** No = N */
	public static final String ISMANUFACTURED_No = "N";
	/** Set Wird produziert.
		@param IsManufactured Wird produziert	  */
	@Override
	public void setIsManufactured (java.lang.String IsManufactured)
	{

		set_Value (COLUMNNAME_IsManufactured, IsManufactured);
	}

	/** Get Wird produziert.
		@return Wird produziert	  */
	@Override
	public java.lang.String getIsManufactured () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IsManufactured);
	}

	/** Set Is MPS.
		@param IsMPS Is MPS	  */
	@Override
	public void setIsMPS (boolean IsMPS)
	{
		set_Value (COLUMNNAME_IsMPS, Boolean.valueOf(IsMPS));
	}

	/** Get Is MPS.
		@return Is MPS	  */
	@Override
	public boolean isMPS () 
	{
		Object oo = get_Value(COLUMNNAME_IsMPS);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sofort Kommissionieren wenn möglich.
		@param IsPickDirectlyIfFeasible 
		Falls "Ja" und ein Bestand wird für einen bestimmten Lieferdispo-Eintrag bereit gestellt oder produziert, dann wird dieser sofort zugeordnet und als kommissioniert markiert.
	  */
	@Override
	public void setIsPickDirectlyIfFeasible (boolean IsPickDirectlyIfFeasible)
	{
		set_Value (COLUMNNAME_IsPickDirectlyIfFeasible, Boolean.valueOf(IsPickDirectlyIfFeasible));
	}

	/** Get Sofort Kommissionieren wenn möglich.
		@return Falls "Ja" und ein Bestand wird für einen bestimmten Lieferdispo-Eintrag bereit gestellt oder produziert, dann wird dieser sofort zugeordnet und als kommissioniert markiert.
	  */
	@Override
	public boolean isPickDirectlyIfFeasible () 
	{
		Object oo = get_Value(COLUMNNAME_IsPickDirectlyIfFeasible);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** 
	 * IsPurchased AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISPURCHASED_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISPURCHASED_Yes = "Y";
	/** No = N */
	public static final String ISPURCHASED_No = "N";
	/** Set Eingekauft.
		@param IsPurchased 
		Die Organisation kauft dieses Produkt ein
	  */
	@Override
	public void setIsPurchased (java.lang.String IsPurchased)
	{

		set_Value (COLUMNNAME_IsPurchased, IsPurchased);
	}

	/** Get Eingekauft.
		@return Die Organisation kauft dieses Produkt ein
	  */
	@Override
	public java.lang.String getIsPurchased () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IsPurchased);
	}

	/** 
	 * IsTraded AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISTRADED_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISTRADED_Yes = "Y";
	/** No = N */
	public static final String ISTRADED_No = "N";
	/** Set Wird gehandelt (Bestellkontrolle).
		@param IsTraded 
		Legt fest, ob mit dem bestreffenden Produkt gehandelt wird. 
	  */
	@Override
	public void setIsTraded (java.lang.String IsTraded)
	{

		set_Value (COLUMNNAME_IsTraded, IsTraded);
	}

	/** Get Wird gehandelt (Bestellkontrolle).
		@return Legt fest, ob mit dem bestreffenden Produkt gehandelt wird. 
	  */
	@Override
	public java.lang.String getIsTraded () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IsTraded);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	/** Set Merkmale.
		@param M_AttributeSetInstance_ID 
		Merkmals Ausprägungen zum Produkt
	  */
	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Merkmale.
		@return Merkmals Ausprägungen zum Produkt
	  */
	@Override
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Product Planning Schema.
		@param M_Product_PlanningSchema_ID Product Planning Schema	  */
	@Override
	public void setM_Product_PlanningSchema_ID (int M_Product_PlanningSchema_ID)
	{
		if (M_Product_PlanningSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_PlanningSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_PlanningSchema_ID, Integer.valueOf(M_Product_PlanningSchema_ID));
	}

	/** Get Product Planning Schema.
		@return Product Planning Schema	  */
	@Override
	public int getM_Product_PlanningSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_PlanningSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * M_ProductPlanningSchema_Selector AD_Reference_ID=540829
	 * Reference name: M_ProductPlanningSchema_Selector_List
	 */
	public static final int M_PRODUCTPLANNINGSCHEMA_SELECTOR_AD_Reference_ID=540829;
	/** Normal = N */
	public static final String M_PRODUCTPLANNINGSCHEMA_SELECTOR_Normal = "N";
	/** Set M_ProductPlanningSchema_Selector.
		@param M_ProductPlanningSchema_Selector M_ProductPlanningSchema_Selector	  */
	@Override
	public void setM_ProductPlanningSchema_Selector (java.lang.String M_ProductPlanningSchema_Selector)
	{

		set_Value (COLUMNNAME_M_ProductPlanningSchema_Selector, M_ProductPlanningSchema_Selector);
	}

	/** Get M_ProductPlanningSchema_Selector.
		@return M_ProductPlanningSchema_Selector	  */
	@Override
	public java.lang.String getM_ProductPlanningSchema_Selector () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_M_ProductPlanningSchema_Selector);
	}

	@Override
	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class, M_Warehouse);
	}

	/** Set Lager.
		@param M_Warehouse_ID 
		Lager oder Ort für Dienstleistung
	  */
	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Lager.
		@return Lager oder Ort für Dienstleistung
	  */
	@Override
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_User getPlanner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Planner_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setPlanner(org.compiere.model.I_AD_User Planner)
	{
		set_ValueFromPO(COLUMNNAME_Planner_ID, org.compiere.model.I_AD_User.class, Planner);
	}

	/** Set Planner.
		@param Planner_ID Planner	  */
	@Override
	public void setPlanner_ID (int Planner_ID)
	{
		if (Planner_ID < 1) 
			set_Value (COLUMNNAME_Planner_ID, null);
		else 
			set_Value (COLUMNNAME_Planner_ID, Integer.valueOf(Planner_ID));
	}

	/** Get Planner.
		@return Planner	  */
	@Override
	public int getPlanner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Planner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_S_Resource getS_Resource() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setS_Resource(org.compiere.model.I_S_Resource S_Resource)
	{
		set_ValueFromPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class, S_Resource);
	}

	/** Set Ressource.
		@param S_Resource_ID 
		Ressource
	  */
	@Override
	public void setS_Resource_ID (int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_Value (COLUMNNAME_S_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_S_Resource_ID, Integer.valueOf(S_Resource_ID));
	}

	/** Get Ressource.
		@return Ressource
	  */
	@Override
	public int getS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set StorageAttributesKey (technical).
		@param StorageAttributesKey StorageAttributesKey (technical)	  */
	@Override
	public void setStorageAttributesKey (java.lang.String StorageAttributesKey)
	{
		set_Value (COLUMNNAME_StorageAttributesKey, StorageAttributesKey);
	}

	/** Get StorageAttributesKey (technical).
		@return StorageAttributesKey (technical)	  */
	@Override
	public java.lang.String getStorageAttributesKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StorageAttributesKey);
	}

	/** Set Transfert Time.
		@param TransfertTime Transfert Time	  */
	@Override
	public void setTransfertTime (java.math.BigDecimal TransfertTime)
	{
		set_Value (COLUMNNAME_TransfertTime, TransfertTime);
	}

	/** Get Transfert Time.
		@return Transfert Time	  */
	@Override
	public java.math.BigDecimal getTransfertTime () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TransfertTime);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Working Time.
		@param WorkingTime 
		Workflow Simulation Execution Time
	  */
	@Override
	public void setWorkingTime (java.math.BigDecimal WorkingTime)
	{
		set_Value (COLUMNNAME_WorkingTime, WorkingTime);
	}

	/** Get Working Time.
		@return Workflow Simulation Execution Time
	  */
	@Override
	public java.math.BigDecimal getWorkingTime () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WorkingTime);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Yield %.
		@param Yield 
		The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	  */
	@Override
	public void setYield (int Yield)
	{
		set_Value (COLUMNNAME_Yield, Integer.valueOf(Yield));
	}

	/** Get Yield %.
		@return The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	  */
	@Override
	public int getYield () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Yield);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}