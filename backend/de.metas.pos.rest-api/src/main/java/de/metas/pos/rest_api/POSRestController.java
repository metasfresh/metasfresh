package de.metas.pos.rest_api;

import de.metas.Profiles;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyRepository;
import de.metas.pos.POSOrder;
import de.metas.pos.POSOrderExternalId;
import de.metas.pos.POSProductsList;
import de.metas.pos.POSService;
import de.metas.pos.rest_api.json.JsonContext;
import de.metas.pos.rest_api.json.JsonPOSConfig;
import de.metas.pos.rest_api.json.JsonPOSOrder;
import de.metas.pos.rest_api.json.JsonPOSOrdersList;
import de.metas.pos.rest_api.json.JsonProductsList;
import de.metas.user.UserId;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/pos")
@RestController
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class POSRestController
{
	@NonNull private final POSService posService;
	@NonNull private final CurrencyRepository currencyRepository;

	private String getADLanguage() {return Env.getADLanguageOrBaseLanguage();}

	private UserId getLoggedUserId() {return Env.getLoggedUserId();}

	private JsonContext newJsonContext()
	{
		return JsonContext.builder()
				.currencyRepository(currencyRepository)
				.adLanguage(getADLanguage())
				.build();
	}

	@GetMapping("/config")
	public JsonPOSConfig getConfiguration()
	{
		final String adLanguage = getADLanguage();

		return JsonPOSConfig.from(posService.getConfig(), adLanguage);
	}

	@GetMapping("/products")
	public JsonProductsList getProducts()
	{
		final Instant date = SystemTime.asInstant();
		final String adLanguage = getADLanguage();

		final POSProductsList products = posService.getProducts(date);
		return JsonProductsList.from(products, adLanguage);
	}

	@GetMapping("/orders")
	public JsonPOSOrdersList getOpenOrders()
	{
		final UserId loggedUserId = getLoggedUserId();

		return JsonPOSOrdersList.of(posService.getOpenOrders(loggedUserId), newJsonContext());
	}

	@PostMapping("/orders/{orderId}/void")
	public void voidOrder(
			@PathVariable("orderId") @NonNull final String orderIdStr
	)
	{
		final POSOrderExternalId externalId = POSOrderExternalId.ofString(orderIdStr);
		final UserId loggedUserId = getLoggedUserId();
		posService.voidOrder(externalId, loggedUserId);
	}

	@PostMapping("/orders")
	public JsonPOSOrder updateOrder(@RequestBody @NonNull final JsonPOSOrder remoteOrder)
	{
		final UserId loggedUserId = getLoggedUserId();
		final POSOrder order = posService.updateOrderFromRemote(remoteOrder, loggedUserId);
		return JsonPOSOrder.of(order, newJsonContext());
	}
}
