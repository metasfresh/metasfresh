package de.metas.invoice.interceptor;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner_product.IBPartnerProductBL;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceLineBL;
import de.metas.lang.SOTrx;
import de.metas.product.ProductId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.VatCodeId;
import de.metas.util.Services;
import lombok.NonNull;
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

	private final DimensionService dimensionService;

	public C_InvoiceLine(@NonNull final DimensionService dimensionService)
	{
		this.dimensionService = dimensionService;
	}

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

		//Product should always be set, but we can't make it mandatory in db because of existing cases
		final ProductId productId = ProductId.ofRepoId(invoiceLine.getM_Product_ID());
		final BPartnerId partnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
		final SOTrx soTrx = SOTrx.ofBooleanNotNull(invoice.isSOTrx());
		partnerProductBL.assertNotExcludedFromTransaction(soTrx, productId, partnerId);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void copyDimensionFromHeader(final I_C_InvoiceLine invoiceLine)
	{
		// only update the section code and user elements. It's not specified if the other dimensions should be inherited from the invoice header to the lines
		final org.compiere.model.I_C_Invoice invoice = invoiceLine.getC_Invoice();
		invoiceLine.setM_SectionCode_ID(invoice.getM_SectionCode_ID());

		final Dimension invoiceDimension = dimensionService.getFromRecord(invoice);
		dimensionService.updateRecordUserElements(invoiceLine, invoiceDimension);
		invoiceLine.setC_Harvesting_Calendar_ID(invoice.getC_Harvesting_Calendar_ID());
		invoiceLine.setHarvesting_Year_ID(invoice.getHarvesting_Year_ID());
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
