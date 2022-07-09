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
import de.metas.cucumber.stepdefs.IdentifierIds_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.purchasecandidate.v2.CreatePurchaseCandidate_StepDef;
import de.metas.logging.LogManager;
import de.metas.order.OrderLineId;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.model.IQuery;
import org.slf4j.Logger;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.function.Supplier;

import static de.metas.bpartner.api.impl.BPRelationDAO.queryBL;
import static org.assertj.core.api.Assertions.*;

public class C_PurchaseCandidate_StepDef
{
	private final static Logger logger = LogManager.getLogger(CreatePurchaseCandidate_StepDef.class);

	private final IdentifierIds_StepDefData identifierIdsTable;
	private final C_PurchaseCandidate_StepDefData purchaseCandidateTable;
	private final C_OrderLine_StepDefData orderLineTable;

	public C_PurchaseCandidate_StepDef(
			@NonNull final IdentifierIds_StepDefData identifierIdsTable,
			@NonNull final C_PurchaseCandidate_StepDefData purchaseCandidateTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable)
	{
		this.identifierIdsTable = identifierIdsTable;
		this.purchaseCandidateTable = purchaseCandidateTable;
		this.orderLineTable = orderLineTable;
	}

	@And("^after not more than (.*)s, no C_PurchaseCandidate found for orderLine (.*)$")
	public void validate_no_C_PurchaseCandidate_found(
			final int timeoutSec,
			@NonNull final String orderLineIdentifier) throws InterruptedException
	{
		final OrderLineId orderLineId = getOrderLineIdByIdentifier(orderLineIdentifier);
		assertThat(orderLineId).isNotNull();

		final Supplier<Boolean> noRecordsFound = () -> getQueryByOrderLineId(orderLineId)
				.count() == 0;

		StepDefUtil.tryAndWait(timeoutSec, 500, noRecordsFound, () -> logCurrentContextExpectedNoRecords(orderLineId));
	}

	@And("^after not more than (.*)s, C_PurchaseCandidate found for orderLine (.*)$")
	public void validate_PP_Order_Candidate_found_for_OrderLine(
			final int timeoutSec,
			@NonNull final String orderLineIdentifier,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final OrderLineId orderLineId = getOrderLineIdByIdentifier(orderLineIdentifier);
		assertThat(orderLineId).isNotNull();

		final Supplier<Boolean> recordFound = () -> getQueryByOrderLineId(orderLineId)
				.first() != null;

		StepDefUtil.tryAndWait(timeoutSec, 500, recordFound);

		final I_C_PurchaseCandidate purchaseCandidateRecord = getQueryByOrderLineId(orderLineId)
				.firstOnlyNotNull(I_C_PurchaseCandidate.class);

		final String purchaseCandidateIdentifier = DataTableUtil.extractStringForColumnName(tableRow, StepDefConstants.TABLECOLUMN_IDENTIFIER);
		purchaseCandidateTable.putOrReplace(purchaseCandidateIdentifier, purchaseCandidateRecord);
	}

	@NonNull
	private IQuery<I_C_PurchaseCandidate> getQueryByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return queryBL.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_C_OrderLineSO_ID, orderLineId)
				.create();
	}

	private void logCurrentContextExpectedNoRecords(@NonNull final OrderLineId orderLineId)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Validating no records found for orderLineID :").append("\n")
				.append(I_C_PurchaseCandidate.COLUMNNAME_C_OrderLineSO_ID).append(" : ").append(orderLineId).append("\n");

		message.append("C_PurchaseCandidate records:").append("\n");

		getQueryByOrderLineId(orderLineId)
				.stream(I_C_PurchaseCandidate.class)
				.forEach(purchaseCandidate -> message
						.append(I_C_PurchaseCandidate.COLUMNNAME_C_PurchaseCandidate_ID).append(" : ").append(purchaseCandidate.getC_PurchaseCandidate_ID()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while validating no C_PurchaseCandidate found for orderLineId: " + orderLineId + ", see found records: \n" + message);
	}

	@Nullable
	private OrderLineId getOrderLineIdByIdentifier(@NonNull final String orderLineIdentifier)
	{
		return OrderLineId.ofRepoIdOrNull(identifierIdsTable.getOptional(orderLineIdentifier)
												  .orElseGet(() -> orderLineTable.get(orderLineIdentifier).getC_OrderLine_ID()));
	}
}
