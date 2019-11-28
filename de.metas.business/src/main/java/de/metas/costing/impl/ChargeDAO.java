package de.metas.costing.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Charge;

import de.metas.costing.ChargeId;
import de.metas.costing.IChargeDAO;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class ChargeDAO implements IChargeDAO
{
	@Override
	public I_C_Charge getByIdOrNull(@Nullable final ChargeId chargeId)
	{
		if (chargeId == null)
		{
			return null;
		}
		return loadOutOfTrx(chargeId, I_C_Charge.class);
	}

	@Override
	public I_C_Charge getById(@NonNull final ChargeId chargeId)
	{
		final I_C_Charge result = loadOutOfTrx(chargeId, I_C_Charge.class);
		if (result == null)
		{
			throw new AdempiereException("Unable to load C_Charge record for id=" + chargeId);
		}
		return result;
	}
}
