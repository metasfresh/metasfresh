package org.adempiere.mm.attributes.api.impl;

import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.callout.M_Attribute;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.compiere.model.I_AD_Ref_List;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeUse;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_AttributeValue_Mapping;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.junit.Ignore;

import javax.annotation.Nullable;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Base context and helpers for {@link M_Attribute}s related tests.
 *
 * @author tsa
 *
 */
@Ignore
public class AttributesTestHelper
{
	public final Properties ctx;
	public PlainContextAware context;

	public I_AD_JavaClass_Type attributeGeneratorType;

	public AttributesTestHelper()
	{
		ctx = Env.getCtx();
		context = PlainContextAware.newOutOfTrx(ctx);

		attributeGeneratorType = createAD_JavaClass_Type(IAttributeValueGenerator.class.getName());

	}

	public I_M_AttributeSet createM_AttributeSet(final I_M_Attribute... attributes)
	{
		final I_M_AttributeSet as = InterfaceWrapperHelper.create(ctx, I_M_AttributeSet.class, ITrx.TRXNAME_None);
		as.setIsInstanceAttribute(true);
		save(as);

		for (final I_M_Attribute attribute : attributes)
		{
			createM_AttributeUse(as, attribute);
		}
		return as;
	}

	public void createM_AttributeUse(final I_M_AttributeSet as, final I_M_Attribute attribute)
	{
		final I_M_AttributeUse attributeUse = InterfaceWrapperHelper.newInstance(I_M_AttributeUse.class, as);
		attributeUse.setM_AttributeSet_ID(as.getM_AttributeSet_ID());
		attributeUse.setM_Attribute_ID(attribute.getM_Attribute_ID());
		save(attributeUse);
	}

	public I_M_Attribute createM_Attribute(final I_AD_JavaClass javaClass)
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.create(ctx, I_M_Attribute.class, ITrx.TRXNAME_None);
		attribute.setAD_JavaClass_ID(javaClass != null ? javaClass.getAD_JavaClass_ID() : -1);
		save(attribute);
		return attribute;
	}

	public I_M_Attribute createM_Attribute_TypeList(final String name)
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.newInstance(I_M_Attribute.class, context);
		attribute.setValue(name);
		attribute.setName(name);
		attribute.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_List);
		save(attribute);
		return attribute;
	}

	public AttributeListValue createM_AttributeValue(
			@NonNull final I_M_Attribute attribute,
			@Nullable final String value)
	{
		return createM_AttributeValue(attribute, null, value);
	}

	public AttributeListValue createM_AttributeValue(
			@NonNull final I_M_Attribute attribute,
			@Nullable final Integer valueRepoId,
			@Nullable final String value)
	{
		final I_M_AttributeValue record = InterfaceWrapperHelper.newInstance(I_M_AttributeValue.class, context);
		record.setM_Attribute(attribute);
		record.setValue(value);
		record.setName("Name_" + value);
		if (valueRepoId != null)
		{
			record.setM_AttributeValue_ID(valueRepoId);
		}
		save(record);
		return AttributeDAO.toAttributeListValue(record);
	}

	public I_M_AttributeValue_Mapping createM_AttributeValue_Mapping(
			final AttributeListValue attributeValue,
			final AttributeListValue attributeValueTo)
	{
		final I_M_AttributeValue_Mapping attributeValueMapping = InterfaceWrapperHelper.newInstance(I_M_AttributeValue_Mapping.class, context);
		attributeValueMapping.setM_AttributeValue_ID(attributeValue.getId().getRepoId());
		attributeValueMapping.setM_AttributeValue_To_ID(attributeValueTo.getId().getRepoId());
		save(attributeValueMapping);
		return attributeValueMapping;
	}

	public I_AD_JavaClass createAD_JavaClass(final String classname)
	{
		final I_AD_JavaClass javaClassDef = InterfaceWrapperHelper.create(ctx, I_AD_JavaClass.class, ITrx.TRXNAME_None);
		javaClassDef.setAD_JavaClass_Type(attributeGeneratorType);
		javaClassDef.setClassname(classname);
		save(javaClassDef);
		return javaClassDef;
	}

	public I_AD_JavaClass createAD_JavaClass(final Class<? extends IAttributeValueGenerator> clazz)
	{
		return createAD_JavaClass(clazz.getName());
	}

	private I_AD_JavaClass_Type createAD_JavaClass_Type(final String interfaceClassname)
	{
		final I_AD_JavaClass_Type javaClassTypeDef = InterfaceWrapperHelper.create(ctx, I_AD_JavaClass_Type.class, ITrx.TRXNAME_None);
		javaClassTypeDef.setClassname(interfaceClassname);
		save(javaClassTypeDef);
		return javaClassTypeDef;
	}

	public void createAD_Ref_List_Items(final int adReferenceId, final String... values)
	{
		for (final String value : values)
		{
			final I_AD_Ref_List item = InterfaceWrapperHelper.create(ctx, I_AD_Ref_List.class, ITrx.TRXNAME_None);
			item.setAD_Reference_ID(adReferenceId);
			item.setValue(value);
			item.setName(value);
			item.setValueName(value);
			save(item);
		}
	}

	/**
	 * Method needed to make sure the attribute was not already created
	 * Normally, this will never happen anywhere else except testing
	 *
	 * @param name
	 * @return
	 */
	public I_M_Attribute retrieveAttributeValue(String name)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Attribute.class).addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Attribute.COLUMNNAME_Value, name)
				.create()
				.firstOnly(I_M_Attribute.class);
	}

	public I_M_Attribute createM_Attribute(
			final String name,
			final String valueType,
			@Nullable final Class<?> javaClass,
			@Nullable final I_C_UOM uom,
			final boolean isInstanceAttribute)
	{
		return attribute()
				.attributeCode(AttributeCode.ofString(name))
				.valueType(AttributeValueType.ofCode(valueType))
				.javaClass(javaClass)
				.uom(uom)
				.instanceAttribute(isInstanceAttribute)
				.build();
	}

	@Builder(builderMethodName = "attribute", builderClassName = "$AttributeBuilder")
	private I_M_Attribute createM_Attribute(
			@NonNull final AttributeCode attributeCode,
			@NonNull final AttributeValueType valueType,
			@Nullable final Class<?> javaClass,
			@Nullable final I_C_UOM uom,
			@Nullable final Boolean instanceAttribute,
			final boolean storageRelevantAttribute)
	{

		final I_AD_JavaClass javaClassDef;
		if (javaClass != null)
		{
			javaClassDef = newInstanceOutOfTrx(I_AD_JavaClass.class);
			javaClassDef.setName(javaClass.getName());
			javaClassDef.setClassname(javaClass.getName());
			save(javaClassDef);
		}
		else
		{
			javaClassDef = null;
		}

		final I_M_Attribute attr;

		// make sure the attribute was not already defined
		final I_M_Attribute existingAttribute = retrieveAttributeValue(attributeCode.getCode());
		if (existingAttribute != null)
		{
			attr = existingAttribute;
		}

		else
		{
			attr = newInstanceOutOfTrx(I_M_Attribute.class);
		}
		attr.setValue(attributeCode.getCode());
		attr.setName(attributeCode.getCode());
		attr.setAttributeValueType(valueType.getCode());

		//
		// Assume all attributes active and non-mandatory
		attr.setIsActive(true);
		attr.setIsMandatory(false);

		//
		// Configure ASI usage
		attr.setIsInstanceAttribute(instanceAttribute != null ? instanceAttribute : true);

		//
		// Configure JC
		attr.setAD_JavaClass_ID(javaClassDef != null ? javaClassDef.getAD_JavaClass_ID() : -1);

		//
		// Configure UOM
		attr.setC_UOM_ID(uom != null ? uom.getC_UOM_ID() : -1);
		
		attr.setIsStorageRelevant(storageRelevantAttribute);

		saveRecord(attr);
		
		return attr;
	}

	public I_M_Attribute createM_Attribute(
			final String name,
			final String valueType,
			final Class<?> javaClass,
			final boolean isInstanceAttribute)
	{
		final I_C_UOM uom = null;
		return createM_Attribute(name, valueType, javaClass, uom, isInstanceAttribute);
	}

	public I_M_Attribute createM_Attribute(
			final String name,
			final String valueType,
			final boolean isInstanceAttribute)
	{
		final Class<?> javaClass = null;
		final I_M_Attribute attr = createM_Attribute(name, valueType, javaClass, isInstanceAttribute);
		save(attr);

		return attr;
	}
}
