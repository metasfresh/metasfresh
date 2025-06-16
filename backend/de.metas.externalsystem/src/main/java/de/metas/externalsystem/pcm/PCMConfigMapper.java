/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.externalsystem.pcm;

import de.metas.externalsystem.model.I_ExternalSystem_Config_ProCareManagement_LocalFile;
import de.metas.externalsystem.pcm.source.PCMContentSourceLocalFile;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.Duration;

@UtilityClass
public class PCMConfigMapper
{
	@NonNull
	public static PCMContentSourceLocalFile buildContentSourceLocalFile(@NonNull final I_ExternalSystem_Config_ProCareManagement_LocalFile externalSystemConfigPCMLocalFile)
	{
		return PCMContentSourceLocalFile.builder()
				.rootLocation(externalSystemConfigPCMLocalFile.getLocalRootLocation())
				.fileNamePatternProduct(externalSystemConfigPCMLocalFile.getProductFileNamePattern())

				.fileNamePatternBPartner(externalSystemConfigPCMLocalFile.getBPartnerFileNamePattern())

				.fileNamePatternWarehouse(externalSystemConfigPCMLocalFile.getWarehouseFileNamePattern())

				.fileNamePatternPurchaseOrder(externalSystemConfigPCMLocalFile.getPurchaseOrderFileNamePattern())

				.processedDirectory(externalSystemConfigPCMLocalFile.getProcessedDirectory())
				.erroredDirectory(externalSystemConfigPCMLocalFile.getErroredDirectory())
				.pollingFrequency(Duration.ofMillis(externalSystemConfigPCMLocalFile.getFrequency()))

				.build();
	}
}
