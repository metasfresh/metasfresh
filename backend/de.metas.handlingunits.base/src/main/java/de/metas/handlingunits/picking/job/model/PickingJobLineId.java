package de.metas.handlingunits.picking.job.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class PickingJobLineId implements RepoIdAware
{
	int repoId;

	private PickingJobLineId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_Picking_Job_Line_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(final PickingJobLineId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static PickingJobLineId ofRepoId(final int repoId)
	{
		return new PickingJobLineId(repoId);
	}

	public static PickingJobLineId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new PickingJobLineId(repoId) : null;
	}

	@NonNull
	@JsonCreator
	public static PickingJobLineId ofString(@NonNull final String string)
	{
		try
		{
			return ofRepoId(Integer.parseInt(string));
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Invalid id string: `" + string + "`", ex);
		}
	}

	public String getAsString() {return String.valueOf(getRepoId());}

	public static boolean equals(@Nullable final PickingJobLineId id1, @Nullable final PickingJobLineId id2) {return Objects.equals(id1, id2);}

	public TableRecordReference toTableRecordReference()
	{
		return TableRecordReference.of(I_M_Picking_Job_Line.Table_Name, repoId);
	}
}
