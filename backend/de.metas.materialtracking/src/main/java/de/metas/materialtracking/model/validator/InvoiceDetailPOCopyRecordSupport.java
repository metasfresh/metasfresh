/*
 * #%L
 * de.metas.materialtracking
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

package de.metas.materialtracking.model.validator;

import org.adempiere.model.GeneralCopyRecordSupport;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice_Detail;
import org.compiere.model.PO;

public class InvoiceDetailPOCopyRecordSupport extends GeneralCopyRecordSupport
{
	@Override
	protected void onRecordCopied(final PO to, final PO from)
	{
		final I_C_Invoice_Detail toInvoiceDetail = InterfaceWrapperHelper.create(to, I_C_Invoice_Detail.class);

		toInvoiceDetail.setC_Invoice_Candidate_ID(-1);
		toInvoiceDetail.setPP_Order_ID(-1);
	}
}
