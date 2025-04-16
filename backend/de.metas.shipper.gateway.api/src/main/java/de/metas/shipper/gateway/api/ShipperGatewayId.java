package de.metas.shipper.gateway.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentHashMap;

@EqualsAndHashCode
public final class ShipperGatewayId
{
	@NonNull private final String code;

	@NonNull private static final ConcurrentHashMap<String, ShipperGatewayId> interner = new ConcurrentHashMap<>();

	private ShipperGatewayId(@NonNull final String code)
	{
		this.code = Check.assumeNotNull(StringUtils.trimBlankToNull(code), "Invalid code: {}", code);
	}

	public static ShipperGatewayId ofString(@NonNull final String code)
	{
		final String codeNorm = Check.assumeNotNull(StringUtils.trimBlankToNull(code), "Invalid code: {}", code);
		return interner.computeIfAbsent(codeNorm, ShipperGatewayId::new);
	}

	@JsonCreator
	@Nullable
	public static ShipperGatewayId ofNullableString(@NonNull final String code)
	{
		final String codeNorm = StringUtils.trimBlankToNull(code);
		return codeNorm != null ? ofString(codeNorm) : null;
	}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	@JsonValue
	public String getAsString() {return code;}
}
