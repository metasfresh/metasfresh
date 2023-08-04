package de.metas.acct.acct_simulation;

import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.factacct_userchanges.FactAcctChanges;
import de.metas.acct.factacct_userchanges.FactAcctChangesType;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.currency.Amount;
import de.metas.money.CurrencyIdToCurrencyCodeConverter;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.tax.api.TaxId;
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

class AcctRow implements IViewRow
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
	private final ViewRowFieldNameAndJsonValuesHolder<AcctRow> values;
	@NonNull private final AcctRowLookups lookups;
	@SuppressWarnings({ "FieldCanBeLocal", "unused" }) // needs to be here for toBuilder()
	@NonNull private final CurrencyIdToCurrencyCodeConverter currencyIdToCurrencyCodeConverter;
	@NonNull private final DocumentId rowId;
	private final boolean readonly;
	@NonNull private final AcctRowCurrencyRate currencyRate;
	@Getter @NonNull final FactAcctChanges userChanges;

	@Builder(toBuilder = true)
	private AcctRow(
			@NonNull final AcctRowLookups lookups,
			@NonNull final CurrencyIdToCurrencyCodeConverter currencyIdToCurrencyCodeConverter,
			@NonNull final FactAcctChanges userChanges,
			@NonNull final DocumentId rowId,
			final boolean readonly,
			@NonNull final AcctRowCurrencyRate currencyRate)
	{
		this.lookups = lookups;
		this.currencyIdToCurrencyCodeConverter = currencyIdToCurrencyCodeConverter;
		this.userChanges = userChanges;
		this.rowId = rowId;
		this.readonly = readonly;
		this.currencyRate = currencyRate;

		this.postingSign = userChanges.getPostingSign();
		this.account = lookups.lookupElementValue(userChanges.getAccountId());
		this.amount_DC = userChanges.getAmount_DC().toAmount(currencyIdToCurrencyCodeConverter::getCurrencyCodeByCurrencyId);
		this.amount_LC = userChanges.getAmount_LC().toAmount(currencyIdToCurrencyCodeConverter::getCurrencyCodeByCurrencyId);
		this.tax = lookups.lookupTax(userChanges.getTaxId());
		this.description = userChanges.getDescription();
		this.sectionCode = lookups.lookupSectionCode(userChanges.getSectionCodeId());
		this.product = lookups.lookupProduct(userChanges.getProductId());
		this.userElementString1 = userChanges.getUserElementString1();
		this.orderSO = lookups.lookupOrder(userChanges.getSalesOrderId());
		this.activity = lookups.lookupActivity(userChanges.getActivityId());

		this.values = ViewRowFieldNameAndJsonValuesHolder.newInstance(AcctRow.class);
	}

	@Override
	public DocumentId getId() {return rowId;}

	@Override
	public boolean isProcessed() {return readonly;}

	@Nullable
	@Override
	public DocumentPath getDocumentPath() {return null;}

	@Override
	public Set<String> getFieldNames() {return values.getFieldNames();}

	@Override
	public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues() {return values.get(this);}

	public boolean isRemoved() {return userChanges.getType().isDelete();}

	public boolean isNotRemoved() {return !isRemoved();}

	public LookupValuesPage getFieldTypeahead(String fieldName, String query) {return lookups.getFieldTypeahead(fieldName, query);}

	public LookupValuesList getFieldDropdown(String fieldName) {return lookups.getFieldDropdown(fieldName);}

	public AcctRow withPatch(final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		if (fieldChangeRequests.isEmpty())
		{
			return this;
		}

		if (readonly)
		{
			throw new AdempiereException("Row is readonly");
		}

		final FactAcctChanges.FactAcctChangesBuilder newChanges = userChanges.toBuilder();
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
				newChanges.postingSign(postingSign);
			}
			else if (FIELDNAME_Account_ID.equals(fieldName))
			{
				newChanges.accountId(event.getValueAsId(ElementValueId::ofRepoIdOrNull));
			}
			else if (FIELDNAME_Amount_DC.equals(fieldName))
			{
				final Money newAmountDC = Money.of(event.getValueAsBigDecimal(BigDecimal.ZERO), userChanges.getAmount_DC().getCurrencyId());
				final Money newAmountLC = currencyRate.convertToLocalCurrency(newAmountDC);
				newChanges.amount_DC(newAmountDC).amount_LC(newAmountLC);
			}
			else if (FIELDNAME_C_Tax_ID.equals(fieldName))
			{
				newChanges.taxId(event.getValueAsId(TaxId::ofRepoIdOrNull));
			}
			else if (FIELDNAME_Description.equals(fieldName))
			{
				newChanges.description(event.getValueAsString(null));
			}
			else if (FIELDNAME_M_SectionCode_ID.equals(fieldName))
			{
				newChanges.sectionCodeId(event.getValueAsId(SectionCodeId::ofRepoIdOrNull));
			}
			else if (FIELDNAME_M_Product_ID.equals(fieldName))
			{
				newChanges.productId(event.getValueAsId(ProductId::ofRepoIdOrNull));
			}
			else if (FIELDNAME_UserElementString1.equals(fieldName))
			{
				newChanges.userElementString1(event.getValueAsString(null));
			}
			else if (FIELDNAME_C_OrderSO_ID.equals(fieldName))
			{
				newChanges.salesOrderId(event.getValueAsId(OrderId::ofRepoIdOrNull));
			}
			else if (FIELDNAME_C_Activity_ID.equals(fieldName))
			{
				newChanges.activityId(event.getValueAsId(ActivityId::ofRepoIdOrNull));
			}
			else
			{
				throw new AdempiereException("Changing field `" + fieldName + "` is not allowed");
			}
		}

		return toBuilder().userChanges(newChanges.build()).build();
	}

	public @NonNull FactAcctChangesType getChangeType()
	{
		return userChanges.getType();
	}

	public AcctRow asRemoved()
	{
		if (userChanges.getType().isDelete())
		{
			return this;
		}

		return toBuilder()
				.userChanges(userChanges.toBuilder().type(FactAcctChangesType.Delete).build())
				.build();
	}
}
