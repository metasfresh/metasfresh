package de.metas.process;

import javax.annotation.Nullable;

import org.adempiere.util.lang.IAutoCloseable;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;

import com.google.common.collect.ImmutableList;

import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@UtilityClass
public class ProcessMDC
{
	public static final String NAME_AD_Process_ID = "AD_Process_ID";
	public static final String NAME_AD_PInstance_ID = "AD_PInstance_ID";

	public static IAutoCloseable putProcessAndInstanceId(
			@Nullable final AdProcessId adProcessId,
			@Nullable final PInstanceId pInstanceId)
	{
		final ImmutableList<MDCCloseable> closeables = ImmutableList.of(
				putAdProcessId(adProcessId),
				putPInstanceId(pInstanceId));

		return () -> closeables.forEach(MDCCloseable::close);
	}

	public static MDCCloseable putAdProcessId(@Nullable final AdProcessId adProcessId)
	{
		return MDC.putCloseable(
				NAME_AD_Process_ID,
				adProcessId != null ? String.valueOf(adProcessId.getRepoId()) : null);
	}

	public static MDCCloseable putPInstanceId(@Nullable final PInstanceId pinstanceId)
	{
		return MDC.putCloseable(
				NAME_AD_PInstance_ID,
				pinstanceId != null ? String.valueOf(pinstanceId.getRepoId()) : null);
	}
}
