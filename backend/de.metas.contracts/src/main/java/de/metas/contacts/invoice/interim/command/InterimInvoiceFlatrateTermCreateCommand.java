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
import de.metas.contacts.invoice.interim.service.IInterimInvoiceOverviewLineDAO;
import de.metas.contacts.invoice.interim.service.IInterimInvoiceSettingsDAO;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.process.FlatrateTermCreator;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutDocStatus;
import de.metas.inout.InOutLine;
import de.metas.inout.InOutLineQuery;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLine;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLineRepository;
import de.metas.organization.ClientAndOrgId;
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
import org.adempiere.ad.trx.api.ITrx;
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
import java.util.stream.Collectors;

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
	private final ClientAndOrgId clientAndOrgId;

	ITrxManager trxManager = Services.get(ITrxManager.class);
	IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	IProductDAO productDAO = Services.get(IProductDAO.class);
	IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	IInterimInvoiceSettingsDAO interimInvoiceSettingsDAO = Services.get(IInterimInvoiceSettingsDAO.class);
	IInterimInvoiceOverviewDAO interimInvoiceOverviewDAO = Services.get(IInterimInvoiceOverviewDAO.class);
	IInterimInvoiceOverviewLineDAO interimInvoiceOverviewLineDAO = Services.get(IInterimInvoiceOverviewLineDAO.class);
	IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	OrderLineRepository orderLineRepository = SpringContextHolder.instance.getBean(OrderLineRepository.class);
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
		this.productId = Objects.requireNonNull(CoalesceUtil.coalesce(productId, orderLine.getProductId()));
		this.conditions = flatrateDAO.getConditionsById(conditionsId);
		this.inOutLines = Objects.requireNonNull(CoalesceUtil.coalesce(inOutLines, Collections.emptyList()));
		this.interimInvoiceSettings = interimInvoiceSettingsDAO.getById(InterimInvoiceSettingsId.ofRepoId(conditions.getC_Interim_Invoice_Settings_ID()));
		this.product = productDAO.getById(this.productId);
		this.clientAndOrgId = Env.getClientAndOrgId(ctx);
	}

	public void execute()
	{
		trxManager.runInThreadInheritedTrx(this::execute0);
	}

	private void execute0()
	{
		final I_C_Flatrate_Term flatrateTerm = getOrCreateFlatrateTerm();

		final I_C_Invoice_Candidate existingIC = getInvoiceCandidate();

		final I_C_Invoice_Candidate withholdingIC = createWithholdingIC(existingIC);
		final InvoiceCandidateId withholdingICId = InvoiceCandidateId.ofRepoId(withholdingIC.getC_Invoice_Candidate_ID());
		final I_C_Invoice_Candidate interimIC = createInterimIC(existingIC);
		final InvoiceCandidateId interimICId = InvoiceCandidateId.ofRepoId(interimIC.getC_Invoice_Candidate_ID());

		final InterimInvoiceOverview interimInvoiceOverview = createInterimInvoiceOverview(flatrateTerm, withholdingICId, interimICId);

		final Collection<InOutLine> matchingInOuts = getMatchingInOuts();

		matchingInOuts.forEach(inOutLine -> createInterimInvoiceLines(interimInvoiceOverview, inOutLine));
		if (matchingInOuts.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Created InterimInvoiceOverview={} but no matching InOuts found", interimInvoiceOverview.getId());
		}
	}

	@NonNull
	private I_C_Invoice_Candidate getInvoiceCandidate()
	{
		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveInvoiceCandidatesForOrderLineId(orderLineId)
				.stream()
				.filter(ic -> !ic.isPartialPayment()) //exclude the interim IC
				.filter(ic -> ic.getM_Product_ID() != conditions.getM_Product_Correction_ID()) // exclude the withholding IC
				.collect(Collectors.toList());

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
		return getExistingFlatrateTerm().orElse(createFlatrateTerm(bpartnerDAO.getById(bpartnerId)));
	}

	private Optional<I_C_Flatrate_Term> getExistingFlatrateTerm()
	{
		return flatrateDAO.retrieveTerms(ctx,
						Env.getOrgId(ctx),
						bpartnerId.getRepoId(),
						null,
						product.getM_Product_Category_ID(),
						product.getM_Product_ID(),
						-1,
						ITrx.TRXNAME_None)
				.stream()
				.filter(term -> term.getC_Flatrate_Conditions_ID() == conditions.getC_Flatrate_Conditions_ID())
				.filter(term -> dateFrom.equals(term.getStartDate()))
				.filter(term -> dateTo.equals(term.getEndDate()))
				.findFirst();
	}

	@NonNull
	private I_C_Flatrate_Term createFlatrateTerm(final I_C_BPartner bpartner)
	{
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
				.qtyDelivered(BigDecimal.ZERO)
				.qtyInvoiced(BigDecimal.ZERO)
				.productId(productId)
				.uomId(productUomId)
				.priceActual(BigDecimal.ZERO)
				.currencyId(CurrencyId.ofRepoId(order.getC_Currency_ID()))

				.withholdingInvoiceCandidateId(witholdingICId)
				.partialPaymentInvoiceCandidateId(interimICId)
				.build();

		return interimInvoiceOverview.toBuilder()
				.id(interimInvoiceOverviewDAO.save(interimInvoiceOverview))
				.build();
	}

	private I_C_Invoice_Candidate createWithholdingIC(final I_C_Invoice_Candidate existingIC)
	{
		final I_C_Invoice_Candidate withholdingIC = InterfaceWrapperHelper.copy()
				.setFrom(existingIC)
				.copyToNew(I_C_Invoice_Candidate.class);

		withholdingIC.setQtyDelivered(BigDecimal.ZERO);
		withholdingIC.setQtyToInvoice(BigDecimal.ZERO);
		withholdingIC.setM_Product_ID(interimInvoiceSettings.getWithholdingProductId().getRepoId());

		invoiceCandDAO.save(withholdingIC);
		return withholdingIC;
	}

	private I_C_Invoice_Candidate createInterimIC(final I_C_Invoice_Candidate existingIC)
	{
		final I_C_Invoice_Candidate interimIC = InterfaceWrapperHelper.copy()
				.setFrom(existingIC)
				.copyToNew(I_C_Invoice_Candidate.class);

		interimIC.setQtyDelivered(BigDecimal.ZERO);
		interimIC.setQtyDeliveredInUOM(BigDecimal.ZERO);
		interimIC.setQtyToInvoice(BigDecimal.ZERO);
		interimIC.setQtyInvoiced(BigDecimal.ZERO);
		interimIC.setQtyToInvoice_Override(BigDecimal.ZERO);
		interimIC.setQtyInvoicedInUOM(BigDecimal.ZERO);
		interimIC.setIsPartialPayment(true);

		invoiceCandDAO.save(interimIC);

		return interimIC;
	}

	private Collection<InOutLine> getMatchingInOuts()
	{
		return inOutDAO.retrieveInOutLinesBy(InOutLineQuery.builder()
				.bPartnerId(bpartnerId)
				.productId(productId)
				.dateFrom(dateFrom)
				.dateTo(dateTo)
				.clientandOrgId(clientAndOrgId)
				.docStatuses(ImmutableList.of(InOutDocStatus.Closed, InOutDocStatus.Completed))
				.build());
	}

	private void createInterimInvoiceLines(@NonNull final InterimInvoiceOverview interimInvoiceOverview, @NonNull final InOutLine inOutLine)
	{
		interimInvoiceOverviewLineDAO.save(InterimInvoiceOverviewLine.builder()
				.interimInvoiceOverviewId(interimInvoiceOverview.getId())
				.inOutAndLineId(InOutAndLineId.of(inOutLine.getInOutId(), inOutLine.getId()))
				.build());
	}
}
