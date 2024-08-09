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

package de.metas.cucumber.stepdefs.edi;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.edi.api.impl.pack.EDIDesadvPackId;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack;
import lombok.NonNull;

public class EDI_Desadv_Pack_StepDefData extends StepDefData<I_EDI_Desadv_Pack>
		implements StepDefDataGetIdAware<EDIDesadvPackId, I_EDI_Desadv_Pack>
{
	public EDI_Desadv_Pack_StepDefData()
	{
		super(I_EDI_Desadv_Pack.class);
	}

	@Override
	public EDIDesadvPackId extractIdFromRecord(final I_EDI_Desadv_Pack record)
	{
		return EDIDesadvPackId.ofRepoId(record.getEDI_Desadv_Pack_ID());
	}

	@NonNull
	public ImmutableList<EDIDesadvPackId> getIds(@NonNull final String commaSeparatedIdentifiers)
	{
		return StepDefDataIdentifier.ofCommaSeparatedString(commaSeparatedIdentifiers)
				.stream()
				.map(this::getId)
				.collect(ImmutableList.toImmutableList());
	}

}
