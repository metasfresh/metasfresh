package de.metas.distribution.ddordercandidate;

import de.metas.material.event.ddordercandidate.DDOrderCandidateData;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DDOrderCandidateTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	void createDbData(final DDOrderCandidate candidate)
	{
		createASI(candidate);
	}

	private void createASI(final DDOrderCandidate candidate)
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		attribute.setValue("attr");
		attribute.setName("attr");
		attribute.setAttributeValueType(AttributeValueType.STRING.getCode());
		attribute.setIsStorageRelevant(true);
		InterfaceWrapperHelper.save(attribute);

		final I_M_AttributeSetInstance asi = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class);
		asi.setM_AttributeSetInstance_ID(candidate.getAttributeSetInstanceId().getRepoId());
		InterfaceWrapperHelper.save(asi);

		final I_M_AttributeInstance ai = InterfaceWrapperHelper.newInstance(I_M_AttributeInstance.class);
		ai.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
		ai.setM_Attribute_ID(attribute.getM_Attribute_ID());
		ai.setValue("aaabbbccc");
		InterfaceWrapperHelper.save(ai);
	}

	@Test
	void toDDOrderCandidateData_from()
	{
		final DDOrderCandidate candidate = DDOrderCandidateRepositoryTest.newFullyFilled();
		System.out.println(" candidate=" + candidate);
		createDbData(candidate);

		final DDOrderCandidateData data = candidate.toDDOrderCandidateData().build();
		System.out.println("data=" + data);

		final DDOrderCandidate candidate2 = DDOrderCandidate.from(data)
				//
				// copy fields which are not supported by DDOrderCandidateData
				.dateOrdered(candidate.getDateOrdered())
				.qtyTUs(candidate.getQtyTUs())
				//
				.build();
		System.out.println("candidate2=" + candidate2);

		Assertions.assertThat(candidate2)
				.usingRecursiveComparison()
				.isEqualTo(candidate);
	}
}