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

package de.metas.contracts.commission.commissioninstance.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance;
import de.metas.contracts.commission.commissioninstance.businesslogic.CreateCommissionSharesRequest;
import lombok.NonNull;

@Service
public class CommissionInstanceService
{
	private final CommissionInstanceRequestFactory commissionInstanceRequestFactory;

	private final CommissionAlgorithmInvoker commissionAlgorithmInvoker;

	public CommissionInstanceService(
			@NonNull final CommissionInstanceRequestFactory commissionInstanceRequestFactory,
			@NonNull final CommissionAlgorithmInvoker commissionAlgorithmInvoker)
	{
		this.commissionInstanceRequestFactory = commissionInstanceRequestFactory;
		this.commissionAlgorithmInvoker = commissionAlgorithmInvoker;
	}

	public Optional<CommissionInstance> computeCommissionInstanceFor(
			@NonNull final CreateForecastCommissionInstanceRequest createForecastCommissionInstanceRequest)
	{
		final Optional<CreateCommissionSharesRequest> request = commissionInstanceRequestFactory.createRequestFor(createForecastCommissionInstanceRequest);

		if (request.isPresent())
		{
			return Optional.of(commissionAlgorithmInvoker.applyCreateRequest(request.get()));
		}
		return Optional.empty();
	}
}
