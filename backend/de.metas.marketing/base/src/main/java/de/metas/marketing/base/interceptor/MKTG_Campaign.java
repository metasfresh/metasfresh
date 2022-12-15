/*
 * #%L
 * marketing-base
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

package de.metas.marketing.base.interceptor;

import de.metas.marketing.base.model.I_MKTG_Campaign;
import de.metas.marketing.base.model.Platform;
import de.metas.marketing.base.model.PlatformGatewayId;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.PlatformRepository;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_MKTG_Campaign.class)
public class MKTG_Campaign
{
	private final PlatformRepository platformRepository = SpringContextHolder.instance.getBean(PlatformRepository.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void orgAnyNotAllowed(@NonNull final I_MKTG_Campaign campaign)
	{
		final PlatformId platformId = PlatformId.ofRepoId(campaign.getMKTG_Platform_ID());

		final Platform platform = platformRepository.getById(platformId);

		if (!platform.getPlatformGatewayId().equals(PlatformGatewayId.ActiveCampaign))
		{
			return;
		}

		if (OrgId.ANY.getRepoId() != campaign.getAD_Org_ID())
		{
			return;
		}

		throw new AdempiereException("Campaign creation with orgId ANY not allowed.")
				.markAsUserValidationError()
				.appendParametersToMessage()
				.setParameter("PlatformGatewayId", platform.getPlatformGatewayId().getCode());
	}
}
