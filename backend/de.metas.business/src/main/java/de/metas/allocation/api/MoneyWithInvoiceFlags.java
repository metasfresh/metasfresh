package de.metas.allocation.api;

import de.metas.invoice.InvoiceDocBaseType;
import de.metas.money.Money;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;

//
//
//
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
public class MoneyWithInvoiceFlags
{
	@NonNull Money value;
	@Getter boolean isAP;
	@Getter boolean isCreditMemo;

	@Getter boolean isAPAdjusted;
	@Getter boolean isCMAjusted;

	@SuppressWarnings("unused")
	public static class MoneyWithInvoiceFlagsBuilder
	{
		public MoneyWithInvoiceFlagsBuilder docBaseType(@NonNull final InvoiceDocBaseType docBaseType)
		{
			return isAP(docBaseType.isAP()).isCreditMemo(docBaseType.isCreditMemo());
		}
	}

	public MoneyWithInvoiceFlags withAPAdjusted()
	{
		if (isAPAdjusted)
		{
			return this;
		}
		else if (isAP)
		{
			return toBuilder().value(value.negate()).isAPAdjusted(true).build();
		}
		else
		{
			return toBuilder().value(value).isAPAdjusted(true).build();
		}
	}

	public MoneyWithInvoiceFlags withoutAPAdjusted()
	{
		if (!isAPAdjusted)
		{
			return this;
		}
		else if (isAP)
		{
			return toBuilder().value(value.negate()).isAPAdjusted(false).build();
		}
		else
		{
			return toBuilder().value(value).isAPAdjusted(false).build();
		}
	}

	public MoneyWithInvoiceFlags withCMAdjusted()
	{
		if (isCMAjusted)
		{
			return this;
		}
		else if (isCreditMemo)
		{
			return toBuilder().value(value.negate()).isCMAjusted(true).build();
		}
		else
		{
			return toBuilder().value(value).isCMAjusted(true).build();
		}
	}

	@SuppressWarnings("unused")
	public MoneyWithInvoiceFlags withCMAdjustedIf(final boolean condition)
	{
		return condition ? withCMAdjusted() : withoutCMAdjusted();
	}

	public MoneyWithInvoiceFlags withoutCMAdjusted()
	{
		if (!isCMAjusted)
		{
			return this;
		}
		else if (isCreditMemo)
		{
			return toBuilder().value(value.negate()).isCMAjusted(false).build();
		}
		else
		{
			return toBuilder().value(value).isCMAjusted(false).build();
		}
	}

	public Money toMoney() {return value;}

	public BigDecimal toBigDecimal() {return value.toBigDecimal();}

	public boolean isZero() {return value.isZero();}
}
