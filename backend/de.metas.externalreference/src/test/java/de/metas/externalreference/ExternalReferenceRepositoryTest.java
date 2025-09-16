/*
 * #%L
 * de.metas.externalreference
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalreference;

import de.metas.externalreference.model.I_S_ExternalReference;
import de.metas.externalsystem.ExternalSystem;
import de.metas.externalsystem.ExternalSystemCreateRequest;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExternalReferenceRepositoryTest
{
	private ExternalReferenceRepository externalReferenceRepository;
	private ExternalSystem github;
	private ExternalSystem everhour;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final ExternalReferenceTypes externalReferenceTypes = new ExternalReferenceTypes();
		externalReferenceTypes.registerType(TestConstants.MOCK_EXTERNAL_REFERENCE_TYPE);

		final ExternalSystemRepository externalSystemRepository = new ExternalSystemRepository();
		github = externalSystemRepository.create(ExternalSystemCreateRequest.builder()
				.name("Github")
				.type(ExternalSystemType.Github)
				.build());
		everhour = externalSystemRepository.create(ExternalSystemCreateRequest.builder()
				.name("Everhour")
				.type(ExternalSystemType.Everhour)
				.build());

		externalReferenceRepository = new ExternalReferenceRepository(Services.get(IQueryBL.class), externalSystemRepository, externalReferenceTypes);
	}

	@Test
	public void save()
	{
		//given
		final ExternalReference mockExternalReference = getMockExternalReference();

		//when
		final ExternalReferenceId externalReferenceId = externalReferenceRepository.save(mockExternalReference);

		//then
		final I_S_ExternalReference record = InterfaceWrapperHelper.load(externalReferenceId, I_S_ExternalReference.class);

		assertEqual(mockExternalReference, record);
	}

	@Test
	public void getReferencedRecordIdOrNullBy()
	{
		//given
		final ExternalReference mockExternalReference = getMockExternalReference();

		externalReferenceRepository.save(mockExternalReference);

		final ExternalReferenceQuery externalReferenceQuery = ExternalReferenceQuery
				.builder()
				.orgId(TestConstants.MOCK_ORG_ID)
				.externalReference(mockExternalReference.getExternalReference())
				.externalReferenceType(mockExternalReference.getExternalReferenceType())
				.externalSystem(mockExternalReference.getExternalSystem())
				.build();
		//when
		final Integer recordID = externalReferenceRepository.getReferencedRecordIdOrNullBy(externalReferenceQuery);

		//then
		Assertions.assertNotNull(recordID);
		assertEquals(recordID.intValue(), mockExternalReference.getRecordId());
	}

	@Test
	public void deleteByRecordIdAndType()
	{
		//given
		final ExternalReference mockExternalReference = getMockExternalReference();

		final ExternalReferenceId externalReferenceId = externalReferenceRepository.save(mockExternalReference);

		//when
		externalReferenceRepository.deleteByRecordIdAndType(mockExternalReference.getRecordId(), mockExternalReference.getExternalReferenceType());

		//then
		Assertions.assertThrows(RuntimeException.class, () -> InterfaceWrapperHelper.load(externalReferenceId, I_S_ExternalReference.class));
	}

	private ExternalReference getMockExternalReference()
	{
		return ExternalReference.builder()
				.orgId(TestConstants.MOCK_ORG_ID)
				.externalReference(TestConstants.MOCK_EXTERNAL_REFERENCE)
				.externalReferenceType(TestConstants.MOCK_EXTERNAL_REFERENCE_TYPE)
				.externalSystem(github)
				.recordId(TestConstants.MOCK_RECORD_ID)
				.version(TestConstants.MOCK_EXTERNAL_REFERENCE_VERSION)
				.build();
	}

	private void assertEqual(final ExternalReference externalReference, final I_S_ExternalReference record)
	{
		assertEquals(externalReference.getRecordId(), record.getRecord_ID());
		assertEquals(externalReference.getExternalReference(), record.getExternalReference());
		assertEquals(externalReference.getExternalReferenceType().getCode(), record.getType());
		assertEquals(externalReference.getExternalSystem().getType(), record.getExternalSystem());
		assertEquals(externalReference.getVersion(), record.getVersion());
	}
}
