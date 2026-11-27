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

package de.metas.contracts.commission.invoicecandidate;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.contracts.invoicecandidate.TaxLocationTestFixture;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.invoice.vatid.TaxLocation;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Where, by whom and when a commission share's invoice candidate is taxed, and with which ship-from country (see {@link TaxLocation}).
 */
public class CommissionShareHandlerTaxLocationTest
{
	private TaxLocationTestFixture fixture;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		fixture = new TaxLocationTestFixture();
	}

	@Test
	void termBillTo_deliveryDate()
	{
		final BPartnerLocationId billToLocationId = fixture.createLocation();
		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setAD_Org_ID(fixture.getOrgId().getRepoId());
		term.setBill_BPartner_ID(billToLocationId.getBpartnerId().getRepoId());
		term.setBill_Location_ID(billToLocationId.getRepoId());
		save(term);

		final I_C_Commission_Share share = newInstance(I_C_Commission_Share.class);
		share.setAD_Org_ID(fixture.getOrgId().getRepoId());
		share.setC_Flatrate_Term_ID(term.getC_Flatrate_Term_ID());
		save(share);

		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setAD_Org_ID(fixture.getOrgId().getRepoId());
		ic.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Commission_Share.Table_Name));
		ic.setRecord_ID(share.getC_Commission_Share_ID());
		ic.setDeliveryDate(Timestamp.valueOf("2026-03-04 00:00:00"));
		save(ic);

		final TaxLocation result = new CommissionShareHandler().getTaxLocation(ic).orElseThrow(AssertionError::new);

		assertThat(result.getBpartnerLocationId()).isEqualTo(billToLocationId);
		assertThat(result.getOrgId()).isEqualTo(fixture.getOrgId());
		assertThat(result.getTaxDate()).isEqualTo(Timestamp.valueOf("2026-03-04 00:00:00").toInstant());
		assertThat(result.getFromCountryId()).isEqualTo(fixture.getOrgCountryId());
	}
}
