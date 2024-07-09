package de.metas.materialtracking.model.validator;

import de.metas.copy_with_details.template.CopyTemplateCustomizer;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.model.copy.ValueToCopy;
import org.compiere.model.copy.ValueToCopyResolveContext;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PP_Order_CopyTemplateCustomizer implements CopyTemplateCustomizer
{
	@Override
	public String getTableName() {return I_PP_Order.Table_Name;}

	@Override
	public ValueToCopy extractValueToCopy(final POInfo poInfo, final String columnName)
	{
		if (de.metas.materialtracking.model.I_PP_Order.COLUMNNAME_QtyOrdered.equals(columnName))
		{
			return ValueToCopy.computeFunction(PP_Order_CopyTemplateCustomizer::computeQtyOrdered);
		}
		else
		{
			return ValueToCopy.NOT_SPECIFIED;
		}
	}

	private static BigDecimal computeQtyOrdered(final ValueToCopyResolveContext context)
	{
		final PO from = context.getFrom();

		final BigDecimal qtyOrderedBeforeOrderClose = from.get_ValueAsBigDecimal(de.metas.materialtracking.model.I_PP_Order.COLUMNNAME_QtyBeforeClose);
		final BigDecimal qtyEntered = from.get_ValueAsBigDecimal(de.metas.materialtracking.model.I_PP_Order.COLUMNNAME_QtyEntered);

		return qtyOrderedBeforeOrderClose == null || qtyOrderedBeforeOrderClose.signum() == 0 ? qtyEntered : qtyOrderedBeforeOrderClose;

	}

	@Override
	public @NonNull InSetPredicate<String> getChildTableNames() {return InSetPredicate.none();}
}
