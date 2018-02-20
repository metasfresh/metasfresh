package de.metas.ui.web.quickinput.orderline;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.Set;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.DisplayType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.model.I_C_Order;
import de.metas.handlingunits.order.api.IHUOrderBL;
import de.metas.i18n.IMsgBL;
import de.metas.ui.web.material.adapter.AvailableStockAdapter;
import de.metas.ui.web.quickinput.IQuickInputDescriptorFactory;
import de.metas.ui.web.quickinput.QuickInput;
import de.metas.ui.web.quickinput.QuickInputConstants;
import de.metas.ui.web.quickinput.QuickInputDescriptor;
import de.metas.ui.web.quickinput.QuickInputLayoutDescriptor;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Builder;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.ProductAndAttributes;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;

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
	@Autowired
	private AvailableStockAdapter availableStockService;

	@Override
	public Set<MatchingKey> getMatchingKeys()
	{
		return ImmutableSet.of(MatchingKey.ofTableName(I_C_OrderLine.Table_Name));
	}

	@Override
	public QuickInputDescriptor createQuickInputEntityDescriptor(final DocumentType documentType, final DocumentId documentTypeId, final DetailId detailId)
	{
		final DocumentEntityDescriptor entityDescriptor = createEntityDescriptor(documentType, documentTypeId, detailId);
		final QuickInputLayoutDescriptor layout = createLayout(entityDescriptor);

		return QuickInputDescriptor.of(entityDescriptor, layout, OrderLineQuickInputProcessor.class);
	}

	private DocumentEntityDescriptor createEntityDescriptor(
			final DocumentType documentType,
			final DocumentId documentTypeId,
			final DetailId detailId)
	{
		return createDescriptorBuilder(documentTypeId, detailId)
				.addField(createProductFieldBuilder())
				.addFieldIf(QuickInputConstants.isEnablePackingInstructionsField(), () -> createPackingInstructionFieldBuilder())
				.addField(createQuantityFieldBuilder())
				.build();
	}

	private static DocumentEntityDescriptor.Builder createDescriptorBuilder(final DocumentId documentTypeId, final DetailId detailId)
	{
		final DocumentEntityDescriptor.Builder entityDescriptor = DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.QuickInput, documentTypeId)
				.disableDefaultTableCallouts()
				// Defaults:
				.setDetailId(detailId)
				.setTableName(I_C_OrderLine.Table_Name) // TODO: figure out if it's needed
		;
		return entityDescriptor;
	}

	private Builder createProductFieldBuilder()
	{
		final Builder productFieldBuilder = DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_M_Product_ID)
				.setCaption(Services.get(IMsgBL.class).translatable(IOrderLineQuickInput.COLUMNNAME_M_Product_ID))
				//
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptorProvider(ProductLookupDescriptor.builderWithStockInfo()
						.bpartnerParamName(I_C_Order.COLUMNNAME_C_BPartner_ID)
						.availableStockDateParamName(I_C_Order.COLUMNNAME_PreparationDate)
						.pricingDateParamName(I_C_Order.COLUMNNAME_PreparationDate)
						.availableStockService(availableStockService)
						.build())
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ConstantLogicExpression.TRUE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.addCallout(OrderLineQuickInputDescriptorFactory::onProductChangedCallout)
				.addCharacteristic(Characteristic.PublicField);
		return productFieldBuilder;
	}

	private static void onProductChangedCallout(final ICalloutField calloutField)
	{
		final QuickInput quickInput = QuickInput.getQuickInputOrNull(calloutField);
		if (quickInput == null)
		{
			return;
		}

		final IOrderLineQuickInput quickInputModel = quickInput.getQuickInputDocumentAs(IOrderLineQuickInput.class);
		final LookupValue productLookupValue = quickInputModel.getM_Product_ID();
		if (productLookupValue == null)
		{
			return;
		}

		final ProductAndAttributes productAndAttributes = ProductLookupDescriptor.toProductAndAttributes(productLookupValue);
		final I_M_Product quickInputProduct = load(productAndAttributes.getProductId(), I_M_Product.class);

		final I_C_Order order = quickInput.getRootDocumentAs(I_C_Order.class);
		Services.get(IHUOrderBL.class).findM_HU_PI_Item_Product(order, quickInputProduct, quickInputModel::setM_HU_PI_Item_Product);
	}

	private boolean isUsePackingInstructionsField()
	{
		return false;
	}

	private static Builder createPackingInstructionFieldBuilder()
	{
		final Builder packingInstructionFieldBuilder = DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID)
				.setCaption(Services.get(IMsgBL.class).translatable(IOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID))
				//
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptorProvider(SqlLookupDescriptor.builder()
						.setCtxTableName(null) // ctxTableName
						.setCtxColumnName(IOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID)
						.setDisplayType(DisplayType.TableDir)
						.setAD_Val_Rule_ID(540199) // FIXME: hardcoded "M_HU_PI_Item_Product_For_Org_and_Product_and_DateOrdered"
						.buildProvider())
				.setValueClass(IntegerLookupValue.class)
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ConstantLogicExpression.FALSE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.addCharacteristic(Characteristic.PublicField);
		return packingInstructionFieldBuilder;
	}

	private static Builder createQuantityFieldBuilder()
	{
		final Builder qtyFieldBuilder = DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_Qty)
				.setCaption(Services.get(IMsgBL.class).translatable(IOrderLineQuickInput.COLUMNNAME_Qty))
				.setWidgetType(DocumentFieldWidgetType.Quantity)
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ConstantLogicExpression.TRUE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.addCharacteristic(Characteristic.PublicField);
		return qtyFieldBuilder;
	}

	private static QuickInputLayoutDescriptor createLayout(final DocumentEntityDescriptor entityDescriptor)
	{
		return QuickInputLayoutDescriptor.build(entityDescriptor, new String[][] {
				{ "M_Product_ID", "M_HU_PI_Item_Product_ID" } //
				, { "Qty" }
		});
	}
}
