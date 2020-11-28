package de.metas.inout.api;

import org.compiere.model.I_M_InOutLine;

import de.metas.util.ISingletonService;

public interface IMaterialBalanceConfigDAO extends ISingletonService
{

	/**
	 * Find the M_Material_Balance_Config entry that fits the details of the inout line
	 */
	MaterialBalanceConfig retrieveFitBalanceConfig(I_M_InOutLine line);
}
