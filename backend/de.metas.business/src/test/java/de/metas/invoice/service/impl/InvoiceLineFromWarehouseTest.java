package de.metas.invoice.service.impl;

import de.metas.currency.CurrencyRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link InvoiceBL#getFromWarehouseId(I_C_Invoice, I_C_InvoiceLine)}: the warehouse an invoice line ships from,
 * which the invoice-line tax paths pass into the tax query.
 */
class InvoiceLineFromWarehouseTest
{
	private InvoiceBL invoiceBL;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new CurrencyRepository());
		invoiceBL = new InvoiceBL();
	}

	@Test
	void invoiceWarehouse_wins()
	{
		final WarehouseId invoiceWarehouseId = createWarehouse();
		final WarehouseId shipmentWarehouseId = createWarehouse();

		final I_C_Invoice invoice = createInvoice(invoiceWarehouseId);
		final I_C_InvoiceLine invoiceLine = createInvoiceLine(invoice, createShipmentLine(shipmentWarehouseId));

		assertThat(invoiceBL.getFromWarehouseId(invoice, invoiceLine)).isEqualTo(invoiceWarehouseId);
	}

	@Test
	void shipmentWarehouse_whenNoInvoiceWarehouse()
	{
		final WarehouseId shipmentWarehouseId = createWarehouse();

		final I_C_Invoice invoice = createInvoice(null);
		final I_C_InvoiceLine invoiceLine = createInvoiceLine(invoice, createShipmentLine(shipmentWarehouseId));

		assertThat(invoiceBL.getFromWarehouseId(invoice, invoiceLine)).isEqualTo(shipmentWarehouseId);
	}

	@Test
	void noShipmentNoInvoiceWarehouse_null()
	{
		final I_C_Invoice invoice = createInvoice(null);
		final I_C_InvoiceLine invoiceLine = createInvoiceLine(invoice, null);

		assertThat(invoiceBL.getFromWarehouseId(invoice, invoiceLine)).isNull();
	}

	private static WarehouseId createWarehouse()
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		saveRecord(warehouse);
		return WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
	}

	private static I_C_Invoice createInvoice(final WarehouseId warehouseId)
	{
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setM_Warehouse_ID(WarehouseId.toRepoId(warehouseId));
		saveRecord(invoice);
		return invoice;
	}

	private static I_M_InOutLine createShipmentLine(final WarehouseId warehouseId)
	{
		final I_M_InOut shipment = newInstance(I_M_InOut.class);
		shipment.setM_Warehouse_ID(warehouseId.getRepoId());
		shipment.setIsSOTrx(true);
		saveRecord(shipment);

		final I_M_InOutLine shipmentLine = newInstance(I_M_InOutLine.class);
		shipmentLine.setM_InOut_ID(shipment.getM_InOut_ID());
		saveRecord(shipmentLine);
		return shipmentLine;
	}

	private static I_C_InvoiceLine createInvoiceLine(final I_C_Invoice invoice, final I_M_InOutLine shipmentLine)
	{
		final I_C_InvoiceLine invoiceLine = newInstance(I_C_InvoiceLine.class);
		invoiceLine.setC_Invoice_ID(invoice.getC_Invoice_ID());
		if (shipmentLine != null)
		{
			invoiceLine.setM_InOutLine_ID(shipmentLine.getM_InOutLine_ID());
		}
		saveRecord(invoiceLine);
		return invoiceLine;
	}
}
