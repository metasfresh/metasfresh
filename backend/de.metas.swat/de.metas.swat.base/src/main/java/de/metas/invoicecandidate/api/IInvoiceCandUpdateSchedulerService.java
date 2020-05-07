package de.metas.invoicecandidate.api;

import org.adempiere.util.ISingletonService;

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

/**
 * Service used to:
 * <ul>
 * <li>execute a given invoice candidates invalidator
 * <li>schedule the invalidated candidates to be updated
 * </ul>
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IInvoiceCandUpdateSchedulerService extends ISingletonService
{
	/**
	 * Schedule an update for invalidated invoice candidates
	 * 
	 * @param request update schedule request
	 */
	void scheduleForUpdate(final IInvoiceCandUpdateSchedulerRequest request);

}
