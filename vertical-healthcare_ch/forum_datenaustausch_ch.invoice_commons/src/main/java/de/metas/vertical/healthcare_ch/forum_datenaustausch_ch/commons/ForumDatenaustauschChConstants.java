package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ForumDatenaustauschChConstants
{
	/**
	 * Note: to activate this spring profile, you can add an AD_SysConfig with
	 * {@code de.metas.spring.profiles.activeN = de.metas.vertical.cables}.
	 *
	 * There is a template AD_Sysconfig record for further info.
	 */
	public static final String PROFILE = "de.metas.vertical.healthcare.forum_datenaustausch_ch";

	public static final String INVOICE_EXPORT_PROVIDER_ID = "forum-datenaustausch.ch-invoice";

	public static final String DUNNING_EXPORT_PROVIDER_ID = "forum-datenaustausch.ch-dunning";
}
