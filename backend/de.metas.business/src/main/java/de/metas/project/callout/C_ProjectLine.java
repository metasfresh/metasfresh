/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.project.callout;

import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_ProjectLine;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Callout(I_C_ProjectLine.class)
public class C_ProjectLine
{
	public C_ProjectLine()
	{
		// register ourselves
		final IProgramaticCalloutProvider programmaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programmaticCalloutProvider.registerAnnotatedCallout(this);
		CopyRecordFactory.enableForTableName(I_C_Project.Table_Name);
	}

	@CalloutMethod(columnNames = I_C_ProjectLine.COLUMNNAME_PlannedQty)
	public void onPlannedQty(final I_C_ProjectLine projectLine)
	{
		updatePlannedAmt(projectLine);
	}

	@CalloutMethod(columnNames = I_C_ProjectLine.COLUMNNAME_PlannedQty)
	public void onPlannedPrice(final I_C_ProjectLine projectLine)
	{
		updatePlannedAmt(projectLine);
	}

	private void updatePlannedAmt(final I_C_ProjectLine projectLine)
	{
		final BigDecimal plannedQty = projectLine.getPlannedQty();
		final BigDecimal plannedPrice = projectLine.getPlannedPrice();
		final BigDecimal plannedAmt = plannedQty.multiply(plannedPrice);
		projectLine.setPlannedAmt(plannedAmt);
	}
}
