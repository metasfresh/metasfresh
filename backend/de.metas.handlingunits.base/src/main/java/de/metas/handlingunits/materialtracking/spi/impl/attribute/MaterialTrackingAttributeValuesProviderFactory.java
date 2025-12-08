package de.metas.handlingunits.materialtracking.spi.impl.attribute;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.mm.attributes.spi.IAttributeValuesProviderFactory;
import org.compiere.model.I_M_Attribute;

public class MaterialTrackingAttributeValuesProviderFactory
		implements IAttributeValuesProviderFactory
{
	@NonNull private final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);

	@Override
	public IAttributeValuesProvider createAttributeValuesProvider(@NonNull final I_M_Attribute attribute)
	{
		return MaterialTrackingAttributeValuesProvider.builder()
				.materialTrackingDAO(materialTrackingDAO)
				.huAttributesBL(huAttributesBL)
				.handlingUnitsBL(handlingUnitsBL)
				//
				.attribute(attribute)
				//
				.build();
	}
}
