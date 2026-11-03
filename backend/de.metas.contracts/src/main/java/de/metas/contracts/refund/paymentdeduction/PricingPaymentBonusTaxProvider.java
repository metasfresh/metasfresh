package de.metas.contracts.refund.paymentdeduction;

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.location.adapter.InvoiceDocumentLocationAdapterFactory;
import de.metas.lang.SOTrx;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import static java.math.BigDecimal.ONE;

/**
 * The tax category of the bonus product comes from its regular pricing in the sales invoice's price list, and the tax from the invoice's bill location and date
 * (the same way the tax of a periodic refund on a bonus product is found).
 */
@Component
public class PricingPaymentBonusTaxProvider implements PaymentBonusTaxProvider
{
	@VisibleForTesting
	static final AdMessageKey MSG_BONUS_PRODUCT_HAS_NO_PRICE = AdMessageKey.of("de.metas.contracts.refund.paymentdeduction.BonusProductHasNoPrice");

	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);

	@Override
	public Tax getTax(@NonNull final I_C_Invoice salesInvoice, @NonNull final ProductId bonusProductId)
	{
		final OrgId orgId = OrgId.ofRepoId(salesInvoice.getAD_Org_ID());
		final SOTrx soTrx = SOTrx.ofBoolean(salesInvoice.isSOTrx());
		final BPartnerLocationAndCaptureId billLocationId = InvoiceDocumentLocationAdapterFactory.locationAdapter(salesInvoice).getBPartnerLocationAndCaptureId();

		final IEditablePricingContext pricingContext = pricingBL
				.createInitialContext(orgId, bonusProductId, BPartnerId.ofRepoId(salesInvoice.getC_BPartner_ID()), Quantitys.of(ONE, bonusProductId), soTrx)
				.setReferencedObject(salesInvoice)
				.setPriceDate(TimeUtil.asLocalDate(salesInvoice.getDateInvoiced(), orgDAO.getTimeZone(orgId)))
				.setPriceListId(PriceListId.ofRepoIdOrNull(salesInvoice.getM_PriceList_ID()))
				.setCountryId(bpartnerDAO.getCountryId(billLocationId.getBpartnerLocationId()));

		final IPricingResult pricingResult = pricingBL.calculatePrice(pricingContext);
		if (!pricingResult.isCalculated() || pricingResult.getTaxCategoryId() == null)
		{
			throw new AdempiereException(MSG_BONUS_PRODUCT_HAS_NO_PRICE, productBL.getProductValueAndName(bonusProductId))
					.markAsUserValidationError()
					.setParameter("M_Product_ID", bonusProductId.getRepoId())
					.setParameter("C_Invoice_ID", salesInvoice.getC_Invoice_ID())
					.setParameter("M_PriceList_ID", salesInvoice.getM_PriceList_ID());
		}

		final TaxId taxId = taxBL.getTaxNotNull(
				salesInvoice,
				pricingResult.getTaxCategoryId(),
				bonusProductId.getRepoId(),
				salesInvoice.getDateInvoiced(),
				orgId,
				null, // warehouseId
				billLocationId,
				soTrx);
		return taxDAO.getTaxById(taxId);
	}
}
