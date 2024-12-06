package de.metas.acct.gljournal_sap.select_open_items;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.document.engine.DocStatus;
import de.metas.elementvalue.ElementValueService;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_M_SectionCode;

@UtilityClass
class OIViewFilterHelper
{
	static final String FILTER_ID = "default";
	static String PARAM_Account_ID = I_Fact_Acct.COLUMNNAME_Account_ID;
	static String PARAM_C_BPartner_ID = I_Fact_Acct.COLUMNNAME_C_BPartner_ID;
	static String PARAM_C_OrderSO_ID = I_Fact_Acct.COLUMNNAME_C_OrderSO_ID;
	static String PARAM_DateAcct = I_Fact_Acct.COLUMNNAME_DateAcct;
	static String PARAM_DocumentNo = I_Fact_Acct.COLUMNNAME_DocumentNo;
	static String PARAM_Description = I_Fact_Acct.COLUMNNAME_Description;
	static String PARAM_M_SectionCode_ID = I_Fact_Acct.COLUMNNAME_M_SectionCode_ID;
	static String PARAM_DocStatus = I_Fact_Acct.COLUMNNAME_DocStatus;
	static String PARAM_POReference = I_Fact_Acct.COLUMNNAME_POReference;
	static String PARAM_UserElementString1 = I_Fact_Acct.COLUMNNAME_UserElementString1;
	static String PARAM_UserElementString2 = I_Fact_Acct.COLUMNNAME_UserElementString2;
	static String PARAM_UserElementString3 = I_Fact_Acct.COLUMNNAME_UserElementString3;
	static String PARAM_UserElementString4 = I_Fact_Acct.COLUMNNAME_UserElementString4;
	static String PARAM_UserElementString5 = I_Fact_Acct.COLUMNNAME_UserElementString5;
	static String PARAM_UserElementString6 = I_Fact_Acct.COLUMNNAME_UserElementString6;
	static String PARAM_UserElementString7 = I_Fact_Acct.COLUMNNAME_UserElementString7;

	public static DocumentFilterDescriptor createFilterDescriptor(
			@NonNull final LookupDescriptorProviders lookupDescriptorProviders,
			@NonNull final ElementValueService elementValueService,
			@NonNull final AcctSchema acctSchema)
	{
		final DocumentFilterDescriptor.Builder builder = DocumentFilterDescriptor.builder()
				.setFilterId(FILTER_ID)
				.setFrequentUsed(false)
				.setDisplayName("default")
				.addParameter(newParamDescriptor(PARAM_Account_ID)
						.widgetType(DocumentFieldWidgetType.Lookup)
						.lookupDescriptor(lookupDescriptorProviders.searchInTable(I_C_ElementValue.Table_Name, elementValueService.isOpenItemRule()).provideForFilter()))
				.addParameter(newParamDescriptor(PARAM_C_BPartner_ID)
						.widgetType(DocumentFieldWidgetType.Lookup)
						.lookupDescriptor(lookupDescriptorProviders.searchInTable(I_C_BPartner.Table_Name).provideForFilter()))
				.addParameter(newParamDescriptor(PARAM_C_OrderSO_ID)
						.widgetType(DocumentFieldWidgetType.Lookup)
						.lookupDescriptor(lookupDescriptorProviders.searchInTable(I_C_Order.Table_Name).provideForFilter()))
				.addParameter(newParamDescriptor(PARAM_DateAcct)
						.widgetType(DocumentFieldWidgetType.LocalDate)
						.operator(DocumentFilterParam.Operator.BETWEEN))
				.addParameter(newParamDescriptor(PARAM_DocumentNo)
						.widgetType(DocumentFieldWidgetType.Text))
				.addParameter(newParamDescriptor(PARAM_Description)
						.widgetType(DocumentFieldWidgetType.Text))
				.addParameter(newParamDescriptor(PARAM_M_SectionCode_ID)
						.widgetType(DocumentFieldWidgetType.Lookup)
						.lookupDescriptor(lookupDescriptorProviders.searchInTable(I_M_SectionCode.Table_Name).provideForFilter()))
				.addParameter(newParamDescriptor(PARAM_DocStatus)
						.widgetType(DocumentFieldWidgetType.List)
						.lookupDescriptor(lookupDescriptorProviders.listByAD_Reference_Value_ID(DocStatus.AD_REFERENCE_ID).provideForFilter()))
				.addParameter(newParamDescriptor(PARAM_POReference)
						.widgetType(DocumentFieldWidgetType.Text));

		if (acctSchema.isElementEnabled(AcctSchemaElementType.UserElementString1))
		{
			builder.addParameter(newParamDescriptor(PARAM_UserElementString1).widgetType(DocumentFieldWidgetType.Text));
		}
		if (acctSchema.isElementEnabled(AcctSchemaElementType.UserElementString2))
		{
			builder.addParameter(newParamDescriptor(PARAM_UserElementString2).widgetType(DocumentFieldWidgetType.Text));
		}
		if (acctSchema.isElementEnabled(AcctSchemaElementType.UserElementString3))
		{
			builder.addParameter(newParamDescriptor(PARAM_UserElementString3).widgetType(DocumentFieldWidgetType.Text));
		}
		if (acctSchema.isElementEnabled(AcctSchemaElementType.UserElementString4))
		{
			builder.addParameter(newParamDescriptor(PARAM_UserElementString4).widgetType(DocumentFieldWidgetType.Text));
		}
		if (acctSchema.isElementEnabled(AcctSchemaElementType.UserElementString5))
		{
			builder.addParameter(newParamDescriptor(PARAM_UserElementString5).widgetType(DocumentFieldWidgetType.Text));
		}
		if (acctSchema.isElementEnabled(AcctSchemaElementType.UserElementString6))
		{
			builder.addParameter(newParamDescriptor(PARAM_UserElementString6).widgetType(DocumentFieldWidgetType.Text));
		}
		if (acctSchema.isElementEnabled(AcctSchemaElementType.UserElementString7))
		{
			builder.addParameter(newParamDescriptor(PARAM_UserElementString7).widgetType(DocumentFieldWidgetType.Text));
		}

		return builder.build();
	}

	private static DocumentFilterParamDescriptor.Builder newParamDescriptor(final String fieldName)
	{
		return DocumentFilterParamDescriptor.builder()
				.fieldName(fieldName)
				.displayName(TranslatableStrings.adElementOrMessage(fieldName));
	}
}
