package de.metas.inoutcandidate.spi.impl;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.handlingunits.spi.impl.HUPackingMaterialDocumentLineCandidate;
import de.metas.handlingunits.spi.impl.HUPackingMaterialsCollector;
import de.metas.inout.InOutLineId;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class HUPackingMaterialsCollectorTest
{
	private static final ProjectId PROJECT_P1 = ProjectId.ofRepoId(1_000_001);
	private static final ProjectId PROJECT_P2 = ProjectId.ofRepoId(1_000_002);

	private LUTUProducerDestinationTestSupport data;
	private IHandlingUnitsDAO handlingUnitsDAO;
	private IHandlingUnitsBL handlingUnitsBL;

	@BeforeEach
	public void Init()
	{
		data = new LUTUProducerDestinationTestSupport();
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	}

	/**
	 * Task https://github.com/metasfresh/metasfresh/issues/1164
	 */
	@Test
	public void test_AggregatedHU()
	{
		//
		// Create an aggregated LU with more than one TU inside
		final I_M_HU luHU = createLU("1000");
		final int countTUs = extractAggregatedTUsCount(luHU);
		assertThat(countTUs).isGreaterThan(1);

		//
		// Create a packing materials collector and collect the LU we just create it
		final HUPackingMaterialsCollector collector = new HUPackingMaterialsCollector(data.helper.createMutableHUContext());
		final IHUPackingMaterialCollectorSource source = null; // N/A
		collector.releasePackingMaterialForHURecursively(luHU, source);

		//
		// Assert collected TUs counter is OK.
		final int collectedCountTUs = collector.getAndResetCountTUs();
		assertThat(collectedCountTUs).isEqualTo(countTUs);
	}

	private I_M_HU createLU(final String totalQtyCU)
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUItemPI(data.piLU_Item_IFCO);
		lutuProducer.setLUPI(data.piLU);
		lutuProducer.setTUPI(data.piTU_IFCO);
		lutuProducer.setMaxTUsPerLU(Integer.MAX_VALUE); // allow as many TUs on that one palette as we want
		data.helper.load(lutuProducer, data.helper.pTomatoProductId, new BigDecimal(totalQtyCU), data.helper.uomKg);
		final List<I_M_HU> createdLUs = lutuProducer.getCreatedHUs();

		assertThat(createdLUs.size()).isEqualTo(1);
		return createdLUs.get(0);
	}

	private int extractAggregatedTUsCount(final I_M_HU luHU)
	{
		final List<I_M_HU> aggregatedHUs = handlingUnitsDAO.retrieveIncludedHUs(luHU);
		assertThat(aggregatedHUs).hasSize(1);

		final I_M_HU aggregatedHU = aggregatedHUs.get(0);
		assertThat(handlingUnitsBL.isAggregateHU(aggregatedHU)).isTrue();

		assertThat(aggregatedHU.getM_HU_Item_Parent().getM_HU_PI_Item_ID()).isEqualTo(data.piLU_Item_IFCO.getM_HU_PI_Item_ID());

		return handlingUnitsDAO.retrieveParentItem(aggregatedHU).getQty().intValueExact();
	}

	/**
	 * Flag ON, set on the parent and carried through splitNew()/merge: sources of two projects give one candidate per project.
	 */
	@Test
	public void considerProject_on_twoSourcesDifferentProjects_yieldsTwoCandidatesEachWithItsProject()
	{
		final HUPackingMaterialsCollector parent = new HUPackingMaterialsCollector(data.helper.createMutableHUContext());
		parent.setConsiderProject(true);

		final HUPackingMaterialsCollector child = parent.splitNew();
		final IHUPackingMaterialCollectorSource sourceP1 = createSource(data.helper.pTomatoProductId, InOutLineId.ofRepoId(1), PROJECT_P1);
		final IHUPackingMaterialCollectorSource sourceP2 = createSource(data.helper.pTomatoProductId, InOutLineId.ofRepoId(2), PROJECT_P2);

		child.addM_HU_PI(data.piTU_IFCO, 1, sourceP1);
		child.addM_HU_PI(data.piTU_IFCO, 1, sourceP2);
		child.mergeBackToParentAndClear();

		final List<HUPackingMaterialDocumentLineCandidate> candidates = parent.getAndClearCandidates();

		assertThat(candidates).hasSize(2);
		assertThat(candidates)
				.extracting(HUPackingMaterialDocumentLineCandidate::getUniqueProjectIdOrNull)
				.containsExactlyInAnyOrder(PROJECT_P1, PROJECT_P2);
	}

	/**
	 * Flag OFF: sources of two projects give one candidate without project, keyed by product, locator and material tracking only.
	 */
	@Test
	public void considerProject_off_twoSourcesDifferentProjects_yieldsOneCandidateWithNullProject()
	{
		final HUPackingMaterialsCollector parent = new HUPackingMaterialsCollector(data.helper.createMutableHUContext());
		// considerProject deliberately left at its default (false).

		final HUPackingMaterialsCollector child = parent.splitNew();
		final IHUPackingMaterialCollectorSource sourceP1 = createSource(data.helper.pTomatoProductId, InOutLineId.ofRepoId(1), PROJECT_P1);
		final IHUPackingMaterialCollectorSource sourceP2 = createSource(data.helper.pTomatoProductId, InOutLineId.ofRepoId(2), PROJECT_P2);

		child.addM_HU_PI(data.piTU_IFCO, 1, sourceP1);
		child.addM_HU_PI(data.piTU_IFCO, 1, sourceP2);
		child.mergeBackToParentAndClear();

		final HUPackingMaterialDocumentLineCandidate candidate = parent.getKey2candidates().values().iterator().next();
		assertThat(parent.getKey2candidates().keySet())
				.containsExactly(Util.mkKey(candidate.getProductId().getRepoId(), -1, -1));

		final List<HUPackingMaterialDocumentLineCandidate> candidates = parent.getAndClearCandidates();

		assertThat(candidates).hasSize(1);
		assertThat(candidates.get(0).getUniqueProjectIdOrNull()).isNull();
	}

	/**
	 * Flag ON: a source without project and one with a project give two candidates.
	 */
	@Test
	public void considerProject_on_oneSourceNoProjectOneSourceWithProject_yieldsTwoCandidates()
	{
		final HUPackingMaterialsCollector parent = new HUPackingMaterialsCollector(data.helper.createMutableHUContext());
		parent.setConsiderProject(true);

		final HUPackingMaterialsCollector child = parent.splitNew();
		final IHUPackingMaterialCollectorSource sourceNoProject = createSource(data.helper.pTomatoProductId, InOutLineId.ofRepoId(1), null);
		final IHUPackingMaterialCollectorSource sourceP1 = createSource(data.helper.pTomatoProductId, InOutLineId.ofRepoId(2), PROJECT_P1);

		child.addM_HU_PI(data.piTU_IFCO, 1, sourceNoProject);
		child.addM_HU_PI(data.piTU_IFCO, 1, sourceP1);
		child.mergeBackToParentAndClear();

		final List<HUPackingMaterialDocumentLineCandidate> candidates = parent.getAndClearCandidates();

		assertThat(candidates).hasSize(2);
		assertThat(candidates)
				.extracting(HUPackingMaterialDocumentLineCandidate::getUniqueProjectIdOrNull)
				.containsExactlyInAnyOrder(null, PROJECT_P1);
	}

	/**
	 * Flag ON: the included packing material of two distinct aggregated HUs of two projects is split per project too.
	 * The extra packing-material PI item is added after the HUs were built, so it is reachable only via the included-packing-material lookup.
	 */
	@Test
	public void considerProject_on_aggregatedHU_includedPackingMaterial_twoDistinctAggregatedHUs_yieldsTwoCandidates()
	{
		final I_M_HU luHU1 = createLU("1000");
		final I_M_HU luHU2 = createLU("1000");
		stampMaterialItemProductOnAggregatedHU(luHU1);
		stampMaterialItemProductOnAggregatedHU(luHU2);

		data.helper.createHU_PI_Item_PackingMaterial(data.piTU_IFCO, data.helper.pmBag);

		final HUPackingMaterialsCollector collector = new HUPackingMaterialsCollector(data.helper.createMutableHUContext());
		collector.setConsiderProject(true);
		collector.setisCollectAggregatedHUs(true);

		final IHUPackingMaterialCollectorSource sourceP1 = createSource(data.helper.pTomatoProductId, InOutLineId.ofRepoId(1), PROJECT_P1);
		final IHUPackingMaterialCollectorSource sourceP2 = createSource(data.helper.pTomatoProductId, InOutLineId.ofRepoId(2), PROJECT_P2);

		collector.releasePackingMaterialForHURecursively(luHU1, sourceP1);
		collector.releasePackingMaterialForHURecursively(luHU2, sourceP2);

		final List<HUPackingMaterialDocumentLineCandidate> candidates = collector.getAndClearCandidates();

		final ProductId bagProductId = ProductId.ofRepoId(data.helper.pmBag.getM_Product_ID());
		final List<HUPackingMaterialDocumentLineCandidate> includedPackingMaterialCandidates = candidates.stream()
				.filter(candidate -> ProductId.equals(candidate.getProductId(), bagProductId))
				.collect(Collectors.toList());

		assertThat(includedPackingMaterialCandidates).hasSize(2);
		assertThat(includedPackingMaterialCandidates)
				.extracting(HUPackingMaterialDocumentLineCandidate::getUniqueProjectIdOrNull)
				.containsExactlyInAnyOrder(PROJECT_P1, PROJECT_P2);
	}

	/**
	 * Flag ON: the same TU released through two consecutive child collectors (split, merge, split again) is counted once.
	 */
	@Test
	public void considerProject_on_sameTUReleasedThroughTwoChildCollectors_countedOnce()
	{
		final I_M_HU tuHU = createRealTU("10");

		final HUPackingMaterialsCollector parent = new HUPackingMaterialsCollector(data.helper.createMutableHUContext());
		parent.setConsiderProject(true);

		final IHUPackingMaterialCollectorSource sourceP1 = createSource(data.helper.pTomatoProductId, InOutLineId.ofRepoId(1), PROJECT_P1);
		final IHUPackingMaterialCollectorSource sourceP2 = createSource(data.helper.pTomatoProductId, InOutLineId.ofRepoId(2), PROJECT_P2);

		final HUPackingMaterialsCollector child1 = parent.splitNew();
		child1.releasePackingMaterialForTU(tuHU, sourceP1);
		child1.mergeBackToParentAndClear();

		final HUPackingMaterialsCollector child2 = parent.splitNew();
		child2.releasePackingMaterialForTU(tuHU, sourceP2);
		child2.mergeBackToParentAndClear();

		final List<HUPackingMaterialDocumentLineCandidate> candidates = parent.getAndClearCandidates();

		assertThat(candidates).hasSize(1);
		assertThat(candidates.get(0).getUniqueProjectIdOrNull()).isEqualTo(PROJECT_P1);
	}

	/**
	 * {@code LUTUProducerDestination} does not set the aggregated HU's {@code M_HU_PI_Item_Product_ID}, which the included-packing-material lookup needs.
	 */
	private void stampMaterialItemProductOnAggregatedHU(final I_M_HU luHU)
	{
		final List<I_M_HU> aggregatedHUs = handlingUnitsDAO.retrieveIncludedHUs(luHU);
		assertThat(aggregatedHUs).hasSize(1);

		final I_M_HU aggregatedHU = aggregatedHUs.get(0);
		aggregatedHU.setM_HU_PI_Item_Product_ID(data.piTU_Item_Product_IFCO_40KgTomatoes.getM_HU_PI_Item_Product_ID());
		InterfaceWrapperHelper.save(aggregatedHU);
	}

	private IHUPackingMaterialCollectorSource createSource(@NonNull final ProductId productId, @NonNull final InOutLineId inOutLineId, @Nullable final ProjectId projectId)
	{
		final I_M_InOutLine inoutLine = newInstance(I_M_InOutLine.class);
		inoutLine.setM_InOutLine_ID(inOutLineId.getRepoId());
		inoutLine.setM_Product_ID(productId.getRepoId());
		inoutLine.setC_Project_ID(ProjectId.toRepoId(projectId));
		return InOutLineHUPackingMaterialCollectorSource.of(inoutLine);
	}

	private I_M_HU createRealTU(final String totalQtyCU)
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLocatorId(data.defaultLocatorId);
		lutuProducer.setNoLU();
		lutuProducer.setTUPI(data.piTU_IFCO);
		data.helper.load(lutuProducer, data.helper.pTomatoProductId, new BigDecimal(totalQtyCU), data.helper.uomKg);
		final List<I_M_HU> createdTUs = lutuProducer.getCreatedHUs();

		assertThat(createdTUs.size()).isEqualTo(1);
		return createdTUs.get(0);
	}

}
