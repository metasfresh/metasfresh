package de.metas.handlingunits.test.misc.builders;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;

import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.strategy.IAttributeAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.IAttributeStrategy;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferStrategy;
import de.metas.handlingunits.attribute.strategy.impl.NullAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.impl.NullSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.impl.SkipHUAttributeTransferStrategy;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;

/**
 * Builder which eases the way {@link I_M_HU_PI_Attribute}s are created. <i>See constructor implementation(s) for initial parameter values.</i>
 *
 * @author al
 */
@ToString
public class HUPIAttributeBuilder
{
	public static HUPIAttributeBuilder newInstance(@NonNull final AttributeId attributeId)
	{
		return new HUPIAttributeBuilder(attributeId);
	}

	public static HUPIAttributeBuilder newInstance(@NonNull final I_M_Attribute attribute)
	{
		return new HUPIAttributeBuilder(attribute);
	}

	private final AttributeId attributeId;

	private I_M_HU_PI_Version huPIVersion;
	private String propagationType;

	private Class<? extends IAttributeSplitterStrategy> splitterStrategyClass;
	private Class<? extends IAttributeAggregationStrategy> aggregationStrategyClass;
	private Class<? extends IHUAttributeTransferStrategy> transferStrategyClass;

	private boolean isInstanceAttribute = true;
	private boolean isMandatory = false;
	private Boolean useInASI = false;

	private HUPIAttributeBuilder(final I_M_Attribute attribute)
	{
		this(AttributeId.ofRepoId(attribute.getM_Attribute_ID()));
		setInstanceAttribute(attribute.isInstanceAttribute());
		setMandatory(attribute.isMandatory());
	}

	private HUPIAttributeBuilder(@NonNull final AttributeId attributeId)
	{
		this.attributeId = attributeId;

		huPIVersion = null;
		propagationType = X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation;

		splitterStrategyClass = NullSplitterStrategy.class;
		aggregationStrategyClass = NullAggregationStrategy.class;
		transferStrategyClass = SkipHUAttributeTransferStrategy.class;
	}

	public HUPIAttributeBuilder setM_HU_PI(final I_M_HU_PI huPI)
	{
		huPIVersion = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersion(huPI);
		return this;
	}

	public HUPIAttributeBuilder setM_HU_PI_Version(final I_M_HU_PI_Version huPIVersion)
	{
		this.huPIVersion = huPIVersion;
		return this;
	}

	public HUPIAttributeBuilder setM_HU_PI_Version(final HuPackingInstructionsVersionId versionId)
	{
		huPIVersion = Services.get(IHandlingUnitsDAO.class).retrievePIVersionById(versionId);
		return this;
	}

	public HUPIAttributeBuilder setPropagationType(final String propagationType)
	{
		this.propagationType = propagationType;
		return this;
	}

	public HUPIAttributeBuilder setSplitterStrategyClass(final Class<? extends IAttributeSplitterStrategy> splitterStrategyClass)
	{
		this.splitterStrategyClass = splitterStrategyClass;
		return this;
	}

	public HUPIAttributeBuilder setAggregationStrategyClass(final Class<? extends IAttributeAggregationStrategy> aggregationStrategyClass)
	{
		this.aggregationStrategyClass = aggregationStrategyClass;
		return this;
	}

	public HUPIAttributeBuilder setTransferStrategyClass(final Class<? extends IHUAttributeTransferStrategy> transferStrategyClass)
	{
		this.transferStrategyClass = transferStrategyClass;
		return this;
	}

	public HUPIAttributeBuilder setInstanceAttribute(final boolean isInstanceAttribute)
	{
		this.isInstanceAttribute = isInstanceAttribute;
		return this;
	}

	public HUPIAttributeBuilder setMandatory(final boolean isMandatory)
	{
		this.isMandatory = isMandatory;
		return this;
	}

	public HUPIAttributeBuilder setUseInASI(final boolean useInASI)
	{
		this.useInASI = useInASI;
		return this;
	}

	public I_M_HU_PI_Attribute create()
	{
		return create(Env.getCtx());
	}

	public I_M_HU_PI_Attribute create(final Properties ctx)
	{
		final boolean alreadyExists = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_PI_Attribute.class)
				.addEqualsFilter(I_M_HU_PI_Attribute.COLUMNNAME_M_HU_PI_Version_ID, huPIVersion.getM_HU_PI_Version_ID())
				.addEqualsFilter(I_M_HU_PI_Attribute.COLUMNNAME_M_Attribute_ID, attributeId)
				.create()
				.anyMatch();
		if (alreadyExists)
		{
			throw new AdempiereException("HU PI Attribute already exist for " + this);
		}

		final I_M_HU_PI_Attribute huPIAttr = InterfaceWrapperHelper.create(ctx, I_M_HU_PI_Attribute.class, ITrx.TRXNAME_None);
		huPIAttr.setM_Attribute_ID(attributeId.getRepoId());

		huPIAttr.setM_HU_PI_Version_ID(huPIVersion.getM_HU_PI_Version_ID());

		huPIAttr.setAD_Org_ID(huPIVersion.getAD_Org_ID());

		huPIAttr.setIsInstanceAttribute(isInstanceAttribute);
		huPIAttr.setIsMandatory(isMandatory);
		if (useInASI != null)
		{
			huPIAttr.setUseInASI(useInASI);
		}

		huPIAttr.setPropagationType(propagationType);

		huPIAttr.setSplitterStrategy_JavaClass_ID(createADJavaClassFromClass(ctx, splitterStrategyClass).getAD_JavaClass_ID());
		huPIAttr.setAggregationStrategy_JavaClass_ID(createADJavaClassFromClass(ctx, aggregationStrategyClass).getAD_JavaClass_ID());
		huPIAttr.setHU_TansferStrategy_JavaClass_ID(createADJavaClassFromClass(ctx, transferStrategyClass).getAD_JavaClass_ID());

		InterfaceWrapperHelper.save(huPIAttr);

		return huPIAttr;
	}

	private I_AD_JavaClass createADJavaClassFromClass(final Properties ctx, @NonNull final Class<? extends IAttributeStrategy> strategyClass)
	{
		final I_AD_JavaClass_Type strategyJavaClassType = InterfaceWrapperHelper.create(ctx, I_AD_JavaClass_Type.class, ITrx.TRXNAME_None);
		strategyJavaClassType.setClassname(strategyClass.getSuperclass().getName());
		InterfaceWrapperHelper.save(strategyJavaClassType);

		final I_AD_JavaClass strategyJavaClass = InterfaceWrapperHelper.create(ctx, I_AD_JavaClass.class, ITrx.TRXNAME_None);
		strategyJavaClass.setAD_JavaClass_Type(strategyJavaClassType);
		strategyJavaClass.setClassname(strategyClass.getName());
		InterfaceWrapperHelper.save(strategyJavaClass);

		return strategyJavaClass;
	}
}
