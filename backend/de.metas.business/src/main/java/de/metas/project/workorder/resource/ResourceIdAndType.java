package de.metas.project.workorder.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.calendar.CalendarResourceId;
import de.metas.product.ResourceId;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

@Value(staticConstructor = "of")
public class ResourceIdAndType
{
	public static final String CalendarResourceId_TYPE = "ResourceIdAndType";
	@NonNull ResourceId resourceId;
	@NonNull WOResourceType type;

	public static ResourceIdAndType machine(@NonNull final ResourceId resourceId) {return of(resourceId, WOResourceType.MACHINE);}

	public static ResourceIdAndType human(@NonNull final ResourceId resourceId) {return of(resourceId, WOResourceType.HUMAN);}

	public static Stream<ResourceIdAndType> allTypes(@NonNull final ResourceId resourceId)
	{
		return Stream.of(machine(resourceId), human(resourceId));
	}

	public static boolean equals(@Nullable final ResourceIdAndType id1, @Nullable final ResourceIdAndType id2) {return Objects.equals(id1, id2);}

	@Override
	@Deprecated
	public String toString()
	{
		return toJsonString();
	}

	@JsonValue
	private String toJsonString()
	{
		return type.getCode() + "-" + resourceId.getRepoId();
	}

	@JsonCreator
	public static ResourceIdAndType ofJsonString(@NonNull final String string)
	{
		try
		{
			final int idx = string.indexOf("-");
			final WOResourceType type = WOResourceType.ofCode(string.substring(0, idx));
			final ResourceId resourceId = RepoIdAwares.ofObject(string.substring(idx + 1), ResourceId.class, ResourceId::ofRepoId);
			return of(resourceId, type);
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Invalid json string: `" + string + "`", ex);
		}
	}

	public CalendarResourceId toCalendarResourceId()
	{
		return CalendarResourceId.ofTypeAndLocalIdPart(CalendarResourceId_TYPE, toJsonString());
	}

	public static ResourceIdAndType ofCalendarResourceId(@NonNull final CalendarResourceId calendarResourceId)
	{
		calendarResourceId.assertType(CalendarResourceId_TYPE);
		return ofJsonString(calendarResourceId.getLocalIdPart());
	}

	@Nullable
	public static ResourceIdAndType ofCalendarResourceIdOrNull(@Nullable final CalendarResourceId calendarResourceId)
	{
		if (calendarResourceId == null || !calendarResourceId.isType(CalendarResourceId_TYPE))
		{
			return null;
		}
		return ofJsonString(calendarResourceId.getLocalIdPart());
	}

	public static Stream<ResourceIdAndType> streamForAllTypes(@NonNull final ResourceId resourceId)
	{
		return Stream.of(WOResourceType.values()).map(type -> of(resourceId, type));
	}
}
