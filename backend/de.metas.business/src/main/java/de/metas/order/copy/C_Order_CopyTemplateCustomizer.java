package de.metas.order.copy;

import com.google.common.collect.ImmutableSet;
import de.metas.copy_with_details.template.CopyTemplateCustomizer;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_Cost;
import org.compiere.model.POInfo;
import org.compiere.model.copy.ValueToCopy;
import org.springframework.stereotype.Component;

@Component
public class C_Order_CopyTemplateCustomizer implements CopyTemplateCustomizer
{
	private static final ImmutableSet<String> COLUMNNAMES_ToCopyDirectly = ImmutableSet.of(
			I_C_Order.COLUMNNAME_PreparationDate, // task 09000
			I_C_Order.COLUMNNAME_FreightAmt
	);

	@Override
	public String getTableName() {return I_C_Order.Table_Name;}

	@Override
	public ValueToCopy extractValueToCopy(final POInfo poInfo, final String columnName)
	{
		return COLUMNNAMES_ToCopyDirectly.contains(columnName) ? ValueToCopy.DIRECT_COPY : ValueToCopy.NOT_SPECIFIED;
	}

	@Override
	public @NonNull InSetPredicate<String> getChildTableNames()
	{
		return InSetPredicate.only(
				I_C_OrderLine.Table_Name
				, I_C_Order_Cost.Table_Name
		);
	}
}
