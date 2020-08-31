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

package de.metas.camel.shipping;

import de.metas.camel.shipping.receipt.ReceiptXmlToJsonRouteBuilder;
import de.metas.camel.shipping.receiptcandidate.ReceiptCandidateJsonToXmlRouteBuilder;
import de.metas.camel.shipping.shipment.ShipmentXmlToJsonRouteBuilder;
import de.metas.camel.shipping.shipmentcandidate.ShipmentCandidateJsonToXmlRouteBuilder;
import org.apache.camel.main.Main;

/**
 * A Camel Application
 */
public class MainApp
{

	/**
	 * A main() so we can easily run these routing rules in our IDE
	 */
	public static void main(String... args) throws Exception
	{
		final Main main = new Main();
		main.configure().addRoutesBuilder(new ShipmentCandidateJsonToXmlRouteBuilder());
		main.configure().addRoutesBuilder(new ReceiptCandidateJsonToXmlRouteBuilder());
		main.configure().addRoutesBuilder(new ShipmentXmlToJsonRouteBuilder());
		main.configure().addRoutesBuilder(new ReceiptXmlToJsonRouteBuilder());
		main.run(args);
	}

}

