package org.adempiere.mm.attributes.api;

import lombok.experimental.UtilityClass;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

/*
 * #%L
 * de.metas.business
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

@UtilityClass
public class AttributeConstants
{
	/**
	 * No ASI (record which actually exists in M_AttributeSetInstance table)
	 */
	public final int M_AttributeSetInstance_ID_None = AttributeSetInstanceId.NONE.getRepoId();

	/**
	 * No Attribute Set (record which actually exists in M_AttributeSet table)
	 */
	public final int M_AttributeSet_ID_None = 0;

	public final AttributeCode ATTR_TE = AttributeCode.ofString("HU_TE");
	public final AttributeCode ATTR_DateReceived = AttributeCode.ofString("HU_DateReceived");
	public final AttributeCode ATTR_SecurPharmScannedStatus = AttributeCode.ofString("HU_Scanned");

	public final String ATTR_BestBeforeDate_String = "HU_BestBeforeDate";
	public final AttributeCode ATTR_BestBeforeDate = AttributeCode.ofString(ATTR_BestBeforeDate_String);
	public final AttributeCode ATTR_MonthsUntilExpiry = AttributeCode.ofString("MonthsUntilExpiry");

	//
	public final AttributeCode ATTR_SubProducerBPartner_Value = AttributeCode.ofString("SubProducerBPartner");

	//
	public final AttributeCode ATTR_Vendor_BPartner_ID = AttributeCode.ofString("Vendor");

	public static final String ATTR_SerialNo_String = "SerialNo";
	public final AttributeCode ATTR_SerialNo = AttributeCode.ofString(ATTR_SerialNo_String);

	public static final String ATTR_LotNumber_String = "Lot-Nummer";
	public static final AttributeCode ATTR_LotNumber = AttributeCode.ofString(ATTR_LotNumber_String);
	public static final AttributeCode ATTR_LotNumberDate = AttributeCode.ofString("HU_LotNumberDate");

	public static final String ATTR_RepackNumber_String = "RepackNumber";
	public static final AttributeCode ATTR_RepackNumber = AttributeCode.ofString(ATTR_RepackNumber_String);

	public static final AttributeCode RouterPassword = AttributeCode.ofString("RouterPassword");
	public static final AttributeCode RouterMAC1 = AttributeCode.ofString("RouterMAC1");
	public static final AttributeCode RouterMAC2 = AttributeCode.ofString("RouterMAC2");
	public static final AttributeCode RouterMAC3 = AttributeCode.ofString("RouterMAC3");
	public static final AttributeCode RouterMAC4 = AttributeCode.ofString("RouterMAC4");
	public static final AttributeCode RouterMAC5 = AttributeCode.ofString("RouterMAC5");
	public static final AttributeCode RouterMAC6 = AttributeCode.ofString("RouterMAC6");

	public static final AttributeCode WarrantyStartDate = AttributeCode.ofString("WarrantyStartDate");

	public static final AttributeCode HU_ExternalLotNumber = AttributeCode.ofString("ExternalLotNumber");

	public static final AttributeCode ProductionDate = AttributeCode.ofString("ProductionDate");
}
