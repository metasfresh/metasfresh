package de.metas.pos;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.document.DocTypeId;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.pos.repository.model.I_C_POS_Order;
import de.metas.pos.repository.model.I_C_POS_OrderLine;
import de.metas.pos.repository.model.I_C_POS_Payment;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Builder
class POSOrdersLoaderAndSaver
{
	@NonNull private final IQueryBL queryBL;

	private final HashMap<POSOrderId, I_C_POS_Order> orderRecordsById = new HashMap<>();
	private final HashMap<POSOrderExternalId, Optional<I_C_POS_Order>> orderRecordsByExternalId = new HashMap<>();
	private final HashMap<POSOrderId, ImmutableList<I_C_POS_OrderLine>> lineRecords = new HashMap<>();
	private final HashMap<POSOrderId, ImmutableList<I_C_POS_Payment>> paymentRecords = new HashMap<>();

	public List<POSOrder> loadFromRecords(@NonNull final List<I_C_POS_Order> orderRecords)
	{
		addToCacheAndWarmUp(orderRecords);
		return orderRecords.stream().map(this::fromRecord).collect(ImmutableList.toImmutableList());
	}

	public POSOrder getById(final @NonNull POSOrderId posOrderId)
	{
		return fromRecord(getOrderRecordById(posOrderId));
	}

	public POSOrder createOrUpdateByExternalId(
			@NonNull final POSOrderExternalId externalId,
			@NonNull final Function<POSOrderExternalId, POSOrder> factory,
			@NonNull final Consumer<POSOrder> updater)
	{
		final I_C_POS_Order orderRecord = getOrderRecordByExternalId(externalId).orElse(null);
		final POSOrder order;
		if (orderRecord == null)
		{
			order = factory.apply(externalId);
		}
		else
		{
			order = fromRecord(orderRecord);
		}

		updater.accept(order);

		save(order);

		return order;
	}

	private void addToCacheAndWarmUp(@NonNull Collection<I_C_POS_Order> orderRecords)
	{
		if (orderRecords.isEmpty())
		{
			return;
		}

		orderRecords.forEach(this::addToCache);

		final ImmutableSet<POSOrderId> posOrderIds = orderRecords.stream().map(POSOrdersLoaderAndSaver::extractPOSOrderId).collect(ImmutableSet.toImmutableSet());
		CollectionUtils.getAllOrLoad(lineRecords, posOrderIds, this::retrieveLineRecordsByOrderIds);
		CollectionUtils.getAllOrLoad(paymentRecords, posOrderIds, this::retrievePaymentRecordsByOrderIds);
	}

	private void addToCache(@NonNull final I_C_POS_Order orderRecord)
	{
		orderRecordsById.put(extractPOSOrderId(orderRecord), orderRecord);
		orderRecordsByExternalId.put(extractExternalId(orderRecord), Optional.of(orderRecord));
	}

	private void putLineRecordsToCache(final POSOrderId posOrderId, List<I_C_POS_OrderLine> lines)
	{
		lineRecords.put(posOrderId, ImmutableList.copyOf(lines));
	}

	private void putPaymentRecordsToCache(final POSOrderId posOrderId, List<I_C_POS_Payment> payments)
	{
		paymentRecords.put(posOrderId, ImmutableList.copyOf(payments));
	}

	private I_C_POS_Order getOrderRecordById(@NonNull final POSOrderId posOrderId)
	{
		I_C_POS_Order orderRecord = orderRecordsById.get(posOrderId);
		if (orderRecord == null)
		{
			orderRecord = retrieveOrderRecordById(posOrderId);
			addToCache(orderRecord);
		}
		return orderRecord;
	}

	private I_C_POS_Order retrieveOrderRecordById(@NonNull final POSOrderId posOrderId)
	{
		final I_C_POS_Order orderRecord = InterfaceWrapperHelper.load(posOrderId, I_C_POS_Order.class);
		if (orderRecord == null)
		{
			throw new AdempiereException("No POSOrder found for " + posOrderId);
		}
		return orderRecord;
	}

	private Optional<I_C_POS_Order> getOrderRecordByExternalId(@NonNull final POSOrderExternalId externalId)
	{
		Optional<I_C_POS_Order> orderRecord = orderRecordsByExternalId.get(externalId);
		if (orderRecord == null)
		{
			orderRecord = retrieveOrderRecordByExternalId(externalId);
			if (orderRecord.isPresent())
			{
				addToCache(orderRecord.get());
			}
			else
			{
				orderRecordsByExternalId.put(externalId, Optional.empty());
			}
		}
		return orderRecord;
	}

	private Optional<I_C_POS_Order> retrieveOrderRecordByExternalId(@NonNull final POSOrderExternalId externalId)
	{
		return queryBL.createQueryBuilder(I_C_POS_Order.class)
				.addEqualsFilter(I_C_POS_Order.COLUMNNAME_ExternalId, externalId.getAsString())
				.create()
				.firstOnlyOptional(I_C_POS_Order.class);
	}

	private List<I_C_POS_OrderLine> getLineRecordsByOrderId(@NonNull final POSOrderId posOrderId)
	{
		return lineRecords.computeIfAbsent(posOrderId, this::retrieveLineRecordsByOrderId);
	}

	private ImmutableList<I_C_POS_OrderLine> retrieveLineRecordsByOrderId(@NonNull final POSOrderId posOrderId)
	{
		return retrieveLineRecordsByOrderIds(ImmutableSet.of(posOrderId)).get(posOrderId);
	}

	private Map<POSOrderId, ImmutableList<I_C_POS_OrderLine>> retrieveLineRecordsByOrderIds(@NonNull final Set<POSOrderId> posOrderIds)
	{
		if (posOrderIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Map<POSOrderId, ImmutableList<I_C_POS_OrderLine>> recordsByOrderId = queryBL.createQueryBuilder(I_C_POS_OrderLine.class)
				.addInArrayFilter(I_C_POS_OrderLine.COLUMNNAME_C_POS_Order_ID, posOrderIds)
				.orderBy(I_C_POS_OrderLine.COLUMNNAME_C_POS_Order_ID)
				.orderBy(I_C_POS_OrderLine.COLUMNNAME_C_POS_OrderLine_ID)
				.create()
				.stream()
				.collect(Collectors.groupingBy(
						lineRecord -> POSOrderId.ofRepoId(lineRecord.getC_POS_Order_ID()),
						ImmutableList.toImmutableList()
				));

		final HashMap<POSOrderId, ImmutableList<I_C_POS_OrderLine>> result = new HashMap<>();
		for (final POSOrderId orderId : posOrderIds)
		{
			final ImmutableList<I_C_POS_OrderLine> records = recordsByOrderId.get(orderId);
			result.put(orderId, records != null ? records : ImmutableList.of());
		}

		return result;
	}

	private ImmutableList<I_C_POS_Payment> getPaymentRecordsByOrderId(@NonNull final POSOrderId posOrderId)
	{
		return paymentRecords.computeIfAbsent(posOrderId, this::retrievePaymentRecordsByOrderId);
	}

	private ImmutableList<I_C_POS_Payment> retrievePaymentRecordsByOrderId(@NonNull final POSOrderId posOrderId)
	{
		return retrievePaymentRecordsByOrderIds(ImmutableSet.of(posOrderId)).get(posOrderId);
	}

	private Map<POSOrderId, ImmutableList<I_C_POS_Payment>> retrievePaymentRecordsByOrderIds(@NonNull final Set<POSOrderId> posOrderIds)
	{
		if (posOrderIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Map<POSOrderId, ImmutableList<I_C_POS_Payment>> recordsByOrderId = queryBL.createQueryBuilder(I_C_POS_Payment.class)
				.addInArrayFilter(I_C_POS_Payment.COLUMNNAME_C_POS_Order_ID, posOrderIds)
				.orderBy(I_C_POS_Payment.COLUMNNAME_C_POS_Payment_ID)
				.create()
				.stream()
				.collect(Collectors.groupingBy(
						lineRecord -> POSOrderId.ofRepoId(lineRecord.getC_POS_Order_ID()),
						ImmutableList.toImmutableList()
				));

		final HashMap<POSOrderId, ImmutableList<I_C_POS_Payment>> result = new HashMap<>();
		for (final POSOrderId orderId : posOrderIds)
		{
			final ImmutableList<I_C_POS_Payment> records = recordsByOrderId.get(orderId);
			result.put(orderId, records != null ? records : ImmutableList.of());
		}

		return result;

	}

	private POSOrder fromRecord(final I_C_POS_Order orderRecord)
	{
		final POSOrderId posOrderId = extractPOSOrderId(orderRecord);
		final List<I_C_POS_OrderLine> lineRecords = getLineRecordsByOrderId(posOrderId);
		final List<I_C_POS_Payment> paymentRecords = getPaymentRecordsByOrderId(posOrderId);

		return fromRecord(orderRecord, lineRecords, paymentRecords);
	}

	private static POSOrderId extractPOSOrderId(final I_C_POS_Order orderRecord)
	{
		return POSOrderId.ofRepoId(orderRecord.getC_POS_Order_ID());
	}

	private static POSOrderExternalId extractExternalId(final I_C_POS_Order orderRecord)
	{
		return POSOrderExternalId.ofString(orderRecord.getExternalId());
	}

	private static POSOrder fromRecord(
			@NonNull final I_C_POS_Order orderRecord,
			@NonNull final List<I_C_POS_OrderLine> lineRecords,
			@NonNull final List<I_C_POS_Payment> paymentRecords)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(orderRecord.getC_Currency_ID());

		return POSOrder.builder()
				.externalId(extractExternalId(orderRecord))
				.localId(extractPOSOrderId(orderRecord))
				.status(POSOrderStatus.ofCode(orderRecord.getStatus()))
				.salesOrderDocTypeId(DocTypeId.ofRepoId(orderRecord.getC_DocTypeOrder_ID()))
				.pricingSystemAndListId(PricingSystemAndListId.ofRepoIds(orderRecord.getM_PricingSystem_ID(), orderRecord.getM_PriceList_ID()))
				.cashbookId(BankAccountId.ofRepoId(orderRecord.getC_BP_BankAccount_ID()))
				.cashierId(UserId.ofRepoId(orderRecord.getCashier_ID()))
				.date(orderRecord.getDateTrx().toInstant())
				.shipToCustomerAndLocationId(BPartnerLocationAndCaptureId.ofRepoId(orderRecord.getC_BPartner_ID(), orderRecord.getC_BPartner_Location_ID(), orderRecord.getC_BPartner_Location_Value_ID()))
				.shipFrom(POSShipFrom.builder()
						.warehouseId(WarehouseId.ofRepoId(orderRecord.getM_Warehouse_ID()))
						.orgId(OrgId.ofRepoId(orderRecord.getAD_Org_ID()))
						.countryId(CountryId.ofRepoId(orderRecord.getC_Country_ID()))
						.build())
				.isTaxIncluded(orderRecord.isTaxIncluded())
				.currencyId(currencyId)
				.lines(lineRecords.stream().map(lineRecord -> fromRecord(lineRecord, currencyId)).collect(ImmutableList.toImmutableList()))
				.payments(paymentRecords.stream().map(paymentRecord -> fromRecord(paymentRecord, currencyId)).collect(ImmutableList.toImmutableList()))
				.posTerminalId(POSTerminalId.ofRepoId(orderRecord.getC_POS_ID()))
				.build();
	}

	private static void updateRecord(@NonNull final I_C_POS_Order orderRecord, @NonNull final POSOrder from)
	{
		orderRecord.setExternalId(from.getExternalId().getAsString());
		orderRecord.setStatus(from.getStatus().getCode());
		orderRecord.setC_DocTypeOrder_ID(from.getSalesOrderDocTypeId().getRepoId());
		orderRecord.setM_PricingSystem_ID(from.getPricingSystemAndListId().getPricingSystemId().getRepoId());
		orderRecord.setM_PriceList_ID(from.getPricingSystemAndListId().getPriceListId().getRepoId());
		orderRecord.setC_BP_BankAccount_ID(from.getCashbookId().getRepoId());
		orderRecord.setCashier_ID(from.getCashierId().getRepoId());
		orderRecord.setDateTrx(Timestamp.from(from.getDate()));
		orderRecord.setC_BPartner_ID(from.getShipToCustomerAndLocationId().getBpartnerRepoId());
		orderRecord.setC_BPartner_Location_ID(from.getShipToCustomerAndLocationId().getBPartnerLocationRepoId());
		orderRecord.setC_BPartner_Location_Value_ID(from.getShipToCustomerAndLocationId().getLocationCaptureRepoId());
		orderRecord.setM_Warehouse_ID(from.getShipFrom().getWarehouseId().getRepoId());
		orderRecord.setAD_Org_ID(from.getShipFrom().getOrgId().getRepoId());
		orderRecord.setC_Country_ID(from.getShipFrom().getCountryId().getRepoId());
		orderRecord.setIsTaxIncluded(from.isTaxIncluded());
		orderRecord.setC_Currency_ID(from.getCurrencyId().getRepoId());
		orderRecord.setGrandTotal(from.getTotalAmt().toBigDecimal());
		orderRecord.setTaxAmt(from.getTaxAmt().toBigDecimal());
		orderRecord.setPaidAmt(from.getPaidAmt().toBigDecimal());
		orderRecord.setOpenAmt(from.getOpenAmt().toBigDecimal());
		orderRecord.setC_POS_ID(from.getPosTerminalId().getRepoId());
	}

	private static POSOrderLine fromRecord(final I_C_POS_OrderLine record, final CurrencyId currencyId)
	{
		return POSOrderLine.builder()
				.externalId(record.getExternalId())
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.productName(record.getProductName())
				.taxCategoryId(TaxCategoryId.ofRepoId(record.getC_TaxCategory_ID()))
				.taxId(TaxId.ofRepoId(record.getC_Tax_ID()))
				.qty(Quantitys.of(record.getQty(), UomId.ofRepoId(record.getC_UOM_ID())))
				.catchWeight(extractCatchWeight(record))
				.price(Money.of(record.getPrice(), currencyId))
				.amount(Money.of(record.getAmount(), currencyId))
				.taxAmt(Money.of(record.getTaxAmt(), currencyId))
				.build();
	}

	@Nullable
	private static Quantity extractCatchWeight(final I_C_POS_OrderLine record)
	{
		final UomId catchWeightUomId = UomId.ofRepoIdOrNull(record.getCatch_UOM_ID());
		return catchWeightUomId != null ? Quantitys.of(record.getCatchWeight(), catchWeightUomId) : null;
	}

	private static void updateRecord(@NonNull final I_C_POS_OrderLine lineRecord, @NonNull final POSOrderLine line)
	{
		lineRecord.setExternalId(line.getExternalId());
		lineRecord.setM_Product_ID(line.getProductId().getRepoId());
		lineRecord.setProductName(line.getProductName());
		lineRecord.setC_TaxCategory_ID(line.getTaxCategoryId().getRepoId());
		lineRecord.setC_Tax_ID(line.getTaxId().getRepoId());
		lineRecord.setQty(line.getQty().toBigDecimal());
		lineRecord.setC_UOM_ID(line.getQty().getUomId().getRepoId());
		lineRecord.setCatch_UOM_ID(line.getCatchWeight() != null ? line.getCatchWeight().getUomId().getRepoId() : -1);
		lineRecord.setCatchWeight(line.getCatchWeight() != null ? line.getCatchWeight().toBigDecimal() : null);
		lineRecord.setPrice(line.getPrice().toBigDecimal());
		lineRecord.setAmount(line.getAmount().toBigDecimal());
		lineRecord.setTaxAmt(line.getTaxAmt().toBigDecimal());
	}

	private static POSPayment fromRecord(final I_C_POS_Payment record, final CurrencyId currencyId)
	{
		return POSPayment.builder()
				.externalId(record.getExternalId())
				.localId(POSPaymentId.ofRepoId(record.getC_POS_Payment_ID()))
				.paymentMethod(POSPaymentMethod.ofCode(record.getPOSPaymentMethod()))
				.amount(Money.of(record.getAmount(), currencyId))
				.paymentReceiptId(PaymentId.ofRepoIdOrNull(record.getC_Payment_ID()))
				.build();
	}

	private static void updateRecord(final I_C_POS_Payment paymentRecord, final POSPayment payment)
	{
		paymentRecord.setExternalId(payment.getExternalId());
		paymentRecord.setPOSPaymentMethod(payment.getPaymentMethod().getCode());
		paymentRecord.setAmount(payment.getAmount().toBigDecimal());
		paymentRecord.setC_Payment_ID(PaymentId.toRepoId(payment.getPaymentReceiptId()));
	}

	public void save(@NonNull final POSOrder order)
	{
		final I_C_POS_Order orderRecord;
		if (order.getLocalId() != null)
		{
			orderRecord = getOrderRecordById(order.getLocalId());
		}
		else
		{
			orderRecord = getOrderRecordByExternalId(order.getExternalId())
					.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_C_POS_Order.class));
		}

		updateRecord(orderRecord, order);
		InterfaceWrapperHelper.save(orderRecord);
		final POSOrderId posOrderId = extractPOSOrderId(orderRecord);
		order.setLocalId(posOrderId);
		addToCache(orderRecord);

		//
		// Lines
		{
			final HashMap<String, I_C_POS_OrderLine> lineRecordsByExternalId = getLineRecordsByOrderId(posOrderId).stream().collect(GuavaCollectors.toHashMapByKey(I_C_POS_OrderLine::getExternalId));
			final ArrayList<I_C_POS_OrderLine> lineRecordsNew = new ArrayList<>(lineRecordsByExternalId.size());
			for (final POSOrderLine line : order.getLines())
			{
				I_C_POS_OrderLine lineRecord = lineRecordsByExternalId.remove(line.getExternalId());
				if (lineRecord == null)
				{
					lineRecord = InterfaceWrapperHelper.newInstance(I_C_POS_OrderLine.class);
				}

				lineRecord.setC_POS_Order_ID(posOrderId.getRepoId());
				lineRecord.setAD_Org_ID(order.getOrgId().getRepoId());
				updateRecord(lineRecord, line);
				InterfaceWrapperHelper.save(lineRecord);
				lineRecordsNew.add(lineRecord);
			}

			InterfaceWrapperHelper.deleteAll(lineRecordsByExternalId.values());

			putLineRecordsToCache(posOrderId, lineRecordsNew);
		}

		//
		// Payments
		{
			final HashMap<String, I_C_POS_Payment> paymentRecordsByExternalId = getPaymentRecordsByOrderId(posOrderId).stream().collect(GuavaCollectors.toHashMapByKey(I_C_POS_Payment::getExternalId));
			final ArrayList<I_C_POS_Payment> paymentRecordsNew = new ArrayList<>(paymentRecordsByExternalId.size());
			for (final POSPayment payment : order.getPayments())
			{
				I_C_POS_Payment paymentRecord = paymentRecordsByExternalId.remove(payment.getExternalId());
				if (paymentRecord == null)
				{
					paymentRecord = InterfaceWrapperHelper.newInstance(I_C_POS_Payment.class);
				}

				paymentRecord.setC_POS_Order_ID(posOrderId.getRepoId());
				paymentRecord.setAD_Org_ID(order.getOrgId().getRepoId());
				updateRecord(paymentRecord, payment);
				InterfaceWrapperHelper.save(paymentRecord);
				paymentRecordsNew.add(paymentRecord);

				payment.setLocalId(POSPaymentId.ofRepoId(paymentRecord.getC_POS_Payment_ID()));
			}

			InterfaceWrapperHelper.deleteAll(paymentRecordsByExternalId.values());

			putPaymentRecordsToCache(posOrderId, paymentRecordsNew);
		}

	}
}
