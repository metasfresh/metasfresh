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
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_MatchInv;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@VisibleForTesting
public class MatchInvOIHandler implements FAOpenItemsHandler
{
	private static final @NonNull AccountConceptualName NotInvoicedReceipts_Acct = BPartnerGroupAccountType.NotInvoicedReceipts.getAccountConceptualName();
	private static final @NonNull AccountConceptualName P_InventoryClearing_Acct = ProductAcctType.P_InventoryClearing_Acct.getAccountConceptualName();

	private final ElementValueService elementValueService;

	public MatchInvOIHandler(@NonNull final ElementValueService elementValueService)
	{
		this.elementValueService = elementValueService;
	}

	@Override
	public @NonNull Set<FAOpenItemsHandlerMatchingKey> getMatchers()
	{
		return ImmutableSet.of(
				FAOpenItemsHandlerMatchingKey.of(NotInvoicedReceipts_Acct, I_M_MatchInv.Table_Name),
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

		final I_M_MatchInv matchInvRecord = InterfaceWrapperHelper.load(request.getRecordId(), I_M_MatchInv.class);
		if (matchInvRecord == null)
		{
			throw new AdempiereException("M_MatchInv not found for id=" + request.getRecordId());
		}

		if (NotInvoicedReceipts_Acct.equals(accountConceptualName))
		{
			final FAOpenItemKey key = FAOpenItemKey.ofTableRecordLineAndSubLineId(
					accountConceptualName,
					I_M_InOut.Table_Name,
					matchInvRecord.getM_InOut_ID(),
					matchInvRecord.getM_InOutLine_ID(),
					0);
			return Optional.of(FAOpenItemTrxInfo.clearing(key));
		}
		else if (P_InventoryClearing_Acct.equals(accountConceptualName))
		{
			final FAOpenItemKey key = FAOpenItemKey.ofTableRecordLineAndSubLineId(
					accountConceptualName,
					I_C_Invoice.Table_Name,
					matchInvRecord.getC_Invoice_ID(),
					matchInvRecord.getC_InvoiceLine_ID(),
					0);
			return Optional.of(FAOpenItemTrxInfo.clearing(key));
		}
		else
		{
			return Optional.empty();
		}
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
