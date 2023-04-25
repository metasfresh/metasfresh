package de.metas.workflow.rest_api.model;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

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

	@Getter
	private final boolean scanBarcodeToStartJobSupport;

	@Getter
	@NonNull private final Instant timestamp;

	@Builder
	private WorkflowLaunchersList(
			@NonNull final ImmutableList<WorkflowLauncher> launchers,
			final boolean scanBarcodeToStartJobSupport,
			@NonNull final Instant timestamp)
	{
		this.launchers = launchers;
		this.scanBarcodeToStartJobSupport = scanBarcodeToStartJobSupport;
		this.timestamp = timestamp;
	}

	public static WorkflowLaunchersList emptyNow()
	{
		return builder().launchers(ImmutableList.of()).timestamp(SystemTime.asInstant()).build();
	}

	@Override
	public Iterator<WorkflowLauncher> iterator() {return launchers.iterator();}

	public Stream<WorkflowLauncher> stream() {return launchers.stream();}

	public boolean isEmpty() {return launchers.isEmpty();}

	public WorkflowLaunchersList mergeWith(@NonNull WorkflowLaunchersList other)
	{
		if (other.isEmpty())
		{
			return this;
		}
		else if (isEmpty())
		{
			return other;
		}
		else
		{
			return builder()
					.launchers(ImmutableList.<WorkflowLauncher>builder()
							.addAll(this.launchers)
							.addAll(other.launchers)
							.build())
					.timestamp(this.timestamp.isAfter(other.timestamp) ? this.timestamp : other.timestamp)
					.build();
		}
	}

	public boolean isStaled(@NonNull final Duration maxStaleAccepted)
	{
		return maxStaleAccepted.compareTo(Duration.ZERO) <= 0 // explicitly asked for a fresh value
				|| SystemTime.asInstant().isAfter(timestamp.plus(maxStaleAccepted));
	}

	public boolean equalsIgnoringTimestamp(@NonNull final WorkflowLaunchersList other)
	{
		return Objects.equals(this.launchers, other.launchers);
	}
}
