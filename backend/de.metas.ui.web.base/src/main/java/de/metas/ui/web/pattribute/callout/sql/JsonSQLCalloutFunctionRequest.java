package de.metas.ui.web.pattribute.callout.sql;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonSQLCalloutFunctionRequest
{
	@Nullable String contextTableName;
	int contextRecordId;
	@Nullable String contextColumnName;
	@NonNull AttributeId attributeId;
	@NonNull AttributeCode attributeCode;
	@NonNull Map<String, Attribute> attributes;

	//
	//
	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Attribute
	{
		@NonNull AttributeId attributeId;
		@NonNull AttributeCode attributeCode;
		@Nullable Object value;
	}
}
