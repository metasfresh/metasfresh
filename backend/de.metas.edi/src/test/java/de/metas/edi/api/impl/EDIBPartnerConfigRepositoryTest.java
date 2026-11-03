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

	@Test
	void lowestSeqNo_winsAmongMatchingCandidates()
	{
		// Arrange: two null-location rows for the same partner, different SeqNo values.
		// The row with the lower SeqNo (10) must win over the one with SeqNo 20.
		AdempiereTestHelper.get().init();

		final I_C_BPartner bp = newInstance(I_C_BPartner.class);
		bp.setValue("SeqTest1");
		bp.setName("SeqTest1");
		saveRecord(bp);
		final BPartnerId pid = BPartnerId.ofRepoId(bp.getC_BPartner_ID());

		final I_C_BPartner_Location loc = newInstance(I_C_BPartner_Location.class);
		loc.setC_BPartner_ID(pid.getRepoId());
		saveRecord(loc);
		final BPartnerLocationId bpl = BPartnerLocationId.ofRepoId(pid, loc.getC_BPartner_Location_ID());

		// SeqNo 10 – exact-location row (GLN = "GLN-10")
		final I_C_BPartner_EDI_Setting s10 = newInstance(I_C_BPartner_EDI_Setting.class);
		s10.setC_BPartner_ID(pid.getRepoId());
		s10.setC_BPartner_Location_ID(bpl.getRepoId());
		s10.setSeqNo(10);
		s10.setIsEdiDesadvRecipient(true);
		s10.setEdiDesadvRecipientGLN("GLN-10");
		s10.setEdiDESADVSendingMode(SENDING_MODE_REPLICATION_INTERFACE);
		s10.setIsEdiInvoicRecipient(false);
		s10.setEdiINVOICSendingMode(SENDING_MODE_REPLICATION_INTERFACE);
		saveRecord(s10);

		// SeqNo 20 – another exact-location row (GLN = "GLN-20")
		final I_C_BPartner_EDI_Setting s20 = newInstance(I_C_BPartner_EDI_Setting.class);
		s20.setC_BPartner_ID(pid.getRepoId());
		s20.setC_BPartner_Location_ID(bpl.getRepoId());
		s20.setSeqNo(20);
		s20.setIsEdiDesadvRecipient(true);
		s20.setEdiDesadvRecipientGLN("GLN-20");
		s20.setEdiDESADVSendingMode(SENDING_MODE_REPLICATION_INTERFACE);
		s20.setIsEdiInvoicRecipient(false);
		s20.setEdiINVOICSendingMode(SENDING_MODE_REPLICATION_INTERFACE);
		saveRecord(s20);

		// Act
		final EDIBPartnerConfig config = EDIBPartnerConfigRepository.newInstanceForUnitTesting().getById(bpl);

		// Assert: lowest SeqNo wins
		assertThat(config.getEdiDesadvRecipientGLN()).isEqualTo("GLN-10");
	}

	@Test
	void nullLocation_withLowestSeqNo_beatsHigherSeqNo_exactLocation()
	{
		// Arrange: null-location row (SeqNo 5) vs exact-location row (SeqNo 50).
		// The null-location row has a lower SeqNo → must win.
		AdempiereTestHelper.get().init();

		final I_C_BPartner bp = newInstance(I_C_BPartner.class);
		bp.setValue("SeqTest2");
		bp.setName("SeqTest2");
		saveRecord(bp);
		final BPartnerId pid = BPartnerId.ofRepoId(bp.getC_BPartner_ID());

		final I_C_BPartner_Location loc = newInstance(I_C_BPartner_Location.class);
		loc.setC_BPartner_ID(pid.getRepoId());
		saveRecord(loc);
		final BPartnerLocationId bpl = BPartnerLocationId.ofRepoId(pid, loc.getC_BPartner_Location_ID());

		// Exact-location row – SeqNo 50, GLN = "GLN-EXACT"
		final I_C_BPartner_EDI_Setting sExact = newInstance(I_C_BPartner_EDI_Setting.class);
		sExact.setC_BPartner_ID(pid.getRepoId());
		sExact.setC_BPartner_Location_ID(bpl.getRepoId());
		sExact.setSeqNo(50);
		sExact.setIsEdiDesadvRecipient(true);
		sExact.setEdiDesadvRecipientGLN("GLN-EXACT");
		sExact.setEdiDESADVSendingMode(SENDING_MODE_REPLICATION_INTERFACE);
		sExact.setIsEdiInvoicRecipient(false);
		sExact.setEdiINVOICSendingMode(SENDING_MODE_REPLICATION_INTERFACE);
		saveRecord(sExact);

		// Null-location (partner-default) row – SeqNo 5, GLN = "GLN-NULL-LOC"
		final I_C_BPartner_EDI_Setting sNull = newInstance(I_C_BPartner_EDI_Setting.class);
		sNull.setC_BPartner_ID(pid.getRepoId());
		// C_BPartner_Location_ID left at 0 → null location
		sNull.setSeqNo(5);
		sNull.setIsEdiDesadvRecipient(true);
		sNull.setEdiDesadvRecipientGLN("GLN-NULL-LOC");
		sNull.setEdiDESADVSendingMode(SENDING_MODE_REPLICATION_INTERFACE);
		sNull.setIsEdiInvoicRecipient(false);
		sNull.setEdiINVOICSendingMode(SENDING_MODE_REPLICATION_INTERFACE);
		saveRecord(sNull);

		// Act
		final EDIBPartnerConfig config = EDIBPartnerConfigRepository.newInstanceForUnitTesting().getById(bpl);

		// Assert: null-location row with SeqNo 5 wins over exact-location row with SeqNo 50
		assertThat(config.getEdiDesadvRecipientGLN()).isEqualTo("GLN-NULL-LOC");
	}

	@Test
	void tiebreak_byLowestId_whenSeqNoEqual()
	{
		// Arrange: two null-location rows, same SeqNo but different record IDs.
		// The one with the lower C_BPartner_EDI_Setting_ID must win.
		AdempiereTestHelper.get().init();

		final I_C_BPartner bp = newInstance(I_C_BPartner.class);
		bp.setValue("SeqTest3");
		bp.setName("SeqTest3");
		saveRecord(bp);
		final BPartnerId pid = BPartnerId.ofRepoId(bp.getC_BPartner_ID());

		final I_C_BPartner_Location loc = newInstance(I_C_BPartner_Location.class);
		loc.setC_BPartner_ID(pid.getRepoId());
		saveRecord(loc);
		final BPartnerLocationId bpl = BPartnerLocationId.ofRepoId(pid, loc.getC_BPartner_Location_ID());

		// Both have SeqNo 10; records are inserted in order so the first save gets the lower ID.
		final I_C_BPartner_EDI_Setting sFirst = newInstance(I_C_BPartner_EDI_Setting.class);
		sFirst.setC_BPartner_ID(pid.getRepoId());
		sFirst.setC_BPartner_Location_ID(bpl.getRepoId());
		sFirst.setSeqNo(10);
		sFirst.setIsEdiDesadvRecipient(true);
		sFirst.setEdiDesadvRecipientGLN("GLN-FIRST");
		sFirst.setEdiDESADVSendingMode(SENDING_MODE_REPLICATION_INTERFACE);
		sFirst.setIsEdiInvoicRecipient(false);
		sFirst.setEdiINVOICSendingMode(SENDING_MODE_REPLICATION_INTERFACE);
		saveRecord(sFirst);

		final I_C_BPartner_EDI_Setting sSecond = newInstance(I_C_BPartner_EDI_Setting.class);
		sSecond.setC_BPartner_ID(pid.getRepoId());
		sSecond.setC_BPartner_Location_ID(bpl.getRepoId());
		sSecond.setSeqNo(10);
		sSecond.setIsEdiDesadvRecipient(true);
		sSecond.setEdiDesadvRecipientGLN("GLN-SECOND");
		sSecond.setEdiDESADVSendingMode(SENDING_MODE_REPLICATION_INTERFACE);
		sSecond.setIsEdiInvoicRecipient(false);
		sSecond.setEdiINVOICSendingMode(SENDING_MODE_REPLICATION_INTERFACE);
		saveRecord(sSecond);

		// Sanity check: first record must have a lower ID than second
		assertThat(sFirst.getC_BPartner_EDI_Setting_ID()).isLessThan(sSecond.getC_BPartner_EDI_Setting_ID());

		// Act
		final EDIBPartnerConfig config = EDIBPartnerConfigRepository.newInstanceForUnitTesting().getById(bpl);

		// Assert: lower ID wins the tiebreak
		assertThat(config.getEdiDesadvRecipientGLN()).isEqualTo("GLN-FIRST");
	}
}
