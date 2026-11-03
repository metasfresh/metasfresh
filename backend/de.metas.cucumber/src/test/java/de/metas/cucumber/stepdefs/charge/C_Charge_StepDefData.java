/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.charge;

import de.metas.costing.ChargeId;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import lombok.NonNull;
import org.compiere.model.I_C_Charge;

/**
 * Stores {@link I_C_Charge} records by identifier alias, enabling cross-step references
 * to charges (e.g. from {@code C_Charge_Acct is set for:}).
 */
public class C_Charge_StepDefData extends StepDefData<I_C_Charge>
		implements StepDefDataGetIdAware<ChargeId, I_C_Charge>
{
	public C_Charge_StepDefData()
	{
		super(I_C_Charge.class);
	}

	@Override
	public ChargeId extractIdFromRecord(@NonNull final I_C_Charge record)
	{
		return ChargeId.ofRepoId(record.getC_Charge_ID());
	}
}
