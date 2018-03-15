package de.metas.ui.web.handlingunits.process;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.Adempiere;
import org.compiere.model.I_M_InOut;

import de.metas.handlingunits.empties.IHUEmptiesService;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/* package */ abstract class WEBUI_M_ReceiptSchedule_CreateEmptiesReturns_Base extends ReceiptScheduleBasedProcess
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.accept();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	// services
	private final transient IHUEmptiesService huEmptiesService = Services.get(IHUEmptiesService.class);
	private final transient IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
	private final DocumentCollection documentsRepo = Adempiere.getBean(DocumentCollection.class);

	private final String _returnMovementType;
	private final int _targetWindowId;

	public WEBUI_M_ReceiptSchedule_CreateEmptiesReturns_Base(final String returnMovementType, final int targetWindowId)
	{
		Check.assumeNotEmpty(returnMovementType, "returnMovementType is not empty");
		Check.assume(targetWindowId > 0, "targetWindowId > 0");

		_returnMovementType = returnMovementType;
		_targetWindowId = targetWindowId;

	}

	private String getReturnMovementType()
	{
		return _returnMovementType;
	}

	private int getTargetWindowId()
	{
		return _targetWindowId;
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_M_ReceiptSchedule receiptSchedule = getProcessInfo().getRecordIfApplies(I_M_ReceiptSchedule.class, ITrx.TRXNAME_ThreadInherited).orElse(null);

		final int emptiesInOutId;
		if (receiptSchedule == null)
		{
			emptiesInOutId = createDraftEmptiesDocument();
		}
		else
		{
			emptiesInOutId = createDraftEmptiesInOutFromReceiptSchedule(receiptSchedule);
		}

		//
		// Notify frontend that the empties document shall be opened in single document layout (not grid)
		if (emptiesInOutId > 0)
		{
			getResult().setRecordToOpen(TableRecordReference.of(I_M_InOut.Table_Name, emptiesInOutId), getTargetWindowId(), OpenTarget.SingleDocument);
		}

		return MSG_OK;
	}

	private int createDraftEmptiesDocument()
	{
		final DocumentPath documentPath = DocumentPath.builder()
				.setDocumentType(WindowId.of(getTargetWindowId()))
				.setDocumentId(DocumentId.NEW_ID_STRING)
				.allowNewDocumentId()
				.build();

		final DocumentId documentId = documentsRepo.forDocumentWritable(documentPath, NullDocumentChangesCollector.instance, document -> {
			huEmptiesService.newReturnsInOutProducer(getCtx())
					.setMovementType(getReturnMovementType())
					.setMovementDate(SystemTime.asDayTimestamp())
					.fillReturnsInOutHeader(InterfaceWrapperHelper.create(document, I_M_InOut.class));
			return document.getDocumentId();
		});

		return documentId.toInt();
	}

	private int createDraftEmptiesInOutFromReceiptSchedule(final I_M_ReceiptSchedule receiptSchedule)
	{
		//
		// Create a draft "empties inout" without any line;
		// Lines will be created manually by the user.
		final I_M_InOut emptiesInOut = huEmptiesService.newReturnsInOutProducer(getCtx())
				.setMovementType(getReturnMovementType())
				.setMovementDate(SystemTime.asDayTimestamp())
				.setC_BPartner(receiptScheduleBL.getC_BPartner_Effective(receiptSchedule))
				.setC_BPartner_Location(receiptScheduleBL.getC_BPartner_Location_Effective(receiptSchedule))
				.setM_Warehouse(receiptScheduleBL.getM_Warehouse_Effective(receiptSchedule))
				.setC_Order(receiptSchedule.getC_Order())
				//
				.dontComplete()
				.create();

		return emptiesInOut == null ? -1 : emptiesInOut.getM_InOut_ID();
	}
}
