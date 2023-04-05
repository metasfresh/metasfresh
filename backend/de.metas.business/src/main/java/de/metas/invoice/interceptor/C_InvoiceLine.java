package de.metas.invoice.interceptor;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner_product.IBPartnerProductBL;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceLineBL;
import de.metas.lang.SOTrx;
import de.metas.product.ProductId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.VatCodeId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice_Verification_SetLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_InvoiceLine.class)
@Component
public class C_InvoiceLine
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);
	private final IBPartnerProductBL partnerProductBL = Services.get(IBPartnerProductBL.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);

	/**
	 * Set QtyInvoicedInPriceUOM, just to make sure is up2date.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void setQtyInvoicedInPriceUOM(final I_C_InvoiceLine invoiceLine)
	{
		invoiceLineBL.setQtyInvoicedInPriceUOM(invoiceLine);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void updateIsReadOnly(final I_C_InvoiceLine invoiceLine)
	{
		invoiceBL.updateInvoiceLineIsReadOnlyFlags(InterfaceWrapperHelper.create(invoiceLine.getC_Invoice(), I_C_Invoice.class), invoiceLine);
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_CHANGE,
	}, ifColumnsChanged = I_C_InvoiceLine.COLUMNNAME_C_OrderLine_ID)
	public void setIsPackagingMaterial(final I_C_InvoiceLine invoiceLine)
	{
		if (invoiceLine.getC_OrderLine() == null)
		{
			// in case the c_orderline_id is removed, make sure the flag is on false. The user can set it on true, manually
			invoiceLine.setIsPackagingMaterial(false);
			return;
		}

		final de.metas.interfaces.I_C_OrderLine ol = InterfaceWrapperHelper.create(invoiceLine.getC_OrderLine(), de.metas.interfaces.I_C_OrderLine.class);

		invoiceLine.setIsPackagingMaterial(ol.isPackagingMaterial());
	}

	/**
	 * If we have a not-yet-processed invoice line in a verification-set, then allow to delete that line.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void deleteVerificationData(final I_C_InvoiceLine invoiceLine)
	{
		queryBL.createQueryBuilder(I_C_Invoice_Verification_SetLine.class)
				.addEqualsFilter(I_C_Invoice_Verification_SetLine.COLUMNNAME_C_InvoiceLine_ID, invoiceLine.getC_InvoiceLine_ID())
				.create()
				.delete();
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_C_InvoiceLine.COLUMNNAME_M_Product_ID })
	public void checkExcludedProducts(final I_C_InvoiceLine invoiceLine)
	{
		final org.compiere.model.I_C_Invoice invoice = invoiceBL.getById(InvoiceId.ofRepoId(invoiceLine.getC_Invoice_ID()));

		final ProductId productId = ProductId.ofRepoIdOrNull(invoiceLine.getM_Product_ID());

		if (productId == null)
		{
			return;
		}

		final BPartnerId partnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
		final SOTrx soTrx = SOTrx.ofBooleanNotNull(invoice.isSOTrx());
		partnerProductBL.assertNotExcludedFromTransaction(soTrx, productId, partnerId);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void copyDimensionFromHeader(final I_C_InvoiceLine invoiceLine)
	{
		final org.compiere.model.I_C_Invoice invoice = invoiceLine.getC_Invoice();
		invoiceLine.setM_SectionCode_ID(invoice.getM_SectionCode_ID());
		invoiceLine.setUserElementString1(invoice.getUserElementString1());
		invoiceLine.setUserElementString2(invoice.getUserElementString2());
		invoiceLine.setUserElementString3(invoice.getUserElementString3());
		invoiceLine.setUserElementString4(invoice.getUserElementString4());
		invoiceLine.setUserElementString5(invoice.getUserElementString5());
		invoiceLine.setUserElementString6(invoice.getUserElementString6());
		invoiceLine.setUserElementString7(invoice.getUserElementString7());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_C_InvoiceLine.COLUMNNAME_C_VAT_Code_ID })
	public void updateTaxFromVatCodeId(final I_C_InvoiceLine invoiceLine)
	{
		if (invoiceLine.isProcessed())
		{
			return;
		}
		final VatCodeId vatCodeId = VatCodeId.ofRepoIdOrNull(invoiceLine.getC_VAT_Code_ID());
		if (vatCodeId == null)
		{
			return;
		}
		final Tax tax = taxDAO.getTaxFromVatCodeIfManualOrNull(vatCodeId);
		if (tax != null)
		{
			invoiceLine.setC_Tax_ID(tax.getTaxId().getRepoId());
		}
	}
}
