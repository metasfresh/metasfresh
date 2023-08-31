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

package de.metas.contracts.modular.interim.invoice.service.impl;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.interim.invoice.InterimInvoiceFlatrateTerm;
import de.metas.contracts.modular.interim.invoice.command.InterimInvoiceFlatrateTermCreateCommand;
import de.metas.contracts.modular.interim.invoice.service.IInterimInvoiceFlatrateTermBL;
import de.metas.contracts.modular.interim.invoice.service.IInterimInvoiceFlatrateTermDAO;
import de.metas.contracts.modular.interim.invoice.service.IInterimInvoiceFlatrateTermLineDAO;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsBL;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutLineId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.InvoiceCandidateIds;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.function.Consumer;

public class InterimInvoiceFlatrateTermBL implements IInterimInvoiceFlatrateTermBL
{
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IInterimInvoiceFlatrateTermDAO interimInvoiceFlatrateTermDAO = Services.get(IInterimInvoiceFlatrateTermDAO.class);
	private final IInterimInvoiceFlatrateTermLineDAO interimInvoiceFlatrateTermLineDAO = Services.get(IInterimInvoiceFlatrateTermLineDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final ModularContractSettingsDAO modularContractSettingsDAO = SpringContextHolder.instance.getBean(ModularContractSettingsDAO.class);
	private final ModularContractSettingsBL modularContractSettingsBL = SpringContextHolder.instance.getBean(ModularContractSettingsBL.class);

	private static final Logger logger = LogManager.getLogger(InterimInvoiceFlatrateTermBL.class);

	@Override
	public void create(
			@NonNull final I_C_Flatrate_Term modularFlatrateTermRecord,
			@NonNull final Timestamp startDate,
			@NonNull final Timestamp endDate)
	{
		createInterimContract(modularFlatrateTermRecord, startDate, endDate, null);
	}

	@Override
	public void create(
			@NonNull final I_C_Flatrate_Term modularFlatrateTermRecord,
			@NonNull final Timestamp startDate,
			@NonNull final Timestamp endDate,
			@NonNull final Consumer<I_C_Flatrate_Term> beforeCompleteInterceptor)
	{
		createInterimContract(modularFlatrateTermRecord, startDate, endDate, beforeCompleteInterceptor);
	}

	@Override
	public void updateInterimInvoiceFlatrateTermForInOut(@NonNull final I_M_InOut inOut)
	{
		inOutDAO.retrieveLines(inOut)
				.forEach(this::updateInterimInvoiceFlatrateTermForInOutLine);
	}

	private void updateInterimInvoiceFlatrateTermForInOutLine(@NonNull final I_M_InOutLine inOutLine)
	{
		InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm = interimInvoiceFlatrateTermDAO.getInterimInvoiceOverviewForInOutLine(inOutLine);
		// note that if we have an InterimInvoiceFlatrateTerm, it means that there is also a completed C_Flatrate_Term in the back
		if (interimInvoiceFlatrateTerm == null)
		{
			//inOutLine is not part of a contract
			return;
		}
		if (!interimInvoiceFlatrateTermDAO.isInterimInvoiceStillUsable(interimInvoiceFlatrateTerm))
		{
			interimInvoiceFlatrateTerm = createInterimInvoiceFlatrateTerm(interimInvoiceFlatrateTerm, inOutLine);

		}

		updateInterimInvoiceOverview(interimInvoiceFlatrateTerm, inOutLine);
	}

	@Override
	public void updateQuantities(@NonNull final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm)
	{
		final Quantity qtyDelivered = getQtyDelivered(interimInvoiceFlatrateTerm);
		interimInvoiceFlatrateTermDAO.save(interimInvoiceFlatrateTerm.toBuilder()
				.qtyDelivered(qtyDelivered)
				.build());

		final InvoiceCandidateId interimInvoiceCandidateId = interimInvoiceFlatrateTerm.getInterimInvoiceCandidateId();
		if (interimInvoiceCandidateId != null)
		{
			final I_C_Invoice_Candidate interimInvoiceCandidate = invoiceCandDAO.getById(interimInvoiceCandidateId);
			setICQtyies(interimInvoiceCandidate, qtyDelivered);
		}

		final InvoiceCandidateId withholdingInvoiceCandidateId = interimInvoiceFlatrateTerm.getWithholdingInvoiceCandidateId();
		if (withholdingInvoiceCandidateId != null)
		{
			final I_C_Invoice_Candidate withholdingInvoiceCandidate = invoiceCandDAO.getById(withholdingInvoiceCandidateId);
			setICQtyies(withholdingInvoiceCandidate, qtyDelivered.negate());
		}
	}

	@Override
	@NonNull
	public Quantity getQtyDelivered(final @NonNull InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm)
	{
		if (interimInvoiceFlatrateTerm.getId() == null)
		{
			return Quantitys.create(BigDecimal.ZERO, interimInvoiceFlatrateTerm.getUomId());
		}
		final ImmutableSet<InOutLineId> inOutLineIds = interimInvoiceFlatrateTermLineDAO.getByInterimInvoiceFlatrateTermId(interimInvoiceFlatrateTerm.getId())
				.stream()
				.map(interimInvoiceFlatrateTermLine -> interimInvoiceFlatrateTermLine.getInOutAndLineId().getInOutLineId())
				.collect(ImmutableSet.toImmutableSet());
		return inOutDAO.getLinesByIds(inOutLineIds, I_M_InOutLine.class)
				.stream()
				.map(inOutLine -> Quantitys.create(inOutLine.getMovementQty(), UomId.ofRepoId(inOutLine.getC_UOM_ID())))
				.reduce(Quantitys.createZero(interimInvoiceFlatrateTerm.getUomId()), Quantity::add);
	}

	@Override
	public void updateInvoicedQtyForPartialPayment(@NonNull final I_C_Invoice_Candidate invoiceCand)
	{
		if (!invoiceCand.isInterimInvoice())
		{
			return;
		}
		final InvoiceCandidateId icId = InvoiceCandidateId.ofRepoId(invoiceCand.getC_Invoice_Candidate_ID());
		final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm = interimInvoiceFlatrateTermDAO.getInterimInvoiceFlatrateTermForWithwoldingOrInterimICId(icId);
		if (interimInvoiceFlatrateTerm == null)
		{
			Loggables.withLogger(logger, Level.WARN).addLog("Invoice candidate with ID={} marked as partial payment, but no InterimInvoiceFlatrateTerm record found for it.", icId);
			return;
		}
		interimInvoiceFlatrateTermDAO.save(interimInvoiceFlatrateTerm.toBuilder()
				.qtyInvoiced(Quantitys.create(invoiceCand.getQtyInvoiced(), interimInvoiceFlatrateTerm.getUomId()))
				.build());
		interimInvoiceFlatrateTermLineDAO.setInvoiceLineToLines(InvoiceCandidateIds.ofRecord(invoiceCand), interimInvoiceFlatrateTerm.getId());
	}

	/**
	 * Update the interim and withholding ICs of an {@link InterimInvoiceFlatrateTerm} for the given {@link I_M_InOutLine}.
	 */
	private void updateInterimInvoiceOverview(@NonNull final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm, @NonNull final I_M_InOutLine inOutLine)
	{
		final InOutAndLineId inOutAndLineId = InOutAndLineId.ofRepoId(inOutLine.getM_InOut_ID(), inOutLine.getM_InOutLine_ID());
		if (interimInvoiceFlatrateTermLineDAO.getByInOutLineId(inOutAndLineId.getInOutLineId()) != null)
		{ //maybe it's already associated
			return;
		}

		interimInvoiceFlatrateTermLineDAO.createInterimInvoiceLine(interimInvoiceFlatrateTerm, inOutAndLineId);

		updateQuantities(interimInvoiceFlatrateTerm);
	}

	private void setICQtyies(final I_C_Invoice_Candidate invoiceCandidate, final Quantity quantity)
	{
		final UomId uomToId = UomId.ofRepoId(invoiceCandidate.getC_UOM_ID());
		final ProductId productId = ProductId.ofRepoId(invoiceCandidate.getM_Product_ID());
		final BigDecimal qtyDeliveredInICUOM = uomConversionBL.convertQuantityTo(quantity, productId, uomToId).toBigDecimal();
		invoiceCandidate.setQtyOrdered(qtyDeliveredInICUOM);
		invoiceCandidate.setQtyEntered(qtyDeliveredInICUOM);
		invoiceCandidate.setQtyDelivered(qtyDeliveredInICUOM);
		invoiceCandidate.setQtyDeliveredInUOM(qtyDeliveredInICUOM);
		invoiceCandidate.setQtyToInvoice(qtyDeliveredInICUOM);
		invoiceCandidate.setQtyToInvoiceInUOM(qtyDeliveredInICUOM);
		invoiceCandidate.setQtyToInvoiceBeforeDiscount(qtyDeliveredInICUOM);
		invoiceCandDAO.save(invoiceCandidate);
	}

	private InterimInvoiceFlatrateTerm createInterimInvoiceFlatrateTerm(@NonNull final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm, final @NonNull I_M_InOutLine inOutLine)
	{
		final I_C_Flatrate_Term flatrateTerm = flatrateDAO.retrieveTerm(interimInvoiceFlatrateTerm.getFlatrateTermId());

		return InterimInvoiceFlatrateTermCreateCommand.builder()
				.productId(ProductId.ofRepoId(inOutLine.getM_Product_ID()))
				.orderLineId(OrderLineId.ofRepoId(inOutLine.getC_OrderLine_ID()))
				.modulareFlatrateTermId(FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()))
				.conditionsId(ConditionsId.ofRepoId(flatrateTerm.getC_Flatrate_Conditions_ID()))
				.bpartnerId(BPartnerId.ofRepoId(flatrateTerm.getBill_BPartner_ID()))
				.dateFrom(TimeUtil.asInstantNonNull(flatrateTerm.getStartDate()))
				.dateTo(TimeUtil.asInstant(flatrateTerm.getEndDate()))
				.build()
				.execute();
	}

	private void createInterimContract(
			@NonNull final I_C_Flatrate_Term modularFlatrateTermRecord,
			@NonNull final Timestamp startDate,
			@NonNull final Timestamp endDate,
			@Nullable final Consumer<I_C_Flatrate_Term> beforeCompleteInterceptor)
	{
		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(modularFlatrateTermRecord.getC_Flatrate_Term_ID());

		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(modularFlatrateTermRecord.getC_OrderLine_Term_ID());
		if (orderLineId == null)
		{
			logger.debug("On create skipped C_Flatrate_Term_ID={}, because of missing C_OrderLine_Term_ID", flatrateTermId);
			return;
		}

		final ModularContractSettings modularContractSettings = modularContractSettingsDAO.getByFlatrateTermId(flatrateTermId);
		final ConditionsId interimConditionsId = modularContractSettingsBL.retrieveFlatrateConditionId(modularContractSettings, TypeConditions.INTERIM_INVOICE);

		InterimInvoiceFlatrateTermCreateCommand.builder()
				.modulareFlatrateTermId(flatrateTermId)
				.conditionsId(interimConditionsId)
				.orderLineId(orderLineId)
				.dateFrom(TimeUtil.asInstantNonNull(startDate))
				.dateTo(TimeUtil.asInstantNonNull(endDate))
				.beforeCompleteInterceptor(beforeCompleteInterceptor)
				.build()
				.execute();
	}
}
