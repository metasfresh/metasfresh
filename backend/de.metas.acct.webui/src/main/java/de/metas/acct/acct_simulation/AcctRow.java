package de.metas.acct.acct_simulation;

import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.currency.Amount;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class AcctRow implements IViewRow
{
	static final String FIELDNAME_PostingSign = "PostingSign";
	@ViewColumn(seqNo = 10, widgetType = DocumentFieldWidgetType.List, listReferenceId = PostingSign.AD_REFERENCE_ID, fieldName = FIELDNAME_PostingSign, editor = ViewEditorRenderMode.ALWAYS)
	@Getter @NonNull private final PostingSign postingSign;

	static final String FIELDNAME_Account_ID = "Account_ID";
	@ViewColumn(seqNo = 20, widgetType = DocumentFieldWidgetType.Lookup, fieldName = FIELDNAME_Account_ID, editor = ViewEditorRenderMode.ALWAYS)
	@Nullable private final LookupValue account;

	static final String FIELDNAME_Amount_DC = "Amount_DC";
	@ViewColumn(seqNo = 30, widgetType = DocumentFieldWidgetType.Amount, fieldName = FIELDNAME_Amount_DC, editor = ViewEditorRenderMode.ALWAYS)
	@Getter @NonNull private final Amount amount_DC;

	static final String FIELDNAME_Amount_LC = "Amount_LC";
	@ViewColumn(seqNo = 40, widgetType = DocumentFieldWidgetType.Amount, fieldName = FIELDNAME_Amount_LC)
	@Getter @NonNull private final Amount amount_LC;

	static final String FIELDNAME_C_Tax_ID = "C_Tax_ID";
	@ViewColumn(seqNo = 50, widgetType = DocumentFieldWidgetType.Lookup, fieldName = FIELDNAME_C_Tax_ID, editor = ViewEditorRenderMode.ALWAYS)
	@Nullable private final LookupValue tax;

	static final String FIELDNAME_Description = "Description";
	@ViewColumn(seqNo = 60, widgetType = DocumentFieldWidgetType.Text, fieldName = FIELDNAME_Description, editor = ViewEditorRenderMode.ALWAYS)
	@Nullable private final String description;

	static final String FIELDNAME_M_SectionCode_ID = "M_SectionCode_ID";
	@ViewColumn(seqNo = 70, widgetType = DocumentFieldWidgetType.Lookup, fieldName = FIELDNAME_M_SectionCode_ID, editor = ViewEditorRenderMode.ALWAYS)
	@Nullable private final LookupValue sectionCode;

	static final String FIELDNAME_M_Product_ID = "M_Product_ID";
	@ViewColumn(seqNo = 80, widgetType = DocumentFieldWidgetType.Lookup, fieldName = FIELDNAME_M_Product_ID, editor = ViewEditorRenderMode.ALWAYS)
	@Nullable private final LookupValue product;

	static final String FIELDNAME_UserElementString1 = "UserElementString1";
	@ViewColumn(seqNo = 90, widgetType = DocumentFieldWidgetType.Text, fieldName = FIELDNAME_UserElementString1, editor = ViewEditorRenderMode.ALWAYS)
	@Nullable private final String userElementString1;

	static final String FIELDNAME_C_OrderSO_ID = "C_OrderSO_ID";
	@ViewColumn(seqNo = 100, widgetType = DocumentFieldWidgetType.Lookup, fieldName = FIELDNAME_C_OrderSO_ID, editor = ViewEditorRenderMode.ALWAYS)
	@Nullable private final LookupValue orderSO;

	static final String FIELDNAME_C_Activity_ID = "C_Activity_ID";
	@ViewColumn(seqNo = 110, widgetType = DocumentFieldWidgetType.Lookup, fieldName = FIELDNAME_C_Activity_ID, editor = ViewEditorRenderMode.ALWAYS)
	@Nullable private final LookupValue activity;

	//
	//
	private final AcctRowLookups lookups;
	private final ViewRowFieldNameAndJsonValuesHolder<AcctRow> values;
	@NonNull private final DocumentId rowId;

	@Builder(toBuilder = true)
	private AcctRow(
			@NonNull final AcctRowLookups lookups,
			@NonNull final DocumentId rowId,
			@NonNull final PostingSign postingSign,
			@Nullable final LookupValue account,
			@NonNull final Amount amount_DC,
			@NonNull final Amount amount_LC,
			@Nullable final LookupValue tax,
			@Nullable final String description,
			@Nullable final LookupValue sectionCode,
			@Nullable final LookupValue product,
			@Nullable final String userElementString1,
			@Nullable final LookupValue orderSO,
			@Nullable final LookupValue activity)
	{
		this.lookups = lookups;
		this.postingSign = postingSign;
		this.account = account;
		this.amount_DC = amount_DC;
		this.amount_LC = amount_LC;
		this.tax = tax;
		this.description = description;
		this.sectionCode = sectionCode;
		this.product = product;
		this.userElementString1 = userElementString1;
		this.orderSO = orderSO;
		this.activity = activity;

		//
		this.rowId = rowId;
		this.values = ViewRowFieldNameAndJsonValuesHolder.newInstance(AcctRow.class);
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

	public LookupValuesPage getFieldTypeahead(String fieldName, String query) {return lookups.getFieldTypeahead(fieldName, query);}

	public LookupValuesList getFieldDropdown(String fieldName) {return lookups.getFieldDropdown(fieldName);}

	public AcctRow withPatch(final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final AcctRowBuilder builder = toBuilder();
		for (final JSONDocumentChangedEvent event : fieldChangeRequests)
		{
			event.assertReplaceOperation();
			final String fieldName = event.getPath();
			if (FIELDNAME_PostingSign.equals(fieldName))
			{
				final PostingSign postingSign = event.getValueAsEnum(PostingSign.class);
				if (postingSign == null)
				{
					throw new AdempiereException("PostingSign is mandatory");
				}
				builder.postingSign(postingSign);
			}
			else if (FIELDNAME_Account_ID.equals(fieldName))
			{
				builder.account(lookups.lookupElementValue(event.getValueAsId(ElementValueId::ofRepoIdOrNull)));
			}
			else if (FIELDNAME_Amount_DC.equals(fieldName))
			{
				final BigDecimal valueBD = event.getValueAsBigDecimal(BigDecimal.ZERO);
				builder.amount_DC(Amount.of(valueBD, amount_DC.getCurrencyCode()));
			}
			else
			{
				throw new AdempiereException("Changing field `" + fieldName + "` is not allowed");
			}
		}

		return builder.build();
	}
}
