/**
 *
 */
package de.metas.invoice.service;

import com.google.common.collect.ImmutableList;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.detail.InvoiceDetailCloneMapper;
import de.metas.invoice.detail.InvoiceWithDetailsService;
import lombok.NonNull;
import org.adempiere.model.CopyRecordSupportTableInfo;
import org.adempiere.model.GeneralCopyRecordSupport;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.PO;

import java.util.List;

public class MInvoicePOCopyRecordSupport extends GeneralCopyRecordSupport
{
	final InvoiceWithDetailsService invoiceWithDetailsService = SpringContextHolder.instance.getBean(InvoiceWithDetailsService.class);

	@Override
	public List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po, final List<CopyRecordSupportTableInfo> suggestedChildren)
	{
		return super.getSuggestedChildren(po, suggestedChildren)
				.stream()
				.filter(MInvoicePOCopyRecordSupport::isSuggestedChildren)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	protected void onRecordAndChildrenCopied(final PO to, final PO from)
	{
		final I_C_Invoice fromInvoice = InterfaceWrapperHelper.create(from, I_C_Invoice.class);
		final I_C_Invoice toInvoice = InterfaceWrapperHelper.create(to, I_C_Invoice.class);
		onRecordAndChildrenCopied(fromInvoice, toInvoice);
	}

	private void onRecordAndChildrenCopied(final I_C_Invoice fromInvoice, final I_C_Invoice targetInvoice)
	{
		final InvoiceDetailCloneMapper.ClonedInvoiceLinesInfo clonedInvoiceLinesInfo = MInvoiceLinePOCopyRecordSupport.getClonedInvoiceLinesInfo(targetInvoice);
		if (clonedInvoiceLinesInfo == null)
		{
			return;
		}

		invoiceWithDetailsService.copyDetailsToClone(InvoiceDetailCloneMapper.builder()
															 .originalInvoiceId(InvoiceId.ofRepoId(fromInvoice.getC_Invoice_ID()))
															 .targetInvoiceId(InvoiceId.ofRepoId(targetInvoice.getC_Invoice_ID()))
															 .clonedInvoiceLinesInfo(clonedInvoiceLinesInfo)
															 .build());
	}

	private static boolean isSuggestedChildren(@NonNull final CopyRecordSupportTableInfo childTableInfo)
	{
		return I_C_InvoiceLine.Table_Name.equals(childTableInfo.getTableName());
	}
}
