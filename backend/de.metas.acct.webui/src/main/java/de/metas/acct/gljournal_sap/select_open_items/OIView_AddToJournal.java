package de.metas.acct.gljournal_sap.select_open_items;

import de.metas.acct.gljournal_sap.service.SAPGLJournalLineCreateRequest;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import org.compiere.SpringContextHolder;

import java.util.List;
import java.util.stream.Collectors;

public class OIView_AddToJournal extends OIViewBasedProcess
{
	private final SAPGLJournalService sapglJournalService = SpringContextHolder.instance.getBean(SAPGLJournalService.class);
	private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getView().hasSelectedRows())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No rows marked as selected");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final OIView view = getView();
		final List<SAPGLJournalLineCreateRequest> createRequests = view.streamByIds(DocumentIdsSelection.ALL)
				.filter(OIRow::isSelected)
				.map(this::toSAPGLJournalLineCreateRequest)
				.collect(Collectors.toList());

		sapglJournalService.createLines(createRequests, view.getSapglJournalId());

		view.clearUserInputAndInvalidateAll();

		return MSG_OK;
	}

	private SAPGLJournalLineCreateRequest toSAPGLJournalLineCreateRequest(final OIRow row)
	{
		final Money openAmount = row.getOpenAmountEffective()
				.toMoney(moneyService::getCurrencyIdByCurrencyCode);

		return SAPGLJournalLineCreateRequest.builder()
				.postingSign(row.getPostingSign().reverse())
				.account(row.getAccount())
				.amount(openAmount.toBigDecimal())
				.expectedCurrencyId(openAmount.getCurrencyId())
				.bpartnerId(row.getBpartnerId())
				.dimension(row.getDimension())
				.openItemTrxInfo(FAOpenItemTrxInfo.clearing(row.getOpenItemKey()))
				.isFieldsReadOnlyInUI(true)
				.build();
	}
}
