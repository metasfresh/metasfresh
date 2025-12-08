/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

-- Column: AD_Sequence_No.CalendarMonth
-- Column: AD_Sequence_No.CalendarMonth
-- 2023-01-03T08:38:34.181Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-01-03 10:38:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585423
;

-- 2023-01-03T08:38:34.968Z
INSERT INTO t_alter_column values('ad_sequence_no','CalendarMonth','VARCHAR(2)',null,null)
;

-- Column: AD_Sequence_No.CalendarMonth
-- Column: AD_Sequence_No.CalendarMonth
-- 2023-01-03T08:43:20.569Z
UPDATE AD_Column SET IsKey='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-01-03 10:43:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585423
;

-- 2023-01-03T08:43:20.982Z
INSERT INTO t_alter_column values('ad_sequence_no','CalendarMonth','VARCHAR(2)',null,null)
;

-- Column: AD_Sequence_No.CalendarMonth
-- Column: AD_Sequence_No.CalendarMonth
-- 2023-01-03T08:44:39.276Z
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-01-03 10:44:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585423
;

-- Column: AD_Sequence_No.CalendarMonth
-- Column: AD_Sequence_No.CalendarMonth
-- 2023-01-03T08:44:52.347Z
UPDATE AD_Column SET DefaultValue='1', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-01-03 10:44:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585423
;

-- 2023-01-03T08:44:52.785Z
INSERT INTO t_alter_column values('ad_sequence_no','CalendarMonth','VARCHAR(2)',null,'1')
;

-- 2023-01-03T08:44:52.790Z
UPDATE AD_Sequence_No SET CalendarMonth='1' WHERE CalendarMonth IS NULL
;

-- 2023-01-03T08:44:52.791Z
INSERT INTO t_alter_column values('ad_sequence_no','CalendarMonth',null,'NOT NULL',null)
;

-- include CalendarMonth in primary key
ALTER TABLE AD_Sequence_no
    DROP CONSTRAINT ad_sequence_no_pkey,
    ADD CONSTRAINT ad_sequence_no_pkey PRIMARY KEY (AD_Sequence_ID, CalendarYear, CalendarMonth)
;