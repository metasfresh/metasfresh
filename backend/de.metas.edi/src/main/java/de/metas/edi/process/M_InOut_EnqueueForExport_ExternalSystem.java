/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.edi.process;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.bpartner.BPartnerId;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.api.impl.EDIBPartnerConfigService;
import de.metas.edi.async.spi.impl.EDIWorkpackageProcessor;
import de.metas.edi.model.I_M_InOut;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

/**
 * Enqueues a single M_InOut for DESADV export via ExternalSystem.
 * <p>
 * This process is only available when the InOut's BPartner is configured for ExternalSystem DESADV export.
 * It creates a workpackage for the specific InOut, which is then processed by {@link EDIWorkpackageProcessor}.
 * <p>
 * The workpackage processor detects that the document is an M_InOut and exports it via
 * {@code exportViaExternalSystem} (lines 130-134 in EDIWorkpackageProcessor).
 * <p>
 * Use case: Selectively retry a single failing InOut without affecting already-sent InOuts on the same DESADV.
 */
public class M_InOut_EnqueueForExport_ExternalSystem extends JavaProcess implements IProcessPrecondition
{
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final EDIBPartnerConfigService ediBPartnerConfigService = SpringContextHolder.instance.getBean(EDIBPartnerConfigService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final I_M_InOut inOut = inOutBL.getById(InOutId.ofRepoId(context.getSingleSelectedRecordId()), I_M_InOut.class);

		final EDIExportStatus exportStatus = EDIExportStatus.ofCode(inOut.getEDI_ExportStatus());
		if (!exportStatus.isPendingOrError())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("InOut must be in Pending or Error status (current: " + exportStatus + ")");
		}

		final BPartnerId bPartnerId = inOutBL.getEffectiveDropshipPartnerId(inOut);
		if (!ediBPartnerConfigService.isDESADVExternalSystemRecipient(bPartnerId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("BPartner is not configured for ExternalSystem DESADV export");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_M_InOut inOut = inOutBL.getById(InOutId.ofRepoId(getRecord_ID()), I_M_InOut.class);

		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(getCtx(), EDIWorkpackageProcessor.class);


		queue.newWorkPackage()
				.setAD_PInstance_ID(getPinstanceId())
				.setPriority(IWorkPackageQueue.PRIORITY_AUTO)
				.bindToTrxName(get_TrxName())
				.addElement(inOut)
				.buildAndEnqueue();

		inOut.setEDI_ExportStatus(EDIExportStatus.Enqueued.getCode());
		inOutBL.save(inOut);

		addLog("Enqueued InOut {} for EDI export via ExternalSystem", inOut.getDocumentNo());

		return MSG_OK;
	}
}
