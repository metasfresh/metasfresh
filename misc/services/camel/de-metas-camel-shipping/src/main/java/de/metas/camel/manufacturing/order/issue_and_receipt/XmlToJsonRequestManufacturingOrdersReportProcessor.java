package de.metas.camel.manufacturing.order.issue_and_receipt;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.ImmutableList;

import de.metas.camel.manufacturing.order.export.MainProductOrComponent;
import de.metas.camel.shipping.RouteBuilderCommonUtil;
import de.metas.common.filemaker.FMPXMLRESULT;
import de.metas.common.filemaker.METADATA;
import de.metas.common.filemaker.ROW;
import de.metas.common.manufacturing.JsonRequestHULookup;
import de.metas.common.manufacturing.JsonRequestIssueToManufacturingOrder;
import de.metas.common.manufacturing.JsonRequestManufacturingOrdersReport;
import de.metas.common.manufacturing.JsonRequestReceiveFromManufacturingOrder;
import de.metas.common.util.time.SystemTime;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * de-metas-camel-shipping
 * %%
 * Copyright (C) 2020 metas GmbH
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

class XmlToJsonRequestManufacturingOrdersReportProcessor implements Processor
{
	private final static Log log = LogFactory.getLog(XmlToJsonRequestManufacturingOrdersReportProcessor.class);

	@Override
	public void process(final Exchange exchange)
	{
		final RunningContext runningContext = new RunningContext();

		processExchange(exchange,
				(rawRow, metadata) -> extractJsonIssueOrReceipt(rawRow, metadata, runningContext),
				this::toJsonRequestManufacturingOrdersReport);
	}

	private <T, R> void processExchange(
			final Exchange exchange,
			final BiFunction<ROW, METADATA, T> itemBuilder,
			final Function<List<T>, R> requestBuilder)
	{
		final FMPXMLRESULT fmpxmlresult = exchange.getIn().getBody(FMPXMLRESULT.class);

		if (fmpxmlresult == null || fmpxmlresult.isEmpty())
		{
			log.debug("exchange.body is empty! -> nothing to do!");
			exchange.getIn().setHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS, 0);
			return; // nothing to do
		}

		final METADATA metadata = fmpxmlresult.getMetadata();

		final List<T> items = fmpxmlresult
				.getResultset()
				.getRows()
				.stream()
				.map(row -> itemBuilder.apply(row, metadata))
				.collect(ImmutableList.toImmutableList());

		final R request = requestBuilder.apply(items);
		System.out.println("REQUEST: " + request);

		exchange.getIn().setHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS, items.size());
		exchange.getIn().setBody(request);
	}

	@NonNull
	private IssueOrReceipt extractJsonIssueOrReceipt(
			@NonNull final ROW rawRow,
			@NonNull final METADATA metadata,
			@NonNull final RunningContext runningContext)
	{
		try
		{
			final IssueOrReceiptXmlRow row = IssueOrReceiptXmlRow.wrap(rawRow, metadata);
			final MainProductOrComponent type = row.get_stueckliste_mutter_oder_tochter();

			if (type == MainProductOrComponent.MAIN_PRODUCT)
			{
				final JsonRequestReceiveFromManufacturingOrder receipt = extractJsonReceipt(row, runningContext);
				return new IssueOrReceipt(receipt);
			}
			else if (type == MainProductOrComponent.COMPONENT)
			{
				final JsonRequestIssueToManufacturingOrder issue = extractJsonIssue(row, runningContext);
				return new IssueOrReceipt(issue);
			}
			else
			{
				throw new IllegalStateException("Unknown type: " + type);
			}
		}
		catch (final Exception ex)
		{
			throw new RuntimeException("Failed parsing " + rawRow);
		}
	}

	@NonNull
	private static JsonRequestReceiveFromManufacturingOrder extractJsonReceipt(
			@NonNull final IssueOrReceiptXmlRow row,
			@NonNull final RunningContext runningContext)
	{
		return JsonRequestReceiveFromManufacturingOrder.builder()
				.requestId(runningContext.nextRequestId())
				.orderId(row.get_stueckliste_id())
				.qtyToReceiveInStockUOM(row.get_artikel_menge())
				.date(SystemTime.asZonedDateTime())
				.lotNumber(row.get_vorkonfektioniertist_mhd_charge())
				.bestBeforeDate(row.get_vorkonfektioniertist_mhd_ablauf_datum())
				.build();
	}

	@NonNull
	private static JsonRequestIssueToManufacturingOrder extractJsonIssue(
			@NonNull final IssueOrReceiptXmlRow row,
			@NonNull final RunningContext runningContext)
	{
		return JsonRequestIssueToManufacturingOrder.builder()
				.requestId(runningContext.nextRequestId())
				.orderId(row.get_stueckliste_id())
				.qtyToIssueInStockUOM(row.get_artikel_menge())
				.date(SystemTime.asZonedDateTime())
				.productNo(row.get_artikel_nummer())
				.handlingUnit(JsonRequestHULookup.builder()
						.lotNumber(row.get_vorkonfektioniertist_mhd_charge())
						.bestBeforeDate(row.get_vorkonfektioniertist_mhd_ablauf_datum())
						.build())
				.build();
	}

	private JsonRequestManufacturingOrdersReport toJsonRequestManufacturingOrdersReport(@NonNull final List<IssueOrReceipt> issueAndReceipts)
	{
		final ImmutableList<JsonRequestIssueToManufacturingOrder> issues = issueAndReceipts.stream()
				.map(IssueOrReceipt::getIssue)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		final ImmutableList<JsonRequestReceiveFromManufacturingOrder> receipts = issueAndReceipts.stream()
				.map(IssueOrReceipt::getReceipt)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		return JsonRequestManufacturingOrdersReport.builder()
				.issues(issues)
				.receipts(receipts)
				.build();
	}

	@ToString
	private static class RunningContext
	{
		private int nextRequestId = 1;
		private int nextIssueId = 1;

		public String nextRequestId()
		{
			final String requestId = String.valueOf(nextRequestId);
			nextRequestId++;
			return requestId;
		}
	}

	@Value
	private static class IssueOrReceipt
	{
		JsonRequestReceiveFromManufacturingOrder receipt;
		JsonRequestIssueToManufacturingOrder issue;

		IssueOrReceipt(@NonNull final JsonRequestReceiveFromManufacturingOrder receipt)
		{
			this.receipt = receipt;
			this.issue = null;
		}

		IssueOrReceipt(@NonNull final JsonRequestIssueToManufacturingOrder issue)
		{
			this.receipt = null;
			this.issue = issue;
		}
	}
}
