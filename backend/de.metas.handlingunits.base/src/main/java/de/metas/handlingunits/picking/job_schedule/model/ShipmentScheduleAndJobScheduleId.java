package de.metas.handlingunits.picking.job_schedule.model;

import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.inout.ShipmentScheduleId;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@Value(staticConstructor = "of")
public class ShipmentScheduleAndJobScheduleId implements Comparable<ShipmentScheduleAndJobScheduleId>
{
	@NonNull ShipmentScheduleId shipmentScheduleId;
	@Nullable PickingJobScheduleId jobScheduleId;

	@Nullable
	public static ShipmentScheduleAndJobScheduleId ofRepoIdsOrNull(final int shipmentScheduleRepoId, final int pickingJobScheduleRepoId)
	{
		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoIdOrNull(shipmentScheduleRepoId);
		if (shipmentScheduleId == null)
		{
			return null;
		}

		return of(shipmentScheduleId, PickingJobScheduleId.ofRepoIdOrNull(pickingJobScheduleRepoId));
	}

	@NonNull
	public static ShipmentScheduleAndJobScheduleId ofRepoIds(final int shipmentScheduleRepoId, final int pickingJobScheduleRepoId)
	{
		final ShipmentScheduleAndJobScheduleId id = ofRepoIdsOrNull(shipmentScheduleRepoId, pickingJobScheduleRepoId);
		if (id == null)
		{
			throw new AdempiereException("No " + ShipmentScheduleAndJobScheduleId.class + " found for shipmentScheduleRepoId=" + shipmentScheduleRepoId + " and pickingJobScheduleRepoId=" + pickingJobScheduleRepoId);
		}
		return id;
	}

	@Override
	@Deprecated
	public String toString() {return toJsonString();}

	@JsonValue
	public String toJsonString()
	{
		return jobScheduleId != null
				? shipmentScheduleId.getRepoId() + "_" + jobScheduleId.getRepoId()
				: "" + shipmentScheduleId.getRepoId();
	}

	@JsonValue
	public static ShipmentScheduleAndJobScheduleId ofJsonString(@NonNull final String json)
	{
		try
		{
			final int idx = json.indexOf("_");
			if (idx > 0)
			{
				return of(
						ShipmentScheduleId.ofRepoId(Integer.parseInt(json.substring(0, idx))),
						PickingJobScheduleId.ofRepoId(Integer.parseInt(json.substring(idx + 1)))
				);
			}
			else
			{
				return of(
						ShipmentScheduleId.ofRepoId(Integer.parseInt(json)),
						null
				);
			}
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid JSON: " + json, ex);
		}
	}

	public TableRecordReference toTableRecordReference()
	{
		return jobScheduleId != null ? jobScheduleId.toTableRecordReference() : shipmentScheduleId.toTableRecordReference();
	}

	public boolean isJobSchedule() {return jobScheduleId != null;}

	@Override
	public int compareTo(@NotNull final ShipmentScheduleAndJobScheduleId other)
	{
		return this.toJsonString().compareTo(other.toJsonString());
	}
}
