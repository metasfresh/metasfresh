package de.metas.ui.web.quickinput.orderline;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_M_Product;
import org.compiere.util.DisplayType;

import de.metas.adempiere.model.I_C_Order;
import de.metas.handlingunits.order.api.IHUOrderBL;
import de.metas.ui.web.quickinput.IQuickInputDescriptorFactory;
import de.metas.ui.web.quickinput.QuickInput;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor.DocumentEntityDataBindingDescriptorBuilder;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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
	public DocumentEntityDescriptor.Builder createQuickInputEntityDescriptor(final DocumentEntityDescriptor.Builder documentDescriptor)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		
		final DocumentEntityDescriptor.Builder quickInputDescriptor = DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.QuickInput, documentDescriptor.getDocumentTypeId())
				.disableDefaultTableCallouts()
				.setDataBinding(DocumentEntityDataBindingDescriptorBuilder.NULL)
				// Defaults:
				.setDetailId(documentDescriptor.getDetailId())
				.setAD_Tab_ID(documentDescriptor.getAD_Tab_ID())
				.setTableName(documentDescriptor.getTableName())
				.setIsSOTrx(documentDescriptor.getIsSOTrx())
				//
				;

		quickInputDescriptor.addField(DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_M_Product_ID)
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
				.addCallout(this::onProductChangedCallout)
				.addCharacteristic(Characteristic.PublicField));

		quickInputDescriptor.addField(DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID)
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
		
		quickInputDescriptor.addField(DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_Qty)
				.setCaption(msgBL.translatable(IOrderLineQuickInput.COLUMNNAME_Qty))
				.setWidgetType(DocumentFieldWidgetType.Quantity)
				.setReadonlyLogic(ILogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ILogicExpression.TRUE)
				.setDisplayLogic(ILogicExpression.TRUE)
				.addCharacteristic(Characteristic.PublicField));

		return quickInputDescriptor;
	}
	
	private void onProductChangedCallout(final ICalloutField calloutField)
	{
		final QuickInput quickInput = QuickInput.getQuickInputOrNull(calloutField);
		if(quickInput == null)
		{
			return;
		}
		
		final IOrderLineQuickInput quickInputModel = quickInput.getQuickInputDocumentAs(IOrderLineQuickInput.class);
		final I_M_Product quickInputProduct = quickInputModel.getM_Product();
		
		final I_C_Order order = quickInput.getRootDocumentAs(I_C_Order.class);
		Services.get(IHUOrderBL.class).findM_HU_PI_Item_Product(order, quickInputProduct, quickInputModel::setM_HU_PI_Item_Product);
	}

}
