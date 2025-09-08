package de.metas.acct.spi.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.copy_with_details.template.CopyTemplateCustomizer;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.POInfo;
import org.compiere.model.copy.ValueToCopy;
import org.springframework.stereotype.Component;

@Component
public class GL_JournalLine_CopyTemplateCustomizer implements CopyTemplateCustomizer
{
	/**
	 * List of column names which needs to be copied directly when using copy with details functionality (even if those columns are marked as IsComputed)
	 */
	private static final ImmutableSet<String> COLUMNNAMES_ToCopyDirectly = ImmutableSet.of(
			I_GL_JournalLine.COLUMNNAME_GL_JournalLine_Group,
			I_GL_JournalLine.COLUMNNAME_IsAllowAccountDR,
			I_GL_JournalLine.COLUMNNAME_IsAllowAccountCR
	);

	@Override
	public String getTableName() {return I_GL_JournalLine.Table_Name;}

	@Override
	public ValueToCopy extractValueToCopy(final POInfo poInfo, final String columnName)
	{
		return COLUMNNAMES_ToCopyDirectly.contains(columnName) ? ValueToCopy.DIRECT_COPY : ValueToCopy.NOT_SPECIFIED;
	}
}
