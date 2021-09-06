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

package de.metas.vertical.healthcare.alberta.bpartner.patient;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAware;
import de.metas.vertical.healthcare.alberta.model.I_C_BPartner_AlbertaPatient;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;

@Repository
public class AlbertaPatientRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	public AlbertaPatient save(@NonNull final AlbertaPatient patient)
	{
		final I_C_BPartner_AlbertaPatient record = InterfaceWrapperHelper.loadOrNew(patient.getBPartnerAlbertaPatientId(), I_C_BPartner_AlbertaPatient.class);

		record.setC_BPartner_ID(patient.getBPartnerId().getRepoId());

		record.setC_BPartner_Hospital_ID(repoIdFromNullable(patient.getHospitalId()));

		record.setC_BPartner_Payer_ID(repoIdFromNullable(patient.getPayerId()));

		record.setAD_User_FieldNurse_ID(repoIdFromNullable(patient.getFieldNurseId()));

		record.setAD_User_CreatedBy_ID(repoIdFromNullable(patient.getCreatedById()));

		record.setAD_User_UpdatedBy_ID(repoIdFromNullable(patient.getUpdatedById()));

		record.setDischargeDate(TimeUtil.asTimestamp(patient.getDischargeDate()));
		record.setCopaymentTo(TimeUtil.asTimestamp(patient.getCopaymentTo()));
		record.setCopaymentFrom(TimeUtil.asTimestamp(patient.getCopaymentFrom()));
		record.setDeactivationDate(TimeUtil.asTimestamp(patient.getDeactivationDate()));
		record.setCreatedAt(TimeUtil.asTimestamp(patient.getCreatedAt()));
		record.setUpdatedAt(TimeUtil.asTimestamp(patient.getUpdatedAt()));

		record.setIsTransferPatient(patient.isTransferPatient());
		record.setIsIVTherapy(patient.isIVTherapy());

		record.setPayerType(patient.getPayerType() != null ? patient.getPayerType().getCode() : null);
		record.setDeactivationReason(patient.getDeactivationReason() != null ? patient.getDeactivationReason().getCode() : null);

		record.setNumberOfInsured(patient.getNumberOfInsured());

		record.setDeactivationComment(patient.getDeactivationComment());

		InterfaceWrapperHelper.save(record);

		return toAlbertaPatient(record);
	}

	@NonNull
	public Optional<AlbertaPatient> getByBPartnerId(@NonNull final BPartnerId bPartnerId)
	{
		return queryBL.createQueryBuilder(I_C_BPartner_AlbertaPatient.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_AlbertaPatient.COLUMNNAME_C_BPartner_ID, bPartnerId)
				.create()
				.firstOnlyOptional(I_C_BPartner_AlbertaPatient.class)
				.map(this::toAlbertaPatient);
	}

	@NonNull
	public AlbertaPatient toAlbertaPatient(@NonNull final I_C_BPartner_AlbertaPatient record)
	{
		final BPartnerAlbertaPatientId bPartnerAlbertaPatientId = BPartnerAlbertaPatientId.ofRepoId(record.getC_BPartner_AlbertaPatient_ID());
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(record.getC_BPartner_ID());
		final BPartnerId hospitalId = BPartnerId.ofRepoIdOrNull(record.getC_BPartner_Hospital_ID());
		final BPartnerId payerId = BPartnerId.ofRepoIdOrNull(record.getC_BPartner_Payer_ID());
		final UserId fieldNurseId = UserId.ofRepoIdOrNull(record.getAD_User_FieldNurse_ID());
		final UserId createdById = UserId.ofRepoIdOrNull(record.getAD_User_CreatedBy_ID());
		final UserId updatedById = UserId.ofRepoIdOrNull(record.getAD_User_UpdatedBy_ID());

		return AlbertaPatient.builder()
				.bPartnerAlbertaPatientId(bPartnerAlbertaPatientId)
				.bPartnerId(bPartnerId)
				.hospitalId(hospitalId)
				.dischargeDate(TimeUtil.asLocalDate(record.getDischargeDate(), SystemTime.zoneId()))
				.payerId(payerId)
				.payerType(PayerType.ofCodeNullable(record.getPayerType()))
				.numberOfInsured(record.getNumberOfInsured())
				.copaymentFrom(TimeUtil.asLocalDate(record.getCopaymentFrom(), SystemTime.zoneId()))
				.copaymentTo(TimeUtil.asLocalDate(record.getCopaymentTo(), SystemTime.zoneId()))
				.isTransferPatient(record.isTransferPatient())
				.isIVTherapy(record.isIVTherapy())
				.fieldNurseId(fieldNurseId)
				.deactivationReason(DeactivationReasonType.ofCodeNullable(record.getDeactivationReason()))
				.deactivationDate(TimeUtil.asLocalDate(record.getDeactivationDate(), SystemTime.zoneId()))
				.deactivationComment(record.getDeactivationComment())
				.createdAt(TimeUtil.asInstant(record.getCreatedAt()))
				.createdById(createdById)
				.updatedAt(TimeUtil.asInstant(record.getUpdatedAt()))
				.updatedById(updatedById)
				.build();
	}

	private int repoIdFromNullable(@Nullable final RepoIdAware repoIdAware)
	{
		if (repoIdAware == null)
		{
			return -1;
		}

		return repoIdAware.getRepoId();
	}
}
