package org.adempiere.mm.attributes.api;

import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import lombok.experimental.UtilityClass;

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
public final class AttributeConstants
{
	/**
	 * No ASI (record which actually exists in M_AttributeSetInstance table)
	 */
	public static final int M_AttributeSetInstance_ID_None = AttributeSetInstanceId.NONE.getRepoId();

	/**
	 * No Attribute Set (record which actually exists in M_AttributeSet table)
	 */
	public static final int M_AttributeSet_ID_None = 0;

	public static final AttributeCode ATTR_TE = AttributeCode.ofString("HU_TE");
	public static final AttributeCode ATTR_DateReceived = AttributeCode.ofString("HU_DateReceived");
	public static final AttributeCode ATTR_SecurPharmScannedStatus = AttributeCode.ofString("HU_Scanned");

	public static final String ATTR_BestBeforeDate_String = "HU_BestBeforeDate";
	public static final AttributeCode ATTR_BestBeforeDate = AttributeCode.ofString(ATTR_BestBeforeDate_String);
	public static final AttributeCode ATTR_MonthsUntilExpiry = AttributeCode.ofString("MonthsUntilExpiry");

	//
	public static final AttributeCode ATTR_SubProducerBPartner_Value = AttributeCode.ofString("SubProducerBPartner");

	public static final AttributeCode ATTR_SerialNo = AttributeCode.ofString("SerialNo");
	public static final AttributeCode ATTR_LotNr = AttributeCode.ofString("Lot-Nummer");

}
