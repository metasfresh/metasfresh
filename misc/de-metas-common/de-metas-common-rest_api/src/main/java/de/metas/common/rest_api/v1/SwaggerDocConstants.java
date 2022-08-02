/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.v1;

public class SwaggerDocConstants
{
	public static final String BPARTNER_IDENTIFIER_DOC = "Identifier of the bPartner in question. Can be\n"
			+ "* a plain `<C_BPartner_ID>`\n"
			+ "* or something like `ext-<C_Bartner.ExternalId>` (This is deprecated and will no longer be available with v2)\n"
			+ "* or something like `val-<C_Bartner.Value>`\n"
			+ "* or something like `gln-<C_Bartner_Location.GLN>`\n";

	public static final String INVOICE_IDENTIFIER_DOC = "Identifier of the Invoice in question. Can be\n"
			+ "* a plain `<C_Invoice.C_Invoice_ID>`\n"
			+ "* or something like `doc-<C_Invoice.documentNo>`"
			+ "* or something like `ext-<C_Invoice.ExternalId>`";

	public static final String ORDER_IDENTIFIER_DOC = "Identifier of the Order in question. Can be\n"
			+ "* a plain `<C_Order.C_Order_ID>`\n"
			+ "* or something like `doc-<C_Order.documentNo>`"
			+ "* or something like `ext-<C_Order.ExternalId>`";

	public static final String CONTACT_IDENTIFIER_DOC = "Identifier of the contact in question. Can be\n"
			+ "* a plain `<AD_User_ID>`\n"
			+ "* or something like `ext-<AD_User_ID.ExternalId>` (This is deprecated and will no longer be available with v2)";

	public static final String LOCATION_IDENTIFIER_DOC = "Identifier of the location in question. Can be\n"
			+ "* a plain `<C_BPartner_Location_ID>`\n"
			+ "* or something like `ext-<C_BPartner_Location_ID.ExternalId>` (This is deprecated and will no longer be available with v2)\n"
			+ "* or something like `gln-<C_BPartner_Location_ID.GLN>`\n";

	public static final String DATASOURCE_IDENTIFIER_DOC = "An identifier can be\n"
			+ "* a plain `<AD_InputDataSource_ID>`\n"
			+ "* or something like `int-<AD_InputDataSource.InternalName>`\n"
			+ "* or something like `val-<AD_InputDataSource.Value>`\n"
			+ "* or something like `ext-<AD_InputDataSource.ExternalId>`\n";

	public static final String PRODUCT_IDENTIFIER_DOC = "Identifier of the product in question. Can be\n"
			+ "* a plain `<M_Product_ID>`\n"
			+ "* or something like `ext-<M_Product_ID.ExternalId>`\n"
			+ "* or something like `val-<M_Product_ID.Value>`";

	public static final String NEXT_DOC = "Optional identifier for the next page that was provided to the client in the previous page.\n"
			+ "If provided, any `since` value is ignored";

	public static final String SINCE_DOC = "Optional epoch timestamp in ms. The enpoint returns all resources that were created or modified *after* the given time.";

	public static final String READ_ONLY_SYNC_ADVISE_DOC = "Defaults to READ_ONLY, if not specified";

	public static final String CREATE_OR_MERGE_SYNC_ADVISE_DOC = "Defaults to CREATE_OR_MERGE, if not specified";

	public static final String PARENT_SYNC_ADVISE_DOC = "Defaults to the parent resource's sync advise, if not specified";
}
