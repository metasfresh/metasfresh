package de.metas.handlingunits.qrcodes.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.mm.attributes.AttributeCode;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class HUQRCodeAttribute
{
	@NonNull AttributeCode code;
	@NonNull String displayName;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable String value;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable String valueRendered;

	@Builder
	@Jacksonized
	private HUQRCodeAttribute(
			@NonNull final AttributeCode code,
			@NonNull final String displayName,
			@Nullable final String value,
			@Nullable final String valueRendered)
	{
		this.code = code;
		this.displayName = displayName;
		this.value = StringUtils.trimBlankToNull(value);
		this.valueRendered = valueRendered != null ? valueRendered : this.value;
	}

	@Nullable
	public BigDecimal getValueAsBigDecimal()
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		return valueNorm != null ? NumberUtils.asBigDecimal(valueNorm) : null;
	}

	@Nullable
	public LocalDate getValueAsLocalDate()
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		return valueNorm != null ? LocalDate.parse(valueNorm) : null;
	}
}
