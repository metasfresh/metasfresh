package de.metas.invoicecandidate.document.dimension;

import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionTest;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvoiceCandidateDimensionFactoryTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void test()
	{
		final InvoiceCandidateDimensionFactory dimensionFactory = new InvoiceCandidateDimensionFactory();

		final I_C_Invoice_Candidate record = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class);
		final Dimension dimension = DimensionTest.newFullyPopulatedDimension().toBuilder()
				.user1_ID(0)
				.user2_ID(0)
				.userElement1Id(0)
				.userElement2Id(0)
				.build();

		dimensionFactory.updateRecord(record, dimension);
		final Dimension dimensionFromRecord = dimensionFactory.getFromRecord(record);
		assertThat(dimensionFromRecord).usingRecursiveComparison().isEqualTo(dimension);
		assertThat(dimensionFromRecord).isEqualTo(dimension);
	}
}