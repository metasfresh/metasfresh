package de.metas.handlingunits.rest_api;

import de.metas.common.handlingunits.JsonHU;
import de.metas.common.handlingunits.JsonHUList;
import de.metas.handlingunits.HuId;
import de.metas.ad_reference.ADReferenceService;
import de.metas.handlingunits.grai.HUGraiService;
import de.metas.handlingunits.grai.HUPIGraiRepository;
import de.metas.handlingunits.impl.HUQtyService;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.inventory.InventoryCandidateService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.labels.HULabelService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.groups.Tuple;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class HandlingUnitsServiceTest
{
	private HandlingUnitsService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		final ADReferenceService adReferenceService = ADReferenceService.newMocked();
		SpringContextHolder.registerJUnitBean(adReferenceService);

		service = new HandlingUnitsService(
				HUQRCodesService.newInstanceForUnitTesting(),
				new HUQtyService(InventoryService.newInstanceForUnitTesting()),
				HULabelService.newInstanceForUnitTesting(),
				new InventoryCandidateService(adReferenceService),
				new HUGraiService(new HUPIGraiRepository()));
	}

	private int createLUPIVersionId()
	{
		final I_M_HU_PI pi = InterfaceWrapperHelper.newInstance(I_M_HU_PI.class);
		InterfaceWrapperHelper.saveRecord(pi);

		final I_M_HU_PI_Version piVersion = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Version.class);
		piVersion.setM_HU_PI_ID(pi.getM_HU_PI_ID());
		piVersion.setHU_UnitType(X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		piVersion.setIsCurrent(true);
		InterfaceWrapperHelper.saveRecord(piVersion);
		return piVersion.getM_HU_PI_Version_ID();
	}

	private HuId createHU(final String huStatus)
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		hu.setHUStatus(huStatus);
		hu.setM_HU_PI_Version_ID(createLUPIVersionId());
		InterfaceWrapperHelper.saveRecord(hu);
		return HuId.ofRepoId(hu.getM_HU_ID());
	}

	/** TC4: closed-LU endpoint must list shipped/destroyed LUs with their status instead of throwing */
	@Test
	void getHUsListOfAnyStatus_returnsShippedAndDestroyedLUsWithStatus()
	{
		final HuId active = createHU(X_M_HU.HUSTATUS_Active);
		final HuId shipped = createHU(X_M_HU.HUSTATUS_Shipped);
		final HuId destroyed = createHU(X_M_HU.HUSTATUS_Destroyed);

		final JsonHUList result = service.getHUsListOfAnyStatus(Arrays.asList(active, shipped, destroyed), "en_US");

		assertThat(result.getHus()).hasSize(3); // hasClosedLUs = !getClosedLUs().isEmpty() on this same list
		assertThat(result.getHus())
				.extracting(JsonHU::getId, JsonHU::getHuStatus)
				.containsExactly(
						Tuple.tuple(String.valueOf(active.getRepoId()), X_M_HU.HUSTATUS_Active),
						Tuple.tuple(String.valueOf(shipped.getRepoId()), X_M_HU.HUSTATUS_Shipped),
						Tuple.tuple(String.valueOf(destroyed.getRepoId()), X_M_HU.HUSTATUS_Destroyed));
	}
}
