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

	public static final Topic remote(final String name)
	{
		return builder().name(name).type(Type.REMOTE).build();
	}

	public static final Topic local(final String name)
	{
		return builder().name(name).type(Type.LOCAL).build();
	}

	public static final Topic of(final String name, Type type)
	{
		return builder().name(name).type(type).build();
	}

	@Builder(toBuilder = true)
	private Topic(
			@NonNull final String name,
			@NonNull final Type type)
	{
		this.name = Check.assumeNotEmpty(name, "name not empty");
		this.type = type;

		this.fullName = type + "." + name;
	}

	public Topic toLocal()
	{
		if (type == Type.LOCAL)
		{
			return this;
		}

		return toBuilder().type(Type.LOCAL).build();
	}

}
