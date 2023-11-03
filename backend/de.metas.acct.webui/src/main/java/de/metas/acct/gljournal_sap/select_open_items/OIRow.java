package de.metas.acct.gljournal_sap.select_open_items;

import de.metas.acct.Account;
import de.metas.acct.api.FactAcctId;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.document.dimension.Dimension;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.descriptor.WidgetSize;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_C_AcctSchema_Element;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Set;

class OIRow implements IViewRow
{
	public static OIRow cast(IViewRow row) {return (OIRow)row;}

	@ViewColumn(seqNo = 10, widgetType = DocumentFieldWidgetType.YesNo, captionKey = "OI/PrintName", widgetSize = WidgetSize.Small)
	@Getter private final boolean isOpenItem;

	@ViewColumn(seqNo = 20, widgetType = DocumentFieldWidgetType.List, listReferenceId = PostingSign.AD_REFERENCE_ID, captionKey = "PostingSign", fieldName = "postingSign", widgetSize = WidgetSize.Small)
	@Getter @NonNull private final PostingSign postingSign;

	@ViewColumn(seqNo = 30, widgetType = DocumentFieldWidgetType.Text, captionKey = "Account_ID")
	@NonNull private final ITranslatableString accountCaption;

	@ViewColumn(seqNo = 40, widgetType = DocumentFieldWidgetType.Amount, captionKey = "Amount")
	@Getter @NonNull private final Amount amount;

	@ViewColumn(seqNo = 50, widgetType = DocumentFieldWidgetType.Amount, captionKey = "OpenAmt", fieldName = "openAmount")
	@NonNull private final Amount openAmount;

	@ViewColumn(seqNo = 60, widgetType = DocumentFieldWidgetType.LocalDate, captionKey = "DateAcct")
	@Getter @NonNull private final Instant dateAcct;

	@ViewColumn(seqNo = 70, widgetType = DocumentFieldWidgetType.Lookup, captionKey = "C_BPartner_ID")
	@Nullable private final ITranslatableString bpartnerCaption;

	@ViewColumn(seqNo = 80, widgetType = DocumentFieldWidgetType.Text, captionKey = "DocumentNo")
	@Nullable private final String documentNo;

	@ViewColumn(seqNo = 90, widgetType = DocumentFieldWidgetType.Text, captionKey = "Description")
	@Nullable private final String description;

	@ViewColumn(seqNo = 100, widgetType = DocumentFieldWidgetType.Lookup, captionKey = "M_SectionCode_ID")
	@Nullable private final LookupValue sectionCode;

	@ViewColumn(seqNo = 110, widgetType = DocumentFieldWidgetType.Text, captionKey = I_C_AcctSchema_Element.COLUMNNAME_UserElementString1)
	@Nullable private final String userElementString1;

	static final String FIELD_Selected = "selected";
	@ViewColumn(seqNo = 120, widgetType = DocumentFieldWidgetType.YesNo, captionKey = "IsSelected", fieldName = FIELD_Selected, editor = ViewEditorRenderMode.ALWAYS, widgetSize = WidgetSize.Small)
	@Getter private final boolean selected;

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
			final boolean isOpenItem,
			@NonNull final PostingSign postingSign,
			@NonNull final Account account,
			@NonNull final ITranslatableString accountCaption,
			@NonNull final Amount amount,
			@NonNull final Amount openAmount,
			@NonNull final Instant dateAcct,
			@Nullable final LookupValue sectionCode,
			@Nullable final BPartnerId bpartnerId,
			@Nullable final ITranslatableString bpartnerCaption,
			@Nullable final String documentNo,
			@Nullable final String description,
			@Nullable final String userElementString1, final boolean selected,
			@NonNull final FAOpenItemKey openItemKey,
			@NonNull final Dimension dimension)
	{
		this.isOpenItem = isOpenItem;
		this.postingSign = postingSign;
		this.accountCaption = accountCaption;
		this.amount = amount;
		this.openAmount = openAmount;
		this.dateAcct = dateAcct;
		this.sectionCode = sectionCode;
		this.bpartnerId = bpartnerId;
		this.bpartnerCaption = bpartnerCaption;
		this.documentNo = documentNo;
		this.description = description;
		this.userElementString1 = userElementString1;
		this.selected = selected;

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

	public OIRow withUserInputCleared()
	{
		return this.selected
				? toBuilder().selected(false).build()
				: this;
	}

	@Nullable
	public OIRowUserInputPart getUserInputPart()
	{
		if (selected)
		{
			return OIRowUserInputPart.builder()
					.rowId(rowId)
					.factAcctId(factAcctId)
					.selected(selected)
					.build();
		}
		else
		{
			return null;
		}
	}

	public OIRow withUserInput(@Nullable final OIRowUserInputPart userInput)
	{
		final boolean selectedNew = userInput != null && userInput.isSelected();

		return this.selected != selectedNew
				? toBuilder().selected(selectedNew).build()
				: this;
	}

	public Amount getOpenAmountEffective() {return openAmount;}
}
