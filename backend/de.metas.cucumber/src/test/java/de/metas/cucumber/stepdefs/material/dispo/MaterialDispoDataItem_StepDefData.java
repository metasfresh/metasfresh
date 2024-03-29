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

package de.metas.cucumber.stepdefs.material.dispo;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.MaterialDispoDataItem;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Having a dedicated class to help the IOC-framework injecting the right instances, if a step-def needs more than one.
 */
public class MaterialDispoDataItem_StepDefData extends StepDefData<MaterialDispoDataItem>
{

	public MaterialDispoDataItem_StepDefData()
	{
		super(null);
	}

	public Optional<StepDefDataIdentifier> getFirstIdentifierByCandidateId(@NonNull final CandidateId candidateId, @Nullable final StepDefDataIdentifier excludeIdentifier)
	{
		for (final StepDefDataIdentifier identifier : getIdentifiers())
		{
			if (excludeIdentifier != null && StepDefDataIdentifier.equals(excludeIdentifier, identifier))
			{
				continue;
			}

			final MaterialDispoDataItem item = get(identifier);
			if (CandidateId.equals(item.getCandidateId(), candidateId))
			{
				return Optional.of(identifier);
			}
		}

		return Optional.empty();
	}
}
