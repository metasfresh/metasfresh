package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_TimeSpan;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.X_C_BPartner_TimeSpan;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.invoice.ContractInvoiceService;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.invoice.InvoiceId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class BPartnerTimeSpanRepository
{
	public final transient String SYS_CONFIG_C_BPartner_TimeSpan_Threshold = "C_BPartner_TimeSpan_Threshold";
	public final transient int DEFAULT_Threshold_BPartner_TimeSpan = 12;

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	private final IContractsDAO contractsDAO = Services.get(IContractsDAO.class);

	@Autowired
	private ContractInvoiceService contractInvoiceService;

	public I_C_BPartner_TimeSpan createNewBPartnerTimeSpan(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner_TimeSpan timeSpan = newInstance(I_C_BPartner_TimeSpan.class);

		timeSpan.setC_BPartner_ID(bpartnerId.getRepoId());

		timeSpan.setC_BPartner_TimeSpan(null);

		save(timeSpan);

		return timeSpan;
	}

	public void setNewCustomer(@NonNull final BPartnerId bpartnerId)
	{
		I_C_BPartner_TimeSpan bpartnerTimeSpan = retrieveBPartnerTimeSpan(bpartnerId);

		if (bpartnerTimeSpan == null)
		{
			bpartnerTimeSpan = createNewBPartnerTimeSpan(bpartnerId);
		}

		bpartnerTimeSpan.setC_BPartner_TimeSpan(X_C_BPartner_TimeSpan.C_BPARTNER_TIMESPAN_Neukunde);
		save(bpartnerTimeSpan);
	}

	public void setRegularCustomer(@NonNull final BPartnerId bpartnerId)
	{
		I_C_BPartner_TimeSpan bpartnerTimeSpan = retrieveBPartnerTimeSpan(bpartnerId);

		if (bpartnerTimeSpan == null)
		{
			bpartnerTimeSpan = createNewBPartnerTimeSpan(bpartnerId);
		}

		bpartnerTimeSpan.setC_BPartner_TimeSpan(X_C_BPartner_TimeSpan.C_BPARTNER_TIMESPAN_Stammkunde);
		save(bpartnerTimeSpan);
	}

	public void setNonSubscriptionCustomer(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner_TimeSpan bpartnerTimeSpan = retrieveBPartnerTimeSpan(bpartnerId);
		bpartnerTimeSpan.setC_BPartner_TimeSpan(null);
		save(bpartnerTimeSpan);
	}

	public I_C_BPartner_TimeSpan retrieveBPartnerTimeSpan(@NonNull final BPartnerId bpartnerId)
	{
		return queryBL.createQueryBuilder(I_C_BPartner_TimeSpan.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_BPartner_TimeSpan.COLUMN_C_BPartner_ID, bpartnerId.getRepoId())
				.create()
				.firstOnly(I_C_BPartner_TimeSpan.class);
	}

	public boolean isNewCustomer(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner_TimeSpan bpartnerTimeSpan = retrieveBPartnerTimeSpan(bpartnerId);

		final String currentTimeSpan = bpartnerTimeSpan.getC_BPartner_TimeSpan();

		return Objects.equals(X_C_BPartner_TimeSpan.C_BPARTNER_TIMESPAN_Neukunde, currentTimeSpan);
	}

	public ImmutableSet<BPartnerId> retrieveBPartnerIdsWithTimeSpan()
	{
		return queryBL.createQueryBuilder(I_C_BPartner_TimeSpan.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addNotNull(I_C_BPartner_TimeSpan.COLUMN_C_BPartner_TimeSpan)
				.andCollect(I_C_BPartner_TimeSpan.COLUMN_C_BPartner_ID, I_C_BPartner.class)
				.create()
				.listIds(BPartnerId::ofRepoId);
	}

	public int retrieveBPartnerTimeSpanThreshold()
	{
		return sysConfigBL.getIntValue(SYS_CONFIG_C_BPartner_TimeSpan_Threshold, DEFAULT_Threshold_BPartner_TimeSpan);
	}

	public boolean hasTimeSpan(BPartnerId bpartnerId)
	{
		return !Check.isEmpty(retrieveBPartnerTimeSpan(bpartnerId));
	}

	public void updateTimeSpan(final BPartnerId bpartnerId)
	{
		final I_C_Flatrate_Term latestFlatrateTermForBPartnerId = contractsDAO.retrieveLatestFlatrateTermForBPartnerId(bpartnerId);

		if (latestFlatrateTermForBPartnerId == null)
		{
			// nothing to change and should not happen
			return;
		}

		final Timestamp contractEndDate = Util.coalesce(latestFlatrateTermForBPartnerId.getMasterEndDate(), latestFlatrateTermForBPartnerId.getEndDate());

		if (dateExceedsThreshold(contractEndDate, SystemTime.asTimestamp()))
		{
			setNonSubscriptionCustomer(bpartnerId);

			return;
		}
		else
		{
			setNewCustomer(bpartnerId);
		}

		final InvoiceId lastSalesContractInvoiceId = contractInvoiceService.retrieveLastSalesContractInvoiceId(bpartnerId);

		if (!Check.isEmpty(lastSalesContractInvoiceId))
		{
			final InvoiceId predecessorSalesContractInvoiceId = contractInvoiceService.retrievePredecessorSalesContractInvoiceId(lastSalesContractInvoiceId);

			if (Check.isEmpty(predecessorSalesContractInvoiceId))
			{
				setNewCustomer(bpartnerId);
			}
			else
			{
				final I_C_Invoice predecessorInvoice = invoiceDAO.getByIdInTrx(predecessorSalesContractInvoiceId);
				final I_C_Invoice lastInvoice = invoiceDAO.getByIdInTrx(lastSalesContractInvoiceId);

				final Timestamp predecessorInvoiceDate = predecessorInvoice.getDateInvoiced();
				final Timestamp lastInvoiceDate = lastInvoice.getDateInvoiced();

				if (dateExceedsThreshold(predecessorInvoiceDate, lastInvoiceDate))
				{
					setNewCustomer(bpartnerId);
				}
				else
				{
					setRegularCustomer(bpartnerId);
				}
			}
		}

	}

	public boolean dateExceedsThreshold(final Timestamp contractEndDate, Timestamp dateToCompare)
	{
		final LocalDate currentDate = TimeUtil.asLocalDate(dateToCompare);
		final LocalDate contractEndLocalDate = TimeUtil.asLocalDate(contractEndDate);

		final int bpTimeSpanThreshold = retrieveBPartnerTimeSpanThreshold();

		return currentDate.minusMonths(bpTimeSpanThreshold).isAfter(contractEndLocalDate);

	}

	public void updateTimeSpanOnInvoiceComplete(final InvoiceId invoiceId)
	{
		if (!contractInvoiceService.isContractSalesInvoice(invoiceId))
		{
			// nothing to do
			return;
		}

		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(invoiceId);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());

		if (!isNewCustomer(bpartnerId))
		{
			// only change bpartners that are already marked as "New Customer"
			return;
		}

		updateTimeSpan(bpartnerId);
	}
}
