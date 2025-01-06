package de.metas.order;

import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchPO;

import java.math.BigDecimal;
import java.sql.Timestamp;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

public interface IMatchPOBL extends ISingletonService
{
	/**
	 * Retrieves or creates a MMatchPO instance. An existing instance is retrieved if it references the same <code>C_OrderLine_ID</code> as either the given <code>iLine</code> or <code>sLine</code>.
	 * The iLine takes precendence.
	 *
	 * @param iLine invoice line optional; but not that one of this an <code>sLine</code> are required
	 * @param receiptLine receipt line optional; but not that one of this an <code>iLine</code> are required
	 * @param dateTrx date used only if a new MMatchPO is created
	 * @param qty qty filter parameter: an existing record that references the <code>C_OrderLine_ID</code> of the given <code>iLine</code> or <code>sLine</code> will be ignored, if it's qty differs
	 *            from this parameter value
	 * @return Match Record; the record is not saved!
	 */
	I_M_MatchPO create(I_C_InvoiceLine iLine, I_M_InOutLine receiptLine, Timestamp dateTrx, BigDecimal qty);

	void unlink(@NonNull OrderLineId orderLineId, @NonNull InvoiceAndLineId invoiceAndLineId);

	void unlink(@NonNull InOutId inoutId);

	void unlink(@NonNull InvoiceId invoiceId);
}
