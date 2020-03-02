package de.metas.ui.web.process.descriptor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.util.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.process.BarcodeScannerType;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.window.datatypes.PanelLayoutType;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.util.Check;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ProcessLayout
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final ProcessId processId;

	private final PanelLayoutType layoutType;
	private final BarcodeScannerType barcodeScannerType;

	private final ITranslatableString caption;
	private final ITranslatableString description;

	private final List<DocumentLayoutElementDescriptor> elements;

	private ProcessLayout(final Builder builder)
	{
		Preconditions.checkNotNull(builder.processId, "processId not set: %s", builder);
		processId = builder.processId;

		Preconditions.checkNotNull(builder.layoutType, "layoutType not set: %s", builder);
		layoutType = builder.layoutType;

		caption = TranslatableStrings.nullToEmpty(builder.caption);
		description = TranslatableStrings.nullToEmpty(builder.description);
		elements = ImmutableList.copyOf(builder.elements);

		if (layoutType == PanelLayoutType.SingleOverlayField)
		{
			final ImmutableSet<BarcodeScannerType> barcodeScannerTypes = this.elements.stream()
					.map(DocumentLayoutElementDescriptor::getBarcodeScannerType)
					.filter(Objects::nonNull)
					.collect(ImmutableSet.toImmutableSet());
			barcodeScannerType = barcodeScannerTypes.size() == 1 ? barcodeScannerTypes.iterator().next() : null;
		}
		else
		{
			barcodeScannerType = null;
		}
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("processId", processId)
				.add("caption", caption)
				.add("elements", elements.isEmpty() ? null : elements)
				.toString();
	}

	public PanelLayoutType getLayoutType()
	{
		return layoutType;
	}

	public BarcodeScannerType getBarcodeScannerType()
	{
		return barcodeScannerType;
	}

	public ITranslatableString getCaption()
	{
		return caption;
	}

	public String getCaption(final String adLanguage)
	{
		return caption.translate(adLanguage);
	}

	public ITranslatableString getDescription()
	{
		return description;
	}

	public String getDescription(final String adLanguage)
	{
		return description.translate(adLanguage);
	}

	public List<DocumentLayoutElementDescriptor> getElements()
	{
		return elements;
	}

	public static final class Builder
	{
		private ProcessId processId;
		private PanelLayoutType layoutType = PanelLayoutType.Panel;
		private ITranslatableString caption;
		private ITranslatableString description;

		private final List<DocumentLayoutElementDescriptor> elements = new ArrayList<>();

		private Builder()
		{
			super();
		}

		public ProcessLayout build()
		{
			return new ProcessLayout(this);
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("processId", processId)
					.add("caption", caption)
					.add("elements-count", elements.size())
					.toString();
		}

		public Builder setProcessId(final ProcessId processId)
		{
			this.processId = processId;
			return this;
		}

		public Builder setLayoutType(final PanelLayoutType layoutType)
		{
			this.layoutType = layoutType;
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

		public ITranslatableString getDescription()
		{
			return description;
		}

		public Builder addElement(final DocumentLayoutElementDescriptor element)
		{
			Check.assumeNotNull(element, "Parameter element is not null");
			elements.add(element);
			return this;
		}

		public Builder addElement(final DocumentFieldDescriptor processParaDescriptor)
		{
			Check.assumeNotNull(processParaDescriptor, "Parameter processParaDescriptor is not null");
			final DocumentLayoutElementDescriptor element = DocumentLayoutElementDescriptor.builder()
					.setCaption(processParaDescriptor.getCaption())
					.setDescription(processParaDescriptor.getDescription())
					.setWidgetType(processParaDescriptor.getWidgetType())
					.setAllowShowPassword(processParaDescriptor.isAllowShowPassword())
					.barcodeScannerType(processParaDescriptor.getBarcodeScannerType())
					.addField(DocumentLayoutElementFieldDescriptor.builder(processParaDescriptor.getFieldName())
							.setLookupInfos(processParaDescriptor.getLookupDescriptor().orElse(null))
							.setPublicField(true)
							.setSupportZoomInto(processParaDescriptor.isSupportZoomInto()))
					.build();

			addElement(element);

			return this;
		}

		public Builder addElements(@Nullable final DocumentEntityDescriptor parametersDescriptor)
		{
			if (parametersDescriptor != null)
			{
				parametersDescriptor.getFields().forEach(this::addElement);
			}

			return this;
		}

	}
}
