package de.metas.acct.gljournal_sap.select_open_items;

import de.metas.acct.Account;
import de.metas.acct.api.FactAcctId;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.document.dimension.Dimension;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Set;

class OIRow implements IViewRow
{
	public static OIRow cast(IViewRow row) {return (OIRow)row;}

	@ViewColumn(seqNo = 20, widgetType = DocumentFieldWidgetType.List, listReferenceId = PostingSign.AD_REFERENCE_ID, captionKey = "PostingSign", fieldName = "postingSign")
	@Getter @NonNull private final PostingSign postingSign;

	@ViewColumn(seqNo = 30, widgetType = DocumentFieldWidgetType.Text, captionKey = "Account_ID")
	@NonNull private final ITranslatableString accountCaption;

	@ViewColumn(seqNo = 40, widgetType = DocumentFieldWidgetType.Amount, captionKey = "Amount")
	@NonNull private final Amount amount;

	@ViewColumn(seqNo = 50, widgetType = DocumentFieldWidgetType.Amount, captionKey = "OpenAmt", fieldName = "openAmount")
	@NonNull private final Amount openAmount;

	@ViewColumn(seqNo = 60, widgetType = DocumentFieldWidgetType.LocalDate, captionKey = "DateAcct")
	@NonNull private final Instant dateAcct;

	@ViewColumn(seqNo = 70, widgetType = DocumentFieldWidgetType.Lookup, captionKey = "C_BPartner_ID")
	@Nullable private final ITranslatableString bpartnerCaption;

	@ViewColumn(seqNo = 80, widgetType = DocumentFieldWidgetType.Text, captionKey = "DocumentNo")
	@Nullable private final String documentNo;

	@ViewColumn(seqNo = 90, widgetType = DocumentFieldWidgetType.Text, captionKey = "Description")
	@Nullable private final String description;

	static final String FIELD_Selected = "selected";
	@ViewColumn(seqNo = 100, widgetType = DocumentFieldWidgetType.YesNo, captionKey = "IsSelected", fieldName = FIELD_Selected, editor = ViewEditorRenderMode.ALWAYS)
	@Getter private final boolean selected;

	static final String FIELD_OpenAmountOverrde = "openAmountOverride";
	@ViewColumn(seqNo = 110, widgetType = DocumentFieldWidgetType.Amount, captionKey = "OpenAmtOverride", fieldName = FIELD_OpenAmountOverrde, editor = ViewEditorRenderMode.ALWAYS)
	@Nullable private final Amount openAmountOverride;

	private final ViewRowFieldNameAndJsonValuesHolder<OIRow> values;

	@NonNull private final DocumentId rowId;
	@Getter @NonNull private final FactAcctId factAcctId;
	@Getter @NonNull private final Account account;
	@Getter @NonNull private final FAOpenItemKey openItemKey;
	@Getter @Nullable private final BPartnerId bpartnerId;
	@Getter @NonNull private final Dimension dimension;

	@Builder(toBuilder = true)
	private OIRow(
			@NonNull final FactAcctId factAcctId,
			@NonNull final PostingSign postingSign,
			@NonNull final Account account,
			@NonNull final ITranslatableString accountCaption,
			@NonNull final Amount amount,
			@NonNull final Amount openAmount,
			@NonNull final Instant dateAcct,
			@Nullable final BPartnerId bpartnerId,
			@Nullable final ITranslatableString bpartnerCaption,
			@Nullable final String documentNo,
			@Nullable final String description,
			final boolean selected,
			@Nullable final Amount openAmountOverride,
			@NonNull final FAOpenItemKey openItemKey,
			@NonNull final Dimension dimension)
	{
		this.postingSign = postingSign;
		this.accountCaption = accountCaption;
		this.amount = amount;
		this.openAmount = openAmount;
		this.dateAcct = dateAcct;
		this.bpartnerId = bpartnerId;
		this.bpartnerCaption = bpartnerCaption;
		this.documentNo = documentNo;
		this.description = description;
		this.selected = selected;
		this.openAmountOverride = openAmountOverride;

		this.values = ViewRowFieldNameAndJsonValuesHolder.newInstance(OIRow.class);
		this.rowId = DocumentId.of(factAcctId);
		this.factAcctId = factAcctId;
		this.account = account;
		this.openItemKey = openItemKey;
		this.dimension = dimension;
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

	public OIRow withSelected(final boolean selected)
	{
		return this.selected != selected
				? toBuilder().selected(selected).build()
				: this;
	}

	public CurrencyCode getAcctCurrencyCode() {return amount.getCurrencyCode();}

	public Amount getOpenAmountEffective() {return CoalesceUtil.coalesceNotNull(openAmountOverride, openAmount);}
}
