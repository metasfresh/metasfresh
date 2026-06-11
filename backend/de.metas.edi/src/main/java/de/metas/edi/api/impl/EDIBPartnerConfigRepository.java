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
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.cache.CCache;
import de.metas.edi.api.EDIBPartnerConfig;
import de.metas.edi.api.EDISendingMode;
import de.metas.esb.edi.model.I_C_BPartner_EDI_Setting;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * Repository Tables: C_BPartner_EDI_Setting
 * Repository Cluster: EDIBPartnerConfigRepository (sole reader)
 */
@Repository
public class EDIBPartnerConfigRepository
{
	private static final Logger logger = LogManager.getLogger(EDIBPartnerConfigRepository.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@VisibleForTesting
	public static EDIBPartnerConfigRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(EDIBPartnerConfigRepository.class, EDIBPartnerConfigRepository::new);
	}

	private final CCache<Integer, EDIBPartnerConfigMap> cache = CCache.<Integer, EDIBPartnerConfigMap>builder()
			.tableName(I_C_BPartner_EDI_Setting.Table_Name)
			.maximumSize(1)
			.build();

	// BPartnerLocationId-based API

	@Nullable
	public EDIBPartnerConfig getByIdOrNull(@NonNull final BPartnerLocationId bPartnerLocationId)
	{
		return getEDIBPartnerConfigMap().resolve(bPartnerLocationId);
	}

	@NonNull
	public EDIBPartnerConfig getById(@NonNull final BPartnerLocationId bPartnerLocationId)
	{
		return Optional.ofNullable(getEDIBPartnerConfigMap().resolve(bPartnerLocationId))
				.orElseThrow(() -> new AdempiereException("No active EDIBPartnerConfig found for BPartnerLocationId " + bPartnerLocationId));
	}

	//
	// --- internal ---

	private EDIBPartnerConfigMap getEDIBPartnerConfigMap()
	{
		return cache.getOrLoadNonNull(0, this::retrieveEDIBPartnerConfigMap);
	}

	private EDIBPartnerConfigMap retrieveEDIBPartnerConfigMap()
	{
		final List<EDIBPartnerConfig> configs = queryBL.createQueryBuilder(I_C_BPartner_EDI_Setting.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(EDIBPartnerConfigRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		final ImmutableMap<BPartnerLocationId, EDIBPartnerConfig> byLocation = configs.stream()
				.filter(config -> config.getBpartnerLocationId() != null)
				.collect(ImmutableMap.toImmutableMap(
						EDIBPartnerConfig::getBpartnerLocationId,
						config -> config,
						(first, ignored) -> {
							logger.warn("Ignoring duplicate active C_BPartner_EDI_Setting for location={}; keeping the first", first.getBpartnerLocationId());
							return first;
						}));

		final ImmutableMap<BPartnerId, EDIBPartnerConfig> defaultByPartner = configs.stream()
				.filter(config -> config.getBpartnerLocationId() == null)
				.collect(ImmutableMap.toImmutableMap(
						EDIBPartnerConfig::getBPartnerId,
						config -> config,
						(first, ignored) -> {
							logger.warn("Ignoring duplicate active default C_BPartner_EDI_Setting for bPartnerId={}; keeping the first", first.getBPartnerId().getRepoId());
							return first;
						}));

		return new EDIBPartnerConfigMap(byLocation, defaultByPartner);
	}

	private static EDIBPartnerConfig fromRecord(@NonNull final I_C_BPartner_EDI_Setting record)
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(record.getC_BPartner_ID());
		final BPartnerLocationId bpartnerLocationId = record.getC_BPartner_Location_ID() > 0
				? BPartnerLocationId.ofRepoId(bPartnerId, record.getC_BPartner_Location_ID())
				: null;

		return EDIBPartnerConfig.builder()
				.bPartnerId(bPartnerId)
				.bpartnerLocationId(bpartnerLocationId)
				.isEdiDesadvRecipient(record.isEdiDesadvRecipient())
				.ediDesadvRecipientGLN(record.getEdiDesadvRecipientGLN())
				.ediDesadvSendingMode(EDISendingMode.ofCode(record.getEdiDESADVSendingMode()))
				.ediDesadvExternalSystemParentConfigId(ExternalSystemParentConfigId.ofRepoIdOrNull(record.getEdiDESADV_ExternalSystem_Config_ID()))
				.isEdiInvoicRecipient(record.isEdiInvoicRecipient())
				.ediInvoicRecipientGLN(record.getEdiInvoicRecipientGLN())
				.ediInvoicSendingMode(EDISendingMode.ofCode(record.getEdiINVOICSendingMode()))
				.ediInvoicExternalSystemParentConfigId(ExternalSystemParentConfigId.ofRepoIdOrNull(record.getEdiINVOIC_ExternalSystem_Config_ID()))
				.build();
	}

	private static final class EDIBPartnerConfigMap
	{
		private final ImmutableMap<BPartnerLocationId, EDIBPartnerConfig> byLocation;
		private final ImmutableMap<BPartnerId, EDIBPartnerConfig> defaultByPartner;

		EDIBPartnerConfigMap(
				@NonNull final ImmutableMap<BPartnerLocationId, EDIBPartnerConfig> byLocation,
				@NonNull final ImmutableMap<BPartnerId, EDIBPartnerConfig> defaultByPartner)
		{
			this.byLocation = byLocation;
			this.defaultByPartner = defaultByPartner;
		}

		@Nullable
		EDIBPartnerConfig resolve(@NonNull final BPartnerLocationId bpl)
		{
			final EDIBPartnerConfig exact = byLocation.get(bpl);
			return exact != null ? exact : defaultByPartner.get(bpl.getBpartnerId());
		}
	}
}
