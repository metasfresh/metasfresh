package de.metas.contracts.flatrate.api;

/*
 * #%L
 * de.metas.contracts
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


import java.sql.Timestamp;

import org.adempiere.util.ISingletonService;

import de.metas.contracts.flatrate.exceptions.SubscriptionChangeException;
import de.metas.contracts.model.I_C_Contract_Change;
import de.metas.contracts.model.I_C_Flatrate_Term;

public interface IContractChangeDAO extends ISingletonService
{
	/**
	 * Returns the change conditions for for switching from and old subscription to a new one. The result also depends on the time span between the old subscription's start and the date of the change.
	 * 
	 * @param oldSubscriptionid
	 * @param oldSubscriptionStart the start date of the old subscription
	 * @param newSubscriptionId
	 * @param changeDate the date of the switch
	 * @return
	 * @throws SubscriptionChangeException
	 */
	I_C_Contract_Change retrieveChangeConditions(
			I_C_Flatrate_Term term,
			int newSubscriptionId,
			Timestamp changeDate);

	/**
	 * 
	 * @param term
	 * @param newStatus
	 * @param changeDate
	 * @return
	 */
	I_C_Contract_Change retrieveChangeConditions(
			I_C_Flatrate_Term term,
			String newStatus,
			Timestamp changeDate);
	
}
