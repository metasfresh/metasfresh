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
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ProjectType;
import org.testcontainers.shaded.org.checkerframework.checker.nullness.qual.NonNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class C_ProjectType_StepDef
{
	/**
	 * Lazy: the {@code @After} hook makes Cucumber instantiate this class for every scenario, so the bean is resolved only when a step uses it.
	 */
	private final SpringContextHolder.Lazy<ProjectTypeRepository> projectTypeRepository = SpringContextHolder.lazyBean(ProjectTypeRepository.class);

	/**
	 * Active state of each project type before this scenario changed it temporarily; restored by {@link #restoreTemporaryProjectTypes()}.
	 */
	private final Map<String, Boolean> priorIsActiveByProjectType = new LinkedHashMap<>();

	@And("^set project type (.*) to (active|inactive)$")
	public void setActive(@NonNull final String projectTypeValue, @NonNull final String active)
	{
		final boolean isActive = active.equals("active");
		final I_C_ProjectType projectType = projectTypeRepository.get().getByName(projectTypeValue);
		projectType.setIsActive(isActive);
		projectTypeRepository.get().save(projectType);
	}

	/**
	 * Sets a project type active or inactive for the current scenario only; its prior state is restored after the scenario.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Given temporarily set project type Sales/Purchase Order to active
	 * </pre>
	 */
	@And("^temporarily set project type (.*) to (active|inactive)$")
	public void temporarilySetActive(@NonNull final String projectTypeValue, @NonNull final String active)
	{
		priorIsActiveByProjectType.putIfAbsent(projectTypeValue, projectTypeRepository.get().getByName(projectTypeValue).isActive());
		setActive(projectTypeValue, active);
	}

	/**
	 * The only restore of the project types this scenario changed temporarily: an {@code @After} hook, so it also runs when a step failed.
	 */
	@After
	public void restoreTemporaryProjectTypes()
	{
		priorIsActiveByProjectType.forEach((projectTypeValue, wasActive) -> setActive(projectTypeValue, wasActive ? "active" : "inactive"));
		priorIsActiveByProjectType.clear();
	}
}
