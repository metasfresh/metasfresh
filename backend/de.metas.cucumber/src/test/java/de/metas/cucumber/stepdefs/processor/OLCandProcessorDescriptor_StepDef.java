/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.processor;

import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.OLCandProcessorDescriptor;
import de.metas.ordercandidate.api.OLCandProcessorRepository;
import de.metas.util.Services;
import io.cucumber.java.en.When;
import org.compiere.SpringContextHolder;

public class OLCandProcessorDescriptor_StepDef
{
	private final OLCandProcessorRepository olCandProcessorRepo = SpringContextHolder.instance.getBean(OLCandProcessorRepository.class);
	private final IOLCandBL olCandBL = Services.get(IOLCandBL.class);

	@When("ProcessOLCands is called")
	public void call_ol_cands_process()
	{
		final OLCandProcessorDescriptor olCandProcessor = olCandProcessorRepo.getById(1000003);
		olCandBL.process(olCandProcessor, null);
	}

}
