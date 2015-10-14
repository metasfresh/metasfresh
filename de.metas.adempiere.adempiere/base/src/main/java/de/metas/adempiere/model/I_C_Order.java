package de.metas.adempiere.model;

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


import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.document.model.IDocumentBillLocation;
import org.adempiere.document.model.IDocumentDeliveryLocation;

public interface I_C_Order extends org.compiere.model.I_C_Order, I_OrderOrInOut
	, IDocumentBillLocation, IDocumentDeliveryLocation
{
	String FREIGHTCOSTRULE_Versandkostenpauschale = "P";

	public static String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";
	
	@Override
	public int getM_PricingSystem_ID();

	@Override
	public void setM_PricingSystem_ID(int pricingSystemId);

	
	public static String RECEIVED_VIA = "ReceivedVia";

	public String getReceivedVia();

	public void setReceivedVia(String receivedVia);
	
	
	public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";
	@Deprecated
	public static final String M_PRODUCT_ID = COLUMNNAME_M_Product_ID;

	@Override
	public int getM_Product_ID();

	@Override
	public void setM_Product_ID(int productId);

	
	public static final String COLUMNNAME_C_Sponsor_ID = "C_Sponsor_ID"; 

	public int getC_Sponsor_ID();

	public void setC_Sponsor_ID(int C_Sponsor_ID);
	
	
	public static final String COLUMNNAME_Ref_RMA_ID = "Ref_RMA_ID"; 

	public int getRef_RMA_ID();

	public void setRef_RMA_ID(int Ref_RMA_ID);
	
	
	public static final String COLUMNNAME_Qty_FastInput = "Qty_FastInput";
	
	@Override
	public BigDecimal getQty_FastInput();

	@Override
	public void setQty_FastInput(BigDecimal Qty_FastInput);
	
	public static final String COLUMNNAME_BPartnerAddress = "BPartnerAddress";
	
	@Override
	public String getBPartnerAddress();
	
	@Override
	public void setBPartnerAddress(String BPartnerAddress);
	
	public static final String COLUMNNAME_BillToAddress = "BillToAddress";
	
	@Override
	public String getBillToAddress();
	
	@Override
	public void setBillToAddress(String billToAddress);

	
	public static final String COLUMNNAME_OfferValidDays = "OfferValidDays";
	public int getOfferValidDays();
	public void setOfferValidDays(int OfferValidDays);
	
	public static final String COLUMNNAME_OfferValidDate = "OfferValidDate";
	public Timestamp getOfferValidDate();
	public void setOfferValidDate(Timestamp OfferValidDate);
	
	
	public static final String COLUMNNAME_DeliveryToAddress = "DeliveryToAddress";
	
	@Override
	public String getDeliveryToAddress();
	
	@Override
	public void setDeliveryToAddress(String DeliveryToAddress);
	
	public static final String COLUMNNAME_IsUseBPartnerAddress = "IsUseBPartnerAddress";
	@Override
	public boolean isUseBPartnerAddress();
	@Override
	public void setIsUseBPartnerAddress(boolean IsUseBPartnerAddress);
	
	public static final String COLUMNNAME_IsUseBillToAddress = "IsUseBillToAddress";
	@Override
	public boolean isUseBillToAddress();
	@Override
	public void setIsUseBillToAddress(boolean IsUseBillToAddress);
	
	public static final String COLUMNNAME_IsUseDeliveryToAddress = "IsUseDeliveryToAddress";
	public boolean isUseDeliveryToAddress();
	public void setIsUseDeliveryToAddress(boolean IsUseDeliveryToAddress);
	
	public static final String COLUMNNAME_Incoterm = "Incoterm";
	@Override
	public String getIncoterm();
	@Override
	public void setIncoterm(String Incoterm);

	public static final String COLUMNNAME_IncotermLocation = "IncotermLocation";
	@Override
	public String getIncotermLocation();
	@Override
	public void setIncotermLocation(String IncotermLocation);
	
	public static String COLUMNNAME_DescriptionBottom = "DescriptionBottom";
	@Override
	public String getDescriptionBottom();
	@Override
	public void setDescriptionBottom(String DescriptionBottom);
	
	public static String COLUMNNAME_HandOver_Partner_ID = "HandOver_Partner_ID";
	public int getHandOver_Partner_ID();
	public void setHandOver_Partner_ID(int HandOver_Partner_ID);
	
	public static String COLUMNNAME_HandOver_Location_ID = "HandOver_Location_ID";
	public int getHandOver_Location_ID();
	public void setHandOver_Location_ID(int HandOver_Location_ID);
	
	public static String COLUMNNAME_IsUseHandOver_Location = "IsUseHandOver_Location";
	public boolean getIsUseHandOver_Location();
	public void setIsUseHandOver_Location(boolean IsUseHandOver_Location);
	
}
