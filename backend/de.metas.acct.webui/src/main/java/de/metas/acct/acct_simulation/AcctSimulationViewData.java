package de.metas.acct.acct_simulation;

import de.metas.acct.factacct_userchanges.FactAcctChangesType;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.currency.MutableAmount;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.ViewHeaderProperties;
import de.metas.ui.web.view.ViewHeaderPropertiesGroup;
import de.metas.ui.web.view.ViewHeaderProperty;
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

	private final AcctSimulationViewDataService dataService;

	@NonNull @Getter private final AcctSimulationDocInfo docInfo;

	//
	// state
	@NonNull private final SynchronizedRowsIndexHolder<AcctRow> rowsHolder = SynchronizedRowsIndexHolder.empty();
	@NonNull private final SynchronizedMutable<ViewHeaderProperties> headerPropertiesHolder = SynchronizedMutable.empty();

	@Builder
	private AcctSimulationViewData(
			final @NonNull AcctSimulationViewDataService dataService,
			final @NonNull AcctSimulationDocInfo docInfo)
	{
		this.dataService = dataService;
		this.docInfo = docInfo;

		rowsHolder.setRows(this.dataService.retrieveRows(this.docInfo));
	}

	@Override
	public Map<DocumentId, AcctRow> getDocumentId2TopLevelRows() {return rowsHolder.getDocumentId2TopLevelRows(AcctRow::isNotRemoved);}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs) {return DocumentIdsSelection.EMPTY;}

	@Override
	public void invalidateAll()
	{
		headerPropertiesHolder.setValue(null);
	}

	@Override
	public void patchRow(final IEditableView.RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		rowsHolder.changeRowById(ctx.getRowId(), row -> row.withPatch(fieldChangeRequests));
		headerPropertiesHolder.setValue(null);
		ViewChangesCollector.getCurrentOrAutoflush().collectHeaderPropertiesChanged(ctx.getViewId());
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
								.value(TranslatableStrings.amount(balance.getInDocumentCurrency().getDebit()))
								.build())
						.entry(ViewHeaderProperty.builder()
								.fieldName("totalCredit_DC")
								.caption(TranslatableStrings.adElementOrMessage("TotalCr_DC"))
								.value(TranslatableStrings.amount(balance.getInDocumentCurrency().getCredit()))
								.build())
						.entry(ViewHeaderProperty.builder()
								.fieldName("balance_DC")
								.caption(TranslatableStrings.adElementOrMessage("Balance_DC"))
								.value(TranslatableStrings.amount(balance.getInDocumentCurrency().getBalance()))
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
		final MutableAmount totalDebit_DC = MutableAmount.zero(docInfo.getDocumentCurrencyCode());
		final MutableAmount totalCredit_DC = MutableAmount.zero(docInfo.getDocumentCurrencyCode());
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

		return AcctSimulationViewBalance.builder()
				.inDocumentCurrency(AmountBalance.builder().debit(totalDebit_DC.toAmount()).credit(totalCredit_DC.toAmount()).build())
				.inLocalCurrency(AmountBalance.builder().debit(totalDebit_LC.toAmount()).credit(totalCredit_LC.toAmount()).build())
				.build();
	}

	public void save()
	{
		if (!computeBalance().getInDocumentCurrency().isBalanced())
		{
			throw new AdempiereException("@NotBalanced@");
		}

		dataService.save(rowsHolder.list(), docInfo);
	}

	public void addNewRow()
	{
		rowsHolder.addRow(dataService.newRow(docInfo));
	}

	public void removeRowsById(@NonNull final DocumentIdsSelection rowIds)
	{
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
	}

}
