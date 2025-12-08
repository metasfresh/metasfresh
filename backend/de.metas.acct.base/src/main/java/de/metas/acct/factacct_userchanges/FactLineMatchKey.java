package de.metas.acct.factacct_userchanges;

import de.metas.costing.CostElementId;
import de.metas.tax.api.TaxId;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.FactLine;
import org.compiere.util.Util;

import javax.annotation.Nullable;

@EqualsAndHashCode
public final class FactLineMatchKey
{
	private final String string;

	private FactLineMatchKey(@NonNull final String string)
	{
		this.string = StringUtils.trimBlankToNull(string);
		if (this.string == null)
		{
			throw new AdempiereException("invalid blank/null matching key");
		}
	}

	@Nullable
	public static FactLineMatchKey ofNullableString(final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		return stringNorm != null ? new FactLineMatchKey(stringNorm) : null;
	}

	public static FactLineMatchKey ofString(final String string)
	{
		return new FactLineMatchKey(string);
	}

	public static FactLineMatchKey ofFactLine(@NonNull final FactLine factLine)
	{
		return ofString(
				Util.ArrayKey.builder()
						// no need to include AD_Table_ID/Record_ID because we always match on document level
						.append(Math.max(factLine.getLine_ID(), 0))
						.append(factLine.getAccountConceptualName())
						.append(CostElementId.toRepoId(factLine.getCostElementId()))
						.append(TaxId.toRepoId(factLine.getTaxId()))
						.append(Math.max(factLine.getM_Locator_ID(), 0))
						.build()
						.toString()
		);
	}

	@Deprecated
	@Override
	public String toString() {return getAsString();}

	public String getAsString()
	{
		return string;
	}
}
