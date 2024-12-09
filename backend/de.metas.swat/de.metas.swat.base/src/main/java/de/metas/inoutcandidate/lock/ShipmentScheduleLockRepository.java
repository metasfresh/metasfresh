package de.metas.inoutcandidate.lock;

import de.metas.inout.ShipmentScheduleId;
import lombok.NonNull;
<<<<<<< HEAD
=======
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

import java.util.Set;

public interface ShipmentScheduleLockRepository
{
	void lock(@NonNull ShipmentScheduleLockRequest request);

	void lockInTrx(@NonNull ShipmentScheduleLockRequest request);

	void unlockNoFail(@NonNull ShipmentScheduleUnLockRequest request);

	void unlock(@NonNull ShipmentScheduleUnLockRequest request);

	ShipmentScheduleLocksMap getByShipmentScheduleIds(@NonNull Set<ShipmentScheduleId> shipmentScheduleIds);
}
