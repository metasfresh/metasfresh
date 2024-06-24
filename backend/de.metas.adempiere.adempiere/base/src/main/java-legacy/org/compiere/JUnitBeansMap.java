/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package org.compiere;

import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.reflect.ClassReference;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ToString
/* package */ final class JUnitBeansMap
{
	private static final Logger logger = LogManager.getLogger(JUnitBeansMap.class);

	private final HashMap<ClassReference<?>, ArrayList<Object>> map = new HashMap<>();

	public JUnitBeansMap()
	{
	}

	private void assertJUnitMode()
	{
		if (!Adempiere.isUnitTestMode())
		{
			throw new AdempiereException("JUnit mode is not active!");
		}
	}

	public synchronized void clear()
	{
		map.clear();
	}

	public synchronized <T> void registerJUnitBean(@NonNull final T beanImpl)
	{
		assertJUnitMode();

		@SuppressWarnings("unchecked")
		final Class<T> beanType = (Class<T>)beanImpl.getClass();

		registerJUnitBean(beanType, beanImpl);
	}

	public synchronized <BT, T extends BT> void registerJUnitBean(
			@NonNull final Class<BT> beanType,
			@NonNull final T beanImpl)
	{
		assertJUnitMode();

		final ArrayList<Object> beans = map.computeIfAbsent(ClassReference.of(beanType), key -> new ArrayList<>());
		beans.add(beanImpl);
		logger.info("JUnit testing: Registered bean {}={}", beanType, beanImpl);
	}

	public synchronized <BT, T extends BT> void registerJUnitBeans(
			@NonNull final Class<BT> beanType,
			@NonNull final List<T> beansToAdd)
	{
		assertJUnitMode();

		final ArrayList<Object> beans = map.computeIfAbsent(ClassReference.of(beanType), key -> new ArrayList<>());
		beans.addAll(beansToAdd);
		logger.info("JUnit testing: Registered beans {}={}", beanType, beansToAdd);
	}

	public synchronized <T> T getBeanOrNull(@NonNull final Class<T> beanType)
	{
		assertJUnitMode();

		final ArrayList<Object> beans = map.get(ClassReference.of(beanType));
		if (beans == null || beans.isEmpty())
		{
			return null;
		}

		if (beans.size() > 1)
		{
			logger.warn("Found more than one bean for {} but returning the first one: {}", beanType, beans);
		}

		final T beanImpl = castBean(beans.get(0), beanType);
		logger.info("JUnit testing Returning manually registered bean: {}", beanImpl);
		return beanImpl;
	}

	private static <T> T castBean(final Object beanImpl, final Class<T> beanType)
	{
		@SuppressWarnings("unchecked")
		final T beanImplCasted = (T)beanImpl;
		return beanImplCasted;
	}

	public synchronized <T> ImmutableList<T> getBeansOfTypeOrNull(@NonNull final Class<T> beanType)
	{
		assertJUnitMode();

		List<Object> beanObjs = map.get(ClassReference.of(beanType));
		if (beanObjs == null)
		{
			final List<Object> assignableBeans = map.values()
					.stream()
					.filter(Objects::nonNull)
					.flatMap(Collection::stream)
					.filter(impl -> beanType.isAssignableFrom(impl.getClass()))
					.collect(Collectors.toList());

			if (assignableBeans.isEmpty())
			{
				return null;
			}

			beanObjs = assignableBeans;
		}

		return beanObjs
				.stream()
				.map(beanObj -> castBean(beanObj, beanType))
				.collect(ImmutableList.toImmutableList());
	}
}
