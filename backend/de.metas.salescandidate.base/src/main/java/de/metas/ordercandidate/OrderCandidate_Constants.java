package de.metas.ordercandidate;

/*
 * #%L
 * de.metas.swat.base
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


public final class OrderCandidate_Constants
{
	private OrderCandidate_Constants()
	{
	}

	public static final String ENTITY_TYPE = "de.metas.ordercandidate";

	/**
	 * Internal name of the AD_Input_Data destination for order candidates.
	 *
	 * @see de.metas.impex.api.IInputDataSourceDAO#retrieveInputDataSource(java.util.Properties, String, boolean,
	 *      String)
	 */
	public static final String DATA_DESTINATION_INTERNAL_NAME = "DEST.de.metas.ordercandidate";


	public static final String HEADER_AGGREGATION_KEY_DELIMITER = "#";

}
