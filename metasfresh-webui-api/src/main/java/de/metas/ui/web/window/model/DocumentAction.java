package de.metas.ui.web.window.model;

import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.ad.process.ISvrProcessPrecondition.PreconditionsContext;
import org.adempiere.util.Check;
import org.slf4j.Logger;

import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;

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

public class DocumentAction
{
	private static final Logger logger = LogManager.getLogger(DocumentAction.class);

	public static final Builder builder()
	{
		return new Builder();
	}

	public static enum DocumentActionType
	{
		Form, Workflow, Process, Report
	};

	private final int actionId;
	private final ITranslatableString caption;
	private final ITranslatableString description;
	private final DocumentActionType type;
	private final Class<? extends ISvrProcessPrecondition> preconditionsClass;

	private DocumentAction(final Builder builder)
	{
		super();

		actionId = builder.actionId;
		Check.assume(actionId > 0, "actionId > 0");

		caption = builder.caption;
		Check.assumeNotNull(caption, "Parameter caption is not null");

		description = builder.description;
		Check.assumeNotNull(description, "Parameter description is not null");

		type = builder.type;
		Check.assumeNotNull(type, "Parameter type is not null");

		preconditionsClass = builder.preconditionsClass;
	}

	public int getActionId()
	{
		return actionId;
	}

	public ITranslatableString getCaption()
	{
		return caption;
	}

	public ITranslatableString getDescription()
	{
		return description;
	}

	public DocumentActionType getType()
	{
		return type;
	}

	public boolean hasPreconditions()
	{
		return preconditionsClass != null;
	}

	public Class<? extends ISvrProcessPrecondition> getPreconditionsClass()
	{
		return preconditionsClass;
	}

	public boolean isApplicableOn(final PreconditionsContext context)
	{
		if (!hasPreconditions())
		{
			return true;
		}

		try
		{
			final ISvrProcessPrecondition preconditions = preconditionsClass.newInstance();
			return preconditions.isPreconditionApplicable(context);
		}
		catch (Exception ex)
		{
			logger.warn("Failed to determine if preconditions of {} are appliable on {}. Considering NOT appliable.", this, context, ex);
			return false;
		}
	}

	public static final class Builder
	{
		private int actionId;
		private ITranslatableString caption;
		private ITranslatableString description;
		private DocumentActionType type;
		private Class<? extends ISvrProcessPrecondition> preconditionsClass;

		private Builder()
		{
			super();
		}

		public DocumentAction build()
		{
			return new DocumentAction(this);
		}

		public Builder setActionId(final int actionId)
		{
			this.actionId = actionId;
			return this;
		}

		public Builder setCaption(final ITranslatableString caption)
		{
			this.caption = caption;
			return this;
		}

		public Builder setDescription(final ITranslatableString description)
		{
			this.description = description;
			return this;
		}

		public Builder setType(final DocumentActionType type)
		{
			this.type = type;
			return this;
		}

		public Builder setPreconditionsClass(final Class<? extends ISvrProcessPrecondition> preconditionsClass)
		{
			this.preconditionsClass = preconditionsClass;
			return this;
		}
	}

}
