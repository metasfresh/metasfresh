/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contacts.invoice.interim.command;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.contacts.invoice.interim.InterimInvoiceOverview;
import de.metas.contacts.invoice.interim.InterimInvoiceOverviewLine;
import de.metas.contacts.invoice.interim.InterimInvoiceSettings;
import de.metas.contacts.invoice.interim.InterimInvoiceSettingsId;
import de.metas.contacts.invoice.interim.service.IInterimInvoiceOverviewDAO;
import de.metas.contacts.invoice.interim.service.IInterimInvoiceSettingsDAO;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.process.FlatrateTermCreator;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutDocStatus;
import de.metas.inout.InOutLine;
import de.metas.inout.InOutLineQuery;
import de.metas.inout.impl.InOutDAO;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidate;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRepository;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLine;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLineRepository;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

public class InterimInvoiceFlatrateTermCreateCommand
{
	@NonNull
	private final Properties ctx;
	@NonNull
	private final BPartnerId bpartnerId;

	@NonNull
	private final ProductId productId;
	@NonNull
	private final Timestamp dateFrom;
	@NonNull
	private final Timestamp dateTo;
	@NonNull
	private final OrderLineId orderLineId;
	@NonNull
	private final Collection<InOutLine> inOutLines;

	private final OrderLine orderLine;
	private final I_C_Flatrate_Conditions conditions;
	private final InterimInvoiceSettings interimInvoiceSettings;
	private final I_M_Product product;

	ITrxManager trxManager = Services.get(ITrxManager.class);
	IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	IProductDAO productDAO = Services.get(IProductDAO.class);
	InOutDAO inOutDAO = Services.get(InOutDAO.class);
	IInterimInvoiceSettingsDAO interimInvoiceSettingsDAO = Services.get(IInterimInvoiceSettingsDAO.class);
	IInterimInvoiceOverviewDAO interimInvoiceOverviewDAO = Services.get(IInterimInvoiceOverviewDAO.class);
	IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	OrderLineRepository orderLineRepository = SpringContextHolder.instance.getBean(OrderLineRepository.class);
	InvoiceCandidateRepository invoiceCandidateRepository = SpringContextHolder.instance.getBean(InvoiceCandidateRepository.class);
	private static final Logger logger = LogManager.getLogger(InterimInvoiceFlatrateTermCreateCommand.class);

	@Builder
	public InterimInvoiceFlatrateTermCreateCommand(@NonNull final Properties ctx,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final ConditionsId conditionsId,
			@Nullable final ProductId productId,
			@NonNull final Timestamp dateFrom,
			@NonNull final Timestamp dateTo,
			@NonNull final OrderLineId orderLineId,
			@Singular @Nullable final Collection<InOutLine> inOutLines)
	{
		this.ctx = ctx;
		this.bpartnerId = bpartnerId;

		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.orderLineId = orderLineId;
		this.orderLine = orderLineRepository.getById(orderLineId);
		this.productId = CoalesceUtil.coalesce(productId, orderLine.getProductId());
		this.conditions = flatrateDAO.getConditionsById(conditionsId);
		this.inOutLines = CoalesceUtil.coalesce(inOutLines, Collections.emptyList());
		this.interimInvoiceSettings = interimInvoiceSettingsDAO.getById(InterimInvoiceSettingsId.ofRepoId(conditions.getC_Interim_Invoice_Settings_ID()));
		this.product = productDAO.getById(this.productId);
	}

	public void execute()
	{
		trxManager.runInThreadInheritedTrx(this::createInterimInvoiceFlatrateTerm);
	}

	private void createInterimInvoiceFlatrateTerm()
	{
		final I_C_Flatrate_Term flatrateTerm = createFlatrateTerm();

		final InvoiceCandidate witholdingIC = createWitholdingIC();
		final InvoiceCandidate interimIC = createWitholdingIC();

		final InterimInvoiceOverview interimInvoiceOverview = createInterimInvoiceOverview(flatrateTerm, witholdingIC.getId(), interimIC.getId());

		final Collection<InOutLine> matchingInOuts = getMatchingInOuts();

		matchingInOuts.forEach(inOutLine -> createInterimInvoiceLines(interimInvoiceOverview, inOutLine));
		if (matchingInOuts.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Created InterimInvoiceOverview={} but no matching InOuts found", interimInvoiceOverview.getId());
		}
	}

	private void createInterimInvoiceLines(@NonNull final InterimInvoiceOverview interimInvoiceOverview, @NonNull final InOutLine inOutLine)
	{
		InterimInvoiceOverviewLine.builder()
				.interimInvoiceOverviewId(interimInvoiceOverview.getId())
				.inOutAndLineId(InOutAndLineId.of(inOutLine.getInOutId(), inOutLine.getId()));
	}

	private I_C_Flatrate_Term createFlatrateTerm()
	{
		final I_C_BPartner bpartner = bpartnerDAO.getById(bpartnerId);

		Check.assumeEquals(conditions.getType_Conditions(), X_C_Flatrate_Conditions.TYPE_CONDITIONS_InterimInvoice);

		return FlatrateTermCreator.builder()
				.bPartners(Collections.singleton(bpartner))
				.conditions(conditions)
				.ctx(ctx)
				.product(product)
				.startDate(dateFrom)
				.endDate(dateTo)
				.isCompleteDocument(true)
				.build()
				.createTermsForBPartners()
				.stream()
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No flatrate term created"));
	}

	private InterimInvoiceOverview createInterimInvoiceOverview(@NonNull final I_C_Flatrate_Term flatrateTerm, @NonNull final InvoiceCandidateId witholdingICId, @NonNull final InvoiceCandidateId interimICId)
	{
		final I_C_Order order = orderDAO.getById(orderLine.getOrderId());

		final UomId productUomId = UomId.ofRepoId(product.getC_UOM_ID());
		final InterimInvoiceOverview interimInvoiceOverview = InterimInvoiceOverview.builder()
				.flatrateTermId(FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()))
				.calendarId(interimInvoiceSettings.getHarvestingCalendarId())
				.orderLineId(orderLineId)
				.qtyOrdered(uomConversionBL.convertQuantityTo(orderLine.getOrderedQty(), productId, productUomId).toBigDecimal())
				.qtyDelivered(BigDecimal.ZERO) // to make virtual ??
				.qtyInvoiced(BigDecimal.ZERO) // to make virtual ??
				.productId(productId)
				.uomId(productUomId)
				.priceActual(BigDecimal.ZERO) // dependent on orderLine price. How to calc if orderLine is moved at line level ??
				.currencyId(CurrencyId.ofRepoId(order.getC_Currency_ID())) // get from orderLine if it does not move. How to calc if orderLine is moved at line level ??

				.withholdingInvoiceCandidateId(witholdingICId)
				.partialPaymentInvoiceCandidateId(interimICId)
				.build();
		return interimInvoiceOverviewDAO.save(interimInvoiceOverview);
	}

	private InvoiceCandidate createWitholdingIC()
	{
		final InvoiceCandidate invoiceCandidate = InvoiceCandidate.builder()
				.build();

		return invoiceCandidate.toBuilder()
				.id(invoiceCandidateRepository.save(invoiceCandidate))
				.build();
	}

	private InvoiceCandidate createInterimIC()
	{
		final InvoiceCandidate invoiceCandidate = InvoiceCandidate.builder()
				.build();

		return invoiceCandidate.toBuilder()
				.id(invoiceCandidateRepository.save(invoiceCandidate))
				.build();
	}

	private Collection<InOutLine> getMatchingInOuts()
	{
		return inOutDAO.retrieveInOutLinesBy(InOutLineQuery.builder()
				.bPartnerId(bpartnerId)
				.productId(productId)
				.dateFrom(dateFrom)
				.dateTo(dateTo)
				.clientandOrgId(Env.getClientAndOrgId(ctx))
				.docStatuses(ImmutableList.of(InOutDocStatus.Closed, InOutDocStatus.Completed))
				.build());
	}
}
