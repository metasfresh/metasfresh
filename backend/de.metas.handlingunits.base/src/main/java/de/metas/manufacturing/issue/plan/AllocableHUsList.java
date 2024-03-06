package de.metas.manufacturing.issue.plan;

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Iterator;

@EqualsAndHashCode
@ToString
class AllocableHUsList implements Iterable<AllocableHU>
{
	private final ImmutableList<AllocableHU> hus;

	AllocableHUsList(@NonNull final ImmutableList<AllocableHU> hus) {this.hus = hus;}

	@Override
	public @NonNull Iterator<AllocableHU> iterator() {return hus.iterator();}
}
