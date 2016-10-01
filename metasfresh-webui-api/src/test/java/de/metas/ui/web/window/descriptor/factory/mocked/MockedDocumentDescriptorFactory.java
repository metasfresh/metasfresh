package de.metas.ui.web.window.descriptor.factory.mocked;

import java.util.HashMap;
import java.util.Map;

import org.adempiere.ad.expression.api.ILogicExpression;
import org.compiere.model.I_C_Order;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import de.metas.ui.web.WebRestApiApplication;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutColumnDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementGroupDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementLineDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSectionDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSideListDescriptor;
import de.metas.ui.web.window.descriptor.LayoutType;
import de.metas.ui.web.window.descriptor.POJODocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.POJODocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Service
@Profile(WebRestApiApplication.PROFILE_Test)
@Primary
public class MockedDocumentDescriptorFactory implements DocumentDescriptorFactory
{
	public static int AD_WINDOW_ID_SalesOrder = 143;
	private static int AD_Tab_SalesOrder_Header = 186;
	@SuppressWarnings("unused")
	private static int AD_Tab_SalesOrder_OrderLines = 187;

	// public static void main(String[] args)
	// {
	// System.out.println(new MockedDocumentDescriptorFactory().getDocumentDescriptor(AD_WINDOW_ID_SalesOrder));
	// }

	private final Map<Integer, Supplier<DocumentDescriptor>> descriptors = new HashMap<>();

	public MockedDocumentDescriptorFactory()
	{
		super();
		descriptors.put(AD_WINDOW_ID_SalesOrder, Suppliers.memoize(() -> createDescriptor_Order(AD_WINDOW_ID_SalesOrder)));
	}

	@Override
	public DocumentDescriptor getDocumentDescriptor(final int AD_Window_ID)
	{
		final Supplier<DocumentDescriptor> descriptorSupplier = descriptors.get(AD_Window_ID);
		final DocumentDescriptor descriptor = descriptorSupplier == null ? null : descriptorSupplier.get();
		if (descriptor == null)
		{
			throw new IllegalArgumentException("No descriptor found for " + AD_Window_ID);
		}
		return descriptor;
	}

	private static DocumentDescriptor createDescriptor_Order(final int AD_Window_ID)
	{
		final DocumentEntityDescriptor.Builder entityDescriptorBuilder = DocumentEntityDescriptor.builder()
				.setDetailId(null)
				//
				.setAllowCreateNewLogic(ILogicExpression.TRUE)
				.setAllowDeleteLogic(ILogicExpression.TRUE)
				//
				.setAD_Window_ID(AD_Window_ID)
				.setAD_Tab_ID(AD_Tab_SalesOrder_Header)
				.setTabNo(0)
				.setTableName(I_C_Order.Table_Name)
				.setIsSOTrx(true)
				//
				.setDataBinding(POJODocumentEntityDataBindingDescriptor.ofClass(I_C_Order.class))
				.addField(DocumentFieldDescriptor.builder()
						.setFieldName(I_C_Order.COLUMNNAME_C_Order_ID)
						.setKey(true)
						.setValueClass(Integer.class)
						.setWidgetType(DocumentFieldWidgetType.Integer)
						.setDisplayLogic(ILogicExpression.FALSE)
						.addCharacteristic(Characteristic.PublicField) // key is always public
						.setDataBinding(POJODocumentFieldDataBindingDescriptor.of(I_C_Order.COLUMNNAME_C_Order_ID)))
				.addField(DocumentFieldDescriptor.builder()
						.setFieldName(I_C_Order.COLUMNNAME_DocumentNo)
						.setValueClass(String.class)
						.setWidgetType(DocumentFieldWidgetType.Text)
						.setDisplayLogic(ILogicExpression.TRUE)
						.addCharacteristic(Characteristic.PublicField) // key is always public
						.setDataBinding(POJODocumentFieldDataBindingDescriptor.of(I_C_Order.COLUMNNAME_DocumentNo)))
						//
						;

		final DocumentLayoutDescriptor.Builder layoutBuilder = DocumentLayoutDescriptor.builder()
				.setGridView(DocumentLayoutDetailDescriptor.builder())
				.setAdvancedView(DocumentLayoutDetailDescriptor.builder())
				.addSection(DocumentLayoutSectionDescriptor.builder()
						.addColumn(DocumentLayoutColumnDescriptor.builder()
								.addElementGroup(DocumentLayoutElementGroupDescriptor.builder()
										.addElementLine(DocumentLayoutElementLineDescriptor.builder()
												.addElement(DocumentLayoutElementDescriptor.builder()
														.setWidgetType(DocumentFieldWidgetType.Integer)
														.setLayoutType(LayoutType.primary)
														.addField(DocumentLayoutElementFieldDescriptor.builder(I_C_Order.COLUMNNAME_C_Order_ID)))
												.addElement(DocumentLayoutElementDescriptor.builder()
														.setWidgetType(DocumentFieldWidgetType.Text)
														.setLayoutType(LayoutType.primary)
														.addField(DocumentLayoutElementFieldDescriptor.builder(I_C_Order.COLUMNNAME_DocumentNo)))))))
														//
														;

		layoutBuilder.setSideList(DocumentLayoutSideListDescriptor.builder()
				.setAD_Window_ID(AD_Window_ID)
				.build());

		return DocumentDescriptor.builder()
				.setLayout(layoutBuilder.build())
				.setEntityDescriptor(entityDescriptorBuilder.build())
				.build();
	}
}
