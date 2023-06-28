package de.metas.gplr.report;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.department.Department;
import de.metas.department.DepartmentService;
import de.metas.gplr.report.model.GPLRBPartnerName;
import de.metas.gplr.report.model.GPLRCurrencyInfo;
import de.metas.gplr.report.model.GPLRIncotermsInfo;
import de.metas.gplr.report.model.GPLRPaymentTermRenderedString;
import de.metas.gplr.report.model.GPLRReport;
import de.metas.gplr.report.model.GPLRReportCharge;
import de.metas.gplr.report.model.GPLRReportLineItem;
import de.metas.gplr.report.model.GPLRReportNote;
import de.metas.gplr.report.model.GPLRReportPurchaseOrder;
import de.metas.gplr.report.model.GPLRReportSalesOrder;
import de.metas.gplr.report.model.GPLRReportShipment;
import de.metas.gplr.report.model.GPLRReportSourceDocument;
import de.metas.gplr.report.model.GPLRReportSummary;
import de.metas.gplr.report.model.GPLRSectionCodeRenderedString;
import de.metas.gplr.report.model.GPLRShipperRenderedString;
import de.metas.gplr.report.model.GPLRWarehouseName;
import de.metas.gplr.report.repository.GPLRReportRepository;
import de.metas.gplr.source.model.SourceBPartnerInfo;
import de.metas.gplr.source.model.SourceCurrencyInfo;
import de.metas.gplr.source.model.SourceDocuments;
import de.metas.gplr.source.model.SourceIncotermsAndLocation;
import de.metas.gplr.source.model.SourceInvoice;
import de.metas.gplr.source.model.SourceOrder;
import de.metas.gplr.source.model.SourceOrderCost;
import de.metas.gplr.source.model.SourceOrderLine;
import de.metas.gplr.source.model.SourceShipment;
import de.metas.gplr.source.model.SourceShipperInfo;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.money.CurrencyCodeToCurrencyIdBiConverter;
import de.metas.order.OrderLineId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;
import java.util.function.UnaryOperator;

final class GPLRReportCreateCommand
{
	//
	// Services
	@NonNull private final GPLRReportRepository gplrReportRepository;
	@NonNull private final DepartmentService departmentService;
	@NonNull private final CurrencyCodeToCurrencyIdBiConverter currencyCodeConverter;

	// Params
	@NonNull private final SourceDocuments source;

	//
	// State
	@NonNull private final String adLanguage = Language.getBaseAD_Language();
	@NonNull private final Instant reportDate = SystemTime.asInstant();

	@Builder
	private GPLRReportCreateCommand(
			final @NonNull GPLRReportRepository gplrReportRepository,
			final @NonNull DepartmentService departmentService,
			final @NonNull CurrencyCodeToCurrencyIdBiConverter currencyCodeConverter,
			final @NonNull SourceDocuments source)
	{
		this.gplrReportRepository = gplrReportRepository;
		this.departmentService = departmentService;
		this.currencyCodeConverter = currencyCodeConverter;
		//
		this.source = source;
	}

	public GPLRReport execute()
	{
		final GPLRReport report = GPLRReport.builder()
				.created(reportDate)
				.sourceDocument(createGPLRReportSourceDocument())
				.salesOrder(createGPLRReportSalesOrder())
				.shipments(source.getShipments()
						.stream()
						.map(this::createGPLRReportShipment)
						.collect(ImmutableList.toImmutableList()))
				.purchaseOrders(source.getPurchaseOrders()
						.stream()
						.map(this::createGPLRReportPurchaseOrder)
						.collect(ImmutableList.toImmutableList()))
				.summary(createGPLRReportSummary())
				.lineItems(createGPLRReportLineItems())
				.charges(createGPLRReportCharges())
				.otherNotes(createGPLRReportNotes())
				.build();

		gplrReportRepository.createNew(report);

		return report;
	}

	private GPLRReportSummary createGPLRReportSummary()
	{
		final SourceOrder salesOrder = source.getSalesOrder();
		final SourceInvoice salesInvoice = source.getSalesInvoice();

		final SourceCurrencyInfo currencyInfo = salesInvoice.getCurrencyInfo();
		final CurrencyCode localCurrency = currencyInfo.getLocalCurrencyCode();
		final CurrencyCode foreignCurrency = currencyInfo.getForeignCurrencyCode();
		final UnaryOperator<Amount> toLocal = amountFC -> currencyInfo.convertToLocal(amountFC, currencyCodeConverter);

		final Amount salesFC = salesInvoice.getLinesNetAmtFC();
		final Amount salesLC = toLocal.apply(salesFC);

		final Amount taxesFC = salesInvoice.getTaxAmtFC();
		final Amount taxesLC = toLocal.apply(taxesFC);

		final Amount estimatedFC = salesOrder.getEstimatedOrderCostAmountFC();
		final Amount estimatedLC = toLocal.apply(estimatedFC);

		final Amount cogsLC = salesOrder.getCOGS_LC();

		final Amount chargesFC = Amount.zero(foreignCurrency); // TODO: understand/compute charges
		final Amount chargesLC = toLocal.apply(chargesFC);

		final Amount profitOrLossLC = salesLC.subtract(estimatedLC).subtract(cogsLC);
		final Percent profitRate = Percent.of(profitOrLossLC.toBigDecimal(), salesLC.toBigDecimal(), 2);

		return GPLRReportSummary.builder()
				.localCurrency(localCurrency)
				.foreignCurrency(foreignCurrency)
				.salesFC(salesFC)
				.salesLC(salesLC)
				.taxesLC(taxesLC)
				.estimatedFC(estimatedFC)
				.estimatedLC(estimatedLC)
				.cogsLC(cogsLC)
				.chargesFC(chargesFC)
				.chargesLC(chargesLC)
				.profitOrLossLC(profitOrLossLC)
				.profitRate(profitRate)
				.build();
	}

	private GPLRReportSourceDocument createGPLRReportSourceDocument()
	{
		final SourceInvoice salesInvoice = source.getSalesInvoice();

		return GPLRReportSourceDocument.builder()
				.salesInvoiceId(salesInvoice.getId())
				.orgId(salesInvoice.getOrgId())
				.departmentName(getDepartment().map(Department::getName).orElse(null))
				.sectionCode(getSectionCode().orElse(null))
				.documentNo(salesInvoice.getDocumentNo())
				.invoiceDocTypeName(salesInvoice.getDocTypeName())
				.createdByName(salesInvoice.getCreatedBy().getName())
				.documentDate(salesInvoice.getDateInvoiced())
				.created(salesInvoice.getCreated())
				.sapProductHierarchy(salesInvoice.getSapProductHierarchy())
				.paymentTerm(GPLRPaymentTermRenderedString.of(salesInvoice.getPaymentTerm()))
				.dueDate(salesInvoice.getDueDate())
				.currencyInfo(toCurrencyInfo(salesInvoice.getCurrencyInfo()))
				.build();
	}

	private Optional<GPLRSectionCodeRenderedString> getSectionCode()
	{
		return source.getSalesOrder().getSectionCode().map(GPLRSectionCodeRenderedString::of);
	}

	private Optional<Department> getDepartment()
	{
		final SourceOrder salesOrder = source.getSalesOrder();
		return salesOrder.getSectionCode().flatMap(sectionCode -> departmentService.getDepartmentBySectionCodeId(sectionCode.getSectionCodeId(), salesOrder.getDateOrdered()));
	}

	private static GPLRCurrencyInfo toCurrencyInfo(final SourceCurrencyInfo currencyInfo)
	{
		return GPLRCurrencyInfo.builder()
				.foreignCurrency(currencyInfo.getForeignCurrencyCode())
				.currencyRate(currencyInfo.getCurrencyRate().toBigDecimal())
				.fecDocumentNo(currencyInfo.getFecDocumentNo())
				.build();
	}

	private GPLRReportSalesOrder createGPLRReportSalesOrder()
	{
		final SourceOrder salesOrder = source.getSalesOrder();

		return GPLRReportSalesOrder.builder()
				.documentNo(salesOrder.getDocumentNo())
				.customer(toBPartnerName(salesOrder.getBpartner()))
				.frameContractNo(salesOrder.getFrameContractNo())
				.poReference(salesOrder.getPoReference())
				.incoterms(toIncotermsInfo(salesOrder.getIncotermsAndLocation()))
				.build();
	}

	private static GPLRBPartnerName toBPartnerName(@NonNull final SourceBPartnerInfo bpartner)
	{
		return GPLRBPartnerName.builder()
				.code(bpartner.getCode())
				.name(bpartner.getName())
				.vatId(bpartner.getVatId())
				.build();
	}

	private GPLRReportShipment createGPLRReportShipment(@NonNull final SourceShipment shipment)
	{
		return GPLRReportShipment.builder()
				.documentNo(shipment.getDocumentNo())
				.shipTo(toBPartnerName(shipment.getShipTo()))
				.shipToCountry(shipment.getShipTo().getCountryCode())
				.warehouse(toWarehouseName(shipment))
				.movementDate(shipment.getMovementDate())
				.incoterms(toIncotermsInfo(shipment.getIncoterms()))
				.shipper(toShipperName(shipment.getShipper()))
				.build();
	}

	private static GPLRWarehouseName toWarehouseName(final SourceShipment shipment)
	{
		return GPLRWarehouseName.builder()
				.code(shipment.getShipFrom().getWarehouseCode())
				.name(shipment.getShipFrom().getWarehouseName())
				.build();
	}

	@Nullable
	private static GPLRIncotermsInfo toIncotermsInfo(@Nullable final SourceIncotermsAndLocation incotermsAndLocation)
	{
		if (incotermsAndLocation == null)
		{
			return null;
		}

		return GPLRIncotermsInfo.builder()
				.code(incotermsAndLocation.getIncoterms().getValue())
				.location(incotermsAndLocation.getLocation())
				.build();
	}

	@Nullable
	private GPLRShipperRenderedString toShipperName(@Nullable final SourceShipperInfo shipper)
	{
		if (shipper == null)
		{
			return null;
		}

		final ITranslatableString shipperName = shipper.getName();
		return GPLRShipperRenderedString.ofShipperName(shipperName.translate(adLanguage));
	}

	private GPLRReportPurchaseOrder createGPLRReportPurchaseOrder(final SourceOrder purchaseOrder)
	{
		return GPLRReportPurchaseOrder.builder()
				.documentNo(purchaseOrder.getDocumentNo())
				.purchasedFrom(toBPartnerName(purchaseOrder.getBpartner()))
				.vendorReference(purchaseOrder.getPoReference()) // is it OK?
				.paymentTerm(GPLRPaymentTermRenderedString.of(purchaseOrder.getPaymentTerm()))
				.incoterms(toIncotermsInfo(purchaseOrder.getIncotermsAndLocation()))
				.currencyInfo(toCurrencyInfo(purchaseOrder.getCurrencyInfo()))
				.build();
	}

	private ImmutableList<GPLRReportLineItem> createGPLRReportLineItems()
	{
		final ImmutableList.Builder<GPLRReportLineItem> result = ImmutableList.builder();

		//
		// Sales Order
		{
			final SourceCurrencyInfo currencyInfo = source.getSalesInvoice().getCurrencyInfo();
			final SourceOrder salesOrder = source.getSalesOrder();
			for (final SourceOrderLine salesOrderLine : salesOrder.getLines())
			{
				result.add(createGPLRReportLine_DocumentLine(salesOrderLine, salesOrder));
				result.add(createGPLRReportLine_Price(salesOrderLine, salesOrder, currencyInfo));
				result.add(createGPLRReportLine_VAT(salesOrderLine, salesOrder, currencyInfo));
				result.add(createGPLRReportLine_COGS(salesOrderLine, salesOrder));
			}
		}

		//
		// Purchase Order(s)
		for (final SourceOrder purchaseOrder : source.getPurchaseOrders())
		{
			final SourceCurrencyInfo currencyInfo = purchaseOrder.getCurrencyInfo();

			for (final SourceOrderLine purchaseOrderLine : purchaseOrder.getLines())
			{
				result.add(createGPLRReportLine_DocumentLine(purchaseOrderLine, purchaseOrder));
				result.add(createGPLRReportLine_Price(purchaseOrderLine, purchaseOrder, currencyInfo));
				result.add(createGPLRReportLine_VAT(purchaseOrderLine, purchaseOrder, currencyInfo));
			}
		}

		//
		//
		return result.build();
	}

	private GPLRReportLineItem createGPLRReportLine_COGS(final SourceOrderLine salesOrderLine, final SourceOrder salesOrder)
	{
		final Amount cogsLC = salesOrderLine.getCogsLC();
		return GPLRReportLineItem.builder()
				.documentNo(salesOrder.getDocumentNo())
				.lineCode("VPRS")
				.description("Cost")
				.amountFC(null)
				.amountLC(cogsLC)
				.build();
	}

	private GPLRReportLineItem createGPLRReportLine_VAT(final SourceOrderLine salesOrderLine, final SourceOrder order, final SourceCurrencyInfo currencyInfo)
	{
		final Amount taxAmtFC = salesOrderLine.getTaxAmtFC();
		return GPLRReportLineItem.builder()
				.documentNo(order.getDocumentNo())
				.lineCode("MWST")
				.description(salesOrderLine.getTax().toRenderedString())
				.amountFC(taxAmtFC)
				.amountLC(currencyInfo.convertToLocal(taxAmtFC, currencyCodeConverter))
				.build();
	}

	private GPLRReportLineItem createGPLRReportLine_Price(final SourceOrderLine orderLine, final SourceOrder order, final SourceCurrencyInfo currencyInfo)
	{
		final Amount lineNetAmtFC = orderLine.getLineNetAmtFC();
		return GPLRReportLineItem.builder()
				.documentNo(order.getDocumentNo())
				.lineCode("PR00")
				.description("Price")
				.amountFC(lineNetAmtFC)
				.amountLC(currencyInfo.convertToLocal(lineNetAmtFC, currencyCodeConverter))
				.build();
	}

	private static GPLRReportLineItem createGPLRReportLine_DocumentLine(final SourceOrderLine orderLine, final SourceOrder order)
	{
		return GPLRReportLineItem.builder()
				.documentNo(order.getDocumentNo())
				.lineCode(formatOrderLineNo(orderLine.getLineNo()))
				.description(orderLine.getProductName())
				.qty(orderLine.getQtyEntered())
				.priceFC(orderLine.getPriceFC())
				.batchNo(orderLine.getBatchNo())
				.amountFC(null)
				.amountLC(null)
				.build();
	}

	private ImmutableList<GPLRReportCharge> createGPLRReportCharges()
	{
		ImmutableList.Builder<GPLRReportCharge> result = ImmutableList.builder();

		for (final SourceOrder purchaseOrder : source.getPurchaseOrders())
		{
			for (final SourceOrderLine purchaseOrderLine : purchaseOrder.getLines())
			{
				final OrderLineId purchaseOrderLineId = purchaseOrderLine.getId();
				for (final SourceOrderCost purchaseOrderCost : purchaseOrder.getOrderCostsByLineId(purchaseOrderLineId))
				{
					if (!purchaseOrderCost.isSingleOrderLine())
					{
						continue;
					}

					result.add(createGPLRReportCharge(purchaseOrderCost, purchaseOrder, purchaseOrderLine));
				}
			}

			for (final SourceOrderCost purchaseOrderCost : purchaseOrder.getOrderCosts())
			{
				if (purchaseOrderCost.isSingleOrderLine())
				{
					continue;
				}

				result.add(createGPLRReportCharge(purchaseOrderCost, purchaseOrder, null));
			}
		}

		return result.build();
	}

	private GPLRReportCharge createGPLRReportCharge(
			@NonNull final SourceOrderCost purchaseOrderCost,
			@NonNull final SourceOrder purchaseOrder,
			@Nullable final SourceOrderLine purchaseOrderLine)
	{
		final SourceCurrencyInfo currencyInfo = purchaseOrder.getCurrencyInfo();

		return GPLRReportCharge.builder()
				.purchaseOrderDocumentNo(purchaseOrder.getDocumentNo())
				.orderLineNo(purchaseOrderLine != null ? formatOrderLineNo(purchaseOrderLine.getLineNo()) : null)
				.costTypeName(purchaseOrderCost.getCostTypeName())
				.vendor(purchaseOrderCost.getVendor() != null ? toBPartnerName(purchaseOrderCost.getVendor()) : null)
				.amountFC(purchaseOrderCost.getCostAmountFC())
				.amountLC(currencyInfo.convertToLocal(purchaseOrderCost.getCostAmountFC(), currencyCodeConverter))
				.build();
	}

	private static String formatOrderLineNo(final int line) {return String.format("%06d", line);}

	private ImmutableList<GPLRReportNote> createGPLRReportNotes()
	{
		final SourceInvoice salesInvoice = source.getSalesInvoice();
		return ImmutableList.of(
				GPLRReportNote.builder()
						.sourceDocument("Billing Document " + salesInvoice.getDocumentNo())
						.text(salesInvoice.getDescriptionBottom()) // TODO check if that's correct
						.build()
		);
	}
}
