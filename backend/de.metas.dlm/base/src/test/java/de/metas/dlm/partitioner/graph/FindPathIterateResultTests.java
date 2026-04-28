package de.metas.dlm.partitioner.graph;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.logging.LogManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * metasfresh-dlm
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class FindPathIterateResultTests
{
	@BeforeEach
	public void before()
	{
		AdempiereTestHelper.get().init();
		LogManager.setLevel(Level.DEBUG);
	}

	/**
	 * Creates order, invoice and payment, and invokes {@link FindPathIterateResult} to find a poath from payment to order.<br>
	 * Note that this is kindow easy because the path followed the actual refernces (p -> i -> o).
	 *
	 */
	@Test
	public void testFindPath()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		InterfaceWrapperHelper.save(order);

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setC_Order_ID(order.getC_Order_ID());
		InterfaceWrapperHelper.save(invoice);

		final I_C_Payment payment = InterfaceWrapperHelper.newInstance(I_C_Payment.class);
		payment.setC_Invoice(invoice);
		InterfaceWrapperHelper.save(payment);

		final TableRecordReference orderRef = TableRecordReference.of(order);
		final TableRecordReference invoiceRef = TableRecordReference.of(invoice);
		final TableRecordReference paymentRef = TableRecordReference.of(payment);

		// note that we don't add the *same* instances. Just equal ones
		final FindPathIterateResult result = new FindPathIterateResult(TableRecordReference.of(payment), TableRecordReference.of(order));

		result.addReferencedRecord(invoiceRef, orderRef, -1);
		result.addReferencedRecord(paymentRef, invoiceRef, -1);

		// so we can now go on with 'initialResult' and spare ourselves one cast ;-)
		final List<ITableRecordReference> path = result.getPath();

		assertThat(path).isNotNull();
		assertThat(path).hasSize(3);
		assertThat(path.get(0)).isEqualTo(paymentRef);
		assertThat(path.get(1)).isEqualTo(invoiceRef);
		assertThat(path.get(2)).isEqualTo(orderRef);
	}

	/**
	 * Created order, invoice and payment, and invokes {@link FindPathIterateResult} to find a poath from payment to order.<br>
	 * Note that here, the path also needs to go against the direction of out references (p -> o <- i).
	 *
	 */
	@Test
	public void testFindPathUndirected()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		InterfaceWrapperHelper.save(order);

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		InterfaceWrapperHelper.save(invoice);

		final I_C_Payment payment = InterfaceWrapperHelper.newInstance(I_C_Payment.class);
		InterfaceWrapperHelper.save(payment);

		final TableRecordReference orderRef = TableRecordReference.of(order);
		final TableRecordReference invoiceRef = TableRecordReference.of(invoice);
		final TableRecordReference paymentRef = TableRecordReference.of(payment);

		// note that we don't add the *same* instances. Just equal ones
		final FindPathIterateResult result = new FindPathIterateResult(TableRecordReference.of(payment), TableRecordReference.of(invoice));

		result.addReferencedRecord(invoiceRef, orderRef, -1);
		result.addReferencedRecord(paymentRef, orderRef, -1);

		// so we can now go on with 'initialResult' and spare ourselves one cast ;-)
		final List<ITableRecordReference> path = result.getPath();

		assertThat(path).isNotNull();
		assertThat(path).hasSize(3);
		assertThat(path.get(0)).isEqualTo(paymentRef);
		assertThat(path.get(1)).isEqualTo(orderRef);
		assertThat(path.get(2)).isEqualTo(invoiceRef);
	}
}
