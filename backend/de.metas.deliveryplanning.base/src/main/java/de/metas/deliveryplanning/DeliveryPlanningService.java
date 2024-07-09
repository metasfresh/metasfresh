/*
 * #%L
 * de.metas.swat.base
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

package de.metas.deliveryplanning;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.blockstatus.BPartnerBlockStatusService;
import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerStatsService;
import de.metas.bpartner.service.impl.CalculateCreditStatusRequest;
import de.metas.bpartner.service.impl.CreditStatus;
import de.metas.cache.CacheMgt;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyBL;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.AdMessageKey;
import de.metas.incoterms.IncotermsId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.sectionCode.SectionCodeId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DocTypeNotFoundException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_BPartner_BlockStatus;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class DeliveryPlanningService
{
	public static final AdMessageKey MSG_M_Delivery_Planning_AllClosed = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.AllClosed");
	public static final AdMessageKey MSG_M_Delivery_Planning_AllOpen = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.AllOpen");
	public static final AdMessageKey MSG_M_Delivery_Planning_AtLeastOnePerOrderLine = AdMessageKey.of("de.metas.deliveryplanning.M_Delivery_Planning_AtLeastOnePerOrderLine");

	private static final AdMessageKey MSG_M_Delivery_Planning_AlreadyReferenced = AdMessageKey.of("de.metas.deliveryplanning.M_Delivery_Planning_AlreadyReferenced");

	public static final AdMessageKey MSG_M_Delivery_Planning_NoForwarder = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.NoForwarder");
	public static final AdMessageKey MSG_M_Delivery_Planning_AllHaveReleaseNo = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.AllHaveReleaseNo");
	public static final AdMessageKey MSG_M_Delivery_Planning_WhithOutReleaseNo = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.WhithOutReleaseNo");
	public static final AdMessageKey MSG_M_Delivery_Planning_BlockedPartner = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.NoBlockedPartner");
	public static final AdMessageKey MSG_M_Delivery_Planning_SalesOrderFullyDelivered = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.SalesOrderFullyDelivered");
	public static final AdMessageKey MSG_M_Delivery_Planning_PurchaseOrderFullyDelivered = AdMessageKey.of("de.metas.deliveryplanning.DeliveryPlanningService.PurchaseOrderFullyDelivered");
	private static final String SYSCONFIG_M_Delivery_Planning_CreateAutomatically = "de.metas.deliveryplanning.DeliveryPlanningService.M_Delivery_Planning_CreateAutomatically";

	public static final String PARAM_AdditionalLines = "AdditionalLines";

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final DeliveryPlanningRepository deliveryPlanningRepository;
	private final DeliveryStatusColorPaletteService deliveryStatusColorPaletteService;
	private final BPartnerBlockStatusService bPartnerBlockStatusService;

	private final BPartnerStatsService bPartnerStatsService;

	private final DimensionService dimensionService;

	final MoneyService moneyService;
	private final MeansOfTransportationService meansOfTransportationService;

	final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	final ITaxBL taxBL = Services.get(ITaxBL.class);

	final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	final IBPartnerBL partnerBL = Services.get(IBPartnerBL.class);

	final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);
	final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);

	public DeliveryPlanningService(
			@NonNull final DeliveryPlanningRepository deliveryPlanningRepository,
			@NonNull final DeliveryStatusColorPaletteService deliveryStatusColorPaletteService,
			@NonNull final BPartnerStatsService bPartnerStatsService,
			@NonNull final MoneyService moneyService,
			@NonNull final BPartnerBlockStatusService bPartnerBlockStatusService,
			@NonNull final DimensionService dimensionService,
			@NonNull final MeansOfTransportationService meansOfTransportationService)
	{
		this.deliveryPlanningRepository = deliveryPlanningRepository;
		this.deliveryStatusColorPaletteService = deliveryStatusColorPaletteService;
		this.bPartnerStatsService = bPartnerStatsService;
		this.moneyService = moneyService;
		this.bPartnerBlockStatusService = bPartnerBlockStatusService;
		this.dimensionService = dimensionService;
		this.meansOfTransportationService = meansOfTransportationService;
	}

	public boolean isAutoCreateEnabled(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_M_Delivery_Planning_CreateAutomatically, false, clientAndOrgId);
	}

	private DeliveryStatusColorPalette getColorPalette()
	{
		return deliveryStatusColorPaletteService.get();
	}

	public void generateIncomingDeliveryPlanning(final I_M_ReceiptSchedule receiptScheduleRecord)
	{
		GenerateIncomingDeliveryPlanningCommand.builder()
				.deliveryPlanningRepository(deliveryPlanningRepository)
				.receiptSchedule(receiptScheduleRecord)
				.colorPalette(getColorPalette())
				.dimensionService(dimensionService)
				.build()
				.execute();
	}

	public void generateOutgoingDeliveryPlanning(final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		GenerateOutgoingDeliveryPlanningCommand.builder()
				.deliveryPlanningRepository(deliveryPlanningRepository)
				.shipmentSchedule(shipmentScheduleRecord)
				.colorPalette(getColorPalette())
				.dimensionService(dimensionService)
				.build()
				.execute();
	}

	public void validateDeletion(final I_M_Delivery_Planning deliveryPlanning)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(deliveryPlanning.getC_OrderLine_ID());
		if (orderLineId == null)
		{
			// nothing to do: delivery planning is not based on any order line
			return;
		}

		final boolean otherDeliveryPlanningsExistForOrderLine = deliveryPlanningRepository.isOtherDeliveryPlanningsExistForOrderLine(orderLineId, DeliveryPlanningId.ofRepoId(deliveryPlanning.getM_Delivery_Planning_ID()));

		if (!otherDeliveryPlanningsExistForOrderLine)
		{
			throw new AdempiereException(MSG_M_Delivery_Planning_AtLeastOnePerOrderLine);
		}

		if (!Check.isBlank(deliveryPlanning.getReleaseNo()))
		{
			throw new AdempiereException(MSG_M_Delivery_Planning_AlreadyReferenced);
		}

	}

	private DeliveryPlanningCreateRequest createRequest(@NonNull final DeliveryPlanningId deliveryPlanningId, @NonNull final Quantity plannedLoadedQty)
	{
		final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningRepository.getById(deliveryPlanningId);
		final OrgId orgId = OrgId.ofRepoId(deliveryPlanningRecord.getAD_Org_ID());

		final ProductId productId = ProductId.ofRepoId(deliveryPlanningRecord.getM_Product_ID());
		final I_C_UOM uomOfRecord = uomDAO.getByIdOrNull(deliveryPlanningRecord.getC_UOM_ID());
		final I_C_UOM uomToUse = uomOfRecord != null ? uomOfRecord : productBL.getStockUOM(productId);

		final Dimension dimension = dimensionService.getFromRecord(deliveryPlanningRecord);

		return DeliveryPlanningCreateRequest.builder()
				.orgId(orgId)
				.clientId(ClientId.ofRepoId(deliveryPlanningRecord.getAD_Client_ID()))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoIdOrNull(deliveryPlanningRecord.getM_ShipmentSchedule_ID()))
				.receiptScheduleId(ReceiptScheduleId.ofRepoIdOrNull(deliveryPlanningRecord.getM_ReceiptSchedule_ID()))
				.orderId(OrderId.ofRepoIdOrNull(deliveryPlanningRecord.getC_Order_ID()))
				.orderLineId(OrderLineId.ofRepoIdOrNull(deliveryPlanningRecord.getC_OrderLine_ID()))
				.productId(productId)
				.partnerId(BPartnerId.ofRepoId(deliveryPlanningRecord.getC_BPartner_ID()))
				.bPartnerLocationId(BPartnerLocationId.ofRepoId(deliveryPlanningRecord.getC_BPartner_ID(), deliveryPlanningRecord.getC_BPartner_Location_ID()))
				.incotermsId(IncotermsId.ofRepoIdOrNull(deliveryPlanningRecord.getC_Incoterms_ID()))
				.incotermLocation(deliveryPlanningRecord.getIncotermLocation())
				.sectionCodeId(SectionCodeId.ofRepoIdOrNull(deliveryPlanningRecord.getM_SectionCode_ID()))
				.warehouseId(WarehouseId.ofRepoId(deliveryPlanningRecord.getM_Warehouse_ID()))
				.deliveryPlanningType(DeliveryPlanningRepository.extractDeliveryPlanningType(deliveryPlanningRecord))
				.orderStatus(OrderStatus.ofNullableCode(deliveryPlanningRecord.getOrderStatus()))
				.meansOfTransportationId(MeansOfTransportationId.ofRepoIdOrNull(deliveryPlanningRecord.getM_MeansOfTransportation_ID()))
				.isB2B(deliveryPlanningRecord.isB2B())
				.qtyOrdered(Quantity.of(deliveryPlanningRecord.getQtyOrdered(), uomToUse))
				.qtyTotalOpen(Quantity.of(deliveryPlanningRecord.getQtyTotalOpen(), uomToUse))
				.actualLoadedQty(Quantity.of(deliveryPlanningRecord.getActualLoadQty(), uomToUse))

				.plannedLoadedQty(plannedLoadedQty)
				.plannedDischargeQty(Quantity.of(deliveryPlanningRecord.getPlannedDischargeQuantity(), uomToUse))
				.actualDischargeQty(Quantity.of(deliveryPlanningRecord.getActualDischargeQuantity(), uomToUse))

				.uom(uomToUse)
				.plannedLoadingDate(TimeUtil.asInstant(deliveryPlanningRecord.getPlannedLoadingDate()))
				.actualLoadingDate(TimeUtil.asInstant(deliveryPlanningRecord.getActualLoadingDate()))
				.plannedDeliveryDate(TimeUtil.asInstant(deliveryPlanningRecord.getPlannedDeliveryDate()))
				.actualDeliveryDate(TimeUtil.asInstant(deliveryPlanningRecord.getActualDeliveryDate()))
				.loadingTime(deliveryPlanningRecord.getLoadingTime())
				.deliveryTime(deliveryPlanningRecord.getDeliveryTime())
				.wayBillNo(deliveryPlanningRecord.getWayBillNo())
				.batch(deliveryPlanningRecord.getBatch())
				.originCountryId(CountryId.ofRepoIdOrNull(deliveryPlanningRecord.getC_OriginCountry_ID()))
				.destinationCountryId(CountryId.ofRepoIdOrNull(deliveryPlanningRecord.getC_DestinationCountry_ID()))
				.shipperId(ShipperId.ofRepoIdOrNull(deliveryPlanningRecord.getM_Shipper_ID()))
				.transportDetails(deliveryPlanningRecord.getTransportDetails())
				.dimension(dimension)
				.build();
	}

	public void createAdditionalDeliveryPlannings(@NonNull final DeliveryPlanningId deliveryPlanningId, final int additionalLines)
	{
		validateDeliveryPlanning(deliveryPlanningId);

		Check.assumeGreaterThanZero(additionalLines, PARAM_AdditionalLines);

		final Quantity openQty = getOpenQty(deliveryPlanningId);

		final Quantity fraction = openQty.divide(BigDecimal.valueOf(additionalLines + 1), 0, RoundingMode.DOWN);

		final Quantity remainder = openQty.subtract(fraction.multiply(additionalLines + 1));
		deliveryPlanningRepository.setPlannedLoadedQuantity(deliveryPlanningId, fraction.add(remainder));

		for (int i = 0; i < additionalLines; i++)
		{
			final DeliveryPlanningCreateRequest request = createRequest(deliveryPlanningId, fraction);

			deliveryPlanningRepository.generateDeliveryPlanning(request);
		}
	}

	private Quantity getOpenQty(final DeliveryPlanningId deliveryPlanningId)
	{
		final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningRepository.getById(deliveryPlanningId);
		final I_C_UOM uom = uomDAO.getById(deliveryPlanningRecord.getC_UOM_ID());

		final Quantity qtyOrdered = Quantity.of(deliveryPlanningRecord.getQtyOrdered(), uom);

		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(deliveryPlanningRecord.getC_OrderLine_ID());
		if (orderLineId == null)
		{
			// the delivery planning has no order line => remaining open qty is 0
			return Quantity.zero(uom);
		}

		Quantity openQty = qtyOrdered;

		final Quantity plannedLoadedQtySum = deliveryPlanningRepository.retrieveForOrderLine(orderLineId)
				.filter(deliveryPlanning -> deliveryPlanningId.getRepoId() != deliveryPlanning.getM_Delivery_Planning_ID())
				.map(DeliveryPlanningService::extractPlannedLoadedQuantity)
				.reduce(Quantity::add)
				.orElse(null);
		if (plannedLoadedQtySum != null && !plannedLoadedQtySum.isZero())
		{
			openQty = openQty.subtract(plannedLoadedQtySum);
		}

		return openQty.toZeroIfNegative();
	}

	private static Quantity extractPlannedLoadedQuantity(final I_M_Delivery_Planning deliveryPlanning)
	{
		final UomId uomId = UomId.ofRepoId(deliveryPlanning.getC_UOM_ID());
		return Quantitys.of(deliveryPlanning.getPlannedLoadedQuantity(), uomId);
	}

	public void deleteForReceiptSchedule(@NonNull final ReceiptScheduleId receiptScheduleId)
	{
		deliveryPlanningRepository.deleteForReceiptSchedule(receiptScheduleId);
	}

	public void deleteForShipmentSchedule(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		deliveryPlanningRepository.deleteForShipmentSchedule(shipmentScheduleId);
	}

	public boolean isClosed(final DeliveryPlanningId deliveryPlanningId)
	{
		final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningRepository.getById(deliveryPlanningId);
		return deliveryPlanningRecord.isClosed();
	}

	public void closeSelectedDeliveryPlannings(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		validateDeliveryPlannings(selectedDeliveryPlanningsFilter);
		deliveryPlanningRepository.closeSelectedDeliveryPlannings(selectedDeliveryPlanningsFilter);
	}

	public void reOpenSelectedDeliveryPlannings(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		validateDeliveryPlannings(selectedDeliveryPlanningsFilter);
		deliveryPlanningRepository.reOpenSelectedDeliveryPlannings(selectedDeliveryPlanningsFilter);
	}

	public boolean isExistsClosedDeliveryPlannings(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return deliveryPlanningRepository.isExistsClosedDeliveryPlannings(selectedDeliveryPlanningsFilter);
	}

	public boolean isExistsOpenDeliveryPlannings(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return deliveryPlanningRepository.isExistsOpenDeliveryPlannings(selectedDeliveryPlanningsFilter);
	}

	public boolean isExistsNoShipperDeliveryPlannings(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return deliveryPlanningRepository.isExistNoShipperDeliveryPlannings(selectedDeliveryPlanningsFilter);
	}

	public void generateCompleteDeliveryInstruction(@NonNull final DeliveryInstructionCreateRequest deliveryInstructionRequest)
	{
		final DeliveryInstructionUserNotificationsProducer deliveryInstructionUserNotificationsProducer = DeliveryInstructionUserNotificationsProducer.newInstance();

		final DeliveryPlanningId deliveryPlanningId = deliveryInstructionRequest.getDeliveryPlanningId();

		final I_M_ShipperTransportation deliveryInstruction = deliveryPlanningRepository.generateDeliveryInstruction(deliveryInstructionRequest);

		docActionBL.processEx(deliveryInstruction, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		deliveryInstructionUserNotificationsProducer
				.notifyGenerated(deliveryInstruction);

		deliveryPlanningRepository.updateDeliveryPlanningFromInstruction(deliveryPlanningId, deliveryInstruction);

		CacheMgt.get().reset(I_M_Delivery_Planning.Table_Name, deliveryPlanningId.getRepoId());

	}

	public boolean creditLimitAllowsDeliveryInstruction(@NonNull final DeliveryInstructionCreateRequest deliveryInstructionRequest)
	{

		final BPartnerStats stats = bPartnerStatsService.getCreateBPartnerStats(deliveryInstructionRequest.getShipperBPartnerId());
		final CreditStatus deliveryCreditStatus = stats.getDeliveryCreditStatus();

		if (CreditStatus.CreditStop.equals(deliveryCreditStatus) || CreditStatus.CreditHold.equals(deliveryCreditStatus))
		{
			return false;
		}

		final CurrencyId baseCurrencyId = currencyBL.getBaseCurrencyId(deliveryInstructionRequest.getClientId(),
				deliveryInstructionRequest.getOrgId());

		final Money creditUsedByDeliveryInstruction = computeCreditUsedByDeliveryInstruction(deliveryInstructionRequest, baseCurrencyId);

		final CalculateCreditStatusRequest calculateCreditStatusRequest = CalculateCreditStatusRequest.builder()
				.stat(stats)
				.additionalAmt(creditUsedByDeliveryInstruction.toBigDecimal())
				.date(TimeUtil.asTimestamp(deliveryInstructionRequest.getDateDoc()))
				.build();

		final CreditStatus calculatedDeliveryCreditStatus = bPartnerStatsService.calculateProjectedDeliveryCreditStatus(calculateCreditStatusRequest);

		if (CreditStatus.CreditHold.equals(calculatedDeliveryCreditStatus))
		{
			return false;
		}

		return true;

	}

	private Money computeCreditUsedByDeliveryInstruction(@NonNull final DeliveryInstructionCreateRequest request,
														 @NonNull final CurrencyId currencyId)
	{
		if (request.getOrderLineId() == null)
		{
			return Money.zero(currencyId);
		}

		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(request.getOrderLineId());

		final Quantity actualLoadQty = request.getQtyLoaded();

		final CurrencyId orderLineCurrencyId = CurrencyId.ofRepoId(orderLine.getC_Currency_ID());
		final Money qtyNetPriceFromOrderLine = Money.of(orderLineBL.computeQtyNetPriceFromOrderLine(orderLine, actualLoadQty),
				orderLineCurrencyId);

		final TaxId taxId = TaxId.ofRepoId(orderLine.getC_Tax_ID());

		final boolean isTaxIncluded = orderLineBL.isTaxIncluded(orderLine);

		final CurrencyPrecision taxPrecision = orderLineBL.getTaxPrecision(orderLine);

		final Tax tax = taxBL.getTaxById(taxId);

		final BigDecimal taxAmt = tax.calculateTax(qtyNetPriceFromOrderLine.toBigDecimal(), isTaxIncluded, taxPrecision.toInt()).getTaxAmount();

		final Money taxAmtInfo = Money.of(taxAmt, orderLineCurrencyId);

		final CurrencyConversionContext currencyConversionContext = extractDeliveryInstructionConversionContext(request);

		return moneyService.convertMoneyToCurrency(taxAmtInfo.add(qtyNetPriceFromOrderLine), currencyId, currencyConversionContext);
	}

	private CurrencyConversionContext extractDeliveryInstructionConversionContext(final DeliveryInstructionCreateRequest request)
	{
		return currencyBL.createCurrencyConversionContext(
				request.getDateDoc(),
				(CurrencyConversionTypeId)null,
				request.getClientId(),
				request.getOrgId());
	}

	public boolean isExistDeliveryPlanningsWithoutReleaseNo(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return deliveryPlanningRepository.isExistDeliveryPlanningsWithoutReleaseNo(selectedDeliveryPlanningsFilter);
	}

	public boolean isExistDeliveryPlanningsWithReleaseNo(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return deliveryPlanningRepository.isExistDeliveryPlanningsWithReleaseNo(selectedDeliveryPlanningsFilter);
	}

	private DeliveryInstructionCreateRequest createDeliveryInstructionRequest(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningRepository.getById(deliveryPlanningId);

		if (deliveryPlanningRecord.getM_Shipper_ID() == 0)
		{
			throw new AdempiereException("Cannot create M_ShipperTransportation if M_Shipper_ID is missing")
					.appendParametersToMessage()
					.setParameter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningId.getRepoId());
		}

		final OrgId orgId = OrgId.ofRepoId(deliveryPlanningRecord.getAD_Org_ID());

		final DeliveryPlanningType deliveryPlanningType = DeliveryPlanningRepository.extractDeliveryPlanningType(deliveryPlanningRecord);

		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.docBaseType(DocBaseType.ShipperTransportation)
				.docSubType(X_C_DocType.DOCSUBTYPE_DeliveryInstruction)
				.adClientId(deliveryPlanningRecord.getAD_Client_ID())
				.adOrgId(deliveryPlanningRecord.getAD_Org_ID())
				.build();

		final DocTypeId docTypeId = docTypeDAO.getDocTypeIdOrNull(docTypeQuery);
		if (docTypeId == null)
		{
			throw new DocTypeNotFoundException(docTypeQuery);
		}

		final ProductId productId = ProductId.ofRepoId(deliveryPlanningRecord.getM_Product_ID());
		final I_C_UOM uomOfRecord = uomDAO.getByIdOrNull(deliveryPlanningRecord.getC_UOM_ID());
		final I_C_UOM uomToUse = uomOfRecord != null ? uomOfRecord : productBL.getStockUOM(productId);

		final BPartnerLocationId deliveryPlanningLocationId = BPartnerLocationId.ofRepoId(deliveryPlanningRecord.getC_BPartner_ID(), deliveryPlanningRecord.getC_BPartner_Location_ID());
		final boolean isIncoming = deliveryPlanningType.isIncoming();
		final BPartnerLocationId shipFrom = extractShipFromLocationId(deliveryPlanningRecord);
		final BPartnerLocationId shipTo = extractShipToLocationId(deliveryPlanningRecord);

		final Dimension deliveryPlanningDimension = dimensionService.getFromRecord(deliveryPlanningRecord);

		return DeliveryInstructionCreateRequest.builder()
				.orgId(orgId)
				.clientId(ClientId.ofRepoId(deliveryPlanningRecord.getAD_Client_ID()))

				.shipperBPartnerId(BPartnerId.ofRepoId(deliveryPlanningRecord.getC_BPartner_ID()))
				.shipperLocationId(deliveryPlanningLocationId)
				.incotermsId(IncotermsId.ofRepoIdOrNull(deliveryPlanningRecord.getC_Incoterms_ID()))
				.incotermLocation(deliveryPlanningRecord.getIncotermLocation())
				.meansOfTransportationId(MeansOfTransportationId.ofRepoIdOrNull(deliveryPlanningRecord.getM_MeansOfTransportation_ID()))
				.loadingPartnerLocationId(shipFrom)
				.loadingDate(TimeUtil.asInstant(deliveryPlanningRecord.getPlannedLoadingDate()))
				.loadingTime(deliveryPlanningRecord.getLoadingTime())
				.deliveryPartnerLocationId(shipTo)
				.deliveryDate(TimeUtil.asInstant(deliveryPlanningRecord.getPlannedDeliveryDate()))
				.deliveryTime(deliveryPlanningRecord.getDeliveryTime())

				.dateDoc(SystemTime.asInstant())
				.docTypeId(docTypeId)

				.shipperId(ShipperId.ofRepoId(deliveryPlanningRecord.getM_Shipper_ID()))

				.productId(productId)
				.isToBeFetched(isIncoming)
				//.locatorId() : Not yet decided where to take it from. TODO in a future CR
				.batchNo(deliveryPlanningRecord.getBatch())
				.qtyLoaded(Quantity.of(deliveryPlanningRecord.getPlannedLoadedQuantity(), uomToUse))
				.qtyDischarged(Quantity.of(deliveryPlanningRecord.getPlannedDischargeQuantity(), uomToUse))
				.uom(uomToUse)
				.orderLineId(OrderLineId.ofRepoIdOrNull(deliveryPlanningRecord.getC_OrderLine_ID()))
				.deliveryPlanningId(deliveryPlanningId)
				.dimension(deliveryPlanningDimension)
				.build();
	}

	private BPartnerLocationId extractShipFromLocationId(final I_M_Delivery_Planning deliveryPlanningRecord)
	{
		final DeliveryPlanningType deliveryPlanningType = DeliveryPlanningRepository.extractDeliveryPlanningType(deliveryPlanningRecord);

		final boolean isIncoming = deliveryPlanningType.isIncoming();

		if (isIncoming)
		{
			final I_M_ReceiptSchedule receiptSchedule = receiptScheduleDAO.getById(ReceiptScheduleId.ofRepoId(deliveryPlanningRecord.getM_ReceiptSchedule_ID()));

			return BPartnerLocationId.ofRepoId(receiptSchedule.getC_BPartner_ID(), receiptSchedule.getC_BPartner_Location_ID());
		}

		final WarehouseId warehouseId = WarehouseId.ofRepoId(deliveryPlanningRecord.getM_Warehouse_ID());
		return warehouseBL.getBPartnerLocationId(warehouseId);
	}

	private BPartnerLocationId extractShipToLocationId(final I_M_Delivery_Planning deliveryPlanningRecord)
	{
		final DeliveryPlanningType deliveryPlanningType = DeliveryPlanningRepository.extractDeliveryPlanningType(deliveryPlanningRecord);

		final boolean isOutgoing = deliveryPlanningType.isOutgoing();

		if (isOutgoing)
		{
			final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleBL.getById(ShipmentScheduleId.ofRepoId(deliveryPlanningRecord.getM_ShipmentSchedule_ID()));

			return BPartnerLocationId.ofRepoId(shipmentSchedule.getC_BPartner_ID(), shipmentSchedule.getC_BPartner_Location_ID());
		}

		final WarehouseId warehouseId = WarehouseId.ofRepoId(deliveryPlanningRecord.getM_Warehouse_ID());
		return warehouseBL.getBPartnerLocationId(warehouseId);
	}

	public void generateDeliveryInstructions(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		final ICompositeQueryFilter<I_M_Delivery_Planning> deliveryPlanningsSuitableForInstruction = deliveryPlanningRepository
				.excludeUnsuitableForInstruction(selectedDeliveryPlanningsFilter)
				.addNotInSubQueryFilter(I_M_Delivery_Planning.COLUMNNAME_C_BPartner_ID, I_C_BPartner_BlockStatus.COLUMNNAME_C_BPartner_ID, bPartnerBlockStatusService.getBlockedBPartnerQuery());

		final Iterator<I_M_Delivery_Planning> deliveryPlanningIterator = deliveryPlanningRepository.extractDeliveryPlannings(deliveryPlanningsSuitableForInstruction);
		while (deliveryPlanningIterator.hasNext())
		{
			final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningIterator.next();

			final DeliveryInstructionCreateRequest deliveryInstructionRequest = createDeliveryInstructionRequest(DeliveryPlanningId.ofRepoId(deliveryPlanningRecord.getM_Delivery_Planning_ID()));

			final DeliveryPlanningType deliveryPlanningType = DeliveryPlanningRepository.extractDeliveryPlanningType(deliveryPlanningRecord);

			final boolean creditLimitAllowsDeliveryInstruction = deliveryPlanningType.isIncoming() || validateCreditLimit(deliveryInstructionRequest);

			if (creditLimitAllowsDeliveryInstruction)
			{
				generateCompleteDeliveryInstruction(deliveryInstructionRequest);
			}
		}
	}

	private boolean validateCreditLimit(final DeliveryInstructionCreateRequest deliveryInstructionRequest)
	{
		if (!creditLimitAllowsDeliveryInstruction(deliveryInstructionRequest))
		{
			final BPartnerStats bPartnerStats = bPartnerStatsService.getCreateBPartnerStats(deliveryInstructionRequest.getShipperBPartnerId());

			final DeliveryInstructionUserNotificationsProducer deliveryInstructionUserNotificationsProducer = DeliveryInstructionUserNotificationsProducer.newInstance();

			final String partnerName = partnerBL.getBPartnerName(deliveryInstructionRequest.getShipperBPartnerId());
			final BigDecimal creditLimitDifference = bPartnerStatsService.getDeliveryOpenBalance(bPartnerStats, deliveryInstructionRequest.getDateDoc());

			final CurrencyId baseCurrencyId = moneyService.getBaseCurrencyId(ClientAndOrgId.ofClientAndOrg(deliveryInstructionRequest.getClientId(), deliveryInstructionRequest.getOrgId()));
			final String creditLimitDifferenceMessage = moneyService.toTranslatableString(Money.of(creditLimitDifference, baseCurrencyId)).getDefaultValue();
			deliveryInstructionUserNotificationsProducer.notifyDeliveryInstructionError(partnerName, creditLimitDifferenceMessage);
			return false;
		}

		return true;

	}

	public void unlinkDeliveryPlannings(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		deliveryPlanningRepository.unlinkDeliveryPlannings(deliveryInstructionId);
	}

	public void regenerateDeliveryInstructions(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		final ICompositeQueryFilter<I_M_Delivery_Planning> dpFilter = deliveryPlanningRepository
				.excludeDeliveryPlanningsWithoutInstruction(selectedDeliveryPlanningsFilter)
				.addNotInSubQueryFilter(I_M_Delivery_Planning.COLUMNNAME_C_BPartner_ID, I_C_BPartner_BlockStatus.COLUMNNAME_C_BPartner_ID, bPartnerBlockStatusService.getBlockedBPartnerQuery());

		final Iterator<I_M_Delivery_Planning> deliveryPlanningIterator = deliveryPlanningRepository.extractDeliveryPlannings(dpFilter);
		while (deliveryPlanningIterator.hasNext())
		{
			final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningIterator.next();

			// first void the existent delivery instructions
			final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(deliveryPlanningRecord.getM_Delivery_Planning_ID());
			voidLinkedDeliveryInstructions(deliveryPlanningId);

			// then generate a new one
			final DeliveryInstructionCreateRequest deliveryInstructionRequest = createDeliveryInstructionRequest(deliveryPlanningId);
			generateCompleteDeliveryInstruction(deliveryInstructionRequest);
		}
	}

	private void voidLinkedDeliveryInstructions(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final Iterator<I_M_ShipperTransportation> deliveryInstructionsIterator = deliveryPlanningRepository.retrieveForDeliveryPlanning(deliveryPlanningId);
		while (deliveryInstructionsIterator.hasNext())
		{
			final I_M_ShipperTransportation deliveryInstructionRecord = deliveryInstructionsIterator.next();

			docActionBL.processEx(deliveryInstructionRecord, IDocument.ACTION_Void, IDocument.STATUS_Voided);
		}
	}

	public void cancelDelivery(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		final ICompositeQueryFilter<I_M_Delivery_Planning> dpFilter = deliveryPlanningRepository
				.excludeDeliveryPlanningsWithoutInstruction(selectedDeliveryPlanningsFilter)
				.addNotInSubQueryFilter(I_M_Delivery_Planning.COLUMNNAME_C_BPartner_ID, I_C_BPartner_BlockStatus.COLUMNNAME_C_BPartner_ID, bPartnerBlockStatusService.getBlockedBPartnerQuery());

		final Iterator<I_M_Delivery_Planning> deliveryPlanningIterator = deliveryPlanningRepository.extractDeliveryPlannings(dpFilter);

		while (deliveryPlanningIterator.hasNext())
		{
			final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningIterator.next();

			// first void the existent delivery instructions
			final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(deliveryPlanningRecord.getM_Delivery_Planning_ID());
			voidLinkedDeliveryInstructions(deliveryPlanningId);

			// then cancel delivery planning
			deliveryPlanningRepository.cancelSelectedDeliveryPlannings(selectedDeliveryPlanningsFilter);
		}
	}

	public Optional<DeliveryPlanningReceiptInfo> getReceiptInfoIfIncomingType(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return deliveryPlanningRepository.getReceiptInfoIfIncomingType(deliveryPlanningId);
	}

	public DeliveryPlanningReceiptInfo getReceiptInfo(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return deliveryPlanningRepository.getReceiptInfoIfIncomingType(deliveryPlanningId)
				.orElseThrow(() -> new AdempiereException("Expected to be an incoming delivery planning"));
	}

	public void updateReceiptInfoById(
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			@NonNull final Consumer<DeliveryPlanningReceiptInfo> updater)
	{
		final DeliveryStatusColorPalette colorPalette = getColorPalette();
		deliveryPlanningRepository.updateReceiptInfoById(
				deliveryPlanningId,
				receiptInfo -> {
					updater.accept(receiptInfo);
					receiptInfo.updateReceivedStatusColor(colorPalette);
				});
	}

	public Optional<DeliveryPlanningShipmentInfo> getShipmentInfoIfOutgoingType(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return deliveryPlanningRepository.getShipmentInfoIfOutgoingType(deliveryPlanningId);
	}

	public DeliveryPlanningShipmentInfo getShipmentInfo(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return deliveryPlanningRepository.getShipmentInfoIfOutgoingType(deliveryPlanningId)
				.orElseThrow(() -> new AdempiereException("Expected to be an outgoing delivery planning"));
	}

	public void updateShipmentInfoById(
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			@NonNull final Consumer<DeliveryPlanningShipmentInfo> updater)
	{
		final DeliveryStatusColorPalette colorPalette = getColorPalette();
		deliveryPlanningRepository.updateShipmentInfoById(
				deliveryPlanningId,
				shipmentInfo -> {
					updater.accept(shipmentInfo);
					shipmentInfo.updateShippedStatusColor(colorPalette);
				});
	}

	public <T> T getShipmentOrReceiptInfo(
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			@NonNull final Function<DeliveryPlanningReceiptInfo, T> receiptInfoMapper,
			@NonNull final Function<DeliveryPlanningShipmentInfo, T> shipmentInfoMapper)
	{
		return deliveryPlanningRepository.getShipmentOrReceiptInfo(deliveryPlanningId, receiptInfoMapper, shipmentInfoMapper);
	}

	public List<DeliveryPlanningShipmentInfo> getShipmentInfosByOrderLineIds(final Set<OrderAndLineId> salesOrderLineIds)
	{
		return deliveryPlanningRepository.getShipmentInfosByOrderLineIds(salesOrderLineIds);
	}

	public boolean hasCompleteDeliveryInstruction(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return deliveryPlanningRepository.hasCompleteDeliveryInstruction(deliveryPlanningId);
	}

	public boolean isExistsBlockedPartnerDeliveryPlannings(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return deliveryPlanningRepository.getDeliveryPlanningQueryBuilder(selectedDeliveryPlanningsFilter)
				.addInSubQueryFilter(I_M_Delivery_Planning.COLUMNNAME_C_BPartner_ID, I_C_BPartner_BlockStatus.COLUMNNAME_C_BPartner_ID, bPartnerBlockStatusService.getBlockedBPartnerQuery())
				.create()
				.anyMatch();
	}

	public boolean hasBlockedBPartner(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningRepository.getById(deliveryPlanningId);

		return bPartnerBlockStatusService.isBPartnerBlocked(BPartnerId.ofRepoId(deliveryPlanningRecord.getC_BPartner_ID()));
	}

	public void validateDeliveryPlanning(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		if (hasBlockedBPartner(deliveryPlanningId))
		{
			throw new AdempiereException(MSG_M_Delivery_Planning_BlockedPartner);
		}
	}

	@NonNull
	public Optional<Timestamp> getMinActualLoadingDateFromPlannings(@NonNull final OrderLineId orderLineId, @NonNull final SOTrx soTrx)
	{
		return deliveryPlanningRepository.getMinActualLoadingDateFromPlannings(orderLineId, soTrx.isSales());
	}

	public void invalidateInvoiceCandidatesFor(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		Optional.ofNullable(OrderLineId.ofRepoIdOrNull(deliveryPlanning.getC_OrderLine_ID()))
				.map(orderLineBL::getOrderLineById)
				.ifPresent(invoiceCandidateHandlerBL::invalidateCandidatesFor);
	}

	public void invalidateInvoiceCandidatesFor(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		invalidateInvoiceCandidatesFor(deliveryPlanningRepository.getById(deliveryPlanningId));
	}

	private void validateDeliveryPlannings(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		if (isExistsBlockedPartnerDeliveryPlannings(selectedDeliveryPlanningsFilter))
		{
			throw new AdempiereException(MSG_M_Delivery_Planning_BlockedPartner);
		}
	}

	public Optional<MeansOfTransportation> getMeansOfTransportationByDeliveryPlanningId(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final I_M_Delivery_Planning deliveryPlanning = deliveryPlanningRepository.getById(deliveryPlanningId);
		return MeansOfTransportationId.optionalOfRepoId(deliveryPlanning.getM_MeansOfTransportation_ID())
				.map(meansOfTransportationService::getById);
	}
}
