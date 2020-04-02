package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger;

import de.metas.util.lang.RepoIdAware;

/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

<<<<<<< HEAD:de.metas.serviceprovider/src/main/java/de/metas/serviceprovider/external/project/ExternalProjectReference.java
package de.metas.serviceprovider.external.project;

import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class ExternalProjectReference
{
	@NonNull
	ExternalProjectType externalProjectType;

	@NonNull
	String projectOwner;

	@NonNull
	String externalProjectReference;

	@NonNull
	OrgId orgId;

	@Nullable
	ProjectId projectId;
=======
public class PlainTriggerDocumentId implements CommissionTriggerDocumentId
{
	public static PlainTriggerDocumentId INSTANCE = new PlainTriggerDocumentId();

	private PlainTriggerDocumentId()
	{
	}

	@Override
	public RepoIdAware getRepoIdAware()
	{
		throw new UnsupportedOperationException();
	}

>>>>>>> origin/5.134_grave_goldstine:de.metas.contracts/src/main/java/de/metas/contracts/commission/commissioninstance/businesslogic/sales/commissiontrigger/PlainTriggerDocumentId.java
}
