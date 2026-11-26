/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.createbpartner;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.bpartner.v2.response.JsonResponseBPartner;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.bpartner.v2.response.JsonResponseContact;
import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.rest_api.v2.bpartner.BPartnerEndpointService;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;

import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_C_BPartner_ID;

public class CreateBPartnerV2_StepDef
{
	private final BPartnerEndpointService bpartnerEndpointService;
	private final C_BPartner_StepDefData bPartnerTable;
	private final AD_User_StepDefData userTable;
	private final AD_Org_StepDefData orgTable;

	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	public CreateBPartnerV2_StepDef(
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final AD_User_StepDefData userTable,
			@NonNull final AD_Org_StepDefData orgTable)
	{
		this.bPartnerTable = bPartnerTable;
		this.userTable = userTable;
		this.orgTable = orgTable;
		this.bpartnerEndpointService = SpringContextHolder.instance.getBean(BPartnerEndpointService.class);
	}

	/**
	 * Verifies that the bPartner identified by the {@code externalIdentifier} column has the expected field values.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code externalIdentifier} – external identifier used to look up the bPartner</li>
	 *   <li>{@code C_BPartner_ID.Identifier} – identifier used to store the resolved bPartner record in the step-def context</li>
	 * </ul>
	 *
	 * <p>Optional columns (absent cell = not asserted):
	 * {@code name}, {@code code}, {@code companyName}, {@code phone}, {@code language}, {@code url},
	 * {@code groupName}, {@code vatId}, {@code parentId}, {@code glnLookupLabel}, {@code CreatedBy},
	 * {@code DiscountPrinted},
	 * {@code AD_Org_ID} – org identifier previously registered in {@link AD_Org_StepDefData}; asserts that
	 * {@code C_BPartner.AD_Org_ID} matches the referenced org.
	 *
	 * <p>Example:
	 * <pre>
	 * Then verify that bPartner was created for externalIdentifier
	 *   | externalIdentifier | C_BPartner_ID.Identifier | name      | AD_Org_ID |
	 *   | ext-001            | bp1                      | ACME Ltd. | org1      |
	 * </pre>
	 */
	@Then("^verify that bPartner was (updated|created) for externalIdentifier$")
	public void verify_bPartner_was_created_for_externalIdentifier_v2(@NonNull final String action, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final String externalIdentifier = row.getAsString("externalIdentifier");
			// Retrieve under the org named by the optional AD_Org_ID column (context-org fallback when absent),
			// so the AD_Org_ID assertion below is meaningful. retrieveBPartner takes the AD_Org.Value; the login
			// step establishes this role's permissions on the thread so the org is READ-accessible.
			final String orgCode = row.getAsOptionalIdentifier(I_C_BPartner.COLUMNNAME_AD_Org_ID)
					.map(orgTable::get)
					.map(org -> org.getValue())
					.orElse(null);
			final JsonResponseComposite composite = bpartnerEndpointService
					.retrieveBPartner(orgCode, ExternalIdentifier.of(externalIdentifier))
					.orElseThrow(() -> new AdempiereException("BPartner not found (orgCode=" + orgCode + "): " + externalIdentifier));
			final JsonResponseBPartner bpartner = composite.getBpartner();

			final SoftAssertions softly = new SoftAssertions();

			row.getAsOptionalString(JsonResponseBPartner.NAME)
					.ifPresent(name -> softly.assertThat(bpartner.getName()).as(JsonResponseBPartner.NAME).isEqualTo(name));
			row.getAsOptionalString(JsonResponseBPartner.CODE)
					.ifPresent(code -> softly.assertThat(bpartner.getCode()).as(JsonResponseBPartner.CODE).isEqualTo(code));
			row.getAsOptionalString(JsonResponseBPartner.COMPANY_NAME)
					.ifPresent(companyName -> softly.assertThat(bpartner.getCompanyName()).as(JsonResponseBPartner.COMPANY_NAME).isEqualTo(DataTableUtil.nullToken2Null(companyName)));
			row.getAsOptionalString(JsonResponseBPartner.PHONE)
					.ifPresent(phone -> softly.assertThat(bpartner.getPhone()).as(JsonResponseBPartner.PHONE).isEqualTo(DataTableUtil.nullToken2Null(phone)));
			row.getAsOptionalString(JsonResponseBPartner.LANGUAGE)
					.ifPresent(language -> softly.assertThat(bpartner.getLanguage()).as(JsonResponseBPartner.LANGUAGE).contains(language));
			row.getAsOptionalString(JsonResponseBPartner.URL)
					.ifPresent(url -> softly.assertThat(bpartner.getUrl()).as(JsonResponseBPartner.URL).isEqualTo(DataTableUtil.nullToken2Null(url)));
			row.getAsOptionalString(JsonResponseBPartner.GROUP_NAME)
					.ifPresent(group -> softly.assertThat(bpartner.getGroup()).as(JsonResponseBPartner.GROUP_NAME).isEqualTo(group));
			row.getAsOptionalString(JsonResponseBPartner.VAT_ID)
					.ifPresent(vatId -> softly.assertThat(bpartner.getVatId()).as(JsonResponseBPartner.VAT_ID).isEqualTo(DataTableUtil.nullToken2Null(vatId)));
			row.getAsOptionalString(JsonResponseBPartner.PARENT_ID)
					.ifPresent(parentId ->
					{
						final String parentIdEff = DataTableUtil.nullToken2Null(parentId);
						if (parentIdEff == null)
						{
							softly.assertThat(bpartner.getParentId()).isNull();
						}
						else
						{
							softly.assertThat(bpartner.getParentId().getValue()).as(JsonResponseBPartner.PARENT_ID).isEqualTo(Integer.parseInt(parentId));
						}
					});
			row.getAsOptionalString(JsonResponseBPartner.GLN_LOOKUP_LABEL)
					.ifPresent(glnLookupLabel -> softly.assertThat(bpartner.getGlnLookupLabel()).as(JsonResponseBPartner.GLN_LOOKUP_LABEL).isEqualTo(glnLookupLabel));

			final I_C_BPartner bPartnerRecord = bpartnerDAO.getById(bpartner.getMetasfreshId().getValue());

			row.getAsOptionalIdentifier(I_C_BPartner.COLUMNNAME_CreatedBy)
					.ifPresent(createdByIdentifier ->
					{
						final I_AD_User userRecord = userTable.get(createdByIdentifier);
						assertThat(userRecord).isNotNull();
						softly.assertThat(bPartnerRecord.getCreatedBy()).isEqualTo(userRecord.getAD_User_ID());
					});

			row.getAsOptionalBoolean(JsonResponseBPartner.DISCOUNT_PRINTED)
					.ifPresent(discountPrinted -> softly.assertThat(bPartnerRecord.isDiscountPrinted()).as(JsonResponseBPartner.DISCOUNT_PRINTED).isEqualTo(discountPrinted));

			row.getAsOptionalIdentifier(I_C_BPartner.COLUMNNAME_AD_Org_ID)
					.ifPresent(orgIdentifier -> softly.assertThat(bPartnerRecord.getAD_Org_ID())
							.as(I_C_BPartner.COLUMNNAME_AD_Org_ID)
							.isEqualTo(orgTable.getIdAsInt(orgIdentifier)));

			softly.assertAll();

			bPartnerTable.putOrReplace(row.getAsIdentifier(COLUMNNAME_C_BPartner_ID), bPartnerRecord);
		});
	}

	/**
	 * Verifies that the location identified by {@code bpartnerIdentifier} + {@code locationIdentifier} has the expected field values.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code bpartnerIdentifier} – external identifier of the owning bPartner</li>
	 *   <li>{@code locationIdentifier} – external identifier of the location</li>
	 * </ul>
	 *
	 * <p>Optional columns (absent cell = not asserted):
	 * {@code address1}, {@code address2}, {@code postal}, {@code poBox}, {@code district}, {@code region},
	 * {@code city}, {@code countryCode}, {@code gln}, {@code vatId}, {@code attention},
	 * {@code AD_Org_ID} – org identifier previously registered in {@link AD_Org_StepDefData}; asserts that
	 * {@code C_BPartner_Location.AD_Org_ID} matches the referenced org.
	 *
	 * <p>Example:
	 * <pre>
	 * And verify that location was created for bpartner
	 *   | bpartnerIdentifier | locationIdentifier | city   | AD_Org_ID |
	 *   | ext-001            | loc-001            | Berlin | org1      |
	 * </pre>
	 */
	@And("^verify that location was (updated|created) for bpartner$")
	public void verify_location_is_created_for_bpartner_v2(@NonNull final String action, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final SoftAssertions softly = new SoftAssertions();

			final String bpartnerIdentifier = row.getAsString("bpartnerIdentifier");
			final String locationIdentifier = row.getAsString("locationIdentifier");

			// Resolve under the org named by the optional AD_Org_ID column (context-org fallback when absent).
			final String orgCode = row.getAsOptionalIdentifier(I_C_BPartner_Location.COLUMNNAME_AD_Org_ID)
					.map(orgTable::get)
					.map(org -> org.getValue())
					.orElse(null);
			final JsonResponseLocation location = bpartnerEndpointService
					.retrieveBPartnerLocation(orgCode, ExternalIdentifier.of(bpartnerIdentifier), ExternalIdentifier.of(locationIdentifier))
					.orElseThrow(() -> new AdempiereException("Location not found (orgCode=" + orgCode + "): bpartner=" + bpartnerIdentifier + " location=" + locationIdentifier));

			row.getAsOptionalString(JsonResponseLocation.ADDRESS_1).map(DataTableUtil::nullToken2Null)
					.ifPresent(address1 -> softly.assertThat(location.getAddress1()).as(JsonResponseLocation.ADDRESS_1).isEqualTo(address1));
			row.getAsOptionalString(JsonResponseLocation.ADDRESS_2).map(DataTableUtil::nullToken2Null)
					.ifPresent(address2 -> softly.assertThat(location.getAddress2()).as(JsonResponseLocation.ADDRESS_2).isEqualTo(address2));
			row.getAsOptionalString(JsonResponseLocation.POSTAL).map(DataTableUtil::nullToken2Null)
					.ifPresent(postal -> softly.assertThat(location.getPostal()).as(JsonResponseLocation.POSTAL).isEqualTo(postal));
			row.getAsOptionalString(JsonResponseLocation.PO_BOX).map(DataTableUtil::nullToken2Null)
					.ifPresent(poBox -> softly.assertThat(location.getPoBox()).as(JsonResponseLocation.PO_BOX).isEqualTo(poBox));
			row.getAsOptionalString(JsonResponseLocation.DISTRICT).map(DataTableUtil::nullToken2Null)
					.ifPresent(district -> softly.assertThat(location.getDistrict()).as(JsonResponseLocation.DISTRICT).isEqualTo(district));
			row.getAsOptionalString(JsonResponseLocation.REGION).map(DataTableUtil::nullToken2Null)
					.ifPresent(region -> softly.assertThat(location.getRegion()).as(JsonResponseLocation.REGION).isEqualTo(region));
			row.getAsOptionalString(JsonResponseLocation.CITY).map(DataTableUtil::nullToken2Null)
					.ifPresent(city -> softly.assertThat(location.getCity()).as(JsonResponseLocation.CITY).isEqualTo(city));
			row.getAsOptionalString(JsonResponseLocation.COUNTRY_CODE).map(DataTableUtil::nullToken2Null)
					.ifPresent(countryCode -> softly.assertThat(location.getCountryCode()).as(JsonResponseLocation.COUNTRY_CODE).isEqualTo(countryCode));
			row.getAsOptionalString(JsonResponseLocation.GLN).map(DataTableUtil::nullToken2Null)
					.ifPresent(gln -> softly.assertThat(location.getGln()).as(JsonResponseLocation.GLN).isEqualTo(gln));
			row.getAsOptionalString(JsonResponseLocation.VAT_ID).map(DataTableUtil::nullToken2Null)
					.ifPresent(vatId -> softly.assertThat(location.getVatId()).as(JsonResponseLocation.VAT_ID).isEqualTo(vatId));
			row.getAsOptionalString(JsonResponseLocation.ATTENTION).map(DataTableUtil::nullToken2Null)
					.ifPresent(attention -> softly.assertThat(location.getAttention()).as(JsonResponseLocation.ATTENTION).isEqualTo(attention));

			row.getAsOptionalIdentifier(I_C_BPartner_Location.COLUMNNAME_AD_Org_ID)
					.ifPresent(orgIdentifier ->
					{
						final I_C_BPartner_Location locationRecord = InterfaceWrapperHelper.loadOutOfTrx(location.getMetasfreshId().getValue(), I_C_BPartner_Location.class);
						softly.assertThat(locationRecord.getAD_Org_ID())
								.as(I_C_BPartner_Location.COLUMNNAME_AD_Org_ID)
								.isEqualTo(orgTable.getIdAsInt(orgIdentifier));
					});

			softly.assertAll();
		});
	}

	/**
	 * Verifies that the contact identified by {@code bpartnerIdentifier} + {@code contactIdentifier} has the expected field values.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code bpartnerIdentifier} – external identifier of the owning bPartner</li>
	 *   <li>{@code contactIdentifier} – external identifier of the contact</li>
	 * </ul>
	 *
	 * <p>Optional columns (absent cell = not asserted):
	 * {@code name}, {@code email}, {@code fax}, {@code invoiceEmailEnabled},
	 * {@code AD_Org_ID} – org identifier previously registered in {@link AD_Org_StepDefData}; asserts that
	 * {@code AD_User.AD_Org_ID} matches the referenced org.
	 *
	 * <p>Example:
	 * <pre>
	 * And verify that contact was created for bpartner
	 *   | bpartnerIdentifier | contactIdentifier | name       | AD_Org_ID |
	 *   | ext-001            | con-001           | Jane Smith | org1      |
	 * </pre>
	 */
	@And("^verify that contact was (updated|created|not modified) for bpartner$")
	public void verify_contact_is_created_for_bpartner_v2(@NonNull final String action, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final String bpartnerIdentifier = row.getAsString("bpartnerIdentifier");
			final String contactIdentifier = row.getAsString("contactIdentifier");

			// Resolve under the org named by the optional AD_Org_ID column (context-org fallback when absent).
			final String orgCode = row.getAsOptionalIdentifier(I_AD_User.COLUMNNAME_AD_Org_ID)
					.map(orgTable::get)
					.map(org -> org.getValue())
					.orElse(null);
			final JsonResponseContact contact = bpartnerEndpointService
					.retrieveBPartnerContact(orgCode, ExternalIdentifier.of(bpartnerIdentifier), ExternalIdentifier.of(contactIdentifier))
					.orElseThrow(() -> new AdempiereException("Contact not found (orgCode=" + orgCode + "): bpartner=" + bpartnerIdentifier + " contact=" + contactIdentifier));

			final SoftAssertions softly = new SoftAssertions();

			row.getAsOptionalString(JsonResponseContact.NAME)
					.ifPresent(name -> softly.assertThat(contact.getName()).as(JsonResponseContact.NAME).isEqualTo(name));
			// FIXME: code (AD_User.Value) assertion disabled — no unique constraint on AD_User.Value yet (see BPartnerCompositeSaver)
			// row.getAsOptionalString(JsonResponseContact.CODE)
			// 		.ifPresent(code -> softly.assertThat(contact.getCode()).as(JsonResponseContact.CODE).isEqualTo(code));
			row.getAsOptionalString(JsonResponseContact.EMAIL)
					.map(DataTableUtil::nullToken2Null)
					.ifPresent(email -> softly.assertThat(contact.getEmail()).as(JsonResponseContact.EMAIL).isEqualTo(email));
			row.getAsOptionalString(JsonResponseContact.FAX)
					.map(DataTableUtil::nullToken2Null)
					.ifPresent(fax -> softly.assertThat(contact.getFax()).as(JsonResponseContact.FAX).isEqualTo(fax));
			row.getAsOptionalBoolean(JsonResponseContact.INVOICE_EMAIL_ENABLED)
					.ifPresent(invoiceEmailEnabled -> softly.assertThat(contact.getInvoiceEmailEnabled()).as(JsonResponseContact.INVOICE_EMAIL_ENABLED).isEqualTo(invoiceEmailEnabled));

			row.getAsOptionalIdentifier(I_AD_User.COLUMNNAME_AD_Org_ID)
					.ifPresent(orgIdentifier ->
					{
						final I_AD_User userRecord = InterfaceWrapperHelper.loadOutOfTrx(contact.getMetasfreshId().getValue(), I_AD_User.class);
						softly.assertThat(userRecord.getAD_Org_ID())
								.as(I_AD_User.COLUMNNAME_AD_Org_ID)
								.isEqualTo(orgTable.getIdAsInt(orgIdentifier));
					});

			softly.assertAll();
		});
	}

}
