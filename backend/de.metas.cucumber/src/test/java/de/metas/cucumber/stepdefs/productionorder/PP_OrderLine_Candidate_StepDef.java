/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.productionorder;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_OrderLine_Candidate;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.model.I_PP_Product_BOMLine;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

public class PP_OrderLine_Candidate_StepDef
{
	private final StepDefData<I_M_Product> productTable;
	private final StepDefData<I_PP_Product_BOMLine> productBOMLineTable;
	private final StepDefData<I_PP_OrderLine_Candidate> ppOrderLineCandidateTable;
	private final StepDefData<I_PP_Order_Candidate> ppOrderCandidateTable;

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public PP_OrderLine_Candidate_StepDef(
			@NonNull final StepDefData<I_M_Product> productTable,
			@NonNull final StepDefData<I_PP_Product_BOMLine> productBOMLineTable,
			@NonNull final StepDefData<I_PP_OrderLine_Candidate> ppOrderLineCandidateTable,
			@NonNull final StepDefData<I_PP_Order_Candidate> ppOrderCandidateTable)
	{
		this.productTable = productTable;
		this.productBOMLineTable = productBOMLineTable;
		this.ppOrderLineCandidateTable = ppOrderLineCandidateTable;
		this.ppOrderCandidateTable = ppOrderCandidateTable;
	}

	@And("^after not more than (.*)s, PP_OrderLine_Candidates are found$")
	public void validatePP_Order_candidate(
			final int timeoutSec,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			validatePP_OrderLine_Candidate(timeoutSec, row);
		}
	}

	private void validatePP_OrderLine_Candidate(
			final int timeoutSec,
			@NonNull final Map<String, String> tableRow) throws InterruptedException
	{
		final String ppOrderCandidateIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_PP_Order_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Order_Candidate ppOrderCandidate = ppOrderCandidateTable.get(ppOrderCandidateIdentifier);

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_PP_OrderLine_Candidate.COLUMNNAME_QtyEntered);

		final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		final String componentType = DataTableUtil.extractStringForColumnName(tableRow, I_PP_OrderLine_Candidate.COLUMNNAME_ComponentType);

		final String productBOMLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_BOMLine.COLUMNNAME_PP_Product_BOMLine_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Product_BOMLine productBOMLineRecord = productBOMLineTable.get(productBOMLineIdentifier);

		final String orderLineCandidateRecordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_PP_OrderLine_Candidate.Table_Name);
		final Supplier<Boolean> ppOrderLineCandidateQueryExecutor = () -> {

			final I_PP_OrderLine_Candidate orderLineCandidateRecord = queryBL.createQueryBuilder(I_PP_OrderLine_Candidate.class)
					.addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_PP_Order_Candidate_ID, ppOrderCandidate.getPP_Order_Candidate_ID())
					.addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
					.addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_QtyEntered, qtyEntered)
					.addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_C_UOM_ID, uomId.getRepoId())
					.addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_ComponentType, componentType)
					.addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_PP_Product_BOMLine_ID, productBOMLineRecord.getPP_Product_BOMLine_ID())
					.create()
					.firstOnly(I_PP_OrderLine_Candidate.class);

			if (orderLineCandidateRecord != null)
			{
				ppOrderLineCandidateTable.putOrReplace(orderLineCandidateRecordIdentifier, orderLineCandidateRecord);
				return true;
			}
			else
			{
				return false;
			}
		};

		final boolean lineCandidateFound = StepDefUtil.tryAndWait(timeoutSec, 500, ppOrderLineCandidateQueryExecutor);

		assertThat(lineCandidateFound).isTrue();
	}
}