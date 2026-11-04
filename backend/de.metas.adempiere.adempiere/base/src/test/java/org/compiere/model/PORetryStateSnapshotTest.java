/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
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


package org.compiere.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objenesis.ObjenesisStd;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Round-trips every field captured by {@link PO#snapshotStateForRetry()}. The PO is instantiated without a constructor (so without POInfo / DB):
 * the snapshot and the restore only work on the PO's own state fields, which are set and read by reflection.
 */
class PORetryStateSnapshotTest
{
	private static final ObjenesisStd OBJENESIS = new ObjenesisStd();

	private PO po;

	@BeforeEach
	void init()
	{
		po = OBJENESIS.newInstance(X_AD_Client.class);

		final Set<Integer> markedChangedColumns = new HashSet<>();
		markedChangedColumns.add(1);
		final HashMap<String, String> custom = new HashMap<>();
		custom.put("CustomColumn", "customValue");
		final HashMap<String, Object> dynAttrs = new HashMap<>();
		dynAttrs.put("dynAttr", "dynValue");
		final ArrayList<PO_LOB> lobInfo = new ArrayList<>();
		lobInfo.add(OBJENESIS.newInstance(PO_LOB.class));

		set("m_oldValues", new Object[] { "old0", 1 });
		set("m_newValues", new Object[] { null, 2 });
		set("m_valueLoaded", new boolean[] { true, false });
		set("m_stale", true);
		set("markedChangedColumns", markedChangedColumns);
		set("m_custom", custom);
		set("m_dynAttrs", dynAttrs);
		set("m_lobInfo", lobInfo);
	}

	/**
	 * Changes every captured field, both by replacing it and by changing the original array / collection in place, as an action would.
	 */
	@SuppressWarnings("unchecked")
	private void changeEverything()
	{
		// change the PO's current arrays and collections in place (after a restore, these are the restored ones) ...
		((Object[])get("m_oldValues"))[0] = "changed";
		((Object[])get("m_newValues"))[0] = "changed";
		((boolean[])get("m_valueLoaded"))[1] = true;
		((Set<Integer>)get("markedChangedColumns")).add(0);
		((HashMap<String, String>)get("m_custom")).put("CustomColumn", "changed");
		((HashMap<String, Object>)get("m_dynAttrs")).put("other", "changed");
		((List<PO_LOB>)get("m_lobInfo")).clear();

		// ... and replace them

		set("m_oldValues", new Object[] { "x", "y" });
		set("m_newValues", new Object[] { "x", "y" });
		set("m_valueLoaded", new boolean[] { false, false });
		set("m_stale", false);
		set("markedChangedColumns", null);
		set("m_custom", null);
		set("m_dynAttrs", null);
		set("m_lobInfo", null);
		set("m_poCacheLocals", new HashMap<>());
	}

	private void assertRestored()
	{
		assertThat((Object[])get("m_oldValues")).containsExactly("old0", 1);
		assertThat((Object[])get("m_newValues")).containsExactly(null, 2);
		assertThat((boolean[])get("m_valueLoaded")).containsExactly(true, false);
		assertThat(get("m_stale")).isEqualTo(true);
		assertThat((Set<?>)get("markedChangedColumns")).containsExactly(1);
		assertThat(get("m_custom")).isEqualTo(singletonMap("CustomColumn", "customValue"));
		assertThat(get("m_dynAttrs")).isEqualTo(singletonMap("dynAttr", "dynValue"));
		assertThat((List<?>)get("m_lobInfo")).hasSize(1);
		assertThat(get("m_poCacheLocals")).isNull();
	}

	@Test
	void restore_putsBackEveryCapturedField_andDropsTheCachedReferencedRecords()
	{
		final PO.RetryStateSnapshot snapshot = po.snapshotStateForRetry();
		changeEverything();

		po.restoreStateForRetry(snapshot);

		assertRestored();
	}

	@Test
	void snapshot_canBeRestoredSeveralTimes()
	{
		final PO.RetryStateSnapshot snapshot = po.snapshotStateForRetry();
		changeEverything();
		po.restoreStateForRetry(snapshot);

		changeEverything();
		po.restoreStateForRetry(snapshot);

		assertRestored();
	}

	@Test
	void restore_doesNotTouchTheFieldsAnActionDoesNotChange()
	{
		set("m_trxName", "trx1");
		set("m_isManualUserAction", true);
		set("m_windowNo", 7);
		final PO.RetryStateSnapshot snapshot = po.snapshotStateForRetry();
		set("m_trxName", "trx2");
		set("m_isManualUserAction", false);
		set("m_windowNo", 8);

		po.restoreStateForRetry(snapshot);

		assertThat(get("m_trxName")).isEqualTo("trx2");
		assertThat(get("m_isManualUserAction")).isEqualTo(false);
		assertThat(get("m_windowNo")).isEqualTo(8);
	}

	private void set(final String fieldName, final Object value)
	{
		try
		{
			final Field field = PO.class.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(po, value);
		}
		catch (final ReflectiveOperationException ex)
		{
			throw new IllegalStateException(ex);
		}
	}

	private Object get(final String fieldName)
	{
		try
		{
			final Field field = PO.class.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(po);
		}
		catch (final ReflectiveOperationException ex)
		{
			throw new IllegalStateException(ex);
		}
	}
}
