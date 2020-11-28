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

package de.metas.serviceprovider.user;

import de.metas.adempiere.model.I_AD_User;
import de.metas.serviceprovider.external.reference.ExternalReferenceRepository;
import de.metas.serviceprovider.external.reference.ExternalReferenceType;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_User.class)
@Component
public class AD_User
{
	private final ExternalReferenceRepository externalReferenceRepository;

	public AD_User(final ExternalReferenceRepository externalReferenceRepository)
	{
		this.externalReferenceRepository = externalReferenceRepository;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteLinkedExternalReferences(@NonNull final I_AD_User record)
	{
		externalReferenceRepository.deleteByRecordIdAndType(record.getAD_User_ID(), ExternalReferenceType.USER_ID);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_AD_User.COLUMNNAME_IsActive)
	public void reactivateLinkedExternalReferences(@NonNull final I_AD_User record)
	{
		if (record.isActive())
		{
			externalReferenceRepository.updateIsActiveByRecordIdAndType(record.getAD_User_ID(), ExternalReferenceType.USER_ID, record.isActive());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_AD_User.COLUMNNAME_IsActive)
	public void inactivateLinkedExternalReferences(@NonNull final I_AD_User record)
	{
		if (!record.isActive())
		{
			externalReferenceRepository.updateIsActiveByRecordIdAndType(record.getAD_User_ID(), ExternalReferenceType.USER_ID, record.isActive());
		}
	}
}
