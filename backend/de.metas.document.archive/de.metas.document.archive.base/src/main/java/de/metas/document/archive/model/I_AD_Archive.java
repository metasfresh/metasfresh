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
	//@formatter:off
	// task 09417
	String COLUMNNAME_C_Doc_Outbound_Config_ID = "C_Doc_Outbound_Config_ID";
	void setC_Doc_Outbound_Config(I_C_Doc_Outbound_Config config);
	I_C_Doc_Outbound_Config getC_Doc_Outbound_Config();
	int getC_Doc_Outbound_Config_ID();
	//@formatter:on
}
