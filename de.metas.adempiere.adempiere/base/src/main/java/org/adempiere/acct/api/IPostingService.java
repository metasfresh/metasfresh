package org.adempiere.acct.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.util.ISingletonService;

/**
 * This is the main entry point for all document posting(accounting) APIs
 * 
 * @author tsa
 *
 */
public interface IPostingService extends ISingletonService
{
	IPostingRequestBuilder newPostingRequest();

	/** @return true if the accounting module is active; false if the accounting module is COMPLETELLY off */
	boolean isEnabled();

	/** @return <code>true</code> if Client Accounting is enabled */
	boolean isClientAccountingEnabled();

	/** @return Client Accounting configuration level; never returns null */
	ClientAccountingStatus getClientAccountingStatus();

	/**
	 * Sets Client Accounting configuration level.
	 * 
	 * WARNING:
	 * <ul>
	 * <li>this shall be used mainly for testing
	 * <li>configuration will be directly persisted in database
	 * </ul>
	 * 
	 * @param status
	 */
	void setClientAccountingStatus(ClientAccountingStatus status);
}
