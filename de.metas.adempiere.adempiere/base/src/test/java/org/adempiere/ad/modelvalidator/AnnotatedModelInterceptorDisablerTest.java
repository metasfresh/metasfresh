package org.adempiere.ad.modelvalidator;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.ModelValidator;
import org.junit.Before;
import org.junit.Test;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class AnnotatedModelInterceptorDisablerTest
{
	private Pointcut pointcut;

	private AnnotatedModelInterceptorDisabler annotatedModelInterceptorDisabler;

	@Before
	public void init() throws NoSuchMethodException, SecurityException
	{
		AdempiereTestHelper.get().init();

		pointcut = new Pointcut(PointcutType.DocValidate, AnnotatedModelInterceptorDisablerTest.class.getMethod("someTestMethod"), new int[] { ModelValidator.TIMING_AFTER_CLOSE }, false);
		annotatedModelInterceptorDisabler = new AnnotatedModelInterceptorDisabler();
	}

	/**
	 * Needed to make our testing pointcut.
	 */
	public void someTestMethod()
	{
		// no need for any code, we just need a method
	}

	@Test
	public void createHowtoDisableMsg0()
	{
		assertThat(AnnotatedModelInterceptorDisabler.createHowtoDisableMessage(pointcut))
				.isEqualTo("Model interceptor method org.adempiere.ad.modelvalidator.AnnotatedModelInterceptorDisablerTest#someTestMethod threw an exception."
						+ "\nYou can disable this method with SysConfig IntercetorEnabled_org.adempiere.ad.modelvalidator.AnnotatedModelInterceptorDisablerTest#someTestMethod='N' (with AD_Client_ID and AD_Org_ID=0!)");
	}

	@Test
	public void setDisabled()
	{
		setupSysConfigAndReload("N");
		assertThat(annotatedModelInterceptorDisabler.isDisabled(pointcut)).isTrue();
	}

	@Test
	public void setNotDisabled()
	{
		setupSysConfigAndReload("Y");
		assertThat(annotatedModelInterceptorDisabler.isDisabled(pointcut)).isFalse();
	}

	private void setupSysConfigAndReload(final String sysConfigValue)
	{
		setupSysConfig(sysConfigValue);
		annotatedModelInterceptorDisabler.invalidateCache();
	}

	/**
	 * Checks if the setting are loaded initially in a new instance.
	 */
	@Test
	public void setDisabledInitital()
	{
		setupSysConfig("N");
		annotatedModelInterceptorDisabler = new AnnotatedModelInterceptorDisabler();
		assertThat(annotatedModelInterceptorDisabler.isDisabled(pointcut)).isTrue();
	}

	private void setupSysConfig(final String sysConfigValue)
	{
		final I_AD_SysConfig sysConfig = newInstance(I_AD_SysConfig.class);
		sysConfig.setName("IntercetorEnabled_org.adempiere.ad.modelvalidator.AnnotatedModelInterceptorDisablerTest#someTestMethod");
		sysConfig.setValue(sysConfigValue);
		save(sysConfig);
	}
}
