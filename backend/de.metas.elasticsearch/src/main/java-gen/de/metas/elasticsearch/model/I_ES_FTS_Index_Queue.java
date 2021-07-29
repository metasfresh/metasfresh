/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.elasticsearch.model;

// TODO impl
public interface I_ES_FTS_Index_Queue
{
	String Table_Name = "ES_FTS_Index_Queue";

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	int getAD_Client_ID();

	//

	String COLUMNNAME_EventType = "EventType";

	String getEventType();

	String EVENTTYPE_Update = "U";
	String EVENTTYPE_Delete = "D";
	//

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	int getAD_Table_ID();

	String COLUMNNAME_Record_ID = "Record_ID";

	int getRecord_ID();

	String COLUMNNAME_ProcessingTag = "ProcessingTag";

	String getProcessingTag();

	String COLUMNNAME_Processed = "Processed";

	boolean isProcessed();

	String COLUMNNAME_IsError = "IsError";

	boolean isError();

	String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

	int getAD_Issue_ID();
}
