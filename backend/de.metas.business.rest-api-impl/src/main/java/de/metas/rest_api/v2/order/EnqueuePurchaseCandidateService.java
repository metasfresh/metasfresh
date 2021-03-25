/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.v2.order;

import de.metas.i18n.TranslatableStrings;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.async.C_PurchaseCandidates_GeneratePurchaseOrders;
import de.metas.rest_api.order.JsonPurchaseCandidatesRequest;
import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;
import de.metas.util.web.exception.InvalidEntityException;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnqueuePurchaseCandidateService
{
	private final PurchaseCandidateRepository purchaseCandidateRepo;

	public EnqueuePurchaseCandidateService(final PurchaseCandidateRepository purchaseCandidateRepo)
	{
		this.purchaseCandidateRepo = purchaseCandidateRepo;
	}

	public void enqueue(@NonNull final JsonPurchaseCandidatesRequest request)
	{
		if (request.getPurchaseCandidates().isEmpty())
		{
			throw new InvalidEntityException(TranslatableStrings.constant("The request's purchaseCandidates array may not be empty"));
		}

		final List<ExternalHeaderIdWithExternalLineIds> headerAndLineIds = POJsonConverters.fromJson(request.getPurchaseCandidates());

		C_PurchaseCandidates_GeneratePurchaseOrders.enqueue(purchaseCandidateRepo.getIdsByExternal(headerAndLineIds));
	}
}
