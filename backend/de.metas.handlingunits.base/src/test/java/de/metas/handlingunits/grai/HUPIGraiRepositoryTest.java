package de.metas.handlingunits.grai;

import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_HU_PI_GRAI;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HUPIGraiRepositoryTest
{
	private HUPIGraiRepository repository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new HUPIGraiRepository();
	}

	@Test
	void resolveHuPackingInstructionsId_oneMatch_returnsCorrectId()
	{
		final I_M_HU_PI_GRAI record = InterfaceWrapperHelper.newInstance(I_M_HU_PI_GRAI.class);
		record.setGRAI_CompanyPrefix("7613204");
		record.setGRAI_AssetType("00307");
		record.setM_HU_PI_ID(42);
		InterfaceWrapperHelper.save(record);

		final GRAI grai = GRAI.ofCanonicalString("7613204.00307.999999");

		final HuPackingInstructionsId result = repository.resolveHuPackingInstructionsId(grai);

		assertThat(result).isEqualTo(HuPackingInstructionsId.ofRepoId(42));
	}
}
