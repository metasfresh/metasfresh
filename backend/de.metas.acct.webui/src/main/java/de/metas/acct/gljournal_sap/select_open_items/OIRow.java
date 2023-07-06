package de.metas.acct.gljournal_sap.select_open_items;

import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

class OIRow implements IViewRow
{
	@ViewColumn(seqNo = 10, widgetType = DocumentFieldWidgetType.YesNo, captionKey = "IsOpenItem")
	private final boolean isOpenItem;
	@ViewColumn(seqNo = 20, widgetType = DocumentFieldWidgetType.List, listReferenceId = PostingSign.AD_REFERENCE_ID, captionKey = "PostingSign")
	private final PostingSign postingSign;
	@ViewColumn(seqNo = 30, widgetType = DocumentFieldWidgetType.Lookup, captionKey = "Account_ID")
	private final LookupValue account;
	@ViewColumn(seqNo = 40, widgetType = DocumentFieldWidgetType.Amount, captionKey = "Amount")
	private final BigDecimal amount;
	@ViewColumn(seqNo = 50, widgetType = DocumentFieldWidgetType.Amount, captionKey = "OpenAmt")
	private final BigDecimal openAmount;
	@ViewColumn(seqNo = 60, widgetType = DocumentFieldWidgetType.LocalDate, captionKey = "DateAcct")
	private final Instant dateAcct;
	@ViewColumn(seqNo = 70, widgetType = DocumentFieldWidgetType.Lookup, captionKey = "C_BPartner_ID")
	private final LookupValue bpartner;
	@ViewColumn(seqNo = 80, widgetType = DocumentFieldWidgetType.Text, captionKey = "DocumentNo")
	private final String documentNo;
	@ViewColumn(seqNo = 90, widgetType = DocumentFieldWidgetType.Text, captionKey = "Description")
	private final String description;

	private final ViewRowFieldNameAndJsonValuesHolder<OIRow> values;
	private final DocumentId rowId;
	@Getter @NonNull private final int factAcctId;

	@Builder
	private OIRow(
			@NonNull final int factAcctId,
			final boolean isOpenItem,
			@NonNull final PostingSign postingSign,
			@NonNull final LookupValue account,
			@NonNull final BigDecimal amount,
			@NonNull final BigDecimal openAmount,
			@NonNull final Instant dateAcct,
			@Nullable final LookupValue bpartner,
			@Nullable final String documentNo,
			@Nullable final String description)
	{
		this.isOpenItem = isOpenItem;
		this.postingSign = postingSign;
		this.account = account;
		this.amount = amount;
		this.openAmount = openAmount;
		this.dateAcct = dateAcct;
		this.bpartner = bpartner;
		this.documentNo = documentNo;
		this.description = description;

		this.values = ViewRowFieldNameAndJsonValuesHolder.newInstance(OIRow.class);
		this.rowId = DocumentId.of(factAcctId);
		this.factAcctId = factAcctId;
	}

	@Override
	public DocumentId getId() {return rowId;}

	@Override
	public boolean isProcessed() {return false;}

	@Nullable
	@Override
	public DocumentPath getDocumentPath() {return null;}

	@Override
	public Set<String> getFieldNames() {return values.getFieldNames();}

	@Override
	public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues() {return values.get(this);}
}
