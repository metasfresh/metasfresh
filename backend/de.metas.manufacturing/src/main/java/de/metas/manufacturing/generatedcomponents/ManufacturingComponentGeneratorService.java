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
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.springframework.stereotype.Service;

@Service
public class ManufacturingComponentGeneratorService
{
	private final ComponentGeneratorRepository componentRepository;
	private final IJavaClassBL javaClassBL = Services.get(IJavaClassBL.class);

	public ManufacturingComponentGeneratorService(
			@NonNull final ComponentGeneratorRepository componentRepository)
	{
		this.componentRepository = componentRepository;
	}

	public boolean hasGeneratorForProduct(@NonNull final ProductId productId)
	{
		return componentRepository.getByProductId(productId).isPresent();
	}

	public ImmutableAttributeSet generate(@NonNull final GeneratedComponentRequest request)
	{
		final ComponentGenerator generator = componentRepository.getByProductId(request.getProductId())
				.orElseThrow(() -> new AdempiereException("No Component Generator for product " + request.getProductId()));

		final IComponentGenerator generatorInstance = javaClassBL.newInstance(generator.getJavaClassId());
		return generatorInstance.generate(ComponentGeneratorContext.builder()
				.qty(request.getQty())
				.existingAttributes(request.getAttributes())
				.parameters(generator.getParams())
				.clientId(request.getClientId())
				.overrideExistingValues(request.isOverrideExistingValues())
				.build());
	}

	public void createDefaultParameters(
			final ComponentGeneratorId generatorId,
			final JavaClassId generatorClassId)
	{
		final IComponentGenerator generatorInstance = javaClassBL.newInstance(generatorClassId);
		componentRepository.createDefaultParameters(generatorId, generatorInstance.getDefaultParameters());
	}
}
