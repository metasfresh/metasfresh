/*
 * #%L
 * metasfresh-material-cockpit
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

package de.metas.material.cockpit.interceptor;

import de.metas.material.cockpit.model.I_MD_Cockpit;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler.computeQtyExpectedSurplus;
import static de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler.computeQtySupplyToSchedule;

@Interceptor(I_MD_Cockpit.class)
@Component
public class MD_Cockpit
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW },
			ifColumnsChanged = { I_MD_Cockpit.COLUMNNAME_MDCandidateQtyStock,
					I_MD_Cockpit.COLUMNNAME_QtySupplyRequired,
					I_MD_Cockpit.COLUMNNAME_QtySupplySum,
					I_MD_Cockpit.COLUMNNAME_QtyDemandSum
			})
	public void updateCounts(@NonNull final I_MD_Cockpit cockpit)
	{
		final BigDecimal qtyStockCurrent = cockpit.getMDCandidateQtyStock();

		cockpit.setQtyStockCurrent(qtyStockCurrent);
		cockpit.setQtySupplyToSchedule(computeQtySupplyToSchedule(cockpit));
		cockpit.setQtyExpectedSurplus(computeQtyExpectedSurplus(cockpit));
	}
}
