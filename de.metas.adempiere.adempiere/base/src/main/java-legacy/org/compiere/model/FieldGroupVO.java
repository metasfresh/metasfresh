package org.compiere.model;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.util.Check;

/**
 * Immutable Field group definition (value object).
 * 
 * @author tsa
 *
 */
public final class FieldGroupVO
{
	public static final FieldGroupVO build(final String fieldGroupName, final FieldGroupType fieldGroupType, final boolean collapsedByDefault)
	{
		// Optimization: if we were asked for a "null" field group, reuse our null instance
		if (Check.isEmpty(fieldGroupName)
				&& (fieldGroupType == null || fieldGroupType == NULL.getFieldGroupType()))
		{
			return NULL;
		}

		return new FieldGroupVO(fieldGroupName, fieldGroupType, collapsedByDefault);
	}

	public static final FieldGroupVO NULL = new FieldGroupVO(null, null, false);

	/** Field group type */
	public static enum FieldGroupType
	{
		/** Not collapsible panel */
		Label
		/** Collapsible panel */
		, Collapsible
		/** Horizontal tab */
		, Tab;

		/**
		 * Gets the {@link FieldGroupType} of given code.
		 * 
		 * @param code {@link X_AD_FieldGroup#FIELDGROUPTYPE_Label}, {@link X_AD_FieldGroup#FIELDGROUPTYPE_Tab}, {@link X_AD_FieldGroup#FIELDGROUPTYPE_Collapse} etc
		 * @param defaultType
		 * @return field group type or <code>defaultType</code> if no type was found for code.
		 */
		public static final FieldGroupType forCodeOrDefault(final String code, final FieldGroupType defaultType)
		{
			final FieldGroupType type = code2type.get(code);
			return type == null ? defaultType : type;
		}

		private static final ImmutableMap<String, FieldGroupType> code2type = ImmutableMap.<String, FieldGroupType> builder()
				.put(X_AD_FieldGroup.FIELDGROUPTYPE_Label, Label)
				.put(X_AD_FieldGroup.FIELDGROUPTYPE_Tab, Tab)
				.put(X_AD_FieldGroup.FIELDGROUPTYPE_Collapse, Collapsible)
				.build();
	}

	private final String fieldGroupName;
	private final FieldGroupType fieldGroupType;
	private final boolean collapsedByDefault;

	private FieldGroupVO(final String fieldGroupName, final FieldGroupType fieldGroupType, final boolean collapsedByDefault)
	{
		super();

		this.fieldGroupName = fieldGroupName == null ? "" : fieldGroupName;
		this.fieldGroupType = fieldGroupType == null ? FieldGroupType.Label : fieldGroupType;
		this.collapsedByDefault = collapsedByDefault;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("name", fieldGroupName)
				.add("type", fieldGroupType)
				.add("collapsedByDefault", collapsedByDefault)
				.toString();
	}

	public String getFieldGroupName()
	{
		return fieldGroupName;
	}

	public FieldGroupType getFieldGroupType()
	{
		return fieldGroupType;
	}

	public boolean isCollapsedByDefault()
	{
		return collapsedByDefault;
	}
}
