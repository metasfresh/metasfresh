package de.metas.acct.gljournal_sap.select_open_items;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.PostingType;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.MutableAmount;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.ViewHeaderProperties;
import de.metas.ui.web.view.ViewHeaderPropertiesGroup;
import de.metas.ui.web.view.ViewHeaderProperty;
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
	@NonNull private final AcctSchema acctSchema;
	@NonNull private final CurrencyCode acctCurrencyCode;
	@NonNull private final PostingType postingType;
	@Getter @Nullable private final DocumentFilter filter;

	//
	// state
	@NonNull private final SynchronizedRowsIndexHolder<OIRow> rowsHolder;
	@NonNull private final SynchronizedMutable<ViewHeaderProperties> headerPropertiesHolder = SynchronizedMutable.empty();

	@Builder
	private OIViewData(
			@NonNull final OIViewDataService viewDataService,
			@NonNull final AcctSchema acctSchema,
			@NonNull final PostingType postingType,
			@Nullable final DocumentFilter filter)
	{
		this.viewDataService = viewDataService;
		this.acctSchema = acctSchema;
		this.acctCurrencyCode = viewDataService.getCurrencyCode(acctSchema);
		this.postingType = postingType;
		this.filter = filter;

		this.rowsHolder = SynchronizedRowsIndexHolder.of(viewDataService.retrieveRows(acctSchema, postingType, filter));
	}

	@Override
	public Map<DocumentId, OIRow> getDocumentId2TopLevelRows() {return rowsHolder.getDocumentId2TopLevelRows();}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs) {return DocumentIdsSelection.EMPTY;}

	@Override
	public void invalidateAll()
	{
		rowsHolder.setRows(viewDataService.retrieveRows(acctSchema, postingType, filter));
		headerPropertiesHolder.setValue(null);
	}

	public ViewHeaderProperties getHeaderProperties()
	{
		return headerPropertiesHolder.computeIfNull(this::computeHeaderProperties);
	}

	private ViewHeaderProperties computeHeaderProperties()
	{
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
								.caption(TranslatableStrings.adElementOrMessage("TotalDebit"))
								.value(TranslatableStrings.amount(totalDebit.toAmount()))
								.build())
						.entry(ViewHeaderProperty.builder()
								.caption(TranslatableStrings.adElementOrMessage("TotalCredit"))
								.value(TranslatableStrings.amount(totalCredit.toAmount()))
								.build())
						.entry(ViewHeaderProperty.builder()
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
	}

	private static OIRow applyChanges(@NonNull final OIRow row, @NonNull final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final OIRow.OIRowBuilder changedRowBuilder = row.toBuilder();

		for (final JSONDocumentChangedEvent event : fieldChangeRequests)
		{
			event.assertReplaceOperation();

			switch (event.getPath())
			{
				case OIRow.FIELD_Selected:
				{
					changedRowBuilder.selected(event.getValueAsBoolean(false));
					break;
				}
				case OIRow.FIELD_OpenAmountOverrde:
				{
					final Amount openAmountOverride = event.getValueAsBigDecimalOptional()
							.filter(value -> value.signum() != 0)
							.map(value -> Amount.of(value, row.getAcctCurrencyCode()))
							.orElse(null);
					changedRowBuilder.openAmountOverride(openAmountOverride);
					break;
				}
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
}
