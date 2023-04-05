package de.metas.payment.paymentterm;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_PaymentTerm;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

public enum BaseLineType implements ReferenceListAwareEnum
{
	AfterDelivery(X_C_PaymentTerm.BASELINETYPES_AfterDelivery), // AD
	AfterBillOfLanding(X_C_PaymentTerm.BASELINETYPES_AfterBillOfLanding), // ABL
	InvoiceDate(X_C_PaymentTerm.BASELINETYPES_InvoiceDate) // ID
	;

	@Getter
	private final String code;

	BaseLineType(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static BaseLineType ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static Optional<BaseLineType> optionalOfCode(@Nullable final String code)
	{
		return Optional.ofNullable(ofNullableCode(code));
	}

	public static BaseLineType ofCode(@NonNull final String code)
	{
		final BaseLineType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + BaseLineType.class + " found for code: " + code);
		}
		return type;
	}

	@Nullable
	public static String toCodeOrNull(@Nullable final BaseLineType type)
	{
		return type != null ? type.getCode() : null;
	}

	private static final ImmutableMap<String, BaseLineType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), BaseLineType::getCode);
}
