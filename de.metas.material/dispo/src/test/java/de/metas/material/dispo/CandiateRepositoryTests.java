package de.metas.material.dispo;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.CandidatesSegment;
import de.metas.material.dispo.Candidate.Type;

/*
 * #%L
 * metasfresh-manufacturing-dispo
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

@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("test")
public class CandiateRepositoryTests
{

	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private final Date now = SystemTime.asDate();
	private final Date earlier = TimeUtil.addMinutes(now, -10);
	private final Date later = TimeUtil.addMinutes(now, +10);

	private I_M_Product product;

	private I_M_Warehouse warehouse;

	private I_C_UOM uom;

	private Candidate stockCandidate;
	private Candidate laterStockCandidate;

	@Autowired
	private CandidateRepository candidateRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		InterfaceWrapperHelper.save(product);

		warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		InterfaceWrapperHelper.save(warehouse);

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);

		// this not-stock candidate needs to be ignored
		final Candidate someOtherCandidate = Candidate.builder()
				.type(Type.DEMAND)
				.orgId(1)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(new BigDecimal("11"))
				.date(now)
				.build();
		candidateRepository.addOrReplace(someOtherCandidate);

		stockCandidate = Candidate.builder()
				.type(Type.STOCK)
				.orgId(1)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(new BigDecimal("10"))
				.date(now)
				.build();
		candidateRepository.addOrReplace(stockCandidate);

		laterStockCandidate = Candidate.builder()
				.type(Type.STOCK)
				.orgId(1)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(new BigDecimal("10"))
				.date(later)
				.build();
		candidateRepository.addOrReplace(laterStockCandidate);
	}

	/**
	 * Verifies that a candidate can be replaced with another candidate that has the same product, type, timestamp and locator.
	 */
	@Test
	public void add_update()
	{
		// guard
		assertThat(candidateRepository.retrieveStockAt(mkSegment(now)).isPresent(), is(true));
		assertThat(toCandidateWithoutIds(candidateRepository.retrieveStockAt(mkSegment(now)).get()), is(stockCandidate));
		final List<Candidate> stockBeforeReplacement = candidateRepository.retrieveStockFrom(mkSegment(now));
		assertThat(stockBeforeReplacement.size(), is(2));
		assertThat(stockBeforeReplacement.stream().map(c -> toCandidateWithoutIds(c)).collect(Collectors.toList()), contains(stockCandidate, laterStockCandidate));

		final Candidate replacementCandidate = stockCandidate.withQuantity(BigDecimal.ONE);
		candidateRepository.addOrReplace(replacementCandidate);

		assertThat(toCandidateWithoutIds(candidateRepository.retrieveStockAt(mkSegment(now)).get()), is(replacementCandidate));
		final List<Candidate> stockAfterReplacement = candidateRepository.retrieveStockFrom(mkSegment(now));
		assertThat(stockAfterReplacement.size(), is(2));
		assertThat(stockAfterReplacement.stream().map(c -> toCandidateWithoutIds(c)).collect(Collectors.toList()), contains(replacementCandidate, laterStockCandidate));
	}

	/**
	 * Verifies that {@link CandidateRepository#retrieveStockAt(CandidatesSegment)} returns the oldest stock candidate with a date before the given {@link CandidatesSegment}'s date
	 */
	@Test
	public void retrieveStockAt()
	{
		final CandidatesSegment earlierQuery = mkSegment(earlier);
		final Optional<Candidate> earlierStock = candidateRepository.retrieveStockAt(earlierQuery);
		assertThat(earlierStock.isPresent(), is(false));

		final CandidatesSegment sameTimeQuery = mkSegment(now);
		final Optional<Candidate> sameTimeStock = candidateRepository.retrieveStockAt(sameTimeQuery);
		assertThat(sameTimeStock.isPresent(), is(true));
		assertThat(toCandidateWithoutIds(sameTimeStock.get()), is(stockCandidate));

		final CandidatesSegment laterQuery = mkSegment(later);
		final Optional<Candidate> laterStock = candidateRepository.retrieveStockAt(laterQuery);
		assertThat(laterStock.isPresent(), is(true));
		assertThat(toCandidateWithoutIds(laterStock.get()), is(laterStockCandidate));
	}

	/**
	 * 
	 * @param candidate
	 * @return returns a version of the given candidate that has {@code null}-Ids; background: we need to "dump it down" to be comparable with the "original"
	 */
	private Candidate toCandidateWithoutIds(final Candidate candidate)
	{
		return candidate.withId(null).withParentId(null);
	}

	@Test
	public void retrieveStockFrom()
	{
		{
			final CandidatesSegment earlierQuery = mkSegment(earlier);

			final List<Candidate> stockFrom = candidateRepository.retrieveStockFrom(earlierQuery);
			assertThat(stockFrom.size(), is(2));

			// what we retrieved, but without the IDs. To be used to compare with our "originals"
			final List<Candidate> stockFromWithOutIds = stockFrom.stream().map(from -> toCandidateWithoutIds(from)).collect(Collectors.toList());

			assertThat(stockFromWithOutIds.contains(stockCandidate), is(true));
			assertThat(stockFromWithOutIds.contains(laterStockCandidate), is(true));
		}
		{
			final CandidatesSegment sameTimeQuery = mkSegment(now);

			final List<Candidate> stockFrom = candidateRepository.retrieveStockFrom(sameTimeQuery);
			assertThat(stockFrom.size(), is(2));

			final List<Candidate> stockFromWithOutIds = stockFrom.stream().map(from -> toCandidateWithoutIds(from)).collect(Collectors.toList());
			assertThat(stockFromWithOutIds.contains(stockCandidate), is(true));
			assertThat(stockFromWithOutIds.contains(laterStockCandidate), is(true));
		}

		{
			final CandidatesSegment laterQuery = mkSegment(later);

			final List<Candidate> stockFrom = candidateRepository.retrieveStockFrom(laterQuery);
			assertThat(stockFrom.size(), is(1));

			final List<Candidate> stockFromWithOutIds = stockFrom.stream().map(from -> from.withId(null).withParentId(null)).collect(Collectors.toList());
			assertThat(stockFromWithOutIds.contains(stockCandidate), is(false));
			assertThat(stockFromWithOutIds.contains(laterStockCandidate), is(true));
		}
	}

	private CandidatesSegment mkSegment(final Date later)
	{
		return CandidatesSegment.builder()
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.date(later)
				.build();
	}

	@Test
	public void retrieveStockViaReference()
	{
		final TableRecordReference reference = TableRecordReference.of("tableName", 123);
		candidateRepository.retrieveSingleStockFor(reference);
	}

}
