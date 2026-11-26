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

package de.metas.contracts.refund.invoicecandidatehandler;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.contracts.invoicecandidate.TaxLocationTestFixture;
import de.metas.invoice.vatid.TaxLocation;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Where, by whom and when a refund's invoice candidate is taxed, and with which ship-from country (see {@link TaxLocation}).
 */
public class FlatrateTermRefund_HandlerTaxLocationTest
{
	private TaxLocationTestFixture fixture;
	private BPartnerLocationId billLocationId;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		fixture = new TaxLocationTestFixture();
		billLocationId = fixture.createLocation();
	}

	@Test
	void billLocation_dateOrderedElseDateToInvoice()
	{
		final I_C_Invoice_Candidate ic = createIC();
		ic.setDateOrdered(Timestamp.valueOf("2026-03-04 00:00:00"));
		ic.setDateToInvoice(Timestamp.valueOf("2026-03-09 00:00:00"));
		save(ic);

		final TaxLocation withDateOrdered = new FlatrateTermRefund_Handler().getTaxLocation(ic).orElseThrow(AssertionError::new);

		assertThat(withDateOrdered.getBpartnerLocationId()).isEqualTo(billLocationId);
		assertThat(withDateOrdered.getOrgId()).isEqualTo(fixture.getOrgId());
		assertThat(withDateOrdered.getTaxDate()).isEqualTo(Timestamp.valueOf("2026-03-04 00:00:00"));
		assertThat(withDateOrdered.getFromCountryId()).isEqualTo(fixture.getOrgCountryId());

		ic.setDateOrdered(null);
		save(ic);

		final TaxLocation withDateToInvoice = new FlatrateTermRefund_Handler().getTaxLocation(ic).orElseThrow(AssertionError::new);

		assertThat(withDateToInvoice.getBpartnerLocationId()).isEqualTo(billLocationId);
		assertThat(withDateToInvoice.getOrgId()).isEqualTo(fixture.getOrgId());
		assertThat(withDateToInvoice.getTaxDate()).isEqualTo(Timestamp.valueOf("2026-03-09 00:00:00"));
		assertThat(withDateToInvoice.getFromCountryId()).isEqualTo(fixture.getOrgCountryId());
	}

	private I_C_Invoice_Candidate createIC()
	{
		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setAD_Org_ID(fixture.getOrgId().getRepoId());
		ic.setBill_BPartner_ID(billLocationId.getBpartnerId().getRepoId());
		ic.setBill_Location_ID(billLocationId.getRepoId());
		save(ic);
		return ic;
	}
}
