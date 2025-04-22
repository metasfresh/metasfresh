/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.event.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for AD_EventLog
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_EventLog 
{

	String Table_Name = "AD_EventLog";

//	/** AD_Table_ID=540888 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Eventstore.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_EventLog_ID (int AD_EventLog_ID);

	/**
	 * Get Eventstore.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_EventLog_ID();

	ModelColumn<I_AD_EventLog, Object> COLUMN_AD_EventLog_ID = new ModelColumn<>(I_AD_EventLog.class, "AD_EventLog_ID", null);
	String COLUMNNAME_AD_EventLog_ID = "AD_EventLog_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_EventLog, Object> COLUMN_Created = new ModelColumn<>(I_AD_EventLog.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Daten.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEventData (@Nullable java.lang.String EventData);

	/**
	 * Get Daten.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEventData();

	ModelColumn<I_AD_EventLog, Object> COLUMN_EventData = new ModelColumn<>(I_AD_EventLog.class, "EventData", null);
	String COLUMNNAME_EventData = "EventData";

	/**
	 * Set Event Name.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEventName (@Nullable java.lang.String EventName);

	/**
	 * Get Event Name.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEventName();

	ModelColumn<I_AD_EventLog, Object> COLUMN_EventName = new ModelColumn<>(I_AD_EventLog.class, "EventName", null);
	String COLUMNNAME_EventName = "EventName";

	/**
	 * Set Date/Time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEventTime (@Nullable java.sql.Timestamp EventTime);

	/**
	 * Get Date/Time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getEventTime();

	ModelColumn<I_AD_EventLog, Object> COLUMN_EventTime = new ModelColumn<>(I_AD_EventLog.class, "EventTime", null);
	String COLUMNNAME_EventTime = "EventTime";

	/**
	 * Set Topicname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEventTopicName (@Nullable java.lang.String EventTopicName);

	/**
	 * Get Topicname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEventTopicName();

	ModelColumn<I_AD_EventLog, Object> COLUMN_EventTopicName = new ModelColumn<>(I_AD_EventLog.class, "EventTopicName", null);
	String COLUMNNAME_EventTopicName = "EventTopicName";

	/**
	 * Set Event type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEventTypeName (@Nullable java.lang.String EventTypeName);

	/**
	 * Get Event type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEventTypeName();

	ModelColumn<I_AD_EventLog, Object> COLUMN_EventTypeName = new ModelColumn<>(I_AD_EventLog.class, "EventTypeName", null);
	String COLUMNNAME_EventTypeName = "EventTypeName";

	/**
	 * Set Event UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEvent_UUID (java.lang.String Event_UUID);

	/**
	 * Get Event UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEvent_UUID();

	ModelColumn<I_AD_EventLog, Object> COLUMN_Event_UUID = new ModelColumn<>(I_AD_EventLog.class, "Event_UUID", null);
	String COLUMNNAME_Event_UUID = "Event_UUID";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_AD_EventLog, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_EventLog.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Error.
	 * An Error occurred in the execution
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsError (boolean IsError);

	/**
	 * Get Error.
	 * An Error occurred in the execution
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isError();

	ModelColumn<I_AD_EventLog, Object> COLUMN_IsError = new ModelColumn<>(I_AD_EventLog.class, "IsError", null);
	String COLUMNNAME_IsError = "IsError";

	/**
	 * Set Error acknowledged.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsErrorAcknowledged (boolean IsErrorAcknowledged);

	/**
	 * Get Error acknowledged.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isErrorAcknowledged();

	ModelColumn<I_AD_EventLog, Object> COLUMN_IsErrorAcknowledged = new ModelColumn<>(I_AD_EventLog.class, "IsErrorAcknowledged", null);
	String COLUMNNAME_IsErrorAcknowledged = "IsErrorAcknowledged";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_AD_EventLog, Object> COLUMN_Record_ID = new ModelColumn<>(I_AD_EventLog.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_EventLog, Object> COLUMN_Updated = new ModelColumn<>(I_AD_EventLog.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
