package de.metas.contracts.commission.commissioninstance.services;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionAlgorithm;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionType;
import de.metas.contracts.commission.commissioninstance.businesslogic.CreateCommissionSharesRequest;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerChange;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Service;

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
public class CommissionAlgorithmInvoker
{
	@NonNull
	public ImmutableList<CommissionShare> createCommissionShares(@NonNull final CreateCommissionSharesRequest request)
	{
		try
		{
			final ImmutableList<CommissionType> commissionTypes = CollectionUtils.extractDistinctElements(
					request.getConfigs(),
					CommissionConfig::getCommissionType);

			final ImmutableList.Builder<CommissionShare> result = ImmutableList.builder();
			for (final CommissionType commissionType : commissionTypes)
			{
				try (final MDCCloseable ignore = MDC.putCloseable("commissionType", commissionType.name()))
				{
					final CommissionAlgorithm algorithm;
					algorithm = createAlgorithmInstance(commissionType);

					// invoke the algorithm
					final ImmutableList<CommissionShare> sharesFromAlgorithm = algorithm.createCommissionShares(request);
					result.addAll(sharesFromAlgorithm);
				}
			}
			return result.build();
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e).setParameter("request", request); // augment&rethrow
		}
	}

	public void updateCommissionShares(@NonNull final CommissionTriggerChange change)
	{
		try
		{
			final ImmutableList<CommissionType> commissionTypes = CollectionUtils.extractDistinctElements(
					change.getInstanceToUpdate().getShares(),
					share -> share.getConfig().getCommissionType());

			for (final CommissionType commissionType : commissionTypes)
			{
				try (final MDCCloseable ignore = MDC.putCloseable("commissionType", commissionType.name()))
				{
					final CommissionAlgorithm algorithm = createAlgorithmInstance(commissionType);

					// invoke the algorithm
					algorithm.applyTriggerChangeToShares(change);
				}
			}
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e).setParameter("change", change);
		}
	}

	@NonNull
	private CommissionAlgorithm createAlgorithmInstance(@NonNull final CommissionType commissionType)
	{
		final Class<? extends CommissionAlgorithm> algorithmClass = commissionType.getAlgorithmClass();
		final CommissionAlgorithm algorithm;
		try
		{
			algorithm = algorithmClass.newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new AdempiereException("Unable to instantiate commission algorithm from class " + algorithmClass)
					.appendParametersToMessage()
					.setParameter("commissionType", commissionType);
		}
		return algorithm;
	}
}
