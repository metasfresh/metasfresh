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

package de.metas.cucumber.stepdefs.stock;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.event.model.I_AD_EventLog;
import de.metas.event.model.I_AD_EventLog_Entry;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

public class MD_Stock_StepDef
{
	private final static transient Logger logger = LogManager.getLogger(MD_Stock_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Product_StepDefData productTable;

	public MD_Stock_StepDef(final M_Product_StepDefData productTable)
	{
		this.productTable = productTable;
	}

	@And("after not more than {int} seconds metasfresh has MD_Stock data")
	public void verify_MD_Stock_Data( final int timeoutSeconds, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> rows = dataTable.asMaps();

		final Supplier<Boolean> supplier = () -> rows.stream().allMatch(this::waitForStock);

		StepDefUtil.tryAndWait(timeoutSeconds, 500, supplier);

		for (final Map<String, String> row : rows)
		{
			validateMD_Stock(row);
		}
	}

	private boolean waitForStock(@NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, "M_Product_ID.Identifier");
		final int productId = productTable.get(productIdentifier).getM_Product_ID();

		final BigDecimal qtyOnHand = DataTableUtil.extractBigDecimalForColumnName(row, "QtyOnHand");

		final I_MD_Stock mdStock = queryBL.createQueryBuilder(I_MD_Stock.class)
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Product_ID, productId)
				.create()
				.firstOnly(I_MD_Stock.class);
		return mdStock != null && mdStock.getQtyOnHand().compareTo(qtyOnHand) == 0;
	}

	private void validateMD_Stock(@NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, "M_Product_ID.Identifier");
		final BigDecimal qtyOnHand = DataTableUtil.extractBigDecimalForColumnName(row, "QtyOnHand");

		final I_M_Product product = productTable.get(productIdentifier);

		final I_MD_Stock mdStock = queryBL.createQueryBuilder(I_MD_Stock.class)
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.create()
				.firstOnly(I_MD_Stock.class);

		if (mdStock == null || mdStock.getQtyOnHand().compareTo(qtyOnHand) != 0)
		{
			logEventLogDiagnostics(product.getM_Product_ID(), qtyOnHand, mdStock);
		}

		assertThat(mdStock).isNotNull();
		assertThat(mdStock.getQtyOnHand()).isEqualTo(qtyOnHand);
	}

	@And("log AD_EventLog count for product {string}")
	public void logAD_EventLog_count(@NonNull final String productIdentifier)
	{
		final I_M_Product product = productTable.get(productIdentifier);
		final int productId = product.getM_Product_ID();
		final String productIdPattern = "\"productId\" : " + productId;

		final List<I_AD_EventLog> eventLogs = queryBL.createQueryBuilder(I_AD_EventLog.class)
				.addStringLikeFilter(I_AD_EventLog.COLUMNNAME_EventData, "%" + productIdPattern + "%", /* ignoreCase */ false)
				.orderBy(I_AD_EventLog.COLUMNNAME_Created)
				.create()
				.list();

		logger.info("=== AD_EventLog count for M_Product_ID={} ({}): {} entries ===",
				productId, product.getName(), eventLogs.size());
		for (final I_AD_EventLog eventLog : eventLogs)
		{
			logger.info("  AD_EventLog_ID={}, EventName={}, IsError={}, Created={}",
					eventLog.getAD_EventLog_ID(),
					eventLog.getEventName(),
					eventLog.isError(),
					eventLog.getCreated());
		}
	}

	private void logEventLogDiagnostics(final int productId, final BigDecimal expectedQty, final I_MD_Stock mdStock)
	{
		final BigDecimal actualQty = mdStock != null ? mdStock.getQtyOnHand() : null;
		logger.warn("=== MD_Stock MISMATCH for M_Product_ID={} ===", productId);
		logger.warn("  Expected QtyOnHand={}, Actual QtyOnHand={}", expectedQty, actualQty);

		// Query recent AD_EventLog entries whose EventData contains the product ID
		final String productIdPattern = "\"productId\" : " + productId;
		final List<I_AD_EventLog> eventLogs = queryBL.createQueryBuilder(I_AD_EventLog.class)
				.addStringLikeFilter(I_AD_EventLog.COLUMNNAME_EventData, "%" + productIdPattern + "%", /* ignoreCase */ false)
				.orderByDescending(I_AD_EventLog.COLUMNNAME_Created)
				.setLimit(10)
				.create()
				.list();

		if (eventLogs.isEmpty())
		{
			logger.warn("  No AD_EventLog entries found containing productId={}", productId);
			return;
		}

		logger.warn("  Found {} AD_EventLog entries for productId={}:", eventLogs.size(), productId);
		for (final I_AD_EventLog eventLog : eventLogs)
		{
			logger.warn("  --- AD_EventLog_ID={}, EventType={}, IsError={}, Created={} ---",
					eventLog.getAD_EventLog_ID(),
					eventLog.getEventTypeName(),
					eventLog.isError(),
					eventLog.getCreated());

			// Query entries (handler results) for this event log
			final List<I_AD_EventLog_Entry> entries = queryBL.createQueryBuilder(I_AD_EventLog_Entry.class)
					.addEqualsFilter(I_AD_EventLog_Entry.COLUMNNAME_AD_EventLog_ID, eventLog.getAD_EventLog_ID())
					.orderBy(I_AD_EventLog_Entry.COLUMNNAME_AD_EventLog_Entry_ID)
					.create()
					.list();

			for (final I_AD_EventLog_Entry entry : entries)
			{
				logger.warn("    Handler={}, IsError={}, MsgText={}",
						entry.getClassname(),
						entry.isError(),
						entry.getMsgText());
			}
		}
		logger.warn("=== END MD_Stock diagnostics ===");
	}
}
