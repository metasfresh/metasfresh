/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.rabbitmqhttp.interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.externalreference.ExternalReferenceId;
import de.metas.externalreference.ExternalUserReferenceType;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import de.metas.externalreference.bpartnerlocation.BPLocationExternalReferenceType;
import de.metas.externalreference.model.I_S_ExternalReference;
import de.metas.externalsystem.rabbitmqhttp.ExportExternalReferenceToRabbitMQService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Set;

@Interceptor(I_S_ExternalReference.class)
@Component
public class S_ExternalReference
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final ExportExternalReferenceToRabbitMQService exportExternalReferenceToRabbitMQService;

	public S_ExternalReference(@NonNull final ExportExternalReferenceToRabbitMQService exportExternalReferenceToRabbitMQService)
	{
		this.exportExternalReferenceToRabbitMQService = exportExternalReferenceToRabbitMQService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void triggerSyncExternalReferenceWithExternalSystem(@NonNull final I_S_ExternalReference externalReference)
	{
		final boolean isExternalRefEligibleToSync = supportedExternalReferenceType()
				.stream()
				.anyMatch(externalRefType -> externalReference.getType().equals(externalRefType.getCode()));

		if (!isExternalRefEligibleToSync)
		{
			return;
		}

		final ExternalReferenceId externalReferenceId = ExternalReferenceId.ofRepoId(externalReference.getS_ExternalReference_ID());

		trxManager.runAfterCommit(() -> exportExternalReferenceToRabbitMQService.enqueueExternalReferenceSync(externalReferenceId));
	}

	@NonNull
	private Set<IExternalReferenceType> supportedExternalReferenceType()
	{
		return ImmutableSet.of(BPartnerExternalReferenceType.BPARTNER,
							   BPLocationExternalReferenceType.BPARTNER_LOCATION,
							   ExternalUserReferenceType.USER_ID);
	}
}
