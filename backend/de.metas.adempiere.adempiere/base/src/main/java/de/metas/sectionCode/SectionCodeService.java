/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.sectionCode;

import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

@Service
public class SectionCodeService
{
	private final SectionCodeRepository sectionCodeRepository;

	public SectionCodeService(@NonNull final SectionCodeRepository sectionCodeRepository)
	{
		this.sectionCodeRepository = sectionCodeRepository;
	}

	@NonNull
	public SectionCodeId getSectionCodeIdByValue(@NonNull final OrgId orgId, @NonNull final String value)
	{
		return sectionCodeRepository.getSectionCodeIdByValue(orgId, value)
				.orElseThrow(() -> new AdempiereException("No SectionCode found for SectionCode.value=" + value));
	}

	@NonNull
	public SectionCode getById(@NonNull final SectionCodeId sectionCodeId)
	{
		return sectionCodeRepository.getById(sectionCodeId);
	}
}
