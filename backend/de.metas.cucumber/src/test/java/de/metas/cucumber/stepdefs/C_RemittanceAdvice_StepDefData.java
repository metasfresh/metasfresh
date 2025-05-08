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

package de.metas.cucumber.stepdefs;

import de.metas.remittanceadvice.RemittanceAdviceId;
import de.metas.remittanceadvice.RemittanceAdviceLineId;
import lombok.NonNull;
import org.compiere.model.I_C_RemittanceAdvice;

public class C_RemittanceAdvice_StepDefData extends StepDefData<I_C_RemittanceAdvice> implements StepDefDataGetIdAware<RemittanceAdviceId, I_C_RemittanceAdvice>
{
	public C_RemittanceAdvice_StepDefData()
	{
		super(I_C_RemittanceAdvice.class);
	}

	@Override
	public RemittanceAdviceId extractIdFromRecord(@NonNull final I_C_RemittanceAdvice record)
	{
		return RemittanceAdviceId.ofRepoId(record.getC_RemittanceAdvice_ID());
	}
}
