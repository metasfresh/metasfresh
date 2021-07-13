package de.metas.procurement.webui.service.impl;

import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.Rfq;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.repository.RfqRepository;
import de.metas.procurement.webui.rest.rfq.JsonChangeRfqQtyRequest;
import de.metas.procurement.webui.rest.rfq.JsonChangeRfqRequest;
import de.metas.procurement.webui.service.IRfQService;
import de.metas.procurement.webui.sync.ISenderToMetasfreshService;
import de.metas.procurement.webui.sync.ISyncAfterCommitCollector;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.transaction.Transactional;
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
	private final ISenderToMetasfreshService senderToMetasfreshService;

	public RfQService(
			@NonNull final RfqRepository rfqRepo,
			@NonNull final ISenderToMetasfreshService senderToMetasfreshService)
	{
		this.rfqRepo = rfqRepo;
		this.senderToMetasfreshService = senderToMetasfreshService;
	}

	@Override
	public List<Rfq> getActiveRfqs(@Nullable final User user)
	{
		final BPartner bpartner = user != null ? user.getBpartner() : null;
		return getActiveRfqs(bpartner);
	}

	@Override
	public List<Rfq> getActiveRfqs(@Nullable final BPartner bpartner)
	{
		return rfqRepo.findActive(bpartner);
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
	@Nullable
	public Rfq getRfqByUUID(@NonNull final String rfq_uuid)
	{
		return rfqRepo.findByUuid(rfq_uuid);
	}

	private void saveRecursivelyAndPush(@NonNull final Rfq rfq)
	{
		saveRecursively(rfq);
		pushToMetasfresh(rfq);
	}

	@Override
	public void saveRecursively(@NonNull final Rfq rfq)
	{
		//rfqQuantityRepo.saveAll(rfq.getQuantities()); // not needed
		rfqRepo.save(rfq);
	}

	private void pushToMetasfresh(@NonNull final Rfq rfq)
	{
		final ISyncAfterCommitCollector syncAfterCommitCollector = senderToMetasfreshService.syncAfterCommit();
		syncAfterCommitCollector.add(rfq);
	}

	@Override
	@Transactional
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
			rfq.setPricePromisedUserEntered(request.getPrice());
		}

		saveRecursively(rfq);

		return rfq;
	}

	@Override
	public long getCountUnconfirmed(@NonNull final User user)
	{
		return rfqRepo.countUnconfirmed(user.getBpartner());
	}

	@Override
	@Transactional
	public void confirmUserEntries(@NonNull final User user)
	{
		final BPartner bpartner = user.getBpartner();
		final List<Rfq> rfqs = rfqRepo.findUnconfirmed(bpartner);
		for (final Rfq rfq : rfqs)
		{
			rfq.confirmByUser();
			saveRecursivelyAndPush(rfq);
		}
	}

}
