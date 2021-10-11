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

package de.metas.vertical.healthcare.alberta.prescription.dao;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.bpartner.patient.AlbertaPatient;
import de.metas.vertical.healthcare.alberta.bpartner.patient.AlbertaPatientRepository;
import de.metas.vertical.healthcare.alberta.model.I_Alberta_PrescriptionRequest;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class AlbertaPrescriptionRequestDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	private final AlbertaPatientRepository albertaPatientRepository;

	public AlbertaPrescriptionRequestDAO(@NonNull final AlbertaPatientRepository albertaPatientRepository)
	{
		this.albertaPatientRepository = albertaPatientRepository;
	}

	@NonNull
	public List<I_Alberta_PrescriptionRequest> getByOrderIds(@NonNull final Set<OrderId> orderIds)
	{
		return queryBL.createQueryBuilder(I_Alberta_PrescriptionRequest.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_Alberta_PrescriptionRequest.COLUMN_C_Order_ID, orderIds)
				.create()
				.list();
	}

	@Nullable
	public I_C_BPartner getPayer(@NonNull final I_Alberta_PrescriptionRequest prescriptionRequest)
	{
		final BPartnerId patientBPartnerId = BPartnerId.ofRepoId(prescriptionRequest.getC_BPartner_Patient_ID());
		final Optional<AlbertaPatient> albertaPatient = albertaPatientRepository.getByBPartnerId(patientBPartnerId);

		if (!albertaPatient.isPresent())
		{
			return null;
		}

		if (albertaPatient.get().getPayerId() == null)
		{
			return null;
		}

		final BPartnerId payerId = albertaPatient.get().getPayerId();
		return bpartnerDAO.getById(payerId, I_C_BPartner.class);
	}
}
