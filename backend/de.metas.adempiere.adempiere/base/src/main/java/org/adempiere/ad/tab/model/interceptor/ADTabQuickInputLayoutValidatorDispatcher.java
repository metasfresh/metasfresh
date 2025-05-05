package org.adempiere.ad.tab.model.interceptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.quickinput.config.QuickInputConfigLayout;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
class ADTabQuickInputLayoutValidatorDispatcher
{
	private final ImmutableMap<String, ADTabQuickInputLayoutValidator> validatorsByHandledTablename;

	public ADTabQuickInputLayoutValidatorDispatcher(final Optional<List<ADTabQuickInputLayoutValidator>> validators)
	{
		this.validatorsByHandledTablename = Maps.uniqueIndex(validators.orElseGet(ImmutableList::of), ADTabQuickInputLayoutValidator::getHandledTabQuickInputTableName);
	}

	public void validateQuickInputLayout(@NonNull String tabTablename, @NonNull final QuickInputConfigLayout layout)
	{
		final ADTabQuickInputLayoutValidator validator = validatorsByHandledTablename.get(tabTablename);
		if (validator != null)
		{
			validator.validateTabQuickInputLayout(layout);
		}
		else
		{
			throw new AdempiereException("Setting quick input layout is not supported for " + tabTablename);
		}
	}
}
