package org.adempiere.mm.attributes.api;

import de.metas.i18n.ITranslatableString;
import de.metas.javaclasses.JavaClassId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueType;
import org.compiere.util.DisplayType;

import javax.annotation.Nullable;
import java.util.Comparator;

@Value
@Builder
public class Attribute
{
	public static final Comparator<Attribute> ORDER_BY_DisplayName = Comparator.<Attribute, String>comparing(attribute -> attribute.getDisplayName().getDefaultValue())
			.thenComparing(Attribute::getAttributeId);

	@NonNull AttributeId attributeId;
	@NonNull AttributeCode attributeCode;
	@NonNull AttributeValueType valueType;
	@NonNull ITranslatableString displayName;
	@Nullable ITranslatableString description;
	@Nullable IStringExpression descriptionPattern;

	@Nullable IStringExpression defaultValueSQL;

	@Builder.Default boolean isActive = true;
	boolean isInstanceAttribute;
	boolean isMandatory;
	boolean isReadOnlyValues;
	boolean isPricingRelevant;
	boolean isStorageRelevant;

	@Nullable UomId uomId;

	boolean isHighVolume;
	@Nullable JavaClassId listValuesProviderJavaClassId;
	@Nullable AdValRuleId listValRuleId;
	@Nullable AttributeValuesOrderByType listOrderBy;

	public boolean isHighVolumeValuesList()
	{
		return this.isHighVolume && valueType.isList();
	}

	public int getNumberDisplayType()
	{
		return isInteger(uomId)
				? DisplayType.Integer
				: DisplayType.Number;
	}

	public static boolean isInteger(@Nullable final UomId uomId)
	{
		return uomId != null && UomId.equals(uomId, UomId.EACH);
	}

	public boolean isDefaultValueSet() {return defaultValueSQL != null;}

}
