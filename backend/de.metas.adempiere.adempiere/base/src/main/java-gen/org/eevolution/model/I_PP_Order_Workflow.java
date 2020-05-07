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


/** Generated Interface for PP_Order_Workflow
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PP_Order_Workflow 
{

    /** TableName=PP_Order_Workflow */
    public static final String Table_Name = "PP_Order_Workflow";

    /** AD_Table_ID=53029 */
    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AccessLevel */
    public static final String COLUMNNAME_AccessLevel = "AccessLevel";

	/** Set Berechtigungsstufe.
	  * Access Level required
	  */
	public void setAccessLevel (java.lang.String AccessLevel);

	/** Get Berechtigungsstufe.
	  * Access Level required
	  */
	public java.lang.String getAccessLevel();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set DB-Tabelle.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get DB-Tabelle.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException;

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column name AD_WF_Node_ID */
    public static final String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";

	/** Set Knoten.
	  * Workflow Node (activity), step or process
	  */
	public void setAD_WF_Node_ID (int AD_WF_Node_ID);

	/** Get Knoten.
	  * Workflow Node (activity), step or process
	  */
	public int getAD_WF_Node_ID();

	public org.compiere.model.I_AD_WF_Node getAD_WF_Node() throws RuntimeException;

	public void setAD_WF_Node(org.compiere.model.I_AD_WF_Node AD_WF_Node);

    /** Column name AD_WF_Responsible_ID */
    public static final String COLUMNNAME_AD_WF_Responsible_ID = "AD_WF_Responsible_ID";

	/** Set Workflow - Verantwortlicher.
	  * Responsible for Workflow Execution
	  */
	public void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID);

	/** Get Workflow - Verantwortlicher.
	  * Responsible for Workflow Execution
	  */
	public int getAD_WF_Responsible_ID();

	public org.compiere.model.I_AD_WF_Responsible getAD_WF_Responsible() throws RuntimeException;

	public void setAD_WF_Responsible(org.compiere.model.I_AD_WF_Responsible AD_WF_Responsible);

    /** Column name AD_Workflow_ID */
    public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

	/** Set Workflow.
	  * Workflow or combination of tasks
	  */
	public void setAD_Workflow_ID (int AD_Workflow_ID);

	/** Get Workflow.
	  * Workflow or combination of tasks
	  */
	public int getAD_Workflow_ID();

	public org.compiere.model.I_AD_Workflow getAD_Workflow() throws RuntimeException;

	public void setAD_Workflow(org.compiere.model.I_AD_Workflow AD_Workflow);

    /** Column name AD_WorkflowProcessor_ID */
    public static final String COLUMNNAME_AD_WorkflowProcessor_ID = "AD_WorkflowProcessor_ID";

	/** Set Workflow - Prozessor.
	  * Workflow Processor Server
	  */
	public void setAD_WorkflowProcessor_ID (int AD_WorkflowProcessor_ID);

	/** Get Workflow - Prozessor.
	  * Workflow Processor Server
	  */
	public int getAD_WorkflowProcessor_ID();

	public org.compiere.model.I_AD_WorkflowProcessor getAD_WorkflowProcessor() throws RuntimeException;

	public void setAD_WorkflowProcessor(org.compiere.model.I_AD_WorkflowProcessor AD_WorkflowProcessor);

    /** Column name Author */
    public static final String COLUMNNAME_Author = "Author";

	/** Set Author.
	  * Author/Creator of the Entity
	  */
	public void setAuthor (java.lang.String Author);

	/** Get Author.
	  * Author/Creator of the Entity
	  */
	public java.lang.String getAuthor();

    /** Column name Cost */
    public static final String COLUMNNAME_Cost = "Cost";

	/** Set Kosten.
	  * Cost information
	  */
	public void setCost (java.math.BigDecimal Cost);

	/** Get Kosten.
	  * Cost information
	  */
	public java.math.BigDecimal getCost();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Date this record was created
	  */
	public java.sql.Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Erstellt durch.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Beschreibung	  */
	public void setDescription (java.lang.String Description);

	/** Get Beschreibung	  */
	public java.lang.String getDescription();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Beleg Nr..
	  * Document sequence number of the document
	  */
	public void setDocumentNo (java.lang.String DocumentNo);

	/** Get Beleg Nr..
	  * Document sequence number of the document
	  */
	public java.lang.String getDocumentNo();

    /** Column name Duration */
    public static final String COLUMNNAME_Duration = "Duration";

	/** Set Duration.
	  * Normal Duration in Duration Unit
	  */
	public void setDuration (int Duration);

	/** Get Duration.
	  * Normal Duration in Duration Unit
	  */
	public int getDuration();

    /** Column name DurationLimit */
    public static final String COLUMNNAME_DurationLimit = "DurationLimit";

	/** Set Duration Limit.
	  * Maximum Duration in Duration Unit
	  */
	public void setDurationLimit (int DurationLimit);

	/** Get Duration Limit.
	  * Maximum Duration in Duration Unit
	  */
	public int getDurationLimit();

    /** Column name DurationUnit */
    public static final String COLUMNNAME_DurationUnit = "DurationUnit";

	/** Set Duration Unit.
	  * Unit of Duration
	  */
	public void setDurationUnit (java.lang.String DurationUnit);

	/** Get Duration Unit.
	  * Unit of Duration
	  */
	public java.lang.String getDurationUnit();

    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/** Set Entitäts-Art.
	  * Dictionary Entity Type;
 Determines ownership and synchronization
	  */
	public void setEntityType (java.lang.String EntityType);

	/** Get Entitäts-Art.
	  * Dictionary Entity Type;
 Determines ownership and synchronization
	  */
	public java.lang.String getEntityType();

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Kommentar/Hilfe.
	  * Comment or Hint
	  */
	public void setHelp (java.lang.String Help);

	/** Get Kommentar/Hilfe.
	  * Comment or Hint
	  */
	public java.lang.String getHelp();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Aktiv.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/** Set Standard.
	  * Default value
	  */
	public void setIsDefault (boolean IsDefault);

	/** Get Standard.
	  * Default value
	  */
	public boolean isDefault();

    /** Column name MovingTime */
    public static final String COLUMNNAME_MovingTime = "MovingTime";

	/** Set Moving Time	  */
	public void setMovingTime (int MovingTime);

	/** Get Moving Time	  */
	public int getMovingTime();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (java.lang.String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public java.lang.String getName();

    /** Column name OverlapUnits */
    public static final String COLUMNNAME_OverlapUnits = "OverlapUnits";

	/** Set Overlap Units.
	  * Overlap Units are number of units that must be completed before they are moved the next activity
	  */
	public void setOverlapUnits (java.math.BigDecimal OverlapUnits);

	/** Get Overlap Units.
	  * Overlap Units are number of units that must be completed before they are moved the next activity
	  */
	public java.math.BigDecimal getOverlapUnits();

    /** Column name PP_Order_ID */
    public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/** Set Produktionsauftrag	  */
	public void setPP_Order_ID (int PP_Order_ID);

	/** Get Produktionsauftrag	  */
	public int getPP_Order_ID();

	public org.eevolution.model.I_PP_Order getPP_Order() throws RuntimeException;

	public void setPP_Order(org.eevolution.model.I_PP_Order PP_Order);

    /** Column name PP_Order_Node_ID */
    public static final String COLUMNNAME_PP_Order_Node_ID = "PP_Order_Node_ID";

	/** Set Manufacturing Order Activity.
	  * Workflow Node (activity), step or process
	  */
	public void setPP_Order_Node_ID (int PP_Order_Node_ID);

	/** Get Manufacturing Order Activity.
	  * Workflow Node (activity), step or process
	  */
	public int getPP_Order_Node_ID();

	public org.eevolution.model.I_PP_Order_Node getPP_Order_Node() throws RuntimeException;

	public void setPP_Order_Node(org.eevolution.model.I_PP_Order_Node PP_Order_Node);

    /** Column name PP_Order_Workflow_ID */
    public static final String COLUMNNAME_PP_Order_Workflow_ID = "PP_Order_Workflow_ID";

	/** Set Manufacturing Order Workflow	  */
	public void setPP_Order_Workflow_ID (int PP_Order_Workflow_ID);

	/** Get Manufacturing Order Workflow	  */
	public int getPP_Order_Workflow_ID();

    /** Column name Priority */
    public static final String COLUMNNAME_Priority = "Priority";

	/** Set Priorität.
	  * Indicates if this request is of a high, medium or low priority.
	  */
	public void setPriority (int Priority);

	/** Get Priorität.
	  * Indicates if this request is of a high, medium or low priority.
	  */
	public int getPriority();

    /** Column name ProcessType */
    public static final String COLUMNNAME_ProcessType = "ProcessType";

	/** Set Process Type	  */
	public void setProcessType (java.lang.String ProcessType);

	/** Get Process Type	  */
	public java.lang.String getProcessType();

    /** Column name PublishStatus */
    public static final String COLUMNNAME_PublishStatus = "PublishStatus";

	/** Set Publication Status.
	  * Status of Publication
	  */
	public void setPublishStatus (java.lang.String PublishStatus);

	/** Get Publication Status.
	  * Status of Publication
	  */
	public java.lang.String getPublishStatus();

    /** Column name QtyBatchSize */
    public static final String COLUMNNAME_QtyBatchSize = "QtyBatchSize";

	/** Set Qty Batch Size	  */
	public void setQtyBatchSize (java.math.BigDecimal QtyBatchSize);

	/** Get Qty Batch Size	  */
	public java.math.BigDecimal getQtyBatchSize();

    /** Column name QueuingTime */
    public static final String COLUMNNAME_QueuingTime = "QueuingTime";

	/** Set Queuing Time	  */
	public void setQueuingTime (int QueuingTime);

	/** Get Queuing Time	  */
	public int getQueuingTime();

    /** Column name S_Resource_ID */
    public static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/** Set Ressource.
	  * Resource
	  */
	public void setS_Resource_ID (int S_Resource_ID);

	/** Get Ressource.
	  * Resource
	  */
	public int getS_Resource_ID();

	public org.compiere.model.I_S_Resource getS_Resource() throws RuntimeException;

	public void setS_Resource(org.compiere.model.I_S_Resource S_Resource);

    /** Column name SetupTime */
    public static final String COLUMNNAME_SetupTime = "SetupTime";

	/** Set Setup Time.
	  * Setup time before starting Production
	  */
	public void setSetupTime (int SetupTime);

	/** Get Setup Time.
	  * Setup time before starting Production
	  */
	public int getSetupTime();

    /** Column name UnitsCycles */
    public static final String COLUMNNAME_UnitsCycles = "UnitsCycles";

	/** Set Units by Cycles.
	  * The Units by Cycles are defined for process type  Flow Repetitive Dedicated and  indicated the product to be manufactured on a production line for duration unit.
	  */
	public void setUnitsCycles (java.math.BigDecimal UnitsCycles);

	/** Get Units by Cycles.
	  * The Units by Cycles are defined for process type  Flow Repetitive Dedicated and  indicated the product to be manufactured on a production line for duration unit.
	  */
	public java.math.BigDecimal getUnitsCycles();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Date this record was updated
	  */
	public java.sql.Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name ValidateWorkflow */
    public static final String COLUMNNAME_ValidateWorkflow = "ValidateWorkflow";

	/** Set Workflow validieren	  */
	public void setValidateWorkflow (java.lang.String ValidateWorkflow);

	/** Get Workflow validieren	  */
	public java.lang.String getValidateWorkflow();

    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/** Set Gültig ab.
	  * Valid from including this date (first day)
	  */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/** Get Gültig ab.
	  * Valid from including this date (first day)
	  */
	public java.sql.Timestamp getValidFrom();

    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

	/** Set Gültig bis.
	  * Valid to including this date (last day)
	  */
	public void setValidTo (java.sql.Timestamp ValidTo);

	/** Get Gültig bis.
	  * Valid to including this date (last day)
	  */
	public java.sql.Timestamp getValidTo();

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Suchschlüssel.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (java.lang.String Value);

	/** Get Suchschlüssel.
	  * Search key for the record in the format required - must be unique
	  */
	public java.lang.String getValue();

    /** Column name Version */
    public static final String COLUMNNAME_Version = "Version";

	/** Set Version.
	  * Version of the table definition
	  */
	public void setVersion (int Version);

	/** Get Version.
	  * Version of the table definition
	  */
	public int getVersion();

    /** Column name WaitingTime */
    public static final String COLUMNNAME_WaitingTime = "WaitingTime";

	/** Set Waiting Time.
	  * Workflow Simulation Waiting time
	  */
	public void setWaitingTime (int WaitingTime);

	/** Get Waiting Time.
	  * Workflow Simulation Waiting time
	  */
	public int getWaitingTime();

    /** Column name WorkflowType */
    public static final String COLUMNNAME_WorkflowType = "WorkflowType";

	/** Set Workflow Type.
	  * Type of Worflow
	  */
	public void setWorkflowType (java.lang.String WorkflowType);

	/** Get Workflow Type.
	  * Type of Worflow
	  */
	public java.lang.String getWorkflowType();

    /** Column name WorkingTime */
    public static final String COLUMNNAME_WorkingTime = "WorkingTime";

	/** Set Working Time.
	  * Workflow Simulation Execution Time
	  */
	public void setWorkingTime (int WorkingTime);

	/** Get Working Time.
	  * Workflow Simulation Execution Time
	  */
	public int getWorkingTime();

    /** Column name Yield */
    public static final String COLUMNNAME_Yield = "Yield";

	/** Set Yield %.
	  * The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	  */
	public void setYield (int Yield);

	/** Get Yield %.
	  * The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	  */
	public int getYield();
}
