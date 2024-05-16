package de.metas.materialtracking.qualityBasedInvoicing.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.location.CountryId;
import de.metas.materialtracking.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorInvoicingInfo;
import de.metas.pricing.PricingSystemId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_PriceList_Version;

/**
 * Wraps an {@link I_C_Invoice_Candidate} and make it behave like {@link IVendorInvoicingInfo}.
 *
 * @author tsa
 */
/* package */class MaterialTrackingAsVendorInvoicingInfo implements IVendorInvoicingInfo
{
	// Loaded values
	private PricingSystemId _pricingSystemId;
	private I_C_Flatrate_Term _flatrateTerm = null;
	private String _invoiceRule = null;
	private boolean _invoiceRuleSet = false;

	private I_M_PriceList_Version _priceListVersion = null;
	private final I_M_Material_Tracking materialTracking;

	public MaterialTrackingAsVendorInvoicingInfo(@NonNull final I_M_Material_Tracking materialTracking)
	{
		this.materialTracking = materialTracking;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " ["
				+ "Bill_BPartner_ID=" + getBill_BPartner_ID()
				+ ", Bill_Location_ID=" + getBill_Location_ID()
				+ ", Bill_User_ID=" + getBill_User_ID()
				+ ", C_Currency_ID=" + getC_Currency_ID()
				+ ", C_Flatrate_Term=" + (_flatrateTerm == null ? "not-loaded" : _flatrateTerm)
				+ ", InvoiceRule=" + (_invoiceRuleSet ? _invoiceRule : "not-loaded")
				+ ", M_PricingSystem_ID=" + (_pricingSystemId == null ? "not-loaded" : _pricingSystemId)
				+ ", M_PriceList_Version_ID=" + (_priceListVersion == null ? "not-set" : _priceListVersion.getM_PriceList_Version_ID())
				+ "]";
	}

	@Override
	public BPartnerId getBill_BPartner_ID()
	{
		return BPartnerId.ofRepoId(getC_Flatrate_Term().getBill_BPartner_ID());
	}

	@Override
	public int getBill_Location_ID()
	{
		return getC_Flatrate_Term().getBill_Location_ID();
	}

	@Override
	public int getBill_User_ID()
	{
		return getC_Flatrate_Term().getBill_User_ID();
	}

	@Override
	public int getC_Currency_ID()
	{
		return getM_PriceList_Version().getM_PriceList().getC_Currency_ID();
	}

	@Override
	public CountryId getCountryId()
	{
		return CoalesceUtil.coalesceSuppliersNotNull(
				// first, try the contract's location from the tine the contract was created
				() -> {
					final I_C_Location billLocationValue = getC_Flatrate_Term().getBill_Location_Value();
					return billLocationValue != null ? CountryId.ofRepoId(billLocationValue.getC_Country_ID()) : null;
				},
				// second, try the current C_Location of the C_BPartner_Location
				() -> {
					final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
					return bPartnerDAO.getCountryId(BPartnerLocationId.ofRepoId(getC_Flatrate_Term().getBill_BPartner_ID(), getC_Flatrate_Term().getBill_Location_ID()));
				}
		);
	}

	@Override
	public I_M_PriceList_Version getM_PriceList_Version()
	{
		return _priceListVersion;
	}

	@Override
	public PricingSystemId getPricingSystemId()
	{
		PricingSystemId pricingSystemId = _pricingSystemId;
		if (pricingSystemId == null)
		{
			pricingSystemId = _pricingSystemId = PricingSystemId.ofRepoId(getC_Flatrate_Term().getM_PricingSystem_ID());
		}
		return pricingSystemId;
	}

	@Override
	public I_C_Flatrate_Term getC_Flatrate_Term()
	{
		if (_flatrateTerm == null)
		{
			_flatrateTerm = Services.get(IFlatrateDAO.class).getById(materialTracking.getC_Flatrate_Term_ID());

			// shouldn't be null because we prevent even material-tracking purchase orders without a flatrate term.
			Check.errorIf(_flatrateTerm == null, "M_Material_Tracking {} has no flatrate term", materialTracking);
		}
		return _flatrateTerm;
	}

	@Override
	public String getInvoiceRule()
	{
		if (!_invoiceRuleSet)
		{
			//
			// Try getting the InvoiceRule from Flatrate Term
			final I_C_Flatrate_Term flatrateTerm = getC_Flatrate_Term();
			final String invoiceRule = flatrateTerm
					.getC_Flatrate_Conditions()
					.getInvoiceRule();

			Check.assumeNotEmpty(invoiceRule, "Unable to retrieve invoiceRule from materialTracking {}", materialTracking);
			_invoiceRule = invoiceRule;
			_invoiceRuleSet = true;
		}

		return _invoiceRule;
	}

	/* package */void setM_PriceList_Version(final I_M_PriceList_Version priceListVersion)
	{
		_priceListVersion = priceListVersion;
	}
}
