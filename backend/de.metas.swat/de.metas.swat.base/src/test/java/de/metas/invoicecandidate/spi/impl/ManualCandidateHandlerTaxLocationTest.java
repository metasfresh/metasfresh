/*
 * #%L
 * de.metas.swat.base
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

package de.metas.invoicecandidate.spi.impl;

import de.metas.invoice.vatid.TaxLocation;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The tax location of a manual invoice candidate: its bill-to, at its {@code DateOrdered}, else (that column is not mandatory)
 * at the same fallback date as for candidates whose handler knows no tax location.
 */
class ManualCandidateHandlerTaxLocationTest
{
	private static final Timestamp DATE_ORDERED = TimeUtil.parseTimestamp("2026-09-01");
	private static final Timestamp PRESET_DATE_INVOICED = TimeUtil.parseTimestamp("2026-09-02");

	private I_C_Invoice_Candidate ic;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		final int orgId = AdempiereTestHelper.createOrgWithTimeZone().getRepoId();

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		save(bpartner);
		final I_C_Location location = newInstance(I_C_Location.class);
		save(location);
		final I_C_BPartner_Location bpartnerLocation = newInstance(I_C_BPartner_Location.class);
		bpartnerLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpartnerLocation.setC_Location_ID(location.getC_Location_ID());
		save(bpartnerLocation);

		ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setAD_Org_ID(orgId);
		ic.setIsSOTrx(true);
		ic.setBill_BPartner_ID(bpartner.getC_BPartner_ID());
		ic.setBill_Location_ID(bpartnerLocation.getC_BPartner_Location_ID());
		ic.setPresetDateInvoiced(PRESET_DATE_INVOICED);
		save(ic);
	}

	@Test
	void dateOrdered()
	{
		ic.setDateOrdered(DATE_ORDERED);

		final TaxLocation taxLocation = new ManualCandidateHandler().getTaxLocation(ic).orElseThrow(AssertionError::new);

		assertThat(taxLocation.getTaxDate()).isEqualTo(DATE_ORDERED.toInstant());
	}

	@Test
	void noDateOrdered_fallsBackToPresetDateInvoiced()
	{
		ic.setDateOrdered(null);

		final TaxLocation taxLocation = new ManualCandidateHandler().getTaxLocation(ic).orElseThrow(AssertionError::new);

		assertThat(taxLocation.getTaxDate()).isEqualTo(PRESET_DATE_INVOICED.toInstant());
	}
}
