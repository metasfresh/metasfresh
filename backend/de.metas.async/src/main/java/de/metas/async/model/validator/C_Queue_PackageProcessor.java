package de.metas.async.model.validator;

/*
 * #%L
 * de.metas.async
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

import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.processor.IWorkpackageProcessorFactory;
import de.metas.async.processor.descriptor.QueueProcessorDescriptorRepository;
import de.metas.async.processor.descriptor.model.QueuePackageProcessor;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.ModelValidator;

@Validator(I_C_Queue_PackageProcessor.class)
public class C_Queue_PackageProcessor
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }
			, ifColumnsChanged = I_C_Queue_PackageProcessor.COLUMNNAME_Classname)
	public void validateClassname(@NonNull final I_C_Queue_PackageProcessor packageProcessor)
	{
		final QueuePackageProcessor queuePackageProcessor = QueueProcessorDescriptorRepository.mapToPackageProcessor(packageProcessor);

		Services.get(IWorkpackageProcessorFactory.class).validateWorkpackageProcessor(queuePackageProcessor);
	}
}
