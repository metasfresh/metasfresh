package de.metas.handlingunits.snapshot;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.ISingletonService;

public interface IHUSnapshotDAO extends ISingletonService
{
	/**
	 * Start creating snapshots from {@link I_M_HU}s.
	 * 
	 * @return {@link I_M_HU} snapshots producer
	 */
	ISnapshotProducer<I_M_HU> createSnapshot();

	/**
	 * Restore {@link I_M_HU}s from snapshots.
	 * 
	 * @return {@link I_M_HU} from snapshots restorer.
	 */
	ISnapshotRestorer<I_M_HU> restoreHUs();
}
