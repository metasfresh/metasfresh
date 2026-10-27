package de.metas.ui.web.handlingunits.process;

import de.metas.ad_reference.ADReferenceService;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowId;
import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.handlingunits.process.WebuiHUTransformCommand.ActionType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * "TU to new LUs" must offer only LU-type parent packing instructions; a TU-type parent (like "Offen") cannot be built as LU.
 */
public class WebuiHUTransformParametersFillerTest
{
	private I_M_HU_PI tuPI;
	private WebuiHUTransformParametersFiller filler;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		tuPI = createPI("SBB Rahmen");
		final I_M_HU_PI_Version tuPIVersion = createPIVersion(tuPI, HuUnitType.TU);

		final I_M_HU tuHU = newInstance(I_M_HU.class);
		tuHU.setM_HU_PI_Version_ID(tuPIVersion.getM_HU_PI_Version_ID());
		saveRecord(tuHU);
		final HuId tuHUId = HuId.ofRepoId(tuHU.getM_HU_ID());

		final HUEditorRow tuRow = HUEditorRow.builder(WindowId.of(123))
				.setRowId(HUEditorRowId.ofTopLevelHU(tuHUId))
				.setType(HUEditorRowType.TU)
				.setTopLevel(true)
				.build();

		filler = WebuiHUTransformParametersFiller.builder()
				.adReferenceService(Mockito.mock(ADReferenceService.class))
				.lookupDataSourceFactory(Mockito.mock(LookupDataSourceFactory.class))
				.view(Mockito.mock(HUEditorView.class))
				.selectedRow(tuRow)
				.actionType(ActionType.TU_To_NewLUs)
				.build();
	}

	@Test
	public void tuIncludedInTUTypeParentAndInLU_onlyTheLUIsOffered()
	{
		createParentItem("Offen", HuUnitType.TU);
		final I_M_HU_PI_Item luItem = createParentItem("Pallet", HuUnitType.LU);

		assertThat(filler.getM_HU_PI_Item_IDs().getKeysAsInt()).containsExactly(luItem.getM_HU_PI_Item_ID());
		final I_M_HU_PI_Item defaultLUItem = filler.getDefaultM_LU_PI_ItemOrNull();
		assertThat(defaultLUItem).isNotNull();
		assertThat(defaultLUItem.getM_HU_PI_Item_ID()).isEqualTo(luItem.getM_HU_PI_Item_ID());
	}

	@Test
	public void tuIncludedOnlyInTUTypeParent_noLUIsOffered()
	{
		createParentItem("Offen", HuUnitType.TU);

		assertThat(filler.getM_HU_PI_Item_IDs().isEmpty()).isTrue();
		assertThat(filler.getDefaultM_LU_PI_ItemOrNull()).isNull();
	}

	private I_M_HU_PI_Item createParentItem(final String name, final HuUnitType huUnitType)
	{
		final I_M_HU_PI_Version parentPIVersion = createPIVersion(createPI(name), huUnitType);

		final I_M_HU_PI_Item parentItem = newInstance(I_M_HU_PI_Item.class);
		parentItem.setM_HU_PI_Version_ID(parentPIVersion.getM_HU_PI_Version_ID());
		parentItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_HandlingUnit);
		parentItem.setIncluded_HU_PI_ID(tuPI.getM_HU_PI_ID());
		parentItem.setQty(BigDecimal.TEN);
		saveRecord(parentItem);
		return parentItem;
	}

	private static I_M_HU_PI createPI(final String name)
	{
		final I_M_HU_PI pi = newInstance(I_M_HU_PI.class);
		pi.setName(name);
		saveRecord(pi);
		return pi;
	}

	private static I_M_HU_PI_Version createPIVersion(final I_M_HU_PI pi, final HuUnitType huUnitType)
	{
		final I_M_HU_PI_Version piVersion = newInstance(I_M_HU_PI_Version.class);
		piVersion.setM_HU_PI_ID(pi.getM_HU_PI_ID());
		piVersion.setName(pi.getName());
		piVersion.setHU_UnitType(huUnitType.getCode());
		piVersion.setIsCurrent(true);
		saveRecord(piVersion);
		return piVersion;
	}
}
