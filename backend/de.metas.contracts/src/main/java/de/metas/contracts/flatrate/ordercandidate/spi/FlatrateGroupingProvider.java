package de.metas.contracts.flatrate.ordercandidate.spi;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.spi.IOLCandGroupingProvider;

/**
 *
 *
 * @author ts
 *
 */
@Component
public class FlatrateGroupingProvider implements IOLCandGroupingProvider
{
	/**
	 * This implementation returns a list containing the given candidate's
	 * <code>C_OLCand.C_Flatrate_Conditions_ID</code> value. That way is is made sure that two candidates with different
	 * flatrate condition won't end up in the same order line.
	 *
	 * @param cand
	 * @return a list with the given <code>cand</code>'s
	 *         {@link de.metas.contracts.flatrate.interfaces.I_C_OLCand#getC_Flatrate_Conditions_ID()}.
	 * 
	 */
	@Override
	public List<Object> provideLineGroupingValues(final OLCand cand)
	{
		final int flatrateConditionsId = cand.getFlatrateConditionsId();
		return ImmutableList.<Object> of(flatrateConditionsId);
	}

}
