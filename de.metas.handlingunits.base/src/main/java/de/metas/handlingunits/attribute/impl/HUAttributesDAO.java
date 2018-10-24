package de.metas.handlingunits.attribute.impl;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.attribute.PIAttributes;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.util.Services;
import lombok.NonNull;

public final class HUAttributesDAO implements IHUAttributesDAO
{
	public static final HUAttributesDAO instance = new HUAttributesDAO();

	private HUAttributesDAO()
	{
		super();
	}

	@Override
	public I_M_HU_Attribute newHUAttribute(final Object contextProvider)
	{
		final I_M_HU_Attribute huAttribute = InterfaceWrapperHelper.newInstance(I_M_HU_Attribute.class, contextProvider);
		return huAttribute;
	}

	@Override
	public void save(final I_M_HU_Attribute huAttribute)
	{
		InterfaceWrapperHelper.save(huAttribute);
	}

	@Override
	public void delete(final I_M_HU_Attribute huAttribute)
	{
		InterfaceWrapperHelper.delete(huAttribute);
	}

	@Override
	public void initHUAttributes(final I_M_HU hu)
	{
		// nothing
	}

	@Override
	public List<I_M_HU_Attribute> retrieveAttributesOrdered(final I_M_HU hu)
	{
		// NOTE: don't cache on this level. Caching is handled on upper levels
		
		final HuPackingInstructionsVersionId piVersionId = Services.get(IHandlingUnitsBL.class).getEffectivePIVersionId(hu);
		final PIAttributes piAttributes = Services.get(IHUPIAttributesDAO.class).retrievePIAttributes(piVersionId);

		// there are only some dozen attributes at most, so i think it'S fine to order them after loading
		final List<I_M_HU_Attribute> huAttributes = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Attribute.class, hu)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Attribute.COLUMNNAME_M_HU_ID, hu.getM_HU_ID())
				.create()
				.stream()
				.sorted(HUAttributesBySeqNoComparator.of(piAttributes))
				.collect(ImmutableList.toImmutableList());

		// Optimization: set M_HU link
		for (final I_M_HU_Attribute huAttribute : huAttributes)
		{
			huAttribute.setM_HU(hu);
		}

		return huAttributes;
	}

	private final List<I_M_HU_Attribute> retrieveAttributes(final I_M_HU hu, @NonNull final AttributeId attributeId)
	{
		final List<I_M_HU_Attribute> huAttributes = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Attribute.class, hu)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Attribute.COLUMN_M_HU_ID, hu.getM_HU_ID())
				.addEqualsFilter(I_M_HU_Attribute.COLUMN_M_Attribute_ID, attributeId)
				.create()
				.list(I_M_HU_Attribute.class);

		// Optimization: set M_HU link
		for (final I_M_HU_Attribute huAttribute : huAttributes)
		{
			huAttribute.setM_HU(hu);
		}

		return huAttributes;
	}

	@Override
	public I_M_HU_Attribute retrieveAttribute(final I_M_HU hu, final AttributeId attributeId)
	{
		final List<I_M_HU_Attribute> huAttributes = retrieveAttributes(hu, attributeId);
		if (huAttributes.isEmpty())
		{
			return null;
		}
		else if (huAttributes.size() == 1)
		{
			return huAttributes.get(0);
		}
		else
		{
			throw new AdempiereException("More than one HU Attributes were found for '" + attributeId + "' on " + hu.getM_HU_ID() + ": " + huAttributes);
		}
	}

	/**
	 * @return {@link NullAutoCloseable} always
	 */
	@Override
	public IAutoCloseable temporaryDisableAutoflush()
	{
		// NOTE: conceptualy this DAO implementation is continuously auto-flushing because it's saving/deleting direclty in database,
		// but we cannot disable this functionality
		return NullAutoCloseable.instance;
	}

	@Override
	public void flushAndClearCache()
	{
		// nothing because there is no internal cache
	}

}
