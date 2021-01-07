package de.metas.procurement.webui.service.impl;

import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.Rfq;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.repository.RfqQtyRepository;
import de.metas.procurement.webui.repository.RfqRepository;
import de.metas.procurement.webui.rest.rfq.JsonChangeRfqQtyRequest;
import de.metas.procurement.webui.rest.rfq.JsonChangeRfqRequest;
import de.metas.procurement.webui.service.IRfQService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/*
 * #%L
 * metasfresh-procurement-webui
 * %%
 * Copyright (C) 2016 metas GmbH
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
public class RfQService implements IRfQService
{
	private final RfqRepository rfqRepo;
	private final RfqQtyRepository rfqQuantityRepo;

	public RfQService(
			@NonNull final RfqRepository rfqRepo,
			@NonNull final RfqQtyRepository rfqQuantityRepo)
	{
		this.rfqRepo = rfqRepo;
		this.rfqQuantityRepo = rfqQuantityRepo;
	}

	@Override
	public List<Rfq> getUserActiveRfqs(@NonNull final User user)
	{
		final BPartner bpartner = user.getBpartner();
		return rfqRepo.findByBpartnerAndClosedFalse(bpartner);
	}

	@Override
	public Rfq getUserActiveRfq(@NonNull final User user, final long rfqId)
	{
		final Rfq rfq = getRfqById(rfqId);

		if (!Objects.equals(rfq.getBpartnerId(), user.getBpartnerId()))
		{
			throw new RuntimeException("RfQ not found");
		}

		if (rfq.isClosed())
		{
			throw new RuntimeException("RfQ already closed");
		}

		return rfq;
	}

	@Override
	public Rfq getRfqById(final long rfq_id)
	{
		return rfqRepo.getOne(rfq_id);
	}

	@Override
	public Rfq changeActiveRfq(
			@NonNull final JsonChangeRfqRequest request,
			@NonNull final User loggedUser)
	{
		final Rfq rfq = getUserActiveRfq(
				loggedUser,
				Long.parseLong(request.getRfqId()));

		for (final JsonChangeRfqQtyRequest qtyChangeRequest : request.getQuantities())
		{
			rfq.setQtyPromised(qtyChangeRequest.getDate(), qtyChangeRequest.getQtyPromised());
		}

		if (request.getPrice() != null)
		{
			rfq.setPricePromised(request.getPrice());
		}

		rfqQuantityRepo.saveAll(rfq.getQuantities());
		rfqRepo.save(rfq);

		return rfq;
	}
}
