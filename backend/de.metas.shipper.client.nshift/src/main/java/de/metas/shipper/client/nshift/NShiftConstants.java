/*
 * #%L
 * de.metas.shipper.client.nshift
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

package de.metas.shipper.client.nshift;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NShiftConstants
{
	// config.additionalProperties
	public static final String ACTOR_ID = "ActorId";
	public static final String SERVICE_LEVEL = "ServiceLevel";
	// SHIPMENT path: set by NShiftShipperGatewayClient.applyShippingRuleOptions (true for non-Manual carrier advise),
	// read by NShiftShipmentService to gate ServiceLevel.
	public static final String USE_SHIPPING_RULES = "UseShippingRules";
	// ADVISE paths: the Carrier_Config.IsSelectionRules flag. Drives UseShippingRules in NShift{Ship,Order}AdvisorService
	// and gates ServiceLevel there. Deliberately a DIFFERENT key from USE_SHIPPING_RULES above — the two are set by
	// different code paths (advise = this config flag; shipment = the non-Manual-advise gate).
	public static final String SELECTION_RULES = "IsSelectionRules";
	public static final String IS_CREATE_DRAFT_SHIPMENT_ONLY = "IsCreateDraftShipmentOnly";

	// shipAdvises
	public static final String PROD_CONCEPT_ID = "ProdConceptId";
	public static final String GOODS_TYPE_ID = "GoodsTypeID";
	public static final String GOODS_TYPE_NAME = "GoodsTypeName";
}
