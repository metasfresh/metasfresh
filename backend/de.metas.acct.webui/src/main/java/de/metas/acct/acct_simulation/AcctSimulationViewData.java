package de.metas.acct.acct_simulation;

import de.metas.acct.factacct_userchanges.FactAcctChangesList;
import de.metas.acct.factacct_userchanges.FactAcctChangesType;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.MutableAmount;
import de.metas.currency.MutableMultiCurrencyAmount;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.ViewHeaderProperties;
import de.metas.ui.web.view.ViewHeaderPropertiesGroup;
import de.metas.ui.web.view.ViewHeaderProperty;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.template.IEditableRowsData;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.view.template.SynchronizedRowsIndexHolder;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.SynchronizedMutable;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import java.util.List;
import java.util.Map;

class AcctSimulationViewData implements IEditableRowsData<AcctRow>
{
	public static AcctSimulationViewData cast(final IRowsData<AcctRow> data) {return (AcctSimulationViewData)data;}

	//
	// Services
	private final AcctSimulationViewDataService dataService;

	//
	// Params
	@NonNull @Getter private final AcctSimulationDocInfo docInfo;
	@NonNull @Getter private final ViewId viewId;

	//
	// State
	@NonNull private final SynchronizedRowsIndexHolder<AcctRow> rowsHolder = SynchronizedRowsIndexHolder.empty();
	@NonNull private final SynchronizedMutable<ViewHeaderProperties> headerPropertiesHolder = SynchronizedMutable.empty();

	@Builder
	private AcctSimulationViewData(
			final @NonNull AcctSimulationViewDataService dataService,
			final @NonNull AcctSimulationDocInfo docInfo,
			final @NonNull ViewId viewId)
	{
		this.dataService = dataService;
		this.docInfo = docInfo;
		this.viewId = viewId;

		rowsHolder.setRows(this.dataService.retrieveRows(this.docInfo));
	}

	public boolean isReadonly() {return docInfo.isFactsReadOnly();}

	@Override
	public Map<DocumentId, AcctRow> getDocumentId2TopLevelRows() {return rowsHolder.getDocumentId2TopLevelRows(AcctRow::isNotRemoved);}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs) {return DocumentIdsSelection.EMPTY;}

	@Override
	public void invalidateAll()
	{
		headerPropertiesHolder.setValue(null);
	}

	private void invalidateHeaderPropertiesAndNotify()
	{
		headerPropertiesHolder.setValue(null);
		ViewChangesCollector.getCurrentOrAutoflush().collectHeaderPropertiesChanged(viewId);
	}

	@Override
	public void patchRow(final IEditableView.RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		if (fieldChangeRequests.isEmpty())
		{
			return;
		}

		if (isReadonly())
		{
			throw new AdempiereException("Editing a readonly simulation is not allowed");
		}

		rowsHolder.changeRowById(ctx.getRowId(), row -> row.withPatch(fieldChangeRequests));
		invalidateHeaderPropertiesAndNotify();
	}

	public ViewHeaderProperties getHeaderProperties()
	{
		return headerPropertiesHolder.computeIfNull(this::computeHeaderProperties);
	}

	private ViewHeaderProperties computeHeaderProperties()
	{
		final AcctSimulationViewBalance balance = computeBalance();

		return ViewHeaderProperties.builder()
				.group(ViewHeaderPropertiesGroup.builder()
						.entry(ViewHeaderProperty.builder()
								.fieldName("totalDebit_DC")
								.caption(TranslatableStrings.adElementOrMessage("TotalDr_DC"))
								.value(balance.getInDocumentCurrency().getDebitAsTrlString())
								.build())
						.entry(ViewHeaderProperty.builder()
								.fieldName("totalCredit_DC")
								.caption(TranslatableStrings.adElementOrMessage("TotalCr_DC"))
								.value(balance.getInDocumentCurrency().getCreditAsTrlString())
								.build())
						.entry(ViewHeaderProperty.builder()
								.fieldName("balance_DC")
								.caption(TranslatableStrings.adElementOrMessage("Balance_DC"))
								.value(balance.getInDocumentCurrency().getBalanceAsTrlString())
								.build())
						.build())
				.group(ViewHeaderPropertiesGroup.builder()
						.entry(ViewHeaderProperty.builder()
								.fieldName("totalDebit_LC")
								.caption(TranslatableStrings.adElementOrMessage("TotalDr_LC"))
								.value(TranslatableStrings.amount(balance.getInLocalCurrency().getDebit()))
								.build())
						.entry(ViewHeaderProperty.builder()
								.fieldName("totalCredit_LC")
								.caption(TranslatableStrings.adElementOrMessage("TotalCr_LC"))
								.value(TranslatableStrings.amount(balance.getInLocalCurrency().getCredit()))
								.build())
						.entry(ViewHeaderProperty.builder()
								.fieldName("balance_LC")
								.caption(TranslatableStrings.adElementOrMessage("Balance_LC"))
								.value(TranslatableStrings.amount(balance.getInLocalCurrency().getBalance()))
								.build())
						.build())
				.build();

	}

	private AcctSimulationViewBalance computeBalance()
	{
		final MutableMultiCurrencyAmount totalDebit_DC = MutableMultiCurrencyAmount.zero(docInfo.getDocumentCurrencyCode());
		final MutableMultiCurrencyAmount totalCredit_DC = MutableMultiCurrencyAmount.zero(docInfo.getDocumentCurrencyCode());
		final MutableAmount totalDebit_LC = MutableAmount.zero(docInfo.getLocalCurrencyCode());
		final MutableAmount totalCredit_LC = MutableAmount.zero(docInfo.getLocalCurrencyCode());

		rowsHolder.stream(AcctRow::isNotRemoved)
				.forEach(row -> {
					final PostingSign postingSign = row.getPostingSign();
					if (postingSign.isDebit())
					{
						totalDebit_DC.add(row.getAmount_DC());
						totalDebit_LC.add(row.getAmount_LC());
					}
					else if (postingSign.isCredit())
					{
						totalCredit_DC.add(row.getAmount_DC());
						totalCredit_LC.add(row.getAmount_LC());
					}
				});

		AmountMultiBalance inDocumentCurrency = AmountMultiBalance.builder().debit(totalDebit_DC.toSet()).credit(totalCredit_DC.toSet()).build();
		if (!CurrencyCode.equals(docInfo.getDocumentCurrencyCode(), docInfo.getLocalCurrencyCode()))
		{
			inDocumentCurrency = inDocumentCurrency.removing(docInfo.getLocalCurrencyCode());
		}

		return AcctSimulationViewBalance.builder()
				.inDocumentCurrency(inDocumentCurrency)
				.inLocalCurrency(AmountBalance.builder().debit(totalDebit_LC.toAmount()).credit(totalCredit_LC.toAmount()).build())
				.build();
	}

	private FactAcctChangesList getFactAcctChangesList()
	{
		return rowsHolder.stream()
				.map(AcctRow::getUserChanges)
				.collect(FactAcctChangesList.collect());
	}

	public void save()
	{
		assertEditable();

		if (!computeBalance().getInDocumentCurrency().isBalanced())
		{
			throw new AdempiereException("@NotBalanced@");
		}

		dataService.save(getFactAcctChangesList(), docInfo);
	}

	public void addNewRow()
	{
		assertEditable();

		final PostingSign postingSign;
		final Amount amount_DC;
		final Amount balance = computeBalance().getInDocumentCurrency().getBalance(docInfo.getDocumentCurrencyCode());
		if (balance.signum() > 0)
		{
			postingSign = PostingSign.CREDIT;
			amount_DC = balance;
		}
		else
		{
			postingSign = PostingSign.DEBIT;
			amount_DC = balance.negate();
		}
		rowsHolder.addRow(dataService.newRow(docInfo, postingSign, amount_DC));
		invalidateHeaderPropertiesAndNotify();
	}

	public void removeRowsById(@NonNull final DocumentIdsSelection rowIds)
	{
		assertEditable();

		rowsHolder.changeRowsByIds(rowIds, row -> {
			final FactAcctChangesType changeType = row.getChangeType();
			if (changeType.isAdd())
			{
				return null; // simply remove it
			}
			else if (changeType.isChangeOrDelete())
			{
				return row.asRemoved();
			}
			else
			{
				throw new AdempiereException("Unknown change type `" + changeType + "` of " + row);
			}
		});

		invalidateHeaderPropertiesAndNotify();
	}

	public void updateSimulation()
	{
		assertEditable();

		// Discard delete lines changes
		final FactAcctChangesList factAcctChangesList = getFactAcctChangesList()
				.removingIf(line -> line.getType().isDelete());

		// Ask the accounting engine to regenerate lines and then apply our changes (excluding removed lines)
		rowsHolder.setRows(this.dataService.retrieveRows(this.docInfo, factAcctChangesList));
		invalidateHeaderPropertiesAndNotify();
	}

	private void assertEditable()
	{
		if (docInfo.isFactsReadOnly())
		{
			throw new AdempiereException("Editing a readonly simulation is not allowed");
		}
	}
}
