package de.metas.phonecall.process;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.order.OrderFactory;
import de.metas.phonecall.PhonecallSchedule;
import de.metas.phonecall.PhonecallScheduleId;
import de.metas.phonecall.service.PhonecallScheduleRepository;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class C_Phonecall_Schedule_CreateSalesOrder extends JavaProcess implements IProcessPrecondition
{
	private final PhonecallScheduleRepository phonecallSchedueRepo = Adempiere.getBean(PhonecallScheduleRepository.class);
	final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	@Override
	protected String doIt() throws Exception
	{
		final PhonecallSchedule phonecallSchedule = phonecallSchedueRepo.getById(PhonecallScheduleId.ofRepoId(getRecord_ID()));

		phonecallSchedueRepo.markAsOrdered(phonecallSchedule);

		final DocTypeQuery query = DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_SalesOrder)
				.docSubType(X_C_DocType.DOCSUBTYPE_StandardOrder)
				.adClientId(Env.getAD_Client_ID())
				.build();

		final int docTypeId = docTypeDAO.getDocTypeId(query).getRepoId();

		final I_C_Order draftOrder = OrderFactory.newSalesOrder()
				.billBPartner(phonecallSchedule.getBpartnerAndLocationId().getBpartnerId().getRepoId(),
						phonecallSchedule.getBpartnerAndLocationId().getRepoId(),
						phonecallSchedule.getContactId().getRepoId())
				.shipBPartner(phonecallSchedule.getBpartnerAndLocationId().getBpartnerId(),
						phonecallSchedule.getBpartnerAndLocationId().getRepoId(),
						phonecallSchedule.getContactId().getRepoId())
				.docType(docTypeId)
				.createDraftOrderHeader();

		final String adWindowId = null; // auto
		getResult().setRecordToOpen(TableRecordReference.of(draftOrder), adWindowId, OpenTarget.SingleDocument);

		return MSG_OK;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if(!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}
}
