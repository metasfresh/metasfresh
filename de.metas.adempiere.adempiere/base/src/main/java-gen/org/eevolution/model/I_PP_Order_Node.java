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
package org.eevolution.model;


/** Generated Interface for PP_Order_Node
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PP_Order_Node 
{

    /** TableName=PP_Order_Node */
    public static final String Table_Name = "PP_Order_Node";

    /** AD_Table_ID=53022 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Aktion.
	 * Indicates the Action to be performed
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAction (java.lang.String Action);

	/**
	 * Get Aktion.
	 * Indicates the Action to be performed
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAction();

    /** Column definition for Action */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_Action = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "Action", null);
    /** Column name Action */
    public static final String COLUMNNAME_Action = "Action";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Client>(I_PP_Order_Node.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Spalte.
	 * Column in the table
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Spalte.
	 * Column in the table
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Column_ID();

	public org.compiere.model.I_AD_Column getAD_Column();

	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

    /** Column definition for AD_Column_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Column>(I_PP_Order_Node.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
    /** Column name AD_Column_ID */
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/**
	 * Set Special Form.
	 * Special Form
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Form_ID (int AD_Form_ID);

	/**
	 * Get Special Form.
	 * Special Form
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Form_ID();

	public org.compiere.model.I_AD_Form getAD_Form();

	public void setAD_Form(org.compiere.model.I_AD_Form AD_Form);

    /** Column definition for AD_Form_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Form> COLUMN_AD_Form_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Form>(I_PP_Order_Node.class, "AD_Form_ID", org.compiere.model.I_AD_Form.class);
    /** Column name AD_Form_ID */
    public static final String COLUMNNAME_AD_Form_ID = "AD_Form_ID";

	/**
	 * Set Bild.
	 * Image or Icon
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Image_ID (int AD_Image_ID);

	/**
	 * Get Bild.
	 * Image or Icon
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Image_ID();

	public org.compiere.model.I_AD_Image getAD_Image();

	public void setAD_Image(org.compiere.model.I_AD_Image AD_Image);

    /** Column definition for AD_Image_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Image> COLUMN_AD_Image_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Image>(I_PP_Order_Node.class, "AD_Image_ID", org.compiere.model.I_AD_Image.class);
    /** Column name AD_Image_ID */
    public static final String COLUMNNAME_AD_Image_ID = "AD_Image_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Org>(I_PP_Order_Node.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Prozess.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Prozess.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Process_ID();

	public org.compiere.model.I_AD_Process getAD_Process();

	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process);

    /** Column definition for AD_Process_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Process>(I_PP_Order_Node.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Externer Prozess.
	 * Operation System Task
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Task_ID (int AD_Task_ID);

	/**
	 * Get Externer Prozess.
	 * Operation System Task
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Task_ID();

	public org.compiere.model.I_AD_Task getAD_Task();

	public void setAD_Task(org.compiere.model.I_AD_Task AD_Task);

    /** Column definition for AD_Task_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Task> COLUMN_AD_Task_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Task>(I_PP_Order_Node.class, "AD_Task_ID", org.compiere.model.I_AD_Task.class);
    /** Column name AD_Task_ID */
    public static final String COLUMNNAME_AD_Task_ID = "AD_Task_ID";

	/**
	 * Set Workflow Block.
	 * Workflow Transaction Execution Block
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_WF_Block_ID (int AD_WF_Block_ID);

	/**
	 * Get Workflow Block.
	 * Workflow Transaction Execution Block
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_WF_Block_ID();

	public org.compiere.model.I_AD_WF_Block getAD_WF_Block();

	public void setAD_WF_Block(org.compiere.model.I_AD_WF_Block AD_WF_Block);

    /** Column definition for AD_WF_Block_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_WF_Block> COLUMN_AD_WF_Block_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_WF_Block>(I_PP_Order_Node.class, "AD_WF_Block_ID", org.compiere.model.I_AD_WF_Block.class);
    /** Column name AD_WF_Block_ID */
    public static final String COLUMNNAME_AD_WF_Block_ID = "AD_WF_Block_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_WF_Node> COLUMN_AD_WF_Node_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_WF_Node>(I_PP_Order_Node.class, "AD_WF_Node_ID", org.compiere.model.I_AD_WF_Node.class);
    /** Column name AD_WF_Node_ID */
    public static final String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";

	/**
	 * Set Workflow - Verantwortlicher.
	 * Responsible for Workflow Execution
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID);

	/**
	 * Get Workflow - Verantwortlicher.
	 * Responsible for Workflow Execution
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_WF_Responsible_ID();

	public org.compiere.model.I_AD_WF_Responsible getAD_WF_Responsible();

	public void setAD_WF_Responsible(org.compiere.model.I_AD_WF_Responsible AD_WF_Responsible);

    /** Column definition for AD_WF_Responsible_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_WF_Responsible> COLUMN_AD_WF_Responsible_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_WF_Responsible>(I_PP_Order_Node.class, "AD_WF_Responsible_ID", org.compiere.model.I_AD_WF_Responsible.class);
    /** Column name AD_WF_Responsible_ID */
    public static final String COLUMNNAME_AD_WF_Responsible_ID = "AD_WF_Responsible_ID";

	/**
	 * Set Fenster.
	 * Data entry or display window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Window_ID (int AD_Window_ID);

	/**
	 * Get Fenster.
	 * Data entry or display window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Window_ID();

	public org.compiere.model.I_AD_Window getAD_Window();

	public void setAD_Window(org.compiere.model.I_AD_Window AD_Window);

    /** Column definition for AD_Window_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Window> COLUMN_AD_Window_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Window>(I_PP_Order_Node.class, "AD_Window_ID", org.compiere.model.I_AD_Window.class);
    /** Column name AD_Window_ID */
    public static final String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

	/**
	 * Set Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Workflow_ID (int AD_Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Workflow_ID();

	public org.compiere.model.I_AD_Workflow getAD_Workflow();

	public void setAD_Workflow(org.compiere.model.I_AD_Workflow AD_Workflow);

    /** Column definition for AD_Workflow_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Workflow> COLUMN_AD_Workflow_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Workflow>(I_PP_Order_Node.class, "AD_Workflow_ID", org.compiere.model.I_AD_Workflow.class);
    /** Column name AD_Workflow_ID */
    public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

	/**
	 * Set Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAttributeName (java.lang.String AttributeName);

	/**
	 * Get Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAttributeName();

    /** Column definition for AttributeName */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_AttributeName = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "AttributeName", null);
    /** Column name AttributeName */
    public static final String COLUMNNAME_AttributeName = "AttributeName";

	/**
	 * Set Merkmals-Wert.
	 * Value of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAttributeValue (java.lang.String AttributeValue);

	/**
	 * Get Merkmals-Wert.
	 * Value of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAttributeValue();

    /** Column definition for AttributeValue */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_AttributeValue = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "AttributeValue", null);
    /** Column name AttributeValue */
    public static final String COLUMNNAME_AttributeValue = "AttributeValue";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_C_BPartner>(I_PP_Order_Node.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Kosten.
	 * Cost information
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCost (java.math.BigDecimal Cost);

	/**
	 * Get Kosten.
	 * Cost information
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCost();

    /** Column definition for Cost */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_Cost = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "Cost", null);
    /** Column name Cost */
    public static final String COLUMNNAME_Cost = "Cost";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_User>(I_PP_Order_Node.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Projektabschluss.
	 * Finish or (planned) completion date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateFinish (java.sql.Timestamp DateFinish);

	/**
	 * Get Projektabschluss.
	 * Finish or (planned) completion date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateFinish();

    /** Column definition for DateFinish */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_DateFinish = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "DateFinish", null);
    /** Column name DateFinish */
    public static final String COLUMNNAME_DateFinish = "DateFinish";

	/**
	 * Set DateFinishSchedule.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateFinishSchedule (java.sql.Timestamp DateFinishSchedule);

	/**
	 * Get DateFinishSchedule.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateFinishSchedule();

    /** Column definition for DateFinishSchedule */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_DateFinishSchedule = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "DateFinishSchedule", null);
    /** Column name DateFinishSchedule */
    public static final String COLUMNNAME_DateFinishSchedule = "DateFinishSchedule";

	/**
	 * Set DateStart.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateStart (java.sql.Timestamp DateStart);

	/**
	 * Get DateStart.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateStart();

    /** Column definition for DateStart */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_DateStart = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "DateStart", null);
    /** Column name DateStart */
    public static final String COLUMNNAME_DateStart = "DateStart";

	/**
	 * Set DateStartSchedule.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateStartSchedule (java.sql.Timestamp DateStartSchedule);

	/**
	 * Get DateStartSchedule.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateStartSchedule();

    /** Column definition for DateStartSchedule */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_DateStartSchedule = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "DateStartSchedule", null);
    /** Column name DateStartSchedule */
    public static final String COLUMNNAME_DateStartSchedule = "DateStartSchedule";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Belegverarbeitung.
	 * The targeted status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocAction (java.lang.String DocAction);

	/**
	 * Get Belegverarbeitung.
	 * The targeted status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocAction();

    /** Column definition for DocAction */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "DocAction", null);
    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Duration.
	 * Normal Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDuration (int Duration);

	/**
	 * Get Duration.
	 * Normal Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDuration();

    /** Column definition for Duration */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_Duration = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "Duration", null);
    /** Column name Duration */
    public static final String COLUMNNAME_Duration = "Duration";

	/**
	 * Set Duration before close.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDurationBeforeClose (int DurationBeforeClose);

	/**
	 * Get Duration before close.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDurationBeforeClose();

    /** Column definition for DurationBeforeClose */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_DurationBeforeClose = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "DurationBeforeClose", null);
    /** Column name DurationBeforeClose */
    public static final String COLUMNNAME_DurationBeforeClose = "DurationBeforeClose";

	/**
	 * Set Duration Limit.
	 * Maximum Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDurationLimit (int DurationLimit);

	/**
	 * Get Duration Limit.
	 * Maximum Duration in Duration Unit
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDurationLimit();

    /** Column definition for DurationLimit */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_DurationLimit = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "DurationLimit", null);
    /** Column name DurationLimit */
    public static final String COLUMNNAME_DurationLimit = "DurationLimit";

	/**
	 * Set Duration Real.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDurationReal (int DurationReal);

	/**
	 * Get Duration Real.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDurationReal();

    /** Column definition for DurationReal */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_DurationReal = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "DurationReal", null);
    /** Column name DurationReal */
    public static final String COLUMNNAME_DurationReal = "DurationReal";

	/**
	 * Set Duration Requiered.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDurationRequiered (int DurationRequiered);

	/**
	 * Get Duration Requiered.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDurationRequiered();

    /** Column definition for DurationRequiered */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_DurationRequiered = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "DurationRequiered", null);
    /** Column name DurationRequiered */
    public static final String COLUMNNAME_DurationRequiered = "DurationRequiered";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Finish Mode.
	 * Workflow Activity Finish Mode
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFinishMode (java.lang.String FinishMode);

	/**
	 * Get Finish Mode.
	 * Workflow Activity Finish Mode
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFinishMode();

    /** Column definition for FinishMode */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_FinishMode = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "FinishMode", null);
    /** Column name FinishMode */
    public static final String COLUMNNAME_FinishMode = "FinishMode";

	/**
	 * Set Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Zentral verwaltet.
	 * Information maintained in System Element table
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCentrallyMaintained (boolean IsCentrallyMaintained);

	/**
	 * Get Zentral verwaltet.
	 * Information maintained in System Element table
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCentrallyMaintained();

    /** Column definition for IsCentrallyMaintained */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_IsCentrallyMaintained = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "IsCentrallyMaintained", null);
    /** Column name IsCentrallyMaintained */
    public static final String COLUMNNAME_IsCentrallyMaintained = "IsCentrallyMaintained";

	/**
	 * Set Is Milestone.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsMilestone (boolean IsMilestone);

	/**
	 * Get Is Milestone.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isMilestone();

    /** Column definition for IsMilestone */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_IsMilestone = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "IsMilestone", null);
    /** Column name IsMilestone */
    public static final String COLUMNNAME_IsMilestone = "IsMilestone";

	/**
	 * Set Is Subcontracting.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsSubcontracting (boolean IsSubcontracting);

	/**
	 * Get Is Subcontracting.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isSubcontracting();

    /** Column definition for IsSubcontracting */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_IsSubcontracting = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "IsSubcontracting", null);
    /** Column name IsSubcontracting */
    public static final String COLUMNNAME_IsSubcontracting = "IsSubcontracting";

	/**
	 * Set Join Element.
	 * Semantics for multiple incoming Transitions
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setJoinElement (java.lang.String JoinElement);

	/**
	 * Get Join Element.
	 * Semantics for multiple incoming Transitions
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getJoinElement();

    /** Column definition for JoinElement */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_JoinElement = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "JoinElement", null);
    /** Column name JoinElement */
    public static final String COLUMNNAME_JoinElement = "JoinElement";

	/**
	 * Set Moving Time.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMovingTime (int MovingTime);

	/**
	 * Get Moving Time.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMovingTime();

    /** Column definition for MovingTime */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_MovingTime = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "MovingTime", null);
    /** Column name MovingTime */
    public static final String COLUMNNAME_MovingTime = "MovingTime";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Overlap Units.
	 * Overlap Units are number of units that must be completed before they are moved the next activity
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOverlapUnits (int OverlapUnits);

	/**
	 * Get Overlap Units.
	 * Overlap Units are number of units that must be completed before they are moved the next activity
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getOverlapUnits();

    /** Column definition for OverlapUnits */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_OverlapUnits = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "OverlapUnits", null);
    /** Column name OverlapUnits */
    public static final String COLUMNNAME_OverlapUnits = "OverlapUnits";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.eevolution.model.I_PP_Order>(I_PP_Order_Node.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
    /** Column name PP_Order_ID */
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/**
	 * Set Manufacturing Order Activity.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_Node_ID (int PP_Order_Node_ID);

	/**
	 * Get Manufacturing Order Activity.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_Node_ID();

    /** Column definition for PP_Order_Node_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_PP_Order_Node_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "PP_Order_Node_ID", null);
    /** Column name PP_Order_Node_ID */
    public static final String COLUMNNAME_PP_Order_Node_ID = "PP_Order_Node_ID";

	/**
	 * Set Manufacturing Order Workflow.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Order_Workflow_ID (int PP_Order_Workflow_ID);

	/**
	 * Get Manufacturing Order Workflow.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Order_Workflow_ID();

	public org.eevolution.model.I_PP_Order_Workflow getPP_Order_Workflow();

	public void setPP_Order_Workflow(org.eevolution.model.I_PP_Order_Workflow PP_Order_Workflow);

    /** Column definition for PP_Order_Workflow_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.eevolution.model.I_PP_Order_Workflow> COLUMN_PP_Order_Workflow_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.eevolution.model.I_PP_Order_Workflow>(I_PP_Order_Node.class, "PP_Order_Workflow_ID", org.eevolution.model.I_PP_Order_Workflow.class);
    /** Column name PP_Order_Workflow_ID */
    public static final String COLUMNNAME_PP_Order_Workflow_ID = "PP_Order_Workflow_ID";

	/**
	 * Set Priorität.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPriority (int Priority);

	/**
	 * Get Priorität.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPriority();

    /** Column definition for Priority */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_Priority = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "Priority", null);
    /** Column name Priority */
    public static final String COLUMNNAME_Priority = "Priority";

	/**
	 * Set Qty before close.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyBeforeClose (java.math.BigDecimal QtyBeforeClose);

	/**
	 * Get Qty before close.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyBeforeClose();

    /** Column definition for QtyBeforeClose */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_QtyBeforeClose = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "QtyBeforeClose", null);
    /** Column name QtyBeforeClose */
    public static final String COLUMNNAME_QtyBeforeClose = "QtyBeforeClose";

	/**
	 * Set Gelieferte Menge.
	 * Delivered Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered);

	/**
	 * Get Gelieferte Menge.
	 * Delivered Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDelivered();

    /** Column definition for QtyDelivered */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_QtyDelivered = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "QtyDelivered", null);
    /** Column name QtyDelivered */
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Qty Reject.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyReject (java.math.BigDecimal QtyReject);

	/**
	 * Get Qty Reject.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyReject();

    /** Column definition for QtyReject */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_QtyReject = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "QtyReject", null);
    /** Column name QtyReject */
    public static final String COLUMNNAME_QtyReject = "QtyReject";

	/**
	 * Set Qty Requiered.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyRequiered (java.math.BigDecimal QtyRequiered);

	/**
	 * Get Qty Requiered.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyRequiered();

    /** Column definition for QtyRequiered */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_QtyRequiered = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "QtyRequiered", null);
    /** Column name QtyRequiered */
    public static final String COLUMNNAME_QtyRequiered = "QtyRequiered";

	/**
	 * Set QtyScrap.
	 * Scrap Quantity for this componet
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyScrap (java.math.BigDecimal QtyScrap);

	/**
	 * Get QtyScrap.
	 * Scrap Quantity for this componet
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyScrap();

    /** Column definition for QtyScrap */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_QtyScrap = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "QtyScrap", null);
    /** Column name QtyScrap */
    public static final String COLUMNNAME_QtyScrap = "QtyScrap";

	/**
	 * Set Queuing Time.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQueuingTime (int QueuingTime);

	/**
	 * Get Queuing Time.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getQueuingTime();

    /** Column definition for QueuingTime */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_QueuingTime = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "QueuingTime", null);
    /** Column name QueuingTime */
    public static final String COLUMNNAME_QueuingTime = "QueuingTime";

	/**
	 * Set Ressource.
	 * Resource
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Ressource.
	 * Resource
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getS_Resource_ID();

	public org.compiere.model.I_S_Resource getS_Resource();

	public void setS_Resource(org.compiere.model.I_S_Resource S_Resource);

    /** Column definition for S_Resource_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_S_Resource> COLUMN_S_Resource_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_S_Resource>(I_PP_Order_Node.class, "S_Resource_ID", org.compiere.model.I_S_Resource.class);
    /** Column name S_Resource_ID */
    public static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/**
	 * Set Setup Time.
	 * Setup time before starting Production
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSetupTime (int SetupTime);

	/**
	 * Get Setup Time.
	 * Setup time before starting Production
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSetupTime();

    /** Column definition for SetupTime */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_SetupTime = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "SetupTime", null);
    /** Column name SetupTime */
    public static final String COLUMNNAME_SetupTime = "SetupTime";

	/**
	 * Set Setup Time Real.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSetupTimeReal (int SetupTimeReal);

	/**
	 * Get Setup Time Real.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSetupTimeReal();

    /** Column definition for SetupTimeReal */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_SetupTimeReal = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "SetupTimeReal", null);
    /** Column name SetupTimeReal */
    public static final String COLUMNNAME_SetupTimeReal = "SetupTimeReal";

	/**
	 * Set Setup Time Requiered.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSetupTimeRequiered (int SetupTimeRequiered);

	/**
	 * Get Setup Time Requiered.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSetupTimeRequiered();

    /** Column definition for SetupTimeRequiered */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_SetupTimeRequiered = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "SetupTimeRequiered", null);
    /** Column name SetupTimeRequiered */
    public static final String COLUMNNAME_SetupTimeRequiered = "SetupTimeRequiered";

	/**
	 * Set Split Element.
	 * Semantics for multiple outgoing Transitions
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSplitElement (java.lang.String SplitElement);

	/**
	 * Get Split Element.
	 * Semantics for multiple outgoing Transitions
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSplitElement();

    /** Column definition for SplitElement */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_SplitElement = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "SplitElement", null);
    /** Column name SplitElement */
    public static final String COLUMNNAME_SplitElement = "SplitElement";

	/**
	 * Set Start Mode.
	 * Workflow Activity Start Mode
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStartMode (java.lang.String StartMode);

	/**
	 * Get Start Mode.
	 * Workflow Activity Start Mode
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStartMode();

    /** Column definition for StartMode */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_StartMode = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "StartMode", null);
    /** Column name StartMode */
    public static final String COLUMNNAME_StartMode = "StartMode";

	/**
	 * Set Subflow Execution.
	 * Mode how the sub-workflow is executed
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSubflowExecution (java.lang.String SubflowExecution);

	/**
	 * Get Subflow Execution.
	 * Mode how the sub-workflow is executed
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSubflowExecution();

    /** Column definition for SubflowExecution */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_SubflowExecution = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "SubflowExecution", null);
    /** Column name SubflowExecution */
    public static final String COLUMNNAME_SubflowExecution = "SubflowExecution";

	/**
	 * Set Units by Cycles.
	 * The Units by Cycles are defined for process type  Flow Repetitive Dedicated and  indicated the product to be manufactured on a production line for duration unit.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUnitsCycles (int UnitsCycles);

	/**
	 * Get Units by Cycles.
	 * The Units by Cycles are defined for process type  Flow Repetitive Dedicated and  indicated the product to be manufactured on a production line for duration unit.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUnitsCycles();

    /** Column definition for UnitsCycles */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_UnitsCycles = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "UnitsCycles", null);
    /** Column name UnitsCycles */
    public static final String COLUMNNAME_UnitsCycles = "UnitsCycles";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_User>(I_PP_Order_Node.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Gültig ab.
	 * Valid from including this date (first day)
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Gültig ab.
	 * Valid from including this date (first day)
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Gültig bis.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidTo (java.sql.Timestamp ValidTo);

	/**
	 * Get Gültig bis.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidTo();

    /** Column definition for ValidTo */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_ValidTo = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "ValidTo", null);
    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

	/**
	 * Set Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/**
	 * Set Waiting Time.
	 * Workflow Simulation Waiting time
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWaitingTime (int WaitingTime);

	/**
	 * Get Waiting Time.
	 * Workflow Simulation Waiting time
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getWaitingTime();

    /** Column definition for WaitingTime */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_WaitingTime = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "WaitingTime", null);
    /** Column name WaitingTime */
    public static final String COLUMNNAME_WaitingTime = "WaitingTime";

	/**
	 * Set Workflow.
	 * Workflow or tasks
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWorkflow_ID (int Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or tasks
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getWorkflow_ID();

	public org.compiere.model.I_AD_Workflow getWorkflow();

	public void setWorkflow(org.compiere.model.I_AD_Workflow Workflow);

    /** Column definition for Workflow_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Workflow> COLUMN_Workflow_ID = new org.adempiere.model.ModelColumn<I_PP_Order_Node, org.compiere.model.I_AD_Workflow>(I_PP_Order_Node.class, "Workflow_ID", org.compiere.model.I_AD_Workflow.class);
    /** Column name Workflow_ID */
    public static final String COLUMNNAME_Workflow_ID = "Workflow_ID";

	/**
	 * Set Working Time.
	 * Workflow Simulation Execution Time
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWorkingTime (int WorkingTime);

	/**
	 * Get Working Time.
	 * Workflow Simulation Execution Time
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getWorkingTime();

    /** Column definition for WorkingTime */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_WorkingTime = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "WorkingTime", null);
    /** Column name WorkingTime */
    public static final String COLUMNNAME_WorkingTime = "WorkingTime";

	/**
	 * Set X Position.
	 * Absolute X (horizontal) position in 1/72 of an inch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setXPosition (int XPosition);

	/**
	 * Get X Position.
	 * Absolute X (horizontal) position in 1/72 of an inch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getXPosition();

    /** Column definition for XPosition */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_XPosition = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "XPosition", null);
    /** Column name XPosition */
    public static final String COLUMNNAME_XPosition = "XPosition";

	/**
	 * Set Yield %.
	 * The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setYield (int Yield);

	/**
	 * Get Yield %.
	 * The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getYield();

    /** Column definition for Yield */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_Yield = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "Yield", null);
    /** Column name Yield */
    public static final String COLUMNNAME_Yield = "Yield";

	/**
	 * Set Y Position.
	 * Absolute Y (vertical) position in 1/72 of an inch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setYPosition (int YPosition);

	/**
	 * Get Y Position.
	 * Absolute Y (vertical) position in 1/72 of an inch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getYPosition();

    /** Column definition for YPosition */
    public static final org.adempiere.model.ModelColumn<I_PP_Order_Node, Object> COLUMN_YPosition = new org.adempiere.model.ModelColumn<I_PP_Order_Node, Object>(I_PP_Order_Node.class, "YPosition", null);
    /** Column name YPosition */
    public static final String COLUMNNAME_YPosition = "YPosition";
}
