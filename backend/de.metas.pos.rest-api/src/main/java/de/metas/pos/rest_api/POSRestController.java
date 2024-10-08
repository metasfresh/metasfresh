package de.metas.pos.rest_api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.metas.Profiles;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyRepository;
import de.metas.pos.POSCashJournal;
import de.metas.pos.POSOrder;
import de.metas.pos.POSOrderExternalId;
import de.metas.pos.POSOrderStatus;
import de.metas.pos.POSProductsSearchResult;
import de.metas.pos.POSService;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalCloseJournalRequest;
import de.metas.pos.POSTerminalId;
import de.metas.pos.POSTerminalOpenJournalRequest;
import de.metas.pos.rest_api.json.JsonCashJournalSummary;
import de.metas.pos.rest_api.json.JsonChangeOrderStatusRequest;
import de.metas.pos.rest_api.json.JsonContext;
import de.metas.pos.rest_api.json.JsonPOSOrder;
import de.metas.pos.rest_api.json.JsonPOSOrdersList;
import de.metas.pos.rest_api.json.JsonPOSPaymentCheckoutRequest;
import de.metas.pos.rest_api.json.JsonPOSPaymentRefundRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Comparator;
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
	public JsonPOSTerminal getPOSTerminalById(@RequestParam("id") String posTerminalIdStr)
	{
		final String adLanguage = getADLanguage();
		final POSTerminalId posTerminalId = POSTerminalId.ofString(posTerminalIdStr);
		return JsonPOSTerminal.from(posService.getPOSTerminalById(posTerminalId), adLanguage);
	}

	@GetMapping("/terminal/list")
	public List<JsonPOSTerminal> getPOSTerminals()
	{
		final String adLanguage = getADLanguage();

		return posService.getPOSTerminals()
				.stream()
				.map(posTerminal -> JsonPOSTerminal.from(posTerminal, adLanguage))
				.sorted(Comparator.comparing(JsonPOSTerminal::getCaption))
				.collect(ImmutableList.toImmutableList());
	}

	@PostMapping("/terminal/openJournal")
	public JsonPOSTerminal openCashJournal(@RequestBody final JsonPOSTerminalOpenJournalRequest request)
	{
		final POSTerminal posTerminal = posService.openCashJournal(
				POSTerminalOpenJournalRequest.builder()
						.posTerminalId(request.getPosTerminalId())
						.cashierId(getLoggedUserId())
						.dateTrx(SystemTime.asInstant())
						.cashBeginningBalance(request.getCashBeginningBalance())
						.openingNote(request.getOpeningNote())
						.build()
		);

		return JsonPOSTerminal.from(posTerminal, getADLanguage());
	}

	@PostMapping("/terminal/closeJournal")
	public JsonPOSTerminal closeCashJournal(@RequestBody final JsonPOSTerminalCloseJournalRequest request)
	{
		final POSTerminal posTerminal = posService.closeCashJournal(
				POSTerminalCloseJournalRequest.builder()
						.posTerminalId(request.getPosTerminalId())
						.cashierId(getLoggedUserId())
						.cashClosingBalance(request.getCashClosingBalance())
						.closingNote(request.getClosingNote())
						.build()
		);

		return JsonPOSTerminal.from(posTerminal, getADLanguage());
	}

	@GetMapping("/terminal/journal")
	public JsonCashJournalSummary getCashJournalSummary(@RequestParam("posTerminalId") @NonNull String posTerminalIdStr)
	{
		final POSTerminalId posTerminalId = POSTerminalId.ofString(posTerminalIdStr);
		final POSCashJournal cashJournal = posService.getCurrentCashJournal(posTerminalId)
				.orElseThrow(() -> new AdempiereException("No open cash journal found"));
		return JsonCashJournalSummary.of(cashJournal, newJsonContext());
	}

	@GetMapping("/products")
	public JsonProductsSearchResult getProducts(
			@RequestParam("posTerminalId") @NonNull String posTerminalIdStr,
			@RequestParam(value = "query", required = false) final String queryParam)
	{
		final POSTerminalId posTerminalId = POSTerminalId.ofString(posTerminalIdStr);
		final Instant date = SystemTime.asInstant();
		final String adLanguage = getADLanguage();

		final POSProductsSearchResult products = posService.getProducts(posTerminalId, date, queryParam);
		return JsonProductsSearchResult.from(products, adLanguage);
	}

	@GetMapping("/orders")
	public JsonPOSOrdersList getOpenOrders(
			@RequestParam("posTerminalId") @NonNull String posTerminalIdStr,
			@RequestParam(value = "ids", required = false) final String commaSeparatedOrderIds
	)
	{
		final UserId loggedUserId = getLoggedUserId();
		final POSTerminalId posTerminalId = POSTerminalId.ofString(posTerminalIdStr);
		final Set<POSOrderExternalId> onlyOrderIds = POSOrderExternalId.ofCommaSeparatedString(commaSeparatedOrderIds);
		final List<POSOrder> orders = posService.getOpenOrders(posTerminalId, loggedUserId, onlyOrderIds);

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

	@PostMapping("/orders/draft")
	public JsonPOSOrder changeStatusToDraft(@RequestBody JsonChangeOrderStatusRequest request)
	{
		return changeStatusTo(request, POSOrderStatus.Drafted);
	}

	@PostMapping("/orders/waitingPayment")
	public JsonPOSOrder changeStatusToWaitingPayment(@RequestBody JsonChangeOrderStatusRequest request)
	{
		return changeStatusTo(request, POSOrderStatus.WaitingPayment);
	}

	@PostMapping("/orders/void")
	public JsonPOSOrder changeStatusToVoid(@RequestBody JsonChangeOrderStatusRequest request)
	{
		return changeStatusTo(request, POSOrderStatus.Voided);
	}

	@PostMapping("/orders/complete")
	public JsonPOSOrder changeStatusToComplete(@RequestBody JsonChangeOrderStatusRequest request)
	{
		return changeStatusTo(request, POSOrderStatus.Completed);
	}

	private JsonPOSOrder changeStatusTo(@NonNull JsonChangeOrderStatusRequest request, @NonNull final POSOrderStatus nextStatus)
	{
		final POSTerminalId posTerminalId = request.getPosTerminalId();
		final POSOrderExternalId externalId = request.getOrder_uuid();
		final UserId loggedUserId = getLoggedUserId();
		final POSOrder order = posService.changeStatusTo(posTerminalId, externalId, nextStatus, loggedUserId);
		return JsonPOSOrder.of(order, newJsonContext());
	}

	@PostMapping("/orders")
	public JsonPOSOrder updateOrder(@RequestBody @NonNull final JsonPOSOrder remoteOrder)
	{
		final POSOrder order = posService.updateOrderFromRemote(remoteOrder.toRemotePOSOrder(), getLoggedUserId());
		return JsonPOSOrder.of(order, newJsonContext());
	}

	@PostMapping("/orders/checkoutPayment")
	public JsonPOSOrder checkoutPayment(@RequestBody JsonPOSPaymentCheckoutRequest request)
	{
		final POSOrder order = posService.checkoutPayment(request.getPosTerminalId(), request.getOrder_uuid(), request.getPayment_uuid(), getLoggedUserId());
		return JsonPOSOrder.of(order, newJsonContext());
	}

	@PostMapping("/orders/refundPayment")
	public JsonPOSOrder refundPayment(@RequestBody JsonPOSPaymentRefundRequest request)
	{
		final POSOrder order = posService.refundPayment(request.getPosTerminalId(), request.getOrder_uuid(), request.getPayment_uuid(), getLoggedUserId());
		return JsonPOSOrder.of(order, newJsonContext());
	}

}
