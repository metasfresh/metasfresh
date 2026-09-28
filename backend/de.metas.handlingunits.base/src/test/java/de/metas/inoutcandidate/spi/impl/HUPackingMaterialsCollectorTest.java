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
import de.metas.project.ProjectId;
import de.metas.util.Services;
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
	 * Task https://github.com/metasfresh/metasfresh/issues/gh404 (Packing lines per project)
	 * <p>
	 * (a) flag ON, two sources with different projects (P1, P2) &rarr; the parent ends up with 2 candidates,
	 * each carrying its own single project. Goes parent &rarr; {@link HUPackingMaterialsCollector#splitNew()} &rarr;
	 * {@link HUPackingMaterialsCollector#addM_HU_PI} &rarr; {@link HUPackingMaterialsCollector#mergeBackToParentAndClear()},
	 * proving the flag survives {@code splitNew()} (Review Focus 4).
	 */
	@Test
	public void considerProject_on_twoSourcesDifferentProjects_yieldsTwoCandidatesEachWithItsProject()
	{
		final HUPackingMaterialsCollector parent = new HUPackingMaterialsCollector(data.helper.createMutableHUContext());
		parent.setConsiderProject(true);

		final HUPackingMaterialsCollector child = parent.splitNew();
		final IHUPackingMaterialCollectorSource sourceP1 = createSource(data.helper.pTomatoProductId.getRepoId(), 1, PROJECT_P1);
		final IHUPackingMaterialCollectorSource sourceP2 = createSource(data.helper.pTomatoProductId.getRepoId(), 2, PROJECT_P2);

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
	 * (b) flag OFF, same two sources as (a) &rarr; exactly 1 candidate with a null project — i.e. **byte-identical**
	 * to today's (pre-flag) behaviour. Pins the "OFF = today" invariant.
	 */
	@Test
	public void considerProject_off_twoSourcesDifferentProjects_yieldsOneCandidateWithNullProject()
	{
		final HUPackingMaterialsCollector parent = new HUPackingMaterialsCollector(data.helper.createMutableHUContext());
		// considerProject left at its default (false) on purpose: the key must stay exactly today's.

		final HUPackingMaterialsCollector child = parent.splitNew();
		final IHUPackingMaterialCollectorSource sourceP1 = createSource(data.helper.pTomatoProductId.getRepoId(), 1, PROJECT_P1);
		final IHUPackingMaterialCollectorSource sourceP2 = createSource(data.helper.pTomatoProductId.getRepoId(), 2, PROJECT_P2);

		child.addM_HU_PI(data.piTU_IFCO, 1, sourceP1);
		child.addM_HU_PI(data.piTU_IFCO, 1, sourceP2);
		child.mergeBackToParentAndClear();

		final List<HUPackingMaterialDocumentLineCandidate> candidates = parent.getAndClearCandidates();

		assertThat(candidates).hasSize(1);
		assertThat(candidates.get(0).getUniqueProjectIdOrNull()).isNull();
	}

	/**
	 * (c) flag ON, one source with no project and one source with P1 &rarr; 2 candidates (null, P1).
	 */
	@Test
	public void considerProject_on_oneSourceNoProjectOneSourceWithProject_yieldsTwoCandidates()
	{
		final HUPackingMaterialsCollector parent = new HUPackingMaterialsCollector(data.helper.createMutableHUContext());
		parent.setConsiderProject(true);

		final HUPackingMaterialsCollector child = parent.splitNew();
		final IHUPackingMaterialCollectorSource sourceNoProject = createSource(data.helper.pTomatoProductId.getRepoId(), 1, null);
		final IHUPackingMaterialCollectorSource sourceP1 = createSource(data.helper.pTomatoProductId.getRepoId(), 2, PROJECT_P1);

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
	 * (d) flag ON, based on {@link #test_AggregatedHU()}: two <b>distinct</b> aggregated HUs (a single one would be
	 * skipped the second time by the seen-set, {@code HUPackingMaterialsCollector.java:227}), collected from two
	 * sources with different projects. Their "included" packing material (only reached when
	 * {@code isCollectAggregatedHUs} is set, key site {@code HUPackingMaterialsCollector.java:273}, Review Focus 3)
	 * must ALSO be split per project, and give 2 candidates.
	 * <p>
	 * The second packing-material PI-item is added to {@code piTU_IFCO}'s PI version <b>after</b> both aggregated HUs
	 * were already built, so it is never materialized as a real {@code M_HU_Item} on either built HU and can only be
	 * reached through the "included packing material" lookup ({@code retrievePackingMaterials}) — isolating key site
	 * {@code :273} from the main key site {@code :258}.
	 */
	@Test
	public void considerProject_on_aggregatedHU_includedPackingMaterial_twoDistinctAggregatedHUs_yieldsTwoCandidates()
	{
		final I_M_HU luHU1 = createLU("1000");
		final I_M_HU luHU2 = createLU("1000");

		data.helper.createHU_PI_Item_PackingMaterial(data.piTU_IFCO, data.helper.pmBag);

		final HUPackingMaterialsCollector collector = new HUPackingMaterialsCollector(data.helper.createMutableHUContext());
		collector.setConsiderProject(true);
		collector.setisCollectAggregatedHUs(true);

		final IHUPackingMaterialCollectorSource sourceP1 = createSource(data.helper.pTomatoProductId.getRepoId(), 1, PROJECT_P1);
		final IHUPackingMaterialCollectorSource sourceP2 = createSource(data.helper.pTomatoProductId.getRepoId(), 2, PROJECT_P2);

		collector.releasePackingMaterialForHURecursively(luHU1, sourceP1);
		collector.releasePackingMaterialForHURecursively(luHU2, sourceP2);

		final List<HUPackingMaterialDocumentLineCandidate> candidates = collector.getAndClearCandidates();

		final List<HUPackingMaterialDocumentLineCandidate> includedPackingMaterialCandidates = candidates.stream()
				.filter(candidate -> candidate.getM_Product().getM_Product_ID() == data.helper.pmBag.getM_Product_ID())
				.collect(Collectors.toList());

		assertThat(includedPackingMaterialCandidates).hasSize(2);
		assertThat(includedPackingMaterialCandidates)
				.extracting(HUPackingMaterialDocumentLineCandidate::getUniqueProjectIdOrNull)
				.containsExactlyInAnyOrder(PROJECT_P1, PROJECT_P2);
	}

	/**
	 * (e) flag ON, the same TU HU released through two child collectors of two sources (P1, then P2) &rarr; its
	 * packing material must be counted <b>once</b>, not twice (seen-set: {@code HUPackingMaterialsCollector.java:227},
	 * {@code :767}, {@code :835}; REQUIREMENTS AC-9 "appears once"). This mirrors the builder's real
	 * split-per-line/merge-back sequence: split, process, merge, THEN split again — only then does the second
	 * child's seen-set snapshot already contain the first child's HU.
	 */
	@Test
	public void considerProject_on_sameTUReleasedThroughTwoChildCollectors_countedOnce()
	{
		final I_M_HU tuHU = createRealTU("10");

		final HUPackingMaterialsCollector parent = new HUPackingMaterialsCollector(data.helper.createMutableHUContext());
		parent.setConsiderProject(true);

		final IHUPackingMaterialCollectorSource sourceP1 = createSource(data.helper.pTomatoProductId.getRepoId(), 1, PROJECT_P1);
		final IHUPackingMaterialCollectorSource sourceP2 = createSource(data.helper.pTomatoProductId.getRepoId(), 2, PROJECT_P2);

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

	private IHUPackingMaterialCollectorSource createSource(final int productId, final int recordId, @Nullable final ProjectId projectId)
	{
		final I_M_InOutLine inoutLine = newInstance(I_M_InOutLine.class);
		inoutLine.setM_InOutLine_ID(recordId);
		inoutLine.setM_Product_ID(productId);
		if (projectId != null)
		{
			inoutLine.setC_Project_ID(projectId.getRepoId());
		}
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
