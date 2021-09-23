package de.metas.handlingunits.picking;

import de.metas.order.createFrom.po_from_so.PurchaseTypeEnum;
import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum TakeWholeHUEnum implements ReferenceListAwareEnum
{
	WHOLE_HU("Y"),
	SPLIT("N"),
	NONE("None");

	private final String code;

	@NonNull
	public static TakeWholeHUEnum ofCode(@NonNull final String code)
	{
		return ofCodeOptional(code)
				.orElseThrow(() -> new AdempiereException("No PurchaseTypeEnum could be found for code!")
						.appendParametersToMessage()
						.setParameter("code", code));
	}

	@NonNull
	public static Optional<TakeWholeHUEnum> ofCodeOptional(@Nullable final String code)
	{
		if (Check.isBlank(code))
		{
			return Optional.empty();
		}

		return Arrays.stream(values())
				.filter(value -> value.getCode().equals(code))
				.findFirst();
	}
}
