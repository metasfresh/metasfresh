/*
 * #%L
 * de.metas.externalreference
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.externalreference.greeting;

import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.organization.OrgId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Greeting;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Greeting.class)
@Component
@RequiredArgsConstructor
public class C_Greeting
{
	private final ExternalReferenceRepository externalReferenceRepository;

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteLinkedExternalReferences(@NonNull final I_C_Greeting record)
	{
		externalReferenceRepository.deleteByRecordIdAndType(record.getC_Greeting_ID(), GreetingExternalReferenceType.GREETING);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_Greeting.COLUMNNAME_AD_Org_ID)
	public void updateLinkedExternalReferencesOrgId(@NonNull final I_C_Greeting record)
	{
		externalReferenceRepository.updateOrgIdByRecordIdAndType(record.getC_Greeting_ID(), GreetingExternalReferenceType.GREETING, OrgId.ofRepoId(record.getAD_Org_ID()));
	}
}
