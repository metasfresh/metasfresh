package de.metas.adempiere.ait.helper;

/*
 * #%L
 * de.metas.adempiere.ait
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

public final class Constants {

	/** 102 = EUR */
	public static final int C_CURRENCY_ID_EURO = 102;

	/** 334 = Zimbabwe Dollar */
	public static final int C_CURRENCY_ID_ZWD = 334;

	/** 100 = piece */
	public static final int UOM_ID = 100;

	public static final boolean TAX_INCLUDED = false;

	public static final int AD_WINDOW_ID_BPARTNER = 123;

	public static final int AD_WINDOW_ID_PURCHASEORDER = 181;

	/**
	 * Id of the "Business Partner" tab (first tab of the BPartner window).
	 */
	public static final int AD_TAB_ID_BPARTNER = 220;

	/**
	 * Id of the "Name" file in the Bpartner Tab (first tab of the BPartner
	 * window).
	 */
	public static final int BPARTNER_NAME_AD_FIELD_ID = 2145;

	public static final int ORDER_PRINT_AD_PROCESS_ID = 110;

	public static final int SHIPMENT_PRINT_AD_PROCESS_ID = 117;

	public static final String COL_CLIENT_ID = "AD_Client_ID";
	public static final String COL_ORG_ID = "AD_Org_ID";
	public static final String COL_CREATED = "Created";
	public static final String COL_CREATED_BY = "CreatedBy";
	public static final String COL_ISACTIVE = "IsActive";
	public static final String COL_UPDATED = "Updated";
	public static final String COL_UPDATED_BY = "UpdatedBy";

	public static final int C_COUNTRY_ID_FRANCE = 102;

	/**
	 * Account 8400, "Erloese 19% USt"
	 */
	public static final int C_ELEMENT_VALUE_ID_REVENUE = 1000012;

	/**
	 * Account 1400, "Abziehbare Vorsteuer"
	 */
	public static final int C_ELEMENT_VALUE_ID_UNKNOWN = 1000029;

	public static final int C_TAXCATEGORY_ID_MWST = 1000001;

	/**
	 * Standard order for the default client (not garden world)
	 */
	public static final int C_DocTypeID_STANDART_SO = 1000030;
}
