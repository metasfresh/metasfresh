package de.metas.handlingunits.picking.job_schedule.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.handlingunits.model.I_M_Picking_Job_Schedule;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class PickingJobScheduleId implements RepoIdAware
{
	int repoId;

	private PickingJobScheduleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID);
	}

	@Override
	@JsonValue
	public int getRepoId() {return repoId;}

	@JsonCreator
	public static PickingJobScheduleId ofRepoId(final int repoId) {return new PickingJobScheduleId(repoId);}

	public static PickingJobScheduleId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? new PickingJobScheduleId(repoId) : null;}

	public static int toRepoId(final PickingJobScheduleId id) {return id != null ? id.getRepoId() : -1;}

	public static boolean equals(@Nullable final PickingJobScheduleId id1, @Nullable final PickingJobScheduleId id2) {return Objects.equals(id1, id2);}

	public TableRecordReference toTableRecordReference()
	{
		return TableRecordReference.of(I_M_Picking_Job_Schedule.Table_Name, repoId);
	}
}
