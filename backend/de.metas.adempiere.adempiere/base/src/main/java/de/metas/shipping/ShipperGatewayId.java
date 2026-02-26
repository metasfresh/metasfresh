package de.metas.shipping;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@SuppressWarnings("UnstableApiUsage")
@EqualsAndHashCode
public final class ShipperGatewayId
{
	@NonNull private final String value;

	private static final Interner<ShipperGatewayId> interner = Interners.newStrongInterner();

	private ShipperGatewayId(@NonNull final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		if (valueNorm == null)
		{
			throw new AdempiereException("value shall not be blank");
		}
		this.value = valueNorm;
	}

	public static ShipperGatewayId ofString(@NonNull final String value)
	{
		return interner.intern(new ShipperGatewayId(value));
	}

	@Nullable
	@JsonCreator
	public static ShipperGatewayId ofNullableString(@Nullable final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		return valueNorm != null ? ofString(valueNorm) : null;
	}

	@Override
	@Deprecated
	public String toString() {return toJson();}

	@JsonValue
	public String toJson() {return value;}

}
