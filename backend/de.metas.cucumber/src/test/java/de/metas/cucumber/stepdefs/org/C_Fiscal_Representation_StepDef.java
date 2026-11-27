/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.org;

import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Fiscal_Representation;

import java.util.Optional;

/**
 * Creates {@link I_C_Fiscal_Representation} records.
 */
@RequiredArgsConstructor
public class C_Fiscal_Representation_StepDef
{
	@NonNull private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	@NonNull private final C_Fiscal_Representation_StepDefData fiscalRepresentationTable;
	@NonNull private final AD_Org_StepDefData orgTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final C_BPartner_Location_StepDefData bpartnerLocationTable;

	/**
	 * Creates a fiscal representation of an organisation in a destination country.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>AD_Org_ID</b> — (optional, identifier-ref) the organisation; defaults to the standard test organisation<br>
	 *   <b>To_Country_ID.CountryCode</b> — (required) ISO code of the destination country<br>
	 *   <b>VATaxID</b> — (optional) the organisation's VAT ID in that country; empty for a registration through a representative that holds it<br>
	 *   <b>C_BPartner_Representative_ID</b> — (optional, identifier-ref) the representative partner; empty for an own registration.
	 *   A row needs at least one of VATaxID and C_BPartner_Representative_ID<br>
	 *   <b>C_BPartner_Location_ID</b> — (optional, identifier-ref) the representative's location<br>
	 *   <b>ValidFrom</b> — (required) start date<br>
	 *   <b>ValidTo</b> — (optional) end date
	 * @cucumber.depends StepDefData: AD_Org_StepDefData, C_BPartner_StepDefData, C_BPartner_Location_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_Fiscal_Representation:
	 *   | Identifier | To_Country_ID.CountryCode | VATaxID     | C_BPartner_Representative_ID | C_BPartner_Location_ID | ValidFrom  |
	 *   | fiscalRep  | FR                        | FR123456789 | representative               | representativeLocation | 2020-01-01 |
	 * </pre>
	 */
	@And("metasfresh contains C_Fiscal_Representation:")
	public void metasfresh_contains_C_Fiscal_Representation(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createFiscalRepresentation);
	}

	private void createFiscalRepresentation(@NonNull final DataTableRow row)
	{
		final I_C_Fiscal_Representation record = InterfaceWrapperHelper.newInstance(I_C_Fiscal_Representation.class);

		record.setAD_Org_ID(row.getAsOptionalIdentifier(I_C_Fiscal_Representation.COLUMNNAME_AD_Org_ID)
									.map(orgTable::getIdAsInt)
									.orElse(StepDefConstants.ORG_ID.getRepoId()));

		final CountryId toCountryId = countryDAO.getCountryIdByCountryCode(row.getAsOptionalCountryCode(I_C_Fiscal_Representation.COLUMNNAME_To_Country_ID)
				.orElseThrow(() -> new IllegalArgumentException("Column To_Country_ID.CountryCode is missing: " + row)));
		record.setTo_Country_ID(toCountryId.getRepoId());

		final Optional<String> vatTaxId = row.getAsOptionalString(I_C_Fiscal_Representation.COLUMNNAME_VATaxID).filter(Check::isNotBlank);
		final Optional<StepDefDataIdentifier> representative = row.getAsOptionalIdentifier(I_C_Fiscal_Representation.COLUMNNAME_C_BPartner_Representative_ID);
		if (!vatTaxId.isPresent() && !representative.isPresent())
		{
			// such a registration never yields a seller VAT ID
			throw new IllegalArgumentException("Either VATaxID or C_BPartner_Representative_ID is required: " + row);
		}
		vatTaxId.ifPresent(record::setVATaxID);
		representative
				.map(identifier -> identifier.lookupNotNullIn(bpartnerTable).getC_BPartner_ID())
				.ifPresent(record::setC_BPartner_Representative_ID);
		row.getAsOptionalIdentifier(I_C_Fiscal_Representation.COLUMNNAME_C_BPartner_Location_ID)
				.map(identifier -> identifier.lookupNotNullIn(bpartnerLocationTable).getC_BPartner_Location_ID())
				.ifPresent(record::setC_BPartner_Location_ID);
		record.setValidFrom(row.getAsLocalDateTimestamp(I_C_Fiscal_Representation.COLUMNNAME_ValidFrom));
		row.getAsOptionalLocalDateTimestamp(I_C_Fiscal_Representation.COLUMNNAME_ValidTo).ifPresent(record::setValidTo);

		InterfaceWrapperHelper.saveRecord(record);

		row.getAsIdentifier().putOrReplace(fiscalRepresentationTable, record);
	}
}
