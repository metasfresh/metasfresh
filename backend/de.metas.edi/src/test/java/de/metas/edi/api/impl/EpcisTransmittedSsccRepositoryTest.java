/*
 * #%L
 * de.metas.edi
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

package de.metas.edi.api.impl;

import de.metas.esb.edi.model.I_EDI_EPCIS_Transmitted_SSCC;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfigId;
import de.metas.inout.InOutId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class EpcisTransmittedSsccRepositoryTest
{
	private static final ExternalSystemScriptedExportConversionConfigId CONFIG_ID =
			ExternalSystemScriptedExportConversionConfigId.ofRepoId(9999);
	private static final String SSCC = "076105640006796324";

	private EpcisTransmittedSsccRepository repository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new EpcisTransmittedSsccRepository();
	}

	private List<I_EDI_EPCIS_Transmitted_SSCC> ledgerRows()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_EDI_EPCIS_Transmitted_SSCC.class)
				.addEqualsFilter(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID, CONFIG_ID.getRepoId())
				.addEqualsFilter(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_SSCC18, SSCC)
				.create()
				.list(I_EDI_EPCIS_Transmitted_SSCC.class);
	}

	@Test
	void insertsActiveRow_whenNoneExists()
	{
		repository.recordTransmittedIfAbsent(CONFIG_ID, InOutId.ofRepoId(101), SSCC);

		final List<I_EDI_EPCIS_Transmitted_SSCC> rows = ledgerRows();
		assertThat(rows).hasSize(1);
		assertThat(rows.get(0).isActive()).isTrue();
		assertThat(rows.get(0).getM_InOut_ID()).isEqualTo(101);
	}

	@Test
	void skips_whenAnActiveRowAlreadyExists()
	{
		repository.recordTransmittedIfAbsent(CONFIG_ID, InOutId.ofRepoId(101), SSCC);
		repository.recordTransmittedIfAbsent(CONFIG_ID, InOutId.ofRepoId(102), SSCC);

		final List<I_EDI_EPCIS_Transmitted_SSCC> rows = ledgerRows();
		assertThat(rows).as("an active row already exists -> no second active row").hasSize(1);
		assertThat(rows.get(0).getM_InOut_ID()).isEqualTo(101);
	}

	/**
	 * The bug fix: after the deactivate-escape-hatch, a confirmed re-send must record a FRESH active
	 * row (so the exactly-once guard is restored), while the deactivated row remains as history.
	 * On the pre-fix code the existence check matched the deactivated row and returned early, so no
	 * active row was written — leaving the SSCC re-transmittable (duplicate).
	 */
	@Test
	void insertsFreshActiveRow_whenOnlyADeactivatedRowExists()
	{
		// first transmission
		repository.recordTransmittedIfAbsent(CONFIG_ID, InOutId.ofRepoId(101), SSCC);
		// escape-hatch: deactivate the ledger row (the WebUI action)
		final I_EDI_EPCIS_Transmitted_SSCC existing = ledgerRows().get(0);
		existing.setIsActive(false);
		saveRecord(existing);

		// confirmed re-send records the transmission again
		repository.recordTransmittedIfAbsent(CONFIG_ID, InOutId.ofRepoId(102), SSCC);

		final List<I_EDI_EPCIS_Transmitted_SSCC> rows = ledgerRows();
		assertThat(rows).as("deactivated history row + a fresh active row").hasSize(2);
		assertThat(rows).filteredOn(I_EDI_EPCIS_Transmitted_SSCC::isActive)
				.as("exactly one ACTIVE row, carrying the re-sending shipment")
				.hasSize(1)
				.allSatisfy(r -> assertThat(r.getM_InOut_ID()).isEqualTo(102));
	}
}
