/*
 * #%L
 * de.metas.business
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

package de.metas.shipping.mpackage;

import com.google.common.collect.ImmutableSet;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import lombok.NonNull;

/**
 * Optionally restricts which InOutLines are included as content items for a given package.
 * <p>
 * Implemented in de.metas.handlingunits to apply HU-based filtering when multiple packages
 * share the same InOut.
 */
@FunctionalInterface
public interface IPackageContentProvider
{
	/**
	 * @return the {@link InOutAndLineId}s to include for this package,
	 * or an empty set if all InOutLines of the InOut should be included.
	 */
	ImmutableSet<InOutAndLineId> getInOutLineIdsForPackage(
			@NonNull PackageId packageId,
			@NonNull InOutId inOutId);
}
