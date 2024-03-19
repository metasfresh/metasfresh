/*
 * #%L
 * de.metas.workflow.rest-api
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.workflow.rest_api.controller.v2.json;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class JsonLaunchersQuery
{
	@NonNull MobileApplicationId applicationId;
	@Nullable GlobalQRCode filterByQRCode;
	@Nullable String filterByDocumentNo;
	@Nullable Set<WorkflowLaunchersFacetId> facetIds;

	boolean countOnly;
}
