package de.metas.handlingunits.movement.generate;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mmovement.MovementAndLineId;
import org.compiere.model.I_M_Movement;

import javax.annotation.Nullable;
import java.util.List;

@Value
public class HUMovementGeneratorResult
{
	public static final HUMovementGeneratorResult EMPTY = builder().build();

	ImmutableList<org.compiere.model.I_M_Movement> movements;
	ImmutableList<MovementAndLineId> movementLineIds;
	ImmutableList<I_M_HU> husMoved;

	@Builder
	private HUMovementGeneratorResult(
			@Nullable final List<I_M_Movement> movements,
			@Nullable final List<MovementAndLineId> movementLineIds,
			@Nullable final List<I_M_HU> husMoved)
	{
		this.movements = movements != null ? ImmutableList.copyOf(movements) : ImmutableList.of();
		this.movementLineIds = movementLineIds != null ? ImmutableList.copyOf(movementLineIds) : ImmutableList.of();
		this.husMoved = husMoved != null ? ImmutableList.copyOf(husMoved) : ImmutableList.of();
	}

	public boolean isEmpty() {return movements.isEmpty() && movementLineIds.isEmpty() && husMoved.isEmpty();}

	public I_M_Movement getSingleMovement() {return CollectionUtils.singleElement(movements);}

	public MovementAndLineId getSingleMovementLineId() {return CollectionUtils.singleElement(movementLineIds);}

	public HUMovementGeneratorResult combine(@NonNull final HUMovementGeneratorResult other)
	{
		if (other.isEmpty())
		{
			return this;
		}
		else if (this.isEmpty())
		{
			return other;
		}
		else
		{
			final ImmutableList<I_M_Movement> newMovements = ImmutableList.<I_M_Movement>builder().addAll(this.movements).addAll(other.movements).build();
			final ImmutableList<MovementAndLineId> newMovementAndLineIds = ImmutableList.<MovementAndLineId>builder().addAll(this.movementLineIds).addAll(other.movementLineIds).build();
			final ImmutableList<I_M_HU> newHUsMoved = ImmutableList.<I_M_HU>builder().addAll(this.husMoved).addAll(other.husMoved).build();

			return new HUMovementGeneratorResult(newMovements, newMovementAndLineIds, newHUsMoved);
		}
	}
}
