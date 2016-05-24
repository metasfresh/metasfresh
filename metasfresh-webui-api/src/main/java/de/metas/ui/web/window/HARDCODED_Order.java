package de.metas.ui.web.window;

import org.compiere.model.I_C_Order;

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

public final class HARDCODED_Order
{
	private static final PropertyName ORDER_DocumentNo = PropertyName.of("DocumentNo");
	private static final PropertyName ORDER_DatePromised = PropertyName.of("DatePromised");
	//
	private static final PropertyName ORDER_C_BPartner_ID = PropertyName.of("C_BPartner_ID");
	private static final PropertyName ORDER_C_BPartner_Location_ID = PropertyName.of("C_BPartner_Location_ID");
	private static final PropertyName ORDER_AD_User_ID = PropertyName.of("AD_User_ID");
	public static final PropertyName ORDER_BPartnerAndAddress = PropertyName.of(ORDER_C_BPartner_ID + "#" + ORDER_C_BPartner_Location_ID + "#" + ORDER_AD_User_ID);
	//
	private static final PropertyName ORDER_Bill_BPartner_ID = PropertyName.of("Bill_BPartner_ID");
	private static final PropertyName ORDER_Bill_Location_ID = PropertyName.of("Bill_Location_ID");
	private static final PropertyName ORDER_Bill_User_ID = PropertyName.of("Bill_User_ID");
	public static final PropertyName ORDER_BillBPartnerAndAddress = PropertyName.of(ORDER_Bill_BPartner_ID + "#" + ORDER_Bill_Location_ID + "#" + ORDER_Bill_User_ID);
	//
	private static final PropertyName ORDER_GrandTotal = PropertyName.of(I_C_Order.COLUMNNAME_GrandTotal);
	private static final PropertyName ORDER_TotalLines = PropertyName.of(I_C_Order.COLUMNNAME_TotalLines);
	
	
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

	private HARDCODED_Order()
	{
		super();
	}

}
