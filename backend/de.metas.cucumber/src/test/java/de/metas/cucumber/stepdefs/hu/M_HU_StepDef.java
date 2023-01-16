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

package de.metas.cucumber.stepdefs.hu;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.EmptyUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.inventory.M_InventoryLine_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inout.returns.ReturnsServiceFacade;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.inventory.InventoryLineId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InventoryLine;

import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_HUStatus;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_M_HU_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class M_HU_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ReturnsServiceFacade returnsServiceFacade = SpringContextHolder.instance.getBean(ReturnsServiceFacade.class);

	private final M_InventoryLine_StepDefData inventoryLineTable;
	private final M_HU_StepDefData huTable;

	public M_HU_StepDef(
			@NonNull final M_InventoryLine_StepDefData inventoryLineTable,
			@NonNull final M_HU_StepDefData huTable)
	{
		this.inventoryLineTable = inventoryLineTable;
		this.huTable = huTable;
	}

	@And("^after not more than (.*)s, there are added M_HUs for inventory$")
	public void find_HUs(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			final String inventoryLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_InventoryLine.COLUMNNAME_M_InventoryLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			final int inventoryLineId = inventoryLineTable.get(inventoryLineIdentifier).getM_InventoryLine_ID();

			final String huIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);

			final InventoryLineId inventoryLineWithHUId = InventoryLineId.ofRepoId(inventoryLineId);

			final de.metas.handlingunits.model.I_M_InventoryLine inventoryLineWithHU = load(inventoryLineWithHUId, de.metas.handlingunits.model.I_M_InventoryLine.class);

			assertThat(inventoryLineWithHU).isNotNull();

			final HuId huId = HuId.ofRepoId(inventoryLineWithHU.getM_HU_ID());

			StepDefUtil.tryAndWait(timeoutSec, 500, () -> loadHU(LoadHURequest.builder()
																		 .huId(huId)
																		 .huIdentifier(huIdentifier)
																		 .build()));
		}
	}
	
	@And("return hu from customer")
	public void return_HU_from_customer(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			returnHUFromCustomer(tableRow);
		}
	}
	
	private void returnHUFromCustomer(@NonNull final Map<String, String> tableRow)
	{
		final String huIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Picking_Candidate.COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_HU hu = huTable.get(huIdentifier);
		assertThat(hu).isNotNull();

		returnsServiceFacade.createCustomerReturnInOutForHUs(ImmutableList.of(hu));
	}
	
	@NonNull
	private Boolean loadHU(@NonNull final LoadHURequest request)
	{
		final IQueryBuilder<I_M_HU> queryBuilder = queryBL.createQueryBuilder(I_M_HU.class)
				.addEqualsFilter(COLUMNNAME_M_HU_ID, request.getHuId());

		if (EmptyUtil.isNotBlank(request.getHuStatus()))
		{
			queryBuilder.addEqualsFilter(COLUMNNAME_HUStatus, request.getHuStatus());
		}

		if (request.getPiItemProductId() != null)
		{
			queryBuilder.addEqualsFilter(COLUMNNAME_M_HU_PI_Item_Product_ID, request.getPiItemProductId());
		}

		final Optional<I_M_HU> hu = queryBuilder.create().firstOnlyOptional(I_M_HU.class);

		if (!hu.isPresent())
		{
			return false;
		}

		huTable.putOrReplace(request.getHuIdentifier(), hu.get());

		return true;
	}
}
