package de.metas.ui.web.window.descriptor;

import java.util.Set;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

/* package */class LambdaDocumentFieldCallout implements IDocumentFieldCallout
{
	private final String id;
	private final Set<String> dependsOnFieldNames;
	private final ILambdaDocumentFieldCallout lambdaCallout;

	public LambdaDocumentFieldCallout(final String triggeringFieldName, final ILambdaDocumentFieldCallout lambdaCallout)
	{
		super();

		Check.assumeNotEmpty(triggeringFieldName, "triggeringFieldName is not empty");
		Check.assumeNotNull(lambdaCallout, "Parameter lambdaCallout is not null");

		id = "lambda-" + triggeringFieldName + "-" + lambdaCallout.toString();
		dependsOnFieldNames = ImmutableSet.of(triggeringFieldName);
		this.lambdaCallout = lambdaCallout;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.toString();
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return dependsOnFieldNames;
	}

	@Override
	public void execute(final ICalloutExecutor executor, final ICalloutField field) throws Exception
	{
		lambdaCallout.execute(field);
	}

}
