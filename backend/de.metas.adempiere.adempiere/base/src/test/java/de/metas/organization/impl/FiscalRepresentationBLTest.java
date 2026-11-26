/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.organization.impl;

import de.metas.location.CountryId;
import de.metas.organization.IFiscalRepresentationBL;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Fiscal_Representation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class FiscalRepresentationBLTest
{
	private static final CountryId GR = CountryId.ofRepoId(101);
	private static final OrgId ORG = OrgId.ofRepoId(1000000);
	private static final Timestamp DATE = Timestamp.valueOf("2026-06-22 00:00:00");

	private IFiscalRepresentationBL fiscalRepresentationBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		fiscalRepresentationBL = Services.get(IFiscalRepresentationBL.class);
	}

	@Test
	public void noRow_empty()
	{
		assertThat(fiscalRepresentationBL.getFiscalRepresentation(GR, ORG, DATE)).isEmpty();
		assertThat(fiscalRepresentationBL.hasFiscalRepresentation(GR, ORG, DATE)).isFalse();
	}

	@Test
	public void ownRegistration_returnsRow()
	{
		final I_C_Fiscal_Representation row = createRow(GR, ORG, "2026-06-01", null, "EL094259216");

		final Optional<I_C_Fiscal_Representation> result = fiscalRepresentationBL.getFiscalRepresentation(GR, ORG, DATE);

		assertThat(result).isPresent();
		assertThat(result.get().getC_Fiscal_Representation_ID()).isEqualTo(row.getC_Fiscal_Representation_ID());
		assertThat(fiscalRepresentationBL.hasFiscalRepresentation(GR, ORG, DATE)).isTrue();
	}

	/**
	 * The latest-ValidFrom row is created FIRST (lowest ID), so an ID-only ordering would pick the older row.
	 */
	@Test
	public void overlappingRows_latestValidFromWinsOverHigherId()
	{
		final I_C_Fiscal_Representation latestValidFrom = createRow(GR, ORG, "2026-06-01", null, "LATEST");
		final I_C_Fiscal_Representation older = createRow(GR, ORG, "2026-01-01", null, "OLDER");

		final I_C_Fiscal_Representation result = fiscalRepresentationBL.getFiscalRepresentation(GR, ORG, DATE).orElseThrow(AssertionError::new);

		assertThat(older.getC_Fiscal_Representation_ID()).isGreaterThan(latestValidFrom.getC_Fiscal_Representation_ID());
		assertThat(result.getC_Fiscal_Representation_ID()).isEqualTo(latestValidFrom.getC_Fiscal_Representation_ID());
	}

	@Test
	public void overlappingRows_sameValidFrom_highestId()
	{
		final I_C_Fiscal_Representation first = createRow(GR, ORG, "2026-06-01", null, "FIRST");
		final I_C_Fiscal_Representation second = createRow(GR, ORG, "2026-06-01", null, "SECOND");

		final I_C_Fiscal_Representation result = fiscalRepresentationBL.getFiscalRepresentation(GR, ORG, DATE).orElseThrow(AssertionError::new);

		assertThat(second.getC_Fiscal_Representation_ID()).isGreaterThan(first.getC_Fiscal_Representation_ID());
		assertThat(result.getC_Fiscal_Representation_ID()).isEqualTo(second.getC_Fiscal_Representation_ID());
	}

	@Test
	public void expiredRow_ignored()
	{
		createRow(GR, ORG, "2026-01-01", "2026-06-21", "EXPIRED");

		assertThat(fiscalRepresentationBL.getFiscalRepresentation(GR, ORG, DATE)).isEmpty();
		assertThat(fiscalRepresentationBL.hasFiscalRepresentation(GR, ORG, DATE)).isFalse();
	}

	private static I_C_Fiscal_Representation createRow(
			final CountryId toCountryId,
			final OrgId orgId,
			final String validFrom,
			@Nullable final String validTo,
			final String vatTaxId)
	{
		final I_C_Fiscal_Representation row = InterfaceWrapperHelper.newInstance(I_C_Fiscal_Representation.class);
		row.setAD_Org_ID(orgId.getRepoId());
		row.setTo_Country_ID(toCountryId.getRepoId());
		row.setValidFrom(Timestamp.valueOf(validFrom + " 00:00:00"));
		row.setValidTo(validTo != null ? Timestamp.valueOf(validTo + " 00:00:00") : null);
		row.setVATaxID(vatTaxId);
		row.setTaxID("TAX");
		InterfaceWrapperHelper.saveRecord(row);
		return row;
	}
}
