package de.metas.pos;

import de.metas.currency.CurrencyPrecision;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
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
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Builder
class POSOrderUpdateFromRemoteCommand
{
	@NonNull private final ITaxDAO taxDAO;

	@NonNull private final POSOrder order;
	@NonNull private final RemotePOSOrder remoteOrder;
	@NonNull private final CurrencyPrecision currencyPrecision;

	public void execute()
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
				createOrUpdateOrderLineFromRemote(remoteOrderLine);
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
					createOrUpdatePaymentFromRemote(remotePayment);
					paymentExternalIdsToKeep.add(remotePayment.getUuid());
				}
			}

			order.preserveOnlyPaymentExternalIds(paymentExternalIdsToKeep);
		}

	}

	private void createOrUpdateOrderLineFromRemote(@NonNull final RemotePOSOrderLine remoteOrderLine)
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
			final Money price = toMoney(remoteOrderLine.getPrice());
			final Money amount = price.multiply(qty.toBigDecimal()).round(currencyPrecision);
			final Money amountPrev = existingLine != null ? existingLine.getAmount() : toMoney(BigDecimal.ZERO);
			final Money taxAmt;
			if (existingLine == null || amount.compareTo(amountPrev) != 0)
			{
				tax = tax != null ? tax : taxDAO.getTaxById(taxId); // load tax if needed
				BigDecimal taxAmtBD = tax.calculateTax(amount.toBigDecimal(), order.isTaxIncluded(), currencyPrecision.toInt());
				taxAmt = toMoney(taxAmtBD);
			}
			else
			{
				taxAmt = existingLine.getTaxAmt();
			}

			final POSOrderLine.POSOrderLineBuilder builder = existingLine != null
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

	private void createOrUpdatePaymentFromRemote(final RemotePOSPayment remotePayment)
	{
		order.createOrUpdatePayment(remotePayment.getUuid(), existingPayment -> {
			final POSPayment.POSPaymentBuilder builder = existingPayment != null
					? existingPayment.toBuilder()
					: POSPayment.builder();

			final BigDecimal amount = currencyPrecision.round(remotePayment.getAmount());

			return builder.externalId(remotePayment.getUuid())
					.paymentMethod(remotePayment.getPaymentMethod())
					.amount(toMoney(amount))
					.build();
		});
	}

	private Money toMoney(final BigDecimal amount)
	{
		return Money.of(amount, order.getCurrencyId());
	}
}
