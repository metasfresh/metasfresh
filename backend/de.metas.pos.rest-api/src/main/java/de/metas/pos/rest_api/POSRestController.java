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
import de.metas.pos.POSPaymentCheckoutRequest;
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
import de.metas.pos.rest_api.json.JsonProduct;
import de.metas.pos.rest_api.json.JsonProductsSearchResult;
import de.metas.user.UserId;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.compiere.util.MimeType;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		final JsonContext jsonContext = newJsonContext();
		final String adLanguage = jsonContext.getAdLanguage();
		final UserId loggedUserId = getLoggedUserId();
		final Instant date = SystemTime.asInstant();

		final POSTerminalId posTerminalId = POSTerminalId.ofString(posTerminalIdStr);
		final POSTerminal posTerminal = posService.getPOSTerminalById(posTerminalId);
		final POSProductsSearchResult products = posService.getProducts(posTerminalId, date, null);

		final List<POSOrder> orders = posService.getOpenOrders(posTerminalId, loggedUserId, null);

		return JsonPOSTerminal.builderFrom(posTerminal, adLanguage)
				.products(JsonProduct.fromList(products.toList(), adLanguage))
				.openOrders(JsonPOSOrder.fromList(orders, jsonContext::getCurrencySymbol))
				.build();
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
	public JsonPOSOrder changeStatusToDrafted(@RequestBody JsonChangeOrderStatusRequest request)
	{
		return changeStatusTo(request, POSOrderStatus.Drafted);
	}

	@PostMapping("/orders/waitingPayment")
	public JsonPOSOrder changeStatusToWaitingPayment(@RequestBody JsonChangeOrderStatusRequest request)
	{
		return changeStatusTo(request, POSOrderStatus.WaitingPayment);
	}

	@PostMapping("/orders/void")
	public JsonPOSOrder changeStatusToVoided(@RequestBody JsonChangeOrderStatusRequest request)
	{
		return changeStatusTo(request, POSOrderStatus.Voided);
	}

	@PostMapping("/orders/complete")
	public JsonPOSOrder changeStatusToCompleted(@RequestBody JsonChangeOrderStatusRequest request)
	{
		return changeStatusTo(request, POSOrderStatus.Completed);
	}

	@PostMapping("/orders/close")
	public JsonPOSOrder changeStatusToClosed(@RequestBody JsonChangeOrderStatusRequest request)
	{
		return changeStatusTo(request, POSOrderStatus.Closed);
	}

	private JsonPOSOrder changeStatusTo(@NonNull JsonChangeOrderStatusRequest request, @NonNull final POSOrderStatus nextStatus)
	{
		final POSTerminalId posTerminalId = request.getPosTerminalId();
		final POSOrderExternalId externalId = request.getOrder_uuid();
		final UserId loggedUserId = getLoggedUserId();
		final POSOrder order = posService.changeStatusTo(posTerminalId, externalId, nextStatus, loggedUserId);
		return JsonPOSOrder.from(order, newJsonContext()::getCurrencySymbol);
	}

	@PostMapping("/orders")
	public JsonPOSOrder updateOrder(@RequestBody @NonNull final JsonPOSOrder remoteOrder)
	{
		final POSOrder order = posService.updateOrderFromRemote(remoteOrder.toRemotePOSOrder(), getLoggedUserId());
		return JsonPOSOrder.from(order, newJsonContext()::getCurrencySymbol);
	}

	@PostMapping("/orders/checkoutPayment")
	public JsonPOSOrder checkoutPayment(@RequestBody JsonPOSPaymentCheckoutRequest request)
	{
		final POSOrder order = posService.checkoutPayment(POSPaymentCheckoutRequest.builder()
				.posTerminalId(request.getPosTerminalId())
				.posOrderExternalId(request.getOrder_uuid())
				.posPaymentExternalId(request.getPayment_uuid())
				.userId(getLoggedUserId())
				.cardPayAmount(request.getCardPayAmount())
				.cashTenderedAmount(request.getCashTenderedAmount())
				.build());
		return JsonPOSOrder.from(order, newJsonContext()::getCurrencySymbol);
	}

	@PostMapping("/orders/refundPayment")
	public JsonPOSOrder refundPayment(@RequestBody JsonPOSPaymentRefundRequest request)
	{
		final POSOrder order = posService.refundPayment(request.getPosTerminalId(), request.getOrder_uuid(), request.getPayment_uuid(), getLoggedUserId());
		return JsonPOSOrder.from(order, newJsonContext()::getCurrencySymbol);
	}

	@GetMapping("/orders/receipt/{filename:.*}")
	@PostMapping("/orders/receipt/{filename:.*}")
	public ResponseEntity<Resource> getReceiptPdf(
			@PathVariable("filename") final String filename,
			@RequestParam(value = "id") final String idStr)
	{
		final POSOrderExternalId posOrderExternalId = POSOrderExternalId.ofString(idStr);
		return posService.getReceiptPdf(posOrderExternalId)
				.map(resource -> createPDFResponseEntry(resource, filename))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	private static ResponseEntity<Resource> createPDFResponseEntry(@NonNull final Resource resource, @NonNull final String filename)
	{
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MimeType.getMediaType(filename));
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
}

