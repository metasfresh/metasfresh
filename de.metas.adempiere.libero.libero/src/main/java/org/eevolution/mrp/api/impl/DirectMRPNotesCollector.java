package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import org.eevolution.mrp.api.IMRPContext;
import org.eevolution.mrp.api.IMRPNoteBuilder;
import org.eevolution.mrp.api.IMRPNotesCollector;

/**
 * An {@link IMRPNotesCollector} which is simply saving the notes in database without doing any notes collecting.
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/fresh_08470_speed_up_MRP_cleanup_%28100715712605%29
 */
public final class DirectMRPNotesCollector implements IMRPNotesCollector
{
	public static final transient DirectMRPNotesCollector instance = new DirectMRPNotesCollector();

	private DirectMRPNotesCollector()
	{
		super();
	}

	@Override
	public IMRPNoteBuilder newMRPNoteBuilder(final IMRPContext mrpContext, final String mrpErrorCode)
	{
		return new MRPNoteBuilder(this)
				.setMRPContext(mrpContext)
				.setMRPCode(mrpErrorCode);
	}

	@Override
	public void collectNote(final IMRPNoteBuilder noteBuilder)
	{
		noteBuilder.createMRPNote();
	}

}
