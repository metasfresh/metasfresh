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

package de.metas.costing.impl;

import de.metas.costing.ChargeId;

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Charge;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

@Repository
public class ChargeRepository
{
	public I_C_Charge getById(@NonNull final ChargeId chargeId)
	{
		final I_C_Charge chargeRecord = loadOutOfTrx(chargeId.getRepoId(), I_C_Charge.class);

		if (chargeRecord == null)
		{
			throw new AdempiereException("@NotFound@ @C_Charge_ID@: " + chargeId);
		}

		return chargeRecord;
	}
}
