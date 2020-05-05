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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for PA_Goal
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_PA_Goal 
{

    /** TableName=PA_Goal */
    public static final String Table_Name = "PA_Goal";

    /** AD_Table_ID=440 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(6);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AD_Role_ID */
    public static final String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/** Set Role.
	  * Responsibility Role
	  */
	public void setAD_Role_ID (int AD_Role_ID);

	/** Get Role.
	  * Responsibility Role
	  */
	public int getAD_Role_ID();

	public I_AD_Role getAD_Role() throws RuntimeException;

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public I_AD_User getAD_User() throws RuntimeException;

    /** Column name ChartType */
    public static final String COLUMNNAME_ChartType = "ChartType";

	/** Set Chart Type.
	  * Type fo chart to render
	  */
	public void setChartType (String ChartType);

	/** Get Chart Type.
	  * Type fo chart to render
	  */
	public String getChartType();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DateFrom */
    public static final String COLUMNNAME_DateFrom = "DateFrom";

	/** Set Date From.
	  * Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom);

	/** Get Date From.
	  * Starting date for a range
	  */
	public Timestamp getDateFrom();

    /** Column name DateLastRun */
    public static final String COLUMNNAME_DateLastRun = "DateLastRun";

	/** Set Date last run.
	  * Date the process was last run.
	  */
	public void setDateLastRun (Timestamp DateLastRun);

	/** Get Date last run.
	  * Date the process was last run.
	  */
	public Timestamp getDateLastRun();

    /** Column name DateTo */
    public static final String COLUMNNAME_DateTo = "DateTo";

	/** Set Date To.
	  * End date of a date range
	  */
	public void setDateTo (Timestamp DateTo);

	/** Get Date To.
	  * End date of a date range
	  */
	public Timestamp getDateTo();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name GoalPerformance */
    public static final String COLUMNNAME_GoalPerformance = "GoalPerformance";

	/** Set Performance Goal.
	  * Target achievement from 0..1
	  */
	public void setGoalPerformance (BigDecimal GoalPerformance);

	/** Get Performance Goal.
	  * Target achievement from 0..1
	  */
	public BigDecimal getGoalPerformance();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsSummary */
    public static final String COLUMNNAME_IsSummary = "IsSummary";

	/** Set Summary Level.
	  * This is a summary entity
	  */
	public void setIsSummary (boolean IsSummary);

	/** Get Summary Level.
	  * This is a summary entity
	  */
	public boolean isSummary();

    /** Column name MeasureActual */
    public static final String COLUMNNAME_MeasureActual = "MeasureActual";

	/** Set Measure Actual.
	  * Actual value that has been measured.
	  */
	public void setMeasureActual (BigDecimal MeasureActual);

	/** Get Measure Actual.
	  * Actual value that has been measured.
	  */
	public BigDecimal getMeasureActual();

    /** Column name MeasureDisplay */
    public static final String COLUMNNAME_MeasureDisplay = "MeasureDisplay";

	/** Set Measure Display.
	  * Measure Scope initially displayed
	  */
	public void setMeasureDisplay (String MeasureDisplay);

	/** Get Measure Display.
	  * Measure Scope initially displayed
	  */
	public String getMeasureDisplay();

    /** Column name MeasureScope */
    public static final String COLUMNNAME_MeasureScope = "MeasureScope";

	/** Set Measure Scope.
	  * Performance Measure Scope
	  */
	public void setMeasureScope (String MeasureScope);

	/** Get Measure Scope.
	  * Performance Measure Scope
	  */
	public String getMeasureScope();

    /** Column name MeasureTarget */
    public static final String COLUMNNAME_MeasureTarget = "MeasureTarget";

	/** Set Measure Target.
	  * Target value for measure
	  */
	public void setMeasureTarget (BigDecimal MeasureTarget);

	/** Get Measure Target.
	  * Target value for measure
	  */
	public BigDecimal getMeasureTarget();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Note */
    public static final String COLUMNNAME_Note = "Note";

	/** Set Note.
	  * Optional additional user defined information
	  */
	public void setNote (String Note);

	/** Get Note.
	  * Optional additional user defined information
	  */
	public String getNote();

    /** Column name PA_ColorSchema_ID */
    public static final String COLUMNNAME_PA_ColorSchema_ID = "PA_ColorSchema_ID";

	/** Set Color Schema.
	  * Performance Color Schema
	  */
	public void setPA_ColorSchema_ID (int PA_ColorSchema_ID);

	/** Get Color Schema.
	  * Performance Color Schema
	  */
	public int getPA_ColorSchema_ID();

	public I_PA_ColorSchema getPA_ColorSchema() throws RuntimeException;

    /** Column name PA_Goal_ID */
    public static final String COLUMNNAME_PA_Goal_ID = "PA_Goal_ID";

	/** Set Goal.
	  * Performance Goal
	  */
	public void setPA_Goal_ID (int PA_Goal_ID);

	/** Get Goal.
	  * Performance Goal
	  */
	public int getPA_Goal_ID();

    /** Column name PA_GoalParent_ID */
    public static final String COLUMNNAME_PA_GoalParent_ID = "PA_GoalParent_ID";

	/** Set Parent Goal.
	  * Parent Goal
	  */
	public void setPA_GoalParent_ID (int PA_GoalParent_ID);

	/** Get Parent Goal.
	  * Parent Goal
	  */
	public int getPA_GoalParent_ID();

	public I_PA_Goal getPA_GoalParent() throws RuntimeException;

    /** Column name PA_Measure_ID */
    public static final String COLUMNNAME_PA_Measure_ID = "PA_Measure_ID";

	/** Set Measure.
	  * Concrete Performance Measurement
	  */
	public void setPA_Measure_ID (int PA_Measure_ID);

	/** Get Measure.
	  * Concrete Performance Measurement
	  */
	public int getPA_Measure_ID();

	public I_PA_Measure getPA_Measure() throws RuntimeException;

    /** Column name RelativeWeight */
    public static final String COLUMNNAME_RelativeWeight = "RelativeWeight";

	/** Set Relative Weight.
	  * Relative weight of this step (0 = ignored)
	  */
	public void setRelativeWeight (BigDecimal RelativeWeight);

	/** Get Relative Weight.
	  * Relative weight of this step (0 = ignored)
	  */
	public BigDecimal getRelativeWeight();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
