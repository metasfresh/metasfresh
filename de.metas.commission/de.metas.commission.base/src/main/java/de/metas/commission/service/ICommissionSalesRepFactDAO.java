package de.metas.commission.service;

/*
 * #%L
 * de.metas.commission.base
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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Period;

import de.metas.commission.model.I_C_AdvComSalesRepFact;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.I_C_Sponsor;

public interface ICommissionSalesRepFactDAO extends ISingletonService
{

	List<I_C_AdvComSalesRepFact> retrieveForComFact(final I_C_AdvCommissionFact cf);

	I_C_AdvComSalesRepFact retrieveLatestAtDate(final Properties ctx, final I_C_Sponsor sponsor, final I_C_AdvComSystem comSystem, final String factName, final Timestamp date, final String trxName,
			final String... status);

	/**
	 * 
	 * @param sponsor
	 * @param factName
	 * @param date
	 * @param status no status means no restriction
	 * @return
	 */
	I_C_AdvComSalesRepFact retrieveLatestAtDate(final I_C_Sponsor sponsor, final I_C_AdvComSystem comSystem, final String factName, final Timestamp date, final String... status);

	I_C_AdvComSalesRepFact retrieveLatestFor(final I_C_Sponsor sponsor, final I_C_AdvComSystem comSystem, final String factName);

	/**
	 * 
	 * @param sponsor
	 * @param srfName
	 * @param period
	 * @param excludeWithZeroValueNumber
	 * @param status no status means no restriction
	 * @return
	 */
	List<I_C_AdvComSalesRepFact> retrieveFactsUntil(final I_C_Sponsor sponsor, final I_C_AdvComSystem comSystem, final String srfName, final I_C_Period period,
			final boolean excludeWithZeroValueNumber, final String... status);

	/**
	 * 
	 * @param sponsor
	 * @param name
	 * @param periodId
	 * @param status no status means no restriction
	 * @return
	 */
	List<I_C_AdvComSalesRepFact> retrieveFactsAt(final I_C_Sponsor sponsor, final I_C_AdvComSystem comSystem, final String name, final int periodId, final String... status);

	/**
	 * 
	 * @param sponsor
	 * @param name
	 * @param period
	 * @param status no status means no restriction
	 * @return
	 */
	BigDecimal retrieveSumUntil(final I_C_Sponsor sponsor, final I_C_AdvComSystem comSystem, final String name, final I_C_Period period, final String... status);

	/**
	 * 
	 * @param sponsor
	 * @param name
	 * @param periodId
	 * @param status no status means no restriction
	 * @return
	 */
	BigDecimal retrieveSumAt(final I_C_Sponsor sponsor, final I_C_AdvComSystem comSystem, final String name, final int periodId, final String... status);

	I_C_AdvComSalesRepFact retrieveForPeriod(final I_C_Sponsor sponsor, final I_C_AdvComSystem comSystem, final String name, final String status, final int periodId);

	/**
	 * Retrieves the srf with the given sponsor, name, status and date ( <code>DateAcct</code>). if there is more than one matching record, the one with the biggest
	 * <code>C_AdvComSalesrepFact_ID</code> is returned.
	 * 
	 * @param sponsor
	 * @param name
	 * @param status
	 * @param date
	 * @return
	 */
	I_C_AdvComSalesRepFact retrieveForDate(final I_C_Sponsor sponsor, final I_C_AdvComSystem comSystem, final String name, final String status, final Timestamp date);

	I_C_AdvComSalesRepFact createDontSave(final Properties ctx, final I_C_Sponsor sponsor, final I_C_AdvComSystem comSystem, final String name, final String status, final Timestamp dateAcct,
			final int periodId, final String trxName);

	/**
	 * The new fact is also saved in this method
	 * 
	 * @param name
	 * @param instance
	 * @param po
	 * @param value
	 * 
	 * @return
	 */
	I_C_AdvComSalesRepFact createDontSave(final I_C_Sponsor sponsor, final I_C_AdvComSystem comSystem, final String name, final String status, final Timestamp dateAcct, final int periodId);

}
