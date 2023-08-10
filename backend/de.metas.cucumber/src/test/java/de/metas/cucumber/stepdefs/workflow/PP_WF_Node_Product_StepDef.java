/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.workflow;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_WF_Node_Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class PP_WF_Node_Product_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final PP_WF_Node_Product_StepDefData workflowNodeProductTable;
	private final AD_WF_Node_StepDefData workflowNodeTable;
	private final M_Product_StepDefData productTable;

	public PP_WF_Node_Product_StepDef(
			@NonNull final PP_WF_Node_Product_StepDefData workflowNodeProductTable,
			@NonNull final AD_WF_Node_StepDefData workflowNodeTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.workflowNodeProductTable = workflowNodeProductTable;
		this.workflowNodeTable = workflowNodeTable;
		this.productTable = productTable;
	}

	@And("create PP_WF_Node_Product:")
	public void create_PP_WF_Node_Product(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final BigDecimal quantity = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_PP_WF_Node_Product.COLUMNNAME_Qty);
			final String specification = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_PP_WF_Node_Product.COLUMNNAME_Specification);
			final boolean isSubcontracting = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_PP_WF_Node_Product.COLUMNNAME_IsSubcontracting, false);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_WF_Node_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);

			final String wfStepIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_WF_Node_Product.COLUMNNAME_AD_WF_Node_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_AD_WF_Node wfNode = workflowNodeTable.get(wfStepIdentifier);

			final I_PP_WF_Node_Product wfNodeProduct = InterfaceWrapperHelper.newInstance(I_PP_WF_Node_Product.class);
			wfNodeProduct.setQty(quantity);
			wfNodeProduct.setSpecification(specification);
			wfNodeProduct.setIsSubcontracting(isSubcontracting);
			wfNodeProduct.setM_Product_ID(product.getM_Product_ID());
			wfNodeProduct.setAD_WF_Node_ID(wfNode.getAD_WF_Node_ID());

			InterfaceWrapperHelper.saveRecord(wfNodeProduct);

			final String wfNodeProductIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_WF_Node_Product.COLUMNNAME_PP_WF_Node_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			workflowNodeProductTable.put(wfNodeProductIdentifier, wfNodeProduct);
		}
	}

	@And("^after not more than (.*)s, PP_WF_Node_Product are found:$")
	public void validate_PP_WF_Node_Product(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			try
			{
				StepDefUtil.tryAndWait(timeoutSec, 1000, () -> load_PP_WF_Node_Product(row));
			}
			catch (InterruptedException e)
			{
				throw new RuntimeException(e);
			}
		}
	}

	private boolean load_PP_WF_Node_Product(@NonNull final Map<String, String> row)
	{
		final BigDecimal quantity = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_PP_WF_Node_Product.COLUMNNAME_Qty);
		final String specification = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_PP_WF_Node_Product.COLUMNNAME_Specification);
		final Boolean isSubcontracting = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_PP_WF_Node_Product.COLUMNNAME_IsSubcontracting, null);

		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_WF_Node_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(productIdentifier);

		final String wfStepIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_WF_Node_Product.COLUMNNAME_AD_WF_Node_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_AD_WF_Node wfNode = workflowNodeTable.get(wfStepIdentifier);

		final IQueryBuilder<I_PP_WF_Node_Product> ppWFNodeProductQueryBuilder = queryBL.createQueryBuilder(I_PP_WF_Node_Product.class)
				.addEqualsFilter(I_PP_WF_Node_Product.COLUMNNAME_AD_WF_Node_ID, wfNode.getAD_WF_Node_ID())
				.addEqualsFilter(I_PP_WF_Node_Product.COLUMNNAME_M_Product_ID, product.getM_Product_ID());

		if (isSubcontracting != null)
		{
			ppWFNodeProductQueryBuilder.addEqualsFilter(I_PP_WF_Node_Product.COLUMNNAME_IsSubcontracting, isSubcontracting);
		}
		if (Check.isNotBlank(specification))
		{
			ppWFNodeProductQueryBuilder.addEqualsFilter(I_PP_WF_Node_Product.COLUMNNAME_Specification, specification);
		}
		if (quantity != null)
		{
			ppWFNodeProductQueryBuilder.addEqualsFilter(I_PP_WF_Node_Product.COLUMNNAME_Qty, quantity);
		}

		final Optional<I_PP_WF_Node_Product> ppWFNodeProduct = ppWFNodeProductQueryBuilder.create()
				.firstOnlyOptional(I_PP_WF_Node_Product.class);

		if (!ppWFNodeProduct.isPresent())
		{
			return false;
		}

		final String ppWFNodeProductIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_WF_Node_Product.COLUMNNAME_PP_WF_Node_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		workflowNodeProductTable.putOrReplace(ppWFNodeProductIdentifier, ppWFNodeProduct.get());

		return true;
	}
}
