package org.adempiere.ad.service.impl;

import de.metas.ad_reference.ReferenceId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;

@Value
public class LookupDisplayColumn
{
	String columnName;
	boolean isTranslated;
	int displayType;
	ReferenceId AD_Reference_ID;
	String columnSQL;
	boolean isVirtual;
	String formatPattern;

	@Builder
	private LookupDisplayColumn(
			final String columnName,
			final String columnSQL,
			final boolean isTranslated,
			final int ad_Reference_ID,
			final ReferenceId ad_Reference_Value_ID,
			final String formatPattern)
	{
		this.columnName = columnName;
		this.isTranslated = isTranslated;
		this.displayType = ad_Reference_ID;
		this.AD_Reference_ID = ad_Reference_Value_ID;
		this.columnSQL = columnSQL;
		this.isVirtual = !Check.isBlank(this.columnSQL);
		this.formatPattern = formatPattern;
	}
}
