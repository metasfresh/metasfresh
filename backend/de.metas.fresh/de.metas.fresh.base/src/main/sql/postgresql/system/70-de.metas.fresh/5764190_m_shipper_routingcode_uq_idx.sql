-- Run mode: SWING_CLIENT

-- 2025-08-28T06:25:50.839Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540820,0,542512,TO_TIMESTAMP('2025-08-28 06:25:50.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Ein aktiver Leitcode muss für den Lieferweg eindeutig sein.','Y','Y','m_shipper_routingcode_m_shipper_id_name_uq','N',TO_TIMESTAMP('2025-08-28 06:25:50.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'isActive = ''Y''')
;

-- 2025-08-28T06:25:50.848Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540820 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2025-08-28T06:26:03.741Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,590618,541455,540820,0,TO_TIMESTAMP('2025-08-28 06:26:03.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y',10,TO_TIMESTAMP('2025-08-28 06:26:03.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-28T06:26:08.943Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,590617,541456,540820,0,TO_TIMESTAMP('2025-08-28 06:26:08.826000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y',20,TO_TIMESTAMP('2025-08-28 06:26:08.826000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-28T06:27:11.535Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Active routingcodes need to be unique for the shipper', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-08-28 06:27:11.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540820 AND AD_Language='en_US'
;

-- 2025-08-28T06:27:11.536Z
UPDATE AD_Index_Table base SET ErrorMsg=trl.ErrorMsg, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Index_Table_Trl trl  WHERE trl.AD_Index_Table_ID=base.AD_Index_Table_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-08-28T06:27:12.407Z
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-08-28 06:27:12.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540820 AND AD_Language='de_CH'
;

-- 2025-08-28T06:27:13.616Z
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-08-28 06:27:13.614000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540820 AND AD_Language='de_DE'
;

-- 2025-08-28T06:30:10.544Z
CREATE UNIQUE INDEX m_shipper_routingcode_m_shipper_id_name_uq ON M_Shipper_RoutingCode (M_Shipper_ID,Name) WHERE isActive = 'Y'
;

