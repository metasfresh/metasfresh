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
import de.metas.common.bpartner.v2.response.JsonResponseContact;
import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.rest_api.v2.bpartner.BPartnerEndpointService;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_C_BPartner_ID;

public class CreateBPartnerV2_StepDef
{
	private final BPartnerEndpointService bpartnerEndpointService;
	private final C_BPartner_StepDefData bPartnerTable;
	private final AD_User_StepDefData userTable;

	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	public CreateBPartnerV2_StepDef(
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final AD_User_StepDefData userTable)
	{
		this.bPartnerTable = bPartnerTable;
		this.userTable = userTable;
		this.bpartnerEndpointService = SpringContextHolder.instance.getBean(BPartnerEndpointService.class);
	}

	@Then("^verify that bPartner was (updated|created) for externalIdentifier$")
	public void verify_bPartner_was_created_for_externalIdentifier_v2(@NonNull final String action, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final String externalIdentifier = row.getAsString("externalIdentifier");
			final JsonResponseBPartner bpartner = bpartnerEndpointService
					.retrieveBPartner(null, ExternalIdentifier.of(externalIdentifier))
					.orElseThrow(() -> new AdempiereException("BPartner not found: " + externalIdentifier))
					.getBpartner();

			final SoftAssertions softly = new SoftAssertions();

			row.getAsOptionalString(JsonResponseBPartner.NAME)
					.ifPresent(v -> softly.assertThat(bpartner.getName()).as(JsonResponseBPartner.NAME).isEqualTo(v));
			row.getAsOptionalString(JsonResponseBPartner.CODE)
					.ifPresent(v -> softly.assertThat(bpartner.getCode()).as(JsonResponseBPartner.CODE).isEqualTo(v));
			row.getAsOptionalString(JsonResponseBPartner.COMPANY_NAME)
					.ifPresent(v -> softly.assertThat(bpartner.getCompanyName()).as(JsonResponseBPartner.COMPANY_NAME).isEqualTo(DataTableUtil.nullToken2Null(v)));
			row.getAsOptionalString(JsonResponseBPartner.PHONE)
					.ifPresent(v -> softly.assertThat(bpartner.getPhone()).as(JsonResponseBPartner.PHONE).isEqualTo(DataTableUtil.nullToken2Null(v)));
			row.getAsOptionalString(JsonResponseBPartner.LANGUAGE)
					.ifPresent(v -> softly.assertThat(bpartner.getLanguage()).as(JsonResponseBPartner.LANGUAGE).contains(v));
			row.getAsOptionalString(JsonResponseBPartner.URL)
					.ifPresent(v -> softly.assertThat(bpartner.getUrl()).as(JsonResponseBPartner.URL).isEqualTo(DataTableUtil.nullToken2Null(v)));
			row.getAsOptionalString(JsonResponseBPartner.GROUP_NAME)
					.ifPresent(v -> softly.assertThat(bpartner.getGroup()).as(JsonResponseBPartner.GROUP_NAME).isEqualTo(v));
			row.getAsOptionalString(JsonResponseBPartner.VAT_ID)
					.ifPresent(v -> softly.assertThat(bpartner.getVatId()).as(JsonResponseBPartner.VAT_ID).isEqualTo(DataTableUtil.nullToken2Null(v)));
			row.getAsOptionalString(JsonResponseBPartner.PARENT_ID)
					.ifPresent(v ->
					{
						final String parentIdEff = DataTableUtil.nullToken2Null(v);
						if (parentIdEff == null)
						{
							softly.assertThat(bpartner.getParentId()).isNull();
						}
						else
						{
							softly.assertThat(bpartner.getParentId().getValue()).as(JsonResponseBPartner.PARENT_ID).isEqualTo(Integer.parseInt(v));
						}
					});
			row.getAsOptionalString(JsonResponseBPartner.GLN_LOOKUP_LABEL)
					.ifPresent(v -> softly.assertThat(bpartner.getGlnLookupLabel()).as(JsonResponseBPartner.GLN_LOOKUP_LABEL).isEqualTo(v));

			final I_C_BPartner bPartnerRecord = bpartnerDAO.getById(bpartner.getMetasfreshId().getValue());

			row.getAsOptionalString(I_C_BPartner.COLUMNNAME_CreatedBy)
					.ifPresent(createdByIdentifier ->
					{
						final I_AD_User userRecord = userTable.get(createdByIdentifier);
						assertThat(userRecord).isNotNull();
						softly.assertThat(bPartnerRecord.getCreatedBy()).isEqualTo(userRecord.getAD_User_ID());
					});

			row.getAsOptionalBoolean(JsonResponseBPartner.DISCOUNT_PRINTED)
					.ifPresent(v -> softly.assertThat(bPartnerRecord.isDiscountPrinted()).as(JsonResponseBPartner.DISCOUNT_PRINTED).isEqualTo(v));

			softly.assertAll();

			bPartnerTable.putOrReplace(row.getAsIdentifier(COLUMNNAME_C_BPartner_ID), bPartnerRecord);
		});
	}

	@And("^verify that location was (updated|created) for bpartner$")
	public void verify_location_is_created_for_bpartner_v2(@NonNull final String action, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final SoftAssertions softly = new SoftAssertions();

			final String bpartnerIdentifier = row.getAsString("bpartnerIdentifier");
			final String locationIdentifier = row.getAsString("locationIdentifier");

			final JsonResponseLocation location = bpartnerEndpointService
					.retrieveBPartnerLocation(null, ExternalIdentifier.of(bpartnerIdentifier), ExternalIdentifier.of(locationIdentifier))
					.orElseThrow(() -> new AdempiereException("Location not found: bpartner=" + bpartnerIdentifier + " location=" + locationIdentifier));

			row.getAsOptionalString(JsonResponseLocation.ADDRESS_1).map(DataTableUtil::nullToken2Null)
					.ifPresent(v -> softly.assertThat(location.getAddress1()).as(JsonResponseLocation.ADDRESS_1).isEqualTo(v));
			row.getAsOptionalString(JsonResponseLocation.ADDRESS_2).map(DataTableUtil::nullToken2Null)
					.ifPresent(v -> softly.assertThat(location.getAddress2()).as(JsonResponseLocation.ADDRESS_2).isEqualTo(v));
			row.getAsOptionalString(JsonResponseLocation.POSTAL).map(DataTableUtil::nullToken2Null)
					.ifPresent(v -> softly.assertThat(location.getPostal()).as(JsonResponseLocation.POSTAL).isEqualTo(v));
			row.getAsOptionalString(JsonResponseLocation.PO_BOX).map(DataTableUtil::nullToken2Null)
					.ifPresent(v -> softly.assertThat(location.getPoBox()).as(JsonResponseLocation.PO_BOX).isEqualTo(v));
			row.getAsOptionalString(JsonResponseLocation.DISTRICT).map(DataTableUtil::nullToken2Null)
					.ifPresent(v -> softly.assertThat(location.getDistrict()).as(JsonResponseLocation.DISTRICT).isEqualTo(v));
			row.getAsOptionalString(JsonResponseLocation.REGION).map(DataTableUtil::nullToken2Null)
					.ifPresent(v -> softly.assertThat(location.getRegion()).as(JsonResponseLocation.REGION).isEqualTo(v));
			row.getAsOptionalString(JsonResponseLocation.CITY).map(DataTableUtil::nullToken2Null)
					.ifPresent(v -> softly.assertThat(location.getCity()).as(JsonResponseLocation.CITY).isEqualTo(v));
			row.getAsOptionalString(JsonResponseLocation.COUNTRY_CODE).map(DataTableUtil::nullToken2Null)
					.ifPresent(v -> softly.assertThat(location.getCountryCode()).as(JsonResponseLocation.COUNTRY_CODE).isEqualTo(v));
			row.getAsOptionalString(JsonResponseLocation.GLN).map(DataTableUtil::nullToken2Null)
					.ifPresent(v -> softly.assertThat(location.getGln()).as(JsonResponseLocation.GLN).isEqualTo(v));
			row.getAsOptionalString(JsonResponseLocation.VAT_ID).map(DataTableUtil::nullToken2Null)
					.ifPresent(v -> softly.assertThat(location.getVatId()).as(JsonResponseLocation.VAT_ID).isEqualTo(v));
			row.getAsOptionalString(JsonResponseLocation.ATTENTION).map(DataTableUtil::nullToken2Null)
					.ifPresent(v -> softly.assertThat(location.getAttention()).as(JsonResponseLocation.ATTENTION).isEqualTo(v));

			softly.assertAll();
		});
	}

	@And("^verify that contact was (updated|created|not modified) for bpartner$")
	public void verify_contact_is_created_for_bpartner_v2(@NonNull final String action, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final String bpartnerIdentifier = row.getAsString("bpartnerIdentifier");
			final String contactIdentifier = row.getAsString("contactIdentifier");

			final JsonResponseContact contact = bpartnerEndpointService
					.retrieveBPartnerContact(null, ExternalIdentifier.of(bpartnerIdentifier), ExternalIdentifier.of(contactIdentifier))
					.orElseThrow(() -> new AdempiereException("Contact not found: bpartner=" + bpartnerIdentifier + " contact=" + contactIdentifier));

			final SoftAssertions softly = new SoftAssertions();

			row.getAsOptionalString(JsonResponseContact.NAME)
					.ifPresent(v -> softly.assertThat(contact.getName()).as(JsonResponseContact.NAME).isEqualTo(v));
			// FIXME: code (AD_User.Value) assertion disabled — no unique constraint on AD_User.Value yet (see BPartnerCompositeSaver)
			// row.getAsOptionalString(JsonResponseContact.CODE)
			// 		.ifPresent(v -> softly.assertThat(contact.getCode()).as(JsonResponseContact.CODE).isEqualTo(v));
			row.getAsOptionalString(JsonResponseContact.EMAIL)
					.map(DataTableUtil::nullToken2Null)
					.ifPresent(v -> softly.assertThat(contact.getEmail()).as(JsonResponseContact.EMAIL).isEqualTo(v));
			row.getAsOptionalString(JsonResponseContact.FAX)
					.map(DataTableUtil::nullToken2Null)
					.ifPresent(v -> softly.assertThat(contact.getFax()).as(JsonResponseContact.FAX).isEqualTo(v));
			row.getAsOptionalBoolean(JsonResponseContact.INVOICE_EMAIL_ENABLED)
					.ifPresent(v -> softly.assertThat(contact.getInvoiceEmailEnabled()).as(JsonResponseContact.INVOICE_EMAIL_ENABLED).isEqualTo(v));

			softly.assertAll();
		});
	}

}
