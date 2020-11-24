package de.metas.printing.model;

/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface I_C_Doc_Outbound_Config extends de.metas.document.archive.model.I_C_Doc_Outbound_Config
{
	//@formatter:off
	// task 09417
	 void setIsDirectEnqueue (boolean IsDirectEnqueue);
	 boolean isDirectEnqueue();
     org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, Object> COLUMN_IsDirectEnqueue = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, Object>(I_C_Doc_Outbound_Config.class, "IsDirectEnqueue", null);
     String COLUMNNAME_IsDirectEnqueue = "IsDirectEnqueue";
	//@formatter:on

	//@formatter:off
	// task 09417
	void setIsDirectProcessQueueItem (boolean IsDirectEnqueue);
	boolean isDirectProcessQueueItem();
    org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, Object> COLUMN_IsDirectProcessQueueItem = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Config, Object>(I_C_Doc_Outbound_Config.class, "IsCreatePrintJob", null);
    String COLUMNNAME_IsDirectProcessQueueItem = "IsDirectProcessQueueItem";
   	//@formatter:on
}
