package de.metas.fresh.material.interceptor;

import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.procurement.AbstractPurchaseOfferEvent;
import de.metas.material.event.procurement.PurchaseOfferCreatedEvent;
import de.metas.material.event.procurement.PurchaseOfferDeletedEvent;
import de.metas.material.event.procurement.PurchaseOfferUpdatedEvent;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.ModelChangeUtil;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;

/**
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
	 * Note: it's important to do this after tje purchaseCandidate was saved,
	 * but before it was deleted, because we need its <code>PMM_PurchaseCandidate_ID</code>.
	 *
	 * @param purchaseCandidateRecord
	 */
	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE,
			ModelValidator.TYPE_BEFORE_DELETE
	}, ifColumnsChanged = {
			I_PMM_PurchaseCandidate.COLUMNNAME_QtyPromised,
			I_PMM_PurchaseCandidate.COLUMNNAME_IsActive
	})
	public void enqueuePurchaseCandidates(
			@NonNull final I_PMM_PurchaseCandidate purchaseCandidateRecord,
			@NonNull final ModelChangeType type)
	{
		final ModelProductDescriptorExtractor productDescriptorFactory = Adempiere.getBean(ModelProductDescriptorExtractor.class);
		final ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(purchaseCandidateRecord);

		final AbstractPurchaseOfferEvent event;

		final boolean deleted = type.isDelete() || ModelChangeUtil.isJustDeactivated(purchaseCandidateRecord);
		final boolean created = type.isNew() || ModelChangeUtil.isJustActivated(purchaseCandidateRecord);
		final BigDecimal qtyPromised = purchaseCandidateRecord.getQtyPromised();
		if (deleted)
		{
			event = PurchaseOfferDeletedEvent.builder()
					.date(TimeUtil.asInstant(purchaseCandidateRecord.getDatePromised()))
					.eventDescriptor(EventDescriptor.ofClientAndOrg(purchaseCandidateRecord.getAD_Client_ID(), purchaseCandidateRecord.getAD_Org_ID()))
					.procurementCandidateId(purchaseCandidateRecord.getPMM_PurchaseCandidate_ID())
					.productDescriptor(productDescriptor)
					.qty(qtyPromised)
					.build();
		}
		else if (created)
		{
			event = PurchaseOfferCreatedEvent.builder()
					.date(TimeUtil.asInstant(purchaseCandidateRecord.getDatePromised()))
					.eventDescriptor(EventDescriptor.ofClientAndOrg(purchaseCandidateRecord.getAD_Client_ID(), purchaseCandidateRecord.getAD_Org_ID()))
					.procurementCandidateId(purchaseCandidateRecord.getPMM_PurchaseCandidate_ID())
					.productDescriptor(productDescriptor)
					.qty(qtyPromised)
					.build();
		}
		else
		{
			final I_PMM_PurchaseCandidate oldPurchaseCandidate = InterfaceWrapperHelper.createOld(purchaseCandidateRecord, I_PMM_PurchaseCandidate.class);
			final BigDecimal oldQtyPromised = oldPurchaseCandidate.getQtyPromised();

			event = PurchaseOfferUpdatedEvent.builder()
					.date(TimeUtil.asInstant(purchaseCandidateRecord.getDatePromised()))
					.eventDescriptor(EventDescriptor.ofClientAndOrg(purchaseCandidateRecord.getAD_Client_ID(), purchaseCandidateRecord.getAD_Org_ID()))
					.procurementCandidateId(purchaseCandidateRecord.getPMM_PurchaseCandidate_ID())
					.productDescriptor(productDescriptor)
					.qty(qtyPromised)
					.qtyDelta(qtyPromised.subtract(oldQtyPromised))
					.build();
		}

		final PostMaterialEventService materialEventService = Adempiere.getBean(PostMaterialEventService.class);
		materialEventService.enqueueEventAfterNextCommit(event);
	}
}
