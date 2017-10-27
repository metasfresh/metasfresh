package de.metas.material.dispo.service.candidatechange.handler;

import static de.metas.material.event.EventTestHelper.CLIENT_ID;
import static de.metas.material.event.EventTestHelper.ORG_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.time.SystemTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.CandidateSpecification.Type;
import de.metas.material.dispo.DispoTestUtils;
import de.metas.material.dispo.candidate.Candidate;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.ProductDescriptorFactory;
import lombok.NonNull;
import mockit.Mocked;
import mockit.Verifications;

/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class DemandCandiateCangeHandlerTest
{
	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private final Date t1 = SystemTime.asDate();

	@Mocked
	private MaterialEventService materialEventService;

	private DemandCandiateHandler demandCandiateHandler;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final CandidateRepository candidateRepository = new CandidateRepository(ProductDescriptorFactory.TESTING_INSTANCE);

		final StockCandidateService stockCandidateService = new StockCandidateService(candidateRepository);

		demandCandiateHandler = new DemandCandiateHandler(candidateRepository, materialEventService, stockCandidateService);
	}

	@Test
	public void testOnDemandCandidateCandidateNewOrChange_noOlderRecords()
	{
		final BigDecimal qty = new BigDecimal("23");
		final Date t = t1;

		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(qty)
				.date(t)
				.build();

		final Candidate candidate = Candidate.builder()
				.type(Type.DEMAND)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescr(materialDescr)
				.build();
		demandCandiateHandler.onCandidateNewOrChange(candidate);

		final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
		assertThat(records.size(), is(2));
		final I_MD_Candidate stockRecord = DispoTestUtils.filter(Type.STOCK).get(0);
		final I_MD_Candidate demandRecord = DispoTestUtils.filter(Type.DEMAND).get(0);

		assertThat(demandRecord.getQty(), comparesEqualTo(qty));
		assertThat(stockRecord.getQty(), comparesEqualTo(qty.negate())); // ..because there was no older record, the "delta" we provided is the current quantity
		assertThat(stockRecord.getMD_Candidate_Parent_ID(), is(demandRecord.getMD_Candidate_ID()));

		assertThat(stockRecord.getSeqNo(), is(demandRecord.getSeqNo() + 1)); // when we sort by SeqNo, the demand needs to be first and thus have the smaller value
	}
	
	@Test
	public void decrease_stock()
	{
		final Candidate candidate = createCandidateWithType(Type.UNRELATED_DECREASE);
		
		demandCandiateHandler.onCandidateNewOrChange(candidate);

		final List<I_MD_Candidate> allRecords = DispoTestUtils.retrieveAllRecords();
		assertThat(allRecords).hasSize(2);

		final I_MD_Candidate unrelatedTransactionCandidate = allRecords.get(0);
		assertThat(unrelatedTransactionCandidate.getMD_Candidate_Type()).isEqualTo(X_MD_Candidate.MD_CANDIDATE_TYPE_UNRELATED_DECREASE);
		assertThat(unrelatedTransactionCandidate.getQty()).isEqualByComparingTo("10");

		final I_MD_Candidate stockCandidate = allRecords.get(1);
		assertThat(stockCandidate.getMD_Candidate_Type()).isEqualTo(X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK);
		assertThat(stockCandidate.getQty()).isEqualByComparingTo("-10");
		assertThat(stockCandidate.getMD_Candidate_Parent_ID()).isEqualTo(unrelatedTransactionCandidate.getMD_Candidate_ID());

		// verify that no event was fired
		new Verifications()
		{{
			materialEventService.fireEvent((MaterialEvent)any); times = 0;
			materialEventService.fireEventAfterNextCommit((MaterialEvent)any, anyString); times = 0;
		}}; // @formatter:on
	}
	
	private static Candidate createCandidateWithType(@NonNull final Type type)
	{
		final Candidate candidate = Candidate.builder()
				.type(type)
				.materialDescr(MaterialDescriptor.builder()
						.productDescriptor(createProductDescriptor())
						.date(SystemTime.asTimestamp())
						.warehouseId(WAREHOUSE_ID)
						.quantity(BigDecimal.TEN)
						.build())
				.build();
		return candidate;
	}
}
