package de.metas.gs1;

import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class GS1Element
{
	@Nullable GS1ApplicationIdentifier identifier;
	@NonNull String key;
	@NonNull Object value;

	public String getValueAsString() {return value.toString();}

	public BigDecimal getValueAsBigDecimal() {return NumberUtils.asBigDecimal(value);}
}
