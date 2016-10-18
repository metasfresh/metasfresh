package de.metas.dlm.partitioner.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.junit.Before;
import org.junit.Test;

import ch.qos.logback.classic.Level;
import de.metas.dlm.Partition;
import de.metas.dlm.exception.DLMException;
import de.metas.dlm.migrator.IMigratorService;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.partitioner.config.PartitionerConfig;
import de.metas.logging.LogManager;

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

public class PartitionerServiceTests
{
	final PartitionerService partitionerService = new PartitionerService();

	@Before
	public void before()
	{
		AdempiereTestHelper.get().init();
		LogManager.setLevel(Level.DEBUG);
	}

	/**
	 * Simple test, just to exercise the service once.
	 */
	@Test
	public void testEmptyConfig()
	{
		final PartitionerConfig config = PartitionerConfig.builder().build();

		partitionerService.createPartition(config);
	}

	/**
	 * Simple test for the case that there is not a single record that needs to be partitioned.
	 */
	@Test
	public void testNoRecordForPartioning()
	{
		final PartitionerConfig config = PartitionerConfig.builder()
				.newLine().setTableName(I_C_Payment.Table_Name).endLine()
				.build();
		final Partition partition = partitionerService.createPartition(config);

		assertNotNull(partition);
		assertThat(partition.getConfig(), is(config));

		assertNotNull(partition.getRecords());
		assertThat(partition.getRecords().isEmpty(), is(true));
	}

	/**
	 * Simple test for the case that there is not a single record that needs to be partitioned.
	 */
	@Test
	public void testOneRecordForPartioning()
	{
		final PartitionerConfig config = PartitionerConfig.builder()
				.newLine().setTableName(I_C_Payment.Table_Name).endLine()
				.build();

		final I_C_Payment payment = InterfaceWrapperHelper.newInstance(I_C_Payment.class);
		InterfaceWrapperHelper.save(payment);

		final Partition partition = partitionerService.createPartition(config);

		assertNotNull(partition);
		assertThat(partition.getConfig(), is(config));

		assertNotNull(partition.getRecords());
		assertThat(partition.getRecords().size(), is(1));
		assertThat(partition.getRecords().get(0), is(InterfaceWrapperHelper.create(payment, IDLMAware.class)));
	}

	/**
	 * Verifies that the partitioned follows a references. The referenced table <code>C_Order</code> also has its own partition config line.
	 */
	@Test
	public void testReference1()
	{
		final PartitionerConfig config = PartitionerConfig.builder()
				.newLine().setTableName(I_C_Invoice.Table_Name)
				.newRef().setReferencedTableName(I_C_Order.Table_Name).setReferencingColumnName(I_C_Invoice.COLUMNNAME_C_Order_ID).setReferencedConfigLine(I_C_Order.Table_Name).endRef()
				.newLine().setTableName(I_C_Order.Table_Name).endLine()
				.build();

		testWithOrderAndInvoice(config);
	}

	/**
	 * Verifies that the partitioned follows a references. The referenced table <code>C_Order</code> does not have its own partition config line.
	 */
	@Test
	public void testReference2()
	{
		final PartitionerConfig config = PartitionerConfig.builder()
				.newLine().setTableName(I_C_Invoice.Table_Name)
				.newRef().setReferencedTableName(I_C_Order.Table_Name).setReferencingColumnName(I_C_Invoice.COLUMNNAME_C_Order_ID).endRef()
				.endLine()
				.build();

		testWithOrderAndInvoice(config);
	}

	/**
	 * Tests the "self-extending" of {@link PartitionerConfig}'s from DLMExceptions.
	 */
	@Test
	public void testDLMException()
	{
		//
		// create a config that only covers the C_Order table.
		final PartitionerConfig config = PartitionerConfig.builder()
				.newLine().setTableName(I_C_Order.Table_Name).endLine()
				.build();

		final Partition testWithOrderAndInvoice = testWithOrderAndInvoice(config);
	}

	@Test(timeout = 10000)
	public void testCircularReferences()
	{
		final PartitionerConfig config = PartitionerConfig.builder()

		// order -> payment
				.newLine().setTableName(I_C_Order.Table_Name)
				.newRef().setReferencingColumnName(I_C_Order.COLUMNNAME_C_Payment_ID).setReferencedTableName(I_C_Payment.Table_Name).setReferencedConfigLine(I_C_Payment.Table_Name).endRef()

		// payment -> invoice
				.newLine().setTableName(I_C_Payment.Table_Name)
				.newRef().setReferencingColumnName(I_C_Payment.COLUMNNAME_C_Invoice_ID).setReferencedTableName(I_C_Invoice.Table_Name).setReferencedConfigLine(I_C_Invoice.Table_Name).endRef()

		// invoice -> order
				.newLine().setTableName(I_C_Invoice.Table_Name)
				.newRef().setReferencingColumnName(I_C_Invoice.COLUMNNAME_C_Order_ID).setReferencedTableName(I_C_Order.Table_Name).setReferencedConfigLine(I_C_Order.Table_Name).endRef()

		.endLine().build();

		//
		// create an order *and* an invoice that references the order
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		POJOWrapper.setInstanceName(order, "order");
		InterfaceWrapperHelper.save(order);

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		POJOWrapper.setInstanceName(invoice, "invoice");
		invoice.setC_Order(order);
		InterfaceWrapperHelper.save(invoice);

		final I_C_Payment payment = InterfaceWrapperHelper.newInstance(I_C_Payment.class);
		POJOWrapper.setInstanceName(payment, "payment");
		payment.setC_Invoice(invoice);
		InterfaceWrapperHelper.save(payment);

		order.setC_Payment(payment);
		InterfaceWrapperHelper.save(order);

		setupMigratorServiceMock();

		//
		// invoke the testee
		// the first test is whether the method detects the circle finishes within the timeout
		final Partition partition = partitionerService.createPartition(config);

		//
		// verify
		assertNotNull(partition);
		assertThat(partition.getConfig(), is(config));

		assertThat(partition.getConfig().getLines().size(), is(3)); // the config has no more or less lines than it had before

		assertNotNull(partition.getRecords());
		assertThat(partition.getRecords().size(), is(3));
		assertThat(partition.getRecords().contains(order), is(true));
		assertThat(partition.getRecords().contains(invoice), is(true));
		assertThat(partition.getRecords().contains(payment), is(true));
	}

	/**
	 * Tests invoice-creditmemo
	 */
	@Test(timeout = 10000)
	public void testCircularReferences_within_same_table()
	{
		testCircularReferences_within_same_table0();
	}

	/**
	 * Verify that a record won't be added to another partition after if was processed via {@link PartitionerService#createPartition(PartitionerConfig)}.
	 */
	@Test
	public void testPartition_stored()
	{
		final Partition partition = testCircularReferences_within_same_table0();
		partitionerService.storePartition(partition);

		final Partition secondPartition = partitionerService.createPartition(partition.getConfig());

		assertThat(secondPartition.getRecords().isEmpty(), is(true)); // we create add additional records, the partitioner shall *not* return the already partitioned ones.
	}

	private Partition testCircularReferences_within_same_table0()
	{
		final PartitionerConfig config = PartitionerConfig.builder()

		// invoice -> credit-memo
				.newLine().setTableName(I_C_Invoice.Table_Name)
				.newRef().setReferencingColumnName(I_C_Invoice.COLUMNNAME_Ref_CreditMemo_ID).setReferencedTableName(I_C_Invoice.Table_Name)
				.newRef().setReferencingColumnName(I_C_Invoice.COLUMNNAME_Ref_Invoice_ID).setReferencedTableName(I_C_Invoice.Table_Name)
				.endRef()
				.endLine().build();

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		POJOWrapper.setInstanceName(invoice, "invoice");
		InterfaceWrapperHelper.save(invoice);

		final I_C_Invoice creditmemo = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		POJOWrapper.setInstanceName(invoice, "creditmemo");
		InterfaceWrapperHelper.save(creditmemo);

		invoice.setRef_CreditMemo(creditmemo);
		InterfaceWrapperHelper.save(invoice);

		creditmemo.setRef_Invoice(invoice);
		InterfaceWrapperHelper.save(creditmemo);

		setupMigratorServiceMock();

		//
		// invoke the testee
		final Partition partition = partitionerService.createPartition(config);

		//
		// verify
		assertNotNull(partition);
		assertThat(partition.getConfig(), is(config));

		assertThat(partition.getConfig().getLines().size(), is(1)); // the config has no more or less lines than it had before
		assertThat(partition.getConfig().getLines().get(0).getReferences().size(), is(2)); // the single config line has no more or less references than it had before.

		assertNotNull(partition.getRecords());
		assertThat(partition.getRecords().size(), is(2));
		assertThat(partition.getRecords().contains(invoice), is(true));
		assertThat(partition.getRecords().contains(creditmemo), is(true));

		return partition;
	}

	private Partition testWithOrderAndInvoice(final PartitionerConfig config)
	{
		//
		// create an order *and* an invoice that references the order
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		InterfaceWrapperHelper.save(order);

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setC_Order(order);
		InterfaceWrapperHelper.save(invoice);

		setupMigratorServiceMock();

		//
		// invoke the testee
		final Partition partition = partitionerService.createPartition(config);

		//
		// verify
		assertNotNull(partition);
		assertThat(partition.getConfig(), is(config));

		assertNotNull(partition.getRecords());
		assertThat(partition.getRecords().size(), is(2));
		assertThat(partition.getRecords().contains(order), is(true));
		assertThat(partition.getRecords().contains(invoice), is(true));

		return partition;
	}

	/**
	 * Emulate the database, throw a DLMException if the invoice record is not contained
	 */
	private void setupMigratorServiceMock()
	{
		Services.registerService(IMigratorService.class, new IMigratorService()
		{
			@Override
			public void testMigratePartition(Partition partition)
			{
				final boolean partitionHasInvoice = partition.getRecords().stream().anyMatch(r -> I_C_Invoice.Table_Name.equals(InterfaceWrapperHelper.getModelTableName(r)));
				if (!partitionHasInvoice)
				{
					throw new DLMException(null, false);
				}
			}
		});
	}
}
