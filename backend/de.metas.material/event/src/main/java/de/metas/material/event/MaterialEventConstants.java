/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.material.event;

import lombok.experimental.UtilityClass;

/**
 * To be read as Data storage for table names which are defined outside the scope of the <b>metasfresh-material-event</b> project
 */
@UtilityClass
public class MaterialEventConstants
{
	public static final String M_HU_TABLE_NAME = "M_HU";
	public static final String M_SHIPMENT_SCHEDULE_TABLE_NAME = "M_ShipmentSchedule";
	public static final String MD_CANDIDATE_TABLE_NAME = "MD_Candidate";
	public static final String C_PURCHASE_CANDIDATE_TABLE_NAME = "C_PurchaseCandidate";
	public static final String PMM_PURCHASECANDIDATE_TABLE_NAME = "PMM_PurchaseCandidate";
	public static final String M_RECEIPTSCHEDULE_TABLE_NAME = "M_ReceiptSchedule";
	public static final String M_SHIPMENTSCHEDULE_TABLE_NAME = "M_ShipmentSchedule";
	public static final String FRESH_QTYONHAND_TABLE_NAME = "Fresh_QtyOnHand";
}
