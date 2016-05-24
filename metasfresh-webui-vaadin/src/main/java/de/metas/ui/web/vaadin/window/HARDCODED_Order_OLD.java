package de.metas.ui.web.vaadin.window;

import java.math.BigDecimal;
import java.util.Date;

import org.compiere.model.I_C_Order;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;

import com.vaadin.server.VaadinService;

import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.descriptor.PropertyDescriptor;
import de.metas.ui.web.window.descriptor.PropertyDescriptorType;
import de.metas.ui.web.window.descriptor.PropertyLayoutInfo;
import de.metas.ui.web.window.descriptor.SqlLookupDescriptor;
import de.metas.ui.web.window.model.WindowModel;
import de.metas.ui.web.window.shared.datatype.ComposedValue;
import de.metas.ui.web.window.shared.datatype.GridRowId;
import de.metas.ui.web.window.shared.datatype.LookupValue;

/*
 * #%L
 * de.metas.ui.web.vaadin
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

public final class HARDCODED_Order_OLD
{
	public static final PropertyName ORDER_DocumentNo = PropertyName.of("DocumentNo");
	public static final PropertyName ORDER_DateOrdered = PropertyName.of("DateOrdered");
	public static final PropertyName ORDER_DatePromised = PropertyName.of("DatePromised");
	public static final PropertyName ORDER_PreparationDate = PropertyName.of("PreparationDate");
	public static final PropertyName ORDER_M_Warehouse_ID = PropertyName.of("M_Warehouse_ID");
	//
	public static final PropertyName ORDER_C_BPartner_ID = PropertyName.of("C_BPartner_ID");
	public static final PropertyName ORDER_C_BPartner_Location_ID = PropertyName.of("C_BPartner_Location_ID");
	public static final PropertyName ORDER_AD_User_ID = PropertyName.of("AD_User_ID");
	public static final PropertyName ORDER_BPartnerAndAddress = PropertyName.of(ORDER_C_BPartner_ID + "#" + ORDER_C_BPartner_Location_ID + "#" + ORDER_AD_User_ID);
	//
	public static final PropertyName ORDER_Bill_BPartner_ID = PropertyName.of("Bill_BPartner_ID");
	public static final PropertyName ORDER_Bill_Location_ID = PropertyName.of("Bill_Location_ID");
	public static final PropertyName ORDER_Bill_User_ID = PropertyName.of("Bill_User_ID");
	public static final PropertyName ORDER_BillBPartnerAndAddress = PropertyName.of(ORDER_Bill_BPartner_ID + "#" + ORDER_Bill_Location_ID + "#" + ORDER_Bill_User_ID);
	//
	public static final PropertyName ORDER_Lines = PropertyName.of("Lines");

	public static final PropertyName ORDER_C_Order_ID = PropertyName.of(I_C_Order.COLUMNNAME_C_Order_ID);
	public static final PropertyName ORDER_GrandTotal = PropertyName.of(I_C_Order.COLUMNNAME_GrandTotal);
	public static final PropertyName ORDER_TotalLines = PropertyName.of(I_C_Order.COLUMNNAME_TotalLines);

	public static final String STRINGEXPRESSION_TitleSummary = ""
			//+ "@" + WindowConstants.PROPERTYNAME_WindowRoot + "/@"
			+ "Window"
			+ " - @" + ORDER_DocumentNo + "/-@";
	public static final String STRINGEXPRESSION_RecordSummary = ""
			+ "@" + ORDER_DocumentNo + "/-@"
			+ "\n@" + ORDER_DatePromised + "/-@"
			+ "\n@" + ORDER_C_BPartner_ID + "/-@"
			+ "\n@" + ORDER_C_BPartner_Location_ID + "/-@";
	public static final String STRINGEXPRESSION_AdditionalRecordSummary = ""
			+ "Net total: @" + ORDER_TotalLines + "/0@"
			+ "\nGrand total: @" + ORDER_GrandTotal + "/0@";

	private static WindowModel singletonWindowModel;

	private HARDCODED_Order_OLD()
	{
		super();
	}

	public static PropertyDescriptor createRootPropertyDescriptor()
	{
		return PropertyDescriptor.builder()
				.setPropertyName(WindowConstants.PROPERTYNAME_WindowRoot)

		// currently the "main" table needs to be specified on this level, whereas "sub tables" like the ORDER_Lines declare their table in the respective child properties descriptor
				.setSqlTableName(I_C_Order.Table_Name)

		//
		// Group: Document
				.addChildPropertyDescriptor(PropertyDescriptor.builder()
						// this ChildPropertyDescriptor goes with the "main table"
						.setPropertyName("Document")
						.setType(PropertyDescriptorType.Group)
						//
						.addChildPropertyDescriptor(PropertyDescriptor.builder()
								.setPropertyName(ORDER_DocumentNo)
								.setSqlColumnName(I_C_Order.COLUMNNAME_DocumentNo)
								.setValueType(String.class)
								.build())
						.addChildPropertyDescriptor(PropertyDescriptor.builder()
								.setPropertyName(ORDER_DateOrdered)
								.setSqlColumnName(I_C_Order.COLUMNNAME_DateOrdered)
								.setValueType(Date.class)
								.build())
						.addChildPropertyDescriptor(PropertyDescriptor.builder()
								.setPropertyName(ORDER_DatePromised)
								.setSqlColumnName(I_C_Order.COLUMNNAME_DatePromised)
								.setValueType(Date.class)
								.build())
						.addChildPropertyDescriptor(PropertyDescriptor.builder()
								.setPropertyName(ORDER_PreparationDate)
								.setSqlColumnName(I_C_Order.COLUMNNAME_PreparationDate)
								.setValueType(Date.class)
								.build())
						.addChildPropertyDescriptor(PropertyDescriptor.builder()
								.setPropertyName(ORDER_M_Warehouse_ID)
								.setSqlColumnName(I_C_Order.COLUMNNAME_M_Warehouse_ID)
								.setValueType(LookupValue.class)
								.build())
						.addChildPropertyDescriptor(PropertyDescriptor.builder()
								.setPropertyName(ORDER_BPartnerAndAddress)
								.setType(PropertyDescriptorType.ComposedValue)
								.setValueType(ComposedValue.class)
								.setLayoutInfo(PropertyLayoutInfo.builder()
										.setNextColumn(true)
										.setRowsSpan(5)
										.build())
								.addChildPropertyDescriptor(PropertyDescriptor.builder()
										.setPropertyName(ORDER_C_BPartner_ID)
										.setComposedValuePartName("C_BPartner_ID")
										.setSqlColumnName(I_C_Order.COLUMNNAME_C_BPartner_ID)
										.setValueType(LookupValue.class)
										.build())
								.addChildPropertyDescriptor(PropertyDescriptor.builder()
										.setPropertyName(ORDER_C_BPartner_Location_ID)
										.setComposedValuePartName("C_BPartner_Location_ID")
										.setSqlColumnName(I_C_Order.COLUMNNAME_C_BPartner_Location_ID)
										.setValueType(LookupValue.class)
										.build())
								.addChildPropertyDescriptor(PropertyDescriptor.builder()
										.setPropertyName(ORDER_AD_User_ID)
										.setComposedValuePartName("AD_User_ID")
										.setSqlColumnName(I_C_Order.COLUMNNAME_AD_User_ID)
										.setValueType(LookupValue.class)
										.build())
								.build())
						.addChildPropertyDescriptor(PropertyDescriptor.builder()
								.setPropertyName(ORDER_BillBPartnerAndAddress)
								.setType(PropertyDescriptorType.ComposedValue)
								.setValueType(ComposedValue.class)
								.setLayoutInfo(PropertyLayoutInfo.builder()
										.setNextColumn(true)
										.setRowsSpan(5)
										.build())
								.addChildPropertyDescriptor(PropertyDescriptor.builder()
										.setPropertyName(ORDER_Bill_BPartner_ID)
										.setComposedValuePartName("C_BPartner_ID")
										.setSqlColumnName(I_C_Order.COLUMNNAME_Bill_BPartner_ID)
										.setSqlLookupDescriptor(SqlLookupDescriptor.of(DisplayType.Search, "Bill_BPartner_ID", 138))
										.setValueType(LookupValue.class)
										.build())
								.addChildPropertyDescriptor(PropertyDescriptor.builder()
										.setPropertyName(ORDER_Bill_Location_ID)
										.setComposedValuePartName("C_BPartner_Location_ID")
										.setSqlColumnName(I_C_Order.COLUMNNAME_Bill_Location_ID)
										.setSqlLookupDescriptor(SqlLookupDescriptor.of(DisplayType.Search, "Bill_Location_ID", 159))
										.setValueType(LookupValue.class)
										.build())
								.addChildPropertyDescriptor(PropertyDescriptor.builder()
										.setPropertyName(ORDER_Bill_User_ID)
										.setComposedValuePartName("AD_User_ID")
										.setSqlColumnName(I_C_Order.COLUMNNAME_Bill_User_ID)
										.setSqlLookupDescriptor(SqlLookupDescriptor.of(DisplayType.Search, "Bill_User_ID", 110))
										.setValueType(LookupValue.class)
										.build())
								.build())
						//
						.build())
				//
				// Group: Lines
				.addChildPropertyDescriptor(PropertyDescriptor.builder()
						.setPropertyName(ORDER_Lines)
						.setType(PropertyDescriptorType.Tabular)
						.setSqlTableName(I_C_OrderLine.Table_Name)
						.setSqlParentLinkColumnName(I_C_Order.COLUMNNAME_C_Order_ID)
						//
						.addChildPropertyDescriptor(PropertyDescriptor.builder()
								.setPropertyName("LineNo")
								.setValueType(Integer.class)
								.setSqlColumnName(I_C_OrderLine.COLUMNNAME_Line)
								.build())
						.addChildPropertyDescriptor(PropertyDescriptor.builder()
								.setPropertyName("M_Product_ID")
								.setValueType(LookupValue.class)
								.setSqlColumnName(I_C_OrderLine.COLUMNNAME_M_Product_ID)
								.build())
						.addChildPropertyDescriptor(PropertyDescriptor.builder()
								.setPropertyName("M_AttributeSetInstance_ID")
								.setValueType(LookupValue.class)
								.setSqlColumnName(I_C_OrderLine.COLUMNNAME_M_AttributeSetInstance_ID)
								.build())
						.addChildPropertyDescriptor(PropertyDescriptor.builder()
								.setPropertyName("QtyTU")
								.setValueType(BigDecimal.class)
								.setSqlColumnName(I_C_OrderLine.COLUMNNAME_QtyEnteredTU)
								.setSqlDisplayType(DisplayType.Quantity)
								.build())
						.addChildPropertyDescriptor(PropertyDescriptor.builder()
								.setPropertyName("M_HU_PI_Item_Product_ID")
								.setValueType(LookupValue.class)
								.setSqlColumnName(I_C_OrderLine.COLUMNNAME_M_HU_PI_Item_Product_ID)
								.build())
						.build())
				//
				// Group: Shipping
				.addChildPropertyDescriptor(PropertyDescriptor.builder()
						.setPropertyName("Shipping")
						.setType(PropertyDescriptorType.Group)
						.build())
				//
				// Group: Pricing
				.addChildPropertyDescriptor(PropertyDescriptor.builder()
						.setPropertyName("Pricing")
						.setType(PropertyDescriptorType.Group)
						// .addChildPropertyDescriptor(PropertyDescriptor.builder()
						// .setPropertyName("PriceActual")
						// .setValueType(java.math.BigDecimal.class)
						// .build())
						.build())
				//
				// Group: History
				.addChildPropertyDescriptor(PropertyDescriptor.builder()
						.setPropertyName("History")
						.setType(PropertyDescriptorType.Group)
						.build())
				//
				// Group: Accounting
				.addChildPropertyDescriptor(PropertyDescriptor.builder()
						.setPropertyName("Accounting")
						.setType(PropertyDescriptorType.Group)
						.build())
				//
				// Additional hidden fields
				.addChildPropertyDescriptor(PropertyDescriptor.builder()
						.setPropertyName(ORDER_C_Order_ID)
						.setValueType(Integer.class)
						.setSqlColumnName(I_C_Order.COLUMNNAME_C_Order_ID)
						.setSqlDisplayType(DisplayType.ID)
						.setLayoutInfo(PropertyLayoutInfo.builder()
								.setDisplayed(false)
								.build())
						.build())
				.addChildPropertyDescriptor(PropertyDescriptor.builder()
						.setPropertyName(ORDER_GrandTotal)
						.setValueType(BigDecimal.class)
						.setSqlColumnName(I_C_Order.COLUMNNAME_GrandTotal)
						.setSqlDisplayType(DisplayType.Amount)
						.setLayoutInfo(PropertyLayoutInfo.builder()
								.setDisplayed(false)
								.build())
						.build())
				.addChildPropertyDescriptor(PropertyDescriptor.builder()
						.setPropertyName(ORDER_TotalLines)
						.setValueType(BigDecimal.class)
						.setSqlColumnName(I_C_Order.COLUMNNAME_TotalLines)
						.setSqlDisplayType(DisplayType.Amount)
						.setLayoutInfo(PropertyLayoutInfo.builder()
								.setDisplayed(false)
								.build())
						.build())
				//
				//
				.build();
	}

	public static synchronized WindowModel getSingletonWindowModel()
	{
		if (VaadinService.getCurrentRequest().getParameter("restartApplication") != null)
		{
			System.out.println("Restarting singletonWindowModel: " + singletonWindowModel);
			singletonWindowModel = null;
		}

		if (singletonWindowModel == null)
		{
			final WindowModel windowModel = new WindowModel();
			windowModel.setRootPropertyDescriptor(createRootPropertyDescriptor());
			singletonWindowModel = windowModel;
		}

		return singletonWindowModel;
	}

	public static final void createHardcodedData(WindowModel model)
	{
		model.setProperty(ORDER_DocumentNo, "123456");
		model.setProperty(ORDER_DatePromised, TimeUtil.getDay(2016, 4 + 1, 24));
		model.setProperty(ORDER_M_Warehouse_ID, LookupValue.of(540008, "Hauptlager"));

		model.setProperty(ORDER_C_BPartner_ID, LookupValue.of(1234, "G0105 - ABC Vegetables"));
		model.setProperty(ORDER_C_BPartner_Location_ID, LookupValue.of(1234, "Johannes-R.-Becher-Straße 3-11"));
		model.setProperty(ORDER_AD_User_ID, LookupValue.of(1234, "Max Mustermann"));

		model.setProperty(ORDER_Bill_BPartner_ID, LookupValue.of(1234, "Bill bpartner"));
		model.setProperty(ORDER_Bill_Location_ID, LookupValue.of(1234, "Bill address"));
		model.setProperty(ORDER_Bill_User_ID, LookupValue.of(1234, "Bill user"));

		// final GridPropertyValue lines = (GridPropertyValue)properties.getPropertyValue(ORDER_Lines);
		for (int i = 1; i <= 10; i++)
		{
			final GridRowId rowId = model.gridNewRow(ORDER_Lines);
			model.setGridProperty(ORDER_Lines, rowId, PropertyName.of("LineNo"), i * 10);
			model.setGridProperty(ORDER_Lines, rowId, PropertyName.of("M_Product_ID"), LookupValue.of(1, "Alice salad " + i));
			model.setGridProperty(ORDER_Lines, rowId, PropertyName.of("M_AttributeSetInstance_ID"), LookupValue.of(1, "BIO"));
			model.setGridProperty(ORDER_Lines, rowId, PropertyName.of("QtyTU"), BigDecimal.valueOf(i));
			model.setGridProperty(ORDER_Lines, rowId, PropertyName.of("M_HU_PI_Item_Product_ID"), LookupValue.of(1, "IFCO"));
		}

	}
}
