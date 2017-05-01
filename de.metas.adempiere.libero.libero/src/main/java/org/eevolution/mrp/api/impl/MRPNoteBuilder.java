package org.eevolution.mrp.api.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.adempiere.util.Services;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.IMRPDAO;

import de.metas.material.planning.IMRPNoteBuilder;
import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.impl.SimpleMRPNoteBuilder;

class MRPNoteBuilder extends SimpleMRPNoteBuilder
{
	public static final MRPNoteBuilder cast(final IMRPNoteBuilder builder)
	{
		return (MRPNoteBuilder)builder;
	}


	public MRPNoteBuilder(final IMRPNotesCollector mrpNotesCollector)
	{
		super(mrpNotesCollector, mrps -> supplyDocumentNos_ToUse(mrps));
	}

	private static Set<String> supplyDocumentNos_ToUse(Collection<I_PP_MRP> mrps)
	{
		final IMRPDAO mrpDAO = Services.get(IMRPDAO.class);

		final Set<String> documentNos = new HashSet<>();

		for (final I_PP_MRP mrp : mrps)
		{
			if (mrp == null)
			{
				// shall not happen
				continue;
			}
			final int ppMRPId = mrp.getPP_MRP_ID();
			final String documentNo = ppMRPId > 0 ? mrpDAO.getDocumentNo(ppMRPId) : null;
			documentNos.add(documentNo);
		}

		return documentNos;
	}

}
