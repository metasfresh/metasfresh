package de.metas.pos;

import de.metas.money.Money;
import de.metas.pos.remote.RemotePOSOrder;
import de.metas.user.UserId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class POSService
{
	@NonNull private final POSTerminalService posTerminalService;
	@NonNull private final POSCashJournalService posJournalService;
	@NonNull private final POSProductsService productsService;
	@NonNull private final POSOrdersService ordersService;

	@NonNull
	public POSTerminal getPOSTerminalById(final POSTerminalId posTerminalId) {return posTerminalService.getPOSTerminalById(posTerminalId);}

	@NonNull
	public Collection<POSTerminal> getPOSTerminals() {return posTerminalService.getPOSTerminals();}

	public POSTerminal openCashJournal(@NonNull final POSTerminalOpenJournalRequest request)
	{
		return posTerminalService.updateById(request.getPosTerminalId(), posTerminal -> {
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

			return posTerminal.openingCashJournal(journal.getId());
		});
	}

	public POSTerminal closeCashJournal(@NonNull final POSTerminalCloseJournalRequest request)
	{
		return posTerminalService.updateById(request.getPosTerminalId(), posTerminal -> {
			final POSCashJournalId cashJournalId = posTerminal.getCashJournalId();
			if (cashJournalId == null)
			{
				throw new AdempiereException("No open cash journal");
			}

			final Money cashClosingBalance = Money.of(request.getCashClosingBalance(), posTerminal.getCurrencyId());
			final UserId cashierId = request.getCashierId();
			final String closingNote = request.getClosingNote();

			final POSCashJournal journal = posJournalService.changeJournalById(cashJournalId, cashJournal -> cashJournal.close(cashClosingBalance, closingNote, cashierId));

			return posTerminal.closingCashJournal(journal.getCashEndingBalance());
		});
	}

	public Optional<POSCashJournal> getCurrentCashJournal(@NonNull final POSTerminalId posTerminalId)
	{
		final POSTerminal posTerminal = posTerminalService.getPOSTerminalById(posTerminalId);
		final POSCashJournalId cashJournalId = posTerminal.getCashJournalId();
		return cashJournalId != null
				? Optional.of(posJournalService.getById(cashJournalId))
				: Optional.empty();
	}

	public POSProductsSearchResult getProducts(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final Instant evalDate,
			@Nullable final String queryString)
	{
		return productsService.getProducts(posTerminalId, evalDate, queryString);
	}

	public List<POSOrder> getOpenOrders(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final UserId userId,
			final Set<POSOrderExternalId> onlyOrderExternalIds)
	{
		return ordersService.list(POSOrderQuery.builder()
				.posTerminalId(posTerminalId)
				.cashierId(userId)
				.isOpen(true)
				.onlyOrderExternalIds(onlyOrderExternalIds)
				.build());
	}

	public POSOrder changeStatusTo(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final POSOrderExternalId externalId,
			@NonNull final POSOrderStatus nextStatus,
			@NonNull final UserId userId)
	{
		return ordersService.changeStatusTo(posTerminalId, externalId, nextStatus, userId);
	}

	public POSOrder updateOrderFromRemote(
			@NonNull final RemotePOSOrder remoteOrder,
			@NonNull final UserId userId)
	{
		return ordersService.updateOrderFromRemote(remoteOrder, userId);
	}

	public POSOrder checkoutPayment(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final POSOrderExternalId posOrderExternalId,
			@NonNull final POSPaymentExternalId posPaymentExternalId,
			@NonNull final UserId userId)
	{
		return ordersService.checkoutPayment(posTerminalId, posOrderExternalId, posPaymentExternalId, userId);
	}

	public POSOrder refundPayment(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final POSOrderExternalId posOrderExternalId,
			@NonNull final POSPaymentExternalId posPaymentExternalId,
			@NonNull final UserId userId)
	{
		return ordersService.refundPayment(posTerminalId, posOrderExternalId, posPaymentExternalId, userId);
	}

}

