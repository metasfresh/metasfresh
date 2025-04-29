package de.metas.material.event.ddordercandidate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.product.ResourceId;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.model.I_DD_Order_Candidate;
import org.eevolution.model.I_PP_Order;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Send by the material planner when it came up with a brilliant distribution plan that could be turned into an {@link I_PP_Order}<br>
 * <b>or</or> if a ddOrder was actually created.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class AbstractDDOrderCandidateEvent implements MaterialEvent
{
	@NonNull private final EventDescriptor eventDescriptor;
	@NonNull @Getter(AccessLevel.PROTECTED) private final DDOrderCandidateData ddOrderCandidate;
	@Nullable private final SupplyRequiredDescriptor supplyRequiredDescriptor;

	public AbstractDDOrderCandidateEvent(
			@NonNull final EventDescriptor eventDescriptor,
			@NonNull final DDOrderCandidateData ddOrderCandidate,
			@Nullable final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		this.eventDescriptor = eventDescriptor;
		this.ddOrderCandidate = ddOrderCandidate;
		this.supplyRequiredDescriptor = supplyRequiredDescriptor;
	}

	@JsonIgnore
	public int getProductId() {return getDdOrderCandidate().getProductId();}

	@JsonIgnore
	public ProductDescriptor getProductDescriptor() {return getDdOrderCandidate().getProductDescriptor();}

	@JsonIgnore
	public AttributesKey getStorageAttributesKey() {return getDdOrderCandidate().getStorageAttributesKey();}

	@JsonIgnore
	public BigDecimal getQty() {return getDdOrderCandidate().getQty();}

	@JsonIgnore
	public Instant getSupplyDate() {return getDdOrderCandidate().getSupplyDate();}

	@JsonIgnore
	public Instant getDemandDate() {return getDdOrderCandidate().getDemandDate();}

	@JsonIgnore
	public boolean isSimulated() {return getDdOrderCandidate().isSimulated();}

	@Nullable
	@JsonIgnore
	public MinMaxDescriptor getFromWarehouseMinMaxDescriptor() {return getDdOrderCandidate().getFromWarehouseMinMaxDescriptor();}

	@Nullable
	@JsonIgnore
	public ResourceId getTargetPlantId() {return getDdOrderCandidate().getTargetPlantId();}

	@NonNull
	@JsonIgnore
	public WarehouseId getTargetWarehouseId() {return getDdOrderCandidate().getTargetWarehouseId();}

	@NonNull
	@JsonIgnore
	public WarehouseId getSourceWarehouseId() {return getDdOrderCandidate().getSourceWarehouseId();}

	@NonNull
	@JsonIgnore
	public ShipperId getShipperId() {return getDdOrderCandidate().getShipperId();}

	@NonNull
	@JsonIgnore
	public SupplyRequiredDescriptor getSupplyRequiredDescriptorNotNull() {return Check.assumeNotNull(getSupplyRequiredDescriptor(), "supplyRequiredDescriptor shall be set for " + this);}

	@Nullable
	@JsonIgnore
	public ProductPlanningId getProductPlanningId() {return getDdOrderCandidate().getProductPlanningId();}

	@Nullable
	@JsonIgnore
	public DistributionNetworkAndLineId getDistributionNetworkAndLineId() {return getDdOrderCandidate().getDistributionNetworkAndLineId();}

	@Nullable
	@JsonIgnore
	public MaterialDispoGroupId getMaterialDispoGroupId() {return getDdOrderCandidate().getMaterialDispoGroupId();}

	@Nullable
	@JsonIgnore
	public PPOrderRef getForwardPPOrderRef() {return getDdOrderCandidate().getForwardPPOrderRef();}

	@JsonIgnore
	public int getExistingDDOrderCandidateId() {return getDdOrderCandidate().getExitingDDOrderCandidateId();}

	@Nullable
	public TableRecordReference getSourceTableReference()
	{
		return TableRecordReference.ofNullable(I_DD_Order_Candidate.Table_Name,ddOrderCandidate.getExitingDDOrderCandidateId());
	}

}
