package de.metas.pos.rest_api;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.common.util.time.SystemTime;
import de.metas.pos.POSProductsList;
import de.metas.pos.POSService;
import de.metas.pos.rest_api.json.JsonPOSConfig;
import de.metas.pos.rest_api.json.JsonPOSOrder;
import de.metas.pos.rest_api.json.JsonPOSOrderUpdateRequest;
import de.metas.pos.rest_api.json.JsonPOSOrdersList;
import de.metas.pos.rest_api.json.JsonProductsList;
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

	@GetMapping("/config")
	public JsonPOSConfig getConfiguration()
	{
		return JsonPOSConfig.from(posService.getConfig());
	}

	@GetMapping("/orders")
	public JsonPOSOrdersList getOpenOrders()
	{
		return JsonPOSOrdersList.builder()
				.list(ImmutableList.of())
				// TODO
				.build();
	}

	@GetMapping("/orders/{orderId}")
	public JsonPOSOrder getOrder(
			@PathVariable("orderId") @NonNull final String orderIdStr
	)
	{
		throw new UnsupportedOperationException(); // TODO
	}

	@PostMapping("/orders")
	public JsonPOSOrder updateOrder(@RequestBody @NonNull final JsonPOSOrderUpdateRequest request)
	{
		throw new UnsupportedOperationException(); // TODO
	}

	@GetMapping("/products")
	public JsonProductsList getProducts()
	{
		final Instant date = SystemTime.asInstant();
		final String adLanguage = Env.getADLanguageOrBaseLanguage();

		final POSProductsList products = posService.getProducts(date);
		return JsonProductsList.from(products, adLanguage);
	}
}
