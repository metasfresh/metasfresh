package de.metas.fresh.mrp_productinfo;

import java.sql.Timestamp;
import java.util.Map;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
/**
 * Specifies a set of MRP product info records that need to be updated.
 * Generally, those records are identified by their <code>Date</code>, <code>M_Product_ID</code> and <code>M_AttributeSetInstance_ID</code>.
 *
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IMRPProductInfoSelector extends Comparable<IMRPProductInfoSelector>
{
	Timestamp getDate();

	int getM_Product_ID();

	int getM_AttributeSetInstance_ID();

	/**
	 * Never return <code>null</code>
	 *
	 * @return
	 */
	Object getModel();

	/**
	 * Returns a string that looks like this:
	 *
	 * <pre>
	 * TableName[Date=(Timestamp),M_Product_ID=(int),M_AttributeSetInstance_ID=(int)]
	 * </pre>
	 *
	 * @return
	 */
	String toStringForRegularLogging();

	/**
	 * @return a map representation.
	 *         The keys are prefixed with the model's table name and <code>Record_ID</code>. The map returned by this method matches the parameter which {@link IMRPProductInfoSelectorFactory#createOrNull(Object, org.adempiere.util.api.IParams)} expects.
	 */
	Map<String, Object> asMap();

	/**
	 *
	 * @return the asi key value for this selector's ASI. Never <code>null</code>, but might return the empty string.
	 */
	String getASIKey();

	/**
	 *
	 * @param obj
	 * @return <code>true</code> if <code>object</code> has an equal <code>M_Product_ID</code>, <code>Date</code> and <code>ASIKey</code> (but might have a different <code>M_AttributeSetInstance_ID</code>!).
	 */
	@Override
	boolean equals(Object obj);
}
