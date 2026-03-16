package de.metas.acct.account_info.hierarchy;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowType;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

class AccountHierarchyRow implements IViewRow
{
	@ViewColumn(seqNo = 10, widgetType = DocumentFieldWidgetType.Text, captionKey = "Value")
	@NonNull private final String accountValue;

	@ViewColumn(seqNo = 20, widgetType = DocumentFieldWidgetType.Text, captionKey = "Name")
	@NonNull private final String accountName;

	@ViewColumn(seqNo = 30, widgetType = DocumentFieldWidgetType.Amount, captionKey = "Balance")
	@Getter @NonNull private final BigDecimal balance;

	@ViewColumn(seqNo = 40, widgetType = DocumentFieldWidgetType.Amount, captionKey = "TotalDr")
	@Getter @NonNull private final BigDecimal debitYTD;

	@ViewColumn(seqNo = 50, widgetType = DocumentFieldWidgetType.Amount, captionKey = "TotalCr")
	@Getter @NonNull private final BigDecimal creditYTD;

	private final ViewRowFieldNameAndJsonValuesHolder<AccountHierarchyRow> values;

	@NonNull private final DocumentId rowId;
	@Getter @NonNull private final ElementValueId elementValueId;
	@Getter @NonNull private final AccountHierarchyRowType rowType;
	@NonNull private final List<AccountHierarchyRow> includedRows;
	@Getter @Nullable private final ElementValueId parentElementValueId;

	@Builder(toBuilder = true)
	private AccountHierarchyRow(
			@NonNull final ElementValueId elementValueId,
			@NonNull final String accountValue,
			@NonNull final String accountName,
			@NonNull final BigDecimal balance,
			@NonNull final BigDecimal debitYTD,
			@NonNull final BigDecimal creditYTD,
			@NonNull final AccountHierarchyRowType rowType,
			@Nullable final ElementValueId parentElementValueId,
			@Nullable final List<AccountHierarchyRow> includedRows)
	{
		this.elementValueId = elementValueId;
		this.accountValue = accountValue;
		this.accountName = accountName;
		this.balance = balance;
		this.debitYTD = debitYTD;
		this.creditYTD = creditYTD;
		this.rowType = rowType;
		this.parentElementValueId = parentElementValueId;
		this.includedRows = includedRows != null ? ImmutableList.copyOf(includedRows) : ImmutableList.of();

		this.values = ViewRowFieldNameAndJsonValuesHolder.newInstance(AccountHierarchyRow.class);
		this.rowId = DocumentId.of(elementValueId);
	}

	@Override
	public DocumentId getId() { return rowId; }

	@Override
	public IViewRowType getType() { return rowType; }

	@Override
	public boolean isProcessed() { return true; }

	@Nullable
	@Override
	public DocumentPath getDocumentPath() { return null; }

	@Override
	public Set<String> getFieldNames() { return values.getFieldNames(); }

	@Override
	public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues() { return values.get(this); }

	@Override
	public List<AccountHierarchyRow> getIncludedRows() { return includedRows; }

	AccountHierarchyRow withIncludedRows(@NonNull final List<AccountHierarchyRow> newIncludedRows)
	{
		return toBuilder().includedRows(newIncludedRows).build();
	}

	AccountHierarchyRow withAggregatedBalances(
			@NonNull final BigDecimal totalBalance,
			@NonNull final BigDecimal totalDebitYTD,
			@NonNull final BigDecimal totalCreditYTD)
	{
		return toBuilder()
				.balance(totalBalance)
				.debitYTD(totalDebitYTD)
				.creditYTD(totalCreditYTD)
				.build();
	}
}
