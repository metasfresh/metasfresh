package de.metas.handlingunits.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Properties;

import org.junit.Test;

import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.storage.EmptyHUListener;

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

public class MutableHUContextTest
{

	@Test
	public void emptyHuListenerAreCopied()
	{
		final EmptyHUListener emptyHuListener = EmptyHUListener.doBeforeDestroyed(hu -> {}, "Empty listener tha dies nothing");
		final String trxName = "strangeTrxName";
		final MutableHUContext mutableHUContext = new MutableHUContext(new Properties(), trxName);
		mutableHUContext.addEmptyHUListener(emptyHuListener);

		final IMutableHUContext copy = mutableHUContext.copyAsMutable();
		assertThat(copy.getTrxName()).isSameAs(trxName);
		assertThat(copy.getEmptyHUListeners()).containsExactly(emptyHuListener);
	}

}
