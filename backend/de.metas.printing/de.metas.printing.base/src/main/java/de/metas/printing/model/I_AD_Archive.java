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



public interface I_AD_Archive extends de.metas.document.archive.model.I_AD_Archive
{
	/**
	 * If <code>true</code>, the printing module shall create a <code>C_Printing_Queue</code> item for this archive, so that it can be printed later.
	 */
	//@formatter:off
	// task 09417
	String COLUMNNAME_IsDirectEnqueue = "IsDirectEnqueue";
	void setIsDirectEnqueue(boolean IsDirectPrint);
	boolean isDirectEnqueue();
	//@formatter:on

	/**
	 * If <code>true</code>, the printing module shall also directly create a <code>C_Print_Job</code>, for this archive.
	 */
	//@formatter:off
	// task 09417
	String COLUMNNAME_IsDirectProcessQueueItem = "IsDirectProcessQueueItem";
	void setIsDirectProcessQueueItem (boolean IsDirectEnqueue);
	boolean isDirectProcessQueueItem();
	org.adempiere.model.ModelColumn<I_AD_Archive, Object> COLUMN_IsDirectProcessQueueItem = new org.adempiere.model.ModelColumn<I_AD_Archive, Object>(I_AD_Archive.class, "IsDirectProcessQueueItem", null);
	//@formatter:on

}
