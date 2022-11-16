package de.metas.ordercandidate.modelvalidator;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.logging.TableRecordMDC;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.api.OLCandValidatorService;
import de.metas.ordercandidate.location.OLCandLocationsUpdaterService;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_Order_Line_Alloc;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Component;

import java.util.List;

import static de.metas.common.util.CoalesceUtil.firstNotEmptyTrimmed;

@Interceptor(I_C_OLCand.class)
@Component
public class C_OLCand
{
	private final OLCandValidatorService olCandValidatorService;
	private final OLCandLocationsUpdaterService olCandLocationsUpdaterService;
	private final IOLCandDAO olCandDAO = Services.get(IOLCandDAO.class);
	private final IBPartnerProductDAO bpartnerProductDAO = Services.get(IBPartnerProductDAO.class);
	private final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IBPartnerBL bpartnerBL;

	public C_OLCand(
			@NonNull final IBPartnerBL bpartnerBL,
			@NonNull final OLCandValidatorService olCandValidatorService,
			@NonNull final OLCandLocationsUpdaterService olCandLocationsUpdaterService)
	{
		this.bpartnerBL = bpartnerBL;
		this.olCandValidatorService = olCandValidatorService;
		this.olCandLocationsUpdaterService = olCandLocationsUpdaterService;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_OLCand.COLUMNNAME_IsError)
	public void onIsErrorUnset(final I_C_OLCand olCand)
	{
		try (final MDC.MDCCloseable ignore = TableRecordMDC.putTableRecordReference(olCand))
		{
			if (!olCand.isError())
			{
				olCand.setAD_Note_ID(0);
				olCand.setErrorMsg(null);
			}
		}
	}

	/**
	 * Method is fired before an order candidate is deleted.<br>
	 * It deletes all {@link I_C_Order_Line_Alloc}s referencing the given order candidate.
	 */
	// 03472
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDelete(final I_C_OLCand olCand)
	{
		try (final MDCCloseable ignore = TableRecordMDC.putTableRecordReference(olCand))
		{
			final List<I_C_Order_Line_Alloc> olasToDelete = olCandDAO.retrieveAllOlas(olCand);

			for (final I_C_Order_Line_Alloc ola : olasToDelete)
			{
				InterfaceWrapperHelper.delete(ola);
			}
		}
	}

	/**
	 * Calls {@link OLCandValidatorService#validate(I_C_OLCand)}.<br>
	 * <p>
	 * Before that it resets the pricing system if there is a new C_BPartner or C_BPartner_Override.<br>
	 * The {@link de.metas.ordercandidate.spi.IOLCandValidator} framework is then supposed to call {@link de.metas.ordercandidate.api.IOLCandBL} to come up with the then-correct pricing system.
	 * <p>
	 * Task http://dewiki908/mediawiki/index.php/09686_PricingSystem_sometimes_not_updated_in_C_OLCand_%28105127201494%29
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validateC_OLCand(final I_C_OLCand olCand)
	{
		try (final MDCCloseable ignore = TableRecordMDC.putTableRecordReference(olCand))
		{
			if (olCandValidatorService.isValidationProcessInProgress())
			{
				return; // we are already within the validation process. no need to call the logic from here.
			}
			if (InterfaceWrapperHelper.isValueChanged(olCand, I_C_OLCand.COLUMNNAME_C_BPartner_ID, I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID)
					&& InterfaceWrapperHelper.isUIAction(olCand))
			{
				// task 09686
				olCand.setM_PricingSystem_ID(0);
			}
			olCandValidatorService.validate(olCand);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_C_OLCand.COLUMNNAME_C_BPartner_ID,
					I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID,
					I_C_OLCand.COLUMNNAME_M_Product_ID,
					I_C_OLCand.COLUMNNAME_M_Product_Override_ID })
	public void setProductDescription(final I_C_OLCand olCand)
	{
		try (final MDCCloseable ignore = TableRecordMDC.putTableRecordReference(olCand))
		{
			if (!Check.isBlank(olCand.getProductDescription()))
			{
				return; // if olCand is new and product description is already set, do not copy anything
			}

			final I_C_BPartner partner = olCandEffectiveValuesBL.getC_BPartner_Effective(olCand);
			final I_M_Product product = olCandEffectiveValuesBL.getM_Product_Effective(olCand);
			if (partner == null || product == null)
			{
				return; // don't try to set them unless we have both the product and partner
			}

			final OrgId orgId = OrgId.ofRepoId(product.getAD_Org_ID());
			final I_C_BPartner_Product bpp = bpartnerProductDAO.retrieveBPartnerProductAssociation(partner, product, orgId);
			if (bpp == null)
			{
				return; // nothing that we can add here with any benefit
			}

			// If we have a BPP association, first try its ProductDescription, then its ProductName
			final String productName = product.getName().trim();
			final String productDescription = firstNotEmptyTrimmed(bpp.getProductDescription(), bpp.getProductName(), productName);

			final boolean descriptionHasAddedValue = !productName.contentEquals(productDescription.trim());
			if (descriptionHasAddedValue)
			{
				olCand.setProductDescription(productDescription);
			}
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeChange_updateLocations(final I_C_OLCand olCand, final ModelChangeType timing)
	{
		try (final MDC.MDCCloseable ignore = TableRecordMDC.putTableRecordReference(olCand))
		{
			// if only an override-bpartner was changed, but not the respective override-location,
			// then make sure to update the override-location as well
			
			final boolean onlyBPartnerOverrideChanged = timing.isChange() 
					&& InterfaceWrapperHelper.isValueChanged(olCand, I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID) 
					&& !InterfaceWrapperHelper.isValueChanged(olCand, I_C_OLCand.COLUMNNAME_C_BP_Location_Override_ID);
			if (onlyBPartnerOverrideChanged)
			{
				olCandLocationsUpdaterService.updateBPartnerLocationOverride(olCand);
			}

			final boolean onlyDropshipPartnerOverrideChanged = timing.isChange() 
					&& InterfaceWrapperHelper.isValueChanged(olCand, I_C_OLCand.COLUMNNAME_DropShip_BPartner_Override_ID) 
					&& !InterfaceWrapperHelper.isValueChanged(olCand, I_C_OLCand.COLUMNNAME_DropShip_Location_Override_ID);
			if (onlyDropshipPartnerOverrideChanged)
			{
				olCandLocationsUpdaterService.updateDropShipLocationOverride(olCand);
			}

			final boolean onlyHandoverBPartnerOverrideChanged = timing.isChange() 
					&& InterfaceWrapperHelper.isValueChanged(olCand, I_C_OLCand.COLUMNNAME_HandOver_Partner_Override_ID)
					&& !InterfaceWrapperHelper.isValueChanged(olCand, I_C_OLCand.COLUMNNAME_HandOver_Location_Override_ID);
			if (onlyHandoverBPartnerOverrideChanged)
			{
				// NOTE: It is not wanted for the handoverLocationOverride to be changed if it was manually set to another value
				final boolean handoverLocationOverrideChanged = InterfaceWrapperHelper.isValueChanged(olCand, I_C_OLCand.COLUMNNAME_HandOver_Location_Override_ID);
				if (!handoverLocationOverrideChanged)
				{
					olCandLocationsUpdaterService.updateHandoverLocationOverride(olCand);
				}
			}

			olCandLocationsUpdaterService.updateCapturedLocations(olCand);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_OLCand.COLUMNNAME_POReference)
	public void updatePOReferenceOnSalesOrder(@NonNull final I_C_OLCand olCand)
	{
		final IQueryBuilder<I_C_Order> updateOrdersQuery = queryBL
				.createQueryBuilder(I_C_Order_Line_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Order_Line_Alloc.COLUMN_C_OLCand_ID, olCand.getC_OLCand_ID())
				.andCollectChildren(I_C_OrderLine.COLUMN_C_OrderLine_ID, I_C_OrderLine.class)
				.andCollect(I_C_Order.COLUMN_C_Order_ID, I_C_Order.class);

		final ICompositeQueryUpdater<I_C_Order> poReferenceUpdater = queryBL
				.createCompositeQueryUpdater(I_C_Order.class)
				.addSetColumnValue(I_C_Order.COLUMNNAME_POReference, olCand.getPOReference());

		updateOrdersQuery.create().update(poReferenceUpdater);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_C_OLCand.COLUMNNAME_C_BPartner_SalesRep_ID,
					I_C_OLCand.COLUMNNAME_C_BPartner_ID })
	public void validateSalesRep(final I_C_OLCand cand)
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(CoalesceUtil.firstGreaterThanZero(cand.getBill_BPartner_ID(), cand.getC_BPartner_ID()));
		final BPartnerId salesRepId = BPartnerId.ofRepoIdOrNull(cand.getC_BPartner_SalesRep_ID());
		bpartnerBL.validateSalesRep(bPartnerId, salesRepId);
	}
}
