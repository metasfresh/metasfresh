package de.metas.acct.account_info.bpartner_balance;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.FactAcctId;
import de.metas.currency.CurrencyCode;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.view.IViewRow;
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
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

class BPartnerBalanceRow implements IViewRow
{
	@ViewColumn(seqNo = 10, widgetType = DocumentFieldWidgetType.LocalDate, captionKey = "DateAcct")
	@Getter @NonNull private final LocalDate dateAcct;

	@ViewColumn(seqNo = 20, widgetType = DocumentFieldWidgetType.Text, captionKey = "Account_ID")
	@NonNull private final ITranslatableString accountCaption;

	@ViewColumn(seqNo = 30, widgetType = DocumentFieldWidgetType.Text, captionKey = "DocumentNo")
	@Nullable private final String documentNo;

	@ViewColumn(seqNo = 40, widgetType = DocumentFieldWidgetType.Text, captionKey = "Description")
	@Nullable private final String description;

	@ViewColumn(seqNo = 50, widgetType = DocumentFieldWidgetType.Amount, captionKey = "AmtAcctDr")
	@NonNull private final BigDecimal amtAcctDr;

	@ViewColumn(seqNo = 60, widgetType = DocumentFieldWidgetType.Amount, captionKey = "AmtAcctCr")
	@NonNull private final BigDecimal amtAcctCr;

	@ViewColumn(seqNo = 70, widgetType = DocumentFieldWidgetType.Amount, captionKey = "EndingBalance")
	@Getter @NonNull private final BigDecimal runningBalance;

	@ViewColumn(seqNo = 80, widgetType = DocumentFieldWidgetType.Text, captionKey = "C_Currency_ID")
	@NonNull private final String currencyCode;

	private final ViewRowFieldNameAndJsonValuesHolder<BPartnerBalanceRow> values;

	@NonNull private final DocumentId rowId;
	@Getter @NonNull private final FactAcctId factAcctId;
	@Getter private final int adTableId;
	@Getter private final int recordId;

	@Builder
	private BPartnerBalanceRow(
			@NonNull final FactAcctId factAcctId,
			@NonNull final LocalDate dateAcct,
			@NonNull final ITranslatableString accountCaption,
			@Nullable final String documentNo,
			@Nullable final String description,
			@NonNull final BigDecimal amtAcctDr,
			@NonNull final BigDecimal amtAcctCr,
			@NonNull final BigDecimal runningBalance,
			@NonNull final CurrencyCode currencyCode,
			final int adTableId,
			final int recordId)
	{
		this.dateAcct = dateAcct;
		this.accountCaption = accountCaption;
		this.documentNo = documentNo;
		this.description = description;
		this.amtAcctDr = amtAcctDr;
		this.amtAcctCr = amtAcctCr;
		this.runningBalance = runningBalance;
		this.currencyCode = currencyCode.toThreeLetterCode();
		this.adTableId = adTableId;
		this.recordId = recordId;

		this.values = ViewRowFieldNameAndJsonValuesHolder.newInstance(BPartnerBalanceRow.class);
		this.rowId = DocumentId.of(factAcctId);
		this.factAcctId = factAcctId;
	}

	@Override
	public DocumentId getId() { return rowId; }

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
	public Collection<? extends IViewRow> getIncludedRows() { return ImmutableList.of(); }
}
