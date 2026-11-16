-- Run mode: SWING_CLIENT

-- Column: C_BPartner.GRAIRequired
-- 2026-03-26T09:15:26.679Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2026-03-26 09:15:26.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592261
;

-- Name: GRAIRequired
-- 2026-03-26T09:15:36.442Z
UPDATE AD_Reference SET EntityType='D',Updated=TO_TIMESTAMP('2026-03-26 09:15:36.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=542081
;

-- Reference: GRAIRequired
-- Value: D
-- ValueName: Ja, mit Dummy-GRAIs
-- 2026-03-26T09:15:41.950Z
UPDATE AD_Ref_List SET EntityType='D', ValueName='Ja, mit Dummy-GRAIs',Updated=TO_TIMESTAMP('2026-03-26 09:15:41.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544174
;

-- Reference: GRAIRequired
-- Value: N
-- ValueName: Nein
-- 2026-03-26T09:15:43.727Z
UPDATE AD_Ref_List SET EntityType='D', ValueName='Nein',Updated=TO_TIMESTAMP('2026-03-26 09:15:43.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544172
;

-- Reference: GRAIRequired
-- Value: Y
-- ValueName: Ja
-- 2026-03-26T09:15:45.498Z
UPDATE AD_Ref_List SET EntityType='D', ValueName='Ja',Updated=TO_TIMESTAMP('2026-03-26 09:15:45.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544173
;


update AD_Ref_List set ValueName='Yes' where AD_Reference_ID=542081 and value='Y';
update AD_Ref_List set ValueName='No' where AD_Reference_ID=542081 and value='N';
update AD_Ref_List set ValueName='YesWithDummyGRAIs' where AD_Reference_ID=542081 and value='D';
