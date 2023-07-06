/*
 * #%L
 * de.metas.swat.base
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

<<<<<<<< HEAD:backend/de.metas.swat/de.metas.swat.base/src/main/java/de/metas/order/invoicecandidate/OrderLineHandlerExtension.java
package de.metas.order.invoicecandidate;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.OrderLineId;
import lombok.NonNull;

public interface OrderLineHandlerExtension
========
package de.metas.inoutcandidate.document.dimension;

import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;

public class ReceiptScheduleDimensionFactoryTestWrapper extends ReceiptScheduleDimensionFactory
>>>>>>>> f6fa6a4208c (attempt to fix unit tests (#15816)):backend/de.metas.swat/de.metas.swat.base/src/main/java/de/metas/inoutcandidate/document/dimension/ReceiptScheduleDimensionFactoryTestWrapper.java
{
	void setDeliveryRelatedData(@NonNull OrderLineId orderLineId,@NonNull I_C_Invoice_Candidate invoiceCandidate);
}
