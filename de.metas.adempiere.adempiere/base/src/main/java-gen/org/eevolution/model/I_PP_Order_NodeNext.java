package org.eevolution.model;


/** Generated Interface for PP_Order_NodeNext
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PP_Order_NodeNext 
{

    /** TableName=PP_Order_NodeNext */
    public static final String Table_Name = "PP_Order_NodeNext";

    /** AD_Table_ID=53023 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, org.compiere.model.I_AD_Client>(I_PP_Order_NodeNext.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, org.compiere.model.I_AD_Org>(I_PP_Order_NodeNext.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Nächster Knoten.
	 * Next Node in workflow
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_WF_Next_ID (int AD_WF_Next_ID);

	/**
	 * Get Nächster Knoten.
	 * Next Node in workflow
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_WF_Next_ID();

	public org.compiere.model.I_AD_WF_Node getAD_WF_Next();

	public void setAD_WF_Next(org.compiere.model.I_AD_WF_Node AD_WF_Next);

    /** Column definition for AD_WF_Next_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, org.compiere.model.I_AD_WF_Node> COLUMN_AD_WF_Next_ID = new org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, org.compiere.model.I_AD_WF_Node>(I_PP_Order_NodeNext.class, "AD_WF_Next_ID", org.compiere.model.I_AD_WF_Node.class);
    /** Column name AD_WF_Next_ID */
    public static final String COLUMNNAME_AD_WF_Next_ID = "AD_WF_Next_ID";

	/**
	 * Set Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_WF_Node_ID (int AD_WF_Node_ID);

	/**
	 * Get Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_WF_Node_ID();

	public org.compiere.model.I_AD_WF_Node getAD_WF_Node();

	public void setAD_WF_Node(org.compiere.model.I_AD_WF_Node AD_WF_Node);

    /** Column definition for AD_WF_Node_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, org.compiere.model.I_AD_WF_Node> COLUMN_AD_WF_Node_ID = new org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, org.compiere.model.I_AD_WF_Node>(I_PP_Order_NodeNext.class, "AD_WF_Node_ID", org.compiere.model.I_AD_WF_Node.class);
    /** Column name AD_WF_Node_ID */
    public static final String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, Object>(I_PP_Order_NodeNext.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, org.compiere.model.I_AD_User>(I_PP_Order_NodeNext.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, Object>(I_PP_Order_NodeNext.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEntityType();

    /** Column definition for EntityType */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, Object>(I_PP_Order_NodeNext.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, Object>(I_PP_Order_NodeNext.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Std User Workflow.
	 * Standard Manual User Approval Workflow
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsStdUserWorkflow (boolean IsStdUserWorkflow);

	/**
	 * Get Std User Workflow.
	 * Standard Manual User Approval Workflow
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isStdUserWorkflow();

    /** Column definition for IsStdUserWorkflow */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, Object> COLUMN_IsStdUserWorkflow = new org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, Object>(I_PP_Order_NodeNext.class, "IsStdUserWorkflow", null);
    /** Column name IsStdUserWorkflow */
    public static final String COLUMNNAME_IsStdUserWorkflow = "IsStdUserWorkflow";

	/**
	 * Set Produktionsauftrag.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_ID (int PP_Order_ID);

	/**
	 * Get Produktionsauftrag.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_ID();

	public org.eevolution.model.I_PP_Order getPP_Order();

	public void setPP_Order(org.eevolution.model.I_PP_Order PP_Order);

    /** Column definition for PP_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, org.eevolution.model.I_PP_Order>(I_PP_Order_NodeNext.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
    /** Column name PP_Order_ID */
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/**
	 * Set Manufacturing Order Activity Next.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_Next_ID (int PP_Order_Next_ID);

	/**
	 * Get Manufacturing Order Activity Next.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_Next_ID();

	public org.eevolution.model.I_PP_Order_Node getPP_Order_Next();

	public void setPP_Order_Next(org.eevolution.model.I_PP_Order_Node PP_Order_Next);

    /** Column definition for PP_Order_Next_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, org.eevolution.model.I_PP_Order_Node> COLUMN_PP_Order_Next_ID = new org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, org.eevolution.model.I_PP_Order_Node>(I_PP_Order_NodeNext.class, "PP_Order_Next_ID", org.eevolution.model.I_PP_Order_Node.class);
    /** Column name PP_Order_Next_ID */
    public static final String COLUMNNAME_PP_Order_Next_ID = "PP_Order_Next_ID";

	/**
	 * Set Manufacturing Order Activity.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_Node_ID (int PP_Order_Node_ID);

	/**
	 * Get Manufacturing Order Activity.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_Node_ID();

	public org.eevolution.model.I_PP_Order_Node getPP_Order_Node();

	public void setPP_Order_Node(org.eevolution.model.I_PP_Order_Node PP_Order_Node);

    /** Column definition for PP_Order_Node_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, org.eevolution.model.I_PP_Order_Node> COLUMN_PP_Order_Node_ID = new org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, org.eevolution.model.I_PP_Order_Node>(I_PP_Order_NodeNext.class, "PP_Order_Node_ID", org.eevolution.model.I_PP_Order_Node.class);
    /** Column name PP_Order_Node_ID */
    public static final String COLUMNNAME_PP_Order_Node_ID = "PP_Order_Node_ID";

	/**
	 * Set Manufacturing Order Activity Next.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_NodeNext_ID (int PP_Order_NodeNext_ID);

	/**
	 * Get Manufacturing Order Activity Next.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_NodeNext_ID();

    /** Column definition for PP_Order_NodeNext_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, Object> COLUMN_PP_Order_NodeNext_ID = new org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, Object>(I_PP_Order_NodeNext.class, "PP_Order_NodeNext_ID", null);
    /** Column name PP_Order_NodeNext_ID */
    public static final String COLUMNNAME_PP_Order_NodeNext_ID = "PP_Order_NodeNext_ID";

	/**
	 * Set Reihenfolge.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, Object>(I_PP_Order_NodeNext.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Transition Code.
	 * Code resulting in TRUE of FALSE
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTransitionCode (java.lang.String TransitionCode);

	/**
	 * Get Transition Code.
	 * Code resulting in TRUE of FALSE
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTransitionCode();

    /** Column definition for TransitionCode */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, Object> COLUMN_TransitionCode = new org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, Object>(I_PP_Order_NodeNext.class, "TransitionCode", null);
    /** Column name TransitionCode */
    public static final String COLUMNNAME_TransitionCode = "TransitionCode";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, Object>(I_PP_Order_NodeNext.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_PP_Order_NodeNext, org.compiere.model.I_AD_User>(I_PP_Order_NodeNext.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
