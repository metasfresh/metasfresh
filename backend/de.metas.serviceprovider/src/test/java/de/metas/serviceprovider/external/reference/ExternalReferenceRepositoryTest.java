/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.serviceprovider.external.reference;

import de.metas.serviceprovider.model.I_S_ExternalReference;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_REFERENCE;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_REFERENCE_TYPE;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_SYSTEM;
import static de.metas.serviceprovider.TestConstants.MOCK_ORG_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_RECORD_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ExternalReferenceRepositoryTest
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ExternalReferenceRepository externalReferenceRepository = new ExternalReferenceRepository(queryBL);

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
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

		final GetReferencedIdRequest getReferencedIdRequest = GetReferencedIdRequest
				.builder()
				.externalReference(mockExternalReference.getExternalReference())
				.externalReferenceType(mockExternalReference.getExternalReferenceType())
				.externalSystem(mockExternalReference.getExternalSystem())
				.build();
		//when
		final Integer recordID = externalReferenceRepository.getReferencedRecordIdOrNullBy(getReferencedIdRequest);

		//then
		assertNotNull(recordID);
		assertEquals(recordID.intValue(), mockExternalReference.getRecordId());
	}

	@Test
	public void deleteByRecordIdAndType()
	{
		//given
		final ExternalReference mockExternalReference = getMockExternalReference();

		final ExternalReferenceId externalReferenceId = externalReferenceRepository.save(mockExternalReference);

		exceptionRule.expect(RuntimeException.class);
		exceptionRule.expectMessage("de.metas.serviceprovider.model.I_S_ExternalReference, id=" + externalReferenceId.getRepoId());
		//when
		externalReferenceRepository.deleteByRecordIdAndType(mockExternalReference.getRecordId(), mockExternalReference.getExternalReferenceType());

		//then
		InterfaceWrapperHelper.load(externalReferenceId, I_S_ExternalReference.class);
	}

	private ExternalReference getMockExternalReference()
	{
		return ExternalReference.builder()
				.orgId(MOCK_ORG_ID)
				.externalReference(MOCK_EXTERNAL_REFERENCE)
				.externalReferenceType(MOCK_EXTERNAL_REFERENCE_TYPE)
				.externalSystem(MOCK_EXTERNAL_SYSTEM)
				.recordId(MOCK_RECORD_ID)
				.build();
	}

	private void assertEqual(final ExternalReference externalReference, final I_S_ExternalReference record)
	{
		assertEquals(externalReference.getRecordId(), record.getRecord_ID());
		assertEquals(externalReference.getExternalReference(), record.getExternalReference());
		assertEquals(externalReference.getExternalReferenceType().getCode(), record.getType());
		assertEquals(externalReference.getExternalSystem().getValue(), record.getExternalSystem());
	}
}
