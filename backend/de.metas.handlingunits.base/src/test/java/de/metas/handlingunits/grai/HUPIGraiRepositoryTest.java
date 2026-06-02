package de.metas.handlingunits.grai;

import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_HU_PI_GRAI;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
	void resolveHuPackingInstructionsId_noMatch_throwsException()
	{
		final GRAI grai = GRAI.ofCanonicalString("7613204.00307.999999");

		assertThatThrownBy(() -> repository.resolveHuPackingInstructionsId(grai))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("GRAINoMatchingTUType");
	}

	@Test
	void resolveHuPackingInstructionsId_oneMatch_returnsCorrectId()
	{
		final int expectedHuPiRepoId = 42;

		final I_M_HU_PI_GRAI record = InterfaceWrapperHelper.newInstance(I_M_HU_PI_GRAI.class);
		record.setGRAI_CompanyPrefix("7613204");
		record.setGRAI_AssetType("00307");
		record.setM_HU_PI_ID(expectedHuPiRepoId);
		InterfaceWrapperHelper.save(record);

		final GRAI grai = GRAI.ofCanonicalString("7613204.00307.999999");

		final HuPackingInstructionsId result = repository.resolveHuPackingInstructionsId(grai);

		assertThat(result).isEqualTo(HuPackingInstructionsId.ofRepoId(expectedHuPiRepoId));
	}

	@Test
	void resolveHuPackingInstructionsId_twoMatches_throwsDBException()
	{
		// Two active rows with the same (CompanyPrefix, AssetType) — a state the global unique index forbids,
		// but firstOnly() must surface it loudly rather than silently returning one.
		final I_M_HU_PI_GRAI record1 = InterfaceWrapperHelper.newInstance(I_M_HU_PI_GRAI.class);
		record1.setGRAI_CompanyPrefix("7613204");
		record1.setGRAI_AssetType("00307");
		record1.setM_HU_PI_ID(42);
		InterfaceWrapperHelper.save(record1);

		final I_M_HU_PI_GRAI record2 = InterfaceWrapperHelper.newInstance(I_M_HU_PI_GRAI.class);
		record2.setGRAI_CompanyPrefix("7613204");
		record2.setGRAI_AssetType("00307");
		record2.setM_HU_PI_ID(99);
		InterfaceWrapperHelper.save(record2);

		final GRAI grai = GRAI.ofCanonicalString("7613204.00307.999999");

		assertThatThrownBy(() -> repository.resolveHuPackingInstructionsId(grai))
				.isInstanceOf(DBException.class);
	}
}
