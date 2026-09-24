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

package de.metas.cucumber.stepdefs.pos;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.pos.POSTerminalId;
import org.compiere.model.I_C_POS;

/**
 * Holds the {@code C_POS} (POS terminal) records created by {@link C_POS_StepDef}, keyed by their scenario identifier.
 */
public class C_POS_StepDefData extends StepDefData<I_C_POS> implements StepDefDataGetIdAware<POSTerminalId, I_C_POS>
{
	public C_POS_StepDefData()
	{
		super(I_C_POS.class);
	}

	@Override
	public POSTerminalId extractIdFromRecord(final I_C_POS record)
	{
		return POSTerminalId.ofRepoId(record.getC_POS_ID());
	}
}
