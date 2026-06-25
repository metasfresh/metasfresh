package de.metas.handlingunits.impl;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributesTestHelper;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
class HandlingUnitsBL_getTopLevelHuIdByGraiTest
{
	private IHandlingUnitsBL handlingUnitsBL;
	private AttributeId graiAttributeId;

	@BeforeEach
	void beforeEach()
	{
		final HUTestHelper huTestHelper = HUTestHelper.newInstanceOutOfTrx();
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		// Register the GRAI M_Attribute and add it to the TEMPLATE PI so it ends up in
		// huRelevantAttributeIds (IHUStorageBL.getAvailableAttributeIds), which is required
		// for the HUQueryBuilder attribute filter to be applied.
		final I_M_Attribute graiAttr = new AttributesTestHelper().createM_Attribute(
				AttributeConstants.ATTR_GRAI.getCode(),
				X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
				true);
		graiAttributeId = AttributeId.ofRepoId(graiAttr.getM_Attribute_ID());

		huTestHelper.createM_HU_PI_Attribute(
				HUPIAttributeBuilder.newInstance(graiAttr)
						.setM_HU_PI(HuPackingInstructionsId.TEMPLATE)
						.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation));
	}

	private I_M_HU createTopLevelActiveHU()
	{
		final I_M_HU hu = newInstance(I_M_HU.class);
		hu.setHUStatus(X_M_HU.HUSTATUS_Active);
		// M_HU_Item_Parent_ID = 0 → top-level (no parent)
		saveRecord(hu);
		return hu;
	}

	/** An HU with a parent item set → NOT top-level (excluded by setOnlyTopLevelHUs()). */
	private I_M_HU createChildActiveHU()
	{
		final I_M_HU_Item parentItem = newInstance(I_M_HU_Item.class);
		saveRecord(parentItem);

		final I_M_HU hu = newInstance(I_M_HU.class);
		hu.setHUStatus(X_M_HU.HUSTATUS_Active);
		hu.setM_HU_Item_Parent_ID(parentItem.getM_HU_Item_ID());
		saveRecord(hu);
		return hu;
	}

	private void stampGraiAttribute(final I_M_HU hu, final String graiCanonical)
	{
		final I_M_HU_Attribute huAttr = newInstance(I_M_HU_Attribute.class);
		huAttr.setM_HU_ID(hu.getM_HU_ID());
		huAttr.setM_Attribute_ID(graiAttributeId.getRepoId());
		huAttr.setValue(graiCanonical);
		saveRecord(huAttr);
	}

	@Test
	void found_when_grai_matches()
	{
		final String graiCanonical = "1234567.00001.SERIAL01";
		final I_M_HU hu = createTopLevelActiveHU();
		stampGraiAttribute(hu, graiCanonical);

		final Optional<HuId> result = handlingUnitsBL.getTopLevelHuIdByGrai(GRAI.parse(graiCanonical));

		assertThat(result).contains(HuId.ofRepoId(hu.getM_HU_ID()));
	}

	@Test
	void empty_when_no_hu_has_that_grai()
	{
		final Optional<HuId> result = handlingUnitsBL.getTopLevelHuIdByGrai(GRAI.parse("9999999.00099.NOMATCH"));

		assertThat(result).isEmpty();
	}

	@Test
	void empty_when_hu_is_inactive()
	{
		// setOnlyActiveHUs(true) filters on IsActive='Y' (distinct from HUStatus)
		final String graiCanonical = "1234567.00001.INACTIVE";
		final I_M_HU hu = createTopLevelActiveHU();
		stampGraiAttribute(hu, graiCanonical);
		hu.setIsActive(false);
		saveRecord(hu);

		final Optional<HuId> result = handlingUnitsBL.getTopLevelHuIdByGrai(GRAI.parse(graiCanonical));

		assertThat(result).isEmpty();
	}

	@Test
	void empty_when_hu_is_not_top_level()
	{
		// setOnlyTopLevelHUs() excludes HUs that have a parent item
		final String graiCanonical = "1234567.00001.CHILDHU";
		final I_M_HU childHu = createChildActiveHU();
		stampGraiAttribute(childHu, graiCanonical);

		final Optional<HuId> result = handlingUnitsBL.getTopLevelHuIdByGrai(GRAI.parse(graiCanonical));

		assertThat(result).isEmpty();
	}
}
