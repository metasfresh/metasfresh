package de.metas.contracts.subscription;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.sql.Timestamp;

import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import lombok.NonNull;

/**
 * Creates the terms and delivery {@link I_C_SubscriptionProgress} entries that the subscription delivery tests work on.
 */
public class SubscriptionProgressTestHelper
{
	private final Timestamp now;

	/**
	 * @param now the reference date for {@link #entry(I_C_Flatrate_Term, String, int, int)}'s event dates
	 */
	public SubscriptionProgressTestHelper(@NonNull final Timestamp now)
	{
		this.now = now;
	}

	/**
	 * @return an active term whose product has the given product type
	 */
	public I_C_Flatrate_Term term(@NonNull final String productType)
	{
		return term(productType, true);
	}

	public I_C_Flatrate_Term term(@NonNull final String productType, final boolean termActive)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setProductType(productType);
		saveRecord(product);
		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setM_Product_ID(product.getM_Product_ID());
		term.setIsActive(termActive);
		saveRecord(term);
		return term;
	}

	/**
	 * @return a delivery entry of the given term, with its event date {@code eventDateDaysFromNow} days after the {@code now} given to the constructor
	 */
	public I_C_SubscriptionProgress entry(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final String status,
			final int eventDateDaysFromNow,
			final int seqNo)
	{
		final I_C_SubscriptionProgress sp = newInstance(I_C_SubscriptionProgress.class);
		sp.setC_Flatrate_Term_ID(term.getC_Flatrate_Term_ID());
		sp.setEventType(X_C_SubscriptionProgress.EVENTTYPE_Delivery);
		sp.setStatus(status);
		sp.setEventDate(TimeUtil.addDays(now, eventDateDaysFromNow));
		sp.setSeqNo(seqNo);
		saveRecord(sp);
		return sp;
	}
}
