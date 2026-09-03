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
	public static final String MANUAL = "IsManual";
	public static final String SELECTION_RULES = "IsSelectionRules";
	public static final String IS_CREATE_DRAFT_SHIPMENT_ONLY = "IsCreateDraftShipmentOnly";

	// shipAdvises
	public static final String PROD_CONCEPT_ID = "ProdConceptId";
	public static final String GOODS_TYPE_ID = "GoodsTypeID";
	public static final String GOODS_TYPE_NAME = "GoodsTypeName";

	// Shared endpoint paths
	/** Used by both {@link de.metas.shipper.client.nshift.NShiftOrderAdvisorService} (advise, Submit=0)
	 * and {@link de.metas.shipper.client.nshift.NShiftShipmentService} (book, Submit=1). */
	public static final String ORDER_ADVICE_ENDPOINT = "/ShipServer/{ID}/OrderAdvice";

	/** OrderAdvice option Visibility="extended": nShift returns product + carrier (+ goods type) detail in the
	 * response. Required on both the advise (Submit=0) and the booking (Submit=1) OrderAdvice calls. */
	public static final String VISIBILITY_EXTENDED = "extended";
}
