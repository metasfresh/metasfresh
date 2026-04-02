/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs.accounting;

import de.metas.util.Services;
import io.cucumber.java.en.And;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_PeriodControl;
import org.compiere.model.X_C_PeriodControl;

public class C_PeriodControl_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@And("^all periods are open$")
	public void all_periods_are_open()
	{
		queryBL.createQueryBuilder(I_C_PeriodControl.class)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_C_PeriodControl.COLUMNNAME_PeriodStatus, X_C_PeriodControl.PERIODSTATUS_Open)
				.execute();
	}
}
