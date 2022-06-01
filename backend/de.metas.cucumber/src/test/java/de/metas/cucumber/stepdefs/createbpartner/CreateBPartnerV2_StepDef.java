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
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.rest_api.v2.bpartner.BPartnerEndpointService;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_C_BPartner_ID;

public class CreateBPartnerV2_StepDef
{
	private final BPartnerEndpointService bpartnerEndpointService;
	private final C_BPartner_StepDefData bPartnerTable;
	private final AD_User_StepDefData userTable;

	final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

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
		final List<Map<String, String>> bpartnerTableList = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : bpartnerTableList)
		{
			final String externalIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, "externalIdentifier");
			final String code = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Code");
			final String name = DataTableUtil.extractStringForColumnName(dataTableRow, "Name");
			final String companyName = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.CompanyName");
			final String parentId = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.ParentId");
			final String phone = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Phone");
			final String language = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Language");
			final String url = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Url");
			final String group = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Group");
			final String vatId = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.VatId");

			// persisted value
			final Optional<JsonResponseComposite> persistedResult = bpartnerEndpointService.retrieveBPartner(null, ExternalIdentifier.of(externalIdentifier));
			final JsonResponseBPartner persistedBPartner = persistedResult.get().getBpartner();

			assertThat(persistedBPartner.getCompanyName()).isEqualTo(companyName);
			assertThat(persistedBPartner.getName()).isEqualTo(name);
			assertThat(persistedBPartner.getUrl()).isEqualTo(url);
			assertThat(persistedBPartner.getVatId()).isEqualTo(vatId);
			assertThat(persistedBPartner.getLanguage()).contains(language);
			assertThat(persistedBPartner.getCode()).isEqualTo(code);
			assertThat(persistedBPartner.getPhone()).isEqualTo(phone);

			if (Check.isNotBlank(group))
			{
				assertThat(persistedBPartner.getGroup()).isEqualTo(group);
			}

			if (Check.isNotBlank(parentId))
			{
				assertThat(persistedBPartner.getParentId().getValue()).isEqualTo(Integer.parseInt(parentId));
			}

			final I_C_BPartner bPartnerRecord = bpartnerDAO.getById(persistedBPartner.getMetasfreshId().getValue());

			final String createdByIdentifier = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + I_C_BPartner.COLUMNNAME_CreatedBy);
			if (Check.isNotBlank(createdByIdentifier))
			{
				final I_AD_User userRecord = userTable.get(createdByIdentifier);

				assertThat(userRecord).isNotNull();
				assertThat(bPartnerRecord.getCreatedBy()).isEqualTo(userRecord.getAD_User_ID());
			}

			final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			bPartnerTable.putOrReplace(bpartnerIdentifier, bPartnerRecord);
		}
	}

	@And("^verify that location was (updated|created) for bpartner$")
	public void verify_location_is_created_for_bpartner_v2(@NonNull final String action, @NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> locationsTableList = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : locationsTableList)
		{
			final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, "bpartnerIdentifier");
			final String locationIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, "locationIdentifier");
			final String address1 = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Address1");
			final String address2 = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Address2");
			final String postal = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Postal");
			final String poBox = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.PoBox");
			final String district = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.District");
			final String region = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Region");
			final String city = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.City");
			final String countryCode = DataTableUtil.extractStringForColumnName(dataTableRow, "CountryCode");
			final String gln = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Gln");

			// persisted value
			final Optional<JsonResponseLocation> persistedResult = bpartnerEndpointService.retrieveBPartnerLocation(
					null, ExternalIdentifier.of(bpartnerIdentifier), ExternalIdentifier.of(locationIdentifier));
			final JsonResponseLocation persistedLocation = persistedResult.get();

			assertThat(persistedLocation.getAddress1()).isEqualTo(address1);
			assertThat(persistedLocation.getAddress2()).isEqualTo(address2);
			assertThat(persistedLocation.getPostal()).isEqualTo(postal);
			assertThat(persistedLocation.getPoBox()).isEqualTo(poBox);
			assertThat(persistedLocation.getRegion()).isEqualTo(region);
			assertThat(persistedLocation.getCountryCode()).isEqualTo(countryCode);
			assertThat(persistedLocation.getCity()).isEqualTo(city);
			assertThat(persistedLocation.getDistrict()).isEqualTo(DataTableUtil.extractValueOrNull(district));
			assertThat(persistedLocation.getGln()).isEqualTo(gln);
		}
	}

	@And("^verify that contact was (updated|created|not modified) for bpartner$")
	public void verify_contact_is_created_for_bpartner_v2(@NonNull final String action, @NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> contactsTableList = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : contactsTableList)
		{
			final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, "bpartnerIdentifier");
			final String contactIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, "contactIdentifier");
			final String name = DataTableUtil.extractStringForColumnName(dataTableRow, "Name");
			final String email = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Email");
			final String fax = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Fax");
			final Boolean isInvoiceEmailEnabled = DataTableUtil.extractBooleanForColumnNameOr(dataTableRow, "OPT.InvoiceEmailEnabled", null);

			// persisted value
			final Optional<JsonResponseContact> persistedResult = bpartnerEndpointService.retrieveBPartnerContact(
					null, ExternalIdentifier.of(bpartnerIdentifier), ExternalIdentifier.of(contactIdentifier));
			final JsonResponseContact persistedContact = persistedResult.get();

			assertThat(persistedContact.getEmail()).isEqualTo(email);
			assertThat(persistedContact.getName()).isEqualTo(name);
			assertThat(persistedContact.getFax()).isEqualTo(fax);
			assertThat(persistedContact.getInvoiceEmailEnabled()).isEqualTo(isInvoiceEmailEnabled);
		}
	}
}
