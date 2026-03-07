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

package de.metas.edi.process.export.json;

import de.metas.attachments.AttachmentEntryService;
import de.metas.common.util.Check;
import de.metas.edi.api.impl.EDIInOutDAO;
import de.metas.edi.model.I_M_InOut;
import de.metas.inout.InOutId;
import de.metas.postgrest.process.PostgRESTProcessExecutor;
import de.metas.process.Param;
import de.metas.report.ReportResultData;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

/**
 * Exports EPCIS event data for one shipment as JSON.
 * Directs {@link de.metas.postgrest.process.PostgRESTProcessExecutor} to fetch from
 * {@code M_InOut_Export_EPCIS_JSON_V} and store the result.
 */
public class M_InOut_EPCIS_Export_JSON extends PostgRESTProcessExecutor
{
	public static final String PARAM_M_InOut_ID = "M_InOut_ID";

	@NonNull private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	@NonNull private final EDIInOutDAO ediInOutDAO = SpringContextHolder.instance.getBean(EDIInOutDAO.class);

	@Param(parameterName = PARAM_M_InOut_ID, mandatory = true)
	private int m_inout_id;

	@Override
	protected final CustomPostgRESTParameters beforePostgRESTCall()
	{
		final boolean calledViaAPI = isCalledViaAPI();

		return CustomPostgRESTParameters.builder()
				.storeJsonFile(!calledViaAPI)
				.expectSingleResult(true) // because we export exactly one record, we don't want the JSON to be an array
				.build();
	}

	@Override
	protected final String afterPostgRESTCall()
	{
		final ReportResultData reportData = Check.assumeNotNull(getResult().getReportData(), "reportData shall not be null after successful invocation");

		final I_M_InOut record = loadRecordOutOfTrx();
		// note that if it was called via API, then the result will also be in API_Response_Audit, but there it will be removed after some time
		final TableRecordReference recordReference = TableRecordReference.of(record);

		addLog("Attaching result with filename {} to the {}-record with ID {}",
				reportData.getReportFilename(),
				recordReference.getTableName(),
				recordReference.getRecord_ID()
		);
		attachmentEntryService.createNewAttachment(
				record,
				reportData.getReportFilename(),
				reportData.getReportDataByteArray());

		return MSG_OK;
	}

	private I_M_InOut loadRecordOutOfTrx()
	{
		final I_M_InOut record = ediInOutDAO.getByIdOutOfTrx(InOutId.ofRepoId(m_inout_id));
		return Check.assumeNotNull(record, "M_InOut with ID={} shall not be null", m_inout_id);
	}
}
