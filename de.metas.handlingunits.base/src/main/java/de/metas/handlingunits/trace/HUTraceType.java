package de.metas.handlingunits.trace;

import de.metas.handlingunits.model.X_M_HU_Trace;

/*
 * #%L
 * de.metas.handlingunits.base
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

/**
 * <b>Please</b> keep this in sync with the trace types of {@code M_HU_Trace}, or bad things will happen {@link X_M_HU_Trace#HUTRACETYPE_AD_Reference_ID}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public enum HUTraceType
{
	/**
	 * Used for records/events related to an outgoing {@code M_InOut}.
	 */
	MATERIAL_SHIPMENT,

	/**
	 * Used for records/events related to an incoming {@code M_InOut}.
	 */
	MATERIAL_RECEIPT,

	/**
	 * Used for records/events related to a {@code M_Movement}.
	 */
	MATERIAL_MOVEMENT,

	/**
	 * Used for records/events related to picking (assignment of HUs to {@code M_Shipment_Schedule}s).
	 */
	MATERIAL_PICKING,

	/**
	 * Used for records/events related to HUs being used in production
	 */
	PRODUCTION_ISSUE,

	/**
	 * Used for records/events related to HUs coming out of production
	 */
	PRODUCTION_RECEIPT,

	/**
	 * Used for records/events related HU-loading (e.g. split of qtys from an aggregate HU).
	 */
	TRANSFORM_LOAD,

	/**
	 * Used for records/events related zo changes in the parent HU relation.
	 */
	TRANSFORM_PARENT
}
