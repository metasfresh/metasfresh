package de.metas.invoicecandidate.document.dimension;

import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionTest;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class InvoiceCandidateDimensionFactoryTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void get_and_set()
	{
		final InvoiceCandidateDimensionFactory dimensionFactory = new InvoiceCandidateDimensionFactory();

		final I_C_Invoice_Candidate record = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class);
		final Dimension dimension = DimensionTest.newFullyPopulatedDimension();
		dimensionFactory.updateRecord(record, dimension);

		final Dimension dimensionFromRecord = dimensionFactory.getFromRecord(record);
		final Dimension dimensionFromRecordExpected = dimension.toBuilder()
				.productId(null)
				.user1_ID(0)
				.user2_ID(0)
				.userElement1Id(0)
				.userElement2Id(0)
				.build();
		assertThat(dimensionFromRecord).usingRecursiveComparison().isEqualTo(dimensionFromRecordExpected);
		assertThat(dimensionFromRecord).isEqualTo(dimensionFromRecordExpected);
	}

	@Test
	void fieldsThatShallNotBeOverridden()
	{
		final InvoiceCandidateDimensionFactory dimensionFactory = new InvoiceCandidateDimensionFactory();

		final I_C_Invoice_Candidate record = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class);
		record.setC_Order_ID(9000001);
		record.setM_Product_ID(9000002);

		dimensionFactory.updateRecord(record, DimensionTest.newFullyPopulatedDimension());

		assertThat(record.getC_Order_ID()).isEqualTo(9000001);
		assertThat(record.getM_Product_ID()).isEqualTo(9000002);
	}

}