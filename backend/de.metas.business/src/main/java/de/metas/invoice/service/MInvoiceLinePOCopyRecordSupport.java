/*
 * #%L
 * de.metas.business
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

package de.metas.invoice.service;

import com.google.common.collect.ImmutableList;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.detail.InvoiceDetailCloneMapper;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.model.CopyRecordSupportTableInfo;
import org.adempiere.model.GeneralCopyRecordSupport;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Invoice_Detail;
import org.compiere.model.PO;

import javax.annotation.Nullable;
import java.util.List;

public class MInvoiceLinePOCopyRecordSupport extends GeneralCopyRecordSupport
{
	private static final String DYNATTR_ClonedInvoiceLinesInfo = "ClonedInvoiceLinesInfo";

	@Override
	protected void onRecordAndChildrenCopied(final PO to, final PO from)
	{
		final I_C_InvoiceLine toInvoiceLine = InterfaceWrapperHelper.create(to, I_C_InvoiceLine.class);
		final I_C_InvoiceLine fromInvoiceLine = InterfaceWrapperHelper.create(from, I_C_InvoiceLine.class);
		onRecordAndChildrenCopied(toInvoiceLine, fromInvoiceLine);
	}

	private void onRecordAndChildrenCopied(final I_C_InvoiceLine toInvoiceLine, final I_C_InvoiceLine fromInvoiceLine)
	{
		InterfaceWrapperHelper
				.computeDynAttributeIfAbsent(getTargetInvoice(), DYNATTR_ClonedInvoiceLinesInfo, InvoiceDetailCloneMapper.ClonedInvoiceLinesInfo::new)
				.addOriginalToClonedInvoiceLineMapping(
						InvoiceLineId.ofRepoId(fromInvoiceLine.getC_InvoiceLine_ID()),
						InvoiceLineId.ofRepoId(toInvoiceLine.getC_InvoiceLine_ID()));
	}

	@Nullable
	public static InvoiceDetailCloneMapper.ClonedInvoiceLinesInfo getClonedInvoiceLinesInfo(@NonNull final I_C_Invoice targetInvoice)
	{
		return InterfaceWrapperHelper.getDynAttribute(targetInvoice, DYNATTR_ClonedInvoiceLinesInfo);
	}

	@NonNull
	private I_C_Invoice getTargetInvoice()
	{
		return Check.assumeNotNull(getParentModel(I_C_Invoice.class), "target invoice is not null");
	}

	@Override
	public List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po, final List<CopyRecordSupportTableInfo> suggestedChildren)
	{
		return super.getSuggestedChildren(po, suggestedChildren)
				.stream()
				.filter(MInvoiceLinePOCopyRecordSupport::isSuggestedChildren)
				.collect(ImmutableList.toImmutableList());
	}

	private static boolean isSuggestedChildren(@NonNull final CopyRecordSupportTableInfo childTableInfo)
	{
		return !I_C_Invoice_Detail.Table_Name.equals(childTableInfo.getTableName());
	}
}
