package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Customer_Retention;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.X_C_Customer_Retention;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.CustomerRetentionId;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.invoice.ContractInvoiceService;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.order.ContractOrderService;
import de.metas.invoice.InvoiceId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
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
	private final ContractInvoiceService contractInvoiceService;

	public I_C_Customer_Retention getById(final CustomerRetentionId customerRetentionId)
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

	public void setNewCustomer(@NonNull final CustomerRetentionId customerRetentionId)
	{
		final I_C_Customer_Retention customerRetention = getById(customerRetentionId);

		customerRetention.setCustomerRetention(X_C_Customer_Retention.CUSTOMERRETENTION_Neukunde);
		save(customerRetention);
	}

	public void setRegularCustomer(@NonNull final CustomerRetentionId customerRetentionId)
	{
		final I_C_Customer_Retention customerRetention = getById(customerRetentionId);

		customerRetention.setCustomerRetention(X_C_Customer_Retention.CUSTOMERRETENTION_Stammkunde);
		save(customerRetention);
	}

	public void setNonSubscriptionCustomer(@NonNull final CustomerRetentionId customerRetentionId)
	{
		final I_C_Customer_Retention customerRetention = getById(customerRetentionId);

		customerRetention.setCustomerRetention(null);
		save(customerRetention);
	}

	public CustomerRetentionId retrieveOrCreateCustomerRetention(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_Customer_Retention customerRetention = retrieveExistingCustomerRetention(bpartnerId);

		if (customerRetention != null)
		{
			return CustomerRetentionId.ofRepoId(customerRetention.getC_Customer_Retention_ID());
		}

		return createEmptyCustomerRetention(bpartnerId);

	}

	public I_C_Customer_Retention retrieveExistingCustomerRetention(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_Customer_Retention existingCustomerRetention = queryBL.createQueryBuilder(I_C_Customer_Retention.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Customer_Retention.COLUMNNAME_C_BPartner_ID, bpartnerId.getRepoId())
				.create()
				.firstOnly(I_C_Customer_Retention.class);

		return existingCustomerRetention;

	}

	public boolean isNewCustomer(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_Customer_Retention customerRetention = retrieveExistingCustomerRetention(bpartnerId);

		if (customerRetention == null)
		{
			return false;
		}

		final String currentCustomerRetention = customerRetention.getCustomerRetention();

		return Objects.equals(X_C_Customer_Retention.CUSTOMERRETENTION_Neukunde, currentCustomerRetention);
	}

	public ImmutableSet<BPartnerId> retrieveBPartnerIdsWithCustomerRetention()
	{
		return queryBL.createQueryBuilder(I_C_Customer_Retention.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_C_Customer_Retention.COLUMN_CustomerRetention)
				.andCollect(I_C_Customer_Retention.COLUMN_C_BPartner_ID, I_C_BPartner.class)
				.create()
				.listIds(BPartnerId::ofRepoId);
	}

	public int retrieveCustomerRetentionThreshold()
	{
		return sysConfigBL.getIntValue(SYS_CONFIG_C_CUSTOMER_RETENTION_Threshold, DEFAULT_Threshold_CustomerRetention);
	}

	public boolean hasCustomerRetention(@NonNull final BPartnerId bpartnerId)
	{
		return !Check.isEmpty(retrieveExistingCustomerRetention(bpartnerId));
	}

	public void updateCustomerRetention(@NonNull final BPartnerId bpartnerId)
	{
		final CustomerRetentionId customerRetentionId = retrieveOrCreateCustomerRetention(bpartnerId);

		final I_C_Flatrate_Term latestFlatrateTermForBPartnerId = contractsDAO.retrieveLatestFlatrateTermForBPartnerId(bpartnerId);

		if (Check.isEmpty(latestFlatrateTermForBPartnerId))
		{
			return;
		}

		final Timestamp contractEndDate = CoalesceUtil.coalesce(latestFlatrateTermForBPartnerId.getMasterEndDate(), latestFlatrateTermForBPartnerId.getEndDate());

		if (dateExceedsThreshold(contractEndDate, SystemTime.asTimestamp()))
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

	public boolean dateExceedsThreshold(@NonNull final Timestamp contractEndDate, @NonNull final Timestamp dateToCompare)
	{
		final LocalDate currentDate = TimeUtil.asLocalDate(dateToCompare);
		final LocalDate contractEndLocalDate = TimeUtil.asLocalDate(contractEndDate);

		final int customerRetentionThreshold = retrieveCustomerRetentionThreshold();

		return currentDate.minusMonths(customerRetentionThreshold).isAfter(contractEndLocalDate);
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

		/*
		 * if (!isNewCustomer(bpartnerId))
		 * {
		 * // only change bpartners that are already marked as "New Customer" TODO WHY
		 * return;
		 * }
		 */

		updateCustomerRetentionAfterInvoiceId(customerRetentionId, invoiceId);
	}

	private void updateCustomerRetentionAfterInvoiceId(@NonNull final CustomerRetentionId customerRetentionId, @NonNull final InvoiceId invoiceId)
	{
		
		// TODO Compare with masterenddate from contract!!!
		final InvoiceId predecessorSalesContractInvoiceId = contractInvoiceService.retrievePredecessorSalesContractInvoiceId(invoiceId);

		if (Check.isEmpty(predecessorSalesContractInvoiceId))
		{
			setNewCustomer(customerRetentionId);
		}
		else
		{
			final I_C_Invoice predecessorInvoice = invoiceDAO.getByIdInTrx(predecessorSalesContractInvoiceId);
			final I_C_Invoice lastInvoice = invoiceDAO.getByIdInTrx(invoiceId);

			final Timestamp predecessorInvoiceDate = predecessorInvoice.getDateInvoiced();
			final Timestamp lastInvoiceDate = lastInvoice.getDateInvoiced();

			if (dateExceedsThreshold(predecessorInvoiceDate, lastInvoiceDate))
			{
				setNewCustomer(customerRetentionId);
			}

			else
			{
				setRegularCustomer(customerRetentionId);
			}
		}
	}

	public void deteleCustomerRetention(@NonNull final BPartnerId bpartnerId)
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
