package de.metas.workflow.rest_api.model;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.global_qrcodes.PrintableQRCode;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class WorkflowLaunchersList implements Iterable<WorkflowLauncher>
{
	@NonNull private final ImmutableList<WorkflowLauncher> launchers;
	@Getter @Nullable private final PrintableQRCode filterByQRCode;

	@Getter
	@NonNull private final Instant timestamp;

	@Builder
	private WorkflowLaunchersList(
			@NonNull final ImmutableList<WorkflowLauncher> launchers,
			@Nullable final PrintableQRCode filterByQRCode,
			@NonNull final Instant timestamp)
	{
		this.launchers = launchers;
		this.filterByQRCode = filterByQRCode;
		this.timestamp = timestamp;
	}

	@Override
	public Iterator<WorkflowLauncher> iterator() {return launchers.iterator();}

	public Stream<WorkflowLauncher> stream() {return launchers.stream();}

	public boolean isEmpty() {return launchers.isEmpty();}

	public boolean isStaled(@NonNull final Duration maxStaleAccepted)
	{
		return maxStaleAccepted.compareTo(Duration.ZERO) <= 0 // explicitly asked for a fresh value
				|| SystemTime.asInstant().isAfter(timestamp.plus(maxStaleAccepted));
	}

	public boolean equalsIgnoringTimestamp(@NonNull final WorkflowLaunchersList other)
	{
		return Objects.equals(this.launchers, other.launchers)
				&& Objects.equals(this.filterByQRCode, other.filterByQRCode);
	}
}
