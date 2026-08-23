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

package de.metas.handlingunits.picking.job.model.facets.external_system;

import de.metas.externalsystem.ExternalSystemId;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacet;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetGroup;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@Value(staticConstructor = "of")
public class ExternalSystemFacet implements PickingJobFacet
{
	@NonNull PickingJobFacetGroup group = PickingJobFacetGroup.EXTERNAL_SYSTEM;
	@With boolean isActive;
	@NonNull ExternalSystemId externalSystemId;
	/** {@code ExternalSystem.Name} — what the operator reads, e.g. "Shopware 6", not the "Shopware6" code. */
	@NonNull String name;
}
