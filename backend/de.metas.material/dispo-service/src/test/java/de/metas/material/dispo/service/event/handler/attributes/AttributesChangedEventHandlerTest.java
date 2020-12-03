package de.metas.material.dispo.service.event.handler.attributes;

import com.google.common.collect.ImmutableList;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.handler.CandidateHandler;
import de.metas.material.event.attributes.AttributesChangedEvent;
import de.metas.material.event.attributes.AttributesKeyWithASI;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * metasfresh-material-dispo-service
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

public class AttributesChangedEventHandlerTest
{
	private static final AttributeSetInstanceId dummyAsiId = AttributeSetInstanceId.ofRepoId(123);

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	private AttributesChangedEventHandler createAttributesChangedEventHandler(final MockedCandidateHandler mockedCandidateHandler)
	{
		final CandidateChangeService candidateChangeService = new CandidateChangeService(ImmutableList.of(mockedCandidateHandler));
		return new AttributesChangedEventHandler(candidateChangeService);
	}

	@Test
	public void test()
	{
		final MockedCandidateHandler mockedCandidateHandler = new MockedCandidateHandler();
		mockedCandidateHandler.setNextGroupId(112233);
		final AttributesChangedEventHandler attributesChangedEventHandler = createAttributesChangedEventHandler(mockedCandidateHandler);

		attributesChangedEventHandler.handleEvent(AttributesChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(10, 20))
				.warehouseId(WarehouseId.ofRepoId(1))
				.date(Instant.parse("2019-10-03T10:15:30.00Z"))
				.productId(2)
				.qty(new BigDecimal("13"))
				.oldStorageAttributes(attributesKeyWithASI(1000))
				.newStorageAttributes(attributesKeyWithASI(2000))
				.huId(333)
				.build());

		final List<Candidate> candidates = mockedCandidateHandler.getSavedCandidates();
		assertThat(candidates).hasSize(2);

		//
		// ATTRIBUTES_CHANGED_FROM:
		{
			final Candidate fromCandidate = candidates.get(0);
			System.out.println("fromCandidate: " + fromCandidate);

			assertThat(fromCandidate.getType()).isEqualTo(CandidateType.ATTRIBUTES_CHANGED_FROM);
			assertThat(fromCandidate.getGroupId()).isEqualTo(MaterialDispoGroupId.ofInt(112233));
			assertThat(fromCandidate.getDate()).isEqualTo("2019-10-03T10:15:30.00Z");
			assertThat(fromCandidate.getWarehouseId()).isEqualTo(WarehouseId.ofRepoId(1));

			assertThat(fromCandidate.getMaterialDescriptor().getQuantity()).isEqualTo("-13");
			assertThat(fromCandidate.getMaterialDescriptor().getProductId()).isEqualTo(2);
			assertThat(fromCandidate.getMaterialDescriptor().getStorageAttributesKey()).isEqualTo(attributesKey(1000));
		}

		//
		// ATTRIBUTES_CHANGED_TO:
		{
			final Candidate toCandidate = candidates.get(1);
			System.out.println("toCandidate: " + toCandidate);

			assertThat(toCandidate.getType()).isEqualTo(CandidateType.ATTRIBUTES_CHANGED_TO);
			assertThat(toCandidate.getGroupId()).isEqualTo(MaterialDispoGroupId.ofInt(112233));
			assertThat(toCandidate.getDate()).isEqualTo("2019-10-03T10:15:30.00Z");
			assertThat(toCandidate.getWarehouseId()).isEqualTo(WarehouseId.ofRepoId(1));

			assertThat(toCandidate.getMaterialDescriptor().getQuantity()).isEqualTo("13");
			assertThat(toCandidate.getMaterialDescriptor().getProductId()).isEqualTo(2);
			assertThat(toCandidate.getMaterialDescriptor().getStorageAttributesKey()).isEqualTo(attributesKey(2000));
		}
	}

	private static AttributesKeyWithASI attributesKeyWithASI(int attributeValueRepoId)
	{
		final AttributesKey attributesKey = attributesKey(attributeValueRepoId);
		return AttributesKeyWithASI.of(attributesKey, dummyAsiId);
	}

	private static AttributesKey attributesKey(int attributeValueRepoId)
	{
		final AttributeValueId attributeValueId = AttributeValueId.ofRepoId(attributeValueRepoId);
		return AttributesKey.ofAttributeValueIds(attributeValueId);
	}

	private static class MockedCandidateHandler implements CandidateHandler
	{
		@Setter
		private int nextGroupId = 100001;

		@Getter
		private ArrayList<Candidate> savedCandidates = new ArrayList<>();

		@Override
		public Collection<CandidateType> getHandeledTypes()
		{
			return ImmutableList.of(CandidateType.ATTRIBUTES_CHANGED_FROM, CandidateType.ATTRIBUTES_CHANGED_TO);
		}

		@Override
		public Candidate onCandidateNewOrChange(
				@NonNull final Candidate candidate,
				final OnNewOrChangeAdvise onNewOrChangeAdvise)
		{
			final Candidate candidateToReturn = candidate.getGroupId() != null
					? candidate
					: candidate.withGroupId(generateGroupId());

			savedCandidates.add(candidateToReturn);

			return candidateToReturn;
		}

		private MaterialDispoGroupId generateGroupId()
		{
			return MaterialDispoGroupId.ofInt(nextGroupId++);
		}

		@Override
		public void onCandidateDelete(Candidate candidate)
		{
			throw new UnsupportedOperationException();
		}
	}
}
