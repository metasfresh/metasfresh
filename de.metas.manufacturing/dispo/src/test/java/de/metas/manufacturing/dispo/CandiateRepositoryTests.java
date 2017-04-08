package de.metas.manufacturing.dispo;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
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

import de.metas.manufacturing.dispo.Candidate.Type;
import de.metas.quantity.Quantity;

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

	/** Watches current test and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private final Date now = SystemTime.asDate();
	private final Date earlier = TimeUtil.addMinutes(now, -10);
	private final Date later = TimeUtil.addMinutes(now, +10);

	private I_M_Product product;

	private I_M_Locator locator;

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

		locator = InterfaceWrapperHelper.newInstance(I_M_Locator.class);
		InterfaceWrapperHelper.save(locator);

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);

		// this not-stock candidate needs to be ignored
		final Candidate someOtherCandidate = Candidate.builder()
				.type(Type.DEMAND)
				.product(product)
				.locator(locator)
				.quantity(new Quantity(new BigDecimal("11"), uom))
				.date(now)
				.build();
		candidateRepository.add(someOtherCandidate);

		stockCandidate = Candidate.builder()
				.type(Type.STOCK)
				.product(product)
				.locator(locator)
				.quantity(new Quantity(new BigDecimal("10"), uom))
				.date(now)
				.build();
		candidateRepository.add(stockCandidate);

		laterStockCandidate = Candidate.builder()
				.type(Type.STOCK)
				.product(product)
				.locator(locator)
				.quantity(new Quantity(new BigDecimal("10"), uom))
				.date(later)
				.build();
		candidateRepository.add(laterStockCandidate);
	}

	/**
	 * Verifies that a candidate can be replaced with another candidate that has the same product, type, timestamp and locator.
	 */
	@Test
	public void add_update()
	{
		// guard
		assertThat(candidateRepository.retrieveStockAt(mkQuery(now)).get(), is(stockCandidate));
		final List<Candidate> stockBeforeReplacement = candidateRepository.retrieveStockFrom(mkQuery(now));
		assertThat(stockBeforeReplacement.size(), is(2));
		assertThat(stockBeforeReplacement, contains(stockCandidate, laterStockCandidate));

		final Candidate replacementCandidate = stockCandidate.withOtherQuantity(new Quantity(BigDecimal.ONE, uom));
		candidateRepository.add(replacementCandidate);

		assertThat(candidateRepository.retrieveStockAt(mkQuery(now)).get(), is(replacementCandidate));
		final List<Candidate> stockAfterReplacement = candidateRepository.retrieveStockFrom(mkQuery(now));
		assertThat(stockAfterReplacement.size(), is(2));
		assertThat(stockAfterReplacement, contains(replacementCandidate, laterStockCandidate));
	}

	/**
	 * Verifies that {@link CandidateRepository#retrieveStockAt(CandidatesSegment)} returns the oldest stock candidate with a date before the given {@link CandidatesSegment}'s date
	 */
	@Test
	public void retrieveStockAt()
	{
		final CandidatesSegment earlierQuery = mkQuery(earlier);
		final Optional<Candidate> earlierStock = candidateRepository.retrieveStockAt(earlierQuery);
		assertThat(earlierStock.isPresent(), is(false));

		final CandidatesSegment sameTimeQuery = mkQuery(now);
		final Optional<Candidate> sameTimeStock = candidateRepository.retrieveStockAt(sameTimeQuery);
		assertThat(sameTimeStock.isPresent(), is(true));
		assertThat(sameTimeStock.get(), is(stockCandidate));

		final CandidatesSegment laterQuery = mkQuery(later);
		final Optional<Candidate> laterStock = candidateRepository.retrieveStockAt(laterQuery);
		assertThat(laterStock.isPresent(), is(true));
		assertThat(laterStock.get(), is(laterStockCandidate));
	}

	@Test
	public void retrieveStockFrom()
	{
		{
			final CandidatesSegment earlierQuery = mkQuery(earlier);

			final List<Candidate> stockFrom = candidateRepository.retrieveStockFrom(earlierQuery);
			assertThat(stockFrom.size(), is(2));
			assertThat(stockFrom.contains(stockCandidate), is(true));
			assertThat(stockFrom.contains(laterStockCandidate), is(true));
		}
		{
			final CandidatesSegment sameTimeQuery = mkQuery(now);

			final List<Candidate> stockFrom = candidateRepository.retrieveStockFrom(sameTimeQuery);
			assertThat(stockFrom.size(), is(2));
			assertThat(stockFrom.contains(stockCandidate), is(true));
			assertThat(stockFrom.contains(laterStockCandidate), is(true));
		}

		{
			final CandidatesSegment laterQuery = mkQuery(later);

			final List<Candidate> stockFrom = candidateRepository.retrieveStockFrom(laterQuery);
			assertThat(stockFrom.size(), is(1));
			assertThat(stockFrom.contains(stockCandidate), is(false));
			assertThat(stockFrom.contains(laterStockCandidate), is(true));
		}
	}

	private CandidatesSegment mkQuery(final Date later)
	{
		return CandidatesSegment.builder()
				.product(product)
				.locator(locator)
				.projectedDate(later)
				.build();
	}

}
