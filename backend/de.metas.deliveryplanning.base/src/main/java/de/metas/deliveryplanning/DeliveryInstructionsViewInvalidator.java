package de.metas.deliveryplanning;

import de.metas.cache.model.CacheSourceModelFactory;
import de.metas.cache.model.IModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.model.ShippingPackageId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Delivery_Planning_Delivery_Instructions_V;
import org.springframework.stereotype.Component;

/**
 * Helpers methods to invalidate M_Delivery_Planning_Delivery_Instructions_V,
 * which is used as an included tab in Delivery Planning window
 */
@Component
public class DeliveryInstructionsViewInvalidator
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IModelCacheInvalidationService modelCacheInvalidationService = Services.get(IModelCacheInvalidationService.class);

	public void invalidateByShipperTransportation(@NonNull final I_M_ShipperTransportation shipperTransportation, @NonNull final ModelChangeType changeType)
	{
		if (changeType.isNew()
				|| (changeType.isChange() && InterfaceWrapperHelper.isValueChanged(shipperTransportation, I_M_ShipperTransportation.COLUMNNAME_DocumentNo)))
		{
			invalidateByShipperTransportationId(ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID()));
		}
	}

	private void invalidateByShipperTransportationId(final @NonNull ShipperTransportationId shipperTransportationId)
	{
		queryBL.createQueryBuilder(I_M_Delivery_Planning_Delivery_Instructions_V.class)
				.addEqualsFilter(I_M_Delivery_Planning_Delivery_Instructions_V.COLUMNNAME_M_ShipperTransportation_ID, shipperTransportationId)
				.forEach(this::invalidate);
	}

	public void invalidateByDeliveryPlanning(@NonNull final I_M_Delivery_Planning deliveryPlanning, @NonNull final ModelChangeType changeType)
	{
		if (changeType.isChange() && InterfaceWrapperHelper.isValueChanged(deliveryPlanning, I_M_Delivery_Planning.COLUMNNAME_ReleaseNo))
		{
			invalidateByDeliveryPlanningId(DeliveryPlanningId.ofRepoId(deliveryPlanning.getM_Delivery_Planning_ID()));
		}
	}

	private void invalidateByDeliveryPlanningId(final @NonNull DeliveryPlanningId deliveryPlanningId)
	{
		queryBL.createQueryBuilder(I_M_Delivery_Planning_Delivery_Instructions_V.class)
				.addEqualsFilter(I_M_Delivery_Planning_Delivery_Instructions_V.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningId)
				.forEach(this::invalidate);
	}

	public void invalidateByShippingPackage(@NonNull final I_M_ShippingPackage shippingPackage, @NonNull final ModelChangeType changeType)
	{
		if (changeType.isNew()
				|| changeType.isDelete())
		{
			invalidateByShippingPackageId(ShippingPackageId.ofRepoId(shippingPackage.getM_ShippingPackage_ID()));
		}
	}

	private void invalidateByShippingPackageId(final ShippingPackageId shippingPackageId)
	{
		queryBL.createQueryBuilder(I_M_Delivery_Planning_Delivery_Instructions_V.class)
				.addEqualsFilter(I_M_Delivery_Planning_Delivery_Instructions_V.COLUMNNAME_M_ShippingPackage_ID, shippingPackageId)
				.forEach(this::invalidate);
	}

	private void invalidate(@NonNull I_M_Delivery_Planning_Delivery_Instructions_V record)
	{
		modelCacheInvalidationService.invalidateForModel(CacheSourceModelFactory.ofObject(record), ModelCacheInvalidationTiming.CHANGE);
	}

}
