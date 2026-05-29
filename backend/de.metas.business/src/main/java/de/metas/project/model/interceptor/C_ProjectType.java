/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.project.model.interceptor;

import de.metas.i18n.AdMessageKey;
import de.metas.organization.OrgId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectTypeId;
import de.metas.project.ProjectTypeRepository;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_ProjectType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Callout(I_C_ProjectType.class)
@RequiredArgsConstructor
public class C_ProjectType
{

	private static final AdMessageKey MSG_C_PROJECT_TYPE_ONE_SOPO_PER_ORG = AdMessageKey.of("C_ProjectType_One_SOPO_Per_Org");
	private final ProjectTypeRepository typeRepository;

	@PostConstruct
	public void postConstruct()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_ProjectType.COLUMNNAME_ProjectCategory)
	public void uniqueSalesPurchaseOrderPerOrg(@NonNull final I_C_ProjectType projectType)
	{
		final ProjectCategory projectCategory = ProjectCategory.ofNullableCode(projectType.getProjectCategory());
		if (projectCategory == null || !projectCategory.isSalesPurchaseOrder())
		{
			return;
		}
		final OrgId orgId = OrgId.ofRepoId(projectType.getAD_Org_ID());
		final ProjectTypeId currentOrderProjectTypeIdInOrg = typeRepository.getFirstIdByProjectCategoryAndOrgOrNull(projectCategory, orgId, true);
		if (currentOrderProjectTypeIdInOrg != null && currentOrderProjectTypeIdInOrg.getRepoId() != projectType.getC_ProjectType_ID())
		{
			throw new AdempiereException(MSG_C_PROJECT_TYPE_ONE_SOPO_PER_ORG);
		}
	}
}
