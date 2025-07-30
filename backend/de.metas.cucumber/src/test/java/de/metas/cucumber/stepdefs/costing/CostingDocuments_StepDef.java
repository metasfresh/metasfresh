package de.metas.cucumber.stepdefs.costing;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDef;
import de.metas.cucumber.stepdefs.C_Order_StepDef;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.hu.M_HU_CreateReceipt_StepDef;
import de.metas.cucumber.stepdefs.hu.M_HU_LUTU_Configuration_StepDef;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDef;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.invoicecandidate.C_Invoice_Candidate_StepDef;
import de.metas.cucumber.stepdefs.match_inv.M_MatchInv_StepDefData;
import de.metas.cucumber.stepdefs.match_po.M_MatchPO_StepDef;
import de.metas.cucumber.stepdefs.receiptschedule.M_ReceiptSchedule_StepDef;
import de.metas.cucumber.stepdefs.shipment.M_InOut_Line_StepDef;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDef;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.inoutcandidate.api.impl.ReceiptMovementDateRule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.matchinv.MatchInv;
import de.metas.invoice.matchinv.MatchInvQuery;
import de.metas.invoice.matchinv.MatchInvType;
import de.metas.invoice.matchinv.service.MatchInvoiceRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.I_M_MatchPO;
import org.compiere.util.Env;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.util.Collection;
import java.util.List;

import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_PriceEntered_Override;

@RequiredArgsConstructor
public class CostingDocuments_StepDef
{
	@NonNull private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	@NonNull private final MatchInvoiceRepository matchInvoiceRepository = SpringContextHolder.instance.getBean(MatchInvoiceRepository.class);

	@NonNull private final C_Order_StepDef orderStepDef;
	@NonNull private final C_OrderLine_StepDef orderLineStepDef;
	@NonNull private final M_ReceiptSchedule_StepDef receiptScheduleStepDef;
	@NonNull private final M_HU_LUTU_Configuration_StepDef lutuConfigurationStepDef;
	@NonNull private final M_HU_CreateReceipt_StepDef createReceiptStepDef;
	@NonNull private final M_InOut_StepDefData inoutTable;
	@NonNull private final M_InOut_Line_StepDef inoutLineStepDef;
	@NonNull private final M_MatchPO_StepDef matchPOStepDef;
	@NonNull private final C_Invoice_Candidate_StepDef invoiceCandidateStepDef;
	@NonNull private final C_Invoice_StepDef invoiceStepDef;
	@NonNull private final C_Invoice_StepDefData invoiceTable;
	@NonNull private final M_MatchInv_StepDefData matchInvTable;
	@NonNull private final M_ShipmentSchedule_StepDef shipmentScheduleStepDef;

	@Given("for costing, create completed order with one line")
	public void createOrders(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.forEach(this::createOrder);
	}

	private void createOrder(final DataTableRow row)
	{
		row.setValueIfMissing(I_C_Order.COLUMNNAME_C_Order_ID, () -> StepDefDataIdentifier.nextUnnamed("purchaseOrder").getAsString());
		row.setValueIfMissing(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID, () -> StepDefDataIdentifier.nextUnnamed("purchaseOrderLine").getAsString());

		row.setAdditionalRowIdentifierColumnName(I_C_Order.COLUMNNAME_C_Order_ID);
		final I_C_Order order = orderStepDef.createOrder(row);

		row.setAdditionalRowIdentifierColumnName(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID);
		orderLineStepDef.createOrderLine(row);

		orderStepDef.completeOrder(order);
	}

	@Given("for costing, create completed material receipt with one line")
	public void createMaterialReceipts(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.forEach(this::createMaterialReceipt);
	}

	private void createMaterialReceipt(final DataTableRow row) throws InterruptedException
	{
		row.setValueIfMissing(I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID, () -> StepDefDataIdentifier.nextUnnamed("rs").getAsString());
		row.setValueIfMissing(I_M_InOut.COLUMNNAME_M_InOut_ID, () -> StepDefDataIdentifier.nextUnnamed("receipt").getAsString());
		row.setValueIfMissing(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID, () -> StepDefDataIdentifier.nextUnnamed("receiptLine").getAsString());
		row.setValueIfMissing(I_M_MatchPO.COLUMNNAME_M_MatchPO_ID, () -> StepDefDataIdentifier.nextUnnamed("mpo").getAsString());

		row.setAdditionalRowIdentifierColumnName(I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID);
		final I_M_ReceiptSchedule receiptSchedule = receiptScheduleStepDef.waitAndLoadReceiptSchedule(row, 60);

		final ImmutableSet<HuId> huIds = createPlanningHUs(receiptSchedule);

		final I_M_InOut receipt = createReceiptStepDef.createMaterialReceipt(
				IHUReceiptScheduleBL.CreateReceiptsParameters.builder()
						.movementDateRule(ReceiptMovementDateRule.CURRENT_DATE)
						.ctx(Env.getCtx())
						.receiptSchedules(ImmutableList.of(receiptSchedule))
						.selectedHuIds(huIds)
						.build()
		);
		row.getAsOptionalIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID).ifPresent(inoutIdentifier -> inoutTable.putOrReplace(inoutIdentifier, receipt));

		row.setAdditionalRowIdentifierColumnName(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID);
		inoutLineStepDef.loadInOutLine(row);

		row.setAdditionalRowIdentifierColumnName(I_M_MatchPO.COLUMNNAME_M_MatchPO_ID);
		matchPOStepDef.loadMatchPO(row);
	}

	private ImmutableSet<HuId> createPlanningHUs(final I_M_ReceiptSchedule receiptSchedule)
	{
		final List<I_M_HU> hus = lutuConfigurationStepDef.createLUTUConfigurationForReceiptSchedule(
				DataTableRow.builder()
						.value("M_ReceiptSchedule_ID", receiptSchedule.getM_ReceiptSchedule_ID())
						.value("IsInfiniteQtyLU", false)
						.value("QtyLU", 0)
						.value("IsInfiniteQtyTU", false)
						.value("QtyTU", 1)
						.value("IsInfiniteQtyCU", false)
						.value("QtyCUsPerTU", receiptSchedule.getQtyToMove())
						.value("M_HU_PI_Item_Product_ID", HUPIItemProductId.VIRTUAL_HU)
						.build()
		);
		return extractHuIds(hus);
	}

	private static ImmutableSet<HuId> extractHuIds(final Collection<I_M_HU> hus)
	{
		return hus.stream().map(hu -> HuId.ofRepoId(hu.getM_HU_ID())).collect(ImmutableSet.toImmutableSet());
	}

	@Given("for costing, create completed invoice with one line")
	public void createInvoices(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.forEach(this::createInvoice);
	}

	private void createInvoice(final DataTableRow row) throws Throwable
	{
		row.setValueIfMissing(I_C_Invoice.COLUMNNAME_C_Invoice_ID, () -> StepDefDataIdentifier.nextUnnamed("invoice").getAsString());
		row.setValueIfMissing(I_M_MatchInv.COLUMNNAME_M_MatchInv_ID, () -> StepDefDataIdentifier.nextUnnamed("matchInv").getAsString());

		final I_C_Invoice_Candidate invoiceCandidate = invoiceCandidateStepDef.waitAndLoad_C_Invoice_Candidate(row, 120);
		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID());

		row.getAsOptionalBigDecimal(COLUMNNAME_PriceEntered_Override).ifPresent(invoiceCandidate::setPriceEntered_Override);
		invoiceCandDAO.save(invoiceCandidate);
		invoiceCandidateStepDef.waitUntilValid(invoiceCandidateId, 120);

		invoiceCandidateStepDef.generateInvoices(ImmutableSet.of(invoiceCandidateId));
		final I_C_Invoice invoice = invoiceStepDef.waitAndLoadSingleInvoice(invoiceCandidateId);
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
		row.getAsOptionalIdentifier("C_Invoice_ID").ifPresent(invoiceIdentifier -> invoiceTable.putOrReplace(invoiceIdentifier, invoice));

		final MatchInv matchInv = matchInvoiceRepository.firstOnly(MatchInvQuery.builder()
				.type(MatchInvType.Material)
				.invoiceId(invoiceId)
				.build());
		row.getAsOptionalIdentifier("M_MatchInv_ID").ifPresent(matchInvIdentifier -> matchInvTable.putOrReplace(matchInvIdentifier, matchInv));
	}

	@Given("for costing, create completed shipment with one line")
	public void createShipments(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.forEach(this::createShipment);
	}

	private void createShipment(DataTableRow row) throws InterruptedException
	{
		row.setValueIfMissing(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, () -> StepDefDataIdentifier.nextUnnamed("sched").getAsString());
		row.setValueIfMissing(I_M_InOut.COLUMNNAME_M_InOut_ID, () -> StepDefDataIdentifier.nextUnnamed("shipment").getAsString());
		row.setValueIfMissing(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID, () -> StepDefDataIdentifier.nextUnnamed("shipmentLine").getAsString());

		row.setAdditionalRowIdentifierColumnName(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID);
		shipmentScheduleStepDef.loadShipmentSchedules(60, DataTableRows.of(row));

		row.setAdditionalRowIdentifierColumnName(I_M_InOut.COLUMNNAME_M_InOut_ID);
		shipmentScheduleStepDef.generateShipmentForSchedule(row);

		row.setAdditionalRowIdentifierColumnName(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID);
		inoutLineStepDef.loadInOutLine(row);
	}

}

