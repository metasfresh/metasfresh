package de.metas.inoutcandidate.lock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.IModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Lock;
import de.metas.logging.LogManager;
import de.metas.tourplanning.model.I_M_ShipmentSchedule;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class ShipmentScheduleLockRepository
{
	private static final Logger logger = LogManager.getLogger(ShipmentScheduleLockRepository.class);

	public void lock(@NonNull final ShipmentScheduleLockRequest request)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.runInNewTrx(() -> lockInTrx(request));
	}

	public void lockInTrx(@NonNull final ShipmentScheduleLockRequest request)
	{
		final ShipmentScheduleLocksMap existingLocks = getByShipmentScheduleIds(request.getShipmentScheduleIds());
		existingLocks.assertLockedBy(request.getLockedBy());
		existingLocks.assertLockType(request.getLockType());

		final Set<ShipmentScheduleId> shipmentScheduleIdsToLock = existingLocks.getShipmentScheduleIdsNotLocked(request.getShipmentScheduleIds());
		if (!shipmentScheduleIdsToLock.isEmpty())
		{
			insertLocks(request.withShipmentScheduleIds(shipmentScheduleIdsToLock));
		}
	}

	private void insertLocks(@NonNull final ShipmentScheduleLockRequest request)
	{
		final UserId lockedBy = request.getLockedBy();
		final ShipmentScheduleLockType lockType = request.getLockType();
		final Set<ShipmentScheduleId> shipmentScheduleIds = request.getShipmentScheduleIds();

		final String sql = "INSERT INTO " + I_M_ShipmentSchedule_Lock.Table_Name + "("
				+ I_M_ShipmentSchedule_Lock.COLUMNNAME_M_ShipmentSchedule_ID
				+ ", " + I_M_ShipmentSchedule_Lock.COLUMNNAME_LockedBy_User_ID
				+ ", " + I_M_ShipmentSchedule_Lock.COLUMNNAME_LockType
				+ ")"
				+ " VALUES (?, ?, ?)";

		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);

			for (final ShipmentScheduleId shipmentScheduleId : shipmentScheduleIds)
			{
				DB.setParameters(pstmt, shipmentScheduleId, lockedBy, lockType.getCode());
				pstmt.addBatch();
			}

			pstmt.executeBatch();
		}
		catch (final SQLException ex)
		{
			if (DBException.isUniqueContraintError(ex))
			{
				throw new AdempiereException("Some of the shipment schedules were already locked: " + shipmentScheduleIds, ex);
			}
			else
			{
				throw new AdempiereException("Failed to lock for " + request, ex);
			}
		}
		finally
		{
			DB.close(pstmt);
		}

		//
		fireShipmentSchedulesChanged(shipmentScheduleIds);
	}

	public void unlockNoFail(@NonNull final ShipmentScheduleUnLockRequest request)
	{
		try
		{
			unlock(request);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed to unlock {}. Ignored", request, ex);
		}
	}

	public void unlock(@NonNull final ShipmentScheduleUnLockRequest request)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.runInNewTrx(() -> unlockInTrx(request));
	}

	private void unlockInTrx(@NonNull final ShipmentScheduleUnLockRequest request)
	{
		final ShipmentScheduleLocksMap existingLocks = getByShipmentScheduleIds(request.getShipmentScheduleIds());
		if (existingLocks.isEmpty())
		{
			return;
		}

		if (request.getLockedBy() != null)
		{
			existingLocks.assertLockedBy(request.getLockedBy());
		}
		existingLocks.assertLockType(request.getLockType());

		deleteLocksByShipmentScheduleIds(existingLocks.getShipmentScheduleIdsLocked());
	}

	private void deleteLocksByShipmentScheduleIds(final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		Check.assumeNotEmpty(shipmentScheduleIds, "shipmentScheduleIds is not empty");

		final List<Object> sqlParams = new ArrayList<>();
		final String sql = "DELETE FROM " + I_M_ShipmentSchedule_Lock.Table_Name
				+ " WHERE " + DB.buildSqlList(I_M_ShipmentSchedule_Lock.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleIds, sqlParams);

		DB.executeUpdateEx(sql, sqlParams.toArray(), ITrx.TRXNAME_ThreadInherited);

		fireShipmentSchedulesChanged(shipmentScheduleIds);
	}

	public ShipmentScheduleLocksMap getByShipmentScheduleIds(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		Check.assumeNotEmpty(shipmentScheduleIds, "shipmentScheduleIds is not empty");

		final List<Object> sqlParams = new ArrayList<>();
		final String sql = "SELECT * FROM " + I_M_ShipmentSchedule_Lock.Table_Name
				+ " WHERE " + DB.buildSqlList(I_M_ShipmentSchedule_Lock.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleIds, sqlParams);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final List<ShipmentScheduleLock> locks = new ArrayList<>();
			while (rs.next())
			{
				locks.add(retrieveLock(rs));
			}

			return ShipmentScheduleLocksMap.of(locks);
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private static ShipmentScheduleLock retrieveLock(final ResultSet rs) throws SQLException
	{
		return ShipmentScheduleLock.builder()
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(rs.getInt(I_M_ShipmentSchedule_Lock.COLUMNNAME_M_ShipmentSchedule_ID)))
				.lockedBy(UserId.ofRepoId(rs.getInt(I_M_ShipmentSchedule_Lock.COLUMNNAME_LockedBy_User_ID)))
				.lockType(ShipmentScheduleLockType.ofCode(rs.getString(I_M_ShipmentSchedule_Lock.COLUMNNAME_LockType)))
				.created(TimeUtil.asZonedDateTime(rs.getTimestamp(I_M_ShipmentSchedule_Lock.COLUMNNAME_Created)))
				.build();
	}

	private void fireShipmentSchedulesChanged(final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return;
		}

		final CacheInvalidateMultiRequest request = CacheInvalidateMultiRequest.rootRecords(I_M_ShipmentSchedule.Table_Name, shipmentScheduleIds);
		Services.get(IModelCacheInvalidationService.class).invalidate(request, ModelCacheInvalidationTiming.CHANGE);
	}
}
