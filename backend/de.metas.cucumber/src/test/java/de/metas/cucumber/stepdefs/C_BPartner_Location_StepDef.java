/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationBL;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Location_ID;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID;

@RequiredArgsConstructor
public class C_BPartner_Location_StepDef
{
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final C_Location_StepDefData locationTable;

	private final TestContext restTestContext;

	private final ILocationBL locationBL = Services.get(ILocationBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	@Nullable private AdempiereException lastUpdateException = null;

	@Given("metasfresh contains C_BPartner_Locations:")
	public void createC_BPartner_Location(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createC_BPartner_Location);
	}

	/**
	 * Updates existing {@code C_BPartner_Location} records.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns <b>C_BPartner_Location_ID.Identifier</b> — (required) step-def identifier of the C_BPartner_Location to update<br>
	 *                   <b>EMail</b> — (optional) new email address; use {@code null} token to clear<br>
	 *                   <b>GLN</b> — (optional) new GLN; use {@code null} token to clear<br>
	 *                   <b>VATaxID</b> — (optional) new VAT-ID value; use {@code null} token to clear<br>
	 * @cucumber.example
	 * <pre>
	 * And update C_BPartner_Location:
	 *   | C_BPartner_Location_ID.Identifier | OPT.GLN       |
	 *   | bpLocation                        | 1234567890123 |
	 * </pre>
	 */
	@Given("update C_BPartner_Location:")
	public void update_C_BPartner_Location(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::updateCBPartnerLocation);
	}

	/**
	 * Attempts to update a C_BPartner_Location and expects an {@link AdempiereException} to be thrown.
	 * The exception is stored in {@link #lastUpdateException} for subsequent assertion steps.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns <b>C_BPartner_Location_ID.Identifier</b> — (required) step-def identifier of the C_BPartner_Location to update<br>
	 *                   <b>VATaxID</b> — (optional) new VAT-ID value (expected to fail validation)<br>
	 * @cucumber.example
	 * <pre>
	 * When update C_BPartner_Location expecting error:
	 *   | C_BPartner_Location_ID.Identifier | VATaxID  |
	 *   | bpl_tc1                           | ATU1234  |
	 * </pre>
	 */
	@When("update C_BPartner_Location expecting error:")
	public void update_c_bpartner_location_expecting_error(@NonNull final DataTable dataTable)
	{
		lastUpdateException = null;
		try
		{
			DataTableRows.of(dataTable).forEach(this::updateCBPartnerLocation);
		}
		catch (final AdempiereException e)
		{
			lastUpdateException = e;
		}
	}

	/**
	 * Asserts that the most recent {@code update C_BPartner_Location expecting error:} step did throw an {@link AdempiereException}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then an AdempiereException was thrown during the last C_BPartner_Location update
	 * </pre>
	 */
	@Then("an AdempiereException was thrown during the last C_BPartner_Location update")
	public void assertLastLocationUpdateExceptionWasThrown()
	{
		assertThat(lastUpdateException)
				.as("Expected an AdempiereException to be thrown during the last C_BPartner_Location update, but none was thrown")
				.isNotNull();
	}

	@Given("update C_Location of the following C_BPartner_Location")
	public void update_C_Location_of_the_C_BPartner_Location(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			updateLocationOfTheBPartnerLocation(tableRow);
		}
	}

	@Given("load C_BPartner_Location:")
	public void load_bpartner_location(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::load_bpartner_location);
	}

	private void createC_BPartner_Location(@NonNull final DataTableRow tableRow)
	{
		final BPartnerId bpartnerId = tableRow.getAsIdentifier(I_C_BPartner.COLUMNNAME_C_BPartner_ID).lookupIdIn(bPartnerTable);
		final String gln = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_C_BPartner_Location.COLUMNNAME_GLN);

		final I_C_BPartner_Location bPartnerLocationRecord = CoalesceUtil.coalesceSuppliers(
				() -> queryBL.createQueryBuilder(I_C_BPartner_Location.class)
						.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, bpartnerId)
						.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_GLN, gln)
						.create()
						.firstOnlyOrNull(I_C_BPartner_Location.class),
				() -> newInstanceOutOfTrx(I_C_BPartner_Location.class));

		assertThat(bPartnerLocationRecord).isNotNull();

		bPartnerLocationRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		bPartnerLocationRecord.setGLN(gln);

		tableRow.getAsOptionalBoolean(I_C_BPartner_Location.COLUMNNAME_IsShipToDefault).ifPresent(bPartnerLocationRecord::setIsShipToDefault);

		final boolean isShipTo = tableRow.getAsOptionalBoolean(I_C_BPartner_Location.COLUMNNAME_IsShipTo).orElse(bPartnerLocationRecord.isShipToDefault());
		bPartnerLocationRecord.setIsShipTo(isShipTo);

		tableRow.getAsOptionalBoolean(I_C_BPartner_Location.COLUMNNAME_IsBillToDefault).ifPresent(bPartnerLocationRecord::setIsBillToDefault);
		tableRow.getAsOptionalBoolean(I_C_BPartner_Location.COLUMNNAME_IsRemitTo).ifPresent(bPartnerLocationRecord::setIsRemitTo);

		final boolean isBillTo = tableRow.getAsOptionalBoolean(I_C_BPartner_Location.COLUMNNAME_IsBillTo).orElse(bPartnerLocationRecord.isBillToDefault());
		bPartnerLocationRecord.setIsBillTo(isBillTo);

		final String locationIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_C_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(locationIdentifier))
		{
			final I_C_Location location = locationTable.get(locationIdentifier);
			assertThat(location).isNotNull();

			bPartnerLocationRecord.setC_Location_ID(location.getC_Location_ID());
			bPartnerLocationRecord.setAddress(locationBL.mkAddress(location));
		}
		else
		{
			final CountryId countryId = tableRow.getAsOptionalString("C_Country_ID")
					.map(countryDAO::getCountryIdByCountryCode)
					.orElse(StepDefConstants.COUNTRY_ID);

			final I_C_Location locationRecord = InterfaceWrapperHelper.newInstance(I_C_Location.class);
			locationRecord.setC_Country_ID(countryId.getRepoId());

			tableRow.getAsOptionalString(I_C_Location.COLUMNNAME_City).ifPresent(locationRecord::setCity);
			tableRow.getAsOptionalString(I_C_Location.COLUMNNAME_Postal).ifPresent(locationRecord::setPostal);
			tableRow.getAsOptionalString(I_C_Location.COLUMNNAME_Address1).ifPresent(locationRecord::setAddress1);
			tableRow.getAsOptionalString(I_C_Location.COLUMNNAME_Address2).ifPresent(locationRecord::setAddress2);
			tableRow.getAsOptionalString(I_C_Location.COLUMNNAME_Address3).ifPresent(locationRecord::setAddress3);
			tableRow.getAsOptionalString(I_C_Location.COLUMNNAME_Address4).ifPresent(locationRecord::setAddress4);

			saveRecord(locationRecord);

			bPartnerLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());
		}

		final String emailLocation = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_EMail);
		if (Check.isNotBlank(emailLocation))
		{
			bPartnerLocationRecord.setEMail(emailLocation);
		}

		final String name = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_Name);
		if (Check.isNotBlank(name))
		{
			bPartnerLocationRecord.setName(name);
		}

		final String bpLocationBPartnerName = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_BPartnerName);
		if (Check.isNotBlank(bpLocationBPartnerName))
		{
			bPartnerLocationRecord.setBPartnerName(bpLocationBPartnerName);
		}

		final String phone = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_Phone);
		if (Check.isNotBlank(phone))
		{
			bPartnerLocationRecord.setPhone(phone);
		}

		tableRow.getAsOptionalString(I_C_BPartner_Location.COLUMNNAME_Attention)
				.ifPresent(bPartnerLocationRecord::setAttention);

		tableRow.getAsOptionalString(I_C_BPartner_Location.COLUMNNAME_IsPreAdviceRequired)
				.ifPresent(bPartnerLocationRecord::setIsPreAdviceRequired);

		final Integer bpartnerLocationId = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID);
		if (bpartnerLocationId != null && bpartnerLocationId > 0)
		{
			bPartnerLocationRecord.setC_BPartner_Location_ID(bpartnerLocationId);
		}

		saveRecord(bPartnerLocationRecord);

		tableRow.getAsOptionalIdentifier("REST.Context.C_BPartner_Location_ID")
				.ifPresent(id -> restTestContext.setVariable(id.getAsString(), bPartnerLocationRecord.getC_BPartner_Location_ID()));

		bPartnerLocationTable.putOrReplace(tableRow.getAsIdentifier(), bPartnerLocationRecord);
	}

	private void load_bpartner_location(@NonNull final DataTableRow tableRow)
	{
		final String bpartnerLocationIdentifier = tableRow.getAsIdentifier(COLUMNNAME_C_BPartner_Location_ID + "." + StepDefDataIdentifier.SUFFIX).getAsString();

		final int id = tableRow.getAsOptionalInt(COLUMNNAME_C_BPartner_Location_ID).orElse(-1);
		if (id > 0)
		{
			bPartnerLocationTable.putOrReplace(bpartnerLocationIdentifier, InterfaceWrapperHelper.load(id, I_C_BPartner_Location.class));
			return;
		}

		final String bpartnerIdentifier = tableRow.getAsIdentifier(COLUMNNAME_C_BPartner_ID).getAsString();
		final Integer bpartnerId = bPartnerTable.getOptional(bpartnerIdentifier)
				.map(I_C_BPartner::getC_BPartner_ID)
				.orElseGet(() -> Integer.parseInt(bpartnerIdentifier));

		final int bpartnerLocationRepoId = tableRow.getAsInt(COLUMNNAME_C_BPartner_Location_ID);
		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerId, bpartnerLocationRepoId);

		final I_C_BPartner_Location bpartnerLocation = bpartnerDAO.getBPartnerLocationByIdInTrx(bPartnerLocationId);
		assertThat(bpartnerLocation).isNotNull();

		bPartnerLocationTable.put(bpartnerLocationIdentifier, bpartnerLocation);
	}

	private void updateCBPartnerLocation(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier bPartnerLocationIdentifier = row.getAsIdentifier(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID);

		final Integer bPartnerLocationID = bPartnerLocationTable.getOptional(bPartnerLocationIdentifier)
				.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
				.orElseGet(() -> Integer.parseInt(bPartnerLocationIdentifier.getAsString()));

		final I_C_BPartner_Location bPartnerLocation = InterfaceWrapperHelper.load(bPartnerLocationID, I_C_BPartner_Location.class);

		row.getAsOptionalString(I_C_BPartner_Location.COLUMNNAME_EMail)
				.filter(Check::isNotBlank)
				.ifPresent(email -> bPartnerLocation.setEMail(DataTableUtil.nullToken2Null(email)));

		row.getAsOptionalString(I_C_BPartner_Location.COLUMNNAME_GLN)
				.filter(Check::isNotBlank)
				.ifPresent(gln -> bPartnerLocation.setGLN(DataTableUtil.nullToken2Null(gln)));

		row.getAsOptionalString(I_C_BPartner_Location.COLUMNNAME_VATaxID)
				.ifPresent(vataxId -> bPartnerLocation.setVATaxID(DataTableUtil.nullToken2Null(vataxId)));

		saveRecord(bPartnerLocation);
		bPartnerLocationTable.putOrReplace(bPartnerLocationIdentifier, bPartnerLocation);
	}

	private void updateLocationOfTheBPartnerLocation(@NonNull final Map<String, String> tableRow)
	{
		final String bPartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final Integer bPartnerLocationID = bPartnerLocationTable.getOptional(bPartnerLocationIdentifier)
				.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
				.orElseGet(() -> Integer.parseInt(bPartnerLocationIdentifier));

		final I_C_BPartner_Location bPartnerLocationRecord = InterfaceWrapperHelper.load(bPartnerLocationID, I_C_BPartner_Location.class);

		final I_C_Location locationRecord = InterfaceWrapperHelper.load(bPartnerLocationRecord.getC_Location_ID(), I_C_Location.class);

		final String address1 = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Location.COLUMNNAME_Address1);

		if (Check.isNotBlank(address1))
		{
			locationRecord.setAddress1(address1);
		}

		final String address2 = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Location.COLUMNNAME_Address2);

		if (Check.isNotBlank(address2))
		{
			locationRecord.setAddress2(address2);
		}

		final String address3 = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Location.COLUMNNAME_Address3);

		if (Check.isNotBlank(address3))
		{
			locationRecord.setAddress3(address3);
		}

		saveRecord(locationRecord);
	}

	@And("validate C_BPartner_Location:")
	public void validate_C_BPartner_Location(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final SoftAssertions softly = new SoftAssertions();

			final StepDefDataIdentifier bpLocationIdentifier = row.getAsIdentifier(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID);
			final I_C_BPartner_Location bpLocation = bPartnerLocationTable.get(bpLocationIdentifier);

			row.getAsOptionalIdentifier(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID)
					.ifPresent(bpId -> softly.assertThat(bpLocation.getC_BPartner_ID()).as("C_BPartner_ID")
							.isEqualTo(bPartnerTable.get(bpId).getC_BPartner_ID()));

			row.getAsOptionalString(I_C_BPartner_Location.COLUMNNAME_Name)
					.map(DataTableUtil::nullToken2Null)
					.ifPresent(v -> softly.assertThat(bpLocation.getName()).as("Name").isEqualTo(v));

			row.getAsOptionalString(I_C_BPartner_Location.COLUMNNAME_GLN)
					.map(DataTableUtil::nullToken2Null)
					.ifPresent(v -> softly.assertThat(bpLocation.getGLN()).as("GLN").isEqualTo(v));

			row.getAsOptionalString(I_C_BPartner_Location.COLUMNNAME_VATaxID)
					.map(DataTableUtil::nullToken2Null)
					.ifPresent(v -> softly.assertThat(bpLocation.getVATaxID()).as("VATaxID").isEqualTo(v));

			row.getAsOptionalString(I_C_BPartner_Location.COLUMNNAME_Attention)
					.map(DataTableUtil::nullToken2Null)
					.ifPresent(v -> softly.assertThat(bpLocation.getAttention()).as("Attention").isEqualTo(v));

			row.getAsOptionalString(I_C_BPartner_Location.COLUMNNAME_IsPreAdviceRequired)
					.map(DataTableUtil::nullToken2Null)
					.ifPresent(v -> softly.assertThat(bpLocation.getIsPreAdviceRequired()).as("IsPreAdviceRequired").isEqualTo(v));

			softly.assertAll();
		});
	}
}