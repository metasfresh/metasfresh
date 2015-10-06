package de.metas.document.archive.model;

/*
 * #%L
 * de.metas.document.archive.base
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


public interface I_AD_Archive extends org.compiere.model.I_AD_Archive
{
	/**
	 * If <code>true</code>, the printing module shall create a <code>C_Printing_Queue</code> item for this archive, so that it can be printed later.
	 */
	//@formatter:off
	public static final String COLUMNNAME_IsDirectPrint = "IsDirectPrint";
	public void setIsDirectPrint(boolean IsDirectPrint);
	public boolean isDirectPrint();
	//@formatter:on

	/**
	 * If <code>true</code>, the printing module shall also directly create a <code>C_Print_Job</code>, for this archive. This flag is only evaluated if {@link #COLUMNNAME_IsDirectPrint} is
	 * <code>true</code>.
	 */
	//@formatter:off
	public static final String COLUMNNAME_IsCreatePrintJob = "IsCreatePrintJob";
	public void setIsCreatePrintJob(boolean IsCreatePrintJob);
	public boolean isCreatePrintJob();
	//@formatter:on
}
