package de.metas.acct.open_items.handlers;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.AccountConceptualName;
import de.metas.acct.accounts.BPartnerGroupAccountType;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.gljournal_sap.SAPGLJournalLine;
import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.acct.open_items.FAOpenItemTrxInfoComputeRequest;
import de.metas.acct.open_items.FAOpenItemsHandler;
import de.metas.acct.open_items.FAOpenItemsHandlerMatchingKey;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueService;
import de.metas.invoice.matchinv.MatchInv;
import de.metas.invoice.matchinv.MatchInvId;
import de.metas.invoice.matchinv.service.MatchInvoiceRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_MatchInv;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

@Component
@VisibleForTesting
@RequiredArgsConstructor
public class MatchInvOIHandler implements FAOpenItemsHandler
{
	private static final @NonNull AccountConceptualName NotInvoicedReceipts_Acct = BPartnerGroupAccountType.NotInvoicedReceipts.getAccountConceptualName();
	private static final @NonNull AccountConceptualName P_InventoryClearing_Acct = ProductAcctType.P_InventoryClearing_Acct.getAccountConceptualName();

	@NonNull private final ElementValueService elementValueService;
	@NonNull private final MatchInvoiceRepository matchInvoiceRepository;

	@Override
	public @NonNull Set<FAOpenItemsHandlerMatchingKey> getMatchers()
	{
		return ImmutableSet.of(
				FAOpenItemsHandlerMatchingKey.of(NotInvoicedReceipts_Acct, I_M_InOut.Table_Name),
				FAOpenItemsHandlerMatchingKey.of(NotInvoicedReceipts_Acct, I_M_MatchInv.Table_Name),
				FAOpenItemsHandlerMatchingKey.of(P_InventoryClearing_Acct, I_C_Invoice.Table_Name),
				FAOpenItemsHandlerMatchingKey.of(P_InventoryClearing_Acct, I_M_MatchInv.Table_Name)
		);
	}

	@Override
	public Optional<FAOpenItemTrxInfo> computeTrxInfo(@NonNull final FAOpenItemTrxInfoComputeRequest request)
	{
		final ElementValue elementValue = elementValueService.getById(request.getElementValueId());
		if (!elementValue.isOpenItem())
		{
			return Optional.empty();
		}

		final AccountConceptualName accountConceptualName = request.getAccountConceptualName();
		if (accountConceptualName == null)
		{
			return Optional.empty();
		}

		final String tableName = request.getTableName();

		if (NotInvoicedReceipts_Acct.equals(accountConceptualName))
		{
			if (I_M_InOut.Table_Name.equals(tableName))
			{
				final FAOpenItemKey key = FAOpenItemKey.ofTableRecordLineAndSubLineId(
						accountConceptualName, I_M_InOut.Table_Name,
						request.getRecordId(), request.getLineId(), 0);
				return Optional.of(FAOpenItemTrxInfo.opening(key));
			}
			else if (I_M_MatchInv.Table_Name.equals(tableName))
			{
				final MatchInv matchInv = matchInvoiceRepository.getById(MatchInvId.ofRepoId(request.getRecordId()));
				final FAOpenItemKey key = FAOpenItemKey.ofTableRecordLineAndSubLineId(
						accountConceptualName, I_M_InOut.Table_Name,
						matchInv.getInoutLineId().getInOutId().getRepoId(),
						matchInv.getInoutLineId().getInOutLineId().getRepoId(),
						0);
				return Optional.of(FAOpenItemTrxInfo.clearing(key));
			}
		}
		else if (P_InventoryClearing_Acct.equals(accountConceptualName))
		{
			if (I_C_Invoice.Table_Name.equals(tableName))
			{
				final FAOpenItemKey key = FAOpenItemKey.ofTableRecordLineAndSubLineId(
						accountConceptualName, I_C_Invoice.Table_Name,
						request.getRecordId(), request.getLineId(), 0);
				return Optional.of(FAOpenItemTrxInfo.opening(key));
			}
			else if (I_M_MatchInv.Table_Name.equals(tableName))
			{
				final MatchInv matchInv = matchInvoiceRepository.getById(MatchInvId.ofRepoId(request.getRecordId()));
				final FAOpenItemKey key = FAOpenItemKey.ofTableRecordLineAndSubLineId(
						accountConceptualName, I_C_Invoice.Table_Name,
						matchInv.getInvoiceAndLineId().getInvoiceId().getRepoId(),
						matchInv.getInvoiceAndLineId().getRepoId(),
						0);
				return Optional.of(FAOpenItemTrxInfo.clearing(key));
			}
		}

		return Optional.empty();
	}

	@Override
	public void onGLJournalLineCompleted(final SAPGLJournalLine line)
	{
	}

	@Override
	public void onGLJournalLineReactivated(final SAPGLJournalLine line)
	{
	}
}
