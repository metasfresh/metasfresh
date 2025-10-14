package de.metas.externalsystem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableBiMap;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@RequiredArgsConstructor
@EqualsAndHashCode
public final class ExternalSystemType
{
	public static final ExternalSystemType Alberta = new ExternalSystemType("ALBERTA");
	public static final ExternalSystemType RabbitMQ = new ExternalSystemType("RabbitMQRESTAPI");
	public static final ExternalSystemType WOO = new ExternalSystemType("WooCommerce");
	public static final ExternalSystemType GRSSignum = new ExternalSystemType("GRSSignum");
	public static final ExternalSystemType LeichUndMehl = new ExternalSystemType("LeichUndMehl");
	public static final ExternalSystemType ProCareManagement = new ExternalSystemType("ProCareManagement");
	public static final ExternalSystemType Shopware6 = new ExternalSystemType("Shopware6");
	public static final ExternalSystemType Other = new ExternalSystemType("Other");
	public static final ExternalSystemType PrintClient = new ExternalSystemType("PrintingClient");
	public static final ExternalSystemType Github = new ExternalSystemType("Github");
	public static final ExternalSystemType Everhour = new ExternalSystemType("Everhour");
	public static final ExternalSystemType ScriptedExportConversion = new ExternalSystemType("ScriptedExportConversion");

	private static final ConcurrentHashMap<String, ExternalSystemType> interner = new ConcurrentHashMap<>();

	static
	{
		Stream.of(Alberta, RabbitMQ, WOO, GRSSignum, LeichUndMehl, ProCareManagement, Shopware6, Other, PrintClient, Github, Everhour, ScriptedExportConversion)
				.forEach(systemValue -> interner.put(systemValue.getValue(), systemValue));
	}

	private static final ImmutableBiMap<ExternalSystemType, String> LEGACY_CODES = ImmutableBiMap.<ExternalSystemType, String>builder()
			.put(Alberta, "A")
			.put(Shopware6, "S6")
			.put(Other, "Other")
			.put(WOO, "WOO")
			.put(RabbitMQ, "RabbitMQ")
			.put(GRSSignum, "GRS")
			.put(LeichUndMehl, "LM")
			.put(PrintClient, "PC")
			.put(ProCareManagement, "PCM")
			.put(ScriptedExportConversion, "SEC")
			.build();

	@Getter
	private final String value;

	@Nullable
	@JsonCreator
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

	@Nullable
	public static ExternalSystemType ofLegacyCodeOrNull(@NonNull final String code)
	{
		return LEGACY_CODES.inverse().get(code);
	}

	@Override
	public String toString() {return getValue();}

	@JsonValue
	public String toJson() {return getValue();}

	public boolean isAlberta() {return Alberta.equals(this);}

	public boolean isRabbitMQ() {return RabbitMQ.equals(this);}

	public boolean isWOO() {return WOO.equals(this);}

	public boolean isGRSSignum() {return GRSSignum.equals(this);}

	public boolean isLeichUndMehl() {return LeichUndMehl.equals(this);}

	public boolean isPrintClient() {return PrintClient.equals(this);}

	public boolean isProCareManagement() {return ProCareManagement.equals(this);}

	public boolean isShopware6() {return Shopware6.equals(this);}

	public boolean isOther() {return Other.equals(this);}

	public boolean isGithub() {return Github.equals(this);}

	public boolean isEverhour() {return Everhour.equals(this);}

	public boolean isScriptedExportConversion() {return ScriptedExportConversion.equals(this);}
}
