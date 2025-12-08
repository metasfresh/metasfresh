package de.metas.attributes_included_tab.descriptor;

import de.metas.i18n.ITranslatableString;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueType;

import javax.annotation.Nullable;

@Value
@Builder
public class AttributesIncludedTabFieldDescriptor
{
	@NonNull ITranslatableString caption;
	@NonNull ITranslatableString description;
	@NonNull AttributeId attributeId;
	@NonNull AttributeCode attributeCode;
	@NonNull AttributeValueType attributeValueType;
	boolean readOnlyValues;
	boolean mandatory;
	@Nullable UomId uomId;
}
