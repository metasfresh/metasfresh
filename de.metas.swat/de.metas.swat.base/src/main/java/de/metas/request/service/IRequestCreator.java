package de.metas.request.service;

import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

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

public interface IRequestCreator extends ISingletonService
{

	/**
	 * Create an R_Request based on a list of document lines
	 * Currently, they are created only from inout lines. Extend functionality when needed!
	 * 
	 * These requests will contain information taken from the lines and from their inout headers:
	 * <li>inout
	 * <li>product
	 * <li>partner
	 * <li>dateDelivered
	 * <li>qualityNotice
	 * <li>org
	 * <li>linked salesrep of the org, etc.
	 * 
	 * @param ctx
	 * @param inOutLineIds
	 * @param trxName
	 */
	void createRequests(final Properties ctx, final List<Integer> docLineIds, final String trxName);
}
