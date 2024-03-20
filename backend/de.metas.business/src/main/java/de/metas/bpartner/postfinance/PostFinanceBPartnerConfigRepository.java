/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.bpartner.postfinance;

import de.metas.bpartner.BPartnerId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_PostFinance_BPartner_Config;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class PostFinanceBPartnerConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public I_PostFinance_BPartner_Config getById(@NonNull final PostFinanceBPartnerConfigId id)
	{
		final I_PostFinance_BPartner_Config record = InterfaceWrapperHelper.load(id, I_PostFinance_BPartner_Config.class);

		if (record == null)
		{
			throw new AdempiereException("No PostFinance_BPartner_Config record found for ID!")
					.appendParametersToMessage()
					.setParameter("PostFinanceBPartnerConfigId", id);
		}

		return record;
	}

	@NonNull
	public Optional<PostFinanceBPartnerConfig> getByBPartnerId(@NonNull final BPartnerId bPartnerId)
	{
		return getRecordByBPartnerId(bPartnerId)
				.map(PostFinanceBPartnerConfigRepository::toPostFinanceBPartnerConfig);
	}

	@NonNull
	public Optional<PostFinanceBPartnerConfig> getByReceiverEBillId(@NonNull final String receiverEBillId)
	{
		return queryBL.createQueryBuilder(I_PostFinance_BPartner_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PostFinance_BPartner_Config.COLUMNNAME_PostFinance_Receiver_eBillId, receiverEBillId)
				.create()
				.firstOptional()
				.map(PostFinanceBPartnerConfigRepository::toPostFinanceBPartnerConfig);
	}

	public void deleteByReceiverEBillId(@NonNull final String receiverEBillId)
	{
		queryBL.createQueryBuilder(I_PostFinance_BPartner_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PostFinance_BPartner_Config.COLUMNNAME_PostFinance_Receiver_eBillId, receiverEBillId)
				.create()
				.delete();
	}

	@NonNull
	public PostFinanceBPartnerConfig createOrUpdate(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final String receiverEBillId)
	{
		return getByBPartnerId(bPartnerId)
				.map(existingBPartnerConfig -> existingBPartnerConfig.toBuilder()
						.receiverEBillId(receiverEBillId)
						.build())
				.map(this::update)
				.orElseGet(() -> create(bPartnerId, receiverEBillId));
	}

	@NonNull
	private PostFinanceBPartnerConfig update(@NonNull final PostFinanceBPartnerConfig postFinanceBPartnerConfig)
	{
		final I_PostFinance_BPartner_Config existingConfig = getById(postFinanceBPartnerConfig.getId());

		existingConfig.setPostFinance_Receiver_eBillId(postFinanceBPartnerConfig.getReceiverEBillId());
		existingConfig.setC_BPartner_ID(postFinanceBPartnerConfig.getBPartnerId().getRepoId());

		saveRecord(existingConfig);

		return toPostFinanceBPartnerConfig(existingConfig);
	}

	@NonNull
	private PostFinanceBPartnerConfig create(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final String receiverEBillId)
	{
		final I_PostFinance_BPartner_Config bPartnerConfig = InterfaceWrapperHelper.newInstance(I_PostFinance_BPartner_Config.class);
		bPartnerConfig.setC_BPartner_ID(bPartnerId.getRepoId());
		bPartnerConfig.setPostFinance_Receiver_eBillId(receiverEBillId);

		saveRecord(bPartnerConfig);

		return toPostFinanceBPartnerConfig(bPartnerConfig);
	}

	@NonNull
	private Optional<I_PostFinance_BPartner_Config> getRecordByBPartnerId(@NonNull final BPartnerId bPartnerId)
	{
		return queryBL.createQueryBuilder(I_PostFinance_BPartner_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PostFinance_BPartner_Config.COLUMNNAME_C_BPartner_ID, bPartnerId)
				.create()
				.firstOnlyOptional();
	}

	@NonNull
	private static PostFinanceBPartnerConfig toPostFinanceBPartnerConfig(@NonNull final I_PostFinance_BPartner_Config bPartnerConfig)
	{
		return PostFinanceBPartnerConfig.builder()
				.id(PostFinanceBPartnerConfigId.ofRepoId(bPartnerConfig.getPostFinance_BPartner_Config_ID()))
				.bPartnerId(BPartnerId.ofRepoId(bPartnerConfig.getC_BPartner_ID()))
				.receiverEBillId(bPartnerConfig.getPostFinance_Receiver_eBillId())
				.build();
	}
}
