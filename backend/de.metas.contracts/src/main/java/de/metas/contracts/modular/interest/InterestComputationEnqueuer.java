/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.interest;

import com.google.common.collect.ImmutableMap;
import de.metas.JsonObjectMapperHolder;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

import static org.compiere.util.Env.getCtx;

@Service
@RequiredArgsConstructor
public class InterestComputationEnqueuer
{
	public static final String ENQUEUED_REQUEST_PARAM = "ENQUEUED_REQUEST_PARAM";

	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	public void enqueueNow(
			@NonNull final EnqueueInterestComputationRequest request,
			@NonNull final UserId userId)
	{
		trxManager.runInThreadInheritedTrx(() -> enqueueInTrx(request, userId));
	}

	private void enqueueInTrx(
			@NonNull final EnqueueInterestComputationRequest request,
			@NonNull final UserId userId)
	{
		workPackageQueueFactory.getQueueForEnqueuing(getCtx(), InterestComputationWorkPackageProcessor.class)
				.newWorkPackage()
				// ensures we are only enqueueing after this trx is committed
				.bindToThreadInheritedTrx()
				.parameters(getParameters(request))
				.setUserInChargeId(userId)
				.buildAndEnqueue();
	}

	@NonNull
	private Map<String,String> getParameters(@NonNull final EnqueueInterestComputationRequest request)
	{
		final String serializedRequest = JsonObjectMapperHolder.toJson(request);
		return ImmutableMap.of(ENQUEUED_REQUEST_PARAM, Objects.requireNonNull(serializedRequest));
	}
}
