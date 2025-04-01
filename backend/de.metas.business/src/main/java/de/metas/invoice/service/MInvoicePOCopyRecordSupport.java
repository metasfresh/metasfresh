/**
 *
 */
package de.metas.invoice.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.detail.InvoiceDetailCloneMapper;
import de.metas.invoice.detail.InvoiceWithDetailsService;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.model.CopyRecordSupportTableInfo;
import org.adempiere.model.GeneralCopyRecordSupport;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Invoice_Detail;
import org.compiere.model.PO;

import javax.annotation.Nullable;
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
		final I_C_Invoice toInvoice = InterfaceWrapperHelper.create(to, I_C_Invoice.class);
		onRecordAndChildrenCopied(toInvoice);
	}

	private void onRecordAndChildrenCopied(final I_C_Invoice targetInvoice)
	{
		invoiceWithDetailsService.updateInvoiceLineId(InvoiceDetailCloneMapperImpl.builder()
															  .targetInvoiceId(InvoiceId.ofRepoId(targetInvoice.getC_Invoice_ID()))
															  .clonedInvoiceLinesInfo(MInvoiceLinePOCopyRecordSupport.getClonedInvoiceLinesInfo(targetInvoice))
															  .build());
	}

	private static boolean isSuggestedChildren(@NonNull final CopyRecordSupportTableInfo childTableInfo)
	{
		return I_C_InvoiceLine.Table_Name.equals(childTableInfo.getTableName()) ||
				I_C_Invoice_Detail.Table_Name.equals(childTableInfo.getTableName());
	}

	@Builder
	private static class InvoiceDetailCloneMapperImpl implements InvoiceDetailCloneMapper
	{
		@NonNull @Getter private final InvoiceId targetInvoiceId;
		@Nullable private final MInvoiceLinePOCopyRecordSupport.ClonedInvoiceLinesInfo clonedInvoiceLinesInfo;

		@NonNull
		public ImmutableMap<InvoiceLineId, InvoiceLineId> getOriginal2targetInvoiceLineIds()
		{
			return clonedInvoiceLinesInfo != null ? clonedInvoiceLinesInfo.getOriginal2targetInvoiceLineIds() : ImmutableMap.of();
		}
	}
}
