/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.common.rest_api.v2.project.workorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonWorkOrderStepResponse
{
	@NonNull
	JsonMetasfreshId stepId;

	@NonNull
	String name;

	@NonNull
	JsonMetasfreshId projectId;

	@Nullable
	String description;

	@NonNull
	Integer seqNo;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateStart;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateEnd;

	@Nullable
	String externalId;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate woPartialReportDate;

	@Nullable
	Integer woPlannedResourceDurationHours;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate deliveryDate;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate woTargetStartDate;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate woTargetEndDate;

	@Nullable
	Integer woPlannedPersonDurationHours;

	@Nullable
	JsonWOStepStatus woStepStatus;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate woFindingsReleasedDate;

	@Nullable
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate woFindingsCreatedDate;

	@Nullable
	List<JsonWorkOrderResourceResponse> resources;
}
