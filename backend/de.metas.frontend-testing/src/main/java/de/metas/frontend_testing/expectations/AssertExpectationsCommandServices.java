package de.metas.frontend_testing.expectations;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.generichumodel.HUType;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.slot.PickingSlotQueue;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.picking.api.PickingSlotId;
import de.metas.pos.POSCashJournal;
import de.metas.pos.POSOrder;
import de.metas.pos.POSOrderQuery;
import de.metas.pos.POSOrdersRepository;
import de.metas.pos.POSService;
import de.metas.pos.POSTerminalId;
import de.metas.pos.returns.POSReturnRepository;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.TaxId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import de.metas.adempiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AssertExpectationsCommandServices
{
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	@NonNull private final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);
	@NonNull private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	@NonNull private final IShipmentScheduleInvalidateRepository invalidationRepository = Services.get(IShipmentScheduleInvalidateRepository.class);
	@NonNull public final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@NonNull private final IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	@NonNull private final IHUInOutDAO huInOutDAO = Services.get(IHUInOutDAO.class);
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	@NonNull private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	@NonNull private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	@NonNull private final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final PickingJobService pickingJobService;
	@NonNull private final HUQRCodesService huQRCodeService;
	@NonNull private final PickingSlotService pickingSlotService;
	@NonNull private final POSOrdersRepository posOrdersRepository;
	@NonNull private final POSReturnRepository posReturnRepository;
	@NonNull private final POSService posService;

	public PickingJob getPickingJobById(final PickingJobId pickingJobId)
	{
		return pickingJobService.getById(pickingJobId);
	}

	public Collection<I_M_ShipmentSchedule> getShipmentSchedulesByIds(final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		return shipmentScheduleBL.getByIds(shipmentScheduleIds).values();
	}

	public List<I_M_ShipmentSchedule_QtyPicked> getShipmentScheduleQtyPickedRecords(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		return shipmentScheduleAllocDAO.retrieveAllQtyPickedRecords(shipmentScheduleIds, I_M_ShipmentSchedule_QtyPicked.class);
	}

	public StockQtyAndUOMQty extractQtyPicked(@NonNull final I_M_ShipmentSchedule_QtyPicked alloc, @NonNull final ProductId productId)
	{
		return shipmentScheduleAllocBL.extractQtyPicked(alloc, productId);
	}

	public I_M_HU getHUById(@NonNull final HuId huId)
	{
		return handlingUnitsBL.getById(huId);
	}

	public HuId getHuIdByQRCode(@NonNull final HUQRCode qrCode)
	{
		return huQRCodeService.getHuIdByQRCode(qrCode);
	}

	public HUType getHUUnitType(@NonNull final I_M_HU hu)
	{
		return handlingUnitsBL.getHUUnitType(hu);
	}

	public IHUStorage getHUStorage(@NonNull final HuId huId)
	{
		return handlingUnitsBL.getStorageFactory().getStorage(handlingUnitsBL.getById(huId));
	}

	public IHUProductStorage getSingleProductStorage(@NonNull final I_M_HU hu)
	{
		return handlingUnitsBL.getSingleHUProductStorage(hu);
	}

	public ImmutableAttributeSet getAttributes(@NonNull final I_M_HU hu)
	{
		return handlingUnitsBL.getImmutableAttributeSet(hu);
	}

	public PickingSlotQueue getPickingSlotQueue(@NonNull final PickingSlotId pickingSlotId)
	{
		return pickingSlotService.getPickingSlotQueue(pickingSlotId);
	}

	public List<I_PP_Order_Qty> getPPOrderQtyForFinishedGoodsReceive(@NonNull final PPOrderId ppOrderId)
	{
		return huPPOrderQtyDAO.retrieveOrderQtyForFinishedGoodsReceive(ppOrderId);
	}

	public List<I_PP_Order_Qty> getPPOrderQtyForComponentIssue(@NonNull final PPOrderId ppOrderId)
	{
		return huPPOrderQtyDAO.retrieveOrderQtys(ppOrderId)
				.stream()
				.filter(candidate -> candidate.getPP_Order_BOMLine_ID() > 0)
				.collect(ImmutableList.toImmutableList());
	}

	public List<I_M_HU> getIncludedHUs(@NonNull final HuId huId)
	{
		return handlingUnitsDAO.retrieveIncludedHUs(huId);
	}

	public List<I_M_HU> getCUs(final HuId huId) {return handlingUnitsBL.getVHUs(huId);}

	public List<de.metas.handlingunits.model.I_M_InOutLine> getInOutLinesForHU(@NonNull final I_M_HU hu)
	{
		return huInOutDAO.retrieveInOutLinesForHU(hu);
	}

	public boolean isAllValid(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		return invalidationRepository.isAllValid(shipmentScheduleIds);
	}

	public List<I_M_InOut> getInOutsByOrderId(@NonNull final OrderId orderId)
	{
		return inOutDAO.retrieveInOutsByOrderId(orderId);
	}

	public List<I_M_InOutLine> getInOutLines(@NonNull final I_M_InOut inOut)
	{
		return inOutDAO.retrieveLines(inOut);
	}

	public Set<OrderLineId> getOrderLineIdsByOrderId(@NonNull final OrderId orderId)
	{
		return orderDAO.retrieveOrderLines(orderId)
				.stream()
				.map(line -> OrderLineId.ofRepoId(line.getC_OrderLine_ID()))
				.collect(Collectors.toSet());
	}

	public List<I_M_InOutLine> getProcessedShipmentLinesByOrderLineIds(@NonNull final Set<OrderLineId> orderLineIds)
	{
		return inOutDAO.retrieveProcessedLinesForOrderLineIds(orderLineIds);
	}

	public POSOrder getSinglePOSOrder(@NonNull final POSTerminalId posTerminalId, @NonNull final UserId cashierId)
	{
		final List<POSOrder> posOrders = posOrdersRepository.list(POSOrderQuery.builder()
				.posTerminalId(posTerminalId)
				.cashierId(cashierId)
				.build());
		return CollectionUtils.singleElement(posOrders);
	}

	public I_C_Order getOrderById(@NonNull final OrderId orderId)
	{
		return orderDAO.getById(orderId);
	}

	public List<I_C_Invoice> getInvoicesByOrderId(@NonNull final OrderId orderId)
	{
		return invoiceDAO.getInvoicesForOrderIds(ImmutableList.of(orderId));
	}

	/**
	 * Finds the {@code M_InOut} a POS return produced, by the SAME {@code ExternalId} convention
	 * {@code POSReturnService#createReturn} uses ({@code "POSReturn-" + <the client's externalId>}) — the return
	 * document carries no direct FK to its POS terminal, so the externalId the client generated for the return
	 * (and passed to {@code POST /api/v2/pos/returns}) is the only handle back to it.
	 */
	@NonNull
	public InOutId getPOSReturnInOutIdByExternalId(@NonNull final String externalId)
	{
		return posReturnRepository.findReturnIdByExternalId("POSReturn-" + externalId)
				.orElseThrow(() -> new AdempiereException("No POS return found for externalId " + externalId));
	}

	public I_M_InOut getInOutById(@NonNull final InOutId inOutId)
	{
		return inOutDAO.getById(inOutId);
	}

	/**
	 * Finds the credit memo a POS return's line was invoiced into, via its invoice candidate's own
	 * {@code C_InvoiceLine} back-reference — the SAME chain {@code POSReturnService#ensureCreditMemo} itself
	 * uses (a return line's {@code C_Invoice_Candidate} → the invoice line {@code InvoiceCandBLCreateInvoices}
	 * created from it). Any one line of the return resolves to the SAME credit memo (a POS return is always its
	 * own standalone, single-invoice document), so the first line with a resolvable invoice line wins.
	 */
	@NonNull
	public InvoiceId getCreditMemoIdForPOSReturn(@NonNull final InOutId returnInOutId)
	{
		final I_M_InOut returnRecord = getInOutById(returnInOutId);
		for (final I_M_InOutLine returnLine : inOutDAO.retrieveLines(returnRecord))
		{
			for (final I_C_Invoice_Candidate ic : invoiceCandDAO.retrieveInvoiceCandidatesForInOutLine(returnLine))
			{
				final InvoiceCandidateId icId = InvoiceCandidateId.ofRepoId(ic.getC_Invoice_Candidate_ID());
				for (final I_C_InvoiceLine il : invoiceCandDAO.retrieveIlForIc(icId))
				{
					return InvoiceId.ofRepoId(il.getC_Invoice_ID());
				}
			}
		}
		throw new AdempiereException("No credit memo found for POS return " + returnInOutId);
	}

	public org.compiere.model.I_C_Invoice getInvoiceById(@NonNull final InvoiceId invoiceId)
	{
		return invoiceDAO.getByIdInTrx(invoiceId);
	}

	public List<de.metas.adempiere.model.I_C_InvoiceLine> getInvoiceLines(@NonNull final InvoiceId invoiceId)
	{
		return invoiceDAO.retrieveLines(invoiceId);
	}

	@NonNull
	public UomId getUomIdByX12DE355(@NonNull final X12DE355 x12de355)
	{
		return uomDAO.getUomIdByX12DE355(x12de355);
	}

	/**
	 * The credit memo's already-completed outbound settlement payment(s), via {@code C_AllocationLine} — see
	 * {@code POSReturnRepository#findSettlementPaymentIds}'s own Javadoc for why (never
	 * {@code C_Payment.C_Invoice_ID} directly, per de.metas.business's payment-linking Golden Rule). Reused here
	 * (not re-implemented) because this credit memo IS the POS-return flow's own artifact — the SAME repository
	 * cluster this query already belongs to.
	 */
	public boolean hasAllocatedPayment(@NonNull final InvoiceId invoiceId)
	{
		return !posReturnRepository.findSettlementPaymentIds(invoiceId).isEmpty();
	}

	/**
	 * Any payment (inbound or outbound) allocated to an ARBITRARY invoice — via the generic, already-existing
	 * {@link IAllocationDAO#retrieveInvoicePayments}, NOT {@code POSReturnRepository}'s own finder (that one is
	 * scoped to the POS-return flow's own credit memos; an invoice-settlement expectation, e.g. a plain sales
	 * invoice from T4, is a different flow with no POS-return artifact behind it).
	 */
	public List<I_C_Payment> getAllocatedPayments(@NonNull final org.compiere.model.I_C_Invoice invoice)
	{
		return allocationDAO.retrieveInvoicePayments(invoice);
	}

	@NonNull
	public BigDecimal getTaxRate(@NonNull final TaxId taxId)
	{
		final Percent rate = taxDAO.getRateById(taxId);
		return rate.toBigDecimal();
	}

	/**
	 * The terminal's current cash journal — resolved fresh (not cached) so a just-added refund/withdrawal line is
	 * visible to the assertion that follows it in the same request.
	 */
	@NonNull
	public POSCashJournal getCurrentCashJournal(@NonNull final POSTerminalId posTerminalId)
	{
		return posService.getCurrentCashJournal(posTerminalId)
				.orElseThrow(() -> new AdempiereException("No open cash journal for POS terminal " + posTerminalId));
	}
}
