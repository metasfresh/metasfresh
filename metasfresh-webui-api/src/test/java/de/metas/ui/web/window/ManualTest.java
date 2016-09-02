package de.metas.ui.web.window;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.adempiere.util.collections.ListUtils;
import org.junit.Ignore;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent.JSONOperation;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Ignore
public class ManualTest
{
	public static void main(final String[] args) throws Exception
	{
		LogManager.setLoggerLevel(org.slf4j.Logger.ROOT_LOGGER_NAME, Level.INFO);
		// LogManager.dumpAllLevelsUpToRoot("org.apache.http.impl.conn.PoolingHttpClientConnectionManager");

		do
		{
			try
			{
				final ManualTest tester = new ManualTest();
				tester.test1();
			}
			catch (final Exception e)
			{
				e.printStackTrace();
				System.err.flush();
				System.out.flush();
			}

			System.out.println("Press any key to repeat...");
			System.out.flush();
			System.in.read();
		}
		while (true);
	}

	private void test1()
	{
		final boolean advanced = false;

		//
		//
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Get Order layout");
		{
			restClient.layout(143, null, advanced);
		}

		//
		//
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Get Order line layout");
		{
			restClient.layout(143, "1", advanced);
		}

		//
		//
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Create Order Header");
		String orderId = "NEW";
		{
			final List<JSONDocumentChangedEvent> events = Arrays.asList(
					JSONDocumentChangedEvent.of(JSONOperation.replace, "C_BPartner_ID", JSONLookupValue.of("2156425", "test")) //
					, JSONDocumentChangedEvent.of(JSONOperation.replace, "M_Shipper_ID", JSONLookupValue.of("1000000", "test")) //
			);

			final List<JSONDocument> response = restClient.commit(143, orderId, events);
			orderId = getId(response, orderId);
			System.out.println("=> orderId=" + orderId);
		}

		//
		//
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Update Order Header: POReference");
		{
			final List<JSONDocumentChangedEvent> events = Arrays.asList(
					JSONDocumentChangedEvent.of(JSONOperation.replace, "POReference", "111") //
			);

			final List<JSONDocument> response = restClient.commit(143, orderId, events);
			orderId = getId(response, orderId);
			System.out.println("=> orderId=" + orderId);
		}

		System.out.println("---------------------------------------------------------------------");
		System.out.println("Get Order Header data");
		{
			final String detailId = null;
			final String rowIdStr = null;
			final String fieldsListStr = null;
			restClient.data(143, orderId, detailId, rowIdStr, fieldsListStr, advanced);
		}

		//
		//
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Create Order Line");
		String rowId = "NEW";
		{
			final List<JSONDocumentChangedEvent> events = Arrays.asList(
					JSONDocumentChangedEvent.of(JSONOperation.replace, "M_Product_ID", JSONLookupValue.of("2005577", "Convenience Salat 250g")) //
					, JSONDocumentChangedEvent.of(JSONOperation.replace, "M_AttributeSetInstance_ID", JSONLookupValue.of("0", "NEW")) // needed because some callouts are setting it to NULL
			);

			final List<JSONDocument> response = restClient.commit(143, orderId, "1", rowId, advanced, events);
			rowId = getRowId(response, rowId);
			System.out.println("=> rowId=" + rowId);
		}

		//
		//
		for (int i = 1; i <= 2; i++)
		{
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Update order line's quantity (" + i + ")");
			final List<JSONDocumentChangedEvent> events = Arrays.asList(
					JSONDocumentChangedEvent.of(JSONOperation.replace, "QtyEntered", BigDecimal.valueOf(100)) //
			);

			final List<JSONDocument> response = restClient.commit(143, orderId, "1", rowId, advanced, events);
			rowId = getRowId(response, rowId);
			System.out.println("=> rowId=" + rowId);
		}

		//
		//
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Test C_Order.C_BPartner_ID lookup");
		{
			restClient.dropdown(143, orderId, "C_BPartner_ID");
		}

		//
		//
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Test C_Order.DocStatus values");
		{
			restClient.dropdown(143, orderId, "DocStatus");
		}

		//
		//
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Test C_Order.DocAction values");
		{
			restClient.dropdown(143, orderId, "DocAction");
		}
	}

	//
	//
	//

	private final WindowRestControllerClient restClient;

	public ManualTest()
	{
		super();
		restClient = new WindowRestControllerClient("localhost", 8080);
	}

	private static String getId(final List<JSONDocument> jsonDocuments, final String defaultValue)
	{
		final JSONDocument jsonDocument = ListUtils.singleElement(jsonDocuments);
		return jsonDocument.getId();
	}

	private static String getRowId(final List<JSONDocument> jsonDocuments, final String defaultValue)
	{
		if (jsonDocuments.isEmpty())
		{
			return defaultValue;
		}
		final JSONDocument jsonDocument = ListUtils.singleElement(jsonDocuments);
		return jsonDocument.getRowId();
	}

}
