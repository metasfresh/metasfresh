package de.metas.acct.acct_simulation;

import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.currency.Amount;
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
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.SynchronizedMutable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class AcctSimulationViewData implements IEditableRowsData<AcctRow>
{
	public static AcctSimulationViewData cast(final IRowsData<AcctRow> data) {return (AcctSimulationViewData)data;}

	private final AcctSimulationViewDataService dataService;
	@Getter private final @NonNull TableRecordReference docRecordRef;
	private final @NonNull ClientId clientId;

	//
	// state
	@NonNull private final SynchronizedRowsIndexHolder<AcctRow> rowsHolder = SynchronizedRowsIndexHolder.empty();
	@NonNull private final SynchronizedMutable<ViewHeaderProperties> headerPropertiesHolder = SynchronizedMutable.empty();

	@Builder
	private AcctSimulationViewData(
			final @NonNull AcctSimulationViewDataService dataService,
			final @NonNull TableRecordReference docRecordRef,
			final @NonNull ClientId clientId)
	{
		this.dataService = dataService;
		this.docRecordRef = docRecordRef;
		this.clientId = clientId;

		loadRows();
	}

	@Override
	public Map<DocumentId, AcctRow> getDocumentId2TopLevelRows() {return rowsHolder.getDocumentId2TopLevelRows();}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs) {return DocumentIdsSelection.EMPTY;}

	@Override
	public void invalidateAll()
	{
		headerPropertiesHolder.setValue(null);
		//loadRows();
	}

	private void loadRows()
	{
		rowsHolder.setRows(dataService.retrieveRows(docRecordRef, clientId));
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
		final MutableAmount totalDebit_DC = MutableAmount.nullValue();
		final MutableAmount totalCredit_DC = MutableAmount.nullValue();

		final MutableAmount totalDebit_LC = MutableAmount.nullValue();
		final MutableAmount totalCredit_LC = MutableAmount.nullValue();

		rowsHolder.stream()
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

		return ViewHeaderProperties.builder()
				.group(ViewHeaderPropertiesGroup.builder()
						.entry(ViewHeaderProperty.builder()
								.fieldName("totalDebit_DC")
								.caption(TranslatableStrings.adElementOrMessage("TotalDr_DC"))
								.value(TranslatableStrings.amount(totalDebit_DC.toAmount()))
								.build())
						.entry(ViewHeaderProperty.builder()
								.fieldName("totalCredit_DC")
								.caption(TranslatableStrings.adElementOrMessage("TotalCr_DC"))
								.value(TranslatableStrings.amount(totalCredit_DC.toAmount()))
								.build())
						.entry(ViewHeaderProperty.builder()
								.fieldName("balance_DC")
								.caption(TranslatableStrings.adElementOrMessage("Balance_DC"))
								.value(TranslatableStrings.amount(computeBalance(totalDebit_DC.toAmount(), totalCredit_DC.toAmount())))
								.build())
						.build())
				.group(ViewHeaderPropertiesGroup.builder()
						.entry(ViewHeaderProperty.builder()
								.fieldName("totalDebit_LC")
								.caption(TranslatableStrings.adElementOrMessage("TotalDr_LC"))
								.value(TranslatableStrings.amount(totalDebit_LC.toAmount()))
								.build())
						.entry(ViewHeaderProperty.builder()
								.fieldName("totalCredit_LC")
								.caption(TranslatableStrings.adElementOrMessage("TotalCr_LC"))
								.value(TranslatableStrings.amount(totalCredit_LC.toAmount()))
								.build())
						.entry(ViewHeaderProperty.builder()
								.fieldName("balance_LC")
								.caption(TranslatableStrings.adElementOrMessage("Balance_LC"))
								.value(TranslatableStrings.amount(computeBalance(totalDebit_LC.toAmount(), totalCredit_LC.toAmount())))
								.build())
						.build())
				.build();

	}

	private static Amount computeBalance(@Nullable Amount debit, @Nullable Amount credit)
	{
		if (debit != null)
		{
			return credit != null ? debit.subtract(credit) : debit;
		}
		else
		{
			return credit != null ? credit.negate() : null;
		}
	}

	public void save()
	{
		dataService.save(rowsHolder.list(), docRecordRef);
	}
}
