package de.metas.ui.web.process.descriptor;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.util.Check;

import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.process.DocumentPreconditionsContext;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Wraps {@link RelatedProcessDescriptor} and {@link ProcessDescriptor} in one java object so we would be able to easily process, filter and process it in streams.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class RelatedProcessDescriptorWrapper
{
	public static final RelatedProcessDescriptorWrapper of(final RelatedProcessDescriptor relatedProcessDescriptor, final ProcessDescriptor processDescriptor)
	{
		return new RelatedProcessDescriptorWrapper(relatedProcessDescriptor, processDescriptor);
	}

	private final RelatedProcessDescriptor relatedProcessDescriptor;
	private final ProcessDescriptor processDescriptor;

	private RelatedProcessDescriptorWrapper(final RelatedProcessDescriptor relatedProcessDescriptor, final ProcessDescriptor processDescriptor)
	{
		super();

		Check.assumeNotNull(relatedProcessDescriptor, "Parameter relatedProcessDescriptor is not null");
		Check.assumeNotNull(processDescriptor, "Parameter processDescriptor is not null");
		Check.assume(relatedProcessDescriptor.getAD_Process_ID() == processDescriptor.getAD_Process_ID(), "AD_Process_ID matching for {} and {}", relatedProcessDescriptor, processDescriptor);

		this.relatedProcessDescriptor = relatedProcessDescriptor;
		this.processDescriptor = processDescriptor;
	}

	public ProcessDescriptor getProcessDescriptor()
	{
		return processDescriptor;
	}

	public boolean isExecutionGranted(final IUserRolePermissions permissions)
	{
		return processDescriptor.isExecutionGranted(permissions);
	}

	public boolean isPreconditionsApplicable(final DocumentPreconditionsContext preconditionsContext)
	{
		return processDescriptor.isPreconditionsApplicable(preconditionsContext);
	}

	public boolean isQuickAction()
	{
		return relatedProcessDescriptor.isWebuiQuickAction();
	}

	public boolean isDefaultQuickAction()
	{
		return relatedProcessDescriptor.isWebuiDefaultQuickAction();
	}
}
