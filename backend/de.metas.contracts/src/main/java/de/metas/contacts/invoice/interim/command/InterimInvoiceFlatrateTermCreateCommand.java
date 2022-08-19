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
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.contacts.invoice.interim.InterimInvoiceFlatrateTerm;
import de.metas.contacts.invoice.interim.InterimInvoiceSettings;
import de.metas.contacts.invoice.interim.InterimInvoiceSettingsId;
import de.metas.contacts.invoice.interim.service.IInterimInvoiceFlatrateTermBL;
import de.metas.contacts.invoice.interim.service.IInterimInvoiceFlatrateTermDAO;
import de.metas.contacts.invoice.interim.service.IInterimInvoiceFlatrateTermLineDAO;
import de.metas.contacts.invoice.interim.service.IInterimInvoiceSettingsDAO;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.process.FlatrateTermCreator;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.impl.ManualCandidateHandler;
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
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

public class InterimInvoiceFlatrateTermCreateCommand
{
	public static final AdMessageKey MSG_PricingSystemsDoNotMatch = AdMessageKey.of("de.metas.contacts.invoice.interim.PricingSystemsDoNotMatch");

	private static final Logger logger = LogManager.getLogger(InterimInvoiceFlatrateTermCreateCommand.class);

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

	private final OrderLine orderLine;
	private final I_C_Flatrate_Conditions conditions;
	private final InterimInvoiceSettings interimInvoiceSettings;
	private final I_M_Product product;

	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IInterimInvoiceSettingsDAO interimInvoiceSettingsDAO = Services.get(IInterimInvoiceSettingsDAO.class);
	private final IInterimInvoiceFlatrateTermDAO interimInvoiceOverviewDAO = Services.get(IInterimInvoiceFlatrateTermDAO.class);
	private final IInterimInvoiceFlatrateTermLineDAO interimInvoiceOverviewLineDAO = Services.get(IInterimInvoiceFlatrateTermLineDAO.class);
	private final IInterimInvoiceFlatrateTermBL interimInvoiceOverviewBL = Services.get(IInterimInvoiceFlatrateTermBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IInvoiceCandidateHandlerDAO invoiceCandidateHandlerDAO = Services.get(IInvoiceCandidateHandlerDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final OrderLineRepository orderLineRepository = SpringContextHolder.instance.getBean(OrderLineRepository.class);

	@Builder
	public InterimInvoiceFlatrateTermCreateCommand(@NonNull final Properties ctx,
			@Nullable final BPartnerId bpartnerId,
			@NonNull final ConditionsId conditionsId,
			@Nullable final ProductId productId,
			@NonNull final Timestamp dateFrom,
			@NonNull final Timestamp dateTo,
			@NonNull final OrderLineId orderLineId)
	{
		this.ctx = ctx;

		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.orderLineId = orderLineId;
		this.orderLine = orderLineRepository.getById(orderLineId);
		this.bpartnerId = Objects.requireNonNull(
				CoalesceUtil.coalesceSuppliers(
						() -> bpartnerId,
						() -> BPartnerId.ofRepoId(orderDAO.getById(orderLine.getOrderId()).getC_BPartner_ID())));
		this.productId = Objects.requireNonNull(CoalesceUtil.coalesce(productId, orderLine.getProductId()));
		this.conditions = flatrateDAO.getConditionsById(conditionsId);
		this.interimInvoiceSettings = interimInvoiceSettingsDAO.getById(InterimInvoiceSettingsId.ofRepoId(conditions.getC_Interim_Invoice_Settings_ID()));
		this.product = productDAO.getById(this.productId);
	}

	public InterimInvoiceFlatrateTerm execute()
	{
		return trxManager.callInThreadInheritedTrx(this::execute0);
	}

	private InterimInvoiceFlatrateTerm execute0()
	{
		final I_C_Flatrate_Term flatrateTerm = getOrCreateFlatrateTerm();

		final I_C_Invoice_Candidate existingIC = getInvoiceCandidateForOrderLine();

		final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm = getOrCreateInterimInvoiceOverview(flatrateTerm, existingIC);

		updateDeliveredSoFarQty(interimInvoiceFlatrateTerm);

		return interimInvoiceFlatrateTerm;
	}

	private void updateDeliveredSoFarQty(@NonNull final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm)
	{
		final Collection<InOutAndLineId> existingInOuts = getUnassignedInOutLinesForOrderLineId();

		if (existingInOuts.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Created InterimInvoiceFlatrateTerm={} but no matching InOuts found.", interimInvoiceFlatrateTerm.getId());
		}
		else
		{
			existingInOuts.forEach(inOutLine -> interimInvoiceOverviewLineDAO.createInterimInvoiceLine(interimInvoiceFlatrateTerm, inOutLine));
			interimInvoiceOverviewBL.updateQuantities(interimInvoiceFlatrateTerm);
		}
	}

	private InterimInvoiceFlatrateTerm getOrCreateInterimInvoiceOverview(final I_C_Flatrate_Term flatrateTerm, final I_C_Invoice_Candidate existingIC)
	{
		final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm = interimInvoiceOverviewDAO.getInterimInvoiceForFlatrateTermAndOrderLineId(FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()), orderLineId);
		if (interimInvoiceFlatrateTerm != null && interimInvoiceOverviewDAO.isInterimInvoiceStillUsable(interimInvoiceFlatrateTerm))
		{
			return interimInvoiceFlatrateTerm;
		}
		else
		{
			final I_C_Invoice_Candidate withholdingIC = createWithholdingIC(flatrateTerm, existingIC);
			final InvoiceCandidateId withholdingICId = InvoiceCandidateId.ofRepoId(withholdingIC.getC_Invoice_Candidate_ID());
			final I_C_Invoice_Candidate interimIC = createInterimIC(flatrateTerm, existingIC);
			final InvoiceCandidateId interimICId = InvoiceCandidateId.ofRepoId(interimIC.getC_Invoice_Candidate_ID());

			return createInterimInvoiceOverview(flatrateTerm, withholdingICId, interimICId);
		}
	}

	@NonNull
	private I_C_Invoice_Candidate getInvoiceCandidateForOrderLine()
	{
		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveInvoiceCandidatesForOrderLineId(orderLineId);

		if (invoiceCandidates.size() > 1)
		{
			//should not happen
			throw new AdempiereException("More than one invoice candidate found for orderLineId=" + orderLineId);
		}
		return invoiceCandidates.stream()
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No invoice candidate found for orderLineId=" + orderLineId));
	}

	@NonNull
	private I_C_Flatrate_Term getOrCreateFlatrateTerm()
	{
		Check.assumeEquals(conditions.getType_Conditions(), X_C_Flatrate_Conditions.TYPE_CONDITIONS_InterimInvoice);
		final I_C_BPartner bpartner = bpartnerDAO.getById(bpartnerId);
		final Optional<I_C_Flatrate_Term> existingFlatrateTerm = getExistingFlatrateTerm(bpartner);
		return existingFlatrateTerm.orElseGet(() -> createFlatrateTerm(bpartner));
	}

	private Optional<I_C_Flatrate_Term> getExistingFlatrateTerm(final @NonNull I_C_BPartner bpartner)
	{
		return flatrateDAO.retrieveTerms(bpartner, conditions)
				.stream()
				.filter(term -> dateFrom.equals(term.getStartDate()))
				.filter(term -> dateTo.equals(term.getEndDate()))
				.findFirst();
	}

	@NonNull
	private I_C_Flatrate_Term createFlatrateTerm(final I_C_BPartner bpartner)
	{
		return FlatrateTermCreator.builder()
				.bPartners(Collections.singleton(bpartner))
				.orgId(orderLine.getOrgId())
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

	private InterimInvoiceFlatrateTerm createInterimInvoiceOverview(@NonNull final I_C_Flatrate_Term flatrateTerm, @NonNull final InvoiceCandidateId witholdingICId, @NonNull final InvoiceCandidateId interimICId)
	{
		final I_C_Order order = orderDAO.getById(orderLine.getOrderId());

		final UomId productUomId = UomId.ofRepoId(product.getC_UOM_ID());
		final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm = InterimInvoiceFlatrateTerm.builder()
				.flatrateTermId(FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()))
				.calendarId(interimInvoiceSettings.getHarvestingCalendarId())
				.orderLineId(orderLineId)
				.qtyOrdered(uomConversionBL.convertQuantityTo(orderLine.getOrderedQty(), productId, productUomId).toBigDecimal())
				.qtyDelivered(BigDecimal.ZERO)
				.qtyInvoiced(BigDecimal.ZERO)
				.productId(productId)
				.uomId(productUomId)
				.priceActual(BigDecimal.ZERO)
				.currencyId(CurrencyId.ofRepoId(order.getC_Currency_ID()))

				.withholdingInvoiceCandidateId(witholdingICId)
				.interimInvoiceCandidateId(interimICId)
				.build();

		return interimInvoiceOverviewDAO.save(interimInvoiceFlatrateTerm);
	}

	private I_C_Invoice_Candidate createWithholdingIC(final I_C_Flatrate_Term flatrateTerm, final I_C_Invoice_Candidate existingIC)
	{
		final I_C_Invoice_Candidate withholdingIC = copyInvoiceCandidate(flatrateTerm, existingIC);

		withholdingIC.setM_Product_ID(interimInvoiceSettings.getWithholdingProductId().getRepoId());
		invoiceCandDAO.save(withholdingIC);
		return withholdingIC;
	}

	private I_C_Invoice_Candidate copyInvoiceCandidate(final I_C_Flatrate_Term flatrateTerm, final I_C_Invoice_Candidate existingIC)
	{
		final I_C_Invoice_Candidate copiedIC = InterfaceWrapperHelper.copy()
				.setFrom(existingIC)
				.copyToNew(I_C_Invoice_Candidate.class);
		final I_C_ILCandHandler handlerRecord = invoiceCandidateHandlerDAO.retrieveForClassOneOnly(Env.getCtx(), ManualCandidateHandler.class);
		copiedIC.setC_ILCandHandler_ID(handlerRecord.getC_ILCandHandler_ID());
		copiedIC.setC_Flatrate_Term_ID(flatrateTerm.getC_Flatrate_Term_ID());
		copiedIC.setRecord_ID(flatrateTerm.getC_Flatrate_Term_ID());
		copiedIC.setAD_Table_ID(tableDAO.retrieveTableId(I_C_Flatrate_Term.Table_Name));
		copiedIC.setQtyInvoiced(BigDecimal.ZERO);
		copiedIC.setQtyToInvoice_Override(null);
		copiedIC.setQtyInvoicedInUOM(BigDecimal.ZERO);
		copiedIC.setQtyDelivered(BigDecimal.ZERO);
		copiedIC.setQtyToInvoice(BigDecimal.ZERO);
		copiedIC.setQtyToInvoiceInUOM(BigDecimal.ZERO);
		copiedIC.setQtyOrdered(BigDecimal.ZERO);
		copiedIC.setQtyEntered(BigDecimal.ZERO);
		copiedIC.setQtyDeliveredInUOM(BigDecimal.ZERO);
		copiedIC.setC_OrderLine_ID(0);
		copiedIC.setC_Order_ID(0);
		return copiedIC;
	}

	private I_C_Invoice_Candidate createInterimIC(final I_C_Flatrate_Term flatrateTerm, final I_C_Invoice_Candidate existingIC)
	{
		final I_C_Invoice_Candidate interimIC = copyInvoiceCandidate(flatrateTerm, existingIC);

		interimIC.setIsInterimInvoice(true);
		invoiceCandDAO.save(interimIC);
		return interimIC;
	}

	private Collection<InOutAndLineId> getUnassignedInOutLinesForOrderLineId()
	{
		return inOutDAO.retrieveLineIdsForOrderLineIdAvailableForInterimInvoice(orderLineId, product);
	}

}
