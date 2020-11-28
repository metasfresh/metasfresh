package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.time.LocalDate;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Customer_Retention;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.X_C_Customer_Retention;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.annotations.VisibleForTesting;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.CustomerRetentionId;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.invoice.ContractInvoiceService;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.order.ContractOrderService;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
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
	private final ContractInvoiceService contractInvoiceService;

	public I_C_Customer_Retention getById(@NonNull final CustomerRetentionId customerRetentionId)
	{
		return load(customerRetentionId.getRepoId(), I_C_Customer_Retention.class);
	}

	public CustomerRetentionRepository(@NonNull final ContractInvoiceService contractInvoiceService)
	{
		this.contractInvoiceService = contractInvoiceService;
	}

	public CustomerRetentionId createEmptyCustomerRetention(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_Customer_Retention customerRetention = newInstance(I_C_Customer_Retention.class);

		customerRetention.setC_BPartner_ID(bpartnerId.getRepoId());

		customerRetention.setCustomerRetention(null);

		save(customerRetention);

		return CustomerRetentionId.ofRepoId(customerRetention.getC_Customer_Retention_ID());
	}

	@VisibleForTesting
	void setNewCustomer(@NonNull final CustomerRetentionId customerRetentionId)
	{
		final I_C_Customer_Retention customerRetention = getById(customerRetentionId);

		Check.assumeNotNull(customerRetention, "Customer retention must not be null");
		customerRetention.setCustomerRetention(X_C_Customer_Retention.CUSTOMERRETENTION_Neukunde);
		save(customerRetention);
	}

	@VisibleForTesting
	void setRegularCustomer(@NonNull final CustomerRetentionId customerRetentionId)
	{
		final I_C_Customer_Retention customerRetention = getById(customerRetentionId);
		Check.assumeNotNull(customerRetention, "Customer retention must not be null");

		customerRetention.setCustomerRetention(X_C_Customer_Retention.CUSTOMERRETENTION_Stammkunde);
		save(customerRetention);
	}

	@VisibleForTesting
	void setNonSubscriptionCustomer(@NonNull final CustomerRetentionId customerRetentionId)
	{
		final I_C_Customer_Retention customerRetention = getById(customerRetentionId);
		Check.assumeNotNull(customerRetention, "Customer retention must not be null");

		customerRetention.setCustomerRetention(null);
		save(customerRetention);
	}

	private CustomerRetentionId retrieveOrCreateCustomerRetention(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_Customer_Retention customerRetention = retrieveExistingCustomerRetention(bpartnerId);

		if (customerRetention != null)
		{
			return CustomerRetentionId.ofRepoId(customerRetention.getC_Customer_Retention_ID());
		}

		return createEmptyCustomerRetention(bpartnerId);

	}

	private I_C_Customer_Retention retrieveExistingCustomerRetention(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_Customer_Retention existingCustomerRetention = queryBL.createQueryBuilder(I_C_Customer_Retention.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Customer_Retention.COLUMNNAME_C_BPartner_ID, bpartnerId.getRepoId())
				.create()
				.firstOnly(I_C_Customer_Retention.class);

		return existingCustomerRetention;

	}

	@VisibleForTesting
	int retrieveCustomerRetentionThreshold()
	{
		return sysConfigBL.getIntValue(SYS_CONFIG_C_CUSTOMER_RETENTION_Threshold, DEFAULT_Threshold_CustomerRetention);
	}

	public void createUpdateCustomerRetention(@NonNull final BPartnerId bpartnerId)
	{
		final CustomerRetentionId customerRetentionId = retrieveOrCreateCustomerRetention(bpartnerId);

		final I_C_Flatrate_Term latestFlatrateTermForBPartnerId = contractsDAO.retrieveLatestFlatrateTermForBPartnerId(bpartnerId);

		if (Check.isEmpty(latestFlatrateTermForBPartnerId))
		{
			return;
		}
		final LocalDate contractEndDate = TimeUtil.asLocalDate(
				CoalesceUtil.coalesce(
						latestFlatrateTermForBPartnerId.getMasterEndDate(),
						latestFlatrateTermForBPartnerId.getEndDate()));

		if (dateExceedsThreshold(contractEndDate, SystemTime.asLocalDate()))
		{
			setNonSubscriptionCustomer(customerRetentionId);

			return;
		}
		else
		{
			setNewCustomer(customerRetentionId);
		}

		final InvoiceId lastSalesContractInvoiceId = contractInvoiceService.retrieveLastSalesContractInvoiceId(bpartnerId);

		if (!Check.isEmpty(lastSalesContractInvoiceId))
		{
			updateCustomerRetentionAfterInvoiceId(customerRetentionId, lastSalesContractInvoiceId);
		}

	}

	@VisibleForTesting
	boolean dateExceedsThreshold(@NonNull final LocalDate contractEndDate, @NonNull final LocalDate dateToCompare)
	{
		final int customerRetentionThreshold = retrieveCustomerRetentionThreshold();

		return dateToCompare.minusMonths(customerRetentionThreshold).isAfter(contractEndDate);
	}

	public void updateCustomerRetentionOnInvoiceComplete(@NonNull final InvoiceId invoiceId)
	{
		if (!contractInvoiceService.isContractSalesInvoice(invoiceId))
		{
			// nothing to do
			return;
		}

		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(invoiceId);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());

		final CustomerRetentionId customerRetentionId = retrieveOrCreateCustomerRetention(bpartnerId);

		updateCustomerRetentionAfterInvoiceId(customerRetentionId, invoiceId);
	}

	private void updateCustomerRetentionAfterInvoiceId(@NonNull final CustomerRetentionId customerRetentionId,
			@NonNull final InvoiceId invoiceId)
	{

		final InvoiceId predecessorSalesContractInvoiceId = contractInvoiceService.retrievePredecessorSalesContractInvoiceId(invoiceId);

		if (Check.isEmpty(predecessorSalesContractInvoiceId))
		{
			setNewCustomer(customerRetentionId);
		}

		else
		{
			final I_C_Invoice predecessorInvoice = invoiceDAO.getByIdInTrx(predecessorSalesContractInvoiceId);
			final I_C_Invoice lastInvoice = invoiceDAO.getByIdInTrx(invoiceId);

			final LocalDate predecessorInvoiceDate = TimeUtil.asLocalDate(predecessorInvoice.getDateInvoiced());
			final LocalDate lastInvoiceDate = TimeUtil.asLocalDate(lastInvoice.getDateInvoiced());

			final LocalDate predecessorInvoiceContractEndDate = contractInvoiceService.retrieveContractEndDateForInvoiceIdOrNull(predecessorSalesContractInvoiceId);

			final LocalDate dateToCompareThreshold;

			if (predecessorInvoiceContractEndDate == null)
			{
				dateToCompareThreshold = predecessorInvoiceDate;
			}

			else
			{
				dateToCompareThreshold = predecessorInvoiceContractEndDate.isAfter(predecessorInvoiceDate) ? predecessorInvoiceContractEndDate
						: predecessorInvoiceDate;
			}

			if (dateExceedsThreshold(dateToCompareThreshold, lastInvoiceDate))
			{
				setNewCustomer(customerRetentionId);
			}

			else
			{
				setRegularCustomer(customerRetentionId);
			}
		}
	}

	public void deleteCustomerRetention(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_Customer_Retention customerRetention = retrieveExistingCustomerRetention(bpartnerId);

		delete(customerRetention);
	}

	public void updateCustomerRetentionOnOrderComplete(@NonNull final OrderId orderId)
	{
		final ContractOrderService contractOrderService = SpringContextHolder.instance.getBean(ContractOrderService.class);
		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

		if (!contractOrderService.isContractSalesOrder(orderId))
		{
			// nothing to do
			return;
		}

		final I_C_Order order = orderDAO.getById(orderId);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());

		final CustomerRetentionId customerRetentionId = retrieveOrCreateCustomerRetention(bpartnerId);

		final I_C_Customer_Retention customerRetentionRecord = getById(customerRetentionId);

		if (Check.isEmpty(customerRetentionRecord.getCustomerRetention()))
		{
			setNewCustomer(customerRetentionId);
		}
	}
}
