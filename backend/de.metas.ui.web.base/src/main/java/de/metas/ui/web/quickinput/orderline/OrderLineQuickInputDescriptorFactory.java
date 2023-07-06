package de.metas.ui.web.quickinput.orderline;

import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.lang.SOTrx;
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfigRepo;
import de.metas.ui.web.material.adapter.AvailableForSaleAdapter;
import de.metas.ui.web.material.adapter.AvailableToPromiseAdapter;
import de.metas.ui.web.quickinput.IQuickInputDescriptorFactory;
import de.metas.ui.web.quickinput.QuickInputConstants;
import de.metas.ui.web.quickinput.QuickInputDescriptor;
import de.metas.ui.web.quickinput.QuickInputLayoutDescriptor;
import de.metas.ui.web.quickinput.field.PackingItemProductFieldHelper;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptorProviderBuilder;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.adempiere.ad.expression.api.impl.LogicExpressionCompiler;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.DisplayType;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Component
/* package */ final class OrderLineQuickInputDescriptorFactory implements IQuickInputDescriptorFactory
{
	// FIXME: hardcoded "VAT_Code_for_SO"
	public static final AdValRuleId AD_VAL_RULE_VAT_Code_for_SO = AdValRuleId.ofRepoId(540610);
	
	// FIXME: hardcoded "VAT_Code_for_PO"
	public static final AdValRuleId AD_VAL_RULE_VAT_Code_for_PO = AdValRuleId.ofRepoId(540611);

	private static final String SYS_CONFIG_FilterFlatrateConditionsADValRule = "OrderLineQuickInputDescriptorFactory.FilterFlatrateConditionsADValRule";

	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final AvailableToPromiseAdapter availableToPromiseAdapter;
	private final AvailableForSaleAdapter availableForSaleAdapter;
	private final AvailableForSalesConfigRepo availableForSalesConfigRepo;
	private final LookupDescriptorProviders lookupDescriptorProviders;

	private final OrderLineQuickInputCallout callout;

	public OrderLineQuickInputDescriptorFactory(
			@NonNull final IBPartnerBL bpartnersService,
			@NonNull final AvailableToPromiseAdapter availableToPromiseAdapter,
			@NonNull final AvailableForSaleAdapter availableForSaleAdapter,
			@NonNull final AvailableForSalesConfigRepo availableForSalesConfigRepo,
			@NonNull final LookupDescriptorProviders lookupDescriptorProviders,
			@NonNull final PackingItemProductFieldHelper packingItemProductFieldHelper,
			@NonNull final UserSession userSession)
	{
		this.availableToPromiseAdapter = availableToPromiseAdapter;
		this.availableForSaleAdapter = availableForSaleAdapter;
		this.availableForSalesConfigRepo = availableForSalesConfigRepo;
		this.lookupDescriptorProviders = lookupDescriptorProviders;

		callout = OrderLineQuickInputCallout.builder()
				.bpartnersService(bpartnersService)
				.packingItemProductFieldHelper(packingItemProductFieldHelper)
				.userSession(userSession)
				.build();
	}

	@Override
	public Set<MatchingKey> getMatchingKeys()
	{
		return ImmutableSet.of(MatchingKey.ofTableName(I_C_OrderLine.Table_Name));
	}

	@Override
	public QuickInputDescriptor createQuickInputDescriptor(
			final DocumentType documentType,
			final DocumentId documentTypeId,
			final DetailId detailId,
			@NonNull final Optional<SOTrx> soTrx)
	{
		final DocumentEntityDescriptor entityDescriptor = createEntityDescriptor(
				documentTypeId,
				detailId,
				soTrx);

		final QuickInputLayoutDescriptor layout = createLayout(entityDescriptor);

		return QuickInputDescriptor.of(
				entityDescriptor,
				layout,
				OrderLineQuickInputProcessor.class);
	}

	private DocumentEntityDescriptor createEntityDescriptor(
			final DocumentId rootDocumentTypeId,
			final DetailId tabId,
			@NonNull final Optional<SOTrx> soTrx)
	{
		return DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.QuickInput, rootDocumentTypeId)
				.setIsSOTrx(soTrx)
				.disableDefaultTableCallouts()
				.setDetailId(tabId)
				.setTableName(I_C_OrderLine.Table_Name) // TODO: figure out if it's needed
				//
				.addField(createProductField(soTrx))
				.addFieldIf(QuickInputConstants.isEnablePackingInstructionsField(), this::createPackingInstructionField)
				.addFieldIf(QuickInputConstants.isEnableVatCodeField(), () -> this.createVatCodeField(soTrx))
				.addField(createCompensationGroupSchemaField())
				.addField(createContractConditionsField())
				.addField(createQuantityField())
				.addFieldIf(QuickInputConstants.isEnableBestBeforePolicy(), this::createBestBeforePolicyField)
				//
				.build();
	}

	private DocumentFieldDescriptor.Builder createProductField(@NonNull final Optional<SOTrx> soTrx)
	{
		final ProductLookupDescriptor productLookupDescriptor = createProductLookupDescriptor(soTrx);
		final ITranslatableString caption = msgBL.translatable(IOrderLineQuickInput.COLUMNNAME_M_Product_ID);

		return DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_M_Product_ID)
				.setLookupDescriptorProvider(productLookupDescriptor)
				.setCaption(caption)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ConstantLogicExpression.TRUE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.addCallout(callout::onProductChanged)
				.addCharacteristic(Characteristic.PublicField);
	}

	private ProductLookupDescriptor createProductLookupDescriptor(@NonNull final Optional<SOTrx> soTrx)
	{
		if (soTrx.orElse(SOTrx.PURCHASE).isSales())
		{
			return ProductLookupDescriptor
					.builderWithStockInfo()
					.bpartnerParamName(I_C_Order.COLUMNNAME_C_BPartner_ID)
					.pricingDateParamName(I_C_Order.COLUMNNAME_DatePromised)
					.hideDiscontinued(true)
					.availableStockDateParamName(I_C_Order.COLUMNNAME_PreparationDate)
					.availableToPromiseAdapter(availableToPromiseAdapter)
					.availableForSaleAdapter(availableForSaleAdapter)
					.availableForSalesConfigRepo(availableForSalesConfigRepo)
					.build();
		}
		else
		{
			return ProductLookupDescriptor
					.builderWithStockInfo()
					.bpartnerParamName(I_C_Order.COLUMNNAME_C_BPartner_ID)
					.pricingDateParamName(I_C_Order.COLUMNNAME_DatePromised)
					.hideDiscontinued(true)
					.availableStockDateParamName(I_C_Order.COLUMNNAME_DatePromised)
					.availableToPromiseAdapter(availableToPromiseAdapter)
					.availableForSaleAdapter(availableForSaleAdapter)
					.availableForSalesConfigRepo(availableForSalesConfigRepo)
					.build();
		}
	}

	private DocumentFieldDescriptor.Builder createPackingInstructionField()
	{
		return DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID)
				.setCaption(msgBL.translatable(IOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID))
				//
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptorProvider(lookupDescriptorProviders.sql()
													 .setCtxTableName(null) // ctxTableName
													 .setCtxColumnName(IOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID)
													 .setDisplayType(DisplayType.TableDir)
													 .setAD_Val_Rule_ID(AdValRuleId.ofRepoId(540365))// FIXME: hardcoded "M_HU_PI_Item_Product_For_Org_and_Product_and_DatePromised"
													 .build())
				.setValueClass(IntegerLookupValue.class)
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ConstantLogicExpression.FALSE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.addCharacteristic(Characteristic.PublicField);
	}

	private DocumentFieldDescriptor.Builder createVatCodeField(@NonNull final Optional<SOTrx> soTrx)
	{
		final SqlLookupDescriptorProviderBuilder descriptorProviderBuilder = lookupDescriptorProviders.sql()
				.setCtxTableName(null) // ctxTableName
				.setCtxColumnName(IOrderLineQuickInput.COLUMNNAME_C_VAT_Code_ID)
				.setDisplayType(DisplayType.TableDir)
				.setPageLength(QuickInputConstants.BIG_ENOUGH_PAGE_LENGTH);
		if (soTrx.orElse(SOTrx.PURCHASE).isSales())
		{
			descriptorProviderBuilder.setAD_Val_Rule_ID(AD_VAL_RULE_VAT_Code_for_SO);
		}
		else
		{
			descriptorProviderBuilder.setAD_Val_Rule_ID(AD_VAL_RULE_VAT_Code_for_PO);
		}

		return DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_C_VAT_Code_ID)
				.setCaption(msgBL.translatable(IOrderLineQuickInput.COLUMNNAME_C_VAT_Code_ID))
				//
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptorProvider(descriptorProviderBuilder.build())
				.setValueClass(IntegerLookupValue.class)
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ConstantLogicExpression.TRUE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.addCharacteristic(Characteristic.PublicField);
	}

	private DocumentFieldDescriptor.Builder createQuantityField()
	{
		return DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_Qty)
				.setCaption(msgBL.translatable(IOrderLineQuickInput.COLUMNNAME_Qty))
				.setWidgetType(DocumentFieldWidgetType.Quantity)
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ConstantLogicExpression.TRUE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.addCharacteristic(Characteristic.PublicField);
	}

	private DocumentFieldDescriptor.Builder createBestBeforePolicyField()
	{
		return DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_ShipmentAllocation_BestBefore_Policy)
				.setCaption(msgBL.translatable(IOrderLineQuickInput.COLUMNNAME_ShipmentAllocation_BestBefore_Policy))
				//
				.setWidgetType(DocumentFieldWidgetType.List)
				.setLookupDescriptorProvider(lookupDescriptorProviders.listByAD_Reference_Value_ID(ShipmentAllocationBestBeforePolicy.AD_REFERENCE_ID))
				.setValueClass(StringLookupValue.class)
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ConstantLogicExpression.FALSE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.addCharacteristic(Characteristic.PublicField);
	}

	private DocumentFieldDescriptor.Builder createCompensationGroupSchemaField()
	{
		return DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_C_CompensationGroup_Schema_ID)
				.setCaption(msgBL.translatable(IOrderLineQuickInput.COLUMNNAME_C_CompensationGroup_Schema_ID))
				.setWidgetType(DocumentFieldWidgetType.Integer)
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ConstantLogicExpression.FALSE)
				.setDisplayLogic(ConstantLogicExpression.FALSE);
	}

	private DocumentFieldDescriptor.Builder createContractConditionsField()
	{
		final ILogicExpression compensationGroupSchemaIsSet = LogicExpressionCompiler.instance.compile("@" + IOrderLineQuickInput.COLUMNNAME_C_CompensationGroup_Schema_ID + "/0@ > 0");

		final boolean isMandatory = compensationGroupSchemaIsSet.toOptionalBoolean()
				.orElse(QuickInputConstants.isContractConditionsFieldMandatory());

		final boolean isDisplayLogic = compensationGroupSchemaIsSet.toOptionalBoolean()
				.orElse(QuickInputConstants.isEnableContractConditionsField());

		return DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_C_Flatrate_Conditions_ID)
				.setCaption(msgBL.translatable(IOrderLineQuickInput.COLUMNNAME_C_Flatrate_Conditions_ID))
				//
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptorProvider(getLookupDescriptorProvider())
				.setValueClass(IntegerLookupValue.class)
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(isMandatory)
				.setDisplayLogic(isDisplayLogic)
				.addCharacteristic(Characteristic.PublicField);

	}

	private DocumentFieldDescriptor.Builder createInternalIntegerField(
			@NonNull final String fieldName,
			@Nullable final Integer defaultValue)
	{
		return DocumentFieldDescriptor.builder(fieldName)
				.setCaption(msgBL.translatable(fieldName))
				.setWidgetType(DocumentFieldWidgetType.Integer)
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ConstantLogicExpression.FALSE)
				.setDisplayLogic(ConstantLogicExpression.FALSE)
				.setDefaultValueExpression(defaultValue != null ? ConstantStringExpression.of(defaultValue.toString()) : null);
	}

	private DocumentFieldDescriptor.Builder createInternalYesNoField(
			@NonNull final String fieldName,
			final boolean defaultValue)
	{
		return DocumentFieldDescriptor.builder(fieldName)
				.setCaption(msgBL.translatable(fieldName))
				.setWidgetType(DocumentFieldWidgetType.YesNo)
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ConstantLogicExpression.TRUE)
				.setDisplayLogic(ConstantLogicExpression.FALSE)
				.setDefaultValueExpression(ConstantStringExpression.of(StringUtils.ofBoolean(defaultValue)));
	}

	private static QuickInputLayoutDescriptor createLayout(final DocumentEntityDescriptor entityDescriptor)
	{
		// IMPORTANT: if Qty is not the last field then frontend will not react on pressing "ENTER" to complete the entry
		return QuickInputLayoutDescriptor.onlyFields(entityDescriptor, new String[][] {
				{ IOrderLineQuickInput.COLUMNNAME_M_Product_ID, IOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID },
				{ IOrderLineQuickInput.COLUMNNAME_ShipmentAllocation_BestBefore_Policy },
				{ IOrderLineQuickInput.COLUMNNAME_C_Flatrate_Conditions_ID },
				{ IOrderLineQuickInput.COLUMNNAME_C_VAT_Code_ID },
				{ IOrderLineQuickInput.COLUMNNAME_Qty },
		});
	}

	@NonNull
	private LookupDescriptorProvider getLookupDescriptorProvider()
	{
		final AdValRuleId valueRuleId = AdValRuleId.ofRepoIdOrNull(sysConfigBL.getIntValue(SYS_CONFIG_FilterFlatrateConditionsADValRule, -1));

		if (valueRuleId == null)
		{
			return lookupDescriptorProviders.searchInTable(I_C_Flatrate_Conditions.Table_Name);
		}

		final SqlLookupDescriptorProviderBuilder descriptorProviderBuilder = lookupDescriptorProviders.sql()
				.setCtxTableName(I_C_Flatrate_Conditions.Table_Name)
				.setCtxColumnName(IOrderLineQuickInput.COLUMNNAME_C_Flatrate_Conditions_ID)
				.setDisplayType(DisplayType.Search)
				.setAD_Val_Rule_ID(valueRuleId);

		return descriptorProviderBuilder.build();
	}
}
