package de.metas.inoutcandidate.document.dimension;

import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionTest;
import de.metas.inoutcandidate.api.impl.ReceiptScheduleDimensionFactoryTest;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ShipmentScheduleDimensionFactoryTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void set_and_get()
	{
		final ShipmentScheduleDimensionFactory dimensionFactory = new ShipmentScheduleDimensionFactory();

		final I_M_ShipmentSchedule record = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		record.setM_Product_ID(900002);
		final Dimension dimension = DimensionTest.newFullyPopulatedDimension();
		dimensionFactory.updateRecord(record, dimension);

		final Dimension dimensionFromRecord = dimensionFactory.getFromRecord(record);
		final Dimension dimensionFromRecordExpected = dimension.toBuilder()
				.projectId(null)
				.campaignId(0)
				.activityId(null)
				.salesOrderId(null)
				.productId(ProductId.ofRepoId(900002))
				.user1_ID(0)
				.user2_ID(0)
				.userElement1Id(0)
				.userElement2Id(0)
				.userElementString1(null)
				.userElementString2(null)
				.userElementString3(null)
				.userElementString4(null)
				.userElementString5(null)
				.userElementString6(null)
				.userElementString7(null)
				.projectId(ProjectId.ofRepoId(1))
				.build();
		assertThat(dimensionFromRecord).usingRecursiveComparison().isEqualTo(dimensionFromRecordExpected);
		assertThat(dimensionFromRecord).isEqualTo(dimensionFromRecordExpected);
	}

	@Test
	void fieldsThatShallNotBeOverridden()
	{
		final ReceiptScheduleDimensionFactory dimensionFactory = new ReceiptScheduleDimensionFactoryTest();

		final I_M_ReceiptSchedule record = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class);
		record.setC_Order_ID(900001);
		record.setM_Product_ID(900002);

		dimensionFactory.updateRecord(record, DimensionTest.newFullyPopulatedDimension());

		assertThat(record.getC_Order_ID()).isEqualTo(900001);
		assertThat(record.getM_Product_ID()).isEqualTo(900002);
	}

}