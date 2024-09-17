package de.metas.pos;

import de.metas.pos.POSOrderLine.POSOrderLineBuilder;
import de.metas.pos.rest_api.json.JsonPOSOrder;
import de.metas.pos.rest_api.json.JsonPOSOrderLine;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.user.UserId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class POSOrdersService
{
	@NonNull private final POSConfigService configService;
	@NonNull private final POSOrdersRepository ordersRepository;

	public List<POSOrder> getOpenOrders(@NonNull final UserId userId)
	{
		return ordersRepository.getOpenOrders(userId);
	}

	public void voidOrder(@NonNull final POSOrderExternalId externalId, @NonNull final UserId userId)
	{
		ordersRepository.updateByExternalId(externalId, order -> {
			assertCanEdit(order, userId);
			order.voidId();
		});
	}

	private void assertCanEdit(@NonNull final POSOrder order, @NonNull final UserId userId)
	{
		order.assertDrafted();

		if (!UserId.equals(order.getCashierId(), userId))
		{
			throw new AdempiereException("Edit not allowed");
		}
	}

	public POSOrder updateOrderFromRemote(final @NonNull JsonPOSOrder remoteOrder, final UserId userId)
	{
		return ordersRepository.createOrUpdateByExternalId(
				remoteOrder.getUuid(),
				externalId -> {
					final POSConfig config = configService.getConfig();
					return POSOrder.builder()
							.externalId(externalId)
							.status(POSOrderStatus.Drafted)
							.cashierId(userId)
							.currencyId(config.getCurrencyId())
							.build();
				},
				order -> {
					assertCanEdit(order, userId);
					updateOrderFromRemote(order, remoteOrder);
				});
	}

	private void updateOrderFromRemote(final POSOrder order, final JsonPOSOrder remoteOrder)
	{
		if (!POSOrderExternalId.equals(order.getExternalId(), remoteOrder.getUuid()))
		{
			throw new AdempiereException("Expected externalIds to match");
		}
		if (remoteOrder.getStatus() != null && !POSOrderStatus.equals(order.getStatus(), remoteOrder.getStatus()))
		{
			throw new AdempiereException("Changing status is not allowed");
		}

		final ArrayList<String> lineExternalIdsInOrder = new ArrayList<>();
		for (final JsonPOSOrderLine remoteOrderLine : remoteOrder.getLines())
		{
			createOrUpdateOrderLineFromRemote(order, remoteOrderLine);
			lineExternalIdsInOrder.add(remoteOrderLine.getUuid());
		}

		order.preserveOnlyLineExternalIdsInOrder(lineExternalIdsInOrder);
	}

	private void createOrUpdateOrderLineFromRemote(
			@NonNull final POSOrder order,
			@NonNull final JsonPOSOrderLine remoteOrderLine)
	{
		order.createOrUpdateLine(remoteOrderLine.getUuid(), existingLine -> {
			final POSOrderLineBuilder builder = existingLine != null
					? existingLine.toBuilder()
					: POSOrderLine.builder();

			return builder.externalId(remoteOrderLine.getUuid())
					.productId(remoteOrderLine.getProductId())
					.productName(remoteOrderLine.getProductName())
					.qty(extractQty(remoteOrderLine))
					.price(remoteOrderLine.getPrice())
					.build();

		});
	}

	private Quantity extractQty(@NonNull final JsonPOSOrderLine remoteOrderLine)
	{
		return Quantitys.create(remoteOrderLine.getQty(), remoteOrderLine.getUomId());
	}
}
