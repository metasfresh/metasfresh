/*
 * #%L
 * de.metas.vertical.healthcare.alberta
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.vertical.healthcare.alberta.service.bpartner.patient;

import de.metas.bpartner.BPartnerId;
import de.metas.user.UserId;
import de.metas.vertical.healthcare.alberta.bpartner.patient.AlbertaPatient;
import de.metas.vertical.healthcare.alberta.bpartner.patient.AlbertaPatientRepository;
import de.metas.vertical.healthcare.alberta.bpartner.patient.DeactivationReasonType;
import de.metas.vertical.healthcare.alberta.bpartner.patient.PayerType;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;

public class AlbertaPatientRepositoryTest
{
	private AlbertaPatientRepository albertaPatientRepository;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		albertaPatientRepository =  new AlbertaPatientRepository();
	}

	@BeforeAll
	public static void initStatic()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@AfterAll
	public static void afterAll()
	{
		validateSnapshots();
	}

	@Test
	public void save_allFields()
	{
		//given
		final AlbertaPatient patient = AlbertaPatient.builder()
				.bPartnerId(BPartnerId.ofRepoId(1))
				.hospitalId(BPartnerId.ofRepoId(2))
				.dischargeDate(LocalDate.parse("2019-11-22"))
				.payerId(BPartnerId.ofRepoId(3))
				.payerType(PayerType.ProfessionalAssociation)
				.numberOfInsured("numberOfInsured")
				.copaymentFrom(LocalDate.parse("2019-11-23"))
				.copaymentTo(LocalDate.parse("2019-11-24"))
				.isTransferPatient(true)
				.isIVTherapy(false)
				.fieldNurseId(UserId.ofRepoId(4))
				.deactivationReason(DeactivationReasonType.AllTherapiesEnded)
				.deactivationDate(LocalDate.parse("2019-11-25"))
				.deactivationComment("deactivationComment")
				.createdAt(Instant.parse("2019-11-26T00:00:00Z"))
				.createdById(UserId.ofRepoId(5))
				.updatedAt(Instant.parse("2019-11-27T00:00:00Z"))
				.updatedById(UserId.ofRepoId(6))
				.build();

		//when
		final AlbertaPatient result = albertaPatientRepository.save(patient);

		//then
		expect(result).toMatchSnapshot();
	}

	@Test
	public void save_onlyMandatory()
	{
		//given
		final AlbertaPatient patient = AlbertaPatient.builder()
				.bPartnerId(BPartnerId.ofRepoId(1))
				.build();

		//when
		final AlbertaPatient result = albertaPatientRepository.save(patient);

		//then
		expect(result).toMatchSnapshot();
	}
}
