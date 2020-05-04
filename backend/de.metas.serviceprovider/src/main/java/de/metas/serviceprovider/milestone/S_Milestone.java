/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.serviceprovider.milestone;

import de.metas.serviceprovider.external.reference.ExternalReferenceRepository;
import de.metas.serviceprovider.external.reference.ExternalReferenceType;
import de.metas.serviceprovider.model.I_S_Milestone;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_S_Milestone.class)
@Component
public class S_Milestone
{
	private final ExternalReferenceRepository externalReferenceRepository;

	public S_Milestone(final ExternalReferenceRepository externalReferenceRepository)
	{
		this.externalReferenceRepository = externalReferenceRepository;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void afterDelete(@NonNull final I_S_Milestone record){
		externalReferenceRepository.deleteByRecordIdAndType(record.getS_Milestone_ID(), ExternalReferenceType.MILESTONE_ID);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_S_Milestone.COLUMNNAME_IsActive)
	public void reactivateLinkedExternalReferences(@NonNull final I_S_Milestone record)
	{
		if (record.isActive())
		{
			externalReferenceRepository.updateIsActiveByRecordIdAndType(record.getS_Milestone_ID(), ExternalReferenceType.MILESTONE_ID, record.isActive());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_S_Milestone.COLUMNNAME_IsActive)
	public void inactivateLinkedExternalReferences(@NonNull final I_S_Milestone record)
	{
		if (!record.isActive())
		{
			externalReferenceRepository.updateIsActiveByRecordIdAndType(record.getS_Milestone_ID(), ExternalReferenceType.MILESTONE_ID, record.isActive());
		}
	}
}
