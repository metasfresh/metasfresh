/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.manufacturing.generatedcomponents;

import de.metas.javaclasses.IJavaClassBL;
import de.metas.javaclasses.JavaClassId;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_PP_ComponentGenerator;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_PP_ComponentGenerator.class)
@Component
public class PP_ComponentGenerator
{
	private final IJavaClassBL javaClassBL = Services.get(IJavaClassBL.class);
	private final ComponentGeneratorRepository componentGeneratorRepository = SpringContextHolder.instance.getBean(ComponentGeneratorRepository.class);

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW })
	void generateDefaultParams(final I_PP_ComponentGenerator po)
	{
		final IComponentGenerator generatorClass = javaClassBL.newInstance(JavaClassId.ofRepoId(po.getAD_JavaClass_ID()));
		componentGeneratorRepository.generateDefaultParameters(po, generatorClass.getDefaultParameters());
	}
}
