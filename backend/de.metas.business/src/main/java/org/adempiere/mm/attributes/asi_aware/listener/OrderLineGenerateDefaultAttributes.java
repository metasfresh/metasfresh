package org.adempiere.mm.attributes.asi_aware.listener;

import com.google.common.collect.ImmutableList;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Evaluatees;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class OrderLineGenerateDefaultAttributes implements IModelAttributeSetInstanceListener
{
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

	@Override
	public @NonNull String getSourceTableName() {return I_C_OrderLine.Table_Name;}

	@Override
	public List<String> getSourceColumnNames() {return ImmutableList.of();}

	@Override
	public void modelChanged(final Object model)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);

		final AttributeSetInstanceId asiId = attributeSetInstanceBL.setInitialAttributes(
				ProductId.ofRepoId(orderLine.getM_Product_ID()),
				AttributeSetInstanceId.ofRepoIdOrNone(orderLine.getM_AttributeSetInstance_ID()),
				Evaluatees.compose(
						Evaluatees.mapBuilder()
								.put("TableName", I_C_OrderLine.Table_Name)
								.build(),
						InterfaceWrapperHelper.getEvaluatee(orderLine)
				)
		);

		orderLine.setM_AttributeSetInstance_ID(asiId.getRepoId());
	}
}
