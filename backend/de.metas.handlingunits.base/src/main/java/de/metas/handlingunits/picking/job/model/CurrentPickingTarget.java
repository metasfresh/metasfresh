package de.metas.handlingunits.picking.job.model;

import de.metas.handlingunits.HuId;
import de.metas.i18n.AdMessageKey;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

@Builder(toBuilder = true)
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CurrentPickingTarget
{
	private final static AdMessageKey MISSING_PICKING_SLOT_ID_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.model.MISSING_PICKING_SLOT_ID_ERROR_MSG");

	public static final CurrentPickingTarget EMPTY = builder().build();

	@Nullable private final PickingSlotIdAndCaption pickingSlot;
	@Nullable private final LUPickingTarget luPickingTarget;
	@Nullable private final TUPickingTarget tuPickingTarget;

	public static boolean equals(@Nullable CurrentPickingTarget o1, @Nullable CurrentPickingTarget o2) {return Objects.equals(o1, o2);}

	@NonNull
	public PickingSlotIdAndCaption getPickingSlotNotNull()
	{
		if (pickingSlot == null)
		{
			throw new AdempiereException(MISSING_PICKING_SLOT_ID_ERROR_MSG);
		}
		return pickingSlot;
	}

	public void assertPickingSlotScanned()
	{
		if (pickingSlot == null)
		{
			throw new AdempiereException(MISSING_PICKING_SLOT_ID_ERROR_MSG);
		}
	}

	public boolean isPickingSlotSet() {return pickingSlot != null;}

	public Optional<PickingSlotIdAndCaption> getPickingSlot() {return Optional.ofNullable(pickingSlot);}
	
	public Optional<PickingSlotId> getPickingSlotId() {return Optional.ofNullable(pickingSlot).map(PickingSlotIdAndCaption::getPickingSlotId);}

	public Optional<String> getPickingSlotCaption() {return Optional.ofNullable(pickingSlot).map(PickingSlotIdAndCaption::getCaption);}

	public CurrentPickingTarget withPickingSlot(@Nullable final PickingSlotIdAndCaption pickingSlot)
	{
		return PickingSlotIdAndCaption.equals(this.pickingSlot, pickingSlot)
				? this
				: toBuilder().pickingSlot(pickingSlot).build();
	}

	@NonNull
	public Optional<LUPickingTarget> getLuPickingTarget() {return Optional.ofNullable(luPickingTarget);}

	@NonNull
	public CurrentPickingTarget withLuPickingTarget(@NonNull final UnaryOperator<LUPickingTarget> luPickingTargetMapper)
	{
		return withLuPickingTarget(luPickingTargetMapper.apply(luPickingTarget));
	}

	@NonNull
	public CurrentPickingTarget withLuPickingTarget(final @Nullable LUPickingTarget luPickingTarget)
	{
		if (LUPickingTarget.equals(this.luPickingTarget, luPickingTarget))
		{
			return this;
		}

		return toBuilder()
				.luPickingTarget(luPickingTarget)
				.tuPickingTarget(luPickingTarget == null ? null : this.tuPickingTarget)
				.build();
	}

	public CurrentPickingTarget withClosedLuPickingTarget(@Nullable final Consumer<HuId> closedLuIdCollector)
	{
		if (luPickingTarget == null)
		{
			return this;
		}

		if (closedLuIdCollector != null && luPickingTarget.getLuId() != null)
		{
			closedLuIdCollector.accept(luPickingTarget.getLuId());
		}

		return withLuPickingTarget((LUPickingTarget)null);
	}

	@NonNull
	public Optional<TUPickingTarget> getTuPickingTarget() {return Optional.ofNullable(tuPickingTarget);}

	@NonNull
	public CurrentPickingTarget withTuPickingTarget(@Nullable final TUPickingTarget tuPickingTarget)
	{
		return TUPickingTarget.equals(this.tuPickingTarget, tuPickingTarget)
				? this
				: toBuilder().tuPickingTarget(tuPickingTarget).build();
	}
}
