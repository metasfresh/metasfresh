package de.metas.pos.rest_api;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.metas.Profiles;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyRepository;
import de.metas.pos.POSCashJournal;
import de.metas.pos.POSOrder;
import de.metas.pos.POSOrderExternalId;
import de.metas.pos.POSOrderStatus;
import de.metas.pos.POSPaymentExternalId;
import de.metas.pos.POSProductsSearchResult;
import de.metas.pos.POSService;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalCloseJournalRequest;
import de.metas.pos.POSTerminalOpenJournalRequest;
import de.metas.pos.rest_api.json.JsonCashJournalSummary;
import de.metas.pos.rest_api.json.JsonContext;
import de.metas.pos.rest_api.json.JsonPOSOrder;
import de.metas.pos.rest_api.json.JsonPOSOrdersList;
import de.metas.pos.rest_api.json.JsonPOSTerminal;
import de.metas.pos.rest_api.json.JsonPOSTerminalCloseJournalRequest;
import de.metas.pos.rest_api.json.JsonPOSTerminalOpenJournalRequest;
import de.metas.pos.rest_api.json.JsonProductsSearchResult;
import de.metas.user.UserId;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Set;

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

	@GetMapping("/terminal")
	public JsonPOSTerminal getPOSTerminal()
	{
		final String adLanguage = getADLanguage();

		return JsonPOSTerminal.from(posService.getPOSTerminal(), adLanguage);
	}

	@PostMapping("/terminal/journal/open")
	public JsonPOSTerminal openCashJournal(@RequestBody final JsonPOSTerminalOpenJournalRequest request)
	{
		final POSTerminal posTerminal = posService.openCashJournal(
				POSTerminalOpenJournalRequest.builder()
						.cashierId(getLoggedUserId())
						.dateTrx(SystemTime.asInstant())
						.cashBeginningBalance(request.getCashBeginningBalance())
						.openingNote(request.getOpeningNote())
						.build()
		);

		return JsonPOSTerminal.from(posTerminal, getADLanguage());
	}

	@PostMapping("/terminal/journal/close")
	public JsonPOSTerminal closeCashJournal(@RequestBody final JsonPOSTerminalCloseJournalRequest request)
	{
		final POSTerminal posTerminal = posService.closeCashJournal(
				POSTerminalCloseJournalRequest.builder()
						.cashierId(getLoggedUserId())
						.cashClosingBalance(request.getCashClosingBalance())
						.closingNote(request.getClosingNote())
						.build()
		);

		return JsonPOSTerminal.from(posTerminal, getADLanguage());
	}

	@GetMapping("/terminal/journal")
	public JsonCashJournalSummary getCashJournalSummary()
	{
		final POSCashJournal cashJournal = posService.getCurrentCashJournal()
				.orElseThrow(() -> new AdempiereException("No open cash journal found"));
		return JsonCashJournalSummary.of(cashJournal, newJsonContext());
	}

	@GetMapping("/products")
	public JsonProductsSearchResult getProducts(@RequestParam(value = "query", required = false) final String queryParam)
	{
		final Instant date = SystemTime.asInstant();
		final String adLanguage = getADLanguage();

		final POSProductsSearchResult products = posService.getProducts(date, queryParam);
		return JsonProductsSearchResult.from(products, adLanguage);
	}

	@GetMapping("/orders")
	public JsonPOSOrdersList getOpenOrders(
			@RequestParam(value = "ids", required = false) final String commaSeparatedOrderIds
	)
	{
		final UserId loggedUserId = getLoggedUserId();
		final Set<POSOrderExternalId> onlyOrderIds = POSOrderExternalId.ofCommaSeparatedString(commaSeparatedOrderIds);
		final List<POSOrder> orders = posService.getOpenOrders(loggedUserId, onlyOrderIds);

		final Set<POSOrderExternalId> missingIds;
		if (onlyOrderIds != null && !onlyOrderIds.isEmpty())
		{
			final Set<POSOrderExternalId> existingIds = orders.stream().map(POSOrder::getExternalId).collect(ImmutableSet.toImmutableSet());
			missingIds = Sets.difference(onlyOrderIds, existingIds);
		}
		else
		{
			missingIds = ImmutableSet.of();
		}

		return JsonPOSOrdersList.from(orders, newJsonContext())
				.missingIds(missingIds)
				.build();
	}

	@PostMapping("/orders/{orderId}/draft")
	public JsonPOSOrder changeStatusToDraft(@PathVariable("orderId") @NonNull final String orderIdStr)
	{
		return changeStatusTo(orderIdStr, POSOrderStatus.Drafted);
	}

	@PostMapping("/orders/{orderId}/waitingPayment")
	public JsonPOSOrder changeStatusToWaitingPayment(@PathVariable("orderId") @NonNull final String orderIdStr)
	{
		return changeStatusTo(orderIdStr, POSOrderStatus.WaitingPayment);
	}

	@PostMapping("/orders/{orderId}/void")
	public JsonPOSOrder changeStatusToVoid(@PathVariable("orderId") @NonNull final String orderIdStr)
	{
		return changeStatusTo(orderIdStr, POSOrderStatus.Voided);
	}

	@PostMapping("/orders/{orderId}/complete")
	public JsonPOSOrder changeStatusToComplete(@PathVariable("orderId") @NonNull final String orderIdStr)
	{
		return changeStatusTo(orderIdStr, POSOrderStatus.Completed);
	}

	private JsonPOSOrder changeStatusTo(@NonNull final String orderIdStr, @NonNull final POSOrderStatus nextStatus)
	{
		final POSOrderExternalId externalId = POSOrderExternalId.ofString(orderIdStr);
		final UserId loggedUserId = getLoggedUserId();
		final POSOrder order = posService.changeStatusTo(externalId, nextStatus, loggedUserId);
		return JsonPOSOrder.of(order, newJsonContext());
	}

	@PostMapping("/orders")
	public JsonPOSOrder updateOrder(@RequestBody @NonNull final JsonPOSOrder remoteOrder)
	{
		final UserId loggedUserId = getLoggedUserId();
		final POSOrder order = posService.updateOrderFromRemote(remoteOrder.toRemotePOSOrder(), loggedUserId);
		return JsonPOSOrder.of(order, newJsonContext());
	}

	@PostMapping("/orders/{orderId}/payments/{paymentId}/checkout")
	public JsonPOSOrder checkoutPayment(
			@PathVariable("orderId") @NonNull final String orderIdStr,
			@PathVariable("paymentId") @NonNull final String paymentIdStr
	)
	{
		final UserId loggedUserId = getLoggedUserId();
		final POSOrderExternalId posOrderExternalId = POSOrderExternalId.ofString(orderIdStr);
		final POSPaymentExternalId posPaymentExternalId = POSPaymentExternalId.ofString(paymentIdStr);
		final POSOrder order = posService.checkoutPayment(posOrderExternalId, posPaymentExternalId, loggedUserId);
		return JsonPOSOrder.of(order, newJsonContext());
	}
}
