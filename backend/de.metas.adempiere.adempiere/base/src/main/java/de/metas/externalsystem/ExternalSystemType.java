package de.metas.externalsystem;

import de.metas.util.StringUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public class ExternalSystemType
{
	public static final ExternalSystemType Alberta = new ExternalSystemType("Alberta");
	public static final ExternalSystemType RabbitMQ = new ExternalSystemType("RabbitMQRESTAPI");
	public static final ExternalSystemType WOO = new ExternalSystemType("WOO");
	public static final ExternalSystemType GRSSignum = new ExternalSystemType("GRSSignum");
	public static final ExternalSystemType LeichUndMehl = new ExternalSystemType("LeichUndMehl");
	public static final ExternalSystemType ProCareManagement = new ExternalSystemType("ProCareManagement");
	public static final ExternalSystemType Shopware6 = new ExternalSystemType("Shopware6");
	public static final ExternalSystemType Other = new ExternalSystemType("Other");
	public static final ExternalSystemType PrintingClient = new ExternalSystemType("PrintingClient");
	public static final ExternalSystemType Github = new ExternalSystemType("Github");
	public static final ExternalSystemType Everhour = new ExternalSystemType("Everhour");

	private static final ConcurrentHashMap<String, ExternalSystemType> interner = new ConcurrentHashMap<>();

	static
	{
		Stream.of(Alberta, RabbitMQ, WOO, GRSSignum, LeichUndMehl, ProCareManagement, Shopware6, Other, PrintingClient, Github, Everhour)
				.forEach(systemValue -> interner.put(systemValue.getValue(), systemValue));
	}

	private final String value;

	@Nullable
	public static ExternalSystemType ofValueOrNull(@Nullable final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		return valueNorm != null ? ofValue(valueNorm) : null;
	}

	@NonNull
	public static ExternalSystemType ofValue(@NonNull final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		return interner.computeIfAbsent(valueNorm, ExternalSystemType::new);
	}
}
