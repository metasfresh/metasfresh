package de.metas.distribution.ddorder.movement.schedule.generate_from_hu;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;

import javax.annotation.Nullable;
import java.util.ArrayList;

@ToString
final class DDOrderLineToAllocate
{
	@NonNull private final IUOMConversionBL uomConversionBL;

	@NonNull private final DDOrderId ddOrderId;
	@NonNull private final DDOrderLineId ddOrderLineId;
	private final int ddOrderLineAlternativeId;

	@Getter
	@Nullable private final String description;

	private final ArrayList<PickFromHU> pickFromHUs = new ArrayList<>();

	@Getter
	@NonNull private final ProductId productId;
	@Getter
	@NonNull private final AttributeSetInstanceId fromAsiId;
	@Getter
	@NonNull private final AttributeSetInstanceId toAsiId;

	@Getter
	@NonNull private final LocatorId pickFromLocatorId;
	@Getter
	@NonNull private final LocatorId dropToLocatorId;

	@Getter
	private Quantity qtyToShipScheduled;
	@Getter
	private Quantity qtyToShipRemaining;

	@Builder
	private DDOrderLineToAllocate(
			final @NonNull IUOMConversionBL uomConversionBL,
			//
			final @NonNull DDOrderId ddOrderId,
			final @NonNull DDOrderLineId ddOrderLineId,
			final int ddOrderLineAlternativeId,
			final @Nullable String description,
			final @NonNull ProductId productId,
			final @NonNull AttributeSetInstanceId fromAsiId,
			final @NonNull AttributeSetInstanceId toAsiId,
			final @NonNull LocatorId pickFromLocatorId,
			final @NonNull LocatorId dropToLocatorId,
			final @NonNull Quantity qtyToShip)
	{
		this.uomConversionBL = uomConversionBL;
		this.ddOrderId = ddOrderId;
		this.ddOrderLineId = ddOrderLineId;
		this.ddOrderLineAlternativeId = ddOrderLineAlternativeId;
		this.description = description;
		this.productId = productId;
		this.fromAsiId = fromAsiId;
		this.toAsiId = toAsiId;
		this.pickFromLocatorId = pickFromLocatorId;
		this.dropToLocatorId = dropToLocatorId;

		this.qtyToShipRemaining = qtyToShip;
		this.qtyToShipScheduled = qtyToShipRemaining.toZero();
	}

	public DDOrderId getDDOrderId() {return ddOrderId;}

	public DDOrderLineId getDDOrderLineId() {return ddOrderLineId;}

	public int getDDOrderLineAlternativeId() {return ddOrderLineAlternativeId;}

	public ImmutableList<PickFromHU> getPickFromHUs() {return ImmutableList.copyOf(pickFromHUs);}

	public boolean isFullyShipped()
	{
		return qtyToShipRemaining.signum() <= 0;
	}

	public void addPickFromHU(@NonNull final PickFromHU pickFromHU)
	{
		this.pickFromHUs.add(pickFromHU);

		qtyToShipRemaining = qtyToShipRemaining.subtract(pickFromHU.getQty());
		qtyToShipScheduled = qtyToShipScheduled.add(pickFromHU.getQty());
	}

	//
	//
	//

	@Value
	@Builder
	public static class PickFromHU
	{
		@NonNull I_M_HU hu;
		@NonNull Quantity qty;

		public HuId getHuId() {return HuId.ofRepoId(hu.getM_HU_ID());}
	}
}
