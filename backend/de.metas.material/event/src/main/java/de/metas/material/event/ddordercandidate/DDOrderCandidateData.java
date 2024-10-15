package de.metas.material.event.ddordercandidate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ResourceId;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class DDOrderCandidateData
{
	@Nullable ProductPlanningId productPlanningId;
	@Nullable DistributionNetworkAndLineId distributionNetworkAndLineId;

	@NonNull ClientAndOrgId clientAndOrgId;
	@NonNull WarehouseId sourceWarehouseId;
	@NonNull WarehouseId targetWarehouseId;
	@Nullable ResourceId targetPlantId;
	@NonNull ShipperId shipperId;

	int customerId;
	int salesOrderId;
	int salesOrderLineId;
	@Nullable PPOrderRef forwardPPOrderRef;

	@NonNull ProductDescriptor productDescriptor;
	@NonNull HUPIItemProductId hupiItemProductId;
	@Nullable MinMaxDescriptor fromWarehouseMinMaxDescriptor;

	@NonNull Instant supplyDate;
	@NonNull Instant demandDate;

	@NonNull BigDecimal qty;
	int uomId;

	boolean simulated;

	/**
	 * Not persisted in the {@code DD_Order} data record, but
	 * when material-dispo posts {@link DDOrderCandidateRequestedEvent}, it contains a group-ID,
	 * and the respective {@link DDOrderCandidateCreatedEvent} contains the same group-ID.
	 */
	@Nullable MaterialDispoGroupId materialDispoGroupId;

	int exitingDDOrderCandidateId;

	@Builder(toBuilder = true)
	@Jacksonized
	private DDOrderCandidateData(
			@Nullable final ProductPlanningId productPlanningId,
			@Nullable final DistributionNetworkAndLineId distributionNetworkAndLineId,
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final WarehouseId sourceWarehouseId,
			@NonNull final WarehouseId targetWarehouseId,
			@Nullable final ResourceId targetPlantId,
			@NonNull final ShipperId shipperId,
			final int customerId,
			final int salesOrderId,
			final int salesOrderLineId,
			@Nullable final PPOrderRef forwardPPOrderRef,
			@NonNull final ProductDescriptor productDescriptor,
			@Nullable final HUPIItemProductId hupiItemProductId,
			@Nullable final MinMaxDescriptor fromWarehouseMinMaxDescriptor,
			@NonNull final Instant supplyDate,
			@NonNull final Instant demandDate,
			@NonNull final BigDecimal qty,
			final int uomId,
			final boolean simulated,
			@Nullable final MaterialDispoGroupId materialDispoGroupId,
			final int exitingDDOrderCandidateId)
	{
		this.productPlanningId = productPlanningId;
		this.distributionNetworkAndLineId = distributionNetworkAndLineId;
		this.clientAndOrgId = clientAndOrgId;
		this.sourceWarehouseId = sourceWarehouseId;
		this.targetWarehouseId = targetWarehouseId;
		this.targetPlantId = targetPlantId;
		this.shipperId = shipperId;
		this.customerId = customerId;
		this.salesOrderId = salesOrderId;
		this.salesOrderLineId = salesOrderLineId;
		this.forwardPPOrderRef = forwardPPOrderRef;
		this.productDescriptor = productDescriptor;
		this.hupiItemProductId = hupiItemProductId != null ? hupiItemProductId : HUPIItemProductId.VIRTUAL_HU;
		this.fromWarehouseMinMaxDescriptor = fromWarehouseMinMaxDescriptor;
		this.supplyDate = supplyDate;
		this.demandDate = demandDate;
		this.qty = qty;
		this.uomId = uomId;
		this.simulated = simulated;
		this.materialDispoGroupId = materialDispoGroupId;
		this.exitingDDOrderCandidateId = exitingDDOrderCandidateId;
	}

	@JsonIgnore
	public int getProductId() {return getProductDescriptor().getProductId();}

	@JsonIgnore
	public int getAttributeSetInstanceId() {return getProductDescriptor().getAttributeSetInstanceId();}

	@JsonIgnore
	public AttributesKey getStorageAttributesKey() {return getProductDescriptor().getStorageAttributesKey();}

	public void assertMaterialDispoGroupIdIsSet()
	{
		if (materialDispoGroupId == null)
		{
			throw new AdempiereException("Expected materialDispoGroupId to be set: " + this);
		}
	}

	public DDOrderCandidateData withPPOrderId(@Nullable final PPOrderId forwardPPOrderIdNew)
	{
		final PPOrderRef forwardPPOrderRefNew = PPOrderRef.withPPOrderId(forwardPPOrderRef, forwardPPOrderIdNew);
		if (Objects.equals(this.forwardPPOrderRef, forwardPPOrderRefNew))
		{
			return this;
		}

		return toBuilder().forwardPPOrderRef(forwardPPOrderRefNew).build();
	}

}
