package de.metas.gplr;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyConversionContext;
import de.metas.department.Department;
import de.metas.department.DepartmentService;
import de.metas.gplr.model.GPLRBPartnerName;
import de.metas.gplr.model.GPLRCurrencyInfo;
import de.metas.gplr.model.GPLRIncotermsInfo;
import de.metas.gplr.model.GPLRPaymentTermRenderedString;
import de.metas.gplr.model.GPLRReport;
import de.metas.gplr.model.GPLRReportCharge;
import de.metas.gplr.model.GPLRReportLineItem;
import de.metas.gplr.model.GPLRReportNote;
import de.metas.gplr.model.GPLRReportPurchaseOrder;
import de.metas.gplr.model.GPLRReportSalesOrder;
import de.metas.gplr.model.GPLRReportShipment;
import de.metas.gplr.model.GPLRReportSourceDocument;
import de.metas.gplr.model.GPLRReportSummary;
import de.metas.gplr.model.GPLRSectionCodeRenderedString;
import de.metas.gplr.model.GPLRShipperRenderedString;
import de.metas.gplr.model.GPLRWarehouseName;
import de.metas.gplr.source.SourceBPartnerInfo;
import de.metas.gplr.source.SourceCurrencyInfo;
import de.metas.gplr.source.SourceDocuments;
import de.metas.gplr.source.SourceIncotermsAndLocation;
import de.metas.gplr.source.SourceInvoice;
import de.metas.gplr.source.SourceOrder;
import de.metas.gplr.source.SourceOrderCost;
import de.metas.gplr.source.SourceOrderLine;
import de.metas.gplr.source.SourceShipment;
import de.metas.gplr.source.SourceShipperInfo;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.money.MoneyService;
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
	@NonNull private final DepartmentService departmentService;
	@NonNull private final MoneyService moneyService;
	//
	// Params
	@NonNull private final SourceDocuments source;

	//
	// State
	@NonNull private final String adLanguage = Language.getBaseAD_Language();
	@NonNull private final Instant reportDate = SystemTime.asInstant();

	@Builder
	private GPLRReportCreateCommand(
			final @NonNull DepartmentService departmentService,
			final @NonNull MoneyService moneyService,
			final @NonNull SourceDocuments source)
	{
		this.departmentService = departmentService;
		this.moneyService = moneyService;
		//
		this.source = source;
	}

	public void execute()
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

		System.out.println(report); // FIXME DEBUG
	}

	private GPLRReportSummary createGPLRReportSummary()
	{
		final SourceOrder salesOrder = source.getSalesOrder();
		final SourceInvoice salesInvoice = source.getSalesInvoice();

		final SourceCurrencyInfo currencyInfo = salesInvoice.getCurrencyInfo();
		final CurrencyCode localCurrency = currencyInfo.getLocalCurrencyCode();
		final CurrencyCode foreignCurrency = currencyInfo.getForeignCurrencyCode();
		final CurrencyConversionContext currencyConversionCtx = currencyInfo.getConversionCtx();
		final UnaryOperator<Amount> toLocal = amountFC -> moneyService.convertToCurrency(amountFC, localCurrency, currencyConversionCtx);

		final Amount estimatedFC = salesOrder.getEstimatedOrderCostAmount();

		// TODO impl
		return GPLRReportSummary.builder()
				.localCurrency(localCurrency)
				.foreignCurrency(foreignCurrency)
				.salesFC(salesInvoice.getLinesNetAmt())
				.salesLC(toLocal.apply(salesInvoice.getLinesNetAmt()))
				.taxesLC(toLocal.apply(salesInvoice.getTaxAmt()))
				.estimatedFC(estimatedFC)
				.estimatedLC(toLocal.apply(estimatedFC))
				.cogsLC(Amount.zero(localCurrency)) // TODO: get them from shipment accounting
				.chargesFC(Amount.zero(foreignCurrency)) // TODO
				.chargesLC(Amount.zero(localCurrency)) // TODO
				.profitOrLossLC(Amount.zero(localCurrency)) // TODO
				.profitRate(Percent.ZERO) // TODO
				.build();
	}

	private GPLRReportSourceDocument createGPLRReportSourceDocument()
	{
		final SourceInvoice salesInvoice = source.getSalesInvoice();

		return GPLRReportSourceDocument.builder()
				.invoiceId(salesInvoice.getId())
				.orgId(salesInvoice.getOrgId())
				.departmentName(getDepartment().map(Department::getName).orElse(null))
				.sectionCode(getSectionCode().orElse(null))
				.documentNo(salesInvoice.getDocumentNo())
				.invoiceDocTypeName(salesInvoice.getDocTypeName())
				.createdByName(salesInvoice.getCreatedBy().getName())
				.documentDate(salesInvoice.getDateInvoiced())
				.created(salesInvoice.getCreated())
				.product("TODO") // TODO find out how to set it here since the SAP_ProductHierarchy is on M_Product which is not on document header level!
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

	@Nullable
	private GPLRCurrencyInfo toCurrencyInfo(final SourceCurrencyInfo currencyInfo)
	{
		return GPLRCurrencyInfo.builder()
				.foreignCurrency(currencyInfo.getForeignCurrencyCode())
				.currencyRate(currencyInfo.getCurrencyRate())
				.fecDocumentNo(currencyInfo.getFecDocumentNo())
				.build();
	}

	private GPLRReportSalesOrder createGPLRReportSalesOrder()
	{
		final SourceOrder salesOrder = source.getSalesOrder();

		return GPLRReportSalesOrder.builder()
				.documentNo(salesOrder.getDocumentNo())
				.customer(toBPartnerName(salesOrder.getBpartner()))
				// TODO figure out how to fetch it... is it C_Order.C_FrameAgreement_Order_ID.DocumentNo?
				// Ruxi sais: C_OrderLine.C_FlatrateTerm_ID.DocumentNo
				.frameContractNo(null)
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
				.currencyInfo(null) // TODO get it from assigned contract.. better solve it on source loading level
				.build();
	}

	private ImmutableList<GPLRReportLineItem> createGPLRReportLineItems()
	{
		final ImmutableList.Builder<GPLRReportLineItem> result = ImmutableList.builder();

		//
		// Sales Order
		{
			final SourceOrder salesOrder = source.getSalesOrder();
			for (final SourceOrderLine salesOrderLine : salesOrder.getLines())
			{
				//
				// Document line
				result.add(GPLRReportLineItem.builder()
						.documentNo(salesOrder.getDocumentNo())
						.lineCode(formatOrderLineNo(salesOrderLine.getLineNo()))
						.description(salesOrderLine.getProductName())
						.qty(salesOrderLine.getQtyEntered())
						.priceFC(null) // TODO
						.batchNo(null) // TODO
						.amountLC(null) // TODO
						.amountFC(null) // TODO
						.build());

				//
				// PR00 - Price
				result.add(GPLRReportLineItem.builder()
						.lineCode("PR00")
						.description("Price")
						.amountLC(null) // TODO
						.amountFC(null) // TODO
						.build());

				//
				// MWST - VAT
				result.add(GPLRReportLineItem.builder()
						.lineCode("MWST")
						.description("VAT name") // TODO
						.amountLC(null) // TODO
						.amountFC(null) // TODO
						.build());

				//
				// VPRS - COGS
				result.add(GPLRReportLineItem.builder()
						.lineCode("VPRS")
						.description("Cost")
						.amountLC(null) // TODO
						.amountFC(null) // TODO
						.build());
			}
		}

		//
		// Purchase Order
		{
			// TODO implement
		}

		//
		//
		return result.build();
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
		return GPLRReportCharge.builder()
				.purchaseOrderDocumentNo(purchaseOrder.getDocumentNo())
				.orderLineNo(purchaseOrderLine != null ? formatOrderLineNo(purchaseOrderLine.getLineNo()) : null)
				.costTypeName(purchaseOrderCost.getCostTypeName())
				.vendor(purchaseOrderCost.getVendor() != null ? toBPartnerName(purchaseOrderCost.getVendor()) : null)
				.amountFC(purchaseOrderCost.getCostAmount())
				.amountLC(null) // TODO
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
