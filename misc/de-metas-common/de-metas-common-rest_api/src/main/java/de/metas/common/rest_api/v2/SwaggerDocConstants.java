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

package de.metas.common.rest_api.v2;

public class SwaggerDocConstants
{
	public static final String BPARTNER_IDENTIFIER_DOC = "Identifier of the bPartner in question. Can be\n"
			+ "* a plain `<C_BPartner_ID>`\n"
			+ "* or something like `ext-<I_S_ExternalReference.ExternalSystem>-<I_S_ExternalReference.ExternalReference>`\n"
			+ "* or something like `gln-<C_BPartner_Location.GLN>`\n"
			+ "* or something like `val-<C_BPartner.Value>`\n";

	public static final String BPARTNER_VALUE_DOC = "Code of the bPartner in question. Can be\n"
			+ "* a plain `C_BPartner.Value`\n"
			+ "* or an External Business Key like `ext-<I_S_ExternalReference.ExternalSystem>-<I_S_ExternalReference.ExternalReference>`\n";

	public static final String CONTACT_IDENTIFIER_DOC = "Identifier of the contact in question. Can be\n"
			+ "* a plain `<AD_User_ID>`\n"
			+ "* or something like `ext-<I_S_ExternalReference.ExternalSystem>-<I_S_ExternalReference.ExternalReference>`\n";

	public static final String LOCATION_IDENTIFIER_DOC = "Identifier of the location in question. Can be\n"
			+ "* a plain `<C_BPartner_Location_ID>`\n"
			+ "* or something like `ext-<I_S_ExternalReference.ExternalSystem>-<I_S_ExternalReference.ExternalReference>`\n"
			+ "* or something like `gln-<C_BPartner_Location_ID.GLN>`\n";

	public static final String EXTERNAL_VERSION_DOC = "It is used to decide if the instance is newer than the currently stored one."
			+ "It is optional and used only when the identifier is an external reference.";

	public static final String NEXT_DOC = "Optional identifier for the next page that was provided to the client in the previous page.\n"
			+ "If provided, any `since` value is ignored";

	public static final String INVOICE_IDENTIFIER_DOC = "Identifier of the Invoice in question. Can be\n"
			+ "* a plain `<C_Invoice.C_Invoice_ID>`\n"
			+ "* or something like `doc-<C_Invoice.documentNo>`"
			+ "* or something like `ext-<C_Invoice.ExternalId>`";

	public static final String SINCE_DOC = "Optional epoch timestamp in ms. The endpoint returns all resources that were created or modified *after* the given time.";

	public static final String READ_ONLY_SYNC_ADVISE_DOC = "Defaults to READ_ONLY, if not specified";

	public static final String CREATE_OR_MERGE_SYNC_ADVISE_DOC = "Defaults to CREATE_OR_MERGE, if not specified";

	public static final String PARENT_SYNC_ADVISE_DOC = "Defaults to the parent resource's sync advise, if not specified";

	public static final String DATASOURCE_IDENTIFIER_DOC = "An identifier can be\n"
			+ "* a plain `<AD_InputDataSource_ID>`\n"
			+ "* or something like `int-<AD_InputDataSource.InternalName>`\n"
			+ "* or something like `val-<AD_InputDataSource.Value>`\n"
			+ "* or something like `ext-<AD_InputDataSource.ExternalId>`\n";

	public static final String PRODUCT_IDENTIFIER_DOC = "Identifier of the product in question. Can be\n"
			+ "* a plain `<M_Product_ID>`\n"
			+ "* or something like `val-<M_Product.Value>`\n"
			+ "* or something like `ext-<ExternalSystemName>-<ExternalReference>` where `<ExternalReference>` translates to an identifier of the product within the given `<ExternalSystem>`";

	public static final String SHIPPER_IDENTIFIER_DOC = "Identifier of the M_Shipper in question. Can be\n"
			+ "* a plain `<M_Shipper_ID>`\n"
			+ "* or something like `ext-<I_S_ExternalReference.ExternalSystem>-<I_S_ExternalReference.ExternalReference>`\n"
			+ "* or something like `val-<M_Shipper.value>`\n";

	public static final String AD_SEQ_NO_ID_DOC = "AD_SeqNo_ID for the seqNo to be retrieved";

	public static final String HU_IDENTIFIER_DOC = "Identifier of the handling unit in question. Can be\n"
			+ "* a plain `<M_HU_ID>`";

	public static final String ACTIVITY_IDENTIFIER_DOC = "Identifier of the activity in question. Can be\n"
			+ "* a plain `<C_Activity_ID>`\n"
			+ "* or something like `val-<C_Activity.Value>`";

	public static final String PROJECT_IDENTIFIER_DOC = "Identifier of the project in question. Can be\n"
			+ "* a plain `<C_Project_ID>`\n"
			+ "* or something like `ext-<C_Project.ExternalId>`\n"
			+ "* or something like `val-<C_Project.value>`\n";

	public static final String STEP_IDENTIFIER_DOC = "Identifier of the project step in question. Can be\n"
			+ "* a plain `<C_Project_WO_Step_ID>`\n"
			+ "* or something like `ext-<C_Project_WO_Step.ExternalId>`\n";

	public static final String OBJECT_UNDER_TEST_IDENTIFIER_DOC = "Identifier of the project object under test in question. Can be\n"
			+ "* a plain `<C_Project_WO_ObjectUnderTest_ID>`\n"
			+ "* or something like `ext-<C_Project_WO_ObjectUnderTest.ExternalId>`\n";

	public static final String RESOURCE_IDENTIFIER_DOC = "Identifier of the step resource in question. Can be\n"
			+ "* a plain `<S_Resource_ID>`\n"
			+ "* or something like `val-<S_Resource.Value>`\n"
			+ "* or something like `int-<S_Resource.InternalName>`\n";

	public static final String PAYMENT_TERM_IDENTIFIER_DOC = "Identifier of the paymentTerm in question. Can be\n"
			+ "* a plain `<C_PaymentTerm_ID>`\n"
			+ "* or something like `val-<C_PaymentTerm.Value>`\n";

	public static final String WAREHOUSE_IDENTIFIER_DOC = "Identifier of the warehouse in question. Can be\n"
			+ "* a plain `<M_Warehouse_ID>`\n"
			+ "* or something like `val-<M_Warehouse.Value>`\n"
			+ "* or something like `ext-<ExternalSystemName>-<ExternalReference>` where `<ExternalReference>` translates to an identifier of the warehouse within the given `<ExternalSystem>`";
}