/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.project;

import de.metas.project.ProjectTypeRepository;
import io.cucumber.java.en.And;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ProjectType;
import org.testcontainers.shaded.org.checkerframework.checker.nullness.qual.NonNull;

public class C_ProjectType_StepDef
{
	private final ProjectTypeRepository projectTypeRepository = SpringContextHolder.instance.getBean(ProjectTypeRepository.class);

	@And("^set project type (.*) to (active|inactive)$")
	public void setActive(@NonNull final String projectTypeValue, @NonNull final String active)
	{
		final boolean isActive = active.equals("active");
		final I_C_ProjectType projectType = projectTypeRepository.getByName(projectTypeValue);
		projectType.setIsActive(isActive);
		projectTypeRepository.save(projectType);
	}
}
