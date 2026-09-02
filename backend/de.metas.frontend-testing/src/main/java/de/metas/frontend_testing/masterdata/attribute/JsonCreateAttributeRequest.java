package de.metas.frontend_testing.masterdata.attribute;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Creates (or upserts, by {@code Value}) an {@code M_Attribute} - including a LIST-type one with its allowed
 * values - and optionally links it into one or more existing {@code M_AttributeSet}s (via {@code M_AttributeUse}).
 * <p>
 * Needed so a Playwright {@code Backend.createMasterdata} call can define an arbitrary attribute (e.g. a
 * "size (cm)" LIST attribute) and attach it to a product's attribute set, the same way cucumber already can via
 * {@code M_Attribute_StepDef} / {@code M_AttributeValue_StepDef} / {@code M_AttributeUse_StepDef}.
 */
@Value
@Builder
@Jacksonized
public class JsonCreateAttributeRequest
{
	/** {@code M_Attribute.Value}. When omitted, a unique value is derived from the request's map-key identifier. */
	@Nullable String value;

	/** {@code M_Attribute.Name}. Defaults to {@link #value} when omitted. */
	@Nullable String name;

	/**
	 * The attribute's value type - either the {@link org.adempiere.mm.attributes.AttributeValueType} enum name
	 * ({@code "STRING"}, {@code "NUMBER"}, {@code "DATE"}, {@code "LIST"}) or its AD ref-list code
	 * ({@code "S"}, {@code "N"}, {@code "D"}, {@code "L"}). Defaults to {@code STRING} when omitted.
	 */
	@Nullable String attributeValueType;

	@Nullable Boolean isMandatory;
	@Nullable Boolean isStorageRelevant;

	/**
	 * {@code M_Attribute.IsInstanceAttribute}. Defaults to {@code N} on a new attribute (the DB column
	 * default) when omitted. Must be {@code true} for the attribute to be offered by the mobile
	 * Manufacturing receive's generic editable-attribute list - see
	 * {@code MaterialReceiptActivityHandler#buildEditableAttributes}, which reads only
	 * {@code IAttributeDAO#retrieveAttributes(attributeSetId, isInstanceAttribute=true)} (AC10).
	 */
	@Nullable Boolean isInstanceAttribute;

	/** Allowed values for a LIST-type attribute; ignored for any other {@link #attributeValueType}. */
	@Nullable List<ListValue> listValues;

	/**
	 * Names of already-existing {@code M_AttributeSet}s (e.g. {@code "LotSerial"}) to link this attribute into
	 * via {@code M_AttributeUse} - i.e. to make it available on any product carrying that attribute set.
	 */
	@Nullable List<String> attributeSetNames;

	@Value
	@Builder
	@Jacksonized
	public static class ListValue
	{
		@NonNull String value;
		@Nullable String name;
	}
}
