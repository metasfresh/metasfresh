<<<<<<<< HEAD:backend/de.metas.adempiere.adempiere/base/src/main/java/de/metas/calendar/standard/interceptor/C_NonBusinessDay.java
package de.metas.calendar.standard.interceptor;

import de.metas.calendar.standard.ICalendarDAO;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_NonBusinessDay;

========
>>>>>>>> new_dawn_uat:backend/de.metas.acct.base/src/test/java/de/metas/acct/interceptor/BpartnerValueToDebtorCreditorIds.java
/*
 * #%L
 * de.metas.acct.base
 * %%
<<<<<<<< HEAD:backend/de.metas.adempiere.adempiere/base/src/main/java/de/metas/calendar/standard/interceptor/C_NonBusinessDay.java
 * Copyright (C) 2018 metas GmbH
========
 * Copyright (C) 2021 metas GmbH
>>>>>>>> new_dawn_uat:backend/de.metas.acct.base/src/test/java/de/metas/acct/interceptor/BpartnerValueToDebtorCreditorIds.java
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

<<<<<<<< HEAD:backend/de.metas.adempiere.adempiere/base/src/main/java/de/metas/calendar/standard/interceptor/C_NonBusinessDay.java
@Interceptor(I_C_NonBusinessDay.class)
public class C_NonBusinessDay
{
	public void validate(final I_C_NonBusinessDay record)
	{
		Services.get(ICalendarDAO.class).validate(record);
========
package de.metas.acct.interceptor;

import lombok.Value;

@Value
class BpartnerValueToDebtorCreditorIds
{
	String value;

	Integer debtorId;
	Integer creditorId;

	public BpartnerValueToDebtorCreditorIds(final String value, final Integer debtorId, final Integer creditorId)
	{
		this.value = value;
		this.debtorId = debtorId;

		this.creditorId = creditorId;
>>>>>>>> new_dawn_uat:backend/de.metas.acct.base/src/test/java/de/metas/acct/interceptor/BpartnerValueToDebtorCreditorIds.java
	}
}
