package de.metas.procurement.webui.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gwt.thirdparty.guava.common.base.Preconditions;

import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.Rfq;
import de.metas.procurement.webui.model.RfqQty;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.repository.RfqQtyRepository;
import de.metas.procurement.webui.repository.RfqRepository;
import de.metas.procurement.webui.service.IRfQService;
import de.metas.procurement.webui.sync.IServerSyncService;
import de.metas.procurement.webui.ui.model.RfqHeader;
import de.metas.procurement.webui.ui.model.RfqQuantityReport;
import de.metas.procurement.webui.util.DateRange;
import de.metas.procurement.webui.util.DateUtils;

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
	@Autowired
	private RfqRepository rfqRepo;
	@Autowired
	private RfqQtyRepository rfqQuantityRepo;
	@Autowired
	private IServerSyncService syncService;

	@Override
	public List<RfqHeader> getActiveRfqHeaders(final User user)
	{
		final BPartner bpartner = user.getBpartner();
		final List<Rfq> rfqs = rfqRepo.findByBpartnerAndClosedFalse(bpartner);

		final List<RfqHeader> rfqHeaders = new ArrayList<>(rfqs.size());
		for (final Rfq rfq : rfqs)
		{
			if (rfq.isClosed())
			{
				continue;
			}

			final List<RfqQuantityReport> rfqQuantities = retrieveRfqQuantityReports(rfq);
			final RfqHeader rfqHeader = RfqHeader.of(rfq, rfqQuantities);
			rfqHeaders.add(rfqHeader);
		}

		return RfqHeader.ORDERING_ByDateStart
				.compound(RfqHeader.ORDERING_ByProductName)
				.immutableSortedCopy(rfqHeaders);
	}

	@Override
	public RfqHeader getActiveRfqHeaderById(final long rfq_id)
	{
		Rfq rfq = rfqRepo.findOne(rfq_id);
		if (rfq == null)
		{
			return null;
		}
		
		if(rfq.isClosed())
		{
			return null;
		}
		
		final List<RfqQuantityReport> rfqQuantities = retrieveRfqQuantityReports(rfq);
		final RfqHeader rfqHeader = RfqHeader.of(rfq, rfqQuantities);
		return rfqHeader;
	}

	private List<RfqQuantityReport> retrieveRfqQuantityReports(final Rfq rfq)
	{
		Preconditions.checkNotNull(rfq, "rfq is null");
		final Map<Date, RfqQty> day2qtyExisting = getRfQQuantitiesIndexedByDatePromised(rfq);

		final String qtyCUInfo = rfq.getQtyCUInfo();

		final List<RfqQuantityReport> rfqQuantityReports = new ArrayList<>();
		final Date dateStart = DateUtils.truncToDay(rfq.getDateStart());
		final Date dateEnd = DateUtils.truncToDay(rfq.getDateEnd());
		for (final Date day : DateRange.of(dateStart, dateEnd).daysIterable())
		{
			final RfqQty rfqQty = day2qtyExisting.get(day);
			final RfqQuantityReport rfqQuantityReport;
			if (rfqQty == null)
			{
				rfqQuantityReport = RfqQuantityReport.of(rfq, day, qtyCUInfo);
			}
			else
			{
				rfqQuantityReport = RfqQuantityReport.of(rfq, day, rfqQty.getQtyPromised(), qtyCUInfo);
			}

			rfqQuantityReports.add(rfqQuantityReport);
		}

		return rfqQuantityReports;
	}

	private Map<Date, RfqQty> getRfQQuantitiesIndexedByDatePromised(final Rfq rfq)
	{
		final Map<Date, RfqQty> day2qty = new HashMap<>();
		for (final RfqQty qty : rfqQuantityRepo.findByRfq(rfq))
		{
			final Date day = DateUtils.truncToDay(qty.getDatePromised());
			day2qty.put(day, qty);
		}

		return day2qty;
	}

	@Override
	@Transactional
	public void send(final RfqHeader rfqHeader)
	{
		if (rfqHeader.checkSent())
		{
			return;
		}

		//
		// Save it
		save(rfqHeader);
	}

	private void save(final RfqHeader rfqHeader)
	{
		//
		// Save header
		final Rfq rfqRecord = rfqRepo.findByUuid(rfqHeader.getRfq_uuid());
		{
			rfqRecord.setPricePromised(rfqHeader.getPrice());
			rfqRepo.save(rfqRecord);

			syncService.syncAfterCommit().add(rfqRecord);
		}

		//
		// Save lines
		final Map<Date, RfqQty> rfqQuantityRecords = getRfQQuantitiesIndexedByDatePromised(rfqRecord);
		for (final RfqQuantityReport rfqQuantityReport : rfqHeader.getQuantities())
		{
			final Date day = rfqQuantityReport.getDay();
			RfqQty rfqQtyRecord = rfqQuantityRecords.get(day);
			if (rfqQtyRecord == null)
			{
				// Skip saving ZERO reports, if not already saved
				if (rfqQuantityReport.getQty().signum() == 0)
				{
					continue;
				}

				rfqQtyRecord = new RfqQty();
				rfqQtyRecord.setRfq(rfqRecord);
				rfqQtyRecord.setDatePromised(day);
			}

			rfqQtyRecord.setQtyPromised(rfqQuantityReport.getQty());
			rfqQuantityRepo.save(rfqQtyRecord);

			syncService.syncAfterCommit().add(rfqQtyRecord);
		}
	}
}
