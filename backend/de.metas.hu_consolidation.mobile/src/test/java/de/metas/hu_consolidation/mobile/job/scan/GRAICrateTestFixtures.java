package de.metas.hu_consolidation.mobile.job.scan;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.picking.slot.PickingSlotQueue;
import de.metas.handlingunits.picking.slot.PickingSlotQueueItem;
import de.metas.picking.api.PickingSlotId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/**
 * POJO HU / GRAI attribute rows shared by the crate tests.
 * Create it after {@code AdempiereTestHelper.get().init()}.
 */
final class GRAICrateTestFixtures
{
	private final I_M_Attribute graiAttribute;

	GRAICrateTestFixtures()
	{
		graiAttribute = newInstance(I_M_Attribute.class);
		graiAttribute.setValue(AttributeConstants.ATTR_GRAI.getCode());
		graiAttribute.setName("GRAI");
		graiAttribute.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40);
		save(graiAttribute);

		// HUQueryBuilder only filters by attributes that are HU-relevant, i.e. mapped on the virtual packing instructions version
		final I_M_HU_PI_Attribute graiPIAttribute = newInstance(I_M_HU_PI_Attribute.class);
		graiPIAttribute.setM_HU_PI_Version_ID(HuPackingInstructionsVersionId.VIRTUAL.getRepoId());
		graiPIAttribute.setM_Attribute_ID(graiAttribute.getM_Attribute_ID());
		save(graiPIAttribute);
	}

	I_M_HU createTopLevelHU(@Nullable final String graiValue)
	{
		final I_M_HU hu = newInstance(I_M_HU.class);
		hu.setIsActive(true);
		save(hu);
		if (graiValue != null)
		{
			setGRAI(hu, graiValue);
		}
		return hu;
	}

	/** A TU that sits inside the given LU (it has a parent HU item, so it is not top-level). */
	I_M_HU createTUInside(final I_M_HU lu, final String graiValue)
	{
		final I_M_HU_Item luItem = newInstance(I_M_HU_Item.class);
		luItem.setM_HU_ID(lu.getM_HU_ID());
		save(luItem);

		final I_M_HU tu = newInstance(I_M_HU.class);
		tu.setIsActive(true);
		tu.setM_HU_Item_Parent_ID(luItem.getM_HU_Item_ID());
		save(tu);
		setGRAI(tu, graiValue);
		return tu;
	}

	void setGRAI(final I_M_HU hu, final String value)
	{
		final I_M_HU_Attribute huAttribute = newInstance(I_M_HU_Attribute.class);
		huAttribute.setM_HU_ID(hu.getM_HU_ID());
		huAttribute.setM_Attribute_ID(graiAttribute.getM_Attribute_ID());
		huAttribute.setValue(value);
		save(huAttribute);
	}

	static HuId id(final I_M_HU hu) {return HuId.ofRepoId(hu.getM_HU_ID());}

	static PickingSlotQueue queue(final PickingSlotId slotId, final HuId... huIds)
	{
		final ImmutableList.Builder<PickingSlotQueueItem> items = ImmutableList.builder();
		for (final HuId huId : huIds)
		{
			items.add(PickingSlotQueueItem.builder().huId(huId).build());
		}
		return PickingSlotQueue.builder().pickingSlotId(slotId).items(items.build()).build();
	}
}
