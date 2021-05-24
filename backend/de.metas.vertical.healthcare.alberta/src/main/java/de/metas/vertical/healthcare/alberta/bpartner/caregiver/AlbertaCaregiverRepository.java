/*
 * #%L
 * de.metas.vertical.healthcare.alberta
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.vertical.healthcare.alberta.bpartner.caregiver;

import de.metas.bpartner.BPartnerId;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.model.I_C_BPartner_AlbertaCareGiver;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AlbertaCaregiverRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	public AlbertaCaregiver save(final @NonNull AlbertaCaregiver caregiver)
	{
		final I_C_BPartner_AlbertaCareGiver record = InterfaceWrapperHelper.loadOrNew(caregiver.getBPartnerAlbertaCaregiverId(), I_C_BPartner_AlbertaCareGiver.class);

		record.setC_BPartner_ID(caregiver.getBPartnerId().getRepoId());
		record.setC_BPartner_Caregiver_ID(caregiver.getCaregiverId().getRepoId());

		record.setIsLegalCarer(caregiver.isLegalCarer());

		record.setType_Contact(caregiver.getType() != null ? caregiver.getType().getCode() : null);

		InterfaceWrapperHelper.save(record);

		return toAlbertaCaregiver(record);
	}

	@NonNull
	public Optional<AlbertaCaregiver> getByPatientAndCaregiver(
			@NonNull final BPartnerId patientId,
			@NonNull final BPartnerId caregiverId)
	{
		return queryBL.createQueryBuilder(I_C_BPartner_AlbertaCareGiver.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_AlbertaCareGiver.COLUMNNAME_C_BPartner_ID, patientId)
				.addEqualsFilter(I_C_BPartner_AlbertaCareGiver.COLUMNNAME_C_BPartner_Caregiver_ID, caregiverId)
				.create()
				.firstOnlyOptional(I_C_BPartner_AlbertaCareGiver.class)
				.map(this::toAlbertaCaregiver);
	}

	@NonNull
	public AlbertaCaregiver toAlbertaCaregiver(@NonNull final I_C_BPartner_AlbertaCareGiver record)
	{
		final BPartnerAlbertaCaregiverId bPartnerAlbertaCaregiverId = BPartnerAlbertaCaregiverId.ofRepoId(record.getC_BPartner_AlbertaCareGiver_ID());
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(record.getC_BPartner_ID());
		final BPartnerId caregiverId = BPartnerId.ofRepoId(record.getC_BPartner_Caregiver_ID());

		return AlbertaCaregiver.builder()
				.bPartnerAlbertaCaregiverId(bPartnerAlbertaCaregiverId)
				.bPartnerId(bPartnerId)
				.caregiverId(caregiverId)
				.type(CaregiverType.ofCodeNullable(record.getType_Contact()))
				.isLegalCarer(record.isLegalCarer())
				.build();
	}
}
