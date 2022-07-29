/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.shopware6.interceptor;

import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.product.ProductExternalReferenceType;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.export.availableforsales.ExportAvailableForSalesToShopwareExternalSystem;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_ExternalSystem_Config_Shopware6.class)
@Component
public class ExternalSystem_Config_Shopware6
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final ExternalSystemConfigRepo externalSystemConfigRepo;
	private final ExportAvailableForSalesToShopwareExternalSystem exportAvailableForSalesToShopwareExternalSystem;
	private final ExternalReferenceRepository externalReferenceRepository;

	public ExternalSystem_Config_Shopware6(
			@NonNull final ExternalSystemConfigRepo externalSystemConfigRepo,
			@NonNull final ExportAvailableForSalesToShopwareExternalSystem exportAvailableForSalesToShopwareExternalSystem,
			@NonNull final ExternalReferenceRepository externalReferenceRepository)
	{
		this.externalSystemConfigRepo = externalSystemConfigRepo;
		this.exportAvailableForSalesToShopwareExternalSystem = exportAvailableForSalesToShopwareExternalSystem;
		this.externalReferenceRepository = externalReferenceRepository;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_Shopware6.COLUMNNAME_ExternalSystem_Config_ID })
	public void checkType(final I_ExternalSystem_Config_Shopware6 config)
	{
		final String parentType =
				externalSystemConfigRepo.getParentTypeById(ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID()));

		if (!ExternalSystemType.Shopware6.getCode().equals(parentType))
		{
			throw new AdempiereException("Invalid external system type!");
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_Shopware6.COLUMNNAME_PercentageOfAvailableForSalesToSync, I_ExternalSystem_Config_Shopware6.COLUMNNAME_IsSyncAvailableForSalesToShopware6 })
	public void syncAvailableForSalesToShopware(@NonNull final I_ExternalSystem_Config_Shopware6 config)
	{
		if (!config.isSyncAvailableForSalesToShopware6())
		{
			return;
		}

		final de.metas.audit.data.ExternalSystemParentConfigId configId = de.metas.audit.data.ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID());

		externalReferenceRepository.getExternalReferencesByTypeAndConfigId(ProductExternalReferenceType.PRODUCT, configId)
				.forEach(externalReference -> {
					final ProductId productId = ProductId.ofRepoId(externalReference.getRecordId());

					trxManager.runAfterCommit(() -> exportAvailableForSalesToShopwareExternalSystem.enqueueProductToBeExported(productId));
				});
	}
}
