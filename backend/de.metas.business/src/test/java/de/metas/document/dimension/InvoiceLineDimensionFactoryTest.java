package de.metas.document.dimension;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_InvoiceLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvoiceLineDimensionFactoryTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void get_and_set()
	{
		final InvoiceLineDimensionFactory dimensionFactory = new InvoiceLineDimensionFactory();

		final I_C_InvoiceLine record = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class);

		final Dimension dimension = DimensionTest.newFullyPopulatedDimension();
		dimensionFactory.updateRecord(record, dimension);


		final Dimension dimensionFromRecord = dimensionFactory.getFromRecord(record);
		final Dimension dimensionFromRecordExpected = dimension.toBuilder()
				.userElement1Id(0)
				.userElement2Id(0)
				.build();
		assertThat(dimensionFromRecord).usingRecursiveComparison().isEqualTo(dimensionFromRecordExpected);
		assertThat(dimensionFromRecord).isEqualTo(dimensionFromRecordExpected);
	}

	@Test
	void fieldsThatShallNotBeOverridden()
	{
		final InvoiceLineDimensionFactory dimensionFactory = new InvoiceLineDimensionFactory();

		final I_C_InvoiceLine record = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class);
		record.setC_Order_ID(12345);

		dimensionFactory.updateRecord(record, DimensionTest.newFullyPopulatedDimension());

		assertThat(record.getC_Order_ID()).isEqualTo(12345);
	}

}