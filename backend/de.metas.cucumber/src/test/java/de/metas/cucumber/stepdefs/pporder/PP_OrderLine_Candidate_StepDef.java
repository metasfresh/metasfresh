/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.pporder;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.billofmaterial.PP_Product_BOMLine_StepDefData;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.compiere.model.IQuery;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.ProductBOMLineId;
import org.eevolution.model.I_PP_OrderLine_Candidate;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class PP_OrderLine_Candidate_StepDef
{
	private final M_Product_StepDefData productTable;
	private final PP_Product_BOMLine_StepDefData productBOMLineTable;
	private final PP_OrderLine_Candidate_StepDefData ppOrderLineCandidateTable;
	private final PP_Order_Candidate_StepDefData ppOrderCandidateTable;
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public PP_OrderLine_Candidate_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final PP_Product_BOMLine_StepDefData productBOMLineTable,
			@NonNull final PP_OrderLine_Candidate_StepDefData ppOrderLineCandidateTable,
			@NonNull final PP_Order_Candidate_StepDefData ppOrderCandidateTable,
			@NonNull final M_AttributeSetInstance_StepDefData attributeSetInstanceTable)
	{
		this.productTable = productTable;
		this.productBOMLineTable = productBOMLineTable;
		this.ppOrderLineCandidateTable = ppOrderLineCandidateTable;
		this.ppOrderCandidateTable = ppOrderCandidateTable;
		this.attributeSetInstanceTable = attributeSetInstanceTable;
	}

	@And("^after not more than (.*)s, PP_OrderLine_Candidates are found$")
	public void validatePP_Order_candidate(
			final int timeoutSec,
			@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_PP_OrderLine_Candidate.Table_Name)
				.forEach(row -> validatePP_OrderLine_Candidate(timeoutSec, row));
	}

	private void validatePP_OrderLine_Candidate(final int timeoutSec, @NonNull final DataTableRow row) throws InterruptedException
	{
		final I_PP_OrderLine_Candidate ppOrderLineCandidate = StepDefUtil.tryAndWaitForItem(
				timeoutSec,
				500,
				() -> {
					final IQuery<I_PP_OrderLine_Candidate> query = toSqlQuery(row);
					return query.firstOnlyOptional(I_PP_OrderLine_Candidate.class)
							.map(ItemProvider.ProviderResult::resultWasFound)
							.orElseGet(() -> ItemProvider.ProviderResult.resultWasNotFound("No record found by query: " + query));
				});

		row.getAsOptionalIdentifier(I_PP_OrderLine_Candidate.COLUMNNAME_M_AttributeSetInstance_ID)
				.map(attributeSetInstanceTable::getId)
				.ifPresent(expectedASIId -> {
					final AttributesKey expectedASIKey = AttributesKeys
							.createAttributesKeyFromASIStorageAttributes(expectedASIId)
							.orElse(AttributesKey.NONE);

					final AttributesKey ppOrderLineCandAttributesKeys = AttributesKeys
							.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoIdOrNone(ppOrderLineCandidate.getM_AttributeSetInstance_ID()))
							.orElse(AttributesKey.NONE);

					assertThat(ppOrderLineCandAttributesKeys).isEqualTo(expectedASIKey);
				});

		row.getAsOptionalIdentifier().ifPresent(identifier -> ppOrderLineCandidateTable.putOrReplace(identifier, ppOrderLineCandidate));
	}

	private IQuery<I_PP_OrderLine_Candidate> toSqlQuery(@NonNull final DataTableRow row)
	{
		final PPOrderCandidateId ppOrderCandidateId = row.getAsIdentifier(I_PP_Order_Candidate.COLUMNNAME_PP_Order_Candidate_ID).lookupIdIn(ppOrderCandidateTable);
		final ProductId productId = row.getAsIdentifier(I_PP_Order_Candidate.COLUMNNAME_M_Product_ID).lookupIdIn(productTable);
		final BigDecimal qtyEntered = row.getAsBigDecimal(I_PP_OrderLine_Candidate.COLUMNNAME_QtyEntered);
		final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(row.getAsString(I_PP_OrderLine_Candidate.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName())));
		final BOMComponentType componentType = row.getAsEnum(I_PP_OrderLine_Candidate.COLUMNNAME_ComponentType, BOMComponentType.class);
		final ProductBOMLineId productBOMLineId = row.getAsIdentifier(I_PP_OrderLine_Candidate.COLUMNNAME_PP_Product_BOMLine_ID).lookupIdIn(productBOMLineTable);

		return queryBL.createQueryBuilder(I_PP_OrderLine_Candidate.class)
				.addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_PP_Order_Candidate_ID, ppOrderCandidateId)
				.addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_QtyEntered, qtyEntered)
				.addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_C_UOM_ID, uomId.getRepoId())
				.addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_ComponentType, componentType)
				.addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_PP_Product_BOMLine_ID, productBOMLineId)
				.create();
	}
}