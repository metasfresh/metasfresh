/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalsystem;

import com.google.common.collect.ImmutableMap;
import de.metas.externalsystem.process.InvokeAlbertaAction;
import de.metas.externalsystem.process.InvokeGRSSignumAction;
import de.metas.externalsystem.process.InvokeOtherAction;
import de.metas.externalsystem.process.InvokePCMAction;
import de.metas.externalsystem.process.InvokeScriptedImportConversionAction;
import de.metas.externalsystem.process.InvokeWooCommerceAction;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import java.util.Optional;

@UtilityClass
public class ExternalSystemProcesses
{
	private static final ImmutableMap<ExternalSystemType, String> PROCESS_CLASS_NAMES_BY_TYPE = ImmutableMap.<ExternalSystemType, String>builder()
			.put(ExternalSystemType.Alberta, InvokeAlbertaAction.class.getName())
			.put(ExternalSystemType.Shopware6, InvokeAlbertaAction.class.getName())
			.put(ExternalSystemType.Other, InvokeOtherAction.class.getName())
			.put(ExternalSystemType.WOO, InvokeWooCommerceAction.class.getName())
			.put(ExternalSystemType.GRSSignum, InvokeGRSSignumAction.class.getName())
			.put(ExternalSystemType.ProCareManagement, InvokePCMAction.class.getName())
			.put(ExternalSystemType.ScriptedImportConversion, InvokeScriptedImportConversionAction.class.getName())
			.build();

	@NonNull
	public static String getExternalSystemProcessClassName(final ExternalSystemType externalSystemType) {
		return Optional.ofNullable(PROCESS_CLASS_NAMES_BY_TYPE.get(externalSystemType))
				.orElseThrow(() -> new AdempiereException("ExternalSystemType doesn't have a process class"));
	}
}
