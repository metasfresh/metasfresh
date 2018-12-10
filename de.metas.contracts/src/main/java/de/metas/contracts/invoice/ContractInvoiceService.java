package de.metas.contracts.invoice;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.impl.BPartnerTimeSpanRepository;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.invoice.InvoiceId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
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

@Service
public class ContractInvoiceService
{
	@Autowired
	private BPartnerTimeSpanRepository bpartnerTImeSpanRepo;

	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String SYS_CONFIG_C_BPartner_TimeSpan_Threshold = "C_BPartner_TimeSpan_Threshold";
	private static final int DEFAULT_Threshold_BPartner_TimeSpan = 12;

	public boolean isContractSalesInvoice(@NonNull final InvoiceId invoiceId)
	{
		final I_C_Invoice invoice = invoiceDAO.getById(invoiceId);

		if (!invoice.isSOTrx())
		{
			// only sales invoices allowed
			return false;
		}

		final List<I_C_InvoiceLine> invoiceLines = invoiceDAO.retrieveLines(invoice);

		for (final I_C_InvoiceLine invoiceLine : invoiceLines)
		{
			final boolean isContractSalesInvoiceLine = invoiceCandDAO.retrieveIcForIl(invoiceLine)
					.stream()
					.anyMatch(invoiceCandidate -> isSubscriptionInvoiceCandidate(invoiceCandidate));

			if (isContractSalesInvoiceLine)
			{
				return true;
			}
		}

		return false;
	}

	// public FlatrateTermId retrieveInvoiceContract(final I_C_Invoice invoice)
	// {
	// final List<I_C_InvoiceLine> invoiceLines = invoiceDAO.retrieveLines(invoice);
	//
	// final Set<FlatrateTermId> flatrateTermIds = new HashSet<>();
	//
	// for (final I_C_InvoiceLine invoiceLine : invoiceLines)
	// {
	// flatrateTermIds.addAll(invoiceCandDAO.retrieveIcForIl(invoiceLine)
	// .stream()
	// .filter(invoiceCandidate -> isSubscriptionInvoiceCandidate(invoiceCandidate))
	// .map(invoiceCandidate -> FlatrateTermId.ofRepoId(invoiceCandidate.getRecord_ID()))
	// .collect(ImmutableSet.toImmutableSet()));
	// }
	// }

	public InvoiceId retrievePredecessorSalesContractInvoiceId(final InvoiceId invoiceId)
	{
		final I_C_Invoice invoice = invoiceDAO.getById(invoiceId);

		final Optional<InvoiceId> predecessorInvoice = queryBL.createQueryBuilder(I_C_Invoice.class)
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_C_BPartner_ID, invoice.getC_BPartner_ID())
				.addNotEqualsFilter(I_C_Invoice.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID())
				.orderByDescending(I_C_Invoice.COLUMNNAME_DateInvoiced)
				.create()
				.listIds(InvoiceId::ofRepoId)
				.stream()
				.filter(olderInvoiceId -> isContractSalesInvoice(olderInvoiceId))
				.findFirst();

		return predecessorInvoice.get();
	}

	private boolean isSubscriptionInvoiceCandidate(final I_C_Invoice_Candidate invoiceCandidate)
	{
		final int tableId = invoiceCandidate.getAD_Table_ID();

		return getTableId(I_C_Flatrate_Term.class) == tableId;
	}

	public void updateBPartnerTimeSpan(final InvoiceId invoiceId)
	{
		final InvoiceId predecessorSalesContractInvoiceId = retrievePredecessorSalesContractInvoiceId(invoiceId);

		if (predecessorSalesContractInvoiceId == null)
		{
			// there is no predecessor. Nothing to do
			return;
		}

		final I_C_Invoice invoice = invoiceDAO.getById(invoiceId);
		final I_C_Invoice predecessorInvoice = invoiceDAO.getById(predecessorSalesContractInvoiceId);

		final LocalDate dateInvoiced = TimeUtil.asLocalDate(invoice.getDateInvoiced());
		final LocalDate predecessorDateInvoiced = TimeUtil.asLocalDate(predecessorInvoice.getDateInvoiced());

		final int monthsBetweenInvoiceDates = (int)Period.between(predecessorDateInvoiced, dateInvoiced)
				.toTotalMonths();

		final int bpartnerTimeSpanThreshold = sysConfigBL.getIntValue(SYS_CONFIG_C_BPartner_TimeSpan_Threshold, DEFAULT_Threshold_BPartner_TimeSpan);

		if (bpartnerTimeSpanThreshold > monthsBetweenInvoiceDates)
		{
			final BPartnerId bpartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
			bpartnerTImeSpanRepo.setRegularCustomer(bpartnerId);
		}
	}
}
