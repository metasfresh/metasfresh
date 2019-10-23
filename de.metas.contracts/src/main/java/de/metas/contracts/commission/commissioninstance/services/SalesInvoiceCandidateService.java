package de.metas.contracts.commission.commissioninstance.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance;
import de.metas.contracts.commission.commissioninstance.businesslogic.CreateInstanceRequest;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTriggerChange;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTriggerData;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionInstanceRepository;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionTriggerDataRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import lombok.NonNull;

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
public class SalesInvoiceCandidateService
{
	private final CommissionInstanceRepository commissionInstanceRepository;
	private final CommissionInstanceRequestFactory commissionInstanceRequestFactory;
	private final CommissionAlgorithmInvoker commissionAlgorithmInvoker;
	private final CommissionTriggerDataRepository commissionTriggerDataRepository;

	public SalesInvoiceCandidateService(
			@NonNull final CommissionInstanceRepository commissionInstanceRepository,
			@NonNull final CommissionInstanceRequestFactory commissionInstanceRequestFactory,
			@NonNull final CommissionTriggerDataRepository commissionTriggerDataRepository,
			@NonNull final CommissionAlgorithmInvoker commissionAlgorithmInvoker)
	{
		this.commissionInstanceRepository = commissionInstanceRepository;
		this.commissionInstanceRequestFactory = commissionInstanceRequestFactory;
		this.commissionTriggerDataRepository = commissionTriggerDataRepository;
		this.commissionAlgorithmInvoker = commissionAlgorithmInvoker;
	}

	/**
	 * Note: creating/updating commission related records results in the invoice candidate handler framework being fired in order to also keep the commission-settlement IC up to date.
	 */
	public void syncSalesICToCommissionInstance(@NonNull final InvoiceCandidateId invoiceCandidateId, final boolean candidateDeleted)
	{
		final List<CommissionInstance> instances = commissionInstanceRepository.getForInvoiceCandidateId(invoiceCandidateId);
		if (instances.isEmpty())
		{
			if(candidateDeleted)
			{
				return; // nothing to do
			}
			// initially create commission data for the given invoice candidate
			final ImmutableList<CreateInstanceRequest> requests = commissionInstanceRequestFactory.createRequestsForNewSalesInvoiceCandidate(invoiceCandidateId);
			for (final CreateInstanceRequest request : requests)
			{
				final CommissionInstance createdInstance = commissionAlgorithmInvoker.applyCreateRequest(request);
				commissionInstanceRepository.save(createdInstance);
			}
			return;
		}

		// update existing commission data
		for (final CommissionInstance instance : instances)
		{
			final CommissionTriggerData newTriggerData = commissionTriggerDataRepository.getForInvoiceCandiateId(invoiceCandidateId, candidateDeleted);
			final CommissionTriggerChange change = CommissionTriggerChange.builder()
					.instanceToUpdate(instance)
					.newCommissionTriggerData(newTriggerData)
					.build();
			commissionAlgorithmInvoker.applyTriggerChangeToSharesOfInstance(change);

			instance.setCurrentTriggerData(newTriggerData);
			commissionInstanceRepository.save(instance);
		}
	}
}
