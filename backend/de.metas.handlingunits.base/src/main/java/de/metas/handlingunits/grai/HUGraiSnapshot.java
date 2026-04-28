package de.metas.handlingunits.grai;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.grai.HUGraiDelta.AttributeChange;
import de.metas.i18n.AdMessageKey;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Value
@Builder
public class HUGraiSnapshot
{
	private static final AdMessageKey GRAI_COUNT_MISMATCH = AdMessageKey.of("de.metas.handlingunits.picking.GRAICountMismatch");

	@NonNull HuId huId;
	@NonNull ImmutableList<TU> tus;
	@NonNull ImmutableList<AggregateBlock> aggregateBlocks;

	// -------------------------------------------------
	// Derived accessors (GET path)
	// -------------------------------------------------

	@NonNull
	public QtyTU getTUCount()
	{
		QtyTU count = QtyTU.ofInt(tus.size());
		for (final AggregateBlock block : aggregateBlocks)
		{
			count = count.add(block.tuCount);
		}
		return count;
	}

	public boolean isAllGraisAssigned()
	{
		return getAllGrais().size() >= getTUCount().toInt();
	}

	public void assertAllGraisAssigned()
	{
		if (isAllGraisAssigned()) {return;}

		throw new AdempiereException(GRAI_COUNT_MISMATCH,
				this.huId,
				getAllGrais().size(),
				getTUCount().toInt());
	}

	public int findMaxDummyCounter(@NonNull final DummyGRAITemplate template)
	{
		int maxCounter = 0;
		for (final GRAI grai : getAllGrais())
		{
			maxCounter = Math.max(maxCounter, template.extractCounter(grai));
		}
		return maxCounter;
	}

	@NonNull
	public GRAISet getAllGrais()
	{
		final ArrayList<GRAI> result = new ArrayList<>();
		for (final TU tu : tus)
		{
			if (tu.grai != null)
			{
				result.add(tu.grai);
			}
		}
		for (final AggregateBlock block : aggregateBlocks)
		{
			block.grais.forEach(result::add);
		}
		return GRAISet.ofCollection(result);
	}

	@NonNull
	public HUGraiDelta generateMissingGRAIs(@NonNull final DummyGRAIProvider nextGraiProvider)
	{
		// Build the desired GRAISet: existing GRAIs + generated dummies for missing slots
		final ArrayList<GRAI> desiredGrais = new ArrayList<>();
		getAllGrais().forEach(desiredGrais::add);

		final int missingCount = getTUCount().toInt() - desiredGrais.size();
		for (int i = 0; i < missingCount; i++)
		{
			desiredGrais.add(nextGraiProvider.nextGRAI());
		}

		return computeDelta(GRAISet.ofCollection(desiredGrais));
	}

	@NonNull
	public HUGraiDelta computeDelta(@NonNull final GRAISet desiredGrais)
	{
		// Step 1: Identify which desired GRAIs are already correctly assigned
		final LinkedHashSet<GRAI> unassigned = new LinkedHashSet<>(desiredGrais.toSet());

		for (final TU tu : tus)
		{
			if (tu.grai != null)
			{
				unassigned.remove(tu.grai);
			}
		}
		for (final AggregateBlock block : aggregateBlocks)
		{
			block.grais.forEach(unassigned::remove);
		}

		// Step 2: Collect TU slots that need a new value (wrong GRAI or empty)
		final List<AttributeChange> changes = new ArrayList<>();
		final List<TU> availableSlots = new ArrayList<>();

		for (final TU tu : tus)
		{
			if (tu.grai != null && !desiredGrais.contains(tu.grai))
			{
				// Wrong GRAI — needs to be replaced or cleared
				availableSlots.add(tu);
			}
			else if (tu.grai == null)
			{
				availableSlots.add(tu);
			}
			// else: correct GRAI already in place — no change
		}

		// Step 3: Assign unassigned GRAIs to available slots; clear the rest
		final ArrayList<GRAI> remainingUnassigned = new ArrayList<>(unassigned);
		for (final TU slot : availableSlots)
		{
			if (!remainingUnassigned.isEmpty())
			{
				final GRAI grai = remainingUnassigned.remove(0);
				changes.add(AttributeChange.of(slot.huId, GRAISet.of(grai)));
			}
			else if (slot.grai != null)
			{
				// No more GRAIs to assign, but this slot had a wrong GRAI — clear it
				changes.add(AttributeChange.of(slot.huId, GRAISet.EMPTY));
			}
			// else: slot was already empty and stays empty — no change
		}

		// Step 4: Handle aggregate blocks
		if (!aggregateBlocks.isEmpty())
		{
			final AggregateBlock firstBlock = aggregateBlocks.get(0);

			// Compute what the first block should hold:
			// = (existing GRAIs that are in desiredGrais) + remainingUnassigned
			final ArrayList<GRAI> newBlockGrais = new ArrayList<>();
			for (final GRAI grai : firstBlock.grais)
			{
				if (desiredGrais.contains(grai))
				{
					newBlockGrais.add(grai);
				}
			}
			newBlockGrais.addAll(remainingUnassigned);
			remainingUnassigned.clear();
			final GRAISet newBlockGraiSet = GRAISet.ofCollection(newBlockGrais);

			if (!newBlockGraiSet.equals(firstBlock.grais))
			{
				changes.add(AttributeChange.of(firstBlock.vhuId, newBlockGraiSet));
			}

			// Clear any additional aggregate blocks
			for (int i = 1; i < aggregateBlocks.size(); i++)
			{
				final AggregateBlock block = aggregateBlocks.get(i);
				if (!block.grais.isEmpty())
				{
					changes.add(AttributeChange.of(block.vhuId, GRAISet.EMPTY));
				}
			}
		}

		final GRAISet unassignedGrais = GRAISet.ofCollection(remainingUnassigned);

		return HUGraiDelta.builder()
				.changes(ImmutableList.copyOf(changes))
				.unassignedGrais(unassignedGrais)
				.build();
	}

	//
	//
	//
	//
	//
	//
	//

	@Value(staticConstructor = "of")
	public static class TU
	{
		@NonNull HuId huId;
		@Nullable GRAI grai;
	}

	@Value(staticConstructor = "of")
	public static class AggregateBlock
	{
		@NonNull HuId vhuId;
		@NonNull QtyTU tuCount;
		@NonNull GRAISet grais;
	}
}
