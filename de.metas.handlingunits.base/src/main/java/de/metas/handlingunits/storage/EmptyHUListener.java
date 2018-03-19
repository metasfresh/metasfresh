package de.metas.handlingunits.storage;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.model.I_M_HU;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.handlingunits.base
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

/**
 * Instances can be added to {@link IHUContext}s using {@link IMutableHUContext#addEmptyHUListener(EmptyHUListener)}.<br>
 * They will be invoked if an empty HU is destroyed by {@link IHandlingUnitsBL#destroyIfEmptyStorage(IHUContext, I_M_HU)}.
 */
@ToString
@EqualsAndHashCode
public class EmptyHUListener
{
	private final transient Consumer<I_M_HU> consumer;
	private final String description;

	public static EmptyHUListener doBeforeDestroyed(
			@NonNull final Consumer<I_M_HU> consumer,
			@NonNull final String description)
	{
		return new EmptyHUListener(consumer, description);
	}

	public static EmptyHUListener doBeforeDestroyed(@NonNull final Consumer<I_M_HU> consumer)
	{
		final String description = null;
		return new EmptyHUListener(consumer, description);
	}

	private EmptyHUListener(
			@NonNull final Consumer<I_M_HU> consumer,
			@Nullable final String description)
	{
		this.consumer = consumer;
		this.description = description;
	}

	public void beforeDestroyEmptyHu(@NonNull final I_M_HU emptyHU)
	{
		consumer.accept(emptyHU);
	}
}
