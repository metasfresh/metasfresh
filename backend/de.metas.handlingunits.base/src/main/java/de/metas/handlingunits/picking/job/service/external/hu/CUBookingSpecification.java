package de.metas.handlingunits.picking.job.service.external.hu;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAssignment;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * Specification (Evans, Domain-Driven Design): how a picked HU was booked. The answer is derived from the HU's shape once, here, instead of
 * each caller guessing it from the HU.
 * <ul>
 * <li>A CU is <b>in a box</b> if it is a virtual HU whose parent is a TU.</li>
 * <li>A CU is <b>booked with a label scan</b> (label-based CU quantity entry mode, see {@code CUQtyEntryPolicy#isLabelRequired()}) if it is in a box
 * and its recorded QR code is its own. Manual books a whole box (a TU, not a CU) or a CU with the box's QR code, so a Manual pick never satisfies this.</li>
 * </ul>
 * No stored column: the booking kind is not persisted, it is derived from the HU and its QR code assignment.
 */
@RequiredArgsConstructor
public class CUBookingSpecification
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL;
	@NonNull private final HUQRCodesService huQRCodesService;

	/**
	 * @return true for a CU (virtual HU) whose parent is a TU (box); false for a top-level CU, for a CU directly on an LU (no box) and for an
	 * aggregated TU (also a virtual HU, but it stands for TUs on an LU)
	 */
	public boolean isCUInBox(@NonNull final I_M_HU hu)
	{
		return handlingUnitsBL.isVirtual(hu)
				&& !handlingUnitsBL.isAggregateHU(hu)
				&& hu.getM_HU_Item_Parent_ID() > 0
				&& getBox(hu).isPresent();
	}

	/** @return the TU the HU is in; empty if it is not in a TU (top-level, or directly on an LU) */
	@NonNull
	public Optional<I_M_HU> getBox(@NonNull final I_M_HU hu)
	{
		final I_M_HU tu = handlingUnitsBL.getTransportUnitHU(hu);
		return tu != null && handlingUnitsBL.isTransportUnit(tu) ? Optional.of(tu) : Optional.empty();
	}

	/** @return the ids of those picked HUs that are booked with a label scan: a CU in a box whose recorded QR code is its own */
	@NonNull
	public ImmutableSet<HuId> filterBookedWithLabelScan(@NonNull final Collection<HUInfo> pickedHUInfos)
	{
		return filterCUsInBox(filterAssignedToOwnQRCode(pickedHUInfos));
	}

	@NonNull
	public ImmutableSet<HuId> filterCUsInBox(@NonNull final Set<HuId> huIds)
	{
		if (huIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return handlingUnitsBL.getByIds(huIds).stream()
				.filter(this::isCUInBox)
				.map(hu -> HuId.ofRepoId(hu.getM_HU_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	/** @return the ids of those HUs whose recorded QR code is assigned to the HU itself (one batch query; a QR code shared by several HUs is no error) */
	@NonNull
	private ImmutableSet<HuId> filterAssignedToOwnQRCode(@NonNull final Collection<HUInfo> huInfos)
	{
		if (huInfos.isEmpty())
		{
			return ImmutableSet.of();
		}

		final ImmutableMap<HUQRCodeUniqueId, HUQRCodeAssignment> assignmentsByQRCodeId = Maps.uniqueIndex(
				huQRCodesService.getHUAssignmentsByQRCodeIfExist(huInfos.stream().map(HUInfo::getQrCode).collect(ImmutableSet.toImmutableSet())),
				HUQRCodeAssignment::getId);

		return huInfos.stream()
				.filter(huInfo -> {
					final HUQRCodeAssignment assignment = assignmentsByQRCodeId.get(huInfo.getQrCode().getId());
					return assignment != null && assignment.isAssignedToHuId(huInfo.getId());
				})
				.map(HUInfo::getId)
				.collect(ImmutableSet.toImmutableSet());
	}
}
