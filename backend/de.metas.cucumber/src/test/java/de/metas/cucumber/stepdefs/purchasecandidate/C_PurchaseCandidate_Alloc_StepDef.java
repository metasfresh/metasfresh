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

package de.metas.cucumber.stepdefs.purchasecandidate;

import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.logging.LogManager;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate_Alloc;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class C_PurchaseCandidate_Alloc_StepDef
{
	private final static Logger logger = LogManager.getLogger(C_PurchaseCandidate_Alloc_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_PurchaseCandidate_StepDefData purchaseCandidateTable;
	private final C_PurchaseCandidate_Alloc_StepDefData purchaseCandidateAllocTable;
	private final C_OrderLine_StepDefData orderLineTable;

	public C_PurchaseCandidate_Alloc_StepDef(
			@NonNull final C_PurchaseCandidate_StepDefData purchaseCandidateTable,
			@NonNull final C_PurchaseCandidate_Alloc_StepDefData purchaseCandidateAllocTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable)
	{
		this.purchaseCandidateTable = purchaseCandidateTable;
		this.purchaseCandidateAllocTable = purchaseCandidateAllocTable;
		this.orderLineTable = orderLineTable;
	}

	@And("^after not more than (.*)s, C_PurchaseCandidate_Alloc are found$")
	public void findC_PurchaseCandidate_Alloc(
			final int timeoutSec,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			findC_PurchaseCandidate_Alloc_ByCandidateId(timeoutSec, tableRow);
		}
	}

	@And("load C_OrderLines from C_PurchaseCandidate_Alloc")
	public void loadC_OrderLines(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			loadC_OrderLines(tableRow);
		}
	}

	private void loadC_OrderLines(@NonNull final Map<String, String> tableRow)
	{
		final String purchaseCandidateAllocIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_PurchaseCandidate_Alloc.COLUMNNAME_C_PurchaseCandidate_Alloc_ID + ".Identifier");
		final I_C_PurchaseCandidate_Alloc purchaseCandidateAllocRecord = purchaseCandidateAllocTable.get(purchaseCandidateAllocIdentifier);
		assertThat(purchaseCandidateAllocRecord).isNotNull();

		final I_C_OrderLine orderLineRecord = InterfaceWrapperHelper.load(purchaseCandidateAllocRecord.getC_OrderLinePO_ID(), I_C_OrderLine.class);
		assertThat(orderLineRecord).isNotNull();

		final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_PurchaseCandidate_Alloc.COLUMNNAME_C_OrderLinePO_ID + ".Identifier");
		orderLineTable.putOrReplace(orderLineIdentifier, orderLineRecord);
	}

	private void findC_PurchaseCandidate_Alloc_ByCandidateId(
			final int timeoutSec,
			final Map<String, String> tableRow) throws InterruptedException
	{
		final String purchaseCandidateIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_PurchaseCandidate.COLUMNNAME_C_PurchaseCandidate_ID + ".Identifier");

		final I_C_PurchaseCandidate purchaseCandidateRecord = purchaseCandidateTable.get(purchaseCandidateIdentifier);
		assertThat(purchaseCandidateRecord).isNotNull();

		final I_C_PurchaseCandidate_Alloc purchaseCandidateAllocRecord = getPurchaseCandidate_Alloc_Record_ByCandidateId(
				timeoutSec,
				PurchaseCandidateId.ofRepoId(purchaseCandidateRecord.getC_PurchaseCandidate_ID()));
		
		assertThat(purchaseCandidateAllocRecord).isNotNull();

		final String purchaseCandidateAllocIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_PurchaseCandidate_Alloc.COLUMNNAME_C_PurchaseCandidate_Alloc_ID + ".Identifier");
		purchaseCandidateAllocTable.putOrReplace(purchaseCandidateAllocIdentifier, purchaseCandidateAllocRecord);
	}

	@NonNull
	private I_C_PurchaseCandidate_Alloc getPurchaseCandidate_Alloc_Record_ByCandidateId(
			final int timeoutSec,
			@NonNull final PurchaseCandidateId purchaseCandidateId) throws InterruptedException
	{
		final Supplier<Optional<I_C_PurchaseCandidate_Alloc>> purchaseCandidateAllocIsFound = () -> queryBL.createQueryBuilder(I_C_PurchaseCandidate_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PurchaseCandidate_Alloc.COLUMNNAME_C_PurchaseCandidate_ID, purchaseCandidateId)
				.create()
				.firstOnlyOptional(I_C_PurchaseCandidate_Alloc.class);

		return StepDefUtil.tryAndWaitForItem(timeoutSec, 500, purchaseCandidateAllocIsFound);
	}
}
