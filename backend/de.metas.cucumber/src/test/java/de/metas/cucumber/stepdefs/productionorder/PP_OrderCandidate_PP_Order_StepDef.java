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
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_OrderCandidate_PP_Order;
import org.eevolution.model.I_PP_Order_Candidate;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

public class PP_OrderCandidate_PP_Order_StepDef
{
	private final StepDefData<I_PP_Order_Candidate> ppOrderCandidateTable;
	private final StepDefData<I_PP_Order> ppOrderTable;

	public PP_OrderCandidate_PP_Order_StepDef(
			@NonNull final StepDefData<I_PP_Order_Candidate> ppOrderCandidateTable,
			@NonNull final StepDefData<I_PP_Order> ppOrderTable)
	{
		this.ppOrderCandidateTable = ppOrderCandidateTable;
		this.ppOrderTable = ppOrderTable;
	}

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@And("^after not more than (.*)s, PP_OrderCandidate_PP_Order are found$")
	public void validatePP_OrderCandidate_PP_Order(
			final int timeoutSec,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_PP_Order orderRecord = ppOrderTable.get(orderIdentifier);

			final String orderCandidateIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_PP_Order_Candidate orderCandidateRecord = ppOrderCandidateTable.get(orderCandidateIdentifier);

			final int qtyEntered = DataTableUtil.extractIntForColumnName(tableRow, I_PP_OrderCandidate_PP_Order.COLUMNNAME_QtyEntered);

			final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_PP_OrderCandidate_PP_Order.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
			final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));
			final Supplier<Boolean> allocationQueryExecutor = () -> {

				final I_PP_OrderCandidate_PP_Order allocationRecord = queryBL.createQueryBuilder(I_PP_OrderCandidate_PP_Order.class)
						.addEqualsFilter(I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_ID, orderRecord.getPP_Order_ID())
						.addEqualsFilter(I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_Candidate_ID, orderCandidateRecord.getPP_Order_Candidate_ID())
						.addEqualsFilter(I_PP_OrderCandidate_PP_Order.COLUMNNAME_QtyEntered, qtyEntered)
						.addEqualsFilter(I_PP_OrderCandidate_PP_Order.COLUMNNAME_C_UOM_ID, uomId.getRepoId())
						.create()
						.firstOnly(I_PP_OrderCandidate_PP_Order.class);

				return allocationRecord != null;
			};

			final boolean allocationFound = StepDefUtil.tryAndWait(timeoutSec, 500, allocationQueryExecutor);

			assertThat(allocationFound).isTrue();
		}
	}
}