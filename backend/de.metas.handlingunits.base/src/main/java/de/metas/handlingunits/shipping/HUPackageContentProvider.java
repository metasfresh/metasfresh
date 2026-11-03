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
import de.metas.handlingunits.IHUPackageDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.shipping.mpackage.IPackageContentProvider;
import de.metas.shipping.mpackage.PackageId;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

/**
 * HU-aware implementation of {@link IPackageContentProvider}.
 *
 * Restricts package content items to the shipment lines the package actually holds, recorded as
 * {@code M_PackageLine} (written per shipped line by {@code HUPackageBL.createPackageLines}). Returns an empty
 * set (= no filter) when the package has no lines recorded.
 */
@Service
public class HUPackageContentProvider implements IPackageContentProvider
{
	private final IHUPackageDAO huPackageDAO = Services.get(IHUPackageDAO.class);

	@Override
	public ImmutableSet<InOutAndLineId> getInOutLineIdsForPackage(
			@NonNull final PackageId packageId,
			@NonNull final InOutId inOutId)
	{
		return huPackageDAO.retrieveInOutLineIdsForPackage(packageId)
				.stream()
				.map(lineId -> InOutAndLineId.ofRepoId(inOutId, lineId.getRepoId()))
				.collect(ImmutableSet.toImmutableSet());
	}
}
