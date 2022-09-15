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
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.logging.LogManager;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLine;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLineRepository;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

public class InterimInvoiceFlatrateTermCreateCommand
{
	private static final Logger logger = LogManager.getLogger(InterimInvoiceFlatrateTermCreateCommand.class);

	@NonNull
	private final BPartnerId bpartnerId;

	@NonNull
	private final ProductId productId;
	@NonNull
	private final Instant dateFrom;
	@Nullable
	private final Instant dateTo;
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
	private final IInterimInvoiceFlatrateTermDAO interimInvoiceFlatrateTermDAO = Services.get(IInterimInvoiceFlatrateTermDAO.class);
	private final IInterimInvoiceFlatrateTermLineDAO interimInvoiceFlatrateTermLineDAO = Services.get(IInterimInvoiceFlatrateTermLineDAO.class);
	private final IInterimInvoiceFlatrateTermBL interimInvoiceFlatrateTermBL = Services.get(IInterimInvoiceFlatrateTermBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final OrderLineRepository orderLineRepository = SpringContextHolder.instance.getBean(OrderLineRepository.class);
	private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);

	@Builder
	public InterimInvoiceFlatrateTermCreateCommand(@Nullable final BPartnerId bpartnerId,
			@NonNull final ConditionsId conditionsId,
			@Nullable final ProductId productId,
			@NonNull final Instant dateFrom,
			@Nullable final Instant dateTo,
			@NonNull final OrderLineId orderLineId)
	{

		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.orderLineId = orderLineId;
		this.orderLine = orderLineRepository.getById(orderLineId);
		this.bpartnerId = CoalesceUtil.coalesceSuppliersNotNull(
				() -> bpartnerId,
				() -> BPartnerId.ofRepoId(orderDAO.getById(orderLine.getOrderId()).getBill_BPartner_ID()));
		this.productId = CoalesceUtil.coalesceNotNull(productId, orderLine.getProductId());
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

		final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm = getOrCreateInterimInvoiceOverview(flatrateTerm);

		updateQtyDeliveredSoFar(interimInvoiceFlatrateTerm);

		invoiceCandidateHandlerBL.createMissingCandidatesFor(flatrateTerm);

		return interimInvoiceFlatrateTerm;
	}

	private void updateQtyDeliveredSoFar(@NonNull final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm)
	{
		final Collection<InOutAndLineId> existingInOuts = getUnassignedInOutLinesForOrderLineId();

		if (existingInOuts.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Created InterimInvoiceFlatrateTerm={} but no matching InOuts found.", interimInvoiceFlatrateTerm.getId());
		}
		else
		{
			existingInOuts.forEach(inOutLine -> interimInvoiceFlatrateTermLineDAO.createInterimInvoiceLine(interimInvoiceFlatrateTerm, inOutLine));
			interimInvoiceFlatrateTermBL.updateQuantities(interimInvoiceFlatrateTerm);
		}
	}

	private InterimInvoiceFlatrateTerm getOrCreateInterimInvoiceOverview(final I_C_Flatrate_Term flatrateTerm)
	{
		final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm = interimInvoiceFlatrateTermDAO.getInterimInvoiceForFlatrateTermAndOrderLineId(FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()), orderLineId);
		if (interimInvoiceFlatrateTerm != null && interimInvoiceFlatrateTermDAO.isInterimInvoiceStillUsable(interimInvoiceFlatrateTerm))
		{
			return interimInvoiceFlatrateTerm;
		}
		else
		{
			return createInterimInvoiceOverview(flatrateTerm);
		}
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
				.filter(term -> Objects.equals(dateFrom, TimeUtil.asInstant(term.getStartDate())))
				.filter(term -> Objects.equals(dateTo, TimeUtil.asInstant(term.getEndDate())))
				.findFirst();
	}

	@NonNull
	private I_C_Flatrate_Term createFlatrateTerm(final I_C_BPartner bpartner)
	{
		return FlatrateTermCreator.builder()
				.bPartners(Collections.singleton(bpartner))
				.orgId(orderLine.getOrgId())
				.conditions(conditions)
				.ctx(InterfaceWrapperHelper.getCtx(bpartner))
				.product(product)
				.startDate(TimeUtil.asTimestamp(dateFrom))
				.endDate(TimeUtil.asTimestamp(dateTo))
				.isCompleteDocument(true)
				.build()
				.createTermsForBPartners()
				.stream()
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No flatrate term created"));
	}

	private InterimInvoiceFlatrateTerm createInterimInvoiceOverview(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		final UomId productUomId = UomId.ofRepoId(product.getC_UOM_ID());
		final Quantity zeroQty = Quantitys.createZero(productUomId);
		final ProductPrice orderLinePriceActual = orderLine.getPriceActual();
		final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm = InterimInvoiceFlatrateTerm.builder()
				.flatrateTermId(FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()))
				.calendarId(interimInvoiceSettings.getHarvestingCalendarId())
				.orderLineId(orderLineId)
				.qtyOrdered(uomConversionBL.convertQuantityTo(orderLine.getOrderedQty(), productId, productUomId))
				.qtyDelivered(zeroQty)
				.qtyInvoiced(zeroQty)
				.productId(productId)
				.uomId(productUomId)
				.priceActual(orderLinePriceActual.toMoney())
				.currencyId(orderLinePriceActual.getCurrencyId())
				.build();

		return interimInvoiceFlatrateTermDAO.save(interimInvoiceFlatrateTerm);
	}

	private Collection<InOutAndLineId> getUnassignedInOutLinesForOrderLineId()
	{
		return inOutDAO.retrieveLineIdsForOrderLineIdAvailableForInterimInvoice(orderLineId);
	}

}
