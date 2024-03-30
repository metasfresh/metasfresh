/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.shipment;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.inout.InOutLineId;
import org.compiere.model.I_M_InOutLine;

public class M_InOutLine_StepDefData extends StepDefData<I_M_InOutLine>
		implements StepDefDataGetIdAware<InOutLineId, I_M_InOutLine>
{
	public M_InOutLine_StepDefData()
	{
		super(I_M_InOutLine.class);
	}

	@Override
	public InOutLineId extractIdFromRecord(final I_M_InOutLine record)
	{
		return InOutLineId.ofRepoId(record.getM_InOutLine_ID());
	}
}
