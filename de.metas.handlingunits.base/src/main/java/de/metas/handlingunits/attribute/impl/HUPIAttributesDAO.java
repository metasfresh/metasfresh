package de.metas.handlingunits.attribute.impl;

import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.util.CacheCtx;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.attribute.PIAttributes;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;

public class HUPIAttributesDAO implements IHUPIAttributesDAO
{
	private static final transient Logger logger = LogManager.getLogger(HUPIAttributesDAO.class);

	/**
	 * Retrieve all direct attributes, including those who are not active for current PI Version.
	 *
	 * @param version
	 * @return list of attributes
	 * @see #retrieveDirectPIAttributes(I_M_HU_PI_Version)
	 */
	public PIAttributes retrieveDirectPIAttributes(final HuPackingInstructionsId piId)
	{
		final HuPackingInstructionsVersionId piVersionId = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersionId(piId);
		return retrieveDirectPIAttributes(Env.getCtx(), piVersionId);
	}

	@Cached(cacheName = I_M_HU_PI_Attribute.Table_Name + "#by#" + I_M_HU_PI_Attribute.COLUMNNAME_M_HU_PI_Version_ID)
	/* package */PIAttributes retrieveDirectPIAttributes(
			@CacheCtx final Properties ctx,
			final HuPackingInstructionsVersionId piVersionId)
	{
		final IQueryBuilder<I_M_HU_PI_Attribute> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_PI_Attribute.class, ctx, ITrx.TRXNAME_None);

		queryBuilder.filter(new EqualsQueryFilter<I_M_HU_PI_Attribute>(I_M_HU_PI_Attribute.COLUMNNAME_M_HU_PI_Version_ID, piVersionId));

		queryBuilder.orderBy()
				.addColumn(I_M_HU_PI_Attribute.COLUMNNAME_SeqNo, Direction.Ascending, Nulls.Last)
				.addColumn(I_M_HU_PI_Attribute.COLUMNNAME_M_Attribute_ID, Direction.Ascending, Nulls.Last);

		final ImmutableList<I_M_HU_PI_Attribute> piAttributes = queryBuilder.create()
				// Retrieve all attributes, including those inactive
				// .setOnlyActiveRecords(true)
				.listImmutable(I_M_HU_PI_Attribute.class); // immutable because this is a cached method

		//
		// We are fetching out of transaction => make all PI Attributes readonly
		for (final I_M_HU_PI_Attribute piAttribute : piAttributes)
		{
			InterfaceWrapperHelper.setSaveDeleteDisabled(piAttribute, true);
		}

		//
		return PIAttributes.of(piAttributes);
	}

	@Override
	public PIAttributes retrievePIAttributes(@NonNull final HuPackingInstructionsVersionId piVersionId)
	{
		//
		// Retrieve and add Direct Attributes
		// NOTE: we want to make use of caching, that's why we are retrieving out of transaction.
		// Anyway, this is master data and this method is not supposed to be used when you want to retrieve master data for modifying it.
		PIAttributes piAttributes = retrieveDirectPIAttributes(Env.getCtx(), piVersionId);

		//
		// Retrieve an add template attributes (from NoPI)
		// only if given version is not of NoPI
		if (!piVersionId.isTemplate())
		{
			final PIAttributes templatePIAttributes = retrieveDirectPIAttributes(Env.getCtx(), HuPackingInstructionsVersionId.TEMPLATE);
			piAttributes = piAttributes.addIfAbsent(templatePIAttributes);
		}

		return piAttributes;
	}

	@Override
	public boolean isTemplateAttribute(final I_M_HU_PI_Attribute huPIAttribute)
	{
		logger.trace("Checking if {} is a template attribute", huPIAttribute);

		//
		// If the PI attribute is from template then it's a template attribute
		final HuPackingInstructionsVersionId piVersionId = HuPackingInstructionsVersionId.ofRepoId(huPIAttribute.getM_HU_PI_Version_ID());
		if (piVersionId.isTemplate())
		{
			if (!huPIAttribute.isActive())
			{
				logger.trace("Considering {} NOT a template attribute because even if it is direct template attribute it's INACTIVE", huPIAttribute);
				return false;
			}
			else
			{
				logger.trace("Considering {} a template attribute because it is direct template attribute", huPIAttribute);
				return true;
			}
		}

		//
		// Get the Template PI attributes and search if this attribute is defined there.
		final AttributeId attributeId = AttributeId.ofRepoId(huPIAttribute.getM_Attribute_ID());
		final Properties ctx = InterfaceWrapperHelper.getCtx(huPIAttribute);
		final PIAttributes templatePIAttributes = retrieveDirectPIAttributes(ctx, HuPackingInstructionsVersionId.TEMPLATE);
		if (templatePIAttributes.hasActiveAttribute(attributeId))
		{
			logger.trace("Considering {} a template attribute because we found M_Attribute_ID={} in template attributes", huPIAttribute, attributeId);
			return true;
		}

		//
		// Not a template attribute
		logger.trace("Considering {} NOT a template attribute", huPIAttribute);
		return false;
	}

	@Override
	public void deleteByVersionId(@NonNull final HuPackingInstructionsVersionId versionId)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_PI_Attribute.class)
				.addEqualsFilter(I_M_HU_PI_Attribute.COLUMN_M_HU_PI_Version_ID, versionId)
				.create()
				.delete();
	}
}
