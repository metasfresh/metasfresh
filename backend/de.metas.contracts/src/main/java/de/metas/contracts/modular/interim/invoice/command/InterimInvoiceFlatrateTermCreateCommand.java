/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.interim.invoice.command;

import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.modular.interim.invoice.InterimInvoiceFlatrateTerm;
import de.metas.contracts.modular.interim.invoice.service.IInterimInvoiceFlatrateTermBL;
import de.metas.contracts.modular.interim.invoice.service.IInterimInvoiceFlatrateTermDAO;
import de.metas.contracts.modular.interim.invoice.service.IInterimInvoiceFlatrateTermLineDAO;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.process.FlatrateTermCreator;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
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
import java.util.function.Consumer;

public class InterimInvoiceFlatrateTermCreateCommand
{
	private static final Logger logger = LogManager.getLogger(InterimInvoiceFlatrateTermCreateCommand.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IInterimInvoiceFlatrateTermDAO interimInvoiceFlatrateTermDAO = Services.get(IInterimInvoiceFlatrateTermDAO.class);
	private final IInterimInvoiceFlatrateTermLineDAO interimInvoiceFlatrateTermLineDAO = Services.get(IInterimInvoiceFlatrateTermLineDAO.class);
	private final IInterimInvoiceFlatrateTermBL interimInvoiceFlatrateTermBL = Services.get(IInterimInvoiceFlatrateTermBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	@NonNull private final BPartnerId bpartnerId;
	@NonNull private final ProductId productId;
	@NonNull private final Instant dateFrom;
	@NonNull private final Instant dateTo;
	@NonNull private final OrderLineId orderLineId;

	@NonNull private final FlatrateTermId modulareFlatrateTermId;
	@Nullable private final Consumer<I_C_Flatrate_Term> beforeCompleteInterceptor;

	@NonNull private final OrderLine orderLine;
	@NonNull private final I_C_Flatrate_Conditions conditions;
	@NonNull private final ModularContractSettings modularContractSettings;
	@NonNull private final I_M_Product product;
	@NonNull private final I_C_Flatrate_Term modularContract;

	@Builder
	public InterimInvoiceFlatrateTermCreateCommand(
			@Nullable final BPartnerId bpartnerId,
			@NonNull final ConditionsId conditionsId,
			@Nullable final ProductId productId,
			@NonNull final Instant dateFrom,
			@NonNull final Instant dateTo,
			@NonNull final OrderLineId orderLineId,
			@NonNull final FlatrateTermId modulareFlatrateTermId,
			@Nullable final Consumer<I_C_Flatrate_Term> beforeCompleteInterceptor)
	{
		final OrderLineRepository orderLineRepository = SpringContextHolder.instance.getBean(OrderLineRepository.class);
		final ModularContractSettingsDAO modularContractSettingsDAO = SpringContextHolder.instance.getBean(ModularContractSettingsDAO.class);
		final IProductDAO productDAO = Services.get(IProductDAO.class);

		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.orderLineId = orderLineId;
		this.orderLine = orderLineRepository.getById(orderLineId);
		this.bpartnerId = CoalesceUtil.coalesceSuppliersNotNull(
				() -> bpartnerId,
				() -> BPartnerId.ofRepoId(orderDAO.getById(orderLine.getOrderId()).getBill_BPartner_ID()));
		this.productId = CoalesceUtil.coalesceNotNull(productId, orderLine.getProductId());
		this.conditions = flatrateBL.getConditionsById(conditionsId);
		this.modularContractSettings = modularContractSettingsDAO.getByFlatrateConditionsId(conditionsId);
		this.modulareFlatrateTermId = modulareFlatrateTermId;
		this.product = productDAO.getById(this.productId);
		this.modularContract = flatrateBL.getById(modulareFlatrateTermId);
		this.beforeCompleteInterceptor = beforeCompleteInterceptor;
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
		return flatrateBL.retrieveTerms(bpartner, conditions)
				.stream()
				.filter(term -> Objects.equals(dateFrom, TimeUtil.asInstant(term.getStartDate())))
				.filter(term -> Objects.equals(dateTo, TimeUtil.asInstant(term.getEndDate())))
				.filter(term -> Objects.equals(orderLineId.getRepoId(), term.getC_OrderLine_Term_ID()))
				.findFirst();
	}

	@NonNull
	private I_C_Flatrate_Term createFlatrateTerm(final I_C_BPartner bpartner)
	{
		final I_C_Flatrate_Term flatrateTermRecord = FlatrateTermCreator.builder()
				.bPartners(Collections.singleton(bpartner))
				.orgId(orderLine.getOrgId())
				.conditions(conditions)
				.ctx(InterfaceWrapperHelper.getCtx(bpartner))
				.product(product)
				.startDate(TimeUtil.asTimestamp(dateFrom))
				.endDate(TimeUtil.asTimestamp(dateTo))
				.isCompleteDocument(false)
				.build()
				.createTermsForBPartners()
				.stream()
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No flatrate term created"));

		flatrateTermRecord.setM_PricingSystem_ID(modularContract.getM_PricingSystem_ID());
		flatrateTermRecord.setPlannedQtyPerUnit(modularContract.getPlannedQtyPerUnit());
		flatrateTermRecord.setContractStatus(modularContract.getContractStatus());
		flatrateTermRecord.setMasterStartDate(TimeUtil.asTimestamp(dateFrom));
		flatrateTermRecord.setMasterEndDate(TimeUtil.asTimestamp(dateTo));
		flatrateTermRecord.setPriceActual(modularContract.getPriceActual());
		flatrateTermRecord.setC_TaxCategory_ID(modularContract.getC_TaxCategory_ID());
		flatrateTermRecord.setC_Order_Term(modularContract.getC_Order_Term());
		flatrateTermRecord.setC_OrderLine_Term(modularContract.getC_OrderLine_Term());
		flatrateTermRecord.setModular_Flatrate_Term_ID(modulareFlatrateTermId.getRepoId());
		flatrateTermRecord.setC_UOM_ID(modularContract.getC_UOM_ID());
		flatrateTermRecord.setDeliveryRule(modularContract.getDeliveryRule());
		flatrateTermRecord.setDeliveryViaRule(modularContract.getDeliveryViaRule());

		flatrateBL.save(flatrateTermRecord);

		Optional.ofNullable(beforeCompleteInterceptor).ifPresent(interceptor -> interceptor.accept(flatrateTermRecord));

		flatrateBL.complete(flatrateTermRecord);

		return flatrateTermRecord;
	}

	private InterimInvoiceFlatrateTerm createInterimInvoiceOverview(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		final UomId productUomId = UomId.ofRepoId(product.getC_UOM_ID());
		final Quantity zeroQty = Quantitys.createZero(productUomId);
		final ProductPrice orderLinePriceActual = orderLine.getPriceActual();
		final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm = InterimInvoiceFlatrateTerm.builder()
				.flatrateTermId(FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()))
				.calendarId(modularContractSettings.getYearAndCalendarId().calendarId())
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
