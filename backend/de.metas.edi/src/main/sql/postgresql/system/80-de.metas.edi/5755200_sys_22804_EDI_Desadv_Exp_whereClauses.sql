-- Run mode: SWING_CLIENT

-- 2025-05-19T12:30:57.501Z
UPDATE EXP_Format SET WhereClause='IsActive=''N'' OR QtyDeliveredInStockingUOM<=0 OR EDI_DesadvLine_ID not in (select EDI_DesadvLine_ID from Edi_Desadv_Pack_Item)',Updated=TO_TIMESTAMP('2025-05-19 12:30:57.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540422
;

-- 2025-05-19T12:35:21.949Z
UPDATE EXP_Format SET Help='Exports those EDI_DesadvLines from this DESADV that are inactive, or have QtyDelivered=0 or are *not* part of any active EDI_Desadv_Pack/EDI_Desadv_Pack_Item.', WhereClause='IsActive=''N'' OR QtyDeliveredInStockingUOM<=0 OR EDI_DesadvLine_ID not in (select EDI_DesadvLine_ID from EDI_Desadv_Pack_Item pi join EDI_Desadv_Pack p on p.EDI_Desadv_Pack_ID=pi.EDI_Desadv_Pack_ID where pi.IsActive=''Y'' and p.IsActive=''Y'')',Updated=TO_TIMESTAMP('2025-05-19 12:35:21.947000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540422
;

-- 2025-05-19T12:38:16.642Z
UPDATE EXP_Format SET WhereClause='IsActive=''Y''',Updated=TO_TIMESTAMP('2025-05-19 12:38:16.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540419
;

-- 2025-05-19T12:41:03.229Z
UPDATE EXP_Format SET Help='Export only Packs that are active and also have an active Pack-Item and an active DESADV-Line with QtyDelivered>0', WhereClause='IsActive=''Y'' and exists (select from EDI_Desadv_Pack_Item pi join EDI_DesadvLine dl on pi.EDI_DesadvLine_ID=dl.EDI_DesadvLine_ID where pi.IsActive=''Y'' and dl.IsActive=''Y'' and dl.QtyDeliveredInUOM>0)',Updated=TO_TIMESTAMP('2025-05-19 12:41:03.227000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540426
;

-- 2025-05-19T12:41:55.909Z
UPDATE EXP_Format SET Help='Export those EDI_DesadvLines from this DESADV that are inactive, or have QtyDelivered=0 or are *not* part of any active EDI_Desadv_Pack/EDI_Desadv_Pack_Item.', WhereClause='IsActive=''N'' OR dl.QtyDeliveredInUOM<=0 OR EDI_DesadvLine_ID not in (select EDI_DesadvLine_ID from EDI_Desadv_Pack_Item pi join EDI_Desadv_Pack p on p.EDI_Desadv_Pack_ID=pi.EDI_Desadv_Pack_ID where pi.IsActive=''Y'' and p.IsActive=''Y'')',Updated=TO_TIMESTAMP('2025-05-19 12:41:55.906000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540422
;

-- 2025-05-19T12:42:11.241Z
UPDATE EXP_Format SET Help='', WhereClause='',Updated=TO_TIMESTAMP('2025-05-19 12:42:11.239000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540426
;

-- 2025-05-19T12:42:31.216Z
UPDATE EXP_Format SET Help='Export only Packs that are active and also have an active Pack-Item and an active DESADV-Line with QtyDelivered>0', WhereClause='IsActive=''Y'' and exists (select from EDI_Desadv_Pack_Item pi join EDI_DesadvLine dl on pi.EDI_DesadvLine_ID=dl.EDI_DesadvLine_ID where pi.IsActive=''Y'' and dl.IsActive=''Y'' and dl.QtyDeliveredInUOM>0)',Updated=TO_TIMESTAMP('2025-05-19 12:42:31.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540419
;

-- 2025-05-19T12:44:10.230Z
UPDATE EXP_Format SET WhereClause='IsActive=''Y'' and exists (select 1 from EDI_Desadv_Pack_Item pi join EDI_DesadvLine dl on pi.EDI_DesadvLine_ID=dl.EDI_DesadvLine_ID where pi.IsActive=''Y'' and dl.IsActive=''Y'' and dl.QtyDeliveredInUOM>0)',Updated=TO_TIMESTAMP('2025-05-19 12:44:10.227000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540419
;

-- 2025-05-19T12:47:26.618Z
UPDATE EXP_Format SET WhereClause='IsActive=''Y'' and exists (select 1 from EDI_DesadvLine dl where dl.IsActive=''Y'' and dl.QtyDeliveredInUOM>0 and EDI_Desadv_Pack_Item.EDI_DesadvLine_ID=dl.EDI_DesadvLine_ID)',Updated=TO_TIMESTAMP('2025-05-19 12:47:26.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540418
;

-- 2025-05-19T12:47:46.618Z
UPDATE EXP_Format SET Help='Export only Items that are active and also have an active DESADV-Line with QtyDelivered>0',Updated=TO_TIMESTAMP('2025-05-19 12:47:46.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540418
;

-- 2025-05-19T12:48:56.036Z
UPDATE EXP_Format SET WhereClause='IsActive=''Y'' and exists (select from EDI_Desadv_Pack_Item pi join EDI_DesadvLine dl on pi.EDI_DesadvLine_ID=dl.EDI_DesadvLine_ID where pi.IsActive=''Y'' and dl.IsActive=''Y'' and dl.QtyDeliveredInUOM>0 and pi.EDI_Desadv_Pack_ID=EDI_Desadv_Pack.EDI_Desadv_Pack',Updated=TO_TIMESTAMP('2025-05-19 12:48:56.034000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540419
;

-- 2025-05-19T12:50:00.389Z
UPDATE EXP_Format SET WhereClause='IsActive=''Y'' and exists (select from EDI_Desadv_Pack_Item pi join EDI_DesadvLine dl on pi.EDI_DesadvLine_ID=dl.EDI_DesadvLine_ID where pi.IsActive=''Y'' and dl.IsActive=''Y'' and dl.QtyDeliveredInUOM>0 and pi.EDI_Desadv_Pack_ID=EDI_Desadv_Pack.EDI_Desadv_Pack_ID)',Updated=TO_TIMESTAMP('2025-05-19 12:50:00.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540419
;

-- 2025-05-19T12:53:16.043Z
UPDATE EXP_Format SET WhereClause='IsActive=''Y'' and EDI_Desadv_Pack_ID in (select pi.EDI_Desadv_Pack_ID from EDI_Desadv_Pack_Item pi join EDI_DesadvLine dl on pi.EDI_DesadvLine_ID=dl.EDI_DesadvLine_ID where pi.IsActive=''Y'' and dl.IsActive=''Y'' and dl.QtyDeliveredInUOM>0)',Updated=TO_TIMESTAMP('2025-05-19 12:53:16.041000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540419
;

-- 2025-05-19T12:54:50.638Z
UPDATE EXP_Format SET WhereClause='IsActive=''Y'' and EDI_DesadvLine_ID in (select dl.EDI_DesadvLine_ID from EDI_DesadvLine dl where dl.IsActive=''Y'' and dl.QtyDeliveredInUOM>0)',Updated=TO_TIMESTAMP('2025-05-19 12:54:50.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540418
;

-- Run mode: SWING_CLIENT

-- 2025-05-19T14:00:45.021Z
UPDATE EXP_Format SET WhereClause='IsActive=''N'' OR QtyDeliveredInUOM<=0 OR EDI_DesadvLine_ID not in (select EDI_DesadvLine_ID from EDI_Desadv_Pack_Item pi join EDI_Desadv_Pack p on p.EDI_Desadv_Pack_ID=pi.EDI_Desadv_Pack_ID where pi.IsActive=''Y'' and p.IsActive=''Y'')',Updated=TO_TIMESTAMP('2025-05-19 14:00:45.018000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540422
;

-- 2025-05-19T14:43:31.012Z
UPDATE EXP_Format SET WhereClause='IsActive=''N'' OR QtyDeliveredInUOM<=0 OR EDI_DesadvLine_ID not in (select EDI_DesadvLine_ID from EDI_Desadv_Pack_Item pi join EDI_Desadv_Pack p on p.EDI_Desadv_Pack_ID=pi.EDI_Desadv_Pack_ID where pi.IsActive=''Y'' and p.IsActive=''Y'' and p.edi_desadv_id=EDI_DesadvLine.EDI_Desadv_ID)',Updated=TO_TIMESTAMP('2025-05-19 14:43:31.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540422
;


-- 2025-05-21T09:35:48.251Z
UPDATE EXP_Format SET Help='No IsActive=''Y'' whereClause because we need this desadv-line to show up in EDI_Exp_DesadvLineWithNoPack in case it''s inactive', WhereClause='',Updated=TO_TIMESTAMP('2025-05-21 09:35:48.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540406
;

