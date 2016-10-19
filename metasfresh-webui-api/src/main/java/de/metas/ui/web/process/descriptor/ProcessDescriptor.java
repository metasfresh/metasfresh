package de.metas.ui.web.process.descriptor;

import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.ad.process.ISvrProcessPrecondition.PreconditionsContext;
import org.adempiere.util.Check;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;

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

public final class ProcessDescriptor
{
	private static final Logger logger = LogManager.getLogger(ProcessDescriptor.class);

	public static final Builder builder()
	{
		return new Builder();
	}

	public static enum ProcessDescriptorType
	{
		Form, Workflow, Process, Report
	};

	private final int actionId;
	private final ProcessDescriptorType type;
	private final Class<? extends ISvrProcessPrecondition> preconditionsClass;
	private final String processClassname;

	private final DocumentEntityDescriptor parametersDescriptor;
	private final ProcessLayout layout;

	private ProcessDescriptor(final Builder builder)
	{
		super();

		actionId = builder.actionId;
		Check.assume(actionId > 0, "actionId > 0");

		type = builder.type;
		Check.assumeNotNull(type, "Parameter type is not null");

		preconditionsClass = builder.getPreconditionsClass();
		processClassname = builder.getProcessClassname();

		parametersDescriptor = builder.parametersDescriptor;
		Check.assumeNotNull(parametersDescriptor, "Parameter parametersDescriptor is not null");

		layout = builder.layout;
		Check.assumeNotNull(layout, "Parameter layout is not null");
	}

	public int getActionId()
	{
		return actionId;
	}
	
	public String getCaption(final String adLanguage)
	{
		return getLayout().getCaption(adLanguage);
	}

	public ProcessDescriptorType getType()
	{
		return type;
	}

	public boolean hasPreconditions()
	{
		return preconditionsClass != null;
	}

	public String getProcessClassname()
	{
		return processClassname;
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
		catch (final Exception ex)
		{
			logger.warn("Failed to determine if preconditions of {} are appliable on {}. Considering NOT appliable.", this, context, ex);
			return false;
		}
	}

	public DocumentEntityDescriptor getParametersDescriptor()
	{
		return parametersDescriptor;
	}

	public ProcessLayout getLayout()
	{
		return layout;
	}

	public static final class Builder
	{
		private int actionId;
		private ProcessDescriptorType type;
		public String processClassname;

		private DocumentEntityDescriptor parametersDescriptor;
		private ProcessLayout layout;

		private Builder()
		{
			super();
		}

		public ProcessDescriptor build()
		{
			return new ProcessDescriptor(this);
		}

		public Builder setActionId(final int actionId)
		{
			this.actionId = actionId;
			return this;
		}

		public Builder setType(final ProcessDescriptorType type)
		{
			this.type = type;
			return this;
		}

		public Builder setProcessClassname(final String processClassname)
		{
			this.processClassname = processClassname;
			return this;
		}
		
		private String getProcessClassname()
		{
			return processClassname;
		}

		private Class<? extends ISvrProcessPrecondition> getPreconditionsClass()
		{
			final String classname = getProcessClassname();
			if (classname == null)
			{
				return null;
			}

			final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			final Class<?> processClass;
			try
			{
				processClass = classLoader.loadClass(classname);
			}
			catch (final ClassNotFoundException e)
			{
				logger.error("Cannot load class: " + classname, e);
				return null;
			}

			if (!ISvrProcessPrecondition.class.isAssignableFrom(processClass))
			{
				return null;
			}

			try
			{
				return processClass.asSubclass(ISvrProcessPrecondition.class);
			}
			catch (final Exception e)
			{
				logger.error(e.getLocalizedMessage(), e);
				return null;
			}
		}

		public Builder setParametersDescriptor(final DocumentEntityDescriptor parametersDescriptor)
		{
			this.parametersDescriptor = parametersDescriptor;
			return this;
		}

		public Builder setLayout(final ProcessLayout layout)
		{
			this.layout = layout;
			return this;
		}
	}

}
