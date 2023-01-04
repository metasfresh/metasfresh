package de.metas.event;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.slf4j.Logger;

/**
 * @see IEventBusFactory#getEventBus(Topic)
 * @see Type
 */
@Value
public class Topic
{
	private static final Logger logger = LogManager.getLogger(Topic.class);

	String name;
	Type type;
	String fullName;

	public static Topic distributed(final String name)
	{
		return builder().name(name).type(Type.DISTRIBUTED).build();
	}

	public static Topic local(final String name)
	{
		return builder().name(name).type(Type.LOCAL).build();
	}

	public static Topic of(final String name, final Type type)
	{
		return builder().name(name).type(type).build();
	}

	@Builder(toBuilder = true)
	private Topic(
			@NonNull final String name,
			@NonNull final Type type)
	{
		if (!EventBusConfig.isEnabled())
		{
			logger.warn("trying to create a distributed Topic (topicName={}) but EventBusConfig.isEnabled() == false, fallback to Local topic...", name);
			this.type = Type.LOCAL;
		}
		else
		{
			this.type = type;
		}

		this.name = Check.assumeNotEmpty(name, "name not empty");

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
