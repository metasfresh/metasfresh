/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.externalsystem.rabbitmqhttp.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.externalsystem.rabbitmqhttp.RabbitMQExternalSystemService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_AD_User;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_User.class)
@Component
public class AD_User
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final RabbitMQExternalSystemService rabbitMQExternalSystemService;

	public AD_User(@NonNull final RabbitMQExternalSystemService rabbitMQExternalSystemService)
	{
		this.rabbitMQExternalSystemService = rabbitMQExternalSystemService;
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE)
	public void triggerSyncBPartnerWithExternalSystem(@NonNull final I_AD_User adUser)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(adUser.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			return;
		}

		trxManager.runAfterCommit(() -> rabbitMQExternalSystemService.enqueueBPartnerSync(bpartnerId));
	}
}
