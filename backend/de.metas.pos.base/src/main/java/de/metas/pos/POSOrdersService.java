package de.metas.pos;

import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.lang.SOTrx;
import de.metas.pos.POSOrderLine.POSOrderLineBuilder;
import de.metas.pos.POSPayment.POSPaymentBuilder;
import de.metas.pos.remote.RemotePOSOrder;
import de.metas.pos.remote.RemotePOSOrderLine;
import de.metas.pos.remote.RemotePOSPayment;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.TaxNotFoundException;
import de.metas.tax.api.TaxQuery;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class POSOrdersService
{
	@NonNull private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	@NonNull private final POSConfigService configService;
	@NonNull private final POSOrdersRepository ordersRepository;
	@NonNull private final CurrencyRepository currencyRepository;
	@NonNull private final POSOrderProcessingServices possOrderProcessingServices;

	public List<POSOrder> getOpenOrders(@NonNull final UserId userId)
	{
		return ordersRepository.getOpenOrders(userId);
	}

	public POSOrder changeStatusTo(@NonNull final POSOrderExternalId externalId, @NonNull final POSOrderStatus nextStatus, @NonNull final UserId userId)
	{
		return ordersRepository.updateByExternalId(externalId, order -> {
			assertCanEdit(order, userId);
			order.changeStatusTo(nextStatus, possOrderProcessingServices);
		});
	}

	private void assertCanEdit(@NonNull final POSOrder order, @NonNull final UserId userId)
	{
		if (!UserId.equals(order.getCashierId(), userId))
		{
			throw new AdempiereException("Edit not allowed");
		}
	}

	public POSOrder updateOrderFromRemote(final @NonNull RemotePOSOrder remoteOrder, final UserId userId)
	{
		return ordersRepository.createOrUpdateByExternalId(
				remoteOrder.getUuid(),
				externalId -> newPOSOrder(externalId, userId),
				order -> {
					assertCanEdit(order, userId);
					updateOrderFromRemote(order, remoteOrder);
				});
	}

	private POSOrder newPOSOrder(@NonNull final POSOrderExternalId externalId, @NonNull final UserId userId)
	{
		final POSConfig config = configService.getConfig();
		return POSOrder.builder()
				.externalId(externalId)
				.status(POSOrderStatus.Drafted)
				.salesOrderDocTypeId(config.getSalesOrderDocTypeId())
				.pricingSystemAndListId(config.getPricingSystemAndListId())
				.cashbookId(config.getCashbookId())
				.cashierId(userId)
				.date(SystemTime.asInstant())
				.shipToCustomerAndLocationId(config.getWalkInCustomerShipToLocationId())
				.shipFrom(config.getShipFrom())
				.isTaxIncluded(config.isTaxIncluded())
				.currencyId(config.getCurrencyId())
				.configId(config.getId())
				.build();
	}

	private void updateOrderFromRemote(final POSOrder order, final RemotePOSOrder remoteOrder)
	{
		if (!POSOrderExternalId.equals(order.getExternalId(), remoteOrder.getUuid()))
		{
			throw new AdempiereException("Expected externalIds to match");
		}

		//
		// Update order lines
		{
			final ArrayList<String> lineExternalIdsInOrder = new ArrayList<>();
			for (final RemotePOSOrderLine remoteOrderLine : remoteOrder.getLines())
			{
				createOrUpdateOrderLineFromRemote(order, remoteOrderLine);
				lineExternalIdsInOrder.add(remoteOrderLine.getUuid());
			}

			order.preserveOnlyLineExternalIdsInOrder(lineExternalIdsInOrder);
		}

		//
		// Update payments
		{
			final HashSet<String> paymentExternalIdsToKeep = new HashSet<>();
			final List<RemotePOSPayment> remotePayments = remoteOrder.getPayments();
			if (!remotePayments.isEmpty())
			{
				for (final RemotePOSPayment remotePayment : remotePayments)
				{
					createOrUpdatePaymentFromRemote(order, remotePayment);
					paymentExternalIdsToKeep.add(remotePayment.getUuid());
				}
			}

			order.preserveOnlyPaymentExternalIds(paymentExternalIdsToKeep);
		}

	}

	private void createOrUpdateOrderLineFromRemote(
			@NonNull final POSOrder order,
			@NonNull final RemotePOSOrderLine remoteOrderLine)
	{
		order.createOrUpdateLine(remoteOrderLine.getUuid(), existingLine -> {
			//
			// TaxId
			final TaxCategoryId taxCategoryId = remoteOrderLine.getTaxCategoryId();
			final TaxId taxId;
			Tax tax = null; // lazy loaded
			if (existingLine != null)
			{
				taxId = existingLine.getTaxId();
			}
			else
			{
				tax = findTax(order, taxCategoryId);
				taxId = tax.getTaxId();
			}

			//
			// Amount, TaxAmt
			final Quantity qty = extractQty(remoteOrderLine);
			final BigDecimal price = remoteOrderLine.getPrice();
			final CurrencyPrecision currencyPrecision = currencyRepository.getStdPrecision(order.getCurrencyId());
			final BigDecimal amount = currencyPrecision.round(qty.toBigDecimal().multiply(price));
			final BigDecimal amountPrev = existingLine != null ? existingLine.getAmount() : BigDecimal.ZERO;
			final BigDecimal taxAmt;
			if (existingLine == null || amount.compareTo(amountPrev) != 0)
			{
				tax = tax != null ? tax : taxDAO.getTaxById(taxId); // load tax if needed
				taxAmt = tax.calculateTax(amount, order.isTaxIncluded(), currencyPrecision.toInt());
			}
			else
			{
				taxAmt = existingLine.getTaxAmt();
			}

			final POSOrderLineBuilder builder = existingLine != null
					? existingLine.toBuilder()
					: POSOrderLine.builder();

			return builder.externalId(remoteOrderLine.getUuid())
					.productId(remoteOrderLine.getProductId())
					.productName(remoteOrderLine.getProductName())
					.taxCategoryId(taxCategoryId)
					.taxId(taxId)
					.qty(qty)
					.price(price)
					.amount(amount)
					.taxAmt(taxAmt)
					.build();
		});
	}

	private Quantity extractQty(@NonNull final RemotePOSOrderLine remoteOrderLine)
	{
		return Quantitys.create(remoteOrderLine.getQty(), remoteOrderLine.getUomId());
	}

	private Tax findTax(final POSOrder order, final TaxCategoryId taxCategoryId)
	{
		final TaxQuery taxQuery = TaxQuery.builder()
				.fromCountryId(order.getShipFrom().getCountryId())
				.orgId(order.getShipFrom().getOrgId())
				.bPartnerLocationId(order.getShipToCustomerAndLocationId())
				.dateOfInterest(Timestamp.from(order.getDate()))
				.taxCategoryId(taxCategoryId)
				.soTrx(SOTrx.SALES)
				.build();

		final Tax tax = taxDAO.getByIfPresent(taxQuery).orElseThrow(() -> TaxNotFoundException.ofQuery(taxQuery));
		if (tax.isDocumentLevel())
		{
			throw new AdempiereException("POS tax shall be all line level")
					.setParameter("tax", tax);
		}
		return tax;
	}

	//
	//
	//

	private void createOrUpdatePaymentFromRemote(final POSOrder order, final RemotePOSPayment remotePayment)
	{
		order.createOrUpdatePayment(remotePayment.getUuid(), existingPayment -> {
			final POSPaymentBuilder builder = existingPayment != null
					? existingPayment.toBuilder()
					: POSPayment.builder();

			return builder.externalId(remotePayment.getUuid())
					.paymentMethod(remotePayment.getPaymentMethod())
					.amount(remotePayment.getAmount())
					.build();
		});
	}

}
