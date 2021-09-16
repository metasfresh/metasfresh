package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance;
import de.metas.contracts.commission.commissioninstance.services.CommissionAlgorithmInvoker;
import de.metas.contracts.commission.commissioninstance.services.CommissionInstanceRequestFactory;
import de.metas.contracts.commission.commissioninstance.services.CommissionInstanceService;
import de.metas.contracts.commission.commissioninstance.services.CommissionTriggerFactory;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionInstanceRepository;
import de.metas.contracts.commission.model.I_C_Commission_Instance;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Service
public class CommissionTriggerDocumentService
{

	private static final Logger logger = LogManager.getLogger(CommissionTriggerDocumentService.class);

	private final CommissionInstanceRepository commissionInstanceRepository;
	private final CommissionAlgorithmInvoker commissionAlgorithmInvoker;
	private final CommissionTriggerFactory commissionTriggerFactory;
	private final CommissionInstanceService commissionInstanceService;

	public CommissionTriggerDocumentService(
			@NonNull final CommissionInstanceRepository commissionInstanceRepository,
			@NonNull final CommissionInstanceRequestFactory commissionInstanceRequestFactory,
			@NonNull final CommissionAlgorithmInvoker commissionAlgorithmInvoker,
			@NonNull final CommissionTriggerFactory commissionTriggerFactory,
			@NonNull final CommissionInstanceService commissionInstanceService)
	{
		this.commissionInstanceRepository = commissionInstanceRepository;
		this.commissionAlgorithmInvoker = commissionAlgorithmInvoker;
		this.commissionTriggerFactory = commissionTriggerFactory;
		this.commissionInstanceService = commissionInstanceService;
	}

	/**
	 * Note: creating/updating commission related records results in the invoice candidate handler framework being fired in order to also keep the commission-settlement IC up to date.
	 */
	public void syncTriggerDocumentToCommissionInstance(
			@NonNull final CommissionTriggerDocument commissionTriggerDocument,
			final boolean candidateDeleted)
	{
		final Optional<CommissionInstance> instance = commissionInstanceRepository.getByDocumentId(commissionTriggerDocument.getId());
		if (!instance.isPresent())
		{
			createNewInstance(commissionTriggerDocument, candidateDeleted);
			return;
		}
		updateInstance(commissionTriggerDocument, candidateDeleted, instance.get());
	}

	private void createNewInstance(
			@NonNull final CommissionTriggerDocument commissionTriggerDocument,
			final boolean candidateDeleted)
	{
		final int repoId = commissionTriggerDocument.getId().getRepoIdAware().getRepoId(); // will be used in logging

		if (candidateDeleted)
		{
			logger.debug("commissionTriggerDocument with id={} has no instances and candidateDeleted=true; -> doing nothing", repoId);
			return; // nothing to do
		}

		// initially create commission data for the given trigger document
		// createdInstance might be not present, if there are no matching contracts and/or settings
		final Optional<CommissionInstance> createdInstance = commissionInstanceService.createCommissionInstance(commissionTriggerDocument);
		if (createdInstance.isPresent())
		{
			commissionInstanceRepository.save(createdInstance.get());
		}
		else
		{
			logger.debug("No existing or newly-created instances for the commissionTriggerDocument with id={}; -> doing nothing", repoId);
		}
	}

	private void updateInstance(
			@NonNull final CommissionTriggerDocument commissionTriggerDocument,
			final boolean candidateDeleted,
			@NonNull final CommissionInstance instance)
	{
		try (final MDCCloseable ignore = TableRecordMDC.putTableRecordReference(I_C_Commission_Instance.Table_Name, instance.getId()))
		{
			final int repoId = commissionTriggerDocument.getId().getRepoIdAware().getRepoId(); // will be used in logging

			// update existing commission data
			logger.debug("commissionTriggerDocument with id={} has an instance (candidateDeleted={}); -> updating its commission shares;",
					repoId, candidateDeleted);

			final CommissionTrigger trigger = commissionTriggerFactory.createForDocument(commissionTriggerDocument, candidateDeleted);

			final CommissionTriggerData newTriggerData = trigger.getCommissionTriggerData();
			final CommissionTriggerChange change = CommissionTriggerChange.builder()
					.instanceToUpdate(instance)
					.newCommissionTriggerData(newTriggerData)
					.build();
			commissionAlgorithmInvoker.updateCommissionShares(change);

			instance.setCurrentTriggerData(newTriggerData);

			if (candidateDeleted)
			{
				commissionInstanceRepository.save(instance);
				return; // we are done
			}

			logger.debug("creating missing commission shares as needed;", repoId, candidateDeleted);

			commissionInstanceService.createAndAddMissingShares(instance, commissionTriggerDocument);

			commissionInstanceRepository.save(instance);
		}
	}

}
