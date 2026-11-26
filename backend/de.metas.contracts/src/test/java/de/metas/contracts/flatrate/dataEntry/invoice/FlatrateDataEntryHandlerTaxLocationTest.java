/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.flatrate.dataEntry.invoice;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.contracts.invoicecandidate.TaxLocationTestFixture;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.invoice.vatid.TaxLocation;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Where, by whom and when a data entry's invoice candidate is taxed, and with which ship-from country (see {@link TaxLocation}).
 */
public class FlatrateDataEntryHandlerTaxLocationTest
{
	private TaxLocationTestFixture fixture;
	private BPartnerLocationId billToLocationId;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		fixture = new TaxLocationTestFixture();
		billToLocationId = fixture.createLocation();
	}

	@Test
	void dropShip_isTaxLocation_periodEndDate()
	{
		final BPartnerLocationId dropShipLocationId = fixture.createLocation();
		final I_C_Flatrate_Term term = createTerm(X_C_Flatrate_Conditions.TYPE_FLATRATE_ReportedQuantity);
		term.setDropShip_BPartner_ID(dropShipLocationId.getBpartnerId().getRepoId());
		term.setDropShip_Location_ID(dropShipLocationId.getRepoId());
		save(term);

		final TaxLocation result = new FlatrateDataEntryHandler().getTaxLocation(createIC(term)).orElseThrow(AssertionError::new);

		assertThat(result.getBpartnerLocationId()).isEqualTo(dropShipLocationId);
		assertThat(result.getOrgId()).isEqualTo(fixture.getOrgId());
		assertThat(result.getTaxDate()).isEqualTo(Timestamp.valueOf("2026-03-31 00:00:00"));
		assertThat(result.getFromCountryId()).isEqualTo(fixture.getOrgCountryId());
	}

	@Test
	void noDropShip_billTo()
	{
		final TaxLocation result = new FlatrateDataEntryHandler().getTaxLocation(createIC(createTerm(X_C_Flatrate_Conditions.TYPE_FLATRATE_ReportedQuantity))).orElseThrow(AssertionError::new);

		assertThat(result.getBpartnerLocationId()).isEqualTo(billToLocationId);
		assertThat(result.getOrgId()).isEqualTo(fixture.getOrgId());
		assertThat(result.getTaxDate()).isEqualTo(Timestamp.valueOf("2026-03-31 00:00:00"));
		assertThat(result.getFromCountryId()).isEqualTo(fixture.getOrgCountryId());
	}

	/**
	 * Only a reported-quantity term is priced and taxed by this handler.
	 */
	@Test
	void notReportedQuantityTerm_isEmpty()
	{
		final I_C_Flatrate_Term term = createTerm(X_C_Flatrate_Conditions.TYPE_FLATRATE_NONE);

		assertThat(new FlatrateDataEntryHandler().getTaxLocation(createIC(term))).isEmpty();
	}

	private I_C_Flatrate_Term createTerm(final String flatrateType)
	{
		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Flatrate(flatrateType);
		save(conditions);

		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setAD_Org_ID(fixture.getOrgId().getRepoId());
		term.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		term.setBill_BPartner_ID(billToLocationId.getBpartnerId().getRepoId());
		term.setBill_Location_ID(billToLocationId.getRepoId());
		save(term);
		return term;
	}

	private I_C_Invoice_Candidate createIC(final I_C_Flatrate_Term term)
	{
		final I_C_Period period = newInstance(I_C_Period.class);
		period.setEndDate(Timestamp.valueOf("2026-03-31 00:00:00"));
		save(period);

		final I_C_Flatrate_DataEntry dataEntry = newInstance(I_C_Flatrate_DataEntry.class);
		dataEntry.setC_Flatrate_Term_ID(term.getC_Flatrate_Term_ID());
		dataEntry.setC_Period_ID(period.getC_Period_ID());
		save(dataEntry);

		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setAD_Org_ID(fixture.getOrgId().getRepoId());
		ic.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Flatrate_DataEntry.Table_Name));
		ic.setRecord_ID(dataEntry.getC_Flatrate_DataEntry_ID());
		save(ic);
		return ic;
	}
}
