package de.metas.ui.web.quickinput.orderline;

import java.util.Set;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.DisplayType;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.model.I_C_Order;
import de.metas.handlingunits.order.api.IHUOrderBL;
import de.metas.i18n.IMsgBL;
import de.metas.ui.web.quickinput.IQuickInputDescriptorFactory;
import de.metas.ui.web.quickinput.QuickInput;
import de.metas.ui.web.quickinput.QuickInputDescriptor;
import de.metas.ui.web.quickinput.QuickInputLayoutDescriptor;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Builder;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor;
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

	private static DocumentEntityDescriptor createEntityDescriptor(
			final DocumentType documentType,
			final DocumentId documentTypeId,
			final DetailId detailId)
	{
		final DocumentEntityDescriptor.Builder descriptorBuilder = createDescriptorBuilder(documentTypeId, detailId);

		descriptorBuilder.addField(createProductFieldBuilder());
		descriptorBuilder.addField(createPackingInstructionFieldBuilder());
		descriptorBuilder.addField(createQuantityFieldBuilder());

		return descriptorBuilder.build();
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

	private static Builder createProductFieldBuilder()
	{
		final Builder productFieldBuilder = DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_M_Product_ID)
				.setCaption(Services.get(IMsgBL.class).translatable(IOrderLineQuickInput.COLUMNNAME_M_Product_ID))
				//
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptorProvider(ProductLookupDescriptor.builder()
						.bpartnerParamName(I_C_Order.COLUMNNAME_C_BPartner_ID)
						.dateParamName(I_C_Order.COLUMNNAME_DatePromised)
						.build())
				.setValueClass(IntegerLookupValue.class)
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
		final I_M_Product quickInputProduct = quickInputModel.getM_Product();

		final I_C_Order order = quickInput.getRootDocumentAs(I_C_Order.class);
		Services.get(IHUOrderBL.class).findM_HU_PI_Item_Product(order, quickInputProduct, quickInputModel::setM_HU_PI_Item_Product);
	}

	private static Builder createPackingInstructionFieldBuilder()
	{
		final Builder packingInstructionFieldBuilder = DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID)
				.setCaption(Services.get(IMsgBL.class).translatable(IOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID))
				//
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptorProvider(SqlLookupDescriptor.builder()
						.setColumnName(IOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID)
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
