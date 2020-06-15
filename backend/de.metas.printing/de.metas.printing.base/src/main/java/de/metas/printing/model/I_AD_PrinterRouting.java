/**
 *
 */
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


/**
 * @author cg
 *
 */
public interface I_AD_PrinterRouting extends de.metas.adempiere.model.I_AD_PrinterRouting
{
	public void setAD_Printer(I_AD_Printer printer);

	@Override
	public de.metas.adempiere.model.I_AD_Printer getAD_Printer();

	/** Column name AD_Printer_Tray_ID */
	public static final String COLUMNNAME_AD_Printer_Tray_ID = "AD_Printer_Tray_ID";

	/** Set Printer tray */
	public void setAD_Printer_Tray_ID(int AD_Printer_Tray_ID);

	/** Get Printer tray */
	public int getAD_Printer_Tray_ID();

	public de.metas.printing.model.I_AD_Printer_Tray getAD_Printer_Tray() throws RuntimeException;

	public void setAD_Printer_Tray(de.metas.printing.model.I_AD_Printer_Tray AD_Printer_Tray);

	/** Page Range = P */
	public static final String ROUTINGTYPE_PageRange = "P";
	/** Last Pages = L */
	public static final String ROUTINGTYPE_LastPages = "L";

	/** Column name RoutingType */
	public static final String COLUMNNAME_RoutingType = "RoutingType";

	/** Set Routing Type */
	public void setRoutingType(String RoutingType);

	/** Get Routing Type */
	public String getRoutingType();

	/** Column name PageFrom */
	public static final String COLUMNNAME_PageFrom = "PageFrom";

	/** Set Page From */
	public void setPageFrom(int PageFrom);

	/** Get Page From */
	public int getPageFrom();

	/** Column name PageTo */
	public static final String COLUMNNAME_PageTo = "PageTo";

	/** Set Page To */
	public void setPageTo(int PageTo);

	/** Get Page To */
	public int getPageTo();
}
