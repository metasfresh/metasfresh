package de.metas.handlingunits.allocation.transfer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Stream;

@Value
@Builder
public class LUTUResult
{
	public static LUTUResult EMPTY = builder().build();

	@NonNull @Singular("lu") ImmutableList<LU> lus;
	@NonNull @Builder.Default TUsList topLevelTUs = TUsList.EMPTY;

	public static LUTUResult ofLU(@NonNull final I_M_HU lu, @NonNull final TU... tus)
	{
		return builder().lu(LU.of(lu, tus)).build();
	}

	public static LUTUResult ofLU(@NonNull final I_M_HU lu, @NonNull final TUsList tus)
	{
		return builder().lu(LU.of(lu, tus)).build();
	}

	public static LUTUResult ofSingleTopLevelTU(final I_M_HU tu)
	{
		return ofTopLevelTUs(TUsList.ofSingleTU(tu));
	}

	public static LUTUResult ofTopLevelTUs(final Collection<I_M_HU> tus)
	{
		return ofTopLevelTUs(TUsList.ofSingleTUsList(tus));
	}

	public static LUTUResult ofTopLevelTUs(@NonNull final TUsList tus)
	{
		if (tus.isEmpty())
		{
			return EMPTY;
		}
		return builder().topLevelTUs(tus).build();
	}

	public boolean isEmpty() {return lus.isEmpty() && topLevelTUs.isEmpty();}

	public boolean isSingleLU() {return lus.size() == 1;}

	public LU getSingleLU() {return CollectionUtils.singleElement(lus);}

	public I_M_HU getSingleLURecord() {return getSingleLU().toHU();}

	public List<I_M_HU> getLURecords() {return lus.stream().map(LU::toHU).collect(ImmutableList.toImmutableList());}

	public LUTUResult assertNoLUs()
	{
		if (!lus.isEmpty())
		{
			throw new AdempiereException("Expected no LUs: " + this);
		}
		return this;
	}

	public LUTUResult assertNoTopLevelTUs()
	{
		if (!topLevelTUs.isEmpty())
		{
			throw new AdempiereException("Expected no top level TUs: " + this);
		}
		return this;
	}

	public List<I_M_HU> getTopLevelTURecords() {return topLevelTUs.toHURecords();}

	public TU getSingleTopLevelTU() {return topLevelTUs.getSingleTU();}

	public I_M_HU getSingleTopLevelTURecord() {return getSingleTopLevelTU().toHU();}

	public TU getSingleTU()
	{
		return streamAllTUs().collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("Expected only one TU: " + this)));
	}

	public TUsList getAllTUs()
	{
		return streamAllTUs().collect(TUsList.collect());
	}

	public List<I_M_HU> getAllTURecords()
	{
		return streamAllTUs().map(TU::toHU).collect(ImmutableList.toImmutableList());
	}

	public ImmutableSet<HuId> getAllTUIds()
	{
		return streamAllTUs().map(TU::getId).collect(ImmutableSet.toImmutableSet());
	}

	public QtyTU getQtyTUs()
	{
		return streamAllTUs().map(TU::getQtyTU).reduce(QtyTU.ZERO, QtyTU::add);
	}

	private Stream<TU> streamAllTUs()
	{
		return Stream.concat(
				lus.stream().flatMap(LU::streamTUs),
				topLevelTUs.stream()
		);
	}

	public List<I_M_HU> getAllLUAndTURecords()
	{
		return streamAllLUAndTURecords().collect(ImmutableList.toImmutableList());
	}

	public Stream<I_M_HU> streamAllLUAndTURecords()
	{
		return Stream.concat(
				lus.stream().flatMap(LU::streamAllLUAndTURecords),
				topLevelTUs.streamHURecords()
		);
	}

	public LUTUResult mergeWith(final LUTUResult other)
	{
		if (isEmpty())
		{
			return other;
		}
		else if (other.isEmpty())
		{
			return this;
		}
		else
		{
			return builder()
					.lus(LU.mergeLists(this.lus, other.lus))
					.topLevelTUs(this.topLevelTUs.mergeWith(other.topLevelTUs))
					.build();
		}
	}

	//
	//
	// ----------------------------------------------------------------------------------
	//
	//

	/**
	 * TU or VHU
	 */
	@Value
	@Builder
	public static class TU
	{
		@NonNull @Getter(AccessLevel.NONE) I_M_HU hu;
		boolean isAggregate;
		@NonNull QtyTU qtyTU;

		private TU(@NonNull final I_M_HU hu, final boolean isAggregate, @NonNull final QtyTU qtyTU)
		{
			if (isAggregate)
			{
				Check.assume(qtyTU.isPositive(), "QtyTU shall be positive for aggregated TUs, but it was {}", qtyTU);
			}
			else
			{
				Check.assume(qtyTU.isOne(), "QtyTU shall be one for non aggregated TU, but it was {}", qtyTU);
			}

			this.hu = hu;
			this.isAggregate = isAggregate;
			this.qtyTU = qtyTU;
		}

		public static TU ofSingleTU(@NonNull final I_M_HU hu) {return builder().hu(hu).isAggregate(false).qtyTU(QtyTU.ONE).build();}

		public static TU ofAggregatedTU(@NonNull final I_M_HU hu, @NonNull final QtyTU qtyTU) {return builder().hu(hu).isAggregate(true).qtyTU(qtyTU).build();}

		public HuId getId() {return HuId.ofRepoId(hu.getM_HU_ID());}

		public I_M_HU toHU() {return hu;}

		public void assertSingleTU()
		{
			if (!qtyTU.isOne())
			{
				throw new AdempiereException("Expected single TU but got " + this);
			}
		}
	}

	//
	//
	// ----------------------------------------------------------------------------------
	//
	//

	@Value
	public static class TUsList implements Iterable<TU>
	{
		public static final TUsList EMPTY = new TUsList(ImmutableList.of());

		@NonNull @Getter(AccessLevel.NONE) ImmutableList<TU> list;
		@NonNull QtyTU qtyTU;

		private TUsList(@NonNull final ImmutableList<TU> list)
		{
			this.list = list;
			this.qtyTU = stream().map(TU::getQtyTU).reduce(QtyTU.ZERO, QtyTU::add);
		}

		public static TUsList of(@NonNull final Collection<TU> list)
		{
			return list.isEmpty() ? EMPTY : new TUsList(ImmutableList.copyOf(list));
		}

		public static TUsList of(@NonNull final TU... array)
		{
			return array.length == 0 ? EMPTY : new TUsList(ImmutableList.copyOf(array));
		}

		public static TUsList ofSingleTU(@NonNull final I_M_HU tu) {return of(TU.ofSingleTU(tu));}

		public static TUsList ofSingleTUsList(final Collection<I_M_HU> tus)
		{
			if (tus.isEmpty()) {return EMPTY;}
			final ImmutableList<TU> list = tus.stream().map(TU::ofSingleTU).collect(ImmutableList.toImmutableList());
			return of(list);
		}

		public static Collector<? super TU, ?, TUsList> collect()
		{
			return GuavaCollectors.collectUsingMapAccumulator(
					TU::getId,
					map -> TUsList.of(map.values())
			);
		}

		public boolean isEmpty() {return list.isEmpty();}

		@Override
		@NonNull
		public Iterator<TU> iterator() {return list.iterator();}

		public Stream<TU> stream() {return list.stream();}

		public TU getSingleTU() {return CollectionUtils.singleElement(list);}

		public List<TU> toList() {return list;}

		public Stream<I_M_HU> streamHURecords() {return stream().map(TU::toHU);}

		public List<I_M_HU> toHURecords() {return streamHURecords().collect(ImmutableList.toImmutableList());}

		public Set<HuId> getHuIds() {return stream().map(TU::getId).collect(ImmutableSet.toImmutableSet());}

		public TUsList mergeWith(@NonNull final TUsList other)
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
				return Stream.concat(this.list.stream(), other.list.stream())
						.collect(collect());
			}
		}
	}

	//
	//
	// ----------------------------------------------------------------------------------
	//
	//

	@Value
	@Builder(toBuilder = true)
	public static class LU
	{
		@NonNull @Getter(AccessLevel.NONE) I_M_HU hu;
		@NonNull TUsList tus;
		boolean isPreExistingLU;

		public static LU of(@NonNull final I_M_HU lu, @NonNull final TU... tus)
		{
			return builder().hu(lu).tus(TUsList.of(tus)).build();
		}

		public static LU of(@NonNull final I_M_HU lu, @NonNull final List<TU> tus)
		{
			return builder().hu(lu).tus(TUsList.of(tus)).build();
		}

		public static LU of(@NonNull final I_M_HU lu, @NonNull final TUsList tus)
		{
			return builder().hu(lu).tus(tus).build();
		}

		public LU markedAsPreExistingLU()
		{
			return this.isPreExistingLU ? this : toBuilder().isPreExistingLU(true).build();
		}

		public HuId getId() {return HuId.ofRepoId(hu.getM_HU_ID());}

		public I_M_HU toHU() {return hu;}

		public Stream<I_M_HU> streamAllLUAndTURecords()
		{
			return Stream.concat(Stream.of(hu), tus.streamHURecords());
		}

		public Stream<TU> streamTUs() {return tus.stream();}

		public LU mergeWith(@NonNull final LU other)
		{
			return builder()
					.isPreExistingLU(this.isPreExistingLU && other.isPreExistingLU)
					.tus(this.tus.mergeWith(other.tus))
					.build();
		}

		private static List<LU> mergeLists(final List<LU> list1, final List<LU> list2)
		{
			if (list2.isEmpty())
			{
				return list1;
			}
			if (list1.isEmpty())
			{
				return list2;
			}
			else
			{
				final HashMap<HuId, LU> lusNew = new HashMap<>();
				list1.forEach(lu -> lusNew.put(lu.getId(), lu));
				list2.forEach(lu -> lusNew.compute(lu.getId(), (luId, existingLU) -> existingLU == null ? lu : existingLU.mergeWith(lu)));

				return ImmutableList.copyOf(lusNew.values());
			}
		}

	}
}
