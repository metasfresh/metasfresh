package de.metas.handlingunits.allocation.impl;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.model.I_M_HU;
import lombok.Builder;
import lombok.NonNull;

import java.util.Collection;
import java.util.List;

/**
 * Loads TUs onto new LUs. Stub, not implemented yet.
 */
public class TUsToNewLUsLoader
{
	@NonNull private final IHUContext huContext;

	@Builder
	private TUsToNewLUsLoader(@NonNull final IHUContext huContext)
	{
		this.huContext = huContext;
	}

	public static boolean isPackToLUAllowed(@NonNull final I_M_HU tu)
	{
		throw new UnsupportedOperationException("not implemented");
	}

	public void addTUs(@NonNull final Collection<I_M_HU> tus)
	{
		throw new UnsupportedOperationException("not implemented");
	}

	public void close()
	{
		throw new UnsupportedOperationException("not implemented");
	}

	public List<I_M_HU> getLU_HUs()
	{
		throw new UnsupportedOperationException("not implemented");
	}
}
