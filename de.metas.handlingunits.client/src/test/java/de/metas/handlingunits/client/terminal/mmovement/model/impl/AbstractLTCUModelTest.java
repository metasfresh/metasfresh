package de.metas.handlingunits.client.terminal.mmovement.model.impl;

/*
 * #%L
 * de.metas.handlingunits.client
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


import org.junit.After;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.TerminalContextFactory;
import de.metas.handlingunits.AbstractHUTestWithSampling;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKeyFactory;
import de.metas.handlingunits.client.terminal.mmovement.model.ILTCUModel;
import de.metas.handlingunits.document.IHUDocumentLine;

/**
 * Abstract test class for HU Client tests
 *
 * @author al
 */
public abstract class AbstractLTCUModelTest extends AbstractHUTestWithSampling
{
	/**
	 * To be used when not binding document lines
	 */
	protected static final IHUDocumentLine NULL_DocumentLine = null;

	protected final IHUKeyFactory keyFactory = new HUKeyFactory();

	private final ILTCUModel defaultModel;
	private ILTCUModel _model;

	public AbstractLTCUModelTest()
	{
		super();

		//
		// Create & bind terminalContext to factory
		final ITerminalContext terminalContext = TerminalContextFactory.get().createContextAndRefs().getLeft();
		keyFactory.setTerminalContext(terminalContext);

		//
		// Create mock LU-TU-CU model to be used in tests
		defaultModel = new MockedLTCUModel(keyFactory.getTerminalContext());

		setLTCUModel(defaultModel);
	}

	public final void setLTCUModel(final ILTCUModel model)
	{
		_model = model;
	}

	public final ILTCUModel getLTCUModel()
	{
		return _model;
	}

	/**
	 * If multiple tests of an implementing class use this, then we should explicitly set it back after usage
	 */
	@After
	public final void resetDefaultLTCUModel()
	{
		setLTCUModel(defaultModel);
	}
}
