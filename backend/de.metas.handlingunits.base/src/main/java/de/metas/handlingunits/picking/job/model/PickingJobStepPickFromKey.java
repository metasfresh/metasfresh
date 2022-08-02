package de.metas.handlingunits.picking.job.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;

@EqualsAndHashCode
@ToString
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PickingJobStepPickFromKey implements Comparable<PickingJobStepPickFromKey>
{
	public static final PickingJobStepPickFromKey MAIN = new PickingJobStepPickFromKey(null);

	@Getter
	@Nullable PickingJobPickFromAlternativeId alternativeId;

	private PickingJobStepPickFromKey(@Nullable final PickingJobPickFromAlternativeId alternativeId)
	{
		this.alternativeId = alternativeId;
	}

	public static PickingJobStepPickFromKey alternative(@NonNull final PickingJobPickFromAlternativeId alternativeId) {return new PickingJobStepPickFromKey(alternativeId);}

	public boolean isMain() {return alternativeId == null;}

	public boolean isAlternative() {return alternativeId != null;}

	@JsonValue
	public String getAsString() {return alternativeId == null ? "MAIN" : alternativeId.getAsString();}

	@Override
	public int compareTo(final PickingJobStepPickFromKey other) {return getAsString().compareTo(other.getAsString());}
}
