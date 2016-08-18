package de.metas.ui.web.window;

import java.util.Arrays;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.google.common.base.Joiner;

import de.metas.ui.web.WebRestApiApplication;
import de.metas.ui.web.window.SpringIntegrationTest.TestConfig;
import de.metas.ui.web.window.controller.WindowRestController;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent.JSONOperation;
import de.metas.ui.web.window.datatypes.json.JSONDocumentField;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayout;
import de.metas.ui.web.window.descriptor.MockedDocumentDescriptorFactory;
import de.metas.ui.web.window.model.DocumentInterfaceWrapperHelper;

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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { TestConfig.class })
@WebAppConfiguration
@IntegrationTest("server.port:0")
// @DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class SpringIntegrationTest
{
	@Configuration
	@Import(WebRestApiApplication.class)
	public static class TestConfig
	{
		@Bean
		@Order(Ordered.HIGHEST_PRECEDENCE)
		public Adempiere adempiere()
		{
			InterfaceWrapperHelper.registerHelper(new DocumentInterfaceWrapperHelper());

			AdempiereTestHelper.get().init();
			return Env.getSingleAdempiereInstance();
		}
	}

	@Autowired
	private WindowRestController restController;

	private RequestAttributes requestAttributes;

	@Before
	public void beforeTest()
	{
		requestAttributes = new MockedRequestAttributes();
		RequestContextHolder.setRequestAttributes(requestAttributes);
	}

	@Test
	public void test_get_layout()
	{
		final JSONDocumentLayout layout = restController.layout(MockedDocumentDescriptorFactory.AD_WINDOW_ID_SalesOrder);
		System.out.println("got layout: " + layout);
	}

	@Test
	public void test_patch_commit_new()
	{
		String orderId = DocumentId.NEW_ID_STRING;

		final List<JSONDocumentChangedEvent> events = Arrays.asList(
				JSONDocumentChangedEvent.of(JSONOperation.replace, "DocumentNo", "1234") //
		);

		final List<JSONDocumentField> result = restController.commit(MockedDocumentDescriptorFactory.AD_WINDOW_ID_SalesOrder, orderId, null, null, events);
		System.out.println("Got result:\n" + Joiner.on("\n").join(result));
		orderId = getId(result, orderId);
		System.out.println("=> orderId=" + orderId);

		final Object documentNo = getField(result, "DocumentNo").getValue();
		Assert.assertEquals("DocumentNo", "1234", documentNo);

	}

	public static final String getId(List<JSONDocumentField> fields, String defaultId)
	{
		final JSONDocumentField idField = getFieldOrNull(fields, JSONDocumentField.FIELD_VALUE_ID);
		if (idField == null)
		{
			return defaultId;
		}

		return Util.coalesce((String)idField.getValue(), defaultId);
	}

	public static final JSONDocumentField getField(List<JSONDocumentField> fields, String fieldName)
	{
		final JSONDocumentField field = getFieldOrNull(fields, fieldName);
		if (field == null)
		{
			throw new IllegalArgumentException("No field with name " + fieldName + " found in " + fields);
		}
		return field;
	}

	public static final JSONDocumentField getFieldOrNull(List<JSONDocumentField> fields, String fieldName)
	{
		for (JSONDocumentField field : fields)
		{
			if (fieldName.equals(field.getField()))
			{
				return field;
			}
		}

		return null;
	}

}
