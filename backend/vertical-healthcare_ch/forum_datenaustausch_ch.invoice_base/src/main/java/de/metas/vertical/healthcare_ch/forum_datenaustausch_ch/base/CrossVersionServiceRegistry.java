package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.XmlVersion;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.CrossVersionRequestConverter;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.CrossVersionResponseConverter;
import lombok.NonNull;

/*
 * #%L
 * vertical-healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_commons
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

@Service
@Profile(ForumDatenaustauschChConstants.PROFILE)
public class CrossVersionServiceRegistry
{
	private final ImmutableMap<String, CrossVersionRequestConverter> xsdName2Requestconverter;
	private final ImmutableMap<XmlVersion, CrossVersionRequestConverter> simpleVersionName2RequestConverter;

	private final ImmutableMap<String, CrossVersionResponseConverter> xsdName2ResponseConverter;
	private final ImmutableMap<XmlVersion, CrossVersionResponseConverter> simpleVersionName2ResponseConverter;

	public CrossVersionServiceRegistry(
			@NonNull final Optional<List<CrossVersionRequestConverter>> crossVersionRequestConverters,
			@NonNull final Optional<List<CrossVersionResponseConverter>> crossVersionResponseConverters
			)
	{
		final List<CrossVersionRequestConverter> //
		requestConverterList = crossVersionRequestConverters.orElse(ImmutableList.of());
		this.xsdName2Requestconverter = Maps.uniqueIndex(requestConverterList, CrossVersionRequestConverter::getXsdName);
		this.simpleVersionName2RequestConverter = Maps.uniqueIndex(requestConverterList, CrossVersionRequestConverter::getVersion);

		final List<CrossVersionResponseConverter> //
		responseConverterList = crossVersionResponseConverters.orElse(ImmutableList.of());
		this.xsdName2ResponseConverter = Maps.uniqueIndex(responseConverterList, CrossVersionResponseConverter::getXsdName);
		this.simpleVersionName2ResponseConverter = Maps.uniqueIndex(responseConverterList, CrossVersionResponseConverter::getVersion);
	}

	public CrossVersionRequestConverter getRequestConverterForXsdName(@NonNull final String xsdName)
	{
		return xsdName2Requestconverter.get(xsdName);
	}

	public CrossVersionRequestConverter getRequestConverterForSimpleVersionName(@NonNull final XmlVersion version)
	{
		return simpleVersionName2RequestConverter.get(version);
	}

	public CrossVersionResponseConverter getResponseConverterForXsdName(@NonNull final String xsdName)
	{
		return xsdName2ResponseConverter.get(xsdName);
	}

	public CrossVersionResponseConverter getResponseConverterForSimpleVersionName(@NonNull final XmlVersion version)
	{
		return simpleVersionName2ResponseConverter.get(version);
	}
}
