package de.metas.handlingunits.shipping;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IHUPackageDAO;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.shipping.mpackage.PackageId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Spring service for HU-level package queries used during shipment processing.
 */
@Service
public class HUPackageService
{
	private final IHUPackageDAO huPackageDAO = Services.get(IHUPackageDAO.class);
	private final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);

	/**
	 * For each package in {@code inOutIdByPackageId}, walks
	 * {@code M_Package_HU → top-level HU → M_HU_Assignment(M_InOutLine) → VHU → M_HU_Attribute(CountryOfOrigin)}
	 * using two bulk queries and returns a per-package map from InOutAndLineId to country-of-origin ISO code.
	 * A null value means the InOutLine belongs to the package but has no country set.
	 */
	@NonNull
	public Map<PackageId, Map<InOutAndLineId, String>> fetchCountryOfOriginByInOutLine(
			@NonNull final Map<PackageId, InOutId> inOutIdByPackageId)
	{
		if (inOutIdByPackageId.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableSet<PackageId> packageIds = ImmutableSet.copyOf(inOutIdByPackageId.keySet());

		// Step 1 — M_Package_HU bulk query: map top-level HU → PackageId / InOutId
		final Map<HuId, InOutId> inOutIdByTopHuId = new HashMap<>();
		final Map<HuId, PackageId> pkgIdByTopHuId = new HashMap<>();
		huPackageDAO.retrievePackageHUs(packageIds).forEach(pkgHU -> {
			final PackageId pkgId = PackageId.ofRepoId(pkgHU.getM_Package_ID());
			final InOutId inOutId = inOutIdByPackageId.get(pkgId);
			if (inOutId != null)
			{
				final HuId topHuId = HuId.ofRepoId(pkgHU.getM_HU_ID());
				inOutIdByTopHuId.put(topHuId, inOutId);
				pkgIdByTopHuId.put(topHuId, pkgId);
			}
		});

		final ImmutableSet<HuId> allTopPackageHuIds = ImmutableSet.copyOf(inOutIdByTopHuId.keySet());
		if (allTopPackageHuIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		// Step 2 — M_HU_Assignment bulk query filtered to M_InOutLine records
		final List<I_M_HU_Assignment> assignments = huAssignmentBL.retrieveAssignmentsForHUsAndTable(
				allTopPackageHuIds, I_M_InOutLine.Table_Name);
		if (assignments.isEmpty())
		{
			return ImmutableMap.of();
		}

		// Step 3 — load all VHUs in one query
		final ImmutableSet<HuId> vhuIds = assignments.stream()
				.map(a -> HuId.ofRepoIdOrNull(a.getVHU_ID()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		final Map<HuId, I_M_HU> vhuById = vhuIds.isEmpty()
				? ImmutableMap.of()
				: handlingUnitsDAO.getByIds(vhuIds).stream()
						.collect(ImmutableMap.toImmutableMap(
								hu -> HuId.ofRepoId(hu.getM_HU_ID()),
								hu -> hu));

		// Step 4 — build per-package InOutLine → CountryOfOrigin map
		final HashMap<PackageId, Map<InOutAndLineId, String>> result = new HashMap<>();
		for (final I_M_HU_Assignment assignment : assignments)
		{
			final HuId topHuId = HuId.ofRepoId(assignment.getM_HU_ID());
			final InOutId assignmentInOutId = inOutIdByTopHuId.get(topHuId);
			if (assignmentInOutId == null) { continue; }
			final PackageId pkgId = pkgIdByTopHuId.get(topHuId);
			if (pkgId == null) { continue; }
			final InOutAndLineId inOutAndLineId = InOutAndLineId.ofRepoId(assignmentInOutId, assignment.getRecord_ID());
			final Map<InOutAndLineId, String> pkgMap = result.computeIfAbsent(pkgId, k -> new LinkedHashMap<>());
			if (pkgMap.containsKey(inOutAndLineId)) { continue; } // first assignment wins per InOutLine
			final HuId vhuId = HuId.ofRepoIdOrNull(assignment.getVHU_ID());
			final I_M_HU vhu = vhuId != null ? vhuById.get(vhuId) : null;
			final String coo = vhu != null
					? huAttributesBL.getHUAttributeValue(vhu, HUAttributeConstants.ATTR_CountryOfOrigin)
					: null;
			pkgMap.put(inOutAndLineId, coo); // null = belongs to package but country not set
		}
		return result;
	}
}
