/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.fulltextsearch.config.model_interceptor;

import de.metas.elasticsearch.model.I_ES_FTS_Config;
import de.metas.fulltextsearch.config.FTSConfigId;
import de.metas.fulltextsearch.config.FTSConfigService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_ES_FTS_Config.class)
@Component
public class ES_FTS_Config
{
	private final FTSConfigService ftsConfigService;

	public ES_FTS_Config(final FTSConfigService ftsConfigService) {this.ftsConfigService = ftsConfigService;}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = I_ES_FTS_Config.COLUMNNAME_ES_DocumentToIndexTemplate)
	public void onAfterSave(@NonNull final I_ES_FTS_Config record)
	{
		ftsConfigService.updateConfigFields(record);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void onBeforeDelete(@NonNull final I_ES_FTS_Config record)
	{
		final FTSConfigId configId = FTSConfigId.ofRepoId(record.getES_FTS_Config_ID());
		ftsConfigService.deleteDependingData(configId);
	}

}
