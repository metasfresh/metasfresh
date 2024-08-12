package de.metas.distribution.ddordercandidate;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.ddordercandidate.DDOrderCandidateData;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.shipping.ShipperId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Objects;

@Getter
@EqualsAndHashCode
@ToString
public class DDOrderCandidate
{
	@Nullable @Setter private DDOrderCandidateId id;
	@NonNull private final ClientAndOrgId clientAndOrgId;

	@NonNull private final Instant dateOrdered;
	@NonNull private final Instant supplyDate;
	@NonNull private final Instant demandDate;

	@NonNull private final ProductId productId;
	@NonNull private final HUPIItemProductId hupiItemProductId;
	@NonNull private final Quantity qty;
	private final int qtyTUs;
	@NonNull private Quantity qtyProcessed;

	@NonNull private final AttributeSetInstanceId attributeSetInstanceId;

	@NonNull private final WarehouseId sourceWarehouseId;
	@NonNull private final WarehouseId targetWarehouseId;
	@Nullable private final ResourceId targetPlantId;
	@NonNull private final ShipperId shipperId;

	private final boolean isSimulated;
	private final boolean isAllowPush;
	private final boolean isKeepTargetPlant;
	private boolean processed;

	@Nullable private final BPartnerId customerId;
	@Nullable private final OrderLineId salesOrderLineId;
	@Nullable private final PPOrderRef forwardPPOrderRef;

	@Nullable private final DistributionNetworkAndLineId distributionNetworkAndLineId;
	@Nullable private final ProductPlanningId productPlanningId;

	// Not persisted fields:
	@Nullable private final String traceId;
	@Nullable private final MaterialDispoGroupId materialDispoGroupId;

	@Builder(toBuilder = true)
	private DDOrderCandidate(
			@Nullable final DDOrderCandidateId id,
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final Instant dateOrdered,
			@NonNull final Instant supplyDate,
			@NonNull final Instant demandDate,
			@NonNull final ProductId productId,
			@Nullable final HUPIItemProductId hupiItemProductId,
			@NonNull final Quantity qty,
			int qtyTUs,
			@Nullable final Quantity qtyProcessed,
			@Nullable final AttributeSetInstanceId attributeSetInstanceId,
			@NonNull final WarehouseId sourceWarehouseId,
			@NonNull final WarehouseId targetWarehouseId,
			@Nullable final ResourceId targetPlantId,
			@NonNull final ShipperId shipperId,
			final boolean isSimulated,
			final boolean isAllowPush,
			final boolean isKeepTargetPlant,
			final boolean processed,
			@Nullable final BPartnerId customerId,
			@Nullable final OrderLineId salesOrderLineId,
			@Nullable final PPOrderRef forwardPPOrderRef,
			@Nullable final DistributionNetworkAndLineId distributionNetworkAndLineId,
			@Nullable final ProductPlanningId productPlanningId,
			@Nullable final String traceId,
			@Nullable final MaterialDispoGroupId materialDispoGroupId)
	{
		Quantity.assertSameUOM(qty, qtyProcessed);

		this.id = id;
		this.clientAndOrgId = clientAndOrgId;
		this.dateOrdered = dateOrdered;
		this.supplyDate = supplyDate;
		this.demandDate = demandDate;
		this.productId = productId;
		this.hupiItemProductId = hupiItemProductId != null ? hupiItemProductId : HUPIItemProductId.VIRTUAL_HU;
		this.qty = qty;
		this.qtyTUs = qtyTUs;
		this.qtyProcessed = qtyProcessed != null ? qtyProcessed : this.qty.toZero();
		this.attributeSetInstanceId = attributeSetInstanceId != null ? attributeSetInstanceId : AttributeSetInstanceId.NONE;
		this.sourceWarehouseId = sourceWarehouseId;
		this.targetWarehouseId = targetWarehouseId;
		this.targetPlantId = targetPlantId;
		this.shipperId = shipperId;
		this.isSimulated = isSimulated;
		this.isAllowPush = isAllowPush;
		this.isKeepTargetPlant = isKeepTargetPlant;
		this.processed = processed;
		this.customerId = customerId;
		this.salesOrderLineId = salesOrderLineId;
		this.forwardPPOrderRef = forwardPPOrderRef;
		this.distributionNetworkAndLineId = distributionNetworkAndLineId;
		this.productPlanningId = productPlanningId;
		this.traceId = traceId;
		this.materialDispoGroupId = materialDispoGroupId;
	}

	public static DDOrderCandidateBuilder from(final DDOrderCandidateData data)
	{
		return DDOrderCandidate.builder()
				.clientAndOrgId(data.getClientAndOrgId())
				//.dateOrdered(...)
				.supplyDate(data.getSupplyDate())
				.demandDate(data.getDemandDate())
				//
				.productId(ProductId.ofRepoId(data.getProductId()))
				.hupiItemProductId(data.getHupiItemProductId())
				.qty(Quantitys.of(data.getQty(), UomId.ofRepoId(data.getUomId())))
				.qtyTUs(1)// TODO
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(data.getAttributeSetInstanceId()))
				//
				.sourceWarehouseId(data.getSourceWarehouseId())
				.targetWarehouseId(data.getTargetWarehouseId())
				.targetPlantId(data.getTargetPlantId())
				.shipperId(data.getShipperId())
				//
				.isSimulated(data.isSimulated())
				//
				.customerId(BPartnerId.ofRepoIdOrNull(data.getCustomerId()))
				.salesOrderLineId(OrderLineId.ofRepoIdOrNull(data.getSalesOrderLineId()))
				.forwardPPOrderRef(data.getForwardPPOrderRef())
				//
				.distributionNetworkAndLineId(data.getDistributionNetworkAndLineId())
				.productPlanningId(data.getProductPlanningId())
				//
				//.traceId(...)
				.materialDispoGroupId(data.getMaterialDispoGroupId())
				;
	}

	public DDOrderCandidateData.DDOrderCandidateDataBuilder toDDOrderCandidateData()
	{
		final AttributesKey storageAttributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(attributeSetInstanceId).orElse(AttributesKey.NONE);
		final ProductDescriptor productDescriptor = ProductDescriptor.forProductAndAttributes(productId.getRepoId(), storageAttributesKey, attributeSetInstanceId.getRepoId());
		final Quantity qtyToProcess = getQtyToProcess();

		return DDOrderCandidateData.builder()
				.clientAndOrgId(clientAndOrgId)
				.productPlanningId(productPlanningId)
				.distributionNetworkAndLineId(distributionNetworkAndLineId)
				.sourceWarehouseId(sourceWarehouseId)
				.targetWarehouseId(targetWarehouseId)
				.targetPlantId(targetPlantId)
				.shipperId(shipperId)
				.customerId(BPartnerId.toRepoId(customerId))
				.salesOrderLineId(OrderLineId.toRepoId(salesOrderLineId))
				.forwardPPOrderRef(forwardPPOrderRef)
				.productDescriptor(productDescriptor)
				.hupiItemProductId(hupiItemProductId)
				.fromWarehouseMinMaxDescriptor(null) // N/A
				.supplyDate(supplyDate)
				.demandDate(demandDate)
				.qty(qtyToProcess.toBigDecimal())
				.uomId(qtyToProcess.getUomId().getRepoId())
				.simulated(isSimulated)
				.materialDispoGroupId(materialDispoGroupId)
				.exitingDDOrderCandidateId(DDOrderCandidateId.toRepoId(getId()));
	}

	public DDOrderCandidateId getIdNotNull() {return Check.assumeNotNull(getId(), "candidate shall be saved: {}", this);}

	public OrgId getOrgId() {return getClientAndOrgId().getOrgId();}

	public DDOrderCandidate withForwardPPOrderId(@Nullable final PPOrderId newPPOrderId)
	{
		final PPOrderRef forwardPPOrderRefNew = PPOrderRef.withPPOrderId(forwardPPOrderRef, newPPOrderId);
		if (Objects.equals(this.forwardPPOrderRef, forwardPPOrderRefNew))
		{
			return this;
		}

		return toBuilder().forwardPPOrderRef(forwardPPOrderRefNew).build();
	}

	public Quantity getQtyToProcess() {return getQty().subtract(getQtyProcessed());}

	public void setQtyProcessed(final @NonNull Quantity qtyProcessed)
	{
		Quantity.assertSameUOM(this.qty, qtyProcessed);
		this.qtyProcessed = qtyProcessed;

		updateProcessed();
	}

	private void updateProcessed()
	{
		this.processed = getQtyToProcess().signum() == 0;
	}
}
