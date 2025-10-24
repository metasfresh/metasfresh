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

package de.metas.cucumber.stepdefs.payment;

import de.metas.banking.PaySelectionId;
import de.metas.banking.PaySelectionLineId;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import org.compiere.model.I_C_PaySelectionLine;

public class C_PaySelectionLine_StepDefData extends StepDefData<I_C_PaySelectionLine> implements StepDefDataGetIdAware<PaySelectionLineId, I_C_PaySelectionLine>
{
	public C_PaySelectionLine_StepDefData()
	{
		super(I_C_PaySelectionLine.class);
	}

	@Override
	public PaySelectionLineId extractIdFromRecord(final I_C_PaySelectionLine record)
	{
		return PaySelectionLineId.ofRepoId(PaySelectionId.ofRepoId(record.getC_PaySelection_ID()), record.getC_PaySelectionLine_ID());
	}
}
