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
import com.google.common.collect.ImmutableListMultimap;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.cache.CCache;
import de.metas.edi.api.EDIBPartnerConfig;
import de.metas.edi.api.EDISendingMode;
import de.metas.esb.edi.model.I_C_BPartner_EDI_Setting;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Repository Tables: C_BPartner_EDI_Setting
 * Repository Cluster: EDIBPartnerConfigRepository (sole reader)
 */
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

		// Group all configs by partner; resolution is done at query time using SeqNo + ID ordering.
		final ImmutableListMultimap<BPartnerId, EDIBPartnerConfig> byPartner = configs.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						EDIBPartnerConfig::getBPartnerId,
						config -> config));

		return new EDIBPartnerConfigMap(byPartner);
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
				.repoId(record.getC_BPartner_EDI_Setting_ID())
				.seqNo(record.getSeqNo())
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
		private static final Comparator<EDIBPartnerConfig> BY_SEQ_NO_THEN_ID =
				Comparator.comparingInt(EDIBPartnerConfig::getSeqNo)
						.thenComparingInt(EDIBPartnerConfig::getRepoId);

		private final ImmutableListMultimap<BPartnerId, EDIBPartnerConfig> byPartner;

		EDIBPartnerConfigMap(@NonNull final ImmutableListMultimap<BPartnerId, EDIBPartnerConfig> byPartner)
		{
			this.byPartner = byPartner;
		}

		@Nullable
		EDIBPartnerConfig resolve(@NonNull final BPartnerLocationId bpl)
		{
			final BPartnerId bPartnerId = bpl.getBpartnerId();
			return byPartner.get(bPartnerId).stream()
					.filter(config -> {
						final BPartnerLocationId configLocation = config.getBpartnerLocationId();
						return configLocation == null || configLocation.equals(bpl);
					})
					.min(BY_SEQ_NO_THEN_ID)
					.orElse(null);
		}
	}
}
