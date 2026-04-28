package de.metas.frontend_testing.expectations.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Splitter;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.X12DE355;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;
import java.util.List;

@Value
public class QtyAndUOMString
{
	@NonNull BigDecimal qty;
	@NonNull X12DE355 uom;

	private QtyAndUOMString(@NonNull final BigDecimal qty, @NonNull final X12DE355 uom)
	{
		this.qty = qty;
		this.uom = uom;
	}

	public static QtyAndUOMString of(@NonNull final BigDecimal qty, @NonNull final X12DE355 uom)
	{
		return new QtyAndUOMString(qty, uom);
	}

	@NonNull
	@JsonCreator
	public static QtyAndUOMString parseString(@NonNull final String string)
	{
		final List<String> parts = Splitter.on(" ").trimResults().omitEmptyStrings().splitToList(string);
		if (parts.size() != 2)
		{
			throw new AdempiereException("Cannot parse " + string + " to QtyAndUOMSymbol: expected 2 parts separated by space");
		}

		try
		{
			return of(new BigDecimal(parts.get(0)), X12DE355.ofCode(parts.get(1)));
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Cannot parse " + string + " to QtyAndUOMSymbol", ex);
		}
	}

	@Override
	public String toString() {return toJson();}

	@JsonValue
	public String toJson() {return qty + " " + uom;}

	public Quantity toQuantity() {return Quantitys.of(qty, uom);}
}
