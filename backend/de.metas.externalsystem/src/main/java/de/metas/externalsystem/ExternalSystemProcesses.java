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
import de.metas.externalsystem.process.InvokeWooCommerceAction;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

@UtilityClass
public class ExternalSystemProcesses
{
	private static final ImmutableMap<ExternalSystem.SystemValue, String> PROCESS_CLASS_NAMES_BY_INTERNAL_NAME = ImmutableMap.<ExternalSystem.SystemValue, String>builder()
			.put(ExternalSystem.SystemValue.Alberta, InvokeAlbertaAction.class.getName())
			.put(ExternalSystem.SystemValue.Shopware6, InvokeAlbertaAction.class.getName())
			.put(ExternalSystem.SystemValue.Other, InvokeOtherAction.class.getName())
			.put(ExternalSystem.SystemValue.WOO, InvokeWooCommerceAction.class.getName())
			.put(ExternalSystem.SystemValue.GRSSignum, InvokeGRSSignumAction.class.getName())
			.put(ExternalSystem.SystemValue.ProCareManagement, InvokePCMAction.class.getName())
			.build();

	@Nullable
	public static String getExternalSystemProcessClassName(final ExternalSystem.SystemValue systemValue) {
		return PROCESS_CLASS_NAMES_BY_INTERNAL_NAME.get(systemValue);
	}
}
