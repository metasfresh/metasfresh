/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.edi.model.validator;

import de.metas.edi.api.IDesadvBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Interceptor(I_M_ShipmentSchedule.class)
@Component
public class M_ShipmentSchedule
{
	private final IDesadvBL desadvBL = Services.get(IDesadvBL.class);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Override)
	public void updateQtyOrdered_Override(@Nonnull final I_M_ShipmentSchedule schedule)
	{
		desadvBL.updateQtyOrdered_OverrideFromShipSchedAndSave(schedule);
	}
}
