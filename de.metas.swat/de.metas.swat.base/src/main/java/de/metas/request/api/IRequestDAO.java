package de.metas.request.api;

import org.adempiere.util.ISingletonService;
import org.eevolution.model.I_DD_OrderLine;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.request.model.I_R_Request;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface IRequestDAO extends ISingletonService
{

	/**
	 * Create a new R_Request based on the given inout line.
	 * This request will contain information taken from the line and from its inout header:
	 * <li>inout
	 * <li>product
	 * <li>partner
	 * <li>dateDelivered
	 * <li>qualityNotice
	 * <li>org
	 * <li>linked salesrep of the org, etc.
	 * 
	 * Note that the quantities are not relevant in the requests. Therefore, the qualityDiscountPercent is not even set in the request.
	 * We can have requests with no quality notices, in case the base inout line was created with qualityDiscountPercent but with no quality notice.
	 * 
	 * @param line
	 */
	I_R_Request createRequestFromInOutLine(I_M_InOutLine line);

	I_R_Request createRequestFromDDOrderLine(I_DD_OrderLine line);
}
