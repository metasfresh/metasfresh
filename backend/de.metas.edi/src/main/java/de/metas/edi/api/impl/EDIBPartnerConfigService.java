/*
 * #%L
 * de.metas.edi
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

package de.metas.edi.api.impl;

import com.google.common.annotations.VisibleForTesting;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.bpartner.BPartnerId;
import de.metas.edi.api.EDIBPartnerConfig;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EDIBPartnerConfigService
{
	@NonNull private final EDIBPartnerConfigRepository ediBPartnerConfigRepository;

	@VisibleForTesting
	public static EDIBPartnerConfigService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(EDIBPartnerConfigService.class,
				() -> new EDIBPartnerConfigService(
						EDIBPartnerConfigRepository.newInstanceForUnitTesting()
				)
		);
	}

	public boolean isEdiDesadvRecipient(@NonNull final BPartnerId bPartnerId)
	{
		final EDIBPartnerConfig ediBPartnerConfig = ediBPartnerConfigRepository.getByIdOrNull(bPartnerId);
		if(ediBPartnerConfig == null)
		{
			return false;
		}

		return ediBPartnerConfig.isEdiDesadvRecipient();
	}

	public boolean isDESADVReplicationInterfaceRecipient(@NonNull final BPartnerId bPartnerId)
	{
		final EDIBPartnerConfig ediBPartnerConfig = ediBPartnerConfigRepository.getByIdOrNull(bPartnerId);
		if(ediBPartnerConfig == null)
		{
			return false;
		}

		return ediBPartnerConfig.isDESADVReplicationInterfaceRecipient();
	}

	public boolean isDESADVExternalSystemRecipient(@NonNull final BPartnerId bPartnerId)
	{
		final EDIBPartnerConfig ediBPartnerConfig = ediBPartnerConfigRepository.getByIdOrNull(bPartnerId);
		if(ediBPartnerConfig == null)
		{
			return false;
		}

		return ediBPartnerConfig.isDESADVExternalSystemRecipient();
	}

	public boolean isDESADVOneDesadvPerShipment(@NonNull final BPartnerId bPartnerId)
	{
		final EDIBPartnerConfig ediBPartnerConfig = ediBPartnerConfigRepository.getByIdOrNull(bPartnerId);
		if (ediBPartnerConfig == null)
		{
			return false;
		}

		return ediBPartnerConfig.isDESADVOneDesadvPerShipment();
	}

	@NonNull
	public ExternalSystemParentConfigId getDESADVExternalSystemParentConfigId(@NonNull final BPartnerId bPartnerId)
	{
		return Check.assumeNotNull(ediBPartnerConfigRepository.getById(bPartnerId).getEdiDesadvExternalSystemParentConfigId(),
				"DESADVExternalSystemParentConfigId should be present for bPartnerId {}", bPartnerId);
	}

	public boolean isEdiInvoicRecipient(@NonNull final BPartnerId bPartnerId)
	{
		final EDIBPartnerConfig ediBPartnerConfig = ediBPartnerConfigRepository.getByIdOrNull(bPartnerId);
		if(ediBPartnerConfig == null)
		{
			return false;
		}

		return ediBPartnerConfig.isEdiInvoicRecipient();
	}

	public boolean isINVOICReplicationInterfaceRecipient(@NonNull final BPartnerId bPartnerId)
	{
		final EDIBPartnerConfig ediBPartnerConfig = ediBPartnerConfigRepository.getByIdOrNull(bPartnerId);
		if(ediBPartnerConfig == null)
		{
			return false;
		}

		return ediBPartnerConfig.isINVOICReplicationInterfaceRecipient();
	}

	public boolean isINVOICExternalSystemRecipient(@NonNull final BPartnerId bPartnerId)
	{
		final EDIBPartnerConfig ediBPartnerConfig = ediBPartnerConfigRepository.getByIdOrNull(bPartnerId);
		if(ediBPartnerConfig == null)
		{
			return false;
		}

		return ediBPartnerConfig.isINVOICExternalSystemRecipient();
	}

	@NonNull
	public ExternalSystemParentConfigId getINVOICExternalSystemParentConfigId(@NonNull final BPartnerId bPartnerId)
	{
		return Check.assumeNotNull(ediBPartnerConfigRepository.getById(bPartnerId).getEdiInvoicExternalSystemParentConfigId(),
				"INVOICExternalSystemParentConfigId should be present for bPartnerId {}", bPartnerId);
	}
}
