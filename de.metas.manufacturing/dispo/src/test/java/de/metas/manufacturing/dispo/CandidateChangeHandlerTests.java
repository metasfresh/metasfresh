package de.metas.manufacturing.dispo;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
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

import de.metas.manufacturing.dispo.Candidate.Type;
import de.metas.manufacturing.dispo.model.I_MD_Candidate;

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
	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private final Date now = SystemTime.asDate();
	private final Date earlier = TimeUtil.addMinutes(now, -10);
	private final Date later = TimeUtil.addMinutes(now, 10);
	private final Date evenLater = TimeUtil.addMinutes(later, 10);

	private I_M_Product product;

	private I_M_Locator locator;

	private I_M_Locator otherLocator;

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

		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		InterfaceWrapperHelper.save(warehouse);

		locator = InterfaceWrapperHelper.newInstance(I_M_Locator.class);
		locator.setM_Warehouse(warehouse);
		InterfaceWrapperHelper.save(locator);

		otherLocator = InterfaceWrapperHelper.newInstance(I_M_Locator.class);
		otherLocator.setM_Warehouse(warehouse);
		InterfaceWrapperHelper.save(otherLocator);

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);
	}

	/**
	 * Verifies that {@link CandidateChangeHandler#applyDeltaToLaterStockCandidates(CandidatesSegment, BigDecimal)} applied the given delta to the right records.
	 * In particular, both records with a matching M_Locator_ID and without any M_Locator_ID shall be updated.
	 * Only records that have a <i>different</i> M_Locator_ID shall not be touched.
	 */
	@Test
	public void testOnStockCandidateChange()
	{
		final Candidate candidate = Candidate.builder()
				.type(Type.STOCK)
				.productId(product.getM_Product_ID())
				.locatorId(locator.getM_Locator_ID())
				.warehouseId(locator.getM_Warehouse_ID())
				.quantity(new BigDecimal("10"))
				.date(now)
				.build();
		candidateRepository.addOrReplace(candidate);

		final Candidate earlierCandidate = Candidate.builder()
				.type(Type.STOCK)
				.productId(product.getM_Product_ID())
				.locatorId(locator.getM_Locator_ID())
				.warehouseId(locator.getM_Warehouse_ID())
				.quantity(new BigDecimal("10"))
				.date(earlier)
				.build();
		candidateRepository.addOrReplace(earlierCandidate);

		final Candidate laterCandidate = Candidate.builder()
				.type(Type.STOCK)
				.productId(product.getM_Product_ID())
				.locatorId(locator.getM_Locator_ID())
				.warehouseId(locator.getM_Warehouse_ID())
				.quantity(new BigDecimal("10"))
				.date(later)
				.build();
		candidateRepository.addOrReplace(laterCandidate);

		final Candidate evenLaterCandidateWithoutLocator = Candidate.builder()
				.type(Type.STOCK)
				.productId(product.getM_Product_ID())
				.warehouseId(locator.getM_Warehouse_ID())
				.quantity(new BigDecimal("12"))
				.date(evenLater)
				.build();
		candidateRepository.addOrReplace(evenLaterCandidateWithoutLocator);

		final Candidate evenLaterCandidateWithDifferentLocator = Candidate.builder()
				.type(Type.STOCK)
				.productId(product.getM_Product_ID())
				.locatorId(otherLocator.getM_Locator_ID())
				.warehouseId(otherLocator.getM_Warehouse_ID())
				.quantity(new BigDecimal("12"))
				.date(evenLater)
				.build();
		candidateRepository.addOrReplace(evenLaterCandidateWithDifferentLocator);

		testee.applyDeltaToLaterStockCandidates(
				mkSegmentWithLocatorId(now, locator),
				new BigDecimal("3"));

		final Optional<Candidate> earlierCandidateAfterChange = candidateRepository.retrieveStockAt(mkSegmentWithLocatorId(earlier, locator));
		assertThat(earlierCandidateAfterChange.isPresent(), is(true));
		assertThat(earlierCandidateAfterChange.get().getQuantity(), is(earlierCandidate.getQuantity())); // quantity shall be unchanged

		final I_MD_Candidate candidateRecordAfterChange = candidateRepository.retrieveExact(candidate).get();
		assertThat(candidateRecordAfterChange.getQty(), is(new BigDecimal("10"))); // quantity shall be unchanged, because that method shall only update *later* records

		final Optional<Candidate> laterCandidateAfterChange = candidateRepository.retrieveStockAt(mkSegmentWithLocatorId(later, locator));
		assertThat(laterCandidateAfterChange.isPresent(), is(true));
		assertThat(laterCandidateAfterChange.get().getQuantity(), is(new BigDecimal("13"))); // quantity shall be plus 3

		final I_MD_Candidate evenLaterCandidateWithOutLocatorRecordAfterChange = candidateRepository.retrieveExact(evenLaterCandidateWithoutLocator).get();
		assertThat(evenLaterCandidateWithOutLocatorRecordAfterChange.getQty(), is(new BigDecimal("15"))); // quantity shall be plus 3 too, because this candidate without a locator should have been matched and updated too

		final I_MD_Candidate evenLaterCandidateWithDifferentLocatorRecordAfterChange = candidateRepository.retrieveExact(evenLaterCandidateWithDifferentLocator).get();
		assertThat(evenLaterCandidateWithDifferentLocatorRecordAfterChange.getQty(), is(new BigDecimal("12"))); // quantity shall be unchanged, because we changed another locator and this one should not have been matched
	}

	private CandidatesSegment mkSegmentWithLocatorId(final Date timestamp, final I_M_Locator locator)
	{
		return CandidatesSegment.builder()
				.productId(product.getM_Product_ID())
				.locatorId(locator.getM_Locator_ID())
				.warehouseId(locator.getM_Warehouse_ID())
				.projectedDate(timestamp)
				.build();
	}
}
