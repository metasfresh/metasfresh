/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.handlingunits.shipping;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUPackageDAO;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_Package_HU;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.shipping.mpackage.IPackageContentProvider;
import de.metas.shipping.mpackage.PackageId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Package;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * HU-aware implementation of {@link IPackageContentProvider}.
 *
 * Restricts package content items to InOutLines assigned to the package's HU(s) via
 * M_Package_HU → M_HU_Assignment. Safeguard: when the InOut has only one package,
 * no filtering is applied to preserve existing behaviour.
 */
@Service
public class HUPackageContentProvider implements IPackageContentProvider
{
	private final IHUPackageDAO huPackageDAO = Services.get(IHUPackageDAO.class);
	private final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);

	@Override
	public ImmutableSet<InOutAndLineId> getInOutLineIdsForPackage(
			@NonNull final PackageId packageId,
			@NonNull final InOutId inOutId)
	{
		final List<I_M_Package> packages = huPackageDAO.retrievePackagesForShipment(inOutId);
		if (packages.size() <= 1)
		{
			return ImmutableSet.of();
		}

		final I_M_Package pkg = packages.stream()
				.filter(p -> PackageId.equals(PackageId.ofRepoId(p.getM_Package_ID()), packageId))
				.findFirst()
				.orElse(null);
		if (pkg == null)
		{
			return ImmutableSet.of();
		}

		final ImmutableSet<HuId> packageHuIds = huPackageDAO.retrievePackageHUs(pkg)
				.stream()
				.map(I_M_Package_HU::getM_HU_ID)
				.map(HuId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		if (packageHuIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return huAssignmentBL.retrieveAssignmentsForHUsAndTable(packageHuIds, I_M_InOutLine.Table_Name)
				.stream()
				.map(I_M_HU_Assignment::getRecord_ID)
				.map(lineId -> InOutAndLineId.ofRepoId(inOutId, lineId))
				.collect(ImmutableSet.toImmutableSet());
	}
}
