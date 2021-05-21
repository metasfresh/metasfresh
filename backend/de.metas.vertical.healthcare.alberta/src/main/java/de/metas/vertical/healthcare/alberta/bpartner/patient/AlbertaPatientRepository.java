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
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.model.I_C_BPartner_AlbertaPatient;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AlbertaPatientRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	public AlbertaPatient save(@NonNull final AlbertaPatient patient)
	{
		final I_C_BPartner_AlbertaPatient record = InterfaceWrapperHelper.loadOrNew(patient.getBPartnerAlbertaPatientId(), I_C_BPartner_AlbertaPatient.class);

		record.setC_BPartner_ID(patient.getBPartnerId().getRepoId());
		record.setC_BPartner_Hospital_ID(patient.getHospitalId().getRepoId());
		record.setDischargeDate(TimeUtil.asTimestamp(patient.getDischargeDate()));
		record.setC_BPartner_Payer_ID(patient.getPayerId().getRepoId());
		record.setNumberOfInsured(patient.getNumberOfInsured());
		record.setCopaymentFrom(TimeUtil.asTimestamp(patient.getCopaymentFrom()));
		record.setCopaymentTo(TimeUtil.asTimestamp(patient.getCopaymentTo()));
		record.setIsTransferPatient(patient.isTransferPatient());
		record.setIsIVTherapy(patient.isIVTherapy());
		record.setAD_User_FieldNurse_ID(patient.getFieldNurseId().getRepoId());
		record.setDeactivationDate(TimeUtil.asTimestamp(patient.getDeactivationDate()));
		record.setDeactivationComment(patient.getDeactivationComment());
		record.setCreatedAt(TimeUtil.asTimestamp(patient.getCreatedAt()));
		record.setAD_User_CreatedBy_ID(patient.getCreatedById().getRepoId());
		record.setUpdatedAt(TimeUtil.asTimestamp(patient.getUpdatedAt()));
		record.setAD_User_UpdatedBy_ID(patient.getUpdatedById().getRepoId());

		if (patient.getPayerType() != null)
		{
			record.setPayerType(patient.getPayerType().getCode());
		}

		if (patient.getDeactivationReason() != null)
		{
			record.setDeactivationReason(patient.getDeactivationReason().getCode());
		}

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
		final BPartnerId hospitalId = BPartnerId.ofRepoId(record.getC_BPartner_Hospital_ID());
		final BPartnerId payerId = BPartnerId.ofRepoId(record.getC_BPartner_Payer_ID());
		final UserId fieldNurseId = UserId.ofRepoId(record.getAD_User_FieldNurse_ID());
		final UserId createdById = UserId.ofRepoId(record.getAD_User_CreatedBy_ID());
		final UserId updatedById = UserId.ofRepoId(record.getAD_User_UpdatedBy_ID());

		return AlbertaPatient.builder()
				.bPartnerAlbertaPatientId(bPartnerAlbertaPatientId)
				.bPartnerId(bPartnerId)
				.hospitalId(hospitalId)
				.dischargeDate(TimeUtil.asInstant(record.getDischargeDate()))
				.payerId(payerId)
				.payerType(PayerType.ofCode(record.getPayerType()))
				.numberOfInsured(record.getNumberOfInsured())
				.copaymentFrom(TimeUtil.asInstant(record.getCopaymentFrom()))
				.copaymentTo(TimeUtil.asInstant(record.getCopaymentTo()))
				.isTransferPatient(record.isTransferPatient())
				.isIVTherapy(record.isIVTherapy())
				.fieldNurseId(fieldNurseId)
				.deactivationReason(DeactivationReasonType.ofCode(record.getDeactivationReason()))
				.deactivationDate(TimeUtil.asInstant(record.getDeactivationDate()))
				.deactivationComment(record.getDeactivationComment())
				.createdAt(TimeUtil.asInstant(record.getCreatedAt()))
				.createdById(createdById)
				.updatedAt(TimeUtil.asInstant(record.getUpdatedAt()))
				.updatedById(updatedById)
				.build();
	}
}
