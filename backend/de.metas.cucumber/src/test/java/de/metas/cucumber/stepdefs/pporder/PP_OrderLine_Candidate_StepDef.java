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

package de.metas.cucumber.stepdefs.pporder;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.billofmaterial.PP_Product_BOMLine_StepDefData;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.IQuery;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.ProductBOMLineId;
import org.eevolution.model.I_PP_OrderLine_Candidate;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;

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
		final I_PP_OrderLine_Candidate ppOrderLineCandidate = StepDefUtil.tryAndWaitForItem(toSqlQuery(row))
				.validateUsingConsumer(record -> this.validatePP_OrderLine_Candidate(record, row))
				.maxWaitSeconds(timeoutSec)
				.execute();

		row.getAsOptionalIdentifier().ifPresent(identifier -> ppOrderLineCandidateTable.putOrReplace(identifier, ppOrderLineCandidate));
	}

	private IQuery<I_PP_OrderLine_Candidate> toSqlQuery(@NonNull final DataTableRow row)
	{
		final PPOrderCandidateId ppOrderCandidateId = row.getAsIdentifier(I_PP_Order_Candidate.COLUMNNAME_PP_Order_Candidate_ID).lookupIdIn(ppOrderCandidateTable);
		final ProductId productId = row.getAsIdentifier(I_PP_Order_Candidate.COLUMNNAME_M_Product_ID).lookupIdIn(productTable);
		// final Quantity qtyEntered = row.getAsQuantity(I_PP_OrderLine_Candidate.COLUMNNAME_QtyEntered, I_PP_OrderLine_Candidate.COLUMNNAME_C_UOM_ID, uomDAO::getByX12DE355);
		final BOMComponentType componentType = row.getAsEnum(I_PP_OrderLine_Candidate.COLUMNNAME_ComponentType, BOMComponentType.class);
		final ProductBOMLineId productBOMLineId = row.getAsIdentifier(I_PP_OrderLine_Candidate.COLUMNNAME_PP_Product_BOMLine_ID).lookupIdIn(productBOMLineTable);

		return queryBL.createQueryBuilder(I_PP_OrderLine_Candidate.class)
				.addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_PP_Order_Candidate_ID, ppOrderCandidateId)
				.addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_M_Product_ID, productId)
				// .addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_QtyEntered, qtyEntered.toBigDecimal())
				// .addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_C_UOM_ID, qtyEntered.getUomId())
				.addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_ComponentType, componentType)
				.addEqualsFilter(I_PP_OrderLine_Candidate.COLUMNNAME_PP_Product_BOMLine_ID, productBOMLineId)
				.create();
	}

	private void validatePP_OrderLine_Candidate(final I_PP_OrderLine_Candidate actual, @NonNull final DataTableRow expected)
	{
		final SoftAssertions softly = new SoftAssertions();

		expected.getAsOptionalIdentifier(I_PP_Order_Candidate.COLUMNNAME_M_Product_ID)
				.map(productTable::getId)
				.ifPresent(productId -> softly.assertThat(ProductId.ofRepoId(actual.getM_Product_ID())).as("M_Product_ID").isEqualTo(productId));

		expected.getAsOptionalQuantity(I_PP_OrderLine_Candidate.COLUMNNAME_QtyEntered, I_PP_OrderLine_Candidate.COLUMNNAME_C_UOM_ID, uomDAO::getByX12DE355)
				.ifPresent(qtyEntered -> {
					softly.assertThat(actual.getQtyEntered()).as("QtyEntered").isEqualByComparingTo(qtyEntered.toBigDecimal());
					softly.assertThat(actual.getC_UOM_ID()).as("C_UOM_ID").isEqualTo(qtyEntered.getUomId().getRepoId());
				});

		expected.getAsOptionalEnum(I_PP_OrderLine_Candidate.COLUMNNAME_ComponentType, BOMComponentType.class)
				.ifPresent(componentType -> softly.assertThat(actual.getComponentType()).as("ComponentType").isEqualTo(componentType.getCode()));

		expected.getAsOptionalIdentifier(I_PP_OrderLine_Candidate.COLUMNNAME_PP_Product_BOMLine_ID)
				.map(productBOMLineTable::getId)
				.ifPresent(productBOMLineId -> softly.assertThat(ProductBOMLineId.ofRepoId(actual.getPP_Product_BOMLine_ID())).as("PP_Product_BOMLine_ID").isEqualTo(productBOMLineId));

		expected.getAsOptionalIdentifier(I_PP_OrderLine_Candidate.COLUMNNAME_M_AttributeSetInstance_ID)
				.map(attributeSetInstanceTable::getId)
				.ifPresent(expectedASIId -> {
					final AttributesKey expectedASIKey = AttributesKeys
							.createAttributesKeyFromASIStorageAttributes(expectedASIId)
							.orElse(AttributesKey.NONE);

					final AttributesKey ppOrderLineCandAttributesKeys = AttributesKeys
							.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoIdOrNone(actual.getM_AttributeSetInstance_ID()))
							.orElse(AttributesKey.NONE);

					softly.assertThat(ppOrderLineCandAttributesKeys).as("AttributeKeys").isEqualTo(expectedASIKey);
				});

		softly.assertAll();
	}

}