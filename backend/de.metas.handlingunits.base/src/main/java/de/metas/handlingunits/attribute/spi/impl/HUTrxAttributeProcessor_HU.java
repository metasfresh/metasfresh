package de.metas.handlingunits.attribute.spi.impl;

import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.HuPackingInstructionsAttributeId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.attribute.exceptions.InvalidAttributeValueException;
import de.metas.handlingunits.attribute.spi.IHUTrxAttributeProcessor;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_Trx_Attribute;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.model.InterfaceWrapperHelper;

/**
 * Process an {@link I_M_HU_Trx_Attribute} for an {@link I_M_HU}.
 *
 * @author tsa
 */
public class HUTrxAttributeProcessor_HU implements IHUTrxAttributeProcessor
{
	@Override
	public void processSave(final IHUContext huContext, final I_M_HU_Trx_Attribute trxAttribute, final Object referencedModel)
	{
		if (!HUConstants.DEBUG_07277_processHUTrxAttribute)
		{
			return; // FIXME debuging
		}

		final I_M_HU hu = InterfaceWrapperHelper.create(referencedModel, I_M_HU.class);

		final IHUPIAttributesDAO huPIAttributesDAO = Services.get(IHUPIAttributesDAO.class);
		final IHUAttributesDAO huAttributesDAO = huContext.getHUAttributeStorageFactory().getHUAttributesDAO();
		final I_M_HU_Attribute huAttributeExisting = retrieveHUAttribute(huAttributesDAO, trxAttribute);

		final I_M_HU_Attribute huAttribute;
		if (huAttributeExisting == null)
		{
			final I_M_HU_PI_Attribute huPIAttributeRecord = huPIAttributesDAO.getById(HuPackingInstructionsAttributeId.ofRepoIdOrNull(trxAttribute.getM_HU_PI_Attribute_ID()));

			// Create new attribute
			huAttribute = InterfaceWrapperHelper.newInstance(I_M_HU_Attribute.class, hu);
			huAttribute.setAD_Org_ID(hu.getAD_Org_ID());
			huAttribute.setM_HU(hu);
			huAttribute.setM_Attribute_ID(trxAttribute.getM_Attribute_ID());

			huAttribute.setM_HU_PI_Attribute_ID(trxAttribute.getM_HU_PI_Attribute_ID());
			huAttribute.setIsUnique(huPIAttributeRecord != null && huPIAttributeRecord.isUnique());
		}
		else
		{
			huAttribute = huAttributeExisting;
		}

		//
		// Update values
		huAttribute.setValue(trxAttribute.getValue());
		huAttribute.setValueNumber(trxAttribute.getValueNumber());
		
		// TODO tsa: why following line was missing?!?
		// huAttribute.setValueDate(trxAttribute.getValueDate());

		huAttributesDAO.save(huAttribute);
	}

	@Override
	public void processDrop(final IHUContext huContext, final I_M_HU_Trx_Attribute huTrxAttribute, final Object referencedModel)
	{
		final IHUAttributesDAO huAttributesDAO = huContext.getHUAttributeStorageFactory().getHUAttributesDAO();
		final I_M_HU_Attribute huAttributeExisting = retrieveHUAttribute(huAttributesDAO, huTrxAttribute);
		if (huAttributeExisting == null)
		{
			throw new InvalidAttributeValueException("Retrieved HUAttribute cannot be null (" + huTrxAttribute.getM_Attribute_ID() + "): " + huTrxAttribute);
		}
		huAttributesDAO.delete(huAttributeExisting);
	}

	private I_M_HU_Attribute retrieveHUAttribute(final IHUAttributesDAO huAttributesDAO, final I_M_HU_Trx_Attribute trx)
	{
		if (trx.getM_HU_Attribute_ID() > 0)
		{
			final I_M_HU_Attribute huAttributeExisting = trx.getM_HU_Attribute();
			if (huAttributeExisting != null && huAttributeExisting.getM_HU_Attribute_ID() > 0)
			{
				return huAttributeExisting;
			}
		}

		final I_M_HU hu = trx.getM_HU();
		final AttributeId attributeId = AttributeId.ofRepoId(trx.getM_Attribute_ID());
		final I_M_HU_Attribute huAttribute = huAttributesDAO.retrieveAttribute(hu, attributeId);
		return huAttribute;
	}

}
