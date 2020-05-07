package de.metas.fresh.mrp_productinfo.model.validator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;

import de.metas.fresh.mrp_productinfo.async.spi.impl.UpdateMRPProductInfoTableWorkPackageProcessor;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;

/**
 * @author metas-dev <dev@metasfresh.com>
 * @task https://metasfresh.atlassian.net/browse/FRESH-86
 *
 */
@Interceptor(I_PMM_PurchaseCandidate.class)
public class PMM_PurchaseCandidate
{
	public static final PMM_PurchaseCandidate INSTANCE = new PMM_PurchaseCandidate();

	private PMM_PurchaseCandidate()
	{
	}

	/**
	 * Note: it's important to enqueue the purchaseCandidate after it was saved, because we need its <code>PMM_PurchaseCandidate_ID</code>.
	 *
	 * @param purchaseCandidate
	 */
	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE,
			ModelValidator.TYPE_AFTER_DELETE }, ifColumnsChanged = I_PMM_PurchaseCandidate.COLUMNNAME_QtyPromised)
	public void enqueuePurchaseCandidates(final I_PMM_PurchaseCandidate purchaseCandidate)
	{
		UpdateMRPProductInfoTableWorkPackageProcessor.schedule(purchaseCandidate);
	}
}
