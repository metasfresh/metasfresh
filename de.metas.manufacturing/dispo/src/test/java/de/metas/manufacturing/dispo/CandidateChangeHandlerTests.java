package de.metas.manufacturing.dispo;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.uom.api.Quantity;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.manufacturing.dispo.Candidate.Type;

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
public class CandidateChangeHandlerTests
{
	final Date now = SystemTime.asDate();
	final Date earlier = TimeUtil.addMinutes(now, -10);
	final Date later = TimeUtil.addMinutes(now, 10);

	private I_M_Product product;

	private I_M_Locator locator;

	private I_C_UOM uom;

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private CandidateChangeHandler testee;// = CandidateChangeHandler.get();

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
	}

	@Test
	public void test()
	{
		candidateRepository.reset();

		final Candidate candidate = Candidate.builder()
				.type(Type.STOCK)
				.product(product)
				.locator(locator)
				.quantity(new Quantity(new BigDecimal("10"), uom))
				.projectedDate(now)
				.build();
		candidateRepository.add(candidate);

		final Candidate earlierCandidate = Candidate.builder()
				.type(Type.STOCK)
				.product(product)
				.locator(locator)
				.quantity(new Quantity(new BigDecimal("10"), uom))
				.projectedDate(earlier)
				.build();
		candidateRepository.add(earlierCandidate);

		final Candidate laterCandidate = Candidate.builder()
				.type(Type.STOCK)
				.product(product)
				.locator(locator)
				.quantity(new Quantity(new BigDecimal("10"), uom))
				.projectedDate(later)
				.build();
		candidateRepository.add(laterCandidate);

		testee.projectedStockChange(
				mkSegment(now),
				new Quantity(new BigDecimal("3"), uom));

		final Optional<Candidate> earlierCandidateAfterChange = candidateRepository.retrieveStockAt(mkSegment(earlier));
		assertThat(earlierCandidateAfterChange.isPresent(), is(true));
		assertThat(earlierCandidateAfterChange.get().getQuantity(), is(earlierCandidate.getQuantity())); // quantity shall be unchanged

		final Optional<Candidate> candidateAfterChange = candidateRepository.retrieveStockAt(mkSegment(now));
		assertThat(candidateAfterChange.isPresent(), is(true));
		assertThat(candidateAfterChange.get().getQuantity(), is(new Quantity(new BigDecimal("13"), uom))); // quantity shall be plus 3

		final Optional<Candidate> laterCandidateAfterChange = candidateRepository.retrieveStockAt(mkSegment(later));
		assertThat(laterCandidateAfterChange.isPresent(), is(true));
		assertThat(laterCandidateAfterChange.get().getQuantity(), is(new Quantity(new BigDecimal("13"), uom))); // quantity shall be plus 3

	}

	private CandidatesSegment mkSegment(final Date timestamp)
	{
		return CandidatesSegment.builder().product(product).locator(locator).projectedDate(timestamp).build();
	}
}
