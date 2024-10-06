package de.metas.pos;

import de.metas.money.Money;
import de.metas.pos.remote.RemotePOSOrder;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class POSService
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final POSTerminalService posTerminalService;
	@NonNull private final POSCashJournalService posJournalService;
	@NonNull private final POSProductsService productsService;
	@NonNull private final POSOrdersService ordersService;

	@NonNull
	public POSTerminal getPOSTerminal() {return posTerminalService.getPOSTerminal();}

	public POSTerminal openCashJournal(@NonNull final POSTerminalOpenJournalRequest request)
	{
		return trxManager.callInThreadInheritedTrx(() -> {
			final POSTerminal posTerminal = getPOSTerminal();
			if (posTerminal.isCashJournalOpen())
			{
				throw new AdempiereException("Cash journal already open");
			}

			final POSCashJournal journal = posJournalService.newJournal(POSCashJournalCreateRequest.builder()
					.orgId(posTerminal.getOrgId())
					.terminalId(posTerminal.getId())
					.dateTrx(request.getDateTrx())
					.cashBeginningBalance(Money.of(request.getCashBeginningBalance(), posTerminal.getCurrencyId()))
					.openingNote(request.getOpeningNote())
					.build());

			posTerminal.openCashJournal(journal.getId());
			posTerminalService.save(posTerminal);
			return posTerminal;
		});
	}

	public POSTerminal closeCashJournal(@NonNull final POSTerminalCloseJournalRequest request)
	{
		return trxManager.callInThreadInheritedTrx(() -> {
			final POSTerminal posTerminal = getPOSTerminal();
			final POSCashJournalId cashJournalId = posTerminal.getCashJournalId();
			if (cashJournalId == null)
			{
				throw new AdempiereException("No open cash journal");
			}

			final Money cashClosingBalance = Money.of(request.getCashClosingBalance(), posTerminal.getCurrencyId());
			final UserId cashierId = request.getCashierId();
			final String closingNote = request.getClosingNote();

			final POSCashJournal journal = posJournalService.changeJournalById(cashJournalId, cashJournal -> cashJournal.close(cashClosingBalance, closingNote, cashierId));

			posTerminal.closeCashJournal(journal.getCashEndingBalance());
			posTerminalService.save(posTerminal);
			return posTerminal;
		});
	}

	public Optional<POSCashJournal> getCurrentCashJournal()
	{
		final POSCashJournalId cashJournalId = getPOSTerminal().getCashJournalId();
		return cashJournalId != null
				? Optional.of(posJournalService.getById(cashJournalId))
				: Optional.empty();
	}

	public POSProductsSearchResult getProducts(@NonNull final Instant evalDate, @Nullable final String queryString)
	{
		final POSTerminalId posTerminalId = posTerminalService.getPOSTerminalId();
		return productsService.getProducts(posTerminalId, evalDate, queryString);
	}

	public List<POSOrder> getOpenOrders(@NonNull final UserId userId, final Set<POSOrderExternalId> onlyOrderExternalIds)
	{
		return ordersService.list(POSOrderQuery.builder()
				.cashierId(userId)
				.isOpen(true)
				.onlyOrderExternalIds(onlyOrderExternalIds)
				.build());
	}

	public POSOrder changeStatusTo(@NonNull final POSOrderExternalId externalId, @NonNull final POSOrderStatus nextStatus, @NonNull final UserId userId)
	{
		return ordersService.changeStatusTo(externalId, nextStatus, userId);
	}

	public POSOrder updateOrderFromRemote(final @NonNull RemotePOSOrder remoteOrder, final UserId userId)
	{
		return ordersService.updateOrderFromRemote(remoteOrder, userId);
	}

	public POSOrder checkoutPayment(
			@NonNull final POSOrderExternalId posOrderExternalId,
			@NonNull final POSPaymentExternalId posPaymentExternalId,
			@NonNull final UserId userId)
	{
		return ordersService.checkoutPayment(posOrderExternalId, posPaymentExternalId, userId);
	}

	public POSOrder refundPayment(
			@NonNull final POSOrderExternalId posOrderExternalId,
			@NonNull final POSPaymentExternalId posPaymentExternalId,
			@NonNull final UserId userId)
	{
		return ordersService.refundPayment(posOrderExternalId, posPaymentExternalId, userId);
	}

}

