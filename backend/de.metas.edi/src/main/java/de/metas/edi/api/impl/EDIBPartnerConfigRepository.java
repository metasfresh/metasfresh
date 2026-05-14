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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.edi.api.EDIBPartnerConfig;
import de.metas.edi.api.EDISendingMode;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import de.metas.edi.model.I_C_BPartner;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Repository
public class EDIBPartnerConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@VisibleForTesting
	public static EDIBPartnerConfigRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(EDIBPartnerConfigRepository.class, EDIBPartnerConfigRepository::new);
	}

	private final CCache<Integer, EDIBPartnerConfigMap> cache = CCache.<Integer, EDIBPartnerConfigMap>builder()
			.tableName(I_C_BPartner.Table_Name)
			.maximumSize(1)
			.build();

	@Nullable
	public EDIBPartnerConfig getByIdOrNull(@NonNull final BPartnerId bPartnerId)
	{
		return getEDIBPartnerConfigMap().getById(bPartnerId);
	}

	@NonNull
	public EDIBPartnerConfig getById(@NonNull final BPartnerId bPartnerId)
	{
		return Optional.ofNullable(getEDIBPartnerConfigMap().getById(bPartnerId))
				.orElseThrow(() -> new AdempiereException("No active EdiBPartnerConfig found for BPartnerId " + bPartnerId.getRepoId()));
	}

	private EDIBPartnerConfigMap getEDIBPartnerConfigMap()
	{
		return cache.getOrLoadNonNull(0, this::retrieveEDIBPartnerConfigMap);
	}

	private EDIBPartnerConfigMap retrieveEDIBPartnerConfigMap()
	{
		// keep in sync with the index c_bpartner_isedidesadvrecipient_isediinvoicrecipient
		final IQueryBuilder<I_C_BPartner> queryBuilder = queryBL.createQueryBuilder(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter();

		queryBuilder.addCompositeQueryFilter()
				.setJoinOr()
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_IsEdiDesadvRecipient, true)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_IsEdiInvoicRecipient, true);

		final ImmutableList<EDIBPartnerConfig> configs = queryBuilder.create()
				.stream()
				.map(EDIBPartnerConfigRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new EDIBPartnerConfigMap(configs);
	}

	private static EDIBPartnerConfig fromRecord(@NonNull final I_C_BPartner partner)
	{
		return EDIBPartnerConfig.builder()
				.bPartnerId(BPartnerId.ofRepoId(partner.getC_BPartner_ID()))
				.isEdiDesadvRecipient(partner.isEdiDesadvRecipient())
				.ediDesadvRecipientGLN(partner.getEdiDesadvRecipientGLN())
				.ediDesadvSendingMode(EDISendingMode.ofCode(partner.getEdiDESADVSendingMode()))
				.ediDesadvExternalSystemParentConfigId(ExternalSystemParentConfigId.ofRepoIdOrNull(partner.getEdiDESADV_ExternalSystem_Config_ID()))
				.isEdiOneEDIDesadvPerShipment(partner.isEdiOneEDIDesadvPerShipment())
				.isEdiInvoicRecipient(partner.isEdiInvoicRecipient())
				.ediInvoicRecipientGLN(partner.getEdiInvoicRecipientGLN())
				.ediInvoicSendingMode(EDISendingMode.ofCode(partner.getEdiINVOICSendingMode()))
				.ediInvoicExternalSystemParentConfigId(ExternalSystemParentConfigId.ofRepoIdOrNull(partner.getEdiINVOIC_ExternalSystem_Config_ID()))
				.build();
	}

	private static final class EDIBPartnerConfigMap
	{
		private final ImmutableMap<BPartnerId, EDIBPartnerConfig> allEnabledById;

		EDIBPartnerConfigMap(final List<EDIBPartnerConfig> list)
		{
			this.allEnabledById = Maps.uniqueIndex(list, EDIBPartnerConfig::getBPartnerId);
		}

		@Nullable
		EDIBPartnerConfig getById(@NonNull final BPartnerId bPartnerId) {return allEnabledById.get(bPartnerId);}
	}
}
