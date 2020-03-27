/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.serviceprovider.importer.info;

import de.metas.organization.OrgId;
import de.metas.serviceprovider.external.issuedetails.ExternalIssueDetail;
import de.metas.serviceprovider.external.project.ExternalProjectType;
import de.metas.serviceprovider.milestone.Milestone;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class ImportIssueInfo
{
	@NonNull
	private OrgId orgId;

	@NonNull
	private ExternalProjectType externalProjectType;

	private BigDecimal estimation;

	private BigDecimal budget;

	@NonNull
	private UomId effortUomId;

	@NonNull
	private String name;

	private String description;

	private boolean processed;

	private UserId assigneeId;

	@NonNull
	private String externalIssueId;

	private String externalIssueNo;

	private String externalIssueURL;

	@Nullable
	Milestone milestone;

	@Nullable
	List<ExternalIssueDetail> externalIssueDetails;
}
