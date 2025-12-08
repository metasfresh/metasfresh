package de.metas.handlingunits.attribute.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.attribute.HUAndPIAttributes;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.attribute.PIAttributes;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.model.InterfaceWrapperHelper;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

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
		return InterfaceWrapperHelper.newInstance(I_M_HU_Attribute.class, contextProvider);
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
	public List<I_M_HU_Attribute> retrieveAllAttributesNoCache(final Collection<HuId> huIds)
	{
		if(huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_M_HU_Attribute.class)
				//.addOnlyActiveRecordsFilter() // all, including not active
				.addInArrayFilter(I_M_HU_Attribute.COLUMNNAME_M_HU_ID, huIds)
				.create()
				.stream()
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public HUAndPIAttributes retrieveAttributesOrdered(final I_M_HU hu)
	{
		// NOTE: don't cache on this level. Caching is handled on upper levels

		// there are only some dozen attributes at most, so i think it'S fine to order them after loading
		final List<I_M_HU_Attribute> huAttributes = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Attribute.class, hu)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Attribute.COLUMNNAME_M_HU_ID, hu.getM_HU_ID())
				.create()
				.stream()
				.collect(ImmutableList.toImmutableList());

		// Optimization: set M_HU link
		for (final I_M_HU_Attribute huAttribute : huAttributes)
		{
			huAttribute.setM_HU(hu);
		}

		final PIAttributes piAttributes = createPIAttributes(huAttributes);

		final ImmutableList<I_M_HU_Attribute> huAttributesSorted = HUAttributesBySeqNoComparator.of(piAttributes).sortAndCopy(huAttributes);
		return HUAndPIAttributes.of(huAttributesSorted, piAttributes);
	}

	private PIAttributes createPIAttributes(final List<I_M_HU_Attribute> huAttributes)
	{
		final IHUPIAttributesDAO piAttributesRepo = Services.get(IHUPIAttributesDAO.class);

		final ImmutableSet<Integer> piAttributeIds = huAttributes.stream().map(I_M_HU_Attribute::getM_HU_PI_Attribute_ID).collect(ImmutableSet.toImmutableSet());
		return piAttributesRepo.retrievePIAttributesByIds(piAttributeIds);
	}

	private List<I_M_HU_Attribute> retrieveAttributes(final I_M_HU hu, @NonNull final AttributeId attributeId)
	{
		final List<I_M_HU_Attribute> huAttributes = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Attribute.class, hu)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Attribute.COLUMNNAME_M_HU_ID, hu.getM_HU_ID())
				.addEqualsFilter(I_M_HU_Attribute.COLUMNNAME_M_Attribute_ID, attributeId)
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
	@Nullable
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

	@Override
	public void flush()
	{
		// nothing because there is no internal cache
	}
}
