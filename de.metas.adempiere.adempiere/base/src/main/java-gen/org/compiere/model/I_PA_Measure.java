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

/** Generated Interface for PA_Measure
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_PA_Measure 
{

    /** TableName=PA_Measure */
    public static final String Table_Name = "PA_Measure";

    /** AD_Table_ID=441 */
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

    /** Column name CalculationClass */
    public static final String COLUMNNAME_CalculationClass = "CalculationClass";

	/** Set Calculation Class.
	  * Java Class for calculation, implementing Interface Measure
	  */
	public void setCalculationClass (String CalculationClass);

	/** Get Calculation Class.
	  * Java Class for calculation, implementing Interface Measure
	  */
	public String getCalculationClass();

    /** Column name C_ProjectType_ID */
    public static final String COLUMNNAME_C_ProjectType_ID = "C_ProjectType_ID";

	/** Set Project Type.
	  * Type of the project
	  */
	public void setC_ProjectType_ID (int C_ProjectType_ID);

	/** Get Project Type.
	  * Type of the project
	  */
	public int getC_ProjectType_ID();

	public I_C_ProjectType getC_ProjectType() throws RuntimeException;

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

    /** Column name ManualActual */
    public static final String COLUMNNAME_ManualActual = "ManualActual";

	/** Set Manual Actual.
	  * Manually entered actual value
	  */
	public void setManualActual (BigDecimal ManualActual);

	/** Get Manual Actual.
	  * Manually entered actual value
	  */
	public BigDecimal getManualActual();

    /** Column name ManualNote */
    public static final String COLUMNNAME_ManualNote = "ManualNote";

	/** Set Note.
	  * Note for manual entry
	  */
	public void setManualNote (String ManualNote);

	/** Get Note.
	  * Note for manual entry
	  */
	public String getManualNote();

    /** Column name MeasureDataType */
    public static final String COLUMNNAME_MeasureDataType = "MeasureDataType";

	/** Set Measure Data Type.
	  * Type of data - Status or in Time
	  */
	public void setMeasureDataType (String MeasureDataType);

	/** Get Measure Data Type.
	  * Type of data - Status or in Time
	  */
	public String getMeasureDataType();

    /** Column name MeasureType */
    public static final String COLUMNNAME_MeasureType = "MeasureType";

	/** Set Measure Type.
	  * Determines how the actual performance is derived
	  */
	public void setMeasureType (String MeasureType);

	/** Get Measure Type.
	  * Determines how the actual performance is derived
	  */
	public String getMeasureType();

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

    /** Column name PA_Benchmark_ID */
    public static final String COLUMNNAME_PA_Benchmark_ID = "PA_Benchmark_ID";

	/** Set Benchmark.
	  * Performance Benchmark
	  */
	public void setPA_Benchmark_ID (int PA_Benchmark_ID);

	/** Get Benchmark.
	  * Performance Benchmark
	  */
	public int getPA_Benchmark_ID();

	public I_PA_Benchmark getPA_Benchmark() throws RuntimeException;

    /** Column name PA_Hierarchy_ID */
    public static final String COLUMNNAME_PA_Hierarchy_ID = "PA_Hierarchy_ID";

	/** Set Reporting Hierarchy.
	  * Optional Reporting Hierarchy - If not selected the default hierarchy trees are used.
	  */
	public void setPA_Hierarchy_ID (int PA_Hierarchy_ID);

	/** Get Reporting Hierarchy.
	  * Optional Reporting Hierarchy - If not selected the default hierarchy trees are used.
	  */
	public int getPA_Hierarchy_ID();

	public I_PA_Hierarchy getPA_Hierarchy() throws RuntimeException;

    /** Column name PA_MeasureCalc_ID */
    public static final String COLUMNNAME_PA_MeasureCalc_ID = "PA_MeasureCalc_ID";

	/** Set Measure Calculation.
	  * Calculation method for measuring performance
	  */
	public void setPA_MeasureCalc_ID (int PA_MeasureCalc_ID);

	/** Get Measure Calculation.
	  * Calculation method for measuring performance
	  */
	public int getPA_MeasureCalc_ID();

	public I_PA_MeasureCalc getPA_MeasureCalc() throws RuntimeException;

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

    /** Column name PA_Ratio_ID */
    public static final String COLUMNNAME_PA_Ratio_ID = "PA_Ratio_ID";

	/** Set Ratio.
	  * Performace Ratio
	  */
	public void setPA_Ratio_ID (int PA_Ratio_ID);

	/** Get Ratio.
	  * Performace Ratio
	  */
	public int getPA_Ratio_ID();

	public I_PA_Ratio getPA_Ratio() throws RuntimeException;

    /** Column name R_RequestType_ID */
    public static final String COLUMNNAME_R_RequestType_ID = "R_RequestType_ID";

	/** Set Request Type.
	  * Type of request (e.g. Inquiry, Complaint, ..)
	  */
	public void setR_RequestType_ID (int R_RequestType_ID);

	/** Get Request Type.
	  * Type of request (e.g. Inquiry, Complaint, ..)
	  */
	public int getR_RequestType_ID();

	public I_R_RequestType getR_RequestType() throws RuntimeException;

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
