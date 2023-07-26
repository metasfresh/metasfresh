/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.order.attachmenteditor;

import de.metas.attachments.AttachmentEntryRepository;
import de.metas.order.OrderId;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.vertical.healthcare.alberta.bpartner.patient.AlbertaPatientRepository;
import de.metas.vertical.healthcare.alberta.prescription.dao.AlbertaPrescriptionRequestDAO;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;

public class OrderAttachmentRowsRepository
{
	private final AlbertaPrescriptionRequestDAO albertaPrescriptionRequestDAO;
	private final AttachmentEntryRepository attachmentEntryRepository;
	private final AlbertaPatientRepository albertaPatientRepository;

	private final LookupDataSource patientLookup;
	private final LookupDataSource payerLookup;
	private final LookupDataSource pharmacyLookup;

	@Builder
	public OrderAttachmentRowsRepository(
			@NonNull final AlbertaPrescriptionRequestDAO albertaPrescriptionRequestDAO,
			@NonNull final AttachmentEntryRepository attachmentEntryRepository,
			@NonNull final AlbertaPatientRepository albertaPatientRepository)
	{
		this.albertaPrescriptionRequestDAO = albertaPrescriptionRequestDAO;
		this.attachmentEntryRepository = attachmentEntryRepository;
		this.albertaPatientRepository = albertaPatientRepository;
		patientLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_C_BPartner.Table_Name);
		payerLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_C_BPartner.Table_Name);
		pharmacyLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_C_BPartner.Table_Name);
	}

	public OrderAttachmentRows getByPurchaseOrderId(@NonNull final OrderId purchaseOrderId)
	{
		return OrderAttachmentRowsLoader.builder()
				.albertaPrescriptionRequestDAO(albertaPrescriptionRequestDAO)
				.attachmentEntryRepository(attachmentEntryRepository)
				.albertaPatientRepository(albertaPatientRepository)
				.patientLookup(patientLookup)
				.payerLookup(payerLookup)
				.pharmacyLookup(pharmacyLookup)
				.purchaseOrderId(purchaseOrderId)
				.build()
				.load();
	}
}
