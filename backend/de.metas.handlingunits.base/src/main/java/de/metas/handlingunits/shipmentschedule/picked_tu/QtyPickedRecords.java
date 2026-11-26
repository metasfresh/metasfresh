package de.metas.handlingunits.shipmentschedule.picked_tu;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inout.ShipmentScheduleId;
import de.metas.quantity.Quantity;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.compiere.model.I_C_UOM;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A list of {@code M_ShipmentSchedule_QtyPicked} rows, in the order given, with the questions the shipment code asks about
 * such a list. A row that has a {@code VHU_ID} references its CU; a row without one is "TU only": it stands for the CUs of its TU.
 */
@EqualsAndHashCode
@ToString
public final class QtyPickedRecords implements Iterable<I_M_ShipmentSchedule_QtyPicked>
{
	public static final QtyPickedRecords EMPTY = new QtyPickedRecords(ImmutableList.of());

	private final ImmutableList<I_M_ShipmentSchedule_QtyPicked> rows;

	private QtyPickedRecords(@NonNull final ImmutableList<I_M_ShipmentSchedule_QtyPicked> rows)
	{
		this.rows = rows;
	}

	public static QtyPickedRecords of(@NonNull final Collection<? extends I_M_ShipmentSchedule_QtyPicked> rows)
	{
		return rows.isEmpty() ? EMPTY : new QtyPickedRecords(ImmutableList.copyOf(rows));
	}

	@Override
	public Iterator<I_M_ShipmentSchedule_QtyPicked> iterator()
	{
		return rows.iterator();
	}

	public Stream<I_M_ShipmentSchedule_QtyPicked> stream()
	{
		return rows.stream();
	}

	public boolean isEmpty()
	{
		return rows.isEmpty();
	}

	public ImmutableList<I_M_ShipmentSchedule_QtyPicked> toList()
	{
		return rows;
	}

	public ImmutableSet<ShipmentScheduleId> extractScheduleIds()
	{
		return rows.stream()
				.map(row -> ShipmentScheduleId.ofRepoId(row.getM_ShipmentSchedule_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	public static boolean hasCU(@NonNull final I_M_ShipmentSchedule_QtyPicked row)
	{
		return HuId.ofRepoIdOrNull(row.getVHU_ID()) != null;
	}

	/**
	 * @return the rows that reference their CU
	 */
	public QtyPickedRecords withCU()
	{
		return filter(QtyPickedRecords::hasCU);
	}

	/**
	 * @return the rows that do not reference a CU, by their ID
	 */
	public QtyPickedRecords tuOnlySortedById()
	{
		return new QtyPickedRecords(rows.stream()
				.filter(row -> !hasCU(row))
				.sorted(Comparator.comparingInt(I_M_ShipmentSchedule_QtyPicked::getM_ShipmentSchedule_QtyPicked_ID))
				.collect(ImmutableList.toImmutableList()));
	}

	public QtyPickedRecords filter(@NonNull final Predicate<I_M_ShipmentSchedule_QtyPicked> predicate)
	{
		return new QtyPickedRecords(rows.stream().filter(predicate).collect(ImmutableList.toImmutableList()));
	}

	/**
	 * @return the CUs that the rows reference
	 */
	public ImmutableSet<HuId> extractCUIds()
	{
		return rows.stream()
				.filter(QtyPickedRecords::hasCU)
				.map(row -> HuId.ofRepoId(row.getVHU_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	public boolean hasNegativeQty()
	{
		return rows.stream().anyMatch(row -> row.getQtyPicked().signum() < 0);
	}

	/**
	 * @param productStockingUOM the UOM of the rows' {@code QtyPicked}: the stock UOM of the product
	 */
	public Quantity totalQtyPicked(@NonNull final I_C_UOM productStockingUOM)
	{
		return rows.stream()
				.map(row -> Quantity.of(row.getQtyPicked(), productStockingUOM))
				.reduce(Quantity::add)
				.orElseGet(() -> Quantity.zero(productStockingUOM));
	}
}
