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
import de.metas.edi.model.I_EDI_Document_Extension;
import de.metas.edi.model.I_M_InOut;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.postgrest.process.PostgRESTProcessExecutor;
import de.metas.process.Param;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;

/**
 * Exports one particular invoice to JSON.
 * It directs {@link PostgRESTProcessExecutor} to store the result to disk if not called via API.
 * It also attaches the resulting JSON file to the invoice and sets the invoice's {@code EDI_ExportStatus} to "Sent".
 */
public class M_InOut_EDI_Export_JSON extends EDI_Export_JSON
{
	public static final String PARAM_M_InOut_ID = "M_InOut_ID";
	
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	@Param(parameterName = PARAM_M_InOut_ID, mandatory = true)
	private int m_inout_id;

	@Override
	protected I_EDI_Document_Extension loadRecord()
	{
		final I_M_InOut record = inOutDAO.getById(InOutId.ofRepoId(m_inout_id), I_M_InOut.class);
		return Check.assumeNotNull(record, "M_InOut with ID={} shall not be null", m_inout_id);
	}

	@Override
	protected void saveRecord(final I_EDI_Document_Extension record)
	{
		final I_M_InOut inOutRecord = InterfaceWrapperHelper.create(record, I_M_InOut.class);
		inOutDAO.save(inOutRecord);
	}
}
