package de.metas.handlingunits;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.util.Check;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompositeDocumentLUTUConfigurationHandler<T> implements IDocumentLUTUConfigurationHandler<List<T>>
{
	public static <T> CompositeDocumentLUTUConfigurationHandler<T> of(@NonNull final IDocumentLUTUConfigurationHandler<T> handler)
	{
		return new CompositeDocumentLUTUConfigurationHandler<>(handler);
	}

	private final IDocumentLUTUConfigurationHandler<T> handler;

	private CompositeDocumentLUTUConfigurationHandler(@NonNull final IDocumentLUTUConfigurationHandler<T> handler)
	{
		this.handler = handler;
	}

	@Override
	public I_M_HU_LUTU_Configuration createNewLUTUConfiguration(final List<T> documentLines)
	{
		Check.assumeNotEmpty(documentLines, "documentLines not empty");

		final T documentLine = documentLines.get(0);
		return handler.createNewLUTUConfiguration(documentLine);
	}

	@Override
	public void updateLUTUConfigurationFromDocumentLine(final I_M_HU_LUTU_Configuration lutuConfiguration, final List<T> documentLines)
	{
		Check.assumeNotEmpty(documentLines, "documentLines not empty");

		final T documentLine = documentLines.get(0);
		handler.updateLUTUConfigurationFromDocumentLine(lutuConfiguration, documentLine);
	}

	@Override
	public void setCurrentLUTUConfiguration(@NonNull final List<T> documentLines, final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		for (final T documentLine : documentLines)
		{
			handler.setCurrentLUTUConfiguration(documentLine, lutuConfiguration);
		}
	}

	@Override
	public I_M_HU_LUTU_Configuration getCurrentLUTUConfigurationOrNull(final List<T> documentLines)
	{
		Check.assumeNotEmpty(documentLines, "documentLines not empty");
		final T documentLine = documentLines.get(0);
		return handler.getCurrentLUTUConfigurationOrNull(documentLine);
	}

	@Override
	public List<I_M_HU_LUTU_Configuration> getCurrentLUTUConfigurationAlternatives(final List<T> documentLines)
	{
		Check.assumeNotEmpty(documentLines, "documentLines not empty");

		final Set<Integer> seenConfigurationIds = new HashSet<>();
		final List<I_M_HU_LUTU_Configuration> altConfigurations = new ArrayList<>(documentLines.size());

		//
		// Main configuration
		final T mainDocumentLine = documentLines.get(0);
		final I_M_HU_LUTU_Configuration mainConfiguration = handler.getCurrentLUTUConfigurationOrNull(mainDocumentLine);
		// Skip the main configuration from returning it
		if (mainConfiguration != null)
		{
			seenConfigurationIds.add(mainConfiguration.getM_HU_LUTU_Configuration_ID());
		}

		for (int i = 1; i < documentLines.size(); i++)
		{
			final T documentLine = documentLines.get(i);
			final I_M_HU_LUTU_Configuration configuration = handler.getCurrentLUTUConfigurationOrNull(documentLine);
			if (configuration == null)
			{
				continue;
			}
			final int configurationId = configuration.getM_HU_LUTU_Configuration_ID();
			if (configurationId <= 0)
			{
				continue;
			}
			if (!seenConfigurationIds.add(configurationId))
			{
				continue;
			}
			altConfigurations.add(configuration);
		}

		return altConfigurations;
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product(final List<T> documentLines)
	{
		Check.assumeNotEmpty(documentLines, "documentLines not empty");
		final T documentLine = documentLines.get(0);
		return handler.getM_HU_PI_Item_Product(documentLine);
	}

	@Override
	public void save(@NonNull final List<T> documentLines)
	{
		for (final T documentLine : documentLines)
		{
			handler.save(documentLine);
		}
	}
}
