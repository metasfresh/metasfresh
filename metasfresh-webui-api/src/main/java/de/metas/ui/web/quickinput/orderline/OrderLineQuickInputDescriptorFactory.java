package de.metas.ui.web.quickinput.orderline;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.DisplayType;

import de.metas.adempiere.model.I_C_Order;
import de.metas.handlingunits.order.api.IHUOrderBL;
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
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
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

public final class OrderLineQuickInputDescriptorFactory implements IQuickInputDescriptorFactory
{
	public static final transient OrderLineQuickInputDescriptorFactory instance = new OrderLineQuickInputDescriptorFactory();

	private OrderLineQuickInputDescriptorFactory()
	{
		super();
	}

	@Override
	public QuickInputDescriptor createQuickInputEntityDescriptor(final DocumentType documentType, final DocumentId documentTypeId, final DetailId detailId)
	{
		final DocumentEntityDescriptor entityDescriptor = createEntityDescriptor(documentType, documentTypeId, detailId);
		final QuickInputLayoutDescriptor layout = createLayout(entityDescriptor);

		return QuickInputDescriptor.of(entityDescriptor, layout, OrderLineQuickInputProcessor.class);
	}

	private DocumentEntityDescriptor createEntityDescriptor(final DocumentType documentType, final DocumentId documentTypeId, final DetailId detailId)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final DocumentEntityDescriptor.Builder entityDescriptor = DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.QuickInput, documentTypeId)
				.disableDefaultTableCallouts()
				// Defaults:
				.setDetailId(detailId)
				.setTableName(I_C_OrderLine.Table_Name) // TODO: figure out if it's needed
		//
		;

		entityDescriptor.addField(DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_M_Product_ID)
				.setCaption(msgBL.translatable(IOrderLineQuickInput.COLUMNNAME_M_Product_ID))
				//
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptorProvider(SqlLookupDescriptor.builder()
						.setColumnName(IOrderLineQuickInput.COLUMNNAME_M_Product_ID)
						.setDisplayType(DisplayType.Search)
						.setAD_Val_Rule_ID(540051) // FIXME: hardcoded
						.buildProvider())
				.setValueClass(IntegerLookupValue.class)
				.setReadonlyLogic(ILogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ILogicExpression.TRUE)
				.setDisplayLogic(ILogicExpression.TRUE)
				.addCallout(OrderLineQuickInputDescriptorFactory::onProductChangedCallout)
				.addCharacteristic(Characteristic.PublicField));

		entityDescriptor.addField(DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID)
				.setCaption(msgBL.translatable(IOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID))
				//
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptorProvider(SqlLookupDescriptor.builder()
						.setColumnName(IOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID)
						.setDisplayType(DisplayType.TableDir)
						.setAD_Val_Rule_ID(540199) // FIXME: hardcoded
						.buildProvider())
				.setValueClass(IntegerLookupValue.class)
				.setReadonlyLogic(ILogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ILogicExpression.TRUE)
				.setDisplayLogic(ILogicExpression.TRUE)
				.addCharacteristic(Characteristic.PublicField));

		entityDescriptor.addField(DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_Qty)
				.setCaption(msgBL.translatable(IOrderLineQuickInput.COLUMNNAME_Qty))
				.setWidgetType(DocumentFieldWidgetType.Quantity)
				.setReadonlyLogic(ILogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ILogicExpression.TRUE)
				.setDisplayLogic(ILogicExpression.TRUE)
				.addCharacteristic(Characteristic.PublicField));

		return entityDescriptor.build();
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

	private QuickInputLayoutDescriptor createLayout(final DocumentEntityDescriptor entityDescriptor)
	{
		final DocumentFieldDescriptor field_M_Product_ID = entityDescriptor.getField("M_Product_ID");
		final DocumentFieldDescriptor field_M_HU_PI_Item_Product_ID = entityDescriptor.getField("M_HU_PI_Item_Product_ID");
		final DocumentFieldDescriptor field_Qty = entityDescriptor.getField("Qty");

		final QuickInputLayoutDescriptor.Builder quickInputLayout = QuickInputLayoutDescriptor.builder()
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaption(field_M_Product_ID.getCaption())
						// .setDescription(field_M_Product_ID.getDescription())
						.setWidgetType(field_M_Product_ID.getWidgetType())
						.addField(DocumentLayoutElementFieldDescriptor.builder(field_M_Product_ID.getFieldName())
								.setPublicField(true)
								.setLookupSource(field_M_Product_ID.getLookupSourceType()))
						.addField(DocumentLayoutElementFieldDescriptor.builder(field_M_HU_PI_Item_Product_ID.getFieldName())
								.setPublicField(true)
								.setLookupSource(field_M_HU_PI_Item_Product_ID.getLookupSourceType())))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaption(field_Qty.getCaption())
						// .setDescription(field_Qty.getDescription())
						.setWidgetType(field_Qty.getWidgetType())
						.addField(DocumentLayoutElementFieldDescriptor.builder(field_Qty.getFieldName())
								.setPublicField(true)
								.setLookupSource(field_Qty.getLookupSourceType())));

		return quickInputLayout.build();
	}

}
