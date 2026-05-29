package org.adempiere.util.lang.impl;

import de.metas.process.PInstanceId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@EqualsAndHashCode
@ToString(doNotUseGetters = true)
public final class TableRecordReferenceSelection
{
	@Nullable private final PInstanceId selectionId;
	@Nullable private final TableRecordReferenceSet recordRefs;

	private TableRecordReferenceSelection(
			@Nullable final PInstanceId selectionId,
			@Nullable final TableRecordReferenceSet recordRefs)
	{
		if (selectionId != null)
		{
			this.selectionId = selectionId;
			this.recordRefs = null;
		}
		else if (recordRefs != null)
		{
			this.selectionId = null;
			this.recordRefs = recordRefs;
		}
		else
		{
			throw new AdempiereException("Either selectionId or recordRefs shall be set");
		}
	}

	public static TableRecordReferenceSelection ofSelectionId(@NonNull final PInstanceId selectionId)
	{
		return new TableRecordReferenceSelection(selectionId, null);
	}

	public static TableRecordReferenceSelection ofRecordRefs(@NonNull final TableRecordReferenceSet recordRefs)
	{
		return new TableRecordReferenceSelection(null, recordRefs);
	}

	public interface CaseMapper<T>
	{
		T selectionId(@NonNull PInstanceId selectionId);

		T recordRefs(@NonNull TableRecordReferenceSet recordRefs);
	}

	public <T> T map(@NonNull final CaseMapper<T> mapper)
	{
		if (selectionId != null)
		{
			return mapper.selectionId(selectionId);
		}
		else if (recordRefs != null)
		{
			return mapper.recordRefs(recordRefs);
		}
		else
		{
			throw new IllegalStateException("No case mapped"); // shall not happen
		}
	}
}
