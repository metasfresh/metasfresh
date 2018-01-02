package de.metas.event;

import org.adempiere.util.Check;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * @see IEventBusFactory#getEventBus(Topic)
 * @see Type
 */
@Value
public class Topic
{
	String name;
	Type type;
	String fullName;

	public static final Topic of(final String name, Type type)
	{
		return builder()
				.name(name)
				.type(type)
				.build();
	}

	@Builder
	public Topic(
			@NonNull final String name,
			@NonNull final Type type)
	{
		this.name = Check.assumeNotEmpty(name, "name not empty");
		this.type = type;

		this.fullName = type + "." + name;
	}

}
