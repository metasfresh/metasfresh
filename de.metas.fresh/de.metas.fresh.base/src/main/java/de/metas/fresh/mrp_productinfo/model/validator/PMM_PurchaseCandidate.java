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

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE,
			ModelValidator.TYPE_BEFORE_DELETE }, ifColumnsChanged = I_PMM_PurchaseCandidate.COLUMNNAME_QtyPromised)
	public void enqueuePurchaseCandidates(final I_PMM_PurchaseCandidate purchaseCandidate)
	{
		UpdateMRPProductInfoTableWorkPackageProcessor.schedule(purchaseCandidate);
	}
}
