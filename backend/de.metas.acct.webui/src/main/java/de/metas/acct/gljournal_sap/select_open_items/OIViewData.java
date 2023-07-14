package de.metas.acct.gljournal_sap.select_open_items;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.MutableAmount;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.document.filter.DocumentFilter;
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
import org.adempiere.util.lang.SynchronizedMutable;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class OIViewData implements IEditableRowsData<OIRow>
{
	public static OIViewData cast(final IRowsData<OIRow> rowsData)
	{
		return (OIViewData)rowsData;
	}

	//
	// services
	@NonNull private final OIViewDataService viewDataService;

	//
	// parameters
	@NonNull private final SAPGLJournalId glJournalId;
	@NonNull private final SynchronizedMutable<SAPGLJournal> glJournalHolder;
	@NonNull private final AcctSchema acctSchema;
	@Getter @Nullable private final DocumentFilter filter;

	//
	// state
	@NonNull private final SynchronizedRowsIndexHolder<OIRow> rowsHolder = SynchronizedRowsIndexHolder.empty();
	@NonNull private final SynchronizedMutable<ViewHeaderProperties> headerPropertiesHolder = SynchronizedMutable.empty();

	@Builder
	private OIViewData(
			@NonNull final OIViewDataService viewDataService,
			@NonNull final SAPGLJournal glJournal,
			@NonNull final AcctSchema acctSchema,
			@Nullable final DocumentFilter filter,
			@Nullable final OIRowUserInputParts initialUserInput)
	{
		this.viewDataService = viewDataService;
		this.glJournalId = glJournal.getId();
		this.glJournalHolder = SynchronizedMutable.of(glJournal);
		this.acctSchema = acctSchema;
		this.filter = filter;

		loadRows(initialUserInput != null ? initialUserInput : OIRowUserInputParts.EMPTY);
	}

	@Override
	public Map<DocumentId, OIRow> getDocumentId2TopLevelRows() {return rowsHolder.getDocumentId2TopLevelRows();}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs) {return DocumentIdsSelection.EMPTY;}

	@Override
	public void invalidateAll()
	{
		headerPropertiesHolder.setValue(null);
		glJournalHolder.setValue(null);
		loadRows(getUserInput());
	}

	private void loadRows(@NonNull final OIRowUserInputParts userInput)
	{
		final SAPGLJournal glJournal = getGLJournal();
		final OIViewDataQuery query = OIViewDataQuery.builder()
				.acctSchema(acctSchema)
				.postingType(glJournal.getPostingType())
				.futureClearingAmounts(FutureClearingAmountMap.of(glJournal, viewDataService.currencyCodeConverter()))
				.filter(filter)
				.includeFactAcctIds(userInput.getFactAcctIds())
				.build();

		final ImmutableList<OIRow> rows = viewDataService.streamRows(query)
				.map(userInput::applyToRow)
				.sorted(Comparator.<OIRow, String>comparing(row -> row.isSelected() ? "1" : "2")
						.thenComparing(OIRow::getDateAcct)
						.thenComparing(OIRow::getFactAcctId)
				)
				.collect(ImmutableList.toImmutableList());

		rowsHolder.setRows(rows);
	}

	private SAPGLJournal getGLJournal()
	{
		return glJournalHolder.computeIfNull(() -> viewDataService.getGlJournal(glJournalId));
	}

	public ViewHeaderProperties getHeaderProperties()
	{
		return headerPropertiesHolder.computeIfNull(this::computeHeaderProperties);
	}

	private ViewHeaderProperties computeHeaderProperties()
	{
		final CurrencyCode acctCurrencyCode = viewDataService.currencyCodeConverter().getCurrencyCodeByCurrencyId(acctSchema.getCurrencyId());
		final MutableAmount totalDebit = MutableAmount.zero(acctCurrencyCode);
		final MutableAmount totalCredit = MutableAmount.zero(acctCurrencyCode);

		rowsHolder.stream(OIRow::isSelected)
				.forEach(row -> {
					final Amount openAmount = row.getOpenAmountEffective();
					if (row.getPostingSign().isDebit())
					{
						totalDebit.add(openAmount);
					}
					else
					{
						totalCredit.add(openAmount);
					}
				});

		final Amount balance = totalDebit.toAmount().subtract(totalCredit.toAmount());

		return ViewHeaderProperties.builder()
				.group(ViewHeaderPropertiesGroup.builder()
						.entry(ViewHeaderProperty.builder()
								.fieldName("totalDebit")
								.caption(TranslatableStrings.adElementOrMessage("TotalDr"))
								.value(TranslatableStrings.amount(totalDebit.toAmount()))
								.build())
						.entry(ViewHeaderProperty.builder()
								.fieldName("totalCredit")
								.caption(TranslatableStrings.adElementOrMessage("TotalCr"))
								.value(TranslatableStrings.amount(totalCredit.toAmount()))
								.build())
						.entry(ViewHeaderProperty.builder()
								.fieldName("balance")
								.caption(TranslatableStrings.adElementOrMessage("Balance"))
								.value(TranslatableStrings.amount(balance))
								.build())
						.build())
				.build();
	}

	@Override
	public void patchRow(final IEditableView.RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		rowsHolder.changeRowById(ctx.getRowId(), row -> applyChanges(row, fieldChangeRequests));
		headerPropertiesHolder.setValue(null);

		ViewChangesCollector.getCurrentOrAutoflush().collectHeaderPropertiesChanged(ctx.getViewId());
		// NOTE: don't need to notify about row changed because that will be returned by the REST call
	}

	private static OIRow applyChanges(@NonNull final OIRow row, @NonNull final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final OIRow.OIRowBuilder changedRowBuilder = row.toBuilder();

		for (final JSONDocumentChangedEvent event : fieldChangeRequests)
		{
			event.assertReplaceOperation();

			if (OIRow.FIELD_Selected.equals(event.getPath()))
			{
				changedRowBuilder.selected(event.getValueAsBoolean(false));
			}
		}

		return changedRowBuilder.build();
	}

	public void markRowsAsSelected(final DocumentIdsSelection rowIds)
	{
		rowsHolder.changeRowsByIds(rowIds, row -> row.withSelected(true));
		headerPropertiesHolder.setValue(null);
	}

	public boolean hasSelectedRows()
	{
		return rowsHolder.anyMatch(OIRow::isSelected);
	}

	public void clearUserInput()
	{
		rowsHolder.changeRowsByIds(DocumentIdsSelection.ALL, OIRow::withUserInputCleared);
		headerPropertiesHolder.setValue(null);
	}

	public OIRowUserInputParts getUserInput() {return OIRowUserInputParts.ofStream(rowsHolder.stream());}

}
