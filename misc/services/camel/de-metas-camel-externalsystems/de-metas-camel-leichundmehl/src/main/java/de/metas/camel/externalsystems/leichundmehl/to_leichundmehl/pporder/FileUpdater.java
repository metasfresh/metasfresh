/*
 * #%L
 * de-metas-camel-leichundmehl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonProductInfo;
import de.metas.common.externalsystem.JsonExternalSystemLeichMehlPluFileConfig;
import de.metas.common.externalsystem.JsonProcessedKeys;
import de.metas.common.externalsystem.JsonReplacementSource;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrder;
import de.metas.common.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ATTRIBUTE_KEY_DATE;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ATTRIBUTE_KEY_EAN128;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ATTRIBUTE_KEY_EAN13;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ATTRIBUTE_KEY_GRAPHIC;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ATTRIBUTE_KEY_NUMBER_FIELD;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ATTRIBUTE_KEY_TEXTAREA;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ATTRIBUTE_KEY_UNIT_CHAR;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ATTRIBUTE_TARGET_ELEMENT_DATA;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ATTRIBUTE_TARGET_ELEMENT_FORMAT;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ATTRIBUTE_TARGET_ELEMENT_TEMPLATE;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ELEMENT_TAG_PRINT_OBJECT;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ELEMENT_TAG_PRINT_OBJECTS;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.JSON_PATH_PREFIX;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.NODE_ATTRIBUTE_NAME;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.NODE_ATTRIBUTE_TYPE;

@Value
public class FileUpdater
{
	private final Map<String, String> attributeTypeToTargetFieldMap;

	@NonNull
	Document document;

	@NonNull
	ExportPPOrderRouteContext context;

	@NonNull
	Configuration configuration;

	@NonNull
	ImmutableList.Builder<JsonProcessedKeys> processedKeys;

	@NonNull
	ImmutableList.Builder<String> missingKeys;

	@Builder
	public FileUpdater(
			@NonNull final Document document,
			@NonNull final ExportPPOrderRouteContext context)
	{
		this.document = document;
		this.context = context;

		this.configuration = com.jayway.jsonpath.Configuration.builder()
				.jsonProvider(new JacksonJsonProvider())
				.build();

		this.processedKeys = ImmutableList.builder();
		this.missingKeys = ImmutableList.builder();

		attributeTypeToTargetFieldMap = initializeAttributeTypeToTargetField();
	}

	public void updateDocument()
	{
		for (final JsonExternalSystemLeichMehlPluFileConfig config : this.context.getPluFileConfigs().getPluFileConfigs())
		{
			final String newValue = resolveValueToUpdate(config);

			if (Check.isBlank(newValue))
			{
				continue;
			}

			updateDocument(config, newValue);
		}
	}

	@NonNull
	private String resolveValueToUpdate(@NonNull final JsonExternalSystemLeichMehlPluFileConfig config)
	{
		final String replacementSourceToString = replacementSourceToString(config.getReplacementSource());

		final DocumentContext jsonContext = getDocumentContext(replacementSourceToString);

		final String replacement = config.getReplacement();

		final Pattern pattern = Pattern.compile(LeichMehlConstants.PLU_FILE_CONFIG_JSON_PRODUCT_PATTERN);
		final Matcher matcher = pattern.matcher(replacement);

		if (!matcher.find())
		{
			return null;
		}

		final Object jsonPathValue = jsonContext.read(JSON_PATH_PREFIX + matcher.group(1));

		return jsonPathValue.toString();
	}

	private void updateDocument(
			@NonNull final JsonExternalSystemLeichMehlPluFileConfig config,
			@NonNull final String value)
	{
		boolean matchedConfig = false;

		final Element printObjectsElement = getElementByTag(this.document, ELEMENT_TAG_PRINT_OBJECTS);
		if (printObjectsElement == null)
		{
			return;
		}

		final NodeList printObjectsChildNodes = printObjectsElement.getChildNodes();

		for (int index = 0; index < printObjectsChildNodes.getLength(); index++)
		{
			final Node currentNode = printObjectsChildNodes.item(index);

			if (currentNode.getNodeType() != Node.ELEMENT_NODE && !currentNode.getNodeName().equals(ELEMENT_TAG_PRINT_OBJECT))
			{
				continue;
			}

			final Element printObjectElement = (Element)currentNode;

			final boolean isMatchingNodeByNameAndType = isMatchingNodeByAttribute(printObjectElement, NODE_ATTRIBUTE_NAME, config.getTargetFieldName())
					&& isMatchingNodeByAttribute(printObjectElement, NODE_ATTRIBUTE_TYPE, config.getTargetFieldType());

			if (!isMatchingNodeByNameAndType)
			{
				continue;
			}

			final String targetTagToUpdate = attributeTypeToTargetFieldMap.get(config.getTargetFieldType());

			final Element elementToUpdate = getElementByTag(printObjectElement, targetTagToUpdate);

			if (elementToUpdate == null)
			{
				continue;
			}

			final String oldValue = elementToUpdate.getTextContent();

			final Pattern pattern = Pattern.compile(config.getReplacePattern());
			final Matcher matcher = pattern.matcher(oldValue);

			int groupIndex = 0;

			String newValue = null;

			while (matcher.find())
			{
				groupIndex++;

				if (matcher.groupCount() > 0)
				{
					final String group = matcher.group(groupIndex);
					newValue = StringUtils.replace(oldValue, group, value);
				}
				else
				{
					newValue = StringUtils.replace(oldValue, oldValue, value);
				}
			}

			if (Check.isBlank(newValue))
			{
				continue;
			}

			elementToUpdate.setTextContent(newValue);

			matchedConfig = true;

			processedKeys.add(JsonProcessedKeys.builder()
									  .key(config.getTargetFieldName())
									  .oldValue(oldValue)
									  .newValue(newValue)
									  .build());
		}

		if (!matchedConfig)
		{
			missingKeys.add(config.getTargetFieldName());
		}
	}

	@NonNull
	private String replacementSourceToString(@NonNull final JsonReplacementSource jsonReplacementSource)
	{
		switch (jsonReplacementSource)
		{
			case Product:
				final JsonProductInfo jsonProductInfo = this.context.getProductInfoNonNull();

				return jsonProductInfoToString(jsonProductInfo);

			case PPOrder:
				final JsonResponseManufacturingOrder jsonResponseManufacturingOrder = this.context.getManufacturingOrderNonNull();

				return jsonResponseManufacturingOrderToString(jsonResponseManufacturingOrder);

			default:
				throw new RuntimeException("Unhandled type=" + jsonReplacementSource.getCode());
		}
	}

	@NonNull
	private DocumentContext getDocumentContext(@NonNull final String jsonString)
	{
		return JsonPath.using(this.configuration).parse(jsonString);
	}

	@NonNull
	private static String jsonProductInfoToString(@NonNull final JsonProductInfo jsonProductInfo)
	{
		try
		{
			return JsonObjectMapperHolder
					.sharedJsonObjectMapper()
					.writeValueAsString(jsonProductInfo);
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}

	@NonNull
	private static String jsonResponseManufacturingOrderToString(@NonNull final JsonResponseManufacturingOrder jsonResponseManufacturingOrder)
	{
		try
		{
			return JsonObjectMapperHolder
					.sharedJsonObjectMapper()
					.writeValueAsString(jsonResponseManufacturingOrder);
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Nullable
	private static Element getElementByTag(@NonNull final Node node, @NonNull final String tagName)
	{
		final Element element = node instanceof Document
				? ((Document)node).getDocumentElement()
				: (Element)node;

		final NodeList nodeList = element.getElementsByTagName(tagName);

		if (nodeList.getLength() == 0)
		{
			return null;
		}

		final Node childNode = nodeList.item(0);

		return (Element)childNode;
	}

	@NonNull
	private static boolean isMatchingNodeByAttribute(
			@NonNull final Node node,
			@NonNull final String attributeName,
			@NonNull final String matchingValue)
	{
		final NamedNodeMap namedNodeMap = node.getAttributes();
		if (namedNodeMap == null)
		{
			return false;
		}

		final Node nodeByAttributeName = namedNodeMap.getNamedItem(attributeName);
		if (nodeByAttributeName == null || Check.isEmpty(nodeByAttributeName))
		{
			return false;
		}

		final String nodeValue = nodeByAttributeName.getNodeValue();
		if (Check.isBlank(nodeValue))
		{
			return false;
		}

		return nodeValue.equals(matchingValue);
	}

	@NonNull
	private static ImmutableMap<String, String> initializeAttributeTypeToTargetField()
	{
		final ImmutableMap.Builder<String, String> attributeTypeToTargetFieldMap = ImmutableMap.builder();

		attributeTypeToTargetFieldMap.put(ATTRIBUTE_KEY_TEXTAREA, ATTRIBUTE_TARGET_ELEMENT_DATA);
		attributeTypeToTargetFieldMap.put(ATTRIBUTE_KEY_EAN13, ATTRIBUTE_TARGET_ELEMENT_DATA);
		attributeTypeToTargetFieldMap.put(ATTRIBUTE_KEY_EAN128, ATTRIBUTE_TARGET_ELEMENT_DATA);
		attributeTypeToTargetFieldMap.put(ATTRIBUTE_KEY_NUMBER_FIELD, ATTRIBUTE_TARGET_ELEMENT_FORMAT);
		attributeTypeToTargetFieldMap.put(ATTRIBUTE_KEY_DATE, ATTRIBUTE_TARGET_ELEMENT_TEMPLATE);
		attributeTypeToTargetFieldMap.put(ATTRIBUTE_KEY_UNIT_CHAR, ATTRIBUTE_TARGET_ELEMENT_DATA);
		attributeTypeToTargetFieldMap.put(ATTRIBUTE_KEY_GRAPHIC, ATTRIBUTE_TARGET_ELEMENT_DATA);

		return attributeTypeToTargetFieldMap.build();
	}
}
