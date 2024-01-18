/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.process.descriptor;

import com.google.common.base.MoreObjects;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionChecker;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.security.IUserRolePermissions;
import de.metas.ui.web.cache.ETag;
import de.metas.ui.web.cache.ETagAware;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

public final class ProcessDescriptor implements ETagAware
{
	private static final Logger logger = LogManager.getLogger(ProcessDescriptor.class);

	public static Builder builder()
	{
		return new Builder();
	}

	public enum ProcessDescriptorType
	{
		Form, Workflow, Process, Report
	}

	@Getter
	private final ProcessId processId;

	@Getter
	private final InternalName internalName;

	@Getter
	private final ProcessDescriptorType type;
	@Getter
	private final String processClassname;
	private final Class<? extends IProcessDefaultParametersProvider> defaultParametersProviderClass;

	@Getter
	private final ProcessLayout layout;
	private final DocumentEntityDescriptor parametersDescriptor;

	@Getter
	private final boolean startProcessDirectly;

	// ETag support
	private static final Supplier<ETag> nextETagSupplier = ETagAware.newETagGenerator();
	private final ETag eTag = nextETagSupplier.get();

	private ProcessDescriptor(@NonNull final Builder builder)
	{
		processId = builder.getProcessId();
		internalName = builder.getInternalName();
		type = builder.getType();

		processClassname = builder.getProcessClassname();
		defaultParametersProviderClass = builder.getProcessDefaultParametersProvider();

		parametersDescriptor = builder.getParametersDescriptor();

		layout = builder.getLayout();

		startProcessDirectly = builder.isStartProcessDirectly();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("processId", processId)
				.toString();
	}

	@Override
	public ETag getETag()
	{
		return eTag;
	}

	public ITranslatableString getCaption()
	{
		return getLayout().getCaption();
	}

	public ITranslatableString getDescription()
	{
		return getLayout().getDescription();
	}

	public boolean isExecutionGranted(final IUserRolePermissions permissions)
	{
		// Filter out processes on which we don't have access
		final ProcessId processId = getProcessId();

		if (ProcessId.PROCESSHANDLERTYPE_AD_Process.equals(processId.getProcessHandlerType()))
		{
			final Boolean accessRW = permissions.checkProcessAccess(processId.getProcessIdAsInt());
			if (accessRW == null)
			{
				// no access at all => cannot execute
				return false;
			}
			else if (!accessRW)
			{
				// has just readonly access => cannot execute
				return false;
			}
		}

		return true;
	}

	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		return ProcessPreconditionChecker.newInstance()
				.setProcess(getProcessClassname())
				.setPreconditionsContext(context)
				.checkApplies();
	}

	@Nullable public IProcessDefaultParametersProvider getDefaultParametersProvider()
	{
		if (defaultParametersProviderClass == null)
		{
			return null;
		}

		try
		{
			return defaultParametersProviderClass.newInstance();
		}
		catch (final InstantiationException | IllegalAccessException ex)
		{
			throw new AdempiereException("Failed to instantiate the process", ex);
		}
	}

	public DocumentEntityDescriptor getParametersDescriptor()
	{
		Check.assumeNotNull(parametersDescriptor, "Parameter parametersDescriptor is not null");
		return parametersDescriptor;
	}

	//
	//
	//
	//
	//

	public static final class Builder
	{
		private ProcessDescriptorType type;
		private ProcessId processId;

		@Getter
		private InternalName internalName;

		private String processClassname;
		private Optional<Class<?>> processClass = Optional.empty();

		private DocumentEntityDescriptor parametersDescriptor;
		private ProcessLayout layout;

		private Boolean startProcessDirectly;

		private Builder()
		{
		}

		public ProcessDescriptor build()
		{
			return new ProcessDescriptor(this);
		}

		public Builder setInternalName(final InternalName internalName)
		{
			this.internalName = internalName;
			return this;
		}

		private ProcessId getProcessId()
		{
			Check.assumeNotNull(processId, "Parameter processId is not null");
			return processId;
		}

		public Builder setProcessId(final ProcessId processId)
		{
			this.processId = processId;
			return this;
		}

		public Builder setType(final ProcessDescriptorType type)
		{
			this.type = type;
			return this;
		}

		private ProcessDescriptorType getType()
		{
			Check.assumeNotNull(type, "Parameter type is not null");
			return type;
		}

		public Builder setProcessClassname(final String processClassname)
		{
			this.processClassname = StringUtils.trimBlankToNull(processClassname);
			processClass = loadProcessClass(this.processClassname);
			return this;
		}

		private String getProcessClassname()
		{
			return processClassname;
		}

		@Nullable private Class<?> getProcessClassOrNull()
		{
			return processClass.orElse(null);
		}

		private static Optional<Class<?>> loadProcessClass(@Nullable final String classname)
		{
			if (classname == null || Check.isBlank(classname))
			{
				return Optional.empty();
			}

			final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			try
			{
				final Class<?> processClass = classLoader.loadClass(classname);
				return Optional.of(processClass);
			}
			catch (final ClassNotFoundException e)
			{
				logger.warn("Cannot load process class: {}", classname, e);
				return Optional.empty();
			}
		}

		@Nullable private Class<? extends IProcessDefaultParametersProvider> getProcessDefaultParametersProvider()
		{
			final Class<?> processClass = getProcessClassOrNull();
			if (processClass == null || !IProcessDefaultParametersProvider.class.isAssignableFrom(processClass))
			{
				return null;
			}

			try
			{
				return processClass.asSubclass(IProcessDefaultParametersProvider.class);
			}
			catch (final Exception e)
			{
				logger.warn(e.getLocalizedMessage(), e);
				return null;
			}
		}

		public Builder setParametersDescriptor(final DocumentEntityDescriptor parametersDescriptor)
		{
			this.parametersDescriptor = parametersDescriptor;
			return this;
		}

		private DocumentEntityDescriptor getParametersDescriptor()
		{
			return parametersDescriptor;
		}

		public Builder setLayout(final ProcessLayout layout)
		{
			this.layout = layout;
			return this;
		}

		private ProcessLayout getLayout()
		{
			Check.assumeNotNull(layout, "Parameter layout is not null");
			return layout;
		}

		public Builder setStartProcessDirectly(final boolean startProcessDirectly)
		{
			this.startProcessDirectly = startProcessDirectly;
			return this;
		}

		private boolean isStartProcessDirectly()
		{
			if (startProcessDirectly != null)
			{
				return startProcessDirectly;
			}
			else
			{
				return computeIsStartProcessDirectly();
			}
		}

		private boolean computeIsStartProcessDirectly()
		{
			return (getParametersDescriptor() == null || getParametersDescriptor().getFields().isEmpty())
					&& TranslatableStrings.isEmpty(getLayout().getDescription());
		}
	}

}
