package org.adempiere.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import org.compiere.model.I_C_BPartner_Location;

import de.metas.adempiere.model.I_C_Order;
import de.metas.interfaces.I_C_OrderLine;

public final class CustomColNames {

	private CustomColNames() {
	}

	@Deprecated
	// used in GridFieldVO.create
	public static final String AD_Column_ColumnClass = "ColumnClass";

	public static final String AD_OrgInfo_REPORT_PREFIX = "ReportPrefix";

	public static final String AD_USER_ISDEFAULTCONTACT = "IsDefaultContact";

	public static final String C_BPartner_M_FREIGHTCOST_ID = "M_FreightCost_ID";

	@Deprecated
	public static final String C_BPartner_POSTAGEFREEAMT = "PostageFreeAmt";
	public static final String C_BPartner_VATaxID = "VATaxID";

	// public static final String C_BPartner_Location_ADDRESS = "Address";

	@Deprecated
	public static final String C_BPartner_Location_ISSUBSCRIPTIONTO_DEFAULT = "IsSubscriptionToDefault";

	@Deprecated
	public static final String C_BPartner_Location_ISBILLTO_DEFAULT = I_C_BPartner_Location.COLUMNNAME_IsBillTo; // "IsBillToDefault";
	@Deprecated
	public static final String C_BPartner_Location_ISSHIPTO_DEFAULT = I_C_BPartner_Location.COLUMNNAME_IsShipTo; // "IsShipToDefault";

	/**
	 * This column is used in BankingPA service
	 */
	public final static String C_BP_BankAcount_Value = "Value";

	/**
	 * ISO-3166 numerical country code
	 */
	public static final String C_Country_NUM_COUNTRY_CODE = "NumCountryCode";

	public static final String C_Invoice_DESCRIPTION_BOTTOM = "DescriptionBottom";
	public static final String C_Invoice_INCOTERM = "Incoterm";
	public static final String C_Invoice_INCOTERMLOCATION = "IncotermLocation";
	public static final String C_Invoice_ISUSE_BPARTNER_ADDRESS = "IsUseBPartnerAddress";
	public static final String C_Invoice_BPARTNERADDRESS = "BPartnerAddress";

//	@Deprecated
//	public static final String C_InvoiceLine_PRODUCT_DESC = I_C_InvoiceLine.COLUMNNAME_ProductDescription;
//	@Deprecated
//	public static final String C_Order_BILLTOADDRESS =  I_C_Order.COLUMNNAME_BillToAddress;
//	@Deprecated
//	public static final String C_Order_BPARTNERADDRESS = I_C_Order.COLUMNNAME_BPartnerAddress;
	public static final String C_Order_C_BP_BANKACCOUNT_ID = "C_BP_BankAccount_ID";
	public static final String C_Order_COMPLETE_ORDER_DISCOUNT = "CompleteOrderDiscount";
	public static final String C_Order_CREATE_NEW_FROM_PROPOSAL = "CreateNewFromProposal";
	public static final String C_Order_DESCRIPTION_BOTTOM = "DescriptionBottom";
	@Deprecated
	public static final String C_Order_FreightCostRule_FREIGHTCOST = I_C_Order.FREIGHTCOSTRULE_Versandkostenpauschale;
//	@Deprecated
//	public static final String C_Order_INCOTERM = I_C_Order.COLUMNNAME_Incoterm;
//	@Deprecated
//	public static final String C_Order_INCOTERMLOCATION = I_C_Order.COLUMNNAME_IncotermLocation;
//	@Deprecated
//	public static final String C_Order_ISUSE_BPARTNER_ADDRESS = I_C_Order.COLUMNNAME_IsUseBPartnerAddress;
//	@Deprecated
//	public static final String C_Order_ISUSE_BILLTO_ADDRESS = I_C_Order.COLUMNNAME_IsUseBillToAddress;
//	@Deprecated
//	public static final String C_Order_ISUSE_DELIVERYTO_ADDRESS = I_C_Order.COLUMNNAME_IsUseDeliveryToAddress;
//	@Deprecated
//	public static final String C_Order_M_PRICING_SYSTEM_ID = I_C_Order.COLUMNNAME_M_PricingSystem_ID;
//	public static final String C_Order_REF_DATE_ORDER = "Ref_DateOrder";
	public static final String C_Order_REF_PROPOSAL_ID = "Ref_Proposal_ID";

	@Deprecated
	public static final String C_OrderLine_ORDER_DISCOUNT = I_C_OrderLine.COLUMNNAME_OrderDiscount;
	@Deprecated
	public static final String C_OrderLine_CHARGE = I_C_OrderLine.COLUMNNAME_C_Charge_ID;
	@Deprecated
	public static final String C_OrderLine_UOM = I_C_OrderLine.COLUMNNAME_C_UOM_ID;

//	@Deprecated
//	public static final String C_Order_M_PRODUCT_ID_FASTINPUT = "M_Product_ID_FastInput";
//	@Deprecated
//	public static final String C_Order_QTY_FASTINPUT = I_C_Order.COLUMNNAME_Qty_FastInput;

	public static final String C_OrderLine_C_ORDER_DOUBLE_ID = "C_Order_Double_ID";
	public static final String C_OrderLine_C_ORDERLINE_DOUBLE_ID = "C_OrderLine_Double_ID";
	public static final String C_OrderLine_DATEORDERED_DOUBLE = "DateOrdered_Double";

	public static final String C_Tax_Acct_T_PAYDISCOUNT_EXTP_ACCT = "T_PayDiscount_Exp_Acct";
	public static final String C_Tax_Acct_T_PAYDISCOUNT_REV_ACCT = "T_PayDiscount_Rev_Acct";
	public static final String C_Tax_Acct_T_REVENUE_ACCT = "T_Revenue_Acct";

	public static final String C_Tax_ISTO_EU_LOCATION = "IsToEULocation";

	public static final String M_InOutLine_PRODUCT_DESC = "ProductDescription";


	public static final String M_Product_Category_C_DOCTYPE_ID = "C_DocType_ID";
	public static final String M_Product_Category_ISSUMMARY = "IsSummary";

	public static final String T_Replenish_C_Period_ID = "C_Period_ID";
	public static final String T_Replenish_TimeToMarket = "TimeToMarket";

//	@Deprecated
//	public static final String M_Shipper_IS_DEFAULT = I_M_Shipper.COLUMNNAME_IsDefault;

	public static final String M_Package_PACKAGE_WEIGHT = "PackageWeight";

	public static final String M_Package_M_PACKAGING_CONTAINER_ID = "M_PackagingContainer_ID";

}
