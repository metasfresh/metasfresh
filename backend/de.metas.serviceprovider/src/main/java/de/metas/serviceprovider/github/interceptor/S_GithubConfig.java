/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.github.interceptor;

import de.metas.organization.OrgId;
import de.metas.serviceprovider.github.config.GithubConfigName;
import de.metas.serviceprovider.model.I_S_GithubConfig;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_S_GithubConfig.class)
@Component
public class S_GithubConfig
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void githubConfigSecretNameValidator(@NonNull final I_S_GithubConfig githubConfig)
	{
		final GithubConfigName gitHubConfigName = GithubConfigName.ofCode(githubConfig.getName());

		if (gitHubConfigName.equals(GithubConfigName.GITHUB_SECRET) && OrgId.ANY.getRepoId() == githubConfig.getAD_Org_ID())
		{
			throw new AdempiereException("Github config name cannot have orgId ANY")
					.markAsUserValidationError()
					.appendParametersToMessage()
					.setParameter("name", gitHubConfigName);
		}
	}
}
