package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileRepository;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.ShipmentScheduleId;
import de.metas.organization.OrgId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.Packageable;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseTypeId;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

@Builder
public class PickingJobCandidateRetrieveCommand
{
	//
	// Services
	@NonNull private final IPackagingDAO packagingDAO;
	@NonNull private final MobileUIPickingUserProfileRepository configRepository;

	//
	// Params
	@NonNull private final PickingJobQuery query;

	//
	// State
	@NonNull private final LinkedHashSet<PickingJobCandidate> orderBasedCandidates = new LinkedHashSet<>();
	@NonNull private final LinkedHashMap<ProductBasedAggregationKey, ProductBasedAggregation> productBasedAggregates = new LinkedHashMap<>();

	public List<PickingJobCandidate> execute()
	{
		packagingDAO.stream(query.toPackageableQuery()).forEach(this::add);
		return aggregate();
	}

	private ImmutableList<PickingJobCandidate> aggregate()
	{
		final ImmutableList.Builder<PickingJobCandidate> result = ImmutableList.builder();
		result.addAll(orderBasedCandidates);
		productBasedAggregates.values().forEach(aggregation -> result.add(aggregation.toPickingJobCandidate()));

		return result.build();
	}

	private void add(@NonNull final Packageable item)
	{
		final PickingJobAggregationType aggregationType = configRepository.getAggregationType(item.getCustomerId());
		switch (aggregationType)
		{
			case SALES_ORDER:
			{
				orderBasedCandidates.add(extractOrderBasedCandidate(item));
				break;
			}
			case PRODUCT:
			{
				productBasedAggregates.computeIfAbsent(ProductBasedAggregationKey.of(item), ProductBasedAggregation::new).add(item);
				break;
			}
			default:
			{
				throw new AdempiereException("Unknown aggregation type: " + aggregationType);
			}
		}
	}

	private static PickingJobCandidate extractOrderBasedCandidate(@NonNull final Packageable item)
	{
		return PickingJobCandidate.builder()
				.aggregationType(PickingJobAggregationType.SALES_ORDER)
				.preparationDate(item.getPreparationDate())
				.salesOrderId(Objects.requireNonNull(item.getSalesOrderId()))
				.salesOrderDocumentNo(Objects.requireNonNull(item.getSalesOrderDocumentNo()))
				.customerName(item.getCustomerName())
				.deliveryBPLocationId(item.getCustomerLocationId())
				.warehouseTypeId(item.getWarehouseTypeId())
				.partiallyPickedBefore(isPartiallyPickedOrDelivered(item))
				.build();
	}

	private static boolean isPartiallyPickedOrDelivered(final Packageable item)
	{
		return item.getQtyPickedPlanned().signum() != 0
				|| item.getQtyPickedNotDelivered().signum() != 0
				|| item.getQtyPickedAndDelivered().signum() != 0;
	}

	//
	//
	//
	//
	//

	@Value
	@EqualsAndHashCode(exclude = "uom")
	@Builder
	private static class ProductBasedAggregationKey
	{
		@NonNull OrgId orgId;
		@NonNull ProductId productId;
		@NonNull UomId uomId;
		@NonNull I_C_UOM uom;
		@Nullable WarehouseTypeId warehouseTypeId;

		public static ProductBasedAggregationKey of(@NonNull final Packageable item)
		{
			final I_C_UOM uom = item.getUOM();
			return builder()
					.orgId(item.getOrgId())
					.productId(item.getProductId())
					.uomId(UomId.ofRepoId(uom.getC_UOM_ID()))
					.uom(uom)
					.warehouseTypeId(item.getWarehouseTypeId())
					.build();
		}
	}

	private static class ProductBasedAggregation
	{
		@NonNull private final ProductBasedAggregationKey key;
		@Nullable private ITranslatableString productName;
		@NonNull private Quantity qtyToDeliver;
		private boolean partiallyPickedBefore = false;
		@NonNull private final HashSet<ShipmentScheduleId> shipmentScheduleIds = new HashSet<>();

		public ProductBasedAggregation(@NonNull final ProductBasedAggregationKey key)
		{
			this.key = key;
			this.qtyToDeliver = Quantity.zero(key.getUom());
		}

		public void add(@NonNull final Packageable item)
		{
			if (productName == null)
			{
				productName = TranslatableStrings.anyLanguage(item.getProductName());
			}

			this.qtyToDeliver = this.qtyToDeliver.add(item.getQtyToDeliver());

			if (isPartiallyPickedOrDelivered(item))
			{
				partiallyPickedBefore = true;
			}

			shipmentScheduleIds.add(item.getShipmentScheduleId());
		}

		public PickingJobCandidate toPickingJobCandidate()
		{
			return PickingJobCandidate.builder()
					.aggregationType(PickingJobAggregationType.PRODUCT)
					.warehouseTypeId(key.getWarehouseTypeId())
					.partiallyPickedBefore(partiallyPickedBefore)
					.productId(key.getProductId())
					.productName(productName)
					.qtyToDeliver(qtyToDeliver)
					.shipmentScheduleIds(ImmutableSet.copyOf(shipmentScheduleIds))
					.build();
		}
	}
}
