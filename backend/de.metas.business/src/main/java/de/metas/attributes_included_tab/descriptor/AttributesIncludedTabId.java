package de.metas.attributes_included_tab.descriptor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_AttributeSet_IncludedTab;

@Value
public class AttributesIncludedTabId implements RepoIdAware
{
	int repoId;

	private AttributesIncludedTabId(final int repoId)
	{
		this.repoId = Check.assumeGreaterOrEqualToZero(repoId, I_M_AttributeSet_IncludedTab.COLUMNNAME_M_AttributeSet_IncludedTab_ID);
	}

	@JsonCreator
	public static AttributesIncludedTabId ofRepoId(final int repoId)
	{
		final AttributesIncludedTabId id = ofRepoIdOrNull(repoId);
		if (id == null)
		{
			throw new AdempiereException("Invalid repoId: " + repoId);
		}
		return id;
	}

	public static AttributesIncludedTabId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new AttributesIncludedTabId(repoId) : null;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
