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

package de.metas.cucumber.stepdefs.ddorder;

import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.IdentifierIds_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.logging.LogManager;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.IQuery;
import org.eevolution.model.I_DD_OrderLine;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

public class DD_OrderLine_StepDef
{
	private final static Logger logger = LogManager.getLogger(DD_OrderLine_StepDef.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final IdentifierIds_StepDefData identifierIdsTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final DD_OrderLine_StepDefData ddOrderTable;

	public DD_OrderLine_StepDef(
			@NonNull final IdentifierIds_StepDefData identifierIdsTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final DD_OrderLine_StepDefData ddOrderTable)
	{
		this.identifierIdsTable = identifierIdsTable;
		this.orderLineTable = orderLineTable;
		this.ddOrderTable = ddOrderTable;
	}

	@And("^no DD_OrderLine found for orderLine (.*)$")
	public void validate_no_DD_OrderLine_found(@NonNull final String orderLineIdentifier)
	{
		final OrderLineId orderLineId = getOrderLineIdByIdentifier(orderLineIdentifier);
		assertThat(orderLineId).isNotNull();

		try
		{
			assertThat(getQueryByOrderLineId(orderLineId).count() == 0).isTrue();
		}
		catch (final Throwable throwable)
		{
			logCurrentContextExpectedNoRecords(orderLineId);
		}
	}

	@And("^after not more than (.*)s, DD_OrderLine found for orderLine (.*)$")
	public void validate_DD_OrderLine_found_for_OrderLine(
			final int timeoutSec,
			@NonNull final String orderLineIdentifier,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final OrderLineId orderLineId = getOrderLineIdByIdentifier(orderLineIdentifier);
		assertThat(orderLineId).isNotNull();

		final Supplier<Optional<I_DD_OrderLine>> locateDDOrderLine = () -> Optional.ofNullable(getQueryByOrderLineId(orderLineId)
				.firstOnly(I_DD_OrderLine.class));

		final I_DD_OrderLine ddOrderLineRecord = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, locateDDOrderLine);

		final String ddOrderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, StepDefConstants.TABLECOLUMN_IDENTIFIER);
		ddOrderTable.putOrReplace(ddOrderLineIdentifier, ddOrderLineRecord);
	}

	private void logCurrentContextExpectedNoRecords(@NonNull final OrderLineId orderLineId)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Validating no records found for orderLineID :").append("\n")
				.append(I_DD_OrderLine.COLUMNNAME_C_OrderLineSO_ID).append(" : ").append(orderLineId).append("\n");

		message.append("DD_OrderLine records:").append("\n");

		getQueryByOrderLineId(orderLineId)
				.stream(I_DD_OrderLine.class)
				.forEach(ddOrderLine -> message
						.append(I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID).append(" : ").append(ddOrderLine.getDD_OrderLine_ID()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while validating no DD_OrderLine found for orderLineId: " + orderLineId + ", see found records: \n" + message);
	}

	@NonNull
	private IQuery<I_DD_OrderLine> getQueryByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addEqualsFilter(I_DD_OrderLine.COLUMN_C_OrderLineSO_ID, orderLineId)
				.create();
	}

	@Nullable
	private OrderLineId getOrderLineIdByIdentifier(@NonNull final String orderLineIdentifier)
	{
		return OrderLineId.ofRepoIdOrNull(identifierIdsTable.getOptional(orderLineIdentifier)
												  .orElseGet(() -> orderLineTable.get(orderLineIdentifier).getC_OrderLine_ID()));
	}
}