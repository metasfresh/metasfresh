package de.metas.dlm.partitioner.impl;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_R_Request;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;

import ch.qos.logback.classic.Level;
import de.metas.dlm.Partition;
import de.metas.dlm.exception.DLMReferenceException;
import de.metas.dlm.migrator.IMigratorService;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_AD_Table;
import de.metas.dlm.partitioner.PartitionRequestFactory;
import de.metas.dlm.partitioner.PartitionRequestFactory.CreatePartitionRequest;
import de.metas.dlm.partitioner.config.PartitionerConfig;
import de.metas.dlm.partitioner.config.TableReferenceDescriptor;
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

/**
 * Different tests for {@link PartitionerServiceOld#createPartition0(PartitionerConfig)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PartitionerServiceCreatePartitionTests
{
	private final PartitionerService partitionerService = new PartitionerService();

	@Before
	public void before()
	{
		AdempiereTestHelper.get().init();
		LogManager.setLevel(Level.DEBUG);

		// create two AD_Elements required by the IDLMService implementation
		{
			final I_AD_Element elementDLMLevel = InterfaceWrapperHelper.newInstance(I_AD_Element.class);
			elementDLMLevel.setColumnName(IDLMAware.COLUMNNAME_DLM_Level);
			InterfaceWrapperHelper.save(elementDLMLevel);

			final I_AD_Element elementPartitionId = InterfaceWrapperHelper.newInstance(I_AD_Element.class);
			elementPartitionId.setColumnName(IDLMAware.COLUMNNAME_DLM_Partition_ID);
			InterfaceWrapperHelper.save(elementPartitionId);
		}
	}

	/**
	 * Simple test, just to exercise the service once.
	 */
	@Test
	public void testEmptyConfig()
	{
		final PartitionerConfig config = PartitionerConfig.builder().build();

		partitionerService.createPartition0(PartitionRequestFactory.builder().setConfig(config).build());
	}

	/**
	 * Simple test for the case that there is not a single record that needs to be partitioned.
	 */
	@Test
	public void testNoRecordForPartioning()
	{
		// we need to explicitly create the AD_Table, because we create no payment record. if we created one, the AD_Table would be created on the fly and under the hoods.
		final I_AD_Table table = InterfaceWrapperHelper.newInstance(I_AD_Table.class);
		table.setTableName(I_C_Payment.Table_Name);
		InterfaceWrapperHelper.save(table);

		final PartitionerConfig config = PartitionerConfig.builder()
				.line(I_C_Payment.Table_Name).endLine()
				.build();
		final List<Partition> partitions = partitionerService.createPartition0(PartitionRequestFactory.builder().setConfig(config).build());

		assertNotNull(partitions);
		assertThat(partitions.isEmpty(), is(true));
	}

	/**
	 * Simple test for the case that there is not a single record that needs to be partitioned.
	 */
	@Test
	public void testOneRecordForPartioning()
	{
		final PartitionerConfig config = PartitionerConfig.builder()
				.line(I_C_Payment.Table_Name).endLine()
				.build();

		final I_C_Payment payment = InterfaceWrapperHelper.newInstance(I_C_Payment.class);
		InterfaceWrapperHelper.save(payment);

		final List<Partition> partitions = partitionerService.createPartition0(PartitionRequestFactory.builder().setConfig(config).build());
		assertThat(partitions.size(), is(1)); // guard
		final Partition fullyLoadedPartition = partitionerService.loadWithAllRecords(partitions.get(0));

		assertNotNull(partitions);
		assertThat(partitions.size(), is(1));
		assertThat(fullyLoadedPartition.getConfig(), is(config));

		assertNotNull(fullyLoadedPartition.getRecordsFlat());
		assertThat(fullyLoadedPartition.getRecordsFlat().size(), is(1));
		assertThat(fullyLoadedPartition.getRecordsFlat().get(0), is(asTableRef(payment)));
	}

	/**
	 * Verifies that the partitioner follows a references. The referenced table <code>C_Order</code> also has its own partition config line.
	 */
	@Test
	public void testReference1()
	{
		final PartitionerConfig config = PartitionerConfig.builder()
				.line(I_C_Invoice.Table_Name)
				.ref().setReferencedTableName(I_C_Order.Table_Name).setReferencingColumnName(I_C_Invoice.COLUMNNAME_C_Order_ID).endRef()
				.line(I_C_Order.Table_Name).endLine()
				.build();

		final Partition partition = testWithOrderAndInvoice(config);
		assertThat(partition.getConfig(), is(config));
	}

	/**
	 * Verifies that the partitioned follows a references. The referenced table <code>C_Order</code> does not have its own partition config line.
	 */
	@Test
	public void testReference2()
	{
		final PartitionerConfig config = PartitionerConfig.builder()
				.line(I_C_Invoice.Table_Name)
				.ref().setReferencedTableName(I_C_Order.Table_Name).setReferencingColumnName(I_C_Invoice.COLUMNNAME_C_Order_ID).endRef()
				.endLine()
				.build();

		final Partition partition = testWithOrderAndInvoice(config);
		assertThat(partition.getConfig(), is(config));
	}

	/**
	 * Tests the "self-extending" of {@link PartitionerConfig}'s from DLMExceptions.
	 */
	@Test
	public void testDLMException()
	{
		//
		// create a config that only covers the C_Order table.
		// we expect the partitioner to create an augmented version of this config.
		// the augmented version shall in addition have a line with tableName=C_Invoice and a reference with referencingColumn=C_Order_ID and referencedTable=C_Order
		final PartitionerConfig config = PartitionerConfig.builder()
				.line(I_C_Order.Table_Name).endLine()
				.build();

		final Partition partition = testWithOrderAndInvoice(config);
		assertThat(partition.getConfig(), is(not(config)));
	}

	@Test(timeout = 10000) // timeout to make sure this test doesn'T run forever in case of a bug
	public void testCircularReferences()
	{
		final PartitionerConfig config = PartitionerConfig.builder()

				// order -> payment
				.line(I_C_Order.Table_Name)
				.ref().setReferencingColumnName(I_C_Order.COLUMNNAME_C_Payment_ID).setReferencedTableName(I_C_Payment.Table_Name).endRef()

				// payment -> invoice
				.line(I_C_Payment.Table_Name)
				.ref().setReferencingColumnName(I_C_Payment.COLUMNNAME_C_Invoice_ID).setReferencedTableName(I_C_Invoice.Table_Name).endRef()

				// invoice -> order
				.line(I_C_Invoice.Table_Name)
				.ref().setReferencingColumnName(I_C_Invoice.COLUMNNAME_C_Order_ID).setReferencedTableName(I_C_Order.Table_Name).endRef()

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
		final List<Partition> partitions = partitionerService.createPartition0(PartitionRequestFactory.builder().setConfig(config).build());

		//
		// verify
		assertNotNull(partitions);
		assertThat(partitions.get(0).getConfig(), is(config));

		assertThat(partitions.get(0).getConfig().getLines().size(), is(3)); // the config has no more or less lines than it had before

		assertNotNull(partitions.get(0).getRecordsFlat());
		assertThat(partitions.get(0).getRecordsFlat().size(), is(3));
		assertThat(partitions.get(0).getRecordsFlat().contains(asTableRef(order)), is(true));
		assertThat(partitions.get(0).getRecordsFlat().contains(asTableRef(invoice)), is(true));
		assertThat(partitions.get(0).getRecordsFlat().contains(asTableRef(payment)), is(true));
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
	 * Verify that a record won't be added to another partition after if was processed via {@link PartitionerServiceOld#createPartition0(PartitionerConfig)}.
	 */
	@Test
	public void testPartition_stored()
	{
		final Partition partition = testCircularReferences_within_same_table0();
		partitionerService.storePartition(partition, false);

		// make sure the DLM_Partition_ID was updated within the records that we found
		partitionerService.loadWithAllRecords(partition).getRecordsFlat().stream().forEach(r -> {

			final IDLMAware dlmAware = r.getModel(PlainContextAware.newOutOfTrx(Env.getCtx()), IDLMAware.class);
			assertThat(dlmAware.getDLM_Partition_ID(), is(partition.getDLM_Partition_ID()));
		});

		final CreatePartitionRequest secondConfig = PartitionRequestFactory.builder().setConfig(partition.getConfig()).build();
		final List<Partition> secondPartitions = partitionerService.createPartition0(secondConfig);

		/*
		 * partitionerService.loadWithAllRecords(partition).getRecords();
		 * partitionerService.loadWithAllRecords(secondPartitions.get(0)).getRecords();
		 */
		assertThat(secondPartitions.isEmpty(), is(true)); // we create add additional records, the partitioner shall *not* return the already partitioned ones.
	}

	/**
	 * Verifies that also a scenario like the following works:
	 * <li>C_Invoice references C_Order
	 * <li>C_OrderLine also references C_Order
	 *
	 * So, if i start a partition with a C_Invoice record, then i know to also add the C_Order record that is referenced by the invoice.<br>
	 * However, i also need to add the C_OrderLine records which also reference the C_Order
	 */
	@Test
	public void test_multiple_roots()
	{
		test_multiple_roots0();
	}

	private List<Partition> test_multiple_roots0()
	{
		final PartitionerConfig config = PartitionerConfig.builder()

				// invoice -> order
				.line(I_C_Invoice.Table_Name)
				.ref().setReferencingColumnName(I_C_Invoice.COLUMNNAME_C_Order_ID).setReferencedTableName(I_C_Order.Table_Name).endRef()

				// orderLine -> order
				.line(I_C_OrderLine.Table_Name)
				.ref().setReferencingColumnName(I_C_OrderLine.COLUMNNAME_C_Order_ID).setReferencedTableName(I_C_Order.Table_Name).endRef()

				.endLine().build();

		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		POJOWrapper.setInstanceName(order, "order");
		InterfaceWrapperHelper.save(order);

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		POJOWrapper.setInstanceName(invoice, "invoice");
		invoice.setC_Order(order);
		InterfaceWrapperHelper.save(invoice);

		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		POJOWrapper.setInstanceName(orderLine, "orderLine");
		orderLine.setC_Order(order);
		InterfaceWrapperHelper.save(orderLine);

		// catch: there is a second order which is unrelated and shall therefore *not* end up in the partition.
		final I_C_Order order2 = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		POJOWrapper.setInstanceName(order2, "order2");
		InterfaceWrapperHelper.save(order2);

		final List<Partition> partitions = partitionerService.createPartition0(PartitionRequestFactory.builder().setConfig(config).build());
		assertThat(partitions.get(0).getRecordsFlat().size(), is(3));
		assertThat(partitions.get(0).getRecordsFlat().contains(asTableRef(order)), is(true));
		assertThat(partitions.get(0).getRecordsFlat().contains(asTableRef(orderLine)), is(true));
		assertThat(partitions.get(0).getRecordsFlat().contains(asTableRef(invoice)), is(true));

		return partitions;
	}

	private Partition testCircularReferences_within_same_table0()
	{
		final PartitionerConfig config = PartitionerConfig.builder()

				// invoice -> credit-memo
				.line(I_C_Invoice.Table_Name)
				.ref().setReferencingColumnName(I_C_Invoice.COLUMNNAME_Ref_CreditMemo_ID).setReferencedTableName(I_C_Invoice.Table_Name)
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
		final List<Partition> partitions = partitionerService.createPartition0(PartitionRequestFactory.builder().setConfig(config).build());

		//
		// verify
		assertNotNull(partitions);
		assertThat(partitions.get(0).getConfig(), is(config));

		assertThat(partitions.get(0).getConfig().getLines().size(), is(1)); // the config has no more or less lines than it had before
		assertThat(partitions.get(0).getConfig().getLines().get(0).getReferences().size(), is(2)); // the single config line has no more or less references than it had before.

		assertNotNull(partitions.get(0).getRecordsFlat());
		assertThat(partitions.get(0).getRecordsFlat().size(), is(2));
		assertThat(partitions.get(0).getRecordsFlat().contains(asTableRef(invoice)), is(true));
		assertThat(partitions.get(0).getRecordsFlat().contains(asTableRef(creditmemo)), is(true));

		return partitions.get(0);
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
		final CreatePartitionRequest otherConfig = PartitionRequestFactory
				.builder()
				.setConfig(config)
				.build();
		final List<Partition> partitions = partitionerService.createPartition0(otherConfig);
		final Partition fullyLoadedPartition = partitionerService.loadWithAllRecords(partitions.get(0));

		//
		// verify
		assertNotNull(partitions);

		assertNotNull(fullyLoadedPartition.getRecordsFlat());
		assertThat(fullyLoadedPartition.getRecordsFlat().size(), is(2));
		assertThat(fullyLoadedPartition.getRecordsFlat().contains(asTableRef(order)), is(true));
		assertThat(fullyLoadedPartition.getRecordsFlat().contains(asTableRef(invoice)), is(true));

		return fullyLoadedPartition;
	}

	/**
	 * Emulate the database, throw a DLMException if an order record but no invoice record is contained.
	 */
	private void setupMigratorServiceMock()
	{
		Services.registerService(IMigratorService.class, new IMigratorService()
		{
			@Override
			public void testMigratePartition(final Partition partition)
			{
				final List<ITableRecordReference> recordsFlat = partitionerService.loadWithAllRecords(partition).getRecordsFlat();
				final boolean partitionHasInvoice = recordsFlat.stream().anyMatch(r -> I_C_Invoice.Table_Name.equals(InterfaceWrapperHelper.getModelTableName(r)));
				final boolean partitionHasOrder = recordsFlat.stream().anyMatch(r -> I_C_Order.Table_Name.equals(InterfaceWrapperHelper.getModelTableName(r)));
				if (partitionHasOrder && !partitionHasInvoice)
				{
					throw new DLMReferenceException(null,
							TableReferenceDescriptor.of(I_C_Invoice.Table_Name,
									I_C_Invoice.COLUMNNAME_C_Order_ID,
									I_C_Order.Table_Name,
									123),
							true);
				}
			}

			@Override
			public Partition migratePartition(final Partition partition)
			{
				throw new UnsupportedOperationException();
			}
		});
	}

	/**
	 * Verifies that also references made via <code>AD_Table_ID</code> and <code>Record_ID</code> work
	 */
	@Test
	public void testADTableID_RecordID()
	{
		final PartitionerConfig config = createADTableID_RecordIDConfig();

		// create an order
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		POJOWrapper.setInstanceName(order, "order");
		InterfaceWrapperHelper.save(order);

		// create a request that references the order
		final I_R_Request request = InterfaceWrapperHelper.newInstance(I_R_Request.class);
		POJOWrapper.setInstanceName(request, "request");

		final ITableRecordReference orderTableRecordReference = ITableRecordReference.FromModelConverter.convert(order);
		request.setAD_Table_ID(orderTableRecordReference.getAD_Table_ID());
		request.setRecord_ID(orderTableRecordReference.getRecord_ID());
		InterfaceWrapperHelper.save(request);

		// create another, unrelated order2
		final I_C_Order order2 = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		POJOWrapper.setInstanceName(order2, "order2");
		InterfaceWrapperHelper.save(order2);

		final List<Partition> partitions = partitionerService.createPartition0(PartitionRequestFactory.builder().setConfig(config).build());

		assertThat(partitions.get(0).getRecordsFlat().size(), is(2));
		assertThat(partitions.get(0).getRecordsFlat().contains(asTableRef(request)), is(true));
		assertThat(partitions.get(0).getRecordsFlat().contains(asTableRef(order)), is(true));
	}

	@Test
	public void testADTableID_RecordID_ignore_unrelated()
	{
		final PartitionerConfig config = createADTableID_RecordIDConfig();

		// create another, unrelated order2
		final I_C_Order order2 = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		POJOWrapper.setInstanceName(order2, "order2");
		InterfaceWrapperHelper.save(order2);

		// create a second request too
		// the second request's AD_Table_ID does not point to C_Order. Only the Record_ID has "by chance" the value that also happens to be order2's ID
		// still, there is no relation between request2 and order2
		final I_R_Request request2 = InterfaceWrapperHelper.newInstance(I_R_Request.class);
		POJOWrapper.setInstanceName(request2, "request2");

		final int invoiceAdTableId = Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name);
		request2.setAD_Table_ID(invoiceAdTableId);
		request2.setRecord_ID(order2.getC_Order_ID());
		InterfaceWrapperHelper.save(request2);

		final List<Partition> partitions = partitionerService.createPartition0(
				PartitionRequestFactory.builder().setConfig(config).build());

		final Partition fullyLoadedPartition = partitionerService.loadWithAllRecords(partitions.get(0));

		assertThat(fullyLoadedPartition.getRecordsFlat().contains(asTableRef(request2)), is(true)); // request2 shall be in the partion
		assertThat(fullyLoadedPartition.getRecordsFlat().contains(asTableRef(order2)), is(false)); // order2 is not referenced by request2 and shall therefore not me in the partition

		assertThat(fullyLoadedPartition.getRecordsFlat().size(), is(1));
	}

	private ITableRecordReference asTableRef(final Object request2)
	{
		return ITableRecordReference.FromModelConverter.convert(request2);
	}

	/**
	 * Creates a config for R_Request.Record_ID => C_Order
	 *
	 * @return
	 */
	private PartitionerConfig createADTableID_RecordIDConfig()
	{
		final PartitionerConfig config = PartitionerConfig.builder()

				// request -> order, via AD_Table_ID/Record_ID, as indicated by the referencing column name
				.line(I_R_Request.Table_Name)
				.ref().setReferencedTableName(I_C_Order.Table_Name).setReferencingColumnName(I_R_Request.COLUMNNAME_Record_ID)
				.endRef().endLine().build();
		return config;
	}

	/**
	 * Similar to {@link #test_multiple_roots()}; Verifies that also a scenario like the following works:
	 * <li>C_Invoice references C_Order
	 * <li>R_Request can also reference C_Order via AD_Table_ID/Record_ID
	 *
	 */
	@Test
	public void testADTableID_RecordID_multiple_roots()
	{
		final PartitionerConfig config = PartitionerConfig.builder()

				// invoice -> order
				.line(I_C_Invoice.Table_Name)
				.ref().setReferencedTableName(I_C_Order.Table_Name).setReferencingColumnName(I_C_Invoice.COLUMNNAME_C_Order_ID).endRef()

				// request -> order, via AD_Table_ID/Record_ID, as indicated by the referencing column name
				.line(I_R_Request.Table_Name)
				.ref().setReferencedTableName(I_C_Order.Table_Name).setReferencingColumnName(I_R_Request.COLUMNNAME_Record_ID).endRef()

				.endLine().build();

		// create an order
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		POJOWrapper.setInstanceName(order, "order");
		InterfaceWrapperHelper.save(order);

		// create a request that references the order
		final I_R_Request request = InterfaceWrapperHelper.newInstance(I_R_Request.class);
		POJOWrapper.setInstanceName(request, "request");

		final ITableRecordReference orderTableRecordReference = ITableRecordReference.FromModelConverter.convert(order);
		request.setAD_Table_ID(orderTableRecordReference.getAD_Table_ID());
		request.setRecord_ID(orderTableRecordReference.getRecord_ID());
		InterfaceWrapperHelper.save(request);

		// create another request2 that happens to have the same Record_ID value, but references not C_Order but some C_Payment we don't care for
		final I_R_Request request2 = InterfaceWrapperHelper.newInstance(I_R_Request.class);
		POJOWrapper.setInstanceName(request2, "request2");
		request2.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Payment.Table_Name));
		request2.setRecord_ID(orderTableRecordReference.getRecord_ID()); // happens to be the same ID ^^
		InterfaceWrapperHelper.save(request2);

		// create an invoice
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		POJOWrapper.setInstanceName(invoice, "invoice");
		invoice.setC_Order(order);
		InterfaceWrapperHelper.save(invoice);

		final CreatePartitionRequest partitionerRequest = PartitionRequestFactory.builder().setConfig(config).build();
		final List<Partition> partitions = partitionerService.createPartition0(partitionerRequest);

		//
		// verify that the unrelated request2 is in one partition and the other three interconnected connected records are in the other partition
		assertThat(partitions.get(0).getRecordsFlat().size(), is(1));
		assertThat(partitions.get(0).getRecordsFlat().contains(asTableRef(request2)), is(true));
		assertThat(partitions.get(0).getRecordsFlat().contains(asTableRef(order)), is(false));
		assertThat(partitions.get(0).getRecordsFlat().contains(asTableRef(request)), is(false));
		assertThat(partitions.get(0).getRecordsFlat().contains(asTableRef(invoice)), is(false));

		assertThat(partitions.get(1).getRecordsFlat().size(), is(3));
		assertThat(partitions.get(1).getRecordsFlat().contains(asTableRef(request2)), is(false));
		assertThat(partitions.get(1).getRecordsFlat().contains(asTableRef(order)), is(true));
		assertThat(partitions.get(1).getRecordsFlat().contains(asTableRef(request)), is(true));
		assertThat(partitions.get(1).getRecordsFlat().contains(asTableRef(invoice)), is(true));
	}

	/**
	 * Verifies that two unrelated records do not end up in one single partition.
	 */
	@Test
	public void testUnrelatedRecords1()
	{
		final PartitionerConfig config = PartitionerConfig.builder()
				// invoice
				.line(I_C_Invoice.Table_Name)
				.endLine().build();

		final I_C_Invoice invoice1 = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		POJOWrapper.setInstanceName(invoice1, "invoice1");
		InterfaceWrapperHelper.save(invoice1);

		final I_C_Invoice invoice2 = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		POJOWrapper.setInstanceName(invoice2, "invoice2");
		InterfaceWrapperHelper.save(invoice2);

		final CreatePartitionRequest partitionerRequest = PartitionRequestFactory.builder().setConfig(config).setOldestFirst(true).build();
		final List<Partition> partitions = partitionerService.createPartition0(partitionerRequest);

		// expecting one partition with invoice1, because the partitioner only looked at that invoice
		assertThat(partitions.size(), is(1));
		assertThat(partitions.get(0).getRecordsFlat().size(), is(1));
		assertThat(partitions.get(0).getRecordsFlat().get(0), is(ITableRecordReference.FromModelConverter.convert(invoice1)));
	}


	/**
	 * Verifies that two unrelated records do not end up in one single partition.
	 */
	@Test
	public void testUnrelatedRecords2()
	{
		final PartitionerConfig config = PartitionerConfig.builder()
				// invoice
				.line(I_C_Invoice.Table_Name)
				.line(I_C_Order.Table_Name)
				.endLine().build();

		final I_C_Invoice invoice1 = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		POJOWrapper.setInstanceName(invoice1, "invoice1");
		InterfaceWrapperHelper.save(invoice1);

		final I_C_Order order1 = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		POJOWrapper.setInstanceName(order1, "order1");
		InterfaceWrapperHelper.save(order1);

		final CreatePartitionRequest partitionerRequest = PartitionRequestFactory.builder().setConfig(config).setOldestFirst(true).build();
		final List<Partition> partitions = partitionerService.createPartition0(partitionerRequest);

		// expecting two partitions because the partitioner looked at each config line and found one partition for each line
		assertThat(partitions.size(), is(2));
		assertThat(partitions.get(0).getRecordsFlat().size(), is(1));
		assertThat(partitions.get(0).getRecordsFlat().get(0), is(ITableRecordReference.FromModelConverter.convert(invoice1)));
		assertThat(partitions.get(1).getRecordsFlat().size(), is(1));
		assertThat(partitions.get(1).getRecordsFlat().get(0), is(ITableRecordReference.FromModelConverter.convert(order1)));
	}
}
