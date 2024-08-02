package de.metas.distribution.ddorder.lowlevel.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.document.engine.DocStatus;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DDOrderUtil;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.material.planning.ddorder.DistributionNetworkLine;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.material.replenish.ReplenishInfo;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO.ATTR_DDORDER_REQUESTED_EVENT_GROUP_ID;

@Builder
class DDOrderLoader
{
	@NonNull private final IProductPlanningDAO productPlanningDAO;
	@NonNull private final IWarehouseDAO warehouseDAO;
	@NonNull private final DistributionNetworkRepository distributionNetworkRepository;
	@NonNull private final DDOrderLowLevelService ddOrderLowLevelService;
	@NonNull private final ReplenishInfoRepository replenishInfoRepository;

	private final HashMap<ProductPlanningId, ProductPlanning> productPlanningCache = new HashMap<>();

	@NonNull
	public DDOrder load(@NonNull final I_DD_Order ddOrderRecord)
	{
		final List<I_DD_OrderLine> lineRecords = ddOrderLowLevelService.retrieveLines(ddOrderRecord);
		return load(ddOrderRecord, lineRecords);
	}

	@NonNull
	public DDOrder load(final @NonNull I_DD_Order ddOrderRecord, final List<I_DD_OrderLine> lineRecords)
	{
		final ProductPlanning productPlanning = getProductPlanning(ddOrderRecord);

		final DDOrder.DDOrderBuilder builder = fromRecord(ddOrderRecord);
		for (final I_DD_OrderLine lineRecord : lineRecords)
		{
			final DDOrderLine line = fromRecord(lineRecord, ddOrderRecord, productPlanning);

			builder.line(line);
		}

		return builder.build();
	}

	@NonNull
	public DDOrder loadWithSingleLine(final @NonNull I_DD_OrderLine lineRecord)
	{
		final I_DD_Order ddOrderRecord = ddOrderLowLevelService.getById(DDOrderId.ofRepoId(lineRecord.getDD_Order_ID()));
		return load(ddOrderRecord, ImmutableList.of(lineRecord));
	}

	@NonNull
	private static DDOrder.DDOrderBuilder fromRecord(@NonNull final I_DD_Order record)
	{
		return DDOrder.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(record.getAD_Client_ID(), record.getAD_Org_ID()))
				.supplyDate(extractSupplyDate(record))
				.ddOrderId(record.getDD_Order_ID())
				.docStatus(DocStatus.ofCode(record.getDocStatus()))
				.plantId(ResourceId.ofRepoIdOrNull(record.getPP_Plant_ID()))
				.productPlanningId(ProductPlanningId.ofRepoIdOrNull(record.getPP_Product_Planning_ID()))
				.sourceWarehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_From_ID()))
				.targetWarehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.shipperId(ShipperId.ofRepoIdOrNull(record.getM_Shipper_ID()))
				.simulated(record.isSimulated())
				.materialDispoGroupId(getMaterialDispoGroupId(record));
	}

	private DDOrderLine fromRecord(
			@NonNull final I_DD_OrderLine lineRecord,
			@NonNull final I_DD_Order headerRecord,
			@Nullable final ProductPlanning productPlanning)
	{
		final Instant demandDate = calculateDemandDate(lineRecord, headerRecord, productPlanning);

		final BPartnerId bpartnerId = CoalesceUtil.coalesceSuppliersNotNull(
				() -> BPartnerId.ofRepoIdOrNull(lineRecord.getC_BPartner_ID()),
				() -> BPartnerId.ofRepoId(headerRecord.getC_BPartner_ID()));

		final ReplenishInfo replenishInfo = replenishInfoRepository.getBy(
				WarehouseId.ofRepoId(headerRecord.getM_Warehouse_From_ID()), // both from-warehouse and product are mandatory DB-columns
				ProductId.ofRepoId(lineRecord.getM_Product_ID()));

		return DDOrderLine.builder()
				.productDescriptor(createProductDescriptor(lineRecord))
				.bpartnerId(bpartnerId)
				.ddOrderLineId(lineRecord.getDD_OrderLine_ID())
				.qty(lineRecord.getQtyDelivered())
				.qtyPending(lineRecord.getQtyOrdered().subtract(lineRecord.getQtyDelivered()))
				.distributionNetworkAndLineId(extractDistributionNetworkAndLineId(lineRecord).orElse(null))
				.salesOrderLineId(lineRecord.getC_OrderLineSO_ID())
				.demandDate(demandDate)
				.fromWarehouseMinMaxDescriptor(replenishInfo.toMinMaxDescriptor())
				.build();
	}

	private ProductDescriptor createProductDescriptor(final I_DD_OrderLine lineRecord)
	{
		final int productId = lineRecord.getM_Product_ID();
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(lineRecord.getM_AttributeSetInstance_ID());
		final AttributesKey storageAttributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(asiId).orElse(AttributesKey.NONE);
		return ProductDescriptor.forProductAndAttributes(productId, storageAttributesKey, asiId.getRepoId());
	}

	private static Optional<DistributionNetworkAndLineId> extractDistributionNetworkAndLineId(final I_DD_OrderLine ddOrderLine)
	{
		return DistributionNetworkAndLineId.optionalOfRepoIds(ddOrderLine.getDD_NetworkDistribution_ID(), ddOrderLine.getDD_NetworkDistributionLine_ID());
	}

	private static MaterialDispoGroupId getMaterialDispoGroupId(final @NonNull I_DD_Order ddOrderRecord)
	{
		return ATTR_DDORDER_REQUESTED_EVENT_GROUP_ID.getValue(ddOrderRecord);
	}

	@Nullable
	private ProductPlanning getProductPlanning(final @NonNull I_DD_Order ddOrderRecord)
	{
		final ProductPlanningId productPlanningId = ProductPlanningId.ofRepoIdOrNull(ddOrderRecord.getPP_Product_Planning_ID());

		return productPlanningId != null
				? productPlanningCache.computeIfAbsent(productPlanningId, productPlanningDAO::getById)
				: null;
	}

	private Instant calculateDemandDate(final @NonNull I_DD_OrderLine lineRecord, final @NonNull I_DD_Order headerRecord, final @Nullable ProductPlanning productPlanning)
	{
		final DistributionNetworkLine distributionNetworkLine = extractDistributionNetworkAndLineId(lineRecord)
				.map(distributionNetworkRepository::getLineById)
				.orElse(null);
		final int durationDays = DDOrderUtil.calculateDurationDays(productPlanning, distributionNetworkLine);

		final Instant supplyDate = extractSupplyDate(headerRecord);
		return supplyDate.minus(durationDays, ChronoUnit.DAYS);
	}

	private static Instant extractSupplyDate(final @NonNull I_DD_Order headerRecord)
	{
		return headerRecord.getDatePromised().toInstant();
	}
}
