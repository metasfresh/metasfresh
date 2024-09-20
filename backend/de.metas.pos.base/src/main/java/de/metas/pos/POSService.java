package de.metas.pos;

import de.metas.pos.remote.RemotePOSOrder;
import de.metas.user.UserId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class POSService
{
	@NonNull private final POSConfigService configService;
	@NonNull private final POSProductsService productsService;
	@NonNull private final POSOrdersService ordersService;

	@NonNull
	public POSConfig getConfig()
	{
		return configService.getConfig();
	}

	public POSProductsList getProducts(@NonNull final Instant evalDate)
	{
		return productsService.getProducts(evalDate);
	}

	public List<POSOrder> getOpenOrders(@NonNull final UserId userId)
	{
		return ordersService.getOpenOrders(userId);
	}

	public POSOrder changeStatusTo(@NonNull final POSOrderExternalId externalId, @NonNull final POSOrderStatus nextStatus, @NonNull final UserId userId)
	{
		return ordersService.changeStatusTo(externalId, nextStatus, userId);
	}

	public POSOrder updateOrderFromRemote(final @NonNull RemotePOSOrder remoteOrder, final UserId userId)
	{
		return ordersService.updateOrderFromRemote(remoteOrder, userId);
	}
}

