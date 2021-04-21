package de.metas.ordercandidate.modelvalidator;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.api.IBPRelationDAO;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.interfaces.I_C_BP_Relation;
import de.metas.logging.TableRecordMDC;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.api.OLCandValidatorService;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_Order_Line_Alloc;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Properties;

import static de.metas.common.util.CoalesceUtil.firstNotEmptyTrimmed;

@Interceptor(I_C_OLCand.class)
@Callout(I_C_OLCand.class)
@Component
public class C_OLCand
{
	private final OLCandValidatorService olCandValidatorService;
	private final IOLCandDAO olCandDAO = Services.get(IOLCandDAO.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IBPRelationDAO bpRelationDAO = Services.get(IBPRelationDAO.class);
	private final IBPartnerProductDAO bpartnerProductDAO = Services.get(IBPartnerProductDAO.class);
	private final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IBPartnerBL bPartnerBL;

	public C_OLCand(
			@NonNull final IBPartnerBL bPartnerBL,
			@NonNull final OLCandValidatorService olCandValidatorService)
	{
		this.bPartnerBL = bPartnerBL;
		this.olCandValidatorService = olCandValidatorService;
	}

	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
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
			if (!Check.isEmpty(olCand.getProductDescription(), true))
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

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID)
	@CalloutMethod(columnNames = { I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID })
	public void onBPartnerOverride(final I_C_OLCand olCand)
	{
		try (final MDCCloseable ignore = TableRecordMDC.putTableRecordReference(olCand))
		{
			setBPLocationOverride(olCand, computeBPLocationOverride(olCand));
		}
	}

	@Nullable
	private I_C_BPartner_Location computeBPLocationOverride(final I_C_OLCand olCand)
	{
		final BPartnerId bpartnerOverrideId = BPartnerId.ofRepoIdOrNull(olCand.getC_BPartner_Override_ID());
		if (bpartnerOverrideId == null)
		{
			// in case the bpartner Override was deleted, also delete the bpartner Location Override
			return null;
		}
		else
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);
			final String trxName = InterfaceWrapperHelper.getTrxName(olCand);
			return bPartnerDAO.retrieveShipToLocation(ctx, bpartnerOverrideId.getRepoId(), trxName);
		}
	}

	private static void setBPLocationOverride(final I_C_OLCand olCand, final I_C_BPartner_Location bpLocationOverride)
	{
		olCand.setC_BP_Location_Override_ID(bpLocationOverride != null ? bpLocationOverride.getC_BPartner_Location_ID() : -1);
		olCand.setC_BP_Location_Override_Value_ID(bpLocationOverride != null ? bpLocationOverride.getC_Location_ID() : -1);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_OLCand.COLUMNNAME_DropShip_BPartner_Override_ID)
	@CalloutMethod(columnNames = { I_C_OLCand.COLUMNNAME_DropShip_BPartner_Override_ID })
	public void onDropShipPartnerOverride(final I_C_OLCand olCand)
	{
		try (final MDCCloseable ignore = TableRecordMDC.putTableRecordReference(olCand))
		{
			setDropShipLocationOverride(olCand, computeDropShipLocationOverride(olCand));
		}
	}

	private I_C_BPartner_Location computeDropShipLocationOverride(final I_C_OLCand olCand)
	{
		final BPartnerId dropShipPartnerOverrideId = BPartnerId.ofRepoIdOrNull(olCand.getDropShip_BPartner_Override_ID());
		if (dropShipPartnerOverrideId == null)
		{
			// in case the drop-ship bpartner Override was deleted, also delete the drop-ship Location Override
			return null;
		}
		else
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);
			final String trxName = InterfaceWrapperHelper.getTrxName(olCand);
			return bPartnerDAO.retrieveShipToLocation(ctx, dropShipPartnerOverrideId.getRepoId(), trxName);
		}
	}

	private static void setDropShipLocationOverride(@NonNull final I_C_OLCand olCand, @Nullable final I_C_BPartner_Location dropShipLocation)
	{
		olCand.setDropShip_Location_Override_ID(dropShipLocation != null ? dropShipLocation.getC_BPartner_Location_ID() : -1);
		olCand.setDropShip_Location_Override_Value_ID(dropShipLocation != null ? dropShipLocation.getC_Location_ID() : -1);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_OLCand.COLUMNNAME_HandOver_Partner_Override_ID)
	public void onHandOverPartnerOverrideInterceptor(final I_C_OLCand olCand)
	{
		try (final MDCCloseable ignore = TableRecordMDC.putTableRecordReference(olCand))
		{
			// NOTE: It is not wanted for the handoverLocationOverride to be changed if it was manually set to another value
			final boolean handoverLocationOverrideChanged = InterfaceWrapperHelper.isValueChanged(olCand, I_C_OLCand.COLUMNNAME_HandOver_Location_Override_ID);
			if (!handoverLocationOverrideChanged)
			{
				updateHandoverLocationOverride(olCand);
			}
		}
	}

	@CalloutMethod(columnNames = { I_C_OLCand.COLUMNNAME_HandOver_Partner_Override_ID })
	public void onHandOverPartnerOverrideCallout(@NonNull final I_C_OLCand olCand)
	{
		try (final MDCCloseable ignore = TableRecordMDC.putTableRecordReference(olCand))
		{
			updateHandoverLocationOverride(olCand);
		}
	}

	private void updateHandoverLocationOverride(@NonNull final I_C_OLCand olCand)
	{
		setHandOverLocationOverride(olCand, computeHandoverLocationOverride(olCand));
	}

	private I_C_BPartner_Location computeHandoverLocationOverride(@NonNull final I_C_OLCand olCand)
	{
		final BPartnerId handOverPartnerOverrideId = BPartnerId.ofRepoIdOrNull(olCand.getHandOver_Partner_Override_ID());
		if (handOverPartnerOverrideId == null)
		{
			// in case the handover bpartner Override was deleted, also delete the handover Location Override
			return null;
		}
		else
		{
			final I_C_BPartner partner = olCandEffectiveValuesBL.getC_BPartner_Effective(olCand);
			final I_C_BPartner handoverPartnerOverride = bPartnerDAO.getById(handOverPartnerOverrideId);
			final I_C_BP_Relation handoverRelation = bpRelationDAO.retrieveHandoverBPRelation(partner, handoverPartnerOverride);
			if (handoverRelation == null)
			{
				// this shall never happen, since both Handover_BPartner and Handover_BPartner_Override must come from such a bpp relation.
				// but I will leave this condition here as extra safety
				return null;
			}
			else
			{
				final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(handoverRelation.getC_BPartnerRelation_ID(), handoverRelation.getC_BPartnerRelation_Location_ID());
				return bPartnerDAO.getBPartnerLocationByIdEvenInactive(bPartnerLocationId);
			}
		}
	}

	private static void setHandOverLocationOverride(
			@NonNull final I_C_OLCand olCand,
			@Nullable final I_C_BPartner_Location handOverLocation)
	{
		olCand.setHandOver_Location_Override_ID(handOverLocation != null ? handOverLocation.getC_BPartner_Location_ID() : -1);
		olCand.setHandOver_Location_Override_Value_ID(handOverLocation != null ? handOverLocation.getC_Location_ID() : -1);
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

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_OLCand.COLUMNNAME_C_BPartner_SalesRep_ID, I_C_OLCand.COLUMNNAME_C_BPartner_ID })
	public void validateSalesRep(final I_C_OLCand cand)
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(CoalesceUtil.firstGreaterThanZero(cand.getBill_BPartner_ID(), cand.getC_BPartner_ID()));
		final BPartnerId salesRepId = BPartnerId.ofRepoIdOrNull(cand.getC_BPartner_SalesRep_ID());
		bPartnerBL.validateSalesRep(bPartnerId, salesRepId);
	}
}
