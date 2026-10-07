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

import com.google.common.collect.ImmutableList;
import de.metas.edi.api.EDIDesadvId;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.api.impl.DesadvBL;
import de.metas.edi.model.I_M_InOut;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.util.List;

/**
 * Manually closes a DESADV to Sent status, even when FulfillmentPercent < 100%.
 * <p>
 * This process is for exceptional cases (e.g., under-delivery where remaining quantities will never be shipped).
 * It sets both EDI_ExportStatus=Sent and Processed=Y on the DESADV.
 * <p>
 * Precondition: All linked InOuts must be Sent or DontSend. If any InOut is Pending, Error, or Invalid,
 * the process is rejected with an error message listing the unresolved InOuts.
 */
public class EDI_Desadv_Close extends JavaProcess implements IProcessPrecondition
{
	private static final AdMessageKey MSG_UNRESOLVED_INOUTS = AdMessageKey.of("EDI_Desadv_Close_UnresolvedInOuts");

	@NonNull private final DesadvBL desadvBL = SpringContextHolder.instance.getBean(DesadvBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final I_EDI_Desadv desadv = desadvBL.getById(EDIDesadvId.ofRepoId(context.getSingleSelectedRecordId()));
		if (!desadvBL.isOneDesadvPerShipment(desadv))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not one desadv per shipment, so should be done the old way via ExportStatus change");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final EDIDesadvId desadvId = EDIDesadvId.ofRepoId(getRecord_ID());
		final I_EDI_Desadv desadv = desadvBL.getById(desadvId);

		if (!desadvBL.isOneDesadvPerShipment(desadv))
		{
			return MSG_OK;
		}

		final List<I_M_InOut> allInOuts = desadvBL.retrieveAllInOuts(desadv);
		if (allInOuts.isEmpty())
		{
			desadv.setEDI_ExportStatus(EDIExportStatus.DontSend.getCode());
			desadv.setProcessed(true);
			desadvBL.save(desadv);
			return MSG_OK;
		}

		final List<I_M_InOut> unresolvedInOuts = allInOuts.stream()
				.filter(inOut -> !EDIExportStatus.ofCode(inOut.getEDI_ExportStatus()).isProcessed())
				.collect(ImmutableList.toImmutableList());

		if (!unresolvedInOuts.isEmpty())
		{
			final String errorDetails = desadvBL.buildAggregatedErrorMessage(unresolvedInOuts);
			throw new AdempiereException(MSG_UNRESOLVED_INOUTS, errorDetails);
		}

		final boolean containsSentInOuts = allInOuts.stream().anyMatch(inOut -> EDIExportStatus.ofCode(inOut.getEDI_ExportStatus()).isSent());
		final EDIExportStatus ediExportStatus = containsSentInOuts ? EDIExportStatus.Sent : EDIExportStatus.DontSend;

		desadv.setEDI_ExportStatus(ediExportStatus.getCode());
		desadv.setProcessed(true);
		desadvBL.save(desadv);

		return MSG_OK;
	}
}
