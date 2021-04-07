/*
 * #%L
 * de.metas.externalreference
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

package de.metas.externalreference;

import de.metas.externalreference.model.I_S_ExternalReference;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class ExternalReferenceRepositoryTest
{
	private ExternalReferenceRepository externalReferenceRepository;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final ExternalReferenceTypes externalReferenceTypes = new ExternalReferenceTypes();
		externalReferenceTypes.registerType(TestConstants.MOCK_EXTERNAL_REFERENCE_TYPE);

		final ExternalSystems externalSystems = new ExternalSystems();
		externalSystems.registerExternalSystem(TestConstants.MOCK_EXTERNAL_SYSTEM);
		externalSystems.registerExternalSystem(TestConstants.MOCK_EXTERNAL_SYSTEM_1);

		externalReferenceRepository = new ExternalReferenceRepository(Services.get(IQueryBL.class), externalSystems, externalReferenceTypes);
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
		Assert.assertNotNull(recordID);
		assertEquals(recordID.intValue(), mockExternalReference.getRecordId());
	}

	@Test
	public void deleteByRecordIdAndType()
	{
		//given
		final ExternalReference mockExternalReference = getMockExternalReference();

		final ExternalReferenceId externalReferenceId = externalReferenceRepository.save(mockExternalReference);

		exceptionRule.expect(RuntimeException.class);
		exceptionRule.expectMessage("de.metas.externalreference.model.I_S_ExternalReference, id=" + externalReferenceId.getRepoId());
		//when
		externalReferenceRepository.deleteByRecordIdAndType(mockExternalReference.getRecordId(), mockExternalReference.getExternalReferenceType());

		//then
		InterfaceWrapperHelper.load(externalReferenceId, I_S_ExternalReference.class);
	}

	private ExternalReference getMockExternalReference()
	{
		return ExternalReference.builder()
				.orgId(TestConstants.MOCK_ORG_ID)
				.externalReference(TestConstants.MOCK_EXTERNAL_REFERENCE)
				.externalReferenceType(TestConstants.MOCK_EXTERNAL_REFERENCE_TYPE)
				.externalSystem(TestConstants.MOCK_EXTERNAL_SYSTEM)
				.recordId(TestConstants.MOCK_RECORD_ID)
				.version(TestConstants.MOCK_EXTERNAL_REFERENCE_VERSION)
				.build();
	}

	private void assertEqual(final ExternalReference externalReference, final I_S_ExternalReference record)
	{
		assertEquals(externalReference.getRecordId(), record.getRecord_ID());
		assertEquals(externalReference.getExternalReference(), record.getExternalReference());
		assertEquals(externalReference.getExternalReferenceType().getCode(), record.getType());
		assertEquals(externalReference.getExternalSystem().getCode(), record.getExternalSystem());
		assertEquals(externalReference.getVersion(), record.getVersion());
	}
}
