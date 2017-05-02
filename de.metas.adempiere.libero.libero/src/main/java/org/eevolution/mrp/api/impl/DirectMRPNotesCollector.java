package org.eevolution.mrp.api.impl;

import de.metas.material.planning.IMRPNoteBuilder;
import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.IMaterialPlanningContext;

/**
 * An {@link IMRPNotesCollector} which is simply saving the notes in database without doing any notes collecting.
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08470_speed_up_MRP_cleanup_%28100715712605%29
 */
public final class DirectMRPNotesCollector implements IMRPNotesCollector
{
	public static final transient DirectMRPNotesCollector instance = new DirectMRPNotesCollector();

	private DirectMRPNotesCollector()
	{
	}

	@Override
	public IMRPNoteBuilder newMRPNoteBuilder(final IMaterialPlanningContext mrpContext, final String mrpErrorCode)
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
