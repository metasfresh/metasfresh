/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2025 metas GmbH
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

import de.metas.common.util.Check;
import de.metas.edi.api.impl.EDIInOutDAO;
import de.metas.edi.model.I_EDI_Document_Extension;
import de.metas.edi.model.I_M_InOut;
import de.metas.inout.InOutId;
import de.metas.postgrest.process.PostgRESTProcessExecutor;
import de.metas.process.Param;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;

/**
 * Exports one particular shipment ({@code M_InOut}) as JSON.
 * It directs {@link PostgRESTProcessExecutor} to store the result to disk if not called via API.
 * It also attaches the resulting JSON file to the shipment and sets the shipment's {@code EDI_ExportStatus} to "Sent".
 * <p>
 * <b>Array-mode contract:</b> a consolidated multi-source-order shipment links to N source DESADVs
 * via the {@code EDI_Desadv_M_InOut} junction. The underlying PostgREST view
 * {@code M_InOut_Export_EDI_DESADV_JSON_V} emits one row per junction entry — i.e. one row per
 * {@code (m_inout_id, edi_desadv_id)} pair. This process therefore returns a JSON <i>array</i> of DESADV
 * documents (one element per linked source DESADV), not a single object. The downstream Camel route is
 * responsible for fanning the array out into N EDIFACT messages.
 */
public class M_InOut_EDI_Export_JSON extends EDI_Export_JSON
{
	public static final String PARAM_M_InOut_ID = "M_InOut_ID";

	private final EDIInOutDAO ediInOutDAO = SpringContextHolder.instance.getBean(EDIInOutDAO.class);

	@Param(parameterName = PARAM_M_InOut_ID, mandatory = true)
	private int m_inout_id;

	@Override
	protected I_EDI_Document_Extension loadRecordOutOfTrx()
	{
		final I_M_InOut record = ediInOutDAO.getByIdOutOfTrx(InOutId.ofRepoId(m_inout_id));
		return Check.assumeNotNull(record, "M_InOut with ID={} shall not be null", m_inout_id);
	}

	@Override
	protected void saveRecord(@NonNull final I_EDI_Document_Extension record)
	{
		final I_M_InOut inOutRecord = InterfaceWrapperHelper.create(record, I_M_InOut.class);
		ediInOutDAO.saveOutOfTrx(inOutRecord); //Should be saved before possible externalSystemInvocation could return an error
	}

	/**
	 * Returns {@code false} — see the class-level Javadoc for the array-mode contract.
	 * <p>
	 * An {@code M_InOut} may map to N DESADVs via the {@code EDI_Desadv_M_InOut} junction; the export
	 * view emits one row per linked DESADV. Per-element fan-out is handled by the Camel route.
	 */
	@Override
	protected boolean shouldExpectSingleResult()
	{
		return false;
	}
}
