package de.metas.material.event.ddordercandidate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
@Jacksonized
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class DDOrderCandidateData
{
	@NonNull ProductPlanningId productPlanningId;
	@Nullable DistributionNetworkAndLineId distributionNetworkAndLineId;

	@NonNull OrgId orgId;
	@NonNull WarehouseId sourceWarehouseId;
	@NonNull WarehouseId targetWarehouseId;
	@NonNull ResourceId targetPlantId;
	@NonNull ShipperId shipperId;

	int salesOrderLineId;
	int customerId;
	
	@NonNull ProductDescriptor productDescriptor;
	@Nullable MinMaxDescriptor fromWarehouseMinMaxDescriptor;

	@NonNull Instant datePromised;

	@NonNull BigDecimal qty;
	int uomId;

	int durationDays;

	boolean simulated;

	@Nullable MaterialDispoGroupId materialDispoGroupId;

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

	@NonNull
	public DistributionNetworkAndLineId getDistributionNetworkAndLineIdNotNull()
	{
		return Check.assumeNotNull(getDistributionNetworkAndLineId(), "distributionNetworkAndLineId shall be set for {}", this);
	}
}
