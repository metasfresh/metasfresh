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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.edi.api.EDIBPartnerConfig;
import de.metas.esb.edi.model.I_C_BPartner_EDI_Setting;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * RED-phase test: verifies location-based EDI config resolution (most-specific wins, partner-default fallback).
 * The methods {@code getById(BPartnerLocationId)} and {@code getByIdOrNull(BPartnerLocationId)} do NOT yet
 * exist on {@link EDIBPartnerConfigRepository}, so this test is expected to fail to compile.
 */
class EDIBPartnerConfigRepositoryTest
{
	// sending mode codes — same as I_C_BPartner constants
	private static final String SENDING_MODE_REPLICATION_INTERFACE = "R";
	private static final String SENDING_MODE_EXTERNAL_SYSTEM = "E";

	// fake config ID — FK enforcement is loose in the in-memory test DB
	private static final int EXTERNAL_SYSTEM_CONFIG_ID = 9999;

	private BPartnerId bPartnerId;
	private BPartnerLocationId locationIdL1;
	private BPartnerLocationId locationIdL2;
	private BPartnerLocationId locationIdLOther;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// Create the BPartner
		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		bpartnerRecord.setValue("TestPartner");
		bpartnerRecord.setName("TestPartner");
		saveRecord(bpartnerRecord);
		bPartnerId = BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID());

		// Create three BPartner locations
		final I_C_BPartner_Location locationL1 = newInstance(I_C_BPartner_Location.class);
		locationL1.setC_BPartner_ID(bPartnerId.getRepoId());
		saveRecord(locationL1);
		locationIdL1 = BPartnerLocationId.ofRepoId(bPartnerId, locationL1.getC_BPartner_Location_ID());

		final I_C_BPartner_Location locationL2 = newInstance(I_C_BPartner_Location.class);
		locationL2.setC_BPartner_ID(bPartnerId.getRepoId());
		saveRecord(locationL2);
		locationIdL2 = BPartnerLocationId.ofRepoId(bPartnerId, locationL2.getC_BPartner_Location_ID());

		final I_C_BPartner_Location locationLOther = newInstance(I_C_BPartner_Location.class);
		locationLOther.setC_BPartner_ID(bPartnerId.getRepoId());
		saveRecord(locationLOther);
		locationIdLOther = BPartnerLocationId.ofRepoId(bPartnerId, locationLOther.getC_BPartner_Location_ID());

		// (P, L1): DESADV via external system
		final I_C_BPartner_EDI_Setting settingL1 = newInstance(I_C_BPartner_EDI_Setting.class);
		settingL1.setC_BPartner_ID(bPartnerId.getRepoId());
		settingL1.setC_BPartner_Location_ID(locationIdL1.getRepoId());
		settingL1.setIsEdiDesadvRecipient(true);
		settingL1.setEdiDESADVSendingMode(SENDING_MODE_EXTERNAL_SYSTEM);
		settingL1.setEdiDESADV_ExternalSystem_Config_ID(EXTERNAL_SYSTEM_CONFIG_ID);
		settingL1.setIsEdiInvoicRecipient(false);
		settingL1.setEdiINVOICSendingMode(SENDING_MODE_REPLICATION_INTERFACE);
		saveRecord(settingL1);

		// (P, L2): DESADV via replication interface
		final I_C_BPartner_EDI_Setting settingL2 = newInstance(I_C_BPartner_EDI_Setting.class);
		settingL2.setC_BPartner_ID(bPartnerId.getRepoId());
		settingL2.setC_BPartner_Location_ID(locationIdL2.getRepoId());
		settingL2.setIsEdiDesadvRecipient(true);
		settingL2.setEdiDESADVSendingMode(SENDING_MODE_REPLICATION_INTERFACE);
		settingL2.setIsEdiInvoicRecipient(false);
		settingL2.setEdiINVOICSendingMode(SENDING_MODE_REPLICATION_INTERFACE);
		saveRecord(settingL2);

		// (P, null location): partner-default — INVOIC via external system
		final I_C_BPartner_EDI_Setting settingDefault = newInstance(I_C_BPartner_EDI_Setting.class);
		settingDefault.setC_BPartner_ID(bPartnerId.getRepoId());
		// C_BPartner_Location_ID left at 0 == null location → partner-default row
		settingDefault.setIsEdiDesadvRecipient(false);
		settingDefault.setEdiDESADVSendingMode(SENDING_MODE_REPLICATION_INTERFACE);
		settingDefault.setIsEdiInvoicRecipient(true);
		settingDefault.setEdiINVOICSendingMode(SENDING_MODE_EXTERNAL_SYSTEM);
		settingDefault.setEdiINVOIC_ExternalSystem_Config_ID(EXTERNAL_SYSTEM_CONFIG_ID);
		saveRecord(settingDefault);
	}

	@Test
	void locationL1_desadvViaExternalSystem()
	{
		final EDIBPartnerConfigRepository repo = EDIBPartnerConfigRepository.newInstanceForUnitTesting();

		// L1 has an exact (P, L1) row → DESADV via external system
		final EDIBPartnerConfig config = repo.getById(locationIdL1);
		assertThat(config.isDESADVExternalSystemRecipient()).isTrue();
	}

	@Test
	void locationL2_desadvViaReplicationInterface()
	{
		final EDIBPartnerConfigRepository repo = EDIBPartnerConfigRepository.newInstanceForUnitTesting();

		// L2 has an exact (P, L2) row → DESADV via replication interface (NOT external)
		final EDIBPartnerConfig config = repo.getById(locationIdL2);
		assertThat(config.isDESADVReplicationInterfaceRecipient()).isTrue();
		assertThat(config.isDESADVExternalSystemRecipient()).isFalse();
	}

	@Test
	void locationLOther_fallsBackToPartnerDefault_invoicViaExternalSystem()
	{
		final EDIBPartnerConfigRepository repo = EDIBPartnerConfigRepository.newInstanceForUnitTesting();

		// L_other has no own setting row → falls back to the partner-default (null location) row
		// which is an INVOIC external recipient
		final EDIBPartnerConfig config = repo.getByIdOrNull(locationIdLOther);
		assertThat(config).isNotNull();
		assertThat(config.isINVOICExternalSystemRecipient()).isTrue();
	}
}
