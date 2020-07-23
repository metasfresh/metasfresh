/*
 * #%L
<<<<<<< HEAD:misc/services/camel/de-metas-camel-shipping/src/main/java/de/metas/camel/shipping/MainApp.java
 * de-metas-camel-shipping
=======
 * de.metas.swat.base
>>>>>>> origin/blonde_monkey_uat:backend/de.metas.swat/de.metas.swat.base/src/main/java/de/metas/inoutcandidate/exportaudit/APIExportAuditRepository.java
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

<<<<<<< HEAD:misc/services/camel/de-metas-camel-shipping/src/main/java/de/metas/camel/shipping/MainApp.java
package de.metas.camel.shipping;

import de.metas.camel.shipping.receiptcandidate.ReceiptCandidateJsonToXmlRouteBuilder;
import de.metas.camel.shipping.shipmentcandidate.ShipmentCandidateJsonToXmlRouteBuilder;
import org.apache.camel.main.Main;

/**
 * A Camel Application
 */
public class MainApp {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {

        final Main main = new Main();
        main.configure().addRoutesBuilder(new ShipmentCandidateJsonToXmlRouteBuilder());
        main.configure().addRoutesBuilder(new ReceiptCandidateJsonToXmlRouteBuilder());
        main.run(args);
    }
=======
package de.metas.inoutcandidate.exportaudit;

import lombok.NonNull;

public interface APIExportAuditRepository<T extends APIExportAuditItem>
{
	APIExportAudit<T> getByTransactionId(@NonNull  String transactionId);
>>>>>>> origin/blonde_monkey_uat:backend/de.metas.swat/de.metas.swat.base/src/main/java/de/metas/inoutcandidate/exportaudit/APIExportAuditRepository.java

	void save(@NonNull APIExportAudit<T> audit);
}
