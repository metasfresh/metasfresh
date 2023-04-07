package de.metas.payment.paymentterm;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_PaymentTerm;

@AllArgsConstructor
public enum BaseLineType implements ReferenceListAwareEnum
{
	AfterDelivery(X_C_PaymentTerm.BASELINETYPE_AfterDelivery), // AD
	AfterBillOfLanding(X_C_PaymentTerm.BASELINETYPE_AfterBillOfLanding), // ABL
	InvoiceDate(X_C_PaymentTerm.BASELINETYPE_InvoiceDate) // ID
	;

	private static final ReferenceListAwareEnums.ValuesIndex<BaseLineType> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	@NonNull
	public static BaseLineType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@NonNull
	public static BaseLineType ofName(@NonNull final String name)
	{
		try
		{
			return BaseLineType.valueOf(name);
		}
		catch (final Throwable t)
		{
			throw new AdempiereException("No " + BaseLineType.class + " found for name: " + name)
					.appendParametersToMessage()
					.setParameter("AdditionalErrorMessage", t.getMessage());
		}
	}

	public boolean isInvoiceDate()
	{
		return this == InvoiceDate;
	}
}
