/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.calendar.standard;

import de.metas.document.DocBaseType;
import de.metas.util.ISingletonService;
import org.adempiere.exceptions.PeriodClosedException;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_PeriodControl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

public interface IPeriodBL extends ISingletonService
{
	void testPeriodOpen(Properties ctx, Timestamp dateAcct, DocBaseType docBaseType, int AD_Org_ID) throws PeriodClosedException;

	boolean isOpen(Properties ctx, Timestamp DateAcct, DocBaseType DocBaseType, int AD_Org_ID);

	/**
	 * Suggests which shall be the PeriodStatus for given period action
	 * 
	 * @param periodAction
	 * @return period status; never returns null
	 */
	String getPeriodStatusForAction(String periodAction);

	/**
	 * Create all {@link I_C_PeriodControl}s for given period.
	 * 
	 * NOTE: this method assumes there are no {@link I_C_PeriodControl}s already created for given period.
	 * 
	 * @param period
	 */
	void createPeriodControls(I_C_Period period);

	/**
	 * Checks if given date is in period.
	 * 
	 * @param period
	 * @param date date
	 * @return true if in period
	 */
	boolean isInPeriod(I_C_Period period, Date date);
}
