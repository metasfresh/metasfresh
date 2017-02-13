package de.metas.ui.web.handlingunits.process;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.context.annotation.Profile;

import de.metas.handlingunits.inout.ReceiptCorrectHUsProcessor;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.WebRestApiApplication;

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

@Profile(value = WebRestApiApplication.PROFILE_Webui)
public class WEBUI_M_ReceiptSchedule_SelectHUsToReverse extends JavaProcess implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_M_ReceiptSchedule receiptSchedule = getRecord(I_M_ReceiptSchedule.class);
		final List<I_M_HU> hus = ReceiptCorrectHUsProcessor.builder()
				.setM_ReceiptSchedule(receiptSchedule)
				.build()
				.getAvailableHUsToReverse();

		if (hus.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @M_HU_ID@");
		}

		getResult().setRecordsToSelectAfterExecution(TableRecordReference.ofList(hus));

		return MSG_OK;
	}
}
