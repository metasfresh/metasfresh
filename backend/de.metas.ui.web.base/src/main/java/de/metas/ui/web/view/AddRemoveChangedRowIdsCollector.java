package de.metas.ui.web.view;

import com.google.common.collect.ImmutableSet;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.NonNull;
import lombok.ToString;
import org.elasticsearch.common.util.set.Sets;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class AddRemoveChangedRowIdsCollector
{
	public static final AddRemoveChangedRowIdsCollector NOT_RECORDING = new NotRecordingAddRemoveChangedRowIdsCollector();

	public static AddRemoveChangedRowIdsCollector newRecording() {return new RecordingAddRemoveChangedRowIdsCollector();}

	//
	//
	//

	public abstract void collectAddedRowId(final DocumentId rowId);

	public abstract Set<DocumentId> getAddedRowIds();

	public boolean hasAddedRows() {return !getAddedRowIds().isEmpty();}

	public abstract void collectRemovedRowIds(final Collection<DocumentId> rowIds);

	public abstract Set<DocumentId> getRemovedRowIds();

	public abstract void collectChangedRowIds(final Collection<DocumentId> rowIds);

	public abstract Set<DocumentId> getChangedRowIds();

	public Set<DocumentId> getChangedOrRemovedRowIds()
	{
		return Sets.union(getChangedRowIds(), getRemovedRowIds());
	}

	//
	//
	//

	public static class NotRecordingAddRemoveChangedRowIdsCollector extends AddRemoveChangedRowIdsCollector
	{
		private NotRecordingAddRemoveChangedRowIdsCollector() {}

		@Override
		public void collectAddedRowId(final DocumentId rowId) {}

		@Override
		public Set<DocumentId> getAddedRowIds()
		{
			throw new IllegalArgumentException("changes are not recorded");
		}

		@Override
		public void collectRemovedRowIds(final Collection<DocumentId> rowIds) {}

		@Override
		public Set<DocumentId> getRemovedRowIds()
		{
			throw new IllegalArgumentException("changes are not recorded");
		}

		@Override
		public void collectChangedRowIds(final Collection<DocumentId> rowIds) {}

		@Override
		public Set<DocumentId> getChangedRowIds()
		{
			throw new IllegalArgumentException("changes are not recorded");
		}
	}

	//
	//
	//
	@ToString
	public static class RecordingAddRemoveChangedRowIdsCollector extends AddRemoveChangedRowIdsCollector
	{
		private HashSet<DocumentId> addedRowIds;
		private HashSet<DocumentId> removedRowIds;
		private HashSet<DocumentId> changedRowIds;

		private RecordingAddRemoveChangedRowIdsCollector() {}

		@Override
		public void collectAddedRowId(@NonNull final DocumentId rowId)
		{
			if (addedRowIds == null)
			{
				addedRowIds = new HashSet<>();
			}
			addedRowIds.add(rowId);
		}

		@Override
		public Set<DocumentId> getAddedRowIds()
		{
			final HashSet<DocumentId> addedRowIds = this.addedRowIds;
			return addedRowIds != null ? addedRowIds : ImmutableSet.of();
		}

		@Override
		public boolean hasAddedRows()
		{
			final HashSet<DocumentId> addedRowIds = this.addedRowIds;
			return addedRowIds != null && !addedRowIds.isEmpty();
		}

		@Override
		public void collectRemovedRowIds(final Collection<DocumentId> rowIds)
		{
			if (removedRowIds == null)
			{
				removedRowIds = new HashSet<>(rowIds);
			}
			else
			{
				removedRowIds.addAll(rowIds);
			}
		}

		@Override
		public Set<DocumentId> getRemovedRowIds()
		{
			final HashSet<DocumentId> removedRowIds = this.removedRowIds;
			return removedRowIds != null ? removedRowIds : ImmutableSet.of();
		}

		@Override
		public void collectChangedRowIds(final Collection<DocumentId> rowIds)
		{
			if (changedRowIds == null)
			{
				changedRowIds = new HashSet<>(rowIds);
			}
			else
			{
				changedRowIds.addAll(rowIds);
			}
		}

		@Override
		public Set<DocumentId> getChangedRowIds()
		{
			final HashSet<DocumentId> changedRowIds = this.changedRowIds;
			return changedRowIds != null ? changedRowIds : ImmutableSet.of();
		}
	}
}
