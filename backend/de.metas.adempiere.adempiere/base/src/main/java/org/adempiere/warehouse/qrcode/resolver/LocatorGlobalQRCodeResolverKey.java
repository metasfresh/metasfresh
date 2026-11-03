package org.adempiere.warehouse.qrcode.resolver;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentHashMap;

@EqualsAndHashCode
public class LocatorGlobalQRCodeResolverKey
{
	@NonNull String value;

	private static final ConcurrentHashMap<String, LocatorGlobalQRCodeResolverKey> interner = new ConcurrentHashMap<>();

	private LocatorGlobalQRCodeResolverKey(@NonNull final String value)
	{
		this.value = value;
	}

	@NonNull
	public static LocatorGlobalQRCodeResolverKey ofString(@NonNull String value)
	{
		final LocatorGlobalQRCodeResolverKey key = ofNullableString(value);
		if (key == null)
		{
			throw new AdempiereException("Invalid key: " + value);
		}
		return key;
	}

	@NonNull
	public static LocatorGlobalQRCodeResolverKey ofClass(@NonNull Class<?> clazz)
	{
		return ofString(clazz.getSimpleName());
	}


	@JsonCreator
	@Nullable
	public static LocatorGlobalQRCodeResolverKey ofNullableString(@Nullable String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		if (valueNorm == null)
		{
			return null;
		}

		return interner.computeIfAbsent(valueNorm, LocatorGlobalQRCodeResolverKey::new);
	}

	@Override
	@Deprecated
	public String toString() {return toJson();}

	@JsonValue
	public String toJson() {return value;}
}
