package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Customer_Retention;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.X_C_Customer_Retention;
import org.compiere.util.TimeUtil;
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
import de.metas.util.lang.CoalesceUtil;
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
public class CustomerRetentionRepository
{
	public final transient String SYS_CONFIG_C_CUSTOMER_RETENTION_Threshold = "C_Customer_Retention_Threshold";
	public final transient int DEFAULT_Threshold_CustomerRetention = 12;

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	private final IContractsDAO contractsDAO = Services.get(IContractsDAO.class);

	@Autowired
	private ContractInvoiceService contractInvoiceService;

	public I_C_Customer_Retention createNewCustomerRetention(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_Customer_Retention customerRetention = newInstance(I_C_Customer_Retention.class);

		customerRetention.setC_BPartner_ID(bpartnerId.getRepoId());

		customerRetention.setCustomerRetention(null);

		save(customerRetention);

		return customerRetention;
	}

	public void setNewCustomer(@NonNull final BPartnerId bpartnerId)
	{
		I_C_Customer_Retention customerRetention = retrieveCustomerRetention(bpartnerId);

		if (customerRetention == null)
		{
			customerRetention = createNewCustomerRetention(bpartnerId);
		}

		customerRetention.setCustomerRetention(X_C_Customer_Retention.CUSTOMERRETENTION_Neukunde);
		save(customerRetention);
	}

	public void setRegularCustomer(@NonNull final BPartnerId bpartnerId)
	{
		I_C_Customer_Retention customerRetention = retrieveCustomerRetention(bpartnerId);

		if (customerRetention == null)
		{
			customerRetention = createNewCustomerRetention(bpartnerId);
		}

		customerRetention.setCustomerRetention(X_C_Customer_Retention.CUSTOMERRETENTION_Stammkunde);
		save(customerRetention);
	}

	public void setNonSubscriptionCustomer(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_Customer_Retention customerRetention = retrieveCustomerRetention(bpartnerId);
		customerRetention.setCustomerRetention(null);
		save(customerRetention);
	}

	public I_C_Customer_Retention retrieveCustomerRetention(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_Customer_Retention firstOnly = queryBL.createQueryBuilder(I_C_Customer_Retention.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Customer_Retention.COLUMN_C_BPartner_ID, bpartnerId.getRepoId())
				.create()
				.firstOnly(I_C_Customer_Retention.class);
		if (firstOnly != null)
		{
			return firstOnly;
		}
		else
		{
			return createNewCustomerRetention(bpartnerId);
		}
	}

	public boolean isNewCustomer(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_Customer_Retention customerRetention = retrieveCustomerRetention(bpartnerId);

		final String currentCustomerRetention = customerRetention.getCustomerRetention();

		return Objects.equals(X_C_Customer_Retention.CUSTOMERRETENTION_Neukunde, currentCustomerRetention);
	}

	public ImmutableSet<BPartnerId> retrieveBPartnerIdsWithCustomerRetention()
	{
		return queryBL.createQueryBuilder(I_C_Customer_Retention.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addNotNull(I_C_Customer_Retention.COLUMN_CustomerRetention)
				.andCollect(I_C_Customer_Retention.COLUMN_C_BPartner_ID, I_C_BPartner.class)
				.create()
				.listIds(BPartnerId::ofRepoId);
	}

	public int retrieveCustomerRetentionThreshold()
	{
		return sysConfigBL.getIntValue(SYS_CONFIG_C_CUSTOMER_RETENTION_Threshold, DEFAULT_Threshold_CustomerRetention);
	}

	public boolean hasCustomerRetention(final BPartnerId bpartnerId)
	{
		return !Check.isEmpty(retrieveCustomerRetention(bpartnerId));
	}

	public void updateCustomerRetention(final BPartnerId bpartnerId)
	{
		final I_C_Flatrate_Term latestFlatrateTermForBPartnerId = contractsDAO.retrieveLatestFlatrateTermForBPartnerId(bpartnerId);

		if (latestFlatrateTermForBPartnerId == null)
		{
			// nothing to change and should not happen
			return;
		}

		final Timestamp contractEndDate = CoalesceUtil.coalesce(latestFlatrateTermForBPartnerId.getMasterEndDate(), latestFlatrateTermForBPartnerId.getEndDate());

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
			updateCustomerRetentionAfterInvoiceId(bpartnerId, lastSalesContractInvoiceId);
		}

	}

	public boolean dateExceedsThreshold(final Timestamp contractEndDate, Timestamp dateToCompare)
	{
		final LocalDate currentDate = TimeUtil.asLocalDate(dateToCompare);
		final LocalDate contractEndLocalDate = TimeUtil.asLocalDate(contractEndDate);

		final int customerRetentionThreshold = retrieveCustomerRetentionThreshold();

		return currentDate.minusMonths(customerRetentionThreshold).isAfter(contractEndLocalDate);

	}

	public void updateCustomerRetentionOnInvoiceComplete(final InvoiceId invoiceId)
	{
		if (!contractInvoiceService.isContractSalesInvoice(invoiceId))
		{
			// nothing to do
			return;
		}

		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(invoiceId);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());

		if (!hasCustomerRetention(bpartnerId))
		{
			createNewCustomerRetention(bpartnerId);
		}

		if (!isNewCustomer(bpartnerId))
		{
			// only change bpartners that are already marked as "New Customer"
			return;
		}

		updateCustomerRetentionAfterInvoiceId(bpartnerId, invoiceId);
	}

	private void updateCustomerRetentionAfterInvoiceId(final BPartnerId bpartnerId, final InvoiceId invoiceId)
	{
		final InvoiceId predecessorSalesContractInvoiceId = contractInvoiceService.retrievePredecessorSalesContractInvoiceId(invoiceId);

		if (Check.isEmpty(predecessorSalesContractInvoiceId))
		{
			setNewCustomer(bpartnerId);
		}
		else
		{
			final I_C_Invoice predecessorInvoice = invoiceDAO.getByIdInTrx(predecessorSalesContractInvoiceId);
			final I_C_Invoice lastInvoice = invoiceDAO.getByIdInTrx(invoiceId);

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

	public void deteleCustomerRetention(final BPartnerId bpartnerId)
	{
		final I_C_Customer_Retention customerRetention = retrieveCustomerRetention(bpartnerId);

		delete(customerRetention);
	}
}
