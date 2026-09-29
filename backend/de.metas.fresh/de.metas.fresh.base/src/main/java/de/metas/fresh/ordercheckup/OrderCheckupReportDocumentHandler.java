package de.metas.fresh.ordercheckup;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;

import java.time.LocalDate;

/**
 * Makes {@link I_C_Order_MFGWarehouse_Report} wrap-able as a document, so the document engine can read its
 * {@code DocStatus} column (defaulted to {@code CO}) and surface it on the record's {@code C_Doc_Outbound_Log}.
 * <p>
 * These reports are never actively processed through the document workflow - they are created already
 * completed - so the action methods (only {@code completeIt} is non-default here) are unsupported.
 */
class OrderCheckupReportDocumentHandler implements DocumentHandler
{
	private static I_C_Order_MFGWarehouse_Report extractRecord(final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_C_Order_MFGWarehouse_Report.class);
	}

	@Override
	public String getSummary(final DocumentTableFields docFields)
	{
		return "Bestellkontrolle " + extractRecord(docFields).getC_Order_MFGWarehouse_Report_ID();
	}

	@Override
	public String getDocumentInfo(final DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public int getDoc_User_ID(final DocumentTableFields docFields)
	{
		return extractRecord(docFields).getAD_User_Responsible_ID();
	}

	@Override
	public LocalDate getDocumentDate(final DocumentTableFields docFields)
	{
		return TimeUtil.asLocalDate(extractRecord(docFields).getCreated());
	}

	@Override
	public String completeIt(final DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException("Bestellkontrolle reports are created already completed; they are not completed through the document engine");
	}
}
